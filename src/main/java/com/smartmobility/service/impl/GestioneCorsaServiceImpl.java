package com.smartmobility.service.impl;

import com.smartmobility.dto.response.CorsaResponse;
import com.smartmobility.dto.response.PercorsoResponse;
import com.smartmobility.dto.response.StimaCorsaResponse;
import com.smartmobility.integration.MezzoIoTService;
import com.smartmobility.integration.ServizioMappaService;
import com.smartmobility.model.Corsa;
import com.smartmobility.model.MetodoPagamento;
import com.smartmobility.model.Mezzo;
import com.smartmobility.model.Prenotazione;
import com.smartmobility.model.Utente;
import com.smartmobility.model.enums.StatoMezzo;
import com.smartmobility.model.enums.StatoPrenotazione;
import com.smartmobility.repository.CorsaRepository;
import com.smartmobility.repository.MetodoPagamentoRepository;
import com.smartmobility.repository.MezzoRepository;
import com.smartmobility.repository.PrenotazioneRepository;
import com.smartmobility.repository.UtenteRepository;
import com.smartmobility.service.GestioneCorsaService;
import com.smartmobility.service.GestorePagamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GestioneCorsaServiceImpl implements GestioneCorsaService {

    private static final float SOSPENSIONE_FISSO = 1.0f;
    private static final Logger log = LoggerFactory.getLogger(GestioneCorsaServiceImpl.class);

    private final CorsaRepository corsaRepository;
    private final MezzoRepository mezzoRepository;
    private final UtenteRepository utenteRepository;
    private final MetodoPagamentoRepository metodoPagamentoRepository;
    private final PrenotazioneRepository prenotazioneRepository;
    private final MezzoIoTService mezzoIoTService;
    private final ServizioMappaService servizioMappaService;
    private final GestorePagamentoService gestorePagamentoService;

    private final Map<Long, Long> pendingPaymentMethods = new ConcurrentHashMap<>();
    private final Map<Long, Long> pausaStartTimes = new ConcurrentHashMap<>();
    private final Map<Long, Long> totalePausaMillis = new ConcurrentHashMap<>();

    public GestioneCorsaServiceImpl(CorsaRepository corsaRepository,
                                     MezzoRepository mezzoRepository,
                                     UtenteRepository utenteRepository,
                                     MetodoPagamentoRepository metodoPagamentoRepository,
                                     PrenotazioneRepository prenotazioneRepository,
                                     MezzoIoTService mezzoIoTService,
                                     ServizioMappaService servizioMappaService,
                                     GestorePagamentoService gestorePagamentoService) {
        this.corsaRepository = corsaRepository;
        this.mezzoRepository = mezzoRepository;
        this.utenteRepository = utenteRepository;
        this.metodoPagamentoRepository = metodoPagamentoRepository;
        this.prenotazioneRepository = prenotazioneRepository;
        this.mezzoIoTService = mezzoIoTService;
        this.servizioMappaService = servizioMappaService;
        this.gestorePagamentoService = gestorePagamentoService;
    }

    @Override
    @Transactional
    public Long avviaCorsa(Long idMezzo, Long idUtente, String qrCode) {
        if (qrCode == null || qrCode.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "QR Code obbligatorio per avviare la corsa");
        }

        Long idMezzoDaQR = parseQrCode(qrCode);
        if (!idMezzo.equals(idMezzoDaQR)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Il QR Code non corrisponde al veicolo selezionato");
        }

        Mezzo mezzo = mezzoRepository.findById(idMezzo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mezzo non trovato"));

        if (mezzo.getStato() != StatoMezzo.prenotato && mezzo.getStato() != StatoMezzo.disponibile) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Mezzo non disponibile per corsa (stato attuale: " + mezzo.getStato() + ")");
        }

        if (mezzo.getStato() == StatoMezzo.prenotato) {
            boolean hasBooking = prenotazioneRepository.findByUtenteIdAndStato(
                    idUtente, StatoPrenotazione.attiva).stream()
                    .anyMatch(p -> p.getMezzo() != null && p.getMezzo().getIdMezzo().equals(idMezzo));
            if (!hasBooking) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                        "Veicolo prenotato da un altro utente. Non puoi avviare la corsa.");
            }
        }

        Utente utente = utenteRepository.findByIdUtente(idUtente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));

        List<Corsa> corseAttive = corsaRepository.findByUtenteIdAndOrarioFineIsNull(utente.getId());
        if (!corseAttive.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Hai già una corsa attiva. Termina la corsa corrente prima di avviarne una nuova.");
        }

        Corsa corsa = new Corsa();
        corsa.setOrarioInizio(LocalDateTime.now());
        corsa.setCoordinatePartenza(mezzo.getCoordinateMezzo());
        corsa.setUtente(utente);
        corsa.setMezzo(mezzo);
        corsa.setCosto(0);
        corsa.setCoordinateArrivo(null);
        corsa = corsaRepository.save(corsa);

        Long idMetodoPagamento = pendingPaymentMethods.remove(idUtente);
        if (idMetodoPagamento != null) {
            MetodoPagamento metodo = metodoPagamentoRepository.findById(idMetodoPagamento).orElse(null);
            if (metodo != null) {
                corsa.setMetodoPagamento(metodo);
                corsaRepository.save(corsa);
            }
        }

        boolean sbloccato = mezzoIoTService.sbloccoMezzoFisico(idMezzo);
        if (!sbloccato) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Impossibile sbloccare il veicolo: errore di comunicazione IoT");
        }

        mezzo.setStato(StatoMezzo.in_uso);
        mezzoRepository.save(mezzo);

        completaPrenotazioniAttive(utente, mezzo);

        totalePausaMillis.put(corsa.getIdCorsa(), 0L);

        return corsa.getIdCorsa();
    }

    private Long parseQrCode(String qrCode) {
        try {
            if (qrCode.startsWith("QR-")) {
                String[] parts = qrCode.split("-");
                if (parts.length >= 2) {
                    return Long.parseLong(parts[1]);
                }
            }
            return Long.parseLong(qrCode);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "QR Code non valido");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CorsaResponse getCorsaAttiva(Long idUtente) {
        Utente utente = utenteRepository.findByIdUtente(idUtente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));

        List<Corsa> corseAttive = corsaRepository.findByUtenteIdAndOrarioFineIsNull(utente.getId());
        if (corseAttive.isEmpty()) {
            return null;
        }

        if (corseAttive.size() > 1) {
            log.warn("Utente {} ha {} corse attive. Restituita la prima.", idUtente, corseAttive.size());
        }

        Corsa corsa = corseAttive.get(0);
        Mezzo mezzo = corsa.getMezzo();

        Long idMetodo = null;
        String metodoLabel = null;
        MetodoPagamento mp = corsa.getMetodoPagamento();
        if (mp != null) {
            idMetodo = mp.getIdMetodoPagamento();
            String num = mp.getNumCarta();
            if (num != null && num.length() >= 4) {
                metodoLabel = num.substring(num.length() - 4) + " - " + mp.getIntestatarioCarta();
            } else {
                metodoLabel = mp.getIntestatarioCarta();
            }
        }

        return new CorsaResponse(
                corsa.getIdCorsa(),
                utente.getIdUtente(),
                mezzo != null ? mezzo.getIdMezzo() : null,
                corsa.getOrarioInizio() != null ? corsa.getOrarioInizio().toString() : null,
                null,
                (double) corsa.getCosto(),
                null,
                "in_corso",
                idMetodo,
                metodoLabel
        );
    }

    @Override
    @Transactional
    public void terminaCorsa(Long idCorsa) {
        Corsa corsa = corsaRepository.findById(idCorsa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corsa non trovata"));

        aggiornaStima(idCorsa);

        // Aggiunge costo fisso di sospensione una tantum se la corsa è stata sospesa
        if (totalePausaMillis.getOrDefault(idCorsa, 0L) > 0) {
            float costoSospensione = corsa.getCosto() + SOSPENSIONE_FISSO;
            corsa.setCosto(costoSospensione);
        }

        LocalDateTime now = LocalDateTime.now();
        corsa.setOrarioFine(now);
        corsa.setCoordinateArrivo(corsa.getMezzo() != null ? corsa.getMezzo().getCoordinateMezzo() : null);
        corsaRepository.save(corsa);

        if (corsa.getMetodoPagamento() != null) {
            boolean pagato = gestorePagamentoService.pagamentoCorsa(
                    corsa.getUtente().getIdUtente(),
                    corsa.getMetodoPagamento().getIdMetodoPagamento(),
                    idCorsa,
                    (double) corsa.getCosto());
            if (!pagato) {
                throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED,
                        "Pagamento non riuscito. La corsa rimane aperta.");
            }
        }

        completaPrenotazioniAttive(corsa.getUtente(), corsa.getMezzo());

        Mezzo mezzo = corsa.getMezzo();
        if (mezzo != null) {
            mezzoIoTService.bloccoMezzoFisico(mezzo.getIdMezzo());
            mezzo.setStato(StatoMezzo.disponibile);
            mezzoRepository.save(mezzo);
        }

        totalePausaMillis.remove(idCorsa);
        pausaStartTimes.remove(idCorsa);
    }

    @Override
    @Transactional
    public StimaCorsaResponse aggiornaStima(Long idCorsa) {
        Corsa corsa = corsaRepository.findById(idCorsa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corsa non trovata"));

        Mezzo mezzo = corsa.getMezzo();
        if (mezzo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mezzo associato alla corsa non trovato");
        }

        LocalDateTime start = corsa.getOrarioInizio();
        LocalDateTime now = LocalDateTime.now();

        long secondiTotali = ChronoUnit.SECONDS.between(start, now);

        long pausaMs = totalePausaMillis.getOrDefault(idCorsa, 0L);
        long secondiPausa = pausaMs / 1000;
        long secondiEffettivi = Math.max(0, secondiTotali - secondiPausa);

        float ore = (float) secondiEffettivi / 3600;
        float stima = mezzo.getCostoOrario() * ore;

        corsa.setCosto(stima);
        corsaRepository.save(corsa);

        return new StimaCorsaResponse(stima, mezzo.getCostoOrario());
    }

    @Override
    @Transactional
    public boolean sospensioneCorsa(Long idCorsa) {
        Corsa corsa = corsaRepository.findById(idCorsa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corsa non trovata. Verificare i dati."));

        Mezzo mezzo = corsa.getMezzo();
        if (mezzo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mezzo associato alla corsa non trovato");
        }

        if (mezzo.getStato() == StatoMezzo.in_uso) {
            boolean bloccato = mezzoIoTService.bloccoMezzoFisico(mezzo.getIdMezzo());
            if (!bloccato) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Impossibile bloccare il mezzo. Contattare assistenza.");
            }
            mezzo.setStato(StatoMezzo.sospeso);
            mezzoRepository.save(mezzo);
            pausaStartTimes.put(idCorsa, System.currentTimeMillis());
            return true;
        } else if (mezzo.getStato() == StatoMezzo.sospeso) {
            boolean sbloccato = mezzoIoTService.sbloccoMezzoFisico(mezzo.getIdMezzo());
            if (!sbloccato) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Impossibile sbloccare il mezzo. Contattare assistenza.");
            }
            mezzo.setStato(StatoMezzo.in_uso);
            mezzoRepository.save(mezzo);
            Long pStart = pausaStartTimes.remove(idCorsa);
            if (pStart != null) {
                long durata = System.currentTimeMillis() - pStart;
                totalePausaMillis.merge(idCorsa, durata, Long::sum);
            }
            aggiornaStima(idCorsa);
            return true;
        }

        return false;
    }

    @Override
    public boolean richiediSblocco(String qrCode) {
        Long idMezzo;
        try {
            if (qrCode.startsWith("QR-")) {
                String[] parts = qrCode.split("-");
                if (parts.length >= 2) {
                    idMezzo = Long.parseLong(parts[1]);
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "QR Code non valido");
                }
            } else {
                idMezzo = Long.parseLong(qrCode);
            }
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "QR Code non valido");
        }

        Mezzo mezzo = mezzoRepository.findById(idMezzo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mezzo non trovato"));

        if (mezzo.getStato() != StatoMezzo.prenotato && mezzo.getStato() != StatoMezzo.disponibile && mezzo.getStato() != StatoMezzo.in_uso && mezzo.getStato() != StatoMezzo.sospeso) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Mezzo non sbloccabile");
        }

        return mezzoIoTService.sbloccoMezzoFisico(idMezzo);
    }

    @Override
    public PercorsoResponse richiediCalcoloPercorso(String coordinateUtente, String destinazione) {
        Object percorso = servizioMappaService.getPercorso(coordinateUtente, destinazione, null);
        return new PercorsoResponse(
                percorso != null ? percorso.toString() : "Percorso non disponibile",
                "Calcolo percorso completato"
        );
    }

    @Override
    @Transactional
    public void acquisisciSceltaMetodo(Long idMetodoPagamento) {
        MetodoPagamento metodo = metodoPagamentoRepository.findById(idMetodoPagamento)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Metodo di pagamento non trovato"));

        Utente utente = metodo.getUtente();
        if (utente == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Metodo di pagamento non associato ad un utente");
        }

        Long utenteId = utente.getId();

        List<Corsa> corseAttive = corsaRepository.findByUtenteIdAndOrarioFineIsNull(utenteId);
        if (!corseAttive.isEmpty()) {
            Corsa corsa = corseAttive.get(0);
            corsa.setMetodoPagamento(metodo);
            corsaRepository.save(corsa);
        } else {
            pendingPaymentMethods.put(utente.getIdUtente(), idMetodoPagamento);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean controllaDisponibilita(Long idCorsa) {
        Corsa corsa = corsaRepository.findById(idCorsa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corsa non trovata"));

        Mezzo mezzo = corsa.getMezzo();
        if (mezzo == null) return false;

        return mezzo.getStato() == StatoMezzo.disponibile
                || mezzo.getStato() == StatoMezzo.prenotato
                || mezzo.getStato() == StatoMezzo.in_uso;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean controllaDisponibilita(Long idCorsa, String info) {
        Corsa corsa = corsaRepository.findById(idCorsa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corsa non trovata"));
        Mezzo mezzo = corsa.getMezzo();
        if (mezzo == null) return false;

        boolean base = mezzo.getStato() == StatoMezzo.disponibile
                || mezzo.getStato() == StatoMezzo.prenotato
                || mezzo.getStato() == StatoMezzo.in_uso;
        if (!base) return false;

        if (info != null && !info.isBlank()) {
            return info.equalsIgnoreCase(mezzo.getTipo());
        }
        return base;
    }

    private void completaPrenotazioniAttive(Utente utente, Mezzo mezzo) {
        if (utente == null || mezzo == null) return;
        List<Prenotazione> attive = prenotazioneRepository.findByUtenteIdAndStato(
                utente.getIdUtente(), StatoPrenotazione.attiva);
        for (Prenotazione p : attive) {
            if (p.getMezzo() != null && p.getMezzo().getIdMezzo().equals(mezzo.getIdMezzo())) {
                p.setStato(StatoPrenotazione.completata);
                prenotazioneRepository.save(p);
            }
        }
    }
}

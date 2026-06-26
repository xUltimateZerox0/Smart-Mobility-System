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

    private static final double SOSPENSIONE_FISSO = 1.0;
    private static final long PAYMENT_TTL_MS = 30 * 60 * 1000; // 30 minuti
    private static final Logger log = LoggerFactory.getLogger(GestioneCorsaServiceImpl.class);

    private final CorsaRepository corsaRepository;
    private final MezzoRepository mezzoRepository;
    private final UtenteRepository utenteRepository;
    private final MetodoPagamentoRepository metodoPagamentoRepository;
    private final PrenotazioneRepository prenotazioneRepository;
    private final MezzoIoTService mezzoIoTService;
    private final ServizioMappaService servizioMappaService;
    private final GestorePagamentoService gestorePagamentoService;

    private final Map<Long, PendingPayment> pendingPaymentMethods = new ConcurrentHashMap<>();
    private final Map<Long, Long> pausaStartTimes = new ConcurrentHashMap<>();
    private final Object lock = new Object();

    private static class PendingPayment {
        final Long metodoId;
        final long timestamp;
        PendingPayment(Long metodoId, long timestamp) {
            this.metodoId = metodoId;
            this.timestamp = timestamp;
        }
        boolean isExpired() {
            return System.currentTimeMillis() - timestamp > PAYMENT_TTL_MS;
        }
    }

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

        List<Corsa> corseAttive = corsaRepository.findByIdUtenteAndOrarioFineIsNull(idUtente);
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

        PendingPayment pending = pendingPaymentMethods.remove(idUtente);
        if (pending == null || pending.isExpired()) {
            if (pending != null) pendingPaymentMethods.remove(idUtente);
            throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED,
                    "Nessun metodo di pagamento selezionato o selezione scaduta. Seleziona un metodo di pagamento prima di avviare la corsa.");
        }
        MetodoPagamento metodo = metodoPagamentoRepository.findById(pending.metodoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Metodo di pagamento non valido o non trovato"));
        corsa.setMetodoPagamento(metodo);
        corsa.setTotalePausaMillis(0L);
        corsa = corsaRepository.save(corsa);

        boolean sbloccato = mezzoIoTService.sbloccoMezzoFisico(idMezzo);
        if (!sbloccato) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Impossibile sbloccare il veicolo: errore di comunicazione IoT");
        }

        mezzo.setStato(StatoMezzo.in_uso);
        mezzoRepository.save(mezzo);

        completaPrenotazioniAttive(utente, mezzo);

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

        List<Corsa> corseAttive = corsaRepository.findByIdUtenteAndOrarioFineIsNull(idUtente);
        if (corseAttive.isEmpty()) {
            return null;
        }

        if (corseAttive.size() > 1) {
            log.error("Grave inconsistenza: utente {} ha {} corse attive multiple", idUtente, corseAttive.size());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Rilevate multiple corse attive per l'utente. Contattare l'assistenza.");
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
                metodoLabel,
                corsa.isPaused(),
                corsa.getTotalePausaMillis()
        );
    }

    @Override
    @Transactional
    public CorsaResponse terminaCorsa(Long idCorsa) {
        Corsa corsa = corsaRepository.findById(idCorsa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corsa non trovata"));

        if (corsa.getOrarioFine() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Corsa già terminata");
        }

        double cost = calcolaCosto(corsa, true);

        if (corsa.getUtente() != null && corsa.getMetodoPagamento() != null) {
            boolean pagato = gestorePagamentoService.pagamentoCorsa(
                    corsa.getUtente().getIdUtente(),
                    corsa.getMetodoPagamento().getIdMetodoPagamento(),
                    idCorsa,
                    cost);
            if (!pagato) {
                throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED,
                        "Pagamento non riuscito. La corsa rimane aperta.");
            }
        }

        corsa.setCosto((float) cost);
        corsa.setOrarioFine(LocalDateTime.now());
        corsa.setCoordinateArrivo(corsa.getMezzo() != null ? corsa.getMezzo().getCoordinateMezzo() : null);
        corsa.setPaused(false);
        corsaRepository.save(corsa);
        completaPrenotazioniAttive(corsa.getUtente(), corsa.getMezzo());

        Mezzo mezzo = corsa.getMezzo();
        if (mezzo != null) {
            if (mezzo.getStato() != StatoMezzo.sospeso) {
                mezzoIoTService.bloccoMezzoFisico(mezzo.getIdMezzo());
            }
            mezzo.setStato(StatoMezzo.disponibile);
            mezzoRepository.save(mezzo);
        }

        pausaStartTimes.remove(idCorsa);

        String metodoLabel = null;
        Long idMetodo = null;
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
                corsa.getUtente() != null ? corsa.getUtente().getIdUtente() : null,
                mezzo != null ? mezzo.getIdMezzo() : null,
                corsa.getOrarioInizio() != null ? corsa.getOrarioInizio().toString() : null,
                corsa.getOrarioFine() != null ? corsa.getOrarioFine().toString() : null,
                (double) corsa.getCosto(),
                0.0,
                "completata",
                idMetodo,
                metodoLabel,
                false,
                0
        );
    }

    @Override
    @Transactional
    public CorsaResponse forzaTerminaCorsa(Long idCorsa) {
        Corsa corsa = corsaRepository.findById(idCorsa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corsa non trovata"));

        if (corsa.getOrarioFine() != null) {
            return buildCorsaResponse(corsa);
        }

        double cost = calcolaCosto(corsa, true);

        corsa.setCosto((float) cost);
        corsa.setOrarioFine(LocalDateTime.now());
        corsa.setCoordinateArrivo(corsa.getMezzo() != null ? corsa.getMezzo().getCoordinateMezzo() : null);

        if (corsa.getUtente() != null && corsa.getMetodoPagamento() != null) {
            try {
                gestorePagamentoService.pagamentoCorsa(
                        corsa.getUtente().getIdUtente(),
                        corsa.getMetodoPagamento().getIdMetodoPagamento(),
                        idCorsa,
                        cost);
            } catch (Exception e) {
                log.warn("Pagamento forzato non riuscito per corsa {}: {}", idCorsa, e.getMessage());
            }
        }

        corsa.setPaused(false);
        corsaRepository.save(corsa);
        completaPrenotazioniAttive(corsa.getUtente(), corsa.getMezzo());

        Mezzo mezzo = corsa.getMezzo();
        if (mezzo != null) {
            mezzo.setStato(StatoMezzo.disponibile);
            mezzoRepository.save(mezzo);
        }

        pausaStartTimes.remove(idCorsa);

        return buildCorsaResponse(corsa);
    }

    @Override
    @Transactional
    public CorsaResponse terminaCorsaAttivaUtente(Long idUtente) {
        List<Corsa> corseAttive = corsaRepository.findByIdUtenteAndOrarioFineIsNull(idUtente);
        if (corseAttive.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessuna corsa attiva per l'utente");
        }
        return forzaTerminaCorsa(corseAttive.get(0).getIdCorsa());
    }

    private CorsaResponse buildCorsaResponse(Corsa corsa) {
        Mezzo mezzo = corsa.getMezzo();
        String metodoLabel = null;
        Long idMetodo = null;
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
                corsa.getUtente() != null ? corsa.getUtente().getIdUtente() : null,
                mezzo != null ? mezzo.getIdMezzo() : null,
                corsa.getOrarioInizio() != null ? corsa.getOrarioInizio().toString() : null,
                corsa.getOrarioFine() != null ? corsa.getOrarioFine().toString() : null,
                (double) corsa.getCosto(),
                0.0,
                corsa.getOrarioFine() != null ? "completata" : "in_corso",
                idMetodo,
                metodoLabel,
                corsa.isPaused(),
                corsa.getTotalePausaMillis()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public StimaCorsaResponse aggiornaStima(Long idCorsa) {
        Corsa corsa = corsaRepository.findById(idCorsa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corsa non trovata"));

        Mezzo mezzo = corsa.getMezzo();
        if (mezzo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mezzo associato alla corsa non trovato");
        }

        double stima = calcolaCosto(corsa, true);

        return new StimaCorsaResponse(stima, mezzo.getCostoOrario());
    }

    private double calcolaCosto(Corsa corsa) {
        return calcolaCosto(corsa, false);
    }

    private double calcolaCosto(Corsa corsa, boolean includeSospensione) {
        Mezzo mezzo = corsa.getMezzo();
        if (mezzo == null) return 0;

        LocalDateTime start = corsa.getOrarioInizio();
        LocalDateTime end = corsa.getOrarioFine() != null ? corsa.getOrarioFine() : LocalDateTime.now();

        long secondiTotali = ChronoUnit.SECONDS.between(start, end);
        long pausaMs = corsa.getTotalePausaMillis();
        long secondiPausa = pausaMs / 1000;
        long secondiEffettivi = Math.max(0, secondiTotali - secondiPausa);

        double ore = secondiEffettivi / 3600.0;
        double costo = mezzo.getCostoOrario() * ore;

        if (includeSospensione && pausaMs > 0) {
            costo += SOSPENSIONE_FISSO;
        }

        return costo;
    }

    @Override
    @Transactional
    public boolean sospensioneCorsa(Long idCorsa) {
        Corsa corsa = corsaRepository.findById(idCorsa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corsa non trovata. Verificare i dati."));

        if (corsa.getOrarioFine() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Impossibile sospendere: corsa già terminata");
        }

        Mezzo mezzo = corsa.getMezzo();
        if (mezzo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mezzo associato alla corsa non trovato");
        }

        synchronized (lock) {
            if (mezzo.getStato() == StatoMezzo.in_uso) {
                boolean bloccato = mezzoIoTService.bloccoMezzoFisico(mezzo.getIdMezzo());
                if (!bloccato) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Impossibile bloccare il mezzo. Contattare assistenza.");
                }
                mezzo.setStato(StatoMezzo.sospeso);
                mezzoRepository.save(mezzo);
                corsa.setPaused(true);
                pausaStartTimes.put(idCorsa, System.currentTimeMillis());
                corsaRepository.save(corsa);
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
                    corsa.setTotalePausaMillis(corsa.getTotalePausaMillis() + durata);
                }
                corsa.setPaused(false);
                corsaRepository.save(corsa);
                return true;
            }
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
        Map<String, Object> percorso = servizioMappaService.getPercorso(coordinateUtente, destinazione, null);
        if (percorso == null || percorso.isEmpty()) {
            return new PercorsoResponse(coordinateUtente, destinazione, 0.0, 0, 0.0,
                    "Percorso non disponibile");
        }
        Double distanza = percorso.containsKey("distanza") ?
                ((Number) percorso.get("distanza")).doubleValue() : 0.0;
        Integer durata = percorso.containsKey("durata") ?
                ((Number) percorso.get("durata")).intValue() : 0;
        String messaggio = percorso.containsKey("messaggio") ?
                (String) percorso.get("messaggio") : "Percorso calcolato con successo";

        double tariffaStandard = 12.0;
        double ore = durata / 60.0;
        double tariffaOraria = Math.max(tariffaStandard, distanza > 0 ? (distanza * 1.5 / ore) : tariffaStandard);
        double costoStimato = Math.round(tariffaOraria * ore * 100.0) / 100.0;

        return new PercorsoResponse(
                coordinateUtente,
                destinazione,
                distanza,
                durata,
                costoStimato,
                messaggio
        );
    }

    @Override
    @Transactional
    public void acquisisciSceltaMetodo(Long idMetodoPagamento, Long idUtente) {
        MetodoPagamento metodo = metodoPagamentoRepository.findById(idMetodoPagamento)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Metodo di pagamento non trovato"));

        if (metodo.getUtente() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Metodo di pagamento non associato ad un utente");
        }

        if (!metodo.getUtente().getIdUtente().equals(idUtente)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Metodo di pagamento non appartiene all'utente corrente");
        }

        List<Corsa> corseAttive = corsaRepository.findByIdUtenteAndOrarioFineIsNull(idUtente);
        if (!corseAttive.isEmpty()) {
            Corsa corsa = corseAttive.get(0);
            corsa.setMetodoPagamento(metodo);
            corsaRepository.save(corsa);
        } else {
            pendingPaymentMethods.put(idUtente, new PendingPayment(idMetodoPagamento, System.currentTimeMillis()));
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

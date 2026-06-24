package com.smartmobility.service.impl;

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

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GestioneCorsaServiceImpl implements GestioneCorsaService {

    private static final float PAUSA_TASSO = 2.0f;

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
    public Long avviaCorsa(Long idMezzo, Long idUtente) {
        Mezzo mezzo = mezzoRepository.findById(idMezzo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mezzo non trovato"));

        if (mezzo.getStato() != StatoMezzo.prenotato && mezzo.getStato() != StatoMezzo.disponibile) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Mezzo non disponibile per corsa");
        }

        Utente utente = utenteRepository.findByIdUtente(idUtente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));

        List<Corsa> corseAttive = corsaRepository.findByUtenteIdAndOrarioFineIsNull(utente.getId());
        if (!corseAttive.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Utente ha già una corsa attiva");
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

        mezzoIoTService.sbloccoMezzoFisico(idMezzo);
        mezzo.setStato(StatoMezzo.in_uso);
        mezzoRepository.save(mezzo);

        completaPrenotazioniAttive(utente, mezzo);

        totalePausaMillis.put(corsa.getIdCorsa(), 0L);

        return corsa.getIdCorsa();
    }

    @Override
    @Transactional
    public void terminaCorsa(Long idCorsa) {
        Corsa corsa = corsaRepository.findById(idCorsa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corsa non trovata"));

        aggiornaStima(idCorsa);

        LocalDateTime now = LocalDateTime.now();
        corsa.setOrarioFine(now);
        corsa.setCoordinateArrivo(corsa.getMezzo() != null ? corsa.getMezzo().getCoordinateMezzo() : null);
        corsaRepository.save(corsa);

        if (corsa.getMetodoPagamento() != null) {
            gestorePagamentoService.pagamentoCorsa(
                    corsa.getUtente().getIdUtente(),
                    corsa.getMetodoPagamento().getIdMetodoPagamento(),
                    idCorsa,
                    (double) corsa.getCosto());
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
    public StimaCorsaResponse aggiornaStima(Long idCorsa) {
        Corsa corsa = corsaRepository.findById(idCorsa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corsa non trovata"));

        Mezzo mezzo = corsa.getMezzo();
        if (mezzo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mezzo associato alla corsa non trovato");
        }

        LocalDateTime start = corsa.getOrarioInizio();
        LocalDateTime now = LocalDateTime.now();
        long minutiTotali = ChronoUnit.MINUTES.between(start, now);

        long pausaMs = totalePausaMillis.getOrDefault(idCorsa, 0L);
        long minutiPausa = pausaMs / 60000;
        long minutiEffettivi = Math.max(0, minutiTotali - minutiPausa);

        float ore = (float) minutiEffettivi / 60;
        float stima = mezzo.getCostoOrario() * ore;

        if (pausaMs > 0) {
            float orePausa = (float) pausaMs / 3600000;
            stima += PAUSA_TASSO * orePausa;
        }

        corsa.setCosto(stima);
        corsaRepository.save(corsa);

        return new StimaCorsaResponse(stima, mezzo.getCostoOrario());
    }

    @Override
    @Transactional
    public boolean sospensioneCorsa(Long idCorsa) {
        Corsa corsa = corsaRepository.findById(idCorsa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corsa non trovata"));

        Mezzo mezzo = corsa.getMezzo();
        if (mezzo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mezzo associato alla corsa non trovato");
        }

        if (mezzo.getStato() == StatoMezzo.in_uso) {
            mezzo.setStato(StatoMezzo.sospeso);
            mezzoRepository.save(mezzo);
            pausaStartTimes.put(idCorsa, System.currentTimeMillis());
            return true;
        } else if (mezzo.getStato() == StatoMezzo.sospeso) {
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
    public boolean controllaDisponibilita(Long idCorsa, String info) {
        boolean base = controllaDisponibilita(idCorsa);
        if (!base) return false;

        if (info != null && !info.isBlank()) {
            Corsa corsa = corsaRepository.findById(idCorsa)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corsa non trovata"));
            Mezzo mezzo = corsa.getMezzo();
            if (mezzo != null && info.equalsIgnoreCase(mezzo.getTipo())) {
                return true;
            }
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

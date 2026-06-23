package com.smartmobility.service.impl;

import com.smartmobility.dto.response.PercorsoResponse;
import com.smartmobility.integration.MezzoIoTService;
import com.smartmobility.integration.ServizioMappaService;
import com.smartmobility.model.Corsa;
import com.smartmobility.model.MetodoPagamento;
import com.smartmobility.model.Mezzo;
import com.smartmobility.model.Utente;
import com.smartmobility.model.enums.StatoMezzo;
import com.smartmobility.repository.CorsaRepository;
import com.smartmobility.repository.MetodoPagamentoRepository;
import com.smartmobility.repository.MezzoRepository;
import com.smartmobility.repository.UtenteRepository;
import com.smartmobility.service.GestioneCorsaService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class GestioneCorsaServiceImpl implements GestioneCorsaService {

    private final CorsaRepository corsaRepository;
    private final MezzoRepository mezzoRepository;
    private final UtenteRepository utenteRepository;
    private final MetodoPagamentoRepository metodoPagamentoRepository;
    private final MezzoIoTService mezzoIoTService;
    private final ServizioMappaService servizioMappaService;

    public GestioneCorsaServiceImpl(CorsaRepository corsaRepository,
                                     MezzoRepository mezzoRepository,
                                     UtenteRepository utenteRepository,
                                     MetodoPagamentoRepository metodoPagamentoRepository,
                                     MezzoIoTService mezzoIoTService,
                                     ServizioMappaService servizioMappaService) {
        this.corsaRepository = corsaRepository;
        this.mezzoRepository = mezzoRepository;
        this.utenteRepository = utenteRepository;
        this.metodoPagamentoRepository = metodoPagamentoRepository;
        this.mezzoIoTService = mezzoIoTService;
        this.servizioMappaService = servizioMappaService;
    }

    @Override
    @Transactional
    public void avviaCorsa(Long idMezzo, Long idUtente) {
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
        corsaRepository.save(corsa);

        mezzo.setStato(StatoMezzo.in_uso);
        mezzoRepository.save(mezzo);
    }

    @Override
    @Transactional
    public void terminaCorsa(Long idCorsa) {
        Corsa corsa = corsaRepository.findById(idCorsa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corsa non trovata"));

        corsa.setOrarioFine(LocalDateTime.now());
        corsaRepository.save(corsa);

        Mezzo mezzo = corsa.getMezzo();
        if (mezzo != null) {
            mezzo.setStato(StatoMezzo.disponibile);
            mezzoRepository.save(mezzo);
        }

        if (corsa.getMetodoPagamento() != null) {
            corsa.setCoordinateArrivo(mezzo != null ? mezzo.getCoordinateMezzo() : null);
            corsaRepository.save(corsa);
        }
    }

    @Override
    public Float aggiornaStima(Long idCorsa) {
        Corsa corsa = corsaRepository.findById(idCorsa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Corsa non trovata"));

        Mezzo mezzo = corsa.getMezzo();
        if (mezzo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mezzo associato alla corsa non trovato");
        }

        LocalDateTime start = corsa.getOrarioInizio();
        LocalDateTime now = LocalDateTime.now();
        float ore = (float) ChronoUnit.MINUTES.between(start, now) / 60;

        float stima = mezzo.getCostoOrario() * ore;
        corsa.setCosto(stima);
        corsaRepository.save(corsa);

        return stima;
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
            return true;
        } else if (mezzo.getStato() == StatoMezzo.sospeso) {
            mezzo.setStato(StatoMezzo.in_uso);
            mezzoRepository.save(mezzo);
            return true;
        }

        return false;
    }

    @Override
    public boolean richiediSblocco(String qrCode) {
        Long idMezzo;
        try {
            idMezzo = Long.parseLong(qrCode);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "QR Code non valido");
        }

        Mezzo mezzo = mezzoRepository.findById(idMezzo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mezzo non trovato"));

        if (mezzo.getStato() != StatoMezzo.prenotato && mezzo.getStato() != StatoMezzo.disponibile) {
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

        List<Corsa> corseAttive = corsaRepository.findByUtenteIdAndOrarioFineIsNull(utente.getId());
        if (corseAttive.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nessuna corsa attiva per l'utente");
        }

        Corsa corsa = corseAttive.get(0);
        corsa.setMetodoPagamento(metodo);
        corsaRepository.save(corsa);
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
}

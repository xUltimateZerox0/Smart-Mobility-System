package com.smartmobility.service.impl;

import com.smartmobility.dto.response.PrenotazioneResponse;
import com.smartmobility.model.Mezzo;
import com.smartmobility.model.Prenotazione;
import com.smartmobility.model.Utente;
import com.smartmobility.model.enums.StatoMezzo;
import com.smartmobility.model.enums.StatoPrenotazione;
import com.smartmobility.repository.MezzoRepository;
import com.smartmobility.repository.PrenotazioneRepository;
import com.smartmobility.repository.UtenteRepository;
import com.smartmobility.service.GestionePrenotazioneService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class GestionePrenotazioneServiceImpl implements GestionePrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final MezzoRepository mezzoRepository;
    private final UtenteRepository utenteRepository;

    public GestionePrenotazioneServiceImpl(PrenotazioneRepository prenotazioneRepository,
                                            MezzoRepository mezzoRepository,
                                            UtenteRepository utenteRepository) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.mezzoRepository = mezzoRepository;
        this.utenteRepository = utenteRepository;
    }

    @Override
    @Transactional
    public void inviaRichiestaPrenotazione(Long idMezzo, Long idUtente) {
        creaPrenotazione(idMezzo, idUtente);
    }

    @Override
    public List<PrenotazioneResponse> richiediLista() {
        return prenotazioneRepository.findAll().stream()
                .map(this::toPrenotazioneResponse)
                .toList();
    }

    @Override
    public List<PrenotazioneResponse> richiediListaPerUtente(Long idUtente) {
        Utente utente = utenteRepository.findByIdUtente(idUtente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));
        return prenotazioneRepository.findAll().stream()
                .filter(p -> p.getUtente() != null && p.getUtente().getId().equals(utente.getId()))
                .map(this::toPrenotazioneResponse)
                .toList();
    }

    @Override
    @Transactional
    public boolean annullaPrenotazione(Long idPrenotazione) {
        Prenotazione prenotazione = prenotazioneRepository.findById(idPrenotazione)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prenotazione non trovata"));

        if (prenotazione.getStato() != StatoPrenotazione.attiva) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Prenotazione non è attiva");
        }

        prenotazione.setStato(StatoPrenotazione.annullata);
        prenotazioneRepository.save(prenotazione);

        Mezzo mezzo = prenotazione.getMezzo();
        if (mezzo != null && mezzo.getStato() == StatoMezzo.prenotato) {
            mezzo.setStato(StatoMezzo.disponibile);
            mezzoRepository.save(mezzo);
        }

        return true;
    }

    @Override
    @Transactional
    public void gestisciTimeout() {
        List<Prenotazione> prenotazioniAttive = prenotazioneRepository.findByStato(StatoPrenotazione.attiva);
        LocalTime now = LocalTime.now();

        for (Prenotazione p : prenotazioniAttive) {
            if (p.getOrarioInizio() != null) {
                long minutiPassati = java.time.Duration.between(p.getOrarioInizio(), now).toMinutes();
                if (minutiPassati >= 15) {
                    p.setStato(StatoPrenotazione.scaduta);
                    prenotazioneRepository.save(p);

                    Mezzo mezzo = p.getMezzo();
                    if (mezzo != null && mezzo.getStato() == StatoMezzo.prenotato) {
                        mezzo.setStato(StatoMezzo.disponibile);
                        mezzoRepository.save(mezzo);
                    }
                }
            }
        }
    }

    @Override
    public void notificaScadenzaTempo(Long idPrenotazione) {
        Prenotazione prenotazione = prenotazioneRepository.findById(idPrenotazione)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prenotazione non trovata"));

        if (prenotazione.getStato() == StatoPrenotazione.attiva) {
            gestisciTimeout();
        }
    }

    private Prenotazione creaPrenotazione(Long idMezzo, Long idUtente) {
        Mezzo mezzo = mezzoRepository.findById(idMezzo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mezzo non trovato"));

        if (mezzo.getStato() != StatoMezzo.disponibile) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Mezzo non disponibile");
        }

        Utente utente = utenteRepository.findByIdUtente(idUtente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));

        List<Prenotazione> attive = prenotazioneRepository.findByUtenteIdAndStato(idUtente, StatoPrenotazione.attiva);
        if (!attive.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Utente ha già una prenotazione attiva");
        }

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setMezzo(mezzo);
        prenotazione.setUtente(utente);
        prenotazione.setOrarioInizio(LocalTime.now());
        prenotazione.setData(LocalDate.now());
        prenotazione.setStato(StatoPrenotazione.attiva);
        prenotazioneRepository.save(prenotazione);

        mezzo.setStato(StatoMezzo.prenotato);
        mezzoRepository.save(mezzo);

        return prenotazione;
    }

    private PrenotazioneResponse toPrenotazioneResponse(Prenotazione p) {
        String nomeVeicolo = null;
        String tipoVeicolo = null;
        if (p.getMezzo() != null) {
            nomeVeicolo = p.getMezzo().getTipo() + " #" + p.getMezzo().getIdMezzo();
            tipoVeicolo = p.getMezzo().getTipo();
        }
        return new PrenotazioneResponse(
                p.getIdPrenotazione(),
                p.getUtente() != null ? p.getUtente().getIdUtente() : null,
                p.getMezzo() != null ? p.getMezzo().getIdMezzo() : null,
                p.getData() != null ? p.getData().toString() : null,
                p.getOrarioInizio() != null ? p.getOrarioInizio().toString() : null,
                p.getStato() != null ? p.getStato().name() : null,
                nomeVeicolo,
                tipoVeicolo
        );
    }
}

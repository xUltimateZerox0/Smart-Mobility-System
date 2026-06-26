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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class GestionePrenotazioneServiceImpl implements GestionePrenotazioneService {

    private static final String PRENOTAZIONE_NON_TROVATA = "Prenotazione non trovata";

    private final PrenotazioneRepository prenotazioneRepository;
    private final MezzoRepository mezzoRepository;
    private final UtenteRepository utenteRepository;

    private GestionePrenotazioneService selfProxy;

    public GestionePrenotazioneServiceImpl(PrenotazioneRepository prenotazioneRepository,
                                            MezzoRepository mezzoRepository,
                                            UtenteRepository utenteRepository) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.mezzoRepository = mezzoRepository;
        this.utenteRepository = utenteRepository;
    }

    @Autowired
    @Lazy
    public void setSelfProxy(GestionePrenotazioneService selfProxy) {
        this.selfProxy = selfProxy;
    }

    @Override
    @Transactional
    public PrenotazioneResponse inviaRichiestaPrenotazione(Long idMezzo, Long idUtente, String orarioInizio) {
        Prenotazione p = creaPrenotazione(idMezzo, idUtente, orarioInizio);
        return toPrenotazioneResponse(p);
    }

    @Override
    @Transactional(readOnly = true)
    public String getQRCode(Long idPrenotazione) {
        Prenotazione prenotazione = prenotazioneRepository.findByIdWithMezzo(idPrenotazione)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PRENOTAZIONE_NON_TROVATA));
        if (prenotazione.getMezzo() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Prenotazione non ha un mezzo associato");
        }
        return "QR-" + prenotazione.getMezzo().getIdMezzo() + "-" + idPrenotazione + "-" + System.currentTimeMillis();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrenotazioneResponse> richiediLista() {
        return prenotazioneRepository.findAllWithDetails().stream()
                .map(this::toPrenotazioneResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrenotazioneResponse> richiediListaPerUtente(Long idUtente) {
        Utente utente = utenteRepository.findByIdUtente(idUtente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));
        return prenotazioneRepository.findByUtenteIdWithDetails(utente.getId()).stream()
                .map(this::toPrenotazioneResponse)
                .toList();
    }

    @Override
    @Transactional
    public boolean annullaPrenotazione(Long idPrenotazione) {
        Prenotazione prenotazione = prenotazioneRepository.findByIdWithMezzo(idPrenotazione)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PRENOTAZIONE_NON_TROVATA));

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
        List<Prenotazione> prenotazioniAttive = prenotazioneRepository.findByStatoWithDetails(StatoPrenotazione.attiva);
        LocalDateTime now = LocalDateTime.now();

        for (Prenotazione p : prenotazioniAttive) {
            if (p.getOrarioInizio() != null && p.getData() != null) {
                LocalDateTime inizio = LocalDateTime.of(p.getData(), p.getOrarioInizio());
                long minutiPassati = Duration.between(inizio, now).toMinutes();
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
    @Transactional
    public void notificaScadenzaTempo(Long idPrenotazione) {
        Prenotazione prenotazione = prenotazioneRepository.findById(idPrenotazione)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PRENOTAZIONE_NON_TROVATA));

        if (prenotazione.getStato() == StatoPrenotazione.attiva) {
            selfProxy.gestisciTimeout();
        }
    }

    private Prenotazione creaPrenotazione(Long idMezzo, Long idUtente, String orarioInizioStr) {
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

        LocalTime orarioInizio;
        LocalDate data;
        if (orarioInizioStr != null && orarioInizioStr.contains("T")) {
            LocalDateTime dateTime = LocalDateTime.parse(orarioInizioStr);
            orarioInizio = dateTime.toLocalTime();
            data = dateTime.toLocalDate();
        } else if (orarioInizioStr != null) {
            orarioInizio = LocalTime.parse(orarioInizioStr);
            data = LocalDate.now();
        } else {
            orarioInizio = LocalTime.now();
            data = LocalDate.now();
        }

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setMezzo(mezzo);
        prenotazione.setUtente(utente);
        prenotazione.setOrarioInizio(orarioInizio);
        prenotazione.setData(data);
        prenotazione.setStato(StatoPrenotazione.attiva);
        prenotazione.setQrCode("QR-" + mezzo.getIdMezzo() + "-" + System.currentTimeMillis());
        prenotazioneRepository.save(prenotazione);

        mezzo.setStato(StatoMezzo.prenotato);
        mezzoRepository.save(mezzo);

        return prenotazione;
    }

    private PrenotazioneResponse toPrenotazioneResponse(Prenotazione p) {
        String nomeVeicolo = null;
        String tipoVeicolo = null;
        Long idVeicolo = null;
        if (p.getMezzo() != null) {
            nomeVeicolo = p.getMezzo().getTipo() + " #" + p.getMezzo().getIdMezzo();
            tipoVeicolo = p.getMezzo().getTipo();
            idVeicolo = p.getMezzo().getIdMezzo();
        }
        String dataInizio;
        if (p.getData() != null && p.getOrarioInizio() != null) {
            dataInizio = p.getData().toString() + "T" + p.getOrarioInizio().toString();
        } else if (p.getData() != null) {
            dataInizio = p.getData().toString();
        } else {
            dataInizio = null;
        }
        return new PrenotazioneResponse(
                p.getIdPrenotazione(),
                p.getUtente() != null ? p.getUtente().getIdUtente() : null,
                p.getMezzo() != null ? p.getMezzo().getIdMezzo() : null,
                dataInizio,
                null,
                p.getStato() != null ? p.getStato().name() : null,
                nomeVeicolo,
                tipoVeicolo,
                p.getOrarioInizio() != null ? p.getOrarioInizio().toString() : null,
                p.getQrCode(),
                idVeicolo
        );
    }
}

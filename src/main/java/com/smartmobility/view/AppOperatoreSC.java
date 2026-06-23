package com.smartmobility.view;

import com.smartmobility.service.GestioneAutenticazioneService;
import com.smartmobility.service.GestionePrenotazioneService;
import com.smartmobility.service.GestioneUtentiService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AppOperatoreSC {

    private final GestioneUtentiService gestioneUtentiService;
    private final GestionePrenotazioneService gestionePrenotazioneService;
    private final GestioneAutenticazioneService gestioneAutenticazioneService;

    public AppOperatoreSC(GestioneUtentiService gestioneUtentiService, GestionePrenotazioneService gestionePrenotazioneService, GestioneAutenticazioneService gestioneAutenticazioneService) {
        this.gestioneUtentiService = gestioneUtentiService;
        this.gestionePrenotazioneService = gestionePrenotazioneService;
        this.gestioneAutenticazioneService = gestioneAutenticazioneService;
    }

    public void mostraPrenotazioni() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void mostraErrore(String msg) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void mostraSuccesso(String msg) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void mostraReport(Long idUtente) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void richiediListaPrenotazioni() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void selezionaPrenotazione(Long idPrenotazione) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void aggiornaReport(Long idUtente) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void richiestaLogout(String email) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

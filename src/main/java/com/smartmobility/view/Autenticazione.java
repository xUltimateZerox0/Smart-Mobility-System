package com.smartmobility.view;

import com.smartmobility.service.GestioneAutenticazioneService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class Autenticazione {

    private final GestioneAutenticazioneService gestioneAutenticazioneService;

    public Autenticazione(GestioneAutenticazioneService gestioneAutenticazioneService) {
        this.gestioneAutenticazioneService = gestioneAutenticazioneService;
    }

    public void mostraFormRegistrazione() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void inserisciCredenziali(String nome, String cognome, String email, String password, String datanascita) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void registrazioneUtente() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

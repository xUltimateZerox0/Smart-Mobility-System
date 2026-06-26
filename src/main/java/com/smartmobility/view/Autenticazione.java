package com.smartmobility.view;

import com.smartmobility.dto.response.AuthResponse;
import com.smartmobility.service.GestioneAutenticazioneService;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@SuppressWarnings("unused")
public class Autenticazione {

    private static final Logger LOG = Logger.getLogger(Autenticazione.class.getName());

    private final GestioneAutenticazioneService gestioneAutenticazioneService;
    private Long idAttore;
    private Long idSessioneAttore;

    public Autenticazione(GestioneAutenticazioneService gestioneAutenticazioneService) {
        this.gestioneAutenticazioneService = gestioneAutenticazioneService;
    }

    public void mostraFormRegistrazione() {
        LOG.info("Mostra form registrazione");
    }

    public void registraUtente(String nome, String cognome, String email, String password, String datanascita) {
        AuthResponse result = gestioneAutenticazioneService.verificaValidita(nome, cognome, email, password, datanascita);
        LOG.info("Registrazione completata per: " + result.getEmail());
        this.idAttore = result.getIdUtente();
    }

    public void accedi(String email, String password) {
        AuthResponse result = gestioneAutenticazioneService.invioCredenziali(email, password);
        LOG.info("Login effettuato per: " + result.getEmail() + " (ruolo: " + result.getRuolo() + ")");
        this.idAttore = result.getIdUtente();
    }

    public void registrazioneUtente() {
        LOG.info("Registrazione utente completata");
    }

    public Long getIdAttore() {
        return idAttore;
    }

    public void setIdAttore(Long idAttore) {
        this.idAttore = idAttore;
    }

    public Long getIdSessioneAttore() {
        return idSessioneAttore;
    }

    public void setIdSessioneAttore(Long idSessioneAttore) {
        this.idSessioneAttore = idSessioneAttore;
    }
}

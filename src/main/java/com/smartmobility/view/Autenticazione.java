package com.smartmobility.view;

import com.smartmobility.dto.response.AuthResponse;
import com.smartmobility.service.GestioneAutenticazioneService;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unused")
public class Autenticazione {

    private final GestioneAutenticazioneService gestioneAutenticazioneService;
    private Long idAttore;
    private Long idSessioneAttore;

    public Autenticazione(GestioneAutenticazioneService gestioneAutenticazioneService) {
        this.gestioneAutenticazioneService = gestioneAutenticazioneService;
    }

    public void mostraFormRegistrazione() {
        System.out.println("Mostra form registrazione");
    }

    public void inserisciCredenziali(String nome, String cognome, String email, String password, String datanascita) {
        AuthResponse result = gestioneAutenticazioneService.verificaValidita(nome, cognome, email, password, datanascita);
        System.out.println("Registrazione completata per: " + result.getEmail());
    }

    public void registrazioneUtente() {
        System.out.println("Registrazione utente completata");
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

package com.smartmobility.view;

import com.smartmobility.service.GestioneAutenticazioneService;
import com.smartmobility.service.GestionePrenotazioneService;
import com.smartmobility.service.GestioneUtentiService;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unused")
public class AppOperatoreSC {

    private final GestioneUtentiService gestioneUtentiService;
    private final GestionePrenotazioneService gestionePrenotazioneService;
    private final GestioneAutenticazioneService gestioneAutenticazioneService;
    private Long idOperatoreSC;
    private Long idSessioneOperatoreSC;

    public AppOperatoreSC(GestioneUtentiService gestioneUtentiService, GestionePrenotazioneService gestionePrenotazioneService, GestioneAutenticazioneService gestioneAutenticazioneService) {
        this.gestioneUtentiService = gestioneUtentiService;
        this.gestionePrenotazioneService = gestionePrenotazioneService;
        this.gestioneAutenticazioneService = gestioneAutenticazioneService;
    }

    public void mostraPrenotazioni() {
        gestionePrenotazioneService.richiediLista();
    }

    public void mostraErrore(String msg) {
        System.err.println("ERRORE: " + msg);
    }

    public void mostraSuccesso(String msg) {
        System.out.println("SUCCESSO: " + msg);
    }

    public void mostraReport(Long idUtente) {
        gestioneUtentiService.cercaReport(idUtente);
    }

    public void richiediListaPrenotazioni() {
        gestionePrenotazioneService.richiediLista();
    }

    public void selezionaPrenotazione(Long idPrenotazione) {
        System.out.println("Prenotazione selezionata: " + idPrenotazione);
    }

    public void aggiornaReport(Long idUtente) {
        System.out.println("Report aggiornato per utente: " + idUtente);
    }

    public void richiestaLogout(String email) {
        gestioneAutenticazioneService.inviaRichiestaLogout(email);
    }

    public Long getIdOperatoreSC() {
        return idOperatoreSC;
    }

    public void setIdOperatoreSC(Long idOperatoreSC) {
        this.idOperatoreSC = idOperatoreSC;
    }

    public Long getIdSessioneOperatoreSC() {
        return idSessioneOperatoreSC;
    }

    public void setIdSessioneOperatoreSC(Long idSessioneOperatoreSC) {
        this.idSessioneOperatoreSC = idSessioneOperatoreSC;
    }
}

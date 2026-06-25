package com.smartmobility.view;

import com.smartmobility.dto.response.UtenteResponse;
import com.smartmobility.service.GestioneAutenticazioneService;
import com.smartmobility.service.GestionePrenotazioneService;
import com.smartmobility.service.GestioneUtentiService;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
        try {
            String report = gestioneUtentiService.cercaReport(idUtente);
            if (report != null && !report.isBlank()) {
                System.out.println("REPORT per utente " + idUtente + ": " + report);
            } else {
                System.out.println("REPORT per utente " + idUtente + ": Nessun report presente");
            }
        } catch (ResponseStatusException e) {
            mostraErrore(e.getReason() != null ? e.getReason() : "Errore nel recupero report");
        }
    }

    public void mostraElencoUtenti() {
        try {
            List<UtenteResponse> utenti = gestioneUtentiService.getElencoUtenti();
            System.out.println("ELENCO UTENTI:");
            for (UtenteResponse u : utenti) {
                System.out.println("  ID=" + u.getIdUtente() + " | " + u.getNome() + " " + u.getCognome() + " | " + u.getEmail() + " | stato=" + u.getStato());
            }
        } catch (ResponseStatusException e) {
            mostraErrore(e.getReason() != null ? e.getReason() : "Errore");
        }
    }

    public void moderazioneUtente(Long idUtente) {
        try {
            boolean result = gestioneUtentiService.gestioneUtente(idUtente);
            System.out.println("MODERAZIONE utente " + idUtente + ": " + (result ? "completata" : "fallita"));
        } catch (ResponseStatusException e) {
            mostraErrore(e.getReason() != null ? e.getReason() : "Errore");
        }
    }

    public void bloccaUtente(Long idUtente) {
        try {
            boolean result = gestioneUtentiService.bloccaUtente(idUtente);
            System.out.println("BLOCCO utente " + idUtente + ": " + (result ? "completato" : "fallito"));
        } catch (ResponseStatusException e) {
            mostraErrore(e.getReason() != null ? e.getReason() : "Errore");
        }
    }

    public void sbloccaUtente(Long idUtente) {
        try {
            boolean result = gestioneUtentiService.sbloccaUtente(idUtente);
            System.out.println("SBLOCCO utente " + idUtente + ": " + (result ? "completato" : "fallito"));
        } catch (ResponseStatusException e) {
            mostraErrore(e.getReason() != null ? e.getReason() : "Errore");
        }
    }

    public void disattivaUtente(Long idUtente) {
        try {
            boolean result = gestioneUtentiService.disattivaUtente(idUtente);
            System.out.println("DISATTIVAZIONE utente " + idUtente + ": " + (result ? "completata" : "fallita"));
        } catch (ResponseStatusException e) {
            mostraErrore(e.getReason() != null ? e.getReason() : "Errore");
        }
    }

    public void richiediListaPrenotazioni() {
        gestionePrenotazioneService.richiediLista();
    }

    public void selezionaPrenotazione(Long idPrenotazione) {
        System.out.println("Prenotazione selezionata: " + idPrenotazione);
    }

    public void aggiornaReport(Long idUtente, String azione) {
        try {
            gestioneUtentiService.azioneCorrettiva(idUtente, azione);
            System.out.println("AZIONE CORRETTIVA per utente " + idUtente + ": " + azione);
        } catch (ResponseStatusException e) {
            mostraErrore(e.getReason() != null ? e.getReason() : "Errore nell'aggiornamento report");
        }
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

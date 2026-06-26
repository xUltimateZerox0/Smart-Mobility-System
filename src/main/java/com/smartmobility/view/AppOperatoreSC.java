package com.smartmobility.view;

import com.smartmobility.dto.response.UtenteResponse;
import com.smartmobility.service.GestioneAutenticazioneService;
import com.smartmobility.service.GestionePrenotazioneService;
import com.smartmobility.service.GestioneUtentiService;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@SuppressWarnings("unused")
public class AppOperatoreSC {

    private static final Logger LOG = Logger.getLogger(AppOperatoreSC.class.getName());
    private static final String ERRORE_GENERICO = "Errore";

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
        LOG.log(Level.SEVERE, "ERRORE: {0}", msg);
    }

    public void mostraSuccesso(String msg) {
        LOG.log(Level.INFO, "SUCCESSO: {0}", msg);
    }

    public void mostraReport(Long idUtente) {
        try {
            String report = gestioneUtentiService.cercaReport(idUtente);
            if (report != null && !report.isBlank()) {
                if (LOG.isLoggable(Level.INFO)) {
                    LOG.log(Level.INFO, String.format("REPORT per utente %s: %s", idUtente, report));
                }
            } else {
                LOG.log(Level.INFO, "REPORT per utente {0}: Nessun report presente", idUtente);
            }
        } catch (ResponseStatusException e) {
            mostraErrore(e.getReason() != null ? e.getReason() : "Errore nel recupero report");
        }
    }

    public void mostraElencoUtenti() {
        try {
            List<UtenteResponse> utenti = gestioneUtentiService.getElencoUtenti();
            LOG.info("ELENCO UTENTI:");
            for (UtenteResponse u : utenti) {
                LOG.info("  ID=" + u.getIdUtente() + " | " + u.getNome() + " " + u.getCognome() + " | " + u.getEmail() + " | stato=" + u.getStato());
            }
        } catch (ResponseStatusException e) {
            mostraErrore(e.getReason() != null ? e.getReason() : ERRORE_GENERICO);
        }
    }

    public void moderazioneUtente(Long idUtente) {
        try {
            boolean result = gestioneUtentiService.gestioneUtente(idUtente);
            LOG.log(Level.INFO, "MODERAZIONE utente {0}: {1}", new Object[]{idUtente, result ? "completata" : "fallita"});
        } catch (ResponseStatusException e) {
            mostraErrore(e.getReason() != null ? e.getReason() : ERRORE_GENERICO);
        }
    }

    public void bloccaUtente(Long idUtente) {
        try {
            boolean result = gestioneUtentiService.bloccaUtente(idUtente);
            LOG.log(Level.INFO, "BLOCCO utente {0}: {1}", new Object[]{idUtente, result ? "completato" : "fallito"});
        } catch (ResponseStatusException e) {
            mostraErrore(e.getReason() != null ? e.getReason() : ERRORE_GENERICO);
        }
    }

    public void sbloccaUtente(Long idUtente) {
        try {
            boolean result = gestioneUtentiService.sbloccaUtente(idUtente);
            LOG.log(Level.INFO, "SBLOCCO utente {0}: {1}", new Object[]{idUtente, result ? "completato" : "fallito"});
        } catch (ResponseStatusException e) {
            mostraErrore(e.getReason() != null ? e.getReason() : ERRORE_GENERICO);
        }
    }

    public void disattivaUtente(Long idUtente) {
        try {
            boolean result = gestioneUtentiService.disattivaUtente(idUtente);
            LOG.log(Level.INFO, "DISATTIVAZIONE utente {0}: {1}", new Object[]{idUtente, result ? "completata" : "fallita"});
        } catch (ResponseStatusException e) {
            mostraErrore(e.getReason() != null ? e.getReason() : ERRORE_GENERICO);
        }
    }

    public void richiediListaPrenotazioni() {
        gestionePrenotazioneService.richiediLista();
    }

    public void selezionaPrenotazione(Long idPrenotazione) {
        LOG.log(Level.INFO, "Prenotazione selezionata: {0}", idPrenotazione);
    }

    public void aggiornaReport(Long idUtente, String azione) {
        try {
            gestioneUtentiService.azioneCorrettiva(idUtente, azione);
            LOG.log(Level.INFO, "AZIONE CORRETTIVA per utente {0}: {1}", new Object[]{idUtente, azione});
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

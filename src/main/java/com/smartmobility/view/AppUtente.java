package com.smartmobility.view;

import com.smartmobility.dto.response.CorsaResponse;
import com.smartmobility.dto.response.MetodoPagamentoResponse;
import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.dto.response.StimaCorsaResponse;
import com.smartmobility.service.GestioneAutenticazioneService;
import com.smartmobility.service.GestioneCorsaService;
import com.smartmobility.service.GestionePrenotazioneService;
import com.smartmobility.service.GestorePagamentoService;
import com.smartmobility.service.RicercaMezziService;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@SuppressWarnings("unused")
public class AppUtente {

    private static final Logger LOG = Logger.getLogger(AppUtente.class.getName());

    private final GestioneCorsaService gestioneCorsaService;
    private final GestorePagamentoService gestorePagamentoService;
    private final RicercaMezziService ricercaMezziService;
    private final GestionePrenotazioneService gestionePrenotazioneService;
    private final GestioneAutenticazioneService gestioneAutenticazioneService;
    private Long idUtente;
    private Long idSessioneUtente;

    public AppUtente(GestioneCorsaService gestioneCorsaService, GestorePagamentoService gestorePagamentoService, RicercaMezziService ricercaMezziService, GestionePrenotazioneService gestionePrenotazioneService, GestioneAutenticazioneService gestioneAutenticazioneService) {
        this.gestioneCorsaService = gestioneCorsaService;
        this.gestorePagamentoService = gestorePagamentoService;
        this.ricercaMezziService = ricercaMezziService;
        this.gestionePrenotazioneService = gestionePrenotazioneService;
        this.gestioneAutenticazioneService = gestioneAutenticazioneService;
    }

    public void mostraErrore(String msg) {
        LOG.log(Level.SEVERE, "ERRORE: {0}", msg);
    }

    public void mostraStima(Long idCorsa) {
        StimaCorsaResponse result = gestioneCorsaService.aggiornaStima(idCorsa);
        if (result != null) {
            LOG.log(Level.INFO, "Costo stimato: {0} (tariffa: {1} €/h)", new Object[]{result.getCosto(), result.getTariffa()});
        }
    }

    public void mostraCorsaAttiva() {
        try {
            CorsaResponse corsa = gestioneCorsaService.getCorsaAttiva(this.idUtente);
            if (corsa != null) {
                LOG.info(">>> SEZIONE CORSA <<<");
                LOG.log(Level.INFO, "ID Corsa: {0}", corsa.getId());
                LOG.log(Level.INFO, "Mezzo: {0}", corsa.getIdMezzo());
                LOG.log(Level.INFO, "Inizio: {0}", corsa.getDataInizio());
                LOG.log(Level.INFO, "Costo: {0}", corsa.getCosto());
                LOG.log(Level.INFO, "Stato: {0}", corsa.getStato());
            } else {
                LOG.info(">>> NESSUNA CORSA ATTIVA <<<");
            }
        } catch (ResponseStatusException e) {
            mostraErrore(e.getReason() != null ? e.getReason() : "Errore nel recupero corsa attiva");
        }
    }

    public void mostraSuccesso() {
        LOG.info("Operazione completata con successo");
    }

    public void mostraFineCorsa() {
        LOG.info("Corsa terminata");
    }

    public void mostraQRCode() {
        LOG.info("QR Code generato");
    }

    public void mostraRipresaCorsa() {
        LOG.info("Corsa ripresa");
    }

    public void mostraMezzi(List<MezzoResponse> mezzi) {
        LOG.log(Level.INFO, "Mezzi trovati: {0}", mezzi.size());
    }

    public void renderizzaDettagliVeicolo(MezzoResponse mezzo) {
        LOG.log(Level.INFO, "Dettagli: {0}", mezzo.getTipo());
    }

    public boolean scansionaQRCode(String qrCode) {
        try {
            boolean sbloccato = gestioneCorsaService.richiediSblocco(qrCode);
            if (sbloccato) {
                LOG.log(Level.INFO, "QR Code validato: {0}", qrCode);
                return true;
            } else {
                mostraErrore("Impossibile sbloccare il veicolo con il QR Code fornito");
                return false;
            }
        } catch (ResponseStatusException e) {
            mostraErrore(e.getReason() != null ? e.getReason() : "QR Code non valido");
            return false;
        }
    }

    public void selezionaMetodo(Long idMetodoPagamento) {
        try {
            gestioneCorsaService.acquisisciSceltaMetodo(idMetodoPagamento, this.idUtente);
            LOG.log(Level.INFO, "Metodo di pagamento selezionato e convalidato: {0}", idMetodoPagamento);
        } catch (ResponseStatusException e) {
            mostraErrore(e.getReason() != null ? e.getReason() : "Errore nella selezione del metodo");
        }
    }

    public void avviaCorsaConQR(Long idMezzo, String qrCode, Long idMetodoPagamento) {
        if (qrCode == null || qrCode.isBlank()) {
            mostraErrore("QR Code non valido o non scansionato. Scansiona il QR Code del veicolo prima di avviare la corsa.");
            return;
        }
        if (idMetodoPagamento == null) {
            mostraErrore("Nessun metodo di pagamento selezionato. Seleziona un metodo di pagamento prima di avviare la corsa.");
            return;
        }
        try {
            gestioneCorsaService.acquisisciSceltaMetodo(idMetodoPagamento, this.idUtente);
            Long idCorsa = gestioneCorsaService.avviaCorsa(idMezzo, this.idUtente, qrCode);
            LOG.log(Level.INFO, "CORSA AVVIATA con ID: {0}", idCorsa);
            mostraCorsaAttiva();
        } catch (ResponseStatusException e) {
            mostraErrore(e.getReason() != null ? e.getReason() : "Errore durante l'avvio della corsa");
        }
    }

    public void terminazioneCorsa(Long idCorsa) {
        try {
            CorsaResponse response = gestioneCorsaService.terminaCorsa(idCorsa);
            LOG.log(Level.INFO, "Corsa terminata. Costo finale: {0}", response.getCosto());
        } catch (ResponseStatusException e) {
            mostraErrore(e.getReason() != null ? e.getReason() : "Errore durante la terminazione della corsa");
        }
    }

    public void sospendiCorsa(Long idCorsa) {
        try {
            boolean sospesa = gestioneCorsaService.sospensioneCorsa(idCorsa);
            LOG.info(sospesa ? "Corsa sospesa/ripresa" : "Impossibile sospendere la corsa");
        } catch (ResponseStatusException e) {
            mostraErrore(e.getReason() != null ? e.getReason() : "Errore durante la sospensione della corsa");
        }
    }

    public void selezionaMezzo(Long idMezzo) {
        LOG.log(Level.INFO, "Mezzo selezionato: {0}", idMezzo);
    }

    public void inserisciDestinazione(String indirizzoArrivo) {
        LOG.log(Level.INFO, "Destinazione: {0}", indirizzoArrivo);
    }

    public void avviaRicercaMezzi(String coordinateUtente, float raggio) {
        List<MezzoResponse> mezzi = ricercaMezziService.visualizzaMezziVicini(coordinateUtente, raggio);
        mostraMezzi(mezzi);
    }

    public void confermaEspansione() {
        LOG.info("Espansione confermata");
    }

    public void notificaAzione(Long idUtente, String azione) {
        LOG.log(Level.INFO, "Notifica a {0}: {1}", new Object[]{idUtente, azione});
    }

    public void ottieniMetodiSalvati() {
        try {
            List<MetodoPagamentoResponse> metodi = gestorePagamentoService.recuperaMetodiSalvati(this.idUtente);
            LOG.log(Level.INFO, "METODI DI PAGAMENTO SALVATI ({0}):", metodi.size());
            for (MetodoPagamentoResponse m : metodi) {
                LOG.log(Level.INFO, "  ID={0} | carta={1} | intestatario={2}", new Object[]{m.getId(), m.getNumCarta(), m.getIntestatarioCarta()});
            }
        } catch (ResponseStatusException e) {
            mostraErrore(e.getReason() != null ? e.getReason() : "Errore nel recupero metodi");
        }
    }

    public void richiestaLogout(String email) {
        gestioneAutenticazioneService.inviaRichiestaLogout(email);
    }

    public Long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
    }

    public Long getIdSessioneUtente() {
        return idSessioneUtente;
    }

    public void setIdSessioneUtente(Long idSessioneUtente) {
        this.idSessioneUtente = idSessioneUtente;
    }
}

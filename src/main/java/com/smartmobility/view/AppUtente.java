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

@Component
@SuppressWarnings("unused")
public class AppUtente {

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
        System.err.println("ERRORE: " + msg);
    }

    public void mostraStima(Long idCorsa) {
        StimaCorsaResponse result = gestioneCorsaService.aggiornaStima(idCorsa);
        if (result != null) {
            System.out.println("Costo stimato: " + result.getCosto() + " (tariffa: " + result.getTariffa() + " €/h)");
        }
    }

    public void mostraCorsaAttiva() {
        try {
            CorsaResponse corsa = gestioneCorsaService.getCorsaAttiva(this.idUtente);
            if (corsa != null) {
                System.out.println(">>> SEZIONE CORSA <<<");
                System.out.println("ID Corsa: " + corsa.getId());
                System.out.println("Mezzo: " + corsa.getIdMezzo());
                System.out.println("Inizio: " + corsa.getDataInizio());
                System.out.println("Costo: " + corsa.getCosto());
                System.out.println("Stato: " + corsa.getStato());
            } else {
                System.out.println(">>> NESSUNA CORSA ATTIVA <<<");
            }
        } catch (ResponseStatusException e) {
            mostraErrore(e.getReason() != null ? e.getReason() : "Errore nel recupero corsa attiva");
        }
    }

    public void mostraSuccesso() {
        System.out.println("Operazione completata con successo");
    }

    public void mostraFineCorsa() {
        System.out.println("Corsa terminata");
    }

    public void mostraQRCode() {
        System.out.println("QR Code generato");
    }

    public void mostraRipresaCorsa() {
        System.out.println("Corsa ripresa");
    }

    public void mostraMezzi(List<MezzoResponse> mezzi) {
        System.out.println("Mezzi trovati: " + mezzi.size());
    }

    public void renderizzaDettagliVeicolo(MezzoResponse mezzo) {
        System.out.println("Dettagli: " + mezzo.getTipo());
    }

    public boolean scansionaQRCode(String qrCode) {
        try {
            boolean sbloccato = gestioneCorsaService.richiediSblocco(qrCode);
            if (sbloccato) {
                System.out.println("QR Code validato: " + qrCode);
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
            System.out.println("Metodo di pagamento selezionato e convalidato: " + idMetodoPagamento);
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
            System.out.println("CORSA AVVIATA con ID: " + idCorsa);
            mostraCorsaAttiva();
        } catch (ResponseStatusException e) {
            mostraErrore(e.getReason() != null ? e.getReason() : "Errore durante l'avvio della corsa");
        }
    }

    public void terminazioneCorsa(Long idCorsa) {
        try {
            CorsaResponse response = gestioneCorsaService.terminaCorsa(idCorsa);
            System.out.println("Corsa terminata. Costo finale: " + response.getCosto());
        } catch (ResponseStatusException e) {
            mostraErrore(e.getReason() != null ? e.getReason() : "Errore durante la terminazione della corsa");
        }
    }

    public void sospendiCorsa(Long idCorsa) {
        try {
            boolean sospesa = gestioneCorsaService.sospensioneCorsa(idCorsa);
            System.out.println(sospesa ? "Corsa sospesa/ripresa" : "Impossibile sospendere la corsa");
        } catch (ResponseStatusException e) {
            mostraErrore(e.getReason() != null ? e.getReason() : "Errore durante la sospensione della corsa");
        }
    }

    public void selezionaMezzo(Long idMezzo) {
        System.out.println("Mezzo selezionato: " + idMezzo);
    }

    public void inserisciDestinazione(String indirizzoArrivo) {
        System.out.println("Destinazione: " + indirizzoArrivo);
    }

    public void avviaRicercaMezzi(String coordinateUtente, float raggio) {
        List<MezzoResponse> mezzi = ricercaMezziService.visualizzaMezziVicini(coordinateUtente, raggio);
        mostraMezzi(mezzi);
    }

    public void confermaEspansione() {
        System.out.println("Espansione confermata");
    }

    public void notificaAzione(Long idUtente, String azione) {
        System.out.println("Notifica a " + idUtente + ": " + azione);
    }

    public void ottieniMetodiSalvati() {
        try {
            List<MetodoPagamentoResponse> metodi = gestorePagamentoService.recuperaMetodiSalvati(this.idUtente);
            System.out.println("METODI DI PAGAMENTO SALVATI (" + metodi.size() + "):");
            for (MetodoPagamentoResponse m : metodi) {
                System.out.println("  ID=" + m.getId() + " | carta=" + m.getNumCarta() + " | intestatario=" + m.getIntestatarioCarta());
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

package com.smartmobility.view;

import com.smartmobility.dto.response.MetodoPagamentoResponse;
import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.service.GestioneAutenticazioneService;
import com.smartmobility.service.GestioneCorsaService;
import com.smartmobility.service.GestionePrenotazioneService;
import com.smartmobility.service.GestorePagamentoService;
import com.smartmobility.service.RicercaMezziService;
import org.springframework.stereotype.Component;

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
        Float result = gestioneCorsaService.aggiornaStima(idCorsa);
        if (result != null) {
            System.out.println("Costo stimato: " + result);
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

    public void mostraMetodi(List<MetodoPagamentoResponse> metodi) {
        System.out.println("Metodi: " + metodi.size());
    }

    public void mostraSceltaMetodi() {
        System.out.println("Mostra scelta metodi");
    }

    public void mostraMezzi(List<MezzoResponse> mezzi) {
        System.out.println("Mezzi trovati: " + mezzi.size());
    }

    public void mostraMetodoConvalidato() {
        System.out.println("Metodo convalidato");
    }

    public void renderizzaDettagliVeicolo(MezzoResponse mezzo) {
        System.out.println("Dettagli: " + mezzo.getTipo());
    }

    public void scansionaQRCode(String qrCode) {
        gestioneCorsaService.richiediSblocco(qrCode);
    }

    public void inserisciDatiCarta(Long idUtente, String numCarta, String dsCarta, String cvv, String intestatarioCarta) {
        gestorePagamentoService.elaboraDatiCarta(idUtente, numCarta, dsCarta, cvv, intestatarioCarta);
    }

    public void apriAvvioCorsa() {
        System.out.println("Avvio corsa");
    }

    public void terminazioneCorsa(Long idCorsa) {
        gestioneCorsaService.terminaCorsa(idCorsa);
    }

    public void sospendiCorsa(Long idCorsa) {
        gestioneCorsaService.sospensioneCorsa(idCorsa);
    }

    public void apriSezioneProfilo(Long idUtente) {
        System.out.println("Profilo utente: " + idUtente);
    }

    public void apriInserimentoMetodoPagamento(Long idUtente) {
        System.out.println("Inserimento metodo per utente: " + idUtente);
    }

    public void selezionaMezzo(Long idMezzo) {
        System.out.println("Mezzo selezionato: " + idMezzo);
    }

    public void inserisciDestinazione(String indirizzoArrivo) {
        System.out.println("Destinazione: " + indirizzoArrivo);
    }

    public void avviaRicercaMezzi(String coordinateUtente, float raggio) {
        ricercaMezziService.visualizzaMezziVicini(coordinateUtente, raggio);
    }

    public void confermaEspansione() {
        System.out.println("Espansione confermata");
    }

    public void notificaAzione(Long idUtente, String azione) {
        System.out.println("Notifica a " + idUtente + ": " + azione);
    }

    public void ottieniMetodiSalvati() {
        gestorePagamentoService.recuperaMetodiSalvati(this.idUtente);
    }

    public void selezionaMetodo(String numCarta) {
        System.out.println("Metodo selezionato: " + numCarta);
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

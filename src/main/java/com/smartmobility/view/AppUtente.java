package com.smartmobility.view;

import com.smartmobility.dto.response.MetodoPagamentoResponse;
import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.service.GestioneAutenticazioneService;
import com.smartmobility.service.GestioneCorsaService;
import com.smartmobility.service.GestionePrenotazioneService;
import com.smartmobility.service.GestorePagamentoService;
import com.smartmobility.service.RicercaMezziService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class AppUtente {

    private final GestioneCorsaService gestioneCorsaService;
    private final GestorePagamentoService gestorePagamentoService;
    private final RicercaMezziService ricercaMezziService;
    private final GestionePrenotazioneService gestionePrenotazioneService;
    private final GestioneAutenticazioneService gestioneAutenticazioneService;

    public AppUtente(GestioneCorsaService gestioneCorsaService, GestorePagamentoService gestorePagamentoService, RicercaMezziService ricercaMezziService, GestionePrenotazioneService gestionePrenotazioneService, GestioneAutenticazioneService gestioneAutenticazioneService) {
        this.gestioneCorsaService = gestioneCorsaService;
        this.gestorePagamentoService = gestorePagamentoService;
        this.ricercaMezziService = ricercaMezziService;
        this.gestionePrenotazioneService = gestionePrenotazioneService;
        this.gestioneAutenticazioneService = gestioneAutenticazioneService;
    }

    public void mostraErrore(String msg) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void mostraStima(Long idCorsa) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void mostraSuccesso() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void mostraFineCorsa() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void mostraQRCode() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void mostraRipresaCorsa() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void mostraMetodi(List<MetodoPagamentoResponse> metodi) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void mostraSceltaMetodi() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void mostraMezzi(List<MezzoResponse> mezzi) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void mostraMetodoConvalidato() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void renderizzaDettagliVeicolo(MezzoResponse mezzo) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void scansionaQRCode(String qrCode) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void inserisciDatiCarta(Long idUtente, String numCarta, String dsCarta, String cvv, String intestatarioCarta) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void apriAvvioCorsa() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void terminazioneCorsa(Long idCorsa) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void sospendiCorsa(Long idCorsa) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void apriSezioneProfilo(Long idUtente) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void apriInserimentoMetodoPagamento(Long idUtente) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void selezionaMezzo(Long idMezzo) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void inserisciDestinazione(String indirizzoArrivo) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void avviaRicercaMezzi(String coordinateUtente, float raggio) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void confermaEspansione() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void notificaAzione(Long idUtente, String azione) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void ottieniMetodiSalvati() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void selezionaMetodo(String numCarta) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void richiestaLogout(String email) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

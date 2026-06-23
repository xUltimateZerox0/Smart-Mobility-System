package com.smartmobility.controller;

import com.smartmobility.dto.request.PrenotazioneRequest;
import com.smartmobility.dto.response.PrenotazioneResponse;
import com.smartmobility.service.GestionePrenotazioneService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class GestionePrenotazioneController {

    private final GestionePrenotazioneService gestionePrenotazioneService;

    public GestionePrenotazioneController(GestionePrenotazioneService gestionePrenotazioneService) {
        this.gestionePrenotazioneService = gestionePrenotazioneService;
    }

    @PostMapping
    public ResponseEntity<Void> createBooking(@Valid @RequestBody PrenotazioneRequest request) {
        gestionePrenotazioneService.inviaRichiestaPrenotazione(request.idMezzo(), request.idUtente());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PrenotazioneResponse>> getBookings() {
        List<PrenotazioneResponse> bookings = gestionePrenotazioneService.richiediLista();
        return ResponseEntity.ok(bookings);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> cancelBooking(@PathVariable Long id) {
        boolean result = gestionePrenotazioneService.annullaPrenotazione(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/timeout")
    public ResponseEntity<Void> handleTimeout() {
        gestionePrenotazioneService.gestisciTimeout();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/notify-expiry")
    public ResponseEntity<Void> notifyExpiry(@PathVariable Long id) {
        gestionePrenotazioneService.notificaScadenzaTempo(id);
        return ResponseEntity.ok().build();
    }
}

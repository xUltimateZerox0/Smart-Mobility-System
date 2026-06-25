package com.smartmobility.controller;

import com.smartmobility.dto.request.PrenotazioneRequest;
import com.smartmobility.dto.response.PrenotazioneResponse;
import com.smartmobility.security.SecurityHelper;
import com.smartmobility.service.GestionePrenotazioneService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class GestionePrenotazioneController {

    private final GestionePrenotazioneService gestionePrenotazioneService;
    private final SecurityHelper securityHelper;

    public GestionePrenotazioneController(GestionePrenotazioneService gestionePrenotazioneService, SecurityHelper securityHelper) {
        this.gestionePrenotazioneService = gestionePrenotazioneService;
        this.securityHelper = securityHelper;
    }

    @PostMapping
    public ResponseEntity<PrenotazioneResponse> createBooking(@Valid @RequestBody PrenotazioneRequest request,
                                                               @RequestHeader(value = "Authorization", required = false) String authHeader) {
        securityHelper.requireUserIdMatch(authHeader, request.idUtente());
        PrenotazioneResponse result = gestionePrenotazioneService.inviaRichiestaPrenotazione(request.idMezzo(), request.idUtente(), request.orarioInizio());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/qrcode")
    public ResponseEntity<String> getQRCode(@PathVariable Long id) {
        String qrCode = gestionePrenotazioneService.getQRCode(id);
        return ResponseEntity.ok(qrCode);
    }

    @GetMapping
    public ResponseEntity<List<PrenotazioneResponse>> getBookings() {
        List<PrenotazioneResponse> bookings = gestionePrenotazioneService.richiediLista();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/user/me")
    public ResponseEntity<List<PrenotazioneResponse>> getMyBookings(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        Long idUtente = securityHelper.getCurrentUserId(authHeader);
        if (idUtente == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        List<PrenotazioneResponse> bookings = gestionePrenotazioneService.richiediListaPerUtente(idUtente);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/user/{idUtente}")
    public ResponseEntity<List<PrenotazioneResponse>> getUserBookings(@PathVariable Long idUtente,
                                                                        @RequestHeader(value = "Authorization", required = false) String authHeader) {
        securityHelper.requireUserIdMatch(authHeader, idUtente);
        List<PrenotazioneResponse> bookings = gestionePrenotazioneService.richiediListaPerUtente(idUtente);
        return ResponseEntity.ok(bookings);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> cancelBooking(@PathVariable Long id,
                                                  @RequestHeader(value = "Authorization", required = false) String authHeader) {
        securityHelper.requireAuth(authHeader);
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

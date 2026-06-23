package com.smartmobility.controller;

import com.smartmobility.dto.response.PrenotazioneResponse;
import com.smartmobility.service.GestionePrenotazioneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class GestionePrenotazioneController {

    private final GestionePrenotazioneService gestionePrenotazioneService;

    public GestionePrenotazioneController(GestionePrenotazioneService gestionePrenotazioneService) {
        this.gestionePrenotazioneService = gestionePrenotazioneService;
    }

    @PostMapping
    public ResponseEntity<Void> createBooking() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @GetMapping
    public ResponseEntity<List<PrenotazioneResponse>> getBookings() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> cancelBooking(@PathVariable Long id) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/timeout")
    public ResponseEntity<Void> handleTimeout() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/{id}/notify-expiry")
    public ResponseEntity<Void> notifyExpiry(@PathVariable Long id) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

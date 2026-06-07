package com.smartmobility.controller;

import com.smartmobility.dto.PrenotazioneDTO;
import com.smartmobility.service.PrenotazioneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/prenotazioni")
public class GestionePrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    public GestionePrenotazioneController(PrenotazioneService prenotazioneService) {
        this.prenotazioneService = prenotazioneService;
    }

    @PostMapping
    public ResponseEntity<Boolean> inviaRichiestaPrenotazione() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @GetMapping
    public ResponseEntity<List<PrenotazioneDTO>> richiediLista() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @DeleteMapping("/{idPrenotazione}")
    public ResponseEntity<Boolean> annullaPrenotazione(@PathVariable Long idPrenotazione) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/timeout")
    public ResponseEntity<Void> gestisciTimeout() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/{idPrenotazione}/notifica-scadenza")
    public ResponseEntity<Void> notificaScadenzaTempo(@PathVariable Long idPrenotazione) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

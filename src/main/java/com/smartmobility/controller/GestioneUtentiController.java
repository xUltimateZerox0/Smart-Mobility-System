package com.smartmobility.controller;

import com.smartmobility.service.UtenteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/utenti")
public class GestioneUtentiController {

    private final UtenteService utenteService;

    public GestioneUtentiController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @PutMapping("/{idUtente}/gestione")
    public ResponseEntity<Boolean> gestioneUtente(@PathVariable Long idUtente) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @GetMapping("/{idUtente}/report")
    public ResponseEntity<String> cercaReport(@PathVariable Long idUtente) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

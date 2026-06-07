package com.smartmobility.controller;

import com.smartmobility.dto.CalcoloPercorsoRequest;
import com.smartmobility.dto.CorsaDTO;
import com.smartmobility.dto.RichiediSbloccoRequest;
import com.smartmobility.service.CorsaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/corse")
public class GestioneCorsaController {

    private final CorsaService corsaService;

    public GestioneCorsaController(CorsaService corsaService) {
        this.corsaService = corsaService;
    }

    @PostMapping("/avvia")
    public ResponseEntity<CorsaDTO> avviaCorsa() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/{idCorsa}/termina")
    public ResponseEntity<CorsaDTO> terminaCorsa(@PathVariable Long idCorsa) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @GetMapping("/disponibilita")
    public ResponseEntity<Boolean> controllaDisponibilita(@RequestParam String parametro) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @GetMapping("/{idCorsa}/stima")
    public ResponseEntity<Float> aggiornaStima(@PathVariable Long idCorsa) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/sospendi")
    public ResponseEntity<Boolean> sospensioneCorsa() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/{idCorsa}/fine")
    public ResponseEntity<Boolean> fineCorsa(@PathVariable Long idCorsa) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/calcolo-percorso")
    public ResponseEntity<String> richiediCalcoloPercorso(@RequestBody CalcoloPercorsoRequest request) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/sblocco")
    public ResponseEntity<Boolean> richiediSblocco(@RequestBody RichiediSbloccoRequest request) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

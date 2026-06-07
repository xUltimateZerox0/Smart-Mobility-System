package com.smartmobility.controller;

import com.smartmobility.dto.RestrizioneRequest;
import com.smartmobility.dto.ZonaGeograficaDTO;
import com.smartmobility.service.ZonaGeograficaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/aree")
public class GestioneAreeController {

    private final ZonaGeograficaService zonaGeograficaService;

    public GestioneAreeController(ZonaGeograficaService zonaGeograficaService) {
        this.zonaGeograficaService = zonaGeograficaService;
    }

    @PutMapping("/restrizioni")
    public ResponseEntity<Void> aggiornaRestrizioni(@RequestBody RestrizioneRequest request) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/conflitti")
    public ResponseEntity<Boolean> analisiConflitti(@RequestBody ZonaGeograficaDTO zona) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @GetMapping
    public ResponseEntity<List<ZonaGeograficaDTO>> getZoneGeografiche() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

package com.smartmobility.controller;

import com.smartmobility.dto.request.ConflictCheckRequest;
import com.smartmobility.dto.request.UpdateZoneRequest;
import com.smartmobility.dto.response.ZonaGeograficaResponse;
import com.smartmobility.service.GestioneAreeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/zones")
public class GestioneAreeController {

    private final GestioneAreeService gestioneAreeService;

    public GestioneAreeController(GestioneAreeService gestioneAreeService) {
        this.gestioneAreeService = gestioneAreeService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateZone(@PathVariable Long id, @Valid @RequestBody UpdateZoneRequest request) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/conflict-check")
    public ResponseEntity<Boolean> checkConflict(@Valid @RequestBody ConflictCheckRequest request) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @GetMapping
    public ResponseEntity<List<ZonaGeograficaResponse>> getZones() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

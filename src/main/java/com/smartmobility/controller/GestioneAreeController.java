package com.smartmobility.controller;

import com.smartmobility.dto.request.ConflictCheckRequest;
import com.smartmobility.dto.request.UpdateZoneRequest;
import com.smartmobility.dto.response.ZonaGeograficaResponse;
import com.smartmobility.service.GestioneAreeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        gestioneAreeService.aggiornaRestrizione(
                id, request.getTipoRestrizione(), request.getNoteRestrizione(), request.getZona());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/conflict-check")
    public ResponseEntity<Boolean> checkConflict(@Valid @RequestBody ConflictCheckRequest request) {
        boolean result = gestioneAreeService.analisiConflitti(request.getZona());
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<ZonaGeograficaResponse>> getZones() {
        List<ZonaGeograficaResponse> zones = gestioneAreeService.getZoneGeografiche();
        return ResponseEntity.ok(zones);
    }
}

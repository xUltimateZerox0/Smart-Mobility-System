package com.smartmobility.controller;

import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.service.GestioneFlottaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/fleet")
public class GestioneFlottaController {

    private final GestioneFlottaService gestioneFlottaService;

    public GestioneFlottaController(GestioneFlottaService gestioneFlottaService) {
        this.gestioneFlottaService = gestioneFlottaService;
    }

    @PostMapping("/{flottaId}/analyze")
    public ResponseEntity<Boolean> analyzeFleet(@PathVariable Long flottaId) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/vehicles/{id}/lock")
    public ResponseEntity<Boolean> lockVehicle(@PathVariable Long id) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/{flottaId}/maintenance")
    public ResponseEntity<Boolean> startMaintenance(@PathVariable Long flottaId) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @GetMapping("/{flottaId}/conditions")
    public ResponseEntity<List<MezzoResponse>> getVehicleConditions(@PathVariable Long flottaId) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

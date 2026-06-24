package com.smartmobility.controller;

import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.service.GestioneFlottaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        boolean result = gestioneFlottaService.analisiStatoFlotta(flottaId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/vehicles/{id}/lock")
    public ResponseEntity<Boolean> lockVehicle(@PathVariable Long id) {
        boolean result = gestioneFlottaService.bloccaMezzo(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/vehicles/{id}/unlock")
    public ResponseEntity<Boolean> unlockVehicle(@PathVariable Long id) {
        boolean result = gestioneFlottaService.sbloccaMezzo(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/vehicles/{id}/maintenance")
    public ResponseEntity<Boolean> startVehicleMaintenance(@PathVariable Long id) {
        boolean result = gestioneFlottaService.avviaManutenzioneVeicolo(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{flottaId}/maintenance")
    public ResponseEntity<Boolean> startMaintenance(@PathVariable Long flottaId) {
        boolean result = gestioneFlottaService.avviaManutenzione(flottaId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{flottaId}/conditions")
    public ResponseEntity<List<MezzoResponse>> getVehicleConditions(@PathVariable Long flottaId) {
        List<MezzoResponse> conditions = gestioneFlottaService.getCondizioniMezzi(flottaId);
        return ResponseEntity.ok(conditions);
    }
}

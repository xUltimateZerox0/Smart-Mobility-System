package com.smartmobility.controller;

import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.dto.response.SegnalazioneResponse;
import com.smartmobility.model.enums.RuoloAttore;
import com.smartmobility.security.SecurityHelper;
import com.smartmobility.service.GestioneFlottaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fleet")
public class GestioneFlottaController {

    private final GestioneFlottaService gestioneFlottaService;
    private final SecurityHelper securityHelper;

    public GestioneFlottaController(GestioneFlottaService gestioneFlottaService,
                                     SecurityHelper securityHelper) {
        this.gestioneFlottaService = gestioneFlottaService;
        this.securityHelper = securityHelper;
    }

    @PostMapping("/vehicles/{id}/lock")
    public ResponseEntity<Boolean> lockVehicle(@PathVariable Long id,
                                                @RequestHeader("Authorization") String authHeader) {
        securityHelper.requireRole(authHeader, RuoloAttore.Operatore);
        boolean result = gestioneFlottaService.bloccaMezzo(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/vehicles/{id}/unlock")
    public ResponseEntity<Boolean> unlockVehicle(@PathVariable Long id,
                                                  @RequestHeader("Authorization") String authHeader) {
        securityHelper.requireRole(authHeader, RuoloAttore.Operatore);
        boolean result = gestioneFlottaService.sbloccaMezzo(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/vehicles/{id}/maintenance")
    public ResponseEntity<Boolean> startVehicleMaintenance(@PathVariable Long id,
                                                             @RequestHeader(value = "Authorization", required = false) String authHeader) {
        securityHelper.requireRole(authHeader, RuoloAttore.PA);
        boolean result = gestioneFlottaService.avviaManutenzioneVeicolo(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{flottaId}/maintenance")
    public ResponseEntity<Boolean> startMaintenance(@PathVariable Long flottaId,
                                                      @RequestHeader(value = "Authorization", required = false) String authHeader) {
        securityHelper.requireRole(authHeader, RuoloAttore.PA);
        boolean result = gestioneFlottaService.avviaManutenzione(flottaId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{flottaId}/analyze")
    public ResponseEntity<Boolean> analyzeFleet(@PathVariable Long flottaId,
                                                  @RequestHeader("Authorization") String authHeader) {
        securityHelper.requireRole(authHeader, RuoloAttore.PA);
        boolean result = gestioneFlottaService.analisiStatoFlotta(flottaId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/segnalazioni")
    public ResponseEntity<List<SegnalazioneResponse>> getSegnalazioni(@RequestHeader("Authorization") String authHeader) {
        securityHelper.requireRole(authHeader, RuoloAttore.PA);
        List<SegnalazioneResponse> segnalazioni = gestioneFlottaService.getSegnalazioni();
        return ResponseEntity.ok(segnalazioni);
    }

    @GetMapping("/segnalazioni/{stato}")
    public ResponseEntity<List<SegnalazioneResponse>> getSegnalazioniByStato(@PathVariable String stato,
                                                                               @RequestHeader("Authorization") String authHeader) {
        securityHelper.requireRole(authHeader, RuoloAttore.PA);
        List<SegnalazioneResponse> segnalazioni = gestioneFlottaService.getSegnalazioniByStato(stato);
        return ResponseEntity.ok(segnalazioni);
    }

    @GetMapping("/{flottaId}/conditions")
    public ResponseEntity<List<MezzoResponse>> getVehicleConditions(@PathVariable Long flottaId,
                                                                     @RequestHeader("Authorization") String authHeader) {
        securityHelper.requireAuth(authHeader);
        List<MezzoResponse> conditions = gestioneFlottaService.getCondizioniMezzi(flottaId);
        return ResponseEntity.ok(conditions);
    }
}

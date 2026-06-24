package com.smartmobility.controller;

import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.model.Attore;
import com.smartmobility.model.Operatore;
import com.smartmobility.model.enums.RuoloAttore;
import com.smartmobility.model.enums.TipoOperatore;
import com.smartmobility.service.GestioneFlottaService;
import com.smartmobility.service.SessionRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/fleet")
public class GestioneFlottaController {

    private final GestioneFlottaService gestioneFlottaService;
    private final SessionRegistry sessionRegistry;

    public GestioneFlottaController(GestioneFlottaService gestioneFlottaService,
                                     SessionRegistry sessionRegistry) {
        this.gestioneFlottaService = gestioneFlottaService;
        this.sessionRegistry = sessionRegistry;
    }

    private Attore getCurrentUser(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Utente non autenticato");
        }
        String token = authHeader.substring(7);
        Attore attore = sessionRegistry.getAttore(token);
        if (attore == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sessione non valida");
        }
        return attore;
    }

    private void requirePaRole(String authHeader) {
        Attore attore = getCurrentUser(authHeader);
        if (attore.getRuolo() != RuoloAttore.PA) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Solo la PA può avviare la manutenzione");
        }
    }

    private void requireOperatoreRole(String authHeader) {
        Attore attore = getCurrentUser(authHeader);
        if (attore.getRuolo() != RuoloAttore.Operatore) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Solo gli operatori possono eseguire questa operazione");
        }
    }

    private void requireNotTecnico(String authHeader) {
        Attore attore = getCurrentUser(authHeader);
        if (attore instanceof Operatore o && o.getTipo() == TipoOperatore.OperatoreTecnico) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "L'operatore tecnico non può avviare la manutenzione");
        }
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
    public ResponseEntity<Boolean> startVehicleMaintenance(@PathVariable Long id,
                                                             @RequestHeader(value = "Authorization", required = false) String authHeader) {
        requirePaRole(authHeader);
        requireNotTecnico(authHeader);
        boolean result = gestioneFlottaService.avviaManutenzioneVeicolo(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{flottaId}/maintenance")
    public ResponseEntity<Boolean> startMaintenance(@PathVariable Long flottaId,
                                                      @RequestHeader(value = "Authorization", required = false) String authHeader) {
        requirePaRole(authHeader);
        requireNotTecnico(authHeader);
        boolean result = gestioneFlottaService.avviaManutenzione(flottaId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{flottaId}/conditions")
    public ResponseEntity<List<MezzoResponse>> getVehicleConditions(@PathVariable Long flottaId) {
        List<MezzoResponse> conditions = gestioneFlottaService.getCondizioniMezzi(flottaId);
        return ResponseEntity.ok(conditions);
    }
}

package com.smartmobility.controller;

import com.smartmobility.dto.request.PaymentMethodSelectionRequest;
import com.smartmobility.dto.request.RouteRequest;
import com.smartmobility.dto.request.StartRideRequest;
import com.smartmobility.dto.request.UnlockRequest;
import com.smartmobility.dto.response.CorsaResponse;
import com.smartmobility.dto.response.PercorsoResponse;
import com.smartmobility.dto.response.StimaCorsaResponse;
import com.smartmobility.model.enums.RuoloAttore;
import com.smartmobility.security.SecurityHelper;
import com.smartmobility.service.GestioneCorsaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rides")
public class GestioneCorsaController {

    private final GestioneCorsaService gestioneCorsaService;
    private final SecurityHelper securityHelper;

    public GestioneCorsaController(GestioneCorsaService gestioneCorsaService, SecurityHelper securityHelper) {
        this.gestioneCorsaService = gestioneCorsaService;
        this.securityHelper = securityHelper;
    }

    @GetMapping("/active")
    public ResponseEntity<CorsaResponse> getActiveRide(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        Long userId = securityHelper.getCurrentUserId(authHeader);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CorsaResponse corsa = gestioneCorsaService.getCorsaAttiva(userId);
        if (corsa == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(corsa);
    }

    @PostMapping("/start")
    public ResponseEntity<Long> startRide(@Valid @RequestBody StartRideRequest request,
                                           @RequestHeader(value = "Authorization", required = false) String authHeader) {
        securityHelper.requireUserIdMatch(authHeader, request.getIdUtente());
        Long corsaId = gestioneCorsaService.avviaCorsa(request.getIdMezzo(), request.getIdUtente(), request.getQrCode());
        return ResponseEntity.ok(corsaId);
    }

    @PostMapping("/{id}/end")
    public ResponseEntity<CorsaResponse> endRide(@PathVariable Long id,
                                                  @RequestHeader(value = "Authorization", required = false) String authHeader) {
        securityHelper.requireAuth(authHeader);
        CorsaResponse response = gestioneCorsaService.terminaCorsa(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/estimate")
    public ResponseEntity<StimaCorsaResponse> getEstimate(@PathVariable Long id,
                                                           @RequestHeader(value = "Authorization", required = false) String authHeader) {
        securityHelper.requireAuth(authHeader);
        StimaCorsaResponse stima = gestioneCorsaService.aggiornaStima(id);
        return ResponseEntity.ok(stima);
    }

    @PostMapping("/{id}/pause")
    public ResponseEntity<Boolean> pauseRide(@PathVariable Long id,
                                              @RequestHeader(value = "Authorization", required = false) String authHeader) {
        securityHelper.requireAuth(authHeader);
        boolean result = gestioneCorsaService.sospensioneCorsa(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/unlock")
    public ResponseEntity<Boolean> unlock(@Valid @RequestBody UnlockRequest request) {
        boolean result = gestioneCorsaService.richiediSblocco(request.getQrCode());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/route")
    public ResponseEntity<PercorsoResponse> calculateRoute(@Valid @RequestBody RouteRequest request) {
        PercorsoResponse percorso = gestioneCorsaService.richiediCalcoloPercorso(
                request.getCoordinateUtente(), request.getDestinazione());
        return ResponseEntity.ok(percorso);
    }

    @PostMapping("/payment-method")
    public ResponseEntity<Void> selectPaymentMethod(@Valid @RequestBody PaymentMethodSelectionRequest request,
                                                     @RequestHeader(value = "Authorization", required = false) String authHeader) {
        Long userId = securityHelper.getCurrentUserId(authHeader);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        gestioneCorsaService.acquisisciSceltaMetodo(request.getIdMetodoPagamento(), userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/force-terminate")
    public ResponseEntity<CorsaResponse> forceTerminateRide(@PathVariable Long id,
                                                             @RequestHeader(value = "Authorization", required = false) String authHeader) {
        securityHelper.requireRole(authHeader, RuoloAttore.Operatore);
        CorsaResponse response = gestioneCorsaService.forzaTerminaCorsa(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<Boolean> checkAvailability(@PathVariable Long id) {
        boolean result = gestioneCorsaService.controllaDisponibilita(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/availability/{info}")
    public ResponseEntity<Boolean> checkAvailability(@PathVariable Long id, @PathVariable String info) {
        boolean result = gestioneCorsaService.controllaDisponibilita(id, info);
        return ResponseEntity.ok(result);
    }
}

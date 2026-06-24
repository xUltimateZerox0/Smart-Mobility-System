package com.smartmobility.controller;

import com.smartmobility.dto.request.PaymentMethodSelectionRequest;
import com.smartmobility.dto.request.RouteRequest;
import com.smartmobility.dto.request.StartRideRequest;
import com.smartmobility.dto.request.UnlockRequest;
import com.smartmobility.dto.response.PercorsoResponse;
import com.smartmobility.dto.response.StimaCorsaResponse;
import com.smartmobility.service.GestioneCorsaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rides")
public class GestioneCorsaController {

    private final GestioneCorsaService gestioneCorsaService;

    public GestioneCorsaController(GestioneCorsaService gestioneCorsaService) {
        this.gestioneCorsaService = gestioneCorsaService;
    }

    @PostMapping("/start")
    public ResponseEntity<Long> startRide(@Valid @RequestBody StartRideRequest request) {
        Long corsaId = gestioneCorsaService.avviaCorsa(request.getIdMezzo(), request.getIdUtente());
        return ResponseEntity.ok(corsaId);
    }

    @PostMapping("/{id}/end")
    public ResponseEntity<Void> endRide(@PathVariable Long id) {
        gestioneCorsaService.terminaCorsa(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/estimate")
    public ResponseEntity<StimaCorsaResponse> getEstimate(@PathVariable Long id) {
        StimaCorsaResponse stima = gestioneCorsaService.aggiornaStima(id);
        return ResponseEntity.ok(stima);
    }

    @PostMapping("/{id}/pause")
    public ResponseEntity<Boolean> pauseRide(@PathVariable Long id) {
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
    public ResponseEntity<Void> selectPaymentMethod(@Valid @RequestBody PaymentMethodSelectionRequest request) {
        gestioneCorsaService.acquisisciSceltaMetodo(request.getIdMetodoPagamento());
        return ResponseEntity.ok().build();
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

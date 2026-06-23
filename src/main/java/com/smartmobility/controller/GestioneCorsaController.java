package com.smartmobility.controller;

import com.smartmobility.dto.request.PaymentMethodSelectionRequest;
import com.smartmobility.dto.request.RouteRequest;
import com.smartmobility.dto.request.StartRideRequest;
import com.smartmobility.dto.request.UnlockRequest;
import com.smartmobility.dto.response.PercorsoResponse;
import com.smartmobility.service.GestioneCorsaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/rides")
public class GestioneCorsaController {

    private final GestioneCorsaService gestioneCorsaService;

    public GestioneCorsaController(GestioneCorsaService gestioneCorsaService) {
        this.gestioneCorsaService = gestioneCorsaService;
    }

    @PostMapping("/start")
    public ResponseEntity<Void> startRide(@Valid @RequestBody StartRideRequest request) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/{id}/end")
    public ResponseEntity<Void> endRide(@PathVariable Long id) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @GetMapping("/{id}/estimate")
    public ResponseEntity<Float> getEstimate(@PathVariable Long id) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/{id}/pause")
    public ResponseEntity<Boolean> pauseRide(@PathVariable Long id) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/unlock")
    public ResponseEntity<Boolean> unlock(@Valid @RequestBody UnlockRequest request) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/route")
    public ResponseEntity<PercorsoResponse> calculateRoute(@Valid @RequestBody RouteRequest request) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/payment-method")
    public ResponseEntity<Void> selectPaymentMethod(@Valid @RequestBody PaymentMethodSelectionRequest request) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<Boolean> checkAvailability(@PathVariable Long id) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @GetMapping("/{id}/availability/{info}")
    public ResponseEntity<Boolean> checkAvailability(@PathVariable Long id, @PathVariable String info) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

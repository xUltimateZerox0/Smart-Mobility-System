package com.smartmobility.controller;

import com.smartmobility.dto.request.AddPaymentMethodRequest;
import com.smartmobility.dto.request.ProcessPaymentRequest;
import com.smartmobility.dto.response.MetodoPagamentoResponse;
import com.smartmobility.service.GestorePagamentoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class GestorePagamentoController {

    private final GestorePagamentoService gestorePagamentoService;

    public GestorePagamentoController(GestorePagamentoService gestorePagamentoService) {
        this.gestorePagamentoService = gestorePagamentoService;
    }

    @PostMapping("/process")
    public ResponseEntity<Boolean> processPayment(@Valid @RequestBody ProcessPaymentRequest request) {
        boolean result = gestorePagamentoService.pagamentoCorsa(
                request.getIdUtente(), request.getIdMetodoPagamento(), request.getCosto());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/methods")
    public ResponseEntity<Boolean> addPaymentMethod(@Valid @RequestBody AddPaymentMethodRequest request) {
        boolean result = gestorePagamentoService.elaboraDatiCarta(
                request.getIdUtente(), request.getNumCarta(),
                request.getDsCarta(), request.getCvv(), request.getIntestatarioCarta());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/methods")
    public ResponseEntity<List<MetodoPagamentoResponse>> getSavedMethods(@RequestParam Long idUtente) {
        List<MetodoPagamentoResponse> methods = gestorePagamentoService.recuperaMetodiSalvati(idUtente);
        return ResponseEntity.ok(methods);
    }
}

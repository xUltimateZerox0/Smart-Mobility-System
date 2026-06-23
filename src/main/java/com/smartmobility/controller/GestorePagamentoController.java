package com.smartmobility.controller;

import com.smartmobility.dto.request.AddPaymentMethodRequest;
import com.smartmobility.dto.request.ProcessPaymentRequest;
import com.smartmobility.dto.response.MetodoPagamentoResponse;
import com.smartmobility.service.GestorePagamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/methods")
    public ResponseEntity<Boolean> addPaymentMethod(@Valid @RequestBody AddPaymentMethodRequest request) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @GetMapping("/methods")
    public ResponseEntity<List<MetodoPagamentoResponse>> getSavedMethods() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

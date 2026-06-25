package com.smartmobility.controller;

import com.smartmobility.dto.request.AddPaymentMethodRequest;
import com.smartmobility.dto.request.ProcessPaymentRequest;
import com.smartmobility.dto.response.MetodoPagamentoResponse;
import com.smartmobility.security.SecurityHelper;
import com.smartmobility.service.GestorePagamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class GestorePagamentoController {

    private final GestorePagamentoService gestorePagamentoService;
    private final SecurityHelper securityHelper;

    public GestorePagamentoController(GestorePagamentoService gestorePagamentoService, SecurityHelper securityHelper) {
        this.gestorePagamentoService = gestorePagamentoService;
        this.securityHelper = securityHelper;
    }

    @PostMapping("/process")
    public ResponseEntity<Boolean> processPayment(@Valid @RequestBody ProcessPaymentRequest request,
                                                   @RequestHeader(value = "Authorization", required = false) String authHeader) {
        securityHelper.requireUserIdMatch(authHeader, request.getIdUtente());
        boolean result = gestorePagamentoService.pagamentoCorsa(
                request.getIdUtente(), request.getIdMetodoPagamento(), null, request.getCosto());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/methods")
    public ResponseEntity<Boolean> addPaymentMethod(@Valid @RequestBody AddPaymentMethodRequest request,
                                                     @RequestHeader(value = "Authorization", required = false) String authHeader) {
        securityHelper.requireUserIdMatch(authHeader, request.getIdUtente());
        boolean result = gestorePagamentoService.elaboraDatiCarta(
                request.getIdUtente(), request.getNumCarta(),
                request.getDsCarta(), request.getCvv(), request.getIntestatarioCarta());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/methods")
    public ResponseEntity<List<MetodoPagamentoResponse>> getSavedMethods(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                                                          @RequestParam(required = false) Long idUtente) {
        Long userId = (idUtente != null) ? idUtente : securityHelper.getCurrentUserId(authHeader);
        if (userId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (idUtente != null) securityHelper.requireUserIdMatch(authHeader, idUtente);
        List<MetodoPagamentoResponse> methods = gestorePagamentoService.recuperaMetodiSalvati(userId);
        return ResponseEntity.ok(methods);
    }
}

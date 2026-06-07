package com.smartmobility.controller;

import com.smartmobility.dto.DatiCartaRequest;
import com.smartmobility.dto.PagamentoRequest;
import com.smartmobility.service.MetodoPagamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/pagamenti")
public class GestorePagamentoController {

    private final MetodoPagamentoService metodoPagamentoService;

    public GestorePagamentoController(MetodoPagamentoService metodoPagamentoService) {
        this.metodoPagamentoService = metodoPagamentoService;
    }

    @PostMapping("/corsa")
    public ResponseEntity<Boolean> pagamentoCorsa(@RequestBody PagamentoRequest request) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/carta")
    public ResponseEntity<Boolean> elaboraDatiCarta(@RequestBody DatiCartaRequest request) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

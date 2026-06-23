package com.smartmobility.controller;

import com.smartmobility.dto.request.LoginRequest;
import com.smartmobility.dto.request.LogoutRequest;
import com.smartmobility.dto.request.RegisterRequest;
import com.smartmobility.dto.response.AuthResponse;
import com.smartmobility.service.GestioneAutenticazioneService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class GestioneAutenticazioneController {

    private final GestioneAutenticazioneService gestioneAutenticazioneService;

    public GestioneAutenticazioneController(GestioneAutenticazioneService gestioneAutenticazioneService) {
        this.gestioneAutenticazioneService = gestioneAutenticazioneService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody LogoutRequest request) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

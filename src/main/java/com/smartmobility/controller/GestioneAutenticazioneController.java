package com.smartmobility.controller;

import com.smartmobility.dto.request.LoginRequest;
import com.smartmobility.dto.request.LogoutRequest;
import com.smartmobility.dto.request.RegisterRequest;
import com.smartmobility.dto.response.AuthResponse;
import com.smartmobility.service.GestioneAutenticazioneService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class GestioneAutenticazioneController {

    private final GestioneAutenticazioneService gestioneAutenticazioneService;

    public GestioneAutenticazioneController(GestioneAutenticazioneService gestioneAutenticazioneService) {
        this.gestioneAutenticazioneService = gestioneAutenticazioneService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = gestioneAutenticazioneService.invioCredenziali(request.email(), request.password());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = gestioneAutenticazioneService.verificaValidita(
                request.getNome(), request.getCognome(),
                request.getEmail(), request.getPassword(),
                request.getDatanascita());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody LogoutRequest request) {
        gestioneAutenticazioneService.inviaRichiestaLogout(request.getEmail());
        return ResponseEntity.noContent().build();
    }
}

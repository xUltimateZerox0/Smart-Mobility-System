package com.smartmobility.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartmobility.dto.request.LoginRequest;
import com.smartmobility.dto.request.LogoutRequest;
import com.smartmobility.dto.request.RegisterRequest;
import com.smartmobility.dto.response.AuthResponse;
import com.smartmobility.service.GestioneAutenticazioneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GestioneAutenticazioneController.class)
class GestioneAutenticazioneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GestioneAutenticazioneService gestioneAutenticazioneService;

    @Test
    void login_WithValidCredentials_ReturnsAuthResponse() throws Exception {
        LoginRequest request = new LoginRequest("test@example.com", "password123");
        AuthResponse response = new AuthResponse("token123", "test@example.com", "Utente", 1L, null);

        when(gestioneAutenticazioneService.invioCredenziali("test@example.com", "password123"))
                .thenReturn(response);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token123"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.ruolo").value("Utente"))
                .andExpect(jsonPath("$.idUtente").value(1L));
    }

    @Test
    void login_WithInvalidCredentials_ReturnsUnauthorized() throws Exception {
        LoginRequest request = new LoginRequest("wrong@example.com", "wrongpass");

        when(gestioneAutenticazioneService.invioCredenziali("wrong@example.com", "wrongpass"))
                .thenThrow(new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.UNAUTHORIZED, "Credenziali non valide"));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void register_WithValidData_ReturnsAuthResponse() throws Exception {
        RegisterRequest request = new RegisterRequest("Mario", "Rossi", "mario@example.com", "pass123", "1990-01-01");
        AuthResponse response = new AuthResponse("token456", "mario@example.com", "Utente", 2L, null);

        when(gestioneAutenticazioneService.verificaValidita("Mario", "Rossi", "mario@example.com", "pass123", "1990-01-01"))
                .thenReturn(response);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token456"))
                .andExpect(jsonPath("$.email").value("mario@example.com"))
                .andExpect(jsonPath("$.ruolo").value("Utente"))
                .andExpect(jsonPath("$.idUtente").value(2L));
    }

    @Test
    void register_WithDuplicateEmail_ReturnsConflict() throws Exception {
        RegisterRequest request = new RegisterRequest("Mario", "Rossi", "existing@example.com", "pass123", "1990-01-01");

        when(gestioneAutenticazioneService.verificaValidita("Mario", "Rossi", "existing@example.com", "pass123", "1990-01-01"))
                .thenThrow(new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.CONFLICT, "Email già registrata"));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void logout_WithValidEmail_ReturnsNoContent() throws Exception {
        LogoutRequest request = new LogoutRequest("test@example.com");

        mockMvc.perform(post("/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    void logout_WithInvalidEmailFormat_ReturnsBadRequest() throws Exception {
        String invalidJson = "{\"email\": \"invalid-email\"}";

        mockMvc.perform(post("/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}

package com.smartmobility.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartmobility.dto.request.CorrectiveActionRequest;
import com.smartmobility.service.GestioneUtentiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GestioneUtentiController.class)
class GestioneUtentiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GestioneUtentiService gestioneUtentiService;

    @Test
    void getReport_WithValidId_ReturnsReport() throws Exception {
        when(gestioneUtentiService.cercaReport(1L)).thenReturn("Segnalazione per comportamento scorretto");

        mockMvc.perform(get("/admin/users/1/report"))
                .andExpect(status().isOk())
                .andExpect(content().string("Segnalazione per comportamento scorretto"));
    }

    @Test
    void getReport_WithInvalidId_ReturnsNotFound() throws Exception {
        when(gestioneUtentiService.cercaReport(999L))
                .thenThrow(new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Utente non trovato"));

        mockMvc.perform(get("/admin/users/999/report"))
                .andExpect(status().isNotFound());
    }

    @Test
    void moderateUser_WithValidId_ReturnsTrue() throws Exception {
        when(gestioneUtentiService.gestioneUtente(1L)).thenReturn(true);

        mockMvc.perform(put("/admin/users/1/moderate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void moderateUser_WhenUserDisabled_ReturnsConflict() throws Exception {
        when(gestioneUtentiService.gestioneUtente(1L))
                .thenThrow(new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.CONFLICT, "Utente disattivato non modificabile"));

        mockMvc.perform(put("/admin/users/1/moderate"))
                .andExpect(status().isConflict());
    }

    @Test
    void correctiveAction_WithValidData_ReturnsNoContent() throws Exception {
        CorrectiveActionRequest request = new CorrectiveActionRequest("Avviso per parcheggio non autorizzato");

        mockMvc.perform(post("/admin/users/1/corrective-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        verify(gestioneUtentiService).azioneCorrettiva(1L, "Avviso per parcheggio non autorizzato");
    }

    @Test
    void correctiveAction_WithEmptyAction_ReturnsBadRequest() throws Exception {
        String invalidJson = "{\"azione\": \"\"}";

        mockMvc.perform(post("/admin/users/1/corrective-action")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}

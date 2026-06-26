package com.smartmobility.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartmobility.dto.request.CorrectiveActionRequest;
import com.smartmobility.model.Attore;
import com.smartmobility.model.enums.RuoloAttore;
import com.smartmobility.security.SecurityHelper;
import com.smartmobility.service.GestioneUtentiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

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

    @MockBean
    private SecurityHelper securityHelper;

    @SuppressWarnings("java:S116")
    private final String AUTH_HEADER = "Bearer test-token";

    private Attore createPA() {
        Attore a = mock(Attore.class);
        when(a.getId()).thenReturn(1L);
        when(a.getRuolo()).thenReturn(RuoloAttore.PA);
        when(a.getEmail()).thenReturn("pa@test.com");
        return a;
    }

    @BeforeEach
    void setUpAuth() {
        Attore pa = createPA();
        when(securityHelper.getCurrentUser(AUTH_HEADER)).thenReturn(pa);
    }

    @Test
    void getReport_WithValidId_ReturnsReport() throws Exception {
        when(gestioneUtentiService.cercaReport(1L)).thenReturn("Segnalazione per comportamento scorretto");

        mockMvc.perform(get("/admin/users/1/report").header("Authorization", AUTH_HEADER))
                .andExpect(status().isOk())
                .andExpect(content().string("Segnalazione per comportamento scorretto"));
    }

    @Test
    void getReport_WithInvalidId_ReturnsNotFound() throws Exception {
        when(gestioneUtentiService.cercaReport(999L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));

        mockMvc.perform(get("/admin/users/999/report").header("Authorization", AUTH_HEADER))
                .andExpect(status().isNotFound());
    }

    @Test
    void moderateUser_WithValidId_ReturnsTrue() throws Exception {
        when(gestioneUtentiService.gestioneUtente(1L)).thenReturn(true);

        mockMvc.perform(put("/admin/users/1/moderate").header("Authorization", AUTH_HEADER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void moderateUser_WhenUserDisabled_ReturnsConflict() throws Exception {
        when(gestioneUtentiService.gestioneUtente(1L))
                .thenThrow(new ResponseStatusException(HttpStatus.CONFLICT, "Utente disattivato non modificabile"));

        mockMvc.perform(put("/admin/users/1/moderate").header("Authorization", AUTH_HEADER))
                .andExpect(status().isConflict());
    }

    @Test
    void correctiveAction_WithValidData_ReturnsNoContent() throws Exception {
        CorrectiveActionRequest request = new CorrectiveActionRequest("Avviso per parcheggio non autorizzato");

        mockMvc.perform(post("/admin/users/1/corrective-action")
                        .header("Authorization", AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        verify(gestioneUtentiService).azioneCorrettiva(1L, "Avviso per parcheggio non autorizzato");
    }

    @Test
    void correctiveAction_WithEmptyAction_ReturnsBadRequest() throws Exception {
        String invalidJson = "{\"azione\": \"\"}";

        mockMvc.perform(post("/admin/users/1/corrective-action")
                        .header("Authorization", AUTH_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}

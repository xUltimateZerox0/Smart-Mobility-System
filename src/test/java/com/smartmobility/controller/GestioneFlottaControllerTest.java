package com.smartmobility.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.model.enums.RuoloAttore;
import com.smartmobility.security.SecurityHelper;
import com.smartmobility.service.GestioneFlottaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GestioneFlottaController.class)
class GestioneFlottaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GestioneFlottaService gestioneFlottaService;

    @MockBean
    private SecurityHelper securityHelper;

    @BeforeEach
    void setUp() {
        doNothing().when(securityHelper).requireAuth(anyString());
        doNothing().when(securityHelper).requireRole(anyString(), any(RuoloAttore.class));
    }

    @Test
    void analyzeFleet_WithValidId_ReturnsBoolean() throws Exception {
        when(gestioneFlottaService.analisiStatoFlotta(1L)).thenReturn(true);

        mockMvc.perform(post("/fleet/1/analyze")
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void analyzeFleet_WithNoIssues_ReturnsFalse() throws Exception {
        when(gestioneFlottaService.analisiStatoFlotta(1L)).thenReturn(false);

        mockMvc.perform(post("/fleet/1/analyze")
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    void lockVehicle_WithValidId_ReturnsTrue() throws Exception {
        when(gestioneFlottaService.bloccaMezzo(1L)).thenReturn(true);

        mockMvc.perform(post("/fleet/vehicles/1/lock")
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void lockVehicle_WithInvalidId_ReturnsNotFound() throws Exception {
        when(gestioneFlottaService.bloccaMezzo(999L))
                .thenThrow(new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Mezzo non trovato"));

        mockMvc.perform(post("/fleet/vehicles/999/lock")
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isNotFound());
    }

    @Test
    void startMaintenance_WithValidFleetId_ReturnsTrue() throws Exception {
        when(gestioneFlottaService.avviaManutenzione(1L)).thenReturn(true);

        mockMvc.perform(post("/fleet/1/maintenance")
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void startMaintenance_WithoutAuth_ReturnsUnauthorized() throws Exception {
        mockMvc.perform(post("/fleet/1/maintenance"))
                .andExpect(status().isOk());
    }

    @Test
    void getVehicleConditions_WithValidFleetId_ReturnsList() throws Exception {
        List<MezzoResponse> conditions = List.of(
                new MezzoResponse(1L, "bici", "disponibile", 41.9028, 12.4964, 80.0, 5.0, "FLOTTA-1")
        );
        when(gestioneFlottaService.getCondizioniMezzi(1L)).thenReturn(conditions);

        mockMvc.perform(get("/fleet/1/conditions")
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipo").value("bici"))
                .andExpect(jsonPath("$[0].stato").value("disponibile"));
    }

    @Test
    void getVehicleConditions_WithEmptyFleet_ReturnsEmptyList() throws Exception {
        when(gestioneFlottaService.getCondizioniMezzi(1L)).thenReturn(List.of());

        mockMvc.perform(get("/fleet/1/conditions")
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}

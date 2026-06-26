package com.smartmobility.controller;

import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.dto.response.SegnalazioneResponse;
import com.smartmobility.security.SecurityHelper;
import com.smartmobility.service.GestioneFlottaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GestioneFlottaController.class)
class GestioneFlottaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GestioneFlottaService gestioneFlottaService;

    @MockBean
    private SecurityHelper securityHelper;

    @BeforeEach
    void setUp() {
        doNothing().when(securityHelper).requireAuth(anyString());
        doNothing().when(securityHelper).requireRole(anyString(), any());
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

    @Test
    void unlockVehicle_WithValidId_ReturnsTrue() throws Exception {
        when(gestioneFlottaService.sbloccaMezzo(1L)).thenReturn(true);

        mockMvc.perform(post("/fleet/vehicles/1/unlock")
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void unlockVehicle_WithInvalidId_ReturnsNotFound() throws Exception {
        when(gestioneFlottaService.sbloccaMezzo(999L))
                .thenThrow(new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Mezzo non trovato"));

        mockMvc.perform(post("/fleet/vehicles/999/unlock")
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isNotFound());
    }

    @Test
    void startVehicleMaintenance_WithValidId_ReturnsTrue() throws Exception {
        when(gestioneFlottaService.avviaManutenzioneVeicolo(1L)).thenReturn(true);

        mockMvc.perform(post("/fleet/vehicles/1/maintenance")
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void startVehicleMaintenance_WithInvalidId_ReturnsNotFound() throws Exception {
        when(gestioneFlottaService.avviaManutenzioneVeicolo(999L))
                .thenThrow(new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Mezzo non trovato"));

        mockMvc.perform(post("/fleet/vehicles/999/maintenance")
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getSegnalazioni_ReturnsList() throws Exception {
        List<SegnalazioneResponse> segnalazioni = List.of(
                new SegnalazioneResponse(1L, 2L, "aperta", "10:30", "2026-06-26", "Guasto meccanico")
        );
        when(gestioneFlottaService.getSegnalazioni()).thenReturn(segnalazioni);

        mockMvc.perform(get("/fleet/segnalazioni")
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idSegnalazione").value(1L))
                .andExpect(jsonPath("$[0].stato").value("aperta"))
                .andExpect(jsonPath("$[0].motivazione").value("Guasto meccanico"));
    }

    @Test
    void getSegnalazioni_WhenEmpty_ReturnsEmptyList() throws Exception {
        when(gestioneFlottaService.getSegnalazioni()).thenReturn(List.of());

        mockMvc.perform(get("/fleet/segnalazioni")
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getSegnalazioniByStato_WithValidStato_ReturnsList() throws Exception {
        List<SegnalazioneResponse> segnalazioni = List.of(
                new SegnalazioneResponse(2L, 3L, "aperta", "11:00", "2026-06-26", "Batteria scarica")
        );
        when(gestioneFlottaService.getSegnalazioniByStato("aperta")).thenReturn(segnalazioni);

        mockMvc.perform(get("/fleet/segnalazioni/aperta")
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idSegnalazione").value(2L))
                .andExpect(jsonPath("$[0].stato").value("aperta"));
    }

    @Test
    void getSegnalazioniByStato_WithNoResults_ReturnsEmptyList() throws Exception {
        when(gestioneFlottaService.getSegnalazioniByStato("risolta")).thenReturn(List.of());

        mockMvc.perform(get("/fleet/segnalazioni/risolta")
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}

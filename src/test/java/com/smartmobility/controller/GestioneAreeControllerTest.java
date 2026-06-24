package com.smartmobility.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartmobility.dto.request.ConflictCheckRequest;
import com.smartmobility.dto.request.UpdateZoneRequest;
import com.smartmobility.dto.response.ZonaGeograficaResponse;
import com.smartmobility.service.GestioneAreeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GestioneAreeController.class)
class GestioneAreeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GestioneAreeService gestioneAreeService;

    @Test
    void updateZone_WithValidData_ReturnsOk() throws Exception {
        UpdateZoneRequest request = new UpdateZoneRequest("ZTL", "Divieto accesso", "41.9028,12.4964;41.9030,12.4970");

        mockMvc.perform(put("/zones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(gestioneAreeService).aggiornaRestrizione(1L, "ZTL", "Divieto accesso", "41.9028,12.4964;41.9030,12.4970");
    }

    @Test
    void updateZone_WithInvalidTipoRestrizione_ReturnsBadRequest() throws Exception {
        UpdateZoneRequest request = new UpdateZoneRequest("INVALID_TYPE", "Note", "41.9028,12.4964");

        doThrow(new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.BAD_REQUEST, "Tipo restrizione non valido"))
                .when(gestioneAreeService).aggiornaRestrizione(1L, "INVALID_TYPE", "Note", "41.9028,12.4964");

        mockMvc.perform(put("/zones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void checkConflict_WithOverlappingZones_ReturnsTrue() throws Exception {
        ConflictCheckRequest request = new ConflictCheckRequest("41.9030,12.4970;41.9040,12.4980");
        when(gestioneAreeService.analisiConflitti("41.9030,12.4970;41.9040,12.4980")).thenReturn(true);

        mockMvc.perform(post("/zones/conflict-check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void checkConflict_WithNoOverlap_ReturnsFalse() throws Exception {
        ConflictCheckRequest request = new ConflictCheckRequest("90.0000,180.0000");
        when(gestioneAreeService.analisiConflitti("90.0000,180.0000")).thenReturn(false);

        mockMvc.perform(post("/zones/conflict-check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    void getZones_ReturnsListOfZones() throws Exception {
        List<ZonaGeograficaResponse> zones = List.of(
                new ZonaGeograficaResponse(1L, "ZTL", "ZTL", "Centro storico", "41.9028,12.4964")
        );
        when(gestioneAreeService.getZoneGeografiche()).thenReturn(zones);

        mockMvc.perform(get("/zones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].tipoRestrizione").value("ZTL"));
    }

    @Test
    void getZones_WhenEmpty_ReturnsEmptyList() throws Exception {
        when(gestioneAreeService.getZoneGeografiche()).thenReturn(List.of());

        mockMvc.perform(get("/zones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}

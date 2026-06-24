package com.smartmobility.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartmobility.dto.request.NearbyVehiclesRequest;
import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.service.RicercaMezziService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RicercaMezziController.class)
class RicercaMezziControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RicercaMezziService ricercaMezziService;

    @Test
    void getNearbyVehicles_WithValidRequest_ReturnsMezzoList() throws Exception {
        NearbyVehiclesRequest request = new NearbyVehiclesRequest("41.9028,12.4964,0.0", 2.0f);
        List<MezzoResponse> responseList = List.of(
                new MezzoResponse(1L, "bici", "disponibile", 41.9028, 12.4964, 80.0, 5.0, "FLOTTA-1")
        );

        when(ricercaMezziService.visualizzaMezziVicini("41.9028,12.4964,0.0", 2.0f))
                .thenReturn(responseList);

        mockMvc.perform(post("/vehicles/nearby")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipo").value("bici"))
                .andExpect(jsonPath("$[0].stato").value("disponibile"))
                .andExpect(jsonPath("$[0].autonomia").value(80.0));
    }

    @Test
    void getNearbyVehicles_WithEmptyResults_ReturnsEmptyList() throws Exception {
        NearbyVehiclesRequest request = new NearbyVehiclesRequest("41.9028,12.4964,0.0", 2.0f);

        when(ricercaMezziService.visualizzaMezziVicini("41.9028,12.4964,0.0", 2.0f))
                .thenReturn(List.of());

        mockMvc.perform(post("/vehicles/nearby")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getNearbyVehicles_WithInvalidCoordinates_ReturnsBadRequest() throws Exception {
        String invalidJson = "{\"coordinateUtente\": \"\", \"raggio\": -1}";

        mockMvc.perform(post("/vehicles/nearby")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getVehicleDetails_WithValidId_ReturnsMezzoResponse() throws Exception {
        MezzoResponse response = new MezzoResponse(1L, "scooter", "disponibile", 41.9028, 12.4964, 60.0, 8.0, "FLOTTA-1");

        when(ricercaMezziService.visualizzaSpecifiche(1L)).thenReturn(response);

        mockMvc.perform(get("/vehicles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipo").value("scooter"))
                .andExpect(jsonPath("$.tariffa").value(8.0));
    }

    @Test
    void getVehicleDetails_WithInvalidId_ReturnsNotFound() throws Exception {
        when(ricercaMezziService.visualizzaSpecifiche(999L))
                .thenThrow(new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Mezzo non trovato"));

        mockMvc.perform(get("/vehicles/999"))
                .andExpect(status().isNotFound());
    }
}

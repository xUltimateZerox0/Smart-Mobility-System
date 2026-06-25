package com.smartmobility.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartmobility.dto.request.*;
import com.smartmobility.dto.response.PercorsoResponse;
import com.smartmobility.dto.response.StimaCorsaResponse;
import com.smartmobility.security.SecurityHelper;
import com.smartmobility.service.GestioneCorsaService;
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

@WebMvcTest(GestioneCorsaController.class)
class GestioneCorsaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GestioneCorsaService gestioneCorsaService;

    @MockBean
    private SecurityHelper securityHelper;

    @Test
    void startRide_WithValidRequest_ReturnsOk() throws Exception {
        StartRideRequest request = new StartRideRequest(1L, 1L, "QR-1");

        mockMvc.perform(post("/rides/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(gestioneCorsaService).avviaCorsa(1L, 1L, "QR-1");
    }

    @Test
    void startRide_WhenMezzoNotAvailable_ReturnsConflict() throws Exception {
        StartRideRequest request = new StartRideRequest(1L, 1L, "QR-1");

        doThrow(new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.CONFLICT, "Mezzo non disponibile"))
                .when(gestioneCorsaService).avviaCorsa(1L, 1L, "QR-1");

        mockMvc.perform(post("/rides/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void endRide_WithValidId_ReturnsOk() throws Exception {
        mockMvc.perform(post("/rides/1/end"))
                .andExpect(status().isOk());

        verify(gestioneCorsaService).terminaCorsa(1L);
    }

    @Test
    void endRide_WithInvalidId_ReturnsNotFound() throws Exception {
        doThrow(new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, "Corsa non trovata"))
                .when(gestioneCorsaService).terminaCorsa(999L);

        mockMvc.perform(post("/rides/999/end"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getEstimate_WithValidId_ReturnsCost() throws Exception {
        when(gestioneCorsaService.aggiornaStima(1L)).thenReturn(new StimaCorsaResponse(15.5f, 10.0f));

        mockMvc.perform(get("/rides/1/estimate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.costo").value(15.5))
                .andExpect(jsonPath("$.tariffa").value(10.0));
    }

    @Test
    void pauseRide_WithValidId_ReturnsTrue() throws Exception {
        when(gestioneCorsaService.sospensioneCorsa(1L)).thenReturn(true);

        mockMvc.perform(post("/rides/1/pause"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void unlock_WithValidQrCode_ReturnsTrue() throws Exception {
        UnlockRequest request = new UnlockRequest("12345");
        when(gestioneCorsaService.richiediSblocco("12345")).thenReturn(true);

        mockMvc.perform(post("/rides/unlock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void unlock_WithInvalidQrCode_ReturnsBadRequest() throws Exception {
        UnlockRequest request = new UnlockRequest("invalid");

        when(gestioneCorsaService.richiediSblocco("invalid"))
                .thenThrow(new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.BAD_REQUEST, "QR Code non valido"));

        mockMvc.perform(post("/rides/unlock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void calculateRoute_WithValidRequest_ReturnsPercorso() throws Exception {
        RouteRequest request = new RouteRequest("41.9028,12.4964,0.0", "41.9030,12.4970,0.0");
        PercorsoResponse response = new PercorsoResponse("route_data", "Calcolo percorso completato");

        when(gestioneCorsaService.richiediCalcoloPercorso("41.9028,12.4964,0.0", "41.9030,12.4970,0.0"))
                .thenReturn(response);

        mockMvc.perform(post("/rides/route")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.messaggio").value("Calcolo percorso completato"));
    }

    @Test
    void selectPaymentMethod_WithValidId_ReturnsOk() throws Exception {
        PaymentMethodSelectionRequest request = new PaymentMethodSelectionRequest(1L);

        mockMvc.perform(post("/rides/payment-method")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(gestioneCorsaService).acquisisciSceltaMetodo(1L);
    }

    @Test
    void checkAvailability_WithValidId_ReturnsBoolean() throws Exception {
        when(gestioneCorsaService.controllaDisponibilita(1L)).thenReturn(true);

        mockMvc.perform(get("/rides/1/availability"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void checkAvailability_WithIdAndInfo_ReturnsBoolean() throws Exception {
        when(gestioneCorsaService.controllaDisponibilita(1L, "bici")).thenReturn(true);

        mockMvc.perform(get("/rides/1/availability/bici"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }
}

package com.smartmobility.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartmobility.dto.request.PrenotazioneRequest;
import com.smartmobility.dto.response.PrenotazioneResponse;
import com.smartmobility.security.SecurityHelper;
import com.smartmobility.service.GestionePrenotazioneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GestionePrenotazioneController.class)
class GestionePrenotazioneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GestionePrenotazioneService gestionePrenotazioneService;

    @MockBean
    private SecurityHelper securityHelper;

    @Test
    void createBooking_WithValidRequest_ReturnsOk() throws Exception {
        PrenotazioneRequest request = new PrenotazioneRequest(1L, 1L, null);
        PrenotazioneResponse mockResponse = new PrenotazioneResponse(1L, 1L, 1L, "2026-01-15", "10:00", "attiva");

        when(gestionePrenotazioneService.inviaRichiestaPrenotazione(1L, 1L, null)).thenReturn(mockResponse);

        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(gestionePrenotazioneService).inviaRichiestaPrenotazione(1L, 1L, null);
    }

    @Test
    void createBooking_WhenMezzoNotAvailable_ReturnsConflict() throws Exception {
        PrenotazioneRequest request = new PrenotazioneRequest(1L, 1L, null);

        doThrow(new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.CONFLICT, "Mezzo non disponibile"))
                .when(gestionePrenotazioneService).inviaRichiestaPrenotazione(1L, 1L, null);

        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void getBookings_ReturnsListOfPrenotazioni() throws Exception {
        List<PrenotazioneResponse> bookings = List.of(
                new PrenotazioneResponse(1L, 1L, 1L, "2026-01-15", "10:00", "attiva")
        );

        when(gestionePrenotazioneService.richiediLista()).thenReturn(bookings);

        mockMvc.perform(get("/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].stato").value("attiva"));
    }

    @Test
    void cancelBooking_WithValidId_ReturnsTrue() throws Exception {
        when(gestionePrenotazioneService.annullaPrenotazione(1L)).thenReturn(true);

        mockMvc.perform(delete("/bookings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void cancelBooking_WithInvalidId_ReturnsNotFound() throws Exception {
        when(gestionePrenotazioneService.annullaPrenotazione(999L))
                .thenThrow(new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Prenotazione non trovata"));

        mockMvc.perform(delete("/bookings/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void handleTimeout_ReturnsOk() throws Exception {
        mockMvc.perform(post("/bookings/timeout"))
                .andExpect(status().isOk());

        verify(gestionePrenotazioneService).gestisciTimeout();
    }

    @Test
    void notifyExpiry_WithValidId_ReturnsOk() throws Exception {
        mockMvc.perform(post("/bookings/1/notify-expiry"))
                .andExpect(status().isOk());

        verify(gestionePrenotazioneService).notificaScadenzaTempo(1L);
    }
}

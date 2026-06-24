package com.smartmobility.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartmobility.dto.request.AnalyzeStatisticsRequest;
import com.smartmobility.dto.request.ExportStatisticsRequest;
import com.smartmobility.dto.response.CorsaResponse;
import com.smartmobility.dto.response.StatisticheResponse;
import com.smartmobility.service.GestioneStatisticheService;
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

@WebMvcTest(GestioneStatisticheController.class)
class GestioneStatisticheControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GestioneStatisticheService gestioneStatisticheService;

    @Test
    void analyzeStatistics_WithValidDates_ReturnsStatisticheResponse() throws Exception {
        AnalyzeStatisticsRequest request = new AnalyzeStatisticsRequest("2026-01-01T00:00:00", "2026-01-31T23:59:59");
        StatisticheResponse response = new StatisticheResponse(10L, 150.0, 500.0, 30.0, "details");

        when(gestioneStatisticheService.analisiTratte("2026-01-01T00:00:00", "2026-01-31T23:59:59"))
                .thenReturn(response);

        mockMvc.perform(post("/statistics/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCorse").value(10))
                .andExpect(jsonPath("$.totalKm").value(150.0))
                .andExpect(jsonPath("$.totalRicavo").value(500.0));
    }

    @Test
    void analyzeStatistics_WithInvalidDateFormat_ReturnsBadRequest() throws Exception {
        AnalyzeStatisticsRequest request = new AnalyzeStatisticsRequest("invalid-date", "invalid-date");

        when(gestioneStatisticheService.analisiTratte("invalid-date", "invalid-date"))
                .thenThrow(new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.BAD_REQUEST, "Formato data non valido"));

        mockMvc.perform(post("/statistics/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void analyzeStatistics_WithNoData_ReturnsEmptyStatistics() throws Exception {
        AnalyzeStatisticsRequest request = new AnalyzeStatisticsRequest("2025-01-01T00:00:00", "2025-01-01T01:00:00");
        StatisticheResponse emptyStats = new StatisticheResponse(0L, 0.0, 0.0, 0.0, "empty");

        when(gestioneStatisticheService.analisiTratte("2025-01-01T00:00:00", "2025-01-01T01:00:00"))
                .thenReturn(emptyStats);

        mockMvc.perform(post("/statistics/analyze")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCorse").value(0));
    }

    @Test
    void exportStatistics_WithValidData_ReturnsOk() throws Exception {
        List<CorsaResponse> corse = List.of(
                new CorsaResponse(1L, 1L, 1L, "2026-01-15T10:00", "2026-01-15T10:30", 2.5, 5.0, "completata")
        );
        ExportStatisticsRequest request = new ExportStatisticsRequest(corse);

        mockMvc.perform(post("/statistics/export")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(gestioneStatisticheService).generaFileStatistiche(corse);
    }

    @Test
    void exportStatistics_WithEmptyList_ReturnsBadRequest() throws Exception {
        ExportStatisticsRequest request = new ExportStatisticsRequest(List.of());

        mockMvc.perform(post("/statistics/export")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}

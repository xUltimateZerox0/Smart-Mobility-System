package com.smartmobility.controller;

import com.smartmobility.dto.request.AnalyzeStatisticsRequest;
import com.smartmobility.dto.request.ExportStatisticsRequest;
import com.smartmobility.dto.response.StatisticheResponse;
import com.smartmobility.service.GestioneStatisticheService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/statistics")
public class GestioneStatisticheController {

    private final GestioneStatisticheService gestioneStatisticheService;

    public GestioneStatisticheController(GestioneStatisticheService gestioneStatisticheService) {
        this.gestioneStatisticheService = gestioneStatisticheService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<StatisticheResponse> analyzeStatistics(@Valid @RequestBody AnalyzeStatisticsRequest request) {
        StatisticheResponse stats = gestioneStatisticheService.analisiTratte(
                request.getDataInizio(), request.getDataFine());
        return ResponseEntity.ok(stats);
    }

    @PostMapping("/export")
    public ResponseEntity<byte[]> exportStatistics(@Valid @RequestBody ExportStatisticsRequest request) {
        String csv = gestioneStatisticheService.generaFileStatistiche(request.getCorse());
        byte[] csvBytes = csv.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        String filename = "statistiche_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".csv";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csvBytes);
    }
}

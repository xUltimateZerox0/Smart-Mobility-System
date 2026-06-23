package com.smartmobility.controller;

import com.smartmobility.dto.request.AnalyzeStatisticsRequest;
import com.smartmobility.dto.request.ExportStatisticsRequest;
import com.smartmobility.dto.response.StatisticheResponse;
import com.smartmobility.service.GestioneStatisticheService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Void> exportStatistics(@Valid @RequestBody ExportStatisticsRequest request) {
        gestioneStatisticheService.generaFileStatistiche(request.getCorse());
        return ResponseEntity.ok().build();
    }
}

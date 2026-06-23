package com.smartmobility.controller;

import com.smartmobility.dto.request.AnalyzeStatisticsRequest;
import com.smartmobility.dto.request.ExportStatisticsRequest;
import com.smartmobility.dto.response.StatisticheResponse;
import com.smartmobility.service.GestioneStatisticheService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/statistics")
public class GestioneStatisticheController {

    private final GestioneStatisticheService gestioneStatisticheService;

    public GestioneStatisticheController(GestioneStatisticheService gestioneStatisticheService) {
        this.gestioneStatisticheService = gestioneStatisticheService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<StatisticheResponse> analyzeStatistics(@Valid @RequestBody AnalyzeStatisticsRequest request) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/export")
    public ResponseEntity<Void> exportStatistics(@Valid @RequestBody ExportStatisticsRequest request) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

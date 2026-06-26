package com.smartmobility.controller;

import com.smartmobility.dto.request.AnalyzeStatisticsRequest;
import com.smartmobility.dto.request.ExportStatisticsRequest;
import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.dto.response.StatisticheResponse;
import com.smartmobility.security.SecurityHelper;
import com.smartmobility.service.GestioneStatisticheService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
public class GestioneStatisticheController {

    private final GestioneStatisticheService gestioneStatisticheService;
    private final SecurityHelper securityHelper;

    public GestioneStatisticheController(GestioneStatisticheService gestioneStatisticheService, SecurityHelper securityHelper) {
        this.gestioneStatisticheService = gestioneStatisticheService;
        this.securityHelper = securityHelper;
    }

    @PostMapping("/analyze")
    public ResponseEntity<StatisticheResponse> analyzeStatistics(@Valid @RequestBody AnalyzeStatisticsRequest request,
                                                                  @RequestHeader("Authorization") String authHeader) {
        securityHelper.requireAuth(authHeader);
        StatisticheResponse stats = gestioneStatisticheService.analisiTratte(
                request.getDataInizio(), request.getDataFine());
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/fleet")
    public ResponseEntity<Map<String, Object>> getFleetAnalysis(@RequestHeader("Authorization") String authHeader) {
        securityHelper.requireAuth(authHeader);
        List<MezzoResponse> mezzi = gestioneStatisticheService.analisiStatoFlotta();
        Map<String, Long> stats = gestioneStatisticheService.getStatisticheFlotta();
        Map<String, Object> response = new HashMap<>();
        response.put("veicoli", mezzi);
        response.put("statistiche", stats);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/export")
    public ResponseEntity<byte[]> exportStatistics(@Valid @RequestBody ExportStatisticsRequest request,
                                                    @RequestHeader("Authorization") String authHeader) {
        securityHelper.requireAuth(authHeader);
        String csv = gestioneStatisticheService.generaFileStatistiche(request.getCorse());
        byte[] csvBytes = csv.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        String filename = "statistiche_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".csv";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csvBytes);
    }
}

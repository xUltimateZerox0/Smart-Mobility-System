package com.smartmobility.controller;

import com.smartmobility.dto.AnalisiTratteRequest;
import com.smartmobility.dto.StatisticheDTO;
import com.smartmobility.service.StatisticheService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/statistiche")
public class GestioneStatisticheController {

    private final StatisticheService statisticheService;

    public GestioneStatisticheController(StatisticheService statisticheService) {
        this.statisticheService = statisticheService;
    }

    @PostMapping("/tratte")
    public ResponseEntity<StatisticheDTO> analisiTratte(@RequestBody AnalisiTratteRequest request) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/genera/{idCorsa}")
    public ResponseEntity<Void> generaFileStatistiche(@PathVariable Long idCorsa) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

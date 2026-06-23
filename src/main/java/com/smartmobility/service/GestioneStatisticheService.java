package com.smartmobility.service;

import com.smartmobility.dto.response.CorsaResponse;
import com.smartmobility.dto.response.StatisticheResponse;

import java.util.List;

public interface GestioneStatisticheService {
    StatisticheResponse analisiTratte(String dataInizio, String dataFine);
    void generaFileStatistiche(List<CorsaResponse> corse);
}

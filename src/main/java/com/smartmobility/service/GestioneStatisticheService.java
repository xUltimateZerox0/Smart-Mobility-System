package com.smartmobility.service;

import com.smartmobility.dto.response.CorsaResponse;
import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.dto.response.StatisticheResponse;

import java.util.List;
import java.util.Map;

public interface GestioneStatisticheService {
    StatisticheResponse analisiTratte(String dataInizio, String dataFine);
    String generaFileStatistiche(List<CorsaResponse> corse);
    List<MezzoResponse> analisiStatoFlotta();
    Map<String, Long> getStatisticheFlotta();
}

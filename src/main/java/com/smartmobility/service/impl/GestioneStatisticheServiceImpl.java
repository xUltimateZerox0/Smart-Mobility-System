package com.smartmobility.service.impl;

import com.smartmobility.dto.response.CorsaResponse;
import com.smartmobility.dto.response.StatisticheResponse;
import com.smartmobility.service.GestioneStatisticheService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GestioneStatisticheServiceImpl implements GestioneStatisticheService {

    @Override
    public StatisticheResponse analisiTratte(String dataInizio, String dataFine) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public void generaFileStatistiche(List<CorsaResponse> corse) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

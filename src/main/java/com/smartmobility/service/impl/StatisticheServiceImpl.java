package com.smartmobility.service.impl;

import com.smartmobility.dto.StatisticheDTO;
import com.smartmobility.repository.CorsaRepository;
import com.smartmobility.service.StatisticheService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class StatisticheServiceImpl implements StatisticheService {

    private final CorsaRepository corsaRepository;

    public StatisticheServiceImpl(CorsaRepository corsaRepository) {
        this.corsaRepository = corsaRepository;
    }

    @Override
    public StatisticheDTO analisiTratte(LocalDate dataInizio, LocalDate dataFine) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public void generaFileStatistiche(Long idCorsa) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

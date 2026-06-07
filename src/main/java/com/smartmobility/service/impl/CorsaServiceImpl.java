package com.smartmobility.service.impl;

import com.smartmobility.dto.CorsaDTO;
import com.smartmobility.dto.StatisticheDTO;
import com.smartmobility.repository.CorsaRepository;
import com.smartmobility.service.CorsaService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class CorsaServiceImpl implements CorsaService {

    private final CorsaRepository corsaRepository;

    public CorsaServiceImpl(CorsaRepository corsaRepository) {
        this.corsaRepository = corsaRepository;
    }

    @Override
    public CorsaDTO avviaCorsa() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public CorsaDTO terminaCorsa(Long idCorsa) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public boolean controllaDisponibilita(String parametro) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public Float aggiornaStima(Long idCorsa) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public boolean sospensioneCorsa() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public boolean fineCorsa(Long idCorsa) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public List<CorsaDTO> getCorseByPeriodo(LocalDate dataInizio, LocalDate dataFine) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public StatisticheDTO analisiTratte(LocalDate dataInizio, LocalDate dataFine) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

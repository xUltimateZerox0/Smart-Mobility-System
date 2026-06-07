package com.smartmobility.service;

import com.smartmobility.dto.CorsaDTO;
import com.smartmobility.dto.StatisticheDTO;

import java.time.LocalDate;
import java.util.List;

public interface CorsaService {

    CorsaDTO avviaCorsa();

    CorsaDTO terminaCorsa(Long idCorsa);

    boolean controllaDisponibilita(String parametro);

    Float aggiornaStima(Long idCorsa);

    boolean sospensioneCorsa();

    boolean fineCorsa(Long idCorsa);

    List<CorsaDTO> getCorseByPeriodo(LocalDate dataInizio, LocalDate dataFine);

    StatisticheDTO analisiTratte(LocalDate dataInizio, LocalDate dataFine);
}

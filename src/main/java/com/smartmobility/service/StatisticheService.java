package com.smartmobility.service;

import com.smartmobility.dto.StatisticheDTO;

import java.time.LocalDate;

public interface StatisticheService {

    StatisticheDTO analisiTratte(LocalDate dataInizio, LocalDate dataFine);

    void generaFileStatistiche(Long idCorsa);
}

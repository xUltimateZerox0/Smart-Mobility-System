package com.smartmobility.dto;

import java.time.LocalDate;

public record AnalisiTratteRequest(
    LocalDate dataInizio,
    LocalDate dataFine
) { }

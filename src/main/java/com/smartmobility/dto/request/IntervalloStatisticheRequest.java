package com.smartmobility.dto.request;

import java.time.LocalDate;

public record IntervalloStatisticheRequest(
    LocalDate dataInizio,
    LocalDate dataFine
) {}

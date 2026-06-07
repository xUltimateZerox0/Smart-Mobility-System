package com.smartmobility.dto;

import java.time.LocalDate;

public record DatiCartaRequest(
    Long idUtente,
    String numCarta,
    LocalDate dsCarta,
    Integer cvv,
    String intestatarioCarta
) { }

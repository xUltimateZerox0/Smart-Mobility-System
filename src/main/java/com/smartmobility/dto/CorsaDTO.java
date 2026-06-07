package com.smartmobility.dto;

import java.time.LocalTime;

public record CorsaDTO(
    Long idCorsa,
    Float costo,
    LocalTime orarioInizio,
    LocalTime orarioFine,
    String coordinatePartenza,
    String coordinateArrivo,
    Long idMezzo
) { }

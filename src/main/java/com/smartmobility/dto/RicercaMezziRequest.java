package com.smartmobility.dto;

public record RicercaMezziRequest(
    String coordinateUtente,
    Float raggio
) { }

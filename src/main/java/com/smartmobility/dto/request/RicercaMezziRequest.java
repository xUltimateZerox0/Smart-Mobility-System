package com.smartmobility.dto.request;

public record RicercaMezziRequest(
    String coordinateUtente,
    float raggio
) {}

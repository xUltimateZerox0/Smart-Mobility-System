package com.smartmobility.dto.request;

public record AvviaCorsaRequest(
    Long idMezzo,
    Long idUtente,
    String qrCode
) {}

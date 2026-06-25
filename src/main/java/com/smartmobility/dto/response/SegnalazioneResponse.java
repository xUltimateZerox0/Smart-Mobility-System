package com.smartmobility.dto.response;

public record SegnalazioneResponse(
    Long idSegnalazione,
    Long idMezzo,
    String stato,
    String ora,
    String data,
    String motivazione
) {}

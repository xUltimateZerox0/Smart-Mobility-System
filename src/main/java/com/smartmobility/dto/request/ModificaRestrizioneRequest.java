package com.smartmobility.dto.request;

public record ModificaRestrizioneRequest(
    Long idArea,
    String tipoRestrizione,
    String noteRestrizione,
    String zona
) {}

package com.smartmobility.dto.request;

import jakarta.validation.constraints.NotNull;

public record PrenotazioneRequest(
    @NotNull
    Long idMezzo,
    @NotNull
    Long idUtente,
    String orarioInizio
) {}

package com.smartmobility.dto;

public record UtenteDTO(
    Long id,
    String email,
    String nomeUtente,
    String cognomeUtente,
    String telefono,
    Integer numMezziPrenotati,
    String reportUtente
) { }

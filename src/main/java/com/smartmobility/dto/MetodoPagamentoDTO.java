package com.smartmobility.dto;

public record MetodoPagamentoDTO(
    Long id,
    String numCarta,
    String intestatarioCarta,
    Long idUtente
) { }

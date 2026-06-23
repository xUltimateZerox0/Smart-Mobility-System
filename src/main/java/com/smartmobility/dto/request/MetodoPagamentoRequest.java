package com.smartmobility.dto.request;

public record MetodoPagamentoRequest(
    String numCarta,
    String dsCarta,
    String cvv,
    String intestatarioCarta
) {}

package com.smartmobility.dto;

import java.time.LocalDate;

public record PagamentoRequest(
    Long idUtente,
    Long idMetodoPagamento,
    Float costo
) { }

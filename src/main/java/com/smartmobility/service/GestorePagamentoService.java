package com.smartmobility.service;

import com.smartmobility.dto.response.MetodoPagamentoResponse;

import java.util.List;

public interface GestorePagamentoService {
    boolean pagamentoCorsa(Long idUtente, Long idMetodoPagamento, Double costo);
    boolean elaboraDatiCarta(Long idUtente, String numCarta, String dsCarta, String cvv, String intestatarioCarta);
    List<MetodoPagamentoResponse> recuperaMetodiSalvati(Long idUtente);
}

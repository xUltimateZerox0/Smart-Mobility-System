package com.smartmobility.service;

import com.smartmobility.dto.MetodoPagamentoDTO;

public interface MetodoPagamentoService {

    boolean pagamentoCorsa(Long idUtente, Long idMetodoPagamento, Float costo);

    boolean elaboraDatiCarta(Long idUtente, String numCarta, String dsCarta, Integer cvv, String intestatarioCarta);
}

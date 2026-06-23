package com.smartmobility.integration;

import org.springframework.stereotype.Service;

@Service
public class GatewayPagamentoServiceImpl implements GatewayPagamentoService {

    @Override
    public boolean effettuaPagamento(Long idMetodoPagamento, Long idCorsa) {
        return true;
    }

    @Override
    public boolean convalidaCarta(String numCarta, String dsCarta, String cvv, String intestatarioCarta) {
        return numCarta != null && numCarta.length() >= 13
                && cvv != null && cvv.length() >= 3
                && intestatarioCarta != null && !intestatarioCarta.isBlank();
    }
}

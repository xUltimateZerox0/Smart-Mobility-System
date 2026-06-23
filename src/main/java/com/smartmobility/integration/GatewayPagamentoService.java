package com.smartmobility.integration;

public interface GatewayPagamentoService {
    boolean effettuaPagamento(Long idMetodoPagamento, Long idCorsa);
    boolean convalidaCarta(String numCarta, String dsCarta, String cvv, String intestatarioCarta);
}

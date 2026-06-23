package com.smartmobility.service.impl;

import com.smartmobility.dto.response.MetodoPagamentoResponse;
import com.smartmobility.service.GestorePagamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GestorePagamentoServiceImpl implements GestorePagamentoService {

    @Override
    public boolean pagamentoCorsa(Long idUtente, Long idMetodoPagamento, Double costo) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public boolean elaboraDatiCarta(Long idUtente, String numCarta, String dsCarta, String cvv, String intestatarioCarta) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public List<MetodoPagamentoResponse> recuperaMetodiSalvati() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

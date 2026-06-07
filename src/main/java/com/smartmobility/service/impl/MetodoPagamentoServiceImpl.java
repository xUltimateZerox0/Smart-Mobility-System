package com.smartmobility.service.impl;

import com.smartmobility.repository.MetodoPagamentoRepository;
import com.smartmobility.service.MetodoPagamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MetodoPagamentoServiceImpl implements MetodoPagamentoService {

    private final MetodoPagamentoRepository metodoPagamentoRepository;

    public MetodoPagamentoServiceImpl(MetodoPagamentoRepository metodoPagamentoRepository) {
        this.metodoPagamentoRepository = metodoPagamentoRepository;
    }

    @Override
    public boolean pagamentoCorsa(Long idUtente, Long idMetodoPagamento, Float costo) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public boolean elaboraDatiCarta(Long idUtente, String numCarta, String dsCarta, Integer cvv, String intestatarioCarta) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

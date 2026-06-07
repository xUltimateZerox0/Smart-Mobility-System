package com.smartmobility.service.impl;

import com.smartmobility.repository.UtenteRepository;
import com.smartmobility.service.UtenteService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UtenteServiceImpl implements UtenteService {

    private final UtenteRepository utenteRepository;

    public UtenteServiceImpl(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    @Override
    public boolean gestioneUtente(Long idUtente) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public String cercaReport(Long idUtente) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

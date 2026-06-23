package com.smartmobility.service.impl;

import com.smartmobility.service.GestioneUtentiService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class GestioneUtentiServiceImpl implements GestioneUtentiService {

    @Override
    public String cercaReport(Long idUtente) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public boolean gestioneUtente(Long idUtente) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public void azioneCorrettiva(Long idUtente, String azione) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

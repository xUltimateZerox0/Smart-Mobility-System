package com.smartmobility.service.impl;

import com.smartmobility.repository.SegnalazioneRepository;
import com.smartmobility.service.SegnalazioneService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SegnalazioneServiceImpl implements SegnalazioneService {

    private final SegnalazioneRepository segnalazioneRepository;

    public SegnalazioneServiceImpl(SegnalazioneRepository segnalazioneRepository) {
        this.segnalazioneRepository = segnalazioneRepository;
    }

    @Override
    public void creaSegnalazione(Long idMezzo) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

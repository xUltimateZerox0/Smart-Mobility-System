package com.smartmobility.service.impl;

import com.smartmobility.dto.PrenotazioneDTO;
import com.smartmobility.repository.PrenotazioneRepository;
import com.smartmobility.service.PrenotazioneService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PrenotazioneServiceImpl implements PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;

    public PrenotazioneServiceImpl(PrenotazioneRepository prenotazioneRepository) {
        this.prenotazioneRepository = prenotazioneRepository;
    }

    @Override
    public boolean inviaRichiestaPrenotazione() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public List<PrenotazioneDTO> richiediLista() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public boolean annullaPrenotazione() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public void gestisciTimeout() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public void notificaScadenzaTempo(Long idPrenotazione) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

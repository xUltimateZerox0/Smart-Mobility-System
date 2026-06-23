package com.smartmobility.service.impl;

import com.smartmobility.dto.response.PrenotazioneResponse;
import com.smartmobility.service.GestionePrenotazioneService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GestionePrenotazioneServiceImpl implements GestionePrenotazioneService {

    @Override
    public void inviaRichiestaPrenotazione() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public List<PrenotazioneResponse> richiediLista() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public boolean annullaPrenotazione(Long idPrenotazione) {
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

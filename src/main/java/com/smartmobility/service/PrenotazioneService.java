package com.smartmobility.service;

import com.smartmobility.dto.PrenotazioneDTO;

import java.util.List;

public interface PrenotazioneService {

    boolean inviaRichiestaPrenotazione();

    List<PrenotazioneDTO> richiediLista();

    boolean annullaPrenotazione();

    void gestisciTimeout();

    void notificaScadenzaTempo(Long idPrenotazione);
}

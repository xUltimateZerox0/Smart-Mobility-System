package com.smartmobility.service;

import com.smartmobility.dto.response.PrenotazioneResponse;

import java.util.List;

public interface GestionePrenotazioneService {
    void inviaRichiestaPrenotazione(Long idMezzo, Long idUtente);
    List<PrenotazioneResponse> richiediLista();
    boolean annullaPrenotazione(Long idPrenotazione);
    void gestisciTimeout();
    void notificaScadenzaTempo(Long idPrenotazione);
}

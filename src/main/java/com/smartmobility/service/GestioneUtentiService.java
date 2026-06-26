package com.smartmobility.service;

import com.smartmobility.dto.response.UtenteResponse;
import java.util.List;

public interface GestioneUtentiService {
    List<UtenteResponse> getElencoUtenti();
    String cercaReport(Long idUtente);
    boolean gestioneUtente(Long idUtente);
    boolean bloccaUtente(Long idUtente);
    boolean sbloccaUtente(Long idUtente);
    boolean disattivaUtente(Long idUtente);
    void cancellaReport(Long idUtente);
    void azioneCorrettiva(Long idUtente, String azione);
}

package com.smartmobility.service;

import com.smartmobility.dto.response.UtenteResponse;
import java.util.List;

public interface GestioneUtentiService {
    List<UtenteResponse> getElencoUtenti();
    String cercaReport(Long idUtente);
    boolean gestioneUtente(Long idUtente);
    void azioneCorrettiva(Long idUtente, String azione);
}

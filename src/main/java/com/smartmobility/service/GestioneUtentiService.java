package com.smartmobility.service;

public interface GestioneUtentiService {
    String cercaReport(Long idUtente);
    boolean gestioneUtente(Long idUtente);
    void azioneCorrettiva(Long idUtente, String azione);
}

package com.smartmobility.service;

import com.smartmobility.dto.response.PercorsoResponse;

public interface GestioneCorsaService {
    Long avviaCorsa(Long idMezzo, Long idUtente);
    void terminaCorsa(Long idCorsa);
    Float aggiornaStima(Long idCorsa);
    boolean sospensioneCorsa(Long idCorsa);
    boolean richiediSblocco(String qrCode);
    PercorsoResponse richiediCalcoloPercorso(String coordinateUtente, String destinazione);
    void acquisisciSceltaMetodo(Long idMetodoPagamento);
    boolean controllaDisponibilita(Long idCorsa);
    boolean controllaDisponibilita(Long idCorsa, String info);
}

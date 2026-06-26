package com.smartmobility.service;

import com.smartmobility.dto.response.CorsaResponse;
import com.smartmobility.dto.response.PercorsoResponse;
import com.smartmobility.dto.response.StimaCorsaResponse;

public interface GestioneCorsaService {
    Long avviaCorsa(Long idMezzo, Long idUtente, String qrCode);
    CorsaResponse getCorsaAttiva(Long idUtente);
    CorsaResponse terminaCorsa(Long idCorsa);
    StimaCorsaResponse aggiornaStima(Long idCorsa);
    boolean sospensioneCorsa(Long idCorsa);
    boolean richiediSblocco(String qrCode);
    PercorsoResponse richiediCalcoloPercorso(String coordinateUtente, String destinazione);
    void acquisisciSceltaMetodo(Long idMetodoPagamento, Long idUtente);
    boolean controllaDisponibilita(Long idCorsa);
    boolean controllaDisponibilita(Long idCorsa, String info);
    CorsaResponse forzaTerminaCorsa(Long idCorsa);
    CorsaResponse terminaCorsaAttivaUtente(Long idUtente);
}

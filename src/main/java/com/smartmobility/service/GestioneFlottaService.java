package com.smartmobility.service;

import com.smartmobility.dto.response.MezzoResponse;

import java.util.List;

public interface GestioneFlottaService {
    boolean analisiStatoFlotta(Long idFlotta);
    boolean bloccaMezzo(Long idMezzo);
    boolean avviaManutenzione(Long idFlotta);
    List<MezzoResponse> getCondizioniMezzi(Long idFlotta);
}

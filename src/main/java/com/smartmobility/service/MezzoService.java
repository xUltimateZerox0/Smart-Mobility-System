package com.smartmobility.service;

import com.smartmobility.dto.MezzoDTO;

import java.util.List;

public interface MezzoService {

    MezzoDTO visualizzaSpecifiche(Long idMezzo);

    List<MezzoDTO> getCondizioniMezzi(Long idFlotta);

    List<MezzoDTO> visualizzaMezziVicini(String coordinateUtente, Float raggio);
}

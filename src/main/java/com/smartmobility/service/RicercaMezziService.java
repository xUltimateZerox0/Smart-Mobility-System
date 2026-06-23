package com.smartmobility.service;

import com.smartmobility.dto.response.MezzoResponse;

import java.util.List;

public interface RicercaMezziService {
    List<MezzoResponse> visualizzaMezziVicini(String coordinateUtente, float raggio);
    MezzoResponse visualizzaSpecifiche(Long idMezzo);
}

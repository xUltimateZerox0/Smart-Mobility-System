package com.smartmobility.service;

import com.smartmobility.dto.response.ZonaGeograficaResponse;

import java.util.List;

public interface GestioneAreeService {
    void aggiornaRestrizione(Long idArea, String tipoRestrizione, String noteRestrizione, String zona);
    boolean analisiConflitti(String zona);
    List<ZonaGeograficaResponse> getZoneGeografiche();
}

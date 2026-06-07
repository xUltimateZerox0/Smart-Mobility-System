package com.smartmobility.service;

import com.smartmobility.dto.ZonaGeograficaDTO;

import java.util.List;

public interface ZonaGeograficaService {

    void aggiornaRestrizioni(Long idArea, String tipoRestrizione, String noteRestrizione, String zonaWkt);

    boolean analisiConflitti(ZonaGeograficaDTO zona);

    List<ZonaGeograficaDTO> getZoneGeografiche();
}

package com.smartmobility.service.impl;

import com.smartmobility.dto.response.ZonaGeograficaResponse;
import com.smartmobility.service.GestioneAreeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GestioneAreeServiceImpl implements GestioneAreeService {

    @Override
    public void aggiornaRestrizione(Long idArea, String tipoRestrizione, String noteRestrizione, String zona) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public boolean analisiConflitti(String zona) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public List<ZonaGeograficaResponse> getZoneGeografiche() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

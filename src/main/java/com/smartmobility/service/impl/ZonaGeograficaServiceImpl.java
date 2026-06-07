package com.smartmobility.service.impl;

import com.smartmobility.dto.ZonaGeograficaDTO;
import com.smartmobility.repository.ZonaGeograficaRepository;
import com.smartmobility.service.ZonaGeograficaService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ZonaGeograficaServiceImpl implements ZonaGeograficaService {

    private final ZonaGeograficaRepository zonaGeograficaRepository;

    public ZonaGeograficaServiceImpl(ZonaGeograficaRepository zonaGeograficaRepository) {
        this.zonaGeograficaRepository = zonaGeograficaRepository;
    }

    @Override
    public void aggiornaRestrizioni(Long idArea, String tipoRestrizione, String noteRestrizione, String zonaWkt) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public boolean analisiConflitti(ZonaGeograficaDTO zona) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public List<ZonaGeograficaDTO> getZoneGeografiche() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

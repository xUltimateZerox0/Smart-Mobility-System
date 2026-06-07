package com.smartmobility.service.impl;

import com.smartmobility.repository.FlottaRepository;
import com.smartmobility.repository.MezzoRepository;
import com.smartmobility.service.FlottaService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FlottaServiceImpl implements FlottaService {

    private final FlottaRepository flottaRepository;
    private final MezzoRepository mezzoRepository;

    public FlottaServiceImpl(FlottaRepository flottaRepository, MezzoRepository mezzoRepository) {
        this.flottaRepository = flottaRepository;
        this.mezzoRepository = mezzoRepository;
    }

    @Override
    public boolean analisiStatoFlotta(Long idFlotta) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public boolean bloccaMezzo(Long idMezzo) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public boolean avviaManutenzione(Long idFlotta) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

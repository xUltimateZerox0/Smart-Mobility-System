package com.smartmobility.service.impl;

import com.smartmobility.dto.MezzoDTO;
import com.smartmobility.repository.MezzoRepository;
import com.smartmobility.service.MezzoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MezzoServiceImpl implements MezzoService {

    private final MezzoRepository mezzoRepository;

    public MezzoServiceImpl(MezzoRepository mezzoRepository) {
        this.mezzoRepository = mezzoRepository;
    }

    @Override
    public MezzoDTO visualizzaSpecifiche(Long idMezzo) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public List<MezzoDTO> getCondizioniMezzi(Long idFlotta) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @Override
    public List<MezzoDTO> visualizzaMezziVicini(String coordinateUtente, Float raggio) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

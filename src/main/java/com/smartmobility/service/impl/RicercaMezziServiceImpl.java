package com.smartmobility.service.impl;

import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.model.Mezzo;
import com.smartmobility.model.enums.StatoMezzo;
import com.smartmobility.repository.MezzoRepository;
import com.smartmobility.service.RicercaMezziService;
import com.smartmobility.util.GeoUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RicercaMezziServiceImpl implements RicercaMezziService {

    private final MezzoRepository mezzoRepository;

    public RicercaMezziServiceImpl(MezzoRepository mezzoRepository) {
        this.mezzoRepository = mezzoRepository;
    }

    @Override
    public List<MezzoResponse> visualizzaMezziVicini(String coordinateUtente, float raggio) {
        List<Mezzo> mezzi = mezzoRepository.findByStato(StatoMezzo.disponibile);

        return mezzi.stream()
                .filter(m -> isWithinRadius(m.getCoordinateMezzo(), coordinateUtente, raggio))
                .map(this::toMezzoResponse)
                .toList();
    }

    @Override
    public MezzoResponse visualizzaSpecifiche(Long idMezzo) {
        Mezzo mezzo = mezzoRepository.findById(idMezzo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mezzo non trovato"));
        return toMezzoResponse(mezzo);
    }

    @Override
    public boolean verificaDisponibilita(Long idMezzo) {
        Mezzo mezzo = mezzoRepository.findById(idMezzo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mezzo non trovato"));
        return mezzo.getStato() == StatoMezzo.disponibile || mezzo.getStato() == StatoMezzo.prenotato;
    }

    private MezzoResponse toMezzoResponse(Mezzo mezzo) {
        double[] coords = GeoUtils.parseCoordinates(mezzo.getCoordinateMezzo());
        String tempoDisp = mezzo.getTempoDisponibilita() != null ? mezzo.getTempoDisponibilita().toString() : null;
        return new MezzoResponse(
                mezzo.getIdMezzo(),
                mezzo.getTipo(),
                mezzo.getStato().name(),
                coords[0],
                coords[1],
                (double) mezzo.getAutonomia(),
                (double) mezzo.getCostoOrario(),
                "MEZZO-" + mezzo.getIdMezzo(),
                tempoDisp,
                mezzo.getCondizione(),
                mezzo.getIdFlotta()
        );
    }

    private boolean isWithinRadius(String coord1, String coord2, float raggio) {
        double[] c1 = GeoUtils.parseCoordinates(coord1);
        double[] c2 = GeoUtils.parseCoordinates(coord2);
        double distance = GeoUtils.haversine(c1[0], c1[1], c2[0], c2[1]);
        return distance <= raggio;
    }


}

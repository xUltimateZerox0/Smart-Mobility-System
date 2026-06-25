package com.smartmobility.service.impl;

import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.model.Mezzo;
import com.smartmobility.model.enums.StatoMezzo;
import com.smartmobility.repository.MezzoRepository;
import com.smartmobility.service.RicercaMezziService;
import com.smartmobility.util.GeoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RicercaMezziServiceImpl implements RicercaMezziService {

    private static final float RAGGIO_INIZIALE = 2.0f;
    private static final float RAGGIO_ESPANSO = 5.0f;
    private static final Logger log = LoggerFactory.getLogger(RicercaMezziServiceImpl.class);

    private final MezzoRepository mezzoRepository;

    public RicercaMezziServiceImpl(MezzoRepository mezzoRepository) {
        this.mezzoRepository = mezzoRepository;
    }

    @Override
    public List<MezzoResponse> visualizzaMezziVicini(String coordinateUtente, float raggio) {
        if (raggio != RAGGIO_INIZIALE && raggio != RAGGIO_ESPANSO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Raggio non consentito. Usa " + (int)RAGGIO_INIZIALE + " o " + (int)RAGGIO_ESPANSO + " km");
        }

        try {
            GeoUtils.parseCoordinates(coordinateUtente);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coordinate utente non valide: " + e.getMessage());
        }

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
        double lat = 0, lon = 0;
        try {
            double[] coords = GeoUtils.parseCoordinates(mezzo.getCoordinateMezzo());
            lat = coords[0];
            lon = coords[1];
        } catch (IllegalArgumentException e) {
            log.warn("Coordinate non valide per mezzo {}: {}", mezzo.getIdMezzo(), e.getMessage());
        }
        String tempoDisp = mezzo.getTempoDisponibilita() != null ? mezzo.getTempoDisponibilita().toString() : null;
        return new MezzoResponse(
                mezzo.getIdMezzo(),
                mezzo.getTipo(),
                mezzo.getStato().name(),
                lat,
                lon,
                (double) mezzo.getAutonomia(),
                (double) mezzo.getCostoOrario(),
                "MEZZO-" + mezzo.getIdMezzo(),
                tempoDisp,
                mezzo.getCondizione(),
                mezzo.getIdFlotta()
        );
    }

    private boolean isWithinRadius(String coord1, String coord2, float raggio) {
        try {
            double[] c1 = GeoUtils.parseCoordinates(coord1);
            double[] c2 = GeoUtils.parseCoordinates(coord2);
            double distance = GeoUtils.haversine(c1[0], c1[1], c2[0], c2[1]);
            return distance <= raggio;
        } catch (IllegalArgumentException e) {
            log.warn("Errore calcolo distanza: {}", e.getMessage());
            return false;
        }
    }


}

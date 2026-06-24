package com.smartmobility.service.impl;

import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.model.Mezzo;
import com.smartmobility.model.enums.StatoMezzo;
import com.smartmobility.repository.MezzoRepository;
import com.smartmobility.service.RicercaMezziService;
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
        double[] coords = parseCoordinates(mezzo.getCoordinateMezzo());
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
        double[] c1 = parseCoordinates(coord1);
        double[] c2 = parseCoordinates(coord2);
        double distance = haversine(c1[0], c1[1], c2[0], c2[1]);
        return distance <= raggio;
    }

    private double[] parseCoordinates(String coords) {
        try {
            String[] parts = coords.split(",");
            double lat = Double.parseDouble(parts[0].trim());
            double lon = Double.parseDouble(parts[1].trim());
            return new double[]{lat, lon};
        } catch (Exception e) {
            return new double[]{0.0, 0.0};
        }
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}

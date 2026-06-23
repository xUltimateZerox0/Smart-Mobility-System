package com.smartmobility.service.impl;

import com.smartmobility.dto.response.ZonaGeograficaResponse;
import com.smartmobility.model.ZonaGeografica;
import com.smartmobility.model.enums.TipoRestrizione;
import com.smartmobility.repository.ZonaGeograficaRepository;
import com.smartmobility.service.GestioneAreeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GestioneAreeServiceImpl implements GestioneAreeService {

    private final ZonaGeograficaRepository zonaGeograficaRepository;

    public GestioneAreeServiceImpl(ZonaGeograficaRepository zonaGeograficaRepository) {
        this.zonaGeograficaRepository = zonaGeograficaRepository;
    }

    @Override
    @Transactional
    public void aggiornaRestrizione(Long idArea, String tipoRestrizione, String noteRestrizione, String zona) {
        ZonaGeografica zonaGeo = zonaGeograficaRepository.findById(idArea)
                .orElseGet(() -> {
                    ZonaGeografica nuova = new ZonaGeografica();
                    nuova.setIdArea(idArea);
                    return nuova;
                });

        try {
            zonaGeo.setTipoRestrizione(TipoRestrizione.valueOf(tipoRestrizione));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo restrizione non valido: " + tipoRestrizione);
        }

        zonaGeo.setNoteRestrizione(noteRestrizione);
        zonaGeo.setZona(zona);
        zonaGeograficaRepository.save(zonaGeo);
    }

    @Override
    public boolean analisiConflitti(String zona) {
        List<ZonaGeografica> tutte = zonaGeograficaRepository.findAll();

        for (ZonaGeografica esistente : tutte) {
            if (esistente.getZona() != null && areZonesOverlapping(esistente.getZona(), zona)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<ZonaGeograficaResponse> getZoneGeografiche() {
        return zonaGeograficaRepository.findAll().stream()
                .map(this::toZonaGeograficaResponse)
                .toList();
    }

    private boolean areZonesOverlapping(String zona1, String zona2) {
        try {
            String[] coords1 = zona1.split(";");
            String[] coords2 = zona2.split(";");

            double[] c1Min = new double[]{Double.MAX_VALUE, Double.MAX_VALUE};
            double[] c1Max = new double[]{Double.MIN_VALUE, Double.MIN_VALUE};
            double[] c2Min = new double[]{Double.MAX_VALUE, Double.MAX_VALUE};
            double[] c2Max = new double[]{Double.MIN_VALUE, Double.MIN_VALUE};

            for (String coord : coords1) {
                String[] parts = coord.trim().split(",");
                double lat = Double.parseDouble(parts[0].trim());
                double lon = Double.parseDouble(parts[1].trim());
                c1Min[0] = Math.min(c1Min[0], lat);
                c1Min[1] = Math.min(c1Min[1], lon);
                c1Max[0] = Math.max(c1Max[0], lat);
                c1Max[1] = Math.max(c1Max[1], lon);
            }

            for (String coord : coords2) {
                String[] parts = coord.trim().split(",");
                double lat = Double.parseDouble(parts[0].trim());
                double lon = Double.parseDouble(parts[1].trim());
                c2Min[0] = Math.min(c2Min[0], lat);
                c2Min[1] = Math.min(c2Min[1], lon);
                c2Max[0] = Math.max(c2Max[0], lat);
                c2Max[1] = Math.max(c2Max[1], lon);
            }

            return c1Min[0] <= c2Max[0] && c1Max[0] >= c2Min[0]
                    && c1Min[1] <= c2Max[1] && c1Max[1] >= c2Min[1];
        } catch (Exception e) {
            return false;
        }
    }

    private ZonaGeograficaResponse toZonaGeograficaResponse(ZonaGeografica z) {
        return new ZonaGeograficaResponse(
                z.getIdArea(),
                z.getTipoRestrizione() != null ? z.getTipoRestrizione().name() : null,
                z.getTipoRestrizione() != null ? z.getTipoRestrizione().name() : null,
                z.getNoteRestrizione(),
                z.getZona()
        );
    }
}

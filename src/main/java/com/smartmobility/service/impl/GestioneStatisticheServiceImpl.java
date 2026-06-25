package com.smartmobility.service.impl;

import com.smartmobility.dto.response.CorsaResponse;
import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.dto.response.StatisticheResponse;
import com.smartmobility.model.Corsa;
import com.smartmobility.model.Mezzo;
import com.smartmobility.model.enums.StatoMezzo;
import com.smartmobility.repository.CorsaRepository;
import com.smartmobility.repository.MezzoRepository;
import com.smartmobility.service.GestioneStatisticheService;
import com.smartmobility.util.GeoUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GestioneStatisticheServiceImpl implements GestioneStatisticheService {

    private final CorsaRepository corsaRepository;
    private final MezzoRepository mezzoRepository;

    public GestioneStatisticheServiceImpl(CorsaRepository corsaRepository, MezzoRepository mezzoRepository) {
        this.corsaRepository = corsaRepository;
        this.mezzoRepository = mezzoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public StatisticheResponse analisiTratte(String dataInizio, String dataFine) {
        LocalDateTime start;
        LocalDateTime end;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            start = LocalDateTime.parse(dataInizio, formatter);
            end = LocalDateTime.parse(dataFine, formatter);
        } catch (Exception e1) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                start = LocalDateTime.parse(dataInizio, formatter);
                end = LocalDateTime.parse(dataFine, formatter);
            } catch (Exception e2) {
                try {
                    start = LocalDateTime.parse(dataInizio + "T00:00:00");
                    end = LocalDateTime.parse(dataFine + "T23:59:59");
                } catch (Exception e3) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato data non valido. Usa yyyy-MM-dd");
                }
            }
        }

        List<Corsa> corse = corsaRepository.findByDataRange(start, end);

        long totalCorse = corse.size();
        double totalKm = 0;
        double totalRicavo = 0;
        double totalDurataMin = 0;

        for (Corsa corsa : corse) {
            totalRicavo += corsa.getCosto();

            if (corsa.getOrarioInizio() != null && corsa.getOrarioFine() != null) {
                totalDurataMin += ChronoUnit.MINUTES.between(corsa.getOrarioInizio(), corsa.getOrarioFine());
            }

            if (corsa.getCoordinatePartenza() != null && corsa.getCoordinateArrivo() != null) {
                totalKm += estimateDistance(corsa.getCoordinatePartenza(), corsa.getCoordinateArrivo());
            }
        }

        double mediaDurata = totalCorse > 0 ? totalDurataMin / totalCorse : 0;

        Map<String, Object> dettagli = new HashMap<>();
        dettagli.put("periodo", dataInizio + " - " + dataFine);
        dettagli.put("totaleMinuti", totalDurataMin);
        dettagli.put("numeroCorse", totalCorse);

        return new StatisticheResponse(totalCorse, totalKm, totalRicavo, mediaDurata, dettagli);
    }

    @Override
    public String generaFileStatistiche(List<CorsaResponse> corse) {
        StringBuilder sb = new StringBuilder();
        sb.append("idCorsa,idUtente,idMezzo,dataInizio,dataFine,costo,distanza,stato\n");
        for (CorsaResponse corsa : corse) {
            sb.append(String.format("%d,%d,%d,%s,%s,%.2f,%.2f,%s%n",
                    corsa.getId(), corsa.getIdUtente(), corsa.getIdMezzo(),
                    corsa.getDataInizio(), corsa.getDataFine(),
                    corsa.getCosto(), corsa.getDistanza(), corsa.getStato()));
        }
        return sb.toString();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MezzoResponse> analisiStatoFlotta() {
        List<Mezzo> mezzi = mezzoRepository.findAll();
        List<MezzoResponse> responses = new ArrayList<>();
        for (Mezzo m : mezzi) {
            MezzoResponse r = new MezzoResponse();
            r.setId(m.getIdMezzo());
            r.setTipo(m.getTipo());
            r.setStato(m.getStato() != null ? m.getStato().name() : null);
            r.setAutonomia((double) m.getAutonomia());
            r.setTariffa((double) m.getCostoOrario());
            r.setCondizione(m.getCondizione());
            r.setIdFlotta(m.getIdFlotta());
            if (m.getCoordinateMezzo() != null) {
                String[] coords = m.getCoordinateMezzo().split(",");
                if (coords.length >= 2) {
                    r.setLatitudine(Double.parseDouble(coords[0].trim()));
                    r.setLongitudine(Double.parseDouble(coords[1].trim()));
                }
            }
            responses.add(r);
        }
        return responses;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> getStatisticheFlotta() {
        List<Mezzo> mezzi = mezzoRepository.findAll();
        Map<String, Long> stats = new HashMap<>();
        stats.put("disponibile", mezzi.stream().filter(m -> m.getStato() == StatoMezzo.disponibile).count());
        stats.put("prenotato", mezzi.stream().filter(m -> m.getStato() == StatoMezzo.prenotato).count());
        stats.put("in_uso", mezzi.stream().filter(m -> m.getStato() == StatoMezzo.in_uso).count());
        stats.put("manutenzione", mezzi.stream().filter(m -> m.getStato() == StatoMezzo.manutenzione).count());
        stats.put("bloccato", mezzi.stream().filter(m -> m.getStato() == StatoMezzo.bloccato).count());
        stats.put("sospeso", mezzi.stream().filter(m -> m.getStato() == StatoMezzo.sospeso).count());
        stats.put("totale", (long) mezzi.size());
        return stats;
    }

    private double estimateDistance(String coord1, String coord2) {
        double[] c1 = GeoUtils.parseCoordinates(coord1);
        double[] c2 = GeoUtils.parseCoordinates(coord2);
        return GeoUtils.haversine(c1[0], c1[1], c2[0], c2[1]);
    }
}

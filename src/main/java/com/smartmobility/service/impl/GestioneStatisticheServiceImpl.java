package com.smartmobility.service.impl;

import com.smartmobility.dto.response.CorsaResponse;
import com.smartmobility.dto.response.StatisticheResponse;
import com.smartmobility.model.Corsa;
import com.smartmobility.repository.CorsaRepository;
import com.smartmobility.service.GestioneStatisticheService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GestioneStatisticheServiceImpl implements GestioneStatisticheService {

    private final CorsaRepository corsaRepository;

    public GestioneStatisticheServiceImpl(CorsaRepository corsaRepository) {
        this.corsaRepository = corsaRepository;
    }

    @Override
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
    public void generaFileStatistiche(List<CorsaResponse> corse) {
        String dirPath = System.getProperty("java.io.tmpdir") + "/smart-mobility-stats";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String filename = "statistiche_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".csv";
        File file = new File(dir, filename);

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println("idCorsa,idUtente,idMezzo,dataInizio,dataFine,costo,distanza,stato");
            for (CorsaResponse corsa : corse) {
                writer.printf("%d,%d,%d,%s,%s,%.2f,%.2f,%s%n",
                        corsa.getId(), corsa.getIdUtente(), corsa.getIdMezzo(),
                        corsa.getDataInizio(), corsa.getDataFine(),
                        corsa.getCosto(), corsa.getDistanza(), corsa.getStato());
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore nella generazione del file statistiche");
        }
    }

    private double estimateDistance(String coord1, String coord2) {
        double[] c1 = parseCoordinates(coord1);
        double[] c2 = parseCoordinates(coord2);
        return haversine(c1[0], c1[1], c2[0], c2[1]);
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

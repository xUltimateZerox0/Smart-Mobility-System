package com.smartmobility.service.impl;

import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.integration.MezzoIoTService;
import com.smartmobility.model.Mezzo;
import com.smartmobility.model.Segnalazione;
import com.smartmobility.model.enums.StatoMezzo;
import com.smartmobility.model.enums.StatoSegnalazione;
import com.smartmobility.repository.MezzoRepository;
import com.smartmobility.repository.SegnalazioneRepository;
import com.smartmobility.service.GestioneFlottaService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class GestioneFlottaServiceImpl implements GestioneFlottaService {

    private final MezzoRepository mezzoRepository;
    private final SegnalazioneRepository segnalazioneRepository;
    private final MezzoIoTService mezzoIoTService;

    public GestioneFlottaServiceImpl(MezzoRepository mezzoRepository,
                                      SegnalazioneRepository segnalazioneRepository,
                                      MezzoIoTService mezzoIoTService) {
        this.mezzoRepository = mezzoRepository;
        this.segnalazioneRepository = segnalazioneRepository;
        this.mezzoIoTService = mezzoIoTService;
    }

    @Override
    @Transactional
    public boolean analisiStatoFlotta(Long idFlotta) {
        List<Mezzo> mezzi = mezzoRepository.findByIdFlotta(String.valueOf(idFlotta));
        boolean foundIssue = false;

        for (Mezzo mezzo : mezzi) {
            if ("danneggiato".equalsIgnoreCase(mezzo.getCondizione())
                    || mezzo.getAutonomia() <= 0
                    || "manutenzione_necessaria".equalsIgnoreCase(mezzo.getCondizione())) {

                Segnalazione segnalazione = new Segnalazione();
                segnalazione.setMezzo(mezzo);
                segnalazione.setStato(StatoSegnalazione.aperta);
                segnalazione.setData(LocalDate.now());
                segnalazione.setOra(LocalTime.now());
                segnalazioneRepository.save(segnalazione);

                mezzo.setStato(StatoMezzo.manutenzione);
                mezzoRepository.save(mezzo);

                foundIssue = true;
            }
        }

        return foundIssue;
    }

    @Override
    @Transactional
    public boolean bloccaMezzo(Long idMezzo) {
        Mezzo mezzo = mezzoRepository.findById(idMezzo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mezzo non trovato"));

        if (!mezzoIoTService.bloccoMezzoFisico(idMezzo)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Blocco remoto fallito");
        }

        mezzo.setStato(StatoMezzo.bloccato);
        mezzoRepository.save(mezzo);

        return true;
    }

    @Override
    @Transactional
    public boolean avviaManutenzione(Long idFlotta) {
        List<Mezzo> mezzi = mezzoRepository.findByIdFlotta(String.valueOf(idFlotta));

        if (mezzi.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessun mezzo trovato per la flotta");
        }

        for (Mezzo mezzo : mezzi) {
            mezzo.setStato(StatoMezzo.manutenzione);

            Segnalazione segnalazione = new Segnalazione();
            segnalazione.setMezzo(mezzo);
            segnalazione.setStato(StatoSegnalazione.aperta);
            segnalazione.setData(LocalDate.now());
            segnalazione.setOra(LocalTime.now());
            segnalazioneRepository.save(segnalazione);

            mezzoRepository.save(mezzo);
        }

        return true;
    }

    @Override
    public List<MezzoResponse> getCondizioniMezzi(Long idFlotta) {
        List<Mezzo> mezzi = mezzoRepository.findByIdFlotta(String.valueOf(idFlotta));

        return mezzi.stream()
                .map(this::toMezzoResponse)
                .toList();
    }

    private MezzoResponse toMezzoResponse(Mezzo mezzo) {
        double[] coords = parseCoordinates(mezzo.getCoordinateMezzo());
        return new MezzoResponse(
                mezzo.getIdMezzo(),
                mezzo.getTipo(),
                mezzo.getStato().name(),
                coords[0],
                coords[1],
                (double) mezzo.getAutonomia(),
                (double) mezzo.getCostoOrario(),
                mezzo.getIdFlotta()
        );
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
}

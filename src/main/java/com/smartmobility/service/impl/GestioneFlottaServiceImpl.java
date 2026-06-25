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
import com.smartmobility.util.GeoUtils;
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
            if (mezzo.getCondizione() != null && mezzo.getCondizione().toLowerCase().startsWith("danneggi")
                    || mezzo.getAutonomia() <= 0
                    || "manutenzione_necessaria".equalsIgnoreCase(mezzo.getCondizione())
                    || "manutenzione".equalsIgnoreCase(mezzo.getCondizione())) {

                Segnalazione segnalazione = new Segnalazione();
                segnalazione.setMezzo(mezzo);
                segnalazione.setStato(StatoSegnalazione.aperta);
                segnalazione.setData(LocalDate.now());
                segnalazione.setOra(LocalTime.now());
                segnalazioneRepository.save(segnalazione);

                mezzo.setStato(StatoMezzo.manutenzione);
                mezzo.setCondizione("manutenzione");
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
    public boolean sbloccaMezzo(Long idMezzo) {
        Mezzo mezzo = mezzoRepository.findById(idMezzo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mezzo non trovato"));

        if (!mezzoIoTService.sbloccoMezzoFisico(idMezzo)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Sblocco remoto fallito");
        }

        mezzo.setStato(StatoMezzo.disponibile);
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
            avviaManutenzioneVeicolo(mezzo.getIdMezzo());
        }

        return true;
    }

    @Override
    @Transactional
    public boolean avviaManutenzioneVeicolo(Long idMezzo) {
        Mezzo mezzo = mezzoRepository.findById(idMezzo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mezzo non trovato"));

        mezzo.setStato(StatoMezzo.manutenzione);
        mezzo.setCondizione("manutenzione");

        Segnalazione segnalazione = new Segnalazione();
        segnalazione.setMezzo(mezzo);
        segnalazione.setStato(StatoSegnalazione.aperta);
        segnalazione.setData(LocalDate.now());
        segnalazione.setOra(LocalTime.now());
        segnalazioneRepository.save(segnalazione);

        mezzoRepository.save(mezzo);

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


}

package com.smartmobility.service.impl;

import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.dto.response.SegnalazioneResponse;
import com.smartmobility.integration.MezzoIoTService;
import com.smartmobility.model.Corsa;
import com.smartmobility.model.Mezzo;
import com.smartmobility.model.Segnalazione;
import com.smartmobility.model.enums.StatoMezzo;
import com.smartmobility.model.enums.StatoSegnalazione;
import com.smartmobility.repository.CorsaRepository;
import com.smartmobility.repository.MezzoRepository;
import com.smartmobility.repository.SegnalazioneRepository;
import com.smartmobility.service.GestioneCorsaService;
import com.smartmobility.service.GestioneFlottaService;
import com.smartmobility.util.GeoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Service
public class GestioneFlottaServiceImpl implements GestioneFlottaService {

    private static final Logger log = LoggerFactory.getLogger(GestioneFlottaServiceImpl.class);
    private static final String MEZZO_NON_TROVATO = "Mezzo non trovato";

    private final MezzoRepository mezzoRepository;
    private final SegnalazioneRepository segnalazioneRepository;
    private final MezzoIoTService mezzoIoTService;
    private final CorsaRepository corsaRepository;
    private final GestioneCorsaService gestioneCorsaService;

    private GestioneFlottaService selfProxy;

    public GestioneFlottaServiceImpl(MezzoRepository mezzoRepository,
                                      SegnalazioneRepository segnalazioneRepository,
                                      MezzoIoTService mezzoIoTService,
                                      CorsaRepository corsaRepository,
                                      GestioneCorsaService gestioneCorsaService) {
        this.mezzoRepository = mezzoRepository;
        this.segnalazioneRepository = segnalazioneRepository;
        this.mezzoIoTService = mezzoIoTService;
        this.corsaRepository = corsaRepository;
        this.gestioneCorsaService = gestioneCorsaService;
    }

    @Autowired
    @Lazy
    public void setSelfProxy(GestioneFlottaService selfProxy) {
        this.selfProxy = selfProxy;
    }

    @Override
    @Transactional
    public boolean analisiStatoFlotta(Long idFlotta) {
        List<Mezzo> mezzi = mezzoRepository.findByIdFlotta(String.valueOf(idFlotta));
        boolean foundIssue = false;

        for (Mezzo mezzo : mezzi) {
            if (mezzo.getStato() == StatoMezzo.manutenzione) {
                continue;
            }

            String motivazione = null;
            if (mezzo.getCondizione() != null && mezzo.getCondizione().toLowerCase().startsWith("danneggi")) {
                motivazione = "Veicolo danneggiato: " + mezzo.getCondizione();
            } else if (mezzo.getAutonomia() <= 0) {
                motivazione = "Autonomia esaurita";
            } else if ("manutenzione_necessaria".equalsIgnoreCase(mezzo.getCondizione())) {
                motivazione = "Manutenzione necessaria: " + mezzo.getCondizione();
            }

            if (motivazione != null) {
                Segnalazione segnalazione = new Segnalazione();
                segnalazione.setMezzo(mezzo);
                segnalazione.setStato(StatoSegnalazione.aperta);
                segnalazione.setData(LocalDate.now());
                segnalazione.setOra(LocalTime.now());
                segnalazione.setMotivazione(motivazione);
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, MEZZO_NON_TROVATO));

        List<Corsa> corseAttive = corsaRepository.findByMezzoIdAndOrarioFineIsNull(idMezzo);
        for (Corsa corsa : corseAttive) {
            try {
                gestioneCorsaService.forzaTerminaCorsa(corsa.getIdCorsa());
                log.info("Corsa {} terminata forzatamente per blocco veicolo {}", corsa.getIdCorsa(), idMezzo);
            } catch (Exception e) {
                log.error("Errore terminazione corsa {} per blocco veicolo: {}", corsa.getIdCorsa(), e.getMessage());
            }
        }

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, MEZZO_NON_TROVATO));

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
            selfProxy.avviaManutenzioneVeicolo(mezzo.getIdMezzo());
        }

        return true;
    }

    @Override
    @Transactional
    @SuppressWarnings("java:S3516")
    public boolean avviaManutenzioneVeicolo(Long idMezzo) {
        Mezzo mezzo = mezzoRepository.findById(idMezzo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, MEZZO_NON_TROVATO));

        if (mezzo.getStato() == StatoMezzo.manutenzione) {
            return true;
        }

        mezzo.setStato(StatoMezzo.manutenzione);
        mezzo.setCondizione("manutenzione");

        Segnalazione segnalazione = new Segnalazione();
        segnalazione.setMezzo(mezzo);
        segnalazione.setStato(StatoSegnalazione.aperta);
        segnalazione.setData(LocalDate.now());
        segnalazione.setOra(LocalTime.now());
        segnalazione.setMotivazione("Manutenzione manuale");
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

    @Override
    public List<SegnalazioneResponse> getSegnalazioni() {
        return segnalazioneRepository.findAll().stream()
                .map(this::toSegnalazioneResponse)
                .toList();
    }

    @Override
    public List<SegnalazioneResponse> getSegnalazioniByStato(String stato) {
        try {
            StatoSegnalazione statoEnum = StatoSegnalazione.valueOf(stato);
            return segnalazioneRepository.findByStato(statoEnum).stream()
                    .map(this::toSegnalazioneResponse)
                    .toList();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stato segnalazione non valido: " + stato);
        }
    }

    private SegnalazioneResponse toSegnalazioneResponse(Segnalazione s) {
        return new SegnalazioneResponse(
                s.getIdSegnalazione(),
                s.getMezzo() != null ? s.getMezzo().getIdMezzo() : null,
                s.getStato() != null ? s.getStato().name() : null,
                s.getOra() != null ? s.getOra().toString() : null,
                s.getData() != null ? s.getData().toString() : null,
                s.getMotivazione()
        );
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
                mezzo.getAutonomia(),
                mezzo.getCostoOrario(),
                "MEZZO-" + mezzo.getIdMezzo(),
                tempoDisp,
                mezzo.getCondizione(),
                mezzo.getIdFlotta()
        );
    }


}

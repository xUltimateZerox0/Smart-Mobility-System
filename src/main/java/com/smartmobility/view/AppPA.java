package com.smartmobility.view;

import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.dto.response.ZonaGeograficaResponse;
import com.smartmobility.service.GestioneAreeService;
import com.smartmobility.service.GestioneAutenticazioneService;
import com.smartmobility.service.GestioneFlottaService;
import com.smartmobility.service.GestioneStatisticheService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@SuppressWarnings("unused")
public class AppPA {

    private static final Logger LOG = Logger.getLogger(AppPA.class.getName());

    private final GestioneFlottaService gestioneFlottaService;
    private final GestioneStatisticheService gestioneStatisticheService;
    private final GestioneAreeService gestioneAreeService;
    private final GestioneAutenticazioneService gestioneAutenticazioneService;
    private Long idPA;
    private Long idSessionePA;

    public AppPA(GestioneFlottaService gestioneFlottaService, GestioneStatisticheService gestioneStatisticheService, GestioneAreeService gestioneAreeService, GestioneAutenticazioneService gestioneAutenticazioneService) {
        this.gestioneFlottaService = gestioneFlottaService;
        this.gestioneStatisticheService = gestioneStatisticheService;
        this.gestioneAreeService = gestioneAreeService;
        this.gestioneAutenticazioneService = gestioneAutenticazioneService;
    }

    public void mostraErrore(String msg) {
        LOG.log(Level.SEVERE, "ERRORE: {0}", msg);
    }

    public void mostraStatistiche(Object statistiche) {
        LOG.log(Level.INFO, "Statistiche: {0}", statistiche);
    }

    public void visualizzaMezzi(List<MezzoResponse> mezzi) {
        LOG.log(Level.INFO, "Mezzi: {0}", mezzi.size());
    }

    public void mostraMappa(List<ZonaGeograficaResponse> zone) {
        LOG.log(Level.INFO, "Zone: {0}", zone.size());
    }

    public void selezionaIntervallo(LocalDate dataInizio, LocalDate dataFine) {
        gestioneStatisticheService.analisiTratte(dataInizio.toString(), dataFine.toString());
    }

    public void richiedeStatoFlotta(String idFlotta) {
        try {
            gestioneFlottaService.getCondizioniMezzi(Long.parseLong(idFlotta));
        } catch (NumberFormatException e) {
            LOG.log(Level.SEVERE, "ERRORE: formato idFlotta non valido - {0}", idFlotta);
        }
    }

    public void avviaIntervento(String idFlotta) {
        try {
            long id = Long.parseLong(idFlotta);
            gestioneFlottaService.analisiStatoFlotta(id);
        } catch (NumberFormatException e) {
            LOG.log(Level.SEVERE, "ERRORE: formato idFlotta non valido - {0}", idFlotta);
        }
    }

    public void selezionaMappa() {
        LOG.info("Mappa selezionata");
    }

    public void modificaRestrizioni(ZonaGeograficaResponse zona) {
        LOG.log(Level.INFO, "Modifica restrizioni per zona: {0}", zona.getId());
    }

    public void confermaSovrascrittura(Long idArea, String tipoRestrizione, String noteRestrizione) {
        gestioneAreeService.aggiornaRestrizione(idArea, tipoRestrizione, noteRestrizione, null);
    }

    public void rifiutaSovrascrittura() {
        LOG.info("Sovrascrittura rifiutata");
    }

    public void richiestaLogout(String email) {
        gestioneAutenticazioneService.inviaRichiestaLogout(email);
    }

    public void mostraSuccesso(String msg) {
        LOG.log(Level.INFO, "SUCCESSO: {0}", msg);
    }

    public Long getIdPA() {
        return idPA;
    }

    public void setIdPA(Long idPA) {
        this.idPA = idPA;
    }

    public Long getIdSessionePA() {
        return idSessionePA;
    }

    public void setIdSessionePA(Long idSessionePA) {
        this.idSessionePA = idSessionePA;
    }
}

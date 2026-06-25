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

@Component
@SuppressWarnings("unused")
public class AppPA {

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
        System.err.println("ERRORE: " + msg);
    }

    public void mostraStatistiche(Object statistiche) {
        System.out.println("Statistiche: " + statistiche);
    }

    public void visualizzaMezzi(List<MezzoResponse> mezzi) {
        System.out.println("Mezzi: " + mezzi.size());
    }

    public void mostraMappa(List<ZonaGeograficaResponse> zone) {
        System.out.println("Zone: " + zone.size());
    }

    public void selezionaIntervallo(LocalDate dataInizio, LocalDate dataFine) {
        gestioneStatisticheService.analisiTratte(dataInizio.toString(), dataFine.toString());
    }

    public void richiedeStatoFlotta(String idFlotta) {
        try {
            gestioneFlottaService.getCondizioniMezzi(Long.parseLong(idFlotta));
        } catch (NumberFormatException e) {
            System.err.println("ERRORE: formato idFlotta non valido - " + idFlotta);
        }
    }

    public void avviaIntervento(String idFlotta) {
        try {
            long id = Long.parseLong(idFlotta);
            gestioneFlottaService.analisiStatoFlotta(id);
        } catch (NumberFormatException e) {
            System.err.println("ERRORE: formato idFlotta non valido - " + idFlotta);
        }
    }

    public void selezionaMappa() {
        System.out.println("Mappa selezionata");
    }

    public void modificaRestrizioni(ZonaGeograficaResponse zona) {
        System.out.println("Modifica restrizioni per zona: " + zona.getId());
    }

    public void confermaSovrascrittura(Long idArea, String tipoRestrizione, String noteRestrizione) {
        gestioneAreeService.aggiornaRestrizione(idArea, tipoRestrizione, noteRestrizione, null);
    }

    public void rifiutaSovrascrittura() {
        System.out.println("Sovrascrittura rifiutata");
    }

    public void richiestaLogout(String email) {
        gestioneAutenticazioneService.inviaRichiestaLogout(email);
    }

    public void mostraSuccesso(String msg) {
        System.out.println("SUCCESSO: " + msg);
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

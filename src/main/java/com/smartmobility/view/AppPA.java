package com.smartmobility.view;

import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.dto.response.ZonaGeograficaResponse;
import com.smartmobility.service.GestioneAreeService;
import com.smartmobility.service.GestioneAutenticazioneService;
import com.smartmobility.service.GestioneFlottaService;
import com.smartmobility.service.GestioneStatisticheService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Component
public class AppPA {

    private final GestioneFlottaService gestioneFlottaService;
    private final GestioneStatisticheService gestioneStatisticheService;
    private final GestioneAreeService gestioneAreeService;
    private final GestioneAutenticazioneService gestioneAutenticazioneService;

    public AppPA(GestioneFlottaService gestioneFlottaService, GestioneStatisticheService gestioneStatisticheService, GestioneAreeService gestioneAreeService, GestioneAutenticazioneService gestioneAutenticazioneService) {
        this.gestioneFlottaService = gestioneFlottaService;
        this.gestioneStatisticheService = gestioneStatisticheService;
        this.gestioneAreeService = gestioneAreeService;
        this.gestioneAutenticazioneService = gestioneAutenticazioneService;
    }

    public void mostraErrore(String msg) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void mostraStatistiche(Object statistiche) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void visualizzaMezzi(List<MezzoResponse> mezzi) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void mostraMappa(List<ZonaGeograficaResponse> zone) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void selezionaIntervallo(LocalDate dataInizio, LocalDate dataFine) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void richiedeStatoFlotta(String idFlotta) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void avviaIntervento(String idFlotta) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void selezionaMappa() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void modificaRestrizioni(ZonaGeograficaResponse zona) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void confermaSovrascrittura(Long idArea, String tipoRestrizione, String noteRestrizione) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void rifiutaSovrascrittura() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void richiestaLogout(String email) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void mostraSuccesso(String msg) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

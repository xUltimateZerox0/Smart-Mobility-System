package com.smartmobility.view;

import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.service.GestioneAutenticazioneService;
import com.smartmobility.service.GestioneFlottaService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class AppOperatoreTecnico {

    private final GestioneFlottaService gestioneFlottaService;
    private final GestioneAutenticazioneService gestioneAutenticazioneService;

    public AppOperatoreTecnico(GestioneFlottaService gestioneFlottaService, GestioneAutenticazioneService gestioneAutenticazioneService) {
        this.gestioneFlottaService = gestioneFlottaService;
        this.gestioneAutenticazioneService = gestioneAutenticazioneService;
    }

    public void mostraSuccesso(String msg) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void mostraErrore(String msg) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void visualizzaMezzi(List<MezzoResponse> mezzi) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void richiedeStatoFlotta(String idFlotta) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void selezionaVeicolo(Long idMezzo) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    public void richiestaLogout(String email) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

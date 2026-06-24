package com.smartmobility.view;

import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.service.GestioneAutenticazioneService;
import com.smartmobility.service.GestioneFlottaService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@SuppressWarnings("unused")
public class AppOperatoreTecnico {

    private final GestioneFlottaService gestioneFlottaService;
    private final GestioneAutenticazioneService gestioneAutenticazioneService;
    private Long idOperatoreTecnico;
    private Long idSessioneOperatoreTecnico;

    public AppOperatoreTecnico(GestioneFlottaService gestioneFlottaService, GestioneAutenticazioneService gestioneAutenticazioneService) {
        this.gestioneFlottaService = gestioneFlottaService;
        this.gestioneAutenticazioneService = gestioneAutenticazioneService;
    }

    public void mostraSuccesso(String msg) {
        System.out.println("SUCCESSO: " + msg);
    }

    public void mostraErrore(String msg) {
        System.err.println("ERRORE: " + msg);
    }

    public void visualizzaMezzi(List<MezzoResponse> mezzi) {
        System.out.println("Mezzi in flotta: " + mezzi.size());
    }

    public void richiedeStatoFlotta(String idFlotta) {
        gestioneFlottaService.getCondizioniMezzi(Long.parseLong(idFlotta));
    }

    public void selezionaVeicolo(Long idMezzo) {
        System.out.println("Veicolo selezionato: " + idMezzo);
    }

    public void richiestaLogout(String email) {
        gestioneAutenticazioneService.inviaRichiestaLogout(email);
    }

    public Long getIdOperatoreTecnico() {
        return idOperatoreTecnico;
    }

    public void setIdOperatoreTecnico(Long idOperatoreTecnico) {
        this.idOperatoreTecnico = idOperatoreTecnico;
    }

    public Long getIdSessioneOperatoreTecnico() {
        return idSessioneOperatoreTecnico;
    }

    public void setIdSessioneOperatoreTecnico(Long idSessioneOperatoreTecnico) {
        this.idSessioneOperatoreTecnico = idSessioneOperatoreTecnico;
    }
}

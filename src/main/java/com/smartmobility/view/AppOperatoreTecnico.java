package com.smartmobility.view;

import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.service.GestioneAutenticazioneService;
import com.smartmobility.service.GestioneFlottaService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@SuppressWarnings("unused")
public class AppOperatoreTecnico {

    private static final Logger LOG = Logger.getLogger(AppOperatoreTecnico.class.getName());

    private final GestioneFlottaService gestioneFlottaService;
    private final GestioneAutenticazioneService gestioneAutenticazioneService;
    private Long idOperatoreTecnico;
    private Long idSessioneOperatoreTecnico;

    public AppOperatoreTecnico(GestioneFlottaService gestioneFlottaService, GestioneAutenticazioneService gestioneAutenticazioneService) {
        this.gestioneFlottaService = gestioneFlottaService;
        this.gestioneAutenticazioneService = gestioneAutenticazioneService;
    }

    public void mostraSuccesso(String msg) {
        LOG.log(Level.INFO, "SUCCESSO: {0}", msg);
    }

    public void mostraErrore(String msg) {
        LOG.log(Level.SEVERE, "ERRORE: {0}", msg);
    }

    public void visualizzaMezzi(List<MezzoResponse> mezzi) {
        LOG.log(Level.INFO, "Mezzi in flotta: {0}", mezzi.size());
    }

    public void richiedeStatoFlotta(String idFlotta) {
        gestioneFlottaService.getCondizioniMezzi(Long.parseLong(idFlotta));
    }

    public void selezionaVeicolo(Long idMezzo) {
        LOG.log(Level.INFO, "Veicolo selezionato: {0}", idMezzo);
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

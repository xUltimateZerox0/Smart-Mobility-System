package com.smartmobility.service.impl;

import com.smartmobility.dto.response.UtenteResponse;
import com.smartmobility.model.Corsa;
import com.smartmobility.model.Utente;
import com.smartmobility.model.enums.StatoUtente;
import com.smartmobility.repository.CorsaRepository;
import com.smartmobility.repository.UtenteRepository;
import com.smartmobility.service.GestioneCorsaService;
import com.smartmobility.service.GestioneUtentiService;
import com.smartmobility.service.SessionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GestioneUtentiServiceImpl implements GestioneUtentiService {

    private static final Logger log = LoggerFactory.getLogger(GestioneUtentiServiceImpl.class);
    private static final String UTENTE_NON_TROVATO = "Utente non trovato";

    private final UtenteRepository utenteRepository;
    private final SessionRegistry sessionRegistry;
    private final CorsaRepository corsaRepository;
    private final GestioneCorsaService gestioneCorsaService;

    public GestioneUtentiServiceImpl(UtenteRepository utenteRepository,
                                      SessionRegistry sessionRegistry,
                                      CorsaRepository corsaRepository,
                                      GestioneCorsaService gestioneCorsaService) {
        this.utenteRepository = utenteRepository;
        this.sessionRegistry = sessionRegistry;
        this.corsaRepository = corsaRepository;
        this.gestioneCorsaService = gestioneCorsaService;
    }

    @Override
    public List<UtenteResponse> getElencoUtenti() {
        return utenteRepository.findAll().stream()
                .map(u -> new UtenteResponse(
                        u.getId(),
                        u.getIdUtente(),
                        u.getNomeUtente(),
                        u.getCognomeUtente(),
                        u.getEmail(),
                        u.getStatoUtente() != null ? u.getStatoUtente().name() : null
                ))
                .toList();
    }

    @Override
    public String cercaReport(Long idUtente) {
        Utente utente = utenteRepository.findByIdUtente(idUtente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, UTENTE_NON_TROVATO));
        return utente.getReportUtente();
    }

    @Override
    @Transactional
    public boolean gestioneUtente(Long idUtente) {
        Utente utente = utenteRepository.findByIdUtente(idUtente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, UTENTE_NON_TROVATO));

        if (utente.getStatoUtente() == StatoUtente.attivo) {
            utente.setStatoUtente(StatoUtente.sospeso);
            sessionRegistry.invalidateByEmail(utente.getEmail());

            List<Corsa> corseAttive = corsaRepository.findByIdUtenteAndOrarioFineIsNull(idUtente);
            for (Corsa corsa : corseAttive) {
                try {
                    gestioneCorsaService.forzaTerminaCorsa(corsa.getIdCorsa());
                    log.info("Corsa {} terminata forzatamente per moderazione utente {}", corsa.getIdCorsa(), idUtente);
                } catch (Exception e) {
                    log.error("Errore terminazione corsa {} per moderazione: {}", corsa.getIdCorsa(), e.getMessage());
                }
            }
        } else if (utente.getStatoUtente() == StatoUtente.sospeso) {
            utente.setStatoUtente(StatoUtente.attivo);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Utente disattivato non modificabile");
        }

        utenteRepository.save(utente);
        return true;
    }

    @Override
    @Transactional
    public boolean bloccaUtente(Long idUtente) {
        Utente utente = utenteRepository.findByIdUtente(idUtente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, UTENTE_NON_TROVATO));
        utente.setStatoUtente(StatoUtente.sospeso);
        utenteRepository.save(utente);
        sessionRegistry.invalidateByEmail(utente.getEmail());

        List<Corsa> corseAttive = corsaRepository.findByIdUtenteAndOrarioFineIsNull(idUtente);
        for (Corsa corsa : corseAttive) {
            try {
                gestioneCorsaService.forzaTerminaCorsa(corsa.getIdCorsa());
                log.info("Corsa {} terminata forzatamente per blocco utente {}", corsa.getIdCorsa(), idUtente);
            } catch (Exception e) {
                log.error("Errore durante terminazione forzata corsa {} per utente {}: {}", corsa.getIdCorsa(), idUtente, e.getMessage());
            }
        }

        return true;
    }

    @Override
    @Transactional
    public boolean sbloccaUtente(Long idUtente) {
        Utente utente = utenteRepository.findByIdUtente(idUtente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, UTENTE_NON_TROVATO));
        utente.setStatoUtente(StatoUtente.attivo);
        utenteRepository.save(utente);
        return true;
    }

    @Override
    @Transactional
    public boolean disattivaUtente(Long idUtente) {
        Utente utente = utenteRepository.findByIdUtente(idUtente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, UTENTE_NON_TROVATO));
        utente.setStatoUtente(StatoUtente.disattivato);
        utenteRepository.save(utente);
        sessionRegistry.invalidateByEmail(utente.getEmail());
        return true;
    }

    @Override
    @Transactional
    public void cancellaReport(Long idUtente) {
        Utente utente = utenteRepository.findByIdUtente(idUtente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, UTENTE_NON_TROVATO));
        utente.setReportUtente(null);
        utenteRepository.save(utente);
    }

    @Override
    @Transactional
    public void azioneCorrettiva(Long idUtente, String azione) {
        Utente utente = utenteRepository.findByIdUtente(idUtente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, UTENTE_NON_TROVATO));

        String existing = utente.getReportUtente();
        String updated = existing == null || existing.isBlank()
                ? azione
                : existing + "\n" + azione;
        utente.setReportUtente(updated);
        utenteRepository.save(utente);
    }
}

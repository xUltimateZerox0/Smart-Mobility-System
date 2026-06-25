package com.smartmobility.service.impl;

import com.smartmobility.dto.response.UtenteResponse;
import com.smartmobility.model.Utente;
import com.smartmobility.model.enums.StatoUtente;
import com.smartmobility.repository.UtenteRepository;
import com.smartmobility.service.GestioneUtentiService;
import com.smartmobility.service.SessionRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GestioneUtentiServiceImpl implements GestioneUtentiService {

    private final UtenteRepository utenteRepository;
    private final SessionRegistry sessionRegistry;

    public GestioneUtentiServiceImpl(UtenteRepository utenteRepository, SessionRegistry sessionRegistry) {
        this.utenteRepository = utenteRepository;
        this.sessionRegistry = sessionRegistry;
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));
        return utente.getReportUtente();
    }

    @Override
    @Transactional
    public boolean gestioneUtente(Long idUtente) {
        Utente utente = utenteRepository.findByIdUtente(idUtente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));

        if (utente.getStatoUtente() == StatoUtente.attivo) {
            utente.setStatoUtente(StatoUtente.sospeso);
            sessionRegistry.invalidateByEmail(utente.getEmail());
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));
        utente.setStatoUtente(StatoUtente.sospeso);
        utenteRepository.save(utente);
        sessionRegistry.invalidateByEmail(utente.getEmail());
        return true;
    }

    @Override
    @Transactional
    public boolean sbloccaUtente(Long idUtente) {
        Utente utente = utenteRepository.findByIdUtente(idUtente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));
        utente.setStatoUtente(StatoUtente.attivo);
        utenteRepository.save(utente);
        return true;
    }

    @Override
    @Transactional
    public boolean disattivaUtente(Long idUtente) {
        Utente utente = utenteRepository.findByIdUtente(idUtente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));
        utente.setStatoUtente(StatoUtente.disattivato);
        utenteRepository.save(utente);
        sessionRegistry.invalidateByEmail(utente.getEmail());
        return true;
    }

    @Override
    @Transactional
    public void cancellaReport(Long idUtente) {
        Utente utente = utenteRepository.findByIdUtente(idUtente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));
        utente.setReportUtente(null);
        utenteRepository.save(utente);
    }

    @Override
    @Transactional
    public void azioneCorrettiva(Long idUtente, String azione) {
        Utente utente = utenteRepository.findByIdUtente(idUtente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));

        String existing = utente.getReportUtente();
        String updated = existing == null || existing.isBlank()
                ? azione
                : existing + "\n" + azione;
        utente.setReportUtente(updated);
        utenteRepository.save(utente);
    }
}

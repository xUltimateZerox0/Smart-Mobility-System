package com.smartmobility.service.impl;

import com.smartmobility.dto.response.AuthResponse;
import com.smartmobility.model.Attore;
import com.smartmobility.model.Operatore;
import com.smartmobility.model.Utente;
import com.smartmobility.model.enums.RuoloAttore;
import com.smartmobility.model.enums.StatoUtente;
import com.smartmobility.repository.AttoreRepository;
import com.smartmobility.repository.UtenteRepository;
import com.smartmobility.service.GestioneAutenticazioneService;
import com.smartmobility.service.SessionRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Service
public class GestioneAutenticazioneServiceImpl implements GestioneAutenticazioneService {

    private final AttoreRepository attoreRepository;
    private final UtenteRepository utenteRepository;
    private final SessionRegistry sessionRegistry;

    public GestioneAutenticazioneServiceImpl(AttoreRepository attoreRepository,
                                              UtenteRepository utenteRepository,
                                              SessionRegistry sessionRegistry) {
        this.attoreRepository = attoreRepository;
        this.utenteRepository = utenteRepository;
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    public AuthResponse invioCredenziali(String email, String password) {
        Attore attore = attoreRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenziali non valide"));

        if (!hashPassword(password).equals(attore.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenziali non valide");
        }

        String token = sessionRegistry.createSession(attore);

        Long idUtente = null;
        String tipo = null;
        if (attore instanceof Utente u) {
            idUtente = u.getIdUtente();
        } else if (attore instanceof Operatore o) {
            tipo = o.getTipo().name();
        }

        return new AuthResponse(token, attore.getEmail(), attore.getRuolo().name(), idUtente, tipo);
    }

    @Override
    public AuthResponse verificaValidita(String nome, String cognome, String email, String password, String datanascita) {
        if (attoreRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email già registrata");
        }

        Utente utente = new Utente();
        utente.setNomeUtente(nome);
        utente.setCognomeUtente(cognome);
        utente.setEmail(email);
        utente.setPassword(hashPassword(password));
        utente.setRuolo(RuoloAttore.Utente);
        utente.setStatoUtente(StatoUtente.attivo);
        utente.setReportUtente("");
        utente.setNumMezziPrenotati(0);
        utente.setCoordinateUtente("0.0,0.0,0.0");

        utente = utenteRepository.save(utente);
        utente.setIdUtente(utente.getId());

        String token = sessionRegistry.createSession(utente);

        return new AuthResponse(token, utente.getEmail(), utente.getRuolo().name(), utente.getIdUtente(), null);
    }

    @Override
    public void inviaRichiestaLogout(String email) {
        sessionRegistry.invalidateByEmail(email);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
}

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

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestioneAutenticazioneServiceImpl implements GestioneAutenticazioneService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
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

        if (!passwordEncoder.matches(password, attore.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenziali non valide");
        }

        if (attore instanceof Utente u && u.getStatoUtente() != StatoUtente.attivo) {
            if (u.getStatoUtente() == StatoUtente.sospeso) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account sospeso");
            }
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account disattivato");
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
    @Transactional
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

        utente.setDataNascita(datanascita);

        utente = utenteRepository.save(utente);
        utente.setIdUtente(utente.getId());
        utenteRepository.save(utente);

        String token = sessionRegistry.createSession(utente);

        return new AuthResponse(token, utente.getEmail(), utente.getRuolo().name(), utente.getIdUtente(), null);
    }

    @Override
    public void inviaRichiestaLogout(String email) {
        sessionRegistry.invalidateByEmail(email);
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
}

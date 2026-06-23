package com.smartmobility.config;

import com.smartmobility.model.Attore;
import com.smartmobility.model.Utente;
import com.smartmobility.model.enums.RuoloAttore;
import com.smartmobility.model.enums.StatoUtente;
import com.smartmobility.repository.AttoreRepository;
import com.smartmobility.repository.UtenteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Configuration
@Profile("dev")
public class DataInitializer {

    @Bean
    CommandLineRunner seedUsers(AttoreRepository attoreRepository, UtenteRepository utenteRepository) {
        return args -> {
            if (attoreRepository.findByEmail("test@smartmobility.com").isEmpty()) {
                Utente utente = new Utente();
                utente.setNomeUtente("Mario");
                utente.setCognomeUtente("Rossi");
                utente.setEmail("test@smartmobility.com");
                utente.setPassword(hashPassword("password"));
                utente.setRuolo(RuoloAttore.Utente);
                utente.setStatoUtente(StatoUtente.attivo);
                utente.setReportUtente("");
                utente.setNumMezziPrenotati(0);
                utente.setCoordinateUtente("45.4642,9.1900");
                utente.setIdUtente(0L);
                utente = utenteRepository.save(utente);
                utente.setIdUtente(utente.getId());
                utenteRepository.save(utente);
            }
        };
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

package com.smartmobility.integration;

import com.smartmobility.model.Attore;
import com.smartmobility.model.Utente;
import com.smartmobility.model.enums.RuoloAttore;
import com.smartmobility.model.enums.StatoUtente;
import com.smartmobility.repository.AttoreRepository;
import com.smartmobility.repository.UtenteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AutenticazioneFlowIntegrationTest {

    @Autowired
    private AttoreRepository attoreRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @BeforeEach
    void setUp() {
        utenteRepository.deleteAll();
        attoreRepository.deleteAll();
    }

    @Test
    void registerUser_PersistsAttoreAndUtente() {
        Utente utente = new Utente();
        utente.setNomeUtente("Mario");
        utente.setCognomeUtente("Rossi");
        utente.setEmail("mario.integration@example.com");
        utente.setPassword("hashed-password");
        utente.setRuolo(RuoloAttore.Utente);
        utente.setStatoUtente(StatoUtente.attivo);
        utente.setReportUtente("");
        utente.setNumMezziPrenotati(0);
        utente.setCoordinateUtente("41.9028,12.4964,0.0");

        utente = utenteRepository.save(utente);
        utente.setIdUtente(utente.getId());

        assertNotNull(utente.getId());
        assertNotNull(utente.getIdUtente());

        Attore foundAttore = attoreRepository.findByEmail("mario.integration@example.com").orElse(null);
        assertNotNull(foundAttore);
        assertEquals(RuoloAttore.Utente, foundAttore.getRuolo());
    }

    @Test
    void emailUniquenessConstraint_ThrowsOnDuplicate() {
        Utente utente1 = new Utente();
        utente1.setNomeUtente("Mario");
        utente1.setCognomeUtente("Rossi");
        utente1.setEmail("duplicate@example.com");
        utente1.setPassword("pass1");
        utente1.setRuolo(RuoloAttore.Utente);
        utente1.setStatoUtente(StatoUtente.attivo);
        utente1.setReportUtente("");
        utente1.setNumMezziPrenotati(0);
        utente1.setCoordinateUtente("41.9028,12.4964,0.0");
        utenteRepository.save(utente1);

        Utente utente2 = new Utente();
        utente2.setNomeUtente("Luigi");
        utente2.setCognomeUtente("Verdi");
        utente2.setEmail("duplicate@example.com");
        utente2.setPassword("pass2");
        utente2.setRuolo(RuoloAttore.Utente);
        utente2.setStatoUtente(StatoUtente.attivo);
        utente2.setReportUtente("");
        utente2.setNumMezziPrenotati(0);
        utente2.setCoordinateUtente("41.9028,12.4964,0.0");

        assertThrows(Exception.class, () -> utenteRepository.save(utente2));
    }

    @Test
    void findAttoreByEmail_ReturnsCorrectUser() {
        Utente utente = new Utente();
        utente.setNomeUtente("Test");
        utente.setCognomeUtente("User");
        utente.setEmail("findme@example.com");
        utente.setPassword("pass");
        utente.setRuolo(RuoloAttore.Utente);
        utente.setStatoUtente(StatoUtente.attivo);
        utente.setReportUtente("");
        utente.setNumMezziPrenotati(0);
        utente.setCoordinateUtente("41.9028,12.4964,0.0");
        utenteRepository.save(utente);

        Attore found = attoreRepository.findByEmail("findme@example.com").orElse(null);
        assertNotNull(found);
        assertEquals("findme@example.com", found.getEmail());
    }
}

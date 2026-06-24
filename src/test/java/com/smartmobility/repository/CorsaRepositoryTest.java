package com.smartmobility.repository;

import com.smartmobility.model.Corsa;
import com.smartmobility.model.Mezzo;
import com.smartmobility.model.Utente;
import com.smartmobility.model.enums.StatoMezzo;
import com.smartmobility.model.enums.StatoUtente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CorsaRepositoryTest {

    @Autowired
    private CorsaRepository corsaRepository;

    @Autowired
    private MezzoRepository mezzoRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    private Utente utente;
    private Mezzo mezzo;

    @BeforeEach
    void setUp() {
        utente = new Utente();
        utente.setNomeUtente("Test");
        utente.setCognomeUtente("User");
        utente.setEmail("corsa.repo@example.com");
        utente.setPassword("pass");
        utente.setRuolo(com.smartmobility.model.enums.RuoloAttore.Utente);
        utente.setStatoUtente(StatoUtente.attivo);
        utente.setReportUtente("");
        utente.setNumMezziPrenotati(0);
        utente.setCoordinateUtente("41.9028,12.4964,0.0");
        utente = utenteRepository.save(utente);
        utente.setIdUtente(utente.getId());

        mezzo = new Mezzo();
        mezzo.setTipo("bici");
        mezzo.setStato(StatoMezzo.disponibile);
        mezzo.setCoordinateMezzo("41.9028,12.4964,0.0");
        mezzo.setAutonomia(80.0f);
        mezzo.setCostoOrario(5.0f);
        mezzo.setVelocitaMax(50.0f);
        mezzo.setCondizione("buona");
        mezzo.setIdFlotta("FLOTTA-1");
        mezzo = mezzoRepository.save(mezzo);
    }

    @Test
    void findByUtenteIdAndOrarioFineIsNull_ReturnsActiveRides() {
        Corsa corsa = new Corsa();
        corsa.setUtente(utente);
        corsa.setMezzo(mezzo);
        corsa.setOrarioInizio(LocalDateTime.now());
        corsa.setCosto(0f);
        corsa.setCoordinatePartenza("41.9028,12.4964,0.0");
        corsaRepository.save(corsa);

        List<Corsa> activeRides = corsaRepository.findByUtenteIdAndOrarioFineIsNull(utente.getId());

        assertEquals(1, activeRides.size());
        assertNull(activeRides.get(0).getOrarioFine());
    }

    @Test
    void findByDataRange_ReturnsCorrectRides() {
        Corsa corsa1 = new Corsa();
        corsa1.setUtente(utente);
        corsa1.setMezzo(mezzo);
        corsa1.setOrarioInizio(LocalDateTime.of(2026, 1, 15, 10, 0));
        corsa1.setOrarioFine(LocalDateTime.of(2026, 1, 15, 10, 30));
        corsa1.setCosto(5.0f);
        corsa1.setCoordinatePartenza("41.9028,12.4964,0.0");
        corsa1.setCoordinateArrivo("41.9030,12.4970,0.0");
        corsaRepository.save(corsa1);

        Corsa corsa2 = new Corsa();
        corsa2.setUtente(utente);
        corsa2.setMezzo(mezzo);
        corsa2.setOrarioInizio(LocalDateTime.of(2026, 2, 1, 14, 0));
        corsa2.setOrarioFine(LocalDateTime.of(2026, 2, 1, 15, 0));
        corsa2.setCosto(8.0f);
        corsa2.setCoordinatePartenza("41.9028,12.4964,0.0");
        corsa2.setCoordinateArrivo("41.9040,12.4980,0.0");
        corsaRepository.save(corsa2);

        List<Corsa> janRides = corsaRepository.findByDataRange(
                LocalDateTime.of(2026, 1, 1, 0, 0),
                LocalDateTime.of(2026, 1, 31, 23, 59));

        assertEquals(1, janRides.size());
    }
}

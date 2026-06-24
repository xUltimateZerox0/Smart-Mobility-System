package com.smartmobility.repository;

import com.smartmobility.config.TestDataFactory;
import com.smartmobility.model.Mezzo;
import com.smartmobility.model.Prenotazione;
import com.smartmobility.model.Utente;
import com.smartmobility.model.enums.StatoMezzo;
import com.smartmobility.model.enums.StatoPrenotazione;
import com.smartmobility.model.enums.StatoUtente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class PrenotazioneRepositoryTest {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

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
        utente.setEmail("test.repo@example.com");
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
    void findByStato_ReturnsActiveBookings() {
        Prenotazione p1 = new Prenotazione();
        p1.setUtente(utente);
        p1.setMezzo(mezzo);
        p1.setStato(StatoPrenotazione.attiva);
        p1.setOrarioInizio(LocalTime.now());
        p1.setData(LocalDate.now());
        prenotazioneRepository.save(p1);

        Prenotazione p2 = new Prenotazione();
        p2.setUtente(utente);
        p2.setMezzo(mezzo);
        p2.setStato(StatoPrenotazione.annullata);
        p2.setOrarioInizio(LocalTime.now());
        p2.setData(LocalDate.now());
        prenotazioneRepository.save(p2);

        List<Prenotazione> attive = prenotazioneRepository.findByStato(StatoPrenotazione.attiva);
        List<Prenotazione> annullate = prenotazioneRepository.findByStato(StatoPrenotazione.annullata);

        assertEquals(1, attive.size());
        assertEquals(1, annullate.size());
    }

    @Test
    void findByUtenteIdAndStato_ReturnsUserBookings() {
        Prenotazione p = new Prenotazione();
        p.setUtente(utente);
        p.setMezzo(mezzo);
        p.setStato(StatoPrenotazione.attiva);
        p.setOrarioInizio(LocalTime.now());
        p.setData(LocalDate.now());
        prenotazioneRepository.save(p);

        List<Prenotazione> results = prenotazioneRepository.findByUtenteIdAndStato(utente.getId(), StatoPrenotazione.attiva);

        assertEquals(1, results.size());
    }

    @Test
    void findByMezzoIdAndStato_ReturnsMezzoBookings() {
        Prenotazione p = new Prenotazione();
        p.setUtente(utente);
        p.setMezzo(mezzo);
        p.setStato(StatoPrenotazione.attiva);
        p.setOrarioInizio(LocalTime.now());
        p.setData(LocalDate.now());
        prenotazioneRepository.save(p);

        List<Prenotazione> results = prenotazioneRepository.findByMezzoIdAndStato(mezzo.getIdMezzo(), StatoPrenotazione.attiva);

        assertEquals(1, results.size());
    }
}

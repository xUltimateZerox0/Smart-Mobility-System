package com.smartmobility.service;

import com.smartmobility.config.TestDataFactory;
import com.smartmobility.model.Mezzo;
import com.smartmobility.model.Prenotazione;
import com.smartmobility.model.Utente;
import com.smartmobility.model.enums.StatoMezzo;
import com.smartmobility.model.enums.StatoPrenotazione;
import com.smartmobility.repository.MezzoRepository;
import com.smartmobility.repository.PrenotazioneRepository;
import com.smartmobility.repository.UtenteRepository;
import com.smartmobility.service.impl.GestionePrenotazioneServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestionePrenotazioneServiceImplTest {

    @Mock
    private PrenotazioneRepository prenotazioneRepository;

    @Mock
    private MezzoRepository mezzoRepository;

    @Mock
    private UtenteRepository utenteRepository;

    private GestionePrenotazioneServiceImpl service;

    private Utente utente;
    private Mezzo mezzo;

    @BeforeEach
    void setUp() {
        service = new GestionePrenotazioneServiceImpl(prenotazioneRepository, mezzoRepository, utenteRepository);
        utente = TestDataFactory.createDefaultUtente();
        mezzo = TestDataFactory.createDefaultMezzo();
    }

    @Test
    void inviaRichiestaPrenotazione_WithValidData_CreatesBooking() {
        when(mezzoRepository.findById(1L)).thenReturn(Optional.of(mezzo));
        when(utenteRepository.findByIdUtente(1L)).thenReturn(Optional.of(utente));
        when(prenotazioneRepository.findByUtenteIdAndStato(1L, StatoPrenotazione.attiva)).thenReturn(List.of());

        service.inviaRichiestaPrenotazione(1L, 1L);

        verify(prenotazioneRepository).save(any(Prenotazione.class));
        verify(mezzoRepository).save(any(Mezzo.class));
        assertEquals(StatoMezzo.prenotato, mezzo.getStato());
    }

    @Test
    void inviaRichiestaPrenotazione_WithUnavailableMezzo_ThrowsConflict() {
        mezzo.setStato(StatoMezzo.in_uso);
        when(mezzoRepository.findById(1L)).thenReturn(Optional.of(mezzo));

        assertThrows(ResponseStatusException.class,
                () -> service.inviaRichiestaPrenotazione(1L, 1L));
    }

    @Test
    void inviaRichiestaPrenotazione_WithExistingActiveBooking_ThrowsConflict() {
        when(mezzoRepository.findById(1L)).thenReturn(Optional.of(mezzo));
        when(utenteRepository.findByIdUtente(1L)).thenReturn(Optional.of(utente));
        when(prenotazioneRepository.findByUtenteIdAndStato(1L, StatoPrenotazione.attiva))
                .thenReturn(List.of(new Prenotazione()));

        assertThrows(ResponseStatusException.class,
                () -> service.inviaRichiestaPrenotazione(1L, 1L));
    }

    @Test
    void annullaPrenotazione_WithActiveBooking_CancelsSuccessfully() {
        Prenotazione prenotazione = TestDataFactory.createPrenotazione(1L, utente, mezzo, StatoPrenotazione.attiva, LocalTime.now());

        when(prenotazioneRepository.findById(1L)).thenReturn(Optional.of(prenotazione));

        boolean result = service.annullaPrenotazione(1L);

        assertTrue(result);
        assertEquals(StatoPrenotazione.annullata, prenotazione.getStato());
        verify(prenotazioneRepository).save(prenotazione);
    }

    @Test
    void annullaPrenotazione_WithNonActiveBooking_ThrowsConflict() {
        Prenotazione prenotazione = TestDataFactory.createPrenotazione(1L, utente, mezzo, StatoPrenotazione.scaduta, LocalTime.now());

        when(prenotazioneRepository.findById(1L)).thenReturn(Optional.of(prenotazione));

        assertThrows(ResponseStatusException.class,
                () -> service.annullaPrenotazione(1L));
    }

    @Test
    void gestisciTimeout_WithExpiredBooking_ReleasesMezzo() {
        mezzo.setStato(StatoMezzo.prenotato);
        Prenotazione expiredPrenotazione = TestDataFactory.createPrenotazione(
                1L, utente, mezzo, StatoPrenotazione.attiva, LocalTime.now().minusMinutes(16));

        when(prenotazioneRepository.findByStato(StatoPrenotazione.attiva))
                .thenReturn(List.of(expiredPrenotazione));

        service.gestisciTimeout();

        assertEquals(StatoPrenotazione.scaduta, expiredPrenotazione.getStato());
        assertEquals(StatoMezzo.disponibile, mezzo.getStato());
        verify(prenotazioneRepository).save(expiredPrenotazione);
        verify(mezzoRepository).save(mezzo);
    }

    @Test
    void gestisciTimeout_WithRecentBooking_DoesNotExpire() {
        Prenotazione activePrenotazione = TestDataFactory.createPrenotazione(
                1L, utente, mezzo, StatoPrenotazione.attiva, LocalTime.now().minusMinutes(5));

        when(prenotazioneRepository.findByStato(StatoPrenotazione.attiva))
                .thenReturn(List.of(activePrenotazione));

        service.gestisciTimeout();

        assertEquals(StatoPrenotazione.attiva, activePrenotazione.getStato());
        verify(prenotazioneRepository, never()).save(activePrenotazione);
    }

    @Test
    void notificaScadenzaTempo_WithActiveBooking_TriggersTimeout() {
        Prenotazione prenotazione = TestDataFactory.createPrenotazione(
                1L, utente, mezzo, StatoPrenotazione.attiva, LocalTime.now().minusMinutes(16));

        when(prenotazioneRepository.findById(1L)).thenReturn(Optional.of(prenotazione));
        when(prenotazioneRepository.findByStato(StatoPrenotazione.attiva))
                .thenReturn(List.of(prenotazione));

        service.notificaScadenzaTempo(1L);

        verify(prenotazioneRepository, atLeastOnce()).save(prenotazione);
    }

    @Test
    void inviaRichiestaPrenotazione_WithNonExistentMezzo_ThrowsNotFound() {
        when(mezzoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> service.inviaRichiestaPrenotazione(999L, 1L));
    }
}

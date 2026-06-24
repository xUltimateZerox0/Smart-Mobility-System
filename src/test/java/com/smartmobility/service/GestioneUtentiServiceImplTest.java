package com.smartmobility.service;

import com.smartmobility.config.TestDataFactory;
import com.smartmobility.model.Utente;
import com.smartmobility.model.enums.StatoUtente;
import com.smartmobility.repository.UtenteRepository;
import com.smartmobility.service.impl.GestioneUtentiServiceImpl;
import com.smartmobility.service.SessionRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestioneUtentiServiceImplTest {

    @Mock
    private UtenteRepository utenteRepository;

    @Mock
    private SessionRegistry sessionRegistry;

    private GestioneUtentiServiceImpl service;

    private Utente utente;

    @BeforeEach
    void setUp() {
        service = new GestioneUtentiServiceImpl(utenteRepository, sessionRegistry);
        utente = TestDataFactory.createDefaultUtente();
    }

    @Test
    void cercaReport_WithValidId_ReturnsReport() {
        utente.setReportUtente("User report content");
        when(utenteRepository.findByIdUtente(1L)).thenReturn(Optional.of(utente));

        String report = service.cercaReport(1L);

        assertEquals("User report content", report);
    }

    @Test
    void cercaReport_WithInvalidId_ThrowsNotFound() {
        when(utenteRepository.findByIdUtente(999L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> service.cercaReport(999L));
    }

    @Test
    void gestioneUtente_WithActiveUser_Suspends() {
        when(utenteRepository.findByIdUtente(1L)).thenReturn(Optional.of(utente));

        boolean result = service.gestioneUtente(1L);

        assertTrue(result);
        assertEquals(StatoUtente.sospeso, utente.getStatoUtente());
    }

    @Test
    void gestioneUtente_WithSuspendedUser_Reactivates() {
        utente.setStatoUtente(StatoUtente.sospeso);
        when(utenteRepository.findByIdUtente(1L)).thenReturn(Optional.of(utente));

        boolean result = service.gestioneUtente(1L);

        assertTrue(result);
        assertEquals(StatoUtente.attivo, utente.getStatoUtente());
    }

    @Test
    void gestioneUtente_WithDisabledUser_ThrowsConflict() {
        utente.setStatoUtente(StatoUtente.disattivato);
        when(utenteRepository.findByIdUtente(1L)).thenReturn(Optional.of(utente));

        assertThrows(ResponseStatusException.class,
                () -> service.gestioneUtente(1L));
    }

    @Test
    void azioneCorrettiva_AppendsToReport() {
        utente.setReportUtente("Previous report");
        when(utenteRepository.findByIdUtente(1L)).thenReturn(Optional.of(utente));

        service.azioneCorrettiva(1L, "New corrective action");

        assertTrue(utente.getReportUtente().contains("Previous report"));
        assertTrue(utente.getReportUtente().contains("New corrective action"));
    }

    @Test
    void azioneCorrettiva_WithEmptyReport_CreatesNew() {
        utente.setReportUtente("");
        when(utenteRepository.findByIdUtente(1L)).thenReturn(Optional.of(utente));

        service.azioneCorrettiva(1L, "First action");

        assertEquals("First action", utente.getReportUtente());
    }
}

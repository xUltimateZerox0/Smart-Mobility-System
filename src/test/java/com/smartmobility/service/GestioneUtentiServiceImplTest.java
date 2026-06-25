package com.smartmobility.service;

import com.smartmobility.config.TestDataFactory;
import com.smartmobility.model.Utente;
import com.smartmobility.model.enums.StatoUtente;
import com.smartmobility.repository.CorsaRepository;
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

    @Mock
    private CorsaRepository corsaRepository;

    @Mock
    private GestioneCorsaService gestioneCorsaService;

    private GestioneUtentiServiceImpl service;

    private Utente utente;

    @BeforeEach
    void setUp() {
        service = new GestioneUtentiServiceImpl(utenteRepository, sessionRegistry, corsaRepository, gestioneCorsaService);
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
    void bloccaUtente_WithValidId_SuspendsAndInvalidatesSession() {
        when(utenteRepository.findByIdUtente(1L)).thenReturn(Optional.of(utente));

        boolean result = service.bloccaUtente(1L);

        assertTrue(result);
        assertEquals(StatoUtente.sospeso, utente.getStatoUtente());
        verify(sessionRegistry).invalidateByEmail(utente.getEmail());
    }

    @Test
    void bloccaUtente_WithInvalidId_ThrowsNotFound() {
        when(utenteRepository.findByIdUtente(999L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> service.bloccaUtente(999L));
    }

    @Test
    void sbloccaUtente_WithValidId_ActivatesUser() {
        utente.setStatoUtente(StatoUtente.sospeso);
        when(utenteRepository.findByIdUtente(1L)).thenReturn(Optional.of(utente));

        boolean result = service.sbloccaUtente(1L);

        assertTrue(result);
        assertEquals(StatoUtente.attivo, utente.getStatoUtente());
    }

    @Test
    void sbloccaUtente_WithInvalidId_ThrowsNotFound() {
        when(utenteRepository.findByIdUtente(999L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> service.sbloccaUtente(999L));
    }

    @Test
    void disattivaUtente_WithValidId_DisablesAndInvalidatesSession() {
        when(utenteRepository.findByIdUtente(1L)).thenReturn(Optional.of(utente));

        boolean result = service.disattivaUtente(1L);

        assertTrue(result);
        assertEquals(StatoUtente.disattivato, utente.getStatoUtente());
        verify(sessionRegistry).invalidateByEmail(utente.getEmail());
    }

    @Test
    void disattivaUtente_WithInvalidId_ThrowsNotFound() {
        when(utenteRepository.findByIdUtente(999L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> service.disattivaUtente(999L));
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

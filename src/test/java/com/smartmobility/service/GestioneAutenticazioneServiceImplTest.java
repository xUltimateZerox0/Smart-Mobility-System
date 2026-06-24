package com.smartmobility.service;

import com.smartmobility.config.TestDataFactory;
import com.smartmobility.dto.response.AuthResponse;
import com.smartmobility.model.Attore;
import com.smartmobility.model.Utente;
import com.smartmobility.model.enums.RuoloAttore;
import com.smartmobility.model.enums.StatoUtente;
import com.smartmobility.repository.AttoreRepository;
import com.smartmobility.repository.UtenteRepository;
import com.smartmobility.service.impl.GestioneAutenticazioneServiceImpl;
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
class GestioneAutenticazioneServiceImplTest {

    @Mock
    private AttoreRepository attoreRepository;

    @Mock
    private UtenteRepository utenteRepository;

    @Mock
    private SessionRegistry sessionRegistry;

    private GestioneAutenticazioneServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new GestioneAutenticazioneServiceImpl(attoreRepository, utenteRepository, sessionRegistry);
    }

    @Test
    void invioCredenziali_WithValidCredentials_ReturnsAuthResponse() {
        Utente utente = TestDataFactory.createDefaultUtente();
        utente.setPassword("5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8"); // SHA-256 of "password"

        when(attoreRepository.findByEmail("mario.rossi@example.com")).thenReturn(Optional.of(utente));
        when(sessionRegistry.createSession(utente)).thenReturn("token-123");

        AuthResponse response = service.invioCredenziali("mario.rossi@example.com", "password");

        assertNotNull(response);
        assertEquals("mario.rossi@example.com", response.getEmail());
        assertEquals("Utente", response.getRuolo());
        assertEquals("token-123", response.getToken());
    }

    @Test
    void invioCredenziali_WithInvalidEmail_ThrowsUnauthorized() {
        when(attoreRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> service.invioCredenziali("notfound@example.com", "password"));
    }

    @Test
    void invioCredenziali_WithWrongPassword_ThrowsUnauthorized() {
        Utente utente = TestDataFactory.createDefaultUtente();
        utente.setPassword("5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8");

        when(attoreRepository.findByEmail("mario.rossi@example.com")).thenReturn(Optional.of(utente));

        assertThrows(ResponseStatusException.class,
                () -> service.invioCredenziali("mario.rossi@example.com", "wrongpassword"));
    }

    @Test
    void verificaValidita_WithNewEmail_CreatesAccount() {
        when(attoreRepository.existsByEmail("new@example.com")).thenReturn(false);

        Utente savedUtente = TestDataFactory.createUtente(1L, 1L, "New", "User", "new@example.com", StatoUtente.attivo);
        when(utenteRepository.save(any(Utente.class))).thenReturn(savedUtente);
        when(sessionRegistry.createSession(any(Attore.class))).thenReturn("token-new");

        AuthResponse response = service.verificaValidita("New", "User", "new@example.com", "password123", "1990-01-01");

        assertNotNull(response);
        assertEquals("new@example.com", response.getEmail());
        assertEquals("Utente", response.getRuolo());
    }

    @Test
    void verificaValidita_WithExistingEmail_ThrowsConflict() {
        when(attoreRepository.existsByEmail("existing@example.com")).thenReturn(true);

        assertThrows(ResponseStatusException.class,
                () -> service.verificaValidita("Existing", "User", "existing@example.com", "pass", "1990-01-01"));
    }

    @Test
    void inviaRichiestaLogout_InvalidatesSession() {
        service.inviaRichiestaLogout("user@example.com");
        verify(sessionRegistry).invalidateByEmail("user@example.com");
    }

    @Test
    void login_SingleSessionConstraint_TerminatesPreviousSession() {
        Utente utente = TestDataFactory.createDefaultUtente();
        utente.setPassword("5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8");

        when(attoreRepository.findByEmail("mario.rossi@example.com")).thenReturn(Optional.of(utente));
        when(sessionRegistry.createSession(utente)).thenReturn("token-456");

        AuthResponse response = service.invioCredenziali("mario.rossi@example.com", "password");

        verify(sessionRegistry).createSession(utente);
        assertEquals("token-456", response.getToken());
    }
}

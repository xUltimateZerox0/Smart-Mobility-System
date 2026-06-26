package com.smartmobility.service;

import com.smartmobility.config.TestDataFactory;
import com.smartmobility.model.Attore;
import com.smartmobility.model.Utente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionRegistryTest {

    private SessionRegistry registry;
    private Utente utente;

    @BeforeEach
    void setUp() {
        registry = new SessionRegistry();
        utente = TestDataFactory.createDefaultUtente();
    }

    @Test
    void createSession_ReturnsToken() {
        String token = registry.createSession(utente);
        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void createSession_InvalidatesPreviousSession() {
        String token1 = registry.createSession(utente);
        String token2 = registry.createSession(utente);

        assertNull(registry.getAttore(token1));
        assertNotNull(registry.getAttore(token2));
    }

    @Test
    void getAttore_WithValidToken_ReturnsAttore() {
        String token = registry.createSession(utente);
        Attore retrieved = registry.getAttore(token);

        assertNotNull(retrieved);
        assertEquals("mario.rossi@example.com", retrieved.getEmail());
    }

    @Test
    void getAttore_WithInvalidToken_ReturnsNull() {
        assertNull(registry.getAttore("non-existent-token"));
    }

    @Test
    void invalidate_RemovesSession() {
        String token = registry.createSession(utente);
        registry.invalidate(token);

        assertNull(registry.getAttore(token));
    }

    @Test
    void invalidateByEmail_RemovesAllSessionsForEmail() {
        String token1 = registry.createSession(utente);
        String token2 = registry.createSession(utente);

        registry.invalidateByEmail("mario.rossi@example.com");

        assertNull(registry.getAttore(token1));
        assertNull(registry.getAttore(token2));
    }

    @Test
    void singleSessionConstraint_LoginTerminatesPrevious() {
        Utente sameUser = TestDataFactory.createUtente(1L, 1L, "Mario", "Rossi",
                "mario.rossi@example.com", com.smartmobility.model.enums.StatoUtente.attivo);

        String token1 = registry.createSession(utente);
        String token2 = registry.createSession(sameUser);

        assertNull(registry.getAttore(token1), "Previous session should be terminated");
        assertNotNull(registry.getAttore(token2), "New session should be active");
    }
}

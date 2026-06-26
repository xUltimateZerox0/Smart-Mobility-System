package com.smartmobility.security;

import com.smartmobility.config.TestDataFactory;
import com.smartmobility.model.Attore;
import com.smartmobility.model.Utente;
import com.smartmobility.model.enums.RuoloAttore;
import com.smartmobility.service.SessionRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("java:S2068")
class SecurityHelperTest {

    @Mock
    private SessionRegistry sessionRegistry;

    @InjectMocks
    private SecurityHelper securityHelper;

    private Utente utente;
    private static final String VALID_TOKEN = "valid-token";
    private static final String VALID_AUTH = "Bearer " + VALID_TOKEN;
    private static final String INVALID_AUTH = "Bearer invalid-token";

    @BeforeEach
    void setUp() {
        utente = TestDataFactory.createDefaultUtente();
    }

    @Test
    void requireAuth_WithValidToken_ReturnsAttore() {
        when(sessionRegistry.getAttore(VALID_TOKEN)).thenReturn(utente);

        assertDoesNotThrow(() -> securityHelper.requireAuth(VALID_AUTH));
    }

    @Test
    void requireAuth_WithInvalidToken_Throws401() {
        when(sessionRegistry.getAttore("invalid-token")).thenReturn(null);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> securityHelper.requireAuth(INVALID_AUTH));
        assertEquals(401, ex.getStatusCode().value());
    }

    @Test
    void requireRole_WithMatchingRole_DoesNotThrow() {
        utente.setRuolo(RuoloAttore.Utente);
        when(sessionRegistry.getAttore(VALID_TOKEN)).thenReturn(utente);

        assertDoesNotThrow(() -> securityHelper.requireRole(VALID_AUTH, RuoloAttore.Utente));
    }

    @Test
    void requireRole_WithWrongRole_Throws403() {
        utente.setRuolo(RuoloAttore.Utente);
        when(sessionRegistry.getAttore(VALID_TOKEN)).thenReturn(utente);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> securityHelper.requireRole(VALID_AUTH, RuoloAttore.PA));
        assertEquals(403, ex.getStatusCode().value());
    }

    @Test
    void requireRole_WithInvalidToken_Throws401() {
        when(sessionRegistry.getAttore("invalid-token")).thenReturn(null);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> securityHelper.requireRole(INVALID_AUTH, RuoloAttore.Utente));
        assertEquals(401, ex.getStatusCode().value());
    }

    @Test
    void requireUserIdMatch_WithMatchingId_DoesNotThrow() {
        utente.setId(42L);
        when(sessionRegistry.getAttore(VALID_TOKEN)).thenReturn(utente);

        assertDoesNotThrow(() -> securityHelper.requireUserIdMatch(VALID_AUTH, 42L));
    }

    @Test
    void requireUserIdMatch_WithWrongId_Throws403() {
        utente.setId(1L);
        when(sessionRegistry.getAttore(VALID_TOKEN)).thenReturn(utente);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> securityHelper.requireUserIdMatch(VALID_AUTH, 99L));
        assertEquals(403, ex.getStatusCode().value());
    }

    @Test
    void requireUserIdMatch_WithInvalidToken_Throws401() {
        when(sessionRegistry.getAttore("invalid-token")).thenReturn(null);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> securityHelper.requireUserIdMatch(INVALID_AUTH, 1L));
        assertEquals(401, ex.getStatusCode().value());
    }

    @Test
    void getCurrentUser_WithValidToken_ReturnsAttore() {
        when(sessionRegistry.getAttore(VALID_TOKEN)).thenReturn(utente);

        Attore result = securityHelper.getCurrentUser(VALID_AUTH);

        assertNotNull(result);
        assertEquals(utente.getId(), result.getId());
        assertEquals(utente.getEmail(), result.getEmail());
    }

    @Test
    void getCurrentUser_WithInvalidToken_Throws401() {
        when(sessionRegistry.getAttore("invalid-token")).thenReturn(null);

        Attore result = securityHelper.getCurrentUser(INVALID_AUTH);

        assertNull(result);
    }

    @Test
    void getCurrentUser_WithNullHeader_ReturnsNull() {
        assertNull(securityHelper.getCurrentUser(null));
    }

    @Test
    void getCurrentUser_WithNonBearerHeader_ReturnsNull() {
        assertNull(securityHelper.getCurrentUser("Basic some-token"));
    }

    @Test
    void getCurrentUserId_WithValidToken_ReturnsId() {
        utente.setId(7L);
        when(sessionRegistry.getAttore(VALID_TOKEN)).thenReturn(utente);

        Long userId = securityHelper.getCurrentUserId(VALID_AUTH);

        assertNotNull(userId);
        assertEquals(7L, userId);
    }

    @Test
    void getCurrentUserId_WithInvalidToken_Throws401() {
        when(sessionRegistry.getAttore("invalid-token")).thenReturn(null);

        Long userId = securityHelper.getCurrentUserId(INVALID_AUTH);

        assertNull(userId);
    }

    @Test
    void getCurrentUserId_WithNullHeader_ReturnsNull() {
        assertNull(securityHelper.getCurrentUserId(null));
    }

    @Test
    void getCurrentUserId_WithNonBearerHeader_ReturnsNull() {
        assertNull(securityHelper.getCurrentUserId("Basic some-token"));
    }

    @Test
    void requireAuth_WithNullHeader_Throws401() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> securityHelper.requireAuth(null));
        assertEquals(401, ex.getStatusCode().value());
    }

    @Test
    void requireAuth_WithNonBearerHeader_Throws401() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> securityHelper.requireAuth("Basic token"));
        assertEquals(401, ex.getStatusCode().value());
    }

    @Test
    void requireRole_WithNullHeader_Throws401() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> securityHelper.requireRole(null, RuoloAttore.Utente));
        assertEquals(401, ex.getStatusCode().value());
    }

    @Test
    void requireUserIdMatch_WithNullHeader_Throws401() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> securityHelper.requireUserIdMatch(null, 1L));
        assertEquals(401, ex.getStatusCode().value());
    }
}

package com.smartmobility.security;

import com.smartmobility.model.Attore;
import com.smartmobility.model.enums.RuoloAttore;
import com.smartmobility.service.SessionRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class SecurityHelper {

    private final SessionRegistry sessionRegistry;

    public SecurityHelper(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    public Attore getCurrentUser(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        String token = authHeader.substring(7);
        return sessionRegistry.getAttore(token);
    }

    public Long getCurrentUserId(String authHeader) {
        Attore attore = getCurrentUser(authHeader);
        return attore != null ? attore.getId() : null;
    }

    public void requireAuth(String authHeader) {
        if (getCurrentUser(authHeader) == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Autenticazione richiesta");
        }
    }

    public void requireRole(String authHeader, RuoloAttore requiredRole) {
        Attore attore = getCurrentUser(authHeader);
        if (attore == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Autenticazione richiesta");
        }
        if (attore.getRuolo() != requiredRole) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accesso negato: ruolo " + requiredRole + " richiesto");
        }
    }

    public void requireUserIdMatch(String authHeader, Long requestedUserId) {
        Attore attore = getCurrentUser(authHeader);
        if (attore == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Autenticazione richiesta");
        }
        if (!attore.getId().equals(requestedUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accesso negato: non puoi operare per conto di un altro utente");
        }
    }
}

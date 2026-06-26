package com.smartmobility.service;

import com.smartmobility.model.Attore;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionRegistry {
    private final Map<String, Attore> sessions = new ConcurrentHashMap<>();

    public String createSession(Attore attore) {
        invalidateByEmail(attore.getEmail());
        String token = UUID.randomUUID().toString();
        sessions.put(token, attore);
        return token;
    }

    public Attore getAttore(String token) {
        return sessions.get(token);
    }

    public void invalidate(String token) {
        sessions.remove(token);
    }

    public void invalidateByEmail(String email) {
        sessions.entrySet().removeIf(e -> e.getValue().getEmail().equals(email));
    }
}

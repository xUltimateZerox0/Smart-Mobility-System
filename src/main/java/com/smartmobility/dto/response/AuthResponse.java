package com.smartmobility.dto.response;

import java.util.Objects;

public class AuthResponse {
    private String token;
    private String email;
    private String ruolo;
    private Long idUtente;

    public AuthResponse() {}

    public AuthResponse(String token, String email, String ruolo, Long idUtente) {
        this.token = token;
        this.email = email;
        this.ruolo = ruolo;
        this.idUtente = idUtente;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }
    public Long getIdUtente() { return idUtente; }
    public void setIdUtente(Long idUtente) { this.idUtente = idUtente; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthResponse that = (AuthResponse) o;
        return Objects.equals(token, that.token) && Objects.equals(email, that.email) && Objects.equals(ruolo, that.ruolo) && Objects.equals(idUtente, that.idUtente);
    }

    @Override
    public int hashCode() { return Objects.hash(token, email, ruolo, idUtente); }

    @Override
    public String toString() { return "AuthResponse{token='" + token + "', email='" + email + "', ruolo='" + ruolo + "', idUtente=" + idUtente + "}"; }
}

package com.smartmobility.dto.response;

public class UtenteResponse {
    private Long id;
    private Long idUtente;
    private String nome;
    private String cognome;
    private String email;
    private String stato;

    public UtenteResponse() {}

    public UtenteResponse(Long id, Long idUtente, String nome, String cognome, String email, String stato) {
        this.id = id;
        this.idUtente = idUtente;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.stato = stato;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdUtente() { return idUtente; }
    public void setIdUtente(Long idUtente) { this.idUtente = idUtente; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getStato() { return stato; }
    public void setStato(String stato) { this.stato = stato; }
}

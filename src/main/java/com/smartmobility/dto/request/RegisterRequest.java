package com.smartmobility.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

public class RegisterRequest {
    @NotBlank
    private String nome;

    @NotBlank
    private String cognome;

    @NotBlank @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String datanascita;

    public RegisterRequest() {}

    public RegisterRequest(String nome, String cognome, String email, String password, String datanascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.datanascita = datanascita;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getDatanascita() { return datanascita; }
    public void setDatanascita(String datanascita) { this.datanascita = datanascita; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterRequest that = (RegisterRequest) o;
        return Objects.equals(nome, that.nome) && Objects.equals(cognome, that.cognome) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(datanascita, that.datanascita);
    }

    @Override
    public int hashCode() { return Objects.hash(nome, cognome, email, password, datanascita); }

    @Override
    public String toString() { return "RegisterRequest{nome='" + nome + "', cognome='" + cognome + "', email='" + email + "', password='" + password + "', datanascita='" + datanascita + "'}"; }
}

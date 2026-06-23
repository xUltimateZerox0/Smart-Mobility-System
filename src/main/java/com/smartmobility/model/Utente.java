package com.smartmobility.model;

import com.smartmobility.model.enums.StatoUtente;
import jakarta.persistence.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Entity
@Table(name = "utente")
@PrimaryKeyJoinColumn(name = "id")
public class Utente extends Attore {

    @Column(name = "id_utente", unique = true)
    private Long idUtente;

    @Column(name = "nome_utente")
    private String nomeUtente;

    @Column(name = "cognome_utente")
    private String cognomeUtente;

    private String telefono;

    @Column(name = "coordinate_utente")
    private String coordinateUtente;

    @Column(name = "report_utente")
    private String reportUtente;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato_utente")
    private StatoUtente statoUtente;

    @Column(name = "num_mezzi_prenotati")
    private int numMezziPrenotati;

    public Utente() {}

    public Long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
    }

    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    public String getCognomeUtente() {
        return cognomeUtente;
    }

    public void setCognomeUtente(String cognomeUtente) {
        this.cognomeUtente = cognomeUtente;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCoordinateUtente() {
        return coordinateUtente;
    }

    public void setCoordinateUtente(String coordinateUtente) {
        this.coordinateUtente = coordinateUtente;
    }

    public String getReportUtente() {
        return reportUtente;
    }

    public void setReportUtente(String reportUtente) {
        this.reportUtente = reportUtente;
    }

    public StatoUtente getStatoUtente() {
        return statoUtente;
    }

    public void setStatoUtente(StatoUtente statoUtente) {
        this.statoUtente = statoUtente;
    }

    public int getNumMezziPrenotati() {
        return numMezziPrenotati;
    }

    public void setNumMezziPrenotati(int numMezziPrenotati) {
        this.numMezziPrenotati = numMezziPrenotati;
    }

    public Utente ricercaUtente(Long idUtente) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    public void azioneCorrettiva(String azione) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    public void creaAccountUtente(String nome, String cognome, String email, String password, String datanascita) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
}

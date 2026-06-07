package com.smartmobility.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "utenze")
public class Utente extends Attore {

    @Column(columnDefinition = "geometry")
    private Point coordinateUtente;

    private Integer numMezziPrenotati;

    private String nomeUtente;

    private String cognomeUtente;

    private String telefono;

    private String reportUtente;

    public Utente() {
    }

    public Point getCoordinateUtente() {
        return coordinateUtente;
    }

    public void setCoordinateUtente(Point coordinateUtente) {
        this.coordinateUtente = coordinateUtente;
    }

    public Integer getNumMezziPrenotati() {
        return numMezziPrenotati;
    }

    public void setNumMezziPrenotati(Integer numMezziPrenotati) {
        this.numMezziPrenotati = numMezziPrenotati;
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

    public String getReportUtente() {
        return reportUtente;
    }

    public void setReportUtente(String reportUtente) {
        this.reportUtente = reportUtente;
    }
}

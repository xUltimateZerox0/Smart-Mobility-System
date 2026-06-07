package com.smartmobility.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "corse")
public class Corsa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCorsa;

    private Float costo;

    private LocalTime orarioInizio;

    private LocalTime orarioFine;

    private String coordinatePartenza;

    private String coordinateArrivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;

    public Corsa() {
    }

    public Long getIdCorsa() {
        return idCorsa;
    }

    public void setIdCorsa(Long idCorsa) {
        this.idCorsa = idCorsa;
    }

    public Float getCosto() {
        return costo;
    }

    public void setCosto(Float costo) {
        this.costo = costo;
    }

    public LocalTime getOrarioInizio() {
        return orarioInizio;
    }

    public void setOrarioInizio(LocalTime orarioInizio) {
        this.orarioInizio = orarioInizio;
    }

    public LocalTime getOrarioFine() {
        return orarioFine;
    }

    public void setOrarioFine(LocalTime orarioFine) {
        this.orarioFine = orarioFine;
    }

    public String getCoordinatePartenza() {
        return coordinatePartenza;
    }

    public void setCoordinatePartenza(String coordinatePartenza) {
        this.coordinatePartenza = coordinatePartenza;
    }

    public String getCoordinateArrivo() {
        return coordinateArrivo;
    }

    public void setCoordinateArrivo(String coordinateArrivo) {
        this.coordinateArrivo = coordinateArrivo;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }
}

package com.smartmobility.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "corsa")
public class Corsa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_corsa")
    private Long idCorsa;

    private float costo;

    @Column(name = "orario_inizio")
    private LocalTime orarioInizio;

    @Column(name = "orario_fine")
    private LocalTime orarioFine;

    @Column(name = "coordinate_partenza")
    private String coordinatePartenza;

    @Column(name = "coordinate_arrivo")
    private String coordinateArrivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_metodo_pagamento")
    private MetodoPagamento metodoPagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente")
    private Utente utente;

    public Corsa() {}

    public Long getIdCorsa() {
        return idCorsa;
    }

    public void setIdCorsa(Long idCorsa) {
        this.idCorsa = idCorsa;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
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

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}

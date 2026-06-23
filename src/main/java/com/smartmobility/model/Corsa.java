package com.smartmobility.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "corsa")
public class Corsa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_corsa")
    private Long idCorsa;

    private float costo;

    @Column(name = "orario_inizio")
    private LocalDateTime orarioInizio;

    @Column(name = "orario_fine")
    private LocalDateTime orarioFine;

    @Column(name = "coordinate_partenza")
    private String coordinatePartenza;

    @Column(name = "coordinate_arrivo")
    private String coordinateArrivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_metodo_pagamento")
    private MetodoPagamento metodoPagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;

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

    public LocalDateTime getOrarioInizio() {
        return orarioInizio;
    }

    public void setOrarioInizio(LocalDateTime orarioInizio) {
        this.orarioInizio = orarioInizio;
    }

    public LocalDateTime getOrarioFine() {
        return orarioFine;
    }

    public void setOrarioFine(LocalDateTime orarioFine) {
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

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}

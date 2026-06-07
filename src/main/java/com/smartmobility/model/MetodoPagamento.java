package com.smartmobility.model;

import jakarta.persistence.*;

@Entity
@Table(name = "metodi_pagamento")
public class MetodoPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numCarta;

    private String intestatarioCarta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente")
    private Utente utente;

    public MetodoPagamento() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumCarta() {
        return numCarta;
    }

    public void setNumCarta(String numCarta) {
        this.numCarta = numCarta;
    }

    public String getIntestatarioCarta() {
        return intestatarioCarta;
    }

    public void setIntestatarioCarta(String intestatarioCarta) {
        this.intestatarioCarta = intestatarioCarta;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}

package com.smartmobility.model;

import jakarta.persistence.*;

@Entity
@Table(name = "metodo_pagamento")
public class MetodoPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_metodo_pagamento")
    private Long idMetodoPagamento;

    @Column(name = "num_carta")
    private String numCarta;

    @Column(name = "intestatario_carta")
    private String intestatarioCarta;

    @Column(name = "ds_carta")
    private String dsCarta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente")
    private Utente utente;

    public MetodoPagamento() { /* required by JPA */ }

    public Long getIdMetodoPagamento() {
        return idMetodoPagamento;
    }

    public void setIdMetodoPagamento(Long idMetodoPagamento) {
        this.idMetodoPagamento = idMetodoPagamento;
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

    public String getDsCarta() { return dsCarta; }
    public void setDsCarta(String dsCarta) { this.dsCarta = dsCarta; }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}

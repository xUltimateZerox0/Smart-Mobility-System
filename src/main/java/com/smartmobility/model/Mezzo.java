package com.smartmobility.model;

import com.smartmobility.model.enums.StatoMezzo;
import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "mezzo")
public class Mezzo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mezzo")
    private Long idMezzo;

    @Column(name = "coordinate_mezzo")
    private String coordinateMezzo;

    @Enumerated(EnumType.STRING)
    private StatoMezzo stato;

    private float autonomia;

    @Column(name = "costo_orario")
    private float costoOrario;

    @Column(name = "velocita_max")
    private float velocitaMax;

    private String condizione;

    private String tipo;

    @Column(name = "id_flotta")
    private String idFlotta;

    @Column(name = "tempo_disponibilita")
    private LocalTime tempoDisponibilita;

    public Mezzo() { /* required by JPA */ }

    public Long getIdMezzo() {
        return idMezzo;
    }

    public void setIdMezzo(Long idMezzo) {
        this.idMezzo = idMezzo;
    }

    public String getCoordinateMezzo() {
        return coordinateMezzo;
    }

    public void setCoordinateMezzo(String coordinateMezzo) {
        this.coordinateMezzo = coordinateMezzo;
    }

    public StatoMezzo getStato() {
        return stato;
    }

    public void setStato(StatoMezzo stato) {
        this.stato = stato;
    }

    public float getAutonomia() {
        return autonomia;
    }

    public void setAutonomia(float autonomia) {
        this.autonomia = autonomia;
    }

    public float getCostoOrario() {
        return costoOrario;
    }

    public void setCostoOrario(float costoOrario) {
        this.costoOrario = costoOrario;
    }

    public float getVelocitaMax() {
        return velocitaMax;
    }

    public void setVelocitaMax(float velocitaMax) {
        this.velocitaMax = velocitaMax;
    }

    public String getCondizione() {
        return condizione;
    }

    public void setCondizione(String condizione) {
        this.condizione = condizione;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdFlotta() {
        return idFlotta;
    }

    public void setIdFlotta(String idFlotta) {
        this.idFlotta = idFlotta;
    }

    public LocalTime getTempoDisponibilita() {
        return tempoDisponibilita;
    }

    public void setTempoDisponibilita(LocalTime tempoDisponibilita) {
        this.tempoDisponibilita = tempoDisponibilita;
    }
}

package com.smartmobility.model;

import com.smartmobility.model.enums.MezzoStato;
import jakarta.persistence.*;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "mezzi")
public class Mezzo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMezzo;

    @Column(columnDefinition = "geometry")
    private Point coordinateMezzo;

    @Enumerated(EnumType.STRING)
    private MezzoStato stato;

    private Float autonomia;

    private Float costoOrario;

    private Float velocitaMax;

    private String condizione;

    private String tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_flotta")
    private Flotta flotta;

    public Mezzo() {
    }

    public Long getIdMezzo() {
        return idMezzo;
    }

    public void setIdMezzo(Long idMezzo) {
        this.idMezzo = idMezzo;
    }

    public Point getCoordinateMezzo() {
        return coordinateMezzo;
    }

    public void setCoordinateMezzo(Point coordinateMezzo) {
        this.coordinateMezzo = coordinateMezzo;
    }

    public MezzoStato getStato() {
        return stato;
    }

    public void setStato(MezzoStato stato) {
        this.stato = stato;
    }

    public Float getAutonomia() {
        return autonomia;
    }

    public void setAutonomia(Float autonomia) {
        this.autonomia = autonomia;
    }

    public Float getCostoOrario() {
        return costoOrario;
    }

    public void setCostoOrario(Float costoOrario) {
        this.costoOrario = costoOrario;
    }

    public Float getVelocitaMax() {
        return velocitaMax;
    }

    public void setVelocitaMax(Float velocitaMax) {
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

    public Flotta getFlotta() {
        return flotta;
    }

    public void setFlotta(Flotta flotta) {
        this.flotta = flotta;
    }
}

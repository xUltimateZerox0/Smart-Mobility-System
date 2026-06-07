package com.smartmobility.model;

import jakarta.persistence.*;

@Entity
@Table(name = "flotte")
public class Flotta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFlotta;

    public Flotta() {
    }

    public Long getIdFlotta() {
        return idFlotta;
    }

    public void setIdFlotta(Long idFlotta) {
        this.idFlotta = idFlotta;
    }
}

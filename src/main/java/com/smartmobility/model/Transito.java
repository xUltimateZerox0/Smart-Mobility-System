package com.smartmobility.model;

import jakarta.persistence.*;

@Entity
@Table(name = "transito")
public class Transito {

    @EmbeddedId
    private TransitoId id;

    public Transito() {}

    public TransitoId getId() {
        return id;
    }

    public void setId(TransitoId id) {
        this.id = id;
    }
}

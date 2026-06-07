package com.smartmobility.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "operatori")
public class Operatore extends Attore {

    public Operatore() {
    }
}

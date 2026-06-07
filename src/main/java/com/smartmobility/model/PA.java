package com.smartmobility.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "pa_enti")
public class PA extends Attore {

    public PA() {
    }
}

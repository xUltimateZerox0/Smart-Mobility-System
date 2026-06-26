package com.smartmobility.model;

import com.smartmobility.model.enums.TipoOperatore;
import jakarta.persistence.*;

@Entity
@Table(name = "operatore")
@PrimaryKeyJoinColumn(name = "id")
public class Operatore extends Attore {

    @Enumerated(EnumType.STRING)
    private TipoOperatore tipo;

    public Operatore() { /* required by JPA */ }

    public TipoOperatore getTipo() {
        return tipo;
    }

    public void setTipo(TipoOperatore tipo) {
        this.tipo = tipo;
    }
}

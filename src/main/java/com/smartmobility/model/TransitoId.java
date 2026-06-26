package com.smartmobility.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TransitoId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "id_corsa")
    private Long idCorsa;

    @Column(name = "id_area")
    private Long idArea;

    public TransitoId() {}

    public Long getIdCorsa() {
        return idCorsa;
    }

    public void setIdCorsa(Long idCorsa) {
        this.idCorsa = idCorsa;
    }

    public Long getIdArea() {
        return idArea;
    }

    public void setIdArea(Long idArea) {
        this.idArea = idArea;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransitoId that = (TransitoId) o;
        return Objects.equals(idCorsa, that.idCorsa) && Objects.equals(idArea, that.idArea);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCorsa, idArea);
    }
}

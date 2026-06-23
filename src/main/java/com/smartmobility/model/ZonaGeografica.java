package com.smartmobility.model;

import com.smartmobility.model.enums.TipoRestrizione;
import jakarta.persistence.*;

@Entity
@Table(name = "zona_geografica")
public class ZonaGeografica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_area")
    private Long idArea;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_restrizione")
    private TipoRestrizione tipoRestrizione;

    @Column(name = "note_restrizione")
    private String noteRestrizione;

    private String zona;

    public ZonaGeografica() {}

    public Long getIdArea() {
        return idArea;
    }

    public void setIdArea(Long idArea) {
        this.idArea = idArea;
    }

    public TipoRestrizione getTipoRestrizione() {
        return tipoRestrizione;
    }

    public void setTipoRestrizione(TipoRestrizione tipoRestrizione) {
        this.tipoRestrizione = tipoRestrizione;
    }

    public String getNoteRestrizione() {
        return noteRestrizione;
    }

    public void setNoteRestrizione(String noteRestrizione) {
        this.noteRestrizione = noteRestrizione;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }
}

package com.smartmobility.model;

import com.smartmobility.model.enums.TipoRestrizione;
import jakarta.persistence.*;
import org.locationtech.jts.geom.LineString;

@Entity
@Table(name = "zone_geografiche")
public class ZonaGeografica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArea;

    @Enumerated(EnumType.STRING)
    private TipoRestrizione tipoRestrizione;

    private String noteRestrizione;

    @Column(columnDefinition = "geometry")
    private LineString zona;

    public ZonaGeografica() {
    }

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

    public LineString getZona() {
        return zona;
    }

    public void setZona(LineString zona) {
        this.zona = zona;
    }
}

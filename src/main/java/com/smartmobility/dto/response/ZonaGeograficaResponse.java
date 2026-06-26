package com.smartmobility.dto.response;

import java.util.Objects;

public class ZonaGeograficaResponse {
    private Long id;
    private String tipoRestrizione;
    private String noteRestrizione;
    private String zona;

    public ZonaGeograficaResponse() {}

    public ZonaGeograficaResponse(Long id, String tipoRestrizione, String noteRestrizione, String zona) {
        this.id = id;
        this.tipoRestrizione = tipoRestrizione;
        this.noteRestrizione = noteRestrizione;
        this.zona = zona;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTipoRestrizione() { return tipoRestrizione; }
    public void setTipoRestrizione(String tipoRestrizione) { this.tipoRestrizione = tipoRestrizione; }
    public String getNoteRestrizione() { return noteRestrizione; }
    public void setNoteRestrizione(String noteRestrizione) { this.noteRestrizione = noteRestrizione; }
    public String getZona() { return zona; }
    public void setZona(String zona) { this.zona = zona; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZonaGeograficaResponse that = (ZonaGeograficaResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(tipoRestrizione, that.tipoRestrizione) && Objects.equals(noteRestrizione, that.noteRestrizione) && Objects.equals(zona, that.zona);
    }

    @Override
    public int hashCode() { return Objects.hash(id, tipoRestrizione, noteRestrizione, zona); }

    @Override
    public String toString() { return "ZonaGeograficaResponse{id=" + id + ", tipoRestrizione='" + tipoRestrizione + "', noteRestrizione='" + noteRestrizione + "', zona='" + zona + "'}"; }
}

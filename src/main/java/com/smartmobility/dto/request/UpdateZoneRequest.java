package com.smartmobility.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

public class UpdateZoneRequest {
    @NotBlank
    private String tipoRestrizione;

    private String noteRestrizione;

    @NotBlank
    private String zona;

    public UpdateZoneRequest() {}

    public UpdateZoneRequest(String tipoRestrizione, String noteRestrizione, String zona) {
        this.tipoRestrizione = tipoRestrizione;
        this.noteRestrizione = noteRestrizione;
        this.zona = zona;
    }

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
        UpdateZoneRequest that = (UpdateZoneRequest) o;
        return Objects.equals(tipoRestrizione, that.tipoRestrizione) && Objects.equals(noteRestrizione, that.noteRestrizione) && Objects.equals(zona, that.zona);
    }

    @Override
    public int hashCode() { return Objects.hash(tipoRestrizione, noteRestrizione, zona); }

    @Override
    public String toString() { return "UpdateZoneRequest{tipoRestrizione='" + tipoRestrizione + "', noteRestrizione='" + noteRestrizione + "', zona='" + zona + "'}"; }
}

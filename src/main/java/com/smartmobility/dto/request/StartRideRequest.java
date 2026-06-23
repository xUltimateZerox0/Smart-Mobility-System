package com.smartmobility.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.Objects;

public class StartRideRequest {
    @NotNull
    private Long idMezzo;

    @NotNull
    private Long idUtente;

    public StartRideRequest() {}

    public StartRideRequest(Long idMezzo, Long idUtente) {
        this.idMezzo = idMezzo;
        this.idUtente = idUtente;
    }

    public Long getIdMezzo() { return idMezzo; }
    public void setIdMezzo(Long idMezzo) { this.idMezzo = idMezzo; }
    public Long getIdUtente() { return idUtente; }
    public void setIdUtente(Long idUtente) { this.idUtente = idUtente; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StartRideRequest that = (StartRideRequest) o;
        return Objects.equals(idMezzo, that.idMezzo) && Objects.equals(idUtente, that.idUtente);
    }

    @Override
    public int hashCode() { return Objects.hash(idMezzo, idUtente); }

    @Override
    public String toString() { return "StartRideRequest{idMezzo=" + idMezzo + ", idUtente=" + idUtente + "}"; }
}

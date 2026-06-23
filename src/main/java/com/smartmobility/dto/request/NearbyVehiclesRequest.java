package com.smartmobility.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.util.Objects;

public class NearbyVehiclesRequest {
    @NotBlank
    private String coordinateUtente;

    @Positive
    private float raggio;

    public NearbyVehiclesRequest() {}

    public NearbyVehiclesRequest(String coordinateUtente, float raggio) {
        this.coordinateUtente = coordinateUtente;
        this.raggio = raggio;
    }

    public String getCoordinateUtente() { return coordinateUtente; }
    public void setCoordinateUtente(String coordinateUtente) { this.coordinateUtente = coordinateUtente; }
    public float getRaggio() { return raggio; }
    public void setRaggio(float raggio) { this.raggio = raggio; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NearbyVehiclesRequest that = (NearbyVehiclesRequest) o;
        return Float.compare(that.raggio, raggio) == 0 && Objects.equals(coordinateUtente, that.coordinateUtente);
    }

    @Override
    public int hashCode() { return Objects.hash(coordinateUtente, raggio); }

    @Override
    public String toString() { return "NearbyVehiclesRequest{coordinateUtente='" + coordinateUtente + "', raggio=" + raggio + "}"; }
}

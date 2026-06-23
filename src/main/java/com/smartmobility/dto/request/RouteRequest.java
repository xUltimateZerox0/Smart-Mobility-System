package com.smartmobility.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

public class RouteRequest {
    @NotBlank
    private String coordinateUtente;

    @NotBlank
    private String destinazione;

    public RouteRequest() {}

    public RouteRequest(String coordinateUtente, String destinazione) {
        this.coordinateUtente = coordinateUtente;
        this.destinazione = destinazione;
    }

    public String getCoordinateUtente() { return coordinateUtente; }
    public void setCoordinateUtente(String coordinateUtente) { this.coordinateUtente = coordinateUtente; }
    public String getDestinazione() { return destinazione; }
    public void setDestinazione(String destinazione) { this.destinazione = destinazione; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteRequest that = (RouteRequest) o;
        return Objects.equals(coordinateUtente, that.coordinateUtente) && Objects.equals(destinazione, that.destinazione);
    }

    @Override
    public int hashCode() { return Objects.hash(coordinateUtente, destinazione); }

    @Override
    public String toString() { return "RouteRequest{coordinateUtente='" + coordinateUtente + "', destinazione='" + destinazione + "'}"; }
}

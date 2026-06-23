package com.smartmobility.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

public class ConflictCheckRequest {
    @NotBlank
    private String zona;

    public ConflictCheckRequest() {}

    public ConflictCheckRequest(String zona) {
        this.zona = zona;
    }

    public String getZona() { return zona; }
    public void setZona(String zona) { this.zona = zona; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConflictCheckRequest that = (ConflictCheckRequest) o;
        return Objects.equals(zona, that.zona);
    }

    @Override
    public int hashCode() { return Objects.hash(zona); }

    @Override
    public String toString() { return "ConflictCheckRequest{zona='" + zona + "'}"; }
}

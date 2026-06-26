package com.smartmobility.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

public class CorrectiveActionRequest {
    @NotBlank
    private String azione;

    public CorrectiveActionRequest() {}

    public CorrectiveActionRequest(String azione) {
        this.azione = azione;
    }

    public String getAzione() { return azione; }
    public void setAzione(String azione) { this.azione = azione; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CorrectiveActionRequest that = (CorrectiveActionRequest) o;
        return Objects.equals(azione, that.azione);
    }

    @Override
    public int hashCode() { return Objects.hash(azione); }

    @Override
    public String toString() { return "CorrectiveActionRequest{azione='" + azione + "'}"; }
}

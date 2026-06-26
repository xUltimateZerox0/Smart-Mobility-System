package com.smartmobility.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

public class AddPaymentMethodRequest {
    @NotNull
    private Long idUtente;

    @NotBlank
    private String numCarta;

    @NotBlank
    private String dsCarta;

    @NotBlank
    private String cvv;

    @NotBlank
    private String intestatarioCarta;

    public AddPaymentMethodRequest() {}

    public AddPaymentMethodRequest(Long idUtente, String numCarta, String dsCarta, String cvv, String intestatarioCarta) {
        this.idUtente = idUtente;
        this.numCarta = numCarta;
        this.dsCarta = dsCarta;
        this.cvv = cvv;
        this.intestatarioCarta = intestatarioCarta;
    }

    public Long getIdUtente() { return idUtente; }
    public void setIdUtente(Long idUtente) { this.idUtente = idUtente; }
    public String getNumCarta() { return numCarta; }
    public void setNumCarta(String numCarta) { this.numCarta = numCarta; }
    public String getDsCarta() { return dsCarta; }
    public void setDsCarta(String dsCarta) { this.dsCarta = dsCarta; }
    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }
    public String getIntestatarioCarta() { return intestatarioCarta; }
    public void setIntestatarioCarta(String intestatarioCarta) { this.intestatarioCarta = intestatarioCarta; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddPaymentMethodRequest that = (AddPaymentMethodRequest) o;
        return Objects.equals(idUtente, that.idUtente) && Objects.equals(numCarta, that.numCarta) && Objects.equals(dsCarta, that.dsCarta) && Objects.equals(cvv, that.cvv) && Objects.equals(intestatarioCarta, that.intestatarioCarta);
    }

    @Override
    public int hashCode() { return Objects.hash(idUtente, numCarta, dsCarta, cvv, intestatarioCarta); }

    @Override
    public String toString() { return "AddPaymentMethodRequest{idUtente=" + idUtente + ", numCarta='[REDACTED]', dsCarta='" + dsCarta + "', cvv='[REDACTED]', intestatarioCarta='" + intestatarioCarta + "'}"; }
}

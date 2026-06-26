package com.smartmobility.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Objects;

public class ProcessPaymentRequest {
    @NotNull
    private Long idUtente;

    @NotNull
    private Long idMetodoPagamento;

    @NotNull @Positive
    private Double costo;

    @NotNull
    private Long idCorsa;

    public ProcessPaymentRequest() {}

    public ProcessPaymentRequest(Long idUtente, Long idMetodoPagamento, Long idCorsa, Double costo) {
        this.idUtente = idUtente;
        this.idMetodoPagamento = idMetodoPagamento;
        this.idCorsa = idCorsa;
        this.costo = costo;
    }

    public Long getIdUtente() { return idUtente; }
    public void setIdUtente(Long idUtente) { this.idUtente = idUtente; }
    public Long getIdMetodoPagamento() { return idMetodoPagamento; }
    public void setIdMetodoPagamento(Long idMetodoPagamento) { this.idMetodoPagamento = idMetodoPagamento; }
    public Double getCosto() { return costo; }
    public void setCosto(Double costo) { this.costo = costo; }
    public Long getIdCorsa() { return idCorsa; }
    public void setIdCorsa(Long idCorsa) { this.idCorsa = idCorsa; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessPaymentRequest that = (ProcessPaymentRequest) o;
        return Objects.equals(idUtente, that.idUtente) && Objects.equals(idMetodoPagamento, that.idMetodoPagamento) && Objects.equals(idCorsa, that.idCorsa) && Objects.equals(costo, that.costo);
    }

    @Override
    public int hashCode() { return Objects.hash(idUtente, idMetodoPagamento, idCorsa, costo); }

    @Override
    public String toString() { return "ProcessPaymentRequest{idUtente=" + idUtente + ", idMetodoPagamento=" + idMetodoPagamento + ", idCorsa=" + idCorsa + ", costo=" + costo + "}"; }
}

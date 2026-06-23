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

    public ProcessPaymentRequest() {}

    public ProcessPaymentRequest(Long idUtente, Long idMetodoPagamento, Double costo) {
        this.idUtente = idUtente;
        this.idMetodoPagamento = idMetodoPagamento;
        this.costo = costo;
    }

    public Long getIdUtente() { return idUtente; }
    public void setIdUtente(Long idUtente) { this.idUtente = idUtente; }
    public Long getIdMetodoPagamento() { return idMetodoPagamento; }
    public void setIdMetodoPagamento(Long idMetodoPagamento) { this.idMetodoPagamento = idMetodoPagamento; }
    public Double getCosto() { return costo; }
    public void setCosto(Double costo) { this.costo = costo; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessPaymentRequest that = (ProcessPaymentRequest) o;
        return Objects.equals(idUtente, that.idUtente) && Objects.equals(idMetodoPagamento, that.idMetodoPagamento) && Objects.equals(costo, that.costo);
    }

    @Override
    public int hashCode() { return Objects.hash(idUtente, idMetodoPagamento, costo); }

    @Override
    public String toString() { return "ProcessPaymentRequest{idUtente=" + idUtente + ", idMetodoPagamento=" + idMetodoPagamento + ", costo=" + costo + "}"; }
}

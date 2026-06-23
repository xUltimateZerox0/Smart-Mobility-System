package com.smartmobility.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.Objects;

public class PaymentMethodSelectionRequest {
    @NotNull
    private Long idMetodoPagamento;

    public PaymentMethodSelectionRequest() {}

    public PaymentMethodSelectionRequest(Long idMetodoPagamento) {
        this.idMetodoPagamento = idMetodoPagamento;
    }

    public Long getIdMetodoPagamento() { return idMetodoPagamento; }
    public void setIdMetodoPagamento(Long idMetodoPagamento) { this.idMetodoPagamento = idMetodoPagamento; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentMethodSelectionRequest that = (PaymentMethodSelectionRequest) o;
        return Objects.equals(idMetodoPagamento, that.idMetodoPagamento);
    }

    @Override
    public int hashCode() { return Objects.hash(idMetodoPagamento); }

    @Override
    public String toString() { return "PaymentMethodSelectionRequest{idMetodoPagamento=" + idMetodoPagamento + "}"; }
}

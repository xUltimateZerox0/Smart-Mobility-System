package com.smartmobility.dto.response;

import java.util.Objects;

public class MetodoPagamentoResponse {
    private Long id;
    private String numCarta;
    private String intestatarioCarta;
    private String dsCarta;

    public MetodoPagamentoResponse() {}

    public MetodoPagamentoResponse(Long id, String numCarta, String intestatarioCarta, String dsCarta) {
        this.id = id;
        this.numCarta = numCarta;
        this.intestatarioCarta = intestatarioCarta;
        this.dsCarta = dsCarta;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNumCarta() { return numCarta; }
    public void setNumCarta(String numCarta) { this.numCarta = numCarta; }
    public String getIntestatarioCarta() { return intestatarioCarta; }
    public void setIntestatarioCarta(String intestatarioCarta) { this.intestatarioCarta = intestatarioCarta; }
    public String getDsCarta() { return dsCarta; }
    public void setDsCarta(String dsCarta) { this.dsCarta = dsCarta; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetodoPagamentoResponse that = (MetodoPagamentoResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(numCarta, that.numCarta) && Objects.equals(intestatarioCarta, that.intestatarioCarta) && Objects.equals(dsCarta, that.dsCarta);
    }

    @Override
    public int hashCode() { return Objects.hash(id, numCarta, intestatarioCarta, dsCarta); }

    @Override
    public String toString() { return "MetodoPagamentoResponse{id=" + id + ", numCarta='" + numCarta + "', intestatarioCarta='" + intestatarioCarta + "', dsCarta='" + dsCarta + "'}"; }
}

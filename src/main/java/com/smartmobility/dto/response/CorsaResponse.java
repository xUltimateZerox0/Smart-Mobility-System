package com.smartmobility.dto.response;

import java.util.Objects;

public class CorsaResponse {
    private Long id;
    private Long idUtente;
    private Long idMezzo;
    private String dataInizio;
    private String dataFine;
    private Double costo;
    private Double distanza;
    private String stato;

    public CorsaResponse() {}

    public CorsaResponse(Long id, Long idUtente, Long idMezzo, String dataInizio, String dataFine, Double costo, Double distanza, String stato) {
        this.id = id;
        this.idUtente = idUtente;
        this.idMezzo = idMezzo;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.costo = costo;
        this.distanza = distanza;
        this.stato = stato;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdUtente() { return idUtente; }
    public void setIdUtente(Long idUtente) { this.idUtente = idUtente; }
    public Long getIdMezzo() { return idMezzo; }
    public void setIdMezzo(Long idMezzo) { this.idMezzo = idMezzo; }
    public String getDataInizio() { return dataInizio; }
    public void setDataInizio(String dataInizio) { this.dataInizio = dataInizio; }
    public String getDataFine() { return dataFine; }
    public void setDataFine(String dataFine) { this.dataFine = dataFine; }
    public Double getCosto() { return costo; }
    public void setCosto(Double costo) { this.costo = costo; }
    public Double getDistanza() { return distanza; }
    public void setDistanza(Double distanza) { this.distanza = distanza; }
    public String getStato() { return stato; }
    public void setStato(String stato) { this.stato = stato; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CorsaResponse that = (CorsaResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(idUtente, that.idUtente) && Objects.equals(idMezzo, that.idMezzo) && Objects.equals(dataInizio, that.dataInizio) && Objects.equals(dataFine, that.dataFine) && Objects.equals(costo, that.costo) && Objects.equals(distanza, that.distanza) && Objects.equals(stato, that.stato);
    }

    @Override
    public int hashCode() { return Objects.hash(id, idUtente, idMezzo, dataInizio, dataFine, costo, distanza, stato); }

    @Override
    public String toString() { return "CorsaResponse{id=" + id + ", idUtente=" + idUtente + ", idMezzo=" + idMezzo + ", dataInizio='" + dataInizio + "', dataFine='" + dataFine + "', costo=" + costo + ", distanza=" + distanza + ", stato='" + stato + "'}"; }
}

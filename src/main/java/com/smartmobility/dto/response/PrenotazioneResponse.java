package com.smartmobility.dto.response;

import java.util.Objects;

public class PrenotazioneResponse {
    private Long id;
    private Long idUtente;
    private Long idMezzo;
    private String dataInizio;
    private String dataFine;
    private String stato;

    public PrenotazioneResponse() {}

    public PrenotazioneResponse(Long id, Long idUtente, Long idMezzo, String dataInizio, String dataFine, String stato) {
        this.id = id;
        this.idUtente = idUtente;
        this.idMezzo = idMezzo;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
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
    public String getStato() { return stato; }
    public void setStato(String stato) { this.stato = stato; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrenotazioneResponse that = (PrenotazioneResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(idUtente, that.idUtente) && Objects.equals(idMezzo, that.idMezzo) && Objects.equals(dataInizio, that.dataInizio) && Objects.equals(dataFine, that.dataFine) && Objects.equals(stato, that.stato);
    }

    @Override
    public int hashCode() { return Objects.hash(id, idUtente, idMezzo, dataInizio, dataFine, stato); }

    @Override
    public String toString() { return "PrenotazioneResponse{id=" + id + ", idUtente=" + idUtente + ", idMezzo=" + idMezzo + ", dataInizio='" + dataInizio + "', dataFine='" + dataFine + "', stato='" + stato + "'}"; }
}

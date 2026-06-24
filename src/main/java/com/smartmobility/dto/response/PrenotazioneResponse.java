package com.smartmobility.dto.response;

import java.util.Objects;

public class PrenotazioneResponse {
    private Long id;
    private Long idUtente;
    private Long idMezzo;
    private String dataInizio;
    private String dataFine;
    private String stato;
    private String nomeVeicolo;
    private String tipoVeicolo;

    public PrenotazioneResponse() {}

    public PrenotazioneResponse(Long id, Long idUtente, Long idMezzo, String dataInizio, String dataFine, String stato) {
        this(id, idUtente, idMezzo, dataInizio, dataFine, stato, null, null);
    }

    public PrenotazioneResponse(Long id, Long idUtente, Long idMezzo, String dataInizio, String dataFine, String stato, String nomeVeicolo, String tipoVeicolo) {
        this.id = id;
        this.idUtente = idUtente;
        this.idMezzo = idMezzo;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.stato = stato;
        this.nomeVeicolo = nomeVeicolo;
        this.tipoVeicolo = tipoVeicolo;
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
    public String getNomeVeicolo() { return nomeVeicolo; }
    public void setNomeVeicolo(String nomeVeicolo) { this.nomeVeicolo = nomeVeicolo; }
    public String getTipoVeicolo() { return tipoVeicolo; }
    public void setTipoVeicolo(String tipoVeicolo) { this.tipoVeicolo = tipoVeicolo; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrenotazioneResponse that = (PrenotazioneResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(idUtente, that.idUtente) && Objects.equals(idMezzo, that.idMezzo) && Objects.equals(dataInizio, that.dataInizio) && Objects.equals(dataFine, that.dataFine) && Objects.equals(stato, that.stato) && Objects.equals(nomeVeicolo, that.nomeVeicolo) && Objects.equals(tipoVeicolo, that.tipoVeicolo);
    }

    @Override
    public int hashCode() { return Objects.hash(id, idUtente, idMezzo, dataInizio, dataFine, stato, nomeVeicolo, tipoVeicolo); }

    @Override
    public String toString() { return "PrenotazioneResponse{id=" + id + ", idUtente=" + idUtente + ", idMezzo=" + idMezzo + ", dataInizio='" + dataInizio + "', dataFine='" + dataFine + "', stato='" + stato + "', nomeVeicolo='" + nomeVeicolo + "', tipoVeicolo='" + tipoVeicolo + "'}"; }
}

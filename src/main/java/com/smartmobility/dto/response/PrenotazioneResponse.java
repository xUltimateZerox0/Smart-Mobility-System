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
    private String orarioInizio;
    private String qrCode;
    private Long idVeicolo;

    public PrenotazioneResponse() {}

    public PrenotazioneResponse(Long id, Long idUtente, Long idMezzo, String dataInizio, String dataFine, String stato) {
        this(id, idUtente, idMezzo, dataInizio, dataFine, stato, null, null, null, null, null);
    }

    public PrenotazioneResponse(Long id, Long idUtente, Long idMezzo, String dataInizio, String dataFine, String stato, String nomeVeicolo, String tipoVeicolo) {
        this(id, idUtente, idMezzo, dataInizio, dataFine, stato, nomeVeicolo, tipoVeicolo, null, null, null);
    }

    public PrenotazioneResponse(Long id, Long idUtente, Long idMezzo, String dataInizio, String dataFine, String stato, String nomeVeicolo, String tipoVeicolo, String orarioInizio, String qrCode, Long idVeicolo) {
        this.id = id;
        this.idUtente = idUtente;
        this.idMezzo = idMezzo;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.stato = stato;
        this.nomeVeicolo = nomeVeicolo;
        this.tipoVeicolo = tipoVeicolo;
        this.orarioInizio = orarioInizio;
        this.qrCode = qrCode;
        this.idVeicolo = idVeicolo;
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
    public String getOrarioInizio() { return orarioInizio; }
    public void setOrarioInizio(String orarioInizio) { this.orarioInizio = orarioInizio; }
    public String getQrCode() { return qrCode; }
    public void setQrCode(String qrCode) { this.qrCode = qrCode; }
    public Long getIdVeicolo() { return idVeicolo; }
    public void setIdVeicolo(Long idVeicolo) { this.idVeicolo = idVeicolo; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrenotazioneResponse that = (PrenotazioneResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(idUtente, that.idUtente) && Objects.equals(idMezzo, that.idMezzo) && Objects.equals(dataInizio, that.dataInizio) && Objects.equals(dataFine, that.dataFine) && Objects.equals(stato, that.stato) && Objects.equals(nomeVeicolo, that.nomeVeicolo) && Objects.equals(tipoVeicolo, that.tipoVeicolo) && Objects.equals(orarioInizio, that.orarioInizio) && Objects.equals(qrCode, that.qrCode) && Objects.equals(idVeicolo, that.idVeicolo);
    }

    @Override
    public int hashCode() { return Objects.hash(id, idUtente, idMezzo, dataInizio, dataFine, stato, nomeVeicolo, tipoVeicolo, orarioInizio, qrCode, idVeicolo); }

    @Override
    public String toString() { return "PrenotazioneResponse{id=" + id + ", idUtente=" + idUtente + ", idMezzo=" + idMezzo + ", dataInizio='" + dataInizio + "', dataFine='" + dataFine + "', stato='" + stato + "', nomeVeicolo='" + nomeVeicolo + "', tipoVeicolo='" + tipoVeicolo + "', orarioInizio='" + orarioInizio + "', qrCode='" + qrCode + "', idVeicolo=" + idVeicolo + "}"; }
}

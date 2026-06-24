package com.smartmobility.dto.response;

import java.util.Objects;

public class MezzoResponse {
    private Long id;
    private String tipo;
    private String stato;
    private Double latitudine;
    private Double longitudine;
    private Double autonomia;
    private Double tariffa;
    private String codiceMezzo;
    private String tempoDisponibilita;
    private String condizione;
    private String idFlotta;

    public MezzoResponse() {}

    public MezzoResponse(Long id, String tipo, String stato, Double latitudine, Double longitudine, Double autonomia, Double tariffa, String codiceMezzo) {
        this(id, tipo, stato, latitudine, longitudine, autonomia, tariffa, codiceMezzo, null, null, null);
    }

    public MezzoResponse(Long id, String tipo, String stato, Double latitudine, Double longitudine, Double autonomia, Double tariffa, String codiceMezzo, String tempoDisponibilita) {
        this(id, tipo, stato, latitudine, longitudine, autonomia, tariffa, codiceMezzo, tempoDisponibilita, null, null);
    }

    public MezzoResponse(Long id, String tipo, String stato, Double latitudine, Double longitudine, Double autonomia, Double tariffa, String codiceMezzo, String tempoDisponibilita, String condizione, String idFlotta) {
        this.id = id;
        this.tipo = tipo;
        this.stato = stato;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.autonomia = autonomia;
        this.tariffa = tariffa;
        this.codiceMezzo = codiceMezzo;
        this.tempoDisponibilita = tempoDisponibilita;
        this.condizione = condizione;
        this.idFlotta = idFlotta;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getStato() { return stato; }
    public void setStato(String stato) { this.stato = stato; }
    public Double getLatitudine() { return latitudine; }
    public void setLatitudine(Double latitudine) { this.latitudine = latitudine; }
    public Double getLongitudine() { return longitudine; }
    public void setLongitudine(Double longitudine) { this.longitudine = longitudine; }
    public Double getAutonomia() { return autonomia; }
    public void setAutonomia(Double autonomia) { this.autonomia = autonomia; }
    public Double getTariffa() { return tariffa; }
    public void setTariffa(Double tariffa) { this.tariffa = tariffa; }
    public String getCodiceMezzo() { return codiceMezzo; }
    public void setCodiceMezzo(String codiceMezzo) { this.codiceMezzo = codiceMezzo; }
    public String getTempoDisponibilita() { return tempoDisponibilita; }
    public void setTempoDisponibilita(String tempoDisponibilita) { this.tempoDisponibilita = tempoDisponibilita; }
    public String getCondizione() { return condizione; }
    public void setCondizione(String condizione) { this.condizione = condizione; }
    public String getIdFlotta() { return idFlotta; }
    public void setIdFlotta(String idFlotta) { this.idFlotta = idFlotta; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MezzoResponse that = (MezzoResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(tipo, that.tipo) && Objects.equals(stato, that.stato) && Objects.equals(latitudine, that.latitudine) && Objects.equals(longitudine, that.longitudine) && Objects.equals(autonomia, that.autonomia) && Objects.equals(tariffa, that.tariffa) && Objects.equals(codiceMezzo, that.codiceMezzo) && Objects.equals(tempoDisponibilita, that.tempoDisponibilita) && Objects.equals(condizione, that.condizione) && Objects.equals(idFlotta, that.idFlotta);
    }

    @Override
    public int hashCode() { return Objects.hash(id, tipo, stato, latitudine, longitudine, autonomia, tariffa, codiceMezzo, tempoDisponibilita, condizione, idFlotta); }

    @Override
    public String toString() { return "MezzoResponse{id=" + id + ", tipo='" + tipo + "', stato='" + stato + "', latitudine=" + latitudine + ", longitudine=" + longitudine + ", autonomia=" + autonomia + ", tariffa=" + tariffa + ", codiceMezzo='" + codiceMezzo + "', tempoDisponibilita='" + tempoDisponibilita + "', condizione='" + condizione + "', idFlotta='" + idFlotta + "'}"; }
}

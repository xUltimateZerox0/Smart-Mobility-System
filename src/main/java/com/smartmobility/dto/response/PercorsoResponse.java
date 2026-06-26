package com.smartmobility.dto.response;

import java.util.Objects;

public class PercorsoResponse {
    private String coordinatePartenza;
    private String coordinateDestinazione;
    private double distanzaKm;
    private Integer durataMinuti;
    private double costoStimato;
    private String messaggio;

    public PercorsoResponse() {}

    public PercorsoResponse(String coordinatePartenza, String coordinateDestinazione,
                            double distanzaKm, Integer durataMinuti,
                            double costoStimato, String messaggio) {
        this.coordinatePartenza = coordinatePartenza;
        this.coordinateDestinazione = coordinateDestinazione;
        this.distanzaKm = distanzaKm;
        this.durataMinuti = durataMinuti;
        this.costoStimato = costoStimato;
        this.messaggio = messaggio;
    }

    public String getCoordinatePartenza() { return coordinatePartenza; }
    public void setCoordinatePartenza(String coordinatePartenza) { this.coordinatePartenza = coordinatePartenza; }
    public String getCoordinateDestinazione() { return coordinateDestinazione; }
    public void setCoordinateDestinazione(String coordinateDestinazione) { this.coordinateDestinazione = coordinateDestinazione; }
    public double getDistanzaKm() { return distanzaKm; }
    public void setDistanzaKm(double distanzaKm) { this.distanzaKm = distanzaKm; }
    public Integer getDurataMinuti() { return durataMinuti; }
    public void setDurataMinuti(Integer durataMinuti) { this.durataMinuti = durataMinuti; }
    public double getCostoStimato() { return costoStimato; }
    public void setCostoStimato(double costoStimato) { this.costoStimato = costoStimato; }
    public String getMessaggio() { return messaggio; }
    public void setMessaggio(String messaggio) { this.messaggio = messaggio; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PercorsoResponse that = (PercorsoResponse) o;
        return Objects.equals(coordinatePartenza, that.coordinatePartenza) &&
               Objects.equals(coordinateDestinazione, that.coordinateDestinazione) &&
               Objects.equals(distanzaKm, that.distanzaKm) &&
               Objects.equals(durataMinuti, that.durataMinuti) &&
               Objects.equals(costoStimato, that.costoStimato) &&
               Objects.equals(messaggio, that.messaggio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinatePartenza, coordinateDestinazione, distanzaKm, durataMinuti, costoStimato, messaggio);
    }

    @Override
    public String toString() {
        return "PercorsoResponse{" +
                "coordinatePartenza='" + coordinatePartenza + '\'' +
                ", coordinateDestinazione='" + coordinateDestinazione + '\'' +
                ", distanzaKm=" + distanzaKm +
                ", durataMinuti=" + durataMinuti +
                ", costoStimato=" + costoStimato +
                ", messaggio='" + messaggio + '\'' +
                '}';
    }
}
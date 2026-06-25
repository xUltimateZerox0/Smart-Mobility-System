package com.smartmobility.dto.response;

import java.util.Objects;

public class PercorsoResponse {
    private String coordinatePartenza;
    private String coordinateDestinazione;
    private Double distanzaKm;
    private Integer durataMinuti;
    private Double costoStimato;
    private String messaggio;

    public PercorsoResponse() {}

    public PercorsoResponse(String coordinatePartenza, String coordinateDestinazione,
                            Double distanzaKm, Integer durataMinuti,
                            Double costoStimato, String messaggio) {
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
    public Double getDistanzaKm() { return distanzaKm; }
    public void setDistanzaKm(Double distanzaKm) { this.distanzaKm = distanzaKm; }
    public Integer getDurataMinuti() { return durataMinuti; }
    public void setDurataMinuti(Integer durataMinuti) { this.durataMinuti = durataMinuti; }
    public Double getCostoStimato() { return costoStimato; }
    public void setCostoStimato(Double costoStimato) { this.costoStimato = costoStimato; }
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
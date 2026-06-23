package com.smartmobility.dto.response;

import java.util.Objects;

public class StatisticheResponse {
    private Long totalCorse;
    private Double totalKm;
    private Double totalRicavo;
    private Double mediaDurata;
    private Object dettagli;

    public StatisticheResponse() {}

    public StatisticheResponse(Long totalCorse, Double totalKm, Double totalRicavo, Double mediaDurata, Object dettagli) {
        this.totalCorse = totalCorse;
        this.totalKm = totalKm;
        this.totalRicavo = totalRicavo;
        this.mediaDurata = mediaDurata;
        this.dettagli = dettagli;
    }

    public Long getTotalCorse() { return totalCorse; }
    public void setTotalCorse(Long totalCorse) { this.totalCorse = totalCorse; }
    public Double getTotalKm() { return totalKm; }
    public void setTotalKm(Double totalKm) { this.totalKm = totalKm; }
    public Double getTotalRicavo() { return totalRicavo; }
    public void setTotalRicavo(Double totalRicavo) { this.totalRicavo = totalRicavo; }
    public Double getMediaDurata() { return mediaDurata; }
    public void setMediaDurata(Double mediaDurata) { this.mediaDurata = mediaDurata; }
    public Object getDettagli() { return dettagli; }
    public void setDettagli(Object dettagli) { this.dettagli = dettagli; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatisticheResponse that = (StatisticheResponse) o;
        return Objects.equals(totalCorse, that.totalCorse) && Objects.equals(totalKm, that.totalKm) && Objects.equals(totalRicavo, that.totalRicavo) && Objects.equals(mediaDurata, that.mediaDurata) && Objects.equals(dettagli, that.dettagli);
    }

    @Override
    public int hashCode() { return Objects.hash(totalCorse, totalKm, totalRicavo, mediaDurata, dettagli); }

    @Override
    public String toString() { return "StatisticheResponse{totalCorse=" + totalCorse + ", totalKm=" + totalKm + ", totalRicavo=" + totalRicavo + ", mediaDurata=" + mediaDurata + ", dettagli=" + dettagli + "}"; }
}

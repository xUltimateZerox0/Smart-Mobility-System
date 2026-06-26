package com.smartmobility.dto.response;

import java.util.Map;
import java.util.Objects;

public class StatisticheResponse {
    private long totalCorse;
    private double totalKm;
    private double totalRicavo;
    private double mediaDurata;
    private Map<String, Object> dettagli;

    public StatisticheResponse() {}

    public StatisticheResponse(long totalCorse, double totalKm, double totalRicavo, double mediaDurata, Map<String, Object> dettagli) {
        this.totalCorse = totalCorse;
        this.totalKm = totalKm;
        this.totalRicavo = totalRicavo;
        this.mediaDurata = mediaDurata;
        this.dettagli = dettagli;
    }

    public long getTotalCorse() { return totalCorse; }
    public void setTotalCorse(long totalCorse) { this.totalCorse = totalCorse; }
    public double getTotalKm() { return totalKm; }
    public void setTotalKm(double totalKm) { this.totalKm = totalKm; }
    public double getTotalRicavo() { return totalRicavo; }
    public void setTotalRicavo(double totalRicavo) { this.totalRicavo = totalRicavo; }
    public double getMediaDurata() { return mediaDurata; }
    public void setMediaDurata(double mediaDurata) { this.mediaDurata = mediaDurata; }
    public Map<String, Object> getDettagli() { return dettagli; }
    public void setDettagli(Map<String, Object> dettagli) { this.dettagli = dettagli; }

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

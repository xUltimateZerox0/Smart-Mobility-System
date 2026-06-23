package com.smartmobility.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

public class AnalyzeStatisticsRequest {
    @NotBlank
    private String dataInizio;

    @NotBlank
    private String dataFine;

    public AnalyzeStatisticsRequest() {}

    public AnalyzeStatisticsRequest(String dataInizio, String dataFine) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public String getDataInizio() { return dataInizio; }
    public void setDataInizio(String dataInizio) { this.dataInizio = dataInizio; }
    public String getDataFine() { return dataFine; }
    public void setDataFine(String dataFine) { this.dataFine = dataFine; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnalyzeStatisticsRequest that = (AnalyzeStatisticsRequest) o;
        return Objects.equals(dataInizio, that.dataInizio) && Objects.equals(dataFine, that.dataFine);
    }

    @Override
    public int hashCode() { return Objects.hash(dataInizio, dataFine); }

    @Override
    public String toString() { return "AnalyzeStatisticsRequest{dataInizio='" + dataInizio + "', dataFine='" + dataFine + "'}"; }
}

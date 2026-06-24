package com.smartmobility.dto.request;

import com.smartmobility.dto.response.CorsaResponse;
import java.util.List;
import java.util.Objects;

public class ExportStatisticsRequest {
    private List<CorsaResponse> corse;

    public ExportStatisticsRequest() {}

    public ExportStatisticsRequest(List<CorsaResponse> corse) {
        this.corse = corse;
    }

    public List<CorsaResponse> getCorse() { return corse; }
    public void setCorse(List<CorsaResponse> corse) { this.corse = corse; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExportStatisticsRequest that = (ExportStatisticsRequest) o;
        return Objects.equals(corse, that.corse);
    }

    @Override
    public int hashCode() { return Objects.hash(corse); }

    @Override
    public String toString() { return "ExportStatisticsRequest{corse=" + corse + "}"; }
}

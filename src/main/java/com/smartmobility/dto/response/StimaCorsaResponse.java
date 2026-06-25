package com.smartmobility.dto.response;

public class StimaCorsaResponse {
    private double costo;
    private double tariffa;

    public StimaCorsaResponse() {}

    public StimaCorsaResponse(double costo, double tariffa) {
        this.costo = costo;
        this.tariffa = tariffa;
    }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }
    public double getTariffa() { return tariffa; }
    public void setTariffa(double tariffa) { this.tariffa = tariffa; }
}

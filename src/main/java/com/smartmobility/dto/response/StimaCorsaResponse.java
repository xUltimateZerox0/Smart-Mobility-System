package com.smartmobility.dto.response;

public class StimaCorsaResponse {
    private float costo;
    private float tariffa;

    public StimaCorsaResponse() {}

    public StimaCorsaResponse(float costo, float tariffa) {
        this.costo = costo;
        this.tariffa = tariffa;
    }

    public float getCosto() { return costo; }
    public void setCosto(float costo) { this.costo = costo; }
    public float getTariffa() { return tariffa; }
    public void setTariffa(float tariffa) { this.tariffa = tariffa; }
}

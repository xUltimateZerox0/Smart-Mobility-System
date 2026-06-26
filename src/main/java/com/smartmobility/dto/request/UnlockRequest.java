package com.smartmobility.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

public class UnlockRequest {
    @NotBlank
    private String qrCode;

    public UnlockRequest() {}

    public UnlockRequest(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getQrCode() { return qrCode; }
    public void setQrCode(String qrCode) { this.qrCode = qrCode; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnlockRequest that = (UnlockRequest) o;
        return Objects.equals(qrCode, that.qrCode);
    }

    @Override
    public int hashCode() { return Objects.hash(qrCode); }

    @Override
    public String toString() { return "UnlockRequest{qrCode='" + qrCode + "'}"; }
}

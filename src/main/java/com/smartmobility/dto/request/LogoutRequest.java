package com.smartmobility.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Objects;

public class LogoutRequest {
    @NotBlank @Email
    private String email;

    public LogoutRequest() {}

    public LogoutRequest(String email) {
        this.email = email;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogoutRequest that = (LogoutRequest) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() { return Objects.hash(email); }

    @Override
    public String toString() { return "LogoutRequest{email='" + email + "'}"; }
}

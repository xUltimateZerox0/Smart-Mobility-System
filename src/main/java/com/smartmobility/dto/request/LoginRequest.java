package com.smartmobility.dto.request;

public record LoginRequest(
    String email,
    String password
) {}

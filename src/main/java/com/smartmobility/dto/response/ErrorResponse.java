package com.smartmobility.dto.response;

public record ErrorResponse(
    int status,
    String message,
    long timestamp
) {}

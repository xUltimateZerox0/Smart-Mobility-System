package com.smartmobility.dto.request;

public record RegistrazioneRequest(
    String nome,
    String cognome,
    String email,
    String password,
    String datanascita
) {}

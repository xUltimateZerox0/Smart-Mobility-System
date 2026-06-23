package com.smartmobility.service;

import com.smartmobility.dto.response.AuthResponse;

public interface GestioneAutenticazioneService {
    AuthResponse invioCredenziali(String email, String password);
    AuthResponse verificaValidita(String nome, String cognome, String email, String password, String datanascita);
    void inviaRichiestaLogout(String email);
}

package com.smartmobility.dto;

import com.smartmobility.model.enums.PrenotazioneStato;

import java.time.LocalDate;
import java.time.LocalTime;

public record PrenotazioneDTO(
    Long idPrenotazione,
    PrenotazioneStato stato,
    Long idUtente,
    Long idMezzo,
    LocalTime orarioInizio,
    LocalDate data
) { }

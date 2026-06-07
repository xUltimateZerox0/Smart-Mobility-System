package com.smartmobility.dto;

import com.smartmobility.model.enums.SegnalazioneStato;

import java.time.LocalDate;
import java.time.LocalTime;

public record SegnalazioneDTO(
    Long idSegnalazione,
    Long idMezzo,
    SegnalazioneStato stato,
    LocalTime ora,
    LocalDate data
) { }

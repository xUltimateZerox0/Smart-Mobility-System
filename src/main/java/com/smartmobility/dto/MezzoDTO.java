package com.smartmobility.dto;

import com.smartmobility.model.enums.MezzoStato;

public record MezzoDTO(
    Long idMezzo,
    Double longitudine,
    Double latitudine,
    MezzoStato stato,
    Float autonomia,
    Float costoOrario,
    Float velocitaMax,
    String condizione,
    String tipo,
    Long idFlotta
) { }

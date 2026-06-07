package com.smartmobility.dto;

public record CalcoloPercorsoRequest(
    Float coordUtenteLat,
    Float coordUtenteLon,
    String destinazione
) { }

package com.smartmobility.dto;

import com.smartmobility.model.enums.TipoRestrizione;

public record RestrizioneRequest(
    Long idArea,
    TipoRestrizione tipoRestrizione,
    String noteRestrizione,
    String zonaWkt
) { }

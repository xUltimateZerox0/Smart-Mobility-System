package com.smartmobility.integration;

import com.smartmobility.model.ZonaGeografica;

public interface ServizioMappaService {
    Object getPercorso(String coordinateIniziali, String coordinateFinali, ZonaGeografica restrizioni);
}

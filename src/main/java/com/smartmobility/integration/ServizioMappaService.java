package com.smartmobility.integration;

import com.smartmobility.model.ZonaGeografica;
import java.util.Map;

public interface ServizioMappaService {
    Map<String, Object> getPercorso(String coordinateIniziali, String coordinateFinali, ZonaGeografica restrizioni);
}

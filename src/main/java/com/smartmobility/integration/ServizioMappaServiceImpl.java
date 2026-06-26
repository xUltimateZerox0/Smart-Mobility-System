package com.smartmobility.integration;

import com.smartmobility.model.ZonaGeografica;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ServizioMappaServiceImpl implements ServizioMappaService {

    @Override
    public Map<String, Object> getPercorso(String coordinateIniziali, String coordinateFinali, ZonaGeografica restrizioni) {
        Map<String, Object> percorso = new HashMap<>();
        percorso.put("coordinateIniziali", coordinateIniziali);
        percorso.put("coordinateFinali", coordinateFinali);
        percorso.put("distanza", 5.2);
        percorso.put("durata", 15);
        percorso.put("messaggio", "Percorso calcolato con successo");
        return percorso;
    }
}

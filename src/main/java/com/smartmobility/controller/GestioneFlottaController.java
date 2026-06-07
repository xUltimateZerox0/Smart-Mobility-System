package com.smartmobility.controller;

import com.smartmobility.dto.MezzoDTO;
import com.smartmobility.service.FlottaService;
import com.smartmobility.service.MezzoService;
import com.smartmobility.service.SegnalazioneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/flotte")
public class GestioneFlottaController {

    private final FlottaService flottaService;
    private final MezzoService mezzoService;
    private final SegnalazioneService segnalazioneService;

    public GestioneFlottaController(FlottaService flottaService, MezzoService mezzoService, SegnalazioneService segnalazioneService) {
        this.flottaService = flottaService;
        this.mezzoService = mezzoService;
        this.segnalazioneService = segnalazioneService;
    }

    @GetMapping("/{idFlotta}/analisi")
    public ResponseEntity<Boolean> analisiStatoFlotta(@PathVariable Long idFlotta) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/mezzi/{idMezzo}/blocca")
    public ResponseEntity<Boolean> bloccaMezzo(@PathVariable Long idMezzo) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/{idFlotta}/manutenzione")
    public ResponseEntity<Boolean> avviaManutenzione(@PathVariable Long idFlotta) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @GetMapping("/{idFlotta}/condizioni-mezzi")
    public ResponseEntity<List<MezzoDTO>> getCondizioniMezzi(@PathVariable Long idFlotta) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

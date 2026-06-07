package com.smartmobility.controller;

import com.smartmobility.dto.MezzoDTO;
import com.smartmobility.dto.RicercaMezziRequest;
import com.smartmobility.service.MezzoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mezzi")
public class RicercaMezziController {

    private final MezzoService mezzoService;

    public RicercaMezziController(MezzoService mezzoService) {
        this.mezzoService = mezzoService;
    }

    @PostMapping("/vicini")
    public ResponseEntity<List<MezzoDTO>> visualizzaMezziVicini(@RequestBody RicercaMezziRequest request) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @GetMapping("/{idMezzo}")
    public ResponseEntity<MezzoDTO> visualizzaSpecifiche(@PathVariable Long idMezzo) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

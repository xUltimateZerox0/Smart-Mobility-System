package com.smartmobility.controller;

import com.smartmobility.dto.request.NearbyVehiclesRequest;
import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.service.RicercaMezziService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class RicercaMezziController {

    private final RicercaMezziService ricercaMezziService;

    public RicercaMezziController(RicercaMezziService ricercaMezziService) {
        this.ricercaMezziService = ricercaMezziService;
    }

    @PostMapping("/nearby")
    public ResponseEntity<List<MezzoResponse>> getNearbyVehicles(@Valid @RequestBody NearbyVehiclesRequest request) {
        List<MezzoResponse> mezzi = ricercaMezziService.visualizzaMezziVicini(
                request.getCoordinateUtente(), request.getRaggio());
        return ResponseEntity.ok(mezzi);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MezzoResponse> getVehicleDetails(@PathVariable Long id) {
        MezzoResponse mezzo = ricercaMezziService.visualizzaSpecifiche(id);
        return ResponseEntity.ok(mezzo);
    }
}

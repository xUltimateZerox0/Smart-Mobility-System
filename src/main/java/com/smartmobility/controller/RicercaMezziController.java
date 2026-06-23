package com.smartmobility.controller;

import com.smartmobility.dto.request.NearbyVehiclesRequest;
import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.service.RicercaMezziService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @GetMapping("/{id}")
    public ResponseEntity<MezzoResponse> getVehicleDetails(@PathVariable Long id) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

package com.smartmobility.controller;

import com.smartmobility.dto.request.CorrectiveActionRequest;
import com.smartmobility.service.GestioneUtentiService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/admin/users")
public class GestioneUtentiController {

    private final GestioneUtentiService gestioneUtentiService;

    public GestioneUtentiController(GestioneUtentiService gestioneUtentiService) {
        this.gestioneUtentiService = gestioneUtentiService;
    }

    @GetMapping("/{id}/report")
    public ResponseEntity<String> getReport(@PathVariable Long id) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PutMapping("/{id}/moderate")
    public ResponseEntity<Boolean> moderateUser(@PathVariable Long id) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }

    @PostMapping("/{id}/corrective-action")
    public ResponseEntity<Void> correctiveAction(@PathVariable Long id,
                                                  @Valid @RequestBody CorrectiveActionRequest request) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Skeleton phase - Awaiting implementation");
    }
}

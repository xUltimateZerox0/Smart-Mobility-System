package com.smartmobility.controller;

import com.smartmobility.dto.request.CorrectiveActionRequest;
import com.smartmobility.dto.response.UtenteResponse;
import com.smartmobility.model.enums.RuoloAttore;
import com.smartmobility.security.SecurityHelper;
import com.smartmobility.service.GestioneUtentiService;
import jakarta.validation.Valid;
import org.springframework.lang.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class GestioneUtentiController {

    private final GestioneUtentiService gestioneUtentiService;
    private final SecurityHelper securityHelper;

    public GestioneUtentiController(GestioneUtentiService gestioneUtentiService, SecurityHelper securityHelper) {
        this.gestioneUtentiService = gestioneUtentiService;
        this.securityHelper = securityHelper;
    }

    private void requireAdminOrPA(@Nullable String authHeader) {
        var attore = securityHelper.getCurrentUser(authHeader);
        if (attore == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Autenticazione richiesta");
        }
        if (attore.getRuolo() != RuoloAttore.PA && attore.getRuolo() != RuoloAttore.Operatore) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accesso negato: ruolo PA o Operatore richiesto");
        }
    }

    @GetMapping
    public ResponseEntity<List<UtenteResponse>> getUsers(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        requireAdminOrPA(authHeader);
        List<UtenteResponse> users = gestioneUtentiService.getElencoUtenti();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}/report")
    public ResponseEntity<String> getReport(@PathVariable Long id,
                                             @RequestHeader(value = "Authorization", required = false) String authHeader) {
        requireAdminOrPA(authHeader);
        String report = gestioneUtentiService.cercaReport(id);
        return ResponseEntity.ok(report);
    }

    @PutMapping("/{id}/moderate")
    public ResponseEntity<Boolean> moderateUser(@PathVariable Long id,
                                                  @RequestHeader(value = "Authorization", required = false) String authHeader) {
        requireAdminOrPA(authHeader);
        boolean result = gestioneUtentiService.gestioneUtente(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/corrective-action")
    public ResponseEntity<Void> correctiveAction(@PathVariable Long id,
                                                   @Valid @RequestBody CorrectiveActionRequest request,
                                                   @RequestHeader(value = "Authorization", required = false) String authHeader) {
        requireAdminOrPA(authHeader);
        gestioneUtentiService.azioneCorrettiva(id, request.getAzione());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/block")
    public ResponseEntity<Boolean> blockUser(@PathVariable Long id,
                                               @RequestHeader(value = "Authorization", required = false) String authHeader) {
        requireAdminOrPA(authHeader);
        boolean result = gestioneUtentiService.bloccaUtente(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/unblock")
    public ResponseEntity<Boolean> unblockUser(@PathVariable Long id,
                                                 @RequestHeader(value = "Authorization", required = false) String authHeader) {
        requireAdminOrPA(authHeader);
        boolean result = gestioneUtentiService.sbloccaUtente(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/disable")
    public ResponseEntity<Boolean> disableUser(@PathVariable Long id,
                                                 @RequestHeader(value = "Authorization", required = false) String authHeader) {
        requireAdminOrPA(authHeader);
        boolean result = gestioneUtentiService.disattivaUtente(id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}/report")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id,
                                               @RequestHeader(value = "Authorization", required = false) String authHeader) {
        requireAdminOrPA(authHeader);
        gestioneUtentiService.cancellaReport(id);
        return ResponseEntity.noContent().build();
    }
}

package com.smartmobility.repository;

import com.smartmobility.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByIdUtente(Long idUtente);
}

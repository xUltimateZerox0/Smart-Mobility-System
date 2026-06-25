package com.smartmobility.repository;

import com.smartmobility.model.Corsa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CorsaRepository extends JpaRepository<Corsa, Long> {
    List<Corsa> findByUtenteId(Long idUtente);
    List<Corsa> findByUtenteIdAndOrarioFineIsNull(Long idUtente);
    List<Corsa> findByOrarioFineIsNull();

    @Query("SELECT c FROM Corsa c WHERE c.orarioInizio < :end AND (c.orarioFine IS NULL OR c.orarioFine > :start)")
    List<Corsa> findByDataRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}

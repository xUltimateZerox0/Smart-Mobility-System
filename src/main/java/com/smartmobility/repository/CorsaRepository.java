package com.smartmobility.repository;

import com.smartmobility.model.Corsa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CorsaRepository extends JpaRepository<Corsa, Long> {
    List<Corsa> findByUtenteId(Long idUtente);
    List<Corsa> findByUtenteIdAndOrarioFineIsNull(Long idUtente);

    @Query("SELECT c FROM Corsa c WHERE c.orarioInizio >= :start AND c.orarioFine <= :end")
    List<Corsa> findByDataRange(@Param("start") LocalDate start, @Param("end") LocalDate end);
}

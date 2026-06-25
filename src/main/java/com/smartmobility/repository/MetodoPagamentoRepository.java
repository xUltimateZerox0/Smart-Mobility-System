package com.smartmobility.repository;

import com.smartmobility.model.MetodoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MetodoPagamentoRepository extends JpaRepository<MetodoPagamento, Long> {
    List<MetodoPagamento> findByUtenteId(Long idUtente);

    @Query("SELECT m FROM MetodoPagamento m WHERE m.utente.idUtente = :idUtente")
    List<MetodoPagamento> findByIdUtente(@Param("idUtente") Long idUtente);

    boolean existsByNumCarta(String numCarta);
}

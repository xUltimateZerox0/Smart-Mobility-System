package com.smartmobility.repository;

import com.smartmobility.model.MetodoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetodoPagamentoRepository extends JpaRepository<MetodoPagamento, Long> {
    List<MetodoPagamento> findByUtenteId(Long idUtente);
    boolean existsByNumCarta(String numCarta);
}

package com.smartmobility.repository;

import com.smartmobility.model.MetodoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetodoPagamentoRepository extends JpaRepository<MetodoPagamento, Long> {

    boolean existsByNumCarta(String numCarta);
}

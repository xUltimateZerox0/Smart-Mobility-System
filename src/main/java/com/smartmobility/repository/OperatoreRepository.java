package com.smartmobility.repository;

import com.smartmobility.model.Operatore;
import com.smartmobility.model.enums.TipoOperatore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OperatoreRepository extends JpaRepository<Operatore, Long> {
    Optional<Operatore> findByEmail(String email);
    List<Operatore> findByTipo(TipoOperatore tipo);
}

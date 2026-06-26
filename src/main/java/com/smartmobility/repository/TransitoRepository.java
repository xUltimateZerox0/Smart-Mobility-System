package com.smartmobility.repository;

import com.smartmobility.model.Transito;
import com.smartmobility.model.TransitoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransitoRepository extends JpaRepository<Transito, TransitoId> {

    @Query("SELECT t FROM Transito t WHERE t.id.idCorsa = :idCorsa")
    List<Transito> findByIdCorsa(@Param("idCorsa") Long idCorsa);

    @Query("SELECT t FROM Transito t WHERE t.id.idArea = :idArea")
    List<Transito> findByIdArea(@Param("idArea") Long idArea);
}

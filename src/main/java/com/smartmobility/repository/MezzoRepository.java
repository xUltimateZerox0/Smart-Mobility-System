package com.smartmobility.repository;

import com.smartmobility.model.Mezzo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MezzoRepository extends JpaRepository<Mezzo, Long> {

    @Query("SELECT m FROM Mezzo m WHERE m.flotta.idFlotta = :idFlotta")
    List<Mezzo> findByIdFlotta(@Param("idFlotta") Long idFlotta);
}

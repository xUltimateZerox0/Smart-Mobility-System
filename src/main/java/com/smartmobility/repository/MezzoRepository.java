package com.smartmobility.repository;

import com.smartmobility.model.Mezzo;
import com.smartmobility.model.enums.StatoMezzo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MezzoRepository extends JpaRepository<Mezzo, Long> {
    List<Mezzo> findByIdFlotta(String idFlotta);
    List<Mezzo> findByStato(StatoMezzo stato);
    List<Mezzo> findByStatoNot(StatoMezzo stato);
}

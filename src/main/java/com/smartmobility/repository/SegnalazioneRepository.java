package com.smartmobility.repository;

import com.smartmobility.model.Segnalazione;
import com.smartmobility.model.enums.StatoSegnalazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SegnalazioneRepository extends JpaRepository<Segnalazione, Long> {

    @Query("SELECT s FROM Segnalazione s WHERE s.mezzo.idMezzo = :mezzoId")
    List<Segnalazione> findByMezzoId(@Param("mezzoId") Long mezzoId);

    List<Segnalazione> findByStato(StatoSegnalazione stato);
}

package com.smartmobility.repository;

import com.smartmobility.model.Segnalazione;
import com.smartmobility.model.enums.StatoSegnalazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SegnalazioneRepository extends JpaRepository<Segnalazione, Long> {

    List<Segnalazione> findByStato(StatoSegnalazione stato);
}

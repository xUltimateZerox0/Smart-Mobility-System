package com.smartmobility.repository;

import com.smartmobility.model.Prenotazione;
import com.smartmobility.model.enums.PrenotazioneStato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    List<Prenotazione> findByStato(PrenotazioneStato stato);
}

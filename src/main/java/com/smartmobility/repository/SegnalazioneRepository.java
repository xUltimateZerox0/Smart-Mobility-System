package com.smartmobility.repository;

import com.smartmobility.model.Segnalazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SegnalazioneRepository extends JpaRepository<Segnalazione, Long> {

}

package com.smartmobility.repository;

import com.smartmobility.model.Prenotazione;
import com.smartmobility.model.enums.StatoPrenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findByStato(StatoPrenotazione stato);

    @Query("SELECT p FROM Prenotazione p WHERE p.utente.id = :idUtente AND p.stato = :stato")
    List<Prenotazione> findByUtenteIdAndStato(@Param("idUtente") Long idUtente, @Param("stato") StatoPrenotazione stato);

    @Query("SELECT p FROM Prenotazione p WHERE p.mezzo.idMezzo = :idMezzo AND p.stato = :stato")
    List<Prenotazione> findByMezzoIdAndStato(@Param("idMezzo") Long idMezzo, @Param("stato") StatoPrenotazione stato);

    @Query("SELECT p FROM Prenotazione p LEFT JOIN FETCH p.mezzo WHERE p.mezzo.idMezzo = :idMezzo AND p.stato = :stato")
    List<Prenotazione> findByMezzoIdAndStatoWithMezzo(@Param("idMezzo") Long idMezzo, @Param("stato") StatoPrenotazione stato);

    @Query("SELECT p FROM Prenotazione p LEFT JOIN FETCH p.utente LEFT JOIN FETCH p.mezzo WHERE p.utente.id = :userId")
    List<Prenotazione> findByUtenteIdWithDetails(@Param("userId") Long userId);

    @Query("SELECT p FROM Prenotazione p LEFT JOIN FETCH p.utente LEFT JOIN FETCH p.mezzo")
    List<Prenotazione> findAllWithDetails();

    @Query("SELECT p FROM Prenotazione p LEFT JOIN FETCH p.mezzo WHERE p.idPrenotazione = :id")
    Optional<Prenotazione> findByIdWithMezzo(@Param("id") Long id);

    @Query("SELECT p FROM Prenotazione p LEFT JOIN FETCH p.utente LEFT JOIN FETCH p.mezzo WHERE p.stato = :stato")
    List<Prenotazione> findByStatoWithDetails(@Param("stato") StatoPrenotazione stato);
}

package com.smartmobility.model;

import com.smartmobility.model.enums.PrenotazioneStato;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "prenotazioni")
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrenotazione;

    @Enumerated(EnumType.STRING)
    private PrenotazioneStato stato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utente")
    private Utente utente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;

    private LocalTime orarioInizio;

    private LocalDate data;

    public Prenotazione() {
    }

    public Long getIdPrenotazione() {
        return idPrenotazione;
    }

    public void setIdPrenotazione(Long idPrenotazione) {
        this.idPrenotazione = idPrenotazione;
    }

    public PrenotazioneStato getStato() {
        return stato;
    }

    public void setStato(PrenotazioneStato stato) {
        this.stato = stato;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public LocalTime getOrarioInizio() {
        return orarioInizio;
    }

    public void setOrarioInizio(LocalTime orarioInizio) {
        this.orarioInizio = orarioInizio;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}

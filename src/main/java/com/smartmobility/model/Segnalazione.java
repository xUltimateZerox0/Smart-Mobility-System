package com.smartmobility.model;

import com.smartmobility.model.enums.SegnalazioneStato;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "segnalazioni")
public class Segnalazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSegnalazione;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;

    @Enumerated(EnumType.STRING)
    private SegnalazioneStato stato;

    private LocalTime ora;

    private LocalDate data;

    public Segnalazione() {
    }

    public Long getIdSegnalazione() {
        return idSegnalazione;
    }

    public void setIdSegnalazione(Long idSegnalazione) {
        this.idSegnalazione = idSegnalazione;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public SegnalazioneStato getStato() {
        return stato;
    }

    public void setStato(SegnalazioneStato stato) {
        this.stato = stato;
    }

    public LocalTime getOra() {
        return ora;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}

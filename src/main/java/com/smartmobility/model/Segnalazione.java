package com.smartmobility.model;

import com.smartmobility.model.enums.StatoSegnalazione;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "segnalazione")
public class Segnalazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_segnalazione")
    private Long idSegnalazione;

    @Enumerated(EnumType.STRING)
    private StatoSegnalazione stato;

    private LocalTime ora;

    private LocalDate data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;

    @Column(name = "motivazione")
    private String motivazione;

    public Segnalazione() {}

    public Long getIdSegnalazione() {
        return idSegnalazione;
    }

    public void setIdSegnalazione(Long idSegnalazione) {
        this.idSegnalazione = idSegnalazione;
    }

    public StatoSegnalazione getStato() {
        return stato;
    }

    public void setStato(StatoSegnalazione stato) {
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

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public String getMotivazione() {
        return motivazione;
    }

    public void setMotivazione(String motivazione) {
        this.motivazione = motivazione;
    }
}

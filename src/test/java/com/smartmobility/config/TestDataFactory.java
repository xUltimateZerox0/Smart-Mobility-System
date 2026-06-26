package com.smartmobility.config;

import com.smartmobility.model.*;
import com.smartmobility.model.enums.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SuppressWarnings("java:S2068") // test-only credentials
public class TestDataFactory {

    public static Utente createUtente(Long id, Long idUtente, String nome, String cognome, String email, StatoUtente stato) {
        Utente utente = new Utente();
        utente.setId(id);
        utente.setIdUtente(idUtente != null ? idUtente : id);
        utente.setNomeUtente(nome);
        utente.setCognomeUtente(cognome);
        utente.setEmail(email);
        utente.setPassword("hashed-password");
        utente.setRuolo(RuoloAttore.Utente);
        utente.setStatoUtente(stato);
        utente.setCoordinateUtente("41.9028,12.4964,0.0");
        utente.setReportUtente("");
        utente.setNumMezziPrenotati(0);
        return utente;
    }

    public static Utente createDefaultUtente() {
        return createUtente(1L, 1L, "Mario", "Rossi", "mario.rossi@example.com", StatoUtente.attivo);
    }

    public static Operatore createOperatore(Long id, String email, TipoOperatore tipo) {
        Operatore op = new Operatore();
        op.setId(id);
        op.setEmail(email);
        op.setPassword("hashed-password");
        op.setRuolo(RuoloAttore.Operatore);
        op.setTipo(tipo);
        return op;
    }

    public static PA createPA(Long id, String email) {
        PA pa = new PA();
        pa.setId(id);
        pa.setEmail(email);
        pa.setPassword("hashed-password");
        pa.setRuolo(RuoloAttore.PA);
        return pa;
    }

    public static Mezzo createMezzo(Long id, String tipo, StatoMezzo stato, String coordinate, float autonomia, float costoOrario) {
        Mezzo mezzo = new Mezzo();
        mezzo.setIdMezzo(id);
        mezzo.setTipo(tipo);
        mezzo.setStato(stato);
        mezzo.setCoordinateMezzo(coordinate);
        mezzo.setAutonomia(autonomia);
        mezzo.setCostoOrario(costoOrario);
        mezzo.setVelocitaMax(50.0f);
        mezzo.setCondizione("buona");
        mezzo.setIdFlotta("FLOTTA-1");
        mezzo.setTempoDisponibilita(LocalTime.of(10, 0));
        return mezzo;
    }

    public static Mezzo createDefaultMezzo() {
        return createMezzo(1L, "bici", StatoMezzo.disponibile, "41.9028,12.4964,0.0", 80.0f, 5.0f);
    }

    public static Corsa createCorsa(Long id, Utente utente, Mezzo mezzo, MetodoPagamento metodo, LocalDateTime inizio, float costo) {
        Corsa corsa = new Corsa();
        corsa.setIdCorsa(id);
        corsa.setUtente(utente);
        corsa.setMezzo(mezzo);
        corsa.setMetodoPagamento(metodo);
        corsa.setOrarioInizio(inizio);
        corsa.setCosto(costo);
        corsa.setCoordinatePartenza(mezzo != null ? mezzo.getCoordinateMezzo() : "41.9028,12.4964,0.0");
        return corsa;
    }

    public static Prenotazione createPrenotazione(Long id, Utente utente, Mezzo mezzo, StatoPrenotazione stato, LocalTime orarioInizio) {
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setIdPrenotazione(id);
        prenotazione.setUtente(utente);
        prenotazione.setMezzo(mezzo);
        prenotazione.setStato(stato);
        prenotazione.setOrarioInizio(orarioInizio);
        prenotazione.setData(LocalDate.now());
        return prenotazione;
    }

    public static MetodoPagamento createMetodoPagamento(Long id, String numCarta, String intestatario, Utente utente) {
        MetodoPagamento metodo = new MetodoPagamento();
        metodo.setIdMetodoPagamento(id);
        metodo.setNumCarta(numCarta);
        metodo.setIntestatarioCarta(intestatario);
        metodo.setUtente(utente);
        return metodo;
    }

    public static MetodoPagamento createDefaultMetodoPagamento(Utente utente) {
        return createMetodoPagamento(1L, "4111111111111111", "Mario Rossi", utente);
    }

    public static Segnalazione createSegnalazione(Long id, Mezzo mezzo, StatoSegnalazione stato) {
        Segnalazione segnalazione = new Segnalazione();
        segnalazione.setIdSegnalazione(id);
        segnalazione.setMezzo(mezzo);
        segnalazione.setStato(stato);
        segnalazione.setData(LocalDate.now());
        segnalazione.setOra(LocalTime.now());
        return segnalazione;
    }

    public static ZonaGeografica createZonaGeografica(Long id, TipoRestrizione tipo, String zona) {
        ZonaGeografica z = new ZonaGeografica();
        z.setIdArea(id);
        z.setTipoRestrizione(tipo);
        z.setNoteRestrizione("Test restriction");
        z.setZona(zona);
        return z;
    }
}

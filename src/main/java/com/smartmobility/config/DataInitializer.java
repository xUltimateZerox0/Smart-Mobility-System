package com.smartmobility.config;

import com.smartmobility.model.MetodoPagamento;
import com.smartmobility.model.Mezzo;
import com.smartmobility.model.Operatore;
import com.smartmobility.model.PA;
import com.smartmobility.model.Utente;
import com.smartmobility.model.ZonaGeografica;
import com.smartmobility.model.enums.RuoloAttore;
import com.smartmobility.model.enums.StatoMezzo;
import com.smartmobility.model.enums.StatoUtente;
import com.smartmobility.model.enums.TipoOperatore;
import com.smartmobility.model.enums.TipoRestrizione;
import com.smartmobility.repository.AttoreRepository;
import com.smartmobility.repository.MetodoPagamentoRepository;
import com.smartmobility.repository.MezzoRepository;
import com.smartmobility.repository.OperatoreRepository;
import com.smartmobility.repository.PARepository;
import com.smartmobility.repository.UtenteRepository;
import com.smartmobility.repository.ZonaGeograficaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalTime;
import java.util.HexFormat;

@Configuration
@Profile("dev")
public class DataInitializer {

    @Bean
    CommandLineRunner seedData(AttoreRepository attoreRepository,
                                UtenteRepository utenteRepository,
                                OperatoreRepository operatoreRepository,
                                PARepository paRepository,
                                MezzoRepository mezzoRepository,
                                ZonaGeograficaRepository zonaRepository,
                                MetodoPagamentoRepository metodoPagamentoRepository) {
        return args -> {
            seedUsers(attoreRepository, utenteRepository, operatoreRepository, paRepository);
            seedMezzi(mezzoRepository);
            seedZone(zonaRepository);
            seedPaymentMethods(metodoPagamentoRepository, attoreRepository);
        };
    }

    private void seedUsers(AttoreRepository attoreRepository,
                           UtenteRepository utenteRepository,
                           OperatoreRepository operatoreRepository,
                           PARepository paRepository) {
        if (attoreRepository.findByEmail("test@smartmobility.com").isEmpty()) {
            Utente utente = new Utente();
            utente.setNomeUtente("Mario");
            utente.setCognomeUtente("Rossi");
            utente.setEmail("test@smartmobility.com");
            utente.setPassword(hashPassword("password"));
            utente.setRuolo(RuoloAttore.Utente);
            utente.setStatoUtente(StatoUtente.attivo);
            utente.setReportUtente("");
            utente.setNumMezziPrenotati(0);
            utente.setCoordinateUtente("45.4642,9.1900");
            utente = utenteRepository.save(utente);
            utente.setIdUtente(utente.getId());
            utenteRepository.save(utente);
        }

        if (attoreRepository.findByEmail("pa@smartmobility.com").isEmpty()) {
            PA pa = new PA();
            pa.setEmail("pa@smartmobility.com");
            pa.setPassword(hashPassword("password"));
            pa.setRuolo(RuoloAttore.PA);
            paRepository.save(pa);
        }

        if (attoreRepository.findByEmail("tecnico@smartmobility.com").isEmpty()) {
            Operatore op = new Operatore();
            op.setEmail("tecnico@smartmobility.com");
            op.setPassword(hashPassword("password"));
            op.setRuolo(RuoloAttore.Operatore);
            op.setTipo(TipoOperatore.OperatoreTecnico);
            operatoreRepository.save(op);
        }

        if (attoreRepository.findByEmail("sc@smartmobility.com").isEmpty()) {
            Operatore op = new Operatore();
            op.setEmail("sc@smartmobility.com");
            op.setPassword(hashPassword("password"));
            op.setRuolo(RuoloAttore.Operatore);
            op.setTipo(TipoOperatore.OperatoreSC);
            operatoreRepository.save(op);
        }
    }

    private void seedMezzi(MezzoRepository mezzoRepository) {
        if (mezzoRepository.count() > 0) return;

        Mezzo m1 = new Mezzo();
        m1.setTipo("bici");
        m1.setCoordinateMezzo("45.4642,9.1900");
        m1.setStato(StatoMezzo.disponibile);
        m1.setAutonomia(50);
        m1.setCostoOrario(5);
        m1.setVelocitaMax(25);
        m1.setCondizione("buona");
        m1.setIdFlotta("1");
        m1.setTempoDisponibilita(LocalTime.of(8, 0));
        mezzoRepository.save(m1);

        Mezzo m2 = new Mezzo();
        m2.setTipo("scooter");
        m2.setCoordinateMezzo("45.4670,9.1850");
        m2.setStato(StatoMezzo.disponibile);
        m2.setAutonomia(80);
        m2.setCostoOrario(15);
        m2.setVelocitaMax(45);
        m2.setCondizione("ottima");
        m2.setIdFlotta("1");
        m2.setTempoDisponibilita(LocalTime.of(7, 0));
        mezzoRepository.save(m2);

        Mezzo m3 = new Mezzo();
        m3.setTipo("auto");
        m3.setCoordinateMezzo("45.4700,9.1950");
        m3.setStato(StatoMezzo.disponibile);
        m3.setAutonomia(200);
        m3.setCostoOrario(30);
        m3.setVelocitaMax(130);
        m3.setCondizione("buona");
        m3.setIdFlotta("1");
        m3.setTempoDisponibilita(LocalTime.of(6, 0));
        mezzoRepository.save(m3);

        Mezzo m4 = new Mezzo();
        m4.setTipo("bici");
        m4.setCoordinateMezzo("45.4600,9.1800");
        m4.setStato(StatoMezzo.in_uso);
        m4.setAutonomia(30);
        m4.setCostoOrario(5);
        m4.setVelocitaMax(25);
        m4.setCondizione("danneggiata");
        m4.setIdFlotta("1");
        m4.setTempoDisponibilita(LocalTime.of(9, 0));
        mezzoRepository.save(m4);

        Mezzo m5 = new Mezzo();
        m5.setTipo("scooter");
        m5.setCoordinateMezzo("45.4750,9.2000");
        m5.setStato(StatoMezzo.manutenzione);
        m5.setAutonomia(60);
        m5.setCostoOrario(15);
        m5.setVelocitaMax(45);
        m5.setCondizione("manutenzione");
        m5.setIdFlotta("1");
        m5.setTempoDisponibilita(LocalTime.of(10, 0));
        mezzoRepository.save(m5);
    }

    private void seedPaymentMethods(MetodoPagamentoRepository metodoPagamentoRepository,
                                     AttoreRepository attoreRepository) {
        if (metodoPagamentoRepository.count() == 0) {
            attoreRepository.findByEmail("test@smartmobility.com").ifPresent(attore -> {
                if (attore instanceof Utente utente) {
                    MetodoPagamento metodo = new MetodoPagamento();
                    metodo.setNumCarta("4111111111111111");
                    metodo.setIntestatarioCarta("Mario Rossi");
                    metodo.setDsCarta("12/28");
                    metodo.setCvv("123");
                    metodo.setUtente(utente);
                    metodoPagamentoRepository.save(metodo);
                }
            });
        }
    }

    private void seedZone(ZonaGeograficaRepository zonaRepository) {
        if (zonaRepository.count() > 0) return;

        ZonaGeografica z1 = new ZonaGeografica();
        z1.setTipoRestrizione(TipoRestrizione.ZTL);
        z1.setNoteRestrizione("Centro storico - accesso vietato 8:00-20:00");
        z1.setZona("45.4640,9.1900,45.4660,9.1950");
        zonaRepository.save(z1);

        ZonaGeografica z2 = new ZonaGeografica();
        z2.setTipoRestrizione(TipoRestrizione.divieto_parcheggio);
        z2.setNoteRestrizione("Area stazione - divieto sosta permanente");
        z2.setZona("45.4700,9.1850,45.4720,9.1900");
        zonaRepository.save(z2);

        ZonaGeografica z3 = new ZonaGeografica();
        z3.setTipoRestrizione(TipoRestrizione.limite_velocita);
        z3.setNoteRestrizione("Zona residenziale - limite 30 km/h");
        z3.setZona("45.4620,9.1880,45.4680,9.1980");
        zonaRepository.save(z3);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
}

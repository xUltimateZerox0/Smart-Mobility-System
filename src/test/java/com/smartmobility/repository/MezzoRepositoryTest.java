package com.smartmobility.repository;

import com.smartmobility.config.TestDataFactory;
import com.smartmobility.model.Mezzo;
import com.smartmobility.model.enums.StatoMezzo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class MezzoRepositoryTest {

    @Autowired
    private MezzoRepository mezzoRepository;

    @Test
    void findByIdFlotta_ReturnsCorrectVehicles() {
        Mezzo m1 = TestDataFactory.createMezzo(1L, "bici", StatoMezzo.disponibile, "41.9028,12.4964,0.0", 80.0f, 5.0f);
        Mezzo m2 = TestDataFactory.createMezzo(2L, "scooter", StatoMezzo.disponibile, "41.9030,12.4970,0.0", 60.0f, 8.0f);
        mezzoRepository.save(m1);
        mezzoRepository.save(m2);

        List<Mezzo> results = mezzoRepository.findByIdFlotta("FLOTTA-1");

        assertEquals(2, results.size());
    }

    @Test
    void findByStato_ReturnsFilteredVehicles() {
        Mezzo disponibile = TestDataFactory.createMezzo(1L, "bici", StatoMezzo.disponibile, "41.9028,12.4964,0.0", 80.0f, 5.0f);
        Mezzo inUso = TestDataFactory.createMezzo(2L, "scooter", StatoMezzo.in_uso, "41.9030,12.4970,0.0", 60.0f, 8.0f);
        mezzoRepository.save(disponibile);
        mezzoRepository.save(inUso);

        List<Mezzo> disponibili = mezzoRepository.findByStato(StatoMezzo.disponibile);
        List<Mezzo> inUsoList = mezzoRepository.findByStato(StatoMezzo.in_uso);

        assertEquals(1, disponibili.size());
        assertEquals(1, inUsoList.size());
    }

    @Test
    void findByStatoNot_ExcludesState() {
        Mezzo disponibile = TestDataFactory.createMezzo(1L, "bici", StatoMezzo.disponibile, "41.9028,12.4964,0.0", 80.0f, 5.0f);
        Mezzo manutenzione = TestDataFactory.createMezzo(2L, "scooter", StatoMezzo.manutenzione, "41.9030,12.4970,0.0", 60.0f, 8.0f);
        mezzoRepository.save(disponibile);
        mezzoRepository.save(manutenzione);

        List<Mezzo> nonDisponibili = mezzoRepository.findByStatoNot(StatoMezzo.disponibile);

        assertEquals(1, nonDisponibili.size());
        assertEquals(StatoMezzo.manutenzione, nonDisponibili.get(0).getStato());
    }
}

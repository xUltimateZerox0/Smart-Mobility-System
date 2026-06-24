package com.smartmobility.service;

import com.smartmobility.config.TestDataFactory;
import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.integration.MezzoIoTService;
import com.smartmobility.model.Mezzo;
import com.smartmobility.model.Segnalazione;
import com.smartmobility.model.enums.StatoMezzo;
import com.smartmobility.model.enums.StatoSegnalazione;
import com.smartmobility.repository.MezzoRepository;
import com.smartmobility.repository.SegnalazioneRepository;
import com.smartmobility.service.impl.GestioneFlottaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestioneFlottaServiceImplTest {

    @Mock
    private MezzoRepository mezzoRepository;

    @Mock
    private SegnalazioneRepository segnalazioneRepository;

    @Mock
    private MezzoIoTService mezzoIoTService;

    private GestioneFlottaServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new GestioneFlottaServiceImpl(mezzoRepository, segnalazioneRepository, mezzoIoTService);
    }

    @Test
    void analisiStatoFlotta_WithDamagedVehicle_CreatesSegnalazione() {
        Mezzo damaged = TestDataFactory.createMezzo(1L, "bici", StatoMezzo.disponibile, "41.9028,12.4964,0.0", 80.0f, 5.0f);
        damaged.setCondizione("danneggiato");

        when(mezzoRepository.findByIdFlotta("1")).thenReturn(List.of(damaged));

        boolean result = service.analisiStatoFlotta(1L);

        assertTrue(result);
        assertEquals(StatoMezzo.manutenzione, damaged.getStato());
        verify(segnalazioneRepository).save(any(Segnalazione.class));
    }

    @Test
    void analisiStatoFlotta_WithZeroAutonomy_CreatesSegnalazione() {
        Mezzo lowBattery = TestDataFactory.createMezzo(1L, "scooter", StatoMezzo.disponibile, "41.9028,12.4964,0.0", 0f, 8.0f);

        when(mezzoRepository.findByIdFlotta("1")).thenReturn(List.of(lowBattery));

        boolean result = service.analisiStatoFlotta(1L);

        assertTrue(result);
        assertEquals(StatoMezzo.manutenzione, lowBattery.getStato());
        verify(segnalazioneRepository).save(any(Segnalazione.class));
    }

    @Test
    void analisiStatoFlotta_WithHealthyVehicles_ReturnsFalse() {
        Mezzo healthy = TestDataFactory.createMezzo(1L, "bici", StatoMezzo.disponibile, "41.9028,12.4964,0.0", 80.0f, 5.0f);
        healthy.setCondizione("buona");

        when(mezzoRepository.findByIdFlotta("1")).thenReturn(List.of(healthy));

        boolean result = service.analisiStatoFlotta(1L);

        assertFalse(result);
        verify(segnalazioneRepository, never()).save(any());
    }

    @Test
    void bloccaMezzo_WithValidId_LocksVehicle() {
        Mezzo mezzo = TestDataFactory.createDefaultMezzo();
        when(mezzoRepository.findById(1L)).thenReturn(Optional.of(mezzo));
        when(mezzoIoTService.bloccoMezzoFisico(1L)).thenReturn(true);

        boolean result = service.bloccaMezzo(1L);

        assertTrue(result);
        assertEquals(StatoMezzo.bloccato, mezzo.getStato());
    }

    @Test
    void bloccaMezzo_WithIoTFailure_ThrowsError() {
        Mezzo mezzo = TestDataFactory.createDefaultMezzo();
        when(mezzoRepository.findById(1L)).thenReturn(Optional.of(mezzo));
        when(mezzoIoTService.bloccoMezzoFisico(1L)).thenReturn(false);

        assertThrows(ResponseStatusException.class,
                () -> service.bloccaMezzo(1L));
    }

    @Test
    void avviaManutenzione_WithValidFleet_UpdatesAllVehicles() {
        Mezzo m1 = TestDataFactory.createMezzo(1L, "bici", StatoMezzo.disponibile, "41.9028,12.4964,0.0", 80.0f, 5.0f);
        Mezzo m2 = TestDataFactory.createMezzo(2L, "scooter", StatoMezzo.disponibile, "41.9030,12.4970,0.0", 60.0f, 8.0f);

        when(mezzoRepository.findByIdFlotta("1")).thenReturn(List.of(m1, m2));

        boolean result = service.avviaManutenzione(1L);

        assertTrue(result);
        assertEquals(StatoMezzo.manutenzione, m1.getStato());
        assertEquals(StatoMezzo.manutenzione, m2.getStato());
        verify(segnalazioneRepository, times(2)).save(any(Segnalazione.class));
    }

    @Test
    void getCondizioniMezzi_WithValidFleet_ReturnsList() {
        Mezzo m1 = TestDataFactory.createDefaultMezzo();
        when(mezzoRepository.findByIdFlotta("1")).thenReturn(List.of(m1));

        List<MezzoResponse> conditions = service.getCondizioniMezzi(1L);

        assertEquals(1, conditions.size());
        assertEquals("bici", conditions.get(0).getTipo());
    }

    @Test
    void getCondizioniMezzi_WithEmptyFleet_ReturnsEmptyList() {
        when(mezzoRepository.findByIdFlotta("1")).thenReturn(List.of());

        List<MezzoResponse> conditions = service.getCondizioniMezzi(1L);

        assertTrue(conditions.isEmpty());
    }
}

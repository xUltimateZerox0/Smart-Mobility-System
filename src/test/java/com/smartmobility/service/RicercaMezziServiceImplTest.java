package com.smartmobility.service;

import com.smartmobility.config.TestDataFactory;
import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.model.Mezzo;
import com.smartmobility.model.enums.StatoMezzo;
import com.smartmobility.repository.MezzoRepository;
import com.smartmobility.service.impl.RicercaMezziServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RicercaMezziServiceImplTest {

    @Mock
    private MezzoRepository mezzoRepository;

    private RicercaMezziServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new RicercaMezziServiceImpl(mezzoRepository);
    }

    @Test
    void visualizzaMezziVicini_WithVehiclesInRange_ReturnsList() {
        Mezzo mezzo1 = TestDataFactory.createMezzo(1L, "bici", StatoMezzo.disponibile, "41.9028,12.4964,0.0", 80.0f, 5.0f);
        Mezzo mezzo2 = TestDataFactory.createMezzo(2L, "scooter", StatoMezzo.disponibile, "41.9030,12.4970,0.0", 60.0f, 8.0f);

        when(mezzoRepository.findByStato(StatoMezzo.disponibile)).thenReturn(List.of(mezzo1, mezzo2));

        List<MezzoResponse> results = service.visualizzaMezziVicini("41.9028,12.4964,0.0", 2.0f);

        assertNotNull(results);
        assertFalse(results.isEmpty());
    }

    @Test
    void visualizzaMezziVicini_WithNoVehiclesInRange_ReturnsEmptyList() {
        Mezzo mezzoFar = TestDataFactory.createMezzo(1L, "bici", StatoMezzo.disponibile, "45.4642,9.1900,0.0", 80.0f, 5.0f);

        when(mezzoRepository.findByStato(StatoMezzo.disponibile)).thenReturn(List.of(mezzoFar));

        List<MezzoResponse> results = service.visualizzaMezziVicini("41.9028,12.4964,0.0", 2.0f);

        assertTrue(results.isEmpty());
    }

    @Test
    void visualizzaMezziVicini_WithNoAvailableVehicles_ReturnsEmptyList() {
        when(mezzoRepository.findByStato(StatoMezzo.disponibile)).thenReturn(List.of());

        List<MezzoResponse> results = service.visualizzaMezziVicini("41.9028,12.4964,0.0", 2.0f);

        assertTrue(results.isEmpty());
    }

    @Test
    void visualizzaMezziVicini_WithExtendedRadius_FindsMoreVehicles() {
        Mezzo mezzoNear = TestDataFactory.createMezzo(1L, "bici", StatoMezzo.disponibile, "41.9030,12.4970,0.0", 80.0f, 5.0f);
        Mezzo mezzoFar = TestDataFactory.createMezzo(2L, "scooter", StatoMezzo.disponibile, "41.9200,12.5200,0.0", 60.0f, 8.0f);

        when(mezzoRepository.findByStato(StatoMezzo.disponibile)).thenReturn(List.of(mezzoNear, mezzoFar));

        List<MezzoResponse> results2km = service.visualizzaMezziVicini("41.9028,12.4964,0.0", 2.0f);
        List<MezzoResponse> results5km = service.visualizzaMezziVicini("41.9028,12.4964,0.0", 5.0f);

        assertTrue(results2km.size() <= results5km.size());
    }

    @Test
    void visualizzaSpecifiche_WithValidId_ReturnsMezzoResponse() {
        Mezzo mezzo = TestDataFactory.createDefaultMezzo();
        when(mezzoRepository.findById(1L)).thenReturn(Optional.of(mezzo));

        MezzoResponse response = service.visualizzaSpecifiche(1L);

        assertNotNull(response);
        assertEquals("bici", response.getTipo());
        assertEquals("disponibile", response.getStato());
    }

    @Test
    void visualizzaSpecifiche_WithInvalidId_ThrowsNotFound() {
        when(mezzoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> service.visualizzaSpecifiche(999L));
    }

    @Test
    void visualizzaMezziVicini_WithMalformedCoordinates_ReturnsEmpty() {
        when(mezzoRepository.findByStato(StatoMezzo.disponibile)).thenReturn(List.of());

        List<MezzoResponse> results = service.visualizzaMezziVicini("invalid,coordinates", 2.0f);
        assertTrue(results.isEmpty());
    }
}

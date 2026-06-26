package com.smartmobility.service;

import com.smartmobility.config.TestDataFactory;
import com.smartmobility.dto.response.ZonaGeograficaResponse;
import com.smartmobility.model.enums.TipoRestrizione;
import com.smartmobility.model.ZonaGeografica;
import com.smartmobility.repository.ZonaGeograficaRepository;
import com.smartmobility.service.impl.GestioneAreeServiceImpl;
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
class GestioneAreeServiceImplTest {

    @Mock
    private ZonaGeograficaRepository zonaGeograficaRepository;

    private GestioneAreeServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new GestioneAreeServiceImpl(zonaGeograficaRepository);
    }

    @Test
    void aggiornaRestrizione_WithExistingZone_Updates() {
        ZonaGeografica existingZone = TestDataFactory.createZonaGeografica(1L, TipoRestrizione.ZTL, "41.9028,12.4964");

        when(zonaGeograficaRepository.findById(1L)).thenReturn(Optional.of(existingZone));

        service.aggiornaRestrizione(1L, "limite_velocita", "Nuovo limite 30km/h", "41.9028,12.4964;41.9030,12.4970");

        assertEquals(TipoRestrizione.limite_velocita, existingZone.getTipoRestrizione());
        assertEquals("Nuovo limite 30km/h", existingZone.getNoteRestrizione());
        verify(zonaGeograficaRepository).save(existingZone);
    }

    @Test
    void aggiornaRestrizione_WithNewZone_Creates() {
        when(zonaGeograficaRepository.findById(1L)).thenReturn(Optional.empty());

        service.aggiornaRestrizione(1L, "divieto_parcheggio", "Divieto sosta", "41.9028,12.4964");

        verify(zonaGeograficaRepository).save(any());
    }

    @Test
    void aggiornaRestrizione_WithInvalidType_ThrowsBadRequest() {
        assertThrows(ResponseStatusException.class,
                () -> service.aggiornaRestrizione(1L, "INVALID_TYPE", "Note", "41.9028,12.4964"));
    }

    @Test
    void analisiConflitti_WithOverlappingZones_ReturnsTrue() {
        ZonaGeografica existingZone = TestDataFactory.createZonaGeografica(1L, TipoRestrizione.ZTL, "41.9000,12.4900;41.9100,12.5000");

        when(zonaGeograficaRepository.findAll()).thenReturn(List.of(existingZone));

        boolean result = service.analisiConflitti("41.9050,12.4950;41.9150,12.5050");

        assertTrue(result);
    }

    @Test
    void analisiConflitti_WithNonOverlappingZones_ReturnsFalse() {
        ZonaGeografica existingZone = TestDataFactory.createZonaGeografica(1L, TipoRestrizione.ZTL, "41.9000,12.4900;41.9100,12.5000");

        when(zonaGeograficaRepository.findAll()).thenReturn(List.of(existingZone));

        boolean result = service.analisiConflitti("45.0000,9.0000;46.0000,10.0000");

        assertFalse(result);
    }

    @Test
    void getZoneGeografiche_ReturnsList() {
        ZonaGeografica zone1 = TestDataFactory.createZonaGeografica(1L, TipoRestrizione.ZTL, "41.9028,12.4964");
        ZonaGeografica zone2 = TestDataFactory.createZonaGeografica(2L, TipoRestrizione.limite_velocita, "41.9030,12.4970");

        when(zonaGeograficaRepository.findAll()).thenReturn(List.of(zone1, zone2));

        List<ZonaGeograficaResponse> zones = service.getZoneGeografiche();

        assertEquals(2, zones.size());
    }

    @Test
    void getZoneGeografiche_WhenEmpty_ReturnsEmptyList() {
        when(zonaGeograficaRepository.findAll()).thenReturn(List.of());

        List<ZonaGeograficaResponse> zones = service.getZoneGeografiche();

        assertTrue(zones.isEmpty());
    }
}

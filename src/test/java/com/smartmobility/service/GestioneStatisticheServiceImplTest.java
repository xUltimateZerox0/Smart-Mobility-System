package com.smartmobility.service;

import com.smartmobility.config.TestDataFactory;
import com.smartmobility.dto.response.CorsaResponse;
import com.smartmobility.dto.response.StatisticheResponse;
import com.smartmobility.model.Corsa;
import com.smartmobility.model.Utente;
import com.smartmobility.model.Mezzo;
import com.smartmobility.repository.CorsaRepository;
import com.smartmobility.service.impl.GestioneStatisticheServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestioneStatisticheServiceImplTest {

    @Mock
    private CorsaRepository corsaRepository;

    private GestioneStatisticheServiceImpl service;

    private Utente utente;
    private Mezzo mezzo;

    @BeforeEach
    void setUp() {
        service = new GestioneStatisticheServiceImpl(corsaRepository);
        utente = TestDataFactory.createDefaultUtente();
        mezzo = TestDataFactory.createDefaultMezzo();
    }

    @Test
    void analisiTratte_WithValidRange_ReturnsStatistics() {
        Corsa corsa1 = TestDataFactory.createCorsa(1L, utente, mezzo, null,
                LocalDateTime.of(2026, 1, 15, 10, 0), 5.0f);
        corsa1.setOrarioFine(LocalDateTime.of(2026, 1, 15, 10, 30));
        corsa1.setCoordinateArrivo("41.9030,12.4970,0.0");

        Corsa corsa2 = TestDataFactory.createCorsa(2L, utente, mezzo, null,
                LocalDateTime.of(2026, 1, 20, 14, 0), 8.0f);
        corsa2.setOrarioFine(LocalDateTime.of(2026, 1, 20, 15, 0));
        corsa2.setCoordinateArrivo("41.9040,12.4980,0.0");

        when(corsaRepository.findByDataRange(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(corsa1, corsa2));

        StatisticheResponse stats = service.analisiTratte("2026-01-01T00:00:00", "2026-01-31T23:59:59");

        assertNotNull(stats);
        assertEquals(2L, stats.getTotalCorse());
        assertTrue(stats.getTotalRicavo() > 0);
        assertTrue(stats.getMediaDurata() > 0);
    }

    @Test
    void analisiTratte_WithNoData_ReturnsEmptyStatistics() {
        when(corsaRepository.findByDataRange(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of());

        StatisticheResponse stats = service.analisiTratte("2025-01-01T00:00:00", "2025-01-01T23:59:59");

        assertEquals(0L, stats.getTotalCorse());
        assertEquals(0.0, stats.getTotalRicavo());
        assertEquals(0.0, stats.getMediaDurata());
    }

    @Test
    void analisiTratte_WithSimpleDateFormat_Works() {
        when(corsaRepository.findByDataRange(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of());

        StatisticheResponse stats = service.analisiTratte("2026-01-01", "2026-01-31");

        assertNotNull(stats);
        assertEquals(0L, stats.getTotalCorse());
    }

    @Test
    void analisiTratte_WithInvalidFormat_ThrowsBadRequest() {
        assertThrows(ResponseStatusException.class,
                () -> service.analisiTratte("not-a-date", "also-not-a-date"));
    }

    @Test
    void generaFileStatistiche_WithValidData_CreatesFile() {
        List<CorsaResponse> corse = List.of(
                new CorsaResponse(1L, 1L, 1L, "2026-01-15T10:00", "2026-01-15T10:30", 2.5, 5.0, "completata")
        );

        assertDoesNotThrow(() -> service.generaFileStatistiche(corse));
    }
}

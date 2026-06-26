package com.smartmobility.service;

import com.smartmobility.config.TestDataFactory;
import com.smartmobility.dto.response.CorsaResponse;
import com.smartmobility.dto.response.MezzoResponse;
import com.smartmobility.dto.response.StatisticheResponse;
import com.smartmobility.model.Corsa;
import com.smartmobility.model.Mezzo;
import com.smartmobility.model.Utente;
import com.smartmobility.model.enums.StatoMezzo;
import com.smartmobility.repository.CorsaRepository;
import com.smartmobility.repository.MezzoRepository;
import com.smartmobility.service.impl.GestioneStatisticheServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestioneStatisticheServiceImplTest {

    @Mock
    private CorsaRepository corsaRepository;

    @Mock
    private MezzoRepository mezzoRepository;

    private GestioneStatisticheServiceImpl service;

    private Utente utente;
    private Mezzo mezzo;

    @BeforeEach
    void setUp() {
        service = new GestioneStatisticheServiceImpl(corsaRepository, mezzoRepository);
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

        when(corsaRepository.findByDataRange(any(), any()))
                .thenReturn(List.of(corsa1, corsa2));

        StatisticheResponse stats = service.analisiTratte("2026-01-01T00:00:00", "2026-01-31T23:59:59");

        assertNotNull(stats);
        assertEquals(2L, stats.getTotalCorse());
        assertTrue(stats.getTotalRicavo() > 0);
        assertTrue(stats.getMediaDurata() > 0);
    }

    @Test
    void analisiTratte_WithNoData_ReturnsEmptyStatistics() {
        when(corsaRepository.findByDataRange(any(), any()))
                .thenReturn(List.of());

        StatisticheResponse stats = service.analisiTratte("2025-01-01T00:00:00", "2025-01-01T23:59:59");

        assertEquals(0L, stats.getTotalCorse());
        assertEquals(0.0, stats.getTotalRicavo());
        assertEquals(0.0, stats.getMediaDurata());
    }

    @Test
    void analisiTratte_WithSimpleDateFormat_Works() {
        when(corsaRepository.findByDataRange(any(), any()))
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
    void analisiTratte_WithDateTimeFormat_Works() {
        when(corsaRepository.findByDataRange(any(), any()))
                .thenReturn(List.of());

        StatisticheResponse stats = service.analisiTratte("2026-01-15 10:00:00", "2026-01-15 11:00:00");

        assertNotNull(stats);
        assertEquals(0L, stats.getTotalCorse());
    }

    @Test
    void analisiTratte_WithCorsaAndNullTimes_SkipsDurationAndDistance() {
        Corsa corsa = TestDataFactory.createCorsa(1L, utente, mezzo, null,
                LocalDateTime.of(2026, 1, 15, 10, 0), 5.0f);
        corsa.setCoordinatePartenza(null);
        corsa.setCoordinateArrivo(null);

        when(corsaRepository.findByDataRange(any(), any()))
                .thenReturn(List.of(corsa));

        StatisticheResponse stats = service.analisiTratte("2026-01-01T00:00:00", "2026-01-31T23:59:59");

        assertEquals(1L, stats.getTotalCorse());
        assertEquals(5.0, stats.getTotalRicavo());
        assertEquals(0.0, stats.getMediaDurata());
    }

    @Test
    void analisiStatoFlotta_WithMultipleMezzi_ReturnsAllResponses() {
        Mezzo m1 = TestDataFactory.createDefaultMezzo();
        m1.setCoordinateMezzo("45.4642,9.1900");
        Mezzo m2 = TestDataFactory.createMezzo(2L, "scooter", StatoMezzo.disponibile, "45.4670,9.1850", 80, 15);

        when(mezzoRepository.findAll()).thenReturn(List.of(m1, m2));

        List<MezzoResponse> result = service.analisiStatoFlotta();

        assertEquals(2, result.size());
        assertEquals("bici", result.get(0).getTipo());
        assertEquals(45.4642, result.get(0).getLatitudine());
        assertEquals(9.1900, result.get(0).getLongitudine());
    }

    @Test
    void analisiStatoFlotta_WhenEmpty_ReturnsEmptyList() {
        when(mezzoRepository.findAll()).thenReturn(List.of());

        List<MezzoResponse> result = service.analisiStatoFlotta();

        assertTrue(result.isEmpty());
    }

    @Test
    void analisiStatoFlotta_WithInvalidCoordinates_SkipsCoordinateParsing() {
        Mezzo m = TestDataFactory.createDefaultMezzo();
        m.setCoordinateMezzo("invalid");

        when(mezzoRepository.findAll()).thenReturn(List.of(m));

        List<MezzoResponse> result = service.analisiStatoFlotta();

        assertEquals(1, result.size());
    }

    @Test
    void getStatisticheFlotta_WithMultipleStati_ReturnsCorrectCounts() {
        Mezzo m1 = TestDataFactory.createDefaultMezzo();
        m1.setStato(StatoMezzo.disponibile);
        Mezzo m2 = TestDataFactory.createMezzo(2L, "scooter", StatoMezzo.in_uso, "45.4670,9.1850", 80, 15);
        Mezzo m3 = TestDataFactory.createMezzo(3L, "auto", StatoMezzo.manutenzione, "45.4700,9.1950", 200, 30);
        Mezzo m4 = TestDataFactory.createMezzo(4L, "bici", StatoMezzo.prenotato, "45.4600,9.1800", 30, 5);

        when(mezzoRepository.findAll()).thenReturn(List.of(m1, m2, m3, m4));

        Map<String, Long> stats = service.getStatisticheFlotta();

        assertEquals(1L, stats.get("disponibile"));
        assertEquals(1L, stats.get("in_uso"));
        assertEquals(1L, stats.get("manutenzione"));
        assertEquals(1L, stats.get("prenotato"));
        assertEquals(0L, stats.get("bloccato"));
        assertEquals(0L, stats.get("sospeso"));
        assertEquals(4L, stats.get("totale"));
    }

    @Test
    void getStatisticheFlotta_WhenEmpty_ReturnsZeros() {
        when(mezzoRepository.findAll()).thenReturn(List.of());

        Map<String, Long> stats = service.getStatisticheFlotta();

        assertEquals(0L, stats.get("totale"));
    }

    @Test
    void generaFileStatistiche_WithValidData_CreatesFile() {
        List<CorsaResponse> corse = List.of(
                new CorsaResponse(1L, 1L, 1L, "2026-01-15T10:00", "2026-01-15T10:30", 2.5, 5.0, "completata")
        );

        String result = service.generaFileStatistiche(corse);

        assertTrue(result.contains("idCorsa"));
        assertTrue(result.contains("1,1,1"));
    }

    @Test
    void generaFileStatistiche_WithEmptyList_ReturnsHeaderOnly() {
        String result = service.generaFileStatistiche(List.of());

        assertTrue(result.contains("idCorsa"));
        assertFalse(result.contains("\n1"));
    }

    @Test
    void analisiTratte_WithCorsaAndOrarioFineNull_SkipsDuration() {
        Corsa corsa = TestDataFactory.createCorsa(1L, utente, mezzo, null,
                LocalDateTime.of(2026, 1, 15, 10, 0), 5.0f);
        corsa.setCoordinatePartenza("41.9028,12.4964");
        corsa.setCoordinateArrivo("41.9030,12.4970");

        when(corsaRepository.findByDataRange(any(), any()))
                .thenReturn(List.of(corsa));

        StatisticheResponse stats = service.analisiTratte("2026-01-01T00:00:00", "2026-01-31T23:59:59");

        assertEquals(1L, stats.getTotalCorse());
        assertTrue(stats.getTotalKm() > 0);
    }
}

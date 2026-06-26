package com.smartmobility.integration;

import com.smartmobility.model.ZonaGeografica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationServiceImplTest {

    private GatewayPagamentoServiceImpl gatewayPagamentoService;
    private MezzoIoTServiceImpl mezzoIoTService;
    private ServizioMappaServiceImpl servizioMappaService;

    @BeforeEach
    void setUp() {
        gatewayPagamentoService = new GatewayPagamentoServiceImpl();
        mezzoIoTService = new MezzoIoTServiceImpl();
        servizioMappaService = new ServizioMappaServiceImpl();
    }

    // ──────────────────────────────────────────────
    // GatewayPagamentoServiceImpl
    // ──────────────────────────────────────────────

    @Test
    void convalidaCarta_cardLength13_returnsTrue() {
        boolean result = gatewayPagamentoService.convalidaCarta(
                "1234567890123", "Visa", "123", "Mario Rossi");
        assertTrue(result);
    }

    @Test
    void convalidaCarta_cardLengthGreaterThan13_returnsTrue() {
        boolean result = gatewayPagamentoService.convalidaCarta(
                "1234567890123456", "Visa", "123", "Mario Rossi");
        assertTrue(result);
    }

    @Test
    void convalidaCarta_cardLengthLessThan13_returnsFalse() {
        boolean result = gatewayPagamentoService.convalidaCarta(
                "123456789012", "Visa", "123", "Mario Rossi");
        assertFalse(result);
    }

    @Test
    void convalidaCarta_nullCard_returnsFalse() {
        boolean result = gatewayPagamentoService.convalidaCarta(
                null, "Visa", "123", "Mario Rossi");
        assertFalse(result);
    }

    @Test
    void convalidaCarta_nullCvv_returnsFalse() {
        boolean result = gatewayPagamentoService.convalidaCarta(
                "1234567890123", "Visa", null, "Mario Rossi");
        assertFalse(result);
    }

    @Test
    void convalidaCarta_shortCvv_returnsFalse() {
        boolean result = gatewayPagamentoService.convalidaCarta(
                "1234567890123", "Visa", "12", "Mario Rossi");
        assertFalse(result);
    }

    @Test
    void convalidaCarta_nullIntestatario_returnsFalse() {
        boolean result = gatewayPagamentoService.convalidaCarta(
                "1234567890123", "Visa", "123", null);
        assertFalse(result);
    }

    @Test
    void convalidaCarta_blankIntestatario_returnsFalse() {
        boolean result = gatewayPagamentoService.convalidaCarta(
                "1234567890123", "Visa", "123", "   ");
        assertFalse(result);
    }

    @Test
    void convalidaCarta_emptyIntestatario_returnsFalse() {
        boolean result = gatewayPagamentoService.convalidaCarta(
                "1234567890123", "Visa", "123", "");
        assertFalse(result);
    }

    @Test
    void convalidaCarta_allNull_returnsFalse() {
        boolean result = gatewayPagamentoService.convalidaCarta(
                null, null, null, null);
        assertFalse(result);
    }

    @Test
    void effettuaPagamento_returnsTrue() {
        boolean result = gatewayPagamentoService.effettuaPagamento(1L, 100L, 25.50);
        assertTrue(result);
    }

    @Test
    void effettuaPagamento_nullIds_returnsTrue() {
        boolean result = gatewayPagamentoService.effettuaPagamento(null, null, 0.0);
        assertTrue(result);
    }

    // ──────────────────────────────────────────────
    // MezzoIoTServiceImpl
    // ──────────────────────────────────────────────

    @Test
    void bloccoMezzoFisico_returnsTrue() {
        boolean result = mezzoIoTService.bloccoMezzoFisico(42L);
        assertTrue(result);
    }

    @Test
    void bloccoMezzoFisico_nullId_returnsTrue() {
        boolean result = mezzoIoTService.bloccoMezzoFisico(null);
        assertTrue(result);
    }

    @Test
    void sbloccoMezzoFisico_returnsTrue() {
        boolean result = mezzoIoTService.sbloccoMezzoFisico(42L);
        assertTrue(result);
    }

    @Test
    void sbloccoMezzoFisico_nullId_returnsTrue() {
        boolean result = mezzoIoTService.sbloccoMezzoFisico(null);
        assertTrue(result);
    }

    // ──────────────────────────────────────────────
    // ServizioMappaServiceImpl
    // ──────────────────────────────────────────────

    @Test
    void getPercorso_returnsMapWithExpectedKeys() {
        Map<String, Object> result = servizioMappaService.getPercorso(
                "41.9028,12.4964", "45.4642,9.1900", new ZonaGeografica());

        assertNotNull(result);
        assertEquals("41.9028,12.4964", result.get("coordinateIniziali"));
        assertEquals("45.4642,9.1900", result.get("coordinateFinali"));
        assertEquals(5.2, result.get("distanza"));
        assertEquals(15, result.get("durata"));
        assertEquals("Percorso calcolato con successo", result.get("messaggio"));
    }

    @Test
    void getPercorso_nullCoordinates_returnsMapWithNullValues() {
        Map<String, Object> result = servizioMappaService.getPercorso(
                null, null, null);

        assertNotNull(result);
        assertNull(result.get("coordinateIniziali"));
        assertNull(result.get("coordinateFinali"));
        assertEquals(5.2, result.get("distanza"));
        assertEquals(15, result.get("durata"));
    }
}

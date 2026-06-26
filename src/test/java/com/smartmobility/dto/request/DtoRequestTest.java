package com.smartmobility.dto.request;

import com.smartmobility.dto.response.CorsaResponse;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("java:S3415")
class DtoRequestTest {

    // ──────────────────────────────────────────────
    // AddPaymentMethodRequest
    // ──────────────────────────────────────────────
    @Test
    void testAddPaymentMethodRequest_noArgConstructor() {
        AddPaymentMethodRequest dto = new AddPaymentMethodRequest();
        assertNull(dto.getIdUtente());
        assertNull(dto.getNumCarta());
        assertNull(dto.getDsCarta());
        assertNull(dto.getCvv());
        assertNull(dto.getIntestatarioCarta());
    }

    @Test
    void testAddPaymentMethodRequest_allArgsConstructor() {
        AddPaymentMethodRequest dto = new AddPaymentMethodRequest(1L, "1234", "Visa", "123", "Mario Rossi");
        assertEquals(1L, dto.getIdUtente());
        assertEquals("1234", dto.getNumCarta());
        assertEquals("Visa", dto.getDsCarta());
        assertEquals("123", dto.getCvv());
        assertEquals("Mario Rossi", dto.getIntestatarioCarta());
    }

    @Test
    void testAddPaymentMethodRequest_setters() {
        AddPaymentMethodRequest dto = new AddPaymentMethodRequest();
        dto.setIdUtente(2L);
        dto.setNumCarta("5678");
        dto.setDsCarta("Mastercard");
        dto.setCvv("456");
        dto.setIntestatarioCarta("Lucia Bianchi");
        assertEquals(2L, dto.getIdUtente());
        assertEquals("5678", dto.getNumCarta());
        assertEquals("Mastercard", dto.getDsCarta());
        assertEquals("456", dto.getCvv());
        assertEquals("Lucia Bianchi", dto.getIntestatarioCarta());
    }

    @Test
    void testAddPaymentMethodRequest_equals() {
        AddPaymentMethodRequest a = new AddPaymentMethodRequest(1L, "1234", "Visa", "123", "Mario Rossi");
        AddPaymentMethodRequest b = new AddPaymentMethodRequest(1L, "1234", "Visa", "123", "Mario Rossi");
        AddPaymentMethodRequest c = new AddPaymentMethodRequest(2L, "9999", "Amex", "999", "Altro");
        // same object
        assertEquals(a, a);
        // equal object
        assertEquals(a, b);
        // different type
        assertNotEquals(a, "string");
        // null
        assertNotEquals(a, null);
        // different values
        assertNotEquals(a, c);
    }

    @Test
    void testAddPaymentMethodRequest_hashCode() {
        AddPaymentMethodRequest a = new AddPaymentMethodRequest(1L, "1234", "Visa", "123", "Mario Rossi");
        AddPaymentMethodRequest b = new AddPaymentMethodRequest(1L, "1234", "Visa", "123", "Mario Rossi");
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testAddPaymentMethodRequest_toString() {
        AddPaymentMethodRequest dto = new AddPaymentMethodRequest(1L, "1234", "Visa", "123", "Mario Rossi");
        assertNotNull(dto.toString());
        assertTrue(dto.toString().contains("AddPaymentMethodRequest"));
    }

    // ──────────────────────────────────────────────
    // AnalyzeStatisticsRequest
    // ──────────────────────────────────────────────
    @Test
    void testAnalyzeStatisticsRequest_noArgConstructor() {
        AnalyzeStatisticsRequest dto = new AnalyzeStatisticsRequest();
        assertNull(dto.getDataInizio());
        assertNull(dto.getDataFine());
    }

    @Test
    void testAnalyzeStatisticsRequest_allArgsConstructor() {
        AnalyzeStatisticsRequest dto = new AnalyzeStatisticsRequest("2024-01-01", "2024-12-31");
        assertEquals("2024-01-01", dto.getDataInizio());
        assertEquals("2024-12-31", dto.getDataFine());
    }

    @Test
    void testAnalyzeStatisticsRequest_setters() {
        AnalyzeStatisticsRequest dto = new AnalyzeStatisticsRequest();
        dto.setDataInizio("2024-06-01");
        dto.setDataFine("2024-06-30");
        assertEquals("2024-06-01", dto.getDataInizio());
        assertEquals("2024-06-30", dto.getDataFine());
    }

    @Test
    void testAnalyzeStatisticsRequest_equals() {
        AnalyzeStatisticsRequest a = new AnalyzeStatisticsRequest("2024-01-01", "2024-12-31");
        AnalyzeStatisticsRequest b = new AnalyzeStatisticsRequest("2024-01-01", "2024-12-31");
        AnalyzeStatisticsRequest c = new AnalyzeStatisticsRequest("2025-01-01", "2025-12-31");
        assertEquals(a, a);
        assertEquals(a, b);
        assertNotEquals(a, "string");
        assertNotEquals(a, null);
        assertNotEquals(a, c);
    }

    @Test
    void testAnalyzeStatisticsRequest_hashCode() {
        AnalyzeStatisticsRequest a = new AnalyzeStatisticsRequest("2024-01-01", "2024-12-31");
        AnalyzeStatisticsRequest b = new AnalyzeStatisticsRequest("2024-01-01", "2024-12-31");
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testAnalyzeStatisticsRequest_toString() {
        AnalyzeStatisticsRequest dto = new AnalyzeStatisticsRequest("2024-01-01", "2024-12-31");
        assertNotNull(dto.toString());
        assertTrue(dto.toString().contains("AnalyzeStatisticsRequest"));
    }

    // ──────────────────────────────────────────────
    // ConflictCheckRequest
    // ──────────────────────────────────────────────
    @Test
    void testConflictCheckRequest_noArgConstructor() {
        ConflictCheckRequest dto = new ConflictCheckRequest();
        assertNull(dto.getZona());
    }

    @Test
    void testConflictCheckRequest_allArgsConstructor() {
        ConflictCheckRequest dto = new ConflictCheckRequest("zonaA");
        assertEquals("zonaA", dto.getZona());
    }

    @Test
    void testConflictCheckRequest_setters() {
        ConflictCheckRequest dto = new ConflictCheckRequest();
        dto.setZona("zonaB");
        assertEquals("zonaB", dto.getZona());
    }

    @Test
    void testConflictCheckRequest_equals() {
        ConflictCheckRequest a = new ConflictCheckRequest("zonaA");
        ConflictCheckRequest b = new ConflictCheckRequest("zonaA");
        ConflictCheckRequest c = new ConflictCheckRequest("zonaB");
        assertEquals(a, a);
        assertEquals(a, b);
        assertNotEquals(a, "string");
        assertNotEquals(a, null);
        assertNotEquals(a, c);
    }

    @Test
    void testConflictCheckRequest_hashCode() {
        ConflictCheckRequest a = new ConflictCheckRequest("zonaA");
        ConflictCheckRequest b = new ConflictCheckRequest("zonaA");
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testConflictCheckRequest_toString() {
        ConflictCheckRequest dto = new ConflictCheckRequest("zonaA");
        assertNotNull(dto.toString());
        assertTrue(dto.toString().contains("ConflictCheckRequest"));
    }

    // ──────────────────────────────────────────────
    // CorrectiveActionRequest
    // ──────────────────────────────────────────────
    @Test
    void testCorrectiveActionRequest_noArgConstructor() {
        CorrectiveActionRequest dto = new CorrectiveActionRequest();
        assertNull(dto.getAzione());
    }

    @Test
    void testCorrectiveActionRequest_allArgsConstructor() {
        CorrectiveActionRequest dto = new CorrectiveActionRequest("azione1");
        assertEquals("azione1", dto.getAzione());
    }

    @Test
    void testCorrectiveActionRequest_setters() {
        CorrectiveActionRequest dto = new CorrectiveActionRequest();
        dto.setAzione("azione2");
        assertEquals("azione2", dto.getAzione());
    }

    @Test
    void testCorrectiveActionRequest_equals() {
        CorrectiveActionRequest a = new CorrectiveActionRequest("azione1");
        CorrectiveActionRequest b = new CorrectiveActionRequest("azione1");
        CorrectiveActionRequest c = new CorrectiveActionRequest("azione2");
        assertEquals(a, a);
        assertEquals(a, b);
        assertNotEquals(a, "string");
        assertNotEquals(a, null);
        assertNotEquals(a, c);
    }

    @Test
    void testCorrectiveActionRequest_hashCode() {
        CorrectiveActionRequest a = new CorrectiveActionRequest("azione1");
        CorrectiveActionRequest b = new CorrectiveActionRequest("azione1");
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testCorrectiveActionRequest_toString() {
        CorrectiveActionRequest dto = new CorrectiveActionRequest("azione1");
        assertNotNull(dto.toString());
        assertTrue(dto.toString().contains("CorrectiveActionRequest"));
    }

    // ──────────────────────────────────────────────
    // ExportStatisticsRequest
    // ──────────────────────────────────────────────
    @Test
    void testExportStatisticsRequest_noArgConstructor() {
        ExportStatisticsRequest dto = new ExportStatisticsRequest();
        assertNull(dto.getCorse());
    }

    @Test
    void testExportStatisticsRequest_allArgsConstructor() {
        CorsaResponse corsa = new CorsaResponse(1L, 10L, 100L, "2024-01-01", "2024-01-01T12:00", 5.0, 10.0, "completata");
        List<CorsaResponse> corse = Collections.singletonList(corsa);
        ExportStatisticsRequest dto = new ExportStatisticsRequest(corse);
        assertEquals(1, dto.getCorse().size());
        assertEquals(corsa, dto.getCorse().get(0));
    }

    @Test
    void testExportStatisticsRequest_setters() {
        ExportStatisticsRequest dto = new ExportStatisticsRequest();
        CorsaResponse corsa = new CorsaResponse(2L, 20L, 200L, "2024-02-01", "2024-02-01T13:00", 3.0, 5.0, "in_corso");
        List<CorsaResponse> corse = Collections.singletonList(corsa);
        dto.setCorse(corse);
        assertEquals(1, dto.getCorse().size());
        assertEquals(corsa, dto.getCorse().get(0));
    }

    @Test
    void testExportStatisticsRequest_equals() {
        CorsaResponse corsa = new CorsaResponse(1L, 10L, 100L, "2024-01-01", "2024-01-01T12:00", 5.0, 10.0, "completata");
        List<CorsaResponse> corse = Collections.singletonList(corsa);
        ExportStatisticsRequest a = new ExportStatisticsRequest(corse);
        ExportStatisticsRequest b = new ExportStatisticsRequest(corse);
        ExportStatisticsRequest c = new ExportStatisticsRequest(Collections.emptyList());
        assertEquals(a, a);
        assertEquals(a, b);
        assertNotEquals(a, "string");
        assertNotEquals(a, null);
        assertNotEquals(a, c);
    }

    @Test
    void testExportStatisticsRequest_hashCode() {
        CorsaResponse corsa = new CorsaResponse(1L, 10L, 100L, "2024-01-01", "2024-01-01T12:00", 5.0, 10.0, "completata");
        List<CorsaResponse> corse = Collections.singletonList(corsa);
        ExportStatisticsRequest a = new ExportStatisticsRequest(corse);
        ExportStatisticsRequest b = new ExportStatisticsRequest(corse);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testExportStatisticsRequest_toString() {
        ExportStatisticsRequest dto = new ExportStatisticsRequest(Collections.emptyList());
        assertNotNull(dto.toString());
        assertTrue(dto.toString().contains("ExportStatisticsRequest"));
    }

    // ──────────────────────────────────────────────
    // LoginRequest (record)
    // ──────────────────────────────────────────────
    @Test
    void testLoginRequest_constructorAndAccessors() {
        LoginRequest dto = new LoginRequest("test@example.com", "password123");
        assertEquals("test@example.com", dto.email());
        assertEquals("password123", dto.password());
    }

    @Test
    void testLoginRequest_equals() {
        LoginRequest a = new LoginRequest("test@example.com", "password123");
        LoginRequest b = new LoginRequest("test@example.com", "password123");
        LoginRequest c = new LoginRequest("other@example.com", "otherpass");
        assertEquals(a, a);
        assertEquals(a, b);
        assertNotEquals(a, "string");
        assertNotEquals(a, null);
        assertNotEquals(a, c);
    }

    @Test
    void testLoginRequest_hashCode() {
        LoginRequest a = new LoginRequest("test@example.com", "password123");
        LoginRequest b = new LoginRequest("test@example.com", "password123");
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testLoginRequest_toString() {
        LoginRequest dto = new LoginRequest("test@example.com", "password123");
        assertNotNull(dto.toString());
    }

    // ──────────────────────────────────────────────
    // LogoutRequest
    // ──────────────────────────────────────────────
    @Test
    void testLogoutRequest_noArgConstructor() {
        LogoutRequest dto = new LogoutRequest();
        assertNull(dto.getEmail());
    }

    @Test
    void testLogoutRequest_allArgsConstructor() {
        LogoutRequest dto = new LogoutRequest("test@example.com");
        assertEquals("test@example.com", dto.getEmail());
    }

    @Test
    void testLogoutRequest_setters() {
        LogoutRequest dto = new LogoutRequest();
        dto.setEmail("other@example.com");
        assertEquals("other@example.com", dto.getEmail());
    }

    @Test
    void testLogoutRequest_equals() {
        LogoutRequest a = new LogoutRequest("test@example.com");
        LogoutRequest b = new LogoutRequest("test@example.com");
        LogoutRequest c = new LogoutRequest("other@example.com");
        assertEquals(a, a);
        assertEquals(a, b);
        assertNotEquals(a, "string");
        assertNotEquals(a, null);
        assertNotEquals(a, c);
    }

    @Test
    void testLogoutRequest_hashCode() {
        LogoutRequest a = new LogoutRequest("test@example.com");
        LogoutRequest b = new LogoutRequest("test@example.com");
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testLogoutRequest_toString() {
        LogoutRequest dto = new LogoutRequest("test@example.com");
        assertNotNull(dto.toString());
        assertTrue(dto.toString().contains("LogoutRequest"));
    }

    // ──────────────────────────────────────────────
    // NearbyVehiclesRequest
    // ──────────────────────────────────────────────
    @Test
    void testNearbyVehiclesRequest_noArgConstructor() {
        NearbyVehiclesRequest dto = new NearbyVehiclesRequest();
        assertNull(dto.getCoordinateUtente());
        assertEquals(0.0f, dto.getRaggio());
    }

    @Test
    void testNearbyVehiclesRequest_allArgsConstructor() {
        NearbyVehiclesRequest dto = new NearbyVehiclesRequest("41.9028,12.4964", 3.5f);
        assertEquals("41.9028,12.4964", dto.getCoordinateUtente());
        assertEquals(3.5f, dto.getRaggio());
    }

    @Test
    void testNearbyVehiclesRequest_setters() {
        NearbyVehiclesRequest dto = new NearbyVehiclesRequest();
        dto.setCoordinateUtente("45.4642,9.1900");
        dto.setRaggio(4.0f);
        assertEquals("45.4642,9.1900", dto.getCoordinateUtente());
        assertEquals(4.0f, dto.getRaggio());
    }

    @Test
    void testNearbyVehiclesRequest_equals() {
        NearbyVehiclesRequest a = new NearbyVehiclesRequest("41.9028,12.4964", 3.5f);
        NearbyVehiclesRequest b = new NearbyVehiclesRequest("41.9028,12.4964", 3.5f);
        NearbyVehiclesRequest c = new NearbyVehiclesRequest("45.4642,9.1900", 5.0f);
        assertEquals(a, a);
        assertEquals(a, b);
        assertNotEquals(a, "string");
        assertNotEquals(a, null);
        assertNotEquals(a, c);
    }

    @Test
    void testNearbyVehiclesRequest_hashCode() {
        NearbyVehiclesRequest a = new NearbyVehiclesRequest("41.9028,12.4964", 3.5f);
        NearbyVehiclesRequest b = new NearbyVehiclesRequest("41.9028,12.4964", 3.5f);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testNearbyVehiclesRequest_toString() {
        NearbyVehiclesRequest dto = new NearbyVehiclesRequest("41.9028,12.4964", 3.5f);
        assertNotNull(dto.toString());
        assertTrue(dto.toString().contains("NearbyVehiclesRequest"));
    }

    // ──────────────────────────────────────────────
    // PaymentMethodSelectionRequest
    // ──────────────────────────────────────────────
    @Test
    void testPaymentMethodSelectionRequest_noArgConstructor() {
        PaymentMethodSelectionRequest dto = new PaymentMethodSelectionRequest();
        assertNull(dto.getIdMetodoPagamento());
    }

    @Test
    void testPaymentMethodSelectionRequest_allArgsConstructor() {
        PaymentMethodSelectionRequest dto = new PaymentMethodSelectionRequest(42L);
        assertEquals(42L, dto.getIdMetodoPagamento());
    }

    @Test
    void testPaymentMethodSelectionRequest_setters() {
        PaymentMethodSelectionRequest dto = new PaymentMethodSelectionRequest();
        dto.setIdMetodoPagamento(99L);
        assertEquals(99L, dto.getIdMetodoPagamento());
    }

    @Test
    void testPaymentMethodSelectionRequest_equals() {
        PaymentMethodSelectionRequest a = new PaymentMethodSelectionRequest(42L);
        PaymentMethodSelectionRequest b = new PaymentMethodSelectionRequest(42L);
        PaymentMethodSelectionRequest c = new PaymentMethodSelectionRequest(99L);
        assertEquals(a, a);
        assertEquals(a, b);
        assertNotEquals(a, "string");
        assertNotEquals(a, null);
        assertNotEquals(a, c);
    }

    @Test
    void testPaymentMethodSelectionRequest_hashCode() {
        PaymentMethodSelectionRequest a = new PaymentMethodSelectionRequest(42L);
        PaymentMethodSelectionRequest b = new PaymentMethodSelectionRequest(42L);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testPaymentMethodSelectionRequest_toString() {
        PaymentMethodSelectionRequest dto = new PaymentMethodSelectionRequest(42L);
        assertNotNull(dto.toString());
        assertTrue(dto.toString().contains("PaymentMethodSelectionRequest"));
    }

    // ──────────────────────────────────────────────
    // PrenotazioneRequest (record)
    // ──────────────────────────────────────────────
    @Test
    void testPrenotazioneRequest_constructorAndAccessors() {
        PrenotazioneRequest dto = new PrenotazioneRequest(1L, 10L, "2024-06-01T10:00");
        assertEquals(1L, dto.idMezzo());
        assertEquals(10L, dto.idUtente());
        assertEquals("2024-06-01T10:00", dto.orarioInizio());
    }

    @Test
    void testPrenotazioneRequest_equals() {
        PrenotazioneRequest a = new PrenotazioneRequest(1L, 10L, "2024-06-01T10:00");
        PrenotazioneRequest b = new PrenotazioneRequest(1L, 10L, "2024-06-01T10:00");
        PrenotazioneRequest c = new PrenotazioneRequest(2L, 20L, "2024-07-01T10:00");
        assertEquals(a, a);
        assertEquals(a, b);
        assertNotEquals(a, "string");
        assertNotEquals(a, null);
        assertNotEquals(a, c);
    }

    @Test
    void testPrenotazioneRequest_hashCode() {
        PrenotazioneRequest a = new PrenotazioneRequest(1L, 10L, "2024-06-01T10:00");
        PrenotazioneRequest b = new PrenotazioneRequest(1L, 10L, "2024-06-01T10:00");
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testPrenotazioneRequest_toString() {
        PrenotazioneRequest dto = new PrenotazioneRequest(1L, 10L, "2024-06-01T10:00");
        assertNotNull(dto.toString());
    }

    // ──────────────────────────────────────────────
    // ProcessPaymentRequest
    // ──────────────────────────────────────────────
    @Test
    void testProcessPaymentRequest_noArgConstructor() {
        ProcessPaymentRequest dto = new ProcessPaymentRequest();
        assertNull(dto.getIdUtente());
        assertNull(dto.getIdMetodoPagamento());
        assertNull(dto.getCosto());
        assertNull(dto.getIdCorsa());
    }

    @Test
    void testProcessPaymentRequest_allArgsConstructor() {
        ProcessPaymentRequest dto = new ProcessPaymentRequest(1L, 2L, 3L, 9.99);
        assertEquals(1L, dto.getIdUtente());
        assertEquals(2L, dto.getIdMetodoPagamento());
        assertEquals(9.99, dto.getCosto());
        assertEquals(3L, dto.getIdCorsa());
    }

    @Test
    void testProcessPaymentRequest_setters() {
        ProcessPaymentRequest dto = new ProcessPaymentRequest();
        dto.setIdUtente(4L);
        dto.setIdMetodoPagamento(5L);
        dto.setIdCorsa(6L);
        dto.setCosto(19.99);
        assertEquals(4L, dto.getIdUtente());
        assertEquals(5L, dto.getIdMetodoPagamento());
        assertEquals(6L, dto.getIdCorsa());
        assertEquals(19.99, dto.getCosto());
    }

    @Test
    void testProcessPaymentRequest_equals() {
        ProcessPaymentRequest a = new ProcessPaymentRequest(1L, 2L, 3L, 9.99);
        ProcessPaymentRequest b = new ProcessPaymentRequest(1L, 2L, 3L, 9.99);
        ProcessPaymentRequest c = new ProcessPaymentRequest(9L, 8L, 7L, 1.00);
        assertEquals(a, a);
        assertEquals(a, b);
        assertNotEquals(a, "string");
        assertNotEquals(a, null);
        assertNotEquals(a, c);
    }

    @Test
    void testProcessPaymentRequest_hashCode() {
        ProcessPaymentRequest a = new ProcessPaymentRequest(1L, 2L, 3L, 9.99);
        ProcessPaymentRequest b = new ProcessPaymentRequest(1L, 2L, 3L, 9.99);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testProcessPaymentRequest_toString() {
        ProcessPaymentRequest dto = new ProcessPaymentRequest(1L, 2L, 3L, 9.99);
        assertNotNull(dto.toString());
        assertTrue(dto.toString().contains("ProcessPaymentRequest"));
    }

    // ──────────────────────────────────────────────
    // RegisterRequest
    // ──────────────────────────────────────────────
    @Test
    void testRegisterRequest_noArgConstructor() {
        RegisterRequest dto = new RegisterRequest();
        assertNull(dto.getNome());
        assertNull(dto.getCognome());
        assertNull(dto.getEmail());
        assertNull(dto.getPassword());
        assertNull(dto.getDatanascita());
    }

    @Test
    void testRegisterRequest_allArgsConstructor() {
        RegisterRequest dto = new RegisterRequest("Mario", "Rossi", "m.rossi@example.com", "secret1", "1990-01-01");
        assertEquals("Mario", dto.getNome());
        assertEquals("Rossi", dto.getCognome());
        assertEquals("m.rossi@example.com", dto.getEmail());
        assertEquals("secret1", dto.getPassword());
        assertEquals("1990-01-01", dto.getDatanascita());
    }

    @Test
    void testRegisterRequest_setters() {
        RegisterRequest dto = new RegisterRequest();
        dto.setNome("Lucia");
        dto.setCognome("Bianchi");
        dto.setEmail("l.bianchi@example.com");
        dto.setPassword("secret2");
        dto.setDatanascita("1995-05-05");
        assertEquals("Lucia", dto.getNome());
        assertEquals("Bianchi", dto.getCognome());
        assertEquals("l.bianchi@example.com", dto.getEmail());
        assertEquals("secret2", dto.getPassword());
        assertEquals("1995-05-05", dto.getDatanascita());
    }

    @Test
    void testRegisterRequest_equals() {
        RegisterRequest a = new RegisterRequest("Mario", "Rossi", "m.rossi@example.com", "secret1", "1990-01-01");
        RegisterRequest b = new RegisterRequest("Mario", "Rossi", "m.rossi@example.com", "secret1", "1990-01-01");
        RegisterRequest c = new RegisterRequest("Altro", "Nome", "a.nome@example.com", "xxx", "2000-01-01");
        assertEquals(a, a);
        assertEquals(a, b);
        assertNotEquals(a, "string");
        assertNotEquals(a, null);
        assertNotEquals(a, c);
    }

    @Test
    void testRegisterRequest_hashCode() {
        RegisterRequest a = new RegisterRequest("Mario", "Rossi", "m.rossi@example.com", "secret1", "1990-01-01");
        RegisterRequest b = new RegisterRequest("Mario", "Rossi", "m.rossi@example.com", "secret1", "1990-01-01");
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testRegisterRequest_toString() {
        RegisterRequest dto = new RegisterRequest("Mario", "Rossi", "m.rossi@example.com", "secret1", "1990-01-01");
        assertNotNull(dto.toString());
        assertTrue(dto.toString().contains("RegisterRequest"));
    }

    // ──────────────────────────────────────────────
    // RouteRequest
    // ──────────────────────────────────────────────
    @Test
    void testRouteRequest_noArgConstructor() {
        RouteRequest dto = new RouteRequest();
        assertNull(dto.getCoordinateUtente());
        assertNull(dto.getDestinazione());
    }

    @Test
    void testRouteRequest_allArgsConstructor() {
        RouteRequest dto = new RouteRequest("41.9028,12.4964", "45.4642,9.1900");
        assertEquals("41.9028,12.4964", dto.getCoordinateUtente());
        assertEquals("45.4642,9.1900", dto.getDestinazione());
    }

    @Test
    void testRouteRequest_setters() {
        RouteRequest dto = new RouteRequest();
        dto.setCoordinateUtente("40.8518,14.2681");
        dto.setDestinazione("43.7696,11.2558");
        assertEquals("40.8518,14.2681", dto.getCoordinateUtente());
        assertEquals("43.7696,11.2558", dto.getDestinazione());
    }

    @Test
    void testRouteRequest_equals() {
        RouteRequest a = new RouteRequest("41.9028,12.4964", "45.4642,9.1900");
        RouteRequest b = new RouteRequest("41.9028,12.4964", "45.4642,9.1900");
        RouteRequest c = new RouteRequest("10.0000,20.0000", "30.0000,40.0000");
        assertEquals(a, a);
        assertEquals(a, b);
        assertNotEquals(a, "string");
        assertNotEquals(a, null);
        assertNotEquals(a, c);
    }

    @Test
    void testRouteRequest_hashCode() {
        RouteRequest a = new RouteRequest("41.9028,12.4964", "45.4642,9.1900");
        RouteRequest b = new RouteRequest("41.9028,12.4964", "45.4642,9.1900");
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testRouteRequest_toString() {
        RouteRequest dto = new RouteRequest("41.9028,12.4964", "45.4642,9.1900");
        assertNotNull(dto.toString());
        assertTrue(dto.toString().contains("RouteRequest"));
    }

    // ──────────────────────────────────────────────
    // StartRideRequest
    // ──────────────────────────────────────────────
    @Test
    void testStartRideRequest_noArgConstructor() {
        StartRideRequest dto = new StartRideRequest();
        assertNull(dto.getIdMezzo());
        assertNull(dto.getIdUtente());
        assertNull(dto.getQrCode());
    }

    @Test
    void testStartRideRequest_allArgsConstructor() {
        StartRideRequest dto = new StartRideRequest(1L, 10L, "QR123");
        assertEquals(1L, dto.getIdMezzo());
        assertEquals(10L, dto.getIdUtente());
        assertEquals("QR123", dto.getQrCode());
    }

    @Test
    void testStartRideRequest_setters() {
        StartRideRequest dto = new StartRideRequest();
        dto.setIdMezzo(2L);
        dto.setIdUtente(20L);
        dto.setQrCode("QR999");
        assertEquals(2L, dto.getIdMezzo());
        assertEquals(20L, dto.getIdUtente());
        assertEquals("QR999", dto.getQrCode());
    }

    @Test
    void testStartRideRequest_equals() {
        StartRideRequest a = new StartRideRequest(1L, 10L, "QR123");
        StartRideRequest b = new StartRideRequest(1L, 10L, "QR123");
        StartRideRequest c = new StartRideRequest(3L, 30L, "QR999");
        assertEquals(a, a);
        assertEquals(a, b);
        assertNotEquals(a, "string");
        assertNotEquals(a, null);
        assertNotEquals(a, c);
    }

    @Test
    void testStartRideRequest_hashCode() {
        StartRideRequest a = new StartRideRequest(1L, 10L, "QR123");
        StartRideRequest b = new StartRideRequest(1L, 10L, "QR123");
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testStartRideRequest_toString() {
        StartRideRequest dto = new StartRideRequest(1L, 10L, "QR123");
        assertNotNull(dto.toString());
        assertTrue(dto.toString().contains("StartRideRequest"));
    }

    // ──────────────────────────────────────────────
    // UnlockRequest
    // ──────────────────────────────────────────────
    @Test
    void testUnlockRequest_noArgConstructor() {
        UnlockRequest dto = new UnlockRequest();
        assertNull(dto.getQrCode());
    }

    @Test
    void testUnlockRequest_allArgsConstructor() {
        UnlockRequest dto = new UnlockRequest("QR-UNLOCK-001");
        assertEquals("QR-UNLOCK-001", dto.getQrCode());
    }

    @Test
    void testUnlockRequest_setters() {
        UnlockRequest dto = new UnlockRequest();
        dto.setQrCode("QR-UNLOCK-002");
        assertEquals("QR-UNLOCK-002", dto.getQrCode());
    }

    @Test
    void testUnlockRequest_equals() {
        UnlockRequest a = new UnlockRequest("QR-UNLOCK-001");
        UnlockRequest b = new UnlockRequest("QR-UNLOCK-001");
        UnlockRequest c = new UnlockRequest("QR-UNLOCK-999");
        assertEquals(a, a);
        assertEquals(a, b);
        assertNotEquals(a, "string");
        assertNotEquals(a, null);
        assertNotEquals(a, c);
    }

    @Test
    void testUnlockRequest_hashCode() {
        UnlockRequest a = new UnlockRequest("QR-UNLOCK-001");
        UnlockRequest b = new UnlockRequest("QR-UNLOCK-001");
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testUnlockRequest_toString() {
        UnlockRequest dto = new UnlockRequest("QR-UNLOCK-001");
        assertNotNull(dto.toString());
        assertTrue(dto.toString().contains("UnlockRequest"));
    }

    // ──────────────────────────────────────────────
    // UpdateZoneRequest
    // ──────────────────────────────────────────────
    @Test
    void testUpdateZoneRequest_noArgConstructor() {
        UpdateZoneRequest dto = new UpdateZoneRequest();
        assertNull(dto.getTipoRestrizione());
        assertNull(dto.getNoteRestrizione());
        assertNull(dto.getZona());
    }

    @Test
    void testUpdateZoneRequest_allArgsConstructor() {
        UpdateZoneRequest dto = new UpdateZoneRequest("divieto", "nessun accesso", "zona3");
        assertEquals("divieto", dto.getTipoRestrizione());
        assertEquals("nessun accesso", dto.getNoteRestrizione());
        assertEquals("zona3", dto.getZona());
    }

    @Test
    void testUpdateZoneRequest_setters() {
        UpdateZoneRequest dto = new UpdateZoneRequest();
        dto.setTipoRestrizione("limite");
        dto.setNoteRestrizione("solo pedoni");
        dto.setZona("zona4");
        assertEquals("limite", dto.getTipoRestrizione());
        assertEquals("solo pedoni", dto.getNoteRestrizione());
        assertEquals("zona4", dto.getZona());
    }

    @Test
    void testUpdateZoneRequest_equals() {
        UpdateZoneRequest a = new UpdateZoneRequest("divieto", "nessun accesso", "zona3");
        UpdateZoneRequest b = new UpdateZoneRequest("divieto", "nessun accesso", "zona3");
        UpdateZoneRequest c = new UpdateZoneRequest("libero", "tutti", "zonaX");
        assertEquals(a, a);
        assertEquals(a, b);
        assertNotEquals(a, "string");
        assertNotEquals(a, null);
        assertNotEquals(a, c);
    }

    @Test
    void testUpdateZoneRequest_hashCode() {
        UpdateZoneRequest a = new UpdateZoneRequest("divieto", "nessun accesso", "zona3");
        UpdateZoneRequest b = new UpdateZoneRequest("divieto", "nessun accesso", "zona3");
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testUpdateZoneRequest_toString() {
        UpdateZoneRequest dto = new UpdateZoneRequest("divieto", "nessun accesso", "zona3");
        assertNotNull(dto.toString());
        assertTrue(dto.toString().contains("UpdateZoneRequest"));
    }
}

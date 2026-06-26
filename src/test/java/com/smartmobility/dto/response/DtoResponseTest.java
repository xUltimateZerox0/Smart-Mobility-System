package com.smartmobility.dto.response;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("java:S3415")
class DtoResponseTest {

    // ========================================================================
    // AuthResponse
    // ========================================================================
    @Test
    void testAuthResponseNoArgConstructor() {
        AuthResponse r = new AuthResponse();
        assertNull(r.getToken());
        assertNull(r.getEmail());
        assertNull(r.getRuolo());
        assertNull(r.getIdUtente());
        assertNull(r.getTipo());
    }

    @Test
    void testAuthResponseAllArgsConstructor() {
        AuthResponse r = new AuthResponse("tok", "a@b.c", "admin", 1L, "jwt");
        assertEquals("tok", r.getToken());
        assertEquals("a@b.c", r.getEmail());
        assertEquals("admin", r.getRuolo());
        assertEquals(1L, r.getIdUtente());
        assertEquals("jwt", r.getTipo());
    }

    @Test
    void testAuthResponseSettersAndGetters() {
        AuthResponse r = new AuthResponse();
        r.setToken("t1");
        r.setEmail("e@x");
        r.setRuolo("user");
        r.setIdUtente(99L);
        r.setTipo("oauth");

        assertEquals("t1", r.getToken());
        assertEquals("e@x", r.getEmail());
        assertEquals("user", r.getRuolo());
        assertEquals(99L, r.getIdUtente());
        assertEquals("oauth", r.getTipo());
    }

    @Test
    void testAuthResponseEqualsHashCodeToString() {
        AuthResponse a = new AuthResponse("t", "e", "r", 1L, "tip");
        AuthResponse b = new AuthResponse("t", "e", "r", 1L, "tip");
        AuthResponse c = new AuthResponse("x", "y", "z", 2L, "oth");
        // self
        assertEquals(a, a);
        assertEquals(a.hashCode(), a.hashCode());
        // equal
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        // not equal
        assertNotEquals(a, c);
        assertNotEquals(a.hashCode(), c.hashCode());
        // null / different type
        assertNotEquals(a, null);
        assertNotEquals(a, "string");
        // toString
        String s = a.toString();
        assertTrue(s.contains("token="));
        assertTrue(s.contains("email="));
        assertTrue(s.contains("ruolo="));
        assertTrue(s.contains("idUtente="));
        assertTrue(s.contains("tipo="));
    }

    // ========================================================================
    // CorsaResponse - 4 constructors (no-arg + 3 parameterized)
    // ========================================================================
    @Test
    void testCorsaResponseNoArgConstructor() {
        CorsaResponse r = new CorsaResponse();
        assertNull(r.getId());
        assertNull(r.getIdUtente());
        assertNull(r.getIdMezzo());
        assertNull(r.getDataInizio());
        assertNull(r.getDataFine());
        assertNull(r.getCosto());
        assertNull(r.getDistanza());
        assertNull(r.getStato());
        assertNull(r.getIdMetodoPagamento());
        assertNull(r.getMetodoPagamentoLabel());
        assertFalse(r.isPaused());
        assertEquals(0.0, r.getTotalePausaMillis());
    }

    @Test
    void testCorsaResponse8ArgConstructor() {
        CorsaResponse r = new CorsaResponse(1L, 2L, 3L, "2024-01-01", "2024-01-02", 10.5, 5.2, "completata");
        assertEquals(1L, r.getId());
        assertEquals(2L, r.getIdUtente());
        assertEquals(3L, r.getIdMezzo());
        assertEquals("2024-01-01", r.getDataInizio());
        assertEquals("2024-01-02", r.getDataFine());
        assertEquals(10.5, r.getCosto());
        assertEquals(5.2, r.getDistanza());
        assertEquals("completata", r.getStato());
        assertNull(r.getIdMetodoPagamento());
        assertNull(r.getMetodoPagamentoLabel());
        assertFalse(r.isPaused());
        assertEquals(0.0, r.getTotalePausaMillis());
    }

    @Test
    void testCorsaResponse10ArgConstructor() {
        CorsaResponse r = new CorsaResponse(1L, 2L, 3L, "2024-01-01", "2024-01-02", 10.5, 5.2, "completata", 100L, "Visa");
        assertEquals(1L, r.getId());
        assertEquals(2L, r.getIdUtente());
        assertEquals(3L, r.getIdMezzo());
        assertEquals("2024-01-01", r.getDataInizio());
        assertEquals("2024-01-02", r.getDataFine());
        assertEquals(10.5, r.getCosto());
        assertEquals(5.2, r.getDistanza());
        assertEquals("completata", r.getStato());
        assertEquals(100L, r.getIdMetodoPagamento());
        assertEquals("Visa", r.getMetodoPagamentoLabel());
        assertFalse(r.isPaused());
        assertEquals(0.0, r.getTotalePausaMillis());
    }

    @Test
    void testCorsaResponse12ArgConstructor() {
        CorsaResponse r = new CorsaResponse(1L, 2L, 3L, "2024-01-01", "2024-01-02", 10.5, 5.2, "completata", 100L, "Visa", true, 5000.0);
        assertEquals(1L, r.getId());
        assertEquals(2L, r.getIdUtente());
        assertEquals(3L, r.getIdMezzo());
        assertEquals("2024-01-01", r.getDataInizio());
        assertEquals("2024-01-02", r.getDataFine());
        assertEquals(10.5, r.getCosto());
        assertEquals(5.2, r.getDistanza());
        assertEquals("completata", r.getStato());
        assertEquals(100L, r.getIdMetodoPagamento());
        assertEquals("Visa", r.getMetodoPagamentoLabel());
        assertTrue(r.isPaused());
        assertEquals(5000.0, r.getTotalePausaMillis());
    }

    @Test
    void testCorsaResponseSettersAndGetters() {
        CorsaResponse r = new CorsaResponse();
        r.setId(10L);
        r.setIdUtente(20L);
        r.setIdMezzo(30L);
        r.setDataInizio("d1");
        r.setDataFine("d2");
        r.setCosto(1.0);
        r.setDistanza(2.0);
        r.setStato("s");
        r.setIdMetodoPagamento(40L);
        r.setMetodoPagamentoLabel("mpl");
        r.setPaused(true);
        r.setTotalePausaMillis(999.0);

        assertEquals(10L, r.getId());
        assertEquals(20L, r.getIdUtente());
        assertEquals(30L, r.getIdMezzo());
        assertEquals("d1", r.getDataInizio());
        assertEquals("d2", r.getDataFine());
        assertEquals(1.0, r.getCosto());
        assertEquals(2.0, r.getDistanza());
        assertEquals("s", r.getStato());
        assertEquals(40L, r.getIdMetodoPagamento());
        assertEquals("mpl", r.getMetodoPagamentoLabel());
        assertTrue(r.isPaused());
        assertEquals(999.0, r.getTotalePausaMillis());
    }

    @Test
    void testCorsaResponseEqualsHashCodeToString() {
        CorsaResponse a = new CorsaResponse(1L, 2L, 3L, "d1", "d2", 1.0, 2.0, "s", 4L, "mpl", true, 5.0);
        CorsaResponse b = new CorsaResponse(1L, 2L, 3L, "d1", "d2", 1.0, 2.0, "s", 4L, "mpl", true, 5.0);
        CorsaResponse c = new CorsaResponse(9L, 8L, 7L, "x", "y", 9.0, 8.0, "z", null, null, false, 0.0);
        // self
        assertEquals(a, a);
        assertEquals(a.hashCode(), a.hashCode());
        // equal
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        // not equal
        assertNotEquals(a, c);
        assertNotEquals(a.hashCode(), c.hashCode());
        // null / different type
        assertNotEquals(a, null);
        assertNotEquals(a, "string");
        // toString
        String s = a.toString();
        assertTrue(s.contains("id="));
        assertTrue(s.contains("idUtente="));
        assertTrue(s.contains("idMezzo="));
        assertTrue(s.contains("dataInizio="));
        assertTrue(s.contains("dataFine="));
        assertTrue(s.contains("costo="));
        assertTrue(s.contains("distanza="));
        assertTrue(s.contains("stato="));
        assertTrue(s.contains("idMetodoPagamento="));
        assertTrue(s.contains("isPaused="));
        assertTrue(s.contains("totalePausaMillis="));
    }

    // ========================================================================
    // ErrorResponse - record
    // ========================================================================
    @Test
    void testErrorResponseRecord() {
        var r = new ErrorResponse(404, "Not Found", 1234567890L);
        assertEquals(404, r.status());
        assertEquals("Not Found", r.message());
        assertEquals(1234567890L, r.timestamp());
    }

    @Test
    void testErrorResponseRecordEqualsHashCodeToString() {
        var a = new ErrorResponse(404, "NF", 1L);
        var b = new ErrorResponse(404, "NF", 1L);
        var c = new ErrorResponse(500, "ISE", 2L);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
        assertNotEquals(a.hashCode(), c.hashCode());
        assertNotEquals(a, null);
        assertNotEquals(a, "string");
        assertTrue(a.toString().contains("status="));
        assertTrue(a.toString().contains("message="));
        assertTrue(a.toString().contains("timestamp="));
    }

    // ========================================================================
    // MetodoPagamentoResponse
    // ========================================================================
    @Test
    void testMetodoPagamentoResponseNoArgConstructor() {
        MetodoPagamentoResponse r = new MetodoPagamentoResponse();
        assertNull(r.getId());
        assertNull(r.getNumCarta());
        assertNull(r.getIntestatarioCarta());
        assertNull(r.getDsCarta());
    }

    @Test
    void testMetodoPagamentoResponseAllArgsConstructor() {
        MetodoPagamentoResponse r = new MetodoPagamentoResponse(1L, "1234", "Mario Rossi", "Debito");
        assertEquals(1L, r.getId());
        assertEquals("1234", r.getNumCarta());
        assertEquals("Mario Rossi", r.getIntestatarioCarta());
        assertEquals("Debito", r.getDsCarta());
    }

    @Test
    void testMetodoPagamentoResponseSettersAndGetters() {
        MetodoPagamentoResponse r = new MetodoPagamentoResponse();
        r.setId(99L);
        r.setNumCarta("5678");
        r.setIntestatarioCarta("Luigi Verdi");
        r.setDsCarta("Credito");

        assertEquals(99L, r.getId());
        assertEquals("5678", r.getNumCarta());
        assertEquals("Luigi Verdi", r.getIntestatarioCarta());
        assertEquals("Credito", r.getDsCarta());
    }

    @Test
    void testMetodoPagamentoResponseEqualsHashCodeToString() {
        var a = new MetodoPagamentoResponse(1L, "1234", "Mario", "Deb");
        var b = new MetodoPagamentoResponse(1L, "1234", "Mario", "Deb");
        var c = new MetodoPagamentoResponse(2L, "5678", "Luigi", "Cre");
        assertEquals(a, a);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
        assertNotEquals(a, null);
        assertNotEquals(a, "string");
        assertTrue(a.toString().contains("id="));
        assertTrue(a.toString().contains("numCarta="));
        assertTrue(a.toString().contains("intestatarioCarta="));
        assertTrue(a.toString().contains("dsCarta="));
    }

    // ========================================================================
    // MezzoResponse - 4 constructors (no-arg + 3 parameterized)
    // ========================================================================
    @Test
    void testMezzoResponseNoArgConstructor() {
        MezzoResponse r = new MezzoResponse();
        assertNull(r.getId());
        assertNull(r.getTipo());
        assertNull(r.getStato());
        assertEquals(0.0, r.getLatitudine());
        assertEquals(0.0, r.getLongitudine());
        assertEquals(0.0, r.getAutonomia());
        assertEquals(0.0, r.getTariffa());
        assertNull(r.getCodiceMezzo());
        assertNull(r.getTempoDisponibilita());
        assertNull(r.getCondizione());
        assertNull(r.getIdFlotta());
    }

    @Test
    void testMezzoResponse8ArgConstructor() {
        MezzoResponse r = new MezzoResponse(1L, "bici", "disponibile", 45.0, 9.0, 100.0, 2.5, "MZ001");
        assertEquals(1L, r.getId());
        assertEquals("bici", r.getTipo());
        assertEquals("disponibile", r.getStato());
        assertEquals(45.0, r.getLatitudine());
        assertEquals(9.0, r.getLongitudine());
        assertEquals(100.0, r.getAutonomia());
        assertEquals(2.5, r.getTariffa());
        assertEquals("MZ001", r.getCodiceMezzo());
        assertNull(r.getTempoDisponibilita());
        assertNull(r.getCondizione());
        assertNull(r.getIdFlotta());
    }

    @Test
    void testMezzoResponse9ArgConstructor() {
        MezzoResponse r = new MezzoResponse(1L, "bici", "disponibile", 45.0, 9.0, 100.0, 2.5, "MZ001", "10 min");
        assertEquals(1L, r.getId());
        assertEquals("bici", r.getTipo());
        assertEquals("disponibile", r.getStato());
        assertEquals(45.0, r.getLatitudine());
        assertEquals(9.0, r.getLongitudine());
        assertEquals(100.0, r.getAutonomia());
        assertEquals(2.5, r.getTariffa());
        assertEquals("MZ001", r.getCodiceMezzo());
        assertEquals("10 min", r.getTempoDisponibilita());
        assertNull(r.getCondizione());
        assertNull(r.getIdFlotta());
    }

    @Test
    void testMezzoResponse11ArgConstructor() {
        MezzoResponse r = new MezzoResponse(1L, "scooter", "manutenzione", 45.5, 9.5, 50.0, 3.0, "MZ002", "20 min", "buona", "FLT-1");
        assertEquals(1L, r.getId());
        assertEquals("scooter", r.getTipo());
        assertEquals("manutenzione", r.getStato());
        assertEquals(45.5, r.getLatitudine());
        assertEquals(9.5, r.getLongitudine());
        assertEquals(50.0, r.getAutonomia());
        assertEquals(3.0, r.getTariffa());
        assertEquals("MZ002", r.getCodiceMezzo());
        assertEquals("20 min", r.getTempoDisponibilita());
        assertEquals("buona", r.getCondizione());
        assertEquals("FLT-1", r.getIdFlotta());
    }

    @Test
    void testMezzoResponseSettersAndGetters() {
        MezzoResponse r = new MezzoResponse();
        r.setId(10L);
        r.setTipo("auto");
        r.setStato("occupato");
        r.setLatitudine(46.0);
        r.setLongitudine(10.0);
        r.setAutonomia(200.0);
        r.setTariffa(5.0);
        r.setCodiceMezzo("CR001");
        r.setTempoDisponibilita("5 min");
        r.setCondizione("ottima");
        r.setIdFlotta("F-2");

        assertEquals(10L, r.getId());
        assertEquals("auto", r.getTipo());
        assertEquals("occupato", r.getStato());
        assertEquals(46.0, r.getLatitudine());
        assertEquals(10.0, r.getLongitudine());
        assertEquals(200.0, r.getAutonomia());
        assertEquals(5.0, r.getTariffa());
        assertEquals("CR001", r.getCodiceMezzo());
        assertEquals("5 min", r.getTempoDisponibilita());
        assertEquals("ottima", r.getCondizione());
        assertEquals("F-2", r.getIdFlotta());
    }

    @Test
    void testMezzoResponseEqualsHashCodeToString() {
        var a = new MezzoResponse(1L, "bici", "disp", 45.0, 9.0, 100.0, 2.5, "MZ001", "10m", "buona", "F1");
        var b = new MezzoResponse(1L, "bici", "disp", 45.0, 9.0, 100.0, 2.5, "MZ001", "10m", "buona", "F1");
        var c = new MezzoResponse(2L, "auto", "occ", 46.0, 10.0, 200.0, 5.0, "CR001", null, null, null);
        assertEquals(a, a);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
        assertNotEquals(a, null);
        assertNotEquals(a, "string");
        String s = a.toString();
        assertTrue(s.contains("id="));
        assertTrue(s.contains("tipo="));
        assertTrue(s.contains("stato="));
        assertTrue(s.contains("latitudine="));
        assertTrue(s.contains("longitudine="));
        assertTrue(s.contains("autonomia="));
        assertTrue(s.contains("tariffa="));
        assertTrue(s.contains("codiceMezzo="));
        assertTrue(s.contains("tempoDisponibilita="));
        assertTrue(s.contains("condizione="));
        assertTrue(s.contains("idFlotta="));
    }

    // ========================================================================
    // PercorsoResponse
    // ========================================================================
    @Test
    void testPercorsoResponseNoArgConstructor() {
        PercorsoResponse r = new PercorsoResponse();
        assertNull(r.getCoordinatePartenza());
        assertNull(r.getCoordinateDestinazione());
        assertEquals(0.0, r.getDistanzaKm());
        assertNull(r.getDurataMinuti());
        assertEquals(0.0, r.getCostoStimato());
        assertNull(r.getMessaggio());
    }

    @Test
    void testPercorsoResponseAllArgsConstructor() {
        PercorsoResponse r = new PercorsoResponse("45.0,9.0", "46.0,10.0", 50.0, 30, 12.5, "Percorso valido");
        assertEquals("45.0,9.0", r.getCoordinatePartenza());
        assertEquals("46.0,10.0", r.getCoordinateDestinazione());
        assertEquals(50.0, r.getDistanzaKm());
        assertEquals(30, r.getDurataMinuti());
        assertEquals(12.5, r.getCostoStimato());
        assertEquals("Percorso valido", r.getMessaggio());
    }

    @Test
    void testPercorsoResponseSettersAndGetters() {
        PercorsoResponse r = new PercorsoResponse();
        r.setCoordinatePartenza("P1");
        r.setCoordinateDestinazione("P2");
        r.setDistanzaKm(99.9);
        r.setDurataMinuti(45);
        r.setCostoStimato(20.0);
        r.setMessaggio("OK");

        assertEquals("P1", r.getCoordinatePartenza());
        assertEquals("P2", r.getCoordinateDestinazione());
        assertEquals(99.9, r.getDistanzaKm());
        assertEquals(45, r.getDurataMinuti());
        assertEquals(20.0, r.getCostoStimato());
        assertEquals("OK", r.getMessaggio());
    }

    @Test
    void testPercorsoResponseEqualsHashCodeToString() {
        var a = new PercorsoResponse("45,9", "46,10", 50.0, 30, 12.5, "OK");
        var b = new PercorsoResponse("45,9", "46,10", 50.0, 30, 12.5, "OK");
        var c = new PercorsoResponse("0,0", "1,1", 10.0, 5, 2.0, "NO");
        assertEquals(a, a);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
        assertNotEquals(a, null);
        assertNotEquals(a, "string");
        String s = a.toString();
        assertTrue(s.contains("coordinatePartenza="));
        assertTrue(s.contains("coordinateDestinazione="));
        assertTrue(s.contains("distanzaKm="));
        assertTrue(s.contains("durataMinuti="));
        assertTrue(s.contains("costoStimato="));
        assertTrue(s.contains("messaggio="));
    }

    // ========================================================================
    // PrenotazioneResponse - 4 constructors (no-arg + 3 parameterized)
    // ========================================================================
    @Test
    void testPrenotazioneResponseNoArgConstructor() {
        PrenotazioneResponse r = new PrenotazioneResponse();
        assertNull(r.getId());
        assertNull(r.getIdUtente());
        assertNull(r.getIdMezzo());
        assertNull(r.getDataInizio());
        assertNull(r.getDataFine());
        assertNull(r.getStato());
        assertNull(r.getNomeVeicolo());
        assertNull(r.getTipoVeicolo());
        assertNull(r.getOrarioInizio());
        assertNull(r.getQrCode());
        assertNull(r.getIdVeicolo());
    }

    @Test
    void testPrenotazioneResponse6ArgConstructor() {
        PrenotazioneResponse r = new PrenotazioneResponse(1L, 2L, 3L, "2024-01-01", "2024-01-02", "attiva");
        assertEquals(1L, r.getId());
        assertEquals(2L, r.getIdUtente());
        assertEquals(3L, r.getIdMezzo());
        assertEquals("2024-01-01", r.getDataInizio());
        assertEquals("2024-01-02", r.getDataFine());
        assertEquals("attiva", r.getStato());
        assertNull(r.getNomeVeicolo());
        assertNull(r.getTipoVeicolo());
        assertNull(r.getOrarioInizio());
        assertNull(r.getQrCode());
        assertNull(r.getIdVeicolo());
    }

    @Test
    void testPrenotazioneResponse8ArgConstructor() {
        PrenotazioneResponse r = new PrenotazioneResponse(1L, 2L, 3L, "2024-01-01", "2024-01-02", "attiva", "BiciX", "elettrica");
        assertEquals(1L, r.getId());
        assertEquals(2L, r.getIdUtente());
        assertEquals(3L, r.getIdMezzo());
        assertEquals("2024-01-01", r.getDataInizio());
        assertEquals("2024-01-02", r.getDataFine());
        assertEquals("attiva", r.getStato());
        assertEquals("BiciX", r.getNomeVeicolo());
        assertEquals("elettrica", r.getTipoVeicolo());
        assertNull(r.getOrarioInizio());
        assertNull(r.getQrCode());
        assertNull(r.getIdVeicolo());
    }

    @Test
    void testPrenotazioneResponse11ArgConstructor() {
        PrenotazioneResponse r = new PrenotazioneResponse(1L, 2L, 3L, "2024-01-01", "2024-01-02", "attiva", "BiciX", "elettrica", "10:00", "QR123", 99L);
        assertEquals(1L, r.getId());
        assertEquals(2L, r.getIdUtente());
        assertEquals(3L, r.getIdMezzo());
        assertEquals("2024-01-01", r.getDataInizio());
        assertEquals("2024-01-02", r.getDataFine());
        assertEquals("attiva", r.getStato());
        assertEquals("BiciX", r.getNomeVeicolo());
        assertEquals("elettrica", r.getTipoVeicolo());
        assertEquals("10:00", r.getOrarioInizio());
        assertEquals("QR123", r.getQrCode());
        assertEquals(99L, r.getIdVeicolo());
    }

    @Test
    void testPrenotazioneResponseSettersAndGetters() {
        PrenotazioneResponse r = new PrenotazioneResponse();
        r.setId(10L);
        r.setIdUtente(20L);
        r.setIdMezzo(30L);
        r.setDataInizio("d1");
        r.setDataFine("d2");
        r.setStato("s");
        r.setNomeVeicolo("nv");
        r.setTipoVeicolo("tv");
        r.setOrarioInizio("12:00");
        r.setQrCode("qr");
        r.setIdVeicolo(40L);

        assertEquals(10L, r.getId());
        assertEquals(20L, r.getIdUtente());
        assertEquals(30L, r.getIdMezzo());
        assertEquals("d1", r.getDataInizio());
        assertEquals("d2", r.getDataFine());
        assertEquals("s", r.getStato());
        assertEquals("nv", r.getNomeVeicolo());
        assertEquals("tv", r.getTipoVeicolo());
        assertEquals("12:00", r.getOrarioInizio());
        assertEquals("qr", r.getQrCode());
        assertEquals(40L, r.getIdVeicolo());
    }

    @Test
    void testPrenotazioneResponseEqualsHashCodeToString() {
        var a = new PrenotazioneResponse(1L, 2L, 3L, "d1", "d2", "s", "nv", "tv", "10:00", "qr", 5L);
        var b = new PrenotazioneResponse(1L, 2L, 3L, "d1", "d2", "s", "nv", "tv", "10:00", "qr", 5L);
        var c = new PrenotazioneResponse(9L, 8L, 7L, "x", "y", "z", null, null, null, null, null);
        assertEquals(a, a);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
        assertNotEquals(a, null);
        assertNotEquals(a, "string");
        String s = a.toString();
        assertTrue(s.contains("id="));
        assertTrue(s.contains("idUtente="));
        assertTrue(s.contains("idMezzo="));
        assertTrue(s.contains("dataInizio="));
        assertTrue(s.contains("dataFine="));
        assertTrue(s.contains("stato="));
        assertTrue(s.contains("nomeVeicolo="));
        assertTrue(s.contains("tipoVeicolo="));
        assertTrue(s.contains("orarioInizio="));
        assertTrue(s.contains("qrCode="));
        assertTrue(s.contains("idVeicolo="));
    }

    // ========================================================================
    // SegnalazioneResponse - record
    // ========================================================================
    @Test
    void testSegnalazioneResponseRecord() {
        var r = new SegnalazioneResponse(1L, 2L, "aperta", "14:30", "2024-06-01", "Freni rotti");
        assertEquals(1L, r.idSegnalazione());
        assertEquals(2L, r.idMezzo());
        assertEquals("aperta", r.stato());
        assertEquals("14:30", r.ora());
        assertEquals("2024-06-01", r.data());
        assertEquals("Freni rotti", r.motivazione());
    }

    @Test
    void testSegnalazioneResponseRecordEqualsHashCodeToString() {
        var a = new SegnalazioneResponse(1L, 2L, "aperta", "14:30", "2024-06-01", "Freni rotti");
        var b = new SegnalazioneResponse(1L, 2L, "aperta", "14:30", "2024-06-01", "Freni rotti");
        var c = new SegnalazioneResponse(3L, 4L, "chiusa", "10:00", "2024-05-01", "Luce rotta");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
        assertNotEquals(a, null);
        assertNotEquals(a, "string");
        String s = a.toString();
        assertTrue(s.contains("idSegnalazione="));
        assertTrue(s.contains("idMezzo="));
        assertTrue(s.contains("stato="));
        assertTrue(s.contains("ora="));
        assertTrue(s.contains("data="));
        assertTrue(s.contains("motivazione="));
    }

    // ========================================================================
    // StatisticheResponse
    // ========================================================================
    @Test
    void testStatisticheResponseNoArgConstructor() {
        StatisticheResponse r = new StatisticheResponse();
        assertEquals(0L, r.getTotalCorse());
        assertEquals(0.0, r.getTotalKm());
        assertEquals(0.0, r.getTotalRicavo());
        assertEquals(0.0, r.getMediaDurata());
        assertNull(r.getDettagli());
    }

    @Test
    void testStatisticheResponseAllArgsConstructor() {
        Map<String, Object> dettagli = Map.of("oggi", 5, "settimana", 30);
        StatisticheResponse r = new StatisticheResponse(100, 500.5, 1200.0, 15.5, dettagli);
        assertEquals(100L, r.getTotalCorse());
        assertEquals(500.5, r.getTotalKm());
        assertEquals(1200.0, r.getTotalRicavo());
        assertEquals(15.5, r.getMediaDurata());
        assertEquals(dettagli, r.getDettagli());
    }

    @Test
    void testStatisticheResponseSettersAndGetters() {
        StatisticheResponse r = new StatisticheResponse();
        r.setTotalCorse(50);
        r.setTotalKm(250.0);
        r.setTotalRicavo(600.0);
        r.setMediaDurata(10.0);
        Map<String, Object> d = Map.of("chiave", "valore");
        r.setDettagli(d);

        assertEquals(50L, r.getTotalCorse());
        assertEquals(250.0, r.getTotalKm());
        assertEquals(600.0, r.getTotalRicavo());
        assertEquals(10.0, r.getMediaDurata());
        assertEquals(d, r.getDettagli());
    }

    @Test
    void testStatisticheResponseEqualsHashCodeToString() {
        Map<String, Object> d = Map.of("k", "v");
        var a = new StatisticheResponse(100, 500.5, 1200.0, 15.5, d);
        var b = new StatisticheResponse(100, 500.5, 1200.0, 15.5, d);
        var c = new StatisticheResponse(0, 0, 0, 0, null);
        assertEquals(a, a);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
        assertNotEquals(a, null);
        assertNotEquals(a, "string");
        String s = a.toString();
        assertTrue(s.contains("totalCorse="));
        assertTrue(s.contains("totalKm="));
        assertTrue(s.contains("totalRicavo="));
        assertTrue(s.contains("mediaDurata="));
        assertTrue(s.contains("dettagli="));
    }

    // ========================================================================
    // StimaCorsaResponse (no equals/hashCode/toString)
    // ========================================================================
    @Test
    void testStimaCorsaResponseNoArgConstructor() {
        StimaCorsaResponse r = new StimaCorsaResponse();
        assertEquals(0.0, r.getCosto());
        assertEquals(0.0, r.getTariffa());
    }

    @Test
    void testStimaCorsaResponseAllArgsConstructor() {
        StimaCorsaResponse r = new StimaCorsaResponse(15.5, 2.0);
        assertEquals(15.5, r.getCosto());
        assertEquals(2.0, r.getTariffa());
    }

    @Test
    void testStimaCorsaResponseSettersAndGetters() {
        StimaCorsaResponse r = new StimaCorsaResponse();
        r.setCosto(99.9);
        r.setTariffa(3.5);
        assertEquals(99.9, r.getCosto());
        assertEquals(3.5, r.getTariffa());
    }

    // ========================================================================
    // UtenteResponse (no equals/hashCode/toString)
    // ========================================================================
    @Test
    void testUtenteResponseNoArgConstructor() {
        UtenteResponse r = new UtenteResponse();
        assertNull(r.getId());
        assertNull(r.getIdUtente());
        assertNull(r.getNome());
        assertNull(r.getCognome());
        assertNull(r.getEmail());
        assertNull(r.getStato());
    }

    @Test
    void testUtenteResponseAllArgsConstructor() {
        UtenteResponse r = new UtenteResponse(1L, 100L, "Mario", "Rossi", "mario@test.com", "attivo");
        assertEquals(1L, r.getId());
        assertEquals(100L, r.getIdUtente());
        assertEquals("Mario", r.getNome());
        assertEquals("Rossi", r.getCognome());
        assertEquals("mario@test.com", r.getEmail());
        assertEquals("attivo", r.getStato());
    }

    @Test
    void testUtenteResponseSettersAndGetters() {
        UtenteResponse r = new UtenteResponse();
        r.setId(5L);
        r.setIdUtente(50L);
        r.setNome("Anna");
        r.setCognome("Bianchi");
        r.setEmail("anna@test.com");
        r.setStato("sospeso");

        assertEquals(5L, r.getId());
        assertEquals(50L, r.getIdUtente());
        assertEquals("Anna", r.getNome());
        assertEquals("Bianchi", r.getCognome());
        assertEquals("anna@test.com", r.getEmail());
        assertEquals("sospeso", r.getStato());
    }

    // ========================================================================
    // ZonaGeograficaResponse
    // ========================================================================
    @Test
    void testZonaGeograficaResponseNoArgConstructor() {
        ZonaGeograficaResponse r = new ZonaGeograficaResponse();
        assertNull(r.getId());
        assertNull(r.getTipoRestrizione());
        assertNull(r.getNoteRestrizione());
        assertNull(r.getZona());
    }

    @Test
    void testZonaGeograficaResponseAllArgsConstructor() {
        ZonaGeograficaResponse r = new ZonaGeograficaResponse(1L, "divieto", "Solo bici", "Zona A");
        assertEquals(1L, r.getId());
        assertEquals("divieto", r.getTipoRestrizione());
        assertEquals("Solo bici", r.getNoteRestrizione());
        assertEquals("Zona A", r.getZona());
    }

    @Test
    void testZonaGeograficaResponseSettersAndGetters() {
        ZonaGeograficaResponse r = new ZonaGeograficaResponse();
        r.setId(99L);
        r.setTipoRestrizione("limite");
        r.setNoteRestrizione("30 km/h");
        r.setZona("Zona B");

        assertEquals(99L, r.getId());
        assertEquals("limite", r.getTipoRestrizione());
        assertEquals("30 km/h", r.getNoteRestrizione());
        assertEquals("Zona B", r.getZona());
    }

    @Test
    void testZonaGeograficaResponseEqualsHashCodeToString() {
        var a = new ZonaGeograficaResponse(1L, "divieto", "solo bici", "ZA");
        var b = new ZonaGeograficaResponse(1L, "divieto", "solo bici", "ZA");
        var c = new ZonaGeograficaResponse(2L, "limite", "30 km/h", "ZB");
        assertEquals(a, a);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
        assertNotEquals(a, null);
        assertNotEquals(a, "string");
        String s = a.toString();
        assertTrue(s.contains("id="));
        assertTrue(s.contains("tipoRestrizione="));
        assertTrue(s.contains("noteRestrizione="));
        assertTrue(s.contains("zona="));
    }
}

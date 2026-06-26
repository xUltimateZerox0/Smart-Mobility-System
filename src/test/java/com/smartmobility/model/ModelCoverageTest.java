package com.smartmobility.model;

import com.smartmobility.model.enums.RuoloAttore;
import com.smartmobility.model.enums.StatoUtente;
import com.smartmobility.model.enums.TipoOperatore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelCoverageTest {

    // ─── Operatore ────────────────────────────────────────────────────────────

    @Test
    void operatoreNoArgConstructor() {
        assertNotNull(new Operatore());
    }

    @Test
    void operatoreGetSetTipo() {
        Operatore o = new Operatore();
        o.setTipo(TipoOperatore.OperatoreTecnico);
        assertEquals(TipoOperatore.OperatoreTecnico, o.getTipo());
    }

    @Test
    void operatoreInheritedAttoreFields() {
        Operatore o = new Operatore();
        o.setId(99L);
        assertEquals(99L, o.getId());
        o.setEmail("op@test.com");
        assertEquals("op@test.com", o.getEmail());
        o.setPassword("secret");
        assertEquals("secret", o.getPassword());
        o.setRuolo(RuoloAttore.Operatore);
        assertEquals(RuoloAttore.Operatore, o.getRuolo());
    }

    // ─── Utente ───────────────────────────────────────────────────────────────

    @Test
    void utenteNoArgConstructor() {
        assertNotNull(new Utente());
    }

    @Test
    void utenteGetSetBasicFields() {
        Utente u = new Utente();
        u.setIdUtente(1L);
        assertEquals(1L, u.getIdUtente());
        u.setNomeUtente("Mario");
        assertEquals("Mario", u.getNomeUtente());
        u.setCognomeUtente("Rossi");
        assertEquals("Rossi", u.getCognomeUtente());
        u.setTelefono("123456789");
        assertEquals("123456789", u.getTelefono());
        u.setCoordinateUtente("41.9,12.5");
        assertEquals("41.9,12.5", u.getCoordinateUtente());
        u.setReportUtente("report1");
        assertEquals("report1", u.getReportUtente());
        u.setStatoUtente(StatoUtente.attivo);
        assertEquals(StatoUtente.attivo, u.getStatoUtente());
        u.setNumMezziPrenotati(3);
        assertEquals(3, u.getNumMezziPrenotati());
        u.setDataNascita("1990-01-01");
        assertEquals("1990-01-01", u.getDataNascita());
    }

    @Test
    void utenteInheritedAttoreFields() {
        Utente u = new Utente();
        u.setId(42L);
        assertEquals(42L, u.getId());
        u.setEmail("user@test.com");
        assertEquals("user@test.com", u.getEmail());
        u.setPassword("pass");
        assertEquals("pass", u.getPassword());
        u.setRuolo(RuoloAttore.Utente);
        assertEquals(RuoloAttore.Utente, u.getRuolo());
    }

    @Test
    void utenteRicercaUtenteMatching() {
        Utente u = new Utente();
        u.setIdUtente(10L);
        assertSame(u, u.ricercaUtente(10L));
    }

    @Test
    void utenteRicercaUtenteNonMatching() {
        Utente u = new Utente();
        u.setIdUtente(10L);
        assertNull(u.ricercaUtente(99L));
    }

    @Test
    void utenteRicercaUtenteNullId() {
        Utente u = new Utente();
        assertNull(u.ricercaUtente(1L));
    }

    @Test
    void utenteAzioneCorrettivaInitialReport() {
        Utente u = new Utente();
        u.azioneCorrettiva("prima azione");
        assertEquals("prima azione", u.getReportUtente());
    }

    @Test
    void utenteAzioneCorrettivaAppendReport() {
        Utente u = new Utente();
        u.setReportUtente("iniziale");
        u.azioneCorrettiva("aggiunta");
        assertEquals("iniziale | aggiunta", u.getReportUtente());
    }

    @Test
    void utenteAzioneCorrettivaMultipleAppends() {
        Utente u = new Utente();
        u.azioneCorrettiva("a");
        u.azioneCorrettiva("b");
        u.azioneCorrettiva("c");
        assertEquals("a | b | c", u.getReportUtente());
    }

    @Test
    void utenteCreaAccountUtenteSetsFields() {
        Utente u = new Utente();
        u.creaAccountUtente("Luca", "Bianchi", "luca@test.com", "password123", "1995-06-15");
        assertEquals("Luca", u.getNomeUtente());
        assertEquals("Bianchi", u.getCognomeUtente());
        assertEquals("luca@test.com", u.getEmail());
        assertEquals("password123", u.getPassword());
        assertEquals(StatoUtente.attivo, u.getStatoUtente());
    }

    // ─── Transito ─────────────────────────────────────────────────────────────

    @Test
    void transitoNoArgConstructor() {
        assertNotNull(new Transito());
    }

    @Test
    void transitoGetSetId() {
        Transito t = new Transito();
        TransitoId id = new TransitoId();
        id.setIdCorsa(1L);
        id.setIdArea(2L);
        t.setId(id);
        assertSame(id, t.getId());
    }

    // ─── TransitoId ───────────────────────────────────────────────────────────

    @Test
    void transitoIdNoArgConstructor() {
        assertNotNull(new TransitoId());
    }

    @Test
    void transitoIdGetSetIdCorsa() {
        TransitoId id = new TransitoId();
        id.setIdCorsa(100L);
        assertEquals(100L, id.getIdCorsa());
    }

    @Test
    void transitoIdGetSetIdArea() {
        TransitoId id = new TransitoId();
        id.setIdArea(200L);
        assertEquals(200L, id.getIdArea());
    }

    @Test
    void transitoIdEqualsSameInstance() {
        TransitoId id = new TransitoId();
        id.setIdCorsa(1L);
        id.setIdArea(2L);
        assertEquals(id, id);
    }

    @Test
    void transitoIdEqualsEqualValues() {
        TransitoId a = new TransitoId();
        a.setIdCorsa(1L);
        a.setIdArea(2L);
        TransitoId b = new TransitoId();
        b.setIdCorsa(1L);
        b.setIdArea(2L);
        assertEquals(a, b);
        assertEquals(b, a);
    }

    @Test
    void transitoIdEqualsDifferentCorsa() {
        TransitoId a = new TransitoId();
        a.setIdCorsa(1L);
        a.setIdArea(2L);
        TransitoId b = new TransitoId();
        b.setIdCorsa(99L);
        b.setIdArea(2L);
        assertNotEquals(a, b);
    }

    @Test
    void transitoIdEqualsDifferentArea() {
        TransitoId a = new TransitoId();
        a.setIdCorsa(1L);
        a.setIdArea(2L);
        TransitoId b = new TransitoId();
        b.setIdCorsa(1L);
        b.setIdArea(99L);
        assertNotEquals(a, b);
    }

    @Test
    void transitoIdEqualsNull() {
        TransitoId id = new TransitoId();
        id.setIdCorsa(1L);
        id.setIdArea(2L);
        assertNotEquals(null, id);
    }

    @Test
    void transitoIdEqualsDifferentClass() {
        TransitoId id = new TransitoId();
        id.setIdCorsa(1L);
        id.setIdArea(2L);
        assertNotEquals("string", id);
    }

    @Test
    void transitoIdHashCodeConsistentWithEquals() {
        TransitoId a = new TransitoId();
        a.setIdCorsa(5L);
        a.setIdArea(10L);
        TransitoId b = new TransitoId();
        b.setIdCorsa(5L);
        b.setIdArea(10L);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void transitoIdHashCodeDifferentValues() {
        TransitoId a = new TransitoId();
        a.setIdCorsa(1L);
        a.setIdArea(2L);
        TransitoId b = new TransitoId();
        b.setIdCorsa(3L);
        b.setIdArea(4L);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void transitoIdEqualsBothNull() {
        TransitoId a = new TransitoId();
        TransitoId b = new TransitoId();
        assertEquals(a, b);
    }
}

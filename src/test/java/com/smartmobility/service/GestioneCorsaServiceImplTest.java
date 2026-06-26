package com.smartmobility.service;

import com.smartmobility.config.TestDataFactory;
import com.smartmobility.dto.response.PercorsoResponse;
import com.smartmobility.dto.response.StimaCorsaResponse;
import com.smartmobility.integration.MezzoIoTService;
import com.smartmobility.integration.ServizioMappaService;
import com.smartmobility.model.*;
import com.smartmobility.model.enums.StatoMezzo;
import com.smartmobility.repository.CorsaRepository;
import com.smartmobility.repository.MetodoPagamentoRepository;
import com.smartmobility.repository.MezzoRepository;
import com.smartmobility.repository.PrenotazioneRepository;
import com.smartmobility.repository.UtenteRepository;
import com.smartmobility.service.impl.GestioneCorsaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestioneCorsaServiceImplTest {

    @Mock
    private CorsaRepository corsaRepository;

    @Mock
    private MezzoRepository mezzoRepository;

    @Mock
    private UtenteRepository utenteRepository;

    @Mock
    private MetodoPagamentoRepository metodoPagamentoRepository;

    @Mock
    private PrenotazioneRepository prenotazioneRepository;

    @Mock
    private MezzoIoTService mezzoIoTService;

    @Mock
    private ServizioMappaService servizioMappaService;

    @Mock
    private GestorePagamentoService gestorePagamentoService;

    private GestioneCorsaServiceImpl service;

    private Utente utente;
    private Mezzo mezzo;
    private MetodoPagamento metodoPagamento;

    @BeforeEach
    void setUp() {
        service = new GestioneCorsaServiceImpl(corsaRepository, mezzoRepository, utenteRepository,
                metodoPagamentoRepository, prenotazioneRepository, mezzoIoTService,
                servizioMappaService, gestorePagamentoService);
        utente = TestDataFactory.createDefaultUtente();
        mezzo = TestDataFactory.createDefaultMezzo();
        metodoPagamento = TestDataFactory.createDefaultMetodoPagamento(utente);
    }

    @Test
    void getCorsaAttiva_WithMultipleActiveRides_ThrowsError() {
        Corsa corsa1 = TestDataFactory.createCorsa(1L, utente, mezzo, metodoPagamento, LocalDateTime.now(), 5.0f);
        Corsa corsa2 = TestDataFactory.createCorsa(2L, utente, mezzo, metodoPagamento, LocalDateTime.now(), 3.0f);

        when(utenteRepository.findByIdUtente(1L)).thenReturn(Optional.of(utente));
        when(corsaRepository.findByIdUtenteAndOrarioFineIsNull(1L)).thenReturn(List.of(corsa1, corsa2));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.getCorsaAttiva(1L));
        assertEquals(500, ex.getStatusCode().value());
    }

    @Test
    void avviaCorsa_WithValidData_StartsRide() {
        when(metodoPagamentoRepository.findById(1L)).thenReturn(Optional.of(metodoPagamento));
        when(corsaRepository.findByIdUtenteAndOrarioFineIsNull(1L)).thenReturn(List.of());
        service.acquisisciSceltaMetodo(1L, utente.getIdUtente());

        when(mezzoIoTService.sbloccoMezzoFisico(1L)).thenReturn(true);
        when(mezzoRepository.findById(1L)).thenReturn(Optional.of(mezzo));
        when(utenteRepository.findByIdUtente(1L)).thenReturn(Optional.of(utente));
        when(corsaRepository.findByIdUtenteAndOrarioFineIsNull(1L)).thenReturn(List.of());
        Corsa savedCorsa = new Corsa();
        savedCorsa.setIdCorsa(1L);
        when(corsaRepository.save(any())).thenReturn(savedCorsa);

        Long corsaId = service.avviaCorsa(1L, 1L, "QR-1");

        assertEquals(1L, corsaId);
        verify(corsaRepository, atLeastOnce()).save(any());
        verify(mezzoRepository).save(mezzo);
        assertEquals(StatoMezzo.in_uso, mezzo.getStato());
    }

    @Test
    void avviaCorsa_WithVehicleNotPrenotatoNorDisponibile_ThrowsConflict() {
        mezzo.setStato(StatoMezzo.manutenzione);
        when(mezzoRepository.findById(1L)).thenReturn(Optional.of(mezzo));

        assertThrows(ResponseStatusException.class,
                () -> service.avviaCorsa(1L, 1L, "QR-1"));
    }

    @Test
    void avviaCorsa_WithoutPaymentMethod_ThrowsPaymentRequired() {
        when(mezzoRepository.findById(1L)).thenReturn(Optional.of(mezzo));
        when(utenteRepository.findByIdUtente(1L)).thenReturn(Optional.of(utente));
        when(corsaRepository.findByIdUtenteAndOrarioFineIsNull(1L)).thenReturn(List.of());
        Corsa savedCorsa = new Corsa();
        savedCorsa.setIdCorsa(1L);
        when(corsaRepository.save(any())).thenReturn(savedCorsa);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.avviaCorsa(1L, 1L, "QR-1"));
        assertEquals(402, ex.getStatusCode().value());
    }

    @Test
    void avviaCorsa_WithExistingActiveRide_ThrowsConflict() {
        when(mezzoRepository.findById(1L)).thenReturn(Optional.of(mezzo));
        when(utenteRepository.findByIdUtente(1L)).thenReturn(Optional.of(utente));
        when(corsaRepository.findByIdUtenteAndOrarioFineIsNull(1L)).thenReturn(List.of(new Corsa()));

        assertThrows(ResponseStatusException.class,
                () -> service.avviaCorsa(1L, 1L, "QR-1"));
    }

    @Test
    void terminaCorsa_WithValidCorsa_TerminatesRide() {
        Corsa corsa = TestDataFactory.createCorsa(1L, utente, mezzo, metodoPagamento, LocalDateTime.now().minusHours(1), 5.0f);
        corsa.setOrarioFine(null);

        when(corsaRepository.findById(1L)).thenReturn(Optional.of(corsa));
        when(gestorePagamentoService.pagamentoCorsa(anyLong(), anyLong(), anyLong(), anyDouble())).thenReturn(true);

        service.terminaCorsa(1L);

        assertNotNull(corsa.getOrarioFine());
        assertEquals(StatoMezzo.disponibile, mezzo.getStato());
        verify(corsaRepository, atLeastOnce()).save(corsa);
        verify(mezzoRepository).save(mezzo);
    }

    @Test
    void terminaCorsa_WithSuspendedVehicle_DoesNotDoubleLock() {
        mezzo.setStato(StatoMezzo.sospeso);
        Corsa corsa = TestDataFactory.createCorsa(1L, utente, mezzo, metodoPagamento, LocalDateTime.now().minusHours(1), 5.0f);
        corsa.setOrarioFine(null);

        when(corsaRepository.findById(1L)).thenReturn(Optional.of(corsa));
        when(gestorePagamentoService.pagamentoCorsa(anyLong(), anyLong(), anyLong(), anyDouble())).thenReturn(true);

        service.terminaCorsa(1L);

        assertNotNull(corsa.getOrarioFine());
        assertEquals(StatoMezzo.disponibile, mezzo.getStato());
        verify(mezzoIoTService, never()).bloccoMezzoFisico(anyLong());
        verify(mezzoRepository).save(mezzo);
    }

    @Test
    void terminaCorsa_WithPaymentFailure_DoesNotPersistNewCost() {
        Corsa corsa = TestDataFactory.createCorsa(1L, utente, mezzo, metodoPagamento, LocalDateTime.now().minusHours(1), 5.0f);
        corsa.setOrarioFine(null);

        when(corsaRepository.findById(1L)).thenReturn(Optional.of(corsa));
        when(gestorePagamentoService.pagamentoCorsa(anyLong(), anyLong(), anyLong(), anyDouble())).thenReturn(false);

        assertThrows(ResponseStatusException.class,
                () -> service.terminaCorsa(1L));

        assertNull(corsa.getOrarioFine());
        assertEquals(5.0f, corsa.getCosto(), 0.001);
        verify(mezzoRepository, never()).save(any());
        verify(corsaRepository, never()).save(any());
    }

    @Test
    void terminaCorsa_WithInvalidId_ThrowsNotFound() {
        when(corsaRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> service.terminaCorsa(999L));
    }

    @Test
    void aggiornaStima_WithActiveCorsa_ReturnsCost() {
        Corsa corsa = TestDataFactory.createCorsa(1L, utente, mezzo, metodoPagamento, LocalDateTime.now().minusHours(2), 0f);

        when(corsaRepository.findById(1L)).thenReturn(Optional.of(corsa));

        StimaCorsaResponse stima = service.aggiornaStima(1L);

        assertNotNull(stima);
        assertTrue(stima.getCosto() > 0);
        assertTrue(stima.getTariffa() > 0);
        verify(corsaRepository, never()).save(any());
    }

    @Test
    void sospensioneCorsa_WithInUseMezzo_Suspends() {
        mezzo.setStato(StatoMezzo.in_uso);
        Corsa corsa = TestDataFactory.createCorsa(1L, utente, mezzo, metodoPagamento, LocalDateTime.now(), 0f);

        when(corsaRepository.findById(1L)).thenReturn(Optional.of(corsa));
        when(mezzoIoTService.bloccoMezzoFisico(1L)).thenReturn(true);

        boolean result = service.sospensioneCorsa(1L);

        assertTrue(result);
        assertEquals(StatoMezzo.sospeso, mezzo.getStato());
    }

    @Test
    void sospensioneCorsa_WithSospesoMezzo_Resumes() {
        mezzo.setStato(StatoMezzo.sospeso);
        Corsa corsa = TestDataFactory.createCorsa(1L, utente, mezzo, metodoPagamento, LocalDateTime.now(), 0f);

        when(corsaRepository.findById(1L)).thenReturn(Optional.of(corsa));
        when(mezzoIoTService.sbloccoMezzoFisico(1L)).thenReturn(true);

        boolean result = service.sospensioneCorsa(1L);

        assertTrue(result);
        assertEquals(StatoMezzo.in_uso, mezzo.getStato());
    }

    @Test
    void sospensioneCorsa_WithDisponibileMezzo_ReturnsFalse() {
        Corsa corsa = TestDataFactory.createCorsa(1L, utente, mezzo, metodoPagamento, LocalDateTime.now(), 0f);

        when(corsaRepository.findById(1L)).thenReturn(Optional.of(corsa));

        boolean result = service.sospensioneCorsa(1L);

        assertFalse(result);
    }

    @Test
    void richiediSblocco_WithValidQrCode_UnlocksVehicle() {
        when(mezzoRepository.findById(1L)).thenReturn(Optional.of(mezzo));
        when(mezzoIoTService.sbloccoMezzoFisico(1L)).thenReturn(true);

        boolean result = service.richiediSblocco("1");

        assertTrue(result);
    }

    @Test
    void richiediSblocco_WithInvalidQrCode_ThrowsBadRequest() {
        assertThrows(ResponseStatusException.class,
                () -> service.richiediSblocco("not-a-number"));
    }

    @Test
    void richiediSblocco_WithNonPrenotabileMezzo_ThrowsConflict() {
        mezzo.setStato(StatoMezzo.manutenzione);
        when(mezzoRepository.findById(1L)).thenReturn(Optional.of(mezzo));

        assertThrows(ResponseStatusException.class,
                () -> service.richiediSblocco("1"));
    }

    @Test
    void richiediCalcoloPercorso_WithValidCoordinates_ReturnsPercorso() {
        Map<String, Object> mockRoute = Map.of(
                "coordinateIniziali", "41.9028,12.4964,0.0",
                "coordinateFinali", "41.9030,12.4970,0.0",
                "distanza", 5.2,
                "durata", 15
        );

        when(servizioMappaService.getPercorso("41.9028,12.4964,0.0", "41.9030,12.4970,0.0", null))
                .thenReturn(mockRoute);

        PercorsoResponse response = service.richiediCalcoloPercorso("41.9028,12.4964,0.0", "41.9030,12.4970,0.0");

        assertNotNull(response);
        assertEquals("Percorso calcolato con successo", response.getMessaggio());
    }

    @Test
    void acquisisciSceltaMetodo_WithValidMethod_AssociatesToRide() {
        when(metodoPagamentoRepository.findById(1L)).thenReturn(Optional.of(metodoPagamento));
        Corsa activeCorsa = TestDataFactory.createCorsa(1L, utente, mezzo, null, LocalDateTime.now(), 0f);
        when(corsaRepository.findByIdUtenteAndOrarioFineIsNull(1L)).thenReturn(List.of(activeCorsa));

        service.acquisisciSceltaMetodo(1L, utente.getIdUtente());

        assertNotNull(activeCorsa.getMetodoPagamento());
        verify(corsaRepository).save(activeCorsa);
    }

    @Test
    void controllaDisponibilita_WithAvailableMezzo_ReturnsTrue() {
        Corsa corsa = TestDataFactory.createCorsa(1L, utente, mezzo, null, LocalDateTime.now(), 0f);
        mezzo.setStato(StatoMezzo.disponibile);

        when(corsaRepository.findById(1L)).thenReturn(Optional.of(corsa));

        boolean result = service.controllaDisponibilita(1L);

        assertTrue(result);
    }

    @Test
    void controllaDisponibilita_WithUnavailableMezzo_ReturnsFalse() {
        Corsa corsa = TestDataFactory.createCorsa(1L, utente, mezzo, null, LocalDateTime.now(), 0f);
        mezzo.setStato(StatoMezzo.manutenzione);

        when(corsaRepository.findById(1L)).thenReturn(Optional.of(corsa));

        boolean result = service.controllaDisponibilita(1L);

        assertFalse(result);
    }

    @Test
    void controllaDisponibilitaOverload_WithInfo_ChecksVehicleType() {
        Corsa corsa = TestDataFactory.createCorsa(1L, utente, mezzo, null, LocalDateTime.now(), 0f);

        when(corsaRepository.findById(1L)).thenReturn(Optional.of(corsa));

        boolean result = service.controllaDisponibilita(1L, "bici");

        assertTrue(result);
    }
}

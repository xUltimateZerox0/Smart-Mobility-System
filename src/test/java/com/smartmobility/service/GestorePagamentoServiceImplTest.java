package com.smartmobility.service;

import com.smartmobility.config.TestDataFactory;
import com.smartmobility.dto.response.MetodoPagamentoResponse;
import com.smartmobility.integration.GatewayPagamentoService;
import com.smartmobility.model.MetodoPagamento;
import com.smartmobility.model.Utente;
import com.smartmobility.repository.MetodoPagamentoRepository;
import com.smartmobility.repository.UtenteRepository;
import com.smartmobility.service.impl.GestorePagamentoServiceImpl;
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
class GestorePagamentoServiceImplTest {

    @Mock
    private MetodoPagamentoRepository metodoPagamentoRepository;

    @Mock
    private UtenteRepository utenteRepository;

    @Mock
    private GatewayPagamentoService gatewayPagamentoService;

    private GestorePagamentoServiceImpl service;

    private Utente utente;
    private MetodoPagamento metodoPagamento;

    @BeforeEach
    void setUp() {
        service = new GestorePagamentoServiceImpl(metodoPagamentoRepository, utenteRepository, gatewayPagamentoService);
        utente = TestDataFactory.createDefaultUtente();
        metodoPagamento = TestDataFactory.createDefaultMetodoPagamento(utente);
    }

    @Test
    void pagamentoCorsa_WithValidData_ReturnsTrue() {
        when(utenteRepository.findByIdUtente(1L)).thenReturn(Optional.of(utente));
        when(metodoPagamentoRepository.findById(1L)).thenReturn(Optional.of(metodoPagamento));
        when(gatewayPagamentoService.effettuaPagamento(1L, 10L)).thenReturn(true);

        boolean result = service.pagamentoCorsa(1L, 1L, 10L, 25.50);

        assertTrue(result);
    }

    @Test
    void pagamentoCorsa_WithWrongUser_ThrowsForbidden() {
        Utente otherUser = TestDataFactory.createUtente(2L, 2L, "Luigi", "Verdi", "luigi@example.com", com.smartmobility.model.enums.StatoUtente.attivo);
        metodoPagamento.setUtente(otherUser);

        when(utenteRepository.findByIdUtente(1L)).thenReturn(Optional.of(utente));
        when(metodoPagamentoRepository.findById(1L)).thenReturn(Optional.of(metodoPagamento));

        assertThrows(ResponseStatusException.class,
                () -> service.pagamentoCorsa(1L, 1L, 10L, 25.50));
    }

    @Test
    void elaboraDatiCarta_WithNewValidCard_ReturnsTrue() {
        when(metodoPagamentoRepository.existsByNumCarta("4111111111111111")).thenReturn(false);
        when(gatewayPagamentoService.convalidaCarta("4111111111111111", "12/28", "123", "Mario Rossi"))
                .thenReturn(true);
        when(utenteRepository.findByIdUtente(1L)).thenReturn(Optional.of(utente));

        boolean result = service.elaboraDatiCarta(1L, "4111111111111111", "12/28", "123", "Mario Rossi");

        assertTrue(result);
        verify(metodoPagamentoRepository).save(any(MetodoPagamento.class));
    }

    @Test
    void elaboraDatiCarta_WithDuplicateCard_ThrowsConflict() {
        when(metodoPagamentoRepository.existsByNumCarta("4111111111111111")).thenReturn(true);

        assertThrows(ResponseStatusException.class,
                () -> service.elaboraDatiCarta(1L, "4111111111111111", "12/28", "123", "Mario Rossi"));
    }

    @Test
    void elaboraDatiCarta_WithInvalidCard_ThrowsBadRequest() {
        when(metodoPagamentoRepository.existsByNumCarta("invalid")).thenReturn(false);
        when(gatewayPagamentoService.convalidaCarta("invalid", "12/28", "12", "Mario Rossi"))
                .thenReturn(false);

        assertThrows(ResponseStatusException.class,
                () -> service.elaboraDatiCarta(1L, "invalid", "12/28", "12", "Mario Rossi"));
    }

    @Test
    void recuperaMetodiSalvati_ReturnsMaskedList() {
        MetodoPagamento metodo1 = TestDataFactory.createMetodoPagamento(1L, "4111111111111111", "Mario Rossi", utente);
        MetodoPagamento metodo2 = TestDataFactory.createMetodoPagamento(2L, "5500000000000004", "Mario Rossi", utente);

        when(utenteRepository.findByIdUtente(utente.getIdUtente())).thenReturn(Optional.of(utente));
        when(metodoPagamentoRepository.findByIdUtente(utente.getIdUtente())).thenReturn(List.of(metodo1, metodo2));

        List<MetodoPagamentoResponse> methods = service.recuperaMetodiSalvati(utente.getIdUtente());

        assertEquals(2, methods.size());
        assertTrue(methods.get(0).getNumCarta().startsWith("****"));
        assertFalse(methods.get(0).getNumCarta().contains("4111"));
    }

    @Test
    void pagamentoCorsa_WithInvalidUtente_ThrowsNotFound() {
        when(utenteRepository.findByIdUtente(999L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> service.pagamentoCorsa(999L, 1L, 10L, 25.50));
    }
}

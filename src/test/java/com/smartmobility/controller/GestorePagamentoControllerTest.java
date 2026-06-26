package com.smartmobility.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartmobility.dto.request.AddPaymentMethodRequest;
import com.smartmobility.dto.request.ProcessPaymentRequest;
import com.smartmobility.dto.response.MetodoPagamentoResponse;
import com.smartmobility.security.SecurityHelper;
import com.smartmobility.service.GestorePagamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GestorePagamentoController.class)
class GestorePagamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GestorePagamentoService gestorePagamentoService;

    @MockBean
    private SecurityHelper securityHelper;

    @BeforeEach
    void setUp() {
        doNothing().when(securityHelper).requireUserIdMatch(any(), anyLong());
        when(securityHelper.getCurrentUserId(anyString())).thenReturn(1L);
    }

    @Test
    void processPayment_WithValidData_ReturnsTrue() throws Exception {
        ProcessPaymentRequest request = new ProcessPaymentRequest(1L, 1L, 10L, 25.50);
        when(gestorePagamentoService.pagamentoCorsa(1L, 1L, 10L, 25.50)).thenReturn(true);

        mockMvc.perform(post("/payments/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void processPayment_WhenPaymentFails_ReturnsFalse() throws Exception {
        ProcessPaymentRequest request = new ProcessPaymentRequest(1L, 1L, 10L, 25.50);
        when(gestorePagamentoService.pagamentoCorsa(1L, 1L, 10L, 25.50)).thenReturn(false);

        mockMvc.perform(post("/payments/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    void addPaymentMethod_WithValidData_ReturnsTrue() throws Exception {
        AddPaymentMethodRequest request = new AddPaymentMethodRequest(1L, "4111111111111111", "12/28", "123", "Mario Rossi");
        when(gestorePagamentoService.elaboraDatiCarta(1L, "4111111111111111", "12/28", "123", "Mario Rossi"))
                .thenReturn(true);

        mockMvc.perform(post("/payments/methods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void addPaymentMethod_WithDuplicateCard_ReturnsConflict() throws Exception {
        AddPaymentMethodRequest request = new AddPaymentMethodRequest(1L, "4111111111111111", "12/28", "123", "Mario Rossi");

        when(gestorePagamentoService.elaboraDatiCarta(1L, "4111111111111111", "12/28", "123", "Mario Rossi"))
                .thenThrow(new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.CONFLICT, "Carta già registrata"));

        mockMvc.perform(post("/payments/methods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void getSavedMethods_ReturnsListOfMethods() throws Exception {
        List<MetodoPagamentoResponse> methods = List.of(
                new MetodoPagamentoResponse(1L, "****1111", "Mario Rossi", null)
        );
        when(gestorePagamentoService.recuperaMetodiSalvati(1L)).thenReturn(methods);

        mockMvc.perform(get("/payments/methods").param("idUtente", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].numCarta").value("****1111"))
                .andExpect(jsonPath("$[0].intestatarioCarta").value("Mario Rossi"));
    }

    @Test
    void getSavedMethods_WithoutIdUtente_ReturnsCurrentUserMethods() throws Exception {
        List<MetodoPagamentoResponse> methods = List.of(
                new MetodoPagamentoResponse(2L, "****2222", "Anna Bianchi", null)
        );
        doReturn(2L).when(securityHelper).getCurrentUserId(anyString());
        when(gestorePagamentoService.recuperaMetodiSalvati(2L)).thenReturn(methods);

        mockMvc.perform(get("/payments/methods")
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2L))
                .andExpect(jsonPath("$[0].intestatarioCarta").value("Anna Bianchi"));
    }

    @Test
    void getSavedMethods_WhenUnauthorized_ReturnsUnauthorized() throws Exception {
        when(securityHelper.getCurrentUserId(anyString())).thenReturn(null);

        mockMvc.perform(get("/payments/methods")
                        .header("Authorization", "Bearer invalid-token"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void processPayment_WithInvalidData_ReturnsBadRequest() throws Exception {
        String invalidJson = "{\"idUtente\": null, \"idMetodoPagamento\": null}";

        mockMvc.perform(post("/payments/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}

package com.smartmobility.service.impl;

import com.smartmobility.dto.response.MetodoPagamentoResponse;
import com.smartmobility.integration.GatewayPagamentoService;
import com.smartmobility.model.MetodoPagamento;
import com.smartmobility.model.Utente;
import com.smartmobility.repository.MetodoPagamentoRepository;
import com.smartmobility.repository.UtenteRepository;
import com.smartmobility.service.GestorePagamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GestorePagamentoServiceImpl implements GestorePagamentoService {

    private static final String UTENTE_NON_TROVATO = "Utente non trovato";

    private final MetodoPagamentoRepository metodoPagamentoRepository;
    private final UtenteRepository utenteRepository;
    private final GatewayPagamentoService gatewayPagamentoService;

    public GestorePagamentoServiceImpl(MetodoPagamentoRepository metodoPagamentoRepository,
                                        UtenteRepository utenteRepository,
                                        GatewayPagamentoService gatewayPagamentoService) {
        this.metodoPagamentoRepository = metodoPagamentoRepository;
        this.utenteRepository = utenteRepository;
        this.gatewayPagamentoService = gatewayPagamentoService;
    }

    @Override
    @Transactional
    public boolean pagamentoCorsa(Long idUtente, Long idMetodoPagamento, Long idCorsa, double costo) {
        utenteRepository.findByIdUtente(idUtente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, UTENTE_NON_TROVATO));

        MetodoPagamento metodo = metodoPagamentoRepository.findById(idMetodoPagamento)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Metodo pagamento non trovato"));

        if (!metodo.getUtente().getIdUtente().equals(idUtente)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Metodo pagamento non appartiene all'utente");
        }

        return gatewayPagamentoService.effettuaPagamento(idMetodoPagamento, idCorsa, costo);
    }

    @Override
    @Transactional
    public boolean elaboraDatiCarta(Long idUtente, String numCarta, String dsCarta, String cvv, String intestatarioCarta) {
        if (metodoPagamentoRepository.existsByNumCarta(numCarta)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Carta già registrata");
        }

        if (!gatewayPagamentoService.convalidaCarta(numCarta, dsCarta, cvv, intestatarioCarta)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Carta non valida");
        }

        Utente utente = utenteRepository.findByIdUtente(idUtente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, UTENTE_NON_TROVATO));

        MetodoPagamento metodo = new MetodoPagamento();
        metodo.setNumCarta(numCarta);
        metodo.setIntestatarioCarta(intestatarioCarta);
        metodo.setDsCarta(dsCarta);
        metodo.setUtente(utente);
        metodoPagamentoRepository.save(metodo);

        return true;
    }

    @Override
    public List<MetodoPagamentoResponse> recuperaMetodiSalvati(Long idUtente) {
        Utente utente = utenteRepository.findByIdUtente(idUtente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, UTENTE_NON_TROVATO));
        return metodoPagamentoRepository.findByIdUtente(utente.getIdUtente()).stream()
                .map(m -> new MetodoPagamentoResponse(
                        m.getIdMetodoPagamento(),
                        maskCardNumber(m.getNumCarta()),
                        m.getIntestatarioCarta(),
                        m.getDsCarta()))
                .toList();
    }

    private String maskCardNumber(String numCarta) {
        if (numCarta == null || numCarta.length() < 4) return "****";
        return "****" + numCarta.substring(numCarta.length() - 4);
    }
}

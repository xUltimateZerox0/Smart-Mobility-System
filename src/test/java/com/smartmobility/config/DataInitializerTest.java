package com.smartmobility.config;

import com.smartmobility.model.MetodoPagamento;
import com.smartmobility.model.Mezzo;
import com.smartmobility.model.Operatore;
import com.smartmobility.model.PA;
import com.smartmobility.model.Utente;
import com.smartmobility.model.ZonaGeografica;
import com.smartmobility.repository.AttoreRepository;
import com.smartmobility.repository.MetodoPagamentoRepository;
import com.smartmobility.repository.MezzoRepository;
import com.smartmobility.repository.OperatoreRepository;
import com.smartmobility.repository.PARepository;
import com.smartmobility.repository.UtenteRepository;
import com.smartmobility.repository.ZonaGeograficaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.CommandLineRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataInitializerTest {

    @Mock private AttoreRepository attoreRepository;
    @Mock private UtenteRepository utenteRepository;
    @Mock private OperatoreRepository operatoreRepository;
    @Mock private PARepository paRepository;
    @Mock private MezzoRepository mezzoRepository;
    @Mock private ZonaGeograficaRepository zonaRepository;
    @Mock private MetodoPagamentoRepository metodoPagamentoRepository;

    @Captor private ArgumentCaptor<Utente> utenteCaptor;
    @Captor private ArgumentCaptor<PA> paCaptor;
    @Captor private ArgumentCaptor<Operatore> operatoreCaptor;
    @Captor private ArgumentCaptor<Mezzo> mezzoCaptor;
    @Captor private ArgumentCaptor<ZonaGeografica> zonaCaptor;
    @Captor private ArgumentCaptor<MetodoPagamento> metodoCaptor;

    private DataInitializer dataInitializer;

    @SuppressWarnings("unchecked")
    private static <T> T returnFirstArg(InvocationOnMock inv) {
        return (T) inv.getArgument(0);
    }

    @BeforeEach
    void setUp() {
        dataInitializer = new DataInitializer();

        lenient().when(utenteRepository.save(any(Utente.class))).thenAnswer(DataInitializerTest::returnFirstArg);
        lenient().when(paRepository.save(any(PA.class))).thenAnswer(DataInitializerTest::returnFirstArg);
        lenient().when(operatoreRepository.save(any(Operatore.class))).thenAnswer(DataInitializerTest::returnFirstArg);
        lenient().when(mezzoRepository.save(any(Mezzo.class))).thenAnswer(DataInitializerTest::returnFirstArg);
        lenient().when(zonaRepository.save(any(ZonaGeografica.class))).thenAnswer(DataInitializerTest::returnFirstArg);
        lenient().when(metodoPagamentoRepository.save(any(MetodoPagamento.class))).thenAnswer(DataInitializerTest::returnFirstArg);
    }

    private void mockAllUsersNotFound() {
        when(attoreRepository.findByEmail("test@smartmobility.com"))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(new Utente()));
        when(attoreRepository.findByEmail("pa@smartmobility.com")).thenReturn(Optional.empty());
        when(attoreRepository.findByEmail("tecnico@smartmobility.com")).thenReturn(Optional.empty());
        when(attoreRepository.findByEmail("sc@smartmobility.com")).thenReturn(Optional.empty());
    }

    private CommandLineRunner createRunner() {
        return dataInitializer.seedData(
                attoreRepository, utenteRepository, operatoreRepository,
                paRepository, mezzoRepository, zonaRepository,
                metodoPagamentoRepository);
    }

    @Test
    void seedData_whenDbEmpty_createsAllSeedData() throws Exception {
        mockAllUsersNotFound();
        when(mezzoRepository.count()).thenReturn(0L);
        when(zonaRepository.count()).thenReturn(0L);
        when(metodoPagamentoRepository.count()).thenReturn(0L);

        createRunner().run();

        verify(utenteRepository, times(2)).save(utenteCaptor.capture());
        Utente savedUtente = utenteCaptor.getAllValues().get(0);
        assertEquals("Mario", savedUtente.getNomeUtente());
        assertEquals("Rossi", savedUtente.getCognomeUtente());
        assertEquals("test@smartmobility.com", savedUtente.getEmail());

        verify(paRepository).save(paCaptor.capture());
        assertEquals("pa@smartmobility.com", paCaptor.getValue().getEmail());

        verify(operatoreRepository, times(2)).save(operatoreCaptor.capture());
        assertEquals("tecnico@smartmobility.com", operatoreCaptor.getAllValues().get(0).getEmail());
        assertEquals("sc@smartmobility.com", operatoreCaptor.getAllValues().get(1).getEmail());

        verify(mezzoRepository, times(5)).save(mezzoCaptor.capture());
        assertEquals(5, mezzoCaptor.getAllValues().size());

        verify(zonaRepository, times(3)).save(zonaCaptor.capture());
        assertEquals(3, zonaCaptor.getAllValues().size());

        verify(metodoPagamentoRepository).save(metodoCaptor.capture());
        assertEquals("4111111111111111", metodoCaptor.getValue().getNumCarta());
    }

    @Test
    void seedData_whenUsersAlreadyExist_skipsUserSeeding() throws Exception {
        Utente existingUtente = new Utente();
        existingUtente.setEmail("test@smartmobility.com");
        PA existingPa = new PA();
        existingPa.setEmail("pa@smartmobility.com");
        Operatore existingTecnico = new Operatore();
        existingTecnico.setEmail("tecnico@smartmobility.com");
        Operatore existingSc = new Operatore();
        existingSc.setEmail("sc@smartmobility.com");

        when(attoreRepository.findByEmail("test@smartmobility.com"))
                .thenReturn(Optional.of(existingUtente));
        when(attoreRepository.findByEmail("pa@smartmobility.com")).thenReturn(Optional.of(existingPa));
        when(attoreRepository.findByEmail("tecnico@smartmobility.com")).thenReturn(Optional.of(existingTecnico));
        when(attoreRepository.findByEmail("sc@smartmobility.com")).thenReturn(Optional.of(existingSc));
        when(mezzoRepository.count()).thenReturn(0L);
        when(zonaRepository.count()).thenReturn(0L);
        when(metodoPagamentoRepository.count()).thenReturn(0L);

        createRunner().run();

        verify(utenteRepository, never()).save(any(Utente.class));
        verify(paRepository, never()).save(any(PA.class));
        verify(operatoreRepository, never()).save(any(Operatore.class));
        verify(mezzoRepository, times(5)).save(any(Mezzo.class));
        verify(zonaRepository, times(3)).save(any(ZonaGeografica.class));
    }

    @Test
    void seedData_whenMezziAlreadyExist_skipsMezziSeeding() throws Exception {
        mockAllUsersNotFound();
        when(mezzoRepository.count()).thenReturn(1L);
        when(zonaRepository.count()).thenReturn(0L);
        when(metodoPagamentoRepository.count()).thenReturn(0L);

        createRunner().run();

        verify(utenteRepository, atLeastOnce()).save(any(Utente.class));
        verify(mezzoRepository, never()).save(any(Mezzo.class));
        verify(zonaRepository, times(3)).save(any(ZonaGeografica.class));
    }

    @Test
    void seedData_whenZoneAlreadyExist_skipsZoneSeeding() throws Exception {
        mockAllUsersNotFound();
        when(mezzoRepository.count()).thenReturn(0L);
        when(zonaRepository.count()).thenReturn(1L);
        when(metodoPagamentoRepository.count()).thenReturn(0L);

        createRunner().run();

        verify(utenteRepository, atLeastOnce()).save(any(Utente.class));
        verify(mezzoRepository, times(5)).save(any(Mezzo.class));
        verify(zonaRepository, never()).save(any(ZonaGeografica.class));
    }

    @Test
    void seedData_whenPaymentMethodsAlreadyExist_skipsPaymentSeeding() throws Exception {
        mockAllUsersNotFound();
        when(mezzoRepository.count()).thenReturn(0L);
        when(zonaRepository.count()).thenReturn(0L);
        when(metodoPagamentoRepository.count()).thenReturn(1L);

        createRunner().run();

        verify(utenteRepository, atLeastOnce()).save(any(Utente.class));
        verify(mezzoRepository, times(5)).save(any(Mezzo.class));
        verify(zonaRepository, times(3)).save(any(ZonaGeografica.class));
        verify(metodoPagamentoRepository, never()).save(any(MetodoPagamento.class));
    }

    @Test
    void seedData_paymentMethods_onlyCreatedForUtenteInstance() throws Exception {
        when(attoreRepository.findByEmail("test@smartmobility.com"))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(new PA()));
        when(attoreRepository.findByEmail("pa@smartmobility.com")).thenReturn(Optional.empty());
        when(attoreRepository.findByEmail("tecnico@smartmobility.com")).thenReturn(Optional.empty());
        when(attoreRepository.findByEmail("sc@smartmobility.com")).thenReturn(Optional.empty());
        when(mezzoRepository.count()).thenReturn(0L);
        when(zonaRepository.count()).thenReturn(0L);
        when(metodoPagamentoRepository.count()).thenReturn(0L);

        createRunner().run();

        verify(metodoPagamentoRepository, never()).save(any(MetodoPagamento.class));
    }

    @Test
    void seedData_password_usesEnvironmentVariableWhenSet() throws Exception {
        mockAllUsersNotFound();
        when(mezzoRepository.count()).thenReturn(0L);
        when(zonaRepository.count()).thenReturn(0L);
        when(metodoPagamentoRepository.count()).thenReturn(0L);

        createRunner().run();

        verify(utenteRepository, times(2)).save(utenteCaptor.capture());
        Utente saved = utenteCaptor.getAllValues().get(0);
        assertNotNull(saved.getPassword());
        assertTrue(saved.getPassword().startsWith("$2a$") || saved.getPassword().startsWith("$2b$"),
                "Password should be BCrypt-encoded");
    }

    @Test
    void seedData_mezzoProperties_areCorrect() throws Exception {
        mockAllUsersNotFound();
        when(mezzoRepository.count()).thenReturn(0L);
        when(zonaRepository.count()).thenReturn(0L);
        when(metodoPagamentoRepository.count()).thenReturn(0L);

        createRunner().run();

        verify(mezzoRepository, times(5)).save(mezzoCaptor.capture());
        var mezzi = mezzoCaptor.getAllValues();

        assertEquals("bici", mezzi.get(0).getTipo());
        assertEquals("scooter", mezzi.get(1).getTipo());
        assertEquals("auto", mezzi.get(2).getTipo());
        assertEquals("bici", mezzi.get(3).getTipo());
        assertEquals("scooter", mezzi.get(4).getTipo());
    }

    @Test
    void seedData_zonaProperties_areCorrect() throws Exception {
        mockAllUsersNotFound();
        when(mezzoRepository.count()).thenReturn(0L);
        when(zonaRepository.count()).thenReturn(0L);
        when(metodoPagamentoRepository.count()).thenReturn(0L);

        createRunner().run();

        verify(zonaRepository, times(3)).save(zonaCaptor.capture());
        var zone = zonaCaptor.getAllValues();

        assertEquals("ZTL", zone.get(0).getTipoRestrizione().name());
        assertEquals("divieto_parcheggio", zone.get(1).getTipoRestrizione().name());
        assertEquals("limite_velocita", zone.get(2).getTipoRestrizione().name());
    }

    @Test
    void seedData_utenteHashPassword_worksCorrectly() throws Exception {
        mockAllUsersNotFound();
        when(mezzoRepository.count()).thenReturn(0L);
        when(zonaRepository.count()).thenReturn(0L);
        when(metodoPagamentoRepository.count()).thenReturn(0L);

        createRunner().run();

        verify(paRepository).save(paCaptor.capture());
        PA savedPa = paCaptor.getValue();
        assertNotNull(savedPa.getPassword());
        assertTrue(savedPa.getPassword().startsWith("$2a$") || savedPa.getPassword().startsWith("$2b$"));
    }
}

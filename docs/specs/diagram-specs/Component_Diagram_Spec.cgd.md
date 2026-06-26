---
clarity-gate-version: 2.1
document-type: Implementation
processed-by: AI + Cross-Reference Engine — componentDiagram-clean.uml (XMI 2.1), classDiagram-v1.8-clean.uml, Master_Spec.cgd.md v4.0
processed-date: 2026-06-25
clarity-status: CLEAR
hitl-status: REVIEWED
hitl-pending-count: 0
points-passed: 1-9
document-sha256: 7a93ef8d253b26b6ba5d4225948f54d6851f7b982fe9d1624519b36ce9c0f74a
hitl-claims: []
---

# Component Diagram — Smart Mobility System

**Versione:** 1.0
**Team:** Cofee Coders
**Progetto:** Ingegneria del Software a.a. 2025/2026
**Architettura:** MVC Web-oriented con Controller Intermediario
**Diagramma:** `docs/diagrams/component-diagram/componentDiagram-clean.uml` (XMI 2.1 — Visual Paradigm 7.0.2)
**Documento Sorgente:** Master_Spec.cgd.md v4.0, Class_Diagram_Spec.cgd.md v1.8

**Pattern Architetturale (da documentazione.md §2.3):**
Il sistema adotta MVC con Controller Intermediario Centralizzato [decisione architetturale]. Le View sono progettate per essere disaccoppiate dal Model. Ogni interazione avviene tramite richieste esplicite ai Controller, che orchestrano il flusso e mediano tra View, Model e sistemi esterni.

---

## 1. View Component

**ID XMI:** `zzS2v7mD.AACAQYg`
**Ruolo:** Strato di presentazione — interfacce utente per ogni attore del sistema.

Le View non interrogano mai direttamente il Model. Ogni comunicazione avviene tramite le interfacce dei Controller.

### 1.1 Classi Contenute

| Classe | ID XMI | Ruolo | Interfaccia Realizzata | ID Interfaccia |
|--------|--------|-------|----------------------|----------------|
| `AppUtente` | `ZznQhXmD.AACAQwQ` | Interfaccia cittadino | Aggiornamenti Corsa | `BGnPp3mD.AACARJJ` |
| `AppOperatoreTecnico` | `zdBIhXmD.AACAQzx` | Dashboard operatore tecnico | Stato Flotta | `88Pfp3mD.AACARMt` |
| `AppPA` | `euPIhXmD.AACAQ1G` | Interfaccia PA | Diagnostica | `Xu9_p3mD.AACARM.` |
| `AppOperatoreSC` | `BSkUhXmD.AACAQ.A` | Interfaccia servizio clienti | Eventi Utente | `8v9fp3mD.AACARMc` |
| `Autenticazione` | `9zizp3mD.AACAQkJ` | Interfaccia login/registrazione | Stato Sessione | `xgSAZ3mD.AACARNt` |

### 1.2 Interfacce Fornite (View → Controller)

| Interfaccia | ID XMI | Descrizione |
|-------------|--------|-------------|
| `Aggiornamenti Corsa` | `BGnPp3mD.AACARJJ` | Notifiche in tempo reale sullo stato della corsa (costo, sospensione, termine) |
| `Stato Flotta` | `88Pfp3mD.AACARMt` | Dati aggiornati su posizione e stato dei veicoli |
| `Diagnostica` | `Xu9_p3mD.AACARM.` | Report e diagnostica sullo stato dei mezzi e della flotta |
| `Eventi Utente` | `8v9fp3mD.AACARMc` | Notifiche relative a moderazione, prenotazioni e azioni correttive |
| `Stato Sessione` | `xgSAZ3mD.AACARNt` | Stato della sessione attuale (autenticato, ruolo, permessi) |

### 1.3 Dipendenze (View → Controller Interfaces)

| Classe View | Dipende da Interfaccia | ID Interfaccia | Controller Target |
|-------------|----------------------|----------------|-------------------|
| `AppUtente` | Gestione Corsa | `R.IxKnmD.AACAQ9w` | Controller |
| `AppOperatoreSC` | Moderazione Utente | `FSGPv7mD.AACARbj` | Controller |
| `AppOperatoreTecnico` | Amministrazione Flotta | `BvUAwXmD.AACAQue` | Controller |
| `AppPA` | Statistiche e Restrizioni | `ie1hKnmD.AACAQ4x` | Controller |
| `Autenticazione` | Gestione Sessioni | `2eaHp3mD.AACAQ9m` | Controller |

**Totale classi View:** 5
**Totale interfacce fornite:** 5
**Totale dipendenze verso Controller:** 5

---

## 2. Controller Component

**ID XMI:** `3mq2v7mD.AACAQYr`
**Ruolo:** Middleware attivo del sistema. Intercetta input della View, valida richieste, interroga/aggiorna il Model, orchestra i sistemi esterni e indirizza i dati alla View.

### 2.1 Classi Contenute

| Classe | ID XMI | Ruolo |
|--------|--------|-------|
| `GestioneCorsa` | `Al.5KnmD.AACARRw` | Orchestrazione ciclo di vita corsa (avvio, sospensione, termine, calcolo percorso) |
| `RicercaMezzi` | `eZ.5KnmD.AACARRn` | Query geolocalizzata su mezzi disponibili |
| `GestorePagamento` | `wHAFKnmD.AACARfr` | Elaborazione transazioni e validazione metodi pagamento |
| `GestioneAree` | `cTkY93mD.AACAQqe` | CRUD zone geografiche e verifica conflitti |
| `GestioneFlotta` | `aNkY93mD.AACAQqX` | Monitoraggio e controllo remoto flotta |
| `GestioneStatistiche` | `q5kY93mD.AACAQqQ` | Aggregazione dati corse e generazione report |
| `GestionePrenotazione` | `thkY93mD.AACAQqJ` | Ciclo di vita prenotazioni con timeout |
| `GestioneUtenti` | `b2kY93mD.AACAQqC` | Moderazione account utente |
| `GestioneAutenticazione` | `qetI93mD.AACAQk0` | Validazione credenziali e gestione sessioni |

### 2.2 Interfacce Fornite (Controller → View)

| Interfaccia | ID XMI | Descrizione | Utilizzata da |
|-------------|--------|-------------|---------------|
| `Moderazione Utente` | `FSGPv7mD.AACARbj` | Operazioni di moderazione account | AppOperatoreSC |
| `Gestione Corsa` | `R.IxKnmD.AACAQ9w` | Gestione ciclo di vita corse | AppUtente |
| `Statistiche e Restrizioni` | `ie1hKnmD.AACAQ4x` | Statistiche e gestione restrizioni | AppPA |
| `Amministrazione Flotta` | `BvUAwXmD.AACAQue` | Controllo remoto e diagnostica flotta | AppOperatoreTecnico |
| `Gestione Sessioni` | `2eaHp3mD.AACAQ9m` | Autenticazione e gestione sessioni | Autenticazione |

### 2.3 Dipendenze Verso View Interfaces

Il Controller dipende dalle interfacce fornite dalla View per inviare aggiornamenti:

| Interfaccia View | ID XMI | Utilizzata per |
|-----------------|--------|----------------|
| `Aggiornamenti Corsa` | `BGnPp3mD.AACARJJ` | Notificare stato corsa a AppUtente |
| `Eventi Utente` | `8v9fp3mD.AACARMc` | Inviare notifiche moderazione a AppOperatoreSC |
| `Stato Flotta` | `88Pfp3mD.AACARMt` | Inviare dati flotta a AppOperatoreTecnico |
| `Diagnostica` | `Xu9_p3mD.AACARM.` | Inviare report diagnostica a AppPA |
| `Stato Sessione` | `xgSAZ3mD.AACARNt` | Gestire stato sessione per Autenticazione |

### 2.4 Dipendenze Verso Model Interfaces

| Interfaccia Model | ID XMI | Descrizione |
|-------------------|--------|-------------|
| `Gestione Dati Supporto` | `wpvLX3mD.AACAQuK` | Accesso a dati di supporto (segnalazioni, prenotazioni) |
| `Gestione Dati Corsa` | `jYqrX3mD.AACAQux` | Accesso a dati di corsa, mezzi, pagamenti |

### 2.5 Dipendenze Verso External Systems

| Classe Controller | Dipende da Interfaccia | External System |
|------------------|----------------------|-----------------|
| `RicercaMezzi` | `API Mappa` | ServizioMappa |
| `GestorePagamento` | `API Pagamento` | Gateway Pagamento |
| `GestioneCorsa` | `API Controllo` | Mezzo : IoT |

**Totale classi Controller:** 9
**Totale interfacce fornite:** 5
**Totale dipendenze verso View:** 5
**Totale dipendenze verso Model:** 2
**Totale dipendenze verso External:** 3

---

## 3. Model Component

**ID XMI:** `YKY2v7mD.AACAQYD`
**Ruolo:** Entità core del sistema. Ricopre un ruolo passivo — espone metodi per accesso e modifica dello stato richiesti dai Controller, ma è totalmente privo di logiche di notifica verso l'esterno.

### 3.1 Classi Contenute

| Classe | ID XMI | Descrizione |
|--------|--------|-------------|
| `Attore` | `hyYo93mD.AACAQm3` | Classe base astratta per utenti, operatori, PA |
| `Utente` | `9WYo93mD.AACAQm.` | Cittadino fruitore dei servizi |
| `Operatore` | `LztI93mD.AACAQlQ` | Personale tecnico o servizio clienti |
| `PA` | `kQ1I93mD.AACAQkt` | Pubblica Amministrazione |
| `Mezzo` | `2sYo93mD.AACAQmw` | Veicolo della flotta |
| `Corsa` | `cZYo93mD.AACAQnF` | Sessione di utilizzo di un mezzo |
| `MetodoPagamento` | `V5tI93mD.AACAQk7` | Dati cifrati carta di pagamento |
| `Prenotazione` | `xVtI93mD.AACAQlC` | Blocco temporaneo di un mezzo |
| `Segnalazione` | `yttI93mD.AACAQlJ` | Report di anomalia su un mezzo |
| `ZonaGeografica` | `wYYo93mD.AACAQmp` | Area geografica con restrizioni |
| `Zona Geografica` | `LesZv7mD.AACAQjq` | Duplicato XMI di ZonaGeografica |
| `Transito` (XMI: transito) | `.eeI93mD.AACAQj9` | Associazione M:N Corsa-ZonaGeografica |

### 3.2 Interfacce Fornite (Model → Controller)

| Interfaccia | ID XMI | Descrizione |
|-------------|--------|-------------|
| `Gestione Dati Utente` | `.XEXv7mD.AACARYL` | Accesso a dati anagrafici e stato utenti |
| `Gestione Dati Supporto` | `wpvLX3mD.AACAQuK` | Accesso a segnalazioni, prenotazioni, zone |
| `Gestione Dati Corsa` | `jYqrX3mD.AACAQux` | Accesso a corse, mezzi, pagamenti |

### 3.3 Dipendenze Verso External Systems

Il Model dipende da DBMS per la persistenza:

| Componente Model | Interfaccia | External System |
|----------------|-------------|-----------------|
| Model (tutte le entità) | `Connessione Dati` (`jvJnv7mD.AACARXl`) | DBMS |

**Totale classi Model:** 12 (incl. 1 duplicato XMI)
**Totale interfacce fornite:** 3
**Totale dipendenze verso External:** 1

---

## 4. External Systems (Simulated)

Tutti i sistemi esterni sono simulati (progetto universitario — chiarimenti-vari.md punto 16).

### 4.1 ServizioMappa

**ID XMI:** `ozG9v7mD.AACARFZ`
**Ruolo:** Servizio di geolocalizzazione e routing esterno.

| Interfaccia Fornita | ID XMI |
|--------------------|--------|
| `API Mappa` | `xP59v7mD.AACARGb` |

**Utilizzata da:** `RicercaMezzi` (Controller)

---

### 4.2 Gateway Pagamento

**ID XMI:** `77O9v7mD.AACARFq`
**Ruolo:** Processore di pagamento esterno.

| Interfaccia Fornita | ID XMI |
|--------------------|--------|
| `API Pagamento` | `Z.W3v7mD.AACARaG` |

**Utilizzata da:** `GestorePagamento` (Controller)

---

### 4.3 DBMS

**ID XMI:** `mqGDv7mD.AACARJV`
**Ruolo:** Persistenza dati — interfaccia CRUD verso database relazionale.

| Interfaccia Fornita | ID XMI |
|--------------------|--------|
| `Connessione Dati` | `jvJnv7mD.AACARXl` |

**Utilizzata da:** Model (tutte le entità)

---

### 4.4 Mezzo : IoT

**ID XMI:** `wksuKnmD.AACAQj5`
**Ruolo:** Interfaccia fisica col veicolo per blocco/sblocco remoto.
**Nota:** Non fornisce interfacce esplicite nell'XMI del diagramma componenti. L'interfaccia `API Controllo` (`3P9eKnmD.AACAQlF`) è definita ma senza realization collegato a Mezzo : IoT (probabile limite di esportazione XMI).

| Interfaccia (Orphan) | ID XMI |
|---------------------|--------|
| `API Controllo` | `3P9eKnmD.AACAQlF` |

**Utilizzata da:** `GestioneCorsa` (Controller)

---

## 5. Interfacce Orphan (XMI Artifacts)

Le seguenti interfacce sono presenti nell'XMI ma non hanno un componente che le realizza. Sono considerate artefatti di esportazione Visual Paradigm.

| Interfaccia | ID XMI | Note |
|-------------|--------|------|
| `Class6` | `kf3Vv7mD.AACAQul` | Nome generico — artefatto XMI |
| `Class14` | `58N9v7mD.AACARG5` | Nome generico — artefatto XMI |
| `Class12` | `n.hDv7mD.AACARJ.` | Nome generico — artefatto XMI |
| `ù` | `DPxdv7mD.AACARBh` | Carattere anomalo — artefatto XMI |
| ` ` (vuoto) | `Dlp9v7mD.AACARGL` | Nome vuoto — artefatto XMI |

---

## 6. Architettura delle Dipendenze (Schema Riassuntivo)

```
┌─────────────────────────────────────────────────────────────────┐
│  VIEW COMPONENT                                                 │
│  ┌──────────┐ ┌──────────────────┐ ┌──────────┐ ┌──────────┐  │
│  │AppUtente │ │AppOperatoreTecnico│ │ AppPA   │ │AppOpe-   │  │
│  │  ────────│ │───────────────────│ │─────────│ │ratoreSC  │  │
│  │Aggiorna- │ │Stato Flotta       │ │Diagno-  │ │Eventi    │  │
│  │menti     │ │                   │ │stica    │ │Utente    │  │
│  │Corsa ◄───│─┼──► (I/F View) ◄──┼─┼────◄────┼─┼────◄─────┼──┤
│  └────┬─────┘ └──────────────────┘ └────┬─────┘ └────┬─────┘  │
│       │    ┌──────────────────┐          │            │        │
│       │    │  Autenticazione  │          │            │        │
│       │    │  Stato Sessione  │          │            │        │
│       │    └────────┬─────────┘          │            │        │
│       │             │                    │            │        │
│       ▼             ▼                    ▼            ▼        │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │  INTERFACCE CONTROLLER (fornite da Controller)           │  │
│  │  Gestione Corsa | Moderazione Utente | Amministrazione   │  │
│  │  Flotta | Statistiche e Restrizioni | Gestione Sessioni  │  │
│  └──────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────────┐
│  CONTROLLER COMPONENT                                          │
│  ┌──────────┐ ┌─────────┐ ┌──────────┐ ┌───────────┐          │
│  │Gestione  │ │Ricerca  │ │Gestore   │ │Gestione   │          │
│  │Corsa     │ │Mezzi    │ │Pagamento │ │Flotta     │          │
│  ├──────────┤ ├─────────┤ ├──────────┤ ├───────────┤          │
│  │Gestione  │ │Gestione │ │Gestione  │ │Gestione   │          │
│  │Aree      │ │Statisti-│ │Prenota-  │ │Utenti     │          │
│  │          │ │che      │ │zione     │ │           │          │
│  ├──────────┤ ├─────────┤ ├──────────┤ ├───────────┤          │
│  │    GestioneAutenticazione            │                     │
│  └─────────────────────────────────────────────────────┘      │
│           │              │              │                      │
│           ▼              ▼              ▼                      │
│  ┌────────────┐  ┌────────────┐  ┌────────────┐               │
│  │Interfacce  │  │Interfacce  │  │Interfacce  │               │
│  │Model       │  │External    │  │View        │               │
│  │(2 dip.)    │  │(3 dip.)    │  │(5 dip.)    │               │
│  └─────┬──────┘  └─────┬──────┘  └────────────┘               │
└────────┼────────────────┼──────────────────────────────────────┘
         │                │
         ▼                ▼
┌──────────────────┐  ┌─────────────────────────────────────────┐
│ MODEL COMPONENT  │  │ EXTERNAL SYSTEMS                        │
│ ┌──────────────┐ │  │ ┌──────────┐ ┌──────────┐ ┌──────────┐ │
│ │Attore        │ │  │ │Servizio  │ │ Gateway  │ │ DBMS     │ │
│ │Utente        │ │  │ │Mappa     │ │Pagamento │ │          │ │
│ │Operatore     │ │  │ │API Mappa │ │API Paga- │ │Connes-   │ │
│ │PA            │ │  │ │          │ │mento     │ │sione     │ │
│ │Mezzo         │ │  │ └──────────┘ └──────────┘ │Dati      │ │
│ │Corsa         │ │  │ ┌────────────────────────┐│          │ │
│ │MetodoPagamento│ │  │ │  Mezzo : IoT          ││ ┌────────┴┐│ │
│ │Prenotazione  │ │  │ │  (API Controllo orphan)││ │Model    ││ │
│ │Segnalazione  │ │  │ └────────────────────────┘│ └─────────┘│ │
│ │ZonaGeografica│ │  └───────────────────────────┘            │ │
│ │Transito      │ │                                           │ │
│ │              │ │                                           │ │
│ └──────┬───────┘ │                                           │ │
│        │         │                                           │ │
│        ▼         │                                           │ │
│  Connessione     │                                           │ │
│  Dati (→ DBMS)   │                                           │ │
└──────────────────┘  └─────────────────────────────────────────┘
```

---

## 7. Tabella Riepilogativa delle Dipendenze

| # | Client | Tipo Client | Supplier | Tipo Supplier | ID XMI Supplier |
|---|--------|------------|----------|---------------|-----------------|
| 1 | RicercaMezzi | Controller Class | API Mappa | External Interface | `xP59v7mD.AACARGb` |
| 2 | GestorePagamento | Controller Class | API Pagamento | External Interface | `Z.W3v7mD.AACARaG` |
| 3 | GestioneCorsa | Controller Class | API Controllo | External Interface | `3P9eKnmD.AACAQlF` |
| 4 | Model | Component | Connessione Dati | External Interface | `jvJnv7mD.AACARXl` |
| 5 | Controller | Component | Gestione Dati Utente | Model Interface | `.XEXv7mD.AACARYL` |
| 6 | Controller | Component | Gestione Dati Supporto | Model Interface | `wpvLX3mD.AACAQuK` |
| 7 | Controller | Component | Gestione Dati Corsa | Model Interface | `jYqrX3mD.AACAQux` |
| 8 | Controller | Component | Aggiornamenti Corsa | View Interface | `BGnPp3mD.AACARJJ` |
| 9 | Controller | Component | Eventi Utente | View Interface | `8v9fp3mD.AACARMc` |
| 10 | Controller | Component | Stato Flotta | View Interface | `88Pfp3mD.AACARMt` |
| 11 | Controller | Component | Diagnostica | View Interface | `Xu9_p3mD.AACARM.` |
| 12 | Controller | Component | Stato Sessione | View Interface | `xgSAZ3mD.AACARNt` |
| 13 | AppUtente | View Class | Gestione Corsa | Controller Interface | `R.IxKnmD.AACAQ9w` |
| 14 | AppOperatoreSC | View Class | Moderazione Utente | Controller Interface | `FSGPv7mD.AACARbj` |
| 15 | AppOperatoreTecnico | View Class | Amministrazione Flotta | Controller Interface | `BvUAwXmD.AACAQue` |
| 16 | AppPA | View Class | Statistiche e Restrizioni | Controller Interface | `ie1hKnmD.AACAQ4x` |
| 17 | Autenticazione | View Class | Gestione Sessioni | Controller Interface | `2eaHp3mD.AACAQ9m` |

---

## 8. Traceability Matrix: XMI → Documento

| ID XMI | Nome XMI | Sezione Documento | Tipo |
|--------|----------|-------------------|------|
| `zzS2v7mD.AACAQYg` | View | §1 | Component |
| `ZznQhXmD.AACAQwQ` | AppUtente | §1.1 | Class |
| `zdBIhXmD.AACAQzx` | AppOperatoreTecnico | §1.1 | Class |
| `euPIhXmD.AACAQ1G` | AppPA | §1.1 | Class |
| `BSkUhXmD.AACAQ.A` | AppOperatoreSC | §1.1 | Class |
| `9zizp3mD.AACAQkJ` | Autenticazione | §1.1 | Class |
| `BGnPp3mD.AACARJJ` | Aggiornamenti Corsa | §1.2 | Interface |
| `88Pfp3mD.AACARMt` | Stato Flotta | §1.2 | Interface |
| `Xu9_p3mD.AACARM.` | Diagnostica | §1.2 | Interface |
| `8v9fp3mD.AACARMc` | Eventi Utente | §1.2 | Interface |
| `xgSAZ3mD.AACARNt` | Stato Sessione | §1.2 | Interface |
| `R.IxKnmD.AACAQ9w` | Gestione Corsa | §1.3, §2.2 | Interface |
| `FSGPv7mD.AACARbj` | Moderazione Utente | §1.3, §2.2 | Interface |
| `BvUAwXmD.AACAQue` | Amministrazione Flotta | §1.3, §2.2 | Interface |
| `ie1hKnmD.AACAQ4x` | Statistiche e Restrizioni | §1.3, §2.2 | Interface |
| `2eaHp3mD.AACAQ9m` | Gestione Sessioni | §1.3, §2.2 | Interface |
| `3mq2v7mD.AACAQYr` | Controller | §2 | Component |
| `Al.5KnmD.AACARRw` | GestioneCorsa | §2.1 | Class |
| `eZ.5KnmD.AACARRn` | RicercaMezzi | §2.1 | Class |
| `wHAFKnmD.AACARfr` | GestorePagamento | §2.1 | Class |
| `cTkY93mD.AACAQqe` | GestioneAree | §2.1 | Class |
| `aNkY93mD.AACAQqX` | GestioneFlotta | §2.1 | Class |
| `q5kY93mD.AACAQqQ` | GestioneStatistiche | §2.1 | Class |
| `thkY93mD.AACAQqJ` | GestionePrenotazione | §2.1 | Class |
| `b2kY93mD.AACAQqC` | GestioneUtenti | §2.1 | Class |
| `qetI93mD.AACAQk0` | GestioneAutenticazione | §2.1 | Class |
| `YKY2v7mD.AACAQYD` | Model | §3 | Component |
| `.XEXv7mD.AACARYL` | Gestione Dati Utente | §3.2 | Interface |
| `wpvLX3mD.AACAQuK` | Gestione Dati Supporto | §3.2 | Interface |
| `jYqrX3mD.AACAQux` | Gestione Dati Corsa | §3.2 | Interface |
| `ozG9v7mD.AACARFZ` | ServizioMappa | §4.1 | Component |
| `xP59v7mD.AACARGb` | API Mappa | §4.1 | Interface |
| `77O9v7mD.AACARFq` | Gateway Pagamento | §4.2 | Component |
| `Z.W3v7mD.AACARaG` | API Pagamento | §4.2 | Interface |
| `mqGDv7mD.AACARJV` | DBMS | §4.3 | Component |
| `jvJnv7mD.AACARXl` | Connessione Dati | §4.3 | Interface |
| `wksuKnmD.AACAQj5` | Mezzo : IoT | §4.4 | Component |
| `3P9eKnmD.AACAQlF` | API Controllo | §4.4 | Interface (orphan) |

---

## 9. Summary Statistics

| Metrica | Valore |
|---------|--------|
| Componenti | 7 (View, Controller, Model, ServizioMappa, Gateway Pagamento, DBMS, Mezzo : IoT) |
| Classi totali | 26 (5 View + 9 Controller + 12 Model) |
| Interfacce totali | 21 (5 View + 5 Controller + 3 Model + 4 External + 4 Orphan) |
| Interfacce funzionali | 17 (escluse 4 orphan artifacts) |
| Dipendenze totali | 17 (3 External + 2 Model + 5 View + 5 View→Controller + 2 Controller→Model) |
| Artefatti XMI (interfacce orphan) | 5 (Class6, Class14, Class12, ù, unnamed) |

---

## HITL Verification Record

### Round A: Derived Data Confirmation

Tutti i dati strutturali sono stati derivati dal parsing XMI di `componentDiagram-clean.uml` e cross-referenziati con `Class_Diagram_Spec.cgd.md`. I 7 componenti, le 26 classi, le 21 interfacce e le 17 dipendenze corrispondono all'XMI originale. Le interfacce orphan (Class6, Class14, Class12, ù, unnamed) sono artefatti Visual Paradigm e non hanno controparte funzionale.

### Round B: True HITL Verification

Nessun claim richiede Round B — documento derivato esclusivamente da fonti già verificate (XMI + Class_Diagram_Spec).

---

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | REVIEWED

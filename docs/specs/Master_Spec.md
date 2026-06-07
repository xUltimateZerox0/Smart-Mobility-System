```yaml
status: SUCCESS_WITH_INFERENCE
extractor_version: "2.0-unsafe"
source_diagrams: 3
  - Use Case Diagram (UC-diagram-clean.xml.uml)
  - Component Diagram (componentdiagram-clean.uml)
  - Class Diagram (classDiagram-clean.xml.uml)
total_parsed_entities: 106
```

# Master UML-XMI Semantic Extraction Report (Unsafe Mode)

**Sistema:** Smart Mobility System  
**Exporter:** Visual Paradigm 7.0.2  
**XMI Version:** 2.1  
**Profili applicati:** Diagramma_Casi_d_uso_profile, cofeecoders_profile, Classi_Diagram_profile

> **Nota Metodologica:** Questo documento è il risultato della fusione semantica delle estrazioni dai tre diagrammi UML del sistema. Ogni deduzione non supportata da testo XMI esplicito è marcata con **[INFERRED]**. Il cross-referencing tra diagrammi è usato per rafforzare o validare le inferenze.

---

## 1. System Boundaries & Actors

### 1.1 Confine di Sistema

Il sistema **Smart Mobility System** è una piattaforma di mobilità condivisa (sharing mobility). Il confine di sistema è definito esplicitamente nel diagramma Use Case come package `uml:Model` (`oJU7knmD.AACAQl.`).

### 1.2 Attori

Identificati da **3 fonti concordanti**: Use Case Diagram (uml:Actor), Component Diagram (classi View), Class Diagram (gerarchia di classi).

| Attore | UC Diagram (Actor) | Component (View) | Class Diagram | Descrizione |
|---|---|---|---|---|
| **Utente** | `DtcDknmD.AACARAz` | AppUtente (`ZznQhXmD.AACAQwQ`) | Utente (`TP7MYnmD.AACAQ2W`) extends Attore | [INFERRED] Utente finale della piattaforma. Cerca mezzi, prenota, gestisce corse, paga, richiede sospensioni. |
| **PA** | `hxcTknmD.AACARG_` | AppPA (`euPIhXmD.AACAQ1G`) | PA (`KdrcYnmD.AACAQ9j`) extends Attore | [INFERRED] Pubblica Amministrazione. Monitora statistiche, analizza la flotta, gestisce restrizioni geografiche. |
| **Operatore Tecnico** | `FA3zknmD.AACARIt` | AppOperatoreTecnico (`zdBIhXmD.AACAQzx`) | Operatore (`DP7MYnmD.AACAQ2U`) extends Attore | [INFERRED] Operatore specializzato nella gestione della flotta: manutenzione, rilocazione, stato operativo veicoli. |
| **Operatore Servizio Clienti** | `tkvzknmD.AACARI2` | AppOperatoreSC (`BSkUhXmD.AACAQ.A`) | Operatore (`DP7MYnmD.AACAQ2U`) extends Attore | [INFERRED] Operatore di supporto customer care: modera utenti, amministra prenotazioni. |

#### Gerarchia Attori (Class Diagram)

```
Attore (hP7MYnmD.AACAQ2H)
  ├── email: String
  └── id: Integer
  ├── Utente (TP7MYnmD.AACAQ2W)
  │     +coordinateUtente, +numMezziPrenotati, +nomeUtente, +cognomeUtente, +telefono
  │     -reportUtente, -idUtente
  │     [16 operazioni incl. ricercaUtente(static), azioneCorrettiva]
  ├── Operatore (DP7MYnmD.AACAQ2U) — classe marker
  └── PA (KdrcYnmD.AACAQ9j) — classe marker
```

> **Cross-Reference:** Il Class Diagram usa una sola classe `Operatore` come marker, mentre il UC Diagram distingue esplicitamente "Operatore Tecnico" e "Operatore Servizio Clienti". Il Component Diagram conferma la distinzione con due View separate (AppOperatoreTecnico, AppOperatoreSC).

---

## 2. Architettura del Sistema

### 2.1 Pattern Architetturale: MVC

Il Component Diagram definisce esplicitamente un'architettura **Model-View-Controller** con tre componenti principali:

```
┌─────────────────────────────────────────────────────────────────────┐
│                      Smart Mobility System                         │
│                                                                     │
│  ┌──────────────┐    ┌──────────────┐    ┌──────────────┐          │
│  │     VIEW     │───▶│  CONTROLLER  │───▶│    MODEL     │          │
│  │              │    │              │    │              │          │
│  │ AppUtente    │    │ Moderaz.Ut.  │    │ Gestione Dati│          │
│  │ AppOperTec   │    │ Gestione Cor.│    │ ZonaGeograf. │          │
│  │ AppPA        │    │ Stat.&Restr. │    │              │          │
│  │ AppOperSC    │    │ Profilazione │    └──────┬───────┘          │
│  └──────────────┘    │ Amm. Flotta  │           │                  │
│                      │              │    ┌──────▼───────┐           │
│                      │ GestioneCorsa│    │    DBMS      │           │
│                      │ RicercaMezzi │    │ Conness. Dati│           │
│                      │ GestorePag.  │    └──────────────┘           │
│                      └──────┬───────┘                               │
│                             │                                       │
│              ┌──────────────┼──────────────┐                        │
│              ▼              ▼              ▼                        │ 
│  ┌──────────────┐ ┌──────────────┐ ┌──────────────┐                 │
│  │ServizioMappa │ │Gateway Pagam.│ │  Mezzo: IoT  │                 │
│  │  API Mappa   │ │ API Pagamento│ │ API Controllo│                 │
│  └──────────────┘ └──────────────┘ └──────────────┘                 │
└─────────────────────────────────────────────────────────────────────┘
```

### 2.2 Componenti & Interfacce (Component Diagram)

#### Layer View

| Componente | xmi:id | Interfacce Consumate (<<use>>) |
|---|---|---|
| **AppUtente** | `ZznQhXmD.AACAQwQ` | Gestione Corsa, Profilazione |
| **AppOperatoreTecnico** | `zdBIhXmD.AACAQzx` | Amministrazione Flotta |
| **AppPA** | `euPIhXmD.AACAQ1G` | Statistiche e Restrizioni |
| **AppOperatoreSC** | `BSkUhXmD.AACAQ.A` | Moderazione Utente |

#### Layer Controller

**Componente Controller** (`3mq2v7mD.AACAQYr`) — realizza 5 interfacce:

| Interfaccia Realizzata | xmi:id | Consumata da |
|---|---|---|
| **Moderazione Utente** | `FSGPv7mD.AACARbj` | AppOperatoreSC |
| **Gestione Corsa** | `R.IxKnmD.AACAQ9w` | AppUtente |
| **Statistiche e Restrizioni** | `ie1hKnmD.AACAQ4x` | AppPA |
| **Profilazione** | `_nOfQXmD.AACAQpW` | AppUtente |
| **Amministrazione Flotta** | `BvUAwXmD.AACAQue` | AppOperatoreTecnico |

**Sub-controller interni:**

| Classe | xmi:id | Interfaccia Esterna Consumata |
|---|---|---|
| **GestioneCorsa** | `Al.5KnmD.AACARRw` | API Controllo (Mezzo:IoT) |
| **RicercaMezzi** | `eZ.5KnmD.AACARRn` | API Mappa (ServizioMappa) |
| **GestorePagamento** | `wHAFKnmD.AACARfr` | API Pagamento (Gateway) |

#### Layer Model

**Componente Model** (`YKY2v7mD.AACAQYD`) — realizza **Gestione Dati** (`.XEXv7mD.AACARYL`), consuma **Connessione Dati** dal DBMS.

#### Servizi Esterni

| Componente | xmi:id | Interfaccia Fornita |
|---|---|---|
| **ServizioMappa** | `ozG9v7mD.AACARFZ` | API Mappa (`xP59v7mD.AACARGb`) |
| **Gateway Pagamento** | `77O9v7mD.AACARFq` | API Pagamento (`Z.W3v7mD.AACARaG`) |
| **DBMS** | `mqGDv7mD.AACARJV` | Connessione Dati (`jvJnv7mD.AACARXl`) |
| **Mezzo : IoT** | `wksuKnmD.AACAQj5` | [INFERRED] API Controllo (`3P9eKnmD.AACAQlF`) |

---

## 3. Entità di Dominio (Class Diagram)

### 3.1 Mezzo

**xmi:id:** `bP7MYnmD.AACAQ2e` — Entità centrale del dominio.

| Attributo | Visibilità | Tipo | Descrizione |
|---|---|---|---|
| coordinateMezzo | public | float | [INFERRED] Posizione GPS corrente del veicolo. |
| idMezzo | public | Integer | Identificativo univoco del mezzo. |
| stato | public | Enum | [INFERRED] Stato operativo: disponibile, in uso, in manutenzione, bloccato. |
| autonomia | public | float | [INFERRED] Autonomia residua (km o % batteria). |
| costoOrario | public | float | [INFERRED] Tariffa oraria del mezzo. |
| velocitàMax | public | float | [INFERRED] Velocità massima del veicolo. |
| condizione | public | String | [INFERRED] Stato fisico del mezzo (buono, danneggiato, ecc.). |
| tipo | public | String | [INFERRED] Tipologia del veicolo (monopattino, bici, scooter, ecc.). |
| idFlotta | public | Integer | FK verso la flotta di appartenenza. |

**Operazioni chiave:** get/set per tutti gli attributi, `getMezzibyFlotta(idFlotta): Mezzo[]` (static).

---

### 3.2 Corsa

**xmi:id:** `TtB6YnmD.AACARJW`

| Attributo | Visibilità | Tipo |
|---|---|---|
| costo | private | float |
| orario inizio | private | time |
| orario fine | private | time |
| coordinate partenza | private | String |
| coordinate arrivo | private | String |
| idCorsa | private | Integer |

**Operazioni chiave:** creaCorsa(), ricercaCorsa(idCorsa), `getCorseByPeriodo(dataInizio, dataFine): Corsa[]` (static), aggiornaCosto(costo).

---

### 3.3 Prenotazione

**xmi:id:** `caKzwXmD.AACARED`

| Attributo | Visibilità | Tipo |
|---|---|---|
| idPrenotazione | private | Integer |
| stato | private | Enum |
| idUtente | private | Integer |
| idMezzo | private | Integer |
| orarioInizio | private | time |
| data | private | date |

**Operazioni chiave:** creaPrenotazione(), `getPrenotazionebyStato(stato): Prenotazione[]` (static).

---

### 3.4 MetodoPagamento

**xmi:id:** `NjhuYnmD.AACARRj`

| Attributo | Visibilità | Tipo |
|---|---|---|
| NumCarta | private | Integer |
| intestatarioCarta | private | String |

**Operazioni chiave:** creaMetodoPagamento(), `controllaMetodoEsistente(NumCarta): bool` (static).

---

### 3.5 Flotta

**xmi:id:** `pCJkKnmD.AACAQpm`

| Attributo | Visibilità | Tipo |
|---|---|---|
| idFlotta | public | Integer |

---

### 3.6 Segnalazione

**xmi:id:** `jrF2wXmD.AACAQoY`

| Attributo | Visibilità | Tipo |
|---|---|---|
| idSegnalazione | private | Integer |
| idMezzo | private | Integer |
| stato | private | Enum |
| ora | private | time |
| data | private | date |

**Operazioni chiave:** creaSegnalazione().

---

### 3.7 ZonaGeografica

**xmi:id:** `s0i1wXmD.AACAQ6x`

| Attributo | Visibilità | Tipo |
|---|---|---|
| idArea | private | Integer |
| tipoRestrizione | private | Enum |
| noteRestrizione | private | String |
| zona | private | LineString |

**Operazioni chiave:** creaZonaGeografica(), `getZone(): ZonaGeografica[]` (static), verificaSovrapposizioni(ZonaGeografica): bool, `checkArea(coordinateUtente): bool` (static).

---

## 4. Controller — Business Logic (Class Diagram)

### 4.1 GestioneCorsa

**xmi:id:** `SKstKnmD.AACAQpH`  
**Responsabilità:** [INFERRED] Orchestratore del ciclo di vita della corsa.  
**Cross-ref UC:** GestioneCorsa (`qmijknmD.AACARCz`), SospensioneCorsa (`aF4vqnmD.AACAQjg`)  
**Cross-ref Component:** Sub-controller nel Controller, consuma API Controllo (Mezzo:IoT)

| Operazione | Parametri | Ritorno |
|---|---|---|
| avviaCorsa() | — | void |
| terminaCorsa() | — | void |
| controllaDisponibilità() | — | void |
| aggiornaStima(idCorsa) | idCorsa: Integer | float |
| sospensioneCorsa() | — | bool |
| CalcoloPercorso() | — | — |
| controllaDisponibilita(string) | string | bool |
| richiediSblocco(QR_Code) | QR_Code: String | bool |
| fineCorsa(idCorsa) | idCorsa: Integer | bool |
| richiediCalcoloPercorso(coordUtente, destString) | float, float | percorso |

---

### 4.2 GestorePagamento

**xmi:id:** `aQstKnmD.AACAQo5`  
**Cross-ref UC:** MetodoPagamento (`d_KDknmD.AACARBB`)  
**Cross-ref Component:** Sub-controller nel Controller, consuma API Pagamento (Gateway)

| Operazione | Parametri | Ritorno |
|---|---|---|
| pagamentoCorsa(idUtente, idMetodoPagamento, costo) | Integer, Integer, float | bool |
| elaboraDatiCarta(idUtente, NumCarta, DsCarta, CVV, intestatarioCarta) | Integer, Integer, date, Integer, String | bool |

---

### 4.3 RicercaMezzi

**xmi:id:** `xostKnmD.AACAQpA`  
**Cross-ref UC:** RicercaMezzi (`FgmjknmD.AACARDg`)  
**Cross-ref Component:** Sub-controller nel Controller, consuma API Mappa (ServizioMappa)

| Operazione | Parametri | Ritorno |
|---|---|---|
| visualizzaMezziVicini(coordinateUtente, raggio) | String, float | Mezzo[] |
| visualizzaSpecifiche(idMezzo) | Integer | Mezzo |

---

### 4.4 GestioneUtenti

**xmi:id:** `bp_NKnmD.AACAQlK`  
**Cross-ref UC:** ModerazioneUtente (`JWWTknmD.AACARH_`)  
**Cross-ref Component:** Controller realizza interfaccia "Moderazione Utente"

| Operazione | Parametri | Ritorno |
|---|---|---|
| gestioneUtente(idUtente) | Integer | bool |
| cercaReport(idUtente) | Integer | String |

---

### 4.5 GestioneFlotta

**xmi:id:** `m.1tKnmD.AACAQqZ`  
**Cross-ref UC:** GestioneFlotta (`PDqTknmD.AACARHp`)  
**Cross-ref Component:** Controller realizza interfaccia "Amministrazione Flotta"

| Operazione | Parametri | Ritorno |
|---|---|---|
| analisiStatoFlotta(idFlotta) | Integer | bool |
| bloccaMezzo(idMezzo) | Integer | bool |
| avviaManutenzione(idFlotta) | Integer | bool |
| getCondizioniMezzi(idFlotta) | Integer | Mezzo[] |

---

### 4.6 GestioneStatistiche

**xmi:id:** `PO_qnnmD.AACAQnq`  
**Cross-ref UC:** MonitoraggioStatisticheAnalisi (`TsrjknmD.AACARFl`), AnalisiStatoFlotta (`nI3jknmD.AACARF3`)

| Operazione | Parametri | Ritorno |
|---|---|---|
| analisiTratte(dataInizio, dataFine) | date, date | statistiche |
| generaFileStatistiche(Corsa) | Corsa | void |

---

### 4.7 GestionePrenotazione

**xmi:id:** `LJIOwXmD.AACAQpM`  
**Cross-ref UC:** PrenotazioneMezzo (`MSajknmD.AACARDF`), AmministrazionePrenotazioni (`mCxTknmD.AACARIR`)

| Operazione | Parametri | Ritorno |
|---|---|---|
| inviaRichiestaPrenotazione() | — | bool |
| richiediLista() | — | Prenotazione[] |
| annullaPrenotazione() | — | bool |
| gestisciTimeout() | — | void |
| notificaScadenzaTempo(idPrenotazione) | Integer | void |

---

### 4.8 GestioneAree

**xmi:id:** `RLyOwXmD.AACAQqB`  
**Cross-ref UC:** RestrizioniGeografiche (`DUATknmD.AACARGJ`)

| Operazione | Parametri | Ritorno |
|---|---|---|
| aggiornaRestrizioni(idArea, tipoRestrizione, noteRestrizione, zona) | Integer, Enum, String, LineString | — |
| analisiConflitti(ZonaGeografica) | ZonaGeografica | bool |
| getZoneGeografiche() | — | ZonaGeografica[] |

---

## 5. Classi View — Presentation Layer (Class Diagram)

| Classe | xmi:id | Ops | Responsabilità |
|---|---|---|---|
| **AppUtente** | `fdM2RXmD.AACAQpv` | 23 | [INFERRED] UI completa: scansionaQRCode, inserisciDatiCarta, apriAvvioCorsa, mostraErrore, mostraStima, terminazioneCorsa, sospendiCorsa, mostraQRCode, mostraRipresaCorsa, apriSezioneProfilo, selezionaMezzo, inserisciDestinazione, avviaRicercaMezzi, confermaEspansione, mostraMezzi, renderizzaDettagliVeicolo, notificaAzione, ecc. |
| **AppOperatoreSC** | `psSuRXmD.AACAQvL` | 7 | [INFERRED] richiediListaPrenotazioni, selezionaPrenotazione, mostraPrenotazioni, mostraSuccesso/Errore, mostraReport, aggiornaReport. |
| **AppPA** | `LPieRXmD.AACAQwu` | 11 | [INFERRED] selezionaIntervallo, mostraStatistiche, richiedeStatoFlotta, avviaIntervento, visualizzaMezzi, selezionaMappa, reindirizzaMappa, modificaRestrizioni, confermaSovrascrittura, rifiutaSovrascrittura. |
| **AppOperatoreTecnico** | `iEOHRXmD.AACARKJ` | 5 | [INFERRED] mostraSuccesso/Errore, richiedeStatoFlotta, selezionaVeicolo, visualizzaMezzi. |

---

## 6. Servizi Esterni (Class Diagram + Component Diagram)

| Servizio | Class xmi:id | Component xmi:id | Operazioni | Interfaccia |
|---|---|---|---|---|
| **Mezzo : IoT** | `0RmQxXmD.AACARc5` | `wksuKnmD.AACAQj5` | `bloccoMezzoFisico(idMezzo): bool`, `sbloccoMezzoFisico(idMezzo): bool` | API Controllo |
| **Gateway Pagamento** | `kPeQxXmD.AACARdI` | `77O9v7mD.AACARFq` | `EffettuaPagamento(idMetodoPagamento, idCorsa): bool`, `convalidaCarta(NumCarta, DsCarta, CVV, intestatarioCarta): bool` | API Pagamento |
| **Servizio Mappa** | `ad.QxXmD.AACARdP` | `ozG9v7mD.AACARFZ` | `getPercorso(coordinateIniziali, coordinateFinali, restrizioni): datiPercorso` | API Mappa |
| **DBMS** | — | `mqGDv7mD.AACARJV` | — | Connessione Dati |

---

## 7. Use Case — Matrice Attore × Caso d'Uso

### 7.1 Utente (6 Use Case)

| Use Case | xmi:id | Controller Class | Business Logic |
|---|---|---|---|
| **RicercaMezzi** | `FgmjknmD.AACARDg` | RicercaMezzi | [INFERRED] L'utente cerca mezzi disponibili per posizione GPS e raggio. Il controller interroga la classe Mezzo e usa ServizioMappa per la geolocalizzazione. |
| **OttimizzazionePercorso** | `sGhjknmD.AACARD2` | GestioneCorsa | [INFERRED] Calcolo percorso ottimale via `richiediCalcoloPercorso()` → `ServizioMappa.getPercorso()` considerando restrizioni da ZonaGeografica. |
| **PrenotazioneMezzo** | `MSajknmD.AACARDF` | GestionePrenotazione | [INFERRED] Prenotazione anticipata. Crea entità Prenotazione con stato, orario, idMezzo. Timeout automatico via `gestisciTimeout()`. |
| **GestioneCorsa** | `qmijknmD.AACARCz` | GestioneCorsa | [INFERRED] Ciclo di vita: avviaCorsa → richiediSblocco(QR) → Mezzo:IoT.sbloccoMezzoFisico → monitoraggio → fineCorsa → Mezzo:IoT.bloccoMezzoFisico → GestorePagamento.pagamentoCorsa. |
| **MetodoPagamento** | `d_KDknmD.AACARBB` | GestorePagamento | [INFERRED] Registrazione carta via `elaboraDatiCarta()` → `GatewayPagamento.convalidaCarta()`. Verifica duplicati con `MetodoPagamento.controllaMetodoEsistente()`. |
| **SospensioneCorsa** | `aF4vqnmD.AACAQjg` | GestioneCorsa | [INFERRED] Sospensione temporanea via `sospensioneCorsa()`. Tariffa ridotta, timeout massimo. |

### 7.2 PA (3 Use Case)

| Use Case | xmi:id | Controller Class | Business Logic |
|---|---|---|---|
| **MonitoraggioStatisticheAnalisi** | `TsrjknmD.AACARFl` | GestioneStatistiche | [INFERRED] Dashboard: `analisiTratte(dataInizio, dataFine)` analizza corse per periodo. `generaFileStatistiche()` produce report. |
| **AnalisiStatoFlotta** | `nI3jknmD.AACARF3` | GestioneFlotta + GestioneStatistiche | [INFERRED] Report aggregato: mezzi attivi/inattivi/in manutenzione, distribuzione, autonomia media. `analisiStatoFlotta(idFlotta)` + `getCondizioniMezzi(idFlotta)`. |
| **RestrizioniGeografiche** | `DUATknmD.AACARGJ` | GestioneAree | [INFERRED] Configurazione geo-fence: `aggiornaRestrizioni()` su ZonaGeografica. `analisiConflitti()` verifica sovrapposizioni. Applicate in runtime da `ZonaGeografica.checkArea()`. |

### 7.3 Operatore Tecnico (1 Use Case)

| Use Case | xmi:id | Controller Class | Business Logic |
|---|---|---|---|
| **GestioneFlotta** | `PDqTknmD.AACARHp` | GestioneFlotta | [INFERRED] Gestione operativa: `bloccaMezzo()`, `avviaManutenzione()`, crea Segnalazione. Interfaccia "Amministrazione Flotta" nel Controller. |

### 7.4 Operatore Servizio Clienti (2 Use Case)

| Use Case | xmi:id | Controller Class | Business Logic |
|---|---|---|---|
| **ModerazioneUtente** | `JWWTknmD.AACARH_` | GestioneUtenti | [INFERRED] `gestioneUtente(idUtente)` + `cercaReport()`. Azione disciplinare via `Utente.azioneCorrettiva(azione: Enum)`. |
| **AmministrazionePrenotazioni** | `mCxTknmD.AACARIR` | GestionePrenotazione | [INFERRED] Back-office: `richiediLista()`, `annullaPrenotazione()`, visualizzazione e modifica per conto utente. |

---

## 8. Architectural Relationships — Matrice Unificata

### 8.1 Associazioni tra Classi (Class Diagram)

| # | Nome | Entità A | Entità B | Molt. | Spiegazione |
|---|---|---|---|---|---|
| 1 | **appartiene** | Flotta | Mezzo | 1..1 ↔ 1..* | [INFERRED] Ogni mezzo appartiene a esattamente 1 flotta. |
| 2 | **utilizza** | Mezzo | Corsa | 1..1 ↔ 0..* | [INFERRED] Ogni corsa usa 1 mezzo; un mezzo può avere N corse storiche. |
| 3 | **modera** | GestioneUtenti | Utente | 0..* ↔ 0..* | [INFERRED] Moderazione M:N. |
| 4 | **verifica** | GestorePagamento | MetodoPagamento | 0..* ↔ 0..* | [INFERRED] Verifica M:N dei metodi. |
| 5 | **interroga** | RicercaMezzi | Mezzo | 0..* ↔ 0..* | [INFERRED] Query di ricerca M:N. |
| 6 | **amministra** | GestioneCorsa | Mezzo | 1..* ↔ 0..* | [INFERRED] Il controller di corsa gestisce i mezzi. |
| 7 | **effettua** | GestioneCorsa | Corsa | 0..* ↔ 1..1 | [INFERRED] Ogni istanza del controller effettua 1 corsa. |
| 8 | **gestisce** | GestioneFlotta | Mezzo | 0..* ↔ 0..* | [INFERRED] Gestione manutenzione/rilocazione. |
| 9 | **crea** | GestioneFlotta | Segnalazione | 0..* ↔ 1..1 | [INFERRED] Crea segnalazioni di guasto. |
| 10 | **analizza** | GestioneStatistiche | Corsa | 0..* ↔ 0..* | [INFERRED] Analisi statistiche sulle corse. |
| 11 | **prenota** | GestionePrenotazione | Mezzo | 1..* ↔ 0..* | [INFERRED] Prenotazione di mezzi. |
| 12 | **ha** | GestionePrenotazione | Prenotazione | 0..* ↔ 1..1 | [INFERRED] Controller ha 1 prenotazione per richiesta. |
| 13 | **genera** | GestionePrenotazione | Segnalazione | 0..* ↔ 1..1 | [INFERRED] Genera segnalazione (timeout, mancato ritiro). |
| 14 | **aggiunge** | GestioneAree | ZonaGeografica | 0..* ↔ 0..* | [INFERRED] Gestione zone operative. |
| 15 | **esegue check** | ZonaGeografica | GestioneCorsa | 0..* ↔ 0..* | [INFERRED] Verifica geo-fence in runtime. |

### 8.2 Dipendenze Architetturali (Component Diagram)

| # | Client | Supplier | Interfaccia | Tipo |
|---|---|---|---|---|
| 1 | AppUtente → | Controller | Gestione Corsa | <<use>> |
| 2 | AppUtente → | Controller | Profilazione | <<use>> |
| 3 | AppOperatoreSC → | Controller | Moderazione Utente | <<use>> |
| 4 | AppOperatoreTecnico → | Controller | Amministrazione Flotta | <<use>> |
| 5 | AppPA → | Controller | Statistiche e Restrizioni | <<use>> |
| 6 | Controller → | Model | Gestione Dati | <<use>> |
| 7 | Model → | DBMS | Connessione Dati | <<use>> |
| 8 | RicercaMezzi → | ServizioMappa | API Mappa | <<use>> |
| 9 | GestorePagamento → | Gateway Pagamento | API Pagamento | <<use>> |
| 10 | GestioneCorsa → | Mezzo:IoT | API Controllo | <<use>> |

---

## 9. Architectural Constraints & Invariants

> Nessun vincolo esplicito (`ownedRule`, `ownedConstraint`) è presente in nessuno dei tre file XMI. I seguenti vincoli sono dedotti dal cross-referencing tra i tre diagrammi.

### Vincoli Strutturali

1. **[INFERRED] MVC Rigoroso:** Flusso dipendenze strettamente unidirezionale: View → Controller → Model → DBMS. Nessuna dipendenza inversa.

2. **[INFERRED] Separazione ruoli per View:** Ogni attore ha un'app View dedicata. Ogni View consuma solo le interfacce pertinenti al proprio ruolo (principio di minimo privilegio). Confermato da UC, Component e Class Diagram.

3. **[INFERRED] Accoppiamento tramite interfacce:** I componenti comunicano esclusivamente via `uml:Interface`. Garantisce basso accoppiamento e alta sostituibilità dei servizi esterni.

### Vincoli di Dominio

4. **[INFERRED] Appartenenza univoca Mezzo→Flotta:** Molteplicità 1..1 nel Class Diagram. Un mezzo non può essere orfano.

5. **[INFERRED] Unicità corsa attiva:** Un utente non può avere più di una corsa attiva. GestioneCorsa effettua una Corsa 1..1. Confermato dal UC SospensioneCorsa (opera sulla stessa istanza).

6. **[INFERRED] Disponibilità esclusiva del Mezzo:** Un mezzo prenotato (Prenotazione) o in corsa (Corsa) non è disponibile per RicercaMezzi. Confermato dalla molteplicità `utilizza` (1..1) e dalla presenza di `controllaDisponibilita()`.

7. **[INFERRED] Geo-fence enforcement:** L'associazione `esegue check` (ZonaGeografica ↔ GestioneCorsa) + il metodo `ZonaGeografica.checkArea()` + il parametro `restrizioni` in `ServizioMappa.getPercorso()` confermano l'applicazione in tempo reale delle restrizioni geografiche sia nel calcolo percorso che durante la corsa.

8. **[INFERRED] Pagamento valido obbligatorio:** `GestorePagamento.pagamentoCorsa()` richiede `idMetodoPagamento`, `GatewayPagamento.convalidaCarta()` è prerequisito. Confermato dal UC MetodoPagamento associato all'Utente.

9. **[INFERRED] Timeout prenotazione:** `GestionePrenotazione.gestisciTimeout()` + `notificaScadenzaTempo()` + associazione `genera → Segnalazione` confermano un vincolo temporale automatico.

10. **[INFERRED] Auditabilità operazioni back-office:** Le operazioni di ModerazioneUtente e AmministrazionePrenotazioni sono tracciabili (cercaReport, aggiornaReport in AppOperatoreSC).

11. **[INFERRED] Separazione Operatore Tecnico / SC:** Il UC Diagram li separa come attori distinti, il Component Diagram conferma con View e interfacce separate, anche se il Class Diagram usa un'unica classe `Operatore` marker.

---

## 10. Cross-Reference: UC → Controller → Entità → Componente

| Use Case | Controller | Entità Coinvolte | Componente/Interfaccia | Servizio Esterno |
|---|---|---|---|---|
| RicercaMezzi | RicercaMezzi | Mezzo | Controller / API Mappa | ServizioMappa |
| OttimizzazionePercorso | GestioneCorsa | Mezzo, ZonaGeografica | Controller / API Mappa | ServizioMappa |
| PrenotazioneMezzo | GestionePrenotazione | Prenotazione, Mezzo | Controller / Gestione Corsa | — |
| GestioneCorsa | GestioneCorsa | Corsa, Mezzo | Controller / API Controllo | Mezzo:IoT |
| SospensioneCorsa | GestioneCorsa | Corsa | Controller / Gestione Corsa | — |
| MetodoPagamento | GestorePagamento | MetodoPagamento | Controller / API Pagamento | Gateway Pagamento |
| MonitoraggioStatistiche | GestioneStatistiche | Corsa | Controller / Stat.&Restr. | — |
| AnalisiStatoFlotta | GestioneFlotta + GestioneStatistiche | Mezzo, Flotta | Controller / Stat.&Restr. | — |
| RestrizioniGeografiche | GestioneAree | ZonaGeografica | Controller / Stat.&Restr. | — |
| GestioneFlotta | GestioneFlotta | Mezzo, Flotta, Segnalazione | Controller / Amm. Flotta | — |
| ModerazioneUtente | GestioneUtenti | Utente | Controller / Moderaz. Ut. | — |
| AmministrazionePrenotazioni | GestionePrenotazione | Prenotazione | Controller / Moderaz. Ut. | — |

---

## Appendice A: Tipi Custom

| Tipo ID | Tipo Inferito |
|---|---|
| `float_id` | [INFERRED] Floating-point (double/float). |
| `void_id` | [INFERRED] Void. |
| `time_id` | [INFERRED] Temporale (LocalTime/Timestamp). |
| `date_id` | [INFERRED] Data (LocalDate). |
| `enum_id` | [INFERRED] Enumerativo generico. |
| `bool_id` | [INFERRED] Booleano. |
| `percorso_id` | [INFERRED] Dati percorso calcolato. |
| `datiPercorso_id` | [INFERRED] Output ServizioMappa. |
| `statistiche_id` | [INFERRED] Dati statistici aggregati. |
| `LineString_id` | [INFERRED] Geometria GeoJSON/WKT per geo-fence. |

## Appendice B: Artefatti di Modellazione Non Significativi

Le seguenti interfacce dal Component Diagram sono artefatti incompleti e **non devono essere considerate**:

| Nome | xmi:id | Motivo |
|---|---|---|
| Class6 | `kf3Vv7mD.AACAQul` | Placeholder, nessun collegamento. |
| ù | `DPxdv7mD.AACARBh` | Typo, nessun collegamento. |
| _(vuoto)_ | `Dlp9v7mD.AACARGL` | Nome assente, orfano. |
| Class14 | `58N9v7mD.AACARG5` | Placeholder, nessun collegamento. |
| Class12 | `n.hDv7mD.AACARJ.` | Placeholder, nessun collegamento. |

## Appendice C: Profili UML

| Profilo | Stereotipi |
|---|---|
| **Diagramma_Casi_d_uso_profile** | «UseCase» (Level, Complexity, UseCaseStatus, ImplementationStatus, Preconditions, Post-conditions, Author, Assumptions — tutti vuoti), «CaseStory», «Context» |
| **cofeecoders_profile** | «use», «Agent» (extends Participant), «Participant», «boundary», «entity», «EntityBean», «primitive», «Struct», «type» |

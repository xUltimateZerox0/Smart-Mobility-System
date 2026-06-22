```yaml
status: SUCCESS_WITH_INFERENCE
extractor_version: "2.0-unsafe"
parsed_entities: 41
```

# UML-XMI Semantic Extraction Report (Unsafe Mode) — Class Diagram

**Source Model:** Classi Diagram  
**Package:** Class Diagram - Smart Mobility System  
**Exporter:** Visual Paradigm 7.0.2  
**XMI Version:** 2.1  
**Profiles:** Classi_Diagram_profile, cofeecoders_profile

---

## 1. System Boundaries & Actors

Il diagramma delle classi non definisce `uml:Actor` espliciti. La gerarchia degli attori è modellata come classi UML con ereditarietà.

### Gerarchia Attori (Classi)

```
Attore (superclasse)
├── Utente
├── Operatore
└── PA
```

| Classe Attore | xmi:id | Descrizione |
|---|---|---|
| **Attore** | `hP7MYnmD.AACAQ2H` |   Superclasse astratta per tutti gli attori del sistema. Definisce le proprietà comuni: `email` (String), `id` (Integer). Fornisce metodi getter/setter. |
| **Utente** | `TP7MYnmD.AACAQ2W` |   Utente finale della piattaforma di mobilità condivisa. Estende Attore con dati di profilazione, geolocalizzazione e stato delle prenotazioni. |
| **Operatore** | `DP7MYnmD.AACAQ2U` |   Classe base per operatori del sistema. Estende Attore. Classe vuota (marker) che potrebbe differenziare i permessi rispetto all'utente finale. |
| **PA** | `KdrcYnmD.AACAQ9j` |   Pubblica Amministrazione. Estende Attore. Classe vuota (marker) per il ruolo istituzionale di monitoraggio e governance. |

---

## 2. Core Entities & Components

### 2.1 Attore (Superclasse)

**Tipo:** Classe | **xmi:id:** `hP7MYnmD.AACAQ2H`  
**Responsabilità:**   Classe base per tutti gli attori del sistema. Centralizza le credenziali comuni.

| Attributi | Visibilità | Tipo |
|---|---|---|
| email | public | String |
| id | public | Integer |

| Operazioni | Parametri | Ritorno |
|---|---|---|
| getEmail() | — | String |
| setEmail(email: String) | email | void |
| getId() | — | Integer |
| setId(id: Integer) | id | void |

---

### 2.2 Utente

**Tipo:** Classe | **xmi:id:** `TP7MYnmD.AACAQ2W` | **Estende:** Attore  
**Responsabilità:**   Entità utente finale con profilazione completa, tracciamento posizione e sistema di report/segnalazioni. Include metodi di ricerca statica e azione correttiva (moderazione).

| Attributi | Visibilità | Tipo |
|---|---|---|
| coordinateUtente | public | String |
| numMezziPrenotati | public | Integer |
| nomeUtente | public | String |
| cognomeUtente | public | String |
| telefono | public | String |
| reportUtente | private | String |
| idUtente | private | Integer |

| Operazioni Chiave | Parametri | Ritorno | Note |
|---|---|---|---|
| getCoordinateUtente() | — | float | |
| setCoordinateUtente(coordinateUtente) | coordinateUtente: float | void | |
| get/setNomeUtente, get/setCognomeUtente, get/setTelefono | ... | String/void | Getter/Setter standard |
| getReportUtente() | — | String | |
| setReportUtente(reportUtente) | reportUtente: String | void | |
| getIdUtente() / setIdUtente(idUtente) | idUtente: Integer | Integer/void | |
| **ricercaUtente**(idUtente) | idUtente: Integer | Utente | **Static** |
| **azioneCorrettiva**(azione) | azione: Enum | void |   Applica un'azione disciplinare all'utente (avvertimento, sospensione, ban). |

---

### 2.3 Operatore

**Tipo:** Classe | **xmi:id:** `DP7MYnmD.AACAQ2U` | **Estende:** Attore  
**Responsabilità:**   Classe marker per gli operatori. Non aggiunge attributi o operazioni proprie; si distingue dall'utente per i permessi di accesso. Potrebbe essere specializzata in sottoclassi (Tecnico, Servizio Clienti).

---

### 2.4 PA

**Tipo:** Classe | **xmi:id:** `KdrcYnmD.AACAQ9j` | **Estende:** Attore  
**Responsabilità:**   Classe marker per la Pubblica Amministrazione. Accede a funzionalità di monitoraggio, statistiche e governance territoriale.

---

### 2.5 Corsa

**Tipo:** Classe | **xmi:id:** `TtB6YnmD.AACARJW`  
**Responsabilità:**   Entità che modella una corsa effettuata dall'utente con un mezzo condiviso. Traccia coordinate, orari e costo.

| Attributi | Visibilità | Tipo |
|---|---|---|
| costo | private | float |
| orario inizio | private | time |
| orario fine | private | time |
| coordinate partenza | private | String |
| coordinate arrivo | private | String |
| idCorsa | private | Integer |

| Operazioni Chiave | Parametri | Ritorno | Note |
|---|---|---|---|
| getCosto() / setCosto(costo) | costo: float | float/void | |
| getOrario inizio/fine, setOrario inizio/fine | time | time/void | |
| getCoordinate partenza/arrivo, set... | float | float/void | |
| creaCorsa() | — | — |   Factory method per istanziare una nuova corsa. |
| getIdCorsa() / setIdCorsa(idCorsa) | idCorsa: Integer | Integer/void | |
| ricercaCorsa(idCorsa) | idCorsa: Integer | Corsa |   Cerca una corsa per ID. |
| **getCorseByPeriodo**(dataInizio, dataFine) | dataInizio: date, dataFine: date | Corsa | **Static** —   Query di corse per intervallo temporale (reporting statistico). |
| aggiornaCosto(costo) | costo: float | void |   Aggiorna il costo a fine corsa. |

---

### 2.6 MetodoPagamento

**Tipo:** Classe | **xmi:id:** `NjhuYnmD.AACARRj`  
**Responsabilità:**   Entità che modella un metodo di pagamento associato ad un utente. Memorizza i dati della carta.

| Attributi | Visibilità | Tipo |
|---|---|---|
| NumCarta | private | Integer |
| intestatarioCarta | private | String |

| Operazioni Chiave | Parametri | Ritorno | Note |
|---|---|---|---|
| getNumCarta() / setNumCarta | NumCarta: Integer | Integer/void | |
| getIntestatarioCarta() / setIntestatarioCarta | intestatarioCarta: String | String/void | |
| creaMetodoPagamento() | — | — |   Factory method. |
| **controllaMetodoEsistente**(NumCarta) | NumCarta: Integer | bool | **Static** —   Verifica se una carta è già registrata nel sistema. |

---

### 2.7 Flotta

**Tipo:** Classe | **xmi:id:** `pCJkKnmD.AACAQpm`  
**Responsabilità:**   Contenitore logico che raggruppa un insieme di mezzi. Ogni mezzo appartiene a esattamente una flotta.

| Attributi | Visibilità | Tipo |
|---|---|---|
| idFlotta | public | Integer |

| Operazioni | Parametri | Ritorno |
|---|---|---|
| getIdFlotta() / setIdFlotta(idFlotta) | idFlotta: Integer | Integer/void |

---

### 2.8 Mezzo

**Tipo:** Classe | **xmi:id:** `bP7MYnmD.AACAQ2e`  
**Responsabilità:**   Entità centrale del dominio. Modella un veicolo condiviso con posizione, stato operativo, autonomia e caratteristiche fisiche.

| Attributi | Visibilità | Tipo |
|---|---|---|
| coordinateMezzo | public | float |
| idMezzo | public | Integer |
| stato | public | Enum |
| autonomia | public | float |
| costoOrario | public | float |
| velocitàMax | public | float |
| condizione | public | String |
| tipo | public | String |
| idFlotta | public | Integer |

| Operazioni Chiave | Parametri | Ritorno | Note |
|---|---|---|---|
| get/set per tutti gli attributi | — | — | Getter/Setter standard |
| **getMezzibyFlotta**(idFlotta) | idFlotta | Mezzo | **Static** —   Restituisce tutti i mezzi di una flotta. |

---

### 2.9 Segnalazione

**Tipo:** Classe | **xmi:id:** `jrF2wXmD.AACAQoY`  
**Responsabilità:**   Entità che modella una segnalazione di guasto o problema su un mezzo. Creata durante la gestione della flotta.

| Attributi | Visibilità | Tipo |
|---|---|---|
| idSegnalazione | private | Integer |
| idMezzo | private | Integer |
| stato | private | Enum |
| ora | private | time |
| data | private | date |

| Operazioni Chiave | Parametri | Ritorno |
|---|---|---|
| get/set per tutti gli attributi | — | — |
| creaSegnalazione() | — | — |

---

### 2.10 Prenotazione

**Tipo:** Classe | **xmi:id:** `caKzwXmD.AACARED`  
**Responsabilità:**   Entità che modella una prenotazione di un mezzo da parte di un utente per un determinato orario.

| Attributi | Visibilità | Tipo |
|---|---|---|
| idPrenotazione | private | Integer |
| stato | private | Enum |
| idUtente | private | Integer |
| idMezzo | private | Integer |
| orarioInizio | private | time |
| data | private | date |

| Operazioni Chiave | Parametri | Ritorno | Note |
|---|---|---|---|
| get/set per tutti gli attributi | — | — | |
| **getPrenotazionebyStato**(enum) | stato: Enum | Prenotazione | **Static** —   Filtra prenotazioni per stato. |
| creaPrenotazione() | — | — |   Factory method. |

---

### 2.11 ZonaGeografica

**Tipo:** Classe | **xmi:id:** `s0i1wXmD.AACAQ6x`  
**Responsabilità:**   Entità che modella un'area geografica con restrizioni operative (geo-fence). Usa geometrie LineString per definire i confini.

| Attributi | Visibilità | Tipo |
|---|---|---|
| idArea | private | Integer |
| tipoRestrizione | private | Enum |
| noteRestrizione | private | String |
| zona | private | LineString |

| Operazioni Chiave | Parametri | Ritorno | Note |
|---|---|---|---|
| get/set per tutti gli attributi | — | — | |
| **getZone()** | — | ZonaGeografica | **Static** —   Restituisce tutte le zone. |
| verificaSovrapposizioni(ZonaGeografica) | ZonaGeografica | bool |   Verifica conflitti spaziali. |
| **checkArea**(coordinateUtente) | coordinateUtente | bool | **Static** —   Verifica se coordinate sono in zona valida. |
| creaZonaGeografica() | — | — |   Factory method. |

---

### 2.12 Classi Controller (Business Logic)

| Classe | xmi:id | Responsabilità | Operazioni |
|---|---|---|---|
| **GestioneUtenti** | `bp_NKnmD.AACAQlK` |   Controller per la moderazione e gestione degli utenti. | `gestioneUtente(idUtente): bool`, `cercaReport(idUtente): String` |
| **GestorePagamento** | `aQstKnmD.AACAQo5` |   Controller per l'elaborazione dei pagamenti. Interfaccia verso il Gateway. | `pagamentoCorsa(idUtente, idMetodoPagamento, costo): bool`, `elaboraDatiCarta(idUtente, NumCarta, DsCarta, CVV, intestatarioCarta): bool` |
| **RicercaMezzi** | `xostKnmD.AACAQpA` |   Controller per la ricerca geolocalizzata dei mezzi. | `visualizzaMezziVicini(coordinateUtente, raggio): Mezzo`, `visualizzaSpecifiche(idMezzo): Mezzo` |
| **GestioneCorsa** | `SKstKnmD.AACAQpH` |   Controller per il ciclo di vita della corsa. Orchestratore centrale. | `avviaCorsa()`, `terminaCorsa()`, `controllaDisponibilità()`, `aggiornaStima(idCorsa): float`, `sospensioneCorsa(): bool`, `CalcoloPercorso()`, `controllaDisponibilita(string): bool`, `richiediSblocco(QR_Code): bool`, `fineCorsa(idCorsa): bool`, `richiediCalcoloPercorso(coordUtente, destString): percorso` |
| **GestioneFlotta** | `m.1tKnmD.AACAQqZ` |   Controller per la gestione operativa della flotta. | `analisiStatoFlotta(idFlotta): bool`, `bloccaMezzo(idMezzo): bool`, `avviaManutenzione(idFlotta): bool`, `getCondizioniMezzi(idFlotta): Mezzo` |
| **GestioneStatistiche** | `PO_qnnmD.AACAQnq` |   Controller per l'analisi statistica delle corse. | `analisiTratte(dataInizio, dataFine): statistiche`, `generaFileStatistiche(Corsa): void` |
| **GestionePrenotazione** | `LJIOwXmD.AACAQpM` |   Controller per il ciclo di vita delle prenotazioni. | `inviaRichiestaPrenotazione(): bool`, `richiediLista(): Prenotazione`, `annullaPrenotazione(): bool`, `gestisciTimeout(): void`, `notificaScadenzaTempo(idPrenotazione): void` |
| **GestioneAree** | `RLyOwXmD.AACAQqB` |   Controller per la gestione delle zone geografiche e restrizioni. | `aggiornaRestrizioni(idArea, tipoRestrizione, noteRestrizione, zona)`, `analisiConflitti(ZonaGeografica): bool`, `getZoneGeografiche(): ZonaGeografica` |

---

### 2.13 Classi View (Presentation Layer)

| Classe | xmi:id | Operazioni Chiave | Responsabilità |
|---|---|---|---|
| **AppUtente** | `fdM2RXmD.AACAQpv` | scansionaQRCode, inserisciDatiCarta, apriAvvioCorsa, mostraErrore, mostraInserimentoMetodoPagamento, mostraStima, terminazioneCorsa, mostraFineCorsa, sospendiCorsa, mostraQRCode, mostraRipresaCorsa, apriSezioneProfilo, mostraSezioneProfilo, apriInserimentoMetodoPagamento, mostraMetodoConvalidato, selezionaMezzo, mostraSuccesso, inserisciDestinazione, avviaRicercaMezzi, confermaEspansione, mostraMezzi, renderizzaDettagliVeicolo, notificaAzione (23 ops) |   Interfaccia utente completa per tutte le funzionalità end-user: corsa, prenotazione, pagamento, ricerca, profilo. |
| **AppOperatoreSC** | `psSuRXmD.AACAQvL` | richiediListaPrenotazioni, selezionaPrenotazione, mostraPrenotazioni, mostraSuccesso, mostraErrore, mostraReport, aggiornaReport (7 ops) |   Interfaccia per l'operatore servizio clienti: gestione prenotazioni e report utenti. |
| **AppPA** | `LPieRXmD.AACAQwu` | selezionaIntervallo, mostraErrore, mostraStatistiche, richiedeStatoFlotta, avviaIntervento, visualizzaMezzi, selezionaMappa, reindirizzaMappa, modificaRestrizioni, confermaSovrascrittura, rifiutaSovrascrittura (11 ops) |   Interfaccia PA: statistiche, stato flotta, gestione restrizioni geografiche. |
| **AppOperatoreTecnico** | `iEOHRXmD.AACARKJ` | mostraSuccesso, mostraErrore, richiedeStatoFlotta, selezionaVeicolo, visualizzaMezzi (5 ops) |   Interfaccia per l'operatore tecnico: visualizzazione stato flotta e selezione veicoli. |

---

### 2.14 Classi Servizi Esterni

| Classe | xmi:id | Operazioni | Responsabilità |
|---|---|---|---|
| **Mezzo : IoT** | `0RmQxXmD.AACARc5` | `bloccoMezzoFisico(idMezzo): bool`, `sbloccoMezzoFisico(idMezzo): bool` |   Interfaccia software del dispositivo IoT a bordo veicolo. Gestisce lo sblocco/blocco fisico del mezzo. |
| **Gateway Pagamento** | `kPeQxXmD.AACARdI` | `EffettuaPagamento(idMetodoPagamento, idCorsa): bool`, `convalidaCarta(NumCarta, DsCarta, CVV, intestatarioCarta): bool` |   Gateway di pagamento esterno. Valida carte ed elabora transazioni finanziarie. |
| **Servizio Mappa** | `ad.QxXmD.AACARdP` | `getPercorso(coordinateIniziali, coordinateFinali, restrizioni): datiPercorso` |   Servizio cartografico esterno. Calcola percorsi considerando restrizioni geografiche. |

---

## 3. Use Case Logic & Flows

> **Nota:** Il diagramma delle classi non contiene Use Case. Questa sezione descrive i **flussi operativi** derivati dalla topologia delle associazioni e dalle firme dei metodi.

### Flusso: Gestione Corsa (Ciclo completo)
- **Business Logic:**   L'utente avvia una corsa tramite AppUtente → GestioneCorsa. Il controller verifica la disponibilità del mezzo (`controllaDisponibilita`), richiede lo sblocco fisico tramite `Mezzo:IoT.sbloccoMezzoFisico()`, crea la corsa (`Corsa.creaCorsa`) e traccia il percorso tramite `ServizioMappa.getPercorso()`. A fine corsa, `GestioneCorsa.fineCorsa()` blocca il mezzo e `GestorePagamento.pagamentoCorsa()` elabora il pagamento via `GatewayPagamento.EffettuaPagamento()`.
- **Includes:** Nessuno (relazioni implicite)
- **Extends:** Sospensione corsa (`sospensioneCorsa()`) come flusso alternativo.

### Flusso: Prenotazione Mezzo
- **Business Logic:**   L'utente prenota un mezzo tramite `GestionePrenotazione.inviaRichiestaPrenotazione()`. Il sistema crea una `Prenotazione` con stato, orario e ID mezzo. `GestionePrenotazione.gestisciTimeout()` gestisce la scadenza automatica. La prenotazione genera una `Segnalazione` di riserva (`genera: Prenotazione → Segnalazione`).
- **Includes:** Nessuno
- **Extends:** Nessuno

### Flusso: Gestione Pagamento
- **Business Logic:**   L'utente registra un metodo di pagamento tramite `AppUtente.inserisciDatiCarta()`. `GestorePagamento.elaboraDatiCarta()` invoca `GatewayPagamento.convalidaCarta()` per la validazione esterna. `MetodoPagamento.controllaMetodoEsistente()` verifica eventuali duplicati.
- **Includes:** Nessuno
- **Extends:** Nessuno

### Flusso: Monitoraggio Statistiche (PA)
- **Business Logic:**   La PA seleziona un intervallo temporale tramite `AppPA.selezionaIntervallo()`. `GestioneStatistiche.analisiTratte()` analizza le corse nel periodo e restituisce le statistiche. `GestioneStatistiche.generaFileStatistiche()` produce un report esportabile.
- **Includes:** Nessuno
- **Extends:** Nessuno

### Flusso: Gestione Restrizioni Geografiche (PA)
- **Business Logic:**   La PA accede alla mappa tramite `AppPA.selezionaMappa()` → `reindirizzaMappa()`. `GestioneAree.aggiornaRestrizioni()` modifica le zone. `GestioneAree.analisiConflitti()` verifica sovrapposizioni. `ZonaGeografica.checkArea()` è usata in runtime per validare le posizioni degli utenti.
- **Includes:** Nessuno
- **Extends:** Sovrascrittura (confermaSovrascrittura/rifiutaSovrascrittura).

---

## 4. Architectural Relationships

### 4.1 Generalizzazioni (Ereditarietà)

| Sottoclasse | Superclasse | Spiegazione |
|---|---|---|
| **Utente** | **Attore** |   L'utente finale eredita email e id dall'attore, aggiungendo dati di profilazione e geolocalizzazione. |
| **Operatore** | **Attore** |   L'operatore eredita le credenziali base, distinguendosi per ruolo e permessi. |
| **PA** | **Attore** |   La Pubblica Amministrazione eredita le credenziali, accedendo a funzionalità di governance. |

### 4.2 Associazioni

| # | Associazione | Entità A | Entità B | Molteplicità | Spiegazione |
|---|---|---|---|---|---|
| 1 | **appartiene** | Flotta | Mezzo | 1..1 ↔ 1..* |   Ogni mezzo appartiene a esattamente una flotta. Una flotta contiene almeno un mezzo. |
| 2 | **utilizza** | Mezzo | Corsa | 1..1 ↔ 0..* |   Ogni corsa utilizza esattamente un mezzo. Un mezzo può essere utilizzato in zero o più corse nel tempo. |
| 3 | **modera** | GestioneUtenti | Utente | 0..* ↔ 0..* |   Il controller di moderazione opera su zero o più utenti, e un utente può essere soggetto a zero o più azioni di moderazione. |
| 4 | **verifica** | GestorePagamento | MetodoPagamento | 0..* ↔ 0..* |   Il gestore pagamento verifica zero o più metodi di pagamento. Un metodo può essere verificato più volte. |
| 5 | **interroga** | RicercaMezzi | Mezzo | 0..* ↔ 0..* |   Il controller di ricerca interroga i mezzi disponibili. Relazione molti-a-molti per query multiple. |
| 6 | **amministra** | GestioneCorsa | Mezzo | 1..* ↔ 0..* |   Il controller di corsa amministra almeno un mezzo durante il ciclo di vita della corsa. |
| 7 | **effettua** | GestioneCorsa | Corsa | 0..* ↔ 1..1 |   Il controller effettua esattamente una corsa alla volta. |
| 8 | **gestisce** | GestioneFlotta | Mezzo | 0..* ↔ 0..* |   Il controller di flotta gestisce i mezzi per manutenzione e rilocazione. |
| 9 | **crea** | GestioneFlotta | Segnalazione | 0..* ↔ 1..1 |   La gestione flotta crea segnalazioni di guasto/manutenzione per ogni mezzo. |
| 10 | **analizza** | GestioneStatistiche | Corsa | 0..* ↔ 0..* |   Il controller statistiche analizza le corse per generare report. |
| 11 | **prenota** | GestionePrenotazione | Mezzo | 1..* ↔ 0..* |   Il controller prenotazione prenota almeno un mezzo alla volta. |
| 12 | **ha** | GestionePrenotazione | Prenotazione | 0..* ↔ 1..1 |   Il controller ha esattamente una prenotazione attiva per richiesta. |
| 13 | **genera** | GestionePrenotazione | Segnalazione | 0..* ↔ 1..1 |   Ogni prenotazione può generare una segnalazione (es. mancato ritiro, timeout). |
| 14 | **aggiunge** | GestioneAree | ZonaGeografica | 0..* ↔ 0..* |   Il controller aree aggiunge e modifica zone geografiche. |
| 15 | **esegue check** | ZonaGeografica | GestioneCorsa | 0..* ↔ 0..* |   Le zone geografiche sono verificate durante la gestione della corsa per applicare restrizioni in tempo reale. |

---

## 5. Architectural Constraints & Invariants

> **Nota:** Il modello XMI non contiene vincoli espliciti (`ownedRule`, `ownedConstraint`). I seguenti vincoli sono dedotti dalla topologia e dalle molteplicità.

1. **  Vincolo di appartenenza univoca del Mezzo:** Ogni `Mezzo` appartiene a esattamente 1 `Flotta` (molteplicità 1..1). Un mezzo non può essere orfano.

2. **  Vincolo di corsa singola per mezzo:** L'associazione `utilizza` (Mezzo 1..1 ↔ Corsa 0..*) implica che ogni corsa usa un solo mezzo. Un mezzo non può essere in due corse simultanee (vincolo di stato).

3. **  Vincolo di prenotazione con mezzo:** L'associazione `prenota` (GestionePrenotazione 1..* ↔ Mezzo 0..*) richiede che ci sia almeno un mezzo disponibile per procedere con la prenotazione.

4. **  Vincolo di pagamento obbligatorio:** Il metodo `GestorePagamento.pagamentoCorsa()` richiede `idMetodoPagamento` valido, implicando che l'utente deve avere almeno un MetodoPagamento registrato e verificato.

5. **  Vincolo di geo-fence in tempo reale:** L'associazione `esegue check` (ZonaGeografica ↔ GestioneCorsa) implica la verifica continua delle restrizioni geografiche durante la corsa.

6. **  Vincolo di integrità segnalazione:** Ogni `Segnalazione` ha `idMezzo` obbligatorio, collegando sempre la segnalazione a un veicolo specifico.

7. **  Vincolo di timeout prenotazione:** Il metodo `GestionePrenotazione.gestisciTimeout()` e `notificaScadenzaTempo()` implicano un vincolo temporale sulle prenotazioni non utilizzate.

8. **  Vincolo di convalida carta:** Il flusso `elaboraDatiCarta → convalidaCarta` richiede validazione esterna (Gateway) prima del salvataggio locale del `MetodoPagamento`.

9. **  Separazione View-Controller:** Le classi App* (View) non accedono direttamente alle entità di dominio. Tutti i flussi passano attraverso i controller (GestioneCorsa, GestorePagamento, ecc.), mantenendo il pattern MVC.

---

## Appendice: Tipi Custom Referenziati

Il modello referenzia tipi non-standard tramite ID interni:

| Tipo ID | Tipo Inferito |
|---|---|
| `float_id` |   Tipo numerico floating-point (double/float). |
| `void_id` |   Tipo void (nessun ritorno). |
| `time_id` |   Tipo temporale (es. LocalTime, Timestamp). |
| `date_id` |   Tipo data (es. LocalDate). |
| `enum_id` |   Tipo enumerativo generico (stato, tipo restrizione, azione correttiva). |
| `bool_id` |   Tipo booleano. |
| `percorso_id` |   Tipo custom per dati di percorso calcolato. |
| `datiPercorso_id` |   Tipo custom per output del Servizio Mappa. |
| `statistiche_id` |   Tipo custom per dati statistici aggregati. |
| `LineString_id` |   Tipo geometrico GeoJSON/WKT per poligoni e confini di zone. |

## Appendice: Profilo UML Applicato

Il profilo **cofeecoders_profile** (identico al component diagram) definisce gli stereotipi: Agent, Participant, boundary, entity, EntityBean, primitive, Struct, type. Nessuna applicazione esplicita di stereotipi alle classi è presente nel modello XMI.

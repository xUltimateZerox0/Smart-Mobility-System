---
status: SUCCESS_WITH_INFERENCE
extractor_version: "2.0-unsafe"
parsed_entities: 156
---

# UML-XMI Semantic Extraction Report (Unsafe Mode)

## Master Specification — Smart Mobility System

> **Avvertenza**: Nessun elemento dei modelli XMI sorgente conteneva documentazione umana (`ownedComment`, `ownedRule`).
> Tutte le descrizioni contrassegnate con **[INFERRED]** sono state dedotte dal nome dell'entità, dal pattern architetturale, dalla topologia delle dipendenze e dai contratti di classe.
>
> **Fonti analizzate**:
> - `Component-diagram-clean.xml.uml` + profilo (architettura a componenti)
> - `Class-diagram-clean.xml.uml` + profilo (modello di dominio e controller)
> - `UC-diagram-clean.xml.uml` + profilo (casi d'uso e attori)

---

## 1. System Boundaries & Actors

Il sistema è denominato **"Smart Mobility System"** (boundary nel diagramma UC) e rappresenta una piattaforma per il noleggio e la gestione di mezzi di trasporto condivisi (biciclette, monopattini, automobili).

### 1.1 Attori (dal Use Case Diagram)

- **Utente**: [INFERRED] Utente finale del servizio di mobilità. Interagisce con il sistema per cercare mezzi, prenotarli, avviare/sospendere corse, ottimizzare percorsi, gestire metodi di pagamento.
  - *Use Case associati*: RicercaMezzi, OttimizzazionePercorso, MetodoPagamento, PrenotazioneMezzo, GestioneCorsa, SospensioneCorsa.

- **PA** (Pubblica Amministrazione): [INFERRED] Ente pubblico che monitora l'utilizzo del servizio, analizza lo stato della flotta e impone restrizioni geografiche.
  - *Use Case associati*: MonitoraggioStatisticheAnalisi, AnalisiStatoFlotta, RestrizioniGeografiche.

- **Operatore Tecnico**: [INFERRED] Operatore specializzato nella manutenzione e gestione tecnica della flotta veicoli.
  - *Use Case associati*: GestioneFlotta.

- **Operatore Servizio Clienti**: [INFERRED] Operatore dedicato all'assistenza clienti e alla moderazione degli utenti.
  - *Use Case associati*: ModerazioneUtente, AmministrazionePrenotazioni.

---

## 2. Core Entities & Components

### 2.1 Architettura a Componenti (Component Diagram — Pattern MVC)

#### 2.1.1 Model (Componente)
- *Responsabilità*: [INFERRED] Layer di dominio del pattern MVC. Contiene tutte le entità di business e gestisce la persistenza dei dati.
- *Interfacce Realizzate*: **Gestione Dati**
- *Dipendenza*: Consuma **Connessione Dati** (dal DBMS)
- *Entità contenute*: Corsa, Attore, Bicicletta, Automobile, Mezzo, Monopattino, Utente, PA, Operatore, OperatoreTecnico, OperatoreServizioClienti, Flotta, ZonaGeografica, MetodoPagamento, Prenotazione, Segnalazione

#### 2.1.2 View (Componente)
- *Responsabilità*: [INFERRED] Layer di presentazione MVC. Contiene i sotto-componenti che rappresentano le UI per ciascun tipo di attore.
- *Sotto-componenti*:
  - **AppUtente**: [INFERRED] Front-end per l'utente finale. Consuma le interfacce «Gestione Corsa» e «Profilazione».
  - **AppOperatore**: [INFERRED] Front-end per gli operatori. Consuma le interfacce «Moderazione Utente» e «Amministrazione Flotta».
  - **AppPA**: [INFERRED] Front-end per la PA. Consuma l'interfaccia «Statistiche e Restrizioni».

#### 2.1.3 Controller (Componente)
- *Responsabilità*: [INFERRED] Layer di logica applicativa MVC. Orchestra le operazioni di business e media tra View e Model.
- *Interfacce Realizzate*: Moderazione Utente, Gestione Corsa, Statistiche e Restrizioni, Profilazione, Amministrazione Flotta
- *Dipendenza*: Consuma **Gestione Dati** (dal Model)
- *Classi Controller contenute*: GestionePrenotazione, GestioneUtenti, GestioneStatistiche, GestioneCorsa, RicercaMezzi, GestorePagamento, GestioneFlotta, GestioneAree

#### 2.1.4 Componenti Esterni / Infrastrutturali

| Componente | Interfaccia Realizzata | Responsabilità |
|------------|----------------------|----------------|
| **ServizioMappa** | API Mappa | [INFERRED] Servizio esterno di cartografia e geolocalizzazione (geocoding, routing, visualizzazione). |
| **Gateway Pagamento** | API Pagamento | [INFERRED] Servizio esterno di payment processing (addebiti, rimborsi, gestione metodi). |
| **DBMS** | Connessione Dati | [INFERRED] Sistema di gestione database per la persistenza dei dati. |
| **Mezzo : IoT** | *(consuma API Controllo)* | [INFERRED] Dispositivo IoT integrato nel mezzo fisico. Fornisce telemetria e accetta comandi di controllo remoto. |

#### 2.1.5 Interfacce Architetturali

| Interfaccia | Tipo | Semantica |
|-------------|------|-----------|
| Gestione Dati | Provided (Model) | [INFERRED] Operazioni CRUD sulle entità di dominio. |
| Connessione Dati | Provided (DBMS) | [INFERRED] Accesso dati a basso livello (SQL/ORM). |
| API Mappa | Provided (ServizioMappa) | [INFERRED] Geolocalizzazione, geocoding, rendering mappa. |
| API Pagamento | Provided (Gateway Pagamento) | [INFERRED] Addebito, rimborso, verifica transazione. |
| API Controllo | Required (Controller) | [INFERRED] Comandi lock/unlock e telemetria IoT. |
| Moderazione Utente | Provided (Controller) | [INFERRED] Ban/unban/segnalazione utenti. |
| Gestione Corsa | Provided (Controller) | [INFERRED] Ciclo di vita corsa (start/stop/track). |
| Statistiche e Restrizioni | Provided (Controller) | [INFERRED] Dati aggregati e vincoli territoriali. |
| Profilazione | Provided (Controller) | [INFERRED] Gestione profilo utente. |
| Amministrazione Flotta | Provided (Controller) | [INFERRED] Gestione operativa flotta veicoli. |

---

### 2.2 Modello di Dominio (Class Diagram)

#### 2.2.1 Gerarchia degli Attori

```
Attore (abstract root)
├── Utente
├── Operatore
│   ├── OperatoreTecnico
│   └── OperatoreServizioClienti
└── PA
```

**Attore** (Tipo: Classe)
- *Responsabilità*: [INFERRED] Classe base per tutti gli utenti del sistema. Contiene le credenziali comuni.
- *Attributi*:
  - `email`: String — [INFERRED] Indirizzo email per autenticazione e comunicazioni.
  - `id`: Integer — [INFERRED] Identificativo univoco dell'attore nel sistema.
- *Operazioni*: getEmail(), setEmail(), getId(), setId()

**Utente** (Tipo: Classe, estende Attore)
- *Responsabilità*: [INFERRED] Utente finale registrato che utilizza il servizio di mobilità condivisa.
- *Attributi*:
  - `coordinateUtente`: float — [INFERRED] Posizione GPS corrente dell'utente per la ricerca mezzi vicini.
  - `numMezziPrenotati`: Integer — [INFERRED] Contatore dei mezzi attualmente prenotati dall'utente.
  - `nomeUtente`: String — [INFERRED] Nome di battesimo.
  - `cognomeUtente`: String — [INFERRED] Cognome.
  - `telefono`: String — [INFERRED] Numero di telefono per contatto e verifica.
- *Operazioni*: getter/setter per tutti gli attributi

**Operatore** (Tipo: Classe, estende Attore)
- *Responsabilità*: [INFERRED] Classe base per gli operatori aziendali. Eredita email e id da Attore.

**OperatoreTecnico** (Tipo: Classe, estende Operatore)
- *Responsabilità*: [INFERRED] Operatore specializzato nella manutenzione e riparazione dei mezzi.

**OperatoreServizioClienti** (Tipo: Classe, estende Operatore)
- *Responsabilità*: [INFERRED] Operatore dedicato al supporto clienti e alla moderazione.

**PA** (Tipo: Classe, estende Attore)
- *Responsabilità*: [INFERRED] Ente di Pubblica Amministrazione. Eredita email e id da Attore.

#### 2.2.2 Gerarchia dei Mezzi

```
Mezzo (abstract root)
├── Bicicletta
├── Automobile
└── Monopattino
```

**Mezzo** (Tipo: Classe)
- *Responsabilità*: [INFERRED] Classe base per tutti i veicoli del servizio di sharing. Definisce le proprietà comuni.
- *Attributi*:
  - `coordinateMezzo`: float — [INFERRED] Posizione GPS corrente del mezzo.
  - `idMezzo`: Integer — [INFERRED] Identificativo univoco del mezzo.
  - `stato`: enum — [INFERRED] Stato operativo (es. disponibile, in uso, in manutenzione, fuori servizio).
  - `autonomia`: float — [INFERRED] Autonomia residua (km o % batteria).
  - `costoOrario`: float — [INFERRED] Tariffa oraria di noleggio (€/h).
  - `velocitàMax`: float — [INFERRED] Velocità massima consentita (km/h).
  - `condizione`: String — [INFERRED] Descrizione testuale dello stato fisico del mezzo.
  - `tipo`: String — [INFERRED] Tipo di mezzo (bicicletta, monopattino, automobile).
  - `idFlotta`: Integer — [INFERRED] FK alla Flotta di appartenenza.
- *Operazioni*: getter/setter per tutti gli attributi

**Bicicletta**, **Automobile**, **Monopattino** (Tipo: Classe, estendono Mezzo)
- *Responsabilità*: [INFERRED] Specializzazioni di Mezzo senza attributi aggiuntivi dichiarati. Possono essere usate per logica polimorfica (es. tariffe diverse, regole di parcheggio diverse).

#### 2.2.3 Entità di Business

**Corsa** (Tipo: Classe)
- *Responsabilità*: [INFERRED] Rappresenta una sessione di utilizzo di un mezzo. Traccia il tragitto e il costo.
- *Attributi*:
  - `costo`: float (private) — [INFERRED] Costo totale della corsa calcolato.
  - `orario inizio`: time (private) — [INFERRED] Timestamp di inizio corsa.
  - `orario fine`: time (private) — [INFERRED] Timestamp di fine corsa.
  - `coordinate partenza`: float (private) — [INFERRED] Posizione GPS di partenza.
  - `coordinate arrivo`: float (private) — [INFERRED] Posizione GPS di arrivo.
- *Operazioni*: getter/setter per tutti gli attributi

**Prenotazione** (Tipo: Classe)
- *Responsabilità*: [INFERRED] Rappresenta la prenotazione anticipata di un mezzo.
- *Attributi*:
  - `id`: Integer — [INFERRED] Identificativo univoco della prenotazione.
  - `stato`: enum — [INFERRED] Stato della prenotazione (attiva, completata, annullata).
  - `utente`: Integer — [INFERRED] FK all'utente che ha effettuato la prenotazione.
  - `mezzo`: Integer — [INFERRED] FK al mezzo prenotato.
  - `ora`: time — [INFERRED] Ora della prenotazione.
  - `data`: date — [INFERRED] Data della prenotazione.
- *Operazioni*: getter/setter per tutti gli attributi

**Segnalazione** (Tipo: Classe)
- *Responsabilità*: [INFERRED] Rappresenta una segnalazione di problema (guasto, parcheggio scorretto, etc.).
- *Attributi*:
  - `idSegnalazione`: Integer — [INFERRED] Identificativo univoco.
  - `idMezzo`: Integer — [INFERRED] FK al mezzo segnalato.
  - `stato`: bool — [INFERRED] Stato di risoluzione (aperta/chiusa).
  - `ora`: time — [INFERRED] Ora della segnalazione.
  - `data`: date — [INFERRED] Data della segnalazione.
- *Operazioni*: getter/setter per tutti gli attributi

**Flotta** (Tipo: Classe)
- *Responsabilità*: [INFERRED] Aggregazione logica di mezzi gestita come unità operativa.
- *Attributi*:
  - `idFlotta`: Integer — [INFERRED] Identificativo univoco della flotta.
- *Operazioni*: getIdFlotta(), setIdFlotta()

**ZonaGeografica** (Tipo: Classe)
- *Responsabilità*: [INFERRED] Definisce un'area geografica operativa con eventuali restrizioni.
- *Attributi*:
  - `idArea`: Integer — [INFERRED] Identificativo univoco dell'area.
  - `tipoRestrizione`: enum (private) — [INFERRED] Tipo di restrizione applicata (parcheggio vietato, velocità ridotta, zona operativa, etc.).
  - `noteRestrizione`: String — [INFERRED] Descrizione testuale della restrizione.
  - `zona`: LineString — [INFERRED] Geometria dell'area (poligono/linea in formato GeoJSON o WKT).
- *Operazioni*: getter/setter per tutti gli attributi

**ReportUtente** (Tipo: Classe)
- *Responsabilità*: [INFERRED] Report/scheda associata a un utente per il tracciamento dello storico.
- *Attributi*:
  - `idUtente`: Integer — [INFERRED] FK all'utente.
  - `report`: String — [INFERRED] Contenuto testuale del report.
- *Operazioni*: getter/setter per tutti gli attributi

**MetodoPagamento** (Tipo: Classe)
- *Responsabilità*: [INFERRED] Metodo di pagamento registrato dall'utente (carta, wallet, etc.).

**MetodoAutenticazione** (Tipo: Classe)
- *Responsabilità*: [INFERRED] Metodo di autenticazione utilizzato per le Corse (es. QR code, NFC, PIN).

#### 2.2.4 Classi Controller (dal Class Diagram)

**GestioneCorsa** (Tipo: Classe)
- *Responsabilità*: [INFERRED] Controller per il ciclo di vita della corsa.
- *Operazioni*:
  - `avviaCorsa()` — [INFERRED] Inizia una nuova corsa: sblocca il mezzo e inizia il tracciamento.
  - `terminaCorsa()` — [INFERRED] Termina la corsa: blocca il mezzo, calcola il costo, avvia il pagamento.
  - `controllaDisponibilità()` — [INFERRED] Verifica che il mezzo sia disponibile prima dell'avvio.
  - `aggiornaStima()` — [INFERRED] Aggiorna la stima di costo/tempo durante la corsa.
  - `sospensioneCorsa()` — [INFERRED] Sospende temporaneamente la corsa (es. pausa).
  - `CalcoloPercorso()` — [INFERRED] Calcola il percorso ottimale per l'utente.

**GestionePrenotazione** (Tipo: Classe)
- *Responsabilità*: [INFERRED] Controller per il ciclo di vita delle prenotazioni.
- *Operazioni*:
  - `richiestaPrenotazione()` — [INFERRED] Crea una nuova prenotazione per un mezzo selezionato.
  - `richiediLista()` — [INFERRED] Restituisce la lista delle prenotazioni dell'utente.
  - `annullaPrenotazione()` — [INFERRED] Annulla una prenotazione attiva.
  - `creaSegnalazione()` — [INFERRED] Genera una segnalazione legata alla prenotazione.

**GestioneUtenti** (Tipo: Classe)
- *Responsabilità*: [INFERRED] Controller per la gestione e moderazione degli utenti.
- *Operazioni*:
  - `gestioneUtente()` — [INFERRED] CRUD sulle informazioni utente, attivazione/disattivazione account.

**RicercaMezzi** (Tipo: Classe)
- *Responsabilità*: [INFERRED] Controller per la ricerca e localizzazione dei mezzi.
- *Operazioni*:
  - `visualizzaMezziVicini()` — [INFERRED] Mostra i mezzi disponibili nella prossimità dell'utente.
  - `visualizzaSpecifiche()` — [INFERRED] Mostra i dettagli di uno specifico mezzo selezionato.

**GestorePagamento** (Tipo: Classe)
- *Responsabilità*: [INFERRED] Controller per l'elaborazione dei pagamenti.
- *Operazioni*:
  - `impostaMetodoPagamento()` — [INFERRED] Configura il metodo di pagamento dell'utente.
  - `visualizzaImporto()` — [INFERRED] Mostra il dettaglio dell'importo da pagare.
  - `controlloMetodo()` — [INFERRED] Verifica la validità del metodo di pagamento prima dell'addebito.

**GestioneFlotta** (Tipo: Classe)
- *Responsabilità*: [INFERRED] Controller per la gestione operativa della flotta.
- *Operazioni*:
  - `analisiStatoFlotta()` — [INFERRED] Analizza lo stato generale della flotta (mezzi disponibili, in uso, guasti).
  - `elencaMezziFlotta()` — [INFERRED] Lista tutti i mezzi appartenenti a una flotta.
  - `creaSegnalazione()` — [INFERRED] Crea una segnalazione di manutenzione per un mezzo.
  - `bloccaMezzo()` — [INFERRED] Blocca un mezzo rendendolo non disponibile (manutenzione, furto, etc.).

**GestioneStatistiche** (Tipo: Classe)
- *Responsabilità*: [INFERRED] Controller per l'aggregazione e presentazione dei dati statistici.
- *Operazioni*:
  - `analisiTratte()` — [INFERRED] Analizza le tratte più percorse, i pattern di utilizzo.
  - `generaFileReport()` — [INFERRED] Genera un file di report scaricabile con i dati aggregati.

**GestioneAree** (Tipo: Classe)
- *Responsabilità*: [INFERRED] Controller per la definizione e gestione delle zone geografiche.
- *Operazioni*:
  - `aggiornaRestrizione()` — [INFERRED] Aggiorna le restrizioni applicate a una zona geografica.

---

### 2.3 Relazioni tra Classi (Associations dal Class Diagram)

| Associazione | Classe A | Molteplicità A | Classe B | Molteplicità B | Semantica |
|-------------|----------|-----------------|----------|-----------------|-----------|
| **possiede** | Utente | 1 | ReportUtente | 1 | [INFERRED] Ogni utente possiede esattamente un report personale. |
| **richiede** | Corsa | 1 | MetodoAutenticazione | 1 | [INFERRED] Ogni corsa richiede esattamente un metodo di autenticazione per lo sblocco. |
| **appartiene** | Flotta | 1 | Mezzo | 1..* | [INFERRED] Ogni mezzo appartiene a esattamente una flotta; una flotta contiene N mezzi. |
| **utilizza** | Mezzo | 1 | Corsa | 0..* | [INFERRED] Un mezzo può essere utilizzato in N corse nel tempo; ogni corsa utilizza un mezzo. |
| **modera** | GestioneUtenti | 0..* | Utente | 0..* | [INFERRED] GestioneUtenti modera gli utenti (ban, warning, verifica). |
| **verifica** | GestorePagamento | 0..* | MetodoPagamento | 0..* | [INFERRED] GestorePagamento verifica e valida i metodi di pagamento registrati. |
| **interroga** | RicercaMezzi | 0..* | Mezzo | 0..* | [INFERRED] RicercaMezzi interroga i mezzi per filtrarli in base a criteri (posizione, tipo, disponibilità). |
| **amministra** | GestioneCorsa | 1..* | Mezzo | 0..* | [INFERRED] GestioneCorsa amministra i mezzi durante le corse (blocco/sblocco). |
| **effettua** | GestioneCorsa | 0..* | Corsa | 1 | [INFERRED] GestioneCorsa effettua (crea/gestisce) le corse. |
| **gestisce** | GestioneFlotta | 0..* | Mezzo | 0..* | [INFERRED] GestioneFlotta gestisce i mezzi per manutenzione e stato operativo. |
| **crea** | GestioneFlotta | 0..* | Segnalazione | 1 | [INFERRED] GestioneFlotta crea segnalazioni di manutenzione per i mezzi. |
| **analizza** | GestioneStatistiche | 0..* | Corsa | 0..* | [INFERRED] GestioneStatistiche analizza le corse per produrre report statistici. |
| **prenota** | GestionePrenotazione | 1..* | Mezzo | 0..* | [INFERRED] GestionePrenotazione prenota i mezzi per gli utenti. |
| **ha** | GestionePrenotazione | 0..* | Prenotazione | 1 | [INFERRED] GestionePrenotazione gestisce (ha) le istanze di Prenotazione. |
| **genera** | GestionePrenotazione | 0..* | Segnalazione | 1 | [INFERRED] GestionePrenotazione genera segnalazioni legate a problemi di prenotazione. |
| **aggiunge** | GestioneAree | 0..* | ZonaGeografica | 0..* | [INFERRED] GestioneAree aggiunge/modifica zone geografiche nel sistema. |
| **esegue check** | ZonaGeografica | 0..* | GestioneCorsa | 0..* | [INFERRED] ZonaGeografica esegue check sulla GestioneCorsa per verificare che la corsa rispetti le restrizioni territoriali. |

---

## 3. Use Case Logic & Flows

### 3.1 Use Case dell'Utente

#### UC: RicercaMezzi
- **Attore principale**: Utente
- **Business Logic**: [INFERRED] L'utente richiede la visualizzazione dei mezzi disponibili nella propria area. Il sistema localizza i mezzi tramite il ServizioMappa (API Mappa), filtra per disponibilità/tipo e li mostra sulla mappa.
- **Preconditions**: [INFERRED] L'utente è autenticato e ha concesso la geolocalizzazione.
- **Post-conditions**: [INFERRED] L'utente visualizza la lista/mappa dei mezzi disponibili.
- **Includes**: Nessuno (non modellato esplicitamente)
- **Extends**: Nessuno

#### UC: PrenotazioneMezzo
- **Attore principale**: Utente
- **Business Logic**: [INFERRED] L'utente seleziona un mezzo dalla lista e richiede la prenotazione. GestionePrenotazione verifica la disponibilità, crea una Prenotazione con stato "attiva", decrementa la disponibilità del mezzo.
- **Preconditions**: [INFERRED] Mezzo disponibile; utente con profilo completo e metodo di pagamento valido.
- **Post-conditions**: [INFERRED] Prenotazione creata; mezzo riservato per l'utente.
- **Includes**: Nessuno
- **Extends**: Nessuno

#### UC: GestioneCorsa
- **Attore principale**: Utente
- **Business Logic**: [INFERRED] L'utente avvia una corsa su un mezzo prenotato o disponibile. GestioneCorsa sblocca il mezzo via API Controllo (IoT), traccia il percorso, e alla fine calcola il costo basandosi su costoOrario del Mezzo e durata. GestorePagamento addebita l'importo via Gateway Pagamento.
- **Preconditions**: [INFERRED] Mezzo prenotato o disponibile; utente autenticato.
- **Post-conditions**: [INFERRED] Corsa registrata; pagamento effettuato; mezzo reso disponibile.
- **Includes**: Nessuno
- **Extends**: Nessuno

#### UC: SospensioneCorsa
- **Attore principale**: Utente
- **Business Logic**: [INFERRED] L'utente richiede una pausa temporanea della corsa in corso. Il mezzo resta bloccato e associato all'utente; il conteggio costi può continuare a tariffa ridotta o fermarsi, a seconda della policy.
- **Preconditions**: [INFERRED] Corsa in corso.
- **Post-conditions**: [INFERRED] Corsa in stato "sospesa"; mezzo bloccato.
- **Includes**: Nessuno
- **Extends**: Nessuno

#### UC: OttimizzazionePercorso
- **Attore principale**: Utente
- **Business Logic**: [INFERRED] L'utente richiede il calcolo del percorso ottimale verso una destinazione. Il sistema usa il ServizioMappa (API Mappa) per calcolare il routing, considerando le RestrizioniGeografiche (zone vietate, percorsi preferenziali).
- **Preconditions**: [INFERRED] Corsa in corso o destinazione specificata.
- **Post-conditions**: [INFERRED] Percorso ottimale visualizzato sulla mappa.
- **Includes**: Nessuno
- **Extends**: Nessuno

#### UC: MetodoPagamento
- **Attore principale**: Utente
- **Business Logic**: [INFERRED] L'utente gestisce i propri metodi di pagamento (aggiunta, rimozione, modifica). GestorePagamento valida il metodo tramite il Gateway Pagamento (API Pagamento).
- **Preconditions**: [INFERRED] Utente autenticato.
- **Post-conditions**: [INFERRED] MetodoPagamento aggiornato nel profilo utente.
- **Includes**: Nessuno
- **Extends**: Nessuno

### 3.2 Use Case della PA

#### UC: MonitoraggioStatisticheAnalisi
- **Attore principale**: PA
- **Business Logic**: [INFERRED] La PA accede a dashboard con dati aggregati: numero corse, tratte più frequentate, distribuzione mezzi, orari di punta. GestioneStatistiche aggrega i dati delle Corse e genera report.
- **Preconditions**: [INFERRED] PA autenticata con credenziali istituzionali.
- **Post-conditions**: [INFERRED] Report statistico visualizzato o scaricato.
- **Includes**: Nessuno
- **Extends**: Nessuno

#### UC: AnalisiStatoFlotta
- **Attore principale**: PA
- **Business Logic**: [INFERRED] La PA visualizza lo stato generale della flotta: mezzi operativi vs. guasti, distribuzione territoriale, livelli di utilizzo.
- **Preconditions**: [INFERRED] PA autenticata.
- **Post-conditions**: [INFERRED] Dashboard stato flotta visualizzata.
- **Includes**: Nessuno
- **Extends**: Nessuno

#### UC: RestrizioniGeografiche
- **Attore principale**: PA
- **Business Logic**: [INFERRED] La PA definisce o modifica restrizioni su zone geografiche: aree di parcheggio consentito, zone a velocità ridotta, confini del servizio. GestioneAree aggiorna le ZonaGeografica.
- **Preconditions**: [INFERRED] PA autenticata; zona geografica esistente o nuova.
- **Post-conditions**: [INFERRED] Restrizione applicata/modificata; GestioneCorsa ne terrà conto via "esegue check".
- **Includes**: Nessuno
- **Extends**: Nessuno

### 3.3 Use Case dell'Operatore Tecnico

#### UC: GestioneFlotta
- **Attore principale**: Operatore Tecnico
- **Business Logic**: [INFERRED] L'operatore tecnico gestisce lo stato operativo dei mezzi: analisi stato flotta, blocco di mezzi guasti, creazione segnalazioni di manutenzione, elenco mezzi per flotta.
- **Preconditions**: [INFERRED] Operatore tecnico autenticato.
- **Post-conditions**: [INFERRED] Stato mezzi aggiornato; segnalazioni create.
- **Includes**: Nessuno
- **Extends**: Nessuno

### 3.4 Use Case dell'Operatore Servizio Clienti

#### UC: ModerazioneUtente
- **Attore principale**: Operatore Servizio Clienti
- **Business Logic**: [INFERRED] L'operatore visualizza segnalazioni relative agli utenti, può sospendere o riattivare account, gestire warning e ban. GestioneUtenti media le operazioni sul Model.
- **Preconditions**: [INFERRED] Operatore SC autenticato.
- **Post-conditions**: [INFERRED] Stato utente aggiornato (attivo/sospeso/bannato).
- **Includes**: Nessuno
- **Extends**: Nessuno

#### UC: AmministrazionePrenotazioni
- **Attore principale**: Operatore Servizio Clienti
- **Business Logic**: [INFERRED] L'operatore visualizza e gestisce le prenotazioni degli utenti: può annullare prenotazioni problematiche, risolvere conflitti, verificare lo stato delle segnalazioni legate alle prenotazioni.
- **Preconditions**: [INFERRED] Operatore SC autenticato.
- **Post-conditions**: [INFERRED] Prenotazioni gestite; segnalazioni risolte.
- **Includes**: Nessuno
- **Extends**: Nessuno

---

## 4. Architectural Relationships

### 4.1 Dependency Map (Component Diagram — Relazioni «use»)

| # | Client | → Supplier (Interfaccia) | Semantica |
|---|--------|--------------------------|-----------|
| 1 | RicercaMezzi | → API Mappa | [INFERRED] Interroga il ServizioMappa per geolocalizzazione dei mezzi. |
| 2 | Model | → Connessione Dati | [INFERRED] Consuma il DBMS per persistenza delle entità di dominio. |
| 3 | Controller | → Gestione Dati | [INFERRED] Accede al Model per manipolare le entità (pattern MVC). |
| 4 | GestorePagamento | → API Pagamento | [INFERRED] Delega le transazioni al Gateway Pagamento esterno. |
| 5 | AppOperatore | → Moderazione Utente | [INFERRED] Consuma l'interfaccia di moderazione per gestire utenti. |
| 6 | GestioneCorsa | → API Controllo | [INFERRED] Invia comandi lock/unlock e riceve telemetria IoT. |
| 7 | AppUtente | → Gestione Corsa | [INFERRED] Consuma l'interfaccia per avviare/monitorare/terminare corse. |
| 8 | AppPA | → Statistiche e Restrizioni | [INFERRED] Consuma l'interfaccia per statistiche e configurazione restrizioni. |
| 9 | AppUtente | → Profilazione | [INFERRED] Consuma l'interfaccia per gestione profilo utente. |
| 10 | AppOperatore | → Amministrazione Flotta | [INFERRED] Consuma l'interfaccia per gestione operativa della flotta. |

### 4.2 Interface Realizations (Component Diagram)

| Componente | Realizza Interfaccia |
|------------|---------------------|
| Model | Gestione Dati |
| Controller | Moderazione Utente, Gestione Corsa, Statistiche e Restrizioni, Profilazione, Amministrazione Flotta |
| ServizioMappa | API Mappa |
| Gateway Pagamento | API Pagamento |
| DBMS | Connessione Dati |

### 4.3 Cross-Diagram Traceability Matrix

Questa matrice mappa le entità tra i tre diagrammi per verificare la coerenza architettonica:

| Concetto | Component Diagram | Class Diagram | Use Case Diagram |
|----------|-------------------|---------------|------------------|
| Utente | Classe in Model; AppUtente in View | Classe con attributi | Attore |
| Operatore | Classe in Model; AppOperatore in View | Classe con gerarchia (Tecnico, SC) | Attori (Op. Tecnico, Op. SC) |
| PA | Classe in Model; AppPA in View | Classe extends Attore | Attore |
| Mezzo | Classe in Model | Classe con gerarchia + attributi | *(Implicito in RicercaMezzi, GestioneFlotta)* |
| Corsa | Classe in Model | Classe con attributi | UC: GestioneCorsa, SospensioneCorsa |
| Prenotazione | Classe in Model | Classe con attributi | UC: PrenotazioneMezzo |
| Segnalazione | Classe in Model | Classe con attributi | *(Implicito in GestioneFlotta)* |
| Flotta | Classe in Model | Classe con attributi | UC: GestioneFlotta, AnalisiStatoFlotta |
| ZonaGeografica | Classe in Model | Classe con attributi | UC: RestrizioniGeografiche |
| MetodoPagamento | Classe in Model | Classe | UC: MetodoPagamento |
| GestioneCorsa | Classe in Controller | Classe con operazioni | UC: GestioneCorsa |
| GestionePrenotazione | Classe in Controller | Classe con operazioni | UC: PrenotazioneMezzo, AmministrazionePrenotazioni |
| RicercaMezzi | Classe in Controller | Classe con operazioni | UC: RicercaMezzi |
| GestorePagamento | Classe in Controller | Classe con operazioni | UC: MetodoPagamento |
| GestioneFlotta | Classe in Controller | Classe con operazioni | UC: GestioneFlotta |
| GestioneStatistiche | Classe in Controller | Classe con operazioni | UC: MonitoraggioStatisticheAnalisi |
| GestioneAree | Classe in Controller | Classe con operazioni | UC: RestrizioniGeografiche |
| GestioneUtenti | Classe in Controller | Classe con operazioni | UC: ModerazioneUtente |

---

## 5. Architectural Constraints & Invariants

- [INFERRED] **Pattern MVC rigoroso**: L'architettura segue un pattern Model-View-Controller con separazione netta. Le View (App*) non accedono mai direttamente al Model o al DBMS; ogni comunicazione transita attraverso le interfacce esposte dal Controller.

- [INFERRED] **Dipendenza unidirezionale View → Controller → Model → DBMS**: Il flusso di dipendenza è strettamente top-down. Non esistono dipendenze inverse.

- [INFERRED] **Servizi esterni disaccoppiati**: ServizioMappa, Gateway Pagamento e Mezzo : IoT sono componenti esterni al core MVC, accessibili solo tramite interfacce dedicate, garantendo basso accoppiamento e sostituibilità.

- [INFERRED] **Segregazione delle interfacce per attore (ISP)**: Ogni tipo di attore (Utente, Operatore, PA) possiede una View dedicata che consuma solo le interfacce pertinenti al proprio ruolo.

- [INFERRED] **Componente IoT isolato**: Il dispositivo fisico (Mezzo : IoT) è accessibile solo dal Controller tramite «API Controllo», impedendo manipolazioni dirette da parte delle View.

- [INFERRED] **Gerarchia di ereditarietà con single-inheritance**: Sia gli Attori (Attore → Utente | Operatore → OpTecnico | OpSC | PA) che i Mezzi (Mezzo → Bicicletta | Automobile | Monopattino) seguono alberi di ereditarietà a singola inheritance senza ereditarietà multipla.

- [INFERRED] **Vincolo di integrità Mezzo-Flotta**: Ogni Mezzo appartiene a esattamente una Flotta (molteplicità 1 lato Flotta nell'associazione "appartiene"). Non esistono mezzi orfani.

- [INFERRED] **Geo-fencing attivo**: L'associazione "esegue check" tra ZonaGeografica e GestioneCorsa indica che le restrizioni territoriali vengono verificate attivamente durante le corse, non solo in fase di prenotazione.

- [INFERRED] **Autenticazione per-corsa**: L'associazione "richiede" tra Corsa e MetodoAutenticazione (1:1) indica che ogni corsa richiede un'autenticazione esplicita, separata dall'autenticazione di login.

---

## 6. Profile Stereotypes

### 6.1 Profilo Component Diagram (`cofeecoders_profile`)

| Stereotipo | Estende | Semantica |
|------------|---------|-----------|
| `«use»` | Usage | [INFERRED] Marca le dipendenze di utilizzo tra componenti. |
| `«Agent»` | Class (→ Participant) | [INFERRED] Classe con comportamento attivo/autonomo. |
| `«Participant»` | Class | [INFERRED] Classe che partecipa a interazioni. |
| `«boundary»` | Class | [INFERRED] Classe che funge da interfaccia con l'esterno. |
| `«entity»` | Class | [INFERRED] Entità di dominio persistente. |
| `«Entity Bean»` | Class | [INFERRED] Entity in stile EJB/JPA. |
| `«primitive»` | Class | [INFERRED] Tipo primitivo del dominio. |
| `«Struct»` | Class | [INFERRED] Value object senza logica di business. |
| `«type»` | Class | [INFERRED] Definizione di tipo nel dominio. |

### 6.2 Profilo Class Diagram (`Classi_Diagram_profile`)

| Stereotipo | Estende | Semantica |
|------------|---------|-----------|
| `«use»` | Usage | [INFERRED] Marca le dipendenze di utilizzo tra classi. |

### 6.3 Profilo Use Case Diagram (`Diagramma_Casi_d_uso_profile`)

| Stereotipo | Estende | Attributi Custom | Semantica |
|------------|---------|------------------|-----------|
| `«UseCase»` | UseCase | Level, Complexity, UseCaseStatus, ImplementationStatus, Preconditions, Post-conditions, Author, Assumptions | [INFERRED] Stereotipo arricchito per la documentazione strutturata dei casi d'uso. **Nota**: tutti i campi sono vuoti nel modello sorgente. |
| `«CaseStory»` | UseCase | *(nessuno)* | [INFERRED] Variante narrativa del caso d'uso (user story format). |
| `«Context»` | UseCase | *(nessuno)* | [INFERRED] Caso d'uso che descrive il contesto operativo del sistema. |

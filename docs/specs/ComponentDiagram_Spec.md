```yaml
status: SUCCESS_WITH_INFERENCE
extractor_version: "2.0-unsafe"
parsed_entities: 37
```

# UML-XMI Semantic Extraction Report (Unsafe Mode)

**Source Model:** cofeecoders  
**Exporter:** Visual Paradigm 7.0.2  
**XMI Version:** 2.1  
**Profile:** cofeecoders_profile (stereotipi: use, Agent, Participant, boundary, entity, EntityBean, primitive, Struct, type)

---

## 1. System Boundaries & Actors

Il modello del diagramma dei componenti non definisce Attori UML espliciti (`uml:Actor`). L'architettura è organizzata secondo il pattern **MVC (Model-View-Controller)** con tre componenti principali come confini di sistema, più tre servizi esterni.

### Confini di Sistema

| Componente Boundary | xmi:id | Ruolo Architetturale |
|---|---|---|
| **View** | `zzS2v7mD.AACAQYg` | [INFERRED] Layer di presentazione. Contiene le interfacce applicative per i diversi profili utente del sistema Smart Mobility. |
| **Controller** | `3mq2v7mD.AACAQYr` | [INFERRED] Layer di logica applicativa e orchestrazione. Espone e realizza le interfacce di servizio del sistema, mediando tra View e Model. |
| **Model** | `YKY2v7mD.AACAQYD` | [INFERRED] Layer di persistenza e logica di dominio. Gestisce l'accesso ai dati e le entità di business del sistema. |

### Servizi Esterni

| Componente | xmi:id | Ruolo |
|---|---|---|
| **ServizioMappa** | `ozG9v7mD.AACARFZ` | [INFERRED] Servizio esterno di cartografia e geolocalizzazione (es. Google Maps, OpenStreetMap). Fornisce API per la visualizzazione mappe e il calcolo percorsi. |
| **Gateway Pagamento** | `77O9v7mD.AACARFq` | [INFERRED] Servizio esterno di elaborazione pagamenti (es. Stripe, PayPal). Gestisce le transazioni finanziarie in modo sicuro tramite API dedicate. |
| **DBMS** | `mqGDv7mD.AACARJV` | [INFERRED] Sistema di gestione database. Fornisce la persistenza dei dati del sistema tramite interfaccia di connessione dati. |
| **Mezzo : IoT** | `wksuKnmD.AACAQj5` | [INFERRED] Dispositivo IoT a bordo del veicolo condiviso. Fornisce telemetria in tempo reale (posizione GPS, stato batteria, blocco/sblocco) al sistema tramite API di controllo. |

---

## 2. Core Entities & Components

### 2.1 Componente: View

**Tipo:** `uml:Component`  
**xmi:id:** `zzS2v7mD.AACAQYg`  
**Responsabilità:** [INFERRED] Layer di presentazione dell'architettura MVC. Contiene le applicazioni client specifiche per ogni tipologia di attore del sistema.

#### Classi contenute:

| Classe | xmi:id | Responsabilità |
|---|---|---|
| **Class** | `EYSJv7mD.AACAQf0` | [INFERRED] Classe base astratta o generica del layer View. Potrebbe rappresentare il template base delle interfacce applicative. |
| **AppUtente** | `ZznQhXmD.AACAQwQ` | [INFERRED] Applicazione client per l'utente finale. Consente ricerca mezzi, prenotazione, gestione corsa, pagamento e gestione profilo. |
| **AppOperatoreTecnico** | `zdBIhXmD.AACAQzx` | [INFERRED] Applicazione client per l'operatore tecnico. Consente la gestione operativa della flotta veicoli (manutenzione, rilocazione, stato operativo). |
| **AppPA** | `euPIhXmD.AACAQ1G` | [INFERRED] Applicazione client per la Pubblica Amministrazione. Consente il monitoraggio statistico, l'analisi della flotta e la gestione delle restrizioni geografiche. |
| **AppOperatoreSC** | `BSkUhXmD.AACAQ.A` | [INFERRED] Applicazione client per l'Operatore Servizio Clienti. Consente la moderazione utenti e l'amministrazione delle prenotazioni. |

---

### 2.2 Componente: Controller

**Tipo:** `uml:Component`  
**xmi:id:** `3mq2v7mD.AACAQYr`  
**Responsabilità:** [INFERRED] Componente centrale di orchestrazione della logica applicativa. Realizza (implementa) tutte le interfacce di servizio del sistema e media le richieste dalle View verso il Model e i servizi esterni.

#### Interfacce Realizzate (provided):

| Interfaccia | xmi:id | Realizzazione |
|---|---|---|
| **Moderazione Utente** | `FSGPv7mD.AACARbj` | `QyGPv7mD.AACARbp` |
| **Gestione Corsa** | `R.IxKnmD.AACAQ9w` | `sOrxKnmD.AACARF1` |
| **Statistiche e Restrizioni** | `ie1hKnmD.AACAQ4x` | `BOXxKnmD.AACARGX` |
| **Profilazione** | `_nOfQXmD.AACAQpW` | `wNx_QXmD.AACAQsf` |
| **Amministrazione Flotta** | `BvUAwXmD.AACAQue` | `7bagwXmD.AACAQvf` |

#### Classi contenute:

| Classe | xmi:id | Responsabilità |
|---|---|---|
| **GestioneCorsa** | `Al.5KnmD.AACARRw` | [INFERRED] Controller dedicato alla gestione del ciclo di vita della corsa: avvio, monitoraggio, terminazione. Comunica con il dispositivo IoT tramite l'interfaccia API Controllo. |
| **RicercaMezzi** | `eZ.5KnmD.AACARRn` | [INFERRED] Controller dedicato alla ricerca e localizzazione dei mezzi disponibili. Utilizza il ServizioMappa tramite l'interfaccia API Mappa per la geolocalizzazione. |
| **GestorePagamento** | `wHAFKnmD.AACARfr` | [INFERRED] Controller dedicato all'elaborazione dei pagamenti. Comunica con il Gateway Pagamento tramite l'interfaccia API Pagamento per processare le transazioni. |

---

### 2.3 Componente: Model

**Tipo:** `uml:Component`  
**xmi:id:** `YKY2v7mD.AACAQYD`  
**Responsabilità:** [INFERRED] Layer di persistenza e dominio dell'architettura MVC. Gestisce le entità di business e l'accesso ai dati tramite il DBMS.

#### Interfacce Realizzate (provided):

| Interfaccia | xmi:id | Realizzazione |
|---|---|---|
| **Gestione Dati** | `.XEXv7mD.AACARYL` | `rXEXv7mD.AACARYR` |

#### Classi contenute:

| Classe | xmi:id | Responsabilità |
|---|---|---|
| **Zona Geografica** | `LesZv7mD.AACAQjq` | [INFERRED] Entità di dominio che modella le zone geografiche operative: geo-fence, aree di parcheggio, zone a traffico limitato. Rappresenta i confini territoriali del servizio. |

---

### 2.4 Componente: ServizioMappa

**Tipo:** `uml:Component`  
**xmi:id:** `ozG9v7mD.AACARFZ`  
**Responsabilità:** [INFERRED] Servizio esterno di cartografia. Fornisce geocoding, routing, visualizzazione mappe e calcolo distanze.

#### Interfacce Realizzate (provided):

| Interfaccia | xmi:id |
|---|---|
| **API Mappa** | `xP59v7mD.AACARGb` |

---

### 2.5 Componente: Gateway Pagamento

**Tipo:** `uml:Component`  
**xmi:id:** `77O9v7mD.AACARFq`  
**Responsabilità:** [INFERRED] Gateway per l'elaborazione sicura dei pagamenti elettronici. Gestisce autorizzazione, cattura e rimborso delle transazioni verso circuiti di pagamento esterni.

#### Interfacce Realizzate (provided):

| Interfaccia | xmi:id |
|---|---|
| **API Pagamento** | `Z.W3v7mD.AACARaG` |

---

### 2.6 Componente: DBMS

**Tipo:** `uml:Component`  
**xmi:id:** `mqGDv7mD.AACARJV`  
**Responsabilità:** [INFERRED] Sistema di gestione database relazionale o NoSQL. Fornisce persistenza, query e transazioni ACID per tutti i dati del dominio applicativo.

#### Interfacce Realizzate (provided):

| Interfaccia | xmi:id |
|---|---|
| **Connessione Dati** | `jvJnv7mD.AACARXl` |

---

### 2.7 Componente: Mezzo : IoT

**Tipo:** `uml:Component`  
**xmi:id:** `wksuKnmD.AACAQj5`  
**Responsabilità:** [INFERRED] Modulo IoT integrato nel veicolo condiviso. Gestisce lo sblocco/blocco fisico del mezzo, la trasmissione della telemetria GPS in tempo reale, il reporting dello stato batteria/carburante e la ricezione di comandi remoti dal Controller.

> **Nota:** Questo componente non realizza esplicitamente interfacce nel modello XMI, ma è il fornitore logico dell'interfaccia **API Controllo**.

---

### 2.8 Interfacce di Sistema (Catalogo completo)

| Interfaccia | xmi:id | Tipo | Responsabilità |
|---|---|---|---|
| **API Mappa** | `xP59v7mD.AACARGb` | `uml:Interface` | [INFERRED] Contratto per servizi di geolocalizzazione: geocoding, reverse geocoding, calcolo percorsi, ricerca POI. |
| **API Pagamento** | `Z.W3v7mD.AACARaG` | `uml:Interface` | [INFERRED] Contratto per operazioni di pagamento: autorizzazione, cattura, rimborso, verifica stato transazione. |
| **Connessione Dati** | `jvJnv7mD.AACARXl` | `uml:Interface` | [INFERRED] Contratto per l'accesso al database: connessione, query, transazioni, gestione del pool di connessioni. |
| **Gestione Dati** | `.XEXv7mD.AACARYL` | `uml:Interface` | [INFERRED] Contratto per le operazioni CRUD sulle entità di dominio. Astrazione del layer di persistenza esposta dal Model al Controller. |
| **Moderazione Utente** | `FSGPv7mD.AACARbj` | `uml:Interface` | [INFERRED] Contratto per le operazioni di moderazione: sospensione account, emissione sanzioni, gestione segnalazioni. |
| **Gestione Corsa** | `R.IxKnmD.AACAQ9w` | `uml:Interface` | [INFERRED] Contratto per il ciclo di vita della corsa: avvio, monitoraggio, sospensione, terminazione, calcolo tariffa. |
| **Statistiche e Restrizioni** | `ie1hKnmD.AACAQ4x` | `uml:Interface` | [INFERRED] Contratto per l'accesso a dashboard statistiche e la configurazione delle restrizioni geografiche operative. |
| **Profilazione** | `_nOfQXmD.AACAQpW` | `uml:Interface` | [INFERRED] Contratto per la gestione del profilo utente: registrazione, aggiornamento dati anagrafici, preferenze, storico attività. |
| **Amministrazione Flotta** | `BvUAwXmD.AACAQue` | `uml:Interface` | [INFERRED] Contratto per la gestione operativa della flotta: registrazione mezzi, pianificazione manutenzione, rilocazione, stato operativo. |
| **API Controllo** | `3P9eKnmD.AACAQlF` | `uml:Interface` | [INFERRED] Contratto per il controllo remoto dei dispositivi IoT a bordo veicolo: sblocco/blocco, lettura telemetria, invio comandi. |
| Class6 | `kf3Vv7mD.AACAQul` | `uml:Interface` | [INFERRED] Interfaccia placeholder non collegata. Probabile artefatto di modellazione incompleto. |
| ù | `DPxdv7mD.AACARBh` | `uml:Interface` | [INFERRED] Interfaccia con nome errato (typo). Probabile artefatto di modellazione. |
| _(vuoto)_ | `Dlp9v7mD.AACARGL` | `uml:Interface` | [INFERRED] Interfaccia senza nome. Artefatto orfano non referenziato da alcuna dipendenza o realizzazione. |
| Class14 | `58N9v7mD.AACARG5` | `uml:Interface` | [INFERRED] Interfaccia placeholder non collegata. Probabile artefatto di modellazione incompleto. |
| Class12 | `n.hDv7mD.AACARJ.` | `uml:Interface` | [INFERRED] Interfaccia placeholder non collegata. Probabile artefatto di modellazione incompleto. |

---

## 3. Use Case Logic & Flows

> **Nota:** Il diagramma dei componenti non contiene Use Case (`uml:UseCase`). Questa sezione è adattata per descrivere i **flussi architetturali** derivati dalle dipendenze `<<use>>` e dalle `InterfaceRealization` nel modello.

### Flusso: AppUtente → Gestione Corsa
- **Business Logic:** [INFERRED] L'AppUtente invoca l'interfaccia "Gestione Corsa" esposta dal Controller per avviare, monitorare e terminare una corsa. Il Controller orchestra l'interazione con il dispositivo IoT (tramite API Controllo) e il Model (tramite Gestione Dati) per tracciare lo stato della corsa.
- **Includes:** Nessuno
- **Extends:** Nessuno

### Flusso: AppUtente → Profilazione
- **Business Logic:** [INFERRED] L'AppUtente invoca l'interfaccia "Profilazione" per consentire all'utente di registrarsi, effettuare login, aggiornare il proprio profilo e gestire le preferenze personali.
- **Includes:** Nessuno
- **Extends:** Nessuno

### Flusso: AppOperatoreSC → Moderazione Utente
- **Business Logic:** [INFERRED] L'AppOperatoreSC invoca l'interfaccia "Moderazione Utente" per consentire all'operatore del servizio clienti di gestire segnalazioni, emettere sanzioni e amministrare lo stato degli account utente.
- **Includes:** Nessuno
- **Extends:** Nessuno

### Flusso: AppOperatoreTecnico → Amministrazione Flotta
- **Business Logic:** [INFERRED] L'AppOperatoreTecnico invoca l'interfaccia "Amministrazione Flotta" per gestire i veicoli: registrazione, manutenzione, rilocazione e aggiornamento dello stato operativo.
- **Includes:** Nessuno
- **Extends:** Nessuno

### Flusso: AppPA → Statistiche e Restrizioni
- **Business Logic:** [INFERRED] L'AppPA invoca l'interfaccia "Statistiche e Restrizioni" per accedere alla dashboard analitica e configurare le zone operative e le restrizioni geografiche del servizio.
- **Includes:** Nessuno
- **Extends:** Nessuno

### Flusso: RicercaMezzi → API Mappa
- **Business Logic:** [INFERRED] Il controller RicercaMezzi utilizza il ServizioMappa tramite l'interfaccia API Mappa per geolocalizzare i mezzi disponibili e calcolare le distanze dall'utente.
- **Includes:** Nessuno
- **Extends:** Nessuno

### Flusso: GestorePagamento → API Pagamento
- **Business Logic:** [INFERRED] Il controller GestorePagamento utilizza il Gateway Pagamento tramite l'interfaccia API Pagamento per processare le transazioni finanziarie (autorizzazione, cattura, rimborso).
- **Includes:** Nessuno
- **Extends:** Nessuno

### Flusso: GestioneCorsa → API Controllo
- **Business Logic:** [INFERRED] Il controller GestioneCorsa utilizza l'interfaccia API Controllo per comunicare con il dispositivo IoT a bordo veicolo: sblocco/blocco mezzo, lettura telemetria in tempo reale.
- **Includes:** Nessuno
- **Extends:** Nessuno

### Flusso: Controller → Gestione Dati (Model)
- **Business Logic:** [INFERRED] Il Controller utilizza l'interfaccia "Gestione Dati" esposta dal Model per eseguire operazioni CRUD sulle entità di dominio. Il Model astrae l'accesso al database.
- **Includes:** Nessuno
- **Extends:** Nessuno

### Flusso: Model → Connessione Dati (DBMS)
- **Business Logic:** [INFERRED] Il Model utilizza l'interfaccia "Connessione Dati" esposta dal DBMS per eseguire query, transazioni e operazioni di persistenza sui dati del sistema.
- **Includes:** Nessuno
- **Extends:** Nessuno

---

## 4. Architectural Relationships

### 4.1 Interface Realizations (Provided Interfaces)

| # | Componente | Interfaccia Realizzata | xmi:id Realizzazione | Spiegazione |
|---|---|---|---|---|
| 1 | **Controller** | **Moderazione Utente** | `QyGPv7mD.AACARbp` | [INFERRED] Il Controller implementa la logica di moderazione utenti, esponendo il servizio alle View. |
| 2 | **Controller** | **Gestione Corsa** | `sOrxKnmD.AACARF1` | [INFERRED] Il Controller implementa la logica del ciclo di vita della corsa. |
| 3 | **Controller** | **Statistiche e Restrizioni** | `BOXxKnmD.AACARGX` | [INFERRED] Il Controller implementa la logica di accesso statistiche e gestione restrizioni geografiche. |
| 4 | **Controller** | **Profilazione** | `wNx_QXmD.AACAQsf` | [INFERRED] Il Controller implementa la logica di gestione profilo utente. |
| 5 | **Controller** | **Amministrazione Flotta** | `7bagwXmD.AACAQvf` | [INFERRED] Il Controller implementa la logica di gestione operativa della flotta. |
| 6 | **ServizioMappa** | **API Mappa** | `3P59v7mD.AACARGh` | [INFERRED] Il ServizioMappa espone l'API di cartografia e geolocalizzazione. |
| 7 | **Gateway Pagamento** | **API Pagamento** | `ABW3v7mD.AACARaM` | [INFERRED] Il Gateway Pagamento espone l'API per l'elaborazione dei pagamenti. |
| 8 | **DBMS** | **Connessione Dati** | `0fJnv7mD.AACARXr` | [INFERRED] Il DBMS espone l'interfaccia di connessione per l'accesso ai dati. |
| 9 | **Model** | **Gestione Dati** | `rXEXv7mD.AACARYR` | [INFERRED] Il Model espone l'interfaccia di gestione dati (CRUD) al Controller. |

### 4.2 Dependencies (Required Interfaces / <<use>>)

| # | Client (Consumer) | Supplier (Provider) | xmi:id Dependency | Spiegazione |
|---|---|---|---|---|
| 1 | **RicercaMezzi** (Controller) | **API Mappa** (ServizioMappa) | `E9Ubv7mD.AACARQF` | [INFERRED] Il controller di ricerca mezzi consuma il servizio di geolocalizzazione per localizzare i veicoli disponibili su mappa. |
| 2 | **Model** | **Connessione Dati** (DBMS) | `t61nv7mD.AACARX_` | [INFERRED] Il Model dipende dal DBMS per la persistenza dei dati di dominio. |
| 3 | **Controller** | **Gestione Dati** (Model) | `vTcXv7mD.AACARYn` | [INFERRED] Il Controller consuma l'interfaccia dati del Model per accedere alle entità di business. |
| 4 | **GestorePagamento** (Controller) | **API Pagamento** (Gateway) | `O4J3v7mD.AACARay` | [INFERRED] Il gestore pagamenti consuma l'API del gateway per processare transazioni finanziarie. |
| 5 | **GestioneCorsa** (Controller) | **API Controllo** (Mezzo IoT) | `onA.KnmD.AACAQle` | [INFERRED] Il controller di gestione corsa consuma l'API di controllo del dispositivo IoT per sbloccare/bloccare il veicolo e ricevere telemetria. |
| 6 | **AppUtente** (View) | **Gestione Corsa** (Controller) | `AnIIhXmD.AACAQys` | [INFERRED] L'app utente consuma l'interfaccia di gestione corsa per avviare e gestire le corse. |
| 7 | **AppUtente** (View) | **Profilazione** (Controller) | `cl6IhXmD.AACAQzZ` | [INFERRED] L'app utente consuma l'interfaccia di profilazione per gestire il profilo personale. |
| 8 | **AppOperatoreSC** (View) | **Moderazione Utente** (Controller) | `dqTIhXmD.AACAQ0Z` | [INFERRED] L'app operatore servizio clienti consuma l'interfaccia di moderazione per gestire gli utenti. |
| 9 | **AppOperatoreTecnico** (View) | **Amministrazione Flotta** (Controller) | `6RnIhXmD.AACAQ0y` | [INFERRED] L'app operatore tecnico consuma l'interfaccia di amministrazione flotta per gestire i veicoli. |
| 10 | **AppPA** (View) | **Statistiche e Restrizioni** (Controller) | `nXuohXmD.AACAQ6g` | [INFERRED] L'app PA consuma l'interfaccia di statistiche e restrizioni per monitoraggio e governance. |

---

## 5. Architectural Constraints & Invariants

> **Nota:** Il modello XMI non contiene vincoli espliciti (`ownedRule`, `ownedConstraint`). I seguenti vincoli sono dedotti dalla topologia architetturale.

1. **[INFERRED] Pattern MVC Rigoroso:** L'architettura segue il pattern Model-View-Controller. Le View non accedono mai direttamente al Model o ai servizi esterni — tutte le comunicazioni passano attraverso le interfacce esposte dal Controller.

2. **[INFERRED] Separazione dei ruoli per applicazione:** Ogni attore del sistema ha un'applicazione View dedicata (AppUtente, AppOperatoreTecnico, AppPA, AppOperatoreSC). Ciascuna View consuma solo le interfacce pertinenti al proprio ruolo, garantendo il principio di minimo privilegio.

3. **[INFERRED] Accoppiamento tramite interfacce:** Tutti i componenti comunicano esclusivamente tramite interfacce (`uml:Interface`). Nessuna dipendenza diretta tra componenti concreti è presente, garantendo basso accoppiamento e alta sostituibilità.

4. **[INFERRED] Single Responsibility dei Controller interni:** Le classi interne al Controller (GestioneCorsa, RicercaMezzi, GestorePagamento) hanno ciascuna una singola responsabilità e una singola dipendenza verso un servizio esterno specifico.

5. **[INFERRED] Dipendenza unidirezionale View → Controller → Model → DBMS:** Il flusso delle dipendenze è strettamente unidirezionale. Non esistono dipendenze inverse (Model → Controller o Controller → View), rispettando il Dependency Inversion Principle.

6. **[INFERRED] Servizi esterni come componenti isolati:** ServizioMappa, Gateway Pagamento, DBMS e Mezzo IoT sono componenti esterni al dominio applicativo, ciascuno con una singola interfaccia di contratto. Questo garantisce la sostituibilità del provider senza impatto sull'architettura interna.

7. **[INFERRED] Vincolo di integrità IoT:** Il componente Mezzo:IoT è l'unico punto di interazione fisica con il veicolo. Tutte le operazioni hardware (sblocco, blocco, telemetria) devono transitare tramite l'interfaccia API Controllo, garantendo un unico punto di accesso controllato.

---

## Appendice A: Profilo UML Applicato

Il profilo **cofeecoders_profile** definisce i seguenti stereotipi:

| Stereotipo | Applica a | Descrizione |
|---|---|---|
| **«use»** | `uml:Usage` | [INFERRED] Marca le dipendenze di utilizzo tra componenti e interfacce. |
| **«Agent»** | `uml:Class` | [INFERRED] Classifica classi come agenti autonomi nel sistema (generalizza Participant). |
| **«Participant»** | `uml:Class` | [INFERRED] Classifica classi come partecipanti in interazioni del sistema. |
| **«boundary»** | `uml:Class` | [INFERRED] Classifica classi come elementi di confine (interfaccia con l'esterno). |
| **«entity»** | `uml:Class` | [INFERRED] Classifica classi come entità di dominio persistenti. |
| **«EntityBean»** | `uml:Class` | [INFERRED] Classifica classi come Entity Bean (pattern J2EE/JPA). |
| **«primitive»** | `uml:Class` | [INFERRED] Classifica classi come tipi primitivi di dominio. |
| **«Struct»** | `uml:Class` | [INFERRED] Classifica classi come strutture dati (value objects). |
| **«type»** | `uml:Class` | [INFERRED] Classifica classi come tipi astratti di dominio. |

> **Nota:** Il profilo definisce una gerarchia di generalizzazione: **Agent** generalizza **Participant**. Nessuna applicazione esplicita di stereotipi alle classi del modello è presente nel file XMI principale.

---

## Appendice B: Artefatti di Modellazione Non Significativi

Le seguenti interfacce sono state identificate come artefatti di modellazione incompleti o errati e **non devono essere considerate nell'implementazione**:

| Nome | xmi:id | Motivo Esclusione |
|---|---|---|
| Class6 | `kf3Vv7mD.AACAQul` | Nome placeholder, nessun collegamento nel modello. |
| ù | `DPxdv7mD.AACARBh` | Nome errato (typo), nessun collegamento nel modello. |
| _(vuoto)_ | `Dlp9v7mD.AACARGL` | Nome assente, nessun collegamento nel modello. |
| Class14 | `58N9v7mD.AACARG5` | Nome placeholder, nessun collegamento nel modello. |
| Class12 | `n.hDv7mD.AACARJ.` | Nome placeholder, nessun collegamento nel modello. |

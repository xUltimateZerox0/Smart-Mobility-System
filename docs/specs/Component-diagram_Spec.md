---
status: SUCCESS_WITH_INFERENCE
extractor_version: "2.0-unsafe"
parsed_entities: 61
---

# UML-XMI Semantic Extraction Report (Unsafe Mode)

> **Avvertenza**: Nessun elemento del modello XMI sorgente conteneva documentazione umana (`ownedComment`, `ownedRule`).
> Tutte le descrizioni contrassegnate con **[INFERRED]** sono state dedotte dal nome dell'entità, dal pattern architetturale e dalla topologia delle dipendenze.

---

## 1. System Boundaries & Actors

Il sistema è denominato **"Smart-Mobility-System"** e rappresenta una piattaforma di Smart Mobility per il noleggio e la gestione di mezzi di trasporto condivisi.

- **Utente**: [INFERRED] Utente finale del servizio di mobilità. Interagisce con il sistema tramite l'applicazione mobile/web dedicata (AppUtente). Può prenotare mezzi, avviare corse, effettuare pagamenti e gestire il proprio profilo.
- **Operatore**: [INFERRED] Operatore aziendale generico responsabile della gestione operativa del servizio.
  - **Operatore tecnico**: [INFERRED] Sottotipo di Operatore specializzato nella manutenzione e gestione tecnica dei mezzi fisici (riparazioni, ricariche, riposizionamento).
  - **Operatore Servizio Clienti**: [INFERRED] Sottotipo di Operatore dedicato all'assistenza clienti, gestione segnalazioni e moderazione degli utenti.
- **PA** (Pubblica Amministrazione): [INFERRED] Ente pubblico che interagisce con il sistema per consultare statistiche di utilizzo, definire restrizioni su zone geografiche e monitorare l'impatto del servizio sul territorio.

---

## 2. Core Entities & Components

### 2.1 Componenti Architetturali (Pattern MVC)

- **Model** (Tipo: Componente):
  - *Responsabilità*: [INFERRED] Layer di dominio del pattern MVC. Contiene tutte le entità di business del sistema e gestisce la persistenza dei dati. Realizza l'interfaccia «Gestione Dati» e consuma l'interfaccia «Connessione Dati» dal DBMS.
  - *Interfacce Realizzate*: Gestione Dati
  - *Entità contenute*: Corsa, Attore, Bicicletta, Automobile, Mezzo, Monopattino, Utente, PA, Operatore, OperatoreTecnico, OperatoreServizioClienti, Flotta, ZonaGeografica, MetodoPagamento, Prenotazione, Segnalazione

- **View** (Tipo: Componente):
  - *Responsabilità*: [INFERRED] Layer di presentazione del pattern MVC. Contiene i sotto-componenti che rappresentano le interfacce utente specifiche per ciascun tipo di attore del sistema.
  - *Sotto-componenti*:
    - **AppUtente** (Tipo: Componente): [INFERRED] Applicazione front-end destinata all'utente finale. Consuma le interfacce «Gestione Corsa» e «Profilazione» esposte dal Controller per consentire la prenotazione di mezzi, l'avvio/chiusura di corse e la gestione del profilo personale.
    - **AppOperatore** (Tipo: Componente): [INFERRED] Applicazione front-end destinata agli operatori. Consuma le interfacce «Moderazione Utente» e «Amministrazione Flotta» esposte dal Controller per gestire utenti, flotte e segnalazioni.
    - **AppPA** (Tipo: Componente): [INFERRED] Applicazione front-end destinata alla Pubblica Amministrazione. Consuma l'interfaccia «Statistiche e Restrizioni» esposta dal Controller per consultare dati aggregati e imporre vincoli territoriali.

- **Controller** (Tipo: Componente):
  - *Responsabilità*: [INFERRED] Layer di logica applicativa del pattern MVC. Orchesta le operazioni di business, media tra le View e il Model, e gestisce la validazione e il coordinamento delle transazioni.
  - *Interfacce Realizzate*: Moderazione Utente, Gestione Corsa, Statistiche e Restrizioni, Profilazione, Amministrazione Flotta
  - *Classi Controller contenute*:
    - **GestionePrenotazione** (Tipo: Classe): [INFERRED] Controller responsabile del ciclo di vita delle prenotazioni (creazione, conferma, annullamento, scadenza).
    - **GestioneUtenti** (Tipo: Classe): [INFERRED] Controller responsabile della registrazione, autenticazione, aggiornamento profilo e disattivazione degli utenti.
    - **GestioneStatistiche** (Tipo: Classe): [INFERRED] Controller responsabile dell'aggregazione e presentazione dei dati statistici di utilizzo del servizio.
    - **GestioneCorsa** (Tipo: Classe): [INFERRED] Controller responsabile del ciclo di vita della corsa (inizio, tracciamento, fine, calcolo costo). Consuma l'interfaccia «API Controllo» per comunicare con i dispositivi IoT dei mezzi.
    - **RicercaMezzi** (Tipo: Classe): [INFERRED] Controller responsabile della ricerca e localizzazione dei mezzi disponibili in una determinata zona. Consuma l'interfaccia «API Mappa» del ServizioMappa per la geolocalizzazione.
    - **GestorePagamento** (Tipo: Classe): [INFERRED] Controller responsabile dell'elaborazione dei pagamenti. Consuma l'interfaccia «API Pagamento» del Gateway Pagamento per delegare le transazioni finanziarie.
    - **GestioneFlotta** (Tipo: Classe): [INFERRED] Controller responsabile della gestione operativa della flotta veicoli (aggiunta, rimozione, stato, manutenzione).
    - **GestioneAree** (Tipo: Classe): [INFERRED] Controller responsabile della definizione e gestione delle zone geografiche operative (aree di parcheggio, zone vietate, confini del servizio).

### 2.2 Componenti Esterni / Infrastrutturali

- **ServizioMappa** (Tipo: Componente):
  - *Responsabilità*: [INFERRED] Servizio esterno di cartografia e geolocalizzazione (es. Google Maps, OpenStreetMap). Espone l'interfaccia «API Mappa» per fornire funzionalità di geocoding, routing e visualizzazione cartografica.
  - *Interfacce Realizzate*: API Mappa

- **Gateway Pagamento** (Tipo: Componente):
  - *Responsabilità*: [INFERRED] Servizio esterno di payment processing (es. Stripe, PayPal). Espone l'interfaccia «API Pagamento» per consentire addebiti, rimborsi e gestione dei metodi di pagamento.
  - *Interfacce Realizzate*: API Pagamento

- **DBMS** (Tipo: Componente):
  - *Responsabilità*: [INFERRED] Sistema di gestione database relazionale che fornisce la persistenza dei dati del sistema. Espone l'interfaccia «Connessione Dati» per le operazioni CRUD.
  - *Interfacce Realizzate*: Connessione Dati

- **Mezzo : IoT** (Tipo: Componente):
  - *Responsabilità*: [INFERRED] Dispositivo IoT fisico integrato nel mezzo di trasporto. Fornisce telemetria in tempo reale (posizione GPS, livello batteria, stato blocco/sblocco) e accetta comandi di controllo remoto tramite l'interfaccia «API Controllo».

### 2.3 Entità di Dominio (all'interno del componente Model)

- **Mezzo** (Tipo: Classe): [INFERRED] Classe base astratta che rappresenta un veicolo generico del servizio di sharing. Contiene proprietà comuni come identificativo, stato, posizione, livello batteria.
  - **Bicicletta** (Tipo: Classe): [INFERRED] Specializzazione di Mezzo che rappresenta una bicicletta condivisa (elettrica o tradizionale).
  - **Automobile** (Tipo: Classe): [INFERRED] Specializzazione di Mezzo che rappresenta un'automobile condivisa (car sharing).
  - **Monopattino** (Tipo: Classe): [INFERRED] Specializzazione di Mezzo che rappresenta un monopattino elettrico condiviso.
- **Attore** (Tipo: Classe): [INFERRED] Classe base per gli attori del sistema (Utente, Operatore, PA). Contiene proprietà comuni come credenziali, dati anagrafici.
- **Utente** (Tipo: Classe): [INFERRED] Entità di dominio che rappresenta l'utente registrato. Attributi attesi: nome, email, telefono, metodo di pagamento, storico corse.
- **PA** (Tipo: Classe): [INFERRED] Entità di dominio che rappresenta un ente di Pubblica Amministrazione registrato nel sistema.
- **Operatore** (Tipo: Classe): [INFERRED] Entità di dominio per l'operatore aziendale. Classe base per Operatore tecnico e Operatore Servizio Clienti.
  - **Operatore tecnico** (Tipo: Classe): [INFERRED] Specializzazione dell'Operatore per le attività di manutenzione.
  - **Operatore Servizio Clienti** (Tipo: Classe): [INFERRED] Specializzazione dell'Operatore per l'assistenza clienti.
- **Corsa** (Tipo: Classe): [INFERRED] Entità che rappresenta una sessione di utilizzo di un mezzo, con punto di partenza, destinazione, durata, costo e stato.
- **Prenotazione** (Tipo: Classe): [INFERRED] Entità che rappresenta la prenotazione anticipata di un mezzo da parte dell'utente, con orario, mezzo associato e stato (attiva, completata, annullata).
- **Flotta** (Tipo: Classe): [INFERRED] Aggregazione logica di mezzi gestita come unità operativa.
- **ZonaGeografica** (Tipo: Classe): [INFERRED] Entità che definisce un'area operativa del servizio con confini geografici.
- **MetodoPagamento** (Tipo: Classe): [INFERRED] Entità che rappresenta un metodo di pagamento associato ad un utente (carta di credito, wallet digitale, etc.).
- **Segnalazione** (Tipo: Classe): [INFERRED] Entità che rappresenta una segnalazione di problema (guasto mezzo, parcheggio scorretto, comportamento utente) inserita da un utente o operatore.

---

## 3. Use Case Logic & Flows

> **Nota**: Il diagramma analizzato è un Component Diagram, non un Use Case Diagram. Non sono presenti elementi `uml:UseCase`, `<include>` o `<extend>`. Di seguito vengono elencati i flussi operativi primari dedotti dalla topologia dei componenti.

### Flusso: Ricerca e Prenotazione Mezzo
- **Business Logic**: [INFERRED] L'utente accede ad AppUtente → richiede la lista dei mezzi disponibili tramite l'interfaccia «Profilazione» (per contestualizzare la ricerca al profilo) → il Controller delega a RicercaMezzi → RicercaMezzi interroga ServizioMappa via «API Mappa» per la geolocalizzazione → l'utente seleziona un mezzo → GestionePrenotazione crea la prenotazione → i dati vengono persistiti dal Model via «Gestione Dati» → il Model scrive nel DBMS via «Connessione Dati».
- **Includes**: Nessuno (non modellato esplicitamente)
- **Extends**: Nessuno (non modellato esplicitamente)

### Flusso: Gestione Corsa
- **Business Logic**: [INFERRED] L'utente avvia una corsa da AppUtente → la richiesta passa tramite l'interfaccia «Gestione Corsa» al Controller → GestioneCorsa invia comandi di sblocco al mezzo fisico via «API Controllo» (Mezzo : IoT) → durante la corsa, il mezzo invia telemetria → alla fine, GestioneCorsa calcola il costo → GestorePagamento addebita l'importo via «API Pagamento» (Gateway Pagamento) → i dati della corsa vengono persistiti nel Model/DBMS.
- **Includes**: Nessuno
- **Extends**: Nessuno

### Flusso: Pagamento
- **Business Logic**: [INFERRED] GestorePagamento (nel Controller) riceve la richiesta di addebito → invia la transazione al Gateway Pagamento tramite l'interfaccia «API Pagamento» → riceve esito (successo/fallimento) → aggiorna lo stato della Corsa/Prenotazione nel Model.
- **Includes**: Nessuno
- **Extends**: Nessuno

### Flusso: Moderazione Utente
- **Business Logic**: [INFERRED] L'Operatore Servizio Clienti accede ad AppOperatore → tramite l'interfaccia «Moderazione Utente» può visualizzare segnalazioni, sospendere o riattivare utenti → le modifiche vengono propagate al Model.
- **Includes**: Nessuno
- **Extends**: Nessuno

### Flusso: Amministrazione Flotta
- **Business Logic**: [INFERRED] L'Operatore (tecnico) accede ad AppOperatore → tramite l'interfaccia «Amministrazione Flotta» gestisce lo stato dei mezzi (disponibile, in manutenzione, fuori servizio) → GestioneFlotta nel Controller coordina le operazioni → aggiornamento persistito nel Model/DBMS.
- **Includes**: Nessuno
- **Extends**: Nessuno

### Flusso: Statistiche PA
- **Business Logic**: [INFERRED] La Pubblica Amministrazione accede ad AppPA → tramite l'interfaccia «Statistiche e Restrizioni» consulta dashboard con dati aggregati su utilizzo, distribuzione mezzi, zone critiche → GestioneStatistiche nel Controller produce i report → può anche definire restrizioni (zone vietate, limiti di velocità) che vengono applicate a GestioneAree.
- **Includes**: Nessuno
- **Extends**: Nessuno

---

## 4. Architectural Relationships

### 4.1 Interface Realizations (Contratti Esposti)

| Componente | Realizza Interfaccia | Semantica |
|------------|---------------------|-----------|
| Model | Gestione Dati | [INFERRED] Il Model espone operazioni CRUD sulle entità di dominio ai consumatori (Controller). |
| Controller | Moderazione Utente | [INFERRED] Il Controller espone funzionalità di ban/unban/segnalazione utenti alle View operatore. |
| Controller | Gestione Corsa | [INFERRED] Il Controller espone il ciclo di vita della corsa (start/stop/track) alle View utente. |
| Controller | Statistiche e Restrizioni | [INFERRED] Il Controller espone dati aggregati e gestione vincoli territoriali alla View PA. |
| Controller | Profilazione | [INFERRED] Il Controller espone operazioni di gestione profilo utente alle View utente. |
| Controller | Amministrazione Flotta | [INFERRED] Il Controller espone la gestione operativa della flotta alle View operatore. |
| ServizioMappa | API Mappa | [INFERRED] Il ServizioMappa espone API di geolocalizzazione, geocoding e rendering mappa. |
| Gateway Pagamento | API Pagamento | [INFERRED] Il Gateway Pagamento espone API di addebito, rimborso e verifica transazione. |
| DBMS | Connessione Dati | [INFERRED] Il DBMS espone un'interfaccia di accesso dati (SQL/ORM) per la persistenza. |

### 4.2 Dependencies (Relazioni «use»)

| Client | → Supplier (Interfaccia) | Semantica |
|--------|--------------------------|-----------|
| RicercaMezzi | → API Mappa | [INFERRED] RicercaMezzi interroga il ServizioMappa per ottenere la posizione dei mezzi su mappa e calcolare distanze dall'utente. |
| Model | → Connessione Dati | [INFERRED] Il componente Model consuma l'interfaccia del DBMS per leggere/scrivere le entità di dominio nel database. |
| Controller | → Gestione Dati | [INFERRED] Il Controller consuma l'interfaccia esposta dal Model per accedere e manipolare le entità di dominio (pattern MVC classico). |
| GestorePagamento | → API Pagamento | [INFERRED] GestorePagamento delega l'elaborazione finanziaria al Gateway Pagamento esterno tramite la sua API. |
| AppOperatore | → Moderazione Utente | [INFERRED] L'applicazione operatore consuma l'interfaccia di moderazione per gestire gli utenti problematici. |
| GestioneCorsa | → API Controllo | [INFERRED] GestioneCorsa invia comandi di lock/unlock e riceve telemetria dal dispositivo IoT del mezzo. |
| AppUtente | → Gestione Corsa | [INFERRED] L'applicazione utente consuma l'interfaccia di gestione corsa per avviare, monitorare e terminare le corse. |
| AppPA | → Statistiche e Restrizioni | [INFERRED] L'applicazione PA consuma l'interfaccia per visualizzare statistiche e configurare restrizioni territoriali. |
| AppUtente | → Profilazione | [INFERRED] L'applicazione utente consuma l'interfaccia di profilazione per gestire i dati personali, preferenze e storico. |
| AppOperatore | → Amministrazione Flotta | [INFERRED] L'applicazione operatore consuma l'interfaccia di amministrazione per gestire lo stato e la distribuzione della flotta. |

---

## 5. Architectural Constraints & Invariants

- [INFERRED] **Pattern MVC rigoroso**: L'architettura segue un pattern Model-View-Controller con separazione netta. Le View (App*) non accedono mai direttamente al Model o al DBMS; ogni comunicazione transita attraverso le interfacce esposte dal Controller.
- [INFERRED] **Dipendenza unidirezionale View → Controller → Model → DBMS**: Il flusso di dipendenza è strettamente top-down. Le View dipendono dal Controller tramite interfacce, il Controller dipende dal Model tramite «Gestione Dati», il Model dipende dal DBMS tramite «Connessione Dati». Non esistono dipendenze inverse.
- [INFERRED] **Servizi esterni disaccoppiati**: ServizioMappa, Gateway Pagamento e Mezzo : IoT sono componenti esterni al core MVC, accessibili esclusivamente tramite interfacce dedicate (API Mappa, API Pagamento, API Controllo), garantendo basso accoppiamento e sostituibilità.
- [INFERRED] **Segregazione delle interfacce per attore**: Ogni tipo di attore (Utente, Operatore, PA) possiede una View dedicata che consuma solo le interfacce pertinenti al proprio ruolo, implementando il principio di Interface Segregation (ISP).
- [INFERRED] **Componente IoT isolato**: Il dispositivo fisico (Mezzo : IoT) è accessibile solo dal Controller tramite «API Controllo», impedendo manipolazioni dirette da parte delle View o del Model.

---

## 6. Profile Stereotypes (dal file Profile)

Il profilo UML `cofeecoders_profile` definisce i seguenti stereotipi personalizzati applicabili alle classi del modello:

| Stereotipo | Estende | Semantica |
|------------|---------|-----------|
| `«use»` | `Usage` | [INFERRED] Marca le dipendenze di utilizzo tra componenti/classi. |
| `«Agent»` | `Class` (generalizza `Participant`) | [INFERRED] Identifica classi con comportamento attivo/autonomo nel sistema. |
| `«Participant»` | `Class` | [INFERRED] Identifica classi che partecipano a interazioni nel sistema. |
| `«boundary»` | `Class` | [INFERRED] Marca classi che fungono da interfaccia tra il sistema e gli attori esterni (confine del sistema). |
| `«entity»` | `Class` | [INFERRED] Marca classi che rappresentano entità di dominio persistenti. |
| `«Entity Bean»` | `Class` | [INFERRED] Marca classi entity in stile EJB/JPA per la persistenza automatica. |
| `«primitive»` | `Class` | [INFERRED] Marca classi che rappresentano tipi primitivi del dominio. |
| `«Struct»` | `Class` | [INFERRED] Marca classi che rappresentano strutture dati (value objects) senza logica di business. |
| `«type»` | `Class` | [INFERRED] Marca classi che rappresentano definizioni di tipo nel dominio. |

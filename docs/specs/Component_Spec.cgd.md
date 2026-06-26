---
clarity-gate-version: 2.1
document-type: Implementation
processed-by: AI — Component_Diagram_Spec.cgd.md v1.0, Class_Diagram_Spec.cgd.md v1.8, interface_Spec.cgd.md v1.0, Master_Spec.cgd.md v4.0
processed-date: 2026-06-25
clarity-status: CLEAR
hitl-status: REVIEWED
hitl-pending-count: 0
points-passed: 1-9
document-sha256: <PENDING>
hitl-claims: []
---

# Specifica delle Componenti — Smart Mobility System

**Versione:** 1.0
**Team:** Cofee Coders
**Progetto:** Ingegneria del Software a.a. 2025/2026
**Architettura:** MVC Web-oriented con Controller Intermediario
**Fonti:** Component_Diagram_Spec.cgd.md v1.0, Class_Diagram_Spec.cgd.md v1.8, interface_Spec.cgd.md v1.0, Master_Spec.cgd.md v4.0

**Pattern Architetturale:** Il sistema adotta MVC con Controller Intermediario Centralizzato. Le View sono progettate per essere disaccoppiate dal Model. Ogni interazione avviene tramite richieste esplicite ai Controller, che orchestrano il flusso e mediano tra View, Model e sistemi esterni *(Component_Diagram_Spec, documentazione.md §2.3)*.

---

## 1. Specifica Componente View

Componente che si occupa di visualizzare i dati all'utente e gestisce l'interazione fra quest'ultimo e l'infrastruttura sottostante. Le View non interrogano mai direttamente il Model — ogni comunicazione avviene tramite le interfacce dei Controller. ID XMI del Componente: `zzS2v7mD.AACAQYg`.

- **AppUtente**: è l'interfaccia principale per il cittadino fruitore dei servizi di sharing. Consente la ricerca dei mezzi nelle vicinanze (raggio base 2km, esteso 5km), la selezione del veicolo, la visualizzazione delle specifiche tecniche e dei costi, l'avvio della corsa tramite scansione QR code, la sospensione e la riattivazione della corsa, la terminazione della corsa, l'inserimento della destinazione per l'ottimizzazione del percorso, la gestione dei metodi di pagamento (inserimento dati carta, selezione metodo salvato) e la richiesta di logout. Realizza l'interfaccia View `Aggiornamenti Corsa` (ID XMI: `BGnPp3mD.AACARJJ`) per notificare al Controller lo stato della corsa. Dipende dalle interfacce Controller `Gestione Corsa` (ID XMI: `R.IxKnmD.AACAQ9w`) per le operazioni su corse, mezzi e prenotazioni.

- **AppOperatoreTecnico**: è la dashboard per l'operatore tecnico. Consente la visualizzazione dello stato della flotta, la richiesta dello stato di una flotta specifica, la selezione di un veicolo per visualizzarne i dettagli, e la richiesta di logout. Realizza l'interfaccia View `Stato Flotta` (ID XMI: `88Pfp3mD.AACARMt`) per ricevere dati aggiornati su posizione e stato dei veicoli. Dipende dall'interfaccia Controller `Amministrazione Flotta` (ID XMI: `BvUAwXmD.AACAQue`) per il controllo remoto e la diagnostica della flotta.

- **AppOperatoreSC**: è l'interfaccia per l'operatore del servizio clienti. Consente la visualizzazione dei report utente, la richiesta della lista delle prenotazioni, la selezione di una prenotazione specifica, l'aggiornamento del report di un utente e la richiesta di logout. Realizza l'interfaccia View `Eventi Utente` (ID XMI: `8v9fp3mD.AACARMc`) per ricevere notifiche relative a moderazione, prenotazioni e azioni correttive. Dipende dall'interfaccia Controller `Moderazione Utente` (ID XMI: `FSGPv7mD.AACARbj`) per le operazioni di moderazione account.

- **AppPA**: è l'interfaccia per la Pubblica Amministrazione. Consente la selezione di un intervallo di date per le statistiche, la richiesta dello stato della flotta, l'avvio di un intervento su una flotta, la selezione della mappa per la gestione delle zone geografiche, la modifica delle restrizioni geografiche, la conferma o il rifiuto della sovrascrittura delle restrizioni e la richiesta di logout. Realizza l'interfaccia View `Diagnostica` (ID XMI: `Xu9_p3mD.AACARM.`) per ricevere report diagnostici sullo stato dei mezzi e della flotta. Dipende dall'interfaccia Controller `Statistiche e Restrizioni` (ID XMI: `ie1hKnmD.AACAQ4x`) per le statistiche e la gestione delle restrizioni.

- **Autenticazione**: è l'interfaccia di pre-autenticazione per il login e la registrazione. Consente l'inserimento delle credenziali (nome, cognome, email, password, data di nascita) per la registrazione e l'invio dei dati per il login. Realizza l'interfaccia View `Stato Sessione` (ID XMI: `xgSAZ3mD.AACARNt`) per gestire lo stato della sessione attuale (autenticato, ruolo, permessi). Dipende dall'interfaccia Controller `Gestione Sessioni` (ID XMI: `2eaHp3mD.AACAQ9m`) per l'autenticazione e la gestione delle sessioni.

---

## 2. Specifica Componente Controller

Componente che riceve i comandi e i dati dell'utente attraverso la View ed esegue operazioni che possono alterare il Model e che portano ad un cambiamento di stato della View; orchestra il flusso MVC, valida le richieste e media tra View, Model e sistemi esterni. ID XMI del Componente: `3mq2v7mD.AACAQYr`.

- **GestioneAutenticazione**: gestisce la validazione delle credenziali, la registrazione degli utenti e le sessioni di autenticazione. Fornisce i metodi `verificaValidita()` per la validazione dei dati di registrazione, `invioCredenziali()` per il login e `inviaRichiestaLogout()` per la terminazione della sessione. Realizza le interfacce Controller `Gestione Sessioni` (ID XMI: `2eaHp3mD.AACAQ9m`) per Autenticazione View e contribuisce alle interfacce `Gestione Corsa`, `Moderazione Utente`, `Amministrazione Flotta` e `Statistiche e Restrizioni` per le altre View. Dipende dall'interfaccia Model `Gestione Dati Utente` (ID XMI: `.XEXv7mD.AACARYL`) per l'accesso ai dati anagrafici degli attori.

- **GestioneUtenti**: gestisce la moderazione degli account utente, inclusa la sospensione e la disattivazione. Fornisce i metodi `gestioneUtente()` per la modifica dello stato dell'account e `cercaReport()` per la consultazione dei report. Realizza l'interfaccia Controller `Moderazione Utente` (ID XMI: `FSGPv7mD.AACARbj`) per AppOperatoreSC. Dipende dall'interfaccia Model `Gestione Dati Utente` (ID XMI: `.XEXv7mD.AACARYL`) per l'accesso ai dati degli utenti.

- **RicercaMezzi**: gestisce le query geolocalizzate sui mezzi disponibili, con raggi multipli (raggio base 2km, raggio esteso 5km — default da UC.UT.01). Fornisce i metodi `visualizzaMezziVicini()` per la ricerca per coordinate e `visualizzaSpecifiche()` per i dettagli di un mezzo. Contribuisce all'interfaccia Controller `Gestione Corsa` (ID XMI: `R.IxKnmD.AACAQ9w`) per AppUtente. Dipende dal sistema esterno Servizio Mappa tramite l'interfaccia `API Mappa` (ID XMI: `xP59v7mD.AACARGb`) per le funzionalità di geolocalizzazione. Dipende dall'interfaccia Model `Gestione Dati Corsa` (ID XMI: `jYqrX3mD.AACAQux`) per l'accesso ai dati dei mezzi.

- **GestioneCorsa**: orchestra l'intero ciclo di vita della corsa: avvio, sospensione, riattivazione, terminazione, calcolo del percorso e stima dei costi. Fornisce i metodi `avviaCorsa()`, `terminaCorsa()`, `sospensioneCorsa()`, `controllaDisponibilita()` (con due overload: no-args e con parametro info), `aggiornaStima()`, `richiediSblocco()` tramite QR code, `richiediCalcoloPercorso()` delegando a Servizio Mappa esterno, e `acquisisciSceltaMetodo()` per il metodo di pagamento. Contribuisce all'interfaccia Controller `Gestione Corsa` (ID XMI: `R.IxKnmD.AACAQ9w`) per AppUtente. Dipende dal sistema esterno Servizio Mappa tramite `API Mappa` per il routing e dal sistema esterno Mezzo:IoT tramite `API Controllo` (ID XMI: `3P9eKnmD.AACAQlF`) per il blocco/sblocco fisico del veicolo. Dipende dall'interfaccia Model `Gestione Dati Corsa` (ID XMI: `jYqrX3mD.AACAQux`) per l'accesso ai dati di corsa, mezzi e pagamenti. È in relazione di associazione con Mezzo (1..* a 0..*) e Corsa (0..* a 1).

- **GestorePagamento**: elabora le transazioni economiche e valida i metodi di pagamento, delegando al sistema esterno Gateway Pagamento. Fornisce i metodi `pagamentoCorsa()` per processare il pagamento di una corsa, `elaboraDatiCarta()` per la validazione dei dati di una nuova carta e `recuperaMetodiSalvati()` per ottenere i metodi di pagamento pre-esistenti. Contribuisce all'interfaccia Controller `Gestione Corsa` (ID XMI: `R.IxKnmD.AACAQ9w`) per AppUtente. Dipende dal sistema esterno Gateway Pagamento tramite l'interfaccia `API Pagamento` (ID XMI: `Z.W3v7mD.AACARaG`) per la convalida carte e l'elaborazione transazioni. Dipende dall'interfaccia Model `Gestione Dati Corsa` (ID XMI: `jYqrX3mD.AACAQux`) per l'accesso ai dati dei metodi di pagamento.

- **GestioneFlotta**: monitora e controlla la flotta di veicoli da remoto. Fornisce i metodi `analisiStatoFlotta()` per rilevare mezzi da manutenere (crea Segnalazione e imposta Mezzo.stato a 'manutenzione'), `bloccaMezzo()` per il blocco remoto, `avviaManutenzione()` per avviare la manutenzione su una flotta e `getCondizioniMezzi()` per la visualizzazione dashboard dello stato della flotta. Realizza l'interfaccia Controller `Amministrazione Flotta` (ID XMI: `BvUAwXmD.AACAQue`) per AppOperatoreTecnico e contribuisce all'interfaccia `Statistiche e Restrizioni` (ID XMI: `ie1hKnmD.AACAQ4x`) per AppPA. Dipende dall'interfaccia Model `Gestione Dati Corsa` per i dati dei mezzi e dall'interfaccia `Gestione Dati Supporto` (ID XMI: `wpvLX3mD.AACAQuK`) per la creazione e consultazione delle segnalazioni. È in relazione di associazione con Mezzo (0..* a 0..*) e Segnalazione (0..* a 1).

- **GestionePrenotazione**: gestisce il ciclo di vita delle prenotazioni con timeout automatico a 15 minuti (default da UC.UT.02). Fornisce i metodi `inviaRichiestaPrenotazione()` per prenotare un mezzo, `richiediLista()` per ottenere la lista delle prenotazioni, `annullaPrenotazione()` per cancellare una prenotazione, `gestisciTimeout()` per l'annullamento automatico allo scadere dei 15 minuti, `notificaScadenzaTempo()` per notificare la scadenza e `concludiPrenotazione()` per chiudere la prenotazione all'avvio della corsa. Contribuisce alle interfacce Controller `Gestione Corsa` (per AppUtente) e `Moderazione Utente` (per AppOperatoreSC). Dipende dall'interfaccia Model `Gestione Dati Supporto` (ID XMI: `wpvLX3mD.AACAQuK`) per l'accesso ai dati di prenotazioni e segnalazioni. È in relazione di associazione con Mezzo (1..* a 0..*), Prenotazione (0..* a 1) e Segnalazione (0..* a 1).

- **GestioneStatistiche**: aggrega i dati delle corse e genera report statistici per la Pubblica Amministrazione. Fornisce i metodi `analisiTratte()` per l'analisi delle corse in un intervallo di date e `generaFileStatistiche()` per la generazione del file statistico. Contribuisce all'interfaccia Controller `Statistiche e Restrizioni` (ID XMI: `ie1hKnmD.AACAQ4x`) per AppPA. Dipende dall'interfaccia Model `Gestione Dati Corsa` (ID XMI: `jYqrX3mD.AACAQux`) per i dati delle corse e dall'interfaccia `Gestione Dati Supporto` (ID XMI: `wpvLX3mD.AACAQuK`) per i dati dei transiti. È in relazione di associazione con Corsa (0..* a 0..*) e Transito (0..* a 0..*).

- **GestioneAree**: esegue operazioni CRUD sulle zone geografiche e verifica i conflitti tra restrizioni sovrapposte. Fornisce i metodi `aggiornaRestrizione()` per modificare una zona, `analisiConflitti()` per verificare sovrapposizioni tra zone e `getZoneGeografiche()` per ottenere tutte le zone. Contribuisce all'interfaccia Controller `Statistiche e Restrizioni` (ID XMI: `ie1hKnmD.AACAQ4x`) per AppPA. Dipende dall'interfaccia Model `Gestione Dati Supporto` (ID XMI: `wpvLX3mD.AACAQuK`) per l'accesso ai dati delle zone geografiche. È in relazione di associazione con ZonaGeografica (0..* a 0..*).

---

## 3. Specifica Componente Model

Componente che contiene le entità core del sistema e i metodi di accesso ai dati. Ricopre un ruolo passivo — espone metodi per accesso e modifica dello stato richiesti dai Controller, ma è totalmente privo di logiche di notifica verso l'esterno *(Component_Diagram_Spec §3)*. ID XMI del Componente: `YKY2v7mD.AACAQYD`. Tutte le entità dipendono da DBMS per la persistenza tramite l'interfaccia `Connessione Dati` (ID XMI: `jvJnv7mD.AACARXl`).

### 3.1 DominioAttori

Racchiude le entità del sistema che modellano gli attori del dominio. Mappa all'interfaccia Model `Gestione Dati Utente` (ID XMI: `.XEXv7mD.AACARYL`) consumata dai Controller GestioneAutenticazione e GestioneUtenti.

- **Attore**: classe base astratta che modella le credenziali di accesso (email, password cifrata) e il ruolo dell'attore (RuoloAttore: Utente, Operatore, PA). Utilizza strategia JOINED inheritance per la persistenza. È in relazione di generalizzazione con Utente, Operatore e PA.

- **Utente**: modella il cittadino fruitore dei servizi di sharing. Contiene i dati anagrafici (nomeUtente, cognomeUtente, telefono), la posizione corrente (coordinateUtente), lo stato dell'account (StatoUtente: attivo, sospeso, disattivato), il report di moderazione (reportUtente) e il contatore dei mezzi prenotati (numMezziPrenotati). Eredita email, password, id e ruolo da Attore. Fornisce metodi per la ricerca (`ricercaUtente`), l'azione correttiva sulla moderazione (`azioneCorrettiva`) e la creazione dell'account (`creaAccountUtente`).

- **Operatore**: modella il personale professionale del servizio, distinto in Tecnico o Servizio Clienti tramite l'enum TipoOperatore. La distinzione è data dal valore dell'enum `tipo`, non da classi separate. Eredita email, password, id e ruolo da Attore.

- **PA**: modella la Pubblica Amministrazione (ente comunale) con privilegi di analisi, statistiche e gestione delle restrizioni geografiche. L'attributo `idPA` coincide con `Attore.id`. Eredita email, password, id e ruolo da Attore.

### 3.2 DominioCorsa

Racchiude le entità del sistema che modellano le corse, i veicoli e i pagamenti. Mappa all'interfaccia Model `Gestione Dati Corsa` (ID XMI: `jYqrX3mD.AACAQux`) consumata dai Controller GestioneCorsa, GestorePagamento, RicercaMezzi, GestioneFlotta e GestioneStatistiche.

- **Mezzo**: modella il veicolo della flotta (bicicletta, scooter, auto). Traccia lo stato (StatoMezzo: disponibile, prenotato, in_uso, sospeso, bloccato, manutenzione), la posizione (coordinateMezzo), l'autonomia residua, il costo orario, la velocità massima, la condizione fisica, il tipo, l'identificativo della flotta (idFlotta) e il tempo di disponibilità. Fornisce metodi di interrogazione avanzati come la ricerca per flotta (`getMezzibyFlotta`), la ricerca geospaziale (`getMezziInArea`) e l'ottenimento dei dettagli completi (`getDettagliMezzo`). È in relazione di associazione 1 a Molti con Corsa.

- **Corsa**: modella la sessione di utilizzo di un mezzo dall'avvio al termine. Include il costo totale, gli orari di inizio e fine, le coordinate di partenza e arrivo, e le foreign key verso MetodoPagamento e Utente. Fornisce metodi per la creazione (`creaCorsa`), la ricerca per ID (`ricercaCorsa`), l'interrogazione per periodo (`getCorseByPeriodo`) e l'aggiornamento del costo (`aggiornaCosto`). È in relazione di associazione con Mezzo (0..* a 1).

- **MetodoPagamento**: modella i dati cifrati della carta di credito/debito associata a un utente. Utilizza chiave surrogata `idMetodoPagamento` come PK (pattern PCI-DSS). Contiene il numero carta (cifrato) e l'intestatario. Fornisce metodi per la creazione (`creaMetodoPagamento`), la verifica di esistenza (`controllaMetodoEsistente`) e la ricerca per utente (`getMetodoByUtente`).

### 3.3 DominioSupporto

Racchiude le entità del sistema che modellano i dati di supporto: prenotazioni, segnalazioni, zone geografiche e transiti. Mappa all'interfaccia Model `Gestione Dati Supporto` (ID XMI: `wpvLX3mD.AACAQuK`) consumata dai Controller GestioneFlotta, GestionePrenotazione, GestioneAree e GestioneStatistiche.

- **Prenotazione**: modella il blocco temporaneo di un mezzo, con timeout automatico a 15 minuti (default da UC.UT.02). Traccia lo stato (StatoPrenotazione: attiva, scaduta, annullata, completata), l'orario di inizio, la data, e le foreign key verso Utente e Mezzo. Fornisce metodi per la creazione (`creaPrenotazione`) e la ricerca per stato (`getPrenotazioneByStato`).

- **Segnalazione**: modella il report di anomalia su un mezzo (guasto, manutenzione, veicolo non raggiungibile). Traccia lo stato (StatoSegnalazione: aperta, in_lavorazione, chiusa), l'ora, la data e la foreign key verso Mezzo. Fornisce il metodo di creazione (`creaSegnalazione`).

- **ZonaGeografica**: modella un'area geografica con restrizioni di circolazione o sosta (TipoRestrizione: divieto_parcheggio, ZTL, limite_velocita). Contiene il tipo di restrizione, le note descrittive e la geometria della zona (LineString). Fornisce metodi per la verifica di sovrapposizioni (`verificaSovrapposizioni`), il controllo di appartenenza di una coordinata all'area (`checkArea` — utilizzato per il vincolo architetturale AP.04), la creazione (`creaZonaGeografica`), l'interrogazione delle restrizioni per coordinate (`getRestrizioniZona`) e il salvataggio (`salvaRestrizioni`).

- **Transito**: modella l'associazione M:N tra Corsa e ZonaGeografica per tracciare le zone attraversate durante una corsa. Contiene le foreign key verso Corsa (`idCorsa`) e ZonaGeografica (`idArea`). Fornisce il metodo per ottenere i transiti di una corsa (`getTransitiByCorsa`).

---

## 4. Specifica Componente Sistemi Esterni

Tutti i sistemi esterni sono simulati (progetto universitario — chiarimenti-vari.md punto 16) *(Component_Diagram_Spec §4)*.

### 4.1 Servizio Mappa

Componente che fornisce servizi di geolocalizzazione e routing esterno per il calcolo di percorsi tra coordinate. ID XMI: `ozG9v7mD.AACARFZ`.
- **Descrizione**: calcola il percorso ottimale tra coordinate iniziali e coordinate finali, tenendo conto delle restrizioni geografiche delle zone attraversate. Realizza l'interfaccia `API Mappa` (ID XMI: `xP59v7mD.AACARGb`).
- **Metodo esposto**: `getPercorso(coordinateIniziali, coordinateFinali, restrizioni)` — restituisce i dati del percorso calcolato. Il parametro `coordinateFinali` corregge il typo XMI `coorfinateFinali` (da chiarimenti-vari.md punto 14).
- **Consumatori**: RicercaMezzi (per la geolocalizzazione dei mezzi vicini) e GestioneCorsa (per il calcolo del percorso ottimale — `richiediCalcoloPercorso()`).

### 4.2 Gateway Pagamento

Componente che processa i pagamenti e convalida le carte di credito/debito come processore di pagamento esterno. ID XMI: `77O9v7mD.AACARFq`.
- **Descrizione**: convalida i dati della carta presso il circuito esterno e processa la transazione economica per la corsa. Realizza l'interfaccia `API Pagamento` (ID XMI: `Z.W3v7mD.AACARaG`).
- **Metodi esposti**: `effettuaPagamento(idMetodoPagamento, idCorsa)` per processare il pagamento (correzione typo XMI da EffettuaPagamento) e `convalidaCarta(numCarta, dsCarta, cvv, intestatarioCarta)` per la validazione dei dati della carta.
- **Consumatore**: GestorePagamento (Controller).

### 4.3 Mezzo : IoT

Componente che fornisce l'interfaccia fisica col veicolo per le operazioni di blocco e sblocco remoto. ID XMI: `wksuKnmD.AACAQj5`.
- **Descrizione**: interfaccia di controllo del veicolo per operazioni fisiche remote. L'interfaccia `API Controllo` (ID XMI: `3P9eKnmD.AACAQlF`) è definita ma senza realization collegato a Mezzo:IoT nell'XMI del diagramma componenti (orphan XMI — probabile limite di esportazione Visual Paradigm). Nonostante lo stato orphan, l'interfaccia è considerata funzionale *(Component_Diagram_Spec §4.4)*.
- **Metodi esposti**: `bloccoMezzoFisico(idMezzo)` per bloccare il veicolo e `sbloccoMezzoFisico(idMezzo)` per sbloccarlo.
- **Consumatore**: GestioneCorsa (Controller) — utilizzato per lo sblocco tramite QR code all'avvio corsa e per il blocco in sospensione/termine corsa.

### 4.4 DBMS

Componente che ha la responsabilità di rendere persistenti i dati immagazzinandoli in un database relazionale. ID XMI: `mqGDv7mD.AACARJV`.
- **Descrizione**: fornisce operazioni CRUD standard (Create, Read, Update, Delete) verso il database relazionale sottostante. Realizza l'interfaccia `Connessione Dati` (ID XMI: `jvJnv7mD.AACARXl`). Le classi Model dipendono da DBMS per la persistenza: Attore, Corsa, MetodoPagamento, Mezzo, Prenotazione, Segnalazione, Transito, ZonaGeografica.
- **Design**: interfaccia CRUD generica (da Master_Spec §5, claim-4c8a2f023). I metodi `getIdDBMS()` e `setIdDBMS(id)` presenti nell'XMI sono inclusi per completezza di tracciabilità. Le operazioni specifiche sono implementate dalle classi Model che chiamano il DBMS per la persistenza.
- **Consumatore**: Model (tutte le entità).

---

## 5. Interfacce Orphan (Artefatti XMI)

Le seguenti interfacce sono presenti nell'XMI del diagramma componenti ma non hanno un componente che le realizza. Sono considerate artefatti di esportazione Visual Paradigm *(Component_Diagram_Spec §5)*.

| Interfaccia | ID XMI | Note |
|-------------|--------|------|
| `Class6` | `kf3Vv7mD.AACAQul` | Nome generico — artefatto XMI |
| `Class14` | `58N9v7mD.AACARG5` | Nome generico — artefatto XMI |
| `Class12` | `n.hDv7mD.AACARJ.` | Nome generico — artefatto XMI |
| `ù` | `DPxdv7mD.AACARBh` | Carattere anomalo — artefatto XMI |
| ` ` (vuoto) | `Dlp9v7mD.AACARGL` | Nome vuoto — artefatto XMI |

---

## 6. Schema delle Dipendenze tra Componenti

```
┌─────────────────────────────┐         ┌─────────────────────────────┐
│   VIEW COMPONENT            │         │   CONTROLLER COMPONENT      │
│  ┌───────────────────────┐  │  5 I/F  │  ┌───────────────────────┐  │
│  │ AppUtente             │──┼──────┬──┼──│ GestioneAutenticazione│  │
│  │ AppOperatoreTecnico   │  │      │  │  │ GestioneUtenti        │  │
│  │ AppOperatoreSC        │  │      │  │  │ RicercaMezzi          │  │
│  │ AppPA                 │  │      │  │  │ GestioneCorsa         │  │
│  │ Autenticazione        │  │      │  │  │ GestorePagamento      │  │
│  └───────────────────────┘  │      │  │  │ GestioneFlotta        │  │
│           ▲                 │      │  │  │ GestionePrenotazione  │  │
│           │ 5 I/F View      │      │  │  │ GestioneStatistiche   │  │
│           └─────────────────┼──────┘  │  │ GestioneAree          │  │
└─────────────────────────────┘         │  └───────────────────────┘  │
                                        │           ▲                │
                                        │           │ 2 I/F Model     │
                                        │           ▼                │
                                        │  ┌─────────────────────┐  │
                                        │  │ MODEL COMPONENT     │  │
                                        │  │  DominioAttori      │  │
                                        │  │  DominioCorsa       │  │
                                        │  │  DominioSupporto    │  │
                                        │  └─────────┬───────────┘  │
                                        │            │               │
                                        └────────────┼───────────────┘
                                                     │ 1 dip. (CRUD)
                                                     ▼
                                        ┌─────────────────────────┐
                                        │  DBMS (Connessione Dati)│
                                        └─────────────────────────┘
                                                     ▲
                                        ┌────────────┴───────────────┐
                                        │  ALTRI SISTEMI ESTERNI    │
                                        │  Servizio Mappa           │
                                        │  Gateway Pagamento        │
                                        │  Mezzo : IoT              │
                                        └────────────────────────────┘
```

---

## 7. Tabella Riepilogativa delle Dipendenze

| # | Cliente | Tipo | Fornitore | Tipo | ID XMI Fornitore | Fonte |
|---|---|---|---|---|---|---|
| 1 | RicercaMezzi | Controller | API Mappa | External Interface | `xP59v7mD.AACARGb` | Component_Diagram_Spec §7 |
| 2 | GestorePagamento | Controller | API Pagamento | External Interface | `Z.W3v7mD.AACARaG` | Component_Diagram_Spec §7 |
| 3 | GestioneCorsa | Controller | API Controllo | External Interface | `3P9eKnmD.AACAQlF` | Component_Diagram_Spec §7 |
| 4 | Model | Component | Connessione Dati | External Interface | `jvJnv7mD.AACARXl` | Component_Diagram_Spec §7 |
| 5 | Controller | Component | Gestione Dati Utente | Model Interface | `.XEXv7mD.AACARYL` | Component_Diagram_Spec §7 |
| 6 | Controller | Component | Gestione Dati Supporto | Model Interface | `wpvLX3mD.AACAQuK` | Component_Diagram_Spec §7 |
| 7 | Controller | Component | Gestione Dati Corsa | Model Interface | `jYqrX3mD.AACAQux` | Component_Diagram_Spec §7 |
| 8 | Controller | Component | Aggiornamenti Corsa | View Interface | `BGnPp3mD.AACARJJ` | Component_Diagram_Spec §7 |
| 9 | Controller | Component | Eventi Utente | View Interface | `8v9fp3mD.AACARMc` | Component_Diagram_Spec §7 |
| 10 | Controller | Component | Stato Flotta | View Interface | `88Pfp3mD.AACARMt` | Component_Diagram_Spec §7 |
| 11 | Controller | Component | Diagnostica | View Interface | `Xu9_p3mD.AACARM.` | Component_Diagram_Spec §7 |
| 12 | Controller | Component | Stato Sessione | View Interface | `xgSAZ3mD.AACARNt` | Component_Diagram_Spec §7 |
| 13 | AppUtente | View Class | Gestione Corsa | Controller Interface | `R.IxKnmD.AACAQ9w` | Component_Diagram_Spec §7 |
| 14 | AppOperatoreSC | View Class | Moderazione Utente | Controller Interface | `FSGPv7mD.AACARbj` | Component_Diagram_Spec §7 |
| 15 | AppOperatoreTecnico | View Class | Amministrazione Flotta | Controller Interface | `BvUAwXmD.AACAQue` | Component_Diagram_Spec §7 |
| 16 | AppPA | View Class | Statistiche e Restrizioni | Controller Interface | `ie1hKnmD.AACAQ4x` | Component_Diagram_Spec §7 |
| 17 | Autenticazione | View Class | Gestione Sessioni | Controller Interface | `2eaHp3mD.AACAQ9m` | Component_Diagram_Spec §7 |

---

## 8. Metadati Documento

| Metrica | Valore | Note |
|---------|--------|------|
| Componenti architetturali | 7 | View, Controller, Model, ServizioMappa, Gateway Pagamento, DBMS, Mezzo:IoT |
| Classi nel Componente View | 5 | AppUtente, AppOperatoreTecnico, AppOperatoreSC, AppPA, Autenticazione |
| Classi nel Componente Controller | 9 | GestioneAutenticazione, GestioneUtenti, RicercaMezzi, GestioneCorsa, GestorePagamento, GestioneFlotta, GestionePrenotazione, GestioneStatistiche, GestioneAree |
| Classi nel Componente Model | 11 | Attore, Utente, Operatore, PA, Mezzo, Corsa, MetodoPagamento, Prenotazione, Segnalazione, ZonaGeografica, Transito |
| Sotto-domini Model | 3 | DominioAttori (4), DominioCorsa (3), DominioSupporto (4) |
| Sistemi Esterni | 4 | Servizio Mappa, Gateway Pagamento, Mezzo : IoT, DBMS |
| Interfacce funzionali | 17 | 5 View + 5 Controller + 3 Model + 4 External |
| Interfacce Orphan (artefatti XMI) | 5 | Class6, Class14, Class12, ù, vuoto |
| Dipendenze totali tracciate | 17 | Component_Diagram_Spec §7 |

---

## 9. Cross-Reference Matrix: Componente → Fonte

| Componente / Classe | Component_Diagram_Spec | Class_Diagram_Spec | interface_Spec | Master_Spec |
|---|---|---|---|---|
| **Componente View** | §1 | §3 | §1 | §4 |
| AppUtente | §1.1 | §3.1 | §1.1, §2.1 | §4 |
| AppOperatoreTecnico | §1.1 | §3.2 | §1.2, §2.3 | §4 |
| AppOperatoreSC | §1.1 | §3.3 | §1.4, §2.2 | §4 |
| AppPA | §1.1 | §3.4 | §1.3, §2.4 | §4 |
| Autenticazione | §1.1 | §3.5 | §1.5, §2.5 | §4 |
| **Componente Controller** | §2 | §2 | §2 | §3 |
| GestioneAutenticazione | §2.1 | §2.1 | §2.1, §2.2, §2.3, §2.4, §2.5 | §3 |
| GestioneUtenti | §2.1 | §2.2 | §2.2 | §3 |
| RicercaMezzi | §2.1 | §2.3 | §2.1 | §3 |
| GestioneCorsa | §2.1 | §2.4 | §2.1 | §3 |
| GestorePagamento | §2.1 | §2.5 | §2.1 | §3 |
| GestioneFlotta | §2.1 | §2.6 | §2.3, §2.4 | §3 |
| GestionePrenotazione | §2.1 | §2.7 | §2.1, §2.2 | §3 |
| GestioneStatistiche | §2.1 | §2.8 | §2.4 | §3 |
| GestioneAree | §2.1 | §2.9 | §2.4 | §3 |
| **Componente Model** | §3 | §1 | §3 | §2 |
| DominioAttori | §3.2 (Gestione Dati Utente) | §1.1–§1.4 | §3.1 | §2 |
| DominioCorsa | §3.2 (Gestione Dati Corsa) | §1.5–§1.7 | §3.3 | §2 |
| DominioSupporto | §3.2 (Gestione Dati Supporto) | §1.8–§1.11 | §3.2 | §2 |
| **Sistemi Esterni** | §4 | §4 | §4 | §5 |
| Servizio Mappa | §4.1 | §4.3 | §4.1 | §5 |
| Gateway Pagamento | §4.2 | §4.2 | §4.2 | §5 |
| Mezzo : IoT | §4.4 | §4.1 | §4.4 | §5 |
| DBMS | §4.3 | §4.4 | §4.3 | §5 |
| Interfacce Orphan | §5 | — | — | — |

---

## HITL Verification Record

### Round A: Derived Data Confirmation

Tutti i dati descrittivi delle componenti sono stati derivati dalle quattro fonti primarie:
- **Componente View (§1):** Ruoli, responsabilità e interfacce da Component_Diagram_Spec §1, Class_Diagram_Spec §3, interface_Spec §1
- **Componente Controller (§2):** Comportamenti e orchestrazione da Component_Diagram_Spec §2, Class_Diagram_Spec §2, interface_Spec §2
- **Componente Model (§3):** Entità di business raggruppate per dominio da Component_Diagram_Spec §3, Master_Spec §2, interface_Spec §3
- **Componente Sistemi Esterni (§4):** Ruoli infrastrutturali da Component_Diagram_Spec §4, Class_Diagram_Spec §4, interface_Spec §4, Master_Spec §5
- **Dipendenze (§7):** Tutte e 17 le dipendenze tracciate da Component_Diagram_Spec §7 e interface_Spec §5

### Round B: True HITL Verification

Nessun claim richiede Round B — documento descrittivo derivato esclusivamente da fonti già verificate e cross-referenziate.

---

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | REVIEWED

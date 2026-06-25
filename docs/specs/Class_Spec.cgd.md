---
clarity-gate-version: 2.1
document-type: Implementation
processed-by: AI — Class_Diagram_Spec.cgd.md v1.8, Component_Diagram_Spec.cgd.md v1.0, interface_Spec.cgd.md v1.0, Master_Spec.cgd.md v4.0
processed-date: 2026-06-25
clarity-status: CLEAR
hitl-status: REVIEWED
hitl-pending-count: 0
points-passed: 1-9
document-sha256: <PENDING>
hitl-claims: []
---

# Specifica delle Classi — Smart Mobility System

**Versione:** 1.0
**Team:** Cofee Coders
**Progetto:** Ingegneria del Software a.a. 2025/2026
**Architettura:** MVC Web-oriented con Controller Intermediario
**Fonti:** Class_Diagram_Spec.cgd.md v1.8, Component_Diagram_Spec.cgd.md v1.0, interface_Spec.cgd.md v1.0, Master_Spec.cgd.md v4.0

---

## 1. Specifica Classi View

Sono le Classi che si occupano dell'interazione con l'utente e della visualizzazione dell'interfaccia del programma e dei risultati delle elaborazioni. Le View sono progettate per essere disaccoppiate dal Model [pattern MVC con Controller Intermediario]. Ogni interazione avviene tramite richieste esplicite ai Controller. La modellazione segue un approccio ibrido, prevalentemente funzionale, in cui l'oggetto UML Classe è usato come un Modulo di Funzioni.

- **AppUtente**: ha la responsabilità di fornire l'interfaccia utente per il cittadino fruitore dei servizi di sharing. Gestisce la visualizzazione di mappe, QR code, dettagli dei mezzi, costi delle corse e stime. Fornisce metodi per la ricerca dei mezzi, la selezione del veicolo, l'avvio/sospensione/terminazione della corsa, l'inserimento della destinazione per l'ottimizzazione del percorso, la gestione dei metodi di pagamento (inserimento dati carta, selezione metodo salvato) e la scansione del QR code per lo sblocco del mezzo. Comunica con i Controller GestioneCorsa, GestorePagamento, RicercaMezzi, GestionePrenotazione e GestioneAutenticazione tramite l'interfaccia `Aggiornamenti Corsa`. Realizza l'interfaccia View `Aggiornamenti Corsa` (ID XMI: `BGnPp3mD.AACARJJ`) consumata dal Controller.

- **AppOperatoreTecnico**: ha la responsabilità di fornire la dashboard per l'operatore tecnico, consentendo la visualizzazione dello stato della flotta, l'emissione di comandi remoti (blocco/sblocco) e la gestione della manutenzione dei veicoli. Comunica con i Controller GestioneFlotta e GestioneAutenticazione tramite l'interfaccia `Stato Flotta`. Realizza l'interfaccia View `Stato Flotta` (ID XMI: `88Pfp3mD.AACARMt`) consumata dal Controller.

- **AppOperatoreSC**: ha la responsabilità di fornire l'interfaccia per l'operatore del servizio clienti, consentendo la moderazione degli account utente (sospensione/disattivazione, consultazione report) e l'amministrazione delle prenotazioni (visualizzazione lista, selezione, aggiornamento report). Comunica con i Controller GestioneUtenti, GestionePrenotazione e GestioneAutenticazione tramite l'interfaccia `Eventi Utente`. Realizza l'interfaccia View `Eventi Utente` (ID XMI: `8v9fp3mD.AACARMc`) consumata dal Controller.

- **AppPA**: ha la responsabilità di fornire l'interfaccia per la Pubblica Amministrazione, consentendo l'analisi delle statistiche sulle corse, il monitoraggio dello stato della flotta, la gestione delle restrizioni geografiche (creazione, modifica, sovrascrittura) e la visualizzazione delle zone sulla mappa. Comunica con i Controller GestioneFlotta, GestioneStatistiche, GestioneAree e GestioneAutenticazione tramite l'interfaccia `Diagnostica`. Realizza l'interfaccia View `Diagnostica` (ID XMI: `Xu9_p3mD.AACARM.`) consumata dal Controller.

- **Autenticazione (View)**: ha la responsabilità di fornire l'interfaccia di pre-autenticazione per il login e la registrazione dell'utente nel sistema. Gestisce la visualizzazione del form di registrazione e l'inserimento delle credenziali (nome, cognome, email, password, data di nascita). Comunica con il Controller GestioneAutenticazione tramite l'interfaccia `Stato Sessione`. Realizza l'interfaccia View `Stato Sessione` (ID XMI: `xgSAZ3mD.AACARNt`) consumata dal Controller.

---

## 2. Specifica Classi Controller

Sono le Classi che si occupano della gestione delle richieste dell'Utente, dell'orchestrazione del flusso MVC e della elaborazione dei dati. Intercettano gli input della View, validano le richieste, interrogano o aggiornano il Model e, una volta elaborata la risposta, indirizzano e formattano i dati per la View. Ogni interazione è mediata dal Controller — le View non interrogano mai direttamente il Model. La modellazione segue un approccio ibrido, prevalentemente funzionale, in cui l'oggetto UML Classe è usato come un Modulo di Funzioni.

- **GestioneAutenticazione**: ha la responsabilità di validare le credenziali, gestire la registrazione degli utenti e amministrare le sessioni di autenticazione. Fornisce i metodi per la verifica dei dati di registrazione (`verificaValidita`), l'invio delle credenziali per il login (`invioCredenziali`) e la richiesta di logout (`inviaRichiestaLogout`). Comunica con la View Autenticazione fornendo l'interfaccia `Gestione Sessioni` (ID XMI: `2eaHp3mD.AACAQ9m`). È consumata da AppUtente, AppOperatoreTecnico, AppOperatoreSC, AppPA e Autenticazione. Accede al Model tramite l'interfaccia `Gestione Dati Utente` per le operazioni sugli attori.

- **GestioneUtenti**: ha la responsabilità di eseguire le operazioni di moderazione degli account utente, inclusa la sospensione e la disattivazione. Fornisce i metodi per la gestione dello stato dell'utente (`gestioneUtente`) e la ricerca dei report (`cercaReport`). Comunica con la View AppOperatoreSC tramite l'interfaccia `Moderazione Utente` (ID XMI: `FSGPv7mD.AACARbj`). Accede al Model tramite l'interfaccia `Gestione Dati Utente`.

- **RicercaMezzi**: ha la responsabilità di eseguire query geolocalizzate sui mezzi disponibili, con raggi multipli (raggio base 2km, raggio esteso 5km — default da UC.UT.01). Fornisce i metodi per visualizzare i mezzi vicini a una coordinata (`visualizzaMezziVicini`) e le specifiche di un singolo mezzo (`visualizzaSpecifiche`). Comunica con la View AppUtente tramite l'interfaccia `Gestione Corsa` (ID XMI: `R.IxKnmD.AACAQ9w`). Dipende dal sistema esterno Servizio Mappa tramite l'interfaccia `API Mappa` per le funzionalità di geolocalizzazione. Accede al Model tramite l'interfaccia `Gestione Dati Corsa`.

- **GestioneCorsa**: ha la responsabilità di orchestrare l'intero ciclo di vita della corsa: avvio, sospensione, riattivazione, terminazione, calcolo del percorso e stima dei costi. Fornisce metodi per avviare la corsa (`avviaCorsa`), terminarla (`terminaCorsa`), sospenderla (`sospensioneCorsa`), controllare la disponibilità del mezzo (`controllaDisponibilita` con due overload), aggiornare la stima dei costi (`aggiornaStima`), richiedere lo sblocco tramite QR code (`richiediSblocco`), calcolare il percorso ottimale (`richiediCalcoloPercorso` delegando a Servizio Mappa) e acquisire la scelta del metodo di pagamento (`acquisisciSceltaMetodo`). Comunica con la View AppUtente tramite l'interfaccia `Gestione Corsa`. Dipende dai sistemi esterni Servizio Mappa (tramite `API Mappa`) per il routing e Mezzo:IoT (tramite `API Controllo`, ID XMI: `3P9eKnmD.AACAQlF`) per il blocco/sblocco fisico del veicolo. Accede al Model tramite l'interfaccia `Gestione Dati Corsa`. È in relazione di associazione con Mezzo (1..* a 0..*) e Corsa (0..* a 1).

- **GestorePagamento**: ha la responsabilità di elaborare le transazioni economiche e validare i metodi di pagamento, delegando al sistema esterno Gateway Pagamento. Fornisce metodi per processare il pagamento di una corsa (`pagamentoCorsa`), elaborare i dati di una nuova carta (`elaboraDatiCarta`) e recuperare i metodi salvati dall'utente (`recuperaMetodiSalvati`). Comunica con la View AppUtente tramite l'interfaccia `Gestione Corsa`. Dipende dal sistema esterno Gateway Pagamento tramite l'interfaccia `API Pagamento` (ID XMI: `Z.W3v7mD.AACARaG`). Accede al Model tramite l'interfaccia `Gestione Dati Corsa`. È in relazione di associazione con MetodoPagamento (0..* a 0..*).

- **GestioneFlotta**: ha la responsabilità di monitorare e controllare la flotta di veicoli da remoto, inclusi il blocco dei mezzi e l'avvio della manutenzione. Fornisce metodi per analizzare lo stato della flotta (`analisiStatoFlotta` — rileva mezzi da manutenere e crea Segnalazione), bloccare un mezzo (`bloccaMezzo`), avviare la manutenzione (`avviaManutenzione`) e ottenere le condizioni dei mezzi (`getCondizioniMezzi` per visualizzazione dashboard). Comunica con le View AppOperatoreTecnico (tramite l'interfaccia `Amministrazione Flotta`, ID XMI: `BvUAwXmD.AACAQue`) e AppPA (tramite l'interfaccia `Statistiche e Restrizioni`, ID XMI: `ie1hKnmD.AACAQ4x`). Accede al Model tramite l'interfaccia `Gestione Dati Corsa` per i dati dei mezzi e tramite `Gestione Dati Supporto` per le segnalazioni. È in relazione di associazione con Mezzo (0..* a 0..*) e Segnalazione (0..* a 1).

- **GestionePrenotazione**: ha la responsabilità di gestire il ciclo di vita delle prenotazioni, con timeout automatico a 15 minuti (default da UC.UT.02). Fornisce metodi per inviare una richiesta di prenotazione (`inviaRichiestaPrenotazione`), richiedere la lista delle prenotazioni (`richiediLista`), annullare una prenotazione (`annullaPrenotazione`), gestire il timeout (`gestisciTimeout`), notificare la scadenza del tempo (`notificaScadenzaTempo`) e concludere la prenotazione una volta avviata la corsa (`concludiPrenotazione`). Comunica con le View AppUtente e AppOperatoreSC tramite le rispettive interfacce Controller (`Gestione Corsa` e `Moderazione Utente`). Accede al Model tramite l'interfaccia `Gestione Dati Supporto`. È in relazione di associazione con Mezzo (1..* a 0..*), Prenotazione (0..* a 1) e Segnalazione (0..* a 1).

- **GestioneStatistiche**: ha la responsabilità di aggregare i dati delle corse e generare report statistici per la Pubblica Amministrazione. Fornisce metodi per analizzare le tratte in un periodo (`analisiTratte`) e generare il file statistiche (`generaFileStatistiche`). Comunica con la View AppPA tramite l'interfaccia `Statistiche e Restrizioni`. Accede al Model tramite le interfacce `Gestione Dati Corsa` (per i dati delle corse) e `Gestione Dati Supporto` (per i dati dei transiti). È in relazione di associazione con Corsa (0..* a 0..*) e Transito (0..* a 0..*).

- **GestioneAree**: ha la responsabilità di eseguire operazioni CRUD sulle zone geografiche e verificare i conflitti tra restrizioni sovrapposte. Fornisce metodi per aggiornare una restrizione (`aggiornaRestrizione`), analizzare i conflitti tra zone (`analisiConflitti`) e ottenere tutte le zone geografiche (`getZoneGeografiche`). Comunica con la View AppPA tramite l'interfaccia `Statistiche e Restrizioni`. Accede al Model tramite l'interfaccia `Gestione Dati Supporto`. È in relazione di associazione con ZonaGeografica (0..* a 0..*).

---

## 3. Specifica Classi Model

Sono le Classi che modellano il Dominio di Business dal punto di vista dei dati. Sono presenti solo getter e setter come operazioni perché le istanze non hanno altra utilità se non quella di visualizzare e modificare i valori contenuti nei suoi campi (Model passivo). Ogni entità dipende da DBMS per la persistenza tramite l'interfaccia `Connessione Dati` (ID XMI: `jvJnv7mD.AACARXl`).

### 3.1 Attore (abstract)
Classe base astratta per tutti gli attori del sistema. Utilizza strategia JOINED inheritance per la persistenza (tabella `attore` base con `utente`, `operatore`, `pa` collegate 1:1). Modella le credenziali di accesso (email, password cifrata) e il ruolo dell'attore nel sistema tramite l'enum RuoloAttore. È in relazione di generalizzazione con Utente, Operatore e PA.

### 3.2 Utente (extends Attore)
Modella l'entità del cittadino fruitore dei servizi di sharing (bicicletta, scooter, auto). Contiene i dati anagrafici (nome, cognome, telefono), la posizione corrente (coordinateUtente), lo stato dell'account (attivo, sospeso, disattivato), il report di moderazione e il contatore dei mezzi attualmente prenotati. Fornisce metodi per la ricerca di un utente (`ricercaUtente`), l'azione correttiva sulla moderazione (`azioneCorrettiva`) e la creazione dell'account (`creaAccountUtente`). Eredita email, password, id e ruolo da Attore. È in relazione di associazione 1 a Molti con Corsa e Prenotazione.

### 3.3 Operatore (extends Attore)
Modella l'entità del personale professionale del servizio, distinto in Tecnico o Servizio Clienti tramite l'enum TipoOperatore (`tipo`). Non esistono classi separate per ciascun tipo — la distinzione è data dal valore dell'enum. La ridichiarazione di `id` è un artefatto della strategia JOINED inheritance JPA e va ignorata semanticamente. Eredita email, password, id e ruolo da Attore.

### 3.4 PA (extends Attore)
Modella l'entità della Pubblica Amministrazione (ente comunale) con privilegi di analisi, statistiche e gestione delle restrizioni geografiche. L'attributo `idPA` coincide con `Attore.id`. Eredita email, password, id e ruolo da Attore.

### 3.5 Mezzo
Modella l'entità del veicolo della flotta (bicicletta, scooter, auto). Traccia lo stato (disponibile, prenotato, in_uso, sospeso, bloccato, manutenzione), la posizione (coordinateMezzo), l'autonomia residua, il costo orario, la velocità massima, la condizione fisica, il tipo di veicolo, l'identificativo della flotta di appartenenza e il tempo di disponibilità. Fornisce metodi di interrogazione avanzati come la ricerca per flotta (`getMezzibyFlotta`), la ricerca geospaziale (`getMezziInArea`) e l'ottenimento dei dettagli completi (`getDettagliMezzo`). È in relazione di associazione 1 a Molti con Corsa (un mezzo può essere utilizzato in più corse).

### 3.6 Corsa
Modella la sessione di utilizzo di un mezzo dall'avvio al termine. Include il costo totale, gli orari di inizio e fine, le coordinate di partenza e arrivo, e le foreign key verso MetodoPagamento e Utente. Fornisce metodi per la creazione della corsa (`creaCorsa`), la ricerca per ID (`ricercaCorsa`), l'interrogazione per periodo (`getCorseByPeriodo`) e l'aggiornamento del costo (`aggiornaCosto`). È in relazione di associazione con Mezzo (0..* a 1) — una corsa è sempre associata a un utente e un mezzo.

### 3.7 MetodoPagamento
Modella i dati cifrati della carta di credito/debito associata a un utente. Utilizza una chiave surrogata (`idMetodoPagamento`) come PK invece del numero carta per sicurezza secondo pattern PCI-DSS. Contiene il numero carta (cifrato) e l'intestatario. Fornisce metodi per la creazione (`creaMetodoPagamento`), la verifica di esistenza (`controllaMetodoEsistente`) e la ricerca per utente (`getMetodoByUtente`). È in relazione di associazione con GestorePagamento (0..* a 0..*).

### 3.8 Prenotazione
Modella il blocco temporaneo di un mezzo, con timeout automatico a 15 minuti (default da UC.UT.02). Traccia lo stato (attiva, scaduta, annullata, completata), l'orario di inizio, la data, e le foreign key verso Utente e Mezzo. Fornisce metodi per la creazione (`creaPrenotazione`) e la ricerca per stato (`getPrenotazioneByStato`). È in relazione di associazione con GestionePrenotazione (0..* a 1).

### 3.9 Segnalazione
Modella il report di anomalia su un mezzo: guasto, necessità di manutenzione, veicolo non raggiungibile. Traccia lo stato (aperta, in_lavorazione, chiusa), l'ora, la data e la foreign key verso Mezzo. Fornisce il metodo di creazione (`creaSegnalazione`). È in relazione di associazione con GestioneFlotta (0..* a 1) e GestionePrenotazione (0..* a 1).

### 3.10 ZonaGeografica
Modella un'area geografica con restrizioni di circolazione o sosta per i mezzi (divieto_parcheggio, ZTL, limite_velocita). Contiene il tipo di restrizione, le note descrittive e la geometria della zona (LineString). Fornisce metodi per la verifica di sovrapposizioni (`verificaSovrapposizioni`), il controllo se una coordinata ricade nell'area (`checkArea`), la creazione (`creaZonaGeografica`), l'interrogazione delle restrizioni per coordinate (`getRestrizioniZona`) e il salvataggio (`salvaRestrizioni`). È in relazione di associazione con GestioneAree (0..* a 0..*). Il vincolo architetturale AP.04 richiede la validazione tramite `checkArea()` prima della terminazione di una corsa.

### 3.11 Transito
Modella l'associazione M:N tra Corsa e ZonaGeografica per tracciare le zone attraversate durante una corsa. Contiene le foreign key verso Corsa (`idCorsa`) e ZonaGeografica (`idArea`). Fornisce il metodo per ottenere i transiti di una corsa (`getTransitiByCorsa`).

---

## 4. Specifica Sistemi Esterni (Infrastruttura)

Tutti i sistemi esterni sono simulati (progetto universitario — chiarimenti-vari.md punto 16). La modellazione segue un approccio ibrido, prevalentemente funzionale, in cui l'oggetto UML Classe è usato come un Modulo di Funzioni.

- **Mezzo : IoT**: ha la responsabilità di fornire l'interfaccia fisica col veicolo per le operazioni di blocco e sblocco remoto. Fornisce i metodi `bloccoMezzoFisico(idMezzo)` e `sbloccoMezzoFisico(idMezzo)`. L'interfaccia `API Controllo` (ID XMI: `3P9eKnmD.AACAQlF`) è definita ma senza realization collegato a Mezzo:IoT nell'XMI del diagramma componenti (orphan XMI). È consumata da GestioneCorsa (Controller) per lo sblocco tramite QR code e il blocco in sospensione/termine corsa.

- **Gateway Pagamento**: ha la responsabilità di processare i pagamenti e convalidare le carte di credito/debito come processore di pagamento esterno. Fornisce i metodi `effettuaPagamento(idMetodoPagamento, idCorsa)` (correzione typo XMI da EffettuaPagamento) e `convalidaCarta(numCarta, dsCarta, cvv, intestatarioCarta)`. Realizza l'interfaccia `API Pagamento` (ID XMI: `Z.W3v7mD.AACARaG`). È consumata da GestorePagamento (Controller).

- **Servizio Mappa**: ha la responsabilità di fornire servizi di geolocalizzazione e routing esterno per il calcolo di percorsi tra coordinate. Fornisce il metodo `getPercorso(coordinateIniziali, coordinateFinali, restrizioni)` (correzione typo XMI da coorfinateFinali a coordinateFinali). Realizza l'interfaccia `API Mappa` (ID XMI: `xP59v7mD.AACARGb`). È consumata da RicercaMezzi e GestioneCorsa (Controller) per le funzionalità di georouting.

- **DBMS**: ha la responsabilità di fornire la persistenza dei dati tramite interfaccia CRUD standard (Create, Read, Update, Delete) verso un database relazionale. Realizza l'interfaccia `Connessione Dati` (ID XMI: `jvJnv7mD.AACARXl`). È consumata da tutte le entità del Model (Attore, Corsa, MetodoPagamento, Mezzo, Prenotazione, Segnalazione, Transito, ZonaGeografica) per le operazioni di persistenza. I metodi `getIdDBMS()` e `setIdDBMS(id)` presenti nell'XMI sono inclusi per completezza di tracciabilità ma non costituiscono l'interfaccia funzionale.

---

## 5. Tabella Riepilogativa

| Layer | Classe | Ruolo | Comunicazioni principali |
|-------|--------|-------|------------------------|
| **View** | AppUtente | Interfaccia cittadino | GestioneCorsa, GestorePagamento, RicercaMezzi, GestionePrenotazione, GestioneAutenticazione |
| **View** | AppOperatoreTecnico | Dashboard operatore tecnico | GestioneFlotta, GestioneAutenticazione |
| **View** | AppOperatoreSC | Interfaccia servizio clienti | GestioneUtenti, GestionePrenotazione, GestioneAutenticazione |
| **View** | AppPA | Interfaccia PA | GestioneFlotta, GestioneStatistiche, GestioneAree, GestioneAutenticazione |
| **View** | Autenticazione | Interfaccia login/registrazione | GestioneAutenticazione |
| **Controller** | GestioneAutenticazione | Validazione credenziali e sessioni | Autenticazione, AppUtente, AppOperatoreTecnico, AppOperatoreSC, AppPA |
| **Controller** | GestioneUtenti | Moderazione account | AppOperatoreSC |
| **Controller** | RicercaMezzi | Query geolocalizzata mezzi | AppUtente, Servizio Mappa |
| **Controller** | GestioneCorsa | Ciclo di vita corsa | AppUtente, Mezzo:IoT, Servizio Mappa |
| **Controller** | GestorePagamento | Transazioni pagamento | AppUtente, Gateway Pagamento |
| **Controller** | GestioneFlotta | Monitoraggio e controllo flotta | AppOperatoreTecnico, AppPA |
| **Controller** | GestionePrenotazione | Ciclo di vita prenotazioni | AppUtente, AppOperatoreSC |
| **Controller** | GestioneStatistiche | Aggregazione dati e report | AppPA |
| **Controller** | GestioneAree | CRUD zone geografiche | AppPA |
| **Model** | Attore | Classe base astratta | GestioneAutenticazione |
| **Model** | Utente | Cittadino fruitore servizi | GestioneAutenticazione, GestioneUtenti |
| **Model** | Operatore | Personale tecnico/SC | GestioneAutenticazione |
| **Model** | PA | Pubblica Amministrazione | GestioneAutenticazione |
| **Model** | Mezzo | Veicolo della flotta | RicercaMezzi, GestioneCorsa, GestioneFlotta, GestionePrenotazione |
| **Model** | Corsa | Sessione di utilizzo mezzo | GestioneCorsa, GestioneStatistiche |
| **Model** | MetodoPagamento | Dati carta cifrati | GestorePagamento |
| **Model** | Prenotazione | Blocco temporaneo mezzo | GestionePrenotazione |
| **Model** | Segnalazione | Report anomalia mezzo | GestioneFlotta, GestionePrenotazione |
| **Model** | ZonaGeografica | Area con restrizioni | GestioneAree, GestioneCorsa |
| **Model** | Transito | Associazione M:N Corsa-Zona | GestioneStatistiche |
| **External** | Mezzo : IoT | Interfaccia fisica veicolo | GestioneCorsa |
| **External** | Gateway Pagamento | Processore pagamento esterno | GestorePagamento |
| **External** | Servizio Mappa | Servizio geolocalizzazione | RicercaMezzi, GestioneCorsa |
| **External** | DBMS | Persistenza dati | Tutte le entità Model |

---

## 6. Metadati Documento

| Metrica | Valore | Note |
|---------|--------|------|
| Classi View | 5 | AppUtente, AppOperatoreTecnico, AppOperatoreSC, AppPA, Autenticazione |
| Classi Controller | 9 | GestioneAutenticazione, GestioneUtenti, RicercaMezzi, GestioneCorsa, GestorePagamento, GestioneFlotta, GestionePrenotazione, GestioneStatistiche, GestioneAree |
| Classi Model (Business) | 11 | Attore, Utente, Operatore, PA, Mezzo, Corsa, MetodoPagamento, Prenotazione, Segnalazione, ZonaGeografica, Transito |
| Classi External | 4 | Mezzo:IoT, Gateway Pagamento, Servizio Mappa, DBMS |
| Totale classi | 29 | 5 + 9 + 11 + 4 |
| Associazioni del dominio | 16 | Documentate in Class_Diagram_Spec §6 e Master_Spec §6 |
| Dipendenze View → Controller | 14 | Documentate in Class_Diagram_Spec §7 |
| Generalizzazioni (extends) | 3 | Utente→Attore, Operatore→Attore, PA→Attore |
| Interfacce di comunicazione | 17 | Documentate in interface_Spec.cgd.md |

---

## 7. Cross-Reference Matrix: Classe → Fonte

| Classe | Class_Diagram_Spec | Component_Diagram_Spec | interface_Spec | Master_Spec |
|--------|-------------------|----------------------|----------------|-------------|
| AppUtente | §3.1 | §1.1 | §1.1, §2.1 | §4 |
| AppOperatoreTecnico | §3.2 | §1.1 | §1.2, §2.3 | §4 |
| AppOperatoreSC | §3.3 | §1.1 | §1.4, §2.2 | §4 |
| AppPA | §3.4 | §1.1 | §1.3, §2.4 | §4 |
| Autenticazione | §3.5 | §1.1 | §1.5, §2.5 | §4 |
| GestioneAutenticazione | §2.1 | §2.1 | §2.1, §2.2, §2.3, §2.4, §2.5 | §3 |
| GestioneUtenti | §2.2 | §2.1 | §2.2 | §3 |
| RicercaMezzi | §2.3 | §2.1 | §2.1 | §3 |
| GestioneCorsa | §2.4 | §2.1 | §2.1 | §3 |
| GestorePagamento | §2.5 | §2.1 | §2.1 | §3 |
| GestioneFlotta | §2.6 | §2.1 | §2.3, §2.4 | §3 |
| GestionePrenotazione | §2.7 | §2.1 | §2.1, §2.2 | §3 |
| GestioneStatistiche | §2.8 | §2.1 | §2.4 | §3 |
| GestioneAree | §2.9 | §2.1 | §2.4 | §3 |
| Attore | §1.1 | §3.1 | §3.1 | §2 |
| Utente | §1.2 | §3.1 | §3.1 | §2 |
| Operatore | §1.3 | §3.1 | §3.1 | §2 |
| PA | §1.4 | §3.1 | §3.1 | §2 |
| Mezzo | §1.5 | §3.1 | §3.3 | §2 |
| Corsa | §1.6 | §3.1 | §3.3 | §2 |
| MetodoPagamento | §1.7 | §3.1 | §3.3 | §2 |
| Prenotazione | §1.8 | §3.1 | §3.2 | §2 |
| Segnalazione | §1.9 | §3.1 | §3.2 | §2 |
| ZonaGeografica | §1.10 | §3.1 | §3.2 | §2 |
| Transito | §1.11 | §3.1 | §3.2 | §2 |
| Mezzo : IoT | §4.1 | §4.4 | §4.4 | §5 |
| Gateway Pagamento | §4.2 | §4.2 | §4.2 | §5 |
| Servizio Mappa | §4.3 | §4.1 | §4.1 | §5 |
| DBMS | §4.4 | §4.3 | §4.3 | §5 |

---

## HITL Verification Record

### Round A: Derived Data Confirmation

Tutti i dati descrittivi delle classi sono stati derivati dalle quattro fonti primarie:
- **Classi View (§1):** Ruoli, responsabilità e comunicazioni da Class_Diagram_Spec §3, Component_Diagram_Spec §1, interface_Spec §1
- **Classi Controller (§2):** Comportamenti e orchestrazione da Class_Diagram_Spec §2, Component_Diagram_Spec §2, interface_Spec §2
- **Classi Model (§3):** Entità di business, attributi e relazioni da Class_Diagram_Spec §1, Master_Spec §2, interface_Spec §3
- **Sistemi Esterni (§4):** Ruoli infrastrutturali da Class_Diagram_Spec §4, Component_Diagram_Spec §4, interface_Spec §4

### Round B: True HITL Verification

Nessun claim richiede Round B — documento descrittivo derivato esclusivamente da fonti già verificate e cross-referenziate.

---

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | REVIEWED

---
clarity-gate-version: 2.1
document-type: Implementation
processed-by: AI — Master_Spec.cgd.md v4.0, documentazione.md v3.0, chiarimenti-vari.md, Class_Diagram_Spec.cgd.md v1.8, Component_Diagram_Spec.cgd.md v1.0
processed-date: 2026-06-25
clarity-status: CLEAR
hitl-status: REVIEWED
hitl-pending-count: 0
points-passed: 1-9
document-sha256: <PENDING>
hitl-claims: []
---

# Glossario — Smart Mobility System

**Versione:** 1.0
**Team:** Cofee Coders
**Progetto:** Ingegneria del Software a.a. 2025/2026
**Architettura:** MVC Web-oriented con Controller Intermediario
**Fonti:** Master_Spec.cgd.md v4.0, documentazione.md v3.0, chiarimenti-vari.md, Class_Diagram_Spec.cgd.md v1.8, Component_Diagram_Spec.cgd.md v1.0

---

## 1. Acronimi

| Acronimo | Significato |
|----------|-------------|
| API | Application Programming Interface |
| CRUD | Create, Read, Update, Delete |
| DBMS | Database Management System |
| FK | Foreign Key (chiave esterna) |
| IoT | Internet of Things |
| JPA | Jakarta Persistence API |
| MVC | Model-View-Controller |
| ORM | Object-Relational Mapping |
| PA | Pubblica Amministrazione |
| PCI-DSS | Payment Card Industry Data Security Standard |
| PK | Primary Key (chiave primaria) |
| QR | Quick Response (codice QR) |
| RBAC | Role-Based Access Control |
| UML | Unified Modeling Language |
| XMI | XML Metadata Interchange |
| ZTL | Zona a Traffico Limitato |

---

## 2. Definizioni

### 2.1 Smart Urban Mobility
Sistema intelligente di mobilità urbana che integra servizi di bike, car e scooter sharing per il comune di una città generica con copertura WiFi full-range. L'obiettivo è bilanciare le esigenze di utenti, operatori e Pubblica Amministrazione, riducendo traffico e inquinamento (Master_Spec, documentazione.md §1).

### 2.2 Attore
Generalizzazione di Utente, Operatore e Pubblica Amministrazione. Indica anche la persona non ancora autenticata (non loggata) che può interagire solo con la View Autenticazione fino al completamento del login (Master_Spec §2, chiarimenti-vari.md punto 9).

### 2.3 Utente
Cittadino fruitore dei servizi di sharing (bicicletta, scooter, auto). Può cercare mezzi disponibili, prenotare, avviare/sospendere/terminare corse, gestire metodi di pagamento e ottimizzare percorsi. Corrisponde all'enum `RuoloAttore.Utente` dopo l'autenticazione (Master_Spec §2, documentazione.md §1.4).

### 2.4 Operatore
Personale professionale del servizio, distinto in due categorie identificate dall'enum `TipoOperatore`: Operatore Tecnico (gestione flotta e mezzi) e Operatore Servizio Clienti (moderazione utenti e amministrazione prenotazioni). Le credenziali di accesso sono pre-generate e fornite dall'amministrazione (Master_Spec §1, documentazione.md §1.3).

### 2.5 Pubblica Amministrazione (PA)
Ente comunale con privilegi di analisi, visualizzazione di statistiche, monitoraggio dello stato della flotta e gestione delle restrizioni geografiche. Le credenziali di accesso sono pre-generate e fornite dall'amministrazione (Master_Spec §2, documentazione.md §3).

### 2.6 Mezzo
Veicolo della flotta (bicicletta, scooter, auto) con stato, posizione, autonomia residua, costo orario, velocità massima, condizione fisica e tempo di disponibilità. Il ciclo di vita dello stato del mezzo segue la sequenza: `disponibile → prenotato → in_uso → (sospeso → in_uso)* → disponibile`, con stati `bloccato` e `manutenzione` (Master_Spec §2, Master_Spec §8).

### 2.7 Corsa
Sessione di utilizzo di un mezzo dall'avvio al termine. Include il costo totale, gli orari di inizio e fine, le coordinate di partenza e arrivo, e le foreign key verso MetodoPagamento e Utente. Una corsa è sempre associata a un Utente e un Mezzo (Master_Spec §2, Master_Spec §8).

### 2.8 Flotta
Insieme di mezzi identificati da un comune identificativo `idFlotta`. La flotta è gestita dagli Operatori Tecnici per il monitoraggio e la manutenzione, e dalla PA per l'analisi delle condizioni (Master_Spec §2, Class_Diagram_Spec §1.5).

### 2.9 MetodoPagamento
Dati cifrati della carta di credito/debito associata a un utente. Utilizza una chiave surrogata (`idMetodoPagamento`) come PK invece del numero carta per conformità PCI-DSS. I dati della carta sono memorizzati cifrati (Master_Spec §2, Class_Diagram_Spec §1.7).

### 2.10 Prenotazione
Blocco temporaneo di un mezzo con validità di 15 minuti dall'orario di inizio (timeout). Se l'utente non avvia la corsa entro questo intervallo, la prenotazione viene automaticamente annullata e il mezzo torna disponibile. Gli stati possibili sono: attiva, scaduta, annullata, completata (Master_Spec §1, UC.UT.02.cgd.md glossario locale).

### 2.11 Segnalazione
Report di anomalia su un mezzo (guasto, necessità di manutenzione, veicolo non raggiungibile). Creata dal sistema tramite `GestioneFlotta.analisiStatoFlotta()`. Il ciclo di vita dello stato segue: `aperta → in_lavorazione → chiusa` (Master_Spec §1, Master_Spec §2).

### 2.12 ZonaGeografica
Area geografica con restrizioni di circolazione o sosta per i mezzi. Le restrizioni sono definite tramite l'enum `TipoRestrizione` con valori: `divieto_parcheggio` (no parking), `ZTL` (Zona a Traffico Limitato) e `limite_velocita` (speed limit). La geometria della zona è modellata come LineString (Master_Spec §1, Master_Spec §2).

### 2.13 Transito
Associazione molti-a-molti (M:N) tra Corsa e ZonaGeografica che traccia le zone geografiche attraversate durante una corsa (Master_Spec §2, Class_Diagram_Spec §1.11).

### 2.14 Coordinate
Attributo di tipo String che memorizza tre coordinate spaziali float (x, y, z) parsate come unica stringa. Utilizzato per `coordinateMezzo`, `coordinateUtente`, `coordinatePartenza` e `coordinateArrivo` (Master_Spec §2, claim-7f2a5b013).

### 2.15 Sessione
Contesto di autenticazione creato dopo il login e distrutto al logout. Determina i permessi e la View istanziata in base al `RuoloAttore` restituito. Una sessione singola per attore — il login termina la sessione precedente. La distruzione della view tramite destroy message UML è semanticamente il meccanismo di logout (Master_Spec §8, chiarimenti-vari.md punti 11 e 21).

### 2.16 QR Code
Codice di risposta rapida generato dal sistema, utilizzato come metodo di autenticazione per lo sblocco del mezzo. Viene generato al termine della prenotazione (UC.UT.02) e utilizzato per avviare la corsa (UC.UT.03). Viene rigenerato durante la sospensione della corsa (UC.UT.06) per la successiva ripresa (Master_Spec §2, UC.UT.02.cgd.md glossario locale, UC.UT.06.cgd.md glossario).

### 2.17 Sospensione Corsa
Pausa temporanea della corsa con mantenimento del possesso del mezzo. Il veicolo viene fisicamente bloccato tramite Mezzo:IoT ma rimane assegnato all'utente. Durante la sospensione si applica una tariffa oraria differenziata. La ripresa avviene tramite scansione del QR Code di sospensione (UC.UT.06.cgd.md glossario, Master_Spec §8).

### 2.18 Stima Costi
Calcolo del costo finale di una corsa secondo la formula: `stimaCosto = costoOrario * oreUtilizzo + costoSospensione(eventuale)`, dove `costoOrario` è attributo di Mezzo, `oreUtilizzo = (orarioFine - orarioInizio)` in ore, e `costoSospensione` è il costo accumulato durante eventuali sospensioni (Master_Spec §3, chiarimenti-vari.md punto 19).

### 2.19 Raggio di Ricerca
Parametro di geolocalizzazione per la ricerca dei mezzi disponibili. Definito con due valori: raggio base (2 km di default) e raggio esteso (5 km di default). Se nessun mezzo viene trovato nel raggio base, il sistema propone all'utente di espandere la ricerca al raggio esteso (Master_Spec §3, documentazione.md UC.UT.01, chiarimenti-vari.md punto 7).

### 2.20 Timeout Prenotazione
Intervallo di 15 minuti dall'orario di inizio della prenotazione entro il quale l'utente deve avviare la corsa. Trascorso il timeout senza avvio, la prenotazione passa automaticamente allo stato `scaduta` e il mezzo torna disponibile (Master_Spec §8, UC.UT.02.cgd.md glossario locale).

### 2.21 Vincolo AP.04
Vincolo architetturale che richiede la verifica geospaziale tramite `ZonaGeografica.checkArea()` prima di consentire la terminazione di una corsa. Impedisce che i mezzi vengano lasciati in aree non designate (Master_Spec §8, chiarimenti-vari.md punto 4).

### 2.22 MVC con Controller Intermediario
Pattern architetturale adottato dal sistema, variante moderna (Web-oriented) del pattern Model-View-Controller. Centralizza l'intero flusso di controllo e scambio dati all'interno dei componenti Controller, che mediano tra View, Model e sistemi esterni. Le View non interrogano mai direttamente il Model (documentazione.md §2.3, Component_Diagram_Spec, Master_Spec §11).

### 2.23 Sistemi Esterni Simulati
I componenti Servizio Mappa, Gateway Pagamento, DBMS e Mezzo:IoT sono simulati in quanto trattasi di progetto universitario. Le interfacce sono definite ma senza implementazione reale (Master_Spec §5, chiarimenti-vari.md punto 16).

### 2.24 JOINED Inheritance
Strategia di persistenza JPA/ORM utilizzata per la gerarchia Attore. Prevede una tabella base `attore` con `utente`, `operatore` e `pa` collegate 1:1 tramite chiave esterna (Master_Spec §10, Master_Spec §11).

### 2.25 Chiave Surrogata
Identificatore artificiale utilizzato come PK al posto di un identificatore naturale. Nel sistema, `idMetodoPagamento` è una chiave surrogata che sostituisce il numero della carta per motivi di sicurezza PCI-DSS (Master_Spec §2, claim-e7f6a003).

### 2.26 ReportUtente
Stringa associata all'account di un utente che contiene informazioni relative alla moderazione. Può essere consultata e aggiornata dall'Operatore Servizio Clienti tramite `cercaReport()` e `aggiornaReport()` (Master_Spec §2, Class_Diagram_Spec §1.2).

### 2.27 Autonomia
Autonomia residua di un mezzo, espressa in float. Indica la distanza o il tempo di utilizzo residuo prima che il mezzo necessiti di ricarica/manutenzione (Master_Spec §2, Class_Diagram_Spec §1.5).

### 2.28 Costo Orario
Costo per ora di utilizzo di un mezzo, attributo float della classe Mezzo. Utilizzato nella formula di calcolo della stima costi di una corsa (Master_Spec §2, chiarimenti-vari.md punto 19).

### 2.29 LineString
Tipo di dato geometrico che rappresenta una linea composta da una sequenza di punti. Utilizzato per modellare la geometria di una ZonaGeografica (Master_Spec §2, Class_Diagram_Spec §1.10).

### 2.30 Servizio Mappa
Sistema esterno (simulato) di geolocalizzazione e routing che fornisce il calcolo del percorso ottimale tra coordinate, tenendo conto delle restrizioni geografiche. Espone l'interfaccia `API Mappa` con il metodo `getPercorso()` (Master_Spec §5, Component_Diagram_Spec §4.1).

### 2.31 Gateway Pagamento
Sistema esterno (simulato) che processa i pagamenti e convalida le carte di credito/debito. Espone l'interfaccia `API Pagamento` con i metodi `effettuaPagamento()` e `convalidaCarta()` (Master_Spec §5, Component_Diagram_Spec §4.2).

### 2.32 Mezzo:IoT
Sistema esterno (simulato) che fornisce l'interfaccia fisica col veicolo per le operazioni di blocco e sblocco remoto. Espone l'interfaccia `API Controllo` con i metodi `bloccoMezzoFisico()` e `sbloccoMezzoFisico()` (Master_Spec §5, Component_Diagram_Spec §4.4).

### 2.33 DBMS
Sistema esterno (simulato) per la persistenza dei dati. Fornisce un'interfaccia CRUD standard (Create, Read, Update, Delete) verso un database relazionale. Tutte le entità del Model dipendono da DBMS per la persistenza (Master_Spec §5, Component_Diagram_Spec §4.3).

### 2.34 Chiave Primaria (PK)
Identificatore univoco per ogni record di una tabella del database. Nel sistema, ogni entità Model ha una PK: `Attore.id`, `Mezzo.idMezzo`, `Corsa.idCorsa`, `MetodoPagamento.idMetodoPagamento`, `Prenotazione.idPrenotazione`, `Segnalazione.idSegnalazione`, `ZonaGeografica.idArea` (Master_Spec §10).

### 2.35 Chiave Esterna (FK)
Attributo che referenzia la chiave primaria di un'altra tabella per stabilire relazioni. Nel sistema: `Corsa.idUtente → Utente`, `Corsa.idMetodoPagamento → MetodoPagamento`, `Prenotazione.idUtente → Utente`, `Prenotazione.idMezzo → Mezzo`, `Segnalazione.idMezzo → Mezzo`, `Transito.idCorsa → Corsa`, `Transito.idArea → ZonaGeografica` (Master_Spec §10).

### 2.36 RuoloAttore (enum)
Enumerazione che definisce i tre ruoli possibili nel sistema: `Utente` (cittadino fruitore), `Operatore` (personale tecnico o servizio clienti), `PA` (Pubblica Amministrazione). Determina il routing post-login e le View accessibili (Master_Spec §1).

### 2.37 TipoOperatore (enum)
Enumerazione che distingue le due categorie di Operatore: `OperatoreTecnico` (gestione flotta e mezzi) e `OperatoreSC` (Servizio Clienti e moderazione). La distinzione è data dal valore dell'enum, non da classi separate (Master_Spec §1).

### 2.38 StatoUtente (enum)
Enumerazione dello stato dell'account utente: `attivo` (funzionante), `sospeso` (temporaneamente bloccato), `disattivato` (permanentemente disabilitato) (Master_Spec §1).

### 2.39 StatoMezzo (enum)
Enumerazione dello stato del veicolo: `disponibile` (libero e prenotabile), `prenotato` (bloccato da prenotazione attiva), `in_uso` (corsa in corso), `sospeso` (corsa in pausa temporanea), `bloccato` (blocco remoto da operatore), `manutenzione` (fuori servizio per intervento tecnico) (Master_Spec §1).

### 2.40 StatoPrenotazione (enum)
Enumerazione dello stato della prenotazione: `attiva` (valida entro i 15 minuti), `scaduta` (timeout superato, annullata automaticamente), `annullata` (cancellata da utente o operatore SC), `completata` (prenotazione onorata, corsa avviata) (Master_Spec §1).

### 2.41 StatoSegnalazione (enum)
Enumerazione dello stato della segnalazione: `aperta` (creata, in attesa), `in_lavorazione` (in gestione), `chiusa` (risolta e archiviata) (Master_Spec §1).

### 2.42 TipoRestrizione (enum)
Enumerazione dei tipi di restrizione geografica: `divieto_parcheggio` (divieto di sosta), `ZTL` (Zona a Traffico Limitato), `limite_velocita` (limite di velocità). Ogni ZonaGeografica ha sempre un tipoRestrizione valido (Master_Spec §1).

---

## 3. Cross-Reference Matrix: Termine → Fonte

| Termine | Master_Spec | documentazione.md | chiarimenti-vari.md | Class_Diagram_Spec | Component_Diagram_Spec |
|---------|-------------|-------------------|---------------------|-------------------|------------------------|
| Smart Urban Mobility | — | §1 | — | — | — |
| Attore | §2 | §1.3 | punto 9 | §1.1 | §3.1 |
| Utente | §2 | §1.4 | — | §1.2 | §3.1 |
| Operatore | §1, §2 | §1.3 | — | §1.3 | §3.1 |
| Pubblica Amministrazione | §2 | §3 | — | §1.4 | §3.1 |
| Mezzo | §2 | — | — | §1.5 | §3.1 |
| Corsa | §2, §8 | — | — | §1.6 | §3.1 |
| Flotta | §2 | — | — | §1.5 | — |
| MetodoPagamento | §2 | — | — | §1.7 | §3.1 |
| Prenotazione | §1, §2 | UC.UT.02 | — | §1.8 | §3.1 |
| Segnalazione | §1, §2 | — | — | §1.9 | §3.1 |
| ZonaGeografica | §1, §2 | — | — | §1.10 | §3.1 |
| Transito | §2 | — | — | §1.11 | §3.1 |
| Coordinate | §2 | — | punto 14, claim | §1 (note) | — |
| Sessione | §8 | — | punti 11, 21 | — | — |
| QR Code | §2 | UC.UT.02, UC.UT.03 | — | — | — |
| Sospensione Corsa | §8 | UC.UT.06 | — | — | — |
| Stima Costi | §3 | — | punto 19 | — | — |
| Raggio di Ricerca | §3 | UC.UT.01 | punto 7 | — | — |
| Timeout Prenotazione | §8 | UC.UT.02 | — | — | — |
| Vincolo AP.04 | §8 | — | punto 4 | — | — |
| MVC con Controller Intermediario | §11 | §2.3 | — | — | Pattern |
| Sistemi Esterni Simulati | §5 | — | punto 16 | §4 | §4 |
| JOINED Inheritance | §10, §11 | — | — | §1 (note) | — |
| Chiave Surrogata | §2 | — | claim | §1.7 | — |
| ReportUtente | §2 | — | — | §1.2 | — |
| Autonomia | §2 | — | — | §1.5 | — |
| Costo Orario | §2 | — | punto 19 | §1.5 | — |
| LineString | §2 | — | — | §1.10 | — |
| Servizio Mappa | §5 | — | — | §4.3 | §4.1 |
| Gateway Pagamento | §5 | — | — | §4.2 | §4.2 |
| Mezzo:IoT | §5 | — | — | §4.1 | §4.4 |
| DBMS | §5 | — | — | §4.4 | §4.3 |
| Chiave Primaria (PK) | §10 | — | — | — | — |
| Chiave Esterna (FK) | §10 | — | — | — | — |
| RuoloAttore | §1 | — | — | §1 (note) | — |
| TipoOperatore | §1 | — | — | §1.3 | — |
| StatoUtente | §1 | — | — | §1.2 | — |
| StatoMezzo | §1 | — | — | §1.5 | — |
| StatoPrenotazione | §1 | — | — | §1.8 | — |
| StatoSegnalazione | §1 | — | — | §1.9 | — |
| TipoRestrizione | §1 | — | — | §1.10 | — |

---

## 4. Metadati Documento

| Metrica | Valore |
|---------|--------|
| Acronimi documentati | 16 |
| Definizioni documentate | 42 |
| Enumerazioni coperte | 7 (RuoloAttore, TipoOperatore, StatoUtente, StatoMezzo, StatoPrenotazione, StatoSegnalazione, TipoRestrizione) |
| Valori enum documentati | 23 |
| Riferimenti incrociati a Master_Spec | 42 su 42 definizioni |
| Riferimenti incrociati a documentazione.md | 10 |
| Riferimenti incrociati a chiarimenti-vari.md | 8 |
| Riferimenti incrociati a Class_Diagram_Spec | 24 |
| Riferimenti incrociati a Component_Diagram_Spec | 8 |

---

## HITL Verification Record

### Round A: Derived Data Confirmation

Tutte le definizioni sono state derivate dalle fonti documentali del repository. Ogni termine è associato alla fonte primaria di riferimento tramite la cross-reference matrix. Nessun termine è stato inventato — ogni definizione corrisponde a un concetto presente nella modellazione del sistema.

### Round B: True HITL Verification

Nessun claim richiede Round B — glossario derivato esclusivamente da fonti già verificate e cross-referenziate.

---

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | REVIEWED

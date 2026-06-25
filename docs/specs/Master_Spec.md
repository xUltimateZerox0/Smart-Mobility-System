# Master Specification — Smart Mobility System (Implementation)

**Versione:** 5.0
**Team:** Cofee Coders
**Progetto:** Ingegneria del Software a.a. 2025/2026
**Architettura:** MVC Web-oriented con Controller Intermediario
**Data:** 2026-06-25
**Priorità fonti:** documentazione.md > chiarimenti-vari >= chiarimentiUc >= classDiagram > ComponentDiagram > UseCasesDiagram > E-R_Diagram > SequenceDiagram

---

## 1. System Overview

Smart Urban Mobility System per il comune di Zootropolis. Integra servizi di bike, car e scooter sharing per bilanciare esigenze di utenti, operatori e Pubblica Amministrazione, riducendo traffico e inquinamento.

### 1.1 Attori del Sistema

| Attore | Ruolo | Enum |
|--------|-------|------|
| **Utente** | Cittadino fruitore di servizi di sharing | `RuoloAttore.Utente` |
| **Operatore Tecnico** | Gestione flotta e manutenzione mezzi | `TipoOperatore.OperatoreTecnico` |
| **Operatore Servizio Clienti** | Moderazione utenti e prenotazioni | `TipoOperatore.OperatoreSC` |
| **Pubblica Amministrazione** | Statistiche, monitoraggio flotta, restrizioni geografiche | `RuoloAttore.PA` |
| **Attore (non autenticato)** | Persona non loggata — solo autenticazione/registrazione | — |

### 1.2 Enumerazioni

| Enum | Valori |
|------|--------|
| `RuoloAttore` | `Utente`, `Operatore`, `PA` |
| `TipoOperatore` | `OperatoreTecnico`, `OperatoreSC` |
| `StatoUtente` | `attivo`, `sospeso`, `disattivato` |
| `StatoMezzo` | `disponibile`, `prenotato`, `in_uso`, `sospeso`, `bloccato`, `manutenzione` |
| `StatoPrenotazione` | `attiva`, `scaduta`, `annullata`, `completata` |
| `StatoSegnalazione` | `aperta`, `in_lavorazione`, `chiusa` |
| `TipoRestrizione` | `divieto_parcheggio`, `ZTL`, `limite_velocita` |

---

## 2. Model Layer — Business Entities

### 2.1 DominioAttori (Interfaccia: `Gestione Dati Utente`)

#### Attore (abstract)
Classe base astratta. JOINED inheritance (tabella `attore` base con `utente`, `operatore`, `pa` collegate 1:1).

| Attributo | Tipo | Note |
|-----------|------|------|
| `id` | Long | PK |
| `email` | String | Unica |
| `password` | String | Cifrata |
| `ruolo` | RuoloAttore | Enum |

#### Utente (extends Attore)
Cittadino fruitore dei servizi di sharing.

| Attributo | Tipo | Note |
|-----------|------|------|
| `idUtente` | Long | PK (coincide con Attore.id) |
| `nomeUtente` | String | — |
| `cognomeUtente` | String | — |
| `telefono` | String | — |
| `coordinateUtente` | String | Coordinate (x,y,z) |
| `reportUtente` | String | Report di moderazione |
| `statoUtente` | StatoUtente | Enum |
| `numMezziPrenotati` | Integer | Contatore |

**Metodi:** `ricercaUtente(idUtente)`, `azioneCorrettiva(azione)`, `creaAccountUtente(nome, cognome, email, password, datanascita)`, getter/setter

#### Operatore (extends Attore)
Personale professionale. Distinzione Tecnico/SC tramite enum `tipo`.

| Attributo | Tipo | Note |
|-----------|------|------|
| `tipo` | TipoOperatore | Enum |

**Metodi:** getter/setter per `tipo`, ereditati da Attore

#### PA (extends Attore)
Pubblica Amministrazione.

| Attributo | Tipo | Note |
|-----------|------|------|
| `idPA` | Long | PK (coincide con Attore.id) |

**Metodi:** getter/setter per `idPA`, ereditati da Attore

### 2.2 DominioCorsa (Interfaccia: `Gestione Dati Corsa`)

#### Mezzo
Veicolo della flotta (bicicletta, monopattino, auto).

| Attributo | Tipo | Note |
|-----------|------|------|
| `idMezzo` | Long | PK |
| `coordinateMezzo` | String | Coordinate (x,y,z) |
| `stato` | StatoMezzo | Enum |
| `autonomia` | Float | Autonomia residua |
| `costoOrario` | Float | Costo per ora |
| `velocitaMax` | Float | Velocità massima |
| `condizione` | String | Condizione fisica |
| `tipo` | String | Bicicletta/Monopattino/Automobile |
| `idFlotta` | String | Identificativo flotta |
| `tempoDisponibilita` | Time | Tempo di disponibilità |

**Metodi:** `getMezzibyFlotta(idFlotta)`, `getMezziInArea(coordinateUtente, raggio)`, `getDettagliMezzo()`, getter/setter

**Associazioni:** 1 `Mezzo` → 0..* `Corsa`

#### Corsa
Sessione di utilizzo di un mezzo dall'avvio al termine.

| Attributo | Tipo | Note |
|-----------|------|------|
| `idCorsa` | Long | PK |
| `costo` | Float | Costo totale |
| `orarioInizio` | Time | Inizio corsa |
| `orarioFine` | Time | Fine corsa (null se attiva) |
| `coordinatePartenza` | String | Coordinate partenza |
| `coordinateArrivo` | String | Coordinate arrivo (null se attiva) |
| `idMetodoPagamento` | Long | FK → MetodoPagamento |
| `idUtente` | Long | FK → Utente |

**Metodi:** `creaCorsa(orarioinizio, coordinatePartenza, idUtente, idMezzo)`, `ricercaCorsa(idCorsa)`, `getCorseByPeriodo(dataInizio, dataFine)`, `aggiornaCosto(costo)`, getter/setter

#### MetodoPagamento
Dati cifrati della carta di credito/debito. Chiave surrogata `idMetodoPagamento` come PK (pattern PCI-DSS).

| Attributo | Tipo | Note |
|-----------|------|------|
| `idMetodoPagamento` | Long | PK (surrogata) |
| `numCarta` | String | Cifrato |
| `intestatarioCarta` | String | — |

**Metodi:** `creaMetodoPagamento(numCarta, intestatarioCarta)`, `controllaMetodoEsistente(numCarta)`, `getMetodoByUtente(idUtente)`, getter/setter

**Associazioni:** 0..* `MetodoPagamento` ↔ 0..* `GestorePagamento`

### 2.3 DominioSupporto (Interfaccia: `Gestione Dati Supporto`)

#### Prenotazione
Blocco temporaneo di un mezzo. Timeout automatico a 15 minuti.

| Attributo | Tipo | Note |
|-----------|------|------|
| `idPrenotazione` | Long | PK |
| `stato` | StatoPrenotazione | Enum |
| `orarioInizio` | Time | Inizio prenotazione |
| `data` | Date | Data prenotazione |
| `idUtente` | Long | FK → Utente |
| `idMezzo` | Long | FK → Mezzo |

**Metodi:** `creaPrenotazione(idMezzo, idUtente, orarioInizio)`, `getPrenotazioneByStato(stato)`, getter/setter

#### Segnalazione
Report di anomalia su un mezzo.

| Attributo | Tipo | Note |
|-----------|------|------|
| `idSegnalazione` | Long | PK |
| `idMezzo` | Long | FK → Mezzo |
| `stato` | StatoSegnalazione | Enum |
| `ora` | Time | Ora creazione |
| `data` | Date | Data creazione |

**Metodi:** `creaSegnalazione(idMezzo, statoS, data, ora, note)`, getter/setter

#### ZonaGeografica
Area geografica con restrizioni.

| Attributo | Tipo | Note |
|-----------|------|------|
| `idArea` | Long | PK |
| `tipoRestrizione` | TipoRestrizione | Enum |
| `noteRestrizione` | String | Note descrittive |
| `zona` | LineString | Geometria della zona |

**Metodi:** `verificaSovrapposizioni(zona)`, `checkArea(coordinateUtente)`, `creaZonaGeografica(...)`, `getRestrizioniZona(coordinateUtente)`, `salvaRestrizioni(...)`, `getZone()`, getter/setter

#### Transito
Associazione M:N tra Corsa e ZonaGeografica.

| Attributo | Tipo | Note |
|-----------|------|------|
| `idCorsa` | Long | FK → Corsa |
| `idArea` | Long | FK → ZonaGeografica |

**Metodi:** `getTransitiByCorsa(idCorsa)`, getter/setter

---

## 3. Controller Layer

### 3.1 GestioneAutenticazione
Validazione credenziali, registrazione utenti, gestione sessioni e logout.

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `verificaValidita(nome, cognome, email, password, datanascita)` | RuoloAttore | dati registrazione |
| `invioCredenziali(email, password)` | RuoloAttore | email, password |
| `inviaRichiestaLogout(email)` | void | email |

**Interfacce realizzate:** Gestione Sessioni (per Autenticazione View), contribuisce a Gestione Corsa, Moderazione Utente, Amministrazione Flotta, Statistiche e Restrizioni
**Model:** Gestione Dati Utente

### 3.2 GestioneUtenti
Moderazione account utente: sospensione, disattivazione, consultazione report.

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `gestioneUtente(idUtente)` | bool | idUtente |
| `cercaReport(idUtente)` | String | idUtente |

**Interfaccia realizzata:** Moderazione Utente (per AppOperatoreSC)
**Model:** Gestione Dati Utente

### 3.3 RicercaMezzi
Query geolocalizzata su mezzi disponibili. Raggio base 2km, raggio esteso 5km.

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `visualizzaMezziVicini(coordinateUtente, raggiob)` | Mezzo | coordinate, raggio |
| `visualizzaSpecifiche(idMezzo)` | Mezzo | idMezzo |

**Interfaccia contribuita:** Gestione Corsa (per AppUtente)
**External:** Servizio Mappa (API Mappa)
**Model:** Gestione Dati Corsa

### 3.4 GestioneCorsa
Orchestrazione ciclo di vita corsa: avvio, sospensione, terminazione, calcolo percorso, stima costi.

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `avviaCorsa(idMezzo, idUtente)` | void | idMezzo, idUtente |
| `terminaCorsa()` | void | — |
| `controllaDisponibilita()` | bool | — |
| `controllaDisponibilita(info)` | bool | info: String |
| `aggiornaStima(idCorsa)` | float | idCorsa |
| `sospensioneCorsa()` | bool | — |
| `richiediSblocco(qrCode)` | bool | qrCode: String |
| `richiediCalcoloPercorso(coordinateUtente, stringaDestinazione)` | datiPercorso | coordinate, destinazione |
| `acquisisciSceltaMetodo(idMetodoPagamento)` | void | idMetodoPagamento |

**Interfaccia contribuita:** Gestione Corsa (per AppUtente)
**External:** Servizio Mappa (API Mappa), Mezzo:IoT (API Controllo)
**Model:** Gestione Dati Corsa
**Associazioni:** 1..* `Mezzo` ↔ 0..* `GestioneCorsa`, 0..* `Corsa` ↔ 1 `GestioneCorsa`

### 3.5 GestorePagamento
Elaborazione transazioni e validazione metodi di pagamento.

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `pagamentoCorsa(idUtente, idMetodoPagamento, costo)` | bool | idUtente, idMetodo, costo |
| `elaboraDatiCarta(idUtente, numCarta, dsCarta, cvv, intestatarioCarta)` | bool | dati carta |
| `recuperaMetodiSalvati()` | MetodoPagamento | — |

**Interfaccia contribuita:** Gestione Corsa (per AppUtente)
**External:** Gateway Pagamento (API Pagamento)
**Model:** Gestione Dati Corsa
**Associazioni:** 0..* `GestorePagamento` ↔ 0..* `MetodoPagamento`

### 3.6 GestioneFlotta
Monitoraggio e controllo remoto della flotta.

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `analisiStatoFlotta(idFlotta)` | bool | idFlotta |
| `bloccaMezzo(idMezzo)` | bool | idMezzo |
| `avviaManutenzione(idFlotta)` | bool | idFlotta |
| `getCondizioniMezzi(idFlotta)` | Mezzo | idFlotta |

**Interfacce realizzate:** Amministrazione Flotta (per AppOperatoreTecnico), contribuisce a Statistiche e Restrizioni (per AppPA)
**Model:** Gestione Dati Corsa (mezzi), Gestione Dati Supporto (segnalazioni)
**Associazioni:** 0..* `Mezzo` ↔ 0..* `GestioneFlotta`, 0..* `Segnalazione` ↔ 1 `GestioneFlotta`

### 3.7 GestionePrenotazione
Ciclo di vita prenotazioni con timeout automatico a 15 minuti.

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `inviaRichiestaPrenotazione()` | void | — |
| `richiediLista()` | Prenotazione | — |
| `annullaPrenotazione(idPrenotazione)` | bool | idPrenotazione |
| `gestisciTimeout()` | void | — |
| `notificaScadenzaTempo(idPrenotazione)` | void | idPrenotazione |
| `concludiPrenotazione(idPrenotazione)` | void | idPrenotazione |

**Interfacce contribuite:** Gestione Corsa (per AppUtente), Moderazione Utente (per AppOperatoreSC)
**Model:** Gestione Dati Supporto
**Associazioni:** 1..* `Mezzo` ↔ 0..* `GestionePrenotazione`, 0..* `Prenotazione` ↔ 1 `GestionePrenotazione`, 0..* `Segnalazione` ↔ 1 `GestionePrenotazione`

### 3.8 GestioneStatistiche
Aggregazione dati corse e generazione report statistici per PA.

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `analisiTratte(dataInizio, dataFine)` | statistiche | dataInizio, dataFine |
| `generaFileStatistiche(corse)` | void | corse |

**Interfaccia contribuita:** Statistiche e Restrizioni (per AppPA)
**Model:** Gestione Dati Corsa (corse), Gestione Dati Supporto (transiti)
**Associazioni:** 0..* `Corsa` ↔ 0..* `GestioneStatistiche`, 0..* `Transito` ↔ 0..* `GestioneStatistiche`

### 3.9 GestioneAree
CRUD zone geografiche e verifica conflitti tra restrizioni sovrapposte.

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `aggiornaRestrizione(idArea, tipoRestrizione, noteRestrizione, zona)` | void | dati restrizione |
| `analisiConflitti(zona)` | bool | ZonaGeografica |
| `getZoneGeografiche()` | ZonaGeografica | — |

**Interfaccia contribuita:** Statistiche e Restrizioni (per AppPA)
**Model:** Gestione Dati Supporto
**Associazioni:** 0..* `ZonaGeografica` ↔ 0..* `GestioneAree`

---

## 4. View Layer

| View | Attore | Interfaccia Realizzata (View→Controller) | Dipende da (Controller→View) |
|------|--------|------------------------------------------|------------------------------|
| **AppUtente** | Utente | Aggiornamenti Corsa | Gestione Corsa |
| **AppOperatoreTecnico** | Operatore Tecnico | Stato Flotta | Amministrazione Flotta |
| **AppOperatoreSC** | Operatore SC | Eventi Utente | Moderazione Utente |
| **AppPA** | PA | Diagnostica | Statistiche e Restrizioni |
| **Autenticazione** | Attore (non aut.) | Stato Sessione | Gestione Sessioni |

### 4.1 AppUtente
Metodi pubblici (interfaccia `Aggiornamenti Corsa` — ID XMI: `BGnPp3mD.AACARJJ`):

`scansionaQRCode(qrCode)`, `inserisciDatiCarta(idUtente, numCarta, dsCarta, cvv, intestatarioCarta)`, `apriAvvioCorsa()`, `terminazioneCorsa(idCorsa)`, `sospendiCorsa(idCorsa)`, `apriSezioneProfilo(idUtente)`, `apriInserimentoMetodoPagamento(idUtente)`, `selezionaMezzo(idMezzo)`, `inserisciDestinazione(indirizzoArrivo)`, `avviaRicercaMezzi(coordinateUtente, raggiob)`, `confermaEspansione()`, `notificaAzione(idUtente, azione)`, `ottieniMetodiSalvati()`, `selezionaMetodo(numCarta)`, `richiestaLogout(email)`

### 4.2 AppOperatoreTecnico
Metodi pubblici (interfaccia `Stato Flotta` — ID XMI: `88Pfp3mD.AACARMt`):

`richiedeStatoFlotta(idFlotta)`, `selezionaVeicolo(idMezzo)`, `richiestaLogout(email)`

### 4.3 AppOperatoreSC
Metodi pubblici (interfaccia `Eventi Utente` — ID XMI: `8v9fp3mD.AACARMc`):

`mostraReport(idUtente)`, `richiediListaPrenotazioni()`, `selezionaPrenotazione(idPrenotazione)`, `aggiornaReport(idUtente)`, `richiestaLogout(email)`

### 4.4 AppPA
Metodi pubblici (interfaccia `Diagnostica` — ID XMI: `Xu9_p3mD.AACARM.`):

`selezionaIntervallo(dataInizio, dataFine)`, `richiedeStatoFlotta(idFlotta)`, `avviaIntervento(idFlotta)`, `selezionaMappa()`, `modificaRestrizioni(zona)`, `confermaSovrascrittura(idArea, tipoRestrizione, noteRestrizione)`, `rifiutaSovrascrittura()`, `richiestaLogout(email)`

### 4.5 Autenticazione
Metodi pubblici (interfaccia `Stato Sessione` — ID XMI: `xgSAZ3mD.AACARNt`):

`inserisciCredenziali(nome, cognome, email, password, datanascita)`, `registrazioneUtente()`

---

## 5. External Systems (Simulated)

Tutti i sistemi esterni sono simulati (progetto universitario).

| Sistema | Interfaccia | Metodi | Consumatore |
|---------|-------------|--------|-------------|
| **Servizio Mappa** | API Mappa | `getPercorso(coordinateIniziali, coordinateFinali, restrizioni)` | RicercaMezzi, GestioneCorsa |
| **Gateway Pagamento** | API Pagamento | `effettuaPagamento(idMetodoPagamento, idCorsa)`, `convalidaCarta(numCarta, dsCarta, cvv, intestatarioCarta)` | GestorePagamento |
| **Mezzo : IoT** | API Controllo | `bloccoMezzoFisico(idMezzo)`, `sbloccoMezzoFisico(idMezzo)` | GestioneCorsa |
| **DBMS** | Connessione Dati | CRUD generico | Model (tutte le entità) |

---

## 6. Architettura delle Dipendenze

### 6.1 View → Controller

| View | Controller dipendente |
|------|----------------------|
| AppUtente | GestioneCorsa, GestorePagamento, RicercaMezzi, GestionePrenotazione, GestioneAutenticazione |
| AppOperatoreSC | GestioneUtenti, GestionePrenotazione, GestioneAutenticazione |
| AppPA | GestioneFlotta, GestioneStatistiche, GestioneAree, GestioneAutenticazione |
| AppOperatoreTecnico | GestioneFlotta, GestioneAutenticazione |
| Autenticazione | GestioneAutenticazione |

### 6.2 Controller → External Systems

| Controller | Sistema Esterno |
|-----------|----------------|
| GestioneCorsa | Servizio Mappa, Mezzo : IoT |
| RicercaMezzi | Servizio Mappa |
| GestorePagamento | Gateway Pagamento |

### 6.3 Controller → Model

| Controller | Interfaccia Model |
|-----------|-------------------|
| GestioneAutenticazione, GestioneUtenti | Gestione Dati Utente |
| GestioneFlotta, GestionePrenotazione, GestioneAree, GestioneStatistiche | Gestione Dati Supporto |
| GestioneCorsa, GestorePagamento, RicercaMezzi, GestioneFlotta, GestioneStatistiche | Gestione Dati Corsa |

### 6.4 Associazioni del Dominio

| Entità A | Relazione | Entità B | Molt. A | Molt. B |
|----------|-----------|----------|---------|---------|
| Mezzo | utilizza | Corsa | 1 | 0..* |
| GestioneUtenti | modera | Utente | 0..* | 0..* |
| GestorePagamento | verifica | MetodoPagamento | 0..* | 0..* |
| RicercaMezzi | interroga | Mezzo | 0..* | 0..* |
| GestioneCorsa | amministra | Mezzo | 1..* | 0..* |
| GestioneCorsa | effettua | Corsa | 0..* | 1 |
| GestioneFlotta | gestisce | Mezzo | 0..* | 0..* |
| GestioneFlotta | crea | Segnalazione | 0..* | 1 |
| GestioneStatistiche | analizza | Corsa | 0..* | 0..* |
| GestioneStatistiche | osserva | Transito | 0..* | 0..* |
| GestionePrenotazione | prenota | Mezzo | 1..* | 0..* |
| GestionePrenotazione | ha | Prenotazione | 0..* | 1 |
| GestionePrenotazione | genera | Segnalazione | 0..* | 1 |
| GestioneAree | aggiunge | ZonaGeografica | 0..* | 0..* |
| ZonaGeografica | esegue check | GestioneCorsa | 0..* | 0..* |
| GestioneAutenticazione | autentica | Attore | 1 | 1 |

### 6.5 Generalizzazioni

| Classe Figlia | Classe Padre |
|--------------|--------------|
| Utente | Attore |
| Operatore | Attore |
| PA | Attore |

---

## 7. Use Case Summary

### 7.1 UT.01 — Ricerca Mezzi
L'utente avvia una ricerca dei mezzi nel raggio base (2km). Se nessun risultato, può estendere al raggio esteso (5km). Seleziona un mezzo per vederne le specifiche.

**Include:** — **Estende:** — **Esteso da:** UC.UT.02

### 7.2 UT.02 — Prenotazione Mezzo
L'utente prenota un mezzo disponibile. Timeout 15 minuti: se non avvia la corsa, la prenotazione scade.

**Include:** — **Estende:** UC.UT.01 **Esteso da:** UC.UT.03

### 7.3 UT.03 — Avvio Corsa (da UC.UT.08)
L'utente scansiona il QR code, seleziona metodo di pagamento, avvia la corsa. Il sistema sblocca il mezzo.

**Include:** UC.UT.08 (Monitoraggio Costo) **Estende:** UC.UT.02 **Esteso da:** UC.UT.06

### 7.4 UT.04 — Ricerca Percorso Migliore
L'utente inserisce una destinazione. Il sistema calcola il percorso ottimale rispettando le restrizioni geografiche.

### 7.5 UT.05 — Seleziona Metodo Pagamento
L'utente seleziona un metodo esistente o ne inserisce uno nuovo. Validazione tramite Gateway Pagamento.

### 7.6 UT.06 — Sospensione Corsa
L'utente sospende la corsa. Il mezzo viene bloccato fisicamente. QR code per la ripresa. Costo sospensione incluso nel totale.

**Estende:** UC.UT.03

### 7.7 UT.07 — Termina Corsa
Verifica area consentita (`checkArea`), calcolo costo finale, pagamento, blocco mezzo, aggiornamento stato.

### 7.8 UT.08 — Monitoraggio Costo
Aggiornamento periodico del costo in tempo reale durante la corsa (ogni 30 secondi).

**Incluso da:** UC.UT.03

### 7.9 UT.09 — Logout Utente
Terminazione sessione utente. Destroy message UML sulla view.

### 7.10 UT.10 — Registrazione Utente
Creazione nuovo account con validazione dati e assenza duplicati.

### 7.11 OP.01 — Moderazione Flotta
Operatore Tecnico visualizza flotta e invia comandi remoti. Blocco mezzo o creazione segnalazione se offline.

### 7.12 OP.02 — Moderazione Utenti
Operatore SC cerca utente, consulta report, applica azione correttiva (sospensione/disattivazione).

### 7.13 OP.03 — Moderazione Prenotazioni
Operatore SC visualizza e annulla prenotazioni attive.

### 7.14 OP.04 — Logout Operatore Tecnico
Terminazione sessione operatore tecnico.

### 7.15 OP.05 — Logout Operatore SC
Terminazione sessione operatore servizio clienti.

### 7.16 AP.01 — Monitoraggio Statistiche
PA seleziona intervallo temporale. Il sistema genera statistiche aggregate e report scaricabile.

### 7.17 AP.02 — Moderazione Stato Flotta
PA richiede stato flotta, analizza condizioni, avvia intervento di manutenzione con creazione segnalazioni.

### 7.18 AP.03 — Restrizioni Geografiche
PA gestisce zone geografiche e restrizioni. Gestione conflitti con sovrascrittura.

### 7.19 AP.04 — Logout PA
Terminazione sessione PA.

### 7.20 ATT.01 — Login
Attore inserisce credenziali. Il sistema autentica e reindirizza alla view corrispondente al ruolo.

---

## 8. Vincoli Architetturali e Invarianti

### 8.1 Vincoli Architetturali

| ID | Vincolo | Descrizione |
|----|---------|-------------|
| C01 | MVC Controller Intermediario | Le View non interrogano mai direttamente il Model. Ogni comunicazione è mediata dal Controller. |
| C02 | Sessione Singola | Un attore può avere una sola sessione attiva. Il login termina la sessione precedente. |
| C03 | Autenticazione Obbligatoria | Lo sblocco del mezzo richiede autenticazione (QR code). |
| C04 | Verifica Area Terminazione | `ZonaGeografica.checkArea(coordinateMezzo)` deve passare prima di terminare una corsa (vincolo AP.04). |
| C05 | Timeout Prenotazione | 15 minuti dall'orario prenotato. Trascorso, la prenotazione passa a `scaduta`. |
| C06 | Raggio Base/Esteso | Ricerca mezzi: raggio base 2km, se nessun risultato → proposta estensione a 5km. |
| C07 | Pagamento Obbligatorio | La corsa non termina senza pagamento riuscito. |
| C08 | Metodo Pagamento Obbligatorio | Prima di avviare la corsa, l'utente deve selezionare un metodo di pagamento. |
| C09 | Prenotazione Propedeutica | L'avvio corsa richiede una prenotazione attiva. |
| C10 | Stato Utente Attivo | Account sospeso o disattivato non può avviare corse. |

### 8.2 Invarianti del Dominio

| ID | Invariante | Descrizione |
|----|------------|-------------|
| D01 | Ciclo Vita Mezzo | `disponibile → prenotato → in_uso → (sospeso → in_uso)* → disponibile`. Stati `bloccato` e `manutenzione` sono terminali fino a intervento. |
| D02 | Ciclo Vita Prenotazione | `attiva → (scaduta|annullata|completata)`. Solo una prenotazione attiva per mezzo. |
| D03 | Ciclo Vita Segnalazione | `aperta → in_lavorazione → chiusa`. |
| D04 | Unicità Email | Ogni `email` in `Attore` deve essere univoca. |
| D05 | Chiave Surrogata Pagamento | `idMetodoPagamento` è PK, non il numero carta (PCI-DSS). |

### 8.3 Formule di Calcolo

| Formula | Descrizione |
|---------|-------------|
| `stimaCosto = costoOrario * oreUtilizzo + costoSospensione` | Costo finale corsa (chiarimenti-vari.md punto 19) |
| `oreUtilizzo = (orarioFine - orarioInizio)` in ore | Durata effettiva della corsa |
| `costoSospensione` = costo accumulato durante eventuali sospensioni | Tariffa differenziata per sospensione |

---

## 9. Anti-Patterns (DO NOT)

| ❌ Don't | ✅ Do Instead | Why |
|----------|---------------|-----|
| View chiama direttamente Model | Tutte le chiamate passano dal Controller | Viola MVC con Controller Intermediario (vincolo C01) |
| Usare `@Data` su entità JPA | Usare `@Getter`/`@Setter` espliciti | `@Data` genera `equals()`/`hashCode()` su proxy JPA, causa LazyInitializationException |
| Esporre entità JPA in REST controller | Usare DTO esplicitamente per ogni risposta | Evita serializzazione di lazy loading e problemi di esposizione dati |
| `@Autowired` su campi | `@RequiredArgsConstructor` + `private final` | Facilita testabilità, immutabilità e chiarezza delle dipendenze |
| Ignorare la verifica area consentita in termina corsa | Chiamare sempre `checkArea(coordinateMezzo)` prima di terminare | Previene abbandono mezzi in aree non autorizzate (vincolo C04) |
| Hardcodare raggi di ricerca | Parametrizzare raggi base (2km) ed esteso (5km) in configurazione | Flessibilità per cambi futuri senza ricompilazione |
| Usare ID numerici interni in URL/API | Usare identificatori opachi (UUID) o parametri espliciti | Previene enumeration attack e accoppiamento all'implementazione DB |

---

## 10. Database Schema (Physical)

### 10.1 DDL

```sql
CREATE TABLE Attore (
    id INTEGER PRIMARY KEY,
    email VARCHAR(255),
    password VARCHAR(255),
    ruolo ENUM('Utente', 'Operatore', 'PA')
);

CREATE TABLE Utente (
    idUtente INTEGER PRIMARY KEY,
    nomeUtente VARCHAR(255),
    cognomeUtente VARCHAR(255),
    telefono VARCHAR(255),
    coordinateUtente VARCHAR(255),
    numMezziPrenotati INTEGER,
    reportUtente CLOB,
    statoUtente ENUM('Attivo', 'Sospeso', 'Disattivato'),
    FOREIGN KEY (idUtente) REFERENCES Attore(id)
);

CREATE TABLE Operatore (
    id INTEGER PRIMARY KEY,
    tipo ENUM('OperatoreTecnico', 'OperatoreSC'),
    FOREIGN KEY (id) REFERENCES Attore(id)
);

CREATE TABLE PA (
    idPA INTEGER PRIMARY KEY,
    FOREIGN KEY (idPA) REFERENCES Attore(id)
);

CREATE TABLE MetodoPagamento (
    idMetodoPagamento INTEGER PRIMARY KEY,
    numCarta INTEGER,
    intestatarioCarta VARCHAR(255)
);

CREATE TABLE Mezzo (
    idMezzo INTEGER PRIMARY KEY,
    coordinateMezzo VARCHAR(255),
    stato ENUM('Disponibile', 'Prenotato', 'In_Uso', 'Sospeso', 'Bloccato', 'Manutenzione'),
    tipo ENUM('Bicicletta', 'Monopattino', 'Automobile'),
    autonomia FLOAT(10),
    costoOrario FLOAT(10),
    velocitaMax FLOAT(10),
    condizione VARCHAR(255),
    idFlotta INTEGER,
    tempoDisponibilita TIME
);

CREATE TABLE Corsa (
    idCorsa INTEGER PRIMARY KEY,
    costo FLOAT(10),
    orarioInizio TIME,
    orarioFine TIME,
    coordinatePartenza VARCHAR(255),
    coordinateArrivo VARCHAR(255),
    idUtente INTEGER,
    idMetodoPagamento INTEGER,
    FOREIGN KEY (idUtente) REFERENCES Utente(idUtente),
    FOREIGN KEY (idMetodoPagamento) REFERENCES MetodoPagamento(idMetodoPagamento)
);

CREATE TABLE Prenotazione (
    idPrenotazione INTEGER PRIMARY KEY,
    stato ENUM('Attiva', 'Scaduta', 'Annullata', 'Completata'),
    orarioInizio TIME,
    data DATE,
    idUtente INTEGER,
    idMezzo INTEGER,
    FOREIGN KEY (idUtente) REFERENCES Utente(idUtente),
    FOREIGN KEY (idMezzo) REFERENCES Mezzo(idMezzo)
);

CREATE TABLE Segnalazione (
    idSegnalazione INTEGER PRIMARY KEY,
    stato ENUM('Aperta', 'In lavorazione', 'Chiusa'),
    ora TIME,
    data DATE,
    idMezzo INTEGER,
    FOREIGN KEY (idMezzo) REFERENCES Mezzo(idMezzo)
);

CREATE TABLE ZonaGeografica (
    idArea INTEGER PRIMARY KEY,
    tipoRestrizione ENUM('ZTL', 'Divieto_Parcheggio', 'Limite_Velocità'),
    noteRestrizione VARCHAR(255),
    zona CLOB
);

CREATE TABLE Transito (
    Corsa INTEGER,
    Area INTEGER,
    PRIMARY KEY (Corsa, Area),
    FOREIGN KEY (Corsa) REFERENCES Corsa(idCorsa),
    FOREIGN KEY (Area) REFERENCES ZonaGeografica(idArea)
);
```

### 10.2 JPA Inheritance (Attore gerarchia)

Strategia `JOINED`: tabella base `attore` con tabelle `utente`, `operatore`, `pa` collegate 1:1 tramite FK.

---

## 11. Interface Summary (17 functional interfaces)

| # | Interfaccia | Categoria | Realizzazione | Consumatore |
|---|-------------|-----------|---------------|-------------|
| 1 | Aggiornamenti Corsa | View-provided | AppUtente | Controller |
| 2 | Stato Flotta | View-provided | AppOperatoreTecnico | Controller |
| 3 | Diagnostica | View-provided | AppPA | Controller |
| 4 | Eventi Utente | View-provided | AppOperatoreSC | Controller |
| 5 | Stato Sessione | View-provided | Autenticazione | Controller |
| 6 | Gestione Corsa | Controller-provided | GestioneCorsa, GestorePagamento, RicercaMezzi, GestionePrenotazione, GestioneAutenticazione | AppUtente |
| 7 | Moderazione Utente | Controller-provided | GestioneUtenti, GestionePrenotazione, GestioneAutenticazione | AppOperatoreSC |
| 8 | Amministrazione Flotta | Controller-provided | GestioneFlotta, GestioneAutenticazione | AppOperatoreTecnico |
| 9 | Statistiche e Restrizioni | Controller-provided | GestioneStatistiche, GestioneAree, GestioneFlotta, GestioneAutenticazione | AppPA |
| 10 | Gestione Sessioni | Controller-provided | GestioneAutenticazione | Autenticazione |
| 11 | Gestione Dati Utente | Model-provided | Attore, Utente, Operatore, PA | Controller |
| 12 | Gestione Dati Supporto | Model-provided | Segnalazione, Prenotazione, ZonaGeografica, Transito | Controller |
| 13 | Gestione Dati Corsa | Model-provided | Corsa, Mezzo, MetodoPagamento | Controller |
| 14 | API Mappa | External | Servizio Mappa | RicercaMezzi |
| 15 | API Pagamento | External | Gateway Pagamento | GestorePagamento |
| 16 | Connessione Dati | External | DBMS | Model |
| 17 | API Controllo | External | Mezzo : IoT | GestioneCorsa |

---

## 12. Summary Statistics

| Metrica | Valore |
|---------|--------|
| Classi Model | 11 (Attore, Utente, Operatore, PA, Mezzo, Corsa, MetodoPagamento, Prenotazione, Segnalazione, ZonaGeografica, Transito) |
| Controller | 9 (GestioneAutenticazione, GestioneUtenti, RicercaMezzi, GestioneCorsa, GestorePagamento, GestioneFlotta, GestionePrenotazione, GestioneStatistiche, GestioneAree) |
| View | 5 (AppUtente, AppOperatoreTecnico, AppOperatoreSC, AppPA, Autenticazione) |
| External Systems | 4 (Mezzo:IoT, Gateway Pagamento, Servizio Mappa, DBMS) |
| Generalizzazioni | 3 (Utente→Attore, Operatore→Attore, PA→Attore) |
| Associazioni del dominio | 16 |
| Interfacce funzionali | 17 |
| Use case specificati | 19 (6 UT + 4 UT impliciti + 3 OP + 3 OP impliciti + 2 AP + 1 AP implicito + 1 ATT) |
| Enumerazioni | 7 |
| Vincoli architetturali | 10 |
| Invarianti del dominio | 5 |
| Anti-patterns | 7 |

---

## 13. Test Case Specifications

### 13.1 Test Strategy

| Livello | Focus | Tecnica |
|---------|-------|---------|
| Unit | Singoli metodi Controller/Model | JUnit 5 + Mockito |
| Integration | Flussi View→Controller→Model→External | SpringBootTest + mock external |
| System | End-to-end via REST API | MockMvc / TestRestTemplate |
| Acceptance | User story satisfaction | Cucumber (opzionale) |

### 13.2 Unit Tests (per componente)

#### GestioneAutenticazione

| TC-ID | Input | Expected | Edge Cases |
|-------|-------|----------|------------|
| TC-AUTH-01 | Credenziali valide (email, password) | RuoloAttore corretto | Email inesistente, password errata |
| TC-AUTH-02 | Dati registrazione validi | Account creato | Email duplicata, formato errato |
| TC-AUTH-03 | Logout con sessione attiva | Sessione terminata | Logout senza sessione |

#### RicercaMezzi

| TC-ID | Input | Expected | Edge Cases |
|-------|-------|----------|------------|
| TC-SEARCH-01 | coordinateUtente, raggio base 2km | Lista mezzi nel raggio | Nessun mezzo nel raggio |
| TC-SEARCH-02 | Espansione raggio a 5km | Lista mezzi nel raggio esteso | Nessun mezzo nemmeno in raggio esteso |

#### GestioneCorsa

| TC-ID | Input | Expected | Edge Cases |
|-------|-------|----------|------------|
| TC-RIDE-01 | QR code valido + metodo pagamento | Corsa avviata, mezzo sbloccato | QR invalido, mezzo non disponibile |
| TC-RIDE-02 | Sospensione corsa attiva | Mezzo bloccato, stato sospeso | Corsa non esistente |
| TC-RIDE-03 | Terminazione corsa in area consentita | Pagamento, blocco mezzo | Area non consentita, pagamento fallito |
| TC-RIDE-04 | aggiornaStima(idCorsa) ogni 30s | Costo aggiornato correttamente | Corsa sospesa (costo sospensione) |

#### GestionePrenotazione

| TC-ID | Input | Expected | Edge Cases |
|-------|-------|----------|------------|
| TC-BOOK-01 | idMezzo disponibile + idUtente | Prenotazione creata, stato 'attiva' | Mezzo già prenotato |
| TC-BOOK-02 | Timeout 15 minuti | Stato -> 'scaduta', mezzo disponibile | Nessuna |

#### GestorePagamento

| TC-ID | Input | Expected | Edge Cases |
|-------|-------|----------|------------|
| TC-PAY-01 | Carta valida | Metodo salvato | Carta già esistente |
| TC-PAY-02 | Pagamento corsa | Transazione ok | Saldo insufficiente (simulato) |

### 13.3 Integration Tests

| IT-ID | Flow | Setup | Verification |
|-------|------|-------|--------------|
| IT-01 | Ricerca → Prenotazione → Avvio Corsa | Mezzo disponibile, utente loggato | Corsa attiva, stato mezzo 'in_uso' |
| IT-02 | Avvio Corsa → Sospensione → Ripresa | Corsa attiva | Mezzo sbloccato, costo aggiornato |
| IT-03 | Avvio Corsa → Terminazione | Corsa attiva, area consentita | Corsa terminata, pagamento ok, mezzo disponibile |
| IT-04 | Login → PA → Statistiche | PA loggata, corse nel periodo | File statistiche generato |
| IT-05 | Login → Operatore SC → Moderazione Utente | Operatore SC loggato | Utente sospeso, notifica inviata |

---

## 14. Error Handling Matrix

### 14.1 Global Error Categories

| Error ID | Tipo | Componente | Rilevamento | Risposta | Fallback | Log |
|----------|------|------------|-------------|----------|----------|-----|
| E-001 | Validazione input | Tutti Controller | Formato errato parametri | 400 Bad Request | Messaggio errore utente | WARN |
| E-002 | Autenticazione | GestioneAutenticazione | Credenziali errate | 401 Unauthorized | Messaggio errore login | INFO |
| E-003 | Autorizzazione | Tutti Controller | Ruolo non autorizzato | 403 Forbidden | Reindirizzamento login | WARN |
| E-004 | Non trovato | Ricerca/Ricerche | Entità inesistente | 404 Not Found | Messaggio entità non trovata | INFO |
| E-005 | Conflitto | GestionePrenotazione | Mezzo già prenotato | 409 Conflict | Messaggio disponibilità | WARN |
| E-006 | Timeout esterno | Gateway Pagamento | Gateway non risponde | 502 Bad Gateway | Retry 3x, poi errore utente | ERROR |
| E-007 | Timeout esterno | Servizio Mappa | Mappa non risponde | 502 Bad Gateway | Retry 3x, poi errore utente | ERROR |
| E-008 | Timeout esterno | Mezzo:IoT | Veicolo non raggiungibile | 503 Service Unavailable | Crea segnalazione, notifica | ERROR |
| E-009 | Business rule | GestioneCorsa | Area non consentita | 422 Unprocessable | Messaggio area non valida | WARN |
| E-010 | Business rule | GestioneCorsa | Pagamento fallito | 402 Payment Required | Richiedi nuovo metodo | WARN |

### 14.2 Error Handling per Use Case

| UC | Error ID | Scenario | Risposta |
|----|----------|----------|----------|
| UT.01 | E-001, E-004 | Nessun mezzo trovato | Proposta espansione raggio o messaggio errore |
| UT.02 | E-005, E-006 | Mezzo non disponibile | Messaggio "mezzo non disponibile" |
| UT.03 | E-002, E-008, E-009 | QR invalido/mezzo non raggiungibile | Messaggio errore, impedisci avvio |
| UT.04 | E-007 | Servizio mappa non disponibile | Messaggio "percorso non calcolabile" |
| UT.05 | E-001, E-006 | Carta non valida/gateway down | Errore validazione, riprova |
| UT.06 | E-008 | Corsa non trovata | Messaggio errore, impedisci sospensione |
| UT.07 | E-009, E-010, E-008 | Area non consentita/pagamento fallito | Blocca terminazione, richiedi azione |
| OP.01 | E-008 | Veicolo offline | Crea segnalazione, notifica operatore |
| OP.02 | E-004 | Utente non trovato | Messaggio errore |
| OP.03 | E-004 | Lista prenotazioni vuota | Messaggio "nessuna prenotazione" |
| AP.01 | E-004 | Nessun dato nel periodo | Messaggio "dati non presenti" |
| AP.03 | E-005 | Conflitto restrizioni | Proposta sovrascrittura o annullamento |

---

## 15. XMI Artifacts and Corrections

| Artefatto XMI | Correzione | Fonte |
|--------------|------------|-------|
| `CalcoloPercorso()` in GestioneCorsa | RIMOSSO — non presente nei flussi UC | chiarimenti-vari.md punto 14 |
| `fineCorsa()` in GestioneCorsa | RIMOSSO — artefatto XMI | chiarimenti-vari.md punto 14 |
| `coorfinateFinali` (Servizio Mappa) | Corretto a `coordinateFinali` | chiarimenti-vari.md punto 14 |
| `EffettuaPagamento` (Gateway Pagamento) | Corretto a `effettuaPagamento` | chiarimenti-vari.md punto 14 |
| `attribute/attribute2` in AppPA | RIMOSSO — artefatto XMI | Class_Diagram_Spec |
| `id` ridichiarato in Operatore | Artefatto JOINED JPA, ignorato | Class_Diagram_Spec |
| `controllaDisponibilità` (con accento, void) | Ignorato; metodo valido è `controllaDisponibilita()` (bool) con overload | Class_Diagram_Spec |
| `reenvisibilita` in OP.01 | RIMOSSO — typo XMI | response2.md Critical #4 |
| Interfacce Orphan (Class6, Class14, Class12, ù, unnamed) | Artefatti esportazione VP, ignorati | Component_Diagram_Spec §5 |

---

## 16. References

### 16.1 Source Documents

| Document | Path | Type |
|----------|------|------|
| Documentazione di progetto | `docs/specs/documentazione.md` | Strategic/Reference |
| Chiarimenti vari | `docs/specs/chiarimenti-vari.md` | Reference |
| Chiarimenti UC | `docs/specs/UC/chiarimentiUC.md` | Reference |
| Class Diagram Spec | `docs/specs/Class_Diagram_Spec.cgd.md` | Implementation |
| Component Diagram Spec | `docs/specs/Component_Diagram_Spec.cgd.md` | Implementation |
| Class Spec | `docs/specs/Class_Spec.cgd.md` | Implementation |
| Component Spec | `docs/specs/Component_Spec.cgd.md` | Implementation |
| Interface Spec | `docs/specs/interface_Spec.cgd.md` | Implementation |
| Glossary | `docs/specs/Glossary.cgd.md` | Implementation |
| Cross-Reference Report | `docs/specs/response2.md` | Reference |
| Readiness Verdict | `docs/specs/verdict.md` | Reference |

### 16.2 UML Diagrams

| Diagramma | Path |
|-----------|------|
| Class Diagram (v1.8) | `docs/diagrams/class-diagram/classDiagram-v1.8-clean.uml` |
| Component Diagram (v1.0) | `docs/diagrams/component-diagram/componentDiagram-clean.uml` |
| Use Case Diagram (v1.1) | `docs/diagrams/use-case-diagram/UCdiagram-v1.1-clean.uml` |
| Sequence Diagrams (19) | `docs/diagrams/sequence-diagrams/UC.*/` |

### 16.3 Use Case Specifications

| UC | Path |
|----|------|
| UC.UT.01 | `docs/specs/UC/UC.UT.01.cgd.md` |
| UC.UT.02 | `docs/specs/UC/UC.UT.02.cgd.md` |
| UC.UT.03 | `docs/specs/UC/UC.UT.03.cgd.md` |
| UC.UT.04 | `docs/specs/UC/UC.UT.04.cgd.md` |
| UC.UT.05 | `docs/specs/UC/UC.UT.05.cgd.md` |
| UC.UT.06 | `docs/specs/UC/UC.UT.06.cgd.md` |
| UC.UT.07 | `docs/specs/UC/UC.UT.07.cgd.md` |
| UC.UT.08 | `docs/specs/UC/UC.UT.08.cgd.md` |
| UC.UT.09 | `docs/specs/UC/UC.UT.09.cgd.md` |
| UC.UT.10 | `docs/specs/UC/UC.UT.10.cgd.md` |
| UC.OP.01 | `docs/specs/UC/UC.OP.01.cgd.md` |
| UC.OP.02 | `docs/specs/UC/UC.OP.02.cgd.md` |
| UC.OP.03 | `docs/specs/UC/UC.OP.03.cgd.md` |
| UC.OP.04 | `docs/specs/UC/UC.OP.04.cgd.md` |
| UC.OP.05 | `docs/specs/UC/UC.OP.05.cgd.md` |
| UC.AP.01 | `docs/specs/UC/UC.AP.01.cgd.md` |
| UC.AP.02 | `docs/specs/UC/UC.AP.02.cgd.md` |
| UC.AP.03 | `docs/specs/UC/UC.AP.03.cgd.md` |
| UC.AP.04 | `docs/specs/UC/UC.AP.04.cgd.md` |
| UC.ATT.01 | `docs/specs/UC/UC.ATT.01.cgd.md` |

---

## 17. Pending Design Decisions

| ID | Descrizione | Impatto | Priorità |
|----|-------------|---------|----------|
| P-01 | Formula algoritmo calcolo costo finale (`aggiornaStima`) non completamente specificata | Implementazione `aggiornaStima` | MEDIUM |
| P-02 | Relazione tra `analisiStatoFlotta` (bool) e `getCondizioniMezzi` (lista Mezzo) — ridondanti o complementari? | Design OP.01 | MEDIUM |
| P-03 | Destroy message UML sufficiente per logout o serve metodo esplicito? | Implementazione logout | LOW |

---

*Documento generato il 2026-06-25. Basato su fonti verificate e cross-referenziate (Spec Gate 13/13, Clarity Gate 9/9).*

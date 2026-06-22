# Smart Mobility System — Master Specification (Consolidated)

**Versione:** 3.0 *(DESIGN TARGET — specifica architetturale pre-implementazione)*
**Team:** Cofee Coders
**Progetto:** Ingegneria del Software a.a. 2025/2026
**Architettura:** MVC Web-oriented con Controller Intermediario
**Data Rilascio:** 25/06/2026 *(TARGET — data futura, da confermare)*
**Natura:** Progetto accademico — sistemi esterni simulati (cfr. chiarimenti-vari.md punto 16)

**Riferimenti:** documentazione.md v3.0 (sorgente primaria), chiarimenti-vari.md, classDiagram-v1.8-clean (XMI), componentDiagram-clean (XMI), UCdiagram-v1.1-clean (XMI), ERdiagram-clean (PlantUML), diagrammi di sequenza (19 UC)

---

## 1. System Boundaries & Actors

### Attore (base — non loggato)
Utente generico non ancora autenticato. Può registrarsi o effettuare il login. Fino all'autenticazione interagisce esclusivamente con il modulo di Autenticazione.
- **Attributi da XMI:** `email`, `password`, `ruolo` (enum), `id`
- **Operazioni:** getter/setter per credenziali e ruolo
- **Nota:** "Attore" indica sia la generalizzazione astratta di Utente/Operatore/PA, sia la persona non loggata (chiarimenti-vari.md punto 9). L'attore non loggato può diventare uno dei tre ruoli dopo il login (UC.ATT.01).

### Utente (extends Attore)
Cittadino *di Zootropolis* [INFERRED — scenario didattico] che utilizza i servizi di bike/car/scooter sharing. Può cercare mezzi, prenotare, avviare corse, sospendere, terminare, gestire metodi di pagamento, ottimizzare percorsi.
- **Attributi specifici (da XMI):** `idUtente`, `nomeUtente`, `cognomeUtente`, `telefono`, `coordinateUtente`, `reportUtente`, `statoUtente` (enum), `numMezziPrenotati`
- **Nota:** `email` è ereditata da Attore, non ridichiarata in Utente.

### Operatore Tecnico (extends Attore)
Gestisce la flotta: visualizza distribuzione mezzi, blocca da remoto veicoli fuori zona, monitora stato mezzi.

### Operatore Servizio Clienti (extends Attore)
Modera gli utenti: consulta anagrafiche, applica sospensioni/disattivazioni, amministra prenotazioni.

### Pubblica Amministrazione — PA (extends Attore)
Ente comunale *di Zootropolis* [INFERRED — scenario didattico]. Accede a statistiche aggregate, analizza stato flotta, gestisce restrizioni geografiche.

**Gerarchia Attori:**
```
Attore (base astratta, id, email, password, ruolo)
├── Utente (idUtente, nomeUtente, cognomeUtente, telefono, coordinateUtente, ...)
├── Operatore (tipo: Tecnico | ServizioClienti)
│   ├── Operatore Tecnico
│   └── Operatore Servizio Clienti
└── PA (idPA)
```

---

## 2. Core Entities & Components (Model Layer)

Tutte le entità in questa sezione sono descritte come *progettate* dal Class Diagram UML (XMI 2.1) — rappresentano il progetto architetturale, non necessariamente l'implementazione finale.

### Mezzo
- **Responsabilità (da XMI):** Veicolo della flotta (bici, scooter, auto) con stato, posizione, autonomia e caratteristiche tecniche.
- **Attributi (da XMI):** `idMezzo`, `coordinateMezzo` (float), `stato` (enum: disponibile/prenotato/in_uso/sospeso/bloccato/manutenzione), `autonomia` (float), `costoOrario` (float), `velocitàMax` (float), `condizione` (String), `tipo` (String), `idFlotta`, `tempoDisponibilita` (time)
- **Operazioni (da XMI):** `getMezziInArea()`, `getDettagliMezzo()`, `getMezzibyFlotta()`, `getTempoDisponibilita()`

### Corsa
- **Responsabilità (da XMI):** Sessione di utilizzo di un mezzo dall'avvio al termine, con costi e coordinate.
- **Attributi (da XMI):** `idCorsa`, `costo` (float), `orarioInizio` (time), `orarioFine` (time), `coordinatePartenza` (String), `coordinateArrivo` (String), `idMetodoPagamento`, `idUtente`
- **Operazioni (da XMI):** `creaCorsa()`, `ricercaCorsa()`, `getCorseByPeriodo()`, `aggiornaCosto()`

### Utente (Model entity)
- **Responsabilità (da XMI):** Anagrafica e stato del cittadino registrato al sistema.
- **Attributi:** `idUtente`, `nomeUtente`, `cognomeUtente`, `telefono`, `coordinateUtente`, `reportUtente`, `statoUtente` (enum), `numMezziPrenotati`
- **Operazioni (da XMI):** `ricercaUtente()`, `azioneCorrettiva()`, `creaAccountUtente()`

### Attore (Model base)
- **Responsabilità (da XMI):** Classe base per autenticazione e autorizzazione (JOINED inheritance).
- **Attributi:** `id`, `email`, `password`, `ruolo` (enum)

### Operatore
- **Responsabilità (da XMI):** Estende Attore. Categoria professionale con tipo.
- **Attributi:** `tipo` (enum: Tecnico / ServizioClienti)
- **Nota:** PK ereditata da Attore (`id`).

### PA
- **Responsabilità (da XMI):** Estende Attore. Ente comunale con privilegi di analisi e gestione restrizioni.
- **Attributi:** `idPA` (coincide con `Attore.id`)

### MetodoPagamento
- **Responsabilità (da XMI):** Dati cifrati della carta di credito/debito associata a un utente.
- **Attributi:** `idMetodoPagamento` (PK, surrogato), `numCarta` (cifrato), `intestatarioCarta` (String)
- **Operazioni (da XMI):** `creaMetodoPagamento()`, `controllaMetodoEsistente()`, `getMetodoByUtente()`
- **Nota:** `idMetodoPagamento` è chiave surrogata — `numCarta` non è usato come PK per ragioni di sicurezza (PCI-DSS) e per evitare esposizione di dati sensibili nelle FK.

### Prenotazione
- **Responsabilità (da XMI):** Blocco temporaneo di un mezzo da parte di un utente (timeout: 15 min — da documentazione.md UC.UT.02).
- **Attributi:** `idPrenotazione`, `stato` (enum: valida/annullata/scaduta), `idUtente`, `idMezzo`, `orarioInizio` (time), `data` (date)
- **Operazioni (da XMI):** `creaPrenotazione()`, `getPrenotazioneByStato()`

### Segnalazione
- **Responsabilità (da XMI):** Report di anomalia su un mezzo (guasto, manutenzione, veicolo non raggiungibile).
- **Attributi:** `idSegnalazione`, `idMezzo`, `stato` (enum), `ora` (time), `data` (date)
- **Operazioni (da XMI):** `creaSegnalazione()`

### ZonaGeografica
- **Responsabilità (da XMI):** Area geografica con restrizioni di circolazione o sosta per i mezzi.
- **Attributi:** `idArea`, `tipoRestrizione` (enum), `noteRestrizione` (String), `zona` (LineString)
- **Operazioni (da XMI):** `getZone()`, `verificaSovrapposizioni()`, `checkArea()`, `creaZonaGeografica()`, `getRestrizioniZona()`

### Transito
- **Responsabilità (da XMI):** Associazione M:N tra Corsa e ZonaGeografica per tracciare le tratte percorse.
- **Attributi:** `idCorsa` (FK → Corsa), `idArea` (FK → ZonaGeografica)
- **Operazioni (da XMI):** `getTransitiByCorsa()`

---

## 3. Controller Layer

I Controller sono *progettati per* orchestrare il flusso MVC. Le operazioni elencate sono estratte dal Class Diagram UML (XMI 2.1).

### GestioneCorsa (Controller)
- **Responsabilità (da XMI):** Orchestrazione del ciclo di vita della corsa: avvio, sospensione, terminazione, calcolo percorso, stima costi.
- **Operazioni (da XMI):** `avviaCorsa()`, `terminaCorsa()`, `controllaDisponibilita()`, `aggiornaStima()`, `sospensioneCorsa()`, `richiediCalcoloPercorso()`, `richiediSblocco()`, `acquisisciSceltaMetodo()`

### RicercaMezzi (Controller)
- **Responsabilità (da XMI):** Query geolocalizzata su mezzi disponibili con supporto a raggi multipli (base 2km, esteso 5km — da documentazione.md UC.UT.01).
- **Operazioni (da XMI):** `visualizzaMezziVicini()`, `visualizzaSpecifiche()`

### GestorePagamento (Controller)
- **Responsabilità (da XMI):** Elaborazione transazioni e validazione metodi di pagamento (delega *progettata per* Gateway Pagamento esterno).
- **Operazioni (da XMI):** `pagamentoCorsa()`, `elaboraDatiCarta()`, `recuperaMetodiSalvati()`

### GestioneAree (Controller)
- **Responsabilità (da XMI):** CRUD di zone geografiche e verifica conflitti tra restrizioni sovrapposte.
- **Operazioni (da XMI):** `aggiornaRestrizioni()`, `analisiConflitti()`, `getZoneGeografiche()`

### GestioneFlotta (Controller)
- **Responsabilità (da XMI):** Monitoraggio e controllo remoto della flotta (blocco, manutenzione).
- **Operazioni (da XMI):** `analisiStatoFlotta()`, `bloccaMezzo()`, `avviaManutenzione()`, `getCondizioniMezzi()`

### GestioneStatistiche (Controller)
- **Responsabilità (da XMI):** Aggregazione dati corse e generazione report per la PA.
- **Operazioni (da XMI):** `generaFileStatistiche()`, `analisiTratte()`

### GestionePrenotazione (Controller)
- **Responsabilità (da XMI):** Gestione ciclo di vita prenotazioni con timeout automatico a 15 minuti.
- **Operazioni (da XMI):** `inviaRichiestaPrenotazione()`, `richiediLista()`, `annullaPrenotazione()`, `gestisciTimeout()`, `notificaScadenzaTempo()`

### GestioneUtenti (Controller)
- **Responsabilità (da XMI):** Moderazione account utente (sospensione/disattivazione).
- **Operazioni (da XMI):** `gestioneUtente()`, `cercaReport()`

### GestioneAutenticazione (Controller)
- **Responsabilità (da XMI):** Validazione credenziali, registrazione, gestione sessioni.
- **Operazioni (da XMI):** `verificaValidita()`, `inviaRichiestaLogout()`, `invioCredenziali()`

---

## 4. View Layer

Le View sono *progettate per* interfacciarsi esclusivamente con i Controller (pattern MVC intermediario). I metodi elencati sono estratti dal Class Diagram UML (XMI 2.1).

### AppUtente (View)
- **Responsabilità (da XMI):** Interfaccia utente cittadino. Gestisce la presentazione di mappe, QR code, dettagli mezzi, costi corse.
- **Operazioni chiave (da XMI):** `avviaRicercaMezzi()`, `selezionaMezzo()`, `scansionaQRCode()`, `inserisciDestinazione()`, `terminazioneCorsa()`, `sospendiCorsa()`, `inserisciDatiCarta()`, `richiestaLogout()`

### AppOperatoreTecnico (View)
- **Responsabilità (da XMI):** Dashboard operatore tecnico per visualizzazione flotta e comandi remoti.
- **Operazioni chiave (da XMI):** `richiedeStatoFlotta()`, `selezionaVeicolo()`, `visualizzaMezzi()`

### AppOperatoreSC (View)
- **Responsabilità (da XMI):** Interfaccia operatore servizio clienti per moderazione e amministrazione prenotazioni.
- **Operazioni chiave (da XMI):** `mostraReport()`, `aggiornaReport()`, `richiediListaPrenotazioni()`, `selezionaPrenotazione()`

### AppPA (View)
- **Responsabilità (da XMI):** Interfaccia PA per statistiche, analisi flotta e restrizioni geografiche.
- **Operazioni chiave (da XMI):** `selezionaIntervallo()`, `richiedeStatoFlotta()`, `avviaIntervento()`, `modificaRestrizioni()`, `confermaSovrascrittura()`

### Autenticazione (View)
- **Responsabilità (da XMI):** Interfaccia per login e registrazione (pre-auth).
- **Operazioni chiave (da XMI):** `mostraFormRegistrazione()`, `inserisciCredenziali()`, `registrazioneUtente()`

---

## 5. External Systems (Simulated)

*Tutti i sistemi esterni sono simulati — trattandosi di progetto universitario (chiarimenti-vari.md punto 16). Le interfacce sono definite ma l'implementazione reale non esiste.*

### Mezzo : IoT (IoT Device)
- **Responsabilità:** Interfaccia fisica col veicolo. Blocco/sblocco remoto, lettura QR code.
- **Operazioni:** `bloccoMezzoFisico()`, `sbloccoMezzoFisico()`

### Gateway Pagamento (Payment Gateway)
- **Responsabilità:** Processore di pagamento esterno. Convalida carte e processa transazioni.
- **Operazioni:** `effettuaPagamento()`, `convalidaCarta()`

### Servizio Mappa (Map Service)
- **Responsabilità:** Servizio di geolocalizzazione e routing esterno.
- **Operazioni:** `getPercorso()`

### DBMS (Database)
- **Responsabilità:** Persistenza dati. Tutti gli accessi ai dati passano attraverso query al database.

---

## 6. Use Case Logic & Flows

*I flussi use case sono tratti da documentazione.md (sezione 2.2.2), sorgente primaria per le specifiche. Le relazioni extends/include sono verificate rispetto alla documentazione e ai chiarimenti-vari.md punto 15.*

### UC.ATT.01 — Login
- **Attori:** Attore (Utente / Operatore / PA)
- **Flusso:** Inserimento credenziali → verifica email → verifica password → controllo stato account → creazione sessione → reindirizzamento ruolo-specifico
- **Flussi alternativi:** email non valida, password errata
- **Postcondizione:** Sessione attiva con permessi di ruolo
- **Include:** Nessuno | **Estende:** Nessuno

### UC.UT.01 — Ricerca Mezzi
- **Attori:** Utente
- **Flusso:** Avvio ricerca (raggio base 2km) → query geolocalizzata su Mezzo → risultati → selezione mezzo → visualizzazione specifiche tecniche
- **Alternativi:** Nessun risultato → proposta espansione raggio (5km) → accetta (ripeti query con raggio esteso) / rifiuta (errore); nessun risultato nel raggio esteso (errore)
- **Include:** Nessuno | **Estende:** Nessuno
- **Esteso da:** UC.UT.02 (Prenotazione Mezzo)

### UC.UT.02 — Prenotazione Mezzo
- **Attori:** Utente
- **Flusso:** Selezione mezzo disponibile → verifica disponibilità → aggiornamento stato "prenotato" → registrazione prenotazione → notifica + QR code
- **Alternativi:** Timeout 15 min → stato "disponibile" → notifica annullamento
- **Include:** Nessuno | **Estende:** UC.UT.01 (Ricerca Mezzi)
- **Esteso da:** UC.UT.03 (Gestione Corsa)

### UC.UT.03 — Gestione Corsa
- **Attori:** Utente
- **Flusso:** Scansione QR → verifica disponibilità mezzo → selezione metodo pagamento → avvio corsa → sblocco fisico IoT → stato "in uso" → aggiornamento periodico costo
- **Alternativi:** Mezzo non disponibile (errore)
- **Include:** UC.UT.05 (Metodo Pagamento), UC.UT.07 (Termina Corsa e Pagamento)
- **Estende:** UC.UT.02 (Prenotazione Mezzo)
- **Esteso da:** UC.UT.06 (Sospensione Corsa)

### UC.UT.04 — Ottimizzazione Percorso
- **Attori:** Utente
- **Flusso:** Inserimento destinazione → recupero restrizioni (ZonaGeografica) → calcolo percorso via Servizio Mappa → visualizzazione tracciato
- **Include:** Nessuno | **Estende:** Nessuno | **Esteso da:** Nessuno
- **Copre:** User story UT.06 (percorso più veloce), UT.10 (aree non accessibili)

### UC.UT.05 — Metodo Pagamento
- **Attori:** Utente
- **Flusso:** Selezione metodo esistente / nuovo → se nuovo: inserimento dati → convalida via Gateway Pagamento → controllo duplicati → salvataggio → associazione a sessione
- **Alternativi:** Carta non convalidata (errore), metodo già esistente (skip salvataggio)
- **Include:** Nessuno | **Estende:** Nessuno

### UC.UT.06 — Sospensione Corsa
- **Attori:** Utente
- **Flusso:** Richiesta sospensione → blocco fisico IoT → stato "sospeso" → generazione QR code → scansione ripresa → sblocco → stato "in uso" → aggiornamento costo (include tariffa sospensione)
- **Include:** Nessuno | **Estende:** UC.UT.03 (Gestione Corsa)

### UC.UT.07 — Termina Corsa e Pagamento
- **Attori:** Utente
- **Flusso:** Richiesta termine → verifica area consentita (ZonaGeografica.checkArea) → calcolo costo finale → transazione via Gateway Pagamento → blocco fisico IoT → stato "disponibile"
- **Alternativi:** Corsa non trovata, area non consentita, pagamento fallito (richiede nuovo metodo)
- **Include:** Nessuno | **Estende:** Nessuno

### UC.UT.08 — Registrazione Utente
- **Attori:** Utente (non registrato)
- **Flusso:** Richiesta registrazione → inserimento dati → validazione formato → controllo email univoca → creazione account (password cifrata) → conferma
- **Include:** Nessuno | **Estende:** Nessuno

### UC.UT.09 — Logout Utente
- **Attori:** Utente
- **Flusso:** Richiesta logout → termine sessione → disconnessione
- **Include:** Nessuno | **Estende:** Nessuno

### UC.OP.01 — Gestione Flotta
- **Attori:** Operatore Tecnico
- **Flusso:** Accesso mappa flotta → visualizzazione stato mezzi → selezione veicolo → comando blocco remoto → blocco fisico IoT → aggiornamento stato
- **Alternativi:** Connessione persa → creazione Segnalazione → alert operatore
- **Include:** Nessuno | **Estende:** Nessuno
- **Copre:** User story OP.01 (distribuzione mezzi), OP.04 (blocco remoto fuori zona)

### UC.OP.02 — Moderazione Utenti
- **Attori:** Operatore Servizio Clienti
- **Flusso:** Ricerca utente → visualizzazione dati + report → aggiornamento report → azione correttiva (sospensione/disattivazione) → notifica utente → disconnessione sessioni
- **Include:** Nessuno | **Estende:** Nessuno
- **Copre:** User story OP.02 (anagrafica utente), OP.03 (moderazione account)

### UC.OP.03 — Amministrazione Prenotazioni
- **Attori:** Operatore Servizio Clienti
- **Flusso:** Richiesta lista prenotazioni valide → selezione → annullamento → stato "annullata" + mezzo "disponibile"
- **Include:** Nessuno | **Estende:** Nessuno
- **Copre:** User story OP.05

### UC.OP.04 — Logout Operatore Tecnico
- Stessa struttura di UC.UT.09

### UC.OP.05 — Logout Operatore SC
- Stessa struttura di UC.UT.09

### UC.AP.01 — Monitoraggio Statistiche e Analisi Tratte
- **Attori:** PA
- **Flusso:** Selezione intervallo temporale → recupero corse nel periodo → per ogni corsa, recupero transiti (zone attraversate via Transito) → generazione file statistiche aggregate → download
- **Include:** Nessuno | **Estende:** Nessuno
- **Copre:** User story AP.01 (statistiche di utilizzo), AP.03 (tratte più utilizzate)

### UC.AP.02 — Analisi Stato Flotta
- **Attori:** PA
- **Flusso:** Richiesta stato flotta → recupero condizioni mezzi (Mezzo.getCondizioniMezzi) → dashboard riepilogativa → avvio intervento manutenzione → creazione Segnalazioni → stato "manutenzione"
- **Include:** Nessuno | **Estende:** Nessuno
- **Copre:** User story AP.02

### UC.AP.03 — Restrizioni Geografiche
- **Attori:** PA
- **Flusso:** Accesso mappa → visualizzazione zone esistenti → modifica restrizioni → verifica conflitti (sovrapposizioni via analisiConflitti) → salvataggio / sovrascrittura / annullamento
- **Include:** Nessuno | **Estende:** Nessuno
- **Copre:** User story AP.04 (vincolo: impedire mezzi in aree non designate)

### UC.AP.04 — Logout PA
- Stessa struttura di UC.UT.09

---

## 7. Architectural Relationships

### 7.1 MVC Layer Mapping

**View → Controller (tutte le interazioni View→Controller):**
- `AppUtente → GestioneCorsa`: Invio comandi corsa e richiesta stime
- `AppUtente → RicercaMezzi`: Richiesta ricerca geolocalizzata
- `AppUtente → GestorePagamento`: Gestione metodi di pagamento
- `AppUtente → GestioneAree`: Visualizzazione aree non accessibili
- `AppUtente → GestionePrenotazione`: Richiesta prenotazione
- `AppUtente → GestioneAutenticazione`: Login/logout/registrazione
- `AppOperatoreTecnico → GestioneFlotta`: Monitoraggio e controllo flotta
- `AppOperatoreSC → GestioneUtenti`: Moderazione account
- `AppOperatoreSC → GestionePrenotazione`: Amministrazione prenotazioni
- `AppPA → GestioneStatistiche`: Richiesta report e analisi
- `AppPA → GestioneFlotta`: Analisi stato flotta
- `AppPA → GestioneAree`: Gestione restrizioni geografiche
- `Autenticazione → GestioneAutenticazione`: Registrazione e login

**Controller → Model:**
- `GestioneCorsa → Corsa`: CRUD corsa, aggiornamento costi
- `GestioneCorsa → Mezzo`: Verifica e aggiornamento stato
- `RicercaMezzi → Mezzo`: Query mezzi per area
- `GestorePagamento → MetodoPagamento`: Recupero e creazione metodi
- `GestorePagamento → Corsa`: Aggiornamento pagamento
- `GestioneFlotta → Mezzo`: Aggiornamento stato e condizioni
- `GestioneFlotta → Segnalazione`: Creazione segnalazioni
- `GestionePrenotazione → Prenotazione`: CRUD prenotazioni
- `GestionePrenotazione → Mezzo`: Aggiornamento stato
- `GestioneUtenti → Utente`: Ricerca e azioni correttive
- `GestioneStatistiche → Corsa`: Query corse per periodo
- `GestioneStatistiche → Transito`: Analisi tratte
- `GestioneAree → ZonaGeografica`: CRUD zone e verifica conflitti
- `GestioneAutenticazione → Attore`: Validazione credenziali

**Controller → External Systems:**
- `GestioneCorsa → Mezzo : IoT`: Blocco/sblocco fisico remoto
- `GestioneCorsa → Servizio Mappa`: Calcolo percorso
- `GestorePagamento → Gateway Pagamento`: Transazione e validazione carta
- `RicercaMezzi → DBMS`: Query dati mezzi

**Controller → View (notifiche):**
- `GestioneCorsa → AppUtente`: Aggiornamenti corsa (costo in tempo reale, stato)
- `GestioneUtenti → AppUtente`: Notifica moderazione (sospensione/disattivazione)
- `GestioneFlotta → AppOperatoreTecnico`: Stato flotta
- `GestionePrenotazione → AppUtente`: Notifica scadenza timeout prenotazione
- `GestioneAutenticazione → Autenticazione`: Stato sessione
- `GestioneFlotta → AppPA`: Diagnostica flotta

### 7.2 Domain Associations (dal Class Diagram XMI)

| Entità A | Relazione | Entità B | Molteplicità | Descrizione |
|----------|-----------|----------|-------------|-------------|
| Utente | utilizza → | Corsa | 1 → 0..* | Un utente ha molte corse |
| Operatore | modera → | Utente | 0..* ↔ 0..* | Operatore modera utenti |
| GestorePagamento | verifica → | MetodoPagamento | 0..* ↔ 0..* | Verifica metodi di pagamento |
| RicercaMezzi | interroga → | Mezzo | 0..* ↔ 0..* | Ricerca interroga mezzi |
| GestioneCorsa | gestisce → | Mezzo | 0..* ↔ 0..* | GestioneCorsa gestisce mezzi |
| GestioneCorsa | effettua → | Corsa | 0..* ↔ 0..* | GestioneCorsa effettua la corsa |
| GestioneFlotta | amministra → | Mezzo | 1..* ↔ 0..* | GestioneFlotta amministra mezzi |
| GestioneFlotta | crea → | Segnalazione | 1..1 ↔ | Flotta crea segnalazioni |
| GestioneStatistiche | analizza → | Corsa | 0..* ↔ 0..* | Statistiche analizza corse |
| GestioneStatistiche | osserva → | Transito | 0..* ↔ 0..* | Statistiche osserva transiti |
| GestionePrenotazione | prenota → | Mezzo | 0..* ↔ 0..* | Prenotazione prenota mezzo |
| GestionePrenotazione | ha → | Prenotazione | 1..1 ↔ 1 | Prenotazione ha una prenotazione |
| GestionePrenotazione | genera → | Segnalazione | 1..1 ↔ | Timeout genera segnalazione |
| GestioneAree | aggiunge → | ZonaGeografica | 0..* ↔ | Aree aggiungono zone |
| ZonaGeografica | check → | GestioneCorsa | 0..* ↔ 0..* | Zona verifica coordinate per corsa |
| GestioneAutenticazione | autentica → | Attore | 1..1 ↔ 0..* | Autenticazione gestisce attori |
| Corsa | ha → | Prenotazione | 1..1 ↔ 1 | Corsa ha una prenotazione |

### 7.3 Include/Extend (Use Case Level)

| Use Case | Relazione | Target | Condizione |
|----------|-----------|--------|------------|
| UC.UT.02 (Prenotazione) | <<extend>> | UC.UT.01 (Ricerca) | Opzionale — la prenotazione estende la ricerca |
| UC.UT.03 (Gestione Corsa) | <<extend>> | UC.UT.02 (Prenotazione) | Opzionale — utente può avviare corsa da prenotazione |
| UC.UT.03 (Gestione Corsa) | <<include>> | UC.UT.05 (Metodo Pagamento) | Sempre — serve metodo pagamento per avviare corsa |
| UC.UT.03 (Gestione Corsa) | <<include>> | UC.UT.07 (Termina Corsa) | Sempre — la corsa termina con pagamento |
| UC.UT.06 (Sospensione) | <<extend>> | UC.UT.03 (Gestione Corsa) | Opzionale — utente può sospendere durante corsa attiva |

---

## 8. Component Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                          VIEW LAYER                             │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌────────┐│
│  │AppUtente │ │AppOp.SC  │ │AppOp.Tec │ │  AppPA   │ │Autent. ││
│  └────┬─────┘ └────┬─────┘ └────┬─────┘ └────┬─────┘ └───┬────┘│
│       │            │            │            │            │     │
├───────┼────────────┼────────────┼────────────┼────────────┼─────┤
│       ▼            ▼            ▼            ▼            ▼     │
│                        CONTROLLER LAYER                         │
│  ┌──────────┬──────────┬──────────┬──────────┬──────────┐      │
│  │Gestione  │Ricerca   │Gestore   │Gestione  │Gestione  │      │
│  │Corsa     │Mezzi     │Pagamento │Flotta    │Statist.  │      │
│  ├──────────┼──────────┼──────────┼──────────┼──────────┤      │
│  │Gestione  │Gestione  │Gestione  │Gestione  │          │      │
│  │Aree      │Prenotaz. │Utenti    │Autentic. │          │      │
│  └────┬─────┴────┬─────┴────┬─────┴────┬─────┴──────────┘      │
│       │          │          │          │                        │
├───────┼──────────┼──────────┼──────────┼────────────────────────┤
│       ▼          ▼          ▼          ▼                        │
│                        MODEL LAYER                              │
│  ┌──────────┬──────────┬──────────┬──────────┬──────────┐      │
│  │Utente    │Mezzo     │Corsa     │Prenotaz. │ZonaGeog. │      │
│  ├──────────┼──────────┼──────────┼──────────┼──────────┤      │
│  │Metodo    │Segnalaz. │Transito  │Attore    │Operatore │      │
│  │Pagamento │          │          │          │    / PA  │      │
│  └──────────┴──────────┴──────────┴──────────┴──────────┘      │
│       │                                                        │
├───────┼────────────────────────────────────────────────────────┤
│       ▼                                                        │
│                   EXTERNAL/SERVICE LAYER                        │
│  ┌──────────────┐ ┌──────────────┐ ┌──────────┐ ┌──────────┐ │
│  │Gateway       │ │Servizio      │ │  DBMS    │ │Mezzo:IoT │ │
│  │Pagamento     │ │Mappa         │ │          │ │          │ │
│  └──────────────┘ └──────────────┘ └──────────┘ └──────────┘ │
└─────────────────────────────────────────────────────────────────┘
```

---

## 9. Component Diagram — Interface Contracts

Le interfacce sono estratte dal Component Diagram XMI 2.1.

### Provided Interfaces (realizzate dai Controller / External)

| Interfaccia | Realizzata Da | Operazioni (desunte) |
|---|---|---|
| `Gestione Corsa` | GestioneCorsa | Avvio/termine/sospensione corsa, calcolo percorso |
| `Moderazione Utente` | GestioneUtenti | Ricerca e moderazione account |
| `Statistiche e Restrizioni` | GestioneStatistiche, GestioneAree | Report statistiche, gestione aree |
| `Amministrazione Flotta` | GestioneFlotta | Controllo e manutenzione flotta |
| `Gestione Sessioni` | GestioneAutenticazione | Login/logout/registrazione |
| `API Mappa` | Servizio Mappa (external) | Calcolo percorsi |
| `API Pagamento` | Gateway Pagamento (external) | Transazioni e validazione carte |
| `Connessione Dati` | DBMS (external) | Persistenza dati |
| `Gestione Dati` | Model (classi entità) | Accesso dati |

### Required Interfaces (consumati dalle View)

| Interfaccia | Consumata Da | Fornita Da |
|---|---|---|
| `Aggiornamenti Corsa` | AppUtente | Controller |
| `Eventi Utente` | AppUtente | Controller |
| `Stato Flotta` | AppOperatoreTecnico | Controller |
| `Diagnostica` | AppOperatoreTecnico | Controller |
| `Stato Sessione` | Autenticazione | Controller |

---

## 10. Architectural Constraints & Invariants

### 10.1 Vincoli di Sistema

1. **Autenticazione obbligatoria:** Qualsiasi operazione su corse, prenotazioni o pagamenti richiede una sessione attiva. L'autenticazione è un vincolo architetturale, non una funzionalità opzionale (chiarimenti-vari.md punto 3).
2. **Verifica geospaziale obbligatoria:** La terminazione di una corsa è consentita solo in aree designate (ZonaGeografica.checkArea). Il sistema *progettato per* impedire automaticamente il termine corsa fuori zona (chiarimenti-vari.md punto 4).
3. **Timeout prenotazione 15 minuti:** Una prenotazione non onorata entro 15 minuti è *progettata per essere* automaticamente annullata dal sistema (GestionePrenotazione.gestisciTimeout).
4. **Blocco corsa attiva:** Un utente è *progettato per* non poter avviare una nuova corsa se già in corsa attiva.
5. **RBAC (Role-Based Access Control):** Ogni attore ha accesso solo alle funzionalità del proprio ruolo. Il routing post-login è determinato dal ruolo *(progettato)*.
6. **Cifratura password:** Le password sono *progettate per essere* memorizzate in forma cifrata. La validazione avviene tramite GestioneAutenticazione.
7. **Pagamento obbligatorio:** Nessuna corsa termina senza transazione completata *(progettato)*. In caso di fallimento pagamento, viene richiesto un nuovo metodo *(progettato)*.
8. **Metodo di pagamento pre-esistente:** Per avviare una corsa è *progettato per* essere necessario un metodo di pagamento valido associato (selezionato durante UC.UT.05 o in fase di avvio corsa).
9. **Sessione singola:** All'atto del login, se esiste una sessione attiva, viene terminata implicitamente (modello standard).
10. **Disaccoppiamento View-Controller-Model:** Le View non *devono* interrogare mai direttamente il Model. Ogni comunicazione View ↔ Model è mediata dal Controller (pattern MVC documentato).
11. **Ruolo unico per sessione:** Dopo il login, il sistema istanzia *(progettato per)* la view corrispondente al ruolo dell'attore.
12. **Simulazione sistemi esterni:** Gateway Pagamento, Servizio Mappa, DBMS e Mezzo:IoT sono simulati (progetto universitario). Le interfacce sono definite ma l'implementazione è finta.
13. **Unicità email:** L'indirizzo email di registrazione deve essere univoco nel sistema.
14. **Cifratura dati pagamento:** I dati della carta sono *progettati per* essere memorizzati cifrati (requisito, non verifica implementativa).

### 10.2 Invarianti del Dominio

- Una `Corsa` è sempre associata a un `Utente` e un `Mezzo`.
- Lo stato del `Mezzo` segue il ciclo: `disponibile → prenotato → in_uso → (sospeso → in_uso)* → disponibile`, con stati aggiuntivi `bloccato` (da UC.OP.01 — blocco remoto operatore) e `manutenzione` (da UC.AP.02 — intervento PA). `bloccato` può transitare a `disponibile` dopo sblocco; `manutenzione` richiede intervento tecnico per tornare `disponibile`.
- Il `costo` di una Corsa è sempre >= 0 e include eventuali costi di sospensione.
- Una `Segnalazione` è generata solo quando un `Mezzo` non risponde o richiede manutenzione.
- Una `ZonaGeografica` ha sempre un `tipoRestrizione` (es. "no_parking", "no_access").
- Le `Prenotazioni` valide sono sempre associate a mezzi con stato "disponibile".

---

## 11. Anti-Patterns (DO NOT)

| Don't | Do Instead | Why |
|---|---|---|
| Memorizzare coordinate come stringhe | Usare tipo strutturato (es. Point/Geometry) | Operazioni geospaziali più efficienti |
| Gestire pagamenti senza convalida esterna | Delegare sempre a Gateway Pagamento | Sicurezza e compliance PCI-DSS |
| Permettere logout senza controllo sessione | Terminare sempre la sessione attiva | Previene session hijacking |
| Ignorare il timeout prenotazioni | Implementare GestionePrenotazione.gestisciTimeout() | Rischio di mezzi bloccati indefinitamente |
| Hardcodare raggi di ricerca | Parametrizzare (raggio base 2km, esteso 5km) | Flessibilità per futuri cambiamenti |
| Permettere modifica diretta del Model dalla View | Passare sempre attraverso i Controller | Violazione del pattern MVC |
| Esporre ID interni nelle API | Usare identificatori opachi (UUID) | Security by obscurity |
| Saltare la verifica area per termine corsa | Validare sempre con ZonaGeografica.checkArea | Vincolo architetturale AP.04 |

---

## 12. Data Model (ER)

### 12.1 Entità e Attributi

```
attore (id, email, password, ruolo:enum)
  ├── utente (id→attore, coordinate_utente, nome_utente, cognome_utente, telefono,
  │            num_mezzi_prenotati, report_utente, stato_utente:enum)
  ├── operatore (id→attore, tipo:enum)
  └── pa (id→attore)

mezzo (id_mezzo, coordinate_mezzo, stato:enum, autonomia, costo_orario,
       velocita_max, condizione, tipo, id_flotta, tempo_disponibilita)
  -- id_flotta è un raggruppamento logico (non tabella separata)

metodo_pagamento (id_metodo_pagamento, num_carta, intestatario_carta)

corsa (id_corsa, costo, orario_inizio, orario_fine, coordinate_partenza,
       coordinate_arrivo, id_metodo_pagamento→metodo_pagamento, id_utente→utente)

prenotazione (id_prenotazione, stato:enum, id_utente→utente, id_mezzo→mezzo,
              orario_inizio, data)

segnalazione (id_segnalazione, id_mezzo→mezzo, stato:enum, ora, data)

zona_geografica (id_area, tipo_restrizione, note_restrizione, zona:LineString)

transito (id_corsa→corsa, id_area→zona_geografica)  -- M:N
```

### 12.2 Relazioni Chiave

| FK | Tabella | Riferimento | Tipo |
|----|---------|-------------|------|
| utente.id | attore.id | 1:1 (JOINED inheritance) |
| operatore.id | attore.id | 1:1 (JOINED inheritance) |
| pa.id | attore.id | 1:1 (JOINED inheritance) |
| corsa.id_utente | utente.id | N:1 |
| corsa.id_metodo_pagamento | metodo_pagamento.id_metodo_pagamento | N:1 |
| prenotazione.id_utente | utente.id | N:1 |
| prenotazione.id_mezzo | mezzo.id_mezzo | N:1 |
| segnalazione.id_mezzo | mezzo.id_mezzo | N:1 |
| transito.id_corsa | corsa.id_corsa | N:M (bridge) |
| transito.id_area | zona_geografica.id_area | N:M (bridge) |

---

## 13. Key Architectural Decisions

1. **MVC con Controller Intermediario Centralizzato:** Scelto per gestire l'ecosistema eterogeneo di multiple View (AppUtente, AppOperatoreSC, AppPA, AppOperatoreTecnico, Autenticazione) e flussi asincroni IoT. I Controller centralizzano tutto il flusso di controllo e scambio dati (documentazione.md sezione 2.3).

2. **Separazione dei logout per ruolo:** 4 diagrammi di logout distinti (UC.UT.09, UC.OP.04, UC.OP.05, UC.AP.04) — scelta progettuale del gruppo per gestire sessioni specifiche per tipo di attore (chiarimenti-vari.md punto 13).

3. **Attore come generalizzazione (JOINED):** Pattern di ereditarietà con tabella `attore` base e tabelle `utente`, `operatore`, `pa` collegate 1:1 via FK.

4. **Gateway Pagamento e Servizio Mappa come componenti simulati:** Interfacce definite con metodi concreti ma senza implementazione reale (ambito universitario, chiarimenti-vari.md punto 16).

5. **Tracciamento geospaziale:** Uso di tipi geometrici (POINT, LINESTRING) per la gestione di posizioni e zone urbane.

6. **MetodoPagamento con chiave surrogata:** Scelto `id_metodo_pagamento` come PK invece del numero di carta per ragioni di sicurezza e per evitare esposizione di dati sensibili nelle relazioni FK.

7. **Flotta come raggruppamento logico:** `id_flotta` in Mezzo è un attributo identificativo, non una FK verso una tabella separata. La gestione della flotta è demandata ai controller (GestioneFlotta).

---

## 14. Glossary

| Termine | Definizione |
|---------|-------------|
| Attore | Generalizzazione di Utente, Operatore e PA (anche utente non loggato) |
| Corsa | Sessione di utilizzo di un mezzo dallo sblocco al blocco, con tracciamento costi |
| Flotta | Insieme di mezzi (raggruppamento logico tramite id_flotta) |
| IoT | Interfaccia simulata per controllo fisico del mezzo (blocco/sblocco) |
| Mezzo | Veicolo della flotta (bicicletta, auto, scooter) |
| PA | Pubblica Amministrazione comunale |
| Prenotazione | Blocco temporaneo di un mezzo (15 minuti massimo, timeout automatico) |
| Restrizione Geografica | Regola urbana che limita la sosta/transito in aree specifiche |
| Sospensione Corsa | Pausa temporanea della corsa con mantenimento del possesso del mezzo |
| Transito | Attraversamento di una zona geografica durante una corsa (tracciato M:N) |

---

## 15. References

### Diagram Sources
| Document | Location | Format |
|---|---|---|
| Class Diagram | `docs/diagrams/class-diagram/classDiagram-v1.8-clean.uml` | XMI 2.1 (Visual Paradigm) |
| Component Diagram | `docs/diagrams/component-diagram/componentDiagram-clean.uml` | XMI 2.1 (Visual Paradigm) |
| Use Case Diagram | `docs/diagrams/use-case-diagram/UCdiagram-v1.1-clean.uml` | XMI 2.1 (Visual Paradigm) |
| ER Diagram | `docs/diagrams/er-diagram/ERdiagram-clean.puml` | PlantUML |
| Sequence Diagrams | `docs/diagrams/sequence-diagrams/UC.*/` | XMI 2.1 (Visual Paradigm) |

### Specification Documents
| Document | Location | Role |
|---|---|---|
| Project Documentation | `docs/specs/documentazione.md` | Sorgente primaria (v3.0) |
| Project Clarifications | `docs/specs/chiarimenti-vari.md` | Interpretazioni e vincoli |
| Master Spec (CGD) | `docs/specs/Master_Spec.cgd.md` | Revisione epistemica |

### Implementation Details Location
| Content Type | Location |
|---|---|
| Anti-patterns | `docs/specs/Master_Spec_Final.md` (Section 11) |
| Use Case Specifications | `docs/specs/documentazione.md` (Section 2.2.2) |
| Sequence Flows | `docs/diagrams/sequence-diagrams/` |
| Architecture | `docs/specs/documentazione.md` (Section 2.3) |

---

*Documento consolidato dalle specifiche esistenti (root Master_Spec.md v1.0, specs Master_Spec.md v3.0, documentazione.md v3.0). Le contraddizioni sono state risolte usando documentazione.md come sorgente primaria (per chiarimenti-vari.md punto 15) e il Class Diagram XMI per la struttura dati. Il documento è pronto per Stream Coding e AI generation.*

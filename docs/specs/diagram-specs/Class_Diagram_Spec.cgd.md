---
clarity-gate-version: 2.1
document-type: Implementation
processed-by: AI + Cross-Reference Engine — classDiagram-v1.8-clean.uml (XMI 2.1), Master_Spec.cgd.md v4.0, componentDiagram-clean.uml
processed-date: 2026-06-25
clarity-status: CLEAR
hitl-status: REVIEWED
hitl-pending-count: 0
points-passed: 1-9
document-sha256: ffa5bb8a50e2f64e1351bdf64b4e1cb33293c9d54b0e8f0ffdb9bb8bfb82a77e
hitl-claims: []
---

# Class Diagram — Smart Mobility System

**Versione:** 1.8
**Team:** Cofee Coders
**Progetto:** Ingegneria del Software a.a. 2025/2026
**Architettura:** MVC Web-oriented con Controller Intermediario
**Diagramma:** `docs/diagrams/class-diagram/classDiagram-v1.8-clean.uml` (XMI 2.1 — Visual Paradigm 7.0.2)
**Documento Sorgente:** Master_Spec.cgd.md v4.0 (cross-reference validato)

**Fonti (ordine di priorità):**
1. `classDiagram-v1.8-clean.uml` (XMI 2.1) — struttura dati diagramma classi
2. `Master_Spec.cgd.md` v4.0 — specifica architetturale validata e cross-referenziata
3. `documentazione.md` v3.0 — descrizione pattern MVC e casi d'uso

**NOTE SUGLI ARTEFATTI XMI:**
- `CalcoloPercorso()` in GestioneCorsa → RIMOSSO (artefatto XMI, da chiarimenti-vari.md punto 14)
- `fineCorsa()` in GestioneCorsa → RIMOSSO (artefatto XMI, non presente nei flussi UC)
- `coorfinateFinali` (Servizio Mappa.getPercorso) → typo XMI, corretto a `coordinateFinali`
- `EffettuaPagamento` (Gateway Pagamento) → typo XMI, corretto a `effettuaPagamento`
- `attribute/attribute2` in AppPA → RIMOSSO (artefatto XMI)
- `id` ridichiarato in `Operatore` → artefatto JOINED inheritance JPA, ignorato semanticamente
- `controllaDisponibilità` (con accento, void) → artefatto XMI, da ignorare; il metodo valido è `controllaDisponibilita()` (bool) con overload

---

## 1. Model Layer — Entità del Dominio

### 1.1 Attore (abstract)
Classe base astratta per tutti gli attori del sistema. Utilizza strategia JOINED inheritance per la persistenza.

| Attributo | Tipo | Visibilità |
|-----------|------|------------|
| `id` | Integer | private |
| `email` | String | private |
| `password` | String (cifrata) | private |
| `ruolo` | RuoloAttore (enum) | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getId()` | Integer | — |
| `setId(id)` | void | id: Integer |
| `getEmail()` | String | — |
| `setEmail(email)` | void | email: String |
| `getPassword()` | String | — |
| `setPassword(password)` | void | password: String |
| `getRuolo()` | RuoloAttore | — |
| `setRuolo(ruolo)` | void | ruolo: RuoloAttore |

---

### 1.2 Utente (extends Attore)
Cittadino fruitore dei servizi di sharing. Può cercare mezzi, prenotare, avviare/sospendere/terminare corse, gestire metodi di pagamento e ottimizzare percorsi.

| Attributo | Tipo | Visibilità |
|-----------|------|------------|
| `idUtente` | Integer | private |
| `nomeUtente` | String | private |
| `cognomeUtente` | String | private |
| `telefono` | String | private |
| `coordinateUtente` | String | private |
| `reportUtente` | String | private |
| `statoUtente` | StatoUtente (enum) | private |
| `numMezziPrenotati` | Integer | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdUtente()` | Integer | — |
| `setIdUtente(idUtente)` | void | idUtente: Integer |
| `getNomeUtente()` | String | — |
| `setNomeUtente(nomeUtente)` | void | nomeUtente: String |
| `getCognomeUtente()` | String | — |
| `setCognomeUtente(cognomeUtente)` | void | cognomeUtente: String |
| `getTelefono()` | String | — |
| `setTelefono(telefono)` | void | telefono: String |
| `getCoordinateUtente()` | String | — |
| `setCoordinateUtente(coordinateUtente)` | void | coordinateUtente: String |
| `getReportUtente()` | String | — |
| `setReportUtente(reportUtente)` | void | reportUtente: String |
| `getStatoUtente()` | StatoUtente | — |
| `setStatoUtente(statoUtente)` | void | statoUtente: StatoUtente |
| `getNumMezziPrenotati()` | Integer | — |
| `setNumMezziPrenotati(numMezziPrenotati)` | void | numMezziPrenotati: Integer |
| `ricercaUtente(idUtente)` | Utente | idUtente: Integer |
| `azioneCorrettiva(azione)` | void | azione: String |
| `creaAccountUtente(nome, cognome, email, password, datanascita)` | void | nome: String, cognome: String, email: String, password: String, datanascita: date |

*Nota:* `email`, `password`, `id`, `ruolo` ereditati da Attore.

---

### 1.3 Operatore (extends Attore)
Categoria professionale del servizio. Distinto in Tecnico o Servizio Clienti tramite l'enum `tipo`.

| Attributo | Tipo | Visibilità |
|-----------|------|------------|
| `tipo` | TipoOperatore (enum) | private |
| `id` | Integer | private (ridichiarazione JOINED) |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getId()` | Integer | — |
| `setId(id)` | void | id: Integer |
| `getTipo()` | TipoOperatore | — |
| `setTipo(tipo)` | void | tipo: TipoOperatore |

---

### 1.4 PA (extends Attore)
Pubblica Amministrazione. Ente comunale con privilegi di analisi, statistiche e gestione restrizioni.

| Attributo | Tipo | Visibilità |
|-----------|------|------------|
| `idPA` | Integer | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdPA()` | Integer | — |
| `setIdPA(idPA)` | void | idPA: Integer |

---

### 1.5 Mezzo
Veicolo della flotta (bicicletta, scooter, auto). Traccia stato, posizione, autonomia e caratteristiche tecniche.

| Attributo | Tipo | Visibilità |
|-----------|------|------------|
| `idMezzo` | Integer | private |
| `coordinateMezzo` | String | private |
| `stato` | StatoMezzo (enum) | private |
| `autonomia` | float | private |
| `costoOrario` | float | private |
| `velocitàMax` | float | private |
| `condizione` | String | private |
| `tipo` | String | private |
| `idFlotta` | Integer | private |
| `tempoDisponibilita` | time | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdMezzo()` | Integer | — |
| `setIdMezzo(idMezzo)` | void | idMezzo: Integer |
| `getCoordinateMezzo()` | String | — |
| `setCoordinateMezzo(coordinateMezzo)` | void | coordinateMezzo: String |
| `getStato()` | StatoMezzo | — |
| `setStato(stato)` | void | stato: StatoMezzo |
| `getAutonomia()` | float | — |
| `setAutonomia(autonomia)` | void | autonomia: float |
| `getCostoOrario()` | float | — |
| `setCostoOrario(costoOrario)` | void | costoOrario: float |
| `getVelocitàMax()` | float | — |
| `setVelocitàMax(velocitàMax)` | void | velocitàMax: float |
| `getCondizione()` | String | — |
| `setCondizione(condizione)` | void | condizione: String |
| `getTipo()` | String | — |
| `setTipo(tipo)` | void | tipo: String |
| `getIdFlotta()` | Integer | — |
| `setIdFlotta(idFlotta)` | void | idFlotta: Integer |
| `getTempoDisponibilita()` | time | — |
| `setTempoDisponibilita(tempoDisponibilita)` | void | tempoDisponibilita: time |
| `getMezzibyFlotta(idFlotta)` | Mezzo | idFlotta: String |
| `getMezziInArea(coordinateUtente, raggio)` | Mezzo | coordinateUtente: String, raggio: float |
| `getDettagliMezzo()` | Mezzo | — |

---

### 1.6 Corsa
Sessione di utilizzo di un mezzo dall'avvio al termine. Include costi, coordinate e FK verso Utente e MetodoPagamento.

| Attributo | Tipo | Visibilità |
|-----------|------|------------|
| `idCorsa` | Integer | private |
| `costo` | float | private |
| `orario inizio` | time | private |
| `orario fine` | time | private |
| `coordinate partenza` | String | private |
| `coordinate arrivo` | String | private |
| `idMetodoPagamento` | Integer (FK) | private |
| `idUtente` | Integer (FK) | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdCorsa()` | Integer | — |
| `setIdCorsa(idCorsa)` | void | idCorsa: Integer |
| `getCosto()` | float | — |
| `setCosto(costo)` | void | costo: float |
| `getOrario inizio()` | time | — |
| `setOrario inizio(orario inizio)` | void | orario inizio: time |
| `getOrario fine()` | time | — |
| `setOrario fine(orario fine)` | void | orario fine: time |
| `getCoordinate partenza()` | String | — |
| `setCoordinate partenza(coordinate partenza)` | void | coordinate partenza: String |
| `getCoordinate arrivo()` | String | — |
| `setCoordinate arrivo(coordinate arrivo)` | void | coordinate arrivo: String |
| `getIdMetodoPagamento()` | Integer | — |
| `setIdMetodoPagamento(idMetodoPagamento)` | void | idMetodoPagamento: Integer |
| `getIdUtente()` | Integer | — |
| `setIdUtente(idUtente)` | void | idUtente: Integer |
| `creaCorsa(orarioinizio, coordinatePartenza, idUtente, idMezzo)` | void | orarioinizio: time, coordinatePartenza: String, idUtente: Integer, idMezzo: Integer |
| `ricercaCorsa(idCorsa)` | Corsa | idCorsa: Integer |
| `getCorseByPeriodo(dataInizio, dataFine)` | Corsa | dataInizio: date, dataFine: date |
| `aggiornaCosto(costo)` | void | costo: float |

---

### 1.7 MetodoPagamento
Dati cifrati della carta di credito/debito associata a un utente. Chiave surrogata per sicurezza [pattern PCI-DSS: idMetodoPagamento usato come PK invece del numero carta].

| Attributo | Tipo | Visibilità |
|-----------|------|------------|
| `NumCarta` | String (cifrato) | private |
| `intestatarioCarta` | String | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getNumCarta()` | String | — |
| `setNumCarta(NumCarta)` | void | NumCarta: String |
| `getIntestatarioCarta()` | String | — |
| `setIntestatarioCarta(intestatarioCarta)` | void | intestatarioCarta: String |
| `creaMetodoPagamento(NumCarta, intestatarioCarta)` | void | NumCarta: String, intestatarioCarta: String |
| `controllaMetodoEsistente(NumCarta)` | bool | NumCarta: String |
| `getMetodoByUtente(idUtente)` | MetodoPagamento | idUtente: Integer |

---

### 1.8 Prenotazione
Blocco temporaneo di un mezzo. Timeout automatico a 15 minuti [default, da UC.UT.02].

| Attributo | Tipo | Visibilità |
|-----------|------|------------|
| `idPrenotazione` | Integer | private |
| `stato` | StatoPrenotazione (enum) | private |
| `idUtente` | Integer (FK) | private |
| `idMezzo` | Integer (FK) | private |
| `orarioInizio` | time | private |
| `data` | date | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdPrenotazione()` | Integer | — |
| `setIdPrenotazione(idPrenotazione)` | void | idPrenotazione: Integer |
| `getStato()` | StatoPrenotazione | — |
| `setStato(stato)` | void | stato: StatoPrenotazione |
| `getIdUtente()` | Integer | — |
| `setIdUtente(idUtente)` | void | idUtente: Integer |
| `getIdMezzo()` | Integer | — |
| `setIdMezzo(idMezzo)` | void | idMezzo: Integer |
| `getOrarioInizio()` | time | — |
| `setOrarioInizio(orarioInizio)` | void | orarioInizio: time |
| `getData()` | date | — |
| `setData(data)` | void | data: date |
| `getPrenotazioneByStato(stato)` | Prenotazione | stato: StatoPrenotazione |
| `creaPrenotazione(idMezzo, idUtente, orarioInizio)` | void | idMezzo: Integer, idUtente: Integer, orarioInizio: time |

---

### 1.9 Segnalazione
Report di anomalia su un mezzo: guasto, manutenzione, veicolo non raggiungibile.

| Attributo | Tipo | Visibilità |
|-----------|------|------------|
| `idSegnalazione` | Integer | private |
| `idMezzo` | Integer (FK) | private |
| `stato` | StatoSegnalazione (enum) | private |
| `ora` | time | private |
| `data` | date | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdSegnalazione()` | Integer | — |
| `setIdSegnalazione(idSegnalazione)` | void | idSegnalazione: Integer |
| `getIdMezzo()` | Integer | — |
| `setIdMezzo(idMezzo)` | void | idMezzo: Integer |
| `getOra()` | time | — |
| `setOra(ora)` | void | ora: time |
| `getData()` | date | — |
| `setData(data)` | void | data: date |
| `getStato()` | StatoSegnalazione | — |
| `setStato(stato)` | void | stato: StatoSegnalazione |
| `creaSegnalazione(idMezzo, statoS, data, ora, note)` | void | idMezzo: Integer, statoS: StatoSegnalazione, data: date, ora: time, note: String |

---

### 1.10 ZonaGeografica
Area geografica con restrizioni di circolazione o sosta.

| Attributo | Tipo | Visibilità |
|-----------|------|------------|
| `idArea` | Integer | private |
| `tipoRestrizione` | TipoRestrizione (enum) | private |
| `noteRestrizione` | String | private |
| `zona` | LineString | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdArea()` | Integer | — |
| `setIdArea(idArea)` | void | idArea: Integer |
| `getTipoRestrizione()` | TipoRestrizione | — |
| `setTipoRestrizione(tipoRestrizione)` | void | tipoRestrizione: TipoRestrizione |
| `getNoteRestrizione()` | String | — |
| `setNoteRestrizione(noteRestrizione)` | void | noteRestrizione: String |
| `getZona()` | LineString | — |
| `setZona(zona)` | void | zona: LineString |
| `getZone()` | ZonaGeografica | — |
| `verificaSovrapposizioni(ZonaGeografica)` | bool | ZonaGeografica |
| `checkArea(coordinateUtente)` | bool | coordinateUtente: String |
| `creaZonaGeografica(idArea, tipoRestrizione, noteRestrizione, zona)` | void | idArea: Integer, tipoRestrizione: TipoRestrizione, noteRestrizione: String, zona: LineString |
| `getRestrizioniZona(coordinateUtente)` | ZonaGeografica | coordinateUtente: String |
| `salvaRestrizioni(idArea, tipoRestrizione, zona)` | bool | idArea: Integer, tipoRestrizione: TipoRestrizione, zona: LineString |

---

### 1.11 Transito
Associazione M:N tra Corsa e ZonaGeografica. Traccia le zone attraversate durante una corsa.

| Attributo | Tipo | Visibilità |
|-----------|------|------------|
| `corsa` | Integer (FK) | private |
| `area` | Integer (FK) | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getCorsa()` | Integer | — |
| `setCorsa(corsa)` | void | corsa: Integer |
| `getArea()` | Integer | — |
| `setArea(area)` | void | area: Integer |
| `getTransitiByCorsa(corsa)` | Transito | corsa: Integer |

---

## 2. Controller Layer

I Controller orchestrano il flusso MVC. Le View non interrogano mai direttamente il Model.

### 2.1 GestioneAutenticazione
Validazione credenziali, registrazione e gestione sessioni.

| Attributo | Visibilità |
|-----------|------------|
| `idGestioneAutenticazione` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdGestioneAutenticazione()` | Integer | — |
| `setIdGestioneAutenticazione(id)` | void | id: Integer |
| `verificaValidita(nome, cognome, email, password, datanascita)` | RuoloAttore | nome: String, cognome: String, email: String, password: String, datanascita: date |
| `invioCredenziali(email, password)` | RuoloAttore | email: String, password: String |
| `inviaRichiestaLogout(email)` | void | email: String |

---

### 2.2 GestioneUtenti
Moderazione account utenti: sospensione/disattivazione.

| Attributo | Visibilità |
|-----------|------------|
| `idGestioneUtenti` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdGestioneUtenti()` | Integer | — |
| `setIdGestioneUtenti(id)` | void | id: Integer |
| `gestioneUtente(idUtente)` | bool | idUtente: Integer |
| `cercaReport(idUtente)` | String | idUtente: Integer |

---

### 2.3 RicercaMezzi
Query geolocalizzata su mezzi disponibili (raggio base 2km [default, da UC.UT.01], raggio esteso 5km [default]).

| Attributo | Visibilità |
|-----------|------------|
| `idRicercaMezzi` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdRicercaMezzi()` | Integer | — |
| `setIdRicercaMezzi(id)` | void | id: Integer |
| `visualizzaMezziVicini(coordinateUtente, raggiob)` | Mezzo | coordinateUtente: String, raggiob: float |
| `visualizzaSpecifiche(idMezzo)` | Mezzo | idMezzo: Integer |

---

### 2.4 GestioneCorsa
Orchestrazione del ciclo di vita della corsa: avvio, sospensione, terminazione, calcolo percorso, stima costi.

| Attributo | Visibilità |
|-----------|------------|
| `idGestioneCorsa` | private |
| `idMetodoPagamento` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdGestioneCorsa()` | Integer | — |
| `setIdGestioneCorsa(id)` | void | id: Integer |
| `getIdMetodoPagamento()` | Integer | — |
| `setIdMetodoPagamento(id)` | void | id: Integer |
| `avviaCorsa()` | void | — |
| `terminaCorsa()` | Integer | — |
| `controllaDisponibilita()` | bool | — |
| `controllaDisponibilita(info)` | bool | info: String |
| `aggiornaStima(idCorsa)` | float | idCorsa: Integer |
| `sospensioneCorsa()` | bool | — |
| `richiediSblocco(qrCode)` | bool | QR_Code: String |
| `richiediCalcoloPercorso(coordinateUtente, destinazione)` | percorso | coordinateUtente: String, stringaDestinazione: String |
| `acquisisciSceltaMetodo(idMetodoPagamento)` | void | idMetodoPagamento: Integer |

---

### 2.5 GestorePagamento
Elaborazione transazioni e validazione metodi di pagamento. Delega a Gateway Pagamento esterno.

| Attributo | Visibilità |
|-----------|------------|
| `idGestorePagamento` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdGestorePagamento()` | Integer | — |
| `setIdGestorePagamento(id)` | void | id: Integer |
| `pagamentoCorsa(idUtente, idMetodoPagamento, costo)` | bool | idUtente: Integer, idMetodoPagamento: Integer, costo: float |
| `elaboraDatiCarta(idUtente, numCarta, dsCarta, cvv, intestatarioCarta)` | bool | idUtente: Integer, NumCarta: String, DsCarta: date, CVV: Integer, intestatarioCarta: String |
| `recuperaMetodiSalvati()` | MetodoPagamento | — |

---

### 2.6 GestioneFlotta
Monitoraggio e controllo remoto della flotta. Blocco e manutenzione mezzi.

| Attributo | Visibilità |
|-----------|------------|
| `idGestioneFlotta` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdGestioneFlotta()` | Integer | — |
| `setIdGestioneFlotta(id)` | void | id: Integer |
| `analisiStatoFlotta(idFlotta)` | bool | idFlotta: Integer (private) |
| `bloccaMezzo(idMezzo)` | bool | idMezzo: Integer |
| `avviaManutenzione(idFlotta)` | bool | idFlotta: Integer |
| `getCondizioniMezzi(idFlotta)` | Mezzo | idFlotta: Integer |

---

### 2.7 GestionePrenotazione
Ciclo di vita prenotazioni con timeout automatico a 15 minuti.

| Attributo | Visibilità |
|-----------|------------|
| `idGestionePrenotazione` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdGestionePrenotazione()` | Integer | — |
| `setIdGestionePrenotazione(id)` | void | id: Integer |
| `inviaRichiestaPrenotazione()` | String | — |
| `richiediLista()` | Prenotazione | — |
| `annullaPrenotazione(idPrenotazione)` | bool | idPrenotazione: Integer |
| `gestisciTimeout()` | void | — |
| `notificaScadenzaTempo(idPrenotazione)` | void | idPrenotazione: Integer |
| `concludiPrenotazione(idPrenotazione)` | void | idPrenotazione: Integer |

---

### 2.8 GestioneStatistiche
Aggregazione dati corse e generazione report statistici per la PA.

| Attributo | Visibilità |
|-----------|------------|
| `idGestioneStatistiche` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdGestioneStatistiche()` | Integer | — |
| `setIdGestioneStatistiche(id)` | void | id: Integer |
| `analisiTratte(dataInizio, dataFine)` | statistiche | dataInizio: date, dataFine: date |
| `generaFileStatistiche(corse)` | void | Corsa (private) |

---

### 2.9 GestioneAree
CRUD di zone geografiche e verifica conflitti tra restrizioni sovrapposte.

| Attributo | Visibilità |
|-----------|------------|
| `idGestioneAree` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdGestioneAree()` | Integer | — |
| `setIdGestioneAree(id)` | void | id: Integer |
| `aggiornaRestrizione(idArea, tipoRestrizione, noteRestrizione, zona)` | void | idArea: Integer, tipoRestrizione: TipoRestrizione, noteRestrizione: String, zona: LineString |
| `analisiConflitti(ZonaGeografica)` | bool | ZonaGeografica |
| `getZoneGeografiche()` | ZonaGeografica | — |

---

## 3. View Layer

Le View sono progettate per essere disaccoppiate dal Model [pattern MVC con Controller Intermediario]. Ogni interazione avviene tramite richieste ai Controller.

### 3.1 AppUtente
Interfaccia utente cittadino: mappe, QR code, dettagli mezzi, costi corse.

| Attributo | Visibilità |
|-----------|------------|
| `idUtente` | private |
| `idSessioneUtente` | private |

| Metodo | Visibilità | Ritorno | Parametri |
|--------|-----------|---------|-----------|
| `mostraErrore(msg)` | private | void | string |
| `mostraStima(idCorsa)` | private | void | idCorsa: Integer |
| `mostraSuccesso()` | private | void | — |
| `mostraFineCorsa()` | private | void | — |
| `mostraQRCode()` | private | void | — |
| `mostraRipresaCorsa()` | private | void | — |
| `mostraMetodi(metodi)` | private | void | MetodoPagamento |
| `mostraSceltaMetodi()` | private | void | — |
| `mostraMezzi(mezzi)` | private | void | Mezzo |
| `mostraMetodoConvalidato()` | private | void | — |
| `renderizzaDettagliVeicolo(mezzo)` | private | void | Mezzo |
| `scansionaQRCode(qrCode)` | public | void | QR_Code: String |
| `inserisciDatiCarta(idUtente, numCarta, dsCarta, cvv, intestatarioCarta)` | public | void | idUtente: Integer, NumCarta: String, DsCarta: date, CVV: Integer, intestatarioCarta: String |
| `apriAvvioCorsa()` | public | void | — |
| `terminazioneCorsa(idCorsa)` | public | void | idCorsa: Integer |
| `sospendiCorsa(idCorsa)` | public | void | idCorsa: Integer |
| `apriSezioneProfilo(idUtente)` | public | void | idUtente: Integer |
| `apriInserimentoMetodoPagamento(idUtente)` | public | void | idUtente: Integer |
| `selezionaMezzo(idMezzo)` | public | void | idMezzo: Integer |
| `inserisciDestinazione(indirizzoArrivo)` | public | void | indirizzoArrivo: String |
| `avviaRicercaMezzi(coordinateUtente, raggiob)` | public | void | coordinateUtente: String, raggiob: float |
| `confermaEspansione()` | public | void | — |
| `notificaAzione(idUtente, azione)` | public | void | idUtente: Integer, azione: String |
| `ottieniMetodiSalvati()` | public | void | — |
| `selezionaMetodo(numCarta)` | public | void | numCarta: String |
| `richiestaLogout(email)` | public | void | email: String |
| `getIdUtente()` | public | Integer | — |
| `setIdUtente(id)` | public | void | idUtente: Integer |
| `getIdSessioneUtente()` | public | Integer | — |
| `setIdSessioneUtente(id)` | public | void | idSessioneUtente: Integer |

---

### 3.2 AppOperatoreTecnico
Dashboard per operatore tecnico: visualizzazione flotta, comandi remoti, gestione manutenzione.

| Attributo | Visibilità |
|-----------|------------|
| `idOperatoreTecnico` | private |
| `idSessioneOperatoreTecnico` | private |

| Metodo | Visibilità | Ritorno | Parametri |
|--------|-----------|---------|-----------|
| `mostraSuccesso(msg)` | private | void | string |
| `mostraErrore(msg)` | private | void | string |
| `visualizzaMezzi(mezzi)` | private | void | listaMezzo |
| `richiedeStatoFlotta(idFlotta)` | public | void | idFlotta: Integer |
| `selezionaVeicolo(idMezzo)` | public | void | idMezzo: Integer |
| `richiestaLogout(email)` | public | void | email: String |
| `getIdOperatoreTecnico()` | public | Integer | — |
| `setIdOperatoreTecnico(id)` | public | void | idOperatoreTecnico: Integer |
| `getIdSessioneOperatoreTecnico()` | public | Integer | — |
| `setIdSessioneOperatoreTecnico(id)` | public | void | idSessioneOperatoreTecnico: Integer |

---

### 3.3 AppOperatoreSC
Interfaccia operatore servizio clienti: moderazione utenti e amministrazione prenotazioni.

| Attributo | Visibilità |
|-----------|------------|
| `idOperatoreSC` | private |
| `idSessioneOperatoreSC` | private |

| Metodo | Visibilità | Ritorno | Parametri |
|--------|-----------|---------|-----------|
| `mostraPrenotazioni()` | private | void | — |
| `mostraErrore(msg)` | private | void | string |
| `mostraSuccesso(msg)` | private | void | string |
| `mostraReport(idUtente)` | public | void | idUtente: Integer |
| `richiediListaPrenotazioni()` | public | void | — |
| `selezionaPrenotazione(idPrenotazione)` | public | void | idPrenotazione: Integer |
| `aggiornaReport(idUtente)` | public | void | idUtente: Integer |
| `richiestaLogout(email)` | public | void | email: String |
| `getIdOperatoreSC()` | public | Integer | — |
| `setIdOperatoreSC(id)` | public | void | idOperatoreSC: Integer |
| `getIdSessioneOperatoreSC()` | public | Integer | — |
| `setIdSessioneOperatoreSC(id)` | public | void | idSessioneOperatoreSC: Integer |

---

### 3.4 AppPA
Interfaccia Pubblica Amministrazione: statistiche, analisi flotta, restrizioni geografiche.

| Attributo | Visibilità |
|-----------|------------|
| `idPA` | private |
| `idSessionePA` | private |

| Metodo | Visibilità | Ritorno | Parametri |
|--------|-----------|---------|-----------|
| `mostraErrore(msg)` | private | void | string |
| `mostraSuccesso()` | private | void | — |
| `mostraStatistiche(statistiche)` | private | void | statistiche |
| `visualizzaMezzi(mezzi)` | private | void | listaMezzo |
| `mostraMappa(zone)` | private | void | listaZonaGeografica |
| `selezionaIntervallo(dataInizio, dataFine)` | public | void | dataInizio: date, dataFine: date |
| `richiedeStatoFlotta(idFlotta)` | public | void | idFlotta: Integer |
| `avviaIntervento(idFlotta)` | public | void | idFlotta: Integer |
| `selezionaMappa()` | public | void | — |
| `modificaRestrizioni(zona)` | public | void | ZonaGeografica |
| `confermaSovrascrittura(idArea, tipoRestrizione, noteRestrizione)` | public | void | idArea: Integer, tipoRestrizione: TipoRestrizione, noteRestrizione: String |
| `rifiutaSovrascrittura()` | public | void | — |
| `richiestaLogout(email)` | public | void | email: String |
| `getIdPA()` | public | Integer | — |
| `setIdPA(id)` | public | void | idPA: Integer |
| `getIdSessionePA()` | public | Integer | — |
| `setIdSessionePA(id)` | public | void | idSessionePA: Integer |

---

### 3.5 Autenticazione (View)
Interfaccia di pre-autenticazione per login e registrazione.

| Attributo | Visibilità |
|-----------|------------|
| `idAttore` | private |
| `idSessioneAttore` | private |

| Metodo | Visibilità | Ritorno | Parametri |
|--------|-----------|---------|-----------|
| `mostraFormRegistrazione()` | private | void | — |
| `inserisciCredenziali(nome, cognome, email, password, datanascita)` | public | void | nome: String, cognome: String, email: String, password: String, datanascita: date |
| `registrazioneUtente()` | public | void | — |
| `getIdAttore()` | public | Integer | — |
| `setIdAttore(id)` | public | void | idAttore: Integer |
| `getIdSessioneAttore()` | public | Integer | — |
| `setIdSessioneAttore(id)` | public | void | idSessioneAttore: Integer |

---

## 4. External Systems (Simulated)

Tutti i sistemi esterni sono simulati (progetto universitario, chiarimenti-vari.md punto 16).

### 4.1 Mezzo : IoT
Interfaccia fisica col veicolo per blocco/sblocco remoto.

| Attributo | Visibilità |
|-----------|------------|
| `idMezzoIoT` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdMezzoIoT()` | Integer | — |
| `setIdMezzoIoT(id)` | void | idMezzoIoT: Integer |
| `bloccoMezzoFisico(idMezzo)` | bool | idMezzo: Integer |
| `sbloccoMezzoFisico(idMezzo)` | bool | idMezzo: Integer |

---

### 4.2 Gateway Pagamento
Processore di pagamento esterno. Convalida carte e processa transazioni.

| Attributo | Visibilità |
|-----------|------------|
| `idGatewayPagamento` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdGatewayPagamento()` | Integer | — |
| `setIdGatewayPagamento(id)` | void | idGatewayPagamento: Integer |
| `effettuaPagamento(idMetodoPagamento, idCorsa)` | bool | idMetodoPagamento: Integer, idCorsa: Integer |
| `convalidaCarta(numCarta, dsCarta, cvv, intestatarioCarta)` | bool | NumCarta: String, DsCarta: date, CVV: Integer, intestatarioCarta: String |

---

### 4.3 Servizio Mappa
Servizio di geolocalizzazione e routing esterno.

| Attributo | Visibilità |
|-----------|------------|
| `idServizioMappa` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdServizioMappa()` | Integer | — |
| `setIdServizioMappa(id)` | void | idServizioMappa: Integer |
| `getPercorso(coordinateIniziali, coordinateFinali, restrizioni)` | datiPercorso | coordinateIniziali, coordinateFinali, restrizioni |

---

### 4.4 DBMS
Persistenza dati tramite interfaccia CRUD verso database relazionale.

| Attributo | Visibilità |
|-----------|------------|
| `idDBMS` | unspecified | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdDBMS()` | unspecified | — |
| `setIdDBMS(id)` | void | idDBMS |

---

## 5. Generalizzazioni (Ereditarietà)

| Classe Figlia | Classe Padre | Tipo |
|--------------|--------------|------|
| `Utente` | `Attore` | extends |
| `Operatore` | `Attore` | extends |
| `PA` | `Attore` | extends |

---

## 6. Associazioni del Dominio

| Entità A | Relazione | Entità B | Molt. A | Molt. B |
|----------|-----------|----------|---------|---------|
| `Mezzo` | utilizza | `Corsa` | 1 | 0..* |
| `GestioneUtenti` | modera | `Utente` | 0..* | 0..* |
| `GestorePagamento` | verifica | `MetodoPagamento` | 0..* | 0..* |
| `RicercaMezzi` | interroga | `Mezzo` | 0..* | 0..* |
| `GestioneCorsa` | amministra | `Mezzo` | 1..* | 0..* |
| `GestioneCorsa` | effettua | `Corsa` | 0..* | 1 |
| `GestioneFlotta` | gestisce | `Mezzo` | 0..* | 0..* |
| `GestioneFlotta` | crea | `Segnalazione` | 0..* | 1 |
| `GestioneStatistiche` | analizza | `Corsa` | 0..* | 0..* |
| `GestioneStatistiche` | osserva | `Transito` | 0..* | 0..* |
| `GestionePrenotazione` | prenota | `Mezzo` | 1..* | 0..* |
| `GestionePrenotazione` | ha | `Prenotazione` | 0..* | 1 |
| `GestionePrenotazione` | genera | `Segnalazione` | 0..* | 1 |
| `GestioneAree` | aggiunge | `ZonaGeografica` | 0..* | 0..* |
| `ZonaGeografica` | esegue check | `GestioneCorsa` | 0..* | 0..* |
| `GestioneAutenticazione` | autentica | `Attore` | 1 | 1 |

---

## 7. Dipendenze View → Controller

| View | Controller Dipendente |
|------|----------------------|
| `AppUtente` | GestioneCorsa, GestorePagamento, RicercaMezzi, GestionePrenotazione, GestioneAutenticazione |
| `AppOperatoreSC` | GestioneUtenti, GestionePrenotazione, GestioneAutenticazione |
| `AppPA` | GestioneFlotta, GestioneStatistiche, GestioneAree, GestioneAutenticazione |
| `AppOperatoreTecnico` | GestioneFlotta, GestioneAutenticazione |
| `Autenticazione` | GestioneAutenticazione |

---

## 8. Dipendenze Controller → External Systems

| Controller | External System |
|-----------|----------------|
| `GestioneCorsa` | Servizio Mappa, Mezzo : IoT |
| `GestionePrenotazione` | Gateway Pagamento |

---

## 9. Dipendenze Model → DBMS

Tutte le entità del Model dipendono da DBMS per la persistenza:

| Entità Model |
|-------------|
| `Attore` |
| `Corsa` |
| `Mezzo` |
| `MetodoPagamento` |
| `Prenotazione` |
| `Segnalazione` |
| `Transito` |
| `ZonaGeografica` |

---

## 10. Summary Statistics

| Metrica | Valore |
|---------|--------|
| Classi Model | 11 (Attore, Utente, Operatore, PA, Mezzo, Corsa, MetodoPagamento, Prenotazione, Segnalazione, ZonaGeografica, Transito) |
| Controller | 9 (GestioneAutenticazione, GestioneUtenti, RicercaMezzi, GestioneCorsa, GestorePagamento, GestioneFlotta, GestionePrenotazione, GestioneStatistiche, GestioneAree) |
| View | 5 (AppUtente, AppOperatoreTecnico, AppOperatoreSC, AppPA, Autenticazione) |
| External Systems | 4 (Mezzo:IoT, Gateway Pagamento, Servizio Mappa, DBMS) |
| Generalizzazioni | 3 (Utente→Attore, Operatore→Attore, PA→Attore) |
| Associazioni | 16 |
| Dipendenze View→Controller | 14 |
| Dipendenze Controller→External | 3 |
| Dipendenze Model→DBMS | 8 |
| Totale attributi Model | 47 |
| Totale metodi Model (incl. getter/setter) | 126 |
| Totale metodi Controller | 54 |
| Totale metodi View (incl. XMI artifacts) | 78 (30 AppUtente, 10 AppOperatoreTecnico, 12 AppOperatoreSC, 19 AppPA, 7 Autenticazione) |
| Totale metodi View (excl. XMI artifacts) | 76 (esclusi getAttribute/setAttribute su AppPA) |
| Totale metodi External | 13 |
| Artefatti XMI rimossi (metodi) | 5 (CalcoloPercorso, fineCorsa, controllaDisponibilità, getAttribute, setAttribute) |
| Correzzioni typo XMI | 2 (coorfinateFinali→coordinateFinali, EffettuaPagamento→effettuaPagamento) |

---

## 11. Traceability Matrix: XMI → Documento

| XMI ID | Elemento XMI | Sezione Documento | Note |
|--------|-------------|-------------------|------|
| `hP7MYnmD.AACAQ2H` | Class: Attore | §1.1 | Classe astratta base |
| `TP7MYnmD.AACAQ2W` | Class: Utente | §1.2 | Estende Attore |
| `DP7MYnmD.AACAQ2U` | Class: Operatore | §1.3 | Estende Attore |
| `KdrcYnmD.AACAQ9j` | Class: PA | §1.4 | Estende Attore |
| `bP7MYnmD.AACAQ2e` | Class: Mezzo | §1.5 | — |
| `TtB6YnmD.AACARJW` | Class: Corsa | §1.6 | — |
| `NjhuYnmD.AACARRj` | Class: MetodoPagamento | §1.7 | Senza idMetodoPagamento nell'XMI |
| `caKzwXmD.AACARED` | Class: Prenotazione | §1.8 | — |
| `jrF2wXmD.AACAQoY` | Class: Segnalazione | §1.9 | — |
| `s0i1wXmD.AACAQ6x` | Class: ZonaGeografica | §1.10 | — |
| `CGCld3mD.AACARXf` | Class: Transito | §1.11 | — |
| `enehh3mD.AACAQ3T` | Class: GestioneAutenticazione | §2.1 | — |
| `bp_NKnmD.AACAQlK` | Class: GestioneUtenti | §2.2 | — |
| `xostKnmD.AACAQpA` | Class: RicercaMezzi | §2.3 | — |
| `SKstKnmD.AACAQpH` | Class: GestioneCorsa | §2.4 | — |
| `aQstKnmD.AACAQo5` | Class: GestorePagamento | §2.5 | — |
| `m.1tKnmD.AACAQqZ` | Class: GestioneFlotta | §2.6 | — |
| `LJIOwXmD.AACAQpM` | Class: GestionePrenotazione | §2.7 | — |
| `PO_qnnmD.AACAQnq` | Class: GestioneStatistiche | §2.8 | — |
| `RLyOwXmD.AACAQqB` | Class: GestioneAree | §2.9 | — |
| `fdM2RXmD.AACAQpv` | Class: AppUtente | §3.1 | 30 metodi totali |
| `iEOHRXmD.AACARKJ` | Class: AppOperatoreTecnico | §3.2 | — |
| `psSuRXmD.AACAQvL` | Class: AppOperatoreSC | §3.3 | — |
| `LPieRXmD.AACAQwu` | Class: AppPA | §3.4 | — |
| `RaMBh3mD.AACAQyQ` | Class: Autenticazione | §3.5 | — |
| `0RmQxXmD.AACARc5` | Class: Mezzo : IoT | §4.1 | External |
| `kPeQxXmD.AACARdI` | Class: Gateway Pagamento | §4.2 | External |
| `ad.QxXmD.AACARdP` | Class: Servizio Mappa | §4.3 | External |
| `3AUhp3mD.AACAR5Q` | Class: DBMS | §4.4 | External |

---

## HITL Verification Record

### Round A: Derived Data Confirmation

Tutti i dati strutturali sono stati derivati dal parsing XMI di `classDiagram-v1.8-clean.uml` e cross-referenziati con `Master_Spec.cgd.md` v4.0 (già REVIEWED con 17/17 claim verificati). Nessun claim nuovo richiede Round A aggiuntivo.

### Round B: True HITL Verification

Nessun claim richiede Round B — documento derivato esclusivamente da fonti già verificate (XMI + Master_Spec).

---

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | REVIEWED

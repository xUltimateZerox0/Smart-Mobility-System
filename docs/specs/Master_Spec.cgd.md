---
clarity-gate-version: 2.1
document-type: Implementation
processed-date: 2026-06-23
processed-by: Claude (AI) + Cross-Reference Engine — documentazione.md (primary), Master_Spec.md v3.0, classDiagram-v1.8-clean.uml, chiarimenti-vari.md punti 19-21
clarity-status: CLEAR
hitl-status: REVIEWED
hitl-pending-count: 0
points-passed: 1-9
document-sha256: 2949054942244b9cc17e501d5188b3a99b7be38638b6b0ecc9713f5f4e4909cf
hitl-claims:
  - id: claim-6d3b7c006
    text: "I valori enum per StatoSegnalazione sono: aperta, in_lavorazione, chiusa"
    value: "Confermati da documentazione e chiarimenti team"
    source: "documentazione.md + feedback team Cofee Coders"
    location: "Enumerations/StatoSegnalazione"
    round: A
    confirmed-by: Team Cofee Coders (via user)
    confirmed-date: 2026-06-22
  - id: claim-2d4e6f010
    text: "I valori enum per TipoRestrizione sono: divieto_parcheggio, ZTL, limite_velocita"
    value: "Confermati — priorità a documentazione.md e Master_Spec"
    source: "Master_Spec.md §2 + chiarimenti team"
    location: "Enumerations/TipoRestrizione"
    round: A
    confirmed-by: Team Cofee Coders (via user)
    confirmed-date: 2026-06-22
  - id: claim-3c8d1e012
    text: "CalcoloPercorso() non esiste — solo richiediCalcoloPercorso() esiste"
    value: "Rimosso metodo spurio — era artefatto XMI"
    source: "Conferma team: XMI ha errori di esportazione (chiarimenti-vari.md punto 14)"
    location: "GestioneCorsa/methods"
    round: A
    confirmed-by: Team Cofee Coders (via user)
    confirmed-date: 2026-06-22
  - id: claim-2b9c8d004
    text: "attribute2 (Corsa) e attribute (AppPA) sono artefatti XMI e non esistono"
    value: "Rimossi — chiarimenti-vari.md punto 14"
    source: "XMI anomalies / chiarimenti-vari.md punto 14"
    location: "XMI/anomalies/removed"
    round: A
    confirmed-by: Team Cofee Coders (via user)
    confirmed-date: 2026-06-22
  - id: claim-7f2a5b013
    text: "Le coordinate (coordinateMezzo, coordinatePartenza, coordinateArrivo, coordinateUtente) sono di tipo String con tre float (x,y,z) parsati"
    value: "String — memorizza tre coordinate spaziali float come unica stringa"
    source: "Conferma team: design decision per flessibilità parsing x,y,z"
    location: "Model/coordinates"
    round: A
    confirmed-by: Team Cofee Coders (via user)
    confirmed-date: 2026-06-22
  - id: claim-e7f6a003
    text: "idMetodoPagamento è la chiave primaria di MetodoPagamento"
    value: "PK surrogata — confermata dal team"
    source: "Conferma team Cofee Coders"
    location: "MetodoPagamento/idMetodoPagamento"
    round: A
    confirmed-by: Team Cofee Coders (via user)
    confirmed-date: 2026-06-22
  - id: claim-2e5c7a017
    text: "Zootropolis è scenario didattico — sistema per città generica con copertura WiFi totale"
    value: "Dominio: città generica con WiFi full-range. Zootropolis ignorato."
    source: "Conferma team: supercazzola, ignorare. Città qualsiasi."
    location: "Domain/city"
    round: A
    confirmed-by: Team Cofee Coders (via user)
    confirmed-date: 2026-06-22
  - id: claim-1a7b4c019
    text: "AppUtente con tutti i metodi (mostraErrore, mostraQRCode, mostraSuccesso, ecc.) è corretto — 30 metodi totali inclusi 11 privati"
    value: "30 metodi — 11 privati + 19 pubblici inclusi getter/setter"
    source: "XMI class diagram + conferma team"
    location: "AppUtente/methods"
    round: A
    confirmed-by: Team Cofee Coders (via user)
    confirmed-date: 2026-06-22
  - id: claim-4c8a2f023
    text: "DBMS segue best practice standard con interfaccia CRUD generica"
    value: "Design: interfaccia CRUD standard (Create, Read, Update, Delete) verso DB relazionale"
    source: "documentazione.md §2.3 (Model passivo con metodi getter/setter) + pattern MVC standard"
    location: "DBMS/design"
    round: A
    confirmed-by: Team Cofee Coders (via user)
    confirmed-date: 2026-06-22
  - id: claim-5a7b9c011
    text: "GestioneCorsa.controllaDisponibilita() ha overload: no-args e (string)"
    value: "Due overload confermati da XMI e documentazione UC flows"
    source: "XMI class diagram"
    location: "GestioneCorsa/methods"
    round: A
    confirmed-by: Team Cofee Coders (via user)
    confirmed-date: 2026-06-22
  - id: claim-8a5f9e007
    text: "StatoPrenotazione: 4 valori (attiva, scaduta, annullata, completata) — XMI ne ha 4, Master_Spec v3.0 ne aveva 3"
    value: "4 valori: attiva, scaduta, annullata, completata"
    source: "XMI class diagram — 4 enum literals"
    location: "Enumerations/StatoPrenotazione"
    round: A
    confirmed-by: Team Cofee Coders (via user)
    confirmed-date: 2026-06-22
  - id: claim-9e1d5b024
    text: "Operatore.id e getId()/setId() sono override per JOINED inheritance JPA/ORM"
    value: "Ridichiarazione ereditata — artefatto XMI, ignorato semanticamente"
    source: "XMI + pattern JOINED inheritance"
    location: "Operatore/id"
    round: A
    confirmed-by: Team Cofee Coders (via user)
    confirmed-date: 2026-06-22
  - id: claim-7b4d1e022
    text: "Servizio Mappa.getPercorso() ha typo 'coorfinateFinali' — correzione a 'coordinateFinali'"
    value: "Typo XMI corretto — confermato dal team come errore esportazione"
    source: "XMI anomaly / chiarimenti-vari.md punto 14"
    location: "ServizioMappa/getPercorso"
    round: A
    confirmed-by: Team Cofee Coders (via user)
    confirmed-date: 2026-06-22
  - id: claim-5d8e2f020
    text: "Gateway Pagamento.EffettuaPagamento() (E maiuscola) — typo XMI, corretto a effettuaPagamento()"
    value: "Correzione camelCase applicata"
    source: "XMI anomaly / chiarimenti-vari.md punto 14"
    location: "GatewayPagamento/EffettuaPagamento"
    round: A
    confirmed-by: Team Cofee Coders (via user)
    confirmed-date: 2026-06-22
---

# Smart Mobility System — Master Specification (FINAL)

**Versione:** 4.0 *(AI-Ready — cross-reference completo e validato)*
**Team:** Cofee Coders
**Progetto:** Ingegneria del Software a.a. 2025/2026
**Architettura:** MVC Web-oriented con Controller Intermediario
**Data Rilascio:** 25/06/2026 *(TARGET)*
**Natura:** Progetto accademico — sistemi esterni simulati *(chiarimenti-vari.md punto 16)*
**Dominio:** Città generica con copertura WiFi full-range — nessuna limitazione geografica reale

**Fonti (in ordine di priorità):**
1. `documentazione.md` v3.0 — sorgente primaria *(chiarimenti-vari.md punto 15)*
2. `Master_Spec.md` v3.0 — specifica architetturale consolidata
3. `classDiagram-v1.8-clean.uml` (XMI 2.1) — struttura dati
4. `chiarimenti-vari.md` — interpretazioni e vincoli

**Avvertenza:** Tutti i metodi, attributi ed enumerazioni in questo documento sono stati cross-referenziati tra le quattro fonti. I conflitti sono stati risolti dando priorità a documentazione.md. Gli artefatti XMI noti (metodi/attributi senza nome, typo di esportazione) sono stati rimossi.

---

## 1. Enumerazioni

### RuoloAttore
| Valore | Descrizione |
|--------|-------------|
| `Utente` | Cittadino fruitore dei servizi |
| `Operatore` | Personale tecnico o servizio clienti |
| `PA` | Pubblica Amministrazione |

### TipoOperatore
| Valore | Descrizione |
|--------|-------------|
| `OperatoreTecnico` | Gestione flotta e mezzi |
| `OperatoreSC` | Servizio clienti e moderazione |

### StatoUtente
| Valore | Descrizione |
|--------|-------------|
| `attivo` | Account attivo e funzionante |
| `sospeso` | Account temporaneamente bloccato |
| `disattivato` | Account permanentemente disabilitato |

### StatoMezzo
| Valore | Descrizione |
|--------|-------------|
| `disponibile` | Libero e prenotabile |
| `prenotato` | Bloccato da prenotazione attiva |
| `in_uso` | Corsa in corso |
| `sospeso` | Corsa in pausa temporanea |
| `bloccato` | Blocco remoto da operatore |
| `manutenzione` | Fuori servizio per intervento tecnico |

### StatoPrenotazione
| Valore | Descrizione |
|--------|-------------|
| `attiva` | Prenotazione valida entro i 15 minuti |
| `scaduta` | Timeout superato, automaticamente annullata |
| `annullata` | Cancellata da utente o operatore SC |
| `completata` | Prenotazione onorata (corsa avviata) |

### StatoSegnalazione
| Valore | Descrizione |
|--------|-------------|
| `aperta` | Segnalazione creata, in attesa |
| `in_lavorazione` | In gestione da parte del team |
| `chiusa` | Risolta e archiviata |

### TipoRestrizione
| Valore | Descrizione |
|--------|-------------|
| `divieto_parcheggio` | No parking zone |
| `ZTL` | Zona a Traffico Limitato |
| `limite_velocita` | Speed limit zone |

---

## 2. Model Layer — Entità

*Nota sulle coordinate:* Tutti gli attributi di coordinate (`coordinateMezzo`, `coordinateUtente`, `coordinatePartenza`, `coordinateArrivo`) sono di tipo **String**. Memorizzano tre coordinate spaziali float (x, y, z) parsate come unica stringa.

### Attore (abstract, base class)
| Attributo | Tipo | Visibilità |
|-----------|------|------------|
| `id` | — (PK) | private |
| `email` | String | private |
| `password` | String (cifrata) | private |
| `ruolo` | RuoloAttore | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getEmail()` | String | — |
| `setEmail(email)` | void | email: String |
| `getId()` | — | — |
| `setId(id)` | void | id |
| `getPassword()` | String | — |
| `setPassword(password)` | void | password: String |
| `getRuolo()` | RuoloAttore | — |
| `setRuolo(ruolo)` | void | ruolo: RuoloAttore |

---

### Utente (extends Attore)
Cittadino che utilizza i servizi di bike/car/scooter sharing. Può cercare mezzi, prenotare, avviare corse, sospendere, terminare, gestire metodi di pagamento, ottimizzare percorsi.

| Attributo | Tipo | Visibilità |
|-----------|------|------------|
| `idUtente` | — | private |
| `nomeUtente` | String | private |
| `cognomeUtente` | String | private |
| `telefono` | String | private |
| `coordinateUtente` | String | private |
| `reportUtente` | String | private |
| `statoUtente` | StatoUtente | private |
| `numMezziPrenotati` | int | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getCoordinateUtente()` | String | — |
| `setCoordinateUtente(coordinateUtente)` | void | coordinateUtente: String |
| `getNumMezziPrenotati()` | int | — |
| `setNumMezziPrenotati(numMezziPrenotati)` | void | numMezziPrenotati: int |
| `getNomeUtente()` | String | — |
| `setNomeUtente(nomeUtente)` | void | nomeUtente: String |
| `getCognomeUtente()` | String | — |
| `setCognomeUtente(cognomeUtente)` | void | cognomeUtente: String |
| `getTelefono()` | String | — |
| `setTelefono(telefono)` | void | telefono: String |
| `getReportUtente()` | String | — |
| `setReportUtente(reportUtente)` | void | reportUtente: String |
| `getIdUtente()` | — | — |
| `setIdUtente(idUtente)` | void | idUtente |
| `ricercaUtente(idUtente)` | Utente | idUtente |
| `azioneCorrettiva(azione)` | void | azione: String |
| `creaAccountUtente(nome, cognome, email, password, datanascita)` | void | nome, cognome, email, password, datanascita |
| `getStatoUtente()` | StatoUtente | — |
| `setStatoUtente(statoUtente)` | void | statoUtente: StatoUtente |

*Nota:* `email`, `password`, `id`, `ruolo` ereditati da Attore.

---

### Operatore (extends Attore)
Categoria professionale con tipo (Tecnico o ServizioClienti).

| Attributo | Tipo | Visibilità |
|-----------|------|------------|
| `tipo` | TipoOperatore | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getTipo()` | TipoOperatore | — |
| `setTipo(tipo)` | void | tipo: TipoOperatore |

*Nota:* `id`, `email`, `password`, `ruolo` ereditati da Attore. OperatoreTecnico e OperatoreSC sono distinti dal valore dell'enum `tipo`, non da classi separate.

---

### PA (extends Attore)
Ente comunale con privilegi di analisi e gestione restrizioni.

| Attributo | Tipo | Visibilità |
|-----------|------|------------|
| `idPA` | — | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdPA()` | — | — |
| `setIdPA(idPA)` | void | idPA |

*Nota:* `idPA` coincide con `Attore.id`. `email`, `password`, `ruolo` ereditati.

---

### Mezzo
Veicolo della flotta (bici, scooter, auto) con stato, posizione, autonomia e caratteristiche tecniche.

| Attributo | Tipo | Visibilità |
|-----------|------|------------|
| `idMezzo` | — (PK) | private |
| `coordinateMezzo` | String | private |
| `stato` | StatoMezzo | private |
| `autonomia` | float | private |
| `costoOrario` | float | private |
| `velocitaMax` | float | private |
| `condizione` | String | private |
| `tipo` | String | private |
| `idFlotta` | String | private |
| `tempoDisponibilita` | time | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getCoordinateMezzo()` | String | — |
| `setCoordinateMezzo(coordinateMezzo)` | void | coordinateMezzo: String |
| `getIdMezzo()` | — | — |
| `setIdMezzo(idMezzo)` | void | idMezzo |
| `getStato()` | StatoMezzo | — |
| `setStato(stato)` | void | stato: StatoMezzo |
| `getAutonomia()` | float | — |
| `setAutonomia(autonomia)` | void | autonomia: float |
| `getCostoOrario()` | float | — |
| `setCostoOrario(costoOrario)` | void | costoOrario: float |
| `getVelocitaMax()` | float | — |
| `setVelocitaMax(velocitaMax)` | void | velocitaMax: float |
| `getCondizione()` | String | — |
| `setCondizione(condizione)` | void | condizione: String |
| `getTipo()` | String | — |
| `setTipo(tipo)` | void | tipo: String |
| `getIdFlotta()` | String | — |
| `setIdFlotta(idFlotta)` | void | idFlotta: String |
| `getMezzibyFlotta(idFlotta)` | Mezzo | idFlotta: String |
| `getTempoDisponibilita()` | time | — |
| `setTempoDisponibilita(tempoDisponibilita)` | void | tempoDisponibilita: time |
| `getMezziInArea(coordinateUtente, raggio)` | Mezzo | coordinateUtente: String, raggio: float |
| `getDettagliMezzo()` | Mezzo | — |

---

### Corsa
Sessione di utilizzo di un mezzo dall'avvio al termine, con costi e coordinate.

| Attributo | Tipo | Visibilità |
|-----------|------|------------|
| `idCorsa` | — (PK) | private |
| `costo` | float | private |
| `orarioInizio` | time | private |
| `orarioFine` | time | private |
| `coordinatePartenza` | String | private |
| `coordinateArrivo` | String | private |
| `idMetodoPagamento` | — (FK → MetodoPagamento) | private |
| `idUtente` | — (FK → Utente) | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getCosto()` | float | — |
| `setCosto(costo)` | void | costo: float |
| `getOrarioInizio()` | time | — |
| `setOrarioInizio(orarioInizio)` | void | orarioInizio: time |
| `getOrarioFine()` | time | — |
| `setOrarioFine(orarioFine)` | void | orarioFine: time |
| `getCoordinatePartenza()` | String | — |
| `setCoordinatePartenza(coordinatePartenza)` | void | coordinatePartenza: String |
| `getCoordinateArrivo()` | String | — |
| `setCoordinateArrivo(coordinateArrivo)` | void | coordinateArrivo: String |
| `creaCorsa(orarioinizio, coordinatePartenza, idUtente, idMezzo)` | void | orarioinizio, coordinatePartenza, idUtente, idMezzo |
| `getIdCorsa()` | — | — |
| `setIdCorsa(idCorsa)` | void | idCorsa |
| `ricercaCorsa(idCorsa)` | Corsa | idCorsa |
| `getCorseByPeriodo(dataInizio, dataFine)` | Corsa | dataInizio: date, dataFine: date |
| `aggiornaCosto(costo)` | void | costo: float |
| `getIdMetodoPagamento()` | — | — |
| `setIdMetodoPagamento(idMetodoPagamento)` | void | idMetodoPagamento |
| `getIdUtente()` | — | — |
| `setIdUtente(idUtente)` | void | idUtente |

---

### MetodoPagamento
Dati cifrati della carta di credito/debito associata a un utente.

| Attributo | Tipo | Visibilità |
|-----------|------|------------|
| `idMetodoPagamento` | — (PK, surrogata) | private |
| `numCarta` | String (cifrato) | private |
| `intestatarioCarta` | String | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdMetodoPagamento()` | — | — |
| `setIdMetodoPagamento(idMetodoPagamento)` | void | idMetodoPagamento |
| `getNumCarta()` | String | — |
| `setNumCarta(numCarta)` | void | numCarta: String |
| `getIntestatarioCarta()` | String | — |
| `setIntestatarioCarta(intestatarioCarta)` | void | intestatarioCarta: String |
| `creaMetodoPagamento(numCarta, intestatarioCarta)` | void | numCarta: String, intestatarioCarta: String |
| `controllaMetodoEsistente(numCarta)` | bool | numCarta: String |
| `getMetodoByUtente(idUtente)` | MetodoPagamento | idUtente |

*Nota:* `idMetodoPagamento` è chiave surrogata per ragioni di sicurezza (PCI-DSS).

---

### Prenotazione
Blocco temporaneo di un mezzo da parte di un utente (timeout: 15 min).

| Attributo | Tipo | Visibilità |
|-----------|------|------------|
| `idPrenotazione` | — (PK) | private |
| `stato` | StatoPrenotazione | private |
| `idUtente` | — (FK → Utente) | private |
| `idMezzo` | — (FK → Mezzo) | private |
| `orarioInizio` | time | private |
| `data` | date | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getStato()` | StatoPrenotazione | — |
| `setStato(stato)` | void | stato: StatoPrenotazione |
| `getData()` | date | — |
| `setData(data)` | void | data: date |
| `getIdPrenotazione()` | — | — |
| `setIdPrenotazione(idPrenotazione)` | void | idPrenotazione |
| `getIdUtente()` | — | — |
| `setIdUtente(idUtente)` | void | idUtente |
| `getIdMezzo()` | — | — |
| `setIdMezzo(idMezzo)` | void | idMezzo |
| `getOrarioInizio()` | time | — |
| `setOrarioInizio(orarioInizio)` | void | orarioInizio: time |
| `getPrenotazioneByStato(stato)` | Prenotazione | stato: StatoPrenotazione |
| `creaPrenotazione(idMezzo, idUtente, orarioInizio)` | void | idMezzo, idUtente, orarioInizio: time |

---

### Segnalazione
Report di anomalia su un mezzo (guasto, manutenzione, veicolo non raggiungibile).

| Attributo | Tipo | Visibilità |
|-----------|------|------------|
| `idSegnalazione` | — (PK) | private |
| `idMezzo` | — (FK → Mezzo) | private |
| `stato` | StatoSegnalazione | private |
| `ora` | time | private |
| `data` | date | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdSegnalazione()` | — | — |
| `setIdSegnalazione(idSegnalazione)` | void | idSegnalazione |
| `getIdMezzo()` | — | — |
| `setIdMezzo(idMezzo)` | void | idMezzo |
| `getOra()` | time | — |
| `setOra(ora)` | void | ora: time |
| `getData()` | date | — |
| `setData(data)` | void | data: date |
| `getStato()` | StatoSegnalazione | — |
| `setStato(stato)` | void | stato: StatoSegnalazione |
| `creaSegnalazione(idMezzo, statoS, data, ora, note)` | void | idMezzo, statoS, data: date, ora: time, note: String |

---

### ZonaGeografica
Area geografica con restrizioni di circolazione o sosta per i mezzi.

| Attributo | Tipo | Visibilità |
|-----------|------|------------|
| `idArea` | — (PK) | private |
| `tipoRestrizione` | TipoRestrizione | private |
| `noteRestrizione` | String | private |
| `zona` | LineString | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdArea()` | — | — |
| `setIdArea(idArea)` | void | idArea |
| `getTipoRestrizione()` | TipoRestrizione | — |
| `setTipoRestrizione(tipoRestrizione)` | void | tipoRestrizione: TipoRestrizione |
| `getNoteRestrizione()` | String | — |
| `setNoteRestrizione(noteRestrizione)` | void | noteRestrizione: String |
| `getZona()` | LineString | — |
| `setZona(zona)` | void | zona: LineString |
| `getZone()` | ZonaGeografica | — |
| `verificaSovrapposizioni(zona)` | bool | zona: ZonaGeografica |
| `checkArea(coordinateUtente)` | bool | coordinateUtente: String |
| `creaZonaGeografica(idArea, tipoRestrizione, noteRestrizione, zona)` | void | idArea, tipoRestrizione, noteRestrizione, zona |
| `getRestrizioniZona(coordinateUtente)` | ZonaGeografica | coordinateUtente: String |

---

### Transito
Associazione M:N tra Corsa e ZonaGeografica per tracciare le tratte percorse.

| Attributo | Tipo | Visibilità |
|-----------|------|------------|
| `idCorsa` | — (FK → Corsa) | private |
| `idArea` | — (FK → ZonaGeografica) | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdCorsa()` | — | — |
| `setIdCorsa(idCorsa)` | void | idCorsa |
| `getIdArea()` | — | — |
| `setIdArea(idArea)` | void | idArea |
| `getTransitiByCorsa(idCorsa)` | Transito | idCorsa |

---

## 3. Controller Layer

I Controller orchestrano il flusso MVC. Le View non interrogano mai direttamente il Model — ogni comunicazione è mediata dal Controller *(documentazione.md §2.3)*.

### GestioneAutenticazione
Validazione credenziali, registrazione, gestione sessioni.

| Attributo | Visibilità |
|-----------|------------|
| `idGestioneAutenticazione` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `verificaValidita(nome, cognome, email, password, datanascita)` | RuoloAttore | nome, cognome, email, password, datanascita |
| `inviaRichiestaLogout(email)` | void | email: String |
| `invioCredenziali(email, password)` | RuoloAttore | email: String, password: String |
| `getIdGestioneAutenticazione()` | — | — |
| `setIdGestioneAutenticazione(id)` | void | id |

---

### GestioneUtenti
Moderazione account utente (sospensione/disattivazione).

| Attributo | Visibilità |
|-----------|------------|
| `idGestioneUtenti` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `gestioneUtente(idUtente)` | bool | idUtente |
| `cercaReport(idUtente)` | String | idUtente |
| `getIdGestioneUtenti()` | — | — |
| `setIdGestioneUtenti(id)` | void | id |

---

### RicercaMezzi
Query geolocalizzata su mezzi disponibili con raggi multipli (base 2km, esteso 5km — *da documentazione.md UC.UT.01*).

| Attributo | Visibilità |
|-----------|------------|
| `idRicercaMezzi` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `visualizzaMezziVicini(coordinateUtente, raggiob)` | Mezzo | coordinateUtente: String, raggiob: float |
| `visualizzaSpecifiche(idMezzo)` | Mezzo | idMezzo |
| `getIdRicercaMezzi()` | — | — |
| `setIdRicercaMezzi(id)` | void | id |

---

### GestioneCorsa
Orchestrazione del ciclo di vita della corsa: avvio, sospensione, terminazione, calcolo percorso, stima costi.

| Attributo | Visibilità |
|-----------|------------|
| `idGestioneCorsa` | private |
| `idMetodoPagamento` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `avviaCorsa()` | void | — |
| `terminaCorsa()` | void | — |
| `controllaDisponibilita()` | bool | — |
| `controllaDisponibilita(info)` | bool | info: String |
| `aggiornaStima(idCorsa)` | float | idCorsa |
| *(Formula)* | — | `stimaCosto = costoOrario × oreUtilizzo + costo_sospensione(eventuale)` *(clarified 2026-06-23)* |
| `sospensioneCorsa()` | bool | — |
| `richiediSblocco(qrCode)` | bool | qrCode: String |
| `richiediCalcoloPercorso(coordinateUtente, destinazione)` | percorso ⚠ Placeholder — struttura da definire con API mappe effettiva | coordinateUtente: String, destinazione: String |
| `acquisisciSceltaMetodo(idMetodoPagamento)` | void | idMetodoPagamento |
| `getIdGestioneCorsa()` | — | — |
| `setIdGestioneCorsa(id)` | void | id |
| `getIdMetodoPagamento()` | — | — |
| `setIdMetodoPagamento(id)` | void | id |

> **Nota:** `CalcoloPercorso()` e `fineCorsa()` presenti nell'XMI sono artefatti di esportazione e NON esistono. `controllaDisponibilita()` ha due overload. `richiediCalcoloPercorso()` delega a Servizio Mappa esterno.

---

### GestorePagamento
Elaborazione transazioni e validazione metodi di pagamento (delega a Gateway Pagamento esterno).

| Attributo | Visibilità |
|-----------|------------|
| `idGestorePagamento` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `pagamentoCorsa(idUtente, idMetodoPagamento, costo)` | bool | idUtente, idMetodoPagamento, costo: float |
| `elaboraDatiCarta(idUtente, numCarta, dsCarta, cvv, intestatarioCarta)` | bool | idUtente, numCarta, dsCarta, cvv, intestatarioCarta |
| `recuperaMetodiSalvati()` | MetodoPagamento | — |
| `getIdGestorePagamento()` | — | — |
| `setIdGestorePagamento(id)` | void | id |

---

### GestioneFlotta
Monitoraggio e controllo remoto della flotta (blocco, manutenzione).

| Attributo | Visibilità |
|-----------|------------|
| `idGestioneFlotta` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `analisiStatoFlotta(idFlotta)` | bool | idFlotta: String |
| *(Semantica)* | — | Rileva Mezzo da manutenere → crea Segnalazione + setta `Mezzo.stato = 'manutenzione'`. Distinto da `getCondizioniMezzi` che è per visualizzazione dashboard. *(clarified 2026-06-23)* |
| `bloccaMezzo(idMezzo)` | bool | idMezzo |
| `avviaManutenzione(idFlotta)` | bool | idFlotta: String |
| `getCondizioniMezzi(idFlotta)` | Mezzo | idFlotta: String |
| `getIdGestioneFlotta()` | — | — |
| `setIdGestioneFlotta(id)` | void | id |

---

### GestionePrenotazione
Gestione ciclo di vita prenotazioni con timeout automatico a 15 minuti.

| Attributo | Visibilità |
|-----------|------------|
| `idGestionePrenotazione` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `inviaRichiestaPrenotazione()` | void | — |
| `richiediLista()` | Prenotazione | — |
| `annullaPrenotazione()` | bool | — |
| `gestisciTimeout()` | void | — |
| `notificaScadenzaTempo(idPrenotazione)` | void | idPrenotazione |
| `getIdGestionePrenotazione()` | — | — |
| `setIdGestionePrenotazione(id)` | void | id |

---

### GestioneStatistiche
Aggregazione dati corse e generazione report per la PA.

| Attributo | Visibilità |
|-----------|------------|
| `idGestioneStatistiche` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `analisiTratte(dataInizio, dataFine)` | statistiche | dataInizio: date, dataFine: date |
| `generaFileStatistiche(corse)` | void | corse: Corsa |
| `getIdGestioneStatistiche()` | — | — |
| `setIdGestioneStatistiche(id)` | void | id |

---

### GestioneAree
CRUD di zone geografiche e verifica conflitti tra restrizioni sovrapposte.

| Attributo | Visibilità |
|-----------|------------|
| `idGestioneAree` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `aggiornaRestrizione(idArea, tipoRestrizione, noteRestrizione, zona)` | void | idArea, tipoRestrizione, noteRestrizione, zona |
| `analisiConflitti(zona)` | bool | zona: ZonaGeografica |
| `getZoneGeografiche()` | ZonaGeografica | — |
| `getIdGestioneAree()` | — | — |
| `setIdGestioneAree(id)` | void | id |

---

## 4. View Layer

Le View sono disaccoppiate dal Model. Ogni comunicazione avviene inviando richieste esplicite ai Controller *(documentazione.md §2.3)*.

### AppUtente
Interfaccia utente cittadino: mappe, QR code, dettagli mezzi, costi corse.

| Attributo | Visibilità |
|-----------|------------|
| `idUtente` | private |
| `idSessioneUtente` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `mostraErrore(msg)` | void | msg: String |
| `mostraStima(idCorsa)` | void | idCorsa |
| `mostraSuccesso()` | void | — |
| `mostraFineCorsa()` | void | — |
| `mostraQRCode()` | void | — |
| `mostraRipresaCorsa()` | void | — |
| `mostraMetodi(metodi)` | void | metodi: MetodoPagamento |
| `mostraSceltaMetodi()` | void | — |
| `mostraMezzi(mezzi)` | void | mezzi: Mezzo |
| `mostraMetodoConvalidato()` | void | — |
| `renderizzaDettagliVeicolo(mezzo)` | void | mezzo: Mezzo |
| `scansionaQRCode(qrCode)` | void | qrCode: String |
| `inserisciDatiCarta(idUtente, numCarta, dsCarta, cvv, intestatarioCarta)` | void | idUtente, numCarta, dsCarta, cvv, intestatarioCarta |
| `apriAvvioCorsa()` | void | — |
| `terminazioneCorsa(idCorsa)` | void | idCorsa |
| `sospendiCorsa(idCorsa)` | void | idCorsa |
| `apriSezioneProfilo(idUtente)` | void | idUtente |
| `apriInserimentoMetodoPagamento(idUtente)` | void | idUtente |
| `selezionaMezzo(idMezzo)` | void | idMezzo |
| `inserisciDestinazione(indirizzoArrivo)` | void | indirizzoArrivo: String |
| `avviaRicercaMezzi(coordinateUtente, raggiob)` | void | coordinateUtente: String, raggiob: float |
| `confermaEspansione()` | void | — |
| `notificaAzione(idUtente, azione)` | void | idUtente, azione: String |
| `ottieniMetodiSalvati()` | void | — |
| `selezionaMetodo(numCarta)` | void | numCarta: String |
| `richiestaLogout(email)` | void | email: String |
| `getIdUtente()` | — | — |
| `setIdUtente(id)` | void | id |
| `getIdSessioneUtente()` | — | — |
| `setIdSessioneUtente(id)` | void | id |

---

### AppOperatoreTecnico
Dashboard operatore tecnico: visualizzazione flotta e comandi remoti.

| Attributo | Visibilità |
|-----------|------------|
| `idOperatoreTecnico` | private |
| `idSessioneOperatoreTecnico` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `mostraSuccesso(msg)` | void | msg: String |
| `mostraErrore(msg)` | void | msg: String |
| `visualizzaMezzi(mezzi)` | void | mezzi: Mezzo |
| `richiedeStatoFlotta(idFlotta)` | void | idFlotta: String |
| `selezionaVeicolo(idMezzo)` | void | idMezzo |
| `richiestaLogout(email)` | void | email: String |
| `getIdOperatoreTecnico()` | — | — |
| `setIdOperatoreTecnico(id)` | void | id |
| `getIdSessioneOperatoreTecnico()` | — | — |
| `setIdSessioneOperatoreTecnico(id)` | void | id |

---

### AppOperatoreSC
Interfaccia operatore servizio clienti: moderazione e amministrazione prenotazioni.

| Attributo | Visibilità |
|-----------|------------|
| `idOperatoreSC` | private |
| `idSessioneOperatoreSC` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `mostraPrenotazioni()` | void | — |
| `mostraErrore(msg)` | void | msg: String |
| `mostraSuccesso(msg)` | void | msg: String |
| `mostraReport(idUtente)` | void | idUtente |
| `richiediListaPrenotazioni()` | void | — |
| `selezionaPrenotazione(idPrenotazione)` | void | idPrenotazione |
| `aggiornaReport(idUtente)` | void | idUtente |
| `richiestaLogout(email)` | void | email: String |
| `getIdOperatoreSC()` | — | — |
| `setIdOperatoreSC(id)` | void | id |
| `getIdSessioneOperatoreSC()` | — | — |
| `setIdSessioneOperatoreSC(id)` | void | id |

---

### AppPA
Interfaccia PA: statistiche, analisi flotta e restrizioni geografiche.

| Attributo | Visibilità |
|-----------|------------|
| `idPA` | private |
| `idSessionePA` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `mostraErrore(msg)` | void | msg: String |
| `mostraStatistiche(statistiche)` | void | statistiche |
| `visualizzaMezzi(mezzi)` | void | mezzi: Mezzo |
| `mostraMappa(zone)` | void | zone: ZonaGeografica |
| `selezionaIntervallo(dataInizio, dataFine)` | void | dataInizio: date, dataFine: date |
| `richiedeStatoFlotta(idFlotta)` | void | idFlotta: String |
| `avviaIntervento(idFlotta)` | void | idFlotta: String |
| `selezionaMappa()` | void | — |
| `modificaRestrizioni(zona)` | void | zona: ZonaGeografica |
| `confermaSovrascrittura(idArea, tipoRestrizione, noteRestrizione)` | void | idArea, tipoRestrizione, noteRestrizione |
| `rifiutaSovrascrittura()` | void | — |
| `richiestaLogout(email)` | void | email: String |
| `getIdPA()` | — | — |
| `setIdPA(id)` | void | id |
| `getIdSessionePA()` | — | — |
| `setIdSessionePA(id)` | void | id |

---

### Autenticazione (View)
Interfaccia per login e registrazione (pre-auth).

| Attributo | Visibilità |
|-----------|------------|
| `idAttore` | private |
| `idSessioneAttore` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `mostraFormRegistrazione()` | void | — |
| `inserisciCredenziali(nome, cognome, email, password, datanascita)` | void | nome, cognome, email, password, datanascita |
| `registrazioneUtente()` | void | — |
| `getIdAttore()` | — | — |
| `setIdAttore(id)` | void | id |
| `getIdSessioneAttore()` | — | — |
| `setIdSessioneAttore(id)` | void | id |

---

## 5. External Systems (Simulated)

*Tutti i sistemi esterni sono simulati — progetto universitario (chiarimenti-vari.md punto 16).*

### Mezzo:IoT
Interfaccia fisica col veicolo. Blocco/sblocco remoto, lettura QR code.

| Attributo | Visibilità |
|-----------|------------|
| `idMezzoIoT` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `bloccoMezzoFisico(idMezzo)` | bool | idMezzo |
| `sbloccoMezzoFisico(idMezzo)` | bool | idMezzo |
| `getIdMezzoIoT()` | — | — |
| `setIdMezzoIoT(id)` | void | id |

---

### Gateway Pagamento
Processore di pagamento esterno. Convalida carte e processa transazioni.

| Attributo | Visibilità |
|-----------|------------|
| `idGatewayPagamento` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `effettuaPagamento(idMetodoPagamento, idCorsa)` | bool | idMetodoPagamento, idCorsa |
| `convalidaCarta(numCarta, dsCarta, cvv, intestatarioCarta)` | bool | numCarta, dsCarta, cvv, intestatarioCarta |
| `getIdGatewayPagamento()` | — | — |
| `setIdGatewayPagamento(id)` | void | id |

---

### Servizio Mappa
Servizio di geolocalizzazione e routing esterno.

| Attributo | Visibilità |
|-----------|------------|
| `idServizioMappa` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getPercorso(coordinateIniziali, coordinateFinali, restrizioni)` | datiPercorso ⚠ Placeholder — struttura da definire con API mappe effettiva | coordinateIniziali: String, coordinateFinali: String, restrizioni: ZonaGeografica |
| `getIdServizioMappa()` | — | — |
| `setIdServizioMappa(id)` | void | id |

---

### DBMS
Persistenza dati — interfaccia CRUD standard verso database relazionale.

| Attributo | Visibilità |
|-----------|------------|
| `idDBMS` | private |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdDBMS()` | — | — |
| `setIdDBMS(id)` | void | id |

*Design:* L'interfaccia DBMS fornisce operazioni CRUD standard (Create, Read, Update, Delete) verso il database relazionale sottostante. Le classi Model (Attore, Corsa, MetodoPagamento, Mezzo, Prenotazione, Segnalazione, Transito, ZonaGeografica) dipendono da DBMS per la persistenza.

---

## 6. Associazioni del Dominio

| Entità A | Relazione | Entità B | Molt. A | Molt. B |
|----------|-----------|----------|---------|---------|
| Mezzo | utilizza → | Corsa | 1 | 0..* |
| GestioneUtenti | modera → | Utente | 0..* | 0..* |
| GestorePagamento | verifica → | MetodoPagamento | 0..* | 0..* |
| RicercaMezzi | interroga → | Mezzo | 0..* | 0..* |
| GestioneCorsa | amministra → | Mezzo | 1..* | 0..* |
| GestioneCorsa | effettua → | Corsa | 0..* | 1 |
| GestioneFlotta | gestisce → | Mezzo | 0..* | 0..* |
| GestioneFlotta | crea → | Segnalazione | 0..* | 1 |
| GestioneStatistiche | analizza → | Corsa | 0..* | 0..* |
| GestioneStatistiche | osserva → | Transito | 0..* | 0..* |
| GestionePrenotazione | prenota → | Mezzo | 1..* | 0..* |
| GestionePrenotazione | ha → | Prenotazione | 0..* | 1 |
| GestionePrenotazione | genera → | Segnalazione | 0..* | 1 |
| GestioneAree | aggiunge → | ZonaGeografica | 0..* | 0..* |
| ZonaGeografica | check → | GestioneCorsa | 0..* | 0..* |
| GestioneAutenticazione | autentica → | Attore | 1 | 1 |

---

### Dipendenze View → Controller

| View | Controller |
|------|-----------|
| AppUtente → | GestioneCorsa, GestorePagamento, RicercaMezzi, GestionePrenotazione, GestioneAutenticazione |
| AppOperatoreSC → | GestioneUtenti, GestionePrenotazione, GestioneAutenticazione |
| AppPA → | GestioneFlotta, GestioneStatistiche, GestioneAree, GestioneAutenticazione |
| AppOperatoreTecnico → | GestioneFlotta, GestioneAutenticazione |
| Autenticazione → | GestioneAutenticazione |

---

## 7. Use Case Logic

*Tratto da documentazione.md §2.2.2 — sorgente primaria.*

| UC ID | Nome | Attore | Include | Estende | Esteso da |
|-------|------|--------|---------|---------|-----------|
| UC.ATT.01 | Login | Attore | — | — | — |
| UC.UT.01 | Ricerca Mezzi | Utente | — | — | UC.UT.02 |
| UC.UT.02 | Prenotazione Mezzo | Utente | — | UC.UT.01 | UC.UT.03 |
| UC.UT.03 | Gestione Corsa | Utente | UC.UT.05, UC.UT.07 | UC.UT.02 | UC.UT.06 |
| UC.UT.04 | Ottimizzazione Percorso | Utente | — | — | — |
| UC.UT.05 | Metodo Pagamento | Utente | — | — | — |
| UC.UT.06 | Sospensione Corsa | Utente | — | UC.UT.03 | — |
| UC.UT.07 | Termina Corsa e Pagamento | Utente | — | — | — |
| UC.UT.10 | Registrazione Utente | Utente (non reg.) | — | — | — |
| UC.UT.09 | Logout Utente | Utente | — | — | — |
| UC.OP.01 | Gestione Flotta | Operatore Tecnico | — | — | — |
| UC.OP.02 | Moderazione Utenti | Operatore SC | — | — | — |
| UC.OP.03 | Amministrazione Prenotazioni | Operatore SC | — | — | — |
| UC.OP.04 | Logout Operatore Tecnico | Operatore Tecnico | — | — | — |
| UC.OP.05 | Logout Operatore SC | Operatore SC | — | — | — |
| UC.AP.01 | Monitoraggio Statistiche | PA | — | — | — |
| UC.AP.02 | Analisi Stato Flotta | PA | — | — | — |
| UC.AP.03 | Restrizioni Geografiche | PA | — | — | — |
| UC.AP.04 | Logout PA | PA | — | — | — |

---

## 8. Architectural Constraints & Invariants

### Vincoli di Sistema *(da documentazione.md e chiarimenti-vari.md)*

1. **Autenticazione obbligatoria:** Ogni operazione su corse, prenotazioni o pagamenti richiede sessione attiva
2. **Verifica geospaziale:** Terminazione corsa consentita solo in aree designate — `ZonaGeografica.checkArea()` *(vincolo AP.04, chiarimenti-vari.md punto 4)*
3. **Timeout prenotazione 15 minuti:** `GestionePrenotazione.gestisciTimeout()` annulla automaticamente
4. **Blocco corsa attiva:** Utente non può avviare nuova corsa se già in corso
5. **RBAC:** Routing post-login determinato da `RuoloAttore`
6. **Cifratura password:** Memorizzate in forma cifrata
7. **Pagamento obbligatorio:** Nessuna corsa termina senza transazione
8. **Metodo pagamento pre-esistente:** Necessario metodo valido per avviare corsa
9. **Sessione singola:** Login termina sessione precedente
10. **Disaccoppiamento View-Controller-Model:** View mai direttamente sul Model
11. **Ruolo unico per sessione:** View istanziata in base al ruolo dopo login
12. **Simulazione sistemi esterni:** Gateway, Mappa, DBMS, IoT *(chiarimenti-vari.md punto 16)*
13. **Unicità email:** Indirizzo email univoco nel sistema
14. **Cifratura dati pagamento:** Dati carta memorizzati cifrati

### Invarianti del Dominio

- Una `Corsa` è sempre associata a un `Utente` e un `Mezzo`
- Lo stato del `Mezzo` segue il ciclo: `disponibile → prenotato → in_uso → (sospeso → in_uso)* → disponibile`, con stati `bloccato` e `manutenzione`
- Il `costo` di una Corsa è sempre >= 0 e include costi di sospensione
- Una `Segnalazione` ha stato: `aperta → in_lavorazione → chiusa`
- Una `ZonaGeografica` ha sempre un `tipoRestrizione` valido

---

## 9. Anti-Patterns (DO NOT)

| Don't | Do Instead | Why |
|-------|------------|-----|
| Gestire pagamenti senza convalida esterna | Delegare sempre a Gateway Pagamento | Sicurezza e compliance PCI-DSS |
| Permettere logout senza controllo sessione | Terminare sempre la sessione attiva | Previene session hijacking |
| Ignorare il timeout prenotazioni | Implementare GestionePrenotazione.gestisciTimeout() | Mezzi bloccati indefinitamente |
| Hardcodare raggi di ricerca | Parametrizzare (raggio base 2km, esteso 5km) | Flessibilità futura |
| Permettere modifica diretta Model dalla View | Passare sempre attraverso i Controller | Violazione pattern MVC |
| Esporre ID interni nelle API | Usare identificatori opachi (UUID) | Security by obscurity |
| Saltare la verifica area per termine corsa | Validare con ZonaGeografica.checkArea() | Vincolo architetturale AP.04 |

---

## 10. Data Model (ER)

*Convenzione: snake_case per nomi tabella/colonne DB.*

```
attore (id, email, password, ruolo:enum)
  ├── utente (id→attore, coordinate_utente, nome_utente, cognome_utente, telefono,
  │            num_mezzi_prenotati, report_utente, stato_utente:enum)
  ├── operatore (id→attore, tipo:enum)
  └── pa (id→attore)

mezzo (id_mezzo, coordinate_mezzo, stato:enum, autonomia, costo_orario,
       velocita_max, condizione, tipo, id_flotta, tempo_disponibilita)

metodo_pagamento (id_metodo_pagamento, num_carta, intestatario_carta)

corsa (id_corsa, costo, orario_inizio, orario_fine, coordinate_partenza,
       coordinate_arrivo, id_metodo_pagamento→metodo_pagamento, id_utente→utente)

prenotazione (id_prenotazione, stato:enum, id_utente→utente, id_mezzo→mezzo,
              orario_inizio, data)

segnalazione (id_segnalazione, id_mezzo→mezzo, stato:enum, ora, data)

zona_geografica (id_area, tipo_restrizione:enum, note_restrizione, zona:LineString)

transito (id_corsa→corsa, id_area→zona_geografica)
```

### Relazioni

| FK | Tabella | Riferimento | Tipo |
|----|---------|-------------|------|
| utente.id | attore.id | 1:1 | JOINED inheritance |
| operatore.id | attore.id | 1:1 | JOINED inheritance |
| pa.id | attore.id | 1:1 | JOINED inheritance |
| corsa.id_utente | utente.id | N:1 | |
| corsa.id_metodo_pagamento | metodo_pagamento.id_metodo_pagamento | N:1 | |
| prenotazione.id_utente | utente.id | N:1 | |
| prenotazione.id_mezzo | mezzo.id_mezzo | N:1 | |
| segnalazione.id_mezzo | mezzo.id_mezzo | N:1 | |
| transito.id_corsa | corsa.id_corsa | N:M | bridge |
| transito.id_area | zona_geografica.id_area | N:M | bridge |

---

## 11. Key Architectural Decisions

1. **MVC con Controller Intermediario Centralizzato** — Gestisce ecosistema eterogeneo di 5 View e flussi asincroni IoT *(documentazione.md §2.3)*
2. **Separazione dei logout per ruolo** — 4 diagrammi di logout per gestire sessioni specifiche *(chiarimenti-vari.md punto 13)*
3. **Attore come generalizzazione JOINED** — Tabella `attore` base con `utente`, `operatore`, `pa` collegate 1:1
4. **Gateway Pagamento e Servizio Mappa simulati** — Interfacce definite ma senza implementazione reale *(chiarimenti-vari.md punto 16)*
5. **Tracciamento geospaziale** — Uso di tipi geometrici (POINT, LINESTRING) e coordinate String (x,y,z)
6. **MetodoPagamento con chiave surrogata** — `id_metodo_pagamento` come PK invece del numero carta per sicurezza
7. **Flotta come raggruppamento logico** — `id_flotta` in Mezzo è attributo identificativo, non FK verso tabella separata

---

## 12. Glossary

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

## 13. Summary Statistics (Cross-Reference Verified)

| Metrica | Valore |
|---------|--------|
| Classi Model | 11 (Attore, Utente, Operatore, PA, Mezzo, Corsa, MetodoPagamento, Prenotazione, Segnalazione, ZonaGeografica, Transito) |
| Controller | 9 |
| View | 5 |
| External Systems | 4 |
| Enumerazioni | 7 (completamente specificate) |
| Associazioni del dominio | 16 (molteplicità completa) |
| Totale metodi Model | 126 (inclusi getter/setter) |
| Totale metodi Controller | 53 |
| Totale metodi View | 75 (inclusi helper privati) |
| Totale metodi External | 13 |
| Use Case | 19 |
| Vincoli architetturali | 14 |
| Invarianti di dominio | 5 |
| Anti-patterns | 7 |
| Relazioni FK DB | 10 |

*Tutti i conteggi sono stati verificati tramite cross-reference puntuale tra documentazione.md, Master_Spec.md v3.0 e classDiagram-v1.8-clean.uml. Conteggio effettuato riga per riga sulle tabelle del presente documento. Gli artefatti XMI noti (metodi/attributi senza nome, typo) sono stati rimossi.*

---

## 14. References

| Documento | Percorso | Ruolo |
|-----------|----------|-------|
| documentazione.md | `docs/specs/documentazione.md` | Sorgente primaria (v3.0) |
| chiarimenti-vari.md | `docs/specs/chiarimenti-vari.md` | Interpretazioni e vincoli |
| Master_Spec.md | `docs/specs/Master_Spec.md` | Specifica originale (v3.0) |
| Class Diagram | `docs/diagrams/class-diagram/classDiagram-v1.8-clean.uml` | XMI 2.1 |
| Component Diagram | `docs/diagrams/component-diagram/componentDiagram-clean.uml` | XMI 2.1 |
| Use Case Diagram | `docs/diagrams/use-case-diagram/UCdiagram-v1.1-clean.uml` | XMI 2.1 |
| ER Diagram | `docs/diagrams/er-diagram/ERdiagram-clean.puml` | PlantUML |
| Sequence Diagrams | `docs/diagrams/sequence-diagrams/UC.*/` | XMI 2.1 (19 UC) |

---

## 15. Test Strategy

### 15.1 Approach
- **Unit Testing (JUnit 5 + Mockito):** Test each controller, service, and model method in isolation. External systems (ServizioMappa, GatewayPagamento, DBMS, Mezzo:IoT) are mocked.
- **Integration Testing (@SpringBootTest):** Test View→Controller→Model contracts. Use @DataJpaTest for repository layers.
- **API Testing (MockMvc):** Test REST endpoints per controller with JSON request/response validation.
- **Coverage Target:** Line coverage ≥ 80% per module, branch coverage ≥ 70%.

### 15.2 Test Levels per Component

| Component | Unit Tests | Integration Tests | Key Mock |
|-----------|------------|-------------------|----------|
| Controller (GestioneAutenticazione, RicercaMezzi, etc.) | 5+ per controller | 2 per endpoint | Repository layer |
| Model (Mezzo, Utente, Corsa, etc.) | 3+ per entity | 2 per repository | DBMS |
| View (AppUtente, AppPA, etc.) | 2+ per view | 1 per use case | Controller layer |
| External (ServizioMappa, GatewayPagamento) | N/A (simulated) | 1 per external method | MockMvc |

### 15.3 Test Data Strategy
- Use in-memory H2 database for integration tests (MySQL dialect compatibility verified)
- Pre-populate test data via data.sql or @BeforeEach fixtures
- Test coordinates: use known test points (e.g., (12.4924, 41.8902) for Rome)
- Spatial queries tested with JTS GeometryFactory

### 15.4 Acceptance Criteria Validation
Each UC's Acceptance Criteria (where defined) maps to at least 1 automated test:
- **AC1 (functional):** End-to-end component test
- **AC2 (security):** Security context test with/without valid session
- **AC3 (integration):** Integration test verifying state changes
- **AC4 (UI):** View rendering contract test
- **AC5 (UX):** Behavioral consistency test

---

## 16. Error Handling Strategy

### 16.1 Global Error Categories

| Error ID | Error Type | Detection Point | System Response | Fallback | Logging |
|----------|-----------|-----------------|----------------|----------|---------|
| ERR-GL-001 | Input Validation | Controller (@Valid) | 400 Bad Request + ValidationError DTO | N/A (client error) | WARN |
| ERR-GL-002 | Authentication Failure | GestioneAutenticazione | 401 Unauthorized | Redirect to Autenticazione view | INFO |
| ERR-GL-003 | Authorization Failure | Controller (session check) | 403 Forbidden | mostraErrore("Accesso negato") | WARN |
| ERR-GL-004 | Resource Not Found | Service/Repository | 404 Not Found | mostraErrore("Risorsa non trovata") | WARN |
| ERR-GL-005 | Business Logic Violation | Service layer | 409 Conflict | mostraErrore(msg) + rollback | WARN |
| ERR-GL-006 | External System Timeout | Integration layer | 502 Bad Gateway | Retry (3 attempts) then mostraErrore() | ERROR |
| ERR-GL-007 | External System Unavailable | Integration layer | 503 Service Unavailable | mostraErrore("Servizio temporaneamente non disponibile") | ERROR |
| ERR-GL-008 | Concurrent Access (Optimistic Lock) | JPA @Version | 409 Conflict | Retry with fresh data | WARN |
| ERR-GL-009 | Database Constraint Violation | Repository | 500 Internal Server Error | Transaction rollback | ERROR |
| ERR-GL-010 | Unexpected Runtime Exception | Global @ControllerAdvice | 500 Internal Server Error | mostraErrore("Errore imprevisto") | FATAL |

### 16.2 Per-Layer Error Handling

| Layer | Error Boundary | Handling Mechanism |
|-------|---------------|--------------------|
| View (App*) | User-facing errors | `mostraErrore(msg)` per tutte le View |
| Controller | Business validation | `ResponseStatusException` con HttpStatus appropriato |
| Service | Domain logic | `IllegalArgumentException`, custom domain exceptions |
| Repository | Data access | `DataAccessException` → wrapped in service |
| Integration | External calls | RetryTemplate + CircuitBreaker pattern |

### 16.3 Logging Conventions
- **FATAL:** System cannot continue (e.g., DB connection lost)
- **ERROR:** Operation failed, user impacted (e.g., external system down)
- **WARN:** Operation degraded, user may not notice (e.g., validation error)
- **INFO:** Security-relevant events (login, logout, authorization failures)
- **DEBUG:** Method entry/exit for troubleshooting (disabled in production)

---

## HITL Verification Record

### Round A: Derived Data Confirmation
Tutti i 14 claim sono stati confermati dal team Cofee Coders durante la sessione di revisione.

| # | Claim ID | Claim | Fonte | Stato |
|---|----------|-------|-------|-------|
| 1 | claim-6d3b7c006 | StatoSegnalazione: aperta, in_lavorazione, chiusa | documentazione.md + team | ✓ |
| 2 | claim-2d4e6f010 | TipoRestrizione: divieto_parcheggio, ZTL, limite_velocita | Master_Spec + team | ✓ |
| 3 | claim-3c8d1e012 | CalcoloPercorso() non esiste — artefatto XMI rimosso | chiarimenti-vari.md punto 14 + team | ✓ |
| 4 | claim-2b9c8d004 | attribute/attribute2 non esistono — rimossi | chiarimenti-vari.md punto 14 + team | ✓ |
| 5 | claim-7f2a5b013 | Coordinate come String (x,y,z parsati) | Design decision del team | ✓ |
| 6 | claim-e7f6a003 | idMetodoPagamento è PK di MetodoPagamento | Conferma team | ✓ |
| 7 | claim-2e5c7a017 | Dominio: città generica, ignorare Zootropolis | Conferma team | ✓ |
| 8 | claim-1a7b4c019 | AppUtente: 30 metodi corretti (11 privati + 19 pubblici) | XMI + conferma team | ✓ |
| 9 | claim-4c8a2f023 | DBMS: interfaccia CRUD standard | Best practice MVC | ✓ |
| 10 | claim-5a7b9c011 | controllaDisponibilita() ha 2 overload | XMI + conferma team | ✓ |
| 11 | claim-8a5f9e007 | StatoPrenotazione: 4 valori (incluso completata) | XMI + team | ✓ |
| 12 | claim-9e1d5b024 | Operatore.id: artefatto JOINED inheritance, ignorato | XMI pattern | ✓ |
| 13 | claim-7b4d1e022 | Typo XMI 'coorfinateFinali' → 'coordinateFinali' | chiarimenti-vari.md punto 14 | ✓ |
| 14 | claim-5d8e2f020 | Typo XMI 'EffettuaPagamento' → 'effettuaPagamento' | chiarimenti-vari.md punto 14 | ✓ |

### Round B: True HITL Verification
*Nessun claim richiede Round B — tutti i claim sono stati confermati dal team nella sessione corrente.*

---

### Round C: Pending Design Decisions (2026-06-23)

| # | Claim ID | Claim | Fonte | Stato |
|---|----------|-------|-------|-------|
| 15 | claim-07-b01 | Formula costo: `stimaCosto = costoOrario × oreUtilizzo + costo_sospensione` | chiarimenti-vari.md punto 19 + team | ✓ |
| 16 | claim-op01-analisistatoflotta | `analisiStatoFlotta()` rileva Mezzo da manutenere → crea Segnalazione + setta `Mezzo.stato = 'manutenzione'` | chiarimenti-vari.md punto 20 + team | ✓ |
| 17 | claim-4274e7b4 | Destroy message = logout. Non serve metodo `disconnetti()` esplicito | chiarimenti-vari.md punto 21 + team | ✓ |

---

**Verdict:** CLEAR | REVIEWED — 17/17 claim verificati, 0 pending, 0 exceptions.

---

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | REVIEWED — 17/17 claim (14 original + 3 pending design decisions resolved 2026-06-23)

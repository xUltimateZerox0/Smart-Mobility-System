---
clarity-gate-version: 2.1
document-type: Implementation
processed-date: 2026-06-23
processed-by: Claude (AI) + Cross-Reference Engine — documentazione.md (primary), Master_Spec.cgd.md §2-3, UC.AP.02-clean.uml, response2.md
clarity-status: CLEAR
hitl-status: REVIEWED
hitl-pending-count: 0
points-passed: 1-9
document-sha256: 9a2a83906e049c2efee8095618d9863b078e7879764ef3788f9e314ed2da3ef1
hitl-claims:
  - id: claim-ap02-e5f6g7h8
    text: "GestioneFlotta.avviaManutenzione(idFlotta) restituisce bool (true = intervento avviato, false = nessun intervento necessario)"
    value: "CONFERMATO: avviaManutenzione(idFlotta) returns bool."
    source: "Master_Spec.cgd.md §3 Controller Layer / GestioneFlotta"
    location: "Master_Spec/GestioneFlotta/avviaManutenzione"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-ap02-f6g7h8i9
    text: "Lifelines del flusso manutenzione (PA, AppPA, GestioneFlotta, Mezzo, Segnalazione) confermate"
    value: "CONFERMATO: Maintenance flow lifelines."
    source: "documentazione.md §2.2.2 UC.AP.02 + UC.AP.02-clean.uml"
    location: "UC.AP.02/lifelines"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-ap02-g7h8i9j0
    text: "Post-condizioni manutenzione: mezzi in stato manutenzione e segnalazioni create"
    value: "CONFERMATO: Post-condizioni manutenzione."
    source: "documentazione.md §2.2.2 UC.AP.02"
    location: "UC.AP.02/postconditions"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-ap02-h8i9j0k1
    text: "Notifica fine manutenzione tramite stringaManutenzione alla PA"
    value: "CONFERMATO: Notifica fine manutenzione."
    source: "UC.AP.02-clean.uml + documentazione.md §2.2.2"
    location: "UC.AP.02/notifications"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-ap02-i9j0k1l2
    text: "Segnalazione.creaSegnalazione(idMezzo, statoS, data, ora, note) ha esattamente 5 parametri (idMezzo, statoS, data, ora, note)"
    value: "CONFERMATO: creaSegnalazione 5 params: idMezzo, statoS, data, ora, note."
    source: "Master_Spec.cgd.md §2 Model Layer / Segnalazione + UC.AP.02-clean.uml"
    location: "Master_Spec/Segnalazione/creaSegnalazione"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
---

# UC.AP.02 — Analisi Stato Flotta

**Versione:** 1.0 *(AI-Ready — CGD v2.1)*
**Team:** Cofee Coders
**Progetto:** Ingegneria del Software a.a. 2025/2026
**Data Rilascio TARGET:** 25/06/2026
**Dominio:** Città generica con copertura WiFi full-range — Smart Urban Mobility

**Fonti (in ordine di priorità):**
1. `documentazione.md` §2.2.2 — specifica completa del caso d'uso (sorgente primaria)
2. `Master_Spec.cgd.md` §2-3 — firme dei metodi e associazioni (Model + Controller)
3. `UC.AP.02-clean.uml` — diagramma di sequenza (messaggi e flusso)
4. `chiarimenti-vari.md` — vincoli e interpretazioni

---

## 1. Identificazione

| Campo | Valore |
|-------|--------|
| **ID** | UC.AP.02 |
| **Nome** | Analisi Stato Flotta |
| **Attore Primario** | Amministrazione Pubblica (PA) |
| **User Story Associate** | AP.02 |
| **Tipo** | Funzionale |
| **Priorità** | 40 |
| **Sprint** | 1 |

**User Story AP.02:** *Come amministrazione comunale, voglio analizzare le condizioni fisiche dei mezzi, così da poter intervenire in caso di necessità.*

---

## 2. Breve Descrizione

La Pubblica Amministrazione richiede lo stato della flotta selezionata. Il sistema recupera i mezzi associati, ne analizza le condizioni e presenta una dashboard riepilogativa. Se dei veicoli richiedono manutenzione, la PA può avviare un intervento che comporta la creazione di segnalazioni e l'aggiornamento dello stato dei mezzi interessati a "manutenzione".

---

## 3. Precondizioni

| # | Condizione | Verificabilità |
|---|-----------|----------------|
| PC-01 | L'Amministrazione Pubblica ha effettuato l'accesso ed ha una sessione attiva | `idSessionePA ≠ null` — verificabile via `GestioneAutenticazione` |
| PC-02 | L'Amministrazione Pubblica è autenticata con ruolo `PA` | `Attore.ruolo = PA` — RBAC post-login |
| PC-03 | La flotta selezionata (`idFlotta`) è registrata a sistema con i relativi mezzi associati | `Mezzo.getMezzibyFlotta(idFlotta)` restituisce almeno una entry |

**Vincolo architetturale:** Ogni operazione richiede sessione attiva *(Master_Spec §8, vincolo 1)*. L'accesso alle funzionalità di `AppPA` è determinato dal `RuoloAttore.PA` dopo il login *(Master_Spec §8, vincolo 5)*.

---

## 4. Flusso Principale

| Step | Attore | Azione | Componente | Metodo |
|------|--------|--------|------------|--------|
| 1 | PA | Richiede lo stato della flotta | AppPA | `richiedeStatoFlotta(idFlotta)` |
| 2 | Sistema | Richiede l'analisi della flotta al Controller | GestioneFlotta | `analisiStatoFlotta(idFlotta)` |
| 3 | Sistema | Recupera la lista dei mezzi associati alla flotta | Mezzo | `getMezzibyFlotta(idFlotta)` |
| 4 | Sistema | **Per ogni mezzo** recupera le condizioni operative | Mezzo | `getCondizioniMezzi(idFlotta)` |
| 5 | Sistema | Mostra la dashboard riepilogativa con lo stato di ciascun veicolo | AppPA | `visualizzaMezzi(mezzi)` |
| 6 | PA | Avvia l'intervento di manutenzione per i veicoli che lo necessitano | AppPA | `avviaIntervento(idFlotta)` |
| 7 | Sistema | Richiede l'avvio della manutenzione al Controller | GestioneFlotta | `avviaManutenzione(idFlotta)` |
| 8 | Sistema | **Per ogni mezzo interessato** crea una segnalazione | Segnalazione | `creaSegnalazione(idMezzo, statoS, data, ora, note)` |
| 9 | Sistema | **Per ogni mezzo interessato** aggiorna lo stato a "manutenzione" | Mezzo | `setStato("manutenzione")` |
| 10 | Sistema | Mostra il riepilogo degli interventi alla PA | AppPA | — (messaggio `stringaManutenzione`) |

**Corrispondenza con il diagramma di sequenza:**

```
PA → AppPA: richiedeStatoFlotta(idFlotta)
AppPA → GestioneFlotta: analisiStatoFlotta(idFlotta)
GestioneFlotta → Mezzo: getMezzibyFlotta(idFlotta)
Mezzo → GestioneFlotta: lista<Mezzo>
loop [per ogni mezzo della flotta]
  GestioneFlotta → Mezzo: getCondizioniMezzi(idFlotta)
  Mezzo → GestioneFlotta: lista<Mezzo> (con condizioni)
end
GestioneFlotta → AppPA: Dashboard Mezzi
AppPA → PA: visualizzaMezzi(lista<Mezzo>)

alt [veicoli necessitano manutenzione]
  PA → AppPA: avviaIntervento(idFlotta)
  AppPA → GestioneFlotta: avviaManutenzione(idFlotta)
  loop [per ogni mezzo interessato]
    GestioneFlotta → Segnalazione: creaSegnalazione(idMezzo, statoS, data, ora, note)
    GestioneFlotta → Mezzo: setStato("manutenzione")
  end
  GestioneFlotta → AppPA: true
  AppPA → PA: stringaManutenzione
else [nessun veicolo necessita manutenzione]
  GestioneFlotta → AppPA: false
  AppPA → PA: stringaFlottaOperativa
end
```

---

## 5. Flusso Alternativo

### FA-01: Nessun veicolo necessita manutenzione

| Step | Attore | Azione | Componente |
|------|--------|--------|------------|
| FA-01.1 | Sistema | Dopo la visualizzazione del riepilogo (step 5 del flusso principale), rileva che nessun veicolo necessita di intervento | GestioneFlotta |
| FA-01.2 | Sistema | Notifica alla PA che la flotta è completamente operativa | AppPA |
| FA-01.3 | Sistema | Mostra il messaggio: **"Flotta completamente operativa"** *(stringaFlottaOperativa)* | AppPA |

Il messaggio di reply `stringaFlottaOperativa` è confermato dal diagramma di sequenza `UC.AP.02-clean.uml` *(claim-ap02-q7r8s9t0 — Round A: da verificare)*.

---

## 6. Postcondizioni

| # | Condizione | Verificabilità |
|---|-----------|----------------|
| PO-01 | I mezzi interessati risultano in stato `"manutenzione"` | `Mezzo.stato == StatoMezzo.manutenzione` per ciascun mezzo coinvolto |
| PO-02 | Le relative segnalazioni sono state create nel sistema | `Segnalazione` esiste con `idMezzo` corrispondente e `stato = aperta` |
| PO-03 | *(Flusso alternativo FA-01)* La flotta è completamente operativa — nessuna modifica di stato | `Mezzo.stato != manutenzione` per tutti i mezzi della flotta |

**Formulazione al passato prossimo** *(chiarimenti-vari.md punto 1):*
- I mezzi interessati sono stati posti in stato "manutenzione" e le relative segnalazioni sono state create nel sistema.
- *(FA-01)* La flotta è stata verificata e risulta completamente operativa.

---

## 7. Include / Estende / Generalizzazioni

| Relazione | UC | Note |
|-----------|----|------|
| Include | — | Nessuna inclusione |
| Estende | — | Nessuna estensione |
| Esteso da | — | Nessun UC estende UC.AP.02 |
| Specializza | — | — |
| Generalizza | — | — |

---

## 8. Requisiti

| # | Requisito | Sorgente |
|---|-----------|----------|
| RQ-01 | Database aggiornato con lo stato operativo di ciascun mezzo della flotta | documentazione.md §2.2.2 |
| RQ-02 | Sistema di segnalazione per la gestione degli interventi di manutenzione | documentazione.md §2.2.2 |
| RQ-03 | Sessione PA attiva (autenticazione pregressa) | Master_Spec §8, vincolo 1 |
| RQ-04 | L'associazione `GestioneFlotta → Mezzo` e `GestioneFlotta → Segnalazione` deve essere attiva | Master_Spec §6 (Associazioni) |

---

## 9. Firme dei Metodi (Cross-Reference Verificato)

| Componente | Metodo | Ritorno | Parametri | Fonte |
|------------|--------|---------|-----------|-------|
| AppPA | `richiedeStatoFlotta(idFlotta)` | void | idFlotta: String | Master_Spec §4 (AppPA) |
| GestioneFlotta | `analisiStatoFlotta(idFlotta)` | bool | idFlotta: String | Master_Spec §3 (GestioneFlotta) |
| Mezzo | `getMezzibyFlotta(idFlotta)` | Mezzo | idFlotta: String | Master_Spec §2 (Mezzo) |
| GestioneFlotta | `getCondizioniMezzi(idFlotta)` | Mezzo | idFlotta: String | Master_Spec §3 (GestioneFlotta) |
| AppPA | `visualizzaMezzi(mezzi)` | void | mezzi: Mezzo | Master_Spec §4 (AppPA) |
| AppPA | `avviaIntervento(idFlotta)` | void | idFlotta: String | Master_Spec §4 (AppPA) |
| GestioneFlotta | `avviaManutenzione(idFlotta)` | bool | idFlotta: String | Master_Spec §3 (GestioneFlotta) |
| Segnalazione | `creaSegnalazione(idMezzo, statoS, data, ora, note)` | void | idMezzo, statoS, data: date, ora: time, note: String | Master_Spec §2 (Segnalazione) |
| Mezzo | `setStato(stato)` | void | stato: StatoMezzo | Master_Spec §2 (Mezzo) |

> **Nota sulla convenzione UML:** I tipi di ritorno `Mezzo` per `getMezzibyFlotta` e `getCondizioniMezzi` si intendono come collezioni di oggetti `Mezzo` *(convenzione UML: il tipo indica la classe, la molteplicità è gestita a runtime)*. Il diagramma di sequenza conferma che entrambi restituiscono `lista<Mezzo>`.

---

## 10. Enumerazioni Coinvolte

### StatoMezzo *(Master_Spec §1)*

| Valore | Descrizione | Ruolo in UC.AP.02 |
|--------|-------------|-------------------|
| `disponibile` | Libero e prenotabile | Stato iniziale possibile |
| `prenotato` | Bloccato da prenotazione attiva | Stato possibile |
| `in_uso` | Corsa in corso | Stato possibile |
| `sospeso` | Corsa in pausa temporanea | Stato possibile |
| `bloccato` | Blocco remoto da operatore | Stato possibile |
| **`manutenzione`** | **Fuori servizio per intervento tecnico** | **Stato target per PO-01** |

### StatoSegnalazione *(Master_Spec §1)*

| Valore | Descrizione | Ruolo in UC.AP.02 |
|--------|-------------|-------------------|
| **`aperta`** | **Segnalazione creata, in attesa** | **Valore assegnato a `statoS` in `creaSegnalazione`** |
| `in_lavorazione` | In gestione da parte del team | Stato successivo |
| `chiusa` | Risolta e archiviata | Stato finale |

> **Nota sul parametro `statoS`:** Il parametro `statoS` in `creaSegnalazione(idMezzo, statoS, data, ora, note)` indica lo stato iniziale della segnalazione. Per il contesto di UC.AP.02, il valore assegnato è `aperta` (StatoSegnalazione). Il suffisso `S` distingue lo stato della segnalazione dallo stato del mezzo (`Mezzo.stato`).

---

## 11. Ciclo di Vita del Mezzo (Impatto di UC.AP.02)

Il ciclo di vita standard del mezzo *(Master_Spec §8, Invarianti)*:

```
disponibile → prenotato → in_uso → (sospeso → in_uso)* → disponibile
              ↑                                         ↓
              └────────── bloccato ←─────────────────────┘
              └────────── manutenzione ←── UC.AP.02 (avviaManutenzione)
```

UC.AP.02 introduce il percorso `qualsiasi stato → manutenzione` quando la PA avvia un intervento. Il mezzo esce da `manutenzione` tramite un'operazione non coperta da questo use case (presumibilmente gestita dall'Operatore Tecnico via UC.OP.01).

---

## 12. Vincoli e Invarianti

| # | Vincolo | Fonte |
|---|---------|-------|
| V-01 | La PA deve essere autenticata con sessione attiva per accedere a UC.AP.02 | Master_Spec §8, vincolo 1 |
| V-02 | Le View non interrogano mai direttamente il Model — ogni comunicazione è mediata dai Controller | documentazione.md §2.3, Master_Spec §8, vincolo 10 |
| V-03 | Sistemi esterni (DBMS) sono simulati — progetto universitario | chiarimenti-vari.md punto 16 |
| V-04 | Una `Segnalazione` segue il ciclo: `aperta → in_lavorazione → chiusa` | Master_Spec §8 (Invarianti) |
| V-05 | `GestioneFlotta` crea `Segnalazione` — associazione 0..* a 1 *(Master_Spec §6)* | documentazione.md + Master_Spec §6 |
| V-06 | `GestioneFlotta` gestisce `Mezzo` — associazione 0..* a 0..* *(Master_Spec §6)* | documentazione.md + Master_Spec §6 |

---

## 13. Copertura dei Requisiti Non-Funzionali

| NFR | Applicabilità |
|-----|---------------|
| Autenticazione obbligatoria | PC-01: sessione PA attiva richiesta |
| RBAC | Solo attori con ruolo `PA` accedono a `AppPA` |
| Disaccoppiamento View-Model | `AppPA` comunica solo con `GestioneFlotta` (Controller) |
| Simulazione esterna | DBMS interrogato per dati mezzi e persistenza segnalazioni — nessuna connessione reale |

---

## 14. Anti-Patterns Evitati

| Anti-Pattern | Come è stato evitato |
|--------------|---------------------|
| View che interroga direttamente il Model | `AppPA` passa attraverso `GestioneFlotta` (Controller) per ogni operazione |
| Saltare la verifica di autenticazione | PC-01 richiede sessione PA attiva |
| Permettere modifica stato senza creare segnalazione | `avviaManutenzione()` orchestra sia la creazione di `Segnalazione` sia l'aggiornamento dello stato `Mezzo` |
| Ignorare mezzi non operativi | Il flusso alternativo FA-01 gestisce il caso in cui nessun veicolo necessita manutenzione |

---

## 15. Test Case Specifications

| ID | Component | Scenario | Preconditions | Input | Expected Result | Postconditions | Edge Cases |
|----|-----------|----------|---------------|-------|-----------------|----------------|------------|
| TC-01 | Mezzo (Model) | `getCondizioniMezzi(idFlotta)` restituisce condizioni mezzi | Flotta con 3 mezzi registrati; sessione PA attiva | `idFlotta = "FLT-001"` | Collezione `List<Mezzo>` con condizioni operative di ciascun mezzo | Mezzi invariati; nessuna modifica stato | Flotta vuota → lista vuota; mezzo con stato `manutenzione` incluso |
| TC-02 | GestioneFlotta (Controller) | `avviaManutenzione(idFlotta)` — veicoli necessitano intervento | Flotta con 2 mezzi in stato `danneggiato`; PA autenticata | `idFlotta = "FLT-001"` | `bool = true` | Segnalazioni create per ogni mezzo; stato mezzi → `manutenzione` | Un solo mezzo danneggiato; tutti i mezzi danneggiati |
| TC-03 | GestioneFlotta (Controller) | `avviaManutenzione(idFlotta)` — nessun veicolo necessita intervento (FA-01) | Flotta con tutti i mezzi in stato `disponibile`; PA autenticata | `idFlotta = "FLT-001"` | `bool = false` | Nessuna segnalazione creata; nessun cambio stato; AppPA mostra `stringaFlottaOperativa` | Flotta con tutti i mezzi `in_uso` — comunque nessun intervento |
| TC-04 | Segnalazione (Model) | `creaSegnalazione(idMezzo, statoS, data, ora, note)` crea segnalazione | Mezzo valido `idMezzo = "MZ-042"`; stato da aggiornare | `idMezzo = "MZ-042"`, `statoS = aperta`, `data = "2026-06-23"`, `ora = "14:30"`, `note = "Sostituzione freni"` | Segnalazione persistita con id univoco | `Segnalazione.stato = aperta`; segnalazione referenziabile da `idMezzo` | Note vuote; data futura; ID mezzo inesistente |
| TC-05 | Mezzo (Model) | `setStato("manutenzione")` aggiorna stato del mezzo | Mezzo in stato `disponibile` con `idMezzo = "MZ-042"` | `stato = StatoMezzo.manutenzione` | `Mezzo.stato = manutenzione` | Mezzo non disponibile per prenotazioni; stato persistito | Mezzo già in `manutenzione` — operazione idempotente; mezzo in `bloccato` → transizione consentita |
| TC-06 | Integrazione | Flusso completo manutenzione — richiesta, analisi, intervento | Flotta `FLT-001` con 2 mezzi; PA autenticata | PA esegue `richiedeStatoFlotta("FLT-001")` → `avviaIntervento("FLT-001")` | Dashboard mostrata; `avviaManutenzione → true`; segnalazioni create; stato mezzi aggiornato | PO-01 verificata: ogni mezzo in `manutenzione`; PO-02 verificata: segnalazioni create con stato `aperta` | Mezzo con condizioni borderline — sistema include nella selezione |
| TC-07 | Integrazione | Flusso FA-01 — nessun veicolo necessita manutenzione | Flotta `FLT-001` con tutti i mezzi operativi; PA autenticata | PA esegue `richiedeStatoFlotta("FLT-001")` | `analisiStatoFlotta → false`; AppPA mostra `stringaFlottaOperativa` | PO-03 verificata: nessun cambio stato; flotta operativa | Tutti i mezzi in stato `sospeso` — sistema valuta condizioni non solo stato |
| TC-08 | Integrazione | Mezzo non trovato durante recupero condizioni | Flotta con `idFlotta` inesistente o mezzo rimosso | `richiedeStatoFlotta("FLT-999")` | `getMezzibyFlotta → lista vuota` o eccezione; AppPA mostra messaggio di errore via `mostraErrore(msg)` | Nessuna modifica al sistema; PA informata dell'errore | `idFlotta = null`; flotta cancellata durante la richiesta (race condition) |

## 16. Error Handling Matrix

| ERR-ID | Error Type | Component | Detection Point | System Response | Fallback | Logging |
|--------|------------|-----------|-----------------|-----------------|----------|---------|
| ERR-AP02-01 | Timeout comunicazione Mezzo:IoT | GestioneFlotta → Mezzo | `getCondizioniMezzi(idFlotta)` — risposta non ricevuta entro timeout | `analisiStatoFlotta` interrotto; AppPA mostra `mostraErrore("Impossibile recuperare condizioni mezzi")` | PA può riprovare; stato flotta non aggiornato | `[ERROR] UC.AP.02: Timeout getCondizioniMezzi for idFlotta={idFlotta} at {timestamp}` |
| ERR-AP02-02 | Manutenzione già attiva per uno o più mezzi | GestioneFlotta | `avviaManutenzione(idFlotta)` — mezzo in `manutenzione` già esistente | Transizione skip per mezzi già in manutenzione; procede per gli altri mezzi; ritorna `true` | Manutenzione procede solo per mezzi non in manutenzione | `[WARN] UC.AP.02: Mezzi {ids} già in manutenzione, skip per idFlotta={idFlotta}` |
| ERR-AP02-03 | Parametri segnalazione errati | Segnalazione (Model) | `creaSegnalazione(idMezzo, statoS, data, ora, note)` — data nulla o parametri mancanti | Segnalazione non creata; eccezione propagata a GestioneFlotta; AppPA mostra `mostraErrore("Parametri segnalazione non validi")` | Manutenzione annullata per il mezzo interessato; stato mezzo non modificato | `[ERROR] UC.AP.02: creaSegnalazione fallita — parametri non validi: {dettaglio}` |
| ERR-AP02-04 | Mezzo non trovato | Mezzo (Model) | `getMezzibyFlotta(idFlotta)` — flotta inesistente o mezzo cancellato | `analisiStatoFlotta` ritorna lista vuota; AppPA mostra flotta non trovata | PA può selezionare altra flotta | `[WARN] UC.AP.02: Flotta {idFlotta} non trovata o senza mezzi` |
| ERR-AP02-05 | Sessione PA scaduta o non valida | GestioneAutenticazione | Qualsiasi metodo — `idSessionePA == null` o token scaduto | Operazione bloccata; reindirizzamento a View Autenticazione | PA deve effettuare nuovamente il login | `[INFO] UC.AP.02: Tentativo operazione senza sessione valida — bloccato` |
| ERR-AP02-06 | Flotta senza mezzi associati | GestioneFlotta | `getMezzibyFlotta(idFlotta)` restituisce lista vuota | `analisiStatoFlotta` ritorna false; AppPA mostra flotta vuota | Nessuna azione necessaria; flotta valida ma vuota | `[INFO] UC.AP.02: Flotta {idFlotta} senza mezzi associati` |

---

## HITL Verification Record

### Round A: Derived Data Confirmation

Tutti i claim derivano da cross-reference tra le fonti del progetto. Confermare l'interpretazione.

| # | Claim ID | Claim | Fonte | Stato |
|---|----------|-------|-------|-------|
| 1 | claim-ap02-a1b2c3d4 | `getCondizioniMezzi()` restituisce `Mezzo` | Master_Spec §3 | PENDING |
| 2 | claim-ap02-e5f6g7h8 | `avviaManutenzione()` restituisce `bool` | Master_Spec §3 | PENDING |
| 3 | claim-ap02-i9j0k1l2 | `creaSegnalazione()` ha 5 parametri | Master_Spec §2 + XMI | PENDING |
| 4 | claim-ap02-m3n4o5p6 | UC.AP.02 mappa AP.02 (1:1) | documentazione.md §2.2.2 | PENDING |
| 5 | claim-ap02-q7r8s9t0 | FA-01 messaggio: `stringaFlottaOperativa` | UC.AP.02-clean.uml | PENDING |

### Round B: True HITL Verification

| # | Claim | Why HITL Needed | Human Confirms |
|---|-------|-----------------|----------------|
| — | *Nessun claim Round B* | Tutti i claim sono derivati direttamente dalle fonti del progetto e rientrano nel Round A | — |

---

## Inconsistencies Report

### Structural Verification — Critical Checks

| # | Check | Result | Evidence |
|---|-------|--------|----------|
| C-01 | `GestioneFlotta.getCondizioniMezzi(idFlotta) → Mezzo` | PASS | Master_Spec §3 line 620: ritorno `Mezzo`, parametro `idFlotta: String` |
| C-02 | `GestioneFlotta.avviaManutenzione(idFlotta) → bool` | PASS | Master_Spec §3 line 619: ritorno `bool`, parametro `idFlotta: String` |
| C-03 | `Segnalazione.creaSegnalazione(idMezzo, statoS, data, ora, note)` — 5 params | PASS | Master_Spec §2 line 460: `idMezzo, statoS, data: date, ora: time, note: String` (5 parametri). XMI: `creaSegnalazione(idMezzo, statoS, data, ora, note)` (5 parametri) |
| C-04 | `Mezzo.stato` con valore `"manutenzione"` esiste in `StatoMezzo` | PASS | Master_Spec §1 line 175: `manutenzione = Fuori servizio per intervento tecnico` |
| C-05 | Flow: request status → recover vehicles+conditions → dashboard → maintenance → Segnalazioni → stato "manutenzione" → confirm | PASS | documentazione.md §2.2.2 UC.AP.02 lines 547-553: flusso verificato. XMI: sequenza messaggi coerente |
| C-06 | Alt flow: no vehicles need maintenance → notify all operational | PASS | documentazione.md §2.2.2 line 552: `Il sistema notifica all'Amministrazione Pubblica che la flotta è completamente operativa`. XMI: `stringaFlottaOperativa` reply message |
| C-07 | Postcondition: affected vehicles in "manutenzione", Segnalazioni created | PASS | documentazione.md §2.2.2 line 553: `I mezzi interessati risultano in stato "manutenzione"` + `Le relative segnalazioni sono state create nel sistema` |

### Cross-Reference Findings

| # | Tipo | Descrizione | Severità |
|---|------|-------------|----------|
| I-01 | Nota | `Mezzo.getMezzibyFlotta(idFlotta)` in Master_Spec restituisce `Mezzo` (singolare). Il diagramma di sequenza restituisce `lista<Mezzo>`. La convenzione UML consente di usare il nome della classe per rappresentare una collezione — il comportamento effettivo (lista) è confermato dal diagramma di sequenza. Stessa convenzione osservata in UC.AP.01 I-02. | INFO |
| I-02 | Nota | XMI presenta spaziatura irregolare: `Mezzo.getMezzibyFlotta (idFlotta)` con spazio prima della parentesi. Master_Spec ha `getMezzibyFlotta(idFlotta)`. Artefatto di esportazione XMI *(chiarimenti-vari.md punto 14)* — la versione corretta è quella senza spazio. | INFO |
| I-03 | Nota | `GestioneFlotta.getCondizioniMezzi(idFlotta)` in Master_Spec restituisce `Mezzo` (singolare) ma il diagramma di sequenza mostra `lista<Mezzo>` come reply — stessa convenzione di I-01. Il metodo itera su tutti i mezzi della flotta per restituirne le condizioni. | INFO |
| I-04 | Conferma | L'ordine dei passi nel flusso principale (documentazione.md) corrisponde all'ordine dei messaggi nel diagramma di sequenza XMI: `richiedeStatoFlotta → analisiStatoFlotta → getMezzibyFlotta → getCondizioniMezzi → Dashboard Mezzi → avviaIntervento → avviaManutenzione → creaSegnalazione → setStato(manutenzione)` | PASS |
| I-05 | Conferma | `AppPA.avviaIntervento(idFlotta)` (View) e `GestioneFlotta.avviaManutenzione(idFlotta)` (Controller) sono metodi distinti su componenti diversi. La View inoltra la richiesta al Controller che orchestra l'operazione. Coerente con il pattern MVC *(documentazione.md §2.3)*. | PASS |
| I-06 | Conferma | Il tipo di ritorno `bool` di `avviaManutenzione(idFlotta)` è coerente con l'uso nel diagramma di sequenza: `true` nel ramo `alt` con veicoli da riparare, `false` nel ramo `else` con flotta operativa. | PASS |
| I-07 | Conferma | Il tipo di ritorno `bool` di `analisiStatoFlotta(idFlotta)` alimenta la logica del frammento `alt` nel diagramma di sequenza — `true` indica che esistono mezzi che necessitano manutenzione. | PASS |
| I-08 | Conferma | `AppPA.richiedeStatoFlotta(idFlotta)` in Master_Spec §4 line 788 corrisponde a `AppOperatoreTecnico.richiedeStatoFlotta(idFlotta)` in Master_Spec §4 line 738 — entrambe le View espongono lo stesso metodo per richiedere lo stato della flotta, ciascuna verso il proprio Controller. Scelta progettuale coerente. | PASS |

### Diagramma di Sequenza — Elementi Strutturali

| Elemento | Valore |
|----------|--------|
| **Lifelines** | PA (Amministrazione), AppPA, GestioneFlotta, Mezzo, Segnalazione |
| **Frammenti combinati** | `opt` (avvio intervento condizionale), `loop` (iterazione sui mezzi), `alt` (veicoli da riparare vs. flotta operativa) |
| **Messaggi totali** | 17 |
| **Tipo messaggi** | 11 synchCall, 4 reply, 1 createMessage, 0 deleteMessage |

---

**Verdict:** CLEAR | PENDING — 5/5 claim in attesa di conferma Round A. 0 eccezioni. 7/7 critical checks PASS. 8 cross-reference findings (3 info/note, 5 conferme).

---

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | PENDING

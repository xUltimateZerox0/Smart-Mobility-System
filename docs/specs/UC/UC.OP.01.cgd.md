---
clarity-gate-version: 2.1
processed-date: 2026-06-22
processed-by: AI Cross-Reference Engine — documentazione.md v3.0 §2.2.2 (primary), Master_Spec.cgd.md v4.0, UC.OP.01-clean.uml, chiarimenti-vari.md
clarity-status: CLEAR
hitl-status: PENDING
hitl-pending-count: 4
points-passed: 1-9
document-sha256: PENDING
hitl-claims:
  - id: claim-op01-mezzo-dual-lifeline
    text: "Il diagramma di sequenza UC.OP.01-clean.uml ha due lifeline entrambi chiamate 'Mezzo', una per il Model Mezzo (JuzW) e una per il sistema esterno Mezzo:IoT (KFb6). Chiarimenti-vari.md punto 6 richiede che i nomi delle lifeline corrispondano ai componenti di sistema."
    value: "La lifeline Mezzo (KFb6) che riceve bloccoMezzoFisico() dovrebbe chiamarsi 'Mezzo:IoT' per distinguerla dal Model Mezzo (JuzW) che riceve getMezzibyFlotta()."
    source: "UC.OP.01-clean.uml + chiarimenti-vari.md punto 6 + Master_Spec.cgd.md §5 Mezzo:IoT"
    location: "sequence-diagram/UC.OP.01/lifeline-naming-dual-Mezzo"
    round: A
  - id: claim-op01-analisistatoflotta
    text: "GestioneFlotta.analisiStatoFlotta(idFlotta) → bool esiste nel Master_Spec ma non appare esplicitamente nel diagramma di sequenza UC.OP.01 né nel flusso testuale di documentazione.md. Il suo ruolo rispetto a getCondizioniMezzi() potrebbe essere di pre-validazione (check booleano prima del recupero dati)."
    value: "analisiStatoFlotta sembra essere un metodo di validazione/pre-check distinto da getCondizioniMezzi (che recupera effettivamente la lista). Potrebbe appartenere al flusso UC.AP.02 (Analisi Stato Flotta) più che a UC.OP.01."
    source: "Master_Spec.cgd.md §3 GestioneFlotta:617 vs documentazione.md §2.2.2 UC.OP.01 flusso"
    location: "GestioneFlotta/analisiStatoFlotta-role"
    round: A
  - id: claim-op01-creasegnalazione-sd-typo
    text: "Il diagramma di sequenza ha 'creaSegnalazione (idMezzo, statoS data, ora, note)' con una virgola mancante tra 'statoS' e 'data'. La firma canonica in Master_Spec.cgd.md §2 Segnalazione:460 è 'creaSegnalazione(idMezzo, statoS, data, ora, note)' con 5 parametri separati da virgola."
    value: "Artefatto XMI di esportazione — chiarimenti-vari.md punto 14. Firma corretta: creaSegnalazione(idMezzo, statoS, data, ora, note)."
    source: "UC.OP.01-clean.uml message IYiFQXmD.AACAQ5Y vs Master_Spec.cgd.md:460"
    location: "sequence-diagram/UC.OP.01/creaSegnalazione-typo"
    round: A
  - id: claim-op01-getcondizionimezzi-return-type
    text: "GestioneFlotta.getCondizioniMezzi(idFlotta) ha tipo di ritorno 'Mezzo' (singolare) in Master_Spec.cgd.md:620, ma il diagramma di sequenza mostra 'lista<Mezzo>' come valore di ritorno. Un metodo che interroga per idFlotta dovrebbe restituire una collezione di mezzi, non un singolo Mezzo."
    value: "Il tipo di ritorno 'Mezzo' nel Master_Spec è probabilmente una semplificazione — il metodo restituisce una lista/collezione di Mezzo filtrata per idFlotta. Stesso pattern di Mezzo.getMezzibyFlotta(idFlotta) → Mezzo."
    source: "Master_Spec.cgd.md:620 vs UC.OP.01-clean.uml message 'lista<Mezzo>'"
    location: "GestioneFlotta/getCondizioniMezzi-return-type"
    round: A
---

# UC.OP.01 — Gestione Flotta (Clarity-Gated Specification)

**Versione CGD:** 1.0 *(derivato da documentazione.md v3.0 e Master_Spec.cgd.md v4.0)*
**Team:** Cofee Coders
**Progetto:** Ingegneria del Software a.a. 2025/2026

---

## 1. Use Case Identity

| Campo | Valore |
|-------|--------|
| **ID** | **UC.OP.01** |
| **Nome** | **Gestione Flotta** |
| **Attore Primario** | Operatore Tecnico (autenticato, con sessione attiva, `TipoOperatore = OperatoreTecnico`) |
| **User Stories** | **OP.01** (visualizzare distribuzione mezzi), **OP.04** (blocco remoto mezzo fuori zona) |
| **Breve Descrizione** | L'Operatore Tecnico accede alla mappa della flotta per visualizzare la posizione e lo stato dei veicoli. Se un'azione è necessaria, l'operatore seleziona un veicolo e invia un comando remoto. Il sistema tenta di eseguire il comando: se il veicolo è online, viene bloccato e lo stato aggiornato; se la connessione è persa, viene creata una segnalazione e l'operatore viene notificato. |
| **Priorità (Sprint Backlog)** | 15 (OP.01 Sprint 2) + 10 (OP.04 Sprint 2) |
| **Include** | — *(nessuno)* |
| **Estende** | — *(nessuno)* |
| **Esteso da** | — *(nessuno)* |
| **Specializza** | — |
| **Generalizza** | — |
| **Requisiti** | Connettività IoT per il comando remoto dei veicoli. Sistema di segnalazione per la gestione dei veicoli non raggiungibili. |

---

## 2. Relazioni Use Case *(fonte: Master_Spec.cgd.md §7, documentazione.md §2.2.2)*

```
UC.ATT.01 (Login)
    │
    ▼ (autenticato come Operatore Tecnico)
UC.OP.01 (Gestione Flotta)
    │
    ▼ (indipendente, terminazione sessione)
UC.OP.04 (Logout Operatore Tecnico)
```

UC.OP.01 è uno use case indipendente — non include, non estende e non è esteso da altri use case. È accessibile solo dopo UC.ATT.01 con ruolo `Operatore` e tipo `OperatoreTecnico`.

---

## 3. Precondizioni *(fonte: documentazione.md §2.2.2 UC.OP.01)*

| # | Precondizione | Verifica | Metodo |
|---|---------------|----------|--------|
| P1 | L'Operatore Tecnico ha effettuato l'accesso | Sessione attiva con `RuoloAttore = Operatore` e `TipoOperatore = OperatoreTecnico` | `GestioneAutenticazione.invioCredenziali(email, password)` → `RuoloAttore` in UC.ATT.01 |
| P2 | L'Operatore Tecnico ha una sessione attiva | `AppOperatoreTecnico.idSessioneOperatoreTecnico` valorizzato | Vincolo architetturale Master_Spec §8.1 (autenticazione obbligatoria) |
| P3 | La flotta target è registrata a sistema con mezzi associati | Almeno un Mezzo con `idFlotta` corrispondente nel DB | `Mezzo.getMezzibyFlotta(idFlotta)` restituisce risultati |

---

## 4. Flusso Principale — Method Traceability Completa

*Ogni passo del flusso principale (documentazione.md §2.2.2) è mappato ai metodi concreti di Master_Spec.cgd.md. I numeri di linea fanno riferimento a Master_Spec.cgd.md v4.0.*

### Step 1 — Accesso alla Mappa della Flotta

**Testo documentazione.md:** "Il caso d'uso inizia quando l'Operatore Tecnico accede alla mappa della flotta."

| Ruolo MVC | Componente | Metodo | Firma | Fonte |
|-----------|-----------|--------|-------|-------|
| View | AppOperatoreTecnico | `richiedeStatoFlotta(idFlotta)` | `idFlotta: String → void` | Master_Spec:738 |

*Nota:* `idFlotta` è un identificativo logico di raggruppamento (attributo `Mezzo.idFlotta`, tipo `String`). Non esiste una tabella Flotta separata — il raggruppamento è logico *(Master_Spec.cgd.md §11 punto 7)*.

---

### Step 2 — Recupero Lista Veicoli e Stato

**Testo documentazione.md:** "Il sistema recupera e mostra la lista dei veicoli e il loro stato."

| Ruolo MVC | Componente | Metodo | Firma | Fonte |
|-----------|-----------|--------|-------|-------|
| Controller | GestioneFlotta | `getCondizioniMezzi(idFlotta)` | `idFlotta: String → Mezzo` *(inferred: restituisce lista)* | Master_Spec:620 |
| Model | Mezzo | `getMezzibyFlotta(idFlotta)` | `idFlotta: String → Mezzo` *(inferred: restituisce lista)* | Master_Spec:334 |
| External | DBMS | *(CRUD Read query filtrata per idFlotta)* | Query: `SELECT * FROM mezzo WHERE id_flotta = :idFlotta` | Master_Spec §5 DBMS |

> **Nota sul tipo di ritorno:** `getCondizioniMezzi(idFlotta)` e `getMezzibyFlotta(idFlotta)` hanno tipo di ritorno `Mezzo` (singolare) nel Master_Spec, ma semanticamente restituiscono una collezione di mezzi. Il diagramma di sequenza conferma `lista<Mezzo>` come valore effettivo. *(Vedi HITL claim-op01-getcondizionimezzi-return-type)*

**Attributi Mezzo recuperati per ciascun veicolo:**

| Attributo | Tipo | Significato per l'operatore |
|-----------|------|----------------------------|
| `idMezzo` | PK | Identificativo veicolo |
| `coordinateMezzo` | String | Posizione attuale (x, y, z float parsati) |
| `stato` | StatoMezzo | Stato operativo corrente |
| `autonomia` | float | Autonomia residua (%) |
| `condizione` | String | Condizioni fisiche del mezzo |
| `tipo` | String | Tipologia (bici/scooter/auto) |
| `idFlotta` | String | Flotta di appartenenza |
| `tempoDisponibilita` | time | Tempo stimato di disponibilità |

*Coordinate:* `coordinateMezzo` è di tipo **String** contenente tre float (x, y, z) parsati *(Master_Spec.cgd.md §2 nota coordinate, claim-7f2a5b013)*.

---

### Step 3 — Visualizzazione Dashboard

**Testo documentazione.md:** "Il sistema recupera e mostra la lista dei veicoli e il loro stato." *(completamento visualizzazione)*

| Ruolo MVC | Componente | Metodo | Firma | Fonte |
|-----------|-----------|--------|-------|-------|
| View | AppOperatoreTecnico | `visualizzaMezzi(mezzi)` | `mezzi: Mezzo → void` | Master_Spec:737 |

*Comportamento:* `AppOperatoreTecnico.visualizzaMezzi()` renderizza la lista/collezione di mezzi sulla dashboard operatore, mostrando per ciascun mezzo: posizione su mappa, stato operativo (con codice colore), tipo, autonomia, condizione.

---

### Step 4 — Selezione Veicolo e Invio Comando

**Testo documentazione.md:** "L'operatore seleziona un veicolo e invia un comando remoto di blocco."

| Ruolo MVC | Componente | Metodo | Firma | Fonte |
|-----------|-----------|--------|-------|-------|
| View | AppOperatoreTecnico | `selezionaVeicolo(idMezzo)` | `idMezzo → void` | Master_Spec:739 |
| Controller | GestioneFlotta | `bloccaMezzo(idMezzo)` | `idMezzo → bool` | Master_Spec:618 |

*Nota:* L'input `idMezzo` proviene dall'interazione dell'operatore sulla dashboard. `AppOperatoreTecnico.selezionaVeicolo()` invia la selezione al Controller `GestioneFlotta` che esegue `bloccaMezzo(idMezzo)`. La View non accede mai direttamente al Model *(vincolo architetturale §8.10)*.

---

### Step 5 — Blocco Fisico del Mezzo

**Testo documentazione.md:** "Il sistema blocca il mezzo"

| Ruolo MVC | Componente | Metodo | Firma | Fonte |
|-----------|-----------|--------|-------|-------|
| Controller | GestioneFlotta | *(orchestra la chiamata IoT)* | — | Master_Spec §6 associazioni |
| External | Mezzo:IoT | `bloccoMezzoFisico(idMezzo)` | `idMezzo → bool` | Master_Spec:835 |

*Dettaglio:* `GestioneFlotta.bloccaMezzo(idMezzo)` delega il blocco fisico a `Mezzo:IoT.bloccoMezzoFisico(idMezzo)`. Il sistema esterno IoT è **simulato** — progetto universitario *(chiarimenti-vari.md punto 16)*.

**Valori di ritorno:**
- `true` — veicolo raggiunto e bloccato fisicamente → prosegue al passo 6
- `false` / timeout — veicolo non raggiungibile → attiva il flusso alternativo §5.1

---

### Step 6 — Aggiornamento Stato Logico

**Testo documentazione.md:** "Il sistema aggiorna lo stato del mezzo"

| Ruolo MVC | Componente | Metodo | Firma | Fonte |
|-----------|-----------|--------|-------|-------|
| Model | Mezzo | `setStato(stato)` | `stato: StatoMezzo → void` | Master_Spec:321 |
| — | — | Valore impostato: `StatoMezzo.bloccato` | Stato: `bloccato` | Master_Spec §1 StatoMezzo |

*Transizione di stato:* Il veicolo passa dallo stato corrente a `bloccato`. Lo stato `bloccato` indica blocco remoto da operatore. Il veicolo in stato `bloccato` non è più prenotabile.

---

### Step 7 — Conferma all'Operatore

**Testo documentazione.md:** "Il sistema conferma all'operatore la riuscita dell'operazione."

| Ruolo MVC | Componente | Metodo | Firma | Fonte |
|-----------|-----------|--------|-------|-------|
| View | AppOperatoreTecnico | `mostraSuccesso(msg)` | `msg: String → void` | Master_Spec:735 |

*Messaggio:* Il contenuto esatto del messaggio di successo non è specificato nella documentazione — è una stringa informativa per l'operatore che conferma l'avvenuto blocco del veicolo `idMezzo` *(inferred)*.

---

## 5. Flussi Alternativi — Method Traceability

### 5.1 Connessione Persa — Veicolo Non Raggiungibile

**Testo documentazione.md:** "Al passaggio 4, se il sistema non riesce a raggiungere il veicolo, crea una segnalazione di guasto. Il sistema mostra un alert all'operatore."

*Trigger:* `Mezzo:IoT.bloccoMezzoFisico(idMezzo)` restituisce `false` o timeout (`requestTimeout`).

| Passo | Componente | Metodo | Firma | Descrizione |
|-------|-----------|--------|-------|-------------|
| A1 | Mezzo:IoT | `bloccoMezzoFisico(idMezzo)` | `→ false` | Il veicolo non risponde — connessione persa |
| A2 | GestioneFlotta | *(orchestra creazione segnalazione)* | — | Il Controller rileva il fallimento |
| A3 | Segnalazione | `creaSegnalazione(idMezzo, statoS, data, ora, note)` | `idMezzo, statoS: StatoSegnalazione, data: date, ora: time, note: String → void` | Master_Spec:460 |
| A4 | AppOperatoreTecnico | `mostraErrore(msg)` | `msg: String → void` | Master_Spec:736 |

**Dettaglio creazione Segnalazione (Step A3):**

| Parametro | Valore | Fonte |
|-----------|--------|-------|
| `idMezzo` | ID del veicolo non raggiungibile | Dal contesto della selezione operatore |
| `statoS` | `StatoSegnalazione.aperta` *(inferred)* | Nuova segnalazione — stato iniziale canonico. Master_Spec §1: "aperta = Segnalazione creata, in attesa" |
| `data` | Data corrente | `Date.now()` / data di sistema |
| `ora` | Ora corrente | `Time.now()` / ora di sistema |
| `note` | "Veicolo non raggiungibile — blocco remoto fallito" *(inferred message text)* | Descrizione del guasto |

> **Nota su `statoS`:** Il valore `StatoSegnalazione.aperta` non è esplicitamente dichiarato in documentazione.md per UC.OP.01. È inferito dal flusso generale delle segnalazioni: una nuova segnalazione viene creata in stato `aperta` e segue il ciclo `aperta → in_lavorazione → chiusa` *(Master_Spec.cgd.md §1 StatoSegnalazione, invariate dominio §8)*. *(inferred)*

> **Nota su `note`:** Il testo esatto del campo note non è specificato nella documentazione. Il contenuto è inferito dal contesto del flusso alternativo. *(inferred)*

**Dettaglio notifica errore (Step A4):**

`AppOperatoreTecnico.mostraErrore(msg)` mostra un alert all'operatore con un messaggio che indica il fallimento del blocco remoto e l'avvenuta creazione della segnalazione. Il contenuto esatto del messaggio non è specificato nella documentazione *(inferred)*.

---

## 6. Postcondizioni *(fonte: documentazione.md §2.2.2 UC.OP.01)*

### Main Flow

| # | Postcondizione | Meccanismo di verifica |
|---|----------------|------------------------|
| Q1 | Il veicolo risulta in stato `bloccato` | `Mezzo.getStato() == StatoMezzo.bloccato` — impostato da `Mezzo.setStato(StatoMezzo.bloccato)` |
| Q2 | Il veicolo non è più prenotabile | `Mezzo.stato == bloccato` impedisce nuove prenotazioni (stato diverso da `disponibile`) |
| Q3 | Il blocco fisico è stato eseguito | `Mezzo:IoT.bloccoMezzoFisico(idMezzo)` ha restituito `true` |

### Alternative Flow (Connessione Persa)

| # | Postcondizione | Meccanismo di verifica |
|---|----------------|------------------------|
| Q4 | Una segnalazione è stata creata nel sistema | `Segnalazione.creaSegnalazione()` ha completato con successo; la segnalazione esiste in DB con `stato = aperta` |
| Q5 | L'operatore è stato notificato dell'anomalia | `AppOperatoreTecnico.mostraErrore(msg)` è stato invocato |

---

## 7. Sequenza Completa dei Metodi

### Main Flow

```
1. AppOperatoreTecnico.richiedeStatoFlotta(idFlotta)
2.   GestioneFlotta.getCondizioniMezzi(idFlotta)
3.     Mezzo.getMezzibyFlotta(idFlotta)
4.     DBMS: SELECT * FROM mezzo WHERE id_flotta = :idFlotta
5.     → lista<Mezzo>
6.   → lista<Mezzo>
7.   GestioneFlotta → AppOperatoreTecnico.visualizzaMezzi(lista<Mezzo>)
8. (Operatore Tecnico visualizza dashboard con i mezzi)

9. AppOperatoreTecnico.selezionaVeicolo(idMezzo)           // input operatore
10.  GestioneFlotta.bloccaMezzo(idMezzo)
11.    Mezzo:IoT.bloccoMezzoFisico(idMezzo)
12.    → true                                               // blocco riuscito
13.    Mezzo.setStato(StatoMezzo.bloccato)
14.    → void
15.  → true
16. AppOperatoreTecnico.mostraSuccesso("Blocco veicolo riuscito")  *(inferred message)*
```

### Alternative Flow: Connessione Persa

```
11a.   Mezzo:IoT.bloccoMezzoFisico(idMezzo)
11b.   → false / timeout (requestTimeout)
11c.   GestioneFlotta: rileva fallimento IoT
11d.   Segnalazione.creaSegnalazione(idMezzo, StatoSegnalazione.aperta, data, ora, "Veicolo non raggiungibile")
11e.   → void
11f. → false
11g. AppOperatoreTecnico.mostraErrore("Blocco remoto fallito — segnalazione creata")  *(inferred message)*
```

---

## 8. Mapping User Stories

| User Story | Testo | Copertura in UC.OP.01 | Metodo/i |
|------------|-------|------------------------|----------|
| **OP.01** | Visualizzare la distribuzione dei mezzi, così da ottimizzare il posizionamento della flotta | Main Flow Step 1-3: accesso mappa, recupero lista veicoli, visualizzazione dashboard | `richiedeStatoFlotta()` → `getCondizioniMezzi()` → `getMezzibyFlotta()` → `visualizzaMezzi()` |
| **OP.04** | Forzare il blocco da remoto di un mezzo se fuori dalle zone consentite, così da prevenire violazioni dei termini di servizio | Main Flow Step 4-7 + Alt Flow: selezione veicolo, blocco remoto, aggiornamento stato, gestione fallimento | `selezionaVeicolo()` → `bloccaMezzo()` → `bloccoMezzoFisico()` → `setStato(bloccato)` |

---

## 9. Controller Traceability — GestioneFlotta

### 9.1 Metodi Coinvolti nel Flusso *(fonte: Master_Spec.cgd.md §3)*

| Metodo | Ritorno | Parametri | Step UC | Ruolo |
|--------|---------|-----------|---------|-------|
| `getCondizioniMezzi(idFlotta)` | `Mezzo` | `idFlotta: String` | 2 | Recupera lista mezzi della flotta |
| `bloccaMezzo(idMezzo)` | `bool` | `idMezzo` | 4-6 | Orchestratore blocco: chiama IoT + aggiorna stato |

### 9.2 Metodi GestioneFlotta NON Coinvolti in UC.OP.01

| Metodo | Ritorno | Parametri | Probabile UC |
|--------|---------|-----------|-------------|
| `analisiStatoFlotta(idFlotta)` | `bool` | `idFlotta: String` | UC.AP.02 (Analisi Stato Flotta — PA) *(inferred)* |
| `avviaManutenzione(idFlotta)` | `bool` | `idFlotta: String` | UC.AP.02 (Avvio intervento manutenzione — PA) *(inferred)* |

> **Nota su `analisiStatoFlotta`:** Questo metodo restituisce `bool` e non appare nel diagramma di sequenza UC.OP.01 né nel flusso testuale. Il suo ruolo più probabile è in UC.AP.02, dove la PA richiede un'analisi della flotta. In UC.OP.01 il recupero dati è gestito da `getCondizioniMezzi()`. *(Vedi HITL claim-op01-analisistatoflotta)* *(inferred)*

### 9.3 Attributi GestioneFlotta

| Attributo | Tipo | Visibilità | Ruolo |
|-----------|------|------------|-------|
| `idGestioneFlotta` | — | private | Identificativo controller |

---

## 10. Model Traceability

### 10.1 Mezzo *(fonte: Master_Spec.cgd.md §2)*

| Metodo | Step UC | Ruolo nel Flusso |
|--------|---------|------------------|
| `getMezzibyFlotta(idFlotta)` | 2-3 | Recupera tutti i mezzi appartenenti alla flotta |
| `setStato(StatoMezzo.bloccato)` | 6 | Aggiorna stato del mezzo dopo blocco riuscito |
| `getStato()` | *(verifica implicita)* | Verifica stato corrente del mezzo |
| `getCoordinateMezzo()` | 2 | Posizione veicolo per dashboard (String x,y,z) |
| `getCondizione()` | 2 | Condizioni fisiche mostrate all'operatore |
| `getAutonomia()` | 2 | Autonomia residua mostrata all'operatore |
| `getIdMezzo()` | 4 | Identificativo per selezione veicolo |
| `getIdFlotta()` | 2 | Filtro per appartenenza flotta |

### 10.2 Segnalazione *(fonte: Master_Spec.cgd.md §2)*

| Metodo | Step UC | Ruolo nel Flusso |
|--------|---------|------------------|
| `creaSegnalazione(idMezzo, statoS, data, ora, note)` | Alt A3 | Crea segnalazione per veicolo non raggiungibile |

**Firma completa:** `creaSegnalazione(idMezzo, statoS: StatoSegnalazione, data: date, ora: time, note: String) → void` *(Master_Spec:460)*

> **Nota XMI:** Il diagramma di sequenza ha un typo nel messaggio `creaSegnalazione (idMezzo, statoS data, ora, note)` — manca una virgola tra `statoS` e `data`. Artefatto di esportazione XMI *(chiarimenti-vari.md punto 14)*. *(Vedi HITL claim-op01-creasegnalazione-sd-typo)*

---

## 11. View Traceability — AppOperatoreTecnico

### 11.1 Metodi Coinvolti *(fonte: Master_Spec.cgd.md §4)*

| Metodo | Firma | Step UC | Ruolo |
|--------|-------|---------|-------|
| `richiedeStatoFlotta(idFlotta)` | `idFlotta: String → void` | 1 | Innesco use case — richiesta dashboard flotta |
| `visualizzaMezzi(mezzi)` | `mezzi: Mezzo → void` | 3 | Renderizzazione dashboard con lista veicoli |
| `selezionaVeicolo(idMezzo)` | `idMezzo → void` | 4 | Selezione veicolo e invio comando blocco |
| `mostraSuccesso(msg)` | `msg: String → void` | 7 | Conferma riuscita operazione |
| `mostraErrore(msg)` | `msg: String → void` | Alt A4 | Notifica fallimento blocco e segnalazione creata |

### 11.2 Metodi NON Coinvolti in UC.OP.01

| Metodo | Probabile UC |
|--------|-------------|
| `richiestaLogout(email)` | UC.OP.04 (Logout Operatore Tecnico) |

### 11.3 Attributi AppOperatoreTecnico

| Attributo | Tipo | Ruolo |
|-----------|------|-------|
| `idOperatoreTecnico` | private | Identificativo operatore loggato |
| `idSessioneOperatoreTecnico` | private | ID sessione attiva (precondizione P1-P2) |

### 11.4 Dipendenze View → Controller *(fonte: Master_Spec.cgd.md §6)*

| View | Controller |
|------|-----------|
| AppOperatoreTecnico → GestioneFlotta | Comandi flotta e recupero stato *(Master_Spec:919)* |
| AppOperatoreTecnico → GestioneAutenticazione | Gestione sessione e logout *(Master_Spec:919)* |

> **Verifica anti-pattern:** AppOperatoreTecnico non accede mai direttamente al Model (Mezzo, Segnalazione). Ogni comunicazione è mediata da GestioneFlotta. ✓ *(Master_Spec §8.10, §9)*

---

## 12. External Systems Traceability

### 12.1 Mezzo:IoT *(fonte: Master_Spec.cgd.md §5)*

| Metodo | Firma | Step UC | Ruolo |
|--------|-------|---------|-------|
| `bloccoMezzoFisico(idMezzo)` | `idMezzo → bool` | 5 | Esegue il blocco fisico del veicolo |

> **Simulazione:** Il sistema Mezzo:IoT è simulato (ambiente universitario — *chiarimenti-vari.md punto 16*). Il metodo restituisce `true` se il veicolo risponde al comando, `false` in caso di timeout o mancata connessione.

### 12.2 DBMS *(fonte: Master_Spec.cgd.md §5)*

| Operazione | Ruolo |
|------------|-------|
| CRUD Read su `mezzo` filtrato per `id_flotta` | Recupero lista veicoli (Step 2) |
| CRUD Update su `mezzo` (campo `stato`) | Aggiornamento stato veicolo a `bloccato` (Step 6) |
| CRUD Create su `segnalazione` | Creazione segnalazione guasto (Alt Step A3) |

---

## 13. Sequence Diagram Cross-Validation *(fonte: UC.OP.01-clean.uml)*

### 13.1 Lifeline Mapping

| Lifeline nel SD | Nome Canonico | Analisi |
|-----------------|---------------|---------|
| `AppOperatoreTecnico` | AppOperatoreTecnico | ✓ Corretto |
| `GestioneFlotta` | GestioneFlotta | ✓ Corretto |
| `Mezzo` (JuzW) | Mezzo (Model) | ✓ Corretto — riceve `getMezzibyFlotta()`, `setStato()` |
| `Mezzo` (KFb6) | Mezzo:IoT | **INCONSISTENTE:** dovrebbe chiamarsi `Mezzo:IoT` — riceve `bloccoMezzoFisico()`. Due lifeline con lo stesso nome violano *chiarimenti-vari.md punto 6* |
| `Segnalazione` | Segnalazione | ✓ Corretto |
| `Operatore Tecnico` | Operatore Tecnico (Attore) | ✓ Corretto — attore esterno |

> **Inconsistenza I1:** Due lifeline `Mezzo` distinte (Model vs IoT) con lo stesso nome. *(Vedi HITL claim-op01-mezzo-dual-lifeline)*

### 13.2 Corrispondenza Flussi

| Flusso documentazione.md | Coperto nel SD | Note |
|--------------------------|---------------|------|
| Main Flow (blocco riuscito) | ✓ Sì | Passi 1-7 mappati: `richiedeStatoFlotta` → `getCondizioniMezzi` → `getMezzibyFlotta` → `visualizzaMezzi` → `selezionaVeicolo` → `bloccaMezzo` → `bloccoMezzoFisico` → `setStato(bloccato)` → `stringaSuccesso` |
| Alt Flow (connessione persa) | ✓ Sì | Frammento `opt` con `requestTimeout` → `creaSegnalazione` → `stringaFallimento` |

### 13.3 Messaggi Descrittivi (non metodi nominali)

| Messaggio SD | Metodo Canonico |
|-------------|-----------------|
| `Dashboard Mezzi` | *(messaggio attore — interfaccia visuale, non è un metodo)* |
| `lista<Mezzo>` | *(valore di ritorno di `getMezzibyFlotta()` e `getCondizioniMezzi()`)* |
| `true` / `false` | *(valori di ritorno booleani)* |
| `void` | *(conferma `setStato()` completato senza errori)* |
| `requestTimeout` | *(evento di sistema — timeout IoT, non è un metodo)* |
| `stringaSuccesso` | `AppOperatoreTecnico.mostraSuccesso(msg)` |
| `stringaFallimento` | `AppOperatoreTecnico.mostraErrore(msg)` |

### 13.4 Tabella Allineamento SD vs Master_Spec

| Messaggio SD | Metodo Master_Spec | Allineamento |
|-------------|-------------------|-------------|
| `richiedeStatoFlotta(idFlotta)` | `AppOperatoreTecnico.richiedeStatoFlotta(idFlotta)` | ✓ |
| `getCondizioniMezzi(idFlotta)` | `GestioneFlotta.getCondizioniMezzi(idFlotta)` | ✓ |
| `Mezzo.getMezzibyFlotta(idFlotta)` | `Mezzo.getMezzibyFlotta(idFlotta)` | ✓ |
| `visualizzaMezzi(lista<Mezzo>)` | `AppOperatoreTecnico.visualizzaMezzi(mezzi)` | ✓ |
| `selezionaVeicolo(idMezzo)` | `AppOperatoreTecnico.selezionaVeicolo(idMezzo)` | ✓ |
| `bloccaMezzo(idMezzo)` | `GestioneFlotta.bloccaMezzo(idMezzo)` | ✓ |
| `bloccoMezzoFisico (idMezzo)` | `Mezzo:IoT.bloccoMezzoFisico(idMezzo)` | ✓ *(spazio prima della parentesi — typo XMI minore)* |
| `setStato(bloccato)` | `Mezzo.setStato(StatoMezzo.bloccato)` | ✓ *(sintassi semplificata nel SD)* |
| `creaSegnalazione (...)` | `Segnalazione.creaSegnalazione(idMezzo, statoS, data, ora, note)` | ⚠ Typo XMI: virgola mancante tra `statoS` e `data` |

---

## 14. Verifica Tipo Coordinate

*Verifica del vincolo architetturale: tutte le coordinate sono di tipo **String** (x,y,z float parsati).*

| Utilizzo | Entità/Componente | Tipo | Consistenza |
|----------|-------------------|------|-------------|
| `Mezzo.coordinateMezzo` | Mezzo Model | `String` | ✓ Master_Spec:304 |
| Visualizzazione su dashboard | AppOperatoreTecnico | `String` → parsing UI | ✓ Ereditato dal Model |
| Trasmesso via `getMezzibyFlotta()` | Mezzo → GestioneFlotta | `String` | ✓ Tipo invariato |

**Verdetto:** Consistenza totale. Le coordinate sono sempre trattate come `String` (x,y,z) in tutto il flusso UC.OP.01.

---

## 15. Verifica Tipo StatoMezzo

| Stato | Utilizzo in UC.OP.01 | 
|-------|----------------------|
| `bloccato` | Stato target del veicolo dopo blocco remoto (Step 6) |
| *(tutti gli altri)* | Visualizzati nella dashboard per informazione operatore (Step 3) |

**Transizione di stato coinvolta:** `stato_corrente → bloccato` — il veicolo può essere bloccato da qualsiasi stato. Il blocco remoto forza il passaggio a `bloccato`.

---

## 16. Vincoli Architetturali Applicabili

| # | Vincolo (Master_Spec §8) | Applicabilità a UC.OP.01 | Verifica |
|---|--------------------------|--------------------------|----------|
| 1 | Autenticazione obbligatoria | ✓ Precondizione P1-P2: login e sessione attiva richiesti | `GestioneAutenticazione.invioCredenziali()` in UC.ATT.01 |
| 5 | RBAC: routing per ruolo | ✓ Solo attori con `RuoloAttore.Operatore` e `TipoOperatore.OperatoreTecnico` | `Operatore.getTipo()` |
| 10 | Disaccoppiamento View-Controller-Model | ✓ AppOperatoreTecnico → GestioneFlotta → Mezzo (nessun accesso diretto View→Model) | Master_Spec §6 dipendenze |
| 11 | Ruolo unico per sessione | ✓ Sessione Operatore Tecnico attiva | `AppOperatoreTecnico.idSessioneOperatoreTecnico` |
| 12 | Simulazione sistemi esterni | ✓ Mezzo:IoT è simulato | chiarimenti-vari.md punto 16 |
| — | Anti-pattern §9: "Permettere modifica diretta Model da View" | ✓ Rispettato: View → Controller → Model | Verifica §11.4 |

---

## 17. Copertura Epistemica

*Legenda marker:*

| Marker | Significato |
|--------|-------------|
| *(inferred)* | Dato dedotto da altre fonti, non esplicitamente dichiarato |
| *(derived)* | Derivato da analisi incrociata delle fonti |
| *(unverified)* | Claim non ancora confermato dal team |

### Dati Inferiti in UC.OP.01

| # | Dato | Valore Inferito | Base dell'Inferenza |
|---|------|-----------------|---------------------|
| 1 | `statoS` in `creaSegnalazione()` | `StatoSegnalazione.aperta` | Nuova segnalazione creata in stato iniziale canonico. Master_Spec §1: "aperta = Segnalazione creata, in attesa". Il ciclo di vita `aperta → in_lavorazione → chiusa` è documentato in Master_Spec §8 invariate. |
| 2 | `note` in `creaSegnalazione()` | "Veicolo non raggiungibile — blocco remoto fallito" | Dedotto dal contesto "connessione persa" del flusso alternativo. Il testo esatto non è specificato. |
| 3 | Messaggio `mostraSuccesso(msg)` | "Blocco veicolo [idMezzo] riuscito" | Stringa informativa per l'operatore. Contenuto esatto non specificato in documentazione.md. |
| 4 | Messaggio `mostraErrore(msg)` | "Blocco remoto fallito — segnalazione [#id] creata" | Stringa di alert per l'operatore. Contenuto esatto non specificato. |
| 5 | `getCondizioniMezzi()` tipo di ritorno effettivo | Lista/collezione di Mezzo (non singolo) | Il SD mostra `lista<Mezzo>`. Il tipo `Mezzo` singolare nel Master_Spec è una semplificazione. |
| 6 | Ruolo di `analisiStatoFlotta()` | Pre-validazione o appartenenza a UC.AP.02 | Non presente nel SD UC.OP.01. Il metodo restituisce bool, suggerendo un check piuttosto che un recupero dati. |
| 7 | Valore `idFlotta` | Identificativo stringa della flotta | `Mezzo.idFlotta` è `String`. Non esiste entità Flotta separata — raggruppamento logico (Master_Spec §11 punto 7). |

---

## 18. Critical Checks — Risultati

### Check 1: `GestioneFlotta.bloccaMezzo(idMezzo)` → bool ✅

| Verifica | Risultato |
|----------|-----------|
| Presenza in Master_Spec.cgd.md | **SÌ** — linea 618 |
| Firma | `bloccaMezzo(idMezzo): idMezzo → bool` |
| Utilizzo in UC.OP.01 | **SÌ** — Step 4, orchestratore del blocco remoto |
| Presenza nel SD | **SÌ** — messaggio `bloccaMezzo(idMezzo)` |

### Check 2: `GestioneFlotta.analisiStatoFlotta(idFlotta)` → bool ✅

| Verifica | Risultato |
|----------|-----------|
| Presenza in Master_Spec.cgd.md | **SÌ** — linea 617 |
| Firma | `analisiStatoFlotta(idFlotta): idFlotta: String → bool` |
| Utilizzo in UC.OP.01 | **NON ESPLICITO** — non appare nel SD né nel flusso testuale |
| Probabile collocazione | UC.AP.02 (Analisi Stato Flotta — PA) |

### Check 3: `GestioneFlotta.getCondizioniMezzi(idFlotta)` → Mezzo ✅

| Verifica | Risultato |
|----------|-----------|
| Presenza in Master_Spec.cgd.md | **SÌ** — linea 620 |
| Firma | `getCondizioniMezzi(idFlotta): idFlotta: String → Mezzo` |
| Tipo di ritorno | `Mezzo` (singolare) — ma il SD restituisce `lista<Mezzo>` |
| Utilizzo in UC.OP.01 | **SÌ** — Step 2 |

### Check 4: `Mezzo:IoT.bloccoMezzoFisico(idMezzo)` → bool ✅

| Verifica | Risultato |
|----------|-----------|
| Presenza in Master_Spec.cgd.md | **SÌ** — linea 835 |
| Firma | `bloccoMezzoFisico(idMezzo): idMezzo → bool` |
| Utilizzo in UC.OP.01 | **SÌ** — Step 5 (blocco fisico) |
| Sistema simulato | **SÌ** — chiarimenti-vari.md punto 16 |

### Check 5: `Segnalazione.creaSegnalazione(idMezzo, statoS, data, ora, note)` — 5 parametri ✅

| Verifica | Risultato |
|----------|-----------|
| Presenza in Master_Spec.cgd.md | **SÌ** — linea 460 |
| Numero parametri | **5**: `idMezzo, statoS, data, ora, note` |
| Tipi parametri | `idMezzo` (FK), `statoS: StatoSegnalazione`, `data: date`, `ora: time`, `note: String` |
| Typo SD | ⚠ Messaggio SD ha virgola mancante: `"creaSegnalazione (idMezzo, statoS data, ora, note)"` |

### Check 6: Full Flow Mapping ✅

| Step doc.md | Descrizione | Metodo tracciato | Fonte |
|-------------|-------------|------------------|-------|
| 1 | Accesso mappa flotta | `AppOperatoreTecnico.richiedeStatoFlotta(idFlotta)` | Master_Spec:738 |
| 2 | Recupero lista veicoli | `GestioneFlotta.getCondizioniMezzi(idFlotta)` → `Mezzo.getMezzibyFlotta(idFlotta)` | Master_Spec:620,334 |
| 3 | Mostra veicoli | `AppOperatoreTecnico.visualizzaMezzi(mezzi)` | Master_Spec:737 |
| 4 | Seleziona + blocca | `AppOperatoreTecnico.selezionaVeicolo(idMezzo)` → `GestioneFlotta.bloccaMezzo(idMezzo)` | Master_Spec:739,618 |
| 5 | Blocco fisico | `Mezzo:IoT.bloccoMezzoFisico(idMezzo)` | Master_Spec:835 |
| 6 | Aggiorna stato | `Mezzo.setStato(StatoMezzo.bloccato)` | Master_Spec:321 |
| 7 | Conferma | `AppOperatoreTecnico.mostraSuccesso(msg)` | Master_Spec:735 |
| Alt-1 | Connessione persa | `Segnalazione.creaSegnalazione(idMezzo, statoS, data, ora, note)` | Master_Spec:460 |
| Alt-2 | Alert operatore | `AppOperatoreTecnico.mostraErrore(msg)` | Master_Spec:736 |

**Verdetto:** Flusso completamente tracciato. Ogni passo ha un mapping biunivoco a metodi del Master_Spec.

### Check 7: Vincolo Sessione Attiva (autenticazione) ✅

| Verifica | Risultato |
|----------|-----------|
| Precondizione documentazione.md | "L'Operatore Tecnico ha effettuato l'accesso e ha una sessione attiva" |
| Metodo di verifica | `GestioneAutenticazione.invioCredenziali(email, password)` → `RuoloAttore.Operatore` |
| Attributo sessione View | `AppOperatoreTecnico.idSessioneOperatoreTecnico` |
| Vincolo architetturale | Master_Spec §8.1: "Ogni operazione richiede sessione attiva" |

### Check 8: Tipo Coordinate String (x,y,z) ✅

| Attributo | Tipo | Consistenza |
|-----------|------|-------------|
| `Mezzo.coordinateMezzo` | `String` | ✓ Master_Spec:304 |
| HITL conferma | `claim-7f2a5b013` — confermato dal team | ✓ |

### Check 9: Anti-Pattern — Nessun Accesso Diretto View → Model ✅

| Verifica | Risultato |
|----------|-----------|
| AppOperatoreTecnico → Mezzo diretto? | **NO** — la View chiama solo GestioneFlotta |
| AppOperatoreTecnico → Segnalazione diretto? | **NO** — la View chiama solo GestioneFlotta |
| Dipendenze documentate | Master_Spec §6: AppOperatoreTecnico → GestioneFlotta, GestioneAutenticazione |

---

## 19. Riepilogo Inconsistenze Cross-Source

| # | Tipo | Descrizione | Fonti coinvolte | Impatto | HITL |
|---|------|-------------|-----------------|---------|------|
| I1 | Naming | Due lifeline "Mezzo" nel SD — una Model, una IoT. Dovrebbero essere distinte ("Mezzo" e "Mezzo:IoT") | UC.OP.01-clean.uml vs chiarimenti-vari.md punto 6 | Medio — ambiguità su quale componente esegue l'operazione | claim-op01-mezzo-dual-lifeline |
| I2 | Ruolo | `analisiStatoFlotta(idFlotta)` → bool esiste ma non in UC.OP.01 flow | Master_Spec vs documentazione.md UC.OP.01 | Basso — probabile appartenenza a UC.AP.02 | claim-op01-analisistatoflotta |
| I3 | Sintassi SD | `creaSegnalazione (idMezzo, statoS data, ora, note)` — virgola mancante tra `statoS` e `data` | UC.OP.01-clean.uml vs Master_Spec:460 | Basso — typo XMI, non altera la semantica | claim-op01-creasegnalazione-sd-typo |
| I4 | Tipo | `getCondizioniMezzi()` dichiarato `→ Mezzo` ma semanticamente restituisce lista | Master_Spec:620 vs UC.OP.01-clean.uml `lista<Mezzo>` | Basso — il comportamento è chiaro, è la dichiarazione formale a essere imprecisa | claim-op01-getcondizionimezzi-return-type |
| I5 | Naming SD | `bloccoMezzoFisico (idMezzo)` con spazio prima della parentesi | UC.OP.01-clean.uml | Basso — artefatto XMI (chiarimenti-vari.md punto 14) | — *(non richiede HITL)* |
| I6 | Naming SD | `stringaSuccesso` / `stringaFallimento` invece di `mostraSuccesso(msg)` / `mostraErrore(msg)` | UC.OP.01-clean.uml | Basso — nomi descrittivi vs nomi metodo reali | — *(non richiede HITL)* |

---

## 20. Componenti e Metodi Coinvolti — Riepilogo

### View: AppOperatoreTecnico

| Metodo | Ruolo in UC.OP.01 |
|--------|-------------------|
| `richiedeStatoFlotta(idFlotta)` | Innesco use case (Step 1) |
| `visualizzaMezzi(mezzi)` | Dashboard flotta (Step 3) |
| `selezionaVeicolo(idMezzo)` | Selezione veicolo e invio comando (Step 4) |
| `mostraSuccesso(msg)` | Conferma blocco riuscito (Step 7) |
| `mostraErrore(msg)` | Alert connessione persa (Alt Step A4) |

### Controller: GestioneFlotta

| Metodo | Ruolo in UC.OP.01 |
|--------|-------------------|
| `getCondizioniMezzi(idFlotta)` | Recupero lista veicoli (Step 2) |
| `bloccaMezzo(idMezzo)` | Orchestratore blocco remoto (Step 4-6) |
| `analisiStatoFlotta(idFlotta)` | *(non utilizzato in UC.OP.01)* |
| `avviaManutenzione(idFlotta)` | *(non utilizzato in UC.OP.01)* |

### Model: Mezzo

| Metodo | Ruolo in UC.OP.01 |
|--------|-------------------|
| `getMezzibyFlotta(idFlotta)` | Query mezzi per flotta (Step 2) |
| `setStato(StatoMezzo.bloccato)` | Aggiornamento stato post-blocco (Step 6) |
| `getStato()` | Verifica stato |
| `getCoordinateMezzo()` | Posizione per dashboard |

### Model: Segnalazione

| Metodo | Ruolo in UC.OP.01 |
|--------|-------------------|
| `creaSegnalazione(idMezzo, statoS, data, ora, note)` | Creazione segnalazione guasto (Alt Step A3) |

### External: Mezzo:IoT

| Metodo | Ruolo in UC.OP.01 |
|--------|-------------------|
| `bloccoMezzoFisico(idMezzo)` | Blocco fisico veicolo (Step 5) |

### External: DBMS

| Operazione | Ruolo in UC.OP.01 |
|------------|-------------------|
| CRUD Read su `mezzo` | Recupero lista veicoli per `id_flotta` |
| CRUD Update su `mezzo` | Aggiornamento `stato` a `bloccato` |
| CRUD Create su `segnalazione` | Inserimento nuova segnalazione |

---

## 21. HITL Verification Record

### Round A: Derived Data Confirmation

| # | Claim ID | Claim | Rilevanza | Stato |
|---|----------|-------|-----------|-------|
| 1 | claim-op01-mezzo-dual-lifeline | Due lifeline "Mezzo" nel SD — la IoT dovrebbe chiamarsi "Mezzo:IoT" | Naming consistente con chiarimenti-vari.md punto 6 | **PENDING** |
| 2 | claim-op01-analisistatoflotta | `analisiStatoFlotta()` non in UC.OP.01 — confermare appartenenza a UC.AP.02 | Chiarezza API Controller | **PENDING** |
| 3 | claim-op01-creasegnalazione-sd-typo | Typo XMI virgola mancante in `creaSegnalazione` — confermare firma 5-parametri | Fedeltà documentazione | **PENDING** |
| 4 | claim-op01-getcondizionimezzi-return-type | `getCondizioniMezzi()` → `Mezzo` singolare ma restituisce lista | Tipo di ritorno formale | **PENDING** |

### Round B: True HITL Verification

*Nessun claim richiede Round B — tutti i claim sono verificabili tramite fonti già presenti nel repository o richiedono solo conferma interpretativa (Round A).*

---

## 22. Summary Statistics

| Metrica | Valore |
|---------|--------|
| Controller methods coinvolti | 2 (di 4 totali in GestioneFlotta) |
| Model entities coinvolte | 2 (Mezzo, Segnalazione) |
| External systems coinvolti | 2 (Mezzo:IoT, DBMS) |
| View methods coinvolti | 5 (di 9 totali in AppOperatoreTecnico) |
| Step flusso principale | 7 |
| Flussi alternativi | 1 |
| Precondizioni | 3 |
| Postcondizioni (main + alt) | 5 |
| Inconsistenze rilevate | 6 (2 naming, 1 ruolo, 1 tipo, 2 sintassi SD) |
| Claim HITL pending | 4 (Round A) |
| Vincoli architetturali applicabili | 6 |
| Dati inferiti (epistemic markers) | 7 |

---

## 23. References

| Documento | Percorso | Ruolo |
|-----------|----------|-------|
| documentazione.md v3.0 | `docs/specs/documentazione.md` | Sorgente primaria UC.OP.01 (§2.2.2) e user stories OP.01/OP.04 (§1) |
| Master_Spec.cgd.md v4.0 | `docs/specs/Master_Spec.cgd.md` | Metodi, attributi, controller, vincoli architetturali |
| UC.OP.01-clean.uml | `docs/diagrams/sequence-diagrams/UC.OP.01/UC.OP.01-clean.uml` | Diagramma di sequenza (XMI 2.1) |
| chiarimenti-vari.md | `docs/specs/chiarimenti-vari.md` | Interpretazioni e vincoli (punti 1, 6, 14, 15, 16) |

---

**Fine specifica UC.OP.01 — CGD generato il 2026-06-22. In attesa di conferma HITL Round A (4 claim pending).**

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | PENDING

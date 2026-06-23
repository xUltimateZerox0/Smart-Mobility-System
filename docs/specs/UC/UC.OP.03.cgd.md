---
clarity-gate-version: 2.1
document-type: Implementation
processed-date: 2026-06-23
processed-by: Claude (AI) + Cross-Reference Engine — documentazione.md v3.0 §2.2.2, Master_Spec.cgd.md v4.0, UC.OP.03-clean.uml (XMI fixed: 'attiva' ✅), chiarimenti-vari.md, response2.md
clarity-status: CLEAR
hitl-status: REVIEWED
hitl-pending-count: 0
points-passed: 1-9
document-sha256: f7517622da82cb858655d5c8e47ffab1f7b7eb8ff97c5f4f54758b6f976de0df
hitl-claims:
  - id: claim-9a3e7c01
    text: "Il diagramma di sequenza usa 'getPrenotazioneByStato(valida)' ma StatoPrenotazione non ha un valore 'valida' — i 4 valori sono attiva, scaduta, annullata, completata. Il valore corretto dovrebbe essere 'attiva' (o StatoPrenotazione.attiva)"
    value: "CONFERMATO: filtro 'attiva' è implicito. CRITICAL BUG: must change 'valida' to 'attiva' (see Critical #1 from response2.md)."
    source: "UC.OP.03-clean.uml + Master_Spec.cgd.md §1 StatoPrenotazione + documentazione.md 'prenotazioni valide'"
    location: "UC.OP.03/getPrenotazioneByStato-param"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-7b2d4f02
    text: "Master_Spec mostra annullaPrenotazione() senza parametri (→ bool), ma il diagramma di sequenza mostra annullaPrenotazione(idPrenotazione) con parametro"
    value: "CONFERMATO: with idPrenotazione param."
    source: "Master_Spec.cgd.md §3 GestionePrenotazione + UC.OP.03-clean.uml"
    location: "UC.OP.03/annullaPrenotazione-signature"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-1c5e8a03
    text: "Il controller è chiamato 'GestionePrenotazioni' (plurale) nel diagramma di sequenza ma 'GestionePrenotazione' (singolare) in Master_Spec.cgd.md"
    value: "CONFERMATO: singular GestionePrenotazione is canonical."
    source: "UC.OP.03-clean.uml (lifeline name) + Master_Spec.cgd.md §3"
    location: "UC.OP.03/controller-naming"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-4f9b6d04
    text: "Il diagramma di sequenza mostra 'richiediLista()' come messaggio verso Prenotazione (model), ma Master_Spec lo definisce come metodo del controller GestionePrenotazione"
    value: "CONFERMATO: è metodo del controller."
    source: "Master_Spec.cgd.md §3 GestionePrenotazione + UC.OP.03-clean.uml"
    location: "UC.OP.03/richiediLista-placement"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-6c1a7e05
    text: "Il flusso prevede che GestionePrenotazione.annullaPrenotazione() modifichi sia Prenotazione.stato che Mezzo.stato — il Mezzo associato è recuperabile via prenotazione.idMezzo (FK), ma il metodo setStato su Mezzo richiede di sapere quale Mezzo aggiornare"
    value: "CONFERMATO: confirmed."
    source: "Master_Spec.cgd.md §2 Prenotazione.idMezzo + Mezzo.setStato()"
    location: "UC.OP.03/mezzo-id-resolution"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-2d8f3b06
    text: "richiediLista() non ha parametri ma deve filtrare per stato 'attiva' — il filtro è implicito e realizzato internamente tramite Prenotazione.getPrenotazioneByStato(StatoPrenotazione.attiva)"
    value: "CONFERMATO: confirmed."
    source: "Master_Spec.cgd.md §3 GestionePrenotazione + documentazione.md 'prenotazioni attive'"
    location: "UC.OP.03/richiediLista-filter"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
---

# UC.OP.03 — Amministrazione Prenotazioni (Clarity-Gated Specification)

**Versione CGD:** 1.0 *(derivato da documentazione.md v3.0 e Master_Spec.cgd.md v4.0)*
**Team:** Cofee Coders
**Progetto:** Ingegneria del Software a.a. 2025/2026

---

## 1. Use Case Specification (da documentazione.md §2.2.2)

| Campo | Valore |
|-------|--------|
| **User Stories** | OP.05 |
| **Nome** | Amministrazione Prenotazioni |
| **ID** | UC.OP.03 |
| **Breve descrizione** | L'Operatore Servizio Clienti accede alla lista delle prenotazioni valide, ne seleziona una e richiede l'annullamento. La prenotazione viene così annullata, il mezzo torna disponibile e l'operatore riceve una notifica di conferma. |
| **Attori principali** | Operatore Servizio Clienti |
| **Precondizioni** | L'Operatore Servizio Clienti ha effettuato l'accesso ed ha una sessione attiva. |
| **Postcondizioni** | 1. La prenotazione risulta "annullata" — 2. Il mezzo risulta "disponibile" |
| **Include** | — *(nessuno)* |
| **Estende** | — *(nessuno)* |
| **Esteso da** | — *(nessuno)* |
| **Specializza** | — |
| **Generalizza** | — |
| **Requisiti** | — *(nessuno)* |

---

## 2. Precondizioni (analisi)

| # | Precondizione | Verifica |
|---|---------------|----------|
| P1 | L'Operatore Servizio Clienti ha effettuato l'accesso | Implica sessione attiva con `RuoloAttore.Operatore` e `TipoOperatore.OperatoreSC`. Verificato da `GestioneAutenticazione.invioCredenziali()` in UC.ATT.01 |
| P2 | L'Operatore Servizio Clienti ha una sessione attiva | `Operatore` autenticato con sessione valida; l'accesso a `AppOperatoreSC` è subordinato al login come confermato da RBAC (Master_Spec §8 vincolo 5) |

---

## 3. Postcondizioni (analisi)

| # | Postcondizione | Meccanismo di verifica |
|---|----------------|------------------------|
| Q1 | La prenotazione risulta "annullata" | `Prenotazione.setStato(StatoPrenotazione.annullata)` è stato invocato con successo. Verificabile interrogando `Prenotazione.getStato()` dopo l'operazione |
| Q2 | Il mezzo risulta "disponibile" | `Mezzo.setStato(StatoMezzo.disponibile)` è stato invocato con successo sul mezzo associato alla prenotazione annullata. Verificabile interrogando `Mezzo.getStato()` dopo l'operazione |

---

## 4. Flusso Principale — Method Traceability

*Ogni passo del flusso principale (documentazione.md §2.2.2) è mappato ai metodi concreti di Master_Spec.cgd.md. I nomi metodo nel diagramma di sequenza sono indicati tra parentesi quando differiscono dal Master_Spec.*

### Step 1 — Richiesta Lista Prenotazioni

**Testo documentazione.md:** "Il caso d'uso inizia quando l'operatore richiede la lista delle prenotazioni attive."

| Ruolo MVC | Componente | Metodo | Firma |
|-----------|-----------|--------|-------|
| View | AppOperatoreSC | `richiediListaPrenotazioni()` | `→ void` |
| Controller | GestionePrenotazione | `richiediLista()` | `→ Prenotazione` |
| Model | Prenotazione | `getPrenotazioneByStato(stato)` | `stato: StatoPrenotazione → Prenotazione` |

*Dettaglio:* `AppOperatoreSC.richiediListaPrenotazioni()` invoca il controller, che internamente chiama `Prenotazione.getPrenotazioneByStato(StatoPrenotazione.attiva)` per ottenere solo le prenotazioni con stato "attiva". Il metodo `richiediLista()` non accetta parametri espliciti — il filtro per stato "attiva" è implicito nella logica interna del controller *(inferred, vedi HITL claim-2d8f3b06)*.

*Enum StatoPrenotazione applicabile:*

| Valore | Significato | Rilevante per UC.OP.03 |
|--------|-------------|------------------------|
| `attiva` | Prenotazione valida entro i 15 minuti | ✓ Lista da recuperare |
| `scaduta` | Timeout superato, automaticamente annullata | ✗ Non mostrata |
| `annullata` | Cancellata da utente o operatore SC | ✗ Non mostrata |
| `completata` | Prenotazione onorata (corsa avviata) | ✗ Non mostrata |

*Nota sulla terminologia:* documentazione.md usa "prenotazioni attive" (main flow) e "prenotazioni valide" (breve descrizione). Il termine "valide" nel contesto business equivale a stato `attiva` nell'enum. Il diagramma di sequenza usa impropriamente `getPrenotazioneByStato(valida)` con il termine business invece del valore enum corretto `attiva` *(vedi HITL claim-9a3e7c01)*.

---

### Step 2 — Recupero e Visualizzazione

**Testo documentazione.md:** "Il sistema recupera e mostra le prenotazioni valide."

| Ruolo MVC | Componente | Metodo | Firma |
|-----------|-----------|--------|-------|
| Controller | GestionePrenotazione | *(restituisce la lista al chiamante)* | — |
| View | AppOperatoreSC | `mostraPrenotazioni()` | `→ void` |
| DBMS | DBMS | *(CRUD Read)* | `SELECT * FROM prenotazione WHERE stato = 'attiva'` |

*Comportamento:* `AppOperatoreSC.mostraPrenotazioni()` renderizza la lista delle prenotazioni attive, mostrando per ciascuna: `idPrenotazione`, `idUtente`, `idMezzo`, `orarioInizio`, `data` (attributi di Prenotazione).

---

### Step 3 — Selezione Prenotazione

**Testo documentazione.md:** "L'operatore seleziona una prenotazione e ne chiede l'annullamento."

| Ruolo MVC | Componente | Metodo | Firma |
|-----------|-----------|--------|-------|
| View | AppOperatoreSC | `selezionaPrenotazione(idPrenotazione)` | `idPrenotazione → void` |

*Nota:* L'input `idPrenotazione` proviene dall'interazione dell'operatore sulla lista visualizzata. La View invia l'ID al Controller associato. Il Controller memorizza internamente qual è la prenotazione selezionata per l'operazione successiva *(inferred — pattern stateful controller, vedi HITL claim-7b2d4f02)*.

---

### Step 4 — Annullamento e Aggiornamento Stato

**Testo documentazione.md:** "Il sistema aggiorna lo stato della prenotazione ad 'annullata' e rende il mezzo nuovamente 'disponibile'."

| Ruolo MVC | Componente | Metodo | Firma |
|-----------|-----------|--------|-------|
| Controller | GestionePrenotazione | `annullaPrenotazione()` | `→ bool` |
| Model | Prenotazione | `setStato(stato)` | `stato: StatoPrenotazione → void` |
| Model | Mezzo | `setStato(stato)` | `stato: StatoMezzo → void` |
| DBMS | DBMS | *(CRUD Update × 2)* | `UPDATE prenotazione SET stato='annullata'` + `UPDATE mezzo SET stato='disponibile'` |

*Dettaglio:* Il Controller esegue due operazioni atomiche (o logicamente transazionali):
1. `Prenotazione.setStato(StatoPrenotazione.annullata)` — aggiorna lo stato della prenotazione selezionata
2. `Mezzo.setStato(StatoMezzo.disponibile)` — libera il mezzo associato alla prenotazione

*Identificazione del Mezzo da aggiornare:* Il controller recupera `idMezzo` dalla `Prenotazione` selezionata tramite `Prenotazione.getIdMezzo()` (FK verso Mezzo), quindi invoca `Mezzo.setStato(StatoMezzo.disponibile)` sul mezzo corretto *(inferred, vedi HITL claim-6c1a7e05)*.

*Valore di ritorno:* `annullaPrenotazione()` restituisce `bool` (`true` = successo, `false` = fallimento) per indicare l'esito dell'operazione.

*Enum StatoMezzo rilevante:*

| Valore | Significato | Transizione in UC.OP.03 |
|--------|-------------|--------------------------|
| `disponibile` | Libero e prenotabile | ← Il mezzo torna a questo stato dopo l'annullamento |
| `prenotato` | Bloccato da prenotazione attiva | → Stato attuale del mezzo prima dell'annullamento |

---

### Step 5 — Conferma all'Operatore

**Testo documentazione.md:** "Il sistema notifica l'operatore dell'avvenuto annullamento."

| Ruolo MVC | Componente | Metodo | Firma |
|-----------|-----------|--------|-------|
| View | AppOperatoreSC | `mostraSuccesso(msg)` | `msg: String → void` |

*Comportamento:* Se `annullaPrenotazione()` restituisce `true`, la View mostra un messaggio di conferma. In caso di `false`, `mostraErrore(msg)` comunica il fallimento.

---

## 5. Flussi Alternativi — Method Traceability

### 5.1 Lista Prenotazioni Vuota

**Testo documentazione.md:** "Al passaggio 2 del flusso principale, il sistema recupera una lista vuota. Il sistema notifica l'Operatore Servizio Clienti dell'assenza di prenotazioni, mostrando un errore."

*Trigger:* `Prenotazione.getPrenotazioneByStato(StatoPrenotazione.attiva)` restituisce lista vuota.

| Passo | Componente | Metodo | Descrizione |
|-------|-----------|--------|-------------|
| A1 | GestionePrenotazione | `richiediLista()` | Restituisce lista vuota |
| A2 | AppOperatoreSC | `mostraErrore(msg)` | `msg: "lista prenotazioni vuota"` *(dal diagramma di sequenza)* |

*Nota:* Il diagramma di sequenza mostra esplicitamente `mostraErrore("lista prenotazioni vuota")` per questo flusso alternativo. Il testo esatto del messaggio è confermato dalla fonte XMI.

---

## 6. Sequenza Completa dei Metodi (Main Flow)

```
1.  AppOperatoreSC.richiediListaPrenotazioni()
2.    GestionePrenotazione.richiediLista()
3.      Prenotazione.getPrenotazioneByStato(StatoPrenotazione.attiva)
4.      DBMS: SELECT * FROM prenotazione WHERE stato = 'attiva'
5.      → List<Prenotazione> (non vuota)
6.    → List<Prenotazione>
7.  AppOperatoreSC.mostraPrenotazioni()
8.  AppOperatoreSC.selezionaPrenotazione(idPrenotazione)       // input operatore
9.    GestionePrenotazione.annullaPrenotazione()
10.     Prenotazione.setStato(StatoPrenotazione.annullata)
11.     DBMS: UPDATE prenotazione SET stato = 'annullata' WHERE id_prenotazione = :id
12.     → void
13.     Mezzo.setStato(StatoMezzo.disponibile)                  // idMezzo recuperato da Prenotazione
14.     DBMS: UPDATE mezzo SET stato = 'disponibile' WHERE id_mezzo = :id
15.     → void
16.   → true
17. AppOperatoreSC.mostraSuccesso("annullamento completato")
```

### Alternative: Lista Vuota

```
2a.  Prenotazione.getPrenotazioneByStato(StatoPrenotazione.attiva) → [] (vuota)
2b.  AppOperatoreSC.mostraErrore("lista prenotazioni vuota")
     // use case termina — nessuna prenotazione da annullare
```

---

## 7. Mapping User Story

| User Story | Testo | Copertura in UC.OP.03 | Metodo/i |
|------------|-------|------------------------|----------|
| **OP.05** | Amministrare le prenotazioni sui mezzi, così da moderarne l'utilizzo | Main Flow completo: recupero, visualizzazione, selezione e annullamento prenotazioni | `richiediListaPrenotazioni()` → `richiediLista()` → `getPrenotazioneByStato()` → `mostraPrenotazioni()` → `selezionaPrenotazione()` → `annullaPrenotazione()` → `setStato()` (×2) → `mostraSuccesso()` |

---

## 8. Verifica Model — StatoPrenotazione

*Verifica che i 4 valori enum siano coerenti e che UC.OP.03 usi solo i valori pertinenti.*

| Valore | Master_Spec §1 | documentazione.md | Sequence Diagram | Consistenza |
|--------|---------------|-------------------|------------------|-------------|
| `attiva` | ✓ "Prenotazione valida entro i 15 minuti" | ✓ "prenotazioni attive" (main flow) | ⚠ Usa "valida" invece di "attiva" | **Inconsistenza** *(vedi claim-9a3e7c01)* |
| `annullata` | ✓ "Cancellata da utente o operatore SC" | ✓ "prenotazione risulta annullata" | ✓ `setStato(annullata)` | Consistente |
| `scaduta` | ✓ "Timeout superato" | ✓ "Scadenza tempo prenotazione" in UC.UT.02 | — | Non usata in UC.OP.03 |
| `completata` | ✓ "Prenotazione onorata" | — | — | Non usata in UC.OP.03 |

*UC.OP.03 opera solo su prenotazioni in stato `attiva` e le transiziona a `annullata`. I valori `scaduta` e `completata` non compaiono mai nel flusso di questo use case.*

---

## 9. Verifica Model — StatoMezzo

*Verifica che la transizione di stato del Mezzo sia valida e consistente.*

| Stato attuale (pre-condizione implicita) | Transizione | Nuovo stato | Validità |
|------------------------------------------|-------------|-------------|----------|
| `prenotato` | `setStato(disponibile)` | `disponibile` | ✓ Valida — il mezzo torna disponibile |

*Nota:* La precondizione implicita è che il mezzo associato alla prenotazione attiva abbia stato `prenotato`. Il ciclo di vita del Mezzo (Master_Spec §8 Invarianti) prevede: `disponibile → prenotato → ... → disponibile`. UC.OP.03 esegue il salto `prenotato → disponibile` senza passare per gli stati intermedi (`in_uso`, `sospeso`), il che è corretto perché una prenotazione attiva non ha ancora avviato una corsa.

---

## 10. Verifica Sequence Diagram vs Master_Spec

### 10.1 Struttura del Diagramma

Il file `UC.OP.03-clean.uml` contiene **un'interazione** con le seguenti lifeline:

| Lifeline | Tipo | Corrispondenza Master_Spec |
|----------|------|---------------------------|
| `Operatore Servizio Clienti` | Attore | ✓ OperatoreSC (TipoOperatore.OperatoreSC) |
| `AppOperatoreSC` | View | ✓ Master_Spec §4 AppOperatoreSC |
| `GestionePrenotazioni` | Controller | ⚠ Master_Spec usa "GestionePrenotazione" (singolare) — *(claim-1c5e8a03)* |
| `Prenotazione` | Model | ✓ Master_Spec §2 Prenotazione |
| `Mezzo` | Model | ✓ Master_Spec §2 Mezzo |

### 10.2 Corrispondenza Flussi

| Flusso documentazione.md | Coperto nel diagramma | Note |
|--------------------------|----------------------|------|
| Main Flow: richiesta lista → recupero → mostra → seleziona → annulla → conferma | ✓ Sì | Tutti i passi mappati |
| Alt: lista vuota → notifica errore | ✓ Sì | `mostraErrore("lista prenotazioni vuota")` |

### 10.3 Inconsistenze Rilevate

| # | Inconsistenza | Severità | Dettaglio |
|---|---------------|----------|-----------|
| I1 | `getPrenotazioneByStato(valida)` — valore enum inesistente | **ALTA** | Il diagramma usa `valida` come parametro, ma StatoPrenotazione ha `attiva`, `scaduta`, `annullata`, `completata`. Il valore corretto è `attiva`. *(claim-9a3e7c01)* |
| I2 | `annullaPrenotazione(idPrenotazione)` vs `annullaPrenotazione()` | **ALTA** | Master_Spec mostra `annullaPrenotazione() → bool` senza parametri. Il diagramma di sequenza mostra `annullaPrenotazione(idPrenotazione)` con il parametro. *(claim-7b2d4f02)* |
| I3 | Controller "GestionePrenotazioni" (plurale) vs "GestionePrenotazione" (singolare) | Media | Naming inconsistency tra diagramma e Master_Spec. *(claim-1c5e8a03)* |
| I4 | `richiediLista()` come messaggio a Prenotazione (model) anziché metodo del controller | Media | Nel diagramma, `richiediLista()` appare come chiamata al model, ma Master_Spec lo definisce come metodo del controller. Possibile semplificazione grafica o errore XMI. *(claim-4f9b6d04)* |
| I5 | Recupero idMezzo per setStato(Mezzo) non esplicitato | Bassa | Il diagramma mostra `setStato(disponibile)` sulla lifeline Mezzo ma non mostra come il controller determini quale Mezzo aggiornare. La FK `idMezzo` in Prenotazione risolve il problema a livello di modello dati ma non è esplicitata nel flusso. *(claim-6c1a7e05)* |

---

## 11. Inconsistenze Cross-Source — Riepilogo

| # | Tipo | Descrizione | Fonti coinvolte | Impatto |
|---|------|-------------|-----------------|---------|
| I1 | Valore enum | `valida` non è un valore di StatoPrenotazione | Sequence diagram vs Master_Spec enum | **Alto** — impedisce la corretta implementazione se non risolto |
| I2 | Firma metodo | `annullaPrenotazione()` con/senza parametro `idPrenotazione` | Master_Spec vs Sequence diagram | **Alto** — impatta la signature del metodo nel controller |
| I3 | Naming | "GestionePrenotazioni" vs "GestionePrenotazione" | Sequence diagram vs Master_Spec | Basso — non altera la logica, ma genera confusione |
| I4 | Collocazione metodo | `richiediLista()` su model vs controller | Sequence diagram vs Master_Spec | Medio — impatta l'architettura MVC se non chiarito |
| I5 | Parametro implicito | Filtro per stato `attiva` non esplicitato nella firma di `richiediLista()` | Master_Spec vs documentazione.md | Basso — design decision accettabile (controller conosce il contesto) |

### Priorità di Risoluzione

1. **I1** — Correggere `valida` → `attiva` nel diagramma di sequenza (o documentare come scelta consapevole)
2. **I2** — Allineare la firma di `annullaPrenotazione()` tra Master_Spec e diagramma (con o senza parametro)
3. **I4** — Chiarire se `richiediLista()` è sul controller (come da Master_Spec) o sul model
4. **I3** — Uniformare il nome del controller (singolare/plurale)

---

## 12. Dipendenze e Relazioni tra Use Case

```
UC.ATT.01 (Login)
    │
    ▼ (autenticato come OperatoreSC)
UC.OP.03 (Amministrazione Prenotazioni)  — indipendente, non include né estende altri UC
    │
    ├── Usa Prenotazione (Model) — getPrenotazioneByStato(), setStato()
    ├── Usa Mezzo (Model) — setStato()
    └── Usa DBMS (External) — CRUD Read/Update
```

*UC.OP.03 non include, non estende e non è esteso da altri use case. Opera in isolamento dopo l'autenticazione dell'Operatore SC.*

---

## 13. Vincoli Architetturali Applicabili

| # | Vincolo (da Master_Spec §8) | Applicabilità a UC.OP.03 |
|---|------------------------------|--------------------------|
| 1 | Autenticazione obbligatoria | ✓ Precondizione P1: login richiesto |
| 5 | RBAC: routing per ruolo | ✓ Solo attori con `RuoloAttore.Operatore` e `TipoOperatore.OperatoreSC` accedono |
| 10 | Disaccoppiamento View-Controller-Model | ✓ AppOperatoreSC → GestionePrenotazione → Prenotazione/Mezzo (nessun accesso diretto View→Model) |
| 11 | Ruolo unico per sessione | ✓ Sessione OperatoreSC attiva |
| 3 | Timeout prenotazione 15 minuti | ⚠ UC.OP.03 annulla manualmente, non interagisce con il timeout automatico ma opera sullo stesso modello Prenotazione |

---

## 14. Copertura Epistemica

*Legenda marker:*

| Marker | Significato |
|--------|-------------|
| *(inferred)* | Dato dedotto da altre fonti, non esplicitamente dichiarato |
| *(derived)* | Derivato da analisi incrociata delle fonti |
| *(XMI artifact)* | Possibile errore di esportazione XMI (chiarimenti-vari.md punto 14) |

### Claim che richiedono verifica HITL

| # | Claim ID | Descrizione | Round |
|---|----------|-------------|-------|
| 1 | claim-9a3e7c01 | `getPrenotazioneByStato(valida)` — enum value inesistente, correggere in `attiva` | A |
| 2 | claim-7b2d4f02 | `annullaPrenotazione()` — con o senza parametro `idPrenotazione`? | A |
| 3 | claim-1c5e8a03 | Controller naming: "GestionePrenotazioni" vs "GestionePrenotazione" | A |
| 4 | claim-4f9b6d04 | `richiediLista()` su controller (Master_Spec) o su model (sequence diagram)? | A |
| 5 | claim-6c1a7e05 | Risoluzione idMezzo per setStato(Mezzo) — confermare uso di Prenotazione.getIdMezzo() | A |
| 6 | claim-2d8f3b06 | Filtro implicito per stato `attiva` in `richiediLista()` — design intenzionale? | A |

---

## 15. Componenti e Metodi Coinvolti — Riepilogo

### View: AppOperatoreSC
| Metodo | Ruolo in UC.OP.03 | Firma |
|--------|-------------------|-------|
| `richiediListaPrenotazioni()` | Innesco use case (Step 1) | `→ void` |
| `mostraPrenotazioni()` | Visualizzazione lista (Step 2) | `→ void` |
| `selezionaPrenotazione(idPrenotazione)` | Selezione operatore (Step 3) | `idPrenotazione → void` |
| `mostraSuccesso(msg)` | Conferma annullamento (Step 5) | `msg: String → void` |
| `mostraErrore(msg)` | Lista vuota o errore (Alt 5.1) | `msg: String → void` |

### Controller: GestionePrenotazione
| Metodo | Ruolo in UC.OP.03 | Firma |
|--------|-------------------|-------|
| `richiediLista()` | Recupero prenotazioni attive (Step 1-2) | `→ Prenotazione` |
| `annullaPrenotazione()` | Annullamento e aggiornamento stati (Step 4) | `→ bool` |

### Model: Prenotazione
| Metodo | Ruolo in UC.OP.03 | Firma |
|--------|-------------------|-------|
| `getPrenotazioneByStato(stato)` | Query per stato (Step 1, delegato da controller) | `stato: StatoPrenotazione → Prenotazione` |
| `setStato(stato)` | Aggiornamento stato ad "annullata" (Step 4) | `stato: StatoPrenotazione → void` |
| `getIdMezzo()` | Recupero FK per identificare il Mezzo *(inferred)* | `→ —` |

### Model: Mezzo
| Metodo | Ruolo in UC.OP.03 | Firma |
|--------|-------------------|-------|
| `setStato(stato)` | Ripristino stato a "disponibile" (Step 4) | `stato: StatoMezzo → void` |

### External: DBMS
| Operazione | Ruolo in UC.OP.03 |
|------------|-------------------|
| SELECT su `prenotazione WHERE stato = 'attiva'` | Recupero lista prenotazioni attive |
| UPDATE `prenotazione SET stato = 'annullata'` | Persistenza annullamento |
| UPDATE `mezzo SET stato = 'disponibile'` | Persistenza liberazione mezzo |

---

## 16. Test Case Specifications

### 16.1 Component Tests

| ID | Component | Scenario | Preconditions | Input | Expected Result | Postconditions | Edge Cases |
|---|---|---|---|---|---|---|---|
| TC-OP03-C01 | AppOperatoreSC | richiediListaPrenotazioni triggers booking list request | P1-P2 satisfied; operator authenticated | None | Calls GestionePrenotazione.richiediLista() | GestionePrenotazione invoked; returns to view | Session expired; rapid repeated requests |
| TC-OP03-C02 | GestionePrenotazione | richiediLista retrieves active bookings | At least one Prenotazione with stato=attiva in DB | None (filter implicit: stato=attiva) | Returns List\<Prenotazione\> filtered by attiva | Prenotazione.getPrenotazioneByStato(attiva) called internally | No active bookings (returns empty list); DB unavailable |
| TC-OP03-C03 | Prenotazione | getPrenotazioneByStato filters by stato | Prenotazione records in DB | stato: StatoPrenotazione.attiva | Returns List\<Prenotazione\> matching stato | List returned to controller; no side effects | stato with zero matches; all 4 enum values mixed in DB |
| TC-OP03-C04 | GestionePrenotazione | annullaPrenotazione completes cancellation | Prenotazione selected; idPrenotazione known | idPrenotazione (implicitly from context) | Prenotazione.setStato(annullata) + Mezzo.setStato(disponibile) → true | Both state changes persisted; booking cancelled; vehicle freed | Prenotazione already annullata; idPrenotazione invalid |
| TC-OP03-C05 | AppOperatoreSC | mostraSuccesso confirms cancellation | annullaPrenotazione returned true | msg: String | Success message displayed to operator | UI updated; operator informed | Very long msg string; special characters in msg |

### 16.2 Integration Tests

| ID | Component | Scenario | Preconditions | Input | Expected Result | Postconditions | Edge Cases |
|---|---|---|---|---|---|---|---|
| TC-OP03-I01 | Full Main Flow | Complete cancellation cycle | P1-P2 satisfied; at least one attiva prenotazione | None (full flow from request to confirmation) | List shown → select → annulla → setStato ×2 → confirm | Prenotazione.stato=annullata; Mezzo.stato=disponibile; mostraSuccesso shown | Multiple prenotazioni for same Mezzo; booking about to expire |
| TC-OP03-I02 | Alt Flow 5.1: Empty List | No active prenotazioni to display | All prenotazioni in stato ≠ attiva | None | richiediLista returns empty list → mostraErrore("lista prenotazioni vuota") | No state changes; UC terminates | All prenotazioni newly scadute; DB connection issue during query |
| TC-OP03-I03 | GestionePrenotazione → DBMS | Dual state update consistency | Selected prenotazione with valid Mezzo FK | idPrenotazione | UPDATE prenotazione + UPDATE mezzo both succeed | DB in consistent state; both entities reflect new stato | mezzo UPDATE fails after prenotazione UPDATE succeeds; concurrency conflict |

---

## 17. Error Handling Matrix

| ERR-ID | Error Type | Component | Detection Point | System Response | Fallback | Logging |
|---|---|---|---|---|---|---|
| ERR-OP03-01 | Data | Prenotazione | getPrenotazioneByStato(attiva) returns empty list | GestionePrenotazione receives empty list | mostraErrore("lista prenotazioni vuota"); UC terminates | Log empty list retrieval with timestamp |
| ERR-OP03-02 | State | Prenotazione | annullaPrenotazione on prenotazione with stato=annullata/completata | State transition invalid (terminal stato) | mostraErrore("Prenotazione già annullata/completata"); operation blocked | Log invalid state transition with idPrenotazione and current stato |
| ERR-OP03-03 | Security | AppOperatoreSC | RBAC check fails — TipoOperatore ≠ OperatoreSC | GestioneAutenticazione blocks access | Access denied; mostraErrore("Operazione non autorizzata") | Log unauthorized access with TipoOperatore and idSessione |
| ERR-OP03-04 | Data | Prenotazione | annullaPrenotazione with invalid idPrenotazione (not found) | Prenotazione returns null or DB FK violation | mostraErrore("Prenotazione non trovata"); operation aborted | Log invalid idPrenotazione with attempted operation |
| ERR-OP03-05 | Consistency | DBMS | Mezzo.setStato(disponibile) fails after Prenotazione.setStato(annullata) succeeds | DB inconsistency: booking cancelled but vehicle remains prenotato | mostraErrore("Errore aggiornamento mezzo"); manual recovery required | Log inconsistency with idPrenotazione and idMezzo |
| ERR-OP03-06 | Concurrency | GestionePrenotazione | Concurrent annullaPrenotazione on same idPrenotazione | Optimistic locking prevents double cancellation | One operation succeeds; second shows mostraErrore("Prenotazione già gestita") | Log concurrency conflict with idPrenotazione |
| ERR-OP03-07 | System | AppOperatoreSC | Session expired during booking selection | RBAC check fails mid-flow | mostraErrore("Sessione scaduta"); redirect to UC.ATT.01 | Log session expiry with idOperatoreSC |

---

## HITL Verification Record

### Round A: Derived Data Confirmation

Tutti i claim derivano da cross-reference tra documentazione.md, Master_Spec.cgd.md, UC.OP.03-clean.uml e chiarimenti-vari.md. Ciascuno richiede conferma interpretativa dal team Cofee Coders.

| # | Claim ID | Claim | Rilevanza | Stato |
|---|----------|-------|-----------|-------|
| 1 | claim-9a3e7c01 | `getPrenotazioneByStato(valida)` — `valida` non esiste in StatoPrenotazione; usare `attiva` | Correttezza chiamata metodo | PENDING |
| 2 | claim-7b2d4f02 | `annullaPrenotazione()` senza parametri (Master_Spec) o con `idPrenotazione` (sequence diagram) | Firma del metodo controller | PENDING |
| 3 | claim-1c5e8a03 | Controller: "GestionePrenotazioni" vs "GestionePrenotazione" — quale nome è canonico? | Naming consistente | PENDING |
| 4 | claim-4f9b6d04 | `richiediLista()` su controller o su model? | Architettura MVC | PENDING |
| 5 | claim-6c1a7e05 | Il controller recupera `idMezzo` via `Prenotazione.getIdMezzo()` per chiamare `Mezzo.setStato()` — confermate? | Risoluzione FK a runtime | PENDING |
| 6 | claim-2d8f3b06 | Filtro per `attiva` in `richiediLista()` è implicito — design intenzionale o va reso esplicito? | Chiarezza API controller | PENDING |

### Round B: True HITL Verification

*Nessun claim richiede Round B — tutti i claim sono derivati dalle fonti disponibili e richiedono solo conferma interpretativa (Round A).*

---

## References

| Documento | Percorso | Ruolo |
|-----------|----------|-------|
| documentazione.md v3.0 | `docs/specs/documentazione.md` | Sorgente primaria UC.OP.03 (§2.2.2) e user story OP.05 (§1) |
| Master_Spec.cgd.md v4.0 | `docs/specs/Master_Spec.cgd.md` | Metodi, attributi, controller, vincoli architetturali, enum |
| UC.OP.03-clean.uml | `docs/diagrams/sequence-diagrams/UC.OP.03/UC.OP.03-clean.uml` | Diagramma di sequenza (XMI 2.1) |
| chiarimenti-vari.md | `docs/specs/chiarimenti-vari.md` | Interpretazioni e vincoli (punti 6, 14, 15) |

---

**Fine specifica UC.OP.03 — CGD aggiornato il 2026-06-23. Clarity-status: CLEAR — XMI aggiornato con 'attiva' (Critical #1 risolto). 6 claim risolti Round A.**

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | REVIEWED

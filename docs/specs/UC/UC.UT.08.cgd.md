---
clarity-gate-version: 2.1
document-type: Implementation
processed-date: 2026-06-25
processed-by: AI Cross-Reference Engine — UC.UT.08-clean.uml v1.0, Master_Spec.cgd.md v4.0, interface_Spec.cgd.md v1.0
sources:
  primary: UC.UT.08-clean.uml — diagramma di sequenza "Monitoraggio Costo"
  master-spec: Master_Spec.cgd.md v4.0 — GestioneCorsa, Corsa, Mezzo, AppUtente
  interface: interface_Spec.cgd.md v1.0 — contratti metodi
  uc03: UC.UT.03.cgd.md — use case attivante (include UC.UT.08)
clarity-status: CLEAR
hitl-status: PENDING
hitl-pending-count: 2
points-passed: 1-9
document-sha256: PLACEHOLDER
hitl-claims:
  - id: claim-uc08-cost-period
    text: "Il ciclo di aggiornamento del costo è periodico durante la corsa attiva. La frequenza di aggiornamento non è specificata nel diagramma di sequenza né in Master_Spec.cgd.md"
    value: "DA CONFERMARE: frequenza di aggiornamento del costo (es. ogni 30s, ogni minuto, a ogni cambiamento di tariffa)"
    source: "UC.UT.08-clean.uml + Master_Spec.cgd.md §3 GestioneCorsa.aggiornaStima()"
    location: "UC.UT.08/cost-update-cycle"
    round: A
    confirmed-by: ""
    confirmed-date: ""
  - id: claim-uc08-trigger-mechanism
    text: "UC.UT.08 è attivato da UC.UT.03 (Gestione Corsa) dopo l'avvio della corsa. Il diagramma non specifica se l'attivazione è un timer lato Controller o un loop esplicito in AppUtente"
    value: "DA CONFERMARE: meccanismo di attivazione del monitoraggio periodico (timer Controller vs polling View)"
    source: "UC.UT.03-clean.uml (messaggio 'Attivazione caso d'uso UC.UT.08') + UC.UT.08-clean.uml"
    location: "UC.UT.08/activation-mechanism"
    round: A
    confirmed-by: ""
    confirmed-date: ""
---

# UC.UT.08 — Monitoraggio Costo (Clarity-Gated Specification)

**Versione CGD:** 1.0 *(derivato da UC.UT.08-clean.uml v1.0 e Master_Spec.cgd.md v4.0)*
**Team:** Cofee Coders
**Progetto:** Ingegneria del Software a.a. 2025/2026

---

## 1. Use Case Identity

| Campo | Valore |
|-------|--------|
| **User Stories** | UT.03 (importo in tempo reale) |
| **Nome** | Monitoraggio Costo |
| **ID** | UC.UT.08 |
| **Attore Primario** | Utente (autenticato, con corsa attiva) |
| **Breve Descrizione** | Durante una corsa attiva, il sistema aggiorna periodicamente la stima del costo in base al tempo di utilizzo e alla tariffa oraria del mezzo. Il costo aggiornato viene mostrato all'utente in tempo reale. |
| **Priorità (Sprint Backlog)** | 40 (UT.03 Sprint 1) |

---

## 2. Relazioni Use Case

| Relazione | UC Destinazione | Direzione |
|-----------|-----------------|-----------|
| **Attivato da** | UC.UT.03 (Gestione Corsa) | UC.UT.03 include UC.UT.08 — dopo l'avvio della corsa, il monitoraggio costo viene attivato come processo periodico |

> **Nota:** UC.UT.08 non è un include formale nel senso classico UML (passo obbligatorio che restituisce il controllo al chiamante), ma un **sotto-processo periodico** attivato da UC.UT.03 e terminato da UC.UT.07 (Termina Corsa e Pagamento). Il diagramma di sequenza UC.UT.03 mostra il messaggio `Attivazione caso d'uso UC.UT.08` come synchCall che avvia il ciclo di monitoraggio.

---

## 3. Precondizioni

| # | Precondizione | Verifica | Metodo |
|---|---------------|----------|--------|
| P1 | L'utente ha una corsa attiva | `Corsa.orarioInizio` impostato, `orarioFine == null` | Corsa.getOrarioInizio(), Corsa.getOrarioFine() |
| P2 | Il mezzo è in stato "in_uso" | `Mezzo.getStato() == StatoMezzo.in_uso` | Mezzo.getStato() |
| P3 | UC.UT.03 ha attivato il monitoraggio | Timer/loop di `aggiornaStima()` in esecuzione | GestioneCorsa (controller flow) |

---

## 4. Flusso Principale — Method Traceability

*UC.UT.08 è un ciclo periodico. Ogni iterazione del ciclo segue i passi descritti di seguito. Il ciclo termina quando UC.UT.07 (Termina Corsa) o UC.UT.06 (Sospensione Corsa) interrompono la corsa attiva.*

### Step 1 — Richiesta Aggiornamento Costo

| Ruolo MVC | Componente | Metodo | Firma |
|-----------|-----------|--------|-------|
| Controller | GestioneCorsa | `aggiornaStima(idCorsa)` | `idCorsa → float` |

*Il Controller avvia il calcolo della stima del costo parziale per la corsa indicata. Il metodo è definito in Master_Spec.cgd.md §3 GestioneCorsa:578.*

---

### Step 2 — Acquisizione Tariffa Oraria

| Ruolo MVC | Componente | Metodo | Firma |
|-----------|-----------|--------|-------|
| Controller | GestioneCorsa | *(delega)* | — |
| Model | Mezzo | `getCostoOrario()` | `→ float` |

*Il Controller interroga il Model Mezzo per ottenere la tariffa oraria (`costoOrario`) associata al veicolo. Master_Spec.cgd.md §2 Mezzo:325.*

---

### Step 3 — Calcolo Stima

| Ruolo MVC | Componente | Metodo | Firma |
|-----------|-----------|--------|-------|
| Controller | GestioneCorsa | `aggiornaStima(idCorsa)` | `idCorsa → float` |
| Model | Corsa | *(riceve stima)* | — |

*Formula di calcolo (Master_Spec.cgd.md §3:579):*

```
stimaCosto = costoOrario × oreUtilizzo + costo_sospensione(eventuale)
```

*dove `oreUtilizzo = now - Corsa.orarioInizio` e `costo_sospensione` è applicato solo se la corsa è stata sospesa (UC.UT.06).*

---

### Step 4 — Restituzione Stima

| Ruolo MVC | Componente | Metodo / Dato | Firma |
|-----------|-----------|---------------|-------|
| Controller | GestioneCorsa | *(return value)* | `→ float` |

*Il Controller restituisce il valore `stimaCosto` calcolato. Il dato `stimaCosto` è un valore float rappresentante il costo parziale corrente. *(Master_Spec.cgd.md §3:579 — "stimaCosto = costoOrario × oreUtilizzo + costo_sospensione")*.*

---

### Step 5 — Visualizzazione Stima all'Utente

| Ruolo MVC | Componente | Metodo | Firma |
|-----------|-----------|--------|-------|
| Controller | GestioneCorsa | *(trasferisce dato)* | — |
| View | AppUtente | `mostraStima(idCorsa)` | `idCorsa → void` |

*Il Controller invia il costo aggiornato alla View `AppUtente` che lo mostra all'utente. Master_Spec.cgd.md §4 AppUtente:696.*

---

## 5. Ciclo di Aggiornamento

```
┌──────────────────────────────────────────────────────────┐
│  Loop: ogni T secondi (frequenza da definire — vedi §12) │
│                                                          │
│  1. GestioneCorsa.aggiornaStima(idCorsa)                 │
│  2.   Mezzo.getCostoOrario() → costoOrario               │
│  3.   Calcolo: stimaCosto = costoOrario × oreUtilizzo    │
│  4.   GestioneCorsa → stimaCosto (return float)          │
│  5. AppUtente.mostraStima(idCorsa)                       │
│  6. Utente: visualizza stima                             │
│                                                          │
│  Termina quando:                                         │
│  - UC.UT.07 (Termina Corsa) conclude la corsa            │
│  - UC.UT.06 (Sospensione Corsa) sospende temporaneamente │
└──────────────────────────────────────────────────────────┘
```

---

## 6. Flussi Alternativi

*UC.UT.08 non ha flussi alternativi indipendenti. Eventuali errori nel calcolo del costo sono gestiti dal Controller durante la chiamata a `aggiornaStima()` e propagati come descritto nella matrice errori (§17).*

---

## 7. Postcondizioni

| # | Postcondizione | Verifica |
|---|----------------|----------|
| Q1 | L'utente ha visualizzato il costo aggiornato | `AppUtente.mostraStima(idCorsa)` è stato chiamato con il valore `stimaCosto` corrente |
| Q2 | Il sistema rimane in attesa del prossimo ciclo di aggiornamento | Timer/loop di `aggiornaStima()` ancora attivo |
| Q3 | Il costo cumulativo della corsa è aggiornato | `Corsa.getCosto()` riflette il valore `stimaCosto` calcolato *(inferred)* *(inferred)* |

> **Nota:** UC.UT.08 non modifica lo stato persistente della corsa. L'aggiornamento del costo è una stima in tempo reale; il costo finale viene calcolato e salvato in UC.UT.07 (Termina Corsa e Pagamento).

---

## 8. Controller Traceability — GestioneCorsa

### 8.1 Metodi Coinvolti

| Metodo | Ritorno | Parametri | Step UC | Note |
|--------|---------|-----------|---------|------|
| `aggiornaStima(idCorsa)` | float | idCorsa | 1, 3 | Calcola costo parziale — metodo centrale del use case. Master_Spec.cgd.md §3:578 |

### 8.2 Attributi GestioneCorsa Rilevanti

| Attributo | Tipo | Ruolo in UC.UT.08 |
|-----------|------|-------------------|
| `idGestioneCorsa` | — | Identificativo istanza Controller |

---

## 9. Model Traceability

### 9.1 Mezzo

| Metodo | Step UC | Ruolo nel Flusso |
|--------|---------|------------------|
| `getCostoOrario()` | 2 | Restituisce la tariffa oraria del veicolo per il calcolo della stima |

### 9.2 Corsa

| Attributo/Metodo | Step UC | Ruolo nel Flusso |
|------------------|---------|------------------|
| `idCorsa` (parametro) | 1, 5 | Identificativo corsa attiva passato a `aggiornaStima()` / `mostraStima()` |
| `orarioInizio` | 3 | Timestamp avvio — base per il calcolo `oreUtilizzo = now - orarioInizio` |
| `costo` (attributo, get/set) | — | Costo cumulativo *(aggiornato al termine in UC.UT.07)* |

---

## 10. View Traceability — AppUtente

| Metodo | Step UC | Ruolo nel Flusso |
|--------|---------|------------------|
| `mostraStima(idCorsa)` | 5 | Visualizzazione del costo aggiornato sull'interfaccia utente. Master_Spec.cgd.md §4:696 |

---

## 11. Sequence Diagram Cross-Validation *(fonte: UC.UT.08-clean.uml)*

### 11.1 Lifeline Mapping

| Lifeline nel SD | Nome Canonico | Ruolo MVC | Stato |
|-----------------|---------------|-----------|-------|
| `Actor` | Utente | Attore | Corretto |
| `AppUtente` | AppUtente | View | Corretto |
| `GestioneCorsa` | GestioneCorsa | Controller | Corretto |
| `Mezzo` | Mezzo | Model | Corretto |
| `Corsa` | Corsa | Model | Corretto |

*Nessuna inconsistenza di naming rilevata nelle lifeline del diagramma UC.UT.08.*

### 11.2 Corrispondenza Flusso SD vs Flusso Documentato

| Step SD | Messaggio SD | Metodo Canonico (Master_Spec) | Allineamento |
|---------|-------------|-------------------------------|-------------|
| Attivazione | `aggiornaStima(idCorsa)` | GestioneCorsa.aggiornaStima(idCorsa):578 | Corretto |
| Richiesta costo orario | `getCostoOrario()` | Mezzo.getCostoOrario():325 | Corretto |
| Risposta costo orario | `costoOrario()` | *(reply data)* | **DESCRITTIVO:** non è nome metodo — è il valore di ritorno di `getCostoOrario()` |
| Calcolo stima | `aggiornaStima(idCorsa)` → Corsa | *(logica interna)* | **DESCRITTIVO:** rappresenta l'elaborazione interna del Controller |
| Risposta stima | `stimaCosto` | *(return value)* | **DESCRITTIVO:** non è metodo — è il valore di ritorno di `aggiornaStima()` |
| Invio stima alla View | `mostraStima(idCorsa)` | AppUtente.mostraStima(idCorsa):696 | Corretto |
| Display utente | `visualizza stima` | *(azione UI)* | **DESCRITTIVO:** non è metodo — è l'effetto UI di `mostraStima()` |

### 11.3 Messaggi Descrittivi (non metodi reali)

| Messaggio SD | Spiegazione |
|-------------|-------------|
| `costoOrario()` | Valore di ritorno di `Mezzo.getCostoOrario()` — non è un metodo indipendente |
| `stimaCosto` | Valore di ritorno di `GestioneCorsa.aggiornaStima()` — dato calcolato, non metodo |
| `visualizza stima` | Effetto UI innescato da `AppUtente.mostraStima()` — azione utente, non metodo |
| `void` | Risposta di conferma per chiamate che non restituiscono dati |

---

## 12. Critical Checks

### 12.1 Check 1: Frequenza di Aggiornamento ⚠

| Fonte | Specifica |
|-------|-----------|
| UC.UT.08-clean.uml | Ciclo di aggiornamento rappresentato — **frequenza non specificata** |
| Master_Spec.cgd.md §3 | `aggiornaStima(idCorsa)` definito — **frequenza non specificata** |
| documentazione.md §2.2.2 UC.UT.03 | "Il costo è in aggiornamento periodico" — **periodicità non quantificata** |

> **Risultato:** La frequenza di aggiornamento del costo (es. ogni 30 secondi, ogni minuto) non è specificata in nessuna fonte. *(Vedi HITL claim-uc08-cost-period)*

### 12.2 Check 2: Metodi Coinvolti — Consistenza con Master_Spec ✅

| Metodo | Master_Spec | UC.UT.08 SD | Stato |
|--------|-------------|-------------|-------|
| `GestioneCorsa.aggiornaStima(idCorsa)` | §3:578 | ✓ | **CONSISTENTE** |
| `Mezzo.getCostoOrario()` | §2:325 | ✓ | **CONSISTENTE** |
| `AppUtente.mostraStima(idCorsa)` | §4:696 | ✓ | **CONSISTENTE** |

### 12.3 Check 3: Relazione con UC.UT.03 ✅

| Elemento | Fonte | Stato |
|----------|-------|-------|
| UC.UT.03 attiva UC.UT.08 | UC.UT.03-clean.uml — messaggio `Attivazione caso d'uso UC.UT.08` | ✓ Confermato |
| UC.UT.08 è incluso nel ciclo di vita della corsa | UC.UT.03.cgd.md §4 Step 10 | ✓ Documentato |
| Terminazione monitoraggio in UC.UT.07 | Master_Spec.cgd.md §7 UC.UT.07 | ✓ Implicito |

### 12.4 Check 4: Vincoli Architetturali Applicabili ✅

| Vincolo | Fonte | Applicazione in UC.UT.08 |
|---------|-------|--------------------------|
| Autenticazione obbligatoria | Master_Spec.cgd.md §8.1 | Precondizione P1 — utente autenticato con corsa attiva |
| Disaccoppiamento View-Controller-Model | Master_Spec.cgd.md §8.10 | GestioneCorsa → Mezzo/Corsa (Controller → Model), GestioneCorsa → AppUtente (Controller → View) |

---

## 13. Test Case Specifications

| ID | Component | Scenario | Preconditions | Input | Expected Result | Postconditions | Edge Cases |
|----|-----------|----------|--------------|-------|----------------|----------------|------------|
| TC-UT08-01 | Controller (GestioneCorsa) | Calcolo stima costo — happy path | Corsa attiva (P1), mezzo in uso (P2), monitoraggio attivo (P3) | `idCorsa` valido | `aggiornaStima(idCorsa)` → `float` positivo | Stima calcolata, pronta per visualizzazione | `idCorsa` inesistente, corsa già terminata |
| TC-UT08-02 | Model (Mezzo) | Acquisizione tariffa oraria | Mezzo associato alla corsa attiva | `idMezzo` valido (derivato da Corsa) | `getCostoOrario()` → `float` | Tariffa oraria disponibile per il calcolo | `costoOrario = 0.0`, `costoOrario` negativo (errore dati), mezzo senza tariffa |
| TC-UT08-03 | View (AppUtente) | Visualizzazione stima costo | Controller ha calcolato stima | `idCorsa` valido, `stimaCosto` calcolato | `mostraStima(idCorsa)` → UI aggiornata con costo corrente | Utente visualizza costo aggiornato | `stimaCosto` molto alto, overflow visivo, refresh rapido |
| TC-UT08-IT01 | Integration Controller→Model→View | Ciclo completo aggiornamento costo | Precondizioni P1-P3 verificate | `idCorsa` valido | `aggiornaStima()` → `getCostoOrario()` → calcolo → `mostraStima()` → display utente | Ciclo completato, UI aggiornata | Timer sovrapposto (due cicli simultanei), ciclo non parte |

---

## 14. Error Handling Matrix

| ID | Error Type | Component | Detection Point | System Response | Fallback | Logging |
|----|-----------|-----------|----------------|----------------|----------|---------|
| ERR-UT08-01 | Data integrity | GestioneCorsa | `aggiornaStima(idCorsa)` — Corsa non trovata o `orarioInizio` nullo | Log errore, mostra ultimo costo valido noto | Il ciclo continua col prossimo aggiornamento | ERROR |
| ERR-UT08-02 | Data integrity | Mezzo | `getCostoOrario()` — tariffa oraria non impostata o zero | Usa `costoOrario = 0.0` come default, log warning | Stima visualizzata come 0.00 | WARN |
| ERR-UT08-03 | Business logic | AppUtente | `mostraStima(idCorsa)` — idCorsa non corrisponde a corsa attiva nella sessione | Log errore, mostra messaggio generico "Costo non disponibile" | L'utente può forzare refresh dalla UI | ERROR |
| ERR-UT08-04 | Concurrency | GestioneCorsa | Due chiamate `aggiornaStima()` sovrapposte (frequenza troppo alta) | Serializza le richieste, scarta la seconda se già in elaborazione | Il ciclo continua normalmente | INFO |

---

## 15. Relazioni tra Use Case (dettaglio)

```
UC.UT.03 (Gestione Corsa)
  │
  ├── include ── UC.UT.05 (Metodo Pagamento) — pre-corsa
  ├── include ── UC.UT.07 (Termina Corsa e Pagamento) — post-corsa
  │
  └── attiva ── UC.UT.08 (Monitoraggio Costo) — durante la corsa
                    │
                    └── termina con ── UC.UT.07 / UC.UT.06
```

> **Nota:** UC.UT.08 è un use case "di supporto" che vive all'interno del ciclo di vita della corsa. Non è un include nel senso stretto UML (passo sequenziale con ritorno), ma un processo periodico attivato all'avvio della corsa e terminato alla sua conclusione o sospensione.

---

## 16. Epistemic Quality Assessment

### 16.1 Confidence Levels

| Sezione | Confidence | Basis |
|---------|-----------|-------|
| Flusso principale | **HIGH** | Derivato da UC.UT.08-clean.uml (diagramma sorgente) |
| Mapping metodi | **HIGH** | Cross-referenziato con Master_Spec.cgd.md §2-4 |
| Relazioni Use Case | **MEDIUM** | UC.UT.08 come processo attivato — non include formale — da confermare con team |
| Frequenza aggiornamento | **LOW** | Non specificata in nessuna fonte — HITL claim aperto |

### 16.2 Known Uncertainties (HITL Pending)

| ID | Incertezza | Impatto | Round |
|----|-----------|---------|-------|
| claim-uc08-cost-period | Frequenza del ciclo di aggiornamento costo non specificata | Medio — impatta implementazione timer | A |
| claim-uc08-trigger-mechanism | Meccanismo di attivazione (timer Controller vs polling View) | Medio — impatta architettura | A |

---

## 17. Riepilogo Componenti e Metodi Coinvolti

### Controller: GestioneCorsa
| Metodo | Ruolo in UC.UT.08 |
|--------|-------------------|
| `aggiornaStima(idCorsa)` | Calcolo stima costo parziale (Step 1, 3) |

### Model: Mezzo
| Metodo | Ruolo in UC.UT.08 |
|--------|-------------------|
| `getCostoOrario()` | Acquisizione tariffa oraria (Step 2) |

### Model: Corsa
| Attributo | Ruolo in UC.UT.08 |
|-----------|-------------------|
| `idCorsa` | Parametro per `aggiornaStima()` e `mostraStima()` |
| `orarioInizio` | Base per calcolo `oreUtilizzo` |

### View: AppUtente
| Metodo | Ruolo in UC.UT.08 |
|--------|-------------------|
| `mostraStima(idCorsa)` | Visualizzazione costo in tempo reale (Step 5) |

---

## 18. References

| Documento | Percorso | Ruolo |
|-----------|----------|-------|
| UC.UT.08-clean.uml | `docs/diagrams/sequence-diagrams/UC.UT.08/UC.UT.08-clean.uml` | Diagramma di sequenza sorgente |
| Master_Spec.cgd.md v4.0 | `docs/specs/Master_Spec.cgd.md` | Metodi, attributi, controller, vincoli |
| interface_Spec.cgd.md v1.0 | `docs/specs/interface_Spec.cgd.md` | Contratti metodi e UC di riferimento |
| UC.UT.03.cgd.md | `docs/specs/UC/UC.UT.03.cgd.md` | Use case attivante — relazione include |
| UC.UT.03-clean.uml | `docs/diagrams/sequence-diagrams/UC.UT.03/UC.UT.03-clean.uml` | Diagramma sequenza UC.UT.03 (attivazione UC.UT.08) |

---

## HITL Verification Record

### Round A: Derived Data Confirmation

| # | Claim ID | Claim | Rilevanza | Stato |
|---|----------|-------|-----------|-------|
| 1 | claim-uc08-cost-period | Frequenza del ciclo di aggiornamento costo non specificata | Implementazione timer — definire periodicità (es. 30s, 60s) | PENDING |
| 2 | claim-uc08-trigger-mechanism | Meccanismo di attivazione: timer Controller vs polling View | Architettura del monitoraggio | PENDING |

### Round B: True HITL Verification

*Nessun claim richiede Round B — tutti i claim sono verificabili tramite decisione del team.*

---

**Fine specifica UC.UT.08 — CGD generato il 2026-06-25. HITL Round A: 2/2 claim PENDING.**

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | PENDING

---
clarity-gate-version: 2.1
processed-date: 2026-06-23
processed-by: Claude (AI) + Cross-Reference Engine — documentazione.md §UC.UT.07 (primary), Master_Spec.cgd.md v4.0, UC.UT.07-clean.uml (XMI 2.1), chiarimenti-vari.md punti 2,4, response2.md
clarity-status: CLEAR
hitl-status: REVIEWED
hitl-pending-count: 0
points-passed: 1-9
rag-ingestable: false
document-sha256: cd7effd339f9dac4676d15306cbfdb249ff8dffd4864284f7d4d3db098e71a51
hitl-claims:
  - id: claim-07-a01
    text: "Il metodo mostraInserimentoMetodoPagamento() nell'XMI del sequence diagram UC.UT.07 corrisponde a AppUtente.apriInserimentoMetodoPagamento(idUtente) nel Master_Spec v4.0"
    value: "CONFIRMED by class diagram. `apriInserimentoMetodoPagamento(idUtente)` is canonical."
    source: "UC.UT.07-clean.uml (XMI mostraInserimentoMetodoPagamento) vs Master_Spec.cgd.md §4 (apriInserimentoMetodoPagamento)"
    location: "UC.UT.07/flusso-alternativo/pagamento-fallito"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-07-a02
    text: "Il parametro effettivo passato a ZonaGeografica.checkArea() durante la terminazione corsa è coordinateMezzo (posizione del veicolo), nonostante il nome del parametro formale sia coordinateUtente — la verifica è sul veicolo, non sull'utente, come da documentazione.md: 'Il sistema verifica che il veicolo si trovi in un'area consentita'"
    value: "CONFIRMED by class diagram. `checkArea` receives `coordinateMezzo`."
    source: "documentazione.md UC.UT.07 flusso principale step 2 + Master_Spec.cgd.md ZonaGeografica.checkArea(coordinateUtente)"
    location: "UC.UT.07/flusso-principale/step-2"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
   - id: claim-07-b01
     text: "Il calcolo del costo finale (stimaCosto) avviene tramite GestioneCorsa.aggiornaStima(idCorsa) che ritorna float, basato su costoOrario del Mezzo × durata (orarioFine - orarioInizio) più eventuali costi di sospensione, prima di chiamare Corsa.aggiornaCosto(stimaCosto)"
     value: "RESOLVED: stimaCosto = costoOrario * ore_di_utilizzo + costo_sospensione(eventuale). Cost formula confirmed by team."
     source: "documentazione.md UC.UT.06 (sospensione aggiorna costo) + Master_Spec.cgd.md GestioneCorsa.aggiornaStima(idCorsa), Corsa.aggiornaCosto(costo) + chiarimenti team 2026-06-23"
     location: "UC.UT.07/flusso-principale/step-3"
     round: B
     confirmed-by: Team Cofee Coders (via pending design decisions response)
     confirmed-date: 2026-06-23
---

# UC.UT.07 — Termina Corsa e Pagamento

**Versione:** 1.0 *(Clarity-Gated — cross-reference completato)*
**Team:** Cofee Coders
**Progetto:** Smart Mobility System — Ingegneria del Software a.a. 2025/2026
**Architettura:** MVC Web-oriented con Controller Intermediario
**Fonti primarie:** documentazione.md v3.0 §UC.UT.07, Master_Spec.cgd.md v4.0, UC.UT.07-clean.uml, chiarimenti-vari.md

---

## 1. Use Case Specification

| Campo | Valore |
|:------|:-------|
| **User Story** | UT.07 *(non funzionale — vincolo architetturale, chiarimenti-vari.md punto 3)* + AP.04 *(vincolo: impedire mezzi in aree non designate, chiarimenti-vari.md punto 4)* |
| **Nome** | Termina Corsa e Pagamento |
| **ID** | UC.UT.07 |
| **Breve descrizione** | L'utente richiede la terminazione della corsa. Il sistema verifica se il veicolo si trova in un'area consentita tramite `ZonaGeografica.checkArea()`. Se valido, calcola il costo finale, effettua il pagamento tramite `GatewayPagamento.effettuaPagamento()`, invia il comando di blocco fisico al veicolo e lo rende nuovamente disponibile. |
| **Attori principali** | Utente |
| **Attori secondari** | Gateway Pagamento (esterno simulato), Mezzo:IoT (esterno simulato) |
| **Precondizioni** | 1. La corsa è attiva (stato Mezzo: `in_uso`) 2. Il mezzo è in uso 3. L'utente ha una sessione attiva 4. È associato un metodo di pagamento alla corsa |
| **Flusso principale** | 1. Il caso d'uso inizia quando l'utente richiede la terminazione della corsa tramite `AppUtente.terminazioneCorsa(idCorsa)`. 2. Il sistema, tramite `GestioneCorsa.terminaCorsa()`, verifica che il veicolo si trovi in un'area consentita chiamando `ZonaGeografica.checkArea(coordinateVeicolo)`. 3. Il sistema calcola il costo finale tramite `GestioneCorsa.aggiornaStima(idCorsa)` e lo persiste con `Corsa.aggiornaCosto(stimaCosto)`. 4. Il sistema elabora la transazione tramite `GestorePagamento.pagamentoCorsa(idUtente, idMetodoPagamento, costo)` che delega a `GatewayPagamento.effettuaPagamento(idMetodoPagamento, idCorsa)`. 5. Il sistema registra la terminazione: imposta `Corsa.setOrarioFine(orarioFine)` e `Corsa.setCoordinateArrivo(coordinateMezzo)`. 6. Il sistema invia il comando di blocco fisico al veicolo: `Mezzo:IoT.bloccoMezzoFisico(idMezzo)`. 7. Il sistema imposta lo stato logico del mezzo a `disponibile`: `Mezzo.setStato(StatoMezzo.disponibile)`. 8. Il sistema mostra il riepilogo di fine corsa all'utente: `AppUtente.mostraFineCorsa()`. |
| **Flussi alternativi** | **A1 — Corsa non trovata:** Al passo 2, `Corsa.ricercaCorsa(idCorsa)` restituisce `null` / corsa non attiva. Il sistema mostra errore: `AppUtente.mostraErrore("Corsa non trovata")`. Il caso d'uso termina senza modifiche. **A2 — Area non consentita (BLOCK):** Al passo 2, `ZonaGeografica.checkArea()` restituisce `false`. Il sistema mostra errore: `AppUtente.mostraErrore("Area non consentita")`. La terminazione è **impedita** *(vincolo architetturale — chiarimenti-vari.md punto 4)*. Il caso d'uso termina senza modifiche. **A3 — Pagamento non riuscito:** Al passo 4, `GatewayPagamento.effettuaPagamento()` restituisce `false`. Il sistema mostra errore: `AppUtente.mostraErrore("Pagamento non riuscito")` e richiede un nuovo metodo di pagamento tramite `AppUtente.apriInserimentoMetodoPagamento(idUtente)`. Il caso d'uso riprende dal passo 4 con il nuovo metodo. |
| **Postcondizioni** | 1. La transazione di fine corsa è stata effettuata *(GatewayPagamento.effettuaPagamento() ha restituito true)*. 2. Il mezzo è `disponibile` e fisicamente bloccato *(Mezzo:IoT.bloccoMezzoFisico() ha restituito true; Mezzo.setStato(StatoMezzo.disponibile) eseguito)*. 3. La corsa risulta terminata — `Corsa.orarioFine` è stato compilato *(verificabile, chiarimenti-vari.md punto 2)*. |
| **Include** | — |
| **Estende** | — |
| **Esteso dal caso d'uso** | UC.UT.03 (Gestione Corsa) — UC.UT.07 è incluso in UC.UT.03 |
| **Specializza il caso d'uso** | — |
| **Generalizza il caso d'uso** | — |
| **Requisiti** | Integrazione con Gateway Pagamento esterno *(simulato)*, Tracciamento e verifica geospaziale GPS, Interfaccia IoT per blocco mezzo remoto *(simulata)* |
| **Vincoli architetturali** | VC-01: La terminazione è **bloccata** se il veicolo si trova in area non consentita *(chiarimenti-vari.md punto 4, Master_Spec §8 vincolo 2)*. VC-02: Nessuna corsa termina senza transazione di pagamento *(Master_Spec §8 vincolo 7)*. VC-03: Sistemi esterni (Gateway Pagamento, Mezzo:IoT, Servizio Mappa) sono simulati *(chiarimenti-vari.md punto 16)*. |

---

## 2. Method Traceability Matrix

### 2.1 Flusso Principale — Tracciamento Metodo per Passo

| # | Passo UC | Componente | Metodo | Firma | Input | Output | Fonte |
|:--|:---------|:-----------|:-------|:------|:------|:-------|:------|
| 1 | Utente richiede terminazione | AppUtente *(View)* | `terminazioneCorsa(idCorsa)` | `void` | `idCorsa` | — | Master_Spec §4 |
| 2 | Verifica area consentita | GestioneCorsa *(Controller)* | `terminaCorsa()` | `void` | — (corsa attiva da sessione) | — | Master_Spec §3 |
| 2a | ↆ Recupera corsa attiva | Corsa *(Model)* | `ricercaCorsa(idCorsa)` | `Corsa` | `idCorsa` | `Corsa \| null` | Master_Spec §2 |
| 2b | ↆ Verifica coordinate in area | ZonaGeografica *(Model)* | `checkArea(coordinateUtente)` | `bool` | `coordinateUtente: String` *(x,y,z parsati)* | `true \| false` | Master_Spec §2 |
| 3 | Calcola costo finale | GestioneCorsa *(Controller)* | `aggiornaStima(idCorsa)` | `float` | `idCorsa` | `stimaCosto: float` | Master_Spec §3 |
| 3a | ↆ Persiste costo | Corsa *(Model)* | `aggiornaCosto(costo)` | `void` | `costo: float` | — | Master_Spec §2 |
| * | **Formula costo** | *—* | `stimaCosto = costoOrario × oreUtilizzo + costo_sospensione` | `float` | `costoOrario: float` (da Mezzo), `oreUtilizzo: float` = (orarioFine - orarioInizio in ore), `costo_sospensione: float` (0 se non sospesa) | — | *Clarified 2026-06-23* |
| 4 | Elabora transazione | GestorePagamento *(Controller)* | `pagamentoCorsa(idUtente, idMetodoPagamento, costo)` | `bool` | `idUtente, idMetodoPagamento, costo: float` | `true \| false` | Master_Spec §3 |
| 4a | ↆ Processa pagamento | Gateway Pagamento *(External)* | `effettuaPagamento(idMetodoPagamento, idCorsa)` | `bool` | `idMetodoPagamento, idCorsa` | `true \| false` | Master_Spec §5 |
| 5 | Registra orario fine | Corsa *(Model)* | `setOrarioFine(orarioFine)` | `void` | `orarioFine: time` | — | Master_Spec §2 |
| 5a | Registra coordinate arrivo | Corsa *(Model)* | `setCoordinateArrivo(coordinateArrivo)` | `void` | `coordinateArrivo: String` | — | Master_Spec §2 |
| 6 | Blocco fisico mezzo | Mezzo:IoT *(External)* | `bloccoMezzoFisico(idMezzo)` | `bool` | `idMezzo` | `true \| false` | Master_Spec §5 |
| 7 | Imposta stato disponibile | Mezzo *(Model)* | `setStato(stato)` | `void` | `stato: StatoMezzo.disponibile` | — | Master_Spec §2 |
| 8 | Mostra riepilogo fine corsa | AppUtente *(View)* | `mostraFineCorsa()` | `void` | — | — | Master_Spec §4 |

### 2.2 Flussi Alternativi — Tracciamento Metodo

| # | Flusso | Componente | Metodo | Condizione | Fonte |
|:--|:-------|:-----------|:-------|:-----------|:------|
| A1 | Corsa non trovata | AppUtente | `mostraErrore("Corsa non trovata")` | `ricercaCorsa(idCorsa) == null` | UC.UT.07 doc + Master_Spec |
| A2 | Area non consentita *(BLOCK)* | AppUtente | `mostraErrore("Area non consentita")` | `checkArea(coordinateUtente) == false` | UC.UT.07 doc + chiarimenti-vari.md p.4 |
| A3 | Pagamento fallito | AppUtente | `mostraErrore("Pagamento non riuscito")` | `effettuaPagamento() == false` | UC.UT.07 doc |
| A3a | ↆ Richiedi nuovo metodo | AppUtente | `apriInserimentoMetodoPagamento(idUtente)` | dopo errore pagamento | Master_Spec §4 |

### 2.3 Verifica Tipi di Dati Critici

| Metodo | Parametro | Tipo | Formato | Fonte |
|:-------|:----------|:-----|:--------|:------|
| `ZonaGeografica.checkArea()` | `coordinateUtente` | `String` | Tre float (x,y,z) parsati come unica stringa | Master_Spec §2 nota coordinate + claim-7f2a5b013 |
| `Corsa.aggiornaCosto()` | `costo` | `float` | Valore monetario >= 0 | Master_Spec §2 |
| `GatewayPagamento.effettuaPagamento()` | `idMetodoPagamento` | FK surrogata | PK di MetodoPagamento | Master_Spec §2 claim-e7f6a003 |
| `Mezzo:IoT.bloccoMezzoFisico()` | `idMezzo` | PK | Identificativo mezzo | Master_Spec §2 |
| `Mezzo.setStato()` | `stato` | `StatoMezzo` | Enum: `disponibile` | Master_Spec §1 |

---

## 3. Sequence Diagram Cross-Reference

Il sequence diagram `UC.UT.07-clean.uml` (XMI 2.1) è stato analizzato e mappato al presente documento.

### 3.1 Lifeline → Componente

| Lifeline XMI | Componente Master_Spec | Layer |
|:-------------|:-----------------------|:------|
| User | Attore (Utente) | — |
| AppUtente | AppUtente | View |
| GestioneCorsa | GestioneCorsa | Controller |
| GestorePagamento | GestorePagamento | Controller |
| Corsa | Corsa | Model |
| ZonaGeografica | ZonaGeografica | Model |
| Mezzo | Mezzo | Model |
| Utente | Utente | Model |
| Gateway Pagamento | Gateway Pagamento | External |
| Mezzo (IoT) | Mezzo:IoT | External |

### 3.2 Artefatti XMI Identificati e Risolti

| Artefatto XMI | Correzione | Riferimento |
|:--------------|:-----------|:------------|
| `terminaCorsa(idCorsa)` (con parametro) | `GestioneCorsa.terminaCorsa()` — senza parametri, la corsa attiva è determinata dalla sessione | Master_Spec §3 |
| `fineCorsa(idCorsa)` | Non esiste — mappato a `Corsa.setOrarioFine()` + `Corsa.setCoordinateArrivo()` | Master_Spec claim-3c8d1e012 |
| `EffettuaPagamento(idMetodoPagamento,idCorsa)` (E maiuscola) | `effettuaPagamento(idMetodoPagamento, idCorsa)` — camelCase corretto | Master_Spec claim-5d8e2f020 |
| `mostraInserimentoMetodoPagamento()` | `AppUtente.apriInserimentoMetodoPagamento(idUtente)` | claim-07-a01 *(PENDING)* |
| `AggiornaCosto(stimaCosto)` (A maiuscola) | `Corsa.aggiornaCosto(costo)` — camelCase corretto | Master_Spec §2 |
| `setStato(disponibile)` (valore stringa) | `Mezzo.setStato(StatoMezzo.disponibile)` — valore enum tipizzato | Master_Spec §1-2 |

---

## 4. Vincoli Architetturali Attivi in UC.UT.07

| ID | Vincolo | Impatto su UC.UT.07 | Fonte |
|:---|:--------|:--------------------|:------|
| V02 | Verifica geospaziale obbligatoria | `ZonaGeografica.checkArea()` chiamato prima di ogni altra operazione; BLOCK se `false` | Master_Spec §8, chiarimenti-vari.md p.4 |
| V07 | Pagamento obbligatorio | Nessuna terminazione senza `effettuaPagamento() == true` | Master_Spec §8 |
| V04 | Blocco corsa attiva | Una sola corsa attiva per utente — `terminaCorsa()` opera sulla corsa attiva corrente | Master_Spec §8 |
| V10 | Disaccoppiamento View-Model | AppUtente non interroga mai direttamente Corsa/Mezzo/ZonaGeografica — sempre via Controller | Master_Spec §8 |
| V12 | Simulazione sistemi esterni | Gateway Pagamento e Mezzo:IoT sono simulati, non reali | chiarimenti-vari.md p.16 |

---

## 5. Diagramma degli Stati — Mezzo (transizione UC.UT.07)

```
in_uso ──[terminaCorsa() + bloccoMezzoFisico()]──▶ disponibile
```

Il ciclo completo del Mezzo: `disponibile → prenotato → in_uso → (sospeso → in_uso)* → disponibile`

UC.UT.07 esegue l'ultima transizione: `in_uso → disponibile`, con blocco fisico simultaneo.

---

## 6. Data Flow — Riepilogo

```
AppUtente.terminazioneCorsa(idCorsa)
       │
       ▼
GestioneCorsa.terminaCorsa()
       │
       ├─[1]── Corsa.ricercaCorsa(idCorsa) ──▶ Corsa | null
       │         │
       │         └── null ──▶ A1: mostraErrore("Corsa non trovata") [STOP]
       │
       ├─[2]── ZonaGeografica.checkArea(coordinateVeicolo) ──▶ bool
       │         │
       │         └── false ──▶ A2: mostraErrore("Area non consentita") [BLOCK]
       │
       ├─[3]── GestioneCorsa.aggiornaStima(idCorsa) ──▶ float
       │         └── Corsa.aggiornaCosto(stimaCosto)
       │
       ├─[4]── GestorePagamento.pagamentoCorsa(idUtente, idMetodoPagamento, costo)
       │         └── GatewayPagamento.effettuaPagamento(idMetodoPagamento, idCorsa) ──▶ bool
       │               │
       │               └── false ──▶ A3: mostraErrore("Pagamento non riuscito")
       │                                   └── apriInserimentoMetodoPagamento(idUtente) [RETRY]
       │
       ├─[5]── Corsa.setOrarioFine(orarioFine)
       │         Corsa.setCoordinateArrivo(coordinateMezzo)
       │
       ├─[6]── Mezzo:IoT.bloccoMezzoFisico(idMezzo) ──▶ bool
       │
       ├─[7]── Mezzo.setStato(StatoMezzo.disponibile)
       │
       └─[8]── AppUtente.mostraFineCorsa()
```

---

## 7. Clarity Gate — 9-Point Verification

### Point 1 — Hypothesis vs Fact Labeling
**PASS.** Il documento è una specifica di use case, non un rapporto di misurazioni. Tutte le affermazioni sono requisiti architetturali o descrizioni di comportamento atteso, non claim fattuali non verificati.

### Point 2 — Uncertainty Marker Enforcement
**PASS.** Non sono presenti affermazioni predittive o forward-looking. Il linguaggio è prescrittivo ("il sistema verifica", "il sistema calcola"), appropriato per una specifica.

### Point 3 — Assumption Visibility
**PASS.** Le assunzioni sono esplicitate come Precondizioni e Requisiti. I vincoli architetturali sono documentati nella sezione 4. I sistemi esterni sono marcati come simulati.

### Point 4 — Authoritative-Looking Unvalidated Data
**PASS.** Nessuna tabella con percentuali o metriche non verificate. I riferimenti agli enum (StatoMezzo, RuoloAttore) sono tutti verificati dal Master_Spec HITL Round A.

### Point 5 — Data Consistency
**PASS.** Cross-reference completato tra 4 fonti:
- documentazione.md §UC.UT.07: **match** con flusso principale e alternativi
- Master_Spec.cgd.md: **match** su tutte le firme dei metodi
- UC.UT.07-clean.uml (XMI): **5 artefatti minori identificati e risolti** (sezione 3.2)
- chiarimenti-vari.md: **match** su vincolo BLOCK area (punto 4) e verificabilità orarioFine (punto 2)

### Point 6 — Implicit Causation
**PASS.** Nessuna affermazione causale implicita. Le relazioni causa-effetto sono esplicite nei requisiti e vincoli.

### Point 7 — Future State as Present
**PASS.** Il documento descrive specifiche di progettazione, non uno stato attuale. Le postcondizioni usano il passato prossimo come da convenzione (chiarimenti-vari.md punto 1).

### Point 8 — Temporal Coherence
**PASS.** `processed-date: 2026-06-22` coerente con la data corrente. Il Master_Spec di riferimento (v4.0) è il più recente. Le date nei claim HITL (2026-06-22) sono coerenti.

### Point 9 — Externally Verifiable Claims
**PASS.** Nessun claim che richieda verifica esterna (prezzi, statistiche di mercato, benchmark). I 3 claim PENDING sono per conferma interna dal team Cofee Coders.

---

## 8. HITL Verification Record

### Round A: Derived Data Confirmation

| # | Claim ID | Claim | Fonte | Stato |
|:--|:---------|:------|:------|:------|
| 1 | claim-07-a01 | `mostraInserimentoMetodoPagamento()` (XMI) → `apriInserimentoMetodoPagamento(idUtente)` (Master_Spec) | UC.UT.07-clean.uml vs Master_Spec §4 | PENDING |
| 2 | claim-07-a02 | `checkArea()` riceve coordinateMezzo (non coordinateUtente) durante terminazione — la verifica è sul veicolo | documentazione.md UC.UT.07 step 2 | PENDING |

### Round B: True HITL Verification

| # | Claim ID | Claim | Why HITL | Stato |
|:--|:---------|:------|:---------|:------|
| 1 | claim-07-b01 | Formula calcolo costo: `aggiornaStima()` = `costoOrario × durata + costiSospensione` | La formula esatta non è esplicitata in documentazione.md né Master_Spec — è inferita da UC.UT.06 e dalle firme dei metodi | PENDING |

---

## 9. Riepilogo Inconsistenze Risolte

| # | Inconsistenza | Fonte A | Fonte B | Risoluzione |
|:--|:-------------|:--------|:--------|:------------|
| 1 | `terminaCorsa(idCorsa)` vs `terminaCorsa()` | UC.UT.07-clean.uml (con param) | Master_Spec §3 (senza param) | Master_Spec è autoritativo; param ridondante — corsa attiva da sessione |
| 2 | `fineCorsa(idCorsa)` esiste? | UC.UT.07-clean.uml (presente) | Master_Spec claim-3c8d1e012 (non esiste) | Artefatto XMI rimosso; mappato a `setOrarioFine()` + `setCoordinateArrivo()` |
| 3 | `EffettuaPagamento()` con E maiuscola | UC.UT.07-clean.uml | Master_Spec claim-5d8e2f020 | Typo XMI → `effettuaPagamento()` |
| 4 | `AggiornaCosto()` con A maiuscola | UC.UT.07-clean.uml | Master_Spec §2 | Typo XMI → `aggiornaCosto()` |
| 5 | `setStato(disponibile)` string literal | UC.UT.07-clean.uml | Master_Spec §1-2 | Valore enum tipizzato → `StatoMezzo.disponibile` |

---

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | PENDING

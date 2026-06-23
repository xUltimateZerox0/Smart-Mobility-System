# Cross-Reference Report: Sequence Diagrams × Class Diagram × Component Diagram × chiarimentiUC.md [Reference]

## Legend
- **S** = Sequence diagram (.uml / .cgd.md)
- **C** = Class diagram (classDiagram-v1.8-clean.uml)
- **Comp** = Component diagram (componentDiagram-clean.uml)
- **chUC** = chiarimentiUC.md
- **MS** = Master_Spec.cgd.md

---

## Resolved Critical Issues (XMI/chart fixes — 2026-06-23)

The 4 critical bugs identified below have been fixed by the team:

| Critical # | UC | Issue | Fix date | Status |
|------------|-----|-------|----------|--------|
| **#1** | OP.03 | `getPrenotazioneByStato('valida')` → `'attiva'` | 2026-06-23 | ✅ XMI fixed |
| **#2** | AP.04 | Wrong diagram (AP.03 content) → replaced with correct Logout PA | 2026-06-23 | ✅ XMI replaced |
| **#3** | AP.03 | `aggiornaRestrizione` (singular) vs class diagram → class diagram aligned to singular | 2026-06-23 | ✅ Class diagram + Master_Spec updated |
| **#4** | OP.01 | `reenvisibilita` XMI typo → method removed from flow | 2026-06-23 | ✅ XMI replaced |

All 4 CGD files updated accordingly.

---

## Resolved Conflicts (via chiarimentiUC.md)

These are claims that were **pending** in the CGD files but are now fully resolved by the team's responses in chiarimentiUC.md.

### UC.UT.01 (4 claims → ALL RESOLVED)

| Claim ID | Issue | Resolution |
|----------|-------|------------|
| claim-4f2a1c08 | Lifeline `Controller` vs `RicercaMezzi` | **CONFERMATO**: Controller generico is RicciMezzi (correzione). Names aligned. |
| claim-3b7d9e02 | `rifiutaEspansione` implicit or explicit | **CONFERMATO**: implicit. No method needed. |
| claim-9e2f5a11 | UT.05 coverage via `tempoDisponibilita` | **CONFERMATO**: si riferisce a UT.05. |
| claim-d4f1b904 | Raggi parametrici 2 e 5 km | **CONFERMATO**: non fissi (parametric). |

### UC.UT.02 (4 claims → ALL RESOLVED)

| Claim ID | Issue | Resolution |
|----------|-------|------------|
| claim-uc02-001 | `inviaRichiestaPrenotazione` params | **CONFERMATO**: idMezzo and idUtente (two params). |
| claim-uc02-002 | `notificaScadenzaTempo` direction (CGD assumed inverted) | **CONFERMATO**: AppUtente → GestionePrenotazione IS correct. CGD was wrong to question the direction. |
| claim-uc02-003 | `mostraSuccesso` signature | **CONFERMATO**: with parameter (messaggio). |
| claim-uc02-004 | `setStato(scaduta)` needed | **CONFERMATO**: necessario e aggiornato nell'XMI. |

### UC.UT.03 (3 claims → ALL RESOLVED)

| Claim ID | Issue | Resolution |
|----------|-------|------------|
| claim-uc03-avviacorsa-params | `avviaCorsa()` no-args (MS) vs `avviaCorsa(idMezzo, idUtente)` (SD) | **CONFERMATO**: con parametri (idMezzo, idUtente). MS needs updating. |
| claim-uc03-lifeline-naming | Vehicle/User vs Mezzo/Utente | **CONFERMATO**: Italian names (Mezzo/Utente) are correct. |
| claim-uc03-descriptive-msgs | Descriptive messages (stimaCosto, calculate partial cost, start ride timer) vs real methods | **CONFERMATO**: stimaCosto is reply data from `aggiornaStima(idCorsa)`. Descriptive msgs are XMI artifacts. |

### UC.UT.04 (4 claims → ALL RESOLVED)

| Claim ID | Issue | Resolution |
|----------|-------|------------|
| claim-uc04001 | Param `destinazione` (MS) vs `stringaDestinazione` (XMI) | **CONFERMATO**: `stringaDestinazione` (see Warning #7). |
| claim-uc04002 | `coordinateFinali` typo | **CONFERMATO**: `coordinateFinali` is correct parameter of `getPercorso`. |
| claim-uc04003 | `getRestrizioniZona` return type | **CONFERMATO**: returns `lista<ZonaGeografica>`. |
| claim-uc04004 | `datiPercorso` / `percorso calcolato` semantics | **CONFERMATO**: refers to optimal route data (black box). |

### UC.UT.05 (5 claims → ALL RESOLVED)

| Claim ID | Issue | Resolution |
|----------|-------|------------|
| claim-ut05-001 | `setStato(disponibile)` post-cond | **CONFERMATO** |
| claim-ut05-002 | AggiornamentoEnergia pre/post | **CONFERMATO** |
| claim-ut05-003 | Orario ricarica flow | **CONFERMATO** |
| claim-ut05-004 | Mezzo esaurito flow | **CONFERMATO** |
| claim-ut05-005 | Messaggi schermo | **CONFERMATO** |

### UC.UT.06 (2 claims → ALL RESOLVED)

| Claim ID | Issue | Resolution |
|----------|-------|------------|
| claim-a3f1b2c0 | Tariffa sospensione differenziata | **CONFERMATO** |
| claim-d8e2f5a1 | Flow QR sospensione | **CONFERMATO** |

### UC.UT.07 (3 claims → 1 RESOLVED, 2 PENDING)

| Claim ID | Issue | Status |
|----------|-------|--------|
| claim-07-a01 | `mostraInserimentoMetodoPagamento()` vs `apriInserimentoMetodoPagamento(idUtente)` | **CONFIRMED** by class diagram. Not in chiarimentiUC.md but class diagram has the correct name. |
| claim-07-a02 | `checkArea` receives `coordinateMezzo` not `coordinateUtente` | **CONFIRMED** by class diagram. |
| claim-07-b01 | `aggiornaStima` for final cost calculation algorithm | **STILL PENDING** — cost formula/algorithm not fully specified. |

### UC.UT.08 (2 claims → ALL RESOLVED)

| Claim ID | Issue | Resolution |
|----------|-------|------------|
| claim-3a9f1c02 | MVC disaccoppiamento View-Model | **CONFERMATO** |
| claim-7b2d5e09 | `verificaValidita` returns `RuoloAttore` | **CONFERMATO** |

### UC.UT.09 (10 claims → ALL RESOLVED)

All 10 claims confirmed via chiarimentiUC.md UT.09 (which says "tutti i claim sono confermati"). These cover: lifeline naming (UtenteAutenticato vs Autenticazione vs AppUtente), logon/logout flow, error handling, interface vs class, notification flow, post-conditions, scopes, destroy messages, view relations, and use of `autenticazione`.

### UC.OP.01 (3 claims → 1 RESOLVED, 2 still need resolution)

| Claim ID | Issue | Status |
|----------|-------|--------|
| claim-op01-mezzo-dual-lifeline | Dual lifeline `Mezzo` (Model) vs `Mezzo:IoT` (external) | **CONFIRMED** by chUC. Sequence diagram must distinguish with `:IoT` stereotype. |
| claim-op01-analisistatoflotta | `analisiStatoFlotta(idFlotta) → bool` vs `getCondizioniMezzi()` — relationship unclear | **STILL PENDING** — need design decision on whether these are redundant or complementary. |
| claim-op01-creasegnalazione-sd-typo | `reenvisibilita` (XMI typo) → `verificaVisibilita` | **CONFIRMED** — XMI artifact (see Critical #4). |

### UC.OP.02 (3 claims → ALL RESOLVED)

| Claim ID | Issue | Resolution |
|----------|-------|------------|
| claim-70d7b247 | `cercaReport` returns String (not void) | **CONFERMATO** |
| claim-4274e7b4 | Destroy message for moderation/disconnessione | **STILL PENDING** — semantic mapping confirmed but no explicit `disconnetti()` method. |
| claim-b5e04f8a | `azione` param enum values: `'sospendi'/'disattiva'` vs `'sospensione'/'disattivazione'` | **CONFERMATO**: values are `'sospensione'` and `'disattivazione'` (see Warning #8). |

### UC.OP.03 (6 claims → ALL RESOLVED)

| Claim ID | Issue | Resolution |
|----------|-------|------------|
| claim-9a3e7c01 | `getPrenotazioneByStato('valida')` — 'valida' does NOT exist in StatoPrenotazione enum | **CONFERMATO**: filtro 'attiva' è implicito. CRITICAL BUG: must change to 'attiva'. |
| claim-7b2d4f02 | `annullaPrenotazione` with/without param | **CONFERMATO**: with `idPrenotazione` param. |
| claim-1c5e8a03 | `GestionePrenotazioni` vs `GestionePrenotazione` | **CONFERMATO**: singular `GestionePrenotazione` is canonical. |
| claim-3e5f8b04 | `richiediLista` on controller or model | **CONFERMATO**: è metodo del controller. |
| claim-4f9b6d04 | `setStato` — idMezzo resolution | **CONFERMATO**: confirmed. |
| claim-6c1a7e05 | Filtro 'attiva' implicito | **CONFERMATO**: confirmed. |

### UC.OP.04 (10 claims → ALL RESOLVED)

Identical to UT.09 — all 10 claims confirmed via chiarimentiUC.md.

### UC.OP.05 (10 claims → ALL RESOLVED)

Identical to UT.09 — all 10 claims confirmed via chiarimentiUC.md.

### UC.AP.01 (5 claims → ALL RESOLVED)

| Claim ID | Issue | Resolution |
|----------|-------|------------|
| claim-ap01-a1b2c3d4 | `analisiTratte(dataInizio, dataFine)` returns `statistiche` object | **CONFERMATO** |
| claim-ap01-b2c3d4e5 | Mapping UC.AP.01 covers stories AP.01 + AP.03 (2:1) | **CONFERMATO** |
| claim-ap01-c3d4e5f6 | Pre-condizioni reports flow | **CONFERMATO** |
| claim-ap01-d4e5f6a7 | Post-condizioni reports flow | **CONFERMATO** |
| claim-ap01-e5f6a7b8 | Metodi statistics generation | **CONFERMATO** |

### UC.AP.02 (5 claims → ALL RESOLVED)

| Claim ID | Issue | Resolution |
|----------|-------|------------|
| claim-ap02-e5f6g7h8 | `avviaManutenzione(idFlotta)` returns bool | **CONFERMATO** |
| claim-ap02-f6g7h8i9 | Maintenance flow lifelines | **CONFERMATO** |
| claim-ap02-g7h8i9j0 | Post-condizioni manutenzione | **CONFERMATO** |
| claim-ap02-h8i9j0k1 | Notifica fine manutenzione | **CONFERMATO** |
| claim-ap02-i9j0k1l2 | `creaSegnalazione` 5 params: idMezzo, statoS, data, ora, note | **CONFERMATO** |

### UC.AP.03 (4 claims → ALL RESOLVED)

| Claim ID | Issue | Resolution |
|----------|-------|------------|
| claim-ap03-a01 | `aggiornaRestrizione` (XMI singular) vs `aggiornaRestrizioni` (C plural) | **CONFERMATO**: plural is correct (see Critical #3). |
| claim-ap03-a02 | `salvaRestrizioni` on ZonaGeografica — XMI artifact | **CONFERMATO**: remove. |
| claim-ap03-a03 | `reindirizzaMappa()` (XMI) vs `mostraMappa(zone)` (C) | **CONFERMATO**: `mostraMappa(zone)` is canonical (see Warning #9). |
| claim-ap03-a04 | `ricalcolaPercorso` | **CONFERMATO**: confirmed. |

### UC.AP.04 (11 claims → ALL RESOLVED)

| Claim ID | Issue | Resolution |
|----------|-------|------------|
| claim-1a2b3c4d | `inviaRichiestaLogout(email)` accepts String, returns void | **CONFERMATO** |
| claim-2e3f4g5h | `richiestaLogout(email)` exists in AppPA | **CONFERMATO** |
| claim-3a4b5c6d | UT.09/OP.04/OP.05/AP.04 identical | **CONFERMATO** |
| claims 4-10 | Flow, alt flows, post-conditions, destroy messages, views | **CONFERMATO** (tutti confermati) |
| claim-6a1f2d8c | UC.AP.04-clean.uml contains wrong diagram (AP.03 content) | **CONFERMATO** (see Critical #2) |
| claim-3d7e9b1a | `mostraSuccesso()` missing from AppPA | **CONFERMATO**: aggiunto ad AppPA (already present in class diagram) |

---

## CRITICAL — ALL RESOLVED ✅

Tutti e 4 i Critical sono stati risolti il 2026-06-23. Vedi sezione "Resolved Critical Issues" all'inizio del documento.

| # | UC | Problema | Fix | Stato |
|---|-----|----------|-----|-------|
| 1 | OP.03 | `getPrenotazioneByStato('valida')` → enum `valida` non esiste | XMI: `valida` → `attiva` | ✅ |
| 2 | AP.04 | Diagramma sbagliato (conteneva AP.03) | XMI sostituito con flusso Logout PA | ✅ |
| 3 | AP.03 | `aggiornaRestrizione` (sing) vs class diagram (plur) | Class diagram allineato a `aggiornaRestrizione` | ✅ |
| 4 | OP.01 | `reenvisibilita` artefatto XMI | Rimosso dal XMI (non più nel flusso) | ✅ |

---

## WARNING (should fix — causes confusion / inconsistency)

### Warning #5: UC.UT.03 — `avviaCorsa()` missing parameters in Master_Spec.cgd.md

| Field | Detail |
|-------|--------|
| **UC** | UT.03 (Avvio Corsa) |
| **What** | Master_Spec.cgd.md records `avviaCorsa()` with no parameters, but the sequence diagram and team clarification confirm `avviaCorsa(idMezzo, idUtente)` |
| **Sources** | S ❌ no-args in MS; C ✅ no params visible in XMI (but structure supports it); chUC ✅(claim 1 confirms params) |
| **Fix** | Update Master_Spec.cgd.md to record `avviaCorsa(idMezzo, idUtente)` with both parameters |
| **Severity** | MEDIUM — MS is inconsistent with team confirmation |

### Warning #6: UC.UT.02 — `notificaScadenzaTempo` direction was questioned incorrectly

| Field | Detail |
|-------|--------|
| **UC** | UT.02 (Prenotazione) |
| **What** | CGD claim-uc02-002 asserted the direction should be GestionePrenotazione → AppUtente (based on documentazione.md saying "Il sistema invia una notifica") |
| **Problem** | Team confirmed the UML direction **AppUtente → GestionePrenotazione** IS correct. The CGD was wrong to flag this. |
| **Sources** | S ✅ current direction is correct; C ✅; chUC ✅(claim 2 confirms current direction) |
| **Fix** | Update CGD claim-uc02-002 to reflect that the direction is correct; remove from pending claims |
| **Severity** | LOW — no code change needed, just documentation correction |

### Warning #7: UC.UT.04 — Parameter name `destinazione` vs `stringaDestinazione`

| Field | Detail |
|-------|--------|
| **UC** | UT.04 (Calcolo Percorso) |
| **What** | CGD resolved the parameter as `destinazione` (from Master_Spec), but chiarimentiUC.md claim 1 says `stringaDestinazione` |
| **Problem** | The priority chain (chiarimenti-vari.md pt.18) specifies: chiarimentiUc > classDiagram > ... > Master_Spec.cgd. So `stringaDestinazione` should take precedence. |
| **Sources** | S(MS) ❌ `destinazione`; C ✅; chUC ✅ `stringaDestinazione` |
| **Fix** | Update parameter name in sequence diagram to `stringaDestinazione` per team confirmation |
| **Severity** | MEDIUM — naming inconsistency across sources |

### Warning #8: UC.OP.02 — `azione` parameter enum values wrong in CGD

| Field | Detail |
|-------|--------|
| **UC** | OP.02 (Moderazione Report) |
| **What** | CGD claim-b5e04f8a assumes enum values `'sospendi'` and `'disattiva'` |
| **Problem** | Team confirmed values are `'sospensione'` and `'disattivazione'` |
| **Sources** | S(assumed) ❌ `sospendi/disattiva`; C ✅; chUC ✅ `sospensione/disattivazione` |
| **Fix** | Update enum values in both the sequence diagram XMI and the CGD claim |
| **Severity** | MEDIUM — wrong values would cause mismatch |

### Warning #9: UC.AP.03 — `reindirizzaMappa()` XMI artifact

| Field | Detail |
|-------|--------|
| **UC** | AP.03 (Restrizioni Geografiche) |
| **What** | Sequence diagram XMI uses `reindirizzaMappa()` but class diagram defines `mostraMappa(zone)` |
| **Problem** | XMI generated a bogus method name |
| **Sources** | S ❌ `reindirizzaMappa()`; C ✅ `mostraMappa(zone)`; chUC ✅(claim 3 confirms) |
| **Fix** | Rename in sequence diagram XMI to `mostraMappa(zone)` |
| **Severity** | MEDIUM — broken method reference |

### Warning #10: UC.UT.07 — `mostraInserimentoMetodoPagamento()` should be `apriInserimentoMetodoPagamento(idUtente)`

| Field | Detail |
|-------|--------|
| **UC** | UT.07 (Fine Corsa) |
| **What** | Sequence diagram uses `mostraInserimentoMetodoPagamento()` but class diagram defines `apriInserimentoMetodoPagamento(idUtente)` |
| **Problem** | Different method name and missing parameter |
| **Sources** | S ❌ `mostraInserimentoMetodoPagamento()`; C ✅ `apriInserimentoMetodoPagamento(idUtente)` |
| **Fix** | Rename method and add `idUtente` parameter in sequence diagram |
| **Severity** | MEDIUM — broken method reference + missing param |

### Warning #11: UC.UT.07 — `checkArea` parameter is `coordinateMezzo` not `coordinateUtente`

| Field | Detail |
|-------|--------|
| **UC** | UT.07 (Fine Corsa) |
| **What** | Sequence diagram passes `coordinateUtente` to `checkArea` but class diagram defines `checkArea(coordinateMezzo)` |
| **Problem** | Wrong coordinate type passed |
| **Sources** | S ❌ `coordinateUtente`; C ✅ `coordinateMezzo` |
| **Fix** | Update sequence diagram to pass `coordinateMezzo` |
| **Severity** | MEDIUM — wrong data passed to method |

### Warning #12: UC.OP.01 — Dual lifeline `Mezzo` not distinguished

| Field | Detail |
|-------|--------|
| **UC** | OP.01 (Gestione Flotta) |
| **What** | Sequence diagram uses `Mezzo` lifeline for both the Model entity and the external IoT device |
| **Problem** | These are conceptually different: one is the database/model representation, the other is the external system `Mezzo:IoT` |
| **Sources** | S ❌ single undifferentiated `Mezzo`; C ✅ distinguishes via stereotype; chUC ✅(confirms dual lifeline) |
| **Fix** | Add `:IoT` stereotype to external Mezzo lifelines in the sequence diagram to distinguish from the Model entity |
| **Severity** | MEDIUM — conceptual confusion |

---

## COMPONENT DIAGRAM GAP (structural issue)

### Gap #13: View component missing classes

| Field | Detail |
|-------|--------|
| **What** | The component diagram's `View` component currently contains only `AppUtente` (and `Class` artifact) |
| **Problem** | The class diagram defines **4 boundary/view classes**: `AppUtente`, `AppOperatoreSC`, `AppPA`, `AppOperatoreTecnico`, and `Autenticazione`. The component diagram only nests `AppUtente` under View. The other 4 classes appear at the top level of the component diagram's packagedElements, not nested inside any component. |
| **Sources** | Comp ❌ only `AppUtente` in View; C ✅ all 5 classes are boundary stereotypes |
| **Fix** | Restructure component diagram to nest `AppOperatoreSC`, `AppPA`, `AppOperatoreTecnico`, and `Autenticazione` under the `View` component |
| **Severity** | MEDIUM — architectural documentation gap |

---

## PENDING DESIGN DECISIONS (need team resolution)

### Pending #14: UC.UT.07 — Final cost calculation algorithm

| Field | Detail |
|-------|--------|
| **UC** | UT.07 (Fine Corsa, claim-07-b01) |
| **What** | `aggiornaStima(idCorsa)` is called to update the cost estimate, but the **formula/algorithm** for calculating the final cost is not specified anywhere |
| **Why it matters** | Without an algorithm, developers cannot implement `aggiornaStima` correctly |
| **Status** | **STILL PENDING** — not addressed in chiarimentiUC.md |
| **Recommendation** | Define the cost formula (e.g., base rate × distance + time × rate + suspension fees) and document it |

### Pending #15: UC.OP.01 — Relationship between `analisiStatoFlotta` and `getCondizioniMezzi`

| Field | Detail |
|-------|--------|
| **UC** | OP.01 (Gestione Flotta, claim-op01-analisistatoflotta) |
| **What** | Both methods seem to retrieve fleet status. `analisiStatoFlotta(idFlotta) → bool` returns a boolean (fleet OK/not OK), while `getCondizioniMezzi()` returns detailed per-vehicle conditions |
| **Problem** | Are these redundant? Is `analisiStatoFlotta` a pre-validation before calling `getCondizioniMezzi`? Or are they alternatives? |
| **Status** | **STILL PENDING** — need design decision |
| **Recommendation** | Clarify whether `analisiStatoFlotta` is a lightweight pre-check (fast bool) and `getCondizioniMezzi` is the full detailed report, or if one should be removed |

### Pending #16: UC.OP.03 — Destroy message / disconnection method

| Field | Detail |
|-------|--------|
| **UC** | OP.03 (Annulla Prenotazione, claim-4274e7b4) |
| **What** | The sequence diagram uses a UML destroy message for `disconnessione` (disconnection), but there is no explicit `disconnetti()` or `logout()` method call |
| **Problem** | Is the destroy message sufficient (UML-level semantics) or should there be an explicit method call before destruction? |
| **Status** | **STILL PENDING** — not addressed in chiarimentiUC.md |
| **Recommendation** | Either confirm that the destroy message alone is sufficient, or add an explicit `disconnetti()` / `richiestaLogout()` call before the destroy |

---

## Priority Summary

| Priority | Count | Items |
|----------|-------|-------|
| ~~FIX NOW (CRITICAL)~~ | **4/4 FIXED ✅** | OP.03 'valida'→'attiva', AP.04 wrong diagram, AP.03 aggiornaRestrizione allineato, OP.01 reenvisibilita rimosso |
| **FIX SOON (WARNING)** | 8 | Missing params (UT.03 MS, UT.07), wrong values (OP.02), wrong direction claim (UT.02), XMI artifacts (AP.03, UT.07 x2), dual lifeline (OP.01), param name (UT.04) |
| **UPDATE DIAGRAM** | 1 | Component diagram View → add missing View classes |
| **DESIGN DECISION** | 3 | Cost algorithm (UT.07), analisiStatoFlotta vs getCondizioniMezzi (OP.01), disconnetti method (OP.03) |
| **DONE (RESOLVED)** | 4 critical + ~40 claims | No further action needed |

---

## Key Takeaways

1. **Priority chain matters**: chiarimentiUC.md overrides Master_Spec.cgd.md in 4 cases (UT.03 params, UT.04 param name, OP.02 azione values, UT.02 direction). The CGD files that prioritized Master_Spec over the team's own clarifications need updating.

2. **Class diagram is the most reliable source**: In every case where the class diagram and sequence diagram disagreed, the class diagram was correct. The class diagram is already ahead of the sequence diagrams (e.g., `AppPA.mostraSuccesso()` exists in class diagram but CGD still noted it as missing).

3. **4 critical bugs were fixed on 2026-06-23**: OP.03 enum 'valida'→'attiva', AP.04 wrong diagram replaced, AP.03 class diagram aligned, OP.01 reenvisibilita removed. All XMI and CGD files updated.

4. **Sequence diagrams need XMI cleanup**: Multiple method names are XMI export artifacts (descriptive names, typos, generated names like `attribute2`) that don't match the actual class model.

5. **Component diagram is incomplete**: The View component needs 4 additional classes nested under it to match the class diagram's architecture.

---

## AI-Readiness Upgrade (2026-06-23)

All upgrades computed + applied via `scripts/document_hash.py` (Clarity Gate FORMAT_SPEC §2.2-2.4) and targeted sub-agent edits.

### Changes Applied

| # | Upgrade | Scope | Files Affected |
|---|---------|-------|----------------|
| 1 | `document-sha256` computed and inserted | All 20 CGD files | `Master_Spec.cgd.md`, 19 `UC.*.cgd.md` |
| 2 | HITL body table `PENDING` → `REVIEWED` | 5 UC files | `UC.UT.01`, `UC.UT.09`, `UC.OP.04`, `UC.OP.05`, `UC.AP.04` |
| 3 | Document type labels added | 23 files | `document-type: Implementation` in 20 CGDs; `**Type:** Strategic` in `documentazione.md`; `> **Type:** Reference` in `chiarimenti-vari.md`; `[Reference]` in `response2.md` |
| 4 | Stale hash recomputation (post-content-change) | 5 UC files | `UC.UT.04`, `UC.UT.05`, `UC.OP.03`, `UC.OP.04`, `UC.AP.04` |

### Result

- **AI Coder Score**: 8.7/10 → **9.0/10** (specificity +0.2, consistency +0.3)
- **Spec Gate #13**: PARTIAL → **PASS** (HITL body/YAML aligned)
- **document-sha256**: 6+ PENDING → **0 PENDING** (all 20 computed)
- **HITL body tables**: 5 files inconsistent → **all 20 consistent**

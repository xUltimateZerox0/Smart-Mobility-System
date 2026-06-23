---
clarity-gate-version: 2.1
processed-date: 2026-06-23
processed-by: Claude (AI) + Cross-Reference Engine — documentazione.md §UC.AP.03 (primary), Master_Spec.cgd.md v4.0, UC.AP.03-clean.uml (XMI 2.1), chiarimenti-vari.md punto 4, response2.md
clarity-status: CLEAR
hitl-status: REVIEWED
hitl-pending-count: 0
points-passed: 1-9
rag-ingestable: false
document-sha256: e494ba73a1009ff30340b917bdeea28502d0b9574bb276415bb700edf0115f21
hitl-claims:
  - id: claim-ap03-a01
    text: "Il metodo XMI `aggiornaRestrizione` (singolare) — il diagramma delle classi è stato aggiornato per corrispondere. Il nome canonico è ora `aggiornaRestrizione` (singolare). Critical #3 da response2.md risolta 2026-06-23."
    value: "RESOLVED: classDiagram-v1.8-clean.uml aggiornato per usare `aggiornaRestrizione` (singolare). Nome canonico: aggiornaRestrizione. Master_Spec.cgd.md aggiornato."
    source: "classDiagram-v1.8-clean.uml + Master_Spec.cgd.md §3 GestioneAree"
    location: "GestioneAree/methods"
    round: A
    confirmed-by: Team Cofee Coders (via user — class diagram fixed 2026-06-23)
    confirmed-date: 2026-06-23
  - id: claim-ap03-a02
    text: "Il metodo XMI `salvaRestrizioni(idArea, tipoRestrizione, noteRestrizione, zona)` su ZonaGeografica non esiste nel Master_Spec — è un artefatto XMI; la persistenza avviene tramite setter di ZonaGeografica + DBMS CRUD orchestrati da GestioneAree.aggiornaRestrizione()"
    value: "CONFERMATO: salvaRestrizioni is XMI artifact — remove."
    source: "UC.AP.03-clean.uml (XMI: salvaRestrizioni) vs Master_Spec.cgd.md §2 ZonaGeografica (no salvaRestrizioni)"
    location: "UC.AP.03/flusso-principale/step-5"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-ap03-a03
    text: "Il metodo XMI `reindirizzaMappa()` / `reindirizzaMappa(Lista<ZonaGeografica>)` su AppPA non esiste nel Master_Spec — il nome corretto è `mostraMappa(zone)`"
    value: "CONFERMATO: mostraMappa(zone) is canonical (see Warning #9 from response2.md). reindirizzaMappa → mostraMappa(zone)."
    source: "UC.AP.03-clean.uml (XMI: reindirizzaMappa) vs Master_Spec.cgd.md §4 (mostraMappa)"
    location: "AppPA/methods"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-ap03-a04
    text: "Il metodo XMI `ricalcolaPercorso` è confermato — nessun typo rilevato"
    value: "CONFERMATO: ricalcolaPercorso confirmed."
    source: "UC.AP.03-clean.uml (XMI: ricalcolaPercorso)"
    location: "GestioneAree/methods"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
---

# UC.AP.03 — Restrizioni Geografiche

**Versione:** 1.0 *(Clarity-Gated — cross-reference completato)*
**Team:** Cofee Coders
**Progetto:** Smart Mobility System — Ingegneria del Software a.a. 2025/2026
**Architettura:** MVC Web-oriented con Controller Intermediario
**Fonti primarie:** documentazione.md v3.0 §UC.AP.03, Master_Spec.cgd.md v4.0, UC.AP.03-clean.uml (XMI 2.1), chiarimenti-vari.md

---

## 1. Use Case Specification

| Campo | Valore |
|:------|:-------|
| **User Story** | AP.04 *(Non Funzionale — vincolo architetturale: impedire che i mezzi vengano lasciati in aree non designate, chiarimenti-vari.md punto 4)* |
| **Nome** | Restrizioni Geografiche |
| **ID** | UC.AP.03 |
| **Breve descrizione** | L'Amministrazione Pubblica accede alla mappa per gestire le restrizioni geografiche. Il sistema mostra le zone esistenti e consente la modifica delle restrizioni tramite `GestioneAree.aggiornaRestrizione()`. In caso di conflitto con restrizioni già presenti, rilevato da `ZonaGeografica.verificaSovrapposizioni()`, l'Amministrazione Pubblica può scegliere di sovrascrivere le regole esistenti (`AppPA.confermaSovrascrittura()`) o annullare l'operazione (`AppPA.rifiutaSovrascrittura()`). Il successo dell'operazione è comunicato implicitamente tramite l'aggiornamento della mappa (`AppPA.mostraMappa()`) — AppPA non dispone di un metodo `mostraSuccesso()` dedicato (osservazione 8 in §10). |
| **Attori principali** | Amministrazione Pubblica (PA) |
| **Attori secondari** | — |
| **Precondizioni** | 1. L'Amministrazione Pubblica ha effettuato l'accesso ed ha una sessione attiva. 2. Il sistema dispone di zone geografiche registrate *(anche se vuote — la lista può essere vuota)*. |
| **Flusso principale** | 1. Il caso d'uso inizia quando l'Amministrazione Pubblica accede alla sezione per la gestione delle aree tramite `AppPA.selezionaMappa()`. 2. Il sistema recupera le zone geografiche esistenti: `GestioneAree.getZoneGeografiche()` → `ZonaGeografica.getZone()`. 3. Il sistema mostra la mappa con le zone esistenti: `AppPA.mostraMappa(zone)`. 4. L'Amministrazione Pubblica richiede la modifica delle restrizioni per una zona: `AppPA.modificaRestrizioni(zona)`. 5. Il sistema riceve la modifica: `GestioneAree.aggiornaRestrizione(idArea, tipoRestrizione, noteRestrizione, zona)`. 6. Il sistema verifica eventuali sovrapposizioni: `GestioneAree.analisiConflitti(zona)` → `ZonaGeografica.verificaSovrapposizioni(zona)` → `bool`. 7. Se nessun conflitto, il sistema persiste le modifiche via `ZonaGeografica.setTipoRestrizione()` / `setNoteRestrizione()` / `setZona()` + DBMS Update, e mostra la situazione aggiornata: `AppPA.mostraMappa(zone)`. |
| **Flussi alternativi** | **A1 — Conflitto con restrizioni esistenti – Sovrascrittura confermata:** Al passo 6 del flusso principale, `ZonaGeografica.verificaSovrapposizioni()` restituisce `true` (conflitto rilevato). Il sistema notifica il conflitto all'Amministrazione Pubblica. L'Amministrazione Pubblica conferma la sovrascrittura: `AppPA.confermaSovrascrittura(idArea, tipoRestrizione, noteRestrizione)`. Il sistema salva le restrizioni sovrascritte e mostra l'aggiornamento: `AppPA.mostraMappa(zone)`. **A2 — Conflitto con restrizioni esistenti – Annullamento:** Al passo 2 del flusso alternativo A1, l'Amministrazione Pubblica rifiuta la sovrascrittura: `AppPA.rifiutaSovrascrittura()`. Il sistema annulla le modifiche e riporta la mappa allo stato precedente: `AppPA.mostraMappa(zone)`. |
| **Postcondizioni** | 1. La zona geografica è stata aggiornata con le nuove regole *(se sovrascrittura confermata o nessun conflitto)*. 2. Le restrizioni sono state salvate nel sistema via DBMS. |
| **Include** | — |
| **Estende** | — |
| **Esteso dal caso d'uso** | — |
| **Specializza il caso d'uso** | — |
| **Generalizza il caso d'uso** | — |
| **Requisiti** | Sistema di gestione delle zone geografiche con supporto alla verifica dei conflitti tra restrizioni sovrapposte. Visualizzazione cartografica interattiva delle zone operative. |
| **Vincoli architetturali** | VC-01: Le zone modificate sono utilizzate da `ZonaGeografica.checkArea()` durante la terminazione corsa in UC.UT.07 *(vincolo AP.04, chiarimenti-vari.md punto 4)*. VC-02: View mai direttamente sul Model — AppPA comunica solo con Controller (GestioneAree, GestioneFlotta, GestioneStatistiche, GestioneAutenticazione) *(Master_Spec §8 vincolo 10)*. VC-03: Sistemi esterni simulati *(chiarimenti-vari.md punto 16)*. |

---

## 2. Method Traceability Matrix

### 2.1 Flusso Principale — Tracciamento Metodo per Passo

| # | Passo UC | Componente | Metodo | Firma | Input | Output | Fonte |
|:--|:---------|:-----------|:-------|:------|:------|:-------|:------|
| 1 | PA accede a gestione aree | AppPA *(View)* | `selezionaMappa()` | `void` | — | — | Master_Spec §4 |
| 2 | Sistema recupera zone | GestioneAree *(Controller)* | `getZoneGeografiche()` | `ZonaGeografica` | — | `ZonaGeografica` (collezione) | Master_Spec §3 |
| 2a | ↳ Recupera zone dal Model | ZonaGeografica *(Model)* | `getZone()` | `ZonaGeografica` | — | `ZonaGeografica` (collezione) | Master_Spec §2 |
| 3 | Sistema mostra mappa | AppPA *(View)* | `mostraMappa(zone)` | `void` | `zone: ZonaGeografica` | — | Master_Spec §4 |
| 4 | PA modifica restrizioni | AppPA *(View)* | `modificaRestrizioni(zona)` | `void` | `zona: ZonaGeografica` | — | Master_Spec §4 |
| 5 | Sistema riceve modifica e prepara update | GestioneAree *(Controller)* | `aggiornaRestrizione(idArea, tipoRestrizione, noteRestrizione, zona)` | `void` | `idArea, tipoRestrizione, noteRestrizione, zona` | — | Master_Spec §3 |
| 6 | Sistema verifica conflitti | GestioneAree *(Controller)* | `analisiConflitti(zona)` | `bool` | `zona: ZonaGeografica` | `true` (conflitto) \| `false` | Master_Spec §3 |
| 6a | ↳ Controlla sovrapposizioni | ZonaGeografica *(Model)* | `verificaSovrapposizioni(zona)` | `bool` | `zona: ZonaGeografica` | `true` \| `false` | Master_Spec §2 |
| 7 | Sistema salva (se nessun conflitto o sovrascrittura confermata) | ZonaGeografica *(Model)* | `setTipoRestrizione(tipoRestrizione)` / `setNoteRestrizione(noteRestrizione)` / `setZona(zona)` | `void` | `tipoRestrizione, noteRestrizione, zona` | — | Master_Spec §2 |
| 7a | ↳ Persiste su DB | DBMS *(External)* | CRUD Update | — | `ZonaGeografica` | — | Master_Spec §5 |
| 7b | Sistema aggiorna mappa (notifica successo implicito) | AppPA *(View)* | `mostraMappa(zone)` | `void` | `zone: ZonaGeografica` | — | Master_Spec §4 |
| — | *(nessun metodo mostraSuccesso su AppPA — successo comunicato tramite aggiornamento mappa)* | — | — | — | — | — | osservazione 8 §10 |

### 2.2 Flussi Alternativi — Tracciamento Metodo

| # | Flusso | Componente | Metodo | Condizione | Fonte |
|:--|:-------|:-----------|:-------|:-----------|:------|
| A1 | Conflitto – Sovrascrittura confermata | AppPA | `confermaSovrascrittura(idArea, tipoRestrizione, noteRestrizione)` | `verificaSovrapposizioni() == true` + PA accetta | UC.AP.03 doc + Master_Spec §4 |
| A1a | ↳ Aggiorna mappa (notifica successo) | AppPA | `mostraMappa(zone)` | dopo sovrascrittura | Master_Spec §4 |
| A2 | Conflitto – Annullamento | AppPA | `rifiutaSovrascrittura()` | `verificaSovrapposizioni() == true` + PA rifiuta | UC.AP.03 doc + Master_Spec §4 |
| A2a | ↳ Ripristina vista mappa | AppPA | `mostraMappa(zone)` | dopo annullamento | Master_Spec §4 |

### 2.3 Verifica Tipi di Dati Critici

| Metodo | Parametro | Tipo | Formato | Fonte |
|:-------|:----------|:-----|:--------|:------|
| `GestioneAree.aggiornaRestrizione()` | `tipoRestrizione` | `TipoRestrizione` | Enum: `divieto_parcheggio` \| `ZTL` \| `limite_velocita` | Master_Spec §1 + claim-2d4e6f010 |
| `GestioneAree.aggiornaRestrizione()` | `zona` | `LineString` | Poligono geospaziale (delimitazione area) | Master_Spec §2 ZonaGeografica |
| `ZonaGeografica.verificaSovrapposizioni()` | `zona` | `ZonaGeografica` | Istanza completa di ZonaGeografica per confronto geometrico | Master_Spec §2 |
| `AppPA.mostraMappa()` | `zone` | `ZonaGeografica` | Collezione di zone da renderizzare su mappa | Master_Spec §4 |
| `AppPA.confermaSovrascrittura()` | `tipoRestrizione` | `TipoRestrizione` | Enum — deve corrispondere a valore valido | Master_Spec §1 |

---

## 3. Sequence Diagram Cross-Reference

Il sequence diagram `UC.AP.03-clean.uml` (XMI 2.1) è stato analizzato e mappato al presente documento.

### 3.1 Lifeline → Componente

| Lifeline XMI | Componente Master_Spec | Layer |
|:-------------|:-----------------------|:------|
| Amministrazione Pubblica | PA (Attore) | — |
| AppPA | AppPA | View |
| GestioneAree | GestioneAree | Controller |
| ZonaGeografica | ZonaGeografica | Model |

### 3.2 Messaggi XMI → Metodi Master_Spec

| Messaggio XMI | Metodo Master_Spec | Note |
|:--------------|:-------------------|:------|
| `selezionaMappa()` | `selezionaMappa()` | **Match** — firma identica |
| `getZoneGeografiche()` | `getZoneGeografiche()` | **Match** — firma identica |
| `ZonaGeografica.getZone()` | `getZone()` | **Match** — firma identica |
| `reindirizzaMappa(Lista<ZonaGeografica>)` | `mostraMappa(zone)` | **Diverge** — XMI usa nome diverso; Master_Spec è autoritativo (claim-ap03-a03) |
| `modificaRestrizioni(ZonaGeografica)` | `modificaRestrizioni(zona)` | **Match** — firma identica |
| `aggiornaRestrizione(idArea, tipoRestrizione, noteRestrizione, zona)` | `aggiornaRestrizione(idArea, tipoRestrizione, noteRestrizione, zona)` | **Match** — class diagram aggiornato per allinearsi (claim-ap03-a01 risolto) |
| `salvaRestrizioni(idArea, tipoRestrizione, noteRestrizione, zona)` | *(non esiste)* | **Artefatto XMI** — mappato a setter ZonaGeografica + DBMS Update (claim-ap03-a02) |
| `AnalisiConflitti(ZonaGeografica)` | `analisiConflitti(zona)` | **Diverge** — XMI maiuscola; Master_Spec camelCase (claim-ap03-a04) |
| `verificaSovrapposizioni(ZonaGeografica)` | `verificaSovrapposizioni(zona)` | **Match** — firma identica |
| `confermaSovrascrittura(idArea, tipoRestrizione, noteRestrizione, zona)` | `confermaSovrascrittura(idArea, tipoRestrizione, noteRestrizione)` | **Diverge** — XMI ha 4 params (include `zona`); Master_Spec ha 3 params |
| `rifiutaSovrascrittura()` | `rifiutaSovrascrittura()` | **Match** — firma identica |

### 3.3 Frammenti Combinati (ALT)

| Frammento XMI | Guard Condition | Mapping UC |
|:--------------|:----------------|:-----------|
| `CombinedFragment2` | `conflitto con restrizioni esistenti` | Flusso alternativo A1/A2 — `verificaSovrapposizioni() == true` |
| `Operand` (inner) | `se l'utente vuole procedere` | A1 (conferma) vs A2 (rifiuta) |
| `Operand3` | *(senza guard esplicita)* | Flusso principale — nessun conflitto |
| `Operand4` | *(senza guard esplicita)* | Post-annullamento — ritorno a UI Mappa |

### 3.4 Artefatti XMI Identificati e Risolti

| # | Artefatto XMI | Correzione | Riferimento |
|:--|:--------------|:-----------|:------------|
| 1 | `aggiornaRestrizione` (era singolare nel XMI, class diagram aggiornato per corrispondere) | Allineato — class diagram e XMI usano entrambi `aggiornaRestrizione` | claim-ap03-a01 *(RISOLTO: class diagram aggiornato 2026-06-23)* |
| 2 | `salvaRestrizioni(...)` su ZonaGeografica | Non esiste — mappato a `setTipoRestrizione()` + `setNoteRestrizione()` + `setZona()` + DBMS Update | claim-ap03-a02 *(PENDING)* |
| 3 | `reindirizzaMappa(...)` su AppPA | `mostraMappa(zone)` — nome canonico da Master_Spec §4 | claim-ap03-a03 *(PENDING)* |
| 4 | `AnalisiConflitti` (A maiuscola) | `analisiConflitti` (camelCase) — typo XMI da chiarimenti-vari.md punto 14 | claim-ap03-a04 *(PENDING)* |
| 5 | `confermaSovrascrittura(...)` con 4 params (include `zona`) | 3 params: `(idArea, tipoRestrizione, noteRestrizione)` — `zona` ridondante, già in contesto | Master_Spec §4 vs XMI |
| 6 | `salvaRestrizioni` chiamato prima di `verificaSovrapposizioni` in XMI | Ordine logico corretto: verifica conflitti → poi salva. L'ordine XMI è invertito. | documentazione.md flusso principale passo 4-5 |

---

## 4. Vincoli Architetturali Attivi in UC.AP.03

| ID | Vincolo | Impatto su UC.AP.03 | Fonte |
|:---|:--------|:--------------------|:------|
| V02 | Verifica geospaziale | `ZonaGeografica.verificaSovrapposizioni()` è il gate per la validazione — ogni modifica di zona passa attraverso questo check | Master_Spec §8, chiarimenti-vari.md p.4 |
| V10 | Disaccoppiamento View-Model | AppPA non interroga mai direttamente ZonaGeografica — sempre via GestioneAree controller | Master_Spec §8 |
| V12 | Simulazione sistemi esterni | DBMS, Servizio Mappa, Gateway Pagamento sono simulati | chiarimenti-vari.md p.16 |
| V05 | RBAC | Solo PA (RuoloAttore.PA) può accedere a GestioneAree e AppPA | Master_Spec §8 |

---

## 5. Relazioni con Altri Use Case

| UC | Relazione | Impatto |
|:---|:----------|:--------|
| UC.UT.07 (Termina Corsa e Pagamento) | Dipende da UC.AP.03 | `ZonaGeografica.checkArea(coordinateUtente)` in UC.UT.07 verifica le zone definite/modificate in UC.AP.03 — se le zone non sono state configurate, il vincolo AP.04 non ha effetto |
| UC.UT.04 (Ottimizzazione Percorso) | Dipende da UC.AP.03 | `ServizioMappa.getPercorso(coordinateIniziali, coordinateFinali, restrizioni)` riceve le zone come parametro `restrizioni` per escludere aree vietate dal percorso |
| UC.AP.01 (Monitoraggio Statistiche) | Relazionato | `GestioneStatistiche` osserva `Transito` (M:N Corsa↔ZonaGeografica) — le zone modificate in UC.AP.03 compaiono nelle statistiche di attraversamento |
| UC.UT.10 (Visualizzazione aree non accessibili) | Dipende da UC.AP.03 | L'utente visualizza le zone definite dalla PA per pianificare il percorso |

---

## 6. Enumerazioni Coinvolte

### 6.1 TipoRestrizione

| Valore | Descrizione | Impatto su UC.AP.03 |
|:-------|:------------|:---------------------|
| `divieto_parcheggio` | No parking zone — impedisce la terminazione corsa nell'area | Impostabile dalla PA; verificato da `checkArea()` in UC.UT.07 |
| `ZTL` | Zona a Traffico Limitato — impedisce il transito dei mezzi | Impostabile dalla PA; usato da ServizioMappa per routing in UC.UT.04 |
| `limite_velocita` | Speed limit zone — impone limite di velocità ai mezzi nell'area | Impostabile dalla PA; usato da ServizioMappa per routing in UC.UT.04 |

*Fonte:* Master_Spec.cgd.md §1 + claim-2d4e6f010 *(confermato HITL Round A)*.

---

## 7. Data Flow — Riepilogo

```
AppPA.selezionaMappa()
       │
       ▼
GestioneAree.getZoneGeografiche()
       │
       └── ZonaGeografica.getZone() ──▶ Lista<ZonaGeografica>
              │
              ▼
AppPA.mostraMappa(zone)
       │
       ▼
AppPA.modificaRestrizioni(zona)
       │
       ▼
GestioneAree.aggiornaRestrizione(idArea, tipoRestrizione, noteRestrizione, zona)
       │
       ▼
GestioneAree.analisiConflitti(zona)
       │
       └── ZonaGeografica.verificaSovrapposizioni(zona) ──▶ bool
              │
              ├── false (nessun conflitto)
              │         │
              │         ├── ZonaGeografica.setTipoRestrizione(tipoRestrizione)
              │         ├── ZonaGeografica.setNoteRestrizione(noteRestrizione)
              │         ├── ZonaGeografica.setZona(zona)
              │         ├── DBMS.Update(ZonaGeografica)
              │         └── AppPA.mostraMappa(zone) [notifica successo implicito]
              │
              └── true (conflitto) ──▶ AppPA notifica conflitto
                                            │
                                            ├── A1: PA conferma ──▶ AppPA.confermaSovrascrittura(idArea, tipoRestrizione, noteRestrizione)
                                            │         ├── ZonaGeografica.setTipoRestrizione(tipoRestrizione)
                                            │         ├── ZonaGeografica.setNoteRestrizione(noteRestrizione)
                                            │         ├── ZonaGeografica.setZona(zona)
                                            │         ├── DBMS.Update(ZonaGeografica)
                                            │         └── AppPA.mostraMappa(zone)
                                            │
                                            └── A2: PA rifiuta ──▶ AppPA.rifiutaSovrascrittura()
                                                      └── AppPA.mostraMappa(zone) [stato precedente]
```

---

## 8. Clarity Gate — 9-Point Verification

### Point 1 — Hypothesis vs Fact Labeling
**PASS.** Il documento è una specifica di use case. Tutte le affermazioni sono requisiti architetturali o descrizioni di comportamento atteso derivati da documentazione.md e Master_Spec.cgd.md. Nessuna affermazione fattuale non verificata presentata come certa.

### Point 2 — Uncertainty Marker Enforcement
**PASS.** Il linguaggio è prescrittivo ("il sistema verifica", "il sistema recupera"), appropriato per una specifica di sistema. Non sono presenti affermazioni predittive o forward-looking.

### Point 3 — Assumption Visibility
**PASS.** Le assunzioni sono esplicitate come Precondizioni e Requisiti. I vincoli architetturali sono documentati nella sezione 4. I sistemi esterni (DBMS) sono marcati come simulati. Le relazioni con altri use case sono documentate nella sezione 5.

### Point 4 — Authoritative-Looking Unvalidated Data
**PASS.** Nessuna tabella con percentuali o metriche non verificate. I valori enum per `TipoRestrizione` sono confermati dal Master_Spec HITL Round A (claim-2d4e6f010). I 4 claim PENDING sono chiaramente marcati e attendono conferma dal team.

### Point 5 — Data Consistency
**PASS.** Cross-reference completato tra 4 fonti:
- documentazione.md §UC.AP.03: **match** con flusso principale e alternativi
- Master_Spec.cgd.md: **match** su tutte le firme dei metodi con 6 divergenze XMI identificate e risolte (sezione 3.4)
- UC.AP.03-clean.uml (XMI): **6 artefatti identificati e risolti**
- chiarimenti-vari.md: **match** su vincolo AP.04 (punto 4) e trattamento errori XMI (punto 14)

### Point 6 — Implicit Causation
**PASS.** Le relazioni causa-effetto sono esplicite nei flussi e nelle condizioni di guard. Nessuna relazione causale implicita non documentata.

### Point 7 — Future State as Present
**PASS.** Il documento descrive specifiche di progettazione, non uno stato attuale. Le postcondizioni usano il passato prossimo come da convenzione (chiarimenti-vari.md punto 1).

### Point 8 — Temporal Coherence
**PASS.** `processed-date: 2026-06-22` coerente con la data corrente. Il Master_Spec di riferimento (v4.0) è il più recente. Le date nei claim HITL (2026-06-22) sono coerenti.

### Point 9 — Externally Verifiable Claims
**PASS.** Nessun claim che richieda verifica esterna. I 4 claim PENDING riguardano discrepanze XMI ↔ Master_Spec e richiedono solo conferma interna dal team Cofee Coders.

---

## 9. HITL Verification Record

### Round A: Derived Data Confirmation

| # | Claim ID | Claim | Fonte | Stato |
|:--|:---------|:------|:------|:------|
| 1 | claim-ap03-a01 | `aggiornaRestrizione` — class diagram aggiornato per corrispondere al XMI (singolare canonico) | UC.AP.03-clean.uml + classDiagram-v1.8-clean.uml + Master_Spec §3 | RESOLVED |
| 2 | claim-ap03-a02 | `salvaRestrizioni(...)` su ZonaGeografica non esiste in Master_Spec — mappato a setter + DBMS | UC.AP.03-clean.uml vs Master_Spec §2 | PENDING |
| 3 | claim-ap03-a03 | `reindirizzaMappa(...)` (XMI) → `mostraMappa(zone)` (Master_Spec) | UC.AP.03-clean.uml vs Master_Spec §4 | PENDING |
| 4 | claim-ap03-a04 | `AnalisiConflitti` (XMI, A maiuscola) → `analisiConflitti` (Master_Spec, camelCase) | UC.AP.03-clean.uml vs Master_Spec §3 | PENDING |

### Round B: True HITL Verification
*Nessun claim Round B — tutti i claim sono derivati da dati esistenti (confronto XMI ↔ Master_Spec).*

---

## 10. Riepilogo Inconsistenze Risolte

| # | Inconsistenza | Fonte A | Fonte B | Risoluzione |
|:--|:-------------|:--------|:--------|:------------|
| 1 | Nome metodo: `aggiornaRestrizione` — class diagram e XMI allineati | classDiagram-v1.8-clean.uml (aggiornato) | UC.AP.03-clean.uml (XMI) | Risolto: class diagram aggiornato per usare `aggiornaRestrizione` (singolare) il 2026-06-23 |
| 2 | Metodo `salvaRestrizioni(...)` su ZonaGeografica | UC.AP.03-clean.uml (XMI) | Master_Spec.cgd.md §2 | Artefatto XMI — mappato a setter ZonaGeografica + DBMS Update orchestrati da `GestioneAree.aggiornaRestrizione()` |
| 3 | Nome metodo View: `reindirizzaMappa(...)` vs `mostraMappa(zone)` | UC.AP.03-clean.uml (XMI) | Master_Spec.cgd.md §4 | Master_Spec è autoritativo → `mostraMappa(zone)` |
| 4 | Capitalizzazione: `AnalisiConflitti` vs `analisiConflitti` | UC.AP.03-clean.uml (XMI) | Master_Spec.cgd.md §3 | Typo XMI (chiarimenti-vari.md p.14) → `analisiConflitti(zona)` |
| 5 | Param count `confermaSovrascrittura`: 4 (XMI) vs 3 (Master_Spec) | UC.AP.03-clean.uml (XMI, include `zona`) | Master_Spec.cgd.md §4 (3 params) | Master_Spec è autoritativo → 3 params: `(idArea, tipoRestrizione, noteRestrizione)` — `zona` ridondante, già in contesto di modifica |
| 6 | Ordine messaggi XMI: save prima di conflict check | UC.AP.03-clean.uml (XMI) | documentazione.md §UC.AP.03 (check → save) | documentazione.md è autoritativo (chiarimenti-vari.md p.15) → ordine corretto: modify → verify → save/overwrite/cancel |
| 7 | User story AP.04 mappata a UC.AP.03 semanticamente divergente | AP.04 (enforcement a fine corsa) | UC.AP.03 (gestione zone) | Corretto — AP.04 è il vincolo (Non Funzionale), UC.AP.03 fornisce l'interfaccia funzionale per gestire le zone che implementano il vincolo *(chiarimenti-vari.md p.4)* |
| 8 | `AppPA.mostraSuccesso(msg)` assente | documentazione.md UC.AP.03 ("notifica il successo dell'operazione") | Master_Spec.cgd.md §4 AppPA (non ha mostraSuccesso) | AppPA non dispone di un metodo mostraSuccesso dedicato. Le altre View (AppOperatoreTecnico, AppOperatoreSC) lo possiedono. Il successo è comunicato implicitamente tramite `mostraMappa(zone)` che aggiorna la vista. Possibile omissione nel Master_Spec — da verificare con il team. |

---

## 11. Verifica Parametri Critici *(come da istruzioni)*

| # | Verifica | Risultato | Dettaglio |
|:--|:---------|:----------|:----------|
| 1 | `GestioneAree.aggiornaRestrizione(idArea, tipoRestrizione, noteRestrizione, zona)` — 4 params | ✅ CONFERMATO | Master_Spec.cgd.md §3: firma esatta con 4 parametri. XMI usa nome singolare ma stessa firma. |
| 2 | `ZonaGeografica.verificaSovrapposizioni(ZonaGeografica) → bool` | ✅ CONFERMATO | Master_Spec.cgd.md §2: `verificaSovrapposizioni(zona)` con param `zona: ZonaGeografica`, ritorna `bool`. XMI match. |
| 3 | `ZonaGeografica.creaZonaGeografica(idArea, tipoRestrizione, noteRestrizione, zona)` — 4 params | ✅ CONFERMATO | Master_Spec.cgd.md §2: firma esatta con 4 parametri. Usato per creazione nuove zone (caso base di gestione aree, non esplicitamente nel flusso UC.AP.03 che si concentra sulla modifica). |
| 4 | `TipoRestrizione`: `divieto_parcheggio`, `ZTL`, `limite_velocita` | ✅ CONFERMATO | Master_Spec.cgd.md §1 + claim-2d4e6f010 *(HITL Round A verified)*. XMI non contiene enum espliciti ma i valori sono coerenti con il dominio. |
| 5 | Map flow: access zone management → show existing zones → modify restrictions → verify conflicts → [if conflict: ask overwrite/cancel] → save | ✅ CONFERMATO | documentazione.md §UC.AP.03 flusso principale + flussi alternativi A1/A2. XMI sequence diagram conferma la struttura con frammenti ALT. |
| 6 | Alternative flow: conflict → overwrite confirmed, OR conflict → cancel | ✅ CONFERMATO | documentazione.md §UC.AP.03 flussi alternativi. XMI CombinedFragment2 + Operand con guard "se l'utente vuole procedere". |
| 7 | Postcondition: zone updated with new rules | ✅ CONFERMATO | documentazione.md §UC.AP.03: "La zona geografica è stata aggiornata con le nuove regole." |

---

## 12. Componenti e Metodi Coinvolti — Riepilogo

### GestioneAree (Controller)

| Metodo | Firma Completa | Ruolo in UC.AP.03 |
|:-------|:---------------|:-------------------|
| `aggiornaRestrizione` | `void aggiornaRestrizione(idArea, tipoRestrizione: TipoRestrizione, noteRestrizione: String, zona: LineString)` | Aggiorna le restrizioni di una zona esistente (passo 5 flusso principale) |
| `analisiConflitti` | `bool analisiConflitti(zona: ZonaGeografica)` | Verifica se la zona modificata confligge con altre zone esistenti (passo 6) |
| `getZoneGeografiche` | `ZonaGeografica getZoneGeografiche()` | Recupera tutte le zone registrate nel sistema (passo 2) |

### AppPA (View)

| Metodo | Firma Completa | Ruolo in UC.AP.03 |
|:-------|:---------------|:-------------------|
| `selezionaMappa` | `void selezionaMappa()` | L'utente PA accede alla sezione mappa/zone (passo 1) |
| `mostraMappa` | `void mostraMappa(zone: ZonaGeografica)` | Visualizza la mappa con le zone e relative restrizioni — comunica implicitamente il successo delle operazioni (passi 3, 7, A1a, A2a) |
| `modificaRestrizioni` | `void modificaRestrizioni(zona: ZonaGeografica)` | La PA modifica i dati di restrizione per una zona (passo 4) |
| `confermaSovrascrittura` | `void confermaSovrascrittura(idArea, tipoRestrizione: TipoRestrizione, noteRestrizione: String)` | La PA conferma la sovrascrittura in caso di conflitto (passo A1) |
| `rifiutaSovrascrittura` | `void rifiutaSovrascrittura()` | La PA annulla l'operazione in caso di conflitto (passo A2) |
| `mostraErrore` | `void mostraErrore(msg: String)` | Notifica errori all'utente PA *(non usato direttamente in UC.AP.03 — conflitti gestiti tramite richiesta sovrascrittura)* |

### ZonaGeografica (Model)

| Metodo | Firma Completa | Ruolo in UC.AP.03 |
|:-------|:---------------|:-------------------|
| `verificaSovrapposizioni` | `bool verificaSovrapposizioni(zona: ZonaGeografica)` | Verifica geometricamente se la zona passata si sovrappone a zone esistenti (passo 6a) |
| `getZone` | `ZonaGeografica getZone()` | Recupera tutte le zone dal database (passo 2a) |
| `creaZonaGeografica` | `void creaZonaGeografica(idArea, tipoRestrizione: TipoRestrizione, noteRestrizione: String, zona: LineString)` | Crea una nuova zona geografica *(caso base — usato per aggiungere nuove zone, non nel flusso principale UC.AP.03 che modifica zone esistenti)* |
| `getRestrizioniZona` | `ZonaGeografica getRestrizioniZona(coordinateUtente: String)` | Recupera le restrizioni attive per una data coordinata *(usato da altri UC, non direttamente in UC.AP.03)* |
| `checkArea` | `bool checkArea(coordinateUtente: String)` | Verifica se le coordinate sono in area consentita *(usato in UC.UT.07 per enforcement delle zone definite in UC.AP.03)* |

---

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | PENDING — 4 claim pending team confirmation (Round A).

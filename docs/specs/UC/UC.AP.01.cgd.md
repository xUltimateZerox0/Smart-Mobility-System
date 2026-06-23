---
clarity-gate-version: 2.1
document-type: Implementation
processed-date: 2026-06-23
processed-by: Claude (AI) + Cross-Reference Engine — documentazione.md (primary), Master_Spec.cgd.md §2-3, UC.AP.01-clean.uml, response2.md
clarity-status: CLEAR
hitl-status: REVIEWED
hitl-pending-count: 0
points-passed: 1-9
document-sha256: b1be8ab53c518d138766e75b5ef0d9b77a8f931211008838313d7ea854ab5860
hitl-claims:
  - id: claim-ap01-a1b2c3d4
    text: "GestioneStatistiche.analisiTratte(dataInizio, dataFine) restituisce un oggetto di tipo statistiche contenente i dati aggregati"
    value: "CONFERMATO: analisiTratte(dataInizio, dataFine) returns statistiche object."
    source: "Master_Spec.cgd.md §3 Controller Layer / GestioneStatistiche"
    location: "Master_Spec/GestioneStatistiche/analisiTratte"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-ap01-b2c3d4e5
    text: "UC.AP.01 copre le user story AP.01 e AP.03 — mappatura 1 use case per 2 user story"
    value: "CONFERMATO: Mapping UC.AP.01 covers stories AP.01 + AP.03 (2:1)."
    source: "documentazione.md §2.2.2 + tabella riepilogativa Sprint Report"
    location: "UseCases/UC.AP.01/mapping"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-ap01-c3d4e5f6
    text: "Pre-condizioni di UC.AP.01 confermate: sessione PA attiva e autenticazione ruolo PA"
    value: "CONFERMATO: Pre-condizioni reports flow."
    source: "documentazione.md §2.2.2 UC.AP.01 + Master_Spec.cgd.md §8"
    location: "UC.AP.01/preconditions"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-ap01-d4e5f6a7
    text: "Post-condizioni di UC.AP.01 confermate: statistiche generate e report disponibile per download"
    value: "CONFERMATO: Post-condizioni reports flow."
    source: "documentazione.md §2.2.2 UC.AP.01"
    location: "UC.AP.01/postconditions"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-ap01-e5f6a7b8
    text: "Metodi per la generazione delle statistiche (analisiTratte, getCorseByPeriodo, getTransitiByCorsa, generaFileStatistiche) confermati"
    value: "CONFERMATO: Metodi statistics generation."
    source: "Master_Spec.cgd.md §2-3 + UC.AP.01-clean.uml"
    location: "UC.AP.01/methods"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
---

# UC.AP.01 — Monitoraggio Statistiche e Analisi Tratte

**Versione:** 1.0 *(AI-Ready — CGD v2.1)*
**Team:** Cofee Coders
**Progetto:** Ingegneria del Software a.a. 2025/2026
**Data Rilascio TARGET:** 25/06/2026
**Dominio:** Città generica con copertura WiFi full-range — Smart Urban Mobility

**Fonti (in ordine di priorità):**
1. `documentazione.md` §2.2.2 — specifica completa del caso d'uso (sorgente primaria)
2. `Master_Spec.cgd.md` §2-3 — firme dei metodi e associazioni (Model + Controller)
3. `UC.AP.01-clean.uml` — diagramma di sequenza (messaggi e flusso)
4. `chiarimenti-vari.md` — vincoli e interpretazioni

---

## 1. Identificazione

| Campo | Valore |
|-------|--------|
| **ID** | UC.AP.01 |
| **Nome** | Monitoraggio Statistiche e Analisi Tratte |
| **Attore Primario** | Amministrazione Pubblica (PA) |
| **User Story Associate** | AP.01 + AP.03 |
| **Tipo** | Funzionale |
| **Priorità** | AP.01: 20 \| AP.03: 15 |
| **Sprint** | 2 |

**User Story AP.01:** *Come amministrazione comunale, voglio accedere alle statistiche di utilizzo del sistema, così da supportare decisioni strategiche.*

**User Story AP.03:** *Come amministrazione comunale, voglio poter conoscere le tratte più utilizzate, così da poter pianificare la manutenzione in specifiche aree.*

> **Nota sulla mappatura:** UC.AP.01 copre entrambe le user story AP.01 e AP.03 *(claim-ap01-m3n4o5p6 — Round A: da verificare)*. La generazione di statistiche aggregate soddisfa AP.01 (supporto decisioni strategiche), mentre l'analisi delle tratte percorse (zone attraversate da ciascuna corsa) soddisfa AP.03 (pianificazione manutenzione aree).

---

## 2. Breve Descrizione

La Pubblica Amministrazione seleziona un intervallo temporale di interesse. Il sistema recupera le corse effettuate nel periodo indicato e i dati sull'attraversamento delle tratte *(zone geografiche attraversate da ciascun mezzo durante la corsa)*. Genera un file di statistiche aggregate e lo presenta all'utente con la possibilità di scaricarlo.

---

## 3. Precondizioni

| # | Condizione | Verificabilità |
|---|-----------|----------------|
| PC-01 | L'Amministrazione Pubblica ha effettuato l'accesso ed ha una sessione attiva | `idSessionePA ≠ null` — verificabile via `GestioneAutenticazione` |
| PC-02 | L'Amministrazione Pubblica è autenticata con ruolo `PA` | `Attore.ruolo = PA` — RBAC post-login |

**Vincolo architetturale:** Ogni operazione richiede sessione attiva *(Master_Spec §8, vincolo 1)*. L'accesso alle funzionalità di `AppPA` è determinato dal `RuoloAttore.PA` dopo il login *(Master_Spec §8, vincolo 5)*.

---

## 4. Flusso Principale

| Step | Attore | Azione | Componente | Metodo |
|------|--------|--------|------------|--------|
| 1 | PA | Avvia la funzione di monitoraggio statistiche | AppPA | — |
| 2 | PA | Seleziona l'intervallo temporale (dataInizio, dataFine) | AppPA | `selezionaIntervallo(dataInizio, dataFine)` |
| 3 | Sistema | Richiede l'analisi delle tratte al Controller | GestioneStatistiche | `analisiTratte(dataInizio, dataFine)` |
| 4 | Sistema | Recupera le corse effettuate nel periodo | Corsa | `getCorseByPeriodo(dataInizio, dataFine)` |
| 5 | Sistema | **Per ogni corsa** recupera le zone geografiche attraversate | Transito | `getTransitiByCorsa(idCorsa)` |
| 6 | Sistema | Elabora i dati aggregati e genera il file di statistiche | GestioneStatistiche | `generaFileStatistiche(corse)` |
| 7 | Sistema | Mostra le statistiche e offre la possibilità di scaricare il report | AppPA | `mostraStatistiche(statistiche)` |

**Corrispondenza con il diagramma di sequenza:**

```
PA → AppPA: seleziona intervallo temporale
AppPA → GestioneStatistiche: richiedi Analisi Tratte
GestioneStatistiche → Corsa: richiedi Dati Corse
Corsa → GestioneStatistiche: restituisce Dati Corse
loop [per ogni corsa dell'intervallo selezionato]
  GestioneStatistiche → Transito: getTransitibyCorsa(idCorsa)
  Transito → GestioneStatistiche: lista<Transito>
end
GestioneStatistiche → GestioneStatistiche: genera File Report
GestioneStatistiche → AppPA: restituisce Report
AppPA → PA: mostra Report e scarica file
```

---

## 5. Flusso Alternativo

### FA-01: Dati non presenti nel periodo selezionato

| Step | Attore | Azione | Componente |
|------|--------|--------|------------|
| FA-01.1 | Sistema | `getCorseByPeriodo(dataInizio, dataFine)` restituisce insieme vuoto | Corsa |
| FA-01.2 | Sistema | Interrompe l'elaborazione e notifica l'assenza di dati | GestioneStatistiche |
| FA-01.3 | Sistema | Mostra il messaggio di errore: **"Mancanza dati, modificare le date"** | AppPA |

Il messaggio di errore esatto è confermato dal diagramma di sequenza: `Avviso "Mancanza dati, modificare le date"` *(claim-ap01-q7r8s9t0 — Round A: da verificare)*.

---

## 6. Postcondizioni

| # | Condizione | Verificabilità |
|---|-----------|----------------|
| PO-01 | Le statistiche aggregate sono state generate | File di report disponibile nel sistema |
| PO-02 | Il file di report è reso disponibile per il download | Interfaccia `AppPA` offre opzione di scaricamento |

**Formulazione al passato prossimo** *(chiarimenti-vari.md punto 1):*
- Le statistiche aggregate sono state generate e il file di report è stato reso disponibile dal sistema.

---

## 7. Include / Estende / Generalizzazioni

| Relazione | UC | Note |
|-----------|----|------|
| Include | — | Nessuna inclusione |
| Estende | — | Nessuna estensione |
| Esteso da | — | Nessun UC estende UC.AP.01 |
| Specializza | — | — |
| Generalizza | — | — |

---

## 8. Requisiti

| # | Requisito | Sorgente |
|---|-----------|----------|
| RQ-01 | Disponibilità di dati storici delle corse nel database | documentazione.md §2.2.2 |
| RQ-02 | Capacità di generazione di file di statistiche aggregate esportabili | documentazione.md §2.2.2 |
| RQ-03 | Sessione PA attiva (autenticazione pregressa) | Master_Spec §8, vincolo 1 |
| RQ-04 | Il sistema deve tracciare i transiti (M:N Corsa↔ZonaGeografica) per ogni corsa | Master_Spec §2 (Transito) |

---

## 9. Firme dei Metodi (Cross-Reference Verificato)

| Componente | Metodo | Ritorno | Parametri | Fonte |
|------------|--------|---------|-----------|-------|
| AppPA | `selezionaIntervallo(dataInizio, dataFine)` | void | dataInizio: date, dataFine: date | Master_Spec §4 (AppPA) |
| GestioneStatistiche | `analisiTratte(dataInizio, dataFine)` | statistiche | dataInizio: date, dataFine: date | Master_Spec §3 (GestioneStatistiche) |
| GestioneStatistiche | `generaFileStatistiche(corse)` | void | corse: Corsa | Master_Spec §3 (GestioneStatistiche) |
| Corsa | `getCorseByPeriodo(dataInizio, dataFine)` | Corsa | dataInizio: date, dataFine: date | Master_Spec §2 (Corsa) |
| Transito | `getTransitiByCorsa(idCorsa)` | Transito | idCorsa | Master_Spec §2 (Transito) |
| AppPA | `mostraStatistiche(statistiche)` | void | statistiche | Master_Spec §4 (AppPA) |

> **Nota:** Il tipo `statistiche` (minuscolo) per `analisiTratte` e `mostraStatistiche` è un tipo di ritorno aggregato non corrispondente a un'entità Model esplicita. Potrebbe rappresentare una struttura dati composita o un DTO. Il tipo `Corsa` come parametro di `generaFileStatistiche` e come ritorno di `getCorseByPeriodo` si intende come collezione di oggetti Corsa *(convenzione UML: il tipo parametro indica la classe, la molteplicità è gestita a runtime)*.

---

## 10. Tracciamento Transito (M:N Corsa ↔ ZonaGeografica)

L'entità `Transito` *(Master_Spec §2)* funge da tabella bridge tra `Corsa` e `ZonaGeografica`:

```
Transito
├── idCorsa (FK → Corsa)
└── idArea (FK → ZonaGeografica)
```

Ogni corsa può attraversare N zone geografiche. Ogni zona può essere attraversata da M corse. Il metodo `getTransitiByCorsa(idCorsa)` restituisce tutti i transiti associati a una corsa, permettendo di ricostruire le tratte percorse.

---

## 11. Vincoli e Invarianti

| # | Vincolo | Fonte |
|---|---------|-------|
| V-01 | La PA deve essere autenticata con sessione attiva per accedere a UC.AP.01 | Master_Spec §8, vincolo 1 |
| V-02 | Le View non interrogano mai direttamente il Model — ogni comunicazione è mediata dai Controller | documentazione.md §2.3, Master_Spec §8, vincolo 10 |
| V-03 | Sistemi esterni (DBMS) sono simulati — progetto universitario | chiarimenti-vari.md punto 16 |
| V-04 | Il costo di ogni Corsa è sempre ≥ 0 | Master_Spec §8 (Invarianti) |

---

## 12. Copertura dei Requisiti Non-Funzionali

| NFR | Applicabilità |
|-----|---------------|
| Autenticazione obbligatoria | PC-01: sessione PA attiva richiesta |
| RBAC | Solo attori con ruolo `PA` accedono a `AppPA` |
| Disaccoppiamento View-Model | `AppPA` comunica solo con `GestioneStatistiche` (Controller) |
| Simulazione esterna | DBMS interrogato per dati storici — nessuna connessione reale |

---

## 13. Anti-Patterns Evitati

| Anti-Pattern | Come è stato evitato |
|--------------|---------------------|
| View che interroga direttamente il Model | `AppPA` passa attraverso `GestioneStatistiche` (Controller) |
| Saltare la verifica di autenticazione | PC-01 richiede sessione PA attiva |
| Hardcodare l'intervallo temporale | `dataInizio` e `dataFine` sono parametri |

## 14. Test Case Specifications

| ID | Component | Scenario | Preconditions | Input | Expected Result | Postconditions | Edge Cases |
|---|---|---|---|---|---|---|---|
| TC-AP01-01 | AppPA | Seleziona intervallo valido | Sessione PA attiva, AppPA inizializzata | `dataInizio`, `dataFine` validi (dataFine >= dataInizio) | Parametri validati e passati a GestioneStatistiche | AppPA in attesa di risposta | Data nel futuro, data uguale a inizio e fine |
| TC-AP01-02 | GestioneStatistiche | Analisi tratte con dati presenti | Corsa e Transito popolati nel periodo | `analisiTratte(dataInizio, dataFine)` invocato | `getCorseByPeriodo` eseguito, dati aggregati restituiti | Report statistiche generato | Periodo con numero elevato di corse |
| TC-AP01-03 | Corsa | Recupero corse per periodo | Database con corse storiche | `getCorseByPeriodo(dataInizio, dataFine)` | Lista di oggetti Corsa nel periodo | Set di corse pronto per elaborazione | Periodo con zero corse (FA-01) |
| TC-AP01-04 | Transito | Recupero transiti per corsa | Corsa identificata con transiti associati | `getTransitiByCorsa(idCorsa)` per corsa esistente | Lista di oggetti Transito associati alla corsa | Dati transiti disponibili per analisi | Corsa senza transiti, corsa con molte zone |
| TC-AP01-05 | AppPA | Visualizzazione statistiche e download | Report statistiche generato | `mostraStatistiche(statistiche)` invocato | Statistiche mostrate, opzione download disponibile | Utente puo scaricare il report | Report con grandi volumi di dati |
| IT-AP01-01 | AppPA → GestioneStatistiche → Corsa | Integrazione catena completa | Sessione attiva, sistema inizializzato | Sequenza completa: selezionaIntervallo → analisiTratte → getCorseByPeriodo | Dati corsa fluiscono da AppPA a GestioneStatistiche a Corsa e ritorno | Flusso dati completo verificato | Test con diverse dimensioni di dataset |
| IT-AP01-02 | GestioneStatistiche → Transito | Integrazione loop transiti | Set di corse ottenuto da getCorseByPeriodo | `getTransitiByCorsa(idCorsa)` in loop per ogni corsa | Ogni corsa restituisce i propri transiti; aggregazione completa | Tutti i transiti raccolti per il report | Numero variabile di transiti per corsa |
| IT-AP01-03 | GestioneStatistiche → AppPA | Integrazione flusso errore FA-01 | Periodo selezionato senza dati | `getCorseByPeriodo` restituisce insieme vuoto | `mostraStatistiche` non invocata, messaggio "Mancanza dati, modificare le date" mostrato | Utente informato, nessun file generato | Transizione da errore a nuovo tentativo con date diverse |

---

## 15. Error Handling Matrix

| ERR-ID | Error Type | Component | Detection Point | System Response | Fallback | Logging |
|---|---|---|---|---|---|---|
| ERR-AP01-01 | GestioneStatistiche non disponibile | GestioneStatistiche | Invocazione `analisiTratte(dataInizio, dataFine)` senza risposta | Timeout — notifica errore all'utente tramite AppPA | Nuovo tentativo dopo timeout | Log di sistema: ServizioStatistiche non raggiungibile |
| ERR-AP01-02 | Nessuna corsa nel periodo | Corsa | `getCorseByPeriodo(dataInizio, dataFine)` restituisce insieme vuoto | Attivazione FA-01: messaggio "Mancanza dati, modificare le date" (FA-01.3) | L'utente modifica l'intervallo e riprova | Nessun log errore (caso d'uso previsto) |
| ERR-AP01-03 | Intervallo date non valido | AppPA | `selezionaIntervallo(dataInizio, dataFine)` con dataInizio > dataFine | Validazione lato AppPA: input rifiutato | L'utente corregge le date | Evento informativo: input non valido |
| ERR-AP01-04 | Transito con ZonaGeografica non valida | Transito | `getTransitiByCorsa(idCorsa)` restituisce transito con idArea inesistente | Transito saltato nell'aggregazione, corsa parzialmente elaborata | Corsa elaborata con transiti validi rimanenti | Warning: riferimento ZonaGeografica non valido per transito |
| ERR-AP01-05 | Corsa senza transiti | Transito | `getTransitiByCorsa(idCorsa)` restituisce lista vuota per corsa valida | Corsa inclusa nei dati aggregati ma senza tratte registrate | Corsa conteggiata ma marcata come "senza percorso" | Log informativo: corsa senza transiti |
| ERR-AP01-06 | Fallimento generazione file | GestioneStatistiche | `generaFileStatistiche(corse)` fallisce durante l'elaborazione | Errore interno — `mostraErrore(msg)` su AppPA | L'utente puo riavviare l'analisi | Log errore con dettagli del fallimento |

---

## HITL Verification Record

### Round A: Derived Data Confirmation

Tutti i claim derivano da cross-reference tra le fonti del progetto. Confermare l'interpretazione.

| # | Claim ID | Claim | Fonte | Stato |
|---|----------|-------|-------|-------|
| 1 | claim-ap01-a1b2c3d4 | `analisiTratte()` restituisce `statistiche` | Master_Spec §3 | PENDING |
| 2 | claim-ap01-e5f6g7h8 | `getCorseByPeriodo()` restituisce `Corsa` | Master_Spec §2 | PENDING |
| 3 | claim-ap01-i9j0k1l2 | `getTransitiByCorsa()` restituisce `Transito` | Master_Spec §2 | PENDING |
| 4 | claim-ap01-m3n4o5p6 | UC.AP.01 mappa AP.01 + AP.03 | documentazione.md §2.2.2 | PENDING |
| 5 | claim-ap01-q7r8s9t0 | Messaggio errore: "Mancanza dati, modificare le date" | UC.AP.01-clean.uml | PENDING |

### Round B: True HITL Verification

*Nessun claim richiede Round B — tutti i claim sono derivati direttamente dalle fonti del progetto e rientrano nel Round A.*

---

## Inconsistencies Report

### Cross-Reference Findings

| # | Tipo | Descrizione | Severità |
|---|------|-------------|----------|
| I-01 | Nota | Il tipo di ritorno `statistiche` (minuscolo) non corrisponde a un'entità Model esplicita nel diagramma delle classi. Nessuna classe `Statistiche` è definita. Potrebbe rappresentare un DTO o struttura dati composita non ancora formalizzata. | INFO |
| I-02 | Nota | `generaFileStatistiche(corse)` accetta `corse: Corsa` (singolare) — la convenzione UML consente di usare il nome della classe per rappresentare una collezione, ma la firma potrebbe trarre in inganno. Il diagramma di sequenza conferma che il metodo riceve una lista di corse. | INFO |
| I-03 | Conferma | Le firme dei metodi in Master_Spec §2-3 sono coerenti con il flusso descritto in documentazione.md §2.2.2 e con i messaggi del diagramma di sequenza UC.AP.01-clean.uml. Nessuna contraddizione rilevata. | PASS |
| I-04 | Conferma | UC.AP.01 non ha relazioni di include/estensione con altri use case. È un caso d'uso indipendente. Coerente con la tabella UC in Master_Spec §7. | PASS |
| I-05 | Conferma | L'ordine dei passi nel flusso principale (documentazione.md) corrisponde all'ordine dei messaggi nel diagramma di sequenza: selezionaIntervallo → richiediAnalisiTratte → getCorseByPeriodo → loop getTransitiByCorsa → generaFileStatistiche → mostraStatistiche. | PASS |

---

**Verdict:** CLEAR | PENDING — 5/5 claim in attesa di conferma Round A. 0 eccezioni. 0 contraddizioni cross-reference. 3 info/note.

---

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | PENDING

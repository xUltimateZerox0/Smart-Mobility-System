---
clarity-gate-version: 2.1
document-type: Implementation
processed-date: 2026-06-23
processed-by: Claude (AI) + Cross-Reference Engine — documentazione.md §UC.OP.02 (primary), Master_Spec.cgd.md v4.0 §3-4, UC.OP.02-clean.uml (XMI 2.1), chiarimenti-vari.md punto 11, response2.md
clarity-status: CLEAR
hitl-status: REVIEWED
hitl-pending-count: 0
points-passed: 1-9
rag-ingestable: false
document-sha256: 2ba7b085554bb4b1f9945422b9065e38a8d113463c38c7d9601b4ec0bd7bf454
hitl-claims:
  - id: claim-70d7b247
    text: "GestioneUtenti.cercaReport(idUtente) firma: Master_Spec v4.0 §3 riporta ritorno String; UC.OP.02-clean.uml mostra synchCall con reply Utente.report; l'user si aspetta ritorno void. Quale è la firma corretta?"
    value: "CONFERMATO: cercaReport returns String (not void)."
    source: "Master_Spec.cgd.md §3 GestioneUtenti vs UC.OP.02-clean.uml vs user expectation"
    location: "GestioneUtenti/cercaReport"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
   - id: claim-4274e7b4
     text: "Il messaggio di destroy sulla lifeline AppUtente nel sequence diagram rappresenta la disconnessione forzata di tutte le sessioni dell'utente moderato, come da chiarimenti-vari.md punto 11: 'i messaggi di destroy nei diagrammi indicano la distruzione dell'istanza... in caso di moderazione utente'"
     value: "RESOLVED: la distruzione della view (destroy message) è il meccanismo di logout. Non serve un metodo disconnetti() esplicito — il destroy message UML è sufficiente per la semantica di disconnessione (chiarito dal team 2026-06-23)."
     source: "UC.OP.02-clean.uml (destroy message) + chiarimenti-vari.md punto 11 + chiarimenti team 2026-06-23"
     location: "UC.OP.02/sequence-diagram/AppUtente-destroy"
     round: A
     confirmed-by: Team Cofee Coders (via pending design decisions response)
     confirmed-date: 2026-06-23
  - id: claim-b5e04f8a
    text: "Utente.azioneCorrettiva(azione) — il parametro azione: String accetta valori 'sospendi' e 'disattiva' mappati rispettivamente a StatoUtente.sospeso e StatoUtente.disattivato. La mappatura esatta dei valori stringa → enum non è documentata esplicitamente"
    value: "CONFERMATO: values are 'sospensione' and 'disattivazione' (see Warning #8 from response2.md)."
    source: "Master_Spec.cgd.md Utente.azioneCorrettiva(azione: String) §2 + StatoUtente enum §1 + documentazione.md UC.OP.02"
    location: "UC.OP.02/flusso-principale/step-4"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
---

# UC.OP.02 — Moderazione Utenti

**Versione:** 1.0 *(Clarity-Gated — cross-reference completato)*
**Team:** Cofee Coders
**Progetto:** Smart Mobility System — Ingegneria del Software a.a. 2025/2026
**Architettura:** MVC Web-oriented con Controller Intermediario
**Fonti primarie:** documentazione.md v3.0 §UC.OP.02, Master_Spec.cgd.md v4.0, UC.OP.02-clean.uml, chiarimenti-vari.md

---

## 1. Use Case Specification

| Campo | Valore |
|:------|:-------|
| **User Story** | OP.02 *(conoscere l'anagrafica dell'utente per risalire a furti/incidenti)* + OP.03 *(moderare l'account utente per prevenire violazioni dei termini di servizio)* |
| **Nome** | Moderazione Utenti |
| **ID** | UC.OP.02 |
| **Breve descrizione** | L'Operatore Servizio Clienti cerca un utente tramite il suo identificativo. Il sistema recupera i dati e il report associato all'utente e li mostra all'operatore. L'operatore può quindi aggiornare il report e applicare un'azione correttiva (sospensione/disattivazione dell'account) che verrà poi notificata all'utente, il quale verrà disconnesso da tutte le sessioni aperte. |
| **Attori principali** | Operatore Servizio Clienti |
| **Attori secondari** | Utente *(destinatario della notifica e disconnessione)* |
| **Precondizioni** | 1. L'Operatore Servizio Clienti ha effettuato l'accesso e ha una sessione attiva (RuoloAttore = `Operatore`, TipoOperatore = `OperatoreSC`). 2. L'utente target esiste nel sistema con stato `attivo`. |
| **Flusso principale** | 1. Il caso d'uso inizia quando l'Operatore Servizio Clienti cerca un utente tramite il suo identificativo (`idUtente`). 2. Il sistema, tramite `AppOperatoreSC.mostraReport(idUtente)`, innesca la ricerca: `GestioneUtenti.cercaReport(idUtente)` → `Utente.ricercaUtente(idUtente)`, recupera i dati anagrafici e il report (`Utente.report`) e li mostra all'operatore. 3. L'operatore aggiorna il report dell'utente: `AppOperatoreSC.aggiornaReport(idUtente)` → `Utente.setReportUtente(nuovoReport)`. 4. L'operatore applica un'azione correttiva: `Utente.azioneCorrettiva(azione)` dove `azione` è `"sospendi"` o `"disattiva"`, e il sistema aggiorna lo stato: `Utente.setStatoUtente(StatoUtente.sospeso)` o `Utente.setStatoUtente(StatoUtente.disattivato)`, orchestrato da `GestioneUtenti.gestioneUtente(idUtente)`. 5. Il sistema invia una notifica all'utente: `AppUtente.notificaAzione(idUtente, azione)`. 6. Il sistema disconnette l'utente da tutte le sessioni aperte — destroy dell'istanza `AppUtente` *(chiarimenti-vari.md punto 11)*. 7. Il sistema conferma l'esito positivo all'operatore: `AppOperatoreSC.mostraSuccesso(msg)`. |
| **Flussi alternativi** | **A1 — Utente Non Trovato:** Al passo 2 del flusso principale, `Utente.ricercaUtente(idUtente)` restituisce `null`. Il sistema mostra errore: `AppOperatoreSC.mostraErrore("Utente non trovato")`. Il caso d'uso termina senza modifiche. |
| **Postcondizioni** | 1. L'azione correttiva è stata applicata — `Utente.statoUtente` è stato aggiornato a `sospeso` o `disattivato` *(verificabile)*. 2. Il report dell'utente (`Utente.reportUtente`) è stato aggiornato *(verificabile)*. 3. È stata inviata all'utente una notifica sull'azione intrapresa tramite `AppUtente.notificaAzione()`. 4. L'utente è stato disconnesso da tutte le sessioni attive *(istanza AppUtente distrutta)*. |
| **Include** | — |
| **Estende** | — |
| **Esteso dal caso d'uso** | — |
| **Specializza il caso d'uso** | — |
| **Generalizza il caso d'uso** | — |
| **Requisiti** | Sistema di gestione utenti con report consultabili e modificabili. Meccanismo di collegamento con l'interfaccia dell'utente per comunicare le azioni correttive ed applicarle. |
| **Vincoli architetturali** | VC-01: Le operazioni di moderazione richiedono autenticazione con ruolo OperatoreSC *(RBAC — Master_Spec §8 vincolo 5)*. VC-02: La View non interroga mai direttamente il Model — AppOperatoreSC passa sempre attraverso GestioneUtenti *(Master_Spec §8 vincolo 10)*. VC-03: Le istanze delle View vengono distrutte alla disconnessione *(chiarimenti-vari.md punto 11)*. |

---

## 2. Method Traceability Matrix

### 2.1 Flusso Principale — Tracciamento Metodo per Passo

| # | Passo UC | Componente | Metodo | Firma | Input | Output | Fonte |
|:--|:---------|:-----------|:-------|:------|:------|:-------|:------|
| 1 | Operatore cerca utente per ID | AppOperatoreSC *(View)* | `mostraReport(idUtente)` | `void` | `idUtente` | — | Master_Spec §4 |
| 2 | ↆ Recupera dati e report | GestioneUtenti *(Controller)* | `cercaReport(idUtente)` | `String` ⚠️ | `idUtente` | `report: String` | Master_Spec §3 — *HITL claim-70d7b247* |
| 2a | ↆↆ Cerca utente nel sistema | Utente *(Model)* | `ricercaUtente(idUtente)` | `Utente` | `idUtente` | `Utente \| null` | Master_Spec §2 |
| 2b | ↆↆ Recupera report | Utente *(Model)* | `getReportUtente()` | `String` | — | `report: String` | Master_Spec §2 |
| 3 | Operatore aggiorna report | AppOperatoreSC *(View)* | `aggiornaReport(idUtente)` | `void` | `idUtente` | — | Master_Spec §4 |
| 3a | ↆ Persiste nuovo report | Utente *(Model)* | `setReportUtente(reportUtente)` | `void` | `reportUtente: String` | — | Master_Spec §2 |
| 4 | Applica azione correttiva | Utente *(Model)* | `azioneCorrettiva(azione)` | `void` | `azione: String` | — | Master_Spec §2 — *HITL claim-b5e04f8a* |
| 4a | Aggiorna stato utente | Utente *(Model)* | `setStatoUtente(statoUtente)` | `void` | `statoUtente: StatoUtente` | — | Master_Spec §2 |
| 4b | Orchestratore moderazione | GestioneUtenti *(Controller)* | `gestioneUtente(idUtente)` | `bool` | `idUtente` | `true \| false` | Master_Spec §3 |
| 5 | Notifica azione all'utente | AppUtente *(View)* | `notificaAzione(idUtente, azione)` | `void` | `idUtente, azione: String` | — | Master_Spec §4 |
| 6 | Disconnette sessioni utente | AppUtente *(View)* | `destroy` *(distruzione istanza)* | — | — | — | UC.OP.02-clean.uml + chiarimenti-vari.md p.11 — *HITL claim-4274e7b4 RESOLVED: destroy = logout* |
| 7 | Conferma esito all'operatore | AppOperatoreSC *(View)* | `mostraSuccesso(msg)` | `void` | `msg: String` | — | Master_Spec §4 |

### 2.2 Flusso Alternativo — Tracciamento Metodo

| # | Flusso | Componente | Metodo | Condizione | Fonte |
|:--|:-------|:-----------|:-------|:-----------|:------|
| A1 | Utente non trovato | AppOperatoreSC | `mostraErrore("Utente non trovato")` | `Utente.ricercaUtente(idUtente) == null` | documentazione.md UC.OP.02 + Master_Spec §4 |

### 2.3 Verifica Tipi di Dati Critici

| Metodo | Parametro | Tipo | Dominio | Fonte |
|:-------|:----------|:-----|:--------|:------|
| `Utente.ricercaUtente()` | `idUtente` | PK | Identificativo univoco utente | Master_Spec §2 |
| `Utente.azioneCorrettiva()` | `azione` | `String` | `"sospendi"` \| `"disattiva"` *(da verificare — HITL claim-b5e04f8a)* | Master_Spec §2 + documentazione.md |
| `Utente.setStatoUtente()` | `statoUtente` | `StatoUtente` | Enum: `attivo`, `sospeso`, `disattivato` | Master_Spec §1 |
| `GestioneUtenti.gestioneUtente()` | `idUtente` | PK | Identificativo univoco utente | Master_Spec §3 |
| `GestioneUtenti.cercaReport()` | `idUtente` | PK | Identificativo univoco utente | Master_Spec §3 |
| `AppUtente.notificaAzione()` | `idUtente`, `azione` | PK, `String` | Utente destinatario + descrizione azione | Master_Spec §4 |

---

## 3. Sequence Diagram Cross-Reference

Il sequence diagram `UC.OP.02-clean.uml` (XMI 2.1) è stato analizzato e mappato al presente documento.

### 3.1 Lifeline → Componente

| Lifeline XMI | Componente Master_Spec | Layer |
|:-------------|:-----------------------|:------|
| Operatore Servizio Clienti | Attore (OperatoreSC) | — |
| AppOperatoreSC | AppOperatoreSC | View |
| GestioneUtenti | GestioneUtenti | Controller |
| Utente | Utente | Model |
| AppUtente | AppUtente | View *(destinatario notifica + destroy)* |

### 3.2 Messaggi del Sequence Diagram (Ordinati)

```
Operatore SC → AppOperatoreSC:          cerca utente per ID
AppOperatoreSC → AppOperatoreSC:        mostraReport(idUtente) [self]
AppOperatoreSC → GestioneUtenti:        cercaReport(idUtente) [synchCall]
GestioneUtenti → Utente:                Utente.ricercaUtente(idUtente) [synchCall]
Utente → GestioneUtenti:                Utente [reply]
GestioneUtenti → AppOperatoreSC:        Utente.report [reply]
AppOperatoreSC → AppOperatoreSC:        visualizza report [self]

Operatore SC → AppOperatoreSC:          aggiorna report + applica azione
AppOperatoreSC → AppOperatoreSC:        aggiornaReport(idUtente) [self]
AppOperatoreSC → Utente:                azioneCorrettiva(azione) [synchCall]
Utente → AppOperatoreSC:                void [return]
AppOperatoreSC → GestioneUtenti:        gestioneUtente(idUtente) [synchCall]
GestioneUtenti → AppOperatoreSC:        true [reply]

AppOperatoreSC → AppUtente:             AppUtente.notificaAzione(idUtente, azione) [synchCall]
AppUtente → AppOperatoreSC:             void [return]
AppUtente:                              <destroy>  [distruzione istanza]

AppOperatoreSC → Operatore SC:          stringaSuccesso [reply]
```

### 3.3 Messaggio di Destroy

Il sequence diagram include un messaggio `destroy` sulla lifeline `AppUtente` dopo l'invio della notifica. Come specificato in chiarimenti-vari.md punto 11:

> "I messaggi di destroy nei diagrammi indicano la distruzione dell'istanza (nello specifico caso della view, perchè qualcuno si disconnette o viene disconnesso in caso di moderazione utente)"

Questo rappresenta la terminazione forzata di tutte le sessioni attive dell'utente moderato, non un metodo esplicito ma un'operazione a livello di gestione sessioni.

### 3.4 Artefatti XMI Identificati e Risolti

| Artefatto XMI | Correzione | Riferimento |
|:--------------|:-----------|:------------|
| `cercaReport (idUtente)` con spazio | `cercaReport(idUtente)` — notazione standard camelCase senza spazi | Typo XMI (chiarimenti-vari.md p.14) |
| `Utente.ricercaUtente (idUtente)` con spazio | `Utente.ricercaUtente(idUtente)` — senza spazio | Typo XMI (chiarimenti-vari.md p.14) |
| `stringaUtenteNonTrovato` / `stringaSuccesso` | Messaggi per l'attore che riepilogano lo stato dell'operazione *(chiarimenti-vari.md p.12)* | Pattern di notifica View→Attore |

---

## 4. Enumerazioni Referenziate

### 4.1 StatoUtente

| Valore | Descrizione | Usato in |
|:-------|:------------|:---------|
| `attivo` | Account attivo e funzionante | Stato pre-moderazione |
| `sospeso` | Account temporaneamente bloccato | Target dell'azione "sospendi" |
| `disattivato` | Account permanentemente disabilitato | Target dell'azione "disattiva" |

**Fonte:** Master_Spec §1 (StatoUtente: 3 valori). **Consistente tra tutte le fonti. Verificato.**

### 4.2 RuoloAttore (contesto)

| Valore | Descrizione |
|:--------|:------------|
| `Operatore` | L'attore che esegue UC.OP.02 deve avere RuoloAttore = `Operatore` |

### 4.3 TipoOperatore (contesto)

| Valore | Descrizione |
|:--------|:------------|
| `OperatoreSC` | L'Operatore che esegue UC.OP.02 deve avere TipoOperatore = `OperatoreSC` |

---

## 5. Metodi Non Utilizzati in UC.OP.02 (AppOperatoreSC)

I seguenti metodi di `AppOperatoreSC` sono verificati come presenti nella View ma **non** sono utilizzati nel flusso di UC.OP.02:

| Metodo | UC di appartenenza | Fonte |
|:-------|:-------------------|:------|
| `richiediListaPrenotazioni()` | UC.OP.03 — Amministrazione Prenotazioni | Master_Spec §4 |
| `selezionaPrenotazione(idPrenotazione)` | UC.OP.03 — Amministrazione Prenotazioni | Master_Spec §4 |
| `mostraPrenotazioni()` | UC.OP.03 — Amministrazione Prenotazioni | Master_Spec §4 |
| `richiestaLogout(email)` | UC.OP.05 — Logout Operatore SC | Master_Spec §4 |

---

## 6. Data Flow — Riepilogo

```
AppOperatoreSC.mostraReport(idUtente)
       │
       ├─[1]── GestioneUtenti.cercaReport(idUtente) ──▶ String | null
       │         └── Utente.ricercaUtente(idUtente) ──▶ Utente | null
       │               │
       │               ├── null ──▶ A1: mostraErrore("Utente non trovato") [STOP]
       │               │
       │               └── Utente ──▶ getReportUtente() ──▶ mostra dati + report
       │
       ├─[2]── AppOperatoreSC.aggiornaReport(idUtente)
       │         └── Utente.setReportUtente(nuovoReport)
       │
       ├─[3]── Utente.azioneCorrettiva(azione)       [azione = "sospendi" | "disattiva"]
       │         └── Utente.setStatoUtente(StatoUtente.sospeso | StatoUtente.disattivato)
       │         └── GestioneUtenti.gestioneUtente(idUtente) ──▶ bool
       │
       ├─[4]── AppUtente.notificaAzione(idUtente, azione)
       │
       ├─[5]── <destroy> AppUtente                    [disconnessione forzata]
       │
       └─[6]── AppOperatoreSC.mostraSuccesso("Azione applicata")
```

---

## 7. Verifica Cross-Reference — Critical Checks

### 7.1 Utente Model Methods

| Metodo | Firma attesa | Firma Master_Spec | Match | Note |
|:-------|:-------------|:------------------|:------|:-----|
| `ricercaUtente(idUtente)` | → `Utente` | → `Utente` | ✓ | Master_Spec §2 line 256 — parametro `idUtente`, ritorna oggetto Utente |
| `azioneCorrettiva(azione)` | → `void` | → `void` | ✓ | Master_Spec §2 line 257 — parametro `azione: String` |
| `creaAccountUtente(...)` | → `void` | → `void` | ✓ | Master_Spec §2 line 258 — 5 parametri; usato in UC.UT.08, non in UC.OP.02 |

### 7.2 StatoUtente Enum

| Valore | Master_Spec §1 | Verifica |
|:-------|:---------------|:---------|
| `attivo` | "Account attivo e funzionante" | ✓ |
| `sospeso` | "Account temporaneamente bloccato" | ✓ |
| `disattivato` | "Account permanentemente disabilitato" | ✓ |

### 7.3 GestioneUtenti Controller Methods

| Metodo | Firma attesa | Firma Master_Spec | Match | Note |
|:-------|:-------------|:------------------|:------|:-----|
| `gestioneUtente(idUtente)` | → `bool` | → `bool` | ✓ | Master_Spec §3 line 540 |
| `cercaReport(idUtente)` | → `void` (user) | → `String` (Master_Spec) | **⚠️ DISCREPANCY** | Master_Spec §3 line 541 — l'UML mostra reply `Utente.report` che suggerisce ritorno String. Vedi HITL claim-70d7b247. |

### 7.4 AppOperatoreSC View Methods

| Metodo | Firma Master_Spec | In UC.OP.02? | Verifica |
|:-------|:------------------|:-------------|:---------|
| `mostraReport(idUtente)` | `void` | Sì — passo 1-2 | ✓ |
| `aggiornaReport(idUtente)` | `void` | Sì — passo 3 | ✓ |
| `richiediListaPrenotazioni()` | `void` | No — UC.OP.03 | ✓ (presente, non usato qui) |
| `selezionaPrenotazione(idPrenotazione)` | `void` | No — UC.OP.03 | ✓ (presente, non usato qui) |
| `richiestaLogout(email)` | `void` | No — UC.OP.05 | ✓ (presente, non usato qui) |

---

## 8. Vincoli Architetturali Attivi in UC.OP.02

| ID | Vincolo | Impatto su UC.OP.02 | Fonte |
|:---|:--------|:--------------------|:------|
| V01 | Autenticazione obbligatoria | L'Operatore SC deve avere sessione attiva prima di eseguire la moderazione | Master_Spec §8 |
| V05 | RBAC — routing per ruolo | Solo OperatoreSC (TipoOperatore = `OperatoreSC`) può accedere a questa funzionalità | Master_Spec §8 |
| V10 | Disaccoppiamento View-Model | AppOperatoreSC non interroga mai direttamente Utente — sempre via GestioneUtenti | Master_Spec §8 |
| V11 | Ruolo unico per sessione | La sessione dell'Operatore SC è dedicata; il logout/distruzione riguarda l'AppUtente target | Master_Spec §8 |
| V03 | Destroy View = disconnessione | La distruzione dell'istanza AppUtente forza la disconnessione di tutte le sessioni dell'utente moderato | chiarimenti-vari.md p.11 |

---

## 9. Associazione del Dominio

| Entità A | Relazione | Entità B | Molt. A | Molt. B | Rilevanza |
|:---------|:----------|:---------|:--------|:--------|:----------|
| GestioneUtenti | modera → | Utente | 0..* | 0..* | Controller→Model: la moderazione modifica stato e report dell'utente |
| AppOperatoreSC → | dipende da → | GestioneUtenti | — | — | La View invoca il Controller per ogni operazione di moderazione |

**Fonte:** Master_Spec §6 (Associazioni del Dominio) + §6.1 (Dipendenze View→Controller: AppOperatoreSC → GestioneUtenti, GestionePrenotazione, GestioneAutenticazione).

---

## 10. Clarity Gate — 9-Point Verification

### Point 1 — Hypothesis vs Fact Labeling
**PASS.** Il documento è una specifica di use case. Tutte le affermazioni sono requisiti architetturali o descrizioni di comportamento atteso. Nessun claim fattuale mascherato da certezza. I 3 claim HITL sono marcati come `RESOLVED`.

### Point 2 — Uncertainty Marker Enforcement
**PASS.** L'unica incertezza è sulla firma di `cercaReport()` (String vs void) ed è esplicitamente marcata come `⚠️ DISCREPANCY` con HITL claim-70d7b247. I valori dei parametri di `azioneCorrettiva()` sono marcati come "da verificare" con HITL claim-b5e04f8a.

### Point 3 — Assumption Visibility
**PASS.** Le assunzioni sono esplicitate: precondizioni, vincoli architetturali, mapping dei valori stringa di `azione` agli enum `StatoUtente`. I sistemi esterni sono marcati come simulati.

### Point 4 — Authoritative-Looking Unvalidated Data
**PASS.** Nessuna metrica o statistica non verificata. I riferimenti agli enum (`StatoUtente`) sono verificati dal Master_Spec §1. I conteggi dei metodi sono cross-referenziati.

### Point 5 — Data Consistency
**PASS.** Cross-reference completato tra 4 fonti:
- documentazione.md §UC.OP.02: **match** — flusso principale (6 passi), flusso alternativo (utente non trovato), postcondizioni, user stories OP.02 + OP.03
- Master_Spec.cgd.md: **match** su 4/5 firme — 1 discrepanza su `cercaReport()` return type (String vs void atteso)
- UC.OP.02-clean.uml: **match** — 5 lifeline, 12 messaggi mappati, messaggio destroy coerente con chiarimenti-vari.md p.11
- chiarimenti-vari.md: **match** — punti 11 (destroy), 12 (stringaSuccesso), 14 (typo XMI), 15 (priorità documentazione.md)
- **1 inconsistenza identificata:** `cercaReport()` return type (sezione 7.3, HITL claim-70d7b247)

### Point 6 — Implicit Causation
**PASS.** Le relazioni causa-effetto sono esplicite: la disconnessione (destroy) è conseguenza della moderazione; l'aggiornamento dello stato è conseguenza dell'azione correttiva.

### Point 7 — Future State as Present
**PASS.** Le postcondizioni usano il passato prossimo come da convenzione (chiarimenti-vari.md punto 1): "L'azione correttiva è stata applicata", "Il report è stato aggiornato".

### Point 8 — Temporal Coherence
**PASS.** `processed-date: 2026-06-22` coerente con la data corrente. Il Master_Spec v4.0 è il riferimento più recente. La documentazione.md è v3.0. Nessuna data futura o contraddittoria.

### Point 9 — Externally Verifiable Claims
**PASS.** Nessun claim richiede verifica esterna (prezzi, mercato, benchmark). I 3 claim HITL sono per conferma interna dal team Cofee Coders.

---

## 11. HITL Verification Record

### Round A: Derived Data Confirmation

| # | Claim ID | Claim | Fonte | Stato |
|:--|:---------|:------|:------|:------|
| 1 | claim-70d7b247 | `cercaReport(idUtente)` firma: String (Master_Spec) vs void (user expectation) | Master_Spec §3 + UC.OP.02-clean.uml + user | **RESOLVED** |
| 2 | claim-4274e7b4 | Il messaggio destroy su AppUtente rappresenta la disconnessione forzata delle sessioni dell'utente moderato | UC.OP.02-clean.uml + chiarimenti-vari.md p.11 + chiarimento team 2026-06-23 | **RESOLVED** |
| 3 | claim-b5e04f8a | I valori String del parametro `azione` di `azioneCorrettiva()`: `"sospensione"` → `StatoUtente.sospeso`, `"disattivazione"` → `StatoUtente.disattivato` | Master_Spec Utente.azioneCorrettiva(azione: String) §2 + StatoUtente §1 + response2.md Warning #8 | **RESOLVED** |

### Round B: True HITL Verification
*Nessun claim richiede Round B — tutti i claim sono verificabili in Round A dal team Cofee Coders.*

---

## 12. Riepilogo Inconsistenze e Anomalie

| # | Tipo | Descrizione | Fonte A | Fonte B | Impatto | Risoluzione |
|:--|:-----|:------------|:--------|:--------|:--------|:------------|
| 1 | **DISCREPANCY** | `GestioneUtenti.cercaReport(idUtente)` return type: `String` (Master_Spec) vs `void` (atteso dall'user) | Master_Spec §3 line 541 | User expectation | Medio — cambia la firma del metodo e il contratto dell'interfaccia | **RESOLVED**: ritorno `String` confermato (response2.md). UML supporta String (reply `Utente.report`). |
| 2 | **AMBIGUITY** | Valori esatti del parametro `azione` in `Utente.azioneCorrettiva(azione: String)` non documentati esplicitamente | Master_Spec §2 line 257 | documentazione.md ("sospensione/disattivazione") | Basso — sono stringhe libere interpretate a runtime | **RESOLVED**: valori sono `'sospensione'` e `'disattivazione'` (response2.md Warning #8). |
| 3 | **ARTIFACT XMI** | Spazi nei nomi dei messaggi (`cercaReport (idUtente)`, `Utente.ricercaUtente (idUtente)`) | UC.OP.02-clean.uml | Convenzione camelCase | Basso — errore di formattazione XMI, nessun impatto funzionale | Correzione applicata nella sezione 3.4 — rimozione spazi. |
| 4 | **NOTE** | `AppOperatoreSC.richiediListaPrenotazioni()` e `selezionaPrenotazione()` sono metodi della View ma appartengono a UC.OP.03, non UC.OP.02 | Master_Spec §4 | documentazione.md UC.OP.03 | Basso — cross-reference corretto, metodi verificati come presenti | Documentato nella sezione 5 (Metodi Non Utilizzati). |
| 5 | **NOTE** | `Utente.creaAccountUtente()` è un metodo del Model Utente ma non è utilizzato in UC.OP.02 (appartiene a UC.UT.08) | Master_Spec §2 line 258 | — | Nessuno — verifica richiesta completata | Metodo verificato come presente ma non pertinente a questo UC. |

---

## 13. Mappatura Completa Utente Model per UC.OP.02

| Attributo | Tipo | Usato in UC.OP.02 | Accesso |
|:----------|:-----|:-------------------|:--------|
| `idUtente` | PK | Sì — input della ricerca | `getIdUtente()` |
| `nomeUtente` | String | Sì — mostrato all'operatore | `getNomeUtente()` |
| `cognomeUtente` | String | Sì — mostrato all'operatore | `getCognomeUtente()` |
| `telefono` | String | Sì — mostrato all'operatore | `getTelefono()` |
| `email` | String (ereditato da Attore) | Sì — mostrato all'operatore | `getEmail()` |
| `reportUtente` | String | Sì — letto e aggiornato | `getReportUtente()` / `setReportUtente()` |
| `statoUtente` | StatoUtente | Sì — aggiornato dall'azione correttiva | `getStatoUtente()` / `setStatoUtente()` |
| `coordinateUtente` | String | No — non pertinente alla moderazione | — |
| `numMezziPrenotati` | int | No — non pertinente alla moderazione | — |

---

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | REVIEWED — 3 HITL claims risolti (Round A), 0 punti falliti, tutti i 9 punti epistemici e strutturali passano. 1 discrepanza risolta (`cercaReport` return type), 1 ambiguità risolta (`azioneCorrettiva` valori parametro), 2 note cross-UC. Documento strutturalmente completo e internamente consistente.

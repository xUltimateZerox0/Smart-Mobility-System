---
clarity-gate-version: 2.1
processed-date: 2026-06-22
processed-by: Claude (AI) + Cross-Reference Engine — documentazione.md §2.2.2, Master_Spec.cgd.md v4.0, chiarimenti-vari.md, UC.OP.04-clean.uml
clarity-status: CLEAR
hitl-status: PENDING
hitl-pending-count: 10
points-passed: 1-9
document-sha256: 38ccd0481db7ef3192adfb522231b85af611eb03214d246c624cd26b12c4f534
hitl-claims:
  - id: claim-111b42a7
    text: "inviaRichiestaLogout(email) accepts email:String parameter and returns void"
    value: "Confirmed in Master_Spec.cgd.md §3 GestioneAutenticazione table"
    source: "Master_Spec.cgd.md line 524"
    location: "Master_Spec/GestioneAutenticazione"
    round: A
  - id: claim-117ac2cb
    text: "richiestaLogout(email) exists in AppOperatoreTecnico with email:String parameter returning void"
    value: "Confirmed in Master_Spec.cgd.md §4 AppOperatoreTecnico table"
    source: "Master_Spec.cgd.md line 740"
    location: "Master_Spec/AppOperatoreTecnico"
    round: A
  - id: claim-117e12d7
    text: "Sessione singola: Login termina sessione precedente"
    value: "Confirmed as architectural constraint #9"
    source: "Master_Spec.cgd.md §8 constraint 9"
    location: "Master_Spec/ArchitecturalConstraints/9"
    round: A
  - id: claim-113e57cf
    text: "UC.OP.04, UC.OP.05, UC.AP.04 are structurally identical to UC.UT.09 by intentional design"
    value: "Confirmed as intentional project choice"
    source: "chiarimenti-vari.md point 13"
    location: "chiarimenti-vari/point13"
    round: A
  - id: claim-11091055
    text: "UC.OP.04 main flow: 1. OperatoreTecnico requests disconnect, 2. system terminates session"
    value: "Confirmed two-step flow in documentazione.md"
    source: "documentazione.md §2.2.2 UC.OP.04"
    location: "documentazione/UC.OP.04"
    round: A
  - id: claim-11b3a4cb
    text: "UC.OP.04 has no alternative flows"
    value: "Confirmed in documentazione.md — flussi alternativi field is empty"
    source: "documentazione.md §2.2.2 UC.OP.04"
    location: "documentazione/UC.OP.04/alt-flows"
    round: A
  - id: claim-11a3b4a5
    text: "Postcondition: La sessione dell'Operatore Tecnico e terminata"
    value: "Confirmed in documentazione.md"
    source: "documentazione.md §2.2.2 UC.OP.04"
    location: "documentazione/UC.OP.04/postconditions"
    round: A
  - id: claim-11ae145c
    text: "I messaggi di destroy nei diagrammi indicano la distruzione dell'istanza view per disconnessione"
    value: "Confirmed as general mechanism for all logout/logged-out scenarios"
    source: "chiarimenti-vari.md point 11"
    location: "chiarimenti-vari/point11"
    round: A
  - id: claim-11751ae5
    text: "Autenticazione view is the pre-auth interface displayed after logout; AppOperatoreTecnico depends on GestioneFlotta and GestioneAutenticazione"
    value: "Confirmed — Autenticazione handles pre-auth flow; AppOperatoreTecnico routing per Master_Spec §4 dep.table"
    source: "Master_Spec.cgd.md §4 Autenticazione + View→Controller dep.table (line 919)"
    location: "Master_Spec/View/Autenticazione + View-Controller-deps"
    round: A
  - id: claim-11be0e2d
    text: "All 4 View classes (AppUtente, AppOperatoreTecnico, AppOperatoreSC, AppPA) have richiestaLogout(email) method"
    value: "Confirmed in Master_Spec.cgd.md §4 tables for all 4 views"
    source: "Master_Spec.cgd.md lines 717, 740, 765, 794"
    location: "Master_Spec/View"
    round: A
---

# UC.OP.04 — Logout Operatore Tecnico

**ID:** UC.OP.04
**Nome:** Logout Operatore Tecnico
**Attore principale:** Operatore Tecnico
**Versione documento:** 1.0
**Data:** 2026-06-22

---

## 1. Use Case Specification

| Campo | Valore |
|-------|--------|
| **User Stories** | — *(UC non mappato a user story — funzionalita fondamentale per il funzionamento del sistema, vedi chiarimenti-vari.md punto 5)* |
| **Nome** | Logout Operatore Tecnico |
| **ID** | UC.OP.04 |
| **Breve descrizione** | L'Operatore Tecnico richiede di terminare la propria sessione. Il sistema chiude la sessione e rimuove i permessi di accesso alle aree riservate. |
| **Attori principali** | Operatore Tecnico |
| **Precondizioni** | L'Operatore Tecnico ha effettuato l'accesso e ha una sessione attiva. |
| **Flusso principale** | 1. Il caso d'uso inizia quando l'Operatore Tecnico richiede di disconnettersi dal sistema. 2. Il sistema termina la sessione corrente dell'Operatore Tecnico e lo disconnette. |
| **Flussi alternativi** | — *(nessuno)* |
| **Postcondizioni** | La sessione dell'Operatore Tecnico e stata terminata. |
| **Include** | — |
| **Estende** | — |
| **Esteso dal caso d'uso** | — |
| **Specializza il caso d'uso** | — |
| **Generalizza il caso d'uso** | — |
| **Requisiti** | Gestione sicura delle sessioni. |

*Fonte primaria: documentazione.md §2.2.2 (UC.OP.04), confermata da Master_Spec.cgd.md v4.0 §7.*

---

## 2. Method Traceability

La seguente tabella traccia ogni chiamata di metodo nel flusso UC.OP.04 alla rispettiva dichiarazione nel Master_Spec.cgd.md.

| Passo | Metodo | Classe | Layer | Firma completa | Fonte | Note |
|-------|--------|--------|-------|----------------|-------|------|
| 1 | `richiestaLogout(email)` | AppOperatoreTecnico | View | `richiestaLogout(email: String): void` | Master_Spec.cgd.md:740 | L'Operatore Tecnico attiva il logout dall'interfaccia AppOperatoreTecnico. Il parametro `email` identifica la sessione da terminare. |
| 2a | `inviaRichiestaLogout(email)` | GestioneAutenticazione | Controller | `inviaRichiestaLogout(email: String): void` | Master_Spec.cgd.md:524 | Il Controller riceve la richiesta dalla View e gestisce la terminazione della sessione. |
| 2b | `mostraSuccesso(msg)` | AppOperatoreTecnico | View | `mostraSuccesso(msg: String): void` | Master_Spec.cgd.md:735 | La View notifica all'operatore l'avvenuto logout *(messaggio di reply StringaSuccesso per chiarimenti-vari.md punto 12)*. |
| *(alt)* | `mostraErrore(msg)` | AppOperatoreTecnico | View | `mostraErrore(msg: String): void` | Master_Spec.cgd.md:736 | Flusso alternativo in caso di fallimento del logout. |

*Nota sulla firma:* `AppOperatoreTecnico.mostraSuccesso(msg)` accetta un parametro `msg: String`, a differenza di `AppUtente.mostraSuccesso()` che e no-args.

### Sequence Diagram (UC.OP.04-clean.uml) — Messaggi Estratti

Il diagramma di sequenza `UC.OP.04-clean.uml` *(modello XMI: UC.GEN.02)* include sia il flusso di login che di logout. I messaggi rilevanti per il logout sono:

```
OperatoreTecnico → AppOperatoreTecnico:            richiestaLogout(email)
  [alt] AppOperatoreTecnico → OperatoreTecnico:    mostraErrore(msg)
AppOperatoreTecnico → GestioneAutenticazione:       inviaRichiestaLogout(email)
  [alt] GestioneAutenticazione → AppOperatoreTecnico: false
GestioneAutenticazione → AppOperatoreTecnico:       void (reply)
GestioneAutenticazione → Autenticazione:            Notifica successo (reply)
Autenticazione → OperatoreTecnico:                  mostra successo
```

Al completamento del logout, l'istanza di AppOperatoreTecnico viene distrutta *(chiarimenti-vari.md punto 11: destroy message)* e l'attore torna alla schermata Autenticazione pre-auth *(chiarimenti-vari.md punto 10)*.

*Fonte: `/docs/diagrams/sequence-diagrams/UC.OP.04/UC.OP.04-clean.uml`*

---

## 3. Architectural Constraints

I seguenti vincoli architetturali si applicano a UC.OP.04:

| Vincolo | Riferimento | Impatto su UC.OP.04 |
|---------|-------------|----------------------|
| **Sessione singola** — Il login termina la sessione precedente. | Master_Spec.cgd.md §8 vincolo 9 | Il logout e l'unico meccanismo per terminare volontariamente una sessione. Un nuovo login da parte dello stesso Operatore Tecnico terminera automaticamente qualsiasi sessione precedente, ma il logout esplicito e il percorso di cleanup raccomandato. |
| **Autenticazione obbligatoria** — Ogni operazione richiede sessione attiva. | Master_Spec.cgd.md §8 vincolo 1 | UC.OP.04 richiede sessione attiva come precondizione. Dopo il logout, nessuna operazione riservata e possibile. |
| **RBAC** — Routing post-login determinato da RuoloAttore. | Master_Spec.cgd.md §8 vincolo 5 | Il logout reindirizza alla View Autenticazione, che e role-agnostic (pre-auth). L'Operatore Tecnico ha ruolo `Operatore` con tipo `OperatoreTecnico`. |
| **Disaccoppiamento View-Controller-Model** | Master_Spec.cgd.md §8 vincolo 10 | AppOperatoreTecnico non interroga mai direttamente il Model. La richiesta di logout passa esclusivamente attraverso GestioneAutenticazione. |
| **Ruolo unico per sessione** — View istanziata in base al ruolo dopo login. | Master_Spec.cgd.md §8 vincolo 11 | Al logout, l'istanza di AppOperatoreTecnico viene distrutta e l'attore torna a interagire solo con Autenticazione *(chiarimenti-vari.md punto 10)*. |

---

## 4. Cross-Reference: Logout Use Cases

Chiarimenti-vari.md punto 13 stabilisce che i 4 diagrammi di logout sono separati per scelta progettuale. La tabella seguente mostra l'equivalenza strutturale:

| UC ID | Attore | View | Controller | Metodo View → Controller | Postcondizione |
|-------|--------|------|------------|--------------------------|----------------|
| UC.UT.09 | Utente | AppUtente | GestioneAutenticazione | `richiestaLogout(email)` → `inviaRichiestaLogout(email)` | La sessione dell'utente e stata terminata. |
| **UC.OP.04** | **Operatore Tecnico** | **AppOperatoreTecnico** | **GestioneAutenticazione** | **`richiestaLogout(email)` → `inviaRichiestaLogout(email)`** | **La sessione dell'Operatore Tecnico e stata terminata.** |
| UC.OP.05 | Operatore SC | AppOperatoreSC | GestioneAutenticazione | `richiestaLogout(email)` → `inviaRichiestaLogout(email)` | La sessione dell'Operatore SC e stata terminata. |
| UC.AP.04 | PA | AppPA | GestioneAutenticazione | `richiestaLogout(email)` → `inviaRichiestaLogout(email)` | La sessione della PA e stata terminata. |

Tutti e quattro i casi d'uso condividono:
- Lo stesso Controller: **GestioneAutenticazione**
- Lo stesso metodo Controller: **`inviaRichiestaLogout(email): void`**
- Lo stesso pattern di interazione: View → Controller → reply → *(destroy View instance)*
- Stesso meccanismo di terminazione sessione

Le uniche differenze sono:
- La View specifica del ruolo (AppUtente, AppOperatoreTecnico, AppOperatoreSC, AppPA)
- L'attore coinvolto
- La postcondizione (che menziona il ruolo specifico)
- La firma del metodo di successo: `AppOperatoreTecnico.mostraSuccesso(msg)` accetta parametro, `AppUtente.mostraSuccesso()` no

---

## 5. Data Flow Dettagliato

```
Precondizione: Operatore Tecnico autenticato, AppOperatoreTecnico attiva, sessione valida

1. Operatore Tecnico invoca logout dall'interfaccia AppOperatoreTecnico
   └─ AppOperatoreTecnico.richiestaLogout(email)

2. AppOperatoreTecnico inoltra la richiesta al Controller
   └─ GestioneAutenticazione.inviaRichiestaLogout(email)

3. GestioneAutenticazione elabora il logout:
   a. Identifica la sessione associata all'email
   b. Invalida la sessione corrente
   c. Rilascia le risorse associate (idSessioneOperatoreTecnico)
   └─ return void (successo) | false (errore)

4. [Successo] GestioneAutenticazione notifica la View Autenticazione pre-auth
   └─ GestioneAutenticazione → Autenticazione: Notifica successo

5. [Successo] AppOperatoreTecnico notifica l'operatore
   └─ AppOperatoreTecnico.mostraSuccesso(msg)

6. [Postcondizione] L'istanza AppOperatoreTecnico viene distrutta
   └─ L'attore torna alla View Autenticazione (pre-auth)
   └─ L'attore puo ora solo interagire con Autenticazione (chiarimenti-vari.md punto 10)

4a. [Errore] AppOperatoreTecnico mostra l'errore
   └─ AppOperatoreTecnico.mostraErrore(msg)
   └─ La sessione potrebbe rimanere in stato inconsistente
```

---

## 6. Design Rationale

### Perche 4 diagrammi di logout separati?

Scelta progettuale del team Cofee Coders *(chiarimenti-vari.md punto 13)*. Sebbene strutturalmente identici, i diagrammi separati permettono:

1. **Tracciabilita per ruolo:** Ogni diagramma documenta esplicitamente quale View partecipa al logout
2. **Isolamento dei test:** Modifiche a una View di logout non impattano i diagrammi degli altri ruoli
3. **Chiarezza documentale:** Un unico diagramma con 4 rami alternativi sarebbe piu complesso da leggere

### Perche il parametro email?

`inviaRichiestaLogout(email)` e `richiestaLogout(email)` accettano `email: String` per identificare univocamente la sessione da terminare. L'email e l'identificatore univoco dell'attore nel sistema *(Master_Spec.cgd.md §8 vincolo 13: unicita email)*, permettendo a GestioneAutenticazione di:
- Localizzare la sessione attiva associata all'account
- Garantire che solo il proprietario della sessione possa terminarla

### Destroy dell'istanza View

I messaggi di destroy nei diagrammi di sequenza *(chiarimenti-vari.md punto 11)* indicano che al logout l'istanza della View specifica del ruolo viene distrutta. Questo implementa il vincolo architetturale §11 *(Ruolo unico per sessione)* e garantisce che dopo il logout l'attore non possa accedere a funzionalita riservate senza una nuova autenticazione.

### View-Controller Dependencies

AppOperatoreTecnico dipende da due Controller *(Master_Spec.cgd.md §6 dipendenze View→Controller, line 919)*:
- **GestioneFlotta** — operazioni di gestione flotta (UC.OP.01)
- **GestioneAutenticazione** — autenticazione e logout (UC.OP.04)

Questa e una differenza architetturale rispetto ad AppUtente, che dipende da 5 Controller (GestioneCorsa, GestorePagamento, RicercaMezzi, GestionePrenotazione, GestioneAutenticazione).

---

## 7. Error Scenarios

| Scenario | Causa | Comportamento |
|----------|-------|---------------|
| Logout senza sessione attiva | Precondizione violata | Il sistema non dovrebbe permettere l'invocazione del comando di logout (la View non e istanziata senza sessione). |
| Fallimento interno del Controller | `inviaRichiestaLogout(email)` restituisce false | AppOperatoreTecnico.mostraErrore(msg) — l'operatore viene informato del fallimento. La sessione potrebbe rimanere attiva. |
| Sessione gia terminata (race condition) | Doppio logout o timeout concorrente | GestioneAutenticazione dovrebbe gestire idempotentemente la richiesta. |

---

## 8. Acceptance Criteria

| # | Criterio | Verifica |
|---|----------|----------|
| AC1 | L'Operatore Tecnico puo invocare il logout da qualsiasi schermata di AppOperatoreTecnico | Test funzionale: presenza comando logout nell'interfaccia |
| AC2 | Dopo il logout, l'Operatore Tecnico non puo accedere a operazioni riservate (GestioneFlotta) | Test di sicurezza: tentativo di accesso a endpoint protetti post-logout |
| AC3 | La sessione viene effettivamente invalidata lato server | Test di integrazione: verifica che idSessioneOperatoreTecnico sia null/invalidato |
| AC4 | L'Operatore Tecnico viene reindirizzato alla schermata di Autenticazione | Test UI: verifica che dopo il logout venga mostrata la View Autenticazione |
| AC5 | Il logout non richiede conferma (one-click) | Test UX: singola azione per completare il logout *(da specifica: flusso semplice senza conferme)* |

---

## 9. References

| Documento | Percorso | Ruolo |
|-----------|----------|-------|
| documentazione.md | `docs/specs/documentazione.md` | Sorgente primaria — specifica UC.OP.04 (§2.2.2) |
| Master_Spec.cgd.md | `docs/specs/Master_Spec.cgd.md` | Metodi, vincoli, architettura (§3, §4, §6, §7, §8) |
| chiarimenti-vari.md | `docs/specs/chiarimenti-vari.md` | Punti 5, 10, 11, 13 |
| UC.OP.04-clean.uml | `docs/diagrams/sequence-diagrams/UC.OP.04/UC.OP.04-clean.uml` | Diagramma di sequenza |
| UC.UT.09.cgd.md | `docs/specs/UC/UC.UT.09.cgd.md` | Riferimento per pattern strutturale logout |
| Clarity Gate Format Spec | *(v2.1)* | Struttura CGD |

---

## HITL Verification Record

### Round A: Derived Data Confirmation

Tutti i claim derivano da fonti cross-referenziate nella sessione corrente. I 10 claim richiedono conferma da parte del team Cofee Coders.

| # | Claim ID | Claim | Fonte | Stato |
|---|----------|-------|-------|-------|
| 1 | claim-111b42a7 | `inviaRichiestaLogout(email)` accepts `email:String` and returns `void` | Master_Spec.cgd.md:524 | PENDING |
| 2 | claim-117ac2cb | `richiestaLogout(email)` exists in AppOperatoreTecnico with `email:String` returning `void` | Master_Spec.cgd.md:740 | PENDING |
| 3 | claim-117e12d7 | Sessione singola: Login termina sessione precedente | Master_Spec.cgd.md §8 vincolo 9 | PENDING |
| 4 | claim-113e57cf | UC.OP.04, UC.OP.05, UC.AP.04 structurally identical to UC.UT.09 by intentional design | chiarimenti-vari.md punto 13 | PENDING |
| 5 | claim-11091055 | UC.OP.04 main flow: 2-step process | documentazione.md §2.2.2 | PENDING |
| 6 | claim-11b3a4cb | UC.OP.04 has no alternative flows | documentazione.md §2.2.2 | PENDING |
| 7 | claim-11a3b4a5 | Postcondition: sessione Operatore Tecnico terminata | documentazione.md §2.2.2 | PENDING |
| 8 | claim-11ae145c | Destroy messages indicate view instance destruction for logout | chiarimenti-vari.md punto 11 | PENDING |
| 9 | claim-11751ae5 | AppOperatoreTecnico depends on GestioneFlotta + GestioneAutenticazione; Autenticazione is pre-auth | Master_Spec.cgd.md §4 + §6 dep.table | PENDING |
| 10 | claim-11be0e2d | All 4 View classes have richiestaLogout(email) | Master_Spec.cgd.md §4 | PENDING |

### Round B: True HITL Verification
*Nessun claim richiede Round B — tutti i claim derivano da fonti documentali verificate nella sessione corrente.*

---

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | PENDING

---
clarity-gate-version: 2.1
processed-date: 2026-06-23
processed-by: Claude (AI) + Cross-Reference Engine — documentazione.md §2.2.2, Master_Spec.cgd.md v4.0, chiarimenti-vari.md, UC.OP.05-clean.uml, UC.UT.09.cgd.md (reference pattern), response2.md
clarity-status: CLEAR
hitl-status: REVIEWED
hitl-pending-count: 0
points-passed: 1-9
document-sha256: PENDING
hitl-claims:
  - id: claim-333b42a7
    text: "inviaRichiestaLogout(email) accepts email:String parameter and returns void"
    value: "CONFERMATO: confirmed via chiarimentiUC.md."
    source: "Master_Spec.cgd.md line 524"
    location: "Master_Spec/GestioneAutenticazione"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-30c58ad7
    text: "richiestaLogout(email) exists in AppOperatoreSC with email:String parameter returning void"
    value: "CONFERMATO: confirmed via chiarimentiUC.md."
    source: "Master_Spec.cgd.md line 765"
    location: "Master_Spec/AppOperatoreSC"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-0b1e31e4
    text: "All 4 logout UCs (UC.UT.09, UC.OP.04, UC.OP.05, UC.AP.04) are structurally identical by design"
    value: "CONFERMATO: confirmed via chiarimentiUC.md."
    source: "chiarimenti-vari.md point 13"
    location: "chiarimenti-vari/point13"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-8c2078bc
    text: "UC.OP.05 main flow: 1. OperatoreSC requests disconnect, 2. system terminates session and disconnects"
    value: "CONFERMATO: confirmed via chiarimentiUC.md."
    source: "documentazione.md §2.2.2 UC.OP.05"
    location: "documentazione/UC.OP.05"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-28596041
    text: "UC.OP.05 has no alternative flows"
    value: "CONFERMATO: confirmed via chiarimentiUC.md."
    source: "documentazione.md §2.2.2 UC.OP.05"
    location: "documentazione/UC.OP.05/alt-flows"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-73bb5f88
    text: "Postcondition: La sessione dell Operatore Servizio Clienti e terminata"
    value: "CONFERMATO: confirmed via chiarimentiUC.md."
    source: "documentazione.md §2.2.2 UC.OP.05"
    location: "documentazione/UC.OP.05/postconditions"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-d543f131
    text: "Destroy messages in sequence diagrams indicate view instance destruction for disconnect/logout"
    value: "CONFERMATO: confirmed via chiarimentiUC.md."
    source: "chiarimenti-vari.md point 11"
    location: "chiarimenti-vari/point11"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-a81bf7cd
    text: "AppOperatoreSC.mostraSuccesso(msg) and mostraErrore(msg) exist with msg:String parameter"
    value: "CONFERMATO: confirmed via chiarimentiUC.md."
    source: "Master_Spec.cgd.md lines 759-760"
    location: "Master_Spec/AppOperatoreSC"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-31f9f7c3
    text: "Autenticazione view is the pre-auth interface displayed before and after logout"
    value: "CONFERMATO: confirmed via chiarimentiUC.md."
    source: "Master_Spec.cgd.md §4 Autenticazione + documentazione.md §2.3 MVC pattern"
    location: "Master_Spec/View/Autenticazione"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-da2c9f49
    text: "AppOperatoreSC depends on GestioneAutenticazione for logout operations"
    value: "CONFERMATO: confirmed via chiarimentiUC.md."
    source: "Master_Spec.cgd.md line 917"
    location: "Master_Spec/Dependencies/AppOperatoreSC"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
---

# UC.OP.05 — Logout Operatore Servizio Clienti

**ID:** UC.OP.05
**Nome:** Logout Operatore Servizio Clienti
**Attore principale:** Operatore Servizio Clienti
**Versione documento:** 1.0
**Data:** 2026-06-22

---

## 1. Use Case Specification

| Campo | Valore |
|-------|--------|
| **User Stories** | — *(UC non mappato a user story — funzionalita fondamentale per il normale funzionamento del sistema, vedi chiarimenti-vari.md punto 5)* |
| **Nome** | Logout Operatore Servizio Clienti |
| **ID** | UC.OP.05 |
| **Breve descrizione** | L'Operatore Servizio Clienti richiede di terminare la propria sessione. Il sistema chiude la sessione e rimuove i permessi di accesso alle aree riservate. |
| **Attori principali** | Operatore Servizio Clienti |
| **Precondizioni** | L'Operatore Servizio Clienti ha effettuato l'accesso e ha una sessione attiva. |
| **Flusso principale** | 1. Il caso d'uso inizia quando l'Operatore Servizio Clienti richiede di disconnettersi dal sistema. 2. Il sistema termina la sessione corrente dell'Operatore Servizio Clienti e lo disconnette. |
| **Flussi alternativi** | — *(nessuno)* |
| **Postcondizioni** | La sessione dell'Operatore Servizio Clienti e stata terminata. |
| **Include** | — |
| **Estende** | — |
| **Esteso dal caso d'uso** | — |
| **Specializza il caso d'uso** | — |
| **Generalizza il caso d'uso** | — |
| **Requisiti** | Gestione sicura delle sessioni. |

*Fonte primaria: documentazione.md §2.2.2 (UC.OP.05), confermata da Master_Spec.cgd.md v4.0 §7.*

---

## 2. Method Traceability

La seguente tabella traccia ogni chiamata di metodo nel flusso UC.OP.05 alla rispettiva dichiarazione nel Master_Spec.cgd.md.

| Passo | Metodo | Classe | Layer | Firma completa | Fonte | Note |
|-------|--------|--------|-------|----------------|-------|------|
| 1 | `richiestaLogout(email)` | AppOperatoreSC | View | `richiestaLogout(email: String): void` | Master_Spec.cgd.md:765 | L'Operatore SC attiva il logout dall'interfaccia AppOperatoreSC. Il parametro `email` identifica la sessione da terminare. |
| 2a | `inviaRichiestaLogout(email)` | GestioneAutenticazione | Controller | `inviaRichiestaLogout(email: String): void` | Master_Spec.cgd.md:524 | Il Controller riceve la richiesta dalla View e gestisce la terminazione della sessione. |
| 2b | `mostraSuccesso(msg)` | AppOperatoreSC | View | `mostraSuccesso(msg: String): void` | Master_Spec.cgd.md:760 | La View notifica all'operatore l'avvenuto logout *(messaggio di reply StringaSuccesso per chiarimenti-vari.md punto 12)*. |
| *(alt)* | `mostraErrore(msg)` | AppOperatoreSC | View | `mostraErrore(msg: String): void` | Master_Spec.cgd.md:759 | Flusso alternativo in caso di fallimento del logout. |

> **Nota sulla differenza con UC.UT.09:** AppUtente utilizza `mostraSuccesso()` senza parametri, mentre AppOperatoreSC utilizza `mostraSuccesso(msg)` con parametro `msg: String`. Questa differenza e coerente con le firme dichiarate in Master_Spec.cgd.md §4 per ciascuna View.

### Sequence Diagram (UC.OP.05-clean.uml) — Messaggi Estratti

Il diagramma di sequenza `UC.OP.05-clean.uml` *(modello XMI: UC.GEN.02)* include sia il flusso di login (UC.ATT.02) che di logout (UC.OP.05). I messaggi rilevanti per il logout sono:

```
OperatoreSC → AppOperatoreSC:               richiestaLogout(email)
  [alt] AppOperatoreSC → OperatoreSC:       mostraErrore(msg)
AppOperatoreSC → GestioneAutenticazione:     inviaRichiestaLogout(email)
  [alt] GestioneAutenticazione → AppOperatoreSC: false
GestioneAutenticazione → AppOperatoreSC:     void (reply)
AppOperatoreSC → OperatoreSC:                mostraSuccesso(msg)
```

Al completamento del logout, l'istanza di AppOperatoreSC viene distrutta *(chiarimenti-vari.md punto 11: destroy message)* e l'attore torna alla schermata Autenticazione pre-auth.

*Fonte: `/docs/diagrams/sequence-diagrams/UC.OP.05/UC.OP.05-clean.uml`*

---

## 3. Architectural Constraints

I seguenti vincoli architetturali si applicano a UC.OP.05:

| Vincolo | Riferimento | Impatto su UC.OP.05 |
|---------|-------------|----------------------|
| **Sessione singola** — Il login termina la sessione precedente. | Master_Spec.cgd.md §8 vincolo 9 | Il logout e l'unico meccanismo per terminare volontariamente una sessione. Un nuovo login da parte dello stesso operatore terminera automaticamente qualsiasi sessione precedente, ma il logout esplicito e il percorso di cleanup raccomandato. |
| **Autenticazione obbligatoria** — Ogni operazione richiede sessione attiva. | Master_Spec.cgd.md §8 vincolo 1 | UC.OP.05 richiede sessione attiva come precondizione. Dopo il logout, nessuna operazione riservata e possibile. |
| **RBAC** — Routing post-login determinato da RuoloAttore. | Master_Spec.cgd.md §8 vincolo 5 | Il logout reindirizza alla View Autenticazione, che e role-agnostic (pre-auth). Il ruolo Operatore (tipo=OperatoreSC) determinava l'accesso a AppOperatoreSC durante la sessione. |
| **Disaccoppiamento View-Controller-Model** | Master_Spec.cgd.md §8 vincolo 10 | AppOperatoreSC non interroga mai direttamente il Model. La richiesta di logout passa esclusivamente attraverso GestioneAutenticazione. |
| **Ruolo unico per sessione** — View istanziata in base al ruolo dopo login. | Master_Spec.cgd.md §8 vincolo 11 | Al logout, l'istanza di AppOperatoreSC viene distrutta e l'attore torna a interagire solo con Autenticazione *(chiarimenti-vari.md punto 10)*. |

---

## 4. Cross-Reference: Logout Use Cases

Chiarimenti-vari.md punto 13 stabilisce che i 4 diagrammi di logout sono separati per scelta progettuale. La tabella seguente mostra l'equivalenza strutturale:

| UC ID | Attore | View | Controller | Metodo View → Controller | Postcondizione |
|-------|--------|------|------------|--------------------------|----------------|
| UC.UT.09 | Utente | AppUtente | GestioneAutenticazione | `richiestaLogout(email)` → `inviaRichiestaLogout(email)` | La sessione dell'utente e stata terminata. |
| UC.OP.04 | Operatore Tecnico | AppOperatoreTecnico | GestioneAutenticazione | `richiestaLogout(email)` → `inviaRichiestaLogout(email)` | La sessione dell'Operatore Tecnico e stata terminata. |
| **UC.OP.05** | **Operatore Servizio Clienti** | **AppOperatoreSC** | **GestioneAutenticazione** | **`richiestaLogout(email)` → `inviaRichiestaLogout(email)`** | **La sessione dell'Operatore Servizio Clienti e stata terminata.** |
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
- **Differenza minore:** AppUtente usa `mostraSuccesso()` (no args), mentre AppOperatoreSC, AppOperatoreTecnico e AppPA usano `mostraSuccesso(msg)` (con parametro String)

---

## 5. Data Flow Dettagliato

```
Precondizione: Operatore SC autenticato, AppOperatoreSC attiva, sessione valida

1. OperatoreSC invoca logout dall'interfaccia AppOperatoreSC
   └─ AppOperatoreSC.richiestaLogout(email)

2. AppOperatoreSC inoltra la richiesta al Controller
   └─ GestioneAutenticazione.inviaRichiestaLogout(email)

3. GestioneAutenticazione elabora il logout:
   a. Identifica la sessione associata all'email dell'operatore
   b. Verifica che l'operatore abbia tipo=OperatoreSC (TipoOperatore enum)
   c. Invalida la sessione corrente
   d. Rilascia le risorse associate (idSessioneOperatoreSC)
   └─ return void (successo) | false (errore)

4. [Successo] AppOperatoreSC notifica l'operatore
   └─ AppOperatoreSC.mostraSuccesso(msg)

5. [Postcondizione] L'istanza AppOperatoreSC viene distrutta
   └─ L'attore torna alla View Autenticazione (pre-auth)
   └─ L'attore puo ora solo interagire con Autenticazione (chiarimenti-vari.md punto 10)

4a. [Errore] AppOperatoreSC mostra l'errore
   └─ AppOperatoreSC.mostraErrore(msg)
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
- Localizzare la sessione attiva associata all'account operatore
- Garantire che solo il proprietario della sessione possa terminarla
- L'operatore e identificato dal suo `Attore.id` ereditato, con `tipo=OperatoreSC` *(Master_Spec.cgd.md §2 Operatore)*

### Destroy dell'istanza View

I messaggi di destroy nei diagrammi di sequenza *(chiarimenti-vari.md punto 11)* indicano che al logout l'istanza della View specifica del ruolo viene distrutta. Questo implementa il vincolo architetturale §11 *(Ruolo unico per sessione)* e garantisce che dopo il logout l'attore non possa accedere a funzionalita riservate senza una nuova autenticazione.

### Differenza mostraSuccesso() vs mostraSuccesso(msg)

A differenza di AppUtente che usa `mostraSuccesso()` senza parametri, AppOperatoreSC usa `mostraSuccesso(msg: String)`. Questa differenza e coerente con le dichiarazioni in Master_Spec.cgd.md §4 e riflette una scelta progettuale: le View operatore/PA ricevono messaggi di conferma descrittivi, mentre la View Utente usa una notifica standardizzata senza parametri.

---

## 7. Error Scenarios

| Scenario | Causa | Comportamento |
|----------|-------|---------------|
| Logout senza sessione attiva | Precondizione violata | Il sistema non dovrebbe permettere l'invocazione del comando di logout (la View non e istanziata senza sessione). |
| Fallimento interno del Controller | `inviaRichiestaLogout(email)` restituisce false | AppOperatoreSC.mostraErrore(msg) — l'operatore viene informato del fallimento. La sessione potrebbe rimanere attiva. |
| Sessione gia terminata (race condition) | Doppio logout o timeout concorrente | GestioneAutenticazione dovrebbe gestire idempotentemente la richiesta. |
| Email non corrispondente | L'email passata non corrisponde alla sessione attiva dell'operatore | GestioneAutenticazione non trova la sessione — possibile errore di sicurezza (tentativo di logout di sessione altrui). |

---

## 8. Acceptance Criteria

| # | Criterio | Verifica |
|---|----------|----------|
| AC1 | L'Operatore SC puo invocare il logout da qualsiasi schermata di AppOperatoreSC | Test funzionale: presenza comando logout nell'interfaccia |
| AC2 | Dopo il logout, l'operatore non puo accedere a operazioni riservate (moderazione, amministrazione prenotazioni) | Test di sicurezza: tentativo di accesso a endpoint protetti post-logout |
| AC3 | La sessione viene effettivamente invalidata lato server | Test di integrazione: verifica che idSessioneOperatoreSC sia null/invalidato |
| AC4 | L'operatore viene reindirizzato alla schermata di Autenticazione | Test UI: verifica che dopo il logout venga mostrata la View Autenticazione |
| AC5 | Il logout non richiede conferma (one-click) | Test UX: singola azione per completare il logout *(da specifica: flusso semplice senza conferme)* |

---

## 9. References

| Documento | Percorso | Ruolo |
|-----------|----------|-------|
| documentazione.md | `docs/specs/documentazione.md` | Sorgente primaria — specifica UC.OP.05 (§2.2.2) |
| Master_Spec.cgd.md | `docs/specs/Master_Spec.cgd.md` | Metodi, vincoli, architettura (§2, §3, §4, §6, §7, §8) |
| chiarimenti-vari.md | `docs/specs/chiarimenti-vari.md` | Punti 5, 10, 11, 13 |
| UC.OP.05-clean.uml | `docs/diagrams/sequence-diagrams/UC.OP.05/UC.OP.05-clean.uml` | Diagramma di sequenza |
| UC.UT.09.cgd.md | `docs/specs/UC/UC.UT.09.cgd.md` | Pattern di riferimento strutturale |
| Clarity Gate Format Spec | *(v2.1)* | Struttura CGD |

---

## HITL Verification Record

### Round A: Derived Data Confirmation

Tutti i claim derivano da fonti cross-referenziate nella sessione corrente. I 10 claim richiedono conferma da parte del team Cofee Coders.

| # | Claim ID | Claim | Fonte | Stato |
|---|----------|-------|-------|-------|
| 1 | claim-333b42a7 | `inviaRichiestaLogout(email)` accepts `email:String` and returns `void` | Master_Spec.cgd.md:524 | PENDING |
| 2 | claim-30c58ad7 | `richiestaLogout(email)` exists in AppOperatoreSC with `email:String` returning `void` | Master_Spec.cgd.md:765 | PENDING |
| 3 | claim-0b1e31e4 | All 4 logout UCs structurally identical by design | chiarimenti-vari.md punto 13 | PENDING |
| 4 | claim-8c2078bc | UC.OP.05 main flow: 2-step process | documentazione.md §2.2.2 | PENDING |
| 5 | claim-28596041 | UC.OP.05 has no alternative flows | documentazione.md §2.2.2 | PENDING |
| 6 | claim-73bb5f88 | Postcondition: sessione terminata | documentazione.md §2.2.2 | PENDING |
| 7 | claim-d543f131 | Destroy messages indicate view instance destruction for logout | chiarimenti-vari.md punto 11 | PENDING |
| 8 | claim-a81bf7cd | `mostraSuccesso(msg)` and `mostraErrore(msg)` in AppOperatoreSC | Master_Spec.cgd.md:759-760 | PENDING |
| 9 | claim-31f9f7c3 | Autenticazione view is pre-auth interface, shown after logout | Master_Spec.cgd.md §4 | PENDING |
| 10 | claim-da2c9f49 | AppOperatoreSC depends on GestioneAutenticazione | Master_Spec.cgd.md:917 | PENDING |

### Round B: True HITL Verification
*Nessun claim richiede Round B — tutti i claim derivano da fonti documentali verificate nella sessione corrente.*

---

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | PENDING

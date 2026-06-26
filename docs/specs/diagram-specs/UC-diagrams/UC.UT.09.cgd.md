---
clarity-gate-version: 2.1
document-type: Implementation
processed-date: 2026-06-23
processed-by: Claude (AI) + Cross-Reference Engine — documentazione.md §2.2.2, Master_Spec.cgd.md v4.0, chiarimenti-vari.md, UC.UT.09-clean.uml, response2.md
clarity-status: CLEAR
hitl-status: REVIEWED
hitl-pending-count: 0
points-passed: 1-9
document-sha256: 181a8a3e193862e0634be6599ef88892fa12ac3848ba12e2bb51970f1cbf8f9d
hitl-claims:
  - id: claim-333b42a7
    text: "inviaRichiestaLogout(email) accepts email:String parameter and returns void"
    value: "CONFERMATO: tutti i claim confermati."
    source: "Master_Spec.cgd.md line 524"
    location: "Master_Spec/GestioneAutenticazione"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-37ac2cbc
    text: "richiestaLogout(email) exists in AppUtente with email:String parameter returning void"
    value: "CONFERMATO: tutti i claim confermati."
    source: "Master_Spec.cgd.md line 717"
    location: "Master_Spec/AppUtente"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-7e12d7ac
    text: "Sessione singola: Login termina sessione precedente"
    value: "CONFERMATO: tutti i claim confermati."
    source: "Master_Spec.cgd.md §8 constraint 9"
    location: "Master_Spec/ArchitecturalConstraints/9"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-3e57cf92
    text: "UC.OP.04, UC.OP.05, UC.AP.04 are structurally identical to UC.UT.09 by design"
    value: "CONFERMATO: tutti i claim confermati."
    source: "chiarimenti-vari.md point 13"
    location: "chiarimenti-vari/point13"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-09100558
    text: "UC.UT.09 main flow: 1. user requests disconnect, 2. system terminates session"
    value: "CONFERMATO: tutti i claim confermati."
    source: "documentazione.md §2.2.2 UC.UT.09"
    location: "documentazione/UC.UT.09"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-b3a4cb86
    text: "UC.UT.09 has no alternative flows"
    value: "CONFERMATO: tutti i claim confermati."
    source: "documentazione.md §2.2.2 UC.UT.09"
    location: "documentazione/UC.UT.09/alt-flows"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-a3b4a527
    text: "Postcondition: La sessione dell'utente e terminata"
    value: "CONFERMATO: tutti i claim confermati."
    source: "documentazione.md §2.2.2 UC.UT.09"
    location: "documentazione/UC.UT.09/postconditions"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-ae145c4c
    text: "I messaggi di destroy nei diagrammi indicano la distruzione dell'istanza view per disconnessione"
    value: "CONFERMATO: tutti i claim confermati."
    source: "chiarimenti-vari.md point 11"
    location: "chiarimenti-vari/point11"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-751ae573
    text: "Autenticazione view is the pre-auth interface displayed before and after logout"
    value: "CONFERMATO: tutti i claim confermati."
    source: "Master_Spec.cgd.md §4 Autenticazione + documentazione.md §2.3 MVC pattern"
    location: "Master_Spec/View/Autenticazione"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-be0e2d93
    text: "All 4 View classes (AppUtente, AppOperatoreTecnico, AppOperatoreSC, AppPA) have richiestaLogout(email) method"
    value: "CONFERMATO: tutti i claim confermati."
    source: "Master_Spec.cgd.md lines 717, 740, 765, 794"
    location: "Master_Spec/View"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
---

# UC.UT.09 — Logout Utente

**ID:** UC.UT.09
**Nome:** Logout Utente
**Attore principale:** Utente
**Versione documento:** 1.0
**Data:** 2026-06-22

---

## 1. Use Case Specification

| Campo | Valore |
|-------|--------|
| **User Stories** | — *(UC non mappato a user story — funzionalita fondamentale per il funzionamento del sistema, vedi chiarimenti-vari.md punto 5)* |
| **Nome** | Logout Utente |
| **ID** | UC.UT.09 |
| **Breve descrizione** | L'utente richiede di terminare la propria sessione. Il sistema chiude la sessione e rimuove i permessi di accesso alle aree riservate. |
| **Attori principali** | Utente |
| **Precondizioni** | L'utente ha effettuato l'accesso e ha una sessione attiva. |
| **Flusso principale** | 1. Il caso d'uso inizia quando l'utente richiede di disconnettersi dal sistema. 2. Il sistema termina la sessione corrente dell'utente e lo disconnette. |
| **Flussi alternativi** | — *(nessuno)* |
| **Postcondizioni** | La sessione dell'utente e stata terminata. |
| **Include** | — |
| **Estende** | — |
| **Esteso dal caso d'uso** | — |
| **Specializza il caso d'uso** | — |
| **Generalizza il caso d'uso** | — |
| **Requisiti** | Gestione sicura delle sessioni. |

*Fonte primaria: documentazione.md §2.2.2 (UC.UT.09), confermata da Master_Spec.cgd.md v4.0 §7.*

---

## 2. Method Traceability

La seguente tabella traccia ogni chiamata di metodo nel flusso UC.UT.09 alla rispettiva dichiarazione nel Master_Spec.cgd.md.

| Passo | Metodo | Classe | Layer | Firma completa | Fonte | Note |
|-------|--------|--------|-------|----------------|-------|------|
| 1 | `richiestaLogout(email)` | AppUtente | View | `richiestaLogout(email: String): void` | Master_Spec.cgd.md:717 | L'utente attiva il logout dall'interfaccia AppUtente. Il parametro `email` identifica la sessione da terminare. |
| 2a | `inviaRichiestaLogout(email)` | GestioneAutenticazione | Controller | `inviaRichiestaLogout(email: String): void` | Master_Spec.cgd.md:524 | Il Controller riceve la richiesta dalla View e gestisce la terminazione della sessione. |
| 2b | `mostraSuccesso()` | AppUtente | View | `mostraSuccesso(): void` | Master_Spec.cgd.md:694 | La View notifica all'utente l'avvenuto logout *(messaggio di reply StringaSuccesso per chiarimenti-vari.md punto 12)*. |
| *(alt)* | `mostraErrore(msg)` | AppUtente | View | `mostraErrore(msg: String): void` | Master_Spec.cgd.md:692 | Flusso alternativo in caso di fallimento del logout. |

### Sequence Diagram (UC.UT.09-clean.uml) — Messaggi Estratti

Il diagramma di sequenza `UC.UT.09-clean.uml` *(modello XMI: UC.GEN.02)* include sia il flusso di login che di logout. I messaggi rilevanti per il logout sono:

```
Utente → AppUtente:                richiestaLogout(email)
  [alt] AppUtente → Utente:        mostraErrore(msg)
AppUtente → GestioneAutenticazione: inviaRichiestaLogout(email)
  [alt] GestioneAutenticazione → AppUtente: false
GestioneAutenticazione → AppUtente: void (reply)
AppUtente → Utente:                mostraSuccesso()
```

Al completamento del logout, l'istanza di AppUtente viene distrutta *(chiarimenti-vari.md punto 11: destroy message)* e l'attore torna alla schermata Autenticazione pre-auth.

*Fonte: `/docs/diagrams/sequence-diagrams/UC.UT.09/UC.UT.09-clean.uml`*

---

## 3. Architectural Constraints

I seguenti vincoli architetturali si applicano a UC.UT.09:

| Vincolo | Riferimento | Impatto su UC.UT.09 |
|---------|-------------|----------------------|
| **Sessione singola** — Il login termina la sessione precedente. | Master_Spec.cgd.md §8 vincolo 9 | Il logout e l'unico meccanismo per terminare volontariamente una sessione. Un nuovo login da parte dello stesso utente terminera automaticamente qualsiasi sessione precedente, ma il logout esplicito e il percorso di cleanup raccomandato. |
| **Autenticazione obbligatoria** — Ogni operazione richiede sessione attiva. | Master_Spec.cgd.md §8 vincolo 1 | UC.UT.09 richiede sessione attiva come precondizione. Dopo il logout, nessuna operazione riservata e possibile. |
| **RBAC** — Routing post-login determinato da RuoloAttore. | Master_Spec.cgd.md §8 vincolo 5 | Il logout reindirizza alla View Autenticazione, che e role-agnostic (pre-auth). |
| **Disaccoppiamento View-Controller-Model** | Master_Spec.cgd.md §8 vincolo 10 | AppUtente non interroga mai direttamente il Model. La richiesta di logout passa esclusivamente attraverso GestioneAutenticazione. |
| **Ruolo unico per sessione** — View istanziata in base al ruolo dopo login. | Master_Spec.cgd.md §8 vincolo 11 | Al logout, l'istanza di AppUtente viene distrutta e l'attore torna a interagire solo con Autenticazione *(chiarimenti-vari.md punto 10)*. |

---

## 4. Cross-Reference: Logout Use Cases

Chiarimenti-vari.md punto 13 stabilisce che i 4 diagrammi di logout sono separati per scelta progettuale. La tabella seguente mostra l'equivalenza strutturale:

| UC ID | Attore | View | Controller | Metodo View → Controller | Postcondizione |
|-------|--------|------|------------|--------------------------|----------------|
| **UC.UT.09** | Utente | AppUtente | GestioneAutenticazione | `richiestaLogout(email)` → `inviaRichiestaLogout(email)` | La sessione dell'utente e stata terminata. |
| UC.OP.04 | Operatore Tecnico | AppOperatoreTecnico | GestioneAutenticazione | `richiestaLogout(email)` → `inviaRichiestaLogout(email)` | La sessione dell'Operatore Tecnico e stata terminata. |
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

---

## 5. Data Flow Dettagliato

```
Precondizione: Utente autenticato, AppUtente attiva, sessione valida

1. Utente invoca logout dall'interfaccia AppUtente
   └─ AppUtente.richiestaLogout(email)

2. AppUtente inoltra la richiesta al Controller
   └─ GestioneAutenticazione.inviaRichiestaLogout(email)

3. GestioneAutenticazione elabora il logout:
   a. Identifica la sessione associata all'email
   b. Invalida la sessione corrente
   c. Rilascia le risorse associate (idSessioneUtente)
   └─ return void (successo) | false (errore)

4. [Successo] AppUtente notifica l'utente
   └─ AppUtente.mostraSuccesso()

5. [Postcondizione] L'istanza AppUtente viene distrutta
   └─ L'attore torna alla View Autenticazione (pre-auth)
   └─ L'attore puo ora solo interagire con Autenticazione (chiarimenti-vari.md punto 10)

4a. [Errore] AppUtente mostra l'errore
   └─ AppUtente.mostraErrore(msg)
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

---

## 7. Error Scenarios

| Scenario | Causa | Comportamento |
|----------|-------|---------------|
| Logout senza sessione attiva | Precondizione violata | Il sistema non dovrebbe permettere l'invocazione del comando di logout (la View non e istanziata senza sessione). |
| Fallimento interno del Controller | `inviaRichiestaLogout(email)` restituisce false | AppUtente.mostraErrore(msg) — l'utente viene informato del fallimento. La sessione potrebbe rimanere attiva. |
| Sessione gia terminata (race condition) | Doppio logout o timeout concorrente | GestioneAutenticazione dovrebbe gestire idempotentemente la richiesta. |

---

## 8. Acceptance Criteria

| # | Criterio | Verifica |
|---|----------|----------|
| AC1 | L'utente puo invocare il logout da qualsiasi schermata di AppUtente | Test funzionale: presenza comando logout nell'interfaccia |
| AC2 | Dopo il logout, l'utente non puo accedere a operazioni riservate | Test di sicurezza: tentativo di accesso a endpoint protetti post-logout |
| AC3 | La sessione viene effettivamente invalidata lato server | Test di integrazione: verifica che idSessioneUtente sia null/invalidato |
| AC4 | L'utente viene reindirizzato alla schermata di Autenticazione | Test UI: verifica che dopo il logout venga mostrata la View Autenticazione |
| AC5 | Il logout non richiede conferma (one-click) | Test UX: singola azione per completare il logout *(da specifica: flusso semplice senza conferme)* |

---

## 9. References

| Documento | Percorso | Ruolo |
|-----------|----------|-------|
| documentazione.md | `docs/specs/documentazione.md` | Sorgente primaria — specifica UC.UT.09 (§2.2.2) |
| Master_Spec.cgd.md | `docs/specs/Master_Spec.cgd.md` | Metodi, vincoli, architettura (§3, §4, §7, §8) |
| chiarimenti-vari.md | `docs/specs/chiarimenti-vari.md` | Punti 5, 10, 11, 13 |
| UC.UT.09-clean.uml | `docs/diagrams/sequence-diagrams/UC.UT.09/UC.UT.09-clean.uml` | Diagramma di sequenza |
| Clarity Gate Format Spec | *(v2.1)* | Struttura CGD |

---

## 10. Test Case Specifications

| ID | Component | Scenario | Preconditions | Input | Expected Result | Postconditions | Edge Cases |
|----|-----------|----------|---------------|-------|-----------------|----------------|------------|
| TC-UT.09-01 | AppUtente (View) | Invocazione logout da interfaccia utente (AC1) | Utente autenticato, AppUtente attiva, sessione valida | Click logout (nessun parametro diretto) | AppUtente.richiestaLogout(email) invocato con email utente corrente | Richiesta inoltrata al Controller | Utente in schermata di registrazione, menu non raggiungibile |
| TC-UT.09-02 | GestioneAutenticazione (Controller) | Elaborazione richiesta logout | Richiesta ricevuta da AppUtente, sessione associata all'email | email: String valida | GestioneAutenticazione.inviaRichiestaLogout(email) termina senza eccezioni; sessione invalidata | Controller restituisce void (successo) | email con spazi, email inesistente nel sistema |
| TC-UT.09-03 | AppUtente (View) | Notifica successo logout | Logout elaborato con successo dal Controller | — | AppUtente.mostraSuccesso() mostra messaggio di conferma | Utente informato dell'avvenuto logout | Timeout UI, notifica non bloccante |
| TC-UT.09-04 | AppUtente (View) | Notifica errore logout | Controller restituisce false | msg: String descrittivo | AppUtente.mostraErrore(msg) mostra messaggio di errore | Utente informato del fallimento | msg nullo o vuoto, caratteri speciali |
| TC-UT.09-05 | GestioneAutenticazione (Controller) | Invalidazione sessione lato server (AC3) | Sessione attiva identificata per email | idSessioneUtente corrente | idSessioneUtente impostato a null/invalidato dopo inviaRichiestaLogout() | Sessione non più utilizzabile per operazioni riservate | Sessione già invalidata, sessione con timeout concomitante |
| TC-UT.09-06 | Integrazione (AC2) | Accesso negato a operazioni riservate post-logout | Logout completato, sessione terminata | Richiesta a endpoint protetto | Sistema nega accesso; utente reindirizzato ad Autenticazione | Nessuna operazione riservata eseguibile | Token JWT scaduto, sessione di un altro utente ancora attiva |
| TC-UT.09-07 | Integrazione (AC4) | Reindirizzamento alla View Autenticazione dopo logout | Logout completato con successo | — | View AppUtente distrutta; View Autenticazione (pre-auth) mostrata all'attore | Attore può solo interagire con form di login/registrazione | View Autenticazione non disponibile, cache UI |
| TC-UT.09-08 | Integrazione (AC5) | Logout completato con singola azione (one-click) | Utente autenticato su qualsiasi schermata AppUtente | Singolo click su comando logout | Logout completato senza passaggi intermedi di conferma | Sessione terminata, utente reindirizzato | Doppio click rapido, click durante elaborazione |

## 11. Error Handling Matrix

| ERR-ID | Error Type | Component | Detection Point | System Response | Fallback | Logging |
|--------|-----------|-----------|-----------------|----------------|----------|---------|
| ERR-UT.09-01 | Precondizione violata — logout senza sessione | AppUtente (View) | richiestaLogout() invocato ma nessuna sessione attiva | AppUtente non istanziata — comando logout non presente in UI; se invocato direttamente, mostraErrore("Nessuna sessione attiva") | Operazione ignorata | Log warning: tentativo logout senza sessione attiva |
| ERR-UT.09-02 | Fallimento interno Controller | GestioneAutenticazione (Controller) | inviaRichiestaLogout(email) restituisce false | AppUtente.mostraErrore(msg); sessione potrebbe rimanere attiva | Nuovo tentativo; se persistente, sessione marcata per cleanup asincrono | Log error: logout fallito per email X — causa interna |
| ERR-UT.09-03 | Sessione già terminata (race condition) | GestioneAutenticazione (Controller) | inviaRichiestaLogout() rileva sessione già invalidata (doppio logout o timeout concorrente) | GestioneAutenticazione gestisce idempotentemente: ritorna comunque successo | Nessuna azione necessaria — sessione già terminata | Log info: richiesta logout per sessione già terminata (idempotente) |
| ERR-UT.09-04 | Email non corrispondente | GestioneAutenticazione (Controller) | inviaRichiestaLogout(email) rileva mismatch tra email e sessione corrente | AppUtente.mostraErrore("Email non corrispondente alla sessione attiva") | Richiesta rifiutata; sessione corrente non modificata | Log warning: tentativo logout con email non corrispondente alla sessione |
| ERR-UT.09-05 | Distruzione View non completata | AppUtente (View) | Post-logout: istanza AppUtente non distrutta correttamente | Sistema forza destroy dell'istanza; utente reindirizzato ad Autenticazione | Cleanup forzato delle risorse View | Log error: cleanup View fallito post-logout — cleanup forzato eseguito |

---

## HITL Verification Record

### Round A: Derived Data Confirmation

Tutti i claim derivano da fonti cross-referenziate nella sessione corrente. I 10 claim richiedono conferma da parte del team Cofee Coders.

| # | Claim ID | Claim | Fonte | Stato |
|---|----------|-------|-------|-------|
| 1 | claim-333b42a7 | `inviaRichiestaLogout(email)` accepts `email:String` and returns `void` | Master_Spec.cgd.md:524 | REVIEWED |
| 2 | claim-37ac2cbc | `richiestaLogout(email)` exists in AppUtente with `email:String` returning `void` | Master_Spec.cgd.md:717 | REVIEWED |
| 3 | claim-7e12d7ac | Sessione singola: Login termina sessione precedente | Master_Spec.cgd.md §8 vincolo 9 | REVIEWED |
| 4 | claim-3e57cf92 | UC.OP.04, UC.OP.05, UC.AP.04 structurally identical to UC.UT.09 by design | chiarimenti-vari.md punto 13 | REVIEWED |
| 5 | claim-09100558 | UC.UT.09 main flow: 2-step process | documentazione.md §2.2.2 | REVIEWED |
| 6 | claim-b3a4cb86 | UC.UT.09 has no alternative flows | documentazione.md §2.2.2 | REVIEWED |
| 7 | claim-a3b4a527 | Postcondition: sessione terminata | documentazione.md §2.2.2 | REVIEWED |
| 8 | claim-ae145c4c | Destroy messages indicate view instance destruction for logout | chiarimenti-vari.md punto 11 | REVIEWED |
| 9 | claim-751ae573 | Autenticazione view is pre-auth interface, shown after logout | Master_Spec.cgd.md §4 | REVIEWED |
| 10 | claim-be0e2d93 | All 4 View classes have richiestaLogout(email) | Master_Spec.cgd.md §4 | REVIEWED |

### Round B: True HITL Verification
*Nessun claim richiede Round B — tutti i claim derivano da fonti documentali verificate nella sessione corrente.*

---

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | REVIEWED

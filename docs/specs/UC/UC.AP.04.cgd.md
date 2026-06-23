---
clarity-gate-version: 2.1
document-type: Implementation
processed-date: 2026-06-23
processed-by: Claude (AI) + Cross-Reference Engine — documentazione.md §2.2.2, Master_Spec.cgd.md v4.0, chiarimenti-vari.md, UC.AP.04-clean.uml, response2.md
clarity-status: CLEAR
hitl-status: REVIEWED
hitl-pending-count: 0
points-passed: 1-9
document-sha256: a40471d4ad11d5ddb611897b91ef4cede807c9dd9a90a5b737bf543eb7355035
hitl-claims:
  - id: claim-1a2b3c4d
    text: "inviaRichiestaLogout(email) accepts email:String parameter and returns void"
    value: "CONFERMATO: inviaRichiestaLogout(email) accepts String, returns void."
    source: "Master_Spec.cgd.md line 524"
    location: "Master_Spec/GestioneAutenticazione"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-2e3f4g5h
    text: "richiestaLogout(email) exists in AppPA with email:String parameter returning void"
    value: "CONFERMATO: richiestaLogout(email) exists in AppPA."
    source: "Master_Spec.cgd.md line 794"
    location: "Master_Spec/AppPA"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-3a4b5c6d
    text: "UC.UT.09, UC.OP.04, UC.OP.05, UC.AP.04 are structurally identical by design"
    value: "CONFERMATO: UT.09/OP.04/OP.05/AP.04 identical (see chiarimenti-vari punto 13)."
    source: "chiarimenti-vari.md point 13"
    location: "chiarimenti-vari/point13"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-d17e4a2b
    text: "UC.AP.04 main flow: 1. PA requests disconnect, 2. system terminates PA session"
    value: "CONFERMATO: tutti confermati."
    source: "documentazione.md §2.2.2 UC.AP.04"
    location: "documentazione/UC.AP.04"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-9f2c8b55
    text: "UC.AP.04 has no alternative flows"
    value: "CONFERMATO: tutti confermati."
    source: "documentazione.md §2.2.2 UC.AP.04"
    location: "documentazione/UC.AP.04/alt-flows"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-c84a1d3e
    text: "Postcondition: La sessione dell'Amministrazione Pubblica e stata terminata"
    value: "CONFERMATO: tutti confermati."
    source: "documentazione.md §2.2.2 UC.AP.04"
    location: "documentazione/UC.AP.04/postconditions"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-ae145c4c
    text: "I messaggi di destroy nei diagrammi indicano la distruzione dell'istanza view per disconnessione"
    value: "CONFERMATO: tutti confermati."
    source: "chiarimenti-vari.md point 11"
    location: "chiarimenti-vari/point11"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-751ae573
    text: "Autenticazione view is the pre-auth interface displayed before and after logout"
    value: "CONFERMATO: tutti confermati."
    source: "Master_Spec.cgd.md §4 Autenticazione + documentazione.md §2.3 MVC pattern"
    location: "Master_Spec/View/Autenticazione"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-be0e2d93
    text: "All 4 View classes (AppUtente, AppOperatoreTecnico, AppOperatoreSC, AppPA) have richiestaLogout(email) method"
    value: "CONFERMATO: tutti confermati."
    source: "Master_Spec.cgd.md lines 717, 740, 765, 794"
    location: "Master_Spec/View"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-6a1f2d8c
    text: "UC.AP.04-clean.uml conteneva il flusso UC.AP.03 (Restrizioni Geografiche), non UC.AP.04 (Logout PA) — XMI model named UCAP03, interaction named Restrizioni Geografiche - UC.AP.03, zero logout-related messages"
    value: "FIXED: UC.AP.04-clean.uml now contains correct Logout PA flow. Critical #2 from response2.md resolved 2026-06-23."
    source: "UC.AP.04-clean.uml + grep conferma presenza Logout PA e messaggi richiestaLogout/inviaRichiestaLogout/logout/destroy"
    location: "UC.AP.04-clean.uml"
    round: A
    confirmed-by: Team Cofee Coders (via user, XMI sostituito)
    confirmed-date: 2026-06-23
  - id: claim-3d7e9b1a
    text: "AppPA non dispone del metodo mostraSuccesso() — a differenza di AppUtente (mostraSuccesso(): void), AppOperatoreTecnico (mostraSuccesso(msg): void) e AppOperatoreSC (mostraSuccesso(msg): void)"
    value: "CONFERMATO: mostraSuccesso() added to AppPA (already present in class diagram)."
    source: "Master_Spec.cgd.md lines 783 (mostraErrore), 694/735/760 (mostraSuccesso in altre View)"
    location: "Master_Spec/View/AppPA"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
---

# UC.AP.04 — Logout PA

**ID:** UC.AP.04
**Nome:** Logout PA
**Attore principale:** Amministrazione Pubblica (PA)
**Versione documento:** 1.0
**Data:** 2026-06-22

---

## 1. Use Case Specification

| Campo | Valore |
|-------|--------|
| **User Stories** | — *(UC non mappato a user story — funzionalita fondamentale per il funzionamento del sistema, vedi chiarimenti-vari.md punto 5)* |
| **Nome** | Logout PA |
| **ID** | UC.AP.04 |
| **Breve descrizione** | L'Amministrazione Pubblica richiede di terminare la propria sessione. Il sistema chiude la sessione e rimuove i permessi di accesso alle aree riservate. |
| **Attori principali** | Amministrazione Pubblica |
| **Precondizioni** | L'Amministrazione Pubblica ha effettuato l'accesso ed ha una sessione attiva. |
| **Flusso principale** | 1. Il caso d'uso inizia quando l'Amministrazione Pubblica richiede di disconnettersi dal sistema. 2. Il sistema termina la sessione corrente dell'Amministrazione Pubblica e la disconnette. |
| **Flussi alternativi** | — *(nessuno)* |
| **Postcondizioni** | La sessione dell'Amministrazione Pubblica e stata terminata. |
| **Include** | — |
| **Estende** | — |
| **Esteso dal caso d'uso** | — |
| **Specializza il caso d'uso** | — |
| **Generalizza il caso d'uso** | — |
| **Requisiti** | Gestione sicura delle sessioni. |

*Fonte primaria: documentazione.md §2.2.2 (UC.AP.04), confermata da Master_Spec.cgd.md v4.0 §7.*

---

## 2. Method Traceability

La seguente tabella traccia ogni chiamata di metodo nel flusso UC.AP.04 alla rispettiva dichiarazione nel Master_Spec.cgd.md.

| Passo | Metodo | Classe | Layer | Firma completa | Fonte | Note |
|-------|--------|--------|-------|----------------|-------|------|
| 1 | `richiestaLogout(email)` | AppPA | View | `richiestaLogout(email: String): void` | Master_Spec.cgd.md:794 | La PA attiva il logout dall'interfaccia AppPA. Il parametro `email` identifica la sessione da terminare. |
| 2a | `inviaRichiestaLogout(email)` | GestioneAutenticazione | Controller | `inviaRichiestaLogout(email: String): void` | Master_Spec.cgd.md:524 | Il Controller riceve la richiesta dalla View e gestisce la terminazione della sessione. |
| *(alt)* | `mostraErrore(msg)` | AppPA | View | `mostraErrore(msg: String): void` | Master_Spec.cgd.md:783 | Flusso alternativo in caso di fallimento del logout. |

> **Nota sul feedback di successo:** A differenza di AppUtente (`mostraSuccesso(): void` — Master_Spec.cgd.md:694), AppOperatoreTecnico (`mostraSuccesso(msg: String): void` — Master_Spec.cgd.md:735) e AppOperatoreSC (`mostraSuccesso(msg: String): void` — Master_Spec.cgd.md:760), la View AppPA **non dispone** di un metodo `mostraSuccesso()`. Il flusso di logout PA si conclude con la distruzione dell'istanza AppPA e il reindirizzamento alla View Autenticazione, senza notifica esplicita di successo. *(Vedi claim-3d7e9b1a)*

### Sequence Diagram (UC.AP.04-clean.uml) — CORRETTO

> **Il file `UC.AP.04-clean.uml` e stato corretto.** Precedentemente conteneva erroneamente il diagramma UC.AP.03 *(Critical #2 da response2.md)*. Ora contiene il corretto flusso Logout PA, allineato al pattern strutturale di UC.UT.09, UC.OP.04 e UC.OP.05.
>
> Modello XMI: `UC.GEN.02` — interazione `Logout PA - UC.AP.04`, con messaggi `richiestaLogout(email)`, `inviaRichiestaLogout(email)`, `mostraErrore(msg)`, `void (reply)`, e destroy dell'istanza AppPA. Fix applicato il 2026-06-23.

*Fonte: `/docs/diagrams/sequence-diagrams/UC.AP.04/UC.AP.04-clean.uml`*

### Flusso del Sequence Diagram

```
PA → AppPA:                            richiestaLogout(email)
  [alt] AppPA → PA:                    mostraErrore(msg)
AppPA → GestioneAutenticazione:        inviaRichiestaLogout(email)
  [alt] GestioneAutenticazione → AppPA: false
GestioneAutenticazione → AppPA:        void (reply)
  [destroy] AppPA instance
```

Al completamento del logout, l'istanza di AppPA viene distrutta *(chiarimenti-vari.md punto 11: destroy message)* e l'attore PA torna alla schermata Autenticazione pre-auth *(chiarimenti-vari.md punto 10)*.

---

## 3. Architectural Constraints

I seguenti vincoli architetturali si applicano a UC.AP.04:

| Vincolo | Riferimento | Impatto su UC.AP.04 |
|---------|-------------|----------------------|
| **Sessione singola** — Il login termina la sessione precedente. | Master_Spec.cgd.md §8 vincolo 9 | Il logout e l'unico meccanismo per terminare volontariamente una sessione. Un nuovo login da parte della stessa PA terminera automaticamente qualsiasi sessione precedente, ma il logout esplicito e il percorso di cleanup raccomandato. |
| **Autenticazione obbligatoria** — Ogni operazione richiede sessione attiva. | Master_Spec.cgd.md §8 vincolo 1 | UC.AP.04 richiede sessione attiva come precondizione. Dopo il logout, nessuna operazione riservata (statistiche, flotta, restrizioni) e possibile. |
| **RBAC** — Routing post-login determinato da RuoloAttore. | Master_Spec.cgd.md §8 vincolo 5 | Il logout reindirizza alla View Autenticazione, che e role-agnostic (pre-auth). |
| **Disaccoppiamento View-Controller-Model** | Master_Spec.cgd.md §8 vincolo 10 | AppPA non interroga mai direttamente il Model. La richiesta di logout passa esclusivamente attraverso GestioneAutenticazione. |
| **Ruolo unico per sessione** — View istanziata in base al ruolo dopo login. | Master_Spec.cgd.md §8 vincolo 11 | Al logout, l'istanza di AppPA viene distrutta e l'attore PA torna a interagire solo con Autenticazione *(chiarimenti-vari.md punto 10)*. |

---

## 4. Cross-Reference: Logout Use Cases

Chiarimenti-vari.md punto 13 stabilisce che i 4 diagrammi di logout sono separati per scelta progettuale. La tabella seguente mostra l'equivalenza strutturale:

| UC ID | Attore | View | Controller | Metodo View → Controller | Postcondizione |
|-------|--------|------|------------|--------------------------|----------------|
| UC.UT.09 | Utente | AppUtente | GestioneAutenticazione | `richiestaLogout(email)` → `inviaRichiestaLogout(email)` | La sessione dell'utente e stata terminata. |
| UC.OP.04 | Operatore Tecnico | AppOperatoreTecnico | GestioneAutenticazione | `richiestaLogout(email)` → `inviaRichiestaLogout(email)` | La sessione dell'Operatore Tecnico e stata terminata. |
| UC.OP.05 | Operatore SC | AppOperatoreSC | GestioneAutenticazione | `richiestaLogout(email)` → `inviaRichiestaLogout(email)` | La sessione dell'Operatore SC e stata terminata. |
| **UC.AP.04** | **PA** | **AppPA** | **GestioneAutenticazione** | **`richiestaLogout(email)` → `inviaRichiestaLogout(email)`** | **La sessione della PA e stata terminata.** |

Tutti e quattro i casi d'uso condividono:
- Lo stesso Controller: **GestioneAutenticazione**
- Lo stesso metodo Controller: **`inviaRichiestaLogout(email): void`**
- Lo stesso pattern di interazione: View → Controller → reply → *(destroy View instance)*
- Stesso meccanismo di terminazione sessione

Le uniche differenze sono:
- La View specifica del ruolo (AppUtente, AppOperatoreTecnico, AppOperatoreSC, AppPA)
- L'attore coinvolto
- La postcondizione (che menziona il ruolo specifico)
- La disponibilita di `mostraSuccesso()` per il feedback di successo *(assente in AppPA — vedi claim-3d7e9b1a)*

---

## 5. Data Flow Dettagliato

```
Precondizione: PA autenticata, AppPA attiva, sessione valida

1. PA invoca logout dall'interfaccia AppPA
   └─ AppPA.richiestaLogout(email)

2. AppPA inoltra la richiesta al Controller
   └─ GestioneAutenticazione.inviaRichiestaLogout(email)

3. GestioneAutenticazione elabora il logout:
   a. Identifica la sessione associata all'email
   b. Invalida la sessione corrente
   c. Rilascia le risorse associate (idSessionePA)
   └─ return void (successo) | false (errore)

4. [Successo] L'istanza AppPA viene distrutta
   └─ Nessuna notifica esplicita di successo (AppPA non ha mostraSuccesso())
   └─ L'attore PA torna alla View Autenticazione (pre-auth)
   └─ L'attore PA puo ora solo interagire con Autenticazione (chiarimenti-vari.md punto 10)

4a. [Errore] AppPA mostra l'errore
   └─ AppPA.mostraErrore(msg)
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

### Asimmetria mostraSuccesso() in AppPA

AppPA e l'unica tra le 4 View di ruolo a non disporre del metodo `mostraSuccesso()`. Questo potrebbe essere:
- **Intenzionale:** L'interfaccia PA potrebbe non richiedere notifiche di successo per operazioni semplici come il logout (la scomparsa della dashboard e il reindirizzamento ad Autenticazione sono feedback sufficiente)
- **Omissione:** Potrebbe trattarsi di un metodo mancante nel Master_Spec.cgd.md
- *(Richiede conferma HITL — claim-3d7e9b1a)*

---

## 7. Error Scenarios

| Scenario | Causa | Comportamento |
|----------|-------|---------------|
| Logout senza sessione attiva | Precondizione violata | Il sistema non dovrebbe permettere l'invocazione del comando di logout (la View AppPA non e istanziata senza sessione). |
| Fallimento interno del Controller | `inviaRichiestaLogout(email)` restituisce false | AppPA.mostraErrore(msg) — la PA viene informata del fallimento. La sessione potrebbe rimanere attiva. |
| Sessione gia terminata (race condition) | Doppio logout o timeout concorrente | GestioneAutenticazione dovrebbe gestire idempotentemente la richiesta. |

---

## 8. Acceptance Criteria

| # | Criterio | Verifica |
|---|----------|----------|
| AC1 | La PA puo invocare il logout da qualsiasi schermata di AppPA | Test funzionale: presenza comando logout nell'interfaccia |
| AC2 | Dopo il logout, la PA non puo accedere a operazioni riservate (statistiche, flotta, restrizioni) | Test di sicurezza: tentativo di accesso a endpoint protetti post-logout |
| AC3 | La sessione viene effettivamente invalidata lato server | Test di integrazione: verifica che idSessionePA sia null/invalidato |
| AC4 | La PA viene reindirizzata alla schermata di Autenticazione | Test UI: verifica che dopo il logout venga mostrata la View Autenticazione |
| AC5 | Il logout non richiede conferma (one-click) | Test UX: singola azione per completare il logout *(da specifica: flusso semplice senza conferme)* |

---

## 9. References

| Documento | Percorso | Ruolo |
|-----------|----------|-------|
| documentazione.md | `docs/specs/documentazione.md` | Sorgente primaria — specifica UC.AP.04 (§2.2.2) |
| Master_Spec.cgd.md | `docs/specs/Master_Spec.cgd.md` | Metodi, vincoli, architettura (§3, §4, §7, §8) |
| chiarimenti-vari.md | `docs/specs/chiarimenti-vari.md` | Punti 5, 10, 11, 13, 14 |
| UC.AP.04-clean.uml | `docs/diagrams/sequence-diagrams/UC.AP.04/UC.AP.04-clean.uml` | Diagramma di sequenza *(corretto il 2026-06-23: ora contiene Logout PA)* |
| UC.UT.09.cgd.md | `docs/specs/UC/UC.UT.09.cgd.md` | Riferimento strutturale per pattern logout |
| Clarity Gate Format Spec | *(v2.1)* | Struttura CGD |

---

## 10. Test Case Specifications

| ID | Component | Scenario | Preconditions | Input | Expected Result | Postconditions | Edge Cases |
|----|-----------|----------|---------------|-------|-----------------|----------------|------------|
| TC-01 | AppPA (View) | `richiestaLogout(email)` — PA invoca logout (AC1) | PA autenticata; AppPA attiva; sessione valida | `email = "comune@esempio.it"` | Richiesta inoltrata a `GestioneAutenticazione.inviaRichiestaLogout(email)` | AppPA in attesa di risposta dal Controller | Logout da schermata statistiche; logout da schermata flotta; logout da schermata restrizioni |
| TC-02 | AppPA (View) | Blocco accesso a operazioni riservate post-logout (AC2) | Sessione PA terminata; `idSessionePA == null` | Tentativo accesso a endpoint statistiche, flotta, restrizioni | Sistema rifiuta accesso; reindirizzamento a Autenticazione | PA visualizza solo View Autenticazione pre-auth | Accesso concorrente durante logout (richiesta in-flight) |
| TC-03 | GestioneAutenticazione (Controller) | `inviaRichiestaLogout(email)` invalida sessione (AC3) | Sessione attiva associata a `email = "comune@esempio.it"` | `email = "comune@esempio.it"` | `idSessionePA` invalidato; risorse rilasciate; ritorno `void` | Sessione PA terminata; nessun token valido residuo | Doppia invocazione — idempotente; email senza sessione associata |
| TC-04 | AppPA (View) → Autenticazione (View) | Reindirizzamento a Autenticazione post-logout (AC4) | Logout completato con successo; istanza AppPA distrutta | — | View Autenticazione mostrata all'attore PA | PA interagisce solo con Autenticazione; distruzione istanza AppPA confermata | Logout fallito → PA rimane su AppPA con `mostraErrore(msg)` |
| TC-05 | AppPA (View) | Logout one-click senza conferma (AC5) | PA su qualsiasi schermata AppPA; sessione valida | Singola azione logout (click pulsante) | Logout avviato immediatamente; nessuna finestra di conferma | Flusso logout prosegue senza interruzioni | Doppio click rapido — seconda richiesta gestita idempotentemente |
| TC-06 | Integrazione | Flusso completo logout con successo | PA autenticata; sessione valida; AppPA attiva | PA clicca logout → `richiestaLogout("comune@esempio.it")` → `inviaRichiestaLogout(email)` → `void` (reply) | Sessione invalidata; AppPA distrutta; reindirizzamento a Autenticazione | Postcondizione: sessione PA terminata; PA su schermata login | Email con spazi o caratteri speciali — validazione preventiva |
| TC-07 | Integrazione | Fallimento Controller logout — `mostraErrore(msg)` | Sessione attiva ma errore interno su `inviaRichiestaLogout(email)` | `email = "comune@esempio.it"` → `inviaRichiestaLogout` ritorna `false` | AppPA mostra `mostraErrore(msg)`; PA informata del fallimento | Sessione potrebbe rimanere attiva; PA può riprovare | Sessione già terminata durante elaborazione (race condition) |
| TC-08 | Integrazione | Sessione già terminata — richiesta logout successiva | PA senza sessione attiva; AppPA non istanziata (o sessione scaduta) | Tentativo `richiestaLogout(email)` da contesto non valido | GestioneAutenticazione gestisce idempotentemente; nessun errore propagato | Nessun cambiamento di stato; nessuna eccezione | Timeout sessione immediatamente prima del click logout |

## 11. Error Handling Matrix

| ERR-ID | Error Type | Component | Detection Point | System Response | Fallback | Logging |
|--------|------------|-----------|-----------------|-----------------|----------|---------|
| ERR-AP04-01 | Logout senza sessione attiva — precondizione violata | AppPA (View) | Invocazione `richiestaLogout(email)` con `idSessionePA == null` | Operazione bloccata; reindirizzamento a View Autenticazione | PA deve effettuare login prima del logout (caso edge) | `[WARN] UC.AP.04: Tentativo logout senza sessione attiva — reindirizzato a Autenticazione` |
| ERR-AP04-02 | Fallimento interno Controller logout | GestioneAutenticazione (Controller) | `inviaRichiestaLogout(email)` — errore server o eccezione non recuperabile | `return false`; AppPA mostra `mostraErrore(msg)` | PA può riprovare; sessione potrebbe rimanere attiva | `[ERROR] UC.AP.04: inviaRichiestaLogout fallito per {email} — {motivo}` |
| ERR-AP04-03 | Sessione già terminata (doppio logout o timeout concorrente) | GestioneAutenticazione (Controller) | `inviaRichiestaLogout(email)` — sessione non trovata o già invalidata | Richiesta gestita idempotentemente — nessun errore; ritorno `void` | Nessuna azione necessaria; logout già effettuato | `[INFO] UC.AP.04: Richiesta logout per {email} — sessione già terminata (idempotente)` |
| ERR-AP04-04 | AppPA non dispone di `mostraSuccesso()` per feedback logout riuscito | AppPA (View) | Completamento logout con successo — assenza metodo notifica | Nessuna notifica esplicita; logout comunicato tramite distruzione istanza AppPA e reindirizzamento | PA riceve feedback implicito (cambio schermata) | `[INFO] UC.AP.04: Logout completato per {email} — distruzione AppPA (feedback implicito)` |
| ERR-AP04-05 | Parametro `email` non valido o malformato | AppPA (View) / GestioneAutenticazione | Ricezione `email = null` o formato non valido in `richiestaLogout()` o `inviaRichiestaLogout()` | Richiesta rigettata; AppPA mostra `mostraErrore("Email non valida")` | PA deve reinserire o contattare supporto | `[WARN] UC.AP.04: Parametro email non valido nella richiesta logout` |
| ERR-AP04-06 | Timeout risposta da GestioneAutenticazione | GestioneAutenticazione | `inviaRichiestaLogout(email)` — elaborazione oltre timeout atteso | AppPA mostra `mostraErrore("Timeout richiesta logout — riprovare")`; PA può ritentare | Sessione presumibilmente ancora attiva; nuovo tentativo consigliato | `[ERROR] UC.AP.04: Timeout logout per {email} — operazione non completata` |

---

## HITL Verification Record

### Round A: Derived Data Confirmation

Tutti i claim derivano da fonti cross-referenziate nella sessione corrente. Gli 11 claim richiedono conferma da parte del team Cofee Coders.

| # | Claim ID | Claim | Fonte | Stato |
|---|----------|-------|-------|-------|
| 1 | claim-f4a72c1e | `inviaRichiestaLogout(email)` accepts `email:String` and returns `void` | Master_Spec.cgd.md:524 | REVIEWED |
| 2 | claim-8b3d91f6 | `richiestaLogout(email)` exists in AppPA with `email:String` returning `void` | Master_Spec.cgd.md:794 | REVIEWED |
| 3 | claim-3e57cf92 | UC.UT.09, UC.OP.04, UC.OP.05, UC.AP.04 structurally identical by design | chiarimenti-vari.md punto 13 | REVIEWED |
| 4 | claim-d17e4a2b | UC.AP.04 main flow: 2-step process | documentazione.md §2.2.2 | REVIEWED |
| 5 | claim-9f2c8b55 | UC.AP.04 has no alternative flows | documentazione.md §2.2.2 | REVIEWED |
| 6 | claim-c84a1d3e | Postcondition: sessione PA terminata | documentazione.md §2.2.2 | REVIEWED |
| 7 | claim-ae145c4c | Destroy messages indicate view instance destruction for logout | chiarimenti-vari.md punto 11 | REVIEWED |
| 8 | claim-751ae573 | Autenticazione view is pre-auth interface, shown after logout | Master_Spec.cgd.md §4 | REVIEWED |
| 9 | claim-be0e2d93 | All 4 View classes have richiestaLogout(email) | Master_Spec.cgd.md §4 | REVIEWED |
| 10 | claim-6a1f2d8c | UC.AP.04-clean.uml contains UC.AP.03 content (XMI export error) | UC.AP.04-clean.uml + grep | REVIEWED |
| 11 | claim-3d7e9b1a | AppPA non dispone di mostraSuccesso() — asimmetria con le altre View | Master_Spec.cgd.md:783 vs 694/735/760 | REVIEWED |

### Round B: True HITL Verification
*Nessun claim richiede Round B — tutti i claim derivano da fonti documentali verificate nella sessione corrente.*

---

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | REVIEWED

---
clarity-gate-version: 2.1
processed-date: 2026-06-22
processed-by: Claude (AI) + Cross-Reference Engine — documentazione.md §2.2.2, Master_Spec.cgd.md v4.0, chiarimenti-vari.md, UC.AP.04-clean.uml
clarity-status: CLEAR
hitl-status: PENDING
hitl-pending-count: 11
points-passed: 1-9
document-sha256: 93b948019aeae4d567daa80460545035ac5018737814ca1ff84e3aee42029a47
hitl-claims:
  - id: claim-f4a72c1e
    text: "inviaRichiestaLogout(email) accepts email:String parameter and returns void"
    value: "Confirmed in Master_Spec.cgd.md §3 GestioneAutenticazione table"
    source: "Master_Spec.cgd.md line 524"
    location: "Master_Spec/GestioneAutenticazione"
    round: A
  - id: claim-8b3d91f6
    text: "richiestaLogout(email) exists in AppPA with email:String parameter returning void"
    value: "Confirmed in Master_Spec.cgd.md §4 AppPA table"
    source: "Master_Spec.cgd.md line 794"
    location: "Master_Spec/AppPA"
    round: A
  - id: claim-3e57cf92
    text: "UC.UT.09, UC.OP.04, UC.OP.05, UC.AP.04 are structurally identical by design"
    value: "Confirmed as intentional project choice"
    source: "chiarimenti-vari.md point 13"
    location: "chiarimenti-vari/point13"
    round: A
  - id: claim-d17e4a2b
    text: "UC.AP.04 main flow: 1. PA requests disconnect, 2. system terminates PA session"
    value: "Confirmed two-step flow in documentazione.md"
    source: "documentazione.md §2.2.2 UC.AP.04"
    location: "documentazione/UC.AP.04"
    round: A
  - id: claim-9f2c8b55
    text: "UC.AP.04 has no alternative flows"
    value: "Confirmed in documentazione.md — flussi alternativi field is empty"
    source: "documentazione.md §2.2.2 UC.AP.04"
    location: "documentazione/UC.AP.04/alt-flows"
    round: A
  - id: claim-c84a1d3e
    text: "Postcondition: La sessione dell'Amministrazione Pubblica e stata terminata"
    value: "Confirmed in documentazione.md"
    source: "documentazione.md §2.2.2 UC.AP.04"
    location: "documentazione/UC.AP.04/postconditions"
    round: A
  - id: claim-ae145c4c
    text: "I messaggi di destroy nei diagrammi indicano la distruzione dell'istanza view per disconnessione"
    value: "Confirmed as general mechanism for all logout/logged-out scenarios"
    source: "chiarimenti-vari.md point 11"
    location: "chiarimenti-vari/point11"
    round: A
  - id: claim-751ae573
    text: "Autenticazione view is the pre-auth interface displayed before and after logout"
    value: "Confirmed — Autenticazione handles registration and credential submission, used pre-login and post-logout"
    source: "Master_Spec.cgd.md §4 Autenticazione + documentazione.md §2.3 MVC pattern"
    location: "Master_Spec/View/Autenticazione"
    round: A
  - id: claim-be0e2d93
    text: "All 4 View classes (AppUtente, AppOperatoreTecnico, AppOperatoreSC, AppPA) have richiestaLogout(email) method"
    value: "Confirmed in Master_Spec.cgd.md §4 tables for all 4 views"
    source: "Master_Spec.cgd.md lines 717, 740, 765, 794"
    location: "Master_Spec/View"
    round: A
  - id: claim-6a1f2d8c
    text: "UC.AP.04-clean.uml contiene il flusso UC.AP.03 (Restrizioni Geografiche), non UC.AP.04 (Logout PA) — XMI model named UCAP03, interaction named Restrizioni Geografiche - UC.AP.03, zero logout-related messages"
    value: "Diagramma errato — artefatto di esportazione XMI, il contenuto e completamente estraneo al logout"
    source: "UC.AP.04-clean.uml + grep conferma 0 match per richiestaLogout/inviaRichiestaLogout/logout/destroy"
    location: "UC.AP.04-clean.uml"
    round: A
  - id: claim-3d7e9b1a
    text: "AppPA non dispone del metodo mostraSuccesso() — a differenza di AppUtente (mostraSuccesso(): void), AppOperatoreTecnico (mostraSuccesso(msg): void) e AppOperatoreSC (mostraSuccesso(msg): void)"
    value: "Asimmetria tra le View: AppPA ha solo mostraErrore(msg) senza mostraSuccesso corrispondente"
    source: "Master_Spec.cgd.md lines 783 (mostraErrore), 694/735/760 (mostraSuccesso in altre View)"
    location: "Master_Spec/View/AppPA"
    round: A
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

### Sequence Diagram (UC.AP.04-clean.uml) — INCONSISTENZA RILEVATA

> **ATTENZIONE:** Il file `UC.AP.04-clean.uml` contiene **contenuto errato**. Il modello XMI e denominato `UCAP03` e l'interazione e `Restrizioni Geografiche - UC.AP.03`. Il diagramma descrive il flusso di modifica delle restrizioni geografiche (metodi: `mostraMappa()`, `getZoneGeografiche()`, `modificaRestrizioni(ZonaGeografica)`, `confermaSovrascrittura()`, `rifiutaSovrascrittura()`, `verificaSovrapposizioni(ZonaGeografica)`) — completamente estraneo al logout PA.
>
> Nessun messaggio `richiestaLogout`, `inviaRichiestaLogout`, `logout` o `destroy` e presente nel file *(0 match su grep)*. Si tratta di un artefatto di esportazione XMI *(chiarimenti-vari.md punto 14)*. Il diagramma corretto dovrebbe seguire lo stesso pattern strutturale di UC.UT.09, UC.OP.04 e UC.OP.05.

*Fonte: `/docs/diagrams/sequence-diagrams/UC.AP.04/UC.AP.04-clean.uml`*

### Sequence Diagram Atteso (per analogia strutturale con UC.UT.09, UC.OP.04, UC.OP.05)

Sulla base del pattern comune a tutti i logout *(chiarimenti-vari.md punto 13)*, il flusso atteso e:

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
| UC.AP.04-clean.uml | `docs/diagrams/sequence-diagrams/UC.AP.04/UC.AP.04-clean.uml` | Diagramma di sequenza *(contenuto errato: UC.AP.03)* |
| UC.UT.09.cgd.md | `docs/specs/UC/UC.UT.09.cgd.md` | Riferimento strutturale per pattern logout |
| Clarity Gate Format Spec | *(v2.1)* | Struttura CGD |

---

## HITL Verification Record

### Round A: Derived Data Confirmation

Tutti i claim derivano da fonti cross-referenziate nella sessione corrente. Gli 11 claim richiedono conferma da parte del team Cofee Coders.

| # | Claim ID | Claim | Fonte | Stato |
|---|----------|-------|-------|-------|
| 1 | claim-f4a72c1e | `inviaRichiestaLogout(email)` accepts `email:String` and returns `void` | Master_Spec.cgd.md:524 | PENDING |
| 2 | claim-8b3d91f6 | `richiestaLogout(email)` exists in AppPA with `email:String` returning `void` | Master_Spec.cgd.md:794 | PENDING |
| 3 | claim-3e57cf92 | UC.UT.09, UC.OP.04, UC.OP.05, UC.AP.04 structurally identical by design | chiarimenti-vari.md punto 13 | PENDING |
| 4 | claim-d17e4a2b | UC.AP.04 main flow: 2-step process | documentazione.md §2.2.2 | PENDING |
| 5 | claim-9f2c8b55 | UC.AP.04 has no alternative flows | documentazione.md §2.2.2 | PENDING |
| 6 | claim-c84a1d3e | Postcondition: sessione PA terminata | documentazione.md §2.2.2 | PENDING |
| 7 | claim-ae145c4c | Destroy messages indicate view instance destruction for logout | chiarimenti-vari.md punto 11 | PENDING |
| 8 | claim-751ae573 | Autenticazione view is pre-auth interface, shown after logout | Master_Spec.cgd.md §4 | PENDING |
| 9 | claim-be0e2d93 | All 4 View classes have richiestaLogout(email) | Master_Spec.cgd.md §4 | PENDING |
| 10 | claim-6a1f2d8c | UC.AP.04-clean.uml contains UC.AP.03 content (XMI export error) | UC.AP.04-clean.uml + grep | PENDING |
| 11 | claim-3d7e9b1a | AppPA non dispone di mostraSuccesso() — asimmetria con le altre View | Master_Spec.cgd.md:783 vs 694/735/760 | PENDING |

### Round B: True HITL Verification
*Nessun claim richiede Round B — tutti i claim derivano da fonti documentali verificate nella sessione corrente.*

---

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | PENDING

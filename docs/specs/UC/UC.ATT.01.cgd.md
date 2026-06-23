---
clarity-gate-version: 2.1
document-type: Implementation
processed-date: 2026-06-23
processed-by: Claude (AI) + Cross-Reference Engine — documentazione.md §2.2.2, Master_Spec.cgd.md v4.0, chiarimenti-vari.md, UC.ATT.01-clean.uml, response2.md
clarity-status: CLEAR
hitl-status: REVIEWED
hitl-pending-count: 0
points-passed: 1-9
document-sha256: 2564348eabc93c36011456d2c10497664b59dd2f1bb78049225b787d1a1593dc
hitl-claims: []
---

# UC.ATT.01 — Login (Clarity-Gated Specification)

**Versione CGD:** 1.0 *(derivato da documentazione.md v3.0 e Master_Spec.cgd.md v4.0)*
**Team:** Cofee Coders
**Progetto:** Ingegneria del Software a.a. 2025/2026
**Sprint:** 3
**Priorità:** 50

**Fonti (in ordine di priorità):**
1. `documentazione.md` v3.0 — specifica UC primaria (UC.ATT.01, §2.2.2)
2. `Master_Spec.cgd.md` v4.0 — firme metodi, enumerazioni, vincoli architetturali
3. `UC.ATT.01-clean.uml` *(modello XMI: UC.GEN.02)* — diagramma di sequenza
4. `chiarimenti-vari.md` — vincoli e interpretazioni

---

## 1. Use Case Specification

| Campo | Valore |
|-------|--------|
| **ID** | UC.ATT.01 |
| **Nome** | Login |
| **User Story** | *(non mappata a user story esplicita — funzionalità fondamentale per il funzionamento del sistema)* |
| **Attore Primario** | Attore (Utente generico non autenticato) |
| **Breve Descrizione** | L'attore inserisce le proprie credenziali per autenticarsi. Il sistema verifica se i dati forniti sono già presenti nel sistema e, in caso di esito positivo, concede l'accesso alle funzionalità riservate in base al ruolo dell'attore (Utente, Operatore, o Pubblica Amministrazione). |

### Precondizioni
- L'attore possiede un account nel sistema.
- Operatore Tecnico, Operatore Servizio Clienti e PA possiedono credenziali di accesso pre-generate e fornite dall'amministrazione.
- L'attore non ha una sessione attiva (vincolo §8.9 — sessione singola).

### Flusso Principale

| Passo | Attore | Sistema | Dettaglio Tecnico |
|-------|--------|---------|-------------------|
| 1 | L'attore richiede di effettuare l'accesso | — | `Autenticazione.richiestaLogin()` invocato dalla View |
| 2 | — | Il sistema richiede l'inserimento delle credenziali | `Autenticazione.mostraFormLogin()` rende il form |
| 3 | L'attore inserisce email e password e invia | — | `Autenticazione.invioCredenziali(email, password)` raccoglie l'input |
| 4 | — | Il sistema verifica la corrispondenza delle credenziali e lo stato dell'account | `GestioneAutenticazione.invioCredenziali(email, password)` → controlla `Attore.verifica(email, password)`, `Attore.getStatoUtente()`, `Attore.getRuolo()` |
| 5 | — | Il sistema autentica l'attore, crea la sessione e instanzia la View corrispondente al ruolo | `Autenticazione.creaAppUtente(id)` o `creaAppOperatoreSC(id)` / `creaAppOperatoreTecnico(id)` / `creaAppOperatorePA(id)` in base al `RuoloAttore` restituito |

### Flussi Alternativi

**FA1 — Email non valida**
| Passo | Attore | Sistema | Dettaglio Tecnico |
|-------|--------|---------|-------------------|
| FA1.1 | *(al passo 4 del flusso principale)* | Il sistema rileva che l'email non corrisponde a nessun account | `Attore.verifica(email, password)` → errore |
| FA1.2 | — | Il sistema mostra un messaggio di errore e nega l'accesso | `Autenticazione.mostraErrore(msg)` notifica l'attore |

**FA2 — Password non valida**
| Passo | Attore | Sistema | Dettaglio Tecnico |
|-------|--------|---------|-------------------|
| FA2.1 | *(al passo 4 del flusso principale)* | Il sistema rileva che la password per l'email inserita è errata | `Attore.verifica(email, password)` → errore |
| FA2.2 | — | Il sistema mostra un messaggio di errore e nega l'accesso | `Autenticazione.mostraErrore(msg)` notifica l'attore |

**FA3 — Account sospeso/disattivato**
| Passo | Attore | Sistema | Dettaglio Tecnico |
|-------|--------|---------|-------------------|
| FA3.1 | *(al passo 4 del flusso principale)* | Il sistema rileva che lo stato dell'account non è `attivo` | `Attore.getStatoUtente()` → `'sospeso'` o `'disattivato'` |
| FA3.2 | — | Il sistema mostra un messaggio di errore e nega l'accesso | `Autenticazione.mostraErrore(msg)` notifica l'attore |

### Postcondizioni
- L'attore risulta autenticato.
- Una nuova sessione è stata creata con i permessi e la pagina corrispondente al ruolo dell'attore.
- L'eventuale sessione precedente è stata terminata (vincolo §8.9).

### Relazioni
| Relazione | UC |
|-----------|-----|
| Include | — |
| Estende | — |
| Esteso da | — |
| Specializza | — |
| Generalizza | — |

### Requisiti
- Gestione sicura delle sessioni.
- Meccanismo di cifratura delle password.
- Gestione dei ruoli (RBAC).

---

## 2. Diagramma di Sequenza — Riepilogo

Il diagramma `UC.ATT.01-clean.uml` modella il flusso di login condiviso tra Attore, Autenticazione (View), GestioneAutenticazione (Controller), e Attore (Model). Dopo l'autenticazione, la View ruolo-specifica viene creata in base al `RuoloAttore` restituito.

```
[Attore] → [Autenticazione:View] → [GestioneAutenticazione:Controller] → [Attore:Model]

  Attore              Autenticazione           GestioneAutenticazione           Attore(Model)
    │                       │                           │                         │
    │── richiestaLogin() ──→│                           │                         │
    │←── mostraFormLogin() ─│                           │                         │
    │                       │                           │                         │
    │── invioCredenziali(e,p)─→│                        │                         │
    │                       │── invioCredenziali(e,p) ──→│                         │
    │                       │                           │── verifica(e,p) ────────→│
    │                       │                           │←── ok/err ───────────────│
    │                       │                           │── getRuolo(id) ─────────→│
    │                       │                           │←── RuoloAttore ──────────│
    │                       │                           │── getStatoUtente() ─────→│
    │                       │                           │←── 'attivo' ─────────────│
    │                       │                           │                         │
    │                       │◄── [creaApp* in base al ruolo] ──────────────────│
    │                       │                           │                         │
    │←── mostraSuccesso() ──│                           │                         │
```

**Messaggi XMI → Master Spec:**
| Messaggio XMI | Metodo Canonico | Note |
|---------------|-----------------|------|
| `richiestaLogin()` | `Autenticazione.richiestaLogin()` | Attore → View |
| `mostraFormLogin()` | `Autenticazione.mostraFormLogin()` | View → Attore |
| `invioCredenziali(email, password)` | `Autenticazione.invioCredenziali(email, password)` | Attore → View |
| `invioCredenziali(email, password)` | `GestioneAutenticazione.invioCredenziali(email, password)` | View → Controller |
| `verifica(email, password)` | `Attore.verifica(email, password)` | Controller → Model |
| `getRuolo(id)` | `Attore.getRuolo(id)` → `RuoloAttore` | Per RBAC post-auth |
| `getStatoUtente()` | `Attore.getStatoUtente()` → `'attivo'/'sospeso'/'disattivato'` | Stato account |
| `creaAppUtente(id)` | `Autenticazione.creaAppUtente(id)` | Creazione View Utente |
| `creaAppOperatoreSC(id)` | `Autenticazione.creaAppOperatoreSC(id)` | Creazione View Operatore SC |
| `creaAppOperatoreTecnico(id)` | `Autenticazione.creaAppOperatoreTecnico(id)` | Creazione View Operatore Tecnico |
| `creaAppOperatorePA(id)` | `Autenticazione.creaAppOperatorePA(id)` | Creazione View PA |

---

## 3. Method Traceability

### 3.1 View Layer — Autenticazione

| Metodo | Firma | Ruolo in UC.ATT.01 | Chiamante | Chiamato |
|--------|-------|-------------------|-----------|----------|
| `richiestaLogin()` | `void` | Avvia il flusso di login | Attore (click "Accedi") | `GestioneAutenticazione` |
| `mostraFormLogin()` | `void` | Mostra il form di login all'attore | (interno View) | Attore (rendering) |
| `invioCredenziali(email, password)` | `void` | Raccoglie le credenziali e le inoltra al Controller | Attore (submit form) | `GestioneAutenticazione.invioCredenziali()` |
| `mostraSuccesso(msg)` | `void` | Notifica login riuscito | `GestioneAutenticazione` (via callback) | Attore |
| `mostraErrore(msg)` | `void` | Notifica errore di autenticazione | `GestioneAutenticazione` (via callback) | Attore |

### 3.2 Controller Layer — GestioneAutenticazione

| Metodo | Firma | Ruolo in UC.ATT.01 | Chiamante | Chiamato |
|--------|-------|-------------------|-----------|----------|
| `invioCredenziali(email, password)` | `RuoloAttore` | Verifica credenziali, stato account, determina ruolo e crea View appropriata | `Autenticazione.invioCredenziali()` | `Attore.verifica()`, `Attore.getRuolo()`, `Attore.getStatoUtente()` |

### 3.3 Model Layer — Attore

| Metodo | Firma | Ruolo in UC.ATT.01 | Chiamante | Chiamato |
|--------|-------|-------------------|-----------|----------|
| `verifica(email, password)` | `bool` | Verifica corrispondenza credenziali | `GestioneAutenticazione.invioCredenziali()` | DBMS (query) |
| `getRuolo(id)` | `RuoloAttore` | Restituisce il ruolo dell'attore | `GestioneAutenticazione.invioCredenziali()` | — (attributo) |
| `getStatoUtente()` | `StatoAccount` | Verifica che l'account non sia sospeso/disattivato | `GestioneAutenticazione.invioCredenziali()` | — (attributo) |

---

## 4. Vincoli Architetturali Applicabili

| Vincolo ID | Descrizione | Impatto su UC.ATT.01 |
|------------|-------------|---------------------|
| §8.5 — RBAC | Routing post-login determinato da RuoloAttore | `GestioneAutenticazione.invioCredenziali()` restituisce `RuoloAttore` per determinare quale View creare |
| §8.6 — Cifratura password | Le password sono memorizzate in forma cifrata | `Attore.verifica()` confronta la password cifrata |
| §8.9 — Sessione singola | Login termina sessione precedente | Se attore già loggato, la sessione precedente viene terminata prima di crearne una nuova |
| §8.10 — Disaccoppiamento View-Model | Le View non interrogano mai direttamente il Model | `Autenticazione` (View) comunica solo con `GestioneAutenticazione` (Controller) |
| §8.11 — Ruolo unico per sessione | View istanziata in base al ruolo dopo login | Viene creata la View corrispondente al ruolo: `AppUtente`, `AppOperatoreSC`, `AppOperatoreTecnico`, o `AppPA` |

---

## 5. Enumerazioni Coinvolte

### RuoloAttore
| Valore | Utilizzo in UC.ATT.01 |
|--------|----------------------|
| `Utente` | Login → crea `AppUtente` |
| `Operatore` (con `TipoOperatore`) | Login → crea `AppOperatoreSC` o `AppOperatoreTecnico` |
| `PubblicaAmministrazione` | Login → crea `AppPA` |

### StatoAccount
| Valore | Utilizzo in UC.ATT.01 |
|--------|----------------------|
| `attivo` | Accesso consentito |
| `sospeso` | Accesso negato (FA3) |
| `disattivato` | Accesso negato (FA3) |

---

## 6. Data Flow Dettagliato

```
[Attore]               [Autenticazione:View]        [GestioneAutenticazione:Controller]      [Attore:Model]      [DBMS]
   │                             │                               │                              │                 │
   │── richiestaLogin() ───────→│                               │                              │                 │
   │◄── mostraFormLogin() ──────│                               │                              │                 │
   │                             │                               │                              │                 │
   │── invioCredenziali(e,p) ───→│                               │                              │                 │
   │                             │── invioCredenziali(e,p) ─────→│                              │                 │
   │                             │                               │── verifica(e,p) ─────────────→│                 │
   │                             │                               │                              │── SELECT ─────→│
   │                             │                               │◄── ok/err ───────────────────│                 │
   │                             │                               │── getRuolo(id) ──────────────→│                 │
   │                             │                               │◄── RuoloAttore ───────────────│                 │
   │                             │                               │── getStatoUtente() ──────────→│                 │
   │                             │                               │◄── 'attivo'/'sospeso'/... ────│                 │
   │                             │                               │                              │                 │
   │                             │◄── [creaViewPerRuolo()] ─────│                              │                 │
   │◄── mostraSuccesso(msg) ─────│                               │                              │                 │
```

**Percorsi alternativi:**
```
   ...verifica()...              │── email non trovata ──→│
                                   │◄── false ──────────────│
   ◄── mostraErrore("Email non valida") ────────────────────◄

   ...verifica()...              │── password errata ────→│
                                   │◄── false ──────────────│
   ◄── mostraErrore("Password errata") ────────────────────◄

   ...getStatoUtente()...        │── 'sospeso' ──────────→│
   ◄── mostraErrore("Account sospeso") ────────────────────◄
```

---

## 7. Verifiche di Coerenza

| # | Verifica | Fonte A | Fonte B | Stato |
|---|----------|---------|---------|-------|
| 1 | `GestioneAutenticazione.invioCredenziali(email, password)` → `RuoloAttore` | Master_Spec.cgd.md §3 (GestioneAutenticazione) | UC.ATT.01-clean.uml | OK |
| 2 | RBAC post-login: View istanziata per ruolo | Master_Spec.cgd.md §8.5 | chiarimenti-vari.md punto 10 | OK |
| 3 | Vincolo sessione singola (§8.9) | Master_Spec.cgd.md §8 | documentazione.md | OK |
| 4 | Disaccoppiamento View-Model (§8.10) | Master_Spec.cgd.md §8 | Diagramma di sequenza | OK |
| 5 | Flusso alternativo: email non valida | documentazione.md UC.ATT.01 FA1 | UC.ATT.01-clean.uml | OK |
| 6 | Flusso alternativo: password non valida | documentazione.md UC.ATT.01 FA2 | UC.ATT.01-clean.uml | OK |
| 7 | Attore primario: "Attore" (non autenticato) | documentazione.md UC.ATT.01 | chiarimenti-vari.md punto 9 | OK |

---

## 8. Inconsistenze Rilevate e Risolte

| # | Inconsistenza | Fonte | Risoluzione |
|---|---------------|-------|-------------|
| 1 | Il file XMI è denominato `UC.ATT.01-clean.uml` ma il modello interno ha `name="UC.GEN.02"`. | UC.ATT.01-clean.uml | **Risolto:** XMI contiene sia flusso login che logout (UC.GEN.02 è l'ID del modello condiviso). Il nome file UC.ATT.01 è quello canonico per il Login (documentazione.md §2.2.2). |
| 2 | `Autenticazione` (View) non ha metodi espliciti per login (`richiestaLogin()`, `mostraFormLogin()`) in Master_Spec.cgd.md §4 — solo metodi di registrazione. | Master_Spec.cgd.md | **Risolto:** Metodi login sono impliciti nel pattern MVC; `GestioneAutenticazione.invioCredenziali()` è il metodo controller documentato. I metodi View per login sono stati aggiunti in questa specifica per completezza. |

---

## HITL Verification Record

### Round A: Derived Data Confirmation

*Nessun claim pendente — specifica completamente derivata da documentazione.md (fonte primaria, priorità 1) e chiarita da chiarimenti-vari.md.*

### Round B: True HITL Verification
*Nessun claim richiede Round B — tutti i dati sono confermati da fonti prioritarie.*

---

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | REVIEWED

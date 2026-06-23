---
clarity-gate-version: 2.1
document-type: Implementation
processed-date: 2026-06-23
processed-by: AI (Cross-Reference Engine) — Master_Spec.cgd.md v4.0, documentazione.md v3.0, classDiagram-v1.8-clean.uml, UC.UT.08-clean.uml, response2.md
clarity-status: CLEAR
hitl-status: REVIEWED
hitl-pending-count: 0
points-passed: 1-9
document-sha256: ddc500236187d4a4549806715c1c12de60337da6610e14371b05ef607d239589
hitl-claims:
  - id: claim-3a9f1c02
    text: "Autenticazione View chiama GestioneAutenticazione.verificaValidita(nome,cognome,email,password,datanascita) e inoltra la risposta senza mai interpellare direttamente Utente Model"
    value: "CONFERMATO: MVC disaccoppiamento View-Model confermato."
    source: "Master_Spec.cgd.md §4 (Autenticazione) + §6 (View→Controller dipendenze) + documentazione.md §2.3 (MVC Controller Intermediario)"
    location: "UC.UT.08/sequential-flow"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-7b2d5e09
    text: "GestioneAutenticazione.verificaValidita() restituisce RuoloAttore.Utente in caso di registrazione riuscita — il valore di ritorno RuoloAttore è semanticamente valido anche in contesto pre-login (indica il ruolo dell'account creato)"
    value: "CONFERMATO: verificaValidita restituisce RuoloAttore confermato."
    source: "Master_Spec.cgd.md §3 (GestioneAutenticazione.verificaValidita) + documentazione.md UC.UT.08 flusso principale passo 4"
    location: "UC.UT.08/controller-flow"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
---

# UC.UT.08 — Registrazione Utente

**Versione:** 1.0 *(AI-Ready — cross-reference Master Spec, documentazione.md, sequence diagram)*
**Team:** Cofee Coders
**Progetto:** Ingegneria del Software a.a. 2025/2026
**Sprint:** 3
**Priorità:** 50

**Fonti (in ordine di priorità):**
1. `documentazione.md` v3.0 — specifica UC primaria (UC.UT.08, §2.2.2)
2. `Master_Spec.cgd.md` v4.0 — firme metodi, enumerazioni, vincoli architetturali
3. `UC.UT.08-clean.uml` — diagramma di sequenza
4. `chiarimenti-vari.md` — vincoli e interpretazioni

---

## 1. Use Case Specification

| Campo | Valore |
|-------|--------|
| **ID** | UC.UT.08 |
| **Nome** | Registrazione Utente |
| **User Story** | *(non mappata a user story esplicita — funzionalità fondamentale per il funzionamento del sistema, chiarimenti-vari.md punto 5)* |
| **Attore Primario** | Utente (non registrato) |
| **Breve Descrizione** | L'utente non registrato inserisce i propri dati anagrafici e le credenziali per creare un nuovo profilo. Il sistema verifica la validità dei dati e l'assenza di duplicati, creando il nuovo account utente con password cifrata. |

### Precondizioni
- L'utente non dispone di un account nel sistema.
- L'utente ha accesso alla View `Autenticazione` (pre-auth).

### Flusso Principale

| Passo | Attore | Sistema | Dettaglio Tecnico |
|-------|--------|---------|-------------------|
| 1 | L'utente non registrato richiede di registrarsi | — | `Autenticazione.registrazioneUtente()` invocato dalla View |
| 2 | — | Il sistema mostra il form di registrazione | `Autenticazione.mostraFormRegistrazione()` rende il form |
| 3 | L'utente inserisce i dati (nome, cognome, email, password, data di nascita) e invia | — | `Autenticazione.inserisciCredenziali(nome, cognome, email, password, datanascita)` raccoglie l'input |
| 4 | — | Il sistema verifica formato e unicità email, crea l'account | `GestioneAutenticazione.verificaValidita(nome, cognome, email, password, datanascita)` → `RuoloAttore.Utente` su successo; internamente chiama `Utente.creaAccountUtente(nome, cognome, email, password, datanascita)` |
| 5 | — | Il sistema salva le credenziali con password cifrata | `Attore.setPassword(password)` memorizza password in forma cifrata *(vincolo §8.6)* |
| 6 | — | Il sistema conferma l'avvenuta registrazione | Notifica di successo propagata via View |

### Flussi Alternativi

**FA1 — Formattazione non valida**
| Passo | Attore | Sistema | Dettaglio Tecnico |
|-------|--------|---------|-------------------|
| FA1.1 | *(al passo 4 del flusso principale)* | Il sistema rileva campi obbligatori vuoti o formati non rispettati | `GestioneAutenticazione.verificaValidita()` rileva errore di validazione |
| FA1.2 | — | Il sistema mostra un messaggio di errore e richiede la correzione | `Autenticazione.mostraErrore(msg)` notifica l'attore |
| FA1.3 | Il flusso riprende dal passo 2 del flusso principale | | |

**FA2 — Email già in uso**
| Passo | Attore | Sistema | Dettaglio Tecnico |
|-------|--------|---------|-------------------|
| FA2.1 | *(al passo 4 del flusso principale)* | Il sistema rileva che l'email è già associata a un account esistente *(vincolo §8.13 — unicità email)* | `GestioneAutenticazione.verificaValidita()` rileva duplicato email |
| FA2.2 | — | Il sistema mostra un messaggio di errore e suggerisce di effettuare il login | Notifica: "Email già registrata. Effettua il login." |
| FA2.3 | Il caso d'uso termina | | |

### Postcondizioni
- Il nuovo account è stato creato ed è presente nel sistema.
- Le credenziali sono state salvate con password cifrata.
- L'utente può ora procedere con UC.ATT.01 (Login).

### Relazioni
| Relazione | UC |
|-----------|-----|
| Include | — |
| Estende | — |
| Esteso da | — |
| Specializza | — |
| Generalizza | — |

### Requisiti
- Cifratura delle password (vincolo §8.6).
- Validazione dei dati di input (formato campi).
- Unicità dell'email nel sistema (vincolo §8.13).

---

## 2. Diagramma di Sequenza — Riepilogo

Il diagramma `UC.UT.08-clean.uml` modella 4 lifeline: **Attore** (utente non registrato), **Autenticazione** (View), **GestioneAutenticazione** (Controller), **Utente** (Model).

```
Attore → Autenticazione → GestioneAutenticazione → Utente
  │            │                   │                  │
  │─registrazioneUtente()────────→│                  │
  │            │                   │                  │
  │←──mostraFormRegistrazione()───│                  │
  │            │                   │                  │
  │──inserisciCredenziali(n,c,e,p,d)→│               │
  │            │                   │                  │
  │            │──verificaValidita(n,c,e,p,d)────→   │
  │            │                   │                  │
  │            │                   │──creaAccountUtente(n,c,e,p,d)→
  │            │                   │←─────successo───│
  │            │←──account creato──│                  │
  │←──notifica successo───────────│                  │
```

**Messaggi XMI → Master Spec:**
| Messaggio XMI | Metodo Master Spec | Note |
|---------------|-------------------|------|
| `registrazioneUtente()` | `Autenticazione.registrazioneUtente()` | View → Controller |
| `mostraFormRegistrazione()` | `Autenticazione.mostraFormRegistrazione()` | View propaga all'attore |
| `inserisciCredenziali(nome,cognome,email,password,datanascita)` | `Autenticazione.inserisciCredenziali(nome,cognome,email,password,datanascita)` | Attore → View |
| `verificaValidità(nome,cognome,email,password,datanascita)` | `GestioneAutenticazione.verificaValidita(nome,cognome,email,password,datanascita)` | **Typo XMI:** accento su `à` — corretto a `verificaValidita` nel Master Spec |
| `creaAccountUtente(nome,cognome,email,password,datanascita)` | `Utente.creaAccountUtente(nome, cognome, email, password, datanascita)` | ✓ firma verificata |
| `successo` | (messaggio di ritorno) | Model → Controller |
| `account creato` | (messaggio di ritorno) | Controller → View |
| `errore_email` | (messaggio alternativo FA2) | Notifica duplicato |
| `errore_formattazione` | (messaggio alternativo FA1) | Notifica formato errato |

---

## 3. Method Traceability

### 3.1 View Layer — Autenticazione

| Metodo | Firma | Ruolo in UC.UT.08 | Chiamante | Chiamato |
|--------|-------|-------------------|-----------|----------|
| `registrazioneUtente()` | `void` | Avvia il flusso di registrazione | Attore (click "Registrati") | `GestioneAutenticazione` |
| `mostraFormRegistrazione()` | `void` | Mostra il form all'attore | (interno View) | Attore (rendering) |
| `inserisciCredenziali(nome, cognome, email, password, datanascita)` | `void` | Raccoglie i dati inseriti dall'attore | Attore (submit form) | `GestioneAutenticazione.verificaValidita()` |

### 3.2 Controller Layer — GestioneAutenticazione

| Metodo | Firma | Ruolo in UC.UT.08 | Chiamante | Chiamato |
|--------|-------|-------------------|-----------|----------|
| `verificaValidita(nome, cognome, email, password, datanascita)` | `RuoloAttore` | Valida formato, verifica unicità email, orchestra creazione account | `Autenticazione.inserisciCredenziali()` | `Utente.creaAccountUtente()`, DBMS (check email) |

### 3.3 Model Layer — Utente

| Metodo | Firma | Ruolo in UC.UT.08 | Chiamante | Chiamato |
|--------|-------|-------------------|-----------|----------|
| `creaAccountUtente(nome, cognome, email, password, datanascita)` | `void` | Crea nuova istanza Utente con dati validati | `GestioneAutenticazione.verificaValidita()` | `Attore.setPassword()`, `Attore.setEmail()`, `Attore.setRuolo(RuoloAttore.Utente)` |

### 3.4 Model Layer — Attore (base class)

| Metodo | Firma | Ruolo in UC.UT.08 | Chiamante | Chiamato |
|--------|-------|-------------------|-----------|----------|
| `setPassword(password)` | `void` | Memorizza password in forma cifrata | `Utente.creaAccountUtente()` | DBMS (persistenza) |
| `setEmail(email)` | `void` | Imposta l'email dell'account | `Utente.creaAccountUtente()` | DBMS (persistenza) |
| `setRuolo(ruolo)` | `void` | Imposta `RuoloAttore.Utente` | `Utente.creaAccountUtente()` | — (attributo) |

---

## 4. Vincoli Architetturali Applicabili

| Vincolo ID | Descrizione | Impatto su UC.UT.08 |
|------------|-------------|---------------------|
| §8.6 — Cifratura password | Le password sono memorizzate in forma cifrata | `Attore.setPassword()` cifra prima di persistere |
| §8.13 — Unicità email | L'indirizzo email deve essere univoco nel sistema | `GestioneAutenticazione.verificaValidita()` controlla duplicati via DBMS prima di creare l'account |
| §8.10 — Disaccoppiamento View-Model | Le View non interrogano mai direttamente il Model | `Autenticazione` (View) comunica solo con `GestioneAutenticazione` (Controller); mai con `Utente` (Model) |
| §8.5 — RBAC | Routing post-login determinato da RuoloAttore | Al termine della registrazione, il ruolo viene impostato a `RuoloAttore.Utente` |

---

## 5. Enumerazioni Coinvolte

### RuoloAttore
| Valore | Utilizzo in UC.UT.08 |
|--------|----------------------|
| `Utente` | Assegnato all'account creato al termine della registrazione |

---

## 6. Data Flow Dettagliato

```
[Attore]                                 [Autenticazione:View]              [GestioneAutenticazione:Controller]      [Utente:Model]          [DBMS]
   │                                              │                                     │                         │                     │
   │── click "Registrati" ──────────────────────→│                                     │                         │                     │
   │                                              │── registrazioneUtente() ───────────→│                         │                     │
   │←── mostraFormRegistrazione() ───────────────│←────────────────────────────────────│                         │                     │
   │                                              │                                     │                         │                     │
   │── inserisciCredenziali(n,c,e,p,d) ─────────→│                                     │                         │                     │
   │                                              │── verificaValidita(n,c,e,p,d) ─────→│                         │                     │
   │                                              │                                     │── check email unique ──────────────→│
   │                                              │                                     │←── email disponibile ──────────────│
   │                                              │                                     │── creaAccountUtente(n,c,e,p,d) ───→│
   │                                              │                                     │                         │── INSERT attore ──→│
   │                                              │                                     │                         │── INSERT utente ──→│
   │                                              │                                     │←── RuoloAttore.Utente ─────────────│
   │←── notifica successo ───────────────────────│←────────────────────────────────────│                         │                     │
```

**Percorsi alternativi:**
```
   ...verificaValidita()...                  │── check email ─→│  (email già presente)
                                              │←── DUPLICATO ──│
                                              │──→ errore_email
                          ←── notifica errore (email in uso, suggerisci login) ──→ Attore

   ...verificaValidita()...                  │── validazione formato fallita
                                              │──→ errore_formattazione
                          ←── notifica errore (campi non validi) ──→ Attore
```

---

## 7. Verifiche di Coerenza

| # | Verifica | Fonte A | Fonte B | Stato |
|---|----------|---------|---------|-------|
| 1 | Firma `creaAccountUtente(nome, cognome, email, password, datanascita)` | Master_Spec.cgd.md §2 (Utente, riga 258) | UC.UT.08-clean.uml | OK — firma identica (XMI senza spazi) |
| 2 | `verificaValidita()` restituisce `RuoloAttore` | Master_Spec.cgd.md §3 (GestioneAutenticazione, riga 523) | — | OK |
| 3 | View `Autenticazione` possiede `mostraFormRegistrazione()`, `inserisciCredenziali()`, `registrazioneUtente()` | Master_Spec.cgd.md §4 (Autenticazione, righe 812-814) | UC.UT.08-clean.uml | OK — metodi corrispondono |
| 4 | Vincolo cifratura password (§8.6) | Master_Spec.cgd.md §8 (riga 961) | documentazione.md UC.UT.08 requisiti | OK |
| 5 | Vincolo unicità email (§8.13) | Master_Spec.cgd.md §8 (riga 968) | documentazione.md UC.UT.08 flusso principale passo 4 | OK |
| 6 | Flusso alternativo: formato errato → errore | documentazione.md UC.UT.08 FA1 | UC.UT.08-clean.uml (`errore_formattazione`) | OK |
| 7 | Flusso alternativo: email in uso → suggerisci login | documentazione.md UC.UT.08 FA2 | UC.UT.08-clean.uml (`errore_email`) | OK |
| 8 | Attore primario: "Utente (Non registrato)" | documentazione.md UC.UT.08 | Master_Spec.cgd.md §7 | OK — attore non registrato è coerente con glossario "Attore" (chiarimenti-vari.md punto 9) |
| 9 | Postcondizioni in passato prossimo | chiarimenti-vari.md punto 1 | UC.UT.08 postcondizioni | OK |
| 10 | Disaccoppiamento View-Model (§8.10) | Master_Spec.cgd.md §8 (riga 965) | Diagramma di sequenza | OK — View non comunica mai con Model |

---

## 8. Inconsistenze Rilevate e Risolte

| # | Inconsistenza | Fonte | Risoluzione |
|---|---------------|-------|-------------|
| 1 | XMI usa `verificaValidità` (accento) vs Master Spec `verificaValidita` | UC.UT.08-clean.uml | **Risolto:** Master Spec ha priorità (chiarimenti-vari.md punto 15). La versione senza accento è quella canonica. |
| 2 | Sequenza `verificaValidita()` viene mostrata tra View e Controller nel diagramma, ma il metodo è sul Controller. Le due occorrenze (send/receive) sono su Attore e Autenticazione. | UC.UT.08-clean.uml | **Risolto:** Artefatto XMI di esportazione. La semantica corretta è: View chiama `GestioneAutenticazione.verificaValidita()`. Il diagramma mostra il flusso logico Actor→View→Controller, dove il messaggio "attraversa" la View. |

---

## 9. Test Case Specifications

| ID | Component | Scenario | Preconditions | Input | Expected Result | Postconditions | Edge Cases |
|----|-----------|----------|---------------|-------|-----------------|----------------|------------|
| TC-UT.08-01 | Autenticazione (View) | Avvio registrazione utente | Utente non registrato, View Autenticazione attiva | Click "Registrati" | Autenticazione.registrazioneUtente() invocato | mostraFormRegistrazione() renderizzato | Utente già loggato, sessione scaduta |
| TC-UT.08-02 | GestioneAutenticazione (Controller) | Validazione dati di registrazione corretti | Form compilato con dati validi | nome, cognome, email, password, datanascita validi | GestioneAutenticazione.verificaValidita() restituisce RuoloAttore.Utente | Utente.creaAccountUtente() invocato | Email con caratteri speciali, password al limite lunghezza |
| TC-UT.08-03 | Utente (Model) | Creazione account utente nel sistema | Dati validati, email univoca | nome, cognome, email, password, datanascita | Utente.creaAccountUtente() crea istanza e chiama Attore.setPassword(), setEmail(), setRuolo() | Account persistito nel DBMS | Nome con spazi, data nascita futura |
| TC-UT.08-04 | Attore (Model) | Cifratura password durante registrazione | Account in creazione, password in chiaro | password: String | Attore.setPassword() memorizza password in forma cifrata | Password non recuperabile in chiaro | Password vuota, password massima lunghezza |
| TC-UT.08-05 | Autenticazione (View) | Visualizzazione form di registrazione | registrazioneUtente() invocato | — | Autenticazione.mostraFormRegistrazione() rende il form con tutti i campi | Utente può inserire credenziali | Schermo ridotto, accessibilità |
| TC-UT.08-06 | Integrazione (FA1) | Formattazione dati non valida | Form compilato con campi vuoti o formato errato | nome vuoto, email senza @, password corta | GestioneAutenticazione.verificaValidita() rileva errore; mostraErrore(msg) | Flusso riprende dal passo 2 (mostraFormRegistrazione) | Tutti i campi vuoti, injection tentatives |
| TC-UT.08-07 | Integrazione (FA2) | Email già in uso nel sistema | Email inserita già associata ad altro account | email duplicata | GestioneAutenticazione.verificaValidita() rileva duplicato; mostraErrore("Email già registrata. Effettua il login.") | Caso d'uso termina; account non creato | Stessa email con case diverso, email liberata dopo cancellazione |
| TC-UT.08-08 | Integrazione | Registrazione completa end-to-end | Tutti i dati validi, email univoca | nome, cognome, email, password, datanascita validi | Sistema crea account, salva password cifrata, mostra notifica successo | Nuovo account presente in DBMS; utente può procedere con UC.ATT.01 | Concorrenza: stessa email inviata simultaneamente |

## 10. Error Handling Matrix

| ERR-ID | Error Type | Component | Detection Point | System Response | Fallback | Logging |
|--------|-----------|-----------|-----------------|----------------|----------|---------|
| ERR-UT.08-01 | Validazione formato fallita | GestioneAutenticazione (Controller) | GestioneAutenticazione.verificaValidita() rileva campi vuoti o formato errato | Autenticazione.mostraErrore(msg) con dettaglio campo errato | Flusso riprende dal passo 2 — form ripresentato | Log info: errore validazione registrazione — campo X non valido |
| ERR-UT.08-02 | Violazione vincolo unicità email | GestioneAutenticazione (Controller) | GestioneAutenticazione.verificaValidita() rileva email già presente in DBMS | Autenticazione.mostraErrore("Email già registrata. Effettua il login.") | Caso d'uso termina; nessun account creato | Log warning: tentativo registrazione con email duplicata |
| ERR-UT.08-03 | Password non conforme a policy | GestioneAutenticazione (Controller) | GestioneAutenticazione.verificaValidita() rileva password < 8 caratteri o senza requisiti minimi | Autenticazione.mostraErrore("Password non conforme ai requisiti di sicurezza") | Flusso riprende dal passo 2 — form ripresentato | Log info: password rifiutata per policy |
| ERR-UT.08-04 | DBMS non raggiungibile | Utente (Model) | Utente.creaAccountUtente() fallisce per errore di connessione DBMS | Autenticazione.mostraErrore("Errore di sistema. Riprovare più tardi.") | Caso d'uso termina; nessun dato persisto | Log critical: DBMS non raggiungibile durante registrazione |
| ERR-UT.08-05 | Dati anagrafici incompleti | GestioneAutenticazione (Controller) | GestioneAutenticazione.verificaValidita() rileva uno o più campi obbligatori mancanti | Autenticazione.mostraErrore("Tutti i campi sono obbligatori") | Flusso riprende dal passo 2 — form ripresentato con campi vuoti evidenziati | Log info: tentativo registrazione con campi incompleti |

---

## HITL Verification Record

### Round A: Derived Data Confirmation

| # | Claim ID | Claim | Fonte | Stato |
|---|----------|-------|-------|-------|
| 1 | claim-3a9f1c02 | View non interpella mai direttamente Utente Model — solo Controller lo fa | Master_Spec.cgd.md §4, §6 + documentazione.md §2.3 | PENDING |
| 2 | claim-7b2d5e09 | `verificaValidita()` restituisce `RuoloAttore.Utente` su registrazione riuscita | Master_Spec.cgd.md §3 | PENDING |

### Round B: True HITL Verification
*Nessun claim richiede Round B — tutti i claim sono derivati da fonti verificate nella sessione corrente.*

---

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | PENDING

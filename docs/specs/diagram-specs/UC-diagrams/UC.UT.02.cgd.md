---
clarity-gate-version: 2.1
document-type: Implementation
processed-date: 2026-06-23
processed-by: Clarity Gate (AI) — Cross-Reference: documentazione.md §2.2.2, Master_Spec.cgd.md §3 §7, UC.UT.02-clean.uml, chiarimenti-vari.md, response2.md
clarity-status: CLEAR
hitl-status: REVIEWED
hitl-pending-count: 0
points-passed: 1-9
points-failed:
document-sha256: ff81519d246d8d12e0b2c506ac681ac26a56035f5ff1715ea1bf5db769affad6
hitl-claims:
  - id: claim-uc02-001
    text: "inviaRichiestaPrenotazione() signature: Master_Spec mostra no-args (void), UML mostra (idMezzo, idUtente). Quale è la firma corretta?"
    value: "CONFERMATO: idMezzo and idUtente (two params)."
    source: "Master_Spec GestionePrenotazione §3 vs UC.UT.02-clean.uml"
    location: "GestionePrenotazione/inviaRichiestaPrenotazione"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-uc02-002
    text: "notificaScadenzaTempo(idPrenotazione) direction: UML lo mostra come AppUtente → GestionePrenotazione (synchCall), ma documentazione.md dice 'Il sistema invia una notifica di annullamento'. La direzione UML è invertita per errore XMI?"
    value: "CONFERMATO: AppUtente → GestionePrenotazione IS correct. CGD was wrong to question the direction."
    source: "UC.UT.02-clean.uml vs documentazione.md §2.2.2 UC.UT.02 flusso alternativo"
    location: "UC.UT.02/AlternativeFlow/notificaScadenzaTempo"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-uc02-003
    text: "mostraSuccesso() signature: Master_Spec AppUtente mostra no-args (void), UML mostra (messaggio). AppOperatoreSC e AppOperatoreTecnico hanno mostraSuccesso(msg) con parametro."
    value: "CONFERMATO: with parameter (messaggio)."
    source: "Master_Spec AppUtente §4 vs UC.UT.02-clean.uml vs AppOperatoreSC/AppOperatoreTecnico"
    location: "AppUtente/mostraSuccesso"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-uc02-004
    text: "Prenotazione.setStato(scaduta) non è esplicitamente mostrato nel diagramma di sequenza né nel testo documentazione.md, ma è logicamente necessario durante il timeout. StatoPrenotazione enum include 'scaduta' (Timeout superato, automaticamente annullata)."
    value: "CONFERMATO: necessario e aggiornato nell'XMI."
    source: "Inferenza da Master_Spec StatoPrenotazione §1 + documentazione.md flusso alternativo"
    location: "UC.UT.02/AlternativeFlow/Prenotazione.setStato(scaduta)"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
---

# UC.UT.02 — Prenotazione Mezzo

## Use Case Specification

| Campo | Descrizione |
|:------|:------------|
| **UserStories** | UT.02 |
| **Nome** | Prenotazione Mezzo |
| **ID** | **UC.UT.02** |
| **Breve descrizione** | L'utente seleziona un mezzo disponibile e ne richiede la prenotazione. Il sistema verifica la disponibilità, aggiorna lo stato del mezzo a "prenotato". È previsto un meccanismo automatico di annullamento se l'utente non raggiunge il mezzo entro **15 minuti** dell'orario prenotato. |
| **Attori principali** | Utente |
| **Precondizioni** | 1. L'utente ha effettuato l'accesso. 2. La mappa con i mezzi è stata resa disponibile (e quindi visibile) dal sistema. |
| **Postcondizioni** | 1. Il mezzo risulta "prenotato". 2. La prenotazione è stata registrata. 3. [Timeout] Il mezzo risulta "disponibile" e la prenotazione "scaduta". |
| **Include** | — |
| **Estende** | **UC.UT.01 (Ricerca Mezzi)** |
| **Esteso dal caso d'uso** | **UC.UT.03 (Gestione Corsa)** |
| **Specializza il caso d'uso** | — |
| **Generalizza il caso d'uso** | — |
| **Requisiti** | Meccanismo di timeout automatico a 15 minuti per l'annullamento delle prenotazioni scadute. |

---

## Flusso Principale

| # | Passo | Metodo | Sorgente | Verifica |
|:-:|:------|:-------|:---------|:---------|
| S1 | L'utente seleziona un mezzo disponibile dalla mappa e ne richiede la prenotazione | `AppUtente.selezionaMezzo(idMezzo)` | Master_Spec AppUtente §4 | ✓ |
| S2 | Il sistema riceve la richiesta di prenotazione | `GestionePrenotazione.inviaRichiestaPrenotazione()` | Master_Spec GestionePrenotazione §3 | ⚠️ HITL claim-uc02-001 |
| S3 | Il sistema registra la prenotazione | `Prenotazione.creaPrenotazione(idMezzo, idUtente, orarioInizio)` | Master_Spec Prenotazione §2 | ✓ |
| S4 | Il sistema aggiorna lo stato del mezzo a "prenotato" | `Mezzo.setStato(prenotato)` | Master_Spec Mezzo §2 + StatoMezzo §1 | ✓ |
| S5 | Il sistema restituisce il QR Code alla View | GestionePrenotazione → AppUtente (`QR_Code` return) | UC.UT.02-clean.uml | ✓ |
| S6 | La View mostra il QR Code all'utente | `AppUtente.mostraQRCode()` | Master_Spec AppUtente §4 | ✓ |
| S7 | L'utente visualizza la notifica di prenotazione avvenuta | *Notifica prenotazione avvenuta e mostra QR_Code* (messaggio Utente) | UC.UT.02-clean.uml | ✓ |

> **Nota sull'ordinamento:** documentazione.md descrive il flusso come "aggiorna stato mezzo → registra prenotazione" (S4 prima di S3), mentre il diagramma di sequenza UML mostra `creaPrenotazione()` prima di `setStato(prenotato)`. La differenza non ha impatto funzionale — entrambi gli approdi sono corretti. Il diagramma UML è stato usato come riferimento per l'ordine esatto nell'implementazione.

---

## Flusso Alternativo: Scadenza Tempo Prenotazione (Timeout 15 minuti)

**Guard:** Dopo 15 minuti dall'orario prenotato

| # | Passo | Metodo | Sorgente | Verifica |
|:-:|:------|:-------|:---------|:---------|
| A1 | Il sistema rileva il superamento del timeout di 15 minuti | `GestionePrenotazione.gestisciTimeout()` | Master_Spec GestionePrenotazione §3 | ✓ |
| A2 | Il sistema reimposta lo stato del mezzo a "disponibile" | `Mezzo.setStato(disponibile)` | Master_Spec Mezzo §2 + UC.UT.02-clean.uml | ✓ |
| A3 | Il sistema aggiorna lo stato della prenotazione a "scaduta" | `Prenotazione.setStato(scaduta)` | Inferenza da Master_Spec StatoPrenotazione §1 | ⚠️ HITL claim-uc02-004 |
| A4 | Il sistema invia la notifica di scadenza | `GestionePrenotazione.notificaScadenzaTempo(idPrenotazione)` | Master_Spec GestionePrenotazione §3 | ⚠️ HITL claim-uc02-002 |
| A5 | La View notifica l'utente dell'annullamento | `AppUtente.mostraSuccesso()` | Master_Spec AppUtente §4 | ⚠️ HITL claim-uc02-003 |
| A6 | L'utente visualizza la notifica di prenotazione annullata | *Notifica prenotazione annullata* (messaggio Utente) | UC.UT.02-clean.uml | ✓ |

---

## Context Map: Extends Relationships

```
UC.UT.01 (Ricerca Mezzi)
    │
    │  «extends» (entry point: utente ha trovato un mezzo)
    ▼
UC.UT.02 (Prenotazione Mezzo)  ← this UC
    │
    │  «extended by» (next step: utente usa QR code per avviare corsa)
    ▼
UC.UT.03 (Gestione Corsa)
```

| Relazione | UC | Significato |
|:----------|:---|:------------|
| UC.UT.02 **estende** | UC.UT.01 | UC.UT.02 si innesta dopo che UC.UT.01 ha mostrato i mezzi disponibili; l'utente seleziona un mezzo dalla mappa e richiede la prenotazione |
| UC.UT.02 **è esteso da** | UC.UT.03 | UC.UT.03 inizia con la scansione del QR Code generato da UC.UT.02; la prenotazione attiva è precondizione per l'avvio della corsa |

**Fonti:** Master_Spec §7 (Use Case Logic table, riga UC.UT.02: Estende=UC.UT.01, Esteso da=UC.UT.03) + documentazione.md §2.2.2 (UC.UT.02 tabella: Estende=UC.UT.01, Esteso da=UC.UT.03). **Consistent across all sources.**

---

## Method Traceability Matrix

### Controller: GestionePrenotazione

| Metodo | Firma Master_Spec | Firma UML | Chiamato in | Stato |
|:-------|:------------------|:----------|:------------|:------|
| `inviaRichiestaPrenotazione()` | `void` (no params) | `(idMezzo, idUtente)` | Main Flow S2 | ⚠️ Parametri divergono |
| `gestisciTimeout()` | `void` (no params) | Non presente in UML | Alt Flow A1 | ✓ (solo Master_Spec) |
| `notificaScadenzaTempo(idPrenotazione)` | `void` (idPrenotazione) | `(idPrenotazione)` | Alt Flow A4 | ⚠️ Direzione UML invertita |
| `richiediLista()` | `Prenotazione` | — | usato da UC.OP.03 | ✓ |
| `annullaPrenotazione()` | `bool` | — | usato da UC.OP.03 | ✓ |

### Model: Prenotazione

| Metodo | Firma | Chiamato in | Stato |
|:-------|:------|:------------|:------|
| `creaPrenotazione(idMezzo, idUtente, orarioInizio)` | `void` | Main Flow S3 | ✓ Match perfetto |
| `setStato(stato)` | `void` (StatoPrenotazione) | Alt Flow A3 (implicito) | ⚠️ Non mostrato in UML |
| `getStato()` | `StatoPrenotazione` | query interne | ✓ |
| `getPrenotazioneByStato(stato)` | `Prenotazione` | query interne | ✓ |

### Model: Mezzo

| Metodo | Firma | Chiamato in | Stato |
|:-------|:------|:------------|:------|
| `setStato(stato)` | `void` (StatoMezzo) | Main Flow S4, Alt Flow A2 | ✓ Match perfetto |
| `getStato()` | `StatoMezzo` | query interne | ✓ |

### View: AppUtente

| Metodo | Firma Master_Spec | Firma UML | Chiamato in | Stato |
|:-------|:------------------|:----------|:------------|:------|
| `selezionaMezzo(idMezzo)` | `void` | `(idMezzo)` | Main Flow S1 | ✓ |
| `mostraQRCode()` | `void` (no params) | `(QR_Code)` | Main Flow S6 | ✓ |
| `mostraSuccesso()` | `void` (no params) | `(messaggio)` | Alt Flow A5 | ⚠️ Parametro assente in Master_Spec |

---

## Enumerazioni Referenziate

### StatoPrenotazione

| Valore | Descrizione | Usato in |
|:-------|:------------|:---------|
| `attiva` | Prenotazione valida entro i 15 minuti | Stato iniziale dopo creaPrenotazione() |
| `scaduta` | Timeout superato, automaticamente annullata | Alt Flow A3 (implicito) |
| `annullata` | Cancellata da utente o operatore SC | UC.OP.03 |
| `completata` | Prenotazione onorata (corsa avviata) | UC.UT.03 (transizione) |

**Fonti:** Master_Spec §1 (StatoPrenotazione table: 4 valori), Master_Spec claim-8a5f9e007 (confermato dal team Cofee Coders). **4 valori. Consistent across all sources.**

### StatoMezzo (valori usati da questo UC)

| Valore | Stato | Usato in |
|:-------|:------|:---------|
| `prenotato` | Bloccato da prenotazione attiva | Main Flow S4 |
| `disponibile` | Libero e prenotabile | Alt Flow A2 |

**Fonte:** Master_Spec §1 (StatoMezzo table: 6 valori). **Consistent.**

---

## Verifica della Coerenza del Timeout (15 minuti)

| Fonte | Riferimento | Testo |
|:------|:------------|:------|
| documentazione.md §2.2.2 | Descrizione UC.UT.02 | "meccanismo automatico di annullamento se l'utente non raggiunge il mezzo entro **15 minuti**" |
| documentazione.md §2.2.2 | Flusso alternativo | "Trascorsi **15 minuti** dall'orario prenotato, il sistema rileva il superamento del timeout" |
| Master_Spec §1 | StatoPrenotazione.attiva | "Prenotazione valida entro i **15 minuti**" |
| Master_Spec §2 | Prenotazione header | "Blocco temporaneo di un mezzo (timeout: **15 min**)" |
| Master_Spec §8 | Vincoli #3 | "Timeout prenotazione **15 minuti**: GestionePrenotazione.gestisciTimeout()" |
| Master_Spec §12 | Glossary | "Blocco temporaneo di un mezzo (**15 minuti** massimo, timeout automatico)" |
| UC.UT.02-clean.uml | Opt fragment guard | "Dopo **15 minuti** dall'orario prenotato" |
| Master_Spec §9 | Anti-patterns | "Ignorare il timeout prenotazioni" → "Implementare gestisciTimeout()" |

**Verdetto:** Il valore 15 minuti è **perfettamente consistente** in tutte e 4 le fonti (8 riferimenti incrociati). [VERIFIED]

---

## Epistemic Audit

### Dati Confermati (Cross-Reference Completo)

| # | Dato | Fonti | Stato |
|:-:|:-----|:------|:------|
| E1 | Timeout = 15 minuti | 8 riferimenti in 4 fonti | CONFIRMED |
| E2 | StatoPrenotazione ha 4 valori (attiva, scaduta, annullata, completata) | Master_Spec §1 + claim-8a5f9e007 (team) | CONFIRMED |
| E3 | UC.UT.02 extends UC.UT.01, extended by UC.UT.03 | Master_Spec §7 + documentazione.md §2.2.2 | CONFIRMED |
| E4 | creaPrenotazione(idMezzo, idUtente, orarioInizio) | Master_Spec Prenotazione + UML | CONFIRMED |
| E5 | Mezzo.setStato(prenotato) e Mezzo.setStato(disponibile) | Master_Spec Mezzo + UML | CONFIRMED |
| E6 | mostraQRCode() presente in AppUtente | Master_Spec AppUtente | CONFIRMED |
| E7 | gestisciTimeout() presente in GestionePrenotazione | Master_Spec GestionePrenotazione | CONFIRMED |
| E8 | notificaScadenzaTempo(idPrenotazione) presente in GestionePrenotazione | Master_Spec GestionePrenotazione | CONFIRMED |
| E9 | Precondizioni: utente autenticato, mappa visibile | documentazione.md §2.2.2 | CONFIRMED |
| E10 | Postcondizioni: mezzo prenotato, prenotazione registrata | documentazione.md §2.2.2 | CONFIRMED |
| E11 | QR Code generato al termine del flusso principale | documentazione.md + UML | CONFIRMED |
| E12 | Dominio: città generica con WiFi full-range | Master_Spec header + claim-2e5c7a017 | CONFIRMED |

### Dati Inferiti / Assunti (Richiedono HITL)

| # | Dato | Assunzione | HITL Claim |
|:-:|:-----|:-----------|:------------|
| I1 | `inviaRichiestaPrenotazione()` accetta parametri (idMezzo, idUtente) | UML mostra 2 params; Master_Spec mostra no-args. Assumo UML sia più dettagliato. | claim-uc02-001 |
| I2 | `notificaScadenzaTempo` direzione corretta è GestionePrenotazione → AppUtente | documentazione.md dice "Il sistema invia una notifica". UML ha direzione opposta (errore XMI). | claim-uc02-002 |
| I3 | `mostraSuccesso()` accetta parametro messaggio | UML mostra `mostraSuccesso(messaggio)`. AppOperatoreSC e AppOperatoreTecnico hanno firme analoghe con param. Assumo consistenza tra View. | claim-uc02-003 |
| I4 | `Prenotazione.setStato(scaduta)` è chiamata durante il timeout | Né documentazione.md né UML lo mostrano esplicitamente, ma è logicamente necessario dato che StatoPrenotazione.scaduta esiste. | claim-uc02-004 |

---

## Diagramma di Sequenza — Riepilogo Lifeline e Messaggi

**Lifeline UML:** Utente, AppUtente, GestionePrenotazione, Prenotazione, Mezzo

**Messaggi Main Flow (ordine UML):**
```
Utente → AppUtente:                  selezionaMezzo(idMezzo)
AppUtente → GestionePrenotazione:    inviaRichiestaPrenotazione(idMezzo, idUtente)
GestionePrenotazione → Prenotazione:  creaPrenotazione(idMezzo, idUtente, orarioInizio)  [create]
GestionePrenotazione → Mezzo:        setStato(prenotato)
Mezzo → GestionePrenotazione:        void [return]
GestionePrenotazione → AppUtente:    QR_Code [return]
AppUtente → AppUtente:               mostraQRCode(QR_Code) [self]
AppUtente → Utente:                  Notifica prenotazione avvenuta e mostra QR_Code
```

**Messaggi Alternative Flow — opt [Dopo 15 minuti dall'orario prenotato]:**
```
GestionePrenotazione → Mezzo:        setStato(disponibile)
Mezzo → GestionePrenotazione:        void [return]
AppUtente → GestionePrenotazione:    notificaScadenzaTempo(idPrenotazione)   ← direzione UML
GestionePrenotazione → AppUtente:    notificaAnnullamentoPrenotazione() [return]
AppUtente → AppUtente:               mostraSuccesso(messaggio) [self]
AppUtente → Utente:                  Notifica prenotazione annullata
```

> **Nota XMI:** `notificaScadenzaTempo(idPrenotazione)` è modellato come AppUtente → GestionePrenotazione nel diagramma UML. Il testo documentazione.md afferma invece "Il sistema invia una notifica di annullamento", suggerendo GestionePrenotazione → AppUtente. Vedi HITL claim-uc02-002.

---

## Architectural Constraints & Invariants

| Vincolo | Riferimento | Applicabilità |
|:--------|:------------|:--------------|
| Autenticazione obbligatoria (sessione attiva) | Master_Spec §8.1 | Precondizione S1 |
| Timeout prenotazione 15 minuti | Master_Spec §8.3 | Flusso alternativo |
| Disaccoppiamento View-Controller-Model | Master_Spec §8.10 | AppUtente → GestionePrenotazione → Model |
| Prenotazione blocca Mezzo (stato prenotato) | Master_Spec §6 | Mezzo.setStato(prenotato) |
| RBAC: solo Utente può prenotare | Master_Spec §8.5 | Attore = Utente |
| Simulazione sistemi esterni | Master_Spec §8.12 + chiarimenti-vari.md §16 | QR code generato internamente |

---

## Glossario Locale

| Termine | Definizione |
|:--------|:------------|
| Timeout | 15 minuti dall'orario di inizio prenotazione — se l'utente non avvia la corsa entro questo intervallo, la prenotazione viene automaticamente annullata |
| QR Code | Codice generato dal sistema al termine della prenotazione, utilizzato in UC.UT.03 per avviare la corsa |
| Prenotazione attiva | Prenotazione in stato `attiva`, entro i 15 minuti di validità |
| Raggio base | Parametro di ricerca ereditato da UC.UT.01 (2 km default) |

---

## §1. Test Case Specifications

| ID | Component | Scenario | Preconditions | Input | Expected Result | Postconditions | Edge Cases |
|----|-----------|----------|--------------|-------|----------------|----------------|------------|
| TC-UT02-01 | View (AppUtente) | Selezione mezzo e avvio prenotazione — happy path | Utente autenticato, mappa visibile (P1-P2), mezzo disponibile | `idMezzo` valido | `selezionaMezzo(idMezzo)` → `inviaRichiestaPrenotazione(idMezzo, idUtente)` | Richiesta prenotazione inviata al Controller | Utente seleziona mezzo già cliccato (doppio click) |
| TC-UT02-02 | Controller (GestionePrenotazione) | Registrazione prenotazione con stato mezzo aggiornato | Richiesta ricevuta, idMezzo/idUtente validi | `idMezzo`, `idUtente`, `orarioInizio` = now | `creaPrenotazione(idMezzo, idUtente, orarioInizio)` → `Mezzo.setStato(prenotato)` | Prenotazione creata, mezzo bloccato (Q1-Q2) | Concorrenza: due utenti prenotano stesso mezzo |
| TC-UT02-03 | Model (Prenotazione) | Creazione prenotazione con salvataggio DB | Controller ha invocato creaPrenotazione | `idMezzo`, `idUtente`, `orarioInizio` | `creaPrenotazione()` inserisce record `StatoPrenotazione.attiva` | Record prenotazione persistente | orarioInizio nel passato, idMezzo inesistente |
| TC-UT02-04 | Model (Mezzo) | Aggiornamento stato mezzo a `prenotato` | Prenotazione creata con successo | `stato = StatoMezzo.prenotato` | `Mezzo.setStato(prenotato)` → DB aggiorna `mezzo.stato` | Mezzo non più disponibile per altre ricerche | Mezzo già in stato `prenotato` o `in_uso` |
| TC-UT02-05 | View (AppUtente) | Visualizzazione QR Code al completamento prenotazione | Prenotazione registrata, QR_Code generato | `QR_Code` ricevuto da GestionePrenotazione | `mostraQRCode(QR_Code)` renderizza codice | Utente visualizza QR valido per sblocco in UC.UT.03 | QR_Code nullo/danneggiato, schermo troppo piccolo |
| TC-UT02-IT01 | Integration View→Controller | Contratto `selezionaMezzo` → `inviaRichiestaPrenotazione` | Precondizioni P1-P2 verificate | `idMezzo` dalla selezione utente | `selezionaMezzo(idMezzo)` invoca `inviaRichiestaPrenotazione(idMezzo, idUtente)` | Parametri idMezzo e idUtente passati correttamente | idMezzo non presente nella lista visualizzata |
| TC-UT02-IT02 | Integration Controller→Model | Contratto `inviaRichiestaPrenotazione` → `creaPrenotazione` + `setStato` | Richiesta valida ricevuta | `idMezzo`, `idUtente`, `orarioInizio` | `creaPrenotazione()` eseguita prima di `Mezzo.setStato(prenotato)` | Transazione atomica: creazione + aggiornamento entrambi completati | Fallimento `setStato` dopo `creaPrenotazione` riuscita |
| TC-UT02-IT03 | Integration Timeout | Contratto `gestisciTimeout` → `setStato(disponibile)` + `setStato(scaduta)` + `notificaScadenzaTempo` | 15 minuti trascorsi da orario prenotato | `idPrenotazione` della prenotazione scaduta | `gestisciTimeout()` → `Mezzo.setStato(disponibile)` → `Prenotazione.setStato(scaduta)` → `notificaScadenzaTempo(idPrenotazione)` | Mezzo torna disponibile, prenotazione scaduta, utente notificato | Timeout a 14:59 vs 15:01, prenotazione già annullata manualmente |

---

## §2. Error Handling Matrix

| ID | Error Type | Component | Detection Point | System Response | Fallback | Logging |
|----|-----------|-----------|----------------|----------------|----------|---------|
| ERR-UT02-01 | Business logic | GestionePrenotazione | `inviaRichiestaPrenotazione()` — `Mezzo.getStato() != disponibile` (già prenotato/in uso) | `mostraErrore("mezzo non disponibile")` | L'utente seleziona un altro mezzo dalla mappa | WARN |
| ERR-UT02-02 | Business logic | GestionePrenotazione | `gestisciTimeout()` — 15 minuti trascorsi, timeout scattato | `notificaScadenzaTempo(idPrenotazione)` → `mostraSuccesso("Prenotazione annullata")` (Alt Flow) | Mezzo rilasciato (disponibile), utente può ri-prenotare | INFO |
| ERR-UT02-03 | External | Prenotazione (DBMS) | `creaPrenotazione()` — errore DB (connessione, constraint violation, deadlock) | Rollback transazione, `mostraErrore("Errore prenotazione")` | L'utente riprova la prenotazione | ERROR |
| ERR-UT02-04 | Input validation | GestionePrenotazione | `inviaRichiestaPrenotazione(idMezzo, idUtente)` — idMezzo o idUtente nulli/invalidi | `mostraErrore("Dati prenotazione non validi")` | L'utente verifica i dati e riprova | WARN |
| ERR-UT02-05 | Security | GestionePrenotazione | `inviaRichiestaPrenotazione()` — sessione utente scaduta | Redirect a `UC.ATT.01` (Login) | L'utente si ri-autentica e ripete la prenotazione | WARN |
| ERR-UT02-06 | Business logic | GestionePrenotazione | Tentativo prenotazione senza preventiva ricerca (UC.UT.01 non eseguito) | `mostraErrore("Selezionare un mezzo dalla mappa")` | Reindirizzamento a UC.UT.01 per la ricerca | ERROR |
| ERR-UT02-07 | External | GestionePrenotazione | Generazione QR_Code fallisce dopo creazione prenotazione | `mostraErrore("Errore generazione QR Code")` | Prenotazione mantenuta, QR rigenerabile su richiesta | ERROR |

---

## HITL Verification Record

### Round A: Derived Data Confirmation

| # | Claim ID | Claim | Stato |
|:-:|:---------|:------|:------|
| 1 | claim-uc02-001 | Firma di `inviaRichiestaPrenotazione()` — no-args vs (idMezzo, idUtente) | REVIEWED |
| 2 | claim-uc02-002 | Direzione `notificaScadenzaTempo` — invertita nell'UML? | REVIEWED |
| 3 | claim-uc02-003 | Firma di `mostraSuccesso()` — no-args vs (messaggio) | REVIEWED |
| 4 | claim-uc02-004 | `Prenotazione.setStato(scaduta)` implicito nel timeout | REVIEWED |

### Round B: True HITL Verification
*Nessun claim richiede Round B — tutti i claim sono verificabili in Round A dal team Cofee Coders.*

---

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | REVIEWED

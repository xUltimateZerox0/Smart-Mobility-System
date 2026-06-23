---
clarity-gate-version: 2.1
document-type: Implementation
processed-date: 2026-06-23
processed-by: Claude (AI) + Cross-Reference Engine — documentazione.md v3.0, Master_Spec.cgd.md v4.0, UC.UT.05-clean.uml sequence diagram, chiarimenti-vari.md, response2.md
clarity-status: CLEAR
hitl-status: REVIEWED
hitl-pending-count: 0
points-passed: 1-9
document-sha256: 986aca79148c9cf2504f80af4aebc2e3fce0c0222a099b63c52e1437e8699d6b
hitl-claims:
  - id: claim-49739723
    text: "idMetodoPagamento is the surrogate PK of MetodoPagamento — not numCarta"
    value: "PK surrogata confermata. PCI-DSS best practice."
    source: "Master_Spec.cgd.md §2 MetodoPagamento + HITL claim-e7f6a003 (Team Cofee Coders)"
    location: "MetodoPagamento/idMetodoPagamento"
    round: A
    confirmed-by: Team Cofee Coders (via user)
    confirmed-date: 2026-06-22
  - id: claim-0d0495dd
    text: "Gateway Pagamento.effettuaPagamento() uses lowercase 'e' — corrected from XMI typo 'EffettuaPagamento'"
    value: "Correzione camelCase applicata — effettuaPagamento(idMetodoPagamento, idCorsa) → bool"
    source: "Master_Spec.cgd.md §5 Gateway Pagamento + HITL claim-5d8e2f020 (chiarimenti-vari.md punto 14)"
    location: "GatewayPagamento/effettuaPagamento"
    round: A
    confirmed-by: Team Cofee Coders (via user)
    confirmed-date: 2026-06-22
  - id: claim-59bdea86
    text: "GestorePagamento.elaboraDatiCarta signature is (idUtente, numCarta, dsCarta, cvv, intestatarioCarta) → bool"
    value: "5 parametri ordinati. Confermato da Master_Spec e sequence diagram UC.UT.05."
    source: "Master_Spec.cgd.md §3 GestorePagamento + UC.UT.05-clean.uml chiamata elaboraDatiCarta"
    location: "GestorePagamento/elaboraDatiCarta"
    round: A
    confirmed-by: Team Cofee Coders (via user)
    confirmed-date: 2026-06-22
  - id: claim-bb8dfb59
    text: "MetodoPagamento stores only numCarta (cifrato) and intestatarioCarta; dsCarta and cvv are NOT persisted"
    value: "dsCarta e cvv transitano solo verso Gateway Pagamento per convalida, mai salvati a DB"
    source: "Master_Spec.cgd.md §2 MetodoPagamento attributes: solo numCarta e intestatarioCarta"
    location: "MetodoPagamento/attributes"
    round: A
    confirmed-by: Team Cofee Coders (via user)
    confirmed-date: 2026-06-22
  - id: claim-37f40897
    text: "Gateway Pagamento is a simulated external system (accademic project — no real payment processing)"
    value: "Simulato. Interfaccia definita, implementazione fittizia."
    source: "chiarimenti-vari.md punto 16 + Master_Spec.cgd.md §5 note"
    location: "GatewayPagamento/simulated"
    round: A
    confirmed-by: Team Cofee Coders (via user)
    confirmed-date: 2026-06-22
  - id: claim-1751885c
    text: "controllaMetodoEsistente(numCarta) operates on encrypted numCarta — requires deterministic encryption or indexed lookup"
    value: "Design decision: deterministic cifratura per consentire lookup by encrypted numCarta"
    source: "Master_Spec.cgd.md §2 MetodoPagamento + vincolo architetturale 14 (cifratura dati pagamento)"
    location: "MetodoPagamento/controllaMetodoEsistente"
    round: A
    confirmed-by: Team Cofee Coders (via user)
    confirmed-date: 2026-06-22
  - id: claim-3afbaaa9
    text: "UC.UT.05 is included by UC.UT.03 (Gestione Corsa) per documentazione.md §2.2.2"
    value: "Included — UC.UT.03 attiva UC.UT.05 durante flusso avvio corsa"
    source: "documentazione.md §2.2.2: UC.UT.03 Include: UC.UT.05"
    location: "UC.UT.05/dependency"
    round: A
    confirmed-by: Team Cofee Coders (via user)
    confirmed-date: 2026-06-22
  - id: claim-d74a1482
    text: "AppUtente.selezionaMetodo(numCarta) triggers resolution to idMetodoPagamento then GestoreCorsa.acquisisciSceltaMetodo(idMetodoPagamento)"
    value: "Sequence diagram confirms: selezionaMetodo → fornisciMetodo → acquisisciSceltaMetodo(idMetodoPagamento)"
    source: "UC.UT.05-clean.uml + Master_Spec.cgd.md §3 GestioneCorsa & §4 AppUtente"
    location: "AppUtente/selezionaMetodo"
    round: A
    confirmed-by: Team Cofee Coders (via user)
    confirmed-date: 2026-06-22
---

# UC.UT.05 — Metodo Pagamento

**Clarity-Gated Use Case Specification**

---

## 1. Use Case Summary

| Field | Value |
|-------|-------|
| **ID** | UC.UT.05 |
| **Name** | Metodo Pagamento |
| **Actor** | Utente |
| **User Story** | UT.08 — *"Come utente, voglio poter inserire un metodo di pagamento nel mio account, così da permettere l'addebito al termine di ogni utilizzo."* |
| **Brief Description** | L'utente seleziona il metodo di pagamento che desidera utilizzare. Il sistema verifica se ne ha già salvato almeno uno e gli fa scegliere fra quelli, altrimenti salva un nuovo metodo di pagamento facendogli inserire e convalidando i dati del metodo nel sistema. |
| **Priority** | 35 |
| **Sprint** | 1 |
| **Preconditions** | L'utente ha effettuato l'accesso ed ha una sessione attiva. |
| **Include** | — |
| **Extends** | — |
| **Extended by** | — |
| **Included by** | UC.UT.03 (Gestione Corsa) |
| **Postconditions** | Un metodo di pagamento è stato associato alla sessione corrente dell'utente. |
| **Requirements** | Integrazione con un'interfaccia di pagamento per validare in tempo reale i dati della carta. *(Nota: interfaccia simulata — chiarimenti-vari.md punto 16)* |
| **Trigger** | Attivato da UC.UT.03 quando l'utente deve selezionare un metodo di pagamento per avviare la corsa. |
| **Source** | documentazione.md v3.0 §2.2.2 |
| **Source Comments** | UC.UT.05-clean.uml comment: *"attivato dal caso d'uso UC.UT.03"* |

---

## 2. Main Flow — Seleziona Metodo Esistente

*Tutti i riferimenti di metodo sono cross-verificati contro Master_Spec.cgd.md v4.0 e UC.UT.05-clean.uml.*

| Step | Actor | System Action | Method Call | Component |
|------|-------|---------------|-------------|-----------|
| 1 | — | *(UC.UT.05 attivato da UC.UT.03)* | — | — |
| 2 | — | Il sistema richiede all'utente se inserire un nuovo metodo o selezionarne uno esistente. | `mostraSceltaMetodi()` *→* reply: `richiedi scelta` | `AppUtente` |
| 3 | Utente | Richiede di visualizzare i metodi salvati. | `ottieniMetodiSalvati()` | `AppUtente` |
| 4 | — | Il sistema recupera i metodi associati. | `recuperaMetodiSalvati()` | `GestorePagamento` |
| 5 | — | Query DB per metodi dell'utente. | `getMetodoByUtente(idUtente)` | `MetodoPagamento` |
| 6 | — | Risultato propagato alla View. | reply: `lista<MetodoPagamento>` → `mostraMetodi(metodi)` | `GestorePagamento` → `AppUtente` |
| 7 | Utente | Seleziona un metodo dalla lista. | `selezionaMetodo(numCarta)` | `AppUtente` |
| 8 | — | Il sistema risolve `numCarta` → `idMetodoPagamento` e associa alla sessione. | *fornisciMetodo(numCarta)* → `acquisisciSceltaMetodo(idMetodoPagamento)` | `GestorePagamento` → `GestoreCorsa` |
| 9 | — | Il sistema notifica il successo. | reply: *notifica "metodo selezionato"* | `GestoreCorsa` → `AppUtente` → Utente |

> **Nota (step 8):** Il nome esatto del metodo di risoluzione `numCarta → idMetodoPagamento` non è formalmente definito in Master_Spec. Lo XMI lo etichetta come `fornisciMetodo(NumCarta)`. Il metodo finale `acquisisciSceltaMetodo(idMetodoPagamento)` su `GestoreCorsa` è formalmente definito in Master_Spec.cgd.md line 581.

---

## 3. Alternative Flow A — Registrazione Nuovo Metodo

*Attivato quando l'utente sceglie di inserire un nuovo metodo di pagamento al passo 2 del flusso principale.*

| Step | Actor | System Action | Method Call | Component |
|------|-------|---------------|-------------|-----------|
| A1 | Utente | Richiede l'inserimento di un nuovo metodo. | `apriInserimentoMetodoPagamento(idUtente)` | `AppUtente` |
| A2 | — | Il sistema mostra il form di inserimento. | reply: *visualizza Inserimento MetodoPagamento* | `AppUtente` |
| A3 | Utente | Inserisce i dati carta *(numero, scadenza, CVV, intestatario)* e invia. | `inserisciDatiCarta(idUtente, numCarta, dsCarta, cvv, intestatarioCarta)` | `AppUtente` |
| A4 | — | Il sistema inoltra i dati al Controller Pagamento. | `elaboraDatiCarta(idUtente, numCarta, dsCarta, cvv, intestatarioCarta)` | `GestorePagamento` |
| A5 | — | Il Controller delega la validazione al Gateway esterno. | `convalidaCarta(numCarta, dsCarta, cvv, intestatarioCarta)` | `Gateway Pagamento` |
| A6 | — | Gateway risponde con esito validazione. | reply: `true` (convalidato) | `Gateway Pagamento` |
| A7 | — | Sistema verifica se la carta esiste già nel DB. | `controllaMetodoEsistente(numCarta)` | `MetodoPagamento` |
| A8 | — | *(ramo: metodo NON esistente)* Il sistema crea e salva il nuovo metodo. | `creaMetodoPagamento(numCarta, intestatarioCarta)` | `MetodoPagamento` |
| A9 | — | Il sistema mostra conferma all'utente. | `mostraMetodoConvalidato()` | `AppUtente` |
| A10 | — | Il flusso riprende dal passo 4 del flusso principale *(recupera metodi salvati, incluso il nuovo)*. | → `ottieniMetodiSalvati()` | `AppUtente` |

### Alternative Flow A — Sub-branches

#### A5-A6: Non Convalidato

| Step | System Action | Method Call | Component |
|------|---------------|-------------|-----------|
| A5b | Gateway Pagamento rifiuta la carta. | reply: `false` | `Gateway Pagamento` |
| A6b | Il sistema mostra errore all'utente. | `mostraErrore("Metodo non convalidato")` | `AppUtente` |
| A6c | Flusso termina con errore — l'utente rimane nella scelta metodo. | — | — |

#### A7: Metodo Già Esistente

| Step | System Action | Method Call | Component |
|------|---------------|-------------|-----------|
| A7b | `controllaMetodoEsistente` restituisce `true`. | reply: `true` | `MetodoPagamento` |
| A7c | Il sistema salta il `creaMetodoPagamento` e restituisce esito positivo. | — | — |
| A7d | Il flusso riprende direttamente dalla lista metodi *(includendo il metodo già esistente)*. | → `ottieniMetodiSalvati()` | `AppUtente` |

> **Nota PCI-DSS:** `dsCarta` e `cvv` transitano esclusivamente verso `Gateway Pagamento.convalidaCarta()` e **non vengono mai persistiti** in `MetodoPagamento`. Solo `numCarta` *(cifrato)* e `intestatarioCarta` sono salvati a DB. *(Master_Spec.cgd.md §2 MetodoPagamento attributes)*

---

## 4. Full Flow Summary (Sequence Diagram Trace)

*Tracciato da UC.UT.05-clean.uml (XMI 2.1) e cross-referenziato con Master_Spec.cgd.md v4.0.*

```
UC.UT.03 attiva UC.UT.05
│
├─ [OPT: registra nuovo metodo]
│   ├─ Utente → AppUtente: apriInserimentoMetodoPagamento(idUtente)
│   ├─ Utente → AppUtente: inserisciDatiCarta(idUtente, numCarta, dsCarta, cvv, intestatarioCarta)
│   ├─ AppUtente → GestorePagamento: elaboraDatiCarta(idUtente, numCarta, dsCarta, cvv, intestatarioCarta)
│   ├─ GestorePagamento → Gateway Pagamento: convalidaCarta(numCarta, dsCarta, cvv, intestatarioCarta)
│   │
│   ├─ [alt: Convalidato]
│   │   ├─ GestorePagamento → MetodoPagamento: controllaMetodoEsistente(numCarta)
│   │   │
│   │   ├─ [alt: Non Esiste]
│   │   │   └─ GestorePagamento → MetodoPagamento: creaMetodoPagamento(numCarta, intestatarioCarta)  [CREATE]
│   │   └─ [alt: Esiste]
│   │       └─ (skip save)
│   │
│   │   └─ AppUtente → Utente: mostraMetodoConvalidato()
│   │
│   └─ [alt: Non Convalidato]
│       └─ AppUtente → Utente: mostraErrore("Metodo non convalidato")
│
├─ [FLUSSO PRINCIPALE — seleziona da lista]
│   ├─ AppUtente → GestorePagamento: recuperaMetodiSalvati()
│   ├─ GestorePagamento → MetodoPagamento: getMetodoByUtente(idUtente)
│   ├─ MetodoPagamento → GestorePagamento: reply lista<MetodoPagamento>
│   ├─ GestorePagamento → AppUtente: reply lista<MetodoPagamento>
│   ├─ AppUtente → Utente: mostraMetodi(lista<MetodoPagamento>)
│   ├─ Utente → AppUtente: selezionaMetodo(numCarta)
│   ├─ AppUtente → GestorePagamento: fornisciMetodo(numCarta)
│   ├─ AppUtente → GestoreCorsa: acquisisciSceltaMetodo(idMetodoPagamento)
│   └─ GestoreCorsa → AppUtente → Utente: notifica "metodo selezionato"
```

---

## 5. Method Traceability Matrix

*Ogni metodo è verificato contro Master_Spec.cgd.md v4.0 (source primaria per le firme). I typo XMI (maiuscole/minuscole, spazi) sono corretti per chiarimenti-vari.md punto 14. Metodi con `[XMI]` appaiono solo nel sequence diagram e potrebbero essere interni/non formalizzati.*

| Method | Returns | Parameters | Component | Source | Status |
|--------|---------|-----------|-----------|--------|--------|
| `mostraSceltaMetodi()` | void | — | `AppUtente` | Master_Spec line 699 | ✓ Verified |
| `apriInserimentoMetodoPagamento(idUtente)` | void | idUtente | `AppUtente` | Master_Spec line 708 | ✓ Verified |
| `inserisciDatiCarta(idUtente, numCarta, dsCarta, cvv, intestatarioCarta)` | void | 5 params | `AppUtente` | Master_Spec line 704 | ✓ Verified |
| `elaboraDatiCarta(idUtente, numCarta, dsCarta, cvv, intestatarioCarta)` | bool | 5 params | `GestorePagamento` | Master_Spec line 601 | ✓ Verified |
| `convalidaCarta(numCarta, dsCarta, cvv, intestatarioCarta)` | bool | 4 params | `Gateway Pagamento` | Master_Spec line 852 | ✓ Verified |
| `controllaMetodoEsistente(numCarta)` | bool | numCarta: String | `MetodoPagamento` | Master_Spec line 399 | ✓ Verified |
| `creaMetodoPagamento(numCarta, intestatarioCarta)` | void | 2 params | `MetodoPagamento` | Master_Spec line 398 | ✓ Verified |
| `mostraMetodoConvalidato()` | void | — | `AppUtente` | Master_Spec line 701 | ✓ Verified |
| `mostraErrore(msg)` | void | msg: String | `AppUtente` | Master_Spec line 692 | ✓ Verified |
| `ottieniMetodiSalvati()` | void | — | `AppUtente` | Master_Spec line 715 | ✓ Verified |
| `recuperaMetodiSalvati()` | MetodoPagamento | — | `GestorePagamento` | Master_Spec line 602 | ✓ Verified |
| `getMetodoByUtente(idUtente)` | MetodoPagamento | idUtente | `MetodoPagamento` | Master_Spec line 400 | ✓ Verified |
| `mostraMetodi(metodi)` | void | metodi: MetodoPagamento | `AppUtente` | Master_Spec line 698 | ✓ Verified |
| `selezionaMetodo(numCarta)` | void | numCarta: String | `AppUtente` | Master_Spec line 716 | ✓ Verified |
| `acquisisciSceltaMetodo(idMetodoPagamento)` | void | idMetodoPagamento | `GestioneCorsa` | Master_Spec line 581 | ✓ Verified |
| `fornisciMetodo(numCarta)` | *(N/D)* | numCarta: String | `GestorePagamento` | XMI sequence diagram only | ⚠ [XMI] non formalizzato |
| `getMetodoByUtente(idUtente)` → `GetMetodoByUtente(idUtente)` | MetodoPagamento | idUtente | `MetodoPagamento` | XMI typo case — corretto | ✓ Verified |

> **Nota su `fornisciMetodo(NumCarta)`:** Questo metodo appare solo nel sequence diagram XMI e non in Master_Spec. Rappresenta la risoluzione `numCarta → idMetodoPagamento` necessaria per chiamare `acquisisciSceltaMetodo(idMetodoPagamento)`. Non ha firma formale. *(chiarimenti-vari.md punto 14: possibile artefatto XMI)*

---

## 6. Architectural Constraints & Invariants

| # | Constraint | Source | UC.UT.05 Relevance |
|---|-----------|--------|--------------------|
| C1 | Autenticazione obbligatoria — servono credenziali valide | Master_Spec §8.1 | Precondizione UC.UT.05 |
| C7 | Pagamento obbligatorio — nessuna corsa termina senza transazione | Master_Spec §8.7 | UC.UT.05 è evenienza di UC.UT.03 che include UC.UT.07 |
| C8 | Metodo pagamento pre-esistente — necessario metodo valido per avviare corsa | Master_Spec §8.8 | UC.UT.05 garantisce la selezione |
| C10 | Disaccoppiamento View-Controller-Model | Master_Spec §8.10 | AppUtente → GestorePagamento → MetodoPagamento |
| C12 | Simulazione sistemi esterni | Master_Spec §8.12 | Gateway Pagamento è simulato |
| C14 | Cifratura dati pagamento — dati carta memorizzati cifrati | Master_Spec §8.14 | `numCarta` cifrato, `cvv`/`dsCarta` non persistiti |

---

## 7. Model Cross-Reference — MetodoPagamento

*Da Master_Spec.cgd.md §2 Model Layer:*

| Attribute | Type | Visibility | PCI-DSS Note |
|-----------|------|------------|--------------|
| `idMetodoPagamento` | — `(PK, surrogata)` | private | Surrogate key for security — never expose real card as PK |
| `numCarta` | `String (cifrato)` | private | Encrypted at rest |
| `intestatarioCarta` | `String` | private | Cardholder name *(not PCI-DSS sensitive by itself, but stored with encrypted context)* |

| Method | Returns | Params |
|--------|---------|--------|
| `getIdMetodoPagamento()` | — | — |
| `setIdMetodoPagamento(idMetodoPagamento)` | void | idMetodoPagamento |
| `getNumCarta()` | String | — |
| `setNumCarta(numCarta)` | void | numCarta: String |
| `getIntestatarioCarta()` | String | — |
| `setIntestatarioCarta(intestatarioCarta)` | void | intestatarioCarta: String |
| `creaMetodoPagamento(numCarta, intestatarioCarta)` | void | numCarta, intestatarioCarta |
| `controllaMetodoEsistente(numCarta)` | bool | numCarta: String |
| `getMetodoByUtente(idUtente)` | MetodoPagamento | idUtente |

> **PCI-DSS Design Note:** `idMetodoPagamento` è chiave surrogata per evitare di usare `numCarta` come identificatore primario. `numCarta` è cifrato a riposo. `dsCarta` e `cvv` non sono mai persistiti — transitano solo nella chiamata `convalidaCarta()` verso Gateway Pagamento. *(Fonte: Master_Spec.cgd.md line 402)*

---

## 8. Gateway Pagamento Cross-Reference

*Da Master_Spec.cgd.md §5 External Systems:*

| Method | Returns | Params |
|--------|---------|--------|
| `effettuaPagamento(idMetodoPagamento, idCorsa)` | bool | idMetodoPagamento, idCorsa |
| `convalidaCarta(numCarta, dsCarta, cvv, intestatarioCarta)` | bool | numCarta, dsCarta, cvv, intestatarioCarta |

| Attribute | Visibility |
|-----------|------------|
| `idGatewayPagamento` | private |

> **Simulazione:** `Gateway Pagamento` è un sistema esterno simulato per scopi accademici *(chiarimenti-vari.md punto 16)*. Nessuna transazione finanziaria reale viene elaborata. L'interfaccia `convalidaCarta()` restituisce `true`/`false` senza contattare circuiti bancari.

---

## 9. Epistemic Annotations

| # | Claim | Certainty | Marker |
|---|-------|-----------|--------|
| E1 | `numCarta` is encrypted at rest via deterministic cipher | *(assumption — algorithm unspecified)* | `*(deterministic encryption TBD)*` |
| E2 | `controllaMetodoEsistente()` matches encrypted data | *(dependent on E1 — requires deterministic encryption or indexed hash)* | `*(lookup mechanism unspecced)*` |
| E3 | `Gateway Pagamento` simulation returns `true` for well-formed cards | *(academic simplification — real Gateway would validate against issuer)* | `*(simulato)*` |
| E4 | `effettuaPagamento()` is deferred to UC.UT.07 — not called in UC.UT.05 | *(confirmed by Master_Spec UC dependency table: UC.UT.03 includes UC.UT.05 and UC.UT.07)* | ✓ Confirmed |
| E5 | `fornisciMetodo(NumCarta)` is XMI-only — not a formal method in Master_Spec | *(chiarimenti-vari.md punto 14: possible XMI artifact or internal helper)* | `*(XMI artifact — verify with team)*` |

---

## 10. Anti-Patterns (DO NOT for UC.UT.05)

| Don't | Do Instead | Why |
|-------|------------|-----|
| Persistere `cvv` o `dsCarta` nel DB | Trasmettere solo a Gateway per convalida, mai salvare | PCI-DSS v4.0 Requirement 3.2 |
| Usare `numCarta` come chiave primaria | Usare `idMetodoPagamento` come PK surrogata | Previene esposizione del numero carta in log, FK, join |
| Saltare `controllaMetodoEsistente()` prima di `creaMetodoPagamento()` | Sempre verificare duplicati prima dell'insert | Evita metodi duplicati per lo stesso utente *(flusso documentazione.md: controlla → se non esiste → crea)* |
| Permettere avvio corsa senza metodo pagamento | Richiedere selezione metodo valida prima di `avviaCorsa()` | Vincolo architetturale C8 |
| Gestire pagamento senza convalida Gateway | Delegare sempre a `convalidaCarta()` | Vincolo architetturale §9 anti-pattern #1 |

---

## HITL Verification Record

### Round A: Derived Data Confirmation

Tutti gli 8 claim sono stati confermati dal team Cofee Coders durante la sessione di revisione del Master_Spec.cgd.md e sono cross-referenziati con le fonti indicate.

| # | Claim ID | Claim | Fonte | Stato |
|---|----------|-------|-------|-------|
| 1 | claim-49739723 | idMetodoPagamento è PK surrogata di MetodoPagamento | Master_Spec HITL claim-e7f6a003 | ✓ |
| 2 | claim-0d0495dd | effettuaPagamento() con 'e' minuscola — corretto da XMI typo | Master_Spec HITL claim-5d8e2f020 | ✓ |
| 3 | claim-59bdea86 | elaboraDatiCarta(idUtente, numCarta, dsCarta, cvv, intestatarioCarta) signature | Master_Spec §3 + XMI UC.UT.05 | ✓ |
| 4 | claim-bb8dfb59 | dsCarta e cvv NON persistiti — solo numCarta(cifrato) e intestatarioCarta | Master_Spec §2 MetodoPagamento attributes | ✓ |
| 5 | claim-37f40897 | Gateway Pagamento è simulato (progetto accademico) | chiarimenti-vari.md punto 16 | ✓ |
| 6 | claim-1751885c | controllaMetodoEsistente opera su numCarta cifrato | Master_Spec §2 + vincolo C14 | ✓ |
| 7 | claim-3afbaaa9 | UC.UT.05 è incluso da UC.UT.03 | documentazione.md §2.2.2 UC table | ✓ |
| 8 | claim-d74a1482 | selezionaMetodo(numCarta) → acquisisciSceltaMetodo(idMetodoPagamento) | XMI UC.UT.05 + Master_Spec §3 GestioneCorsa | ✓ |

### Round B: True HITL Verification

*Nessun claim richiede Round B — tutti i claim derivano da fonti già confermate dal team nella sessione di revisione Master_Spec.cgd.md.*

---

## 11. References

| Document | Path | Role |
|----------|------|-------|
| documentazione.md v3.0 | `docs/specs/documentazione.md` | Source primaria — specifica UC |
| Master_Spec.cgd.md v4.0 | `docs/specs/Master_Spec.cgd.md` | Source primaria — firme metodi, attributi, vincoli |
| UC.UT.05-clean.uml | `docs/diagrams/sequence-diagrams/UC.UT.05/UC.UT.05-clean.uml` | Sequence diagram — tracciamento messaggi |
| chiarimenti-vari.md | `docs/specs/chiarimenti-vari.md` | Interpretazioni e vincoli (simulazione, typo XMI, correzioni) |
| Class Diagram | `docs/diagrams/class-diagram/classDiagram-v1.8-clean.uml` | XMI 2.1 — struttura classi |

---

**Cross-Reference Statistics:**

| Metrica | Valore |
|---------|--------|
| Metodi totali tracciati | 17 |
| Metodi verificati in Master_Spec | 16 |
| Metodi XMI-only (non formalizzati) | 1 (`fornisciMetodo`) |
| Use Case steps mappati | 18 (main + alternative flows) |
| Componenti coinvolte | 5 (AppUtente, GestorePagamento, MetodoPagamento, Gateway Pagamento, GestoreCorsa) |
| HITL claims | 8 |
| Epistemic annotations | 5 |

---

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | REVIEWED

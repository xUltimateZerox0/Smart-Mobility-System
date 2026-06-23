---
clarity-gate-version: 2.1
processed-date: 2026-06-23
processed-by: Claude (AI) + Cross-Reference Engine — documentazione.md §UC.UT.06 (primary), Master_Spec.cgd.md v4.0, UC.UT.06-clean.uml, chiarimenti-vari.md, response2.md
clarity-status: CLEAR
hitl-status: REVIEWED
hitl-pending-count: 0
points-passed: 1-9
document-sha256: PENDING
hitl-claims:
  - id: claim-a3f1b2c0
    text: "La tariffa di sospensione è differenziata rispetto alla tariffa oraria standard e il calcolo del costo di sospensione è gestito da Corsa.aggiornaCosto() con logica interna non specificata nei requisiti attuali"
    value: "CONFERMATO: tariffa differenziata confermata."
    source: "documentazione.md UC.UT.06 Requisiti: 'tariffa differenziata per la sospensione' + Master_Spec.cgd.md §8 Invariants: 'Il costo di una Corsa è sempre >= 0 e include costi di sospensione'"
    location: "UC.UT.06/flow/suspension-cost"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-d8e2f5a1
    text: "Il QR code di sospensione è generato dal sistema (non dal mezzo) e mostrato all'utente tramite AppUtente.mostraQRCode(); la scansione QR invoca GestioneCorsa.richiediSblocco(qrCode)"
    value: "CONFERMATO: flusso QR sospensione confermato."
    source: "documentazione.md UC.UT.06 Flusso principale passi 4-5 + Master_Spec.cgd.md AppUtente.mostraQRCode(), AppUtente.scansionaQRCode(), GestioneCorsa.richiediSblocco() + UC.UT.06-clean.uml sequence diagram messages"
    location: "UC.UT.06/flow/qr-generation-scanning"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
---

# UC.UT.06 — Sospensione Corsa (Ride Suspension)

**Versione:** 1.0 *(AI-Ready — cross-reference completo e validato)*  
**Team:** Cofee Coders  
**Progetto:** Smart Mobility System — Ingegneria del Software a.a. 2025/2026  
**Architettura:** MVC Web-oriented con Controller Intermediario  

**Fonti (in ordine di priorità):**
1. `documentazione.md` §UC.UT.06 — specifica primaria del caso d'uso *(chiarimenti-vari.md punto 15)*
2. `Master_Spec.cgd.md` v4.0 — specifica architetturale e metodi
3. `UC.UT.06-clean.uml` (XMI 2.1) — diagramma di sequenza
4. `chiarimenti-vari.md` — interpretazioni e vincoli

---

## 1. Use Case Specification

| Campo | Valore |
|-------|--------|
| **ID** | UC.UT.06 |
| **Nome** | Sospensione Corsa |
| **User Story** | UT.09 — *Come utente, voglio poter effettuare delle soste senza perdere il possesso del mezzo, così da poter sospendere la mia corsa.* |
| **Attore Principale** | Utente |
| **Relazione UC** | **Estende:** UC.UT.03 (Gestione Corsa) |
| **Precondizioni** | 1. La corsa è stata avviata ed è attiva. 2. Lo stato del mezzo è `in_uso`. |
| **Postcondizioni** | 1. Il costo della corsa include il costo di sospensione. 2. Il mezzo è tornato nello stato `in_uso` dopo la ripresa. |
| **Requisiti** | Integrazione sensore per lettura QR Code sul mezzo; Interfaccia IoT per blocco/sblocco Mezzo remoto; tariffa differenziata per la sospensione *(estimated — formula esatta non specificata nei requisiti)*. |

### 1.1 Breve Descrizione

L'utente richiede di sospendere la propria corsa attiva. Il sistema blocca il veicolo fisicamente e ne aggiorna lo stato a `sospeso`, fornendo all'utente un QR code. Successivamente, l'utente scansiona il QR code per sbloccare il mezzo, riprendere la corsa e il sistema aggiorna i costi relativi al tempo di sospensione.

### 1.2 Flusso Principale

| Passo | Attore | Azione | Descrizione |
|-------|--------|--------|-------------|
| 1 | Utente | `sospendiCorsa(idCorsa)` | L'utente invia il comando per sospendere la corsa attiva |
| 2 | Sistema | `bloccoMezzoFisico(idMezzo)` | Il sistema ha bloccato fisicamente il mezzo via IoT |
| 3 | Sistema | `setStato(sospeso)` | Il sistema ha impostato lo stato del mezzo su `sospeso` |
| 4 | Sistema | `mostraQRCode()` | Il sistema ha fornito il QR Code di sospensione all'utente |
| 5 | Utente | `scansionaQRCode(qrCode)` | L'utente ha scansionato il QR code per riprendere la corsa |
| 6 | Sistema | `sbloccoMezzoFisico(idMezzo)` | Il sistema ha sbloccato fisicamente il mezzo via IoT |
| 7 | Sistema | `setStato(in_uso)` | Il sistema ha reimpostato lo stato del mezzo a `in_uso` |
| 8 | Sistema | `aggiornaCosto(costoSospensione)` | Il sistema ha aggiornato il costo della corsa includendo la tariffa di sospensione |
| 9 | Sistema | `mostraRipresaCorsa()` | Il sistema ha mostrato la conferma di ripresa corsa all'utente |

### 1.3 Flusso Alternativo

**FA1 — Corsa non esistente:** Al passo 2 del flusso principale, il sistema ha rilevato che la corsa indicata non è presente a sistema. Il sistema ha mostrato un messaggio di errore all'utente (`mostraErrore(msg)`), impedendo la sospensione.

---

## 2. Method Traceability Matrix

### 2.1 Sospensione Corsa (Suspend)

| # | Metodo | Componente | Layer | Ritorno | Parametri | Fonte |
|---|--------|------------|-------|---------|-----------|-------|
| S1 | `sospendiCorsa(idCorsa)` | AppUtente | View | `void` | `idCorsa` | `Master_Spec.cgd.md:707` |
| S2 | `sospensioneCorsa()` | GestioneCorsa | Controller | `bool` | — | `Master_Spec.cgd.md:578` |
| S3 | `bloccoMezzoFisico(idMezzo)` | Mezzo:IoT | External | `bool` | `idMezzo` | `Master_Spec.cgd.md:835` |
| S4 | `setStato(stato: sospeso)` | Mezzo | Model | `void` | `stato: StatoMezzo` | `Master_Spec.cgd.md:321` |
| S5 | `mostraQRCode()` | AppUtente | View | `void` | — | `Master_Spec.cgd.md:696` |
| S6 | `mostraErrore(msg)` | AppUtente | View | `void` | `msg: String` | `Master_Spec.cgd.md:692` |

### 2.2 Ripresa Corsa (Resume)

| # | Metodo | Componente | Layer | Ritorno | Parametri | Fonte |
|---|--------|------------|-------|---------|-----------|-------|
| R1 | `scansionaQRCode(qrCode)` | AppUtente | View | `void` | `qrCode: String` | `Master_Spec.cgd.md:702` |
| R2 | `richiediSblocco(qrCode)` | GestioneCorsa | Controller | `bool` | `qrCode: String` | `Master_Spec.cgd.md:579` |
| R3 | `sbloccoMezzoFisico(idMezzo)` | Mezzo:IoT | External | `bool` | `idMezzo` | `Master_Spec.cgd.md:836` |
| R4 | `setStato(stato: in_uso)` | Mezzo | Model | `void` | `stato: StatoMezzo` | `Master_Spec.cgd.md:321` |
| R5 | `aggiornaCosto(costoSospensione)` | Corsa | Model | `void` | `costo: float` | `Master_Spec.cgd.md:373` |
| R6 | `mostraRipresaCorsa()` | AppUtente | View | `void` | — | `Master_Spec.cgd.md:697` |

### 2.3 Diagramma di Sequenza — Lifeline Mapping

| Lifeline (XMI) | Componente (Master_Spec) | Layer |
|-----------------|--------------------------|-------|
| Utente | Utente (Attore) | Actor |
| AppUtente | AppUtente | View |
| GestioneCorsa | GestioneCorsa | Controller |
| Mezzo | Mezzo | Model |
| MezzoIoT | Mezzo:IoT | External |
| Corsa | Corsa | Model |

*Mapping verificato da `UC.UT.06-clean.uml` → `Master_Spec.cgd.md` componenti.*

---

## 3. Critical Checks — Verification Record

### Check 1: GestioneCorsa.sospensioneCorsa() return type
| Fonte | Evidenza | Esito |
|-------|----------|-------|
| `Master_Spec.cgd.md:578` | `sospensioneCorsa() \| bool \| —` | **PASS** |

### Check 2: Mezzo:IoT has both bloccoMezzoFisico() AND sbloccoMezzoFisico()
| Fonte | Evidenza | Esito |
|-------|----------|-------|
| `Master_Spec.cgd.md:835` | `bloccoMezzoFisico(idMezzo) \| bool \| idMezzo` | **PASS** |
| `Master_Spec.cgd.md:836` | `sbloccoMezzoFisico(idMezzo) \| bool \| idMezzo` | **PASS** |

### Check 3: Mezzo state cycle — in_uso → sospeso → in_uso
| Fonte | Evidenza | Esito |
|-------|----------|-------|
| `Master_Spec.cgd.md:974` | Invariante: `(sospeso → in_uso)*` nel ciclo di stato | **PASS** |
| `Master_Spec.cgd.md:168-175` | `StatoMezzo` enum include `sospeso` tra i valori validi | **PASS** |
| `documentazione.md:371-372` | "Il sistema imposta lo stato del mezzo su 'sospeso'" / "Il sistema reimposta lo stato del mezzo a 'in uso'" | **PASS** |

### Check 4: Suspension cost included in Corsa.aggiornaCosto()
| Fonte | Evidenza | Esito |
|-------|----------|-------|
| `Master_Spec.cgd.md:975` | Invariante: `Il costo di una Corsa è sempre >= 0 e include costi di sospensione` | **PASS** |
| `Master_Spec.cgd.md:373` | `aggiornaCosto(costo) \| void \| costo: float` | **PASS** |
| `documentazione.md:373` | Postcondizione: "Il costo della corsa include anche il costo della sospensione" | **PASS** |

### Check 5: Full Flow Mapping (Suspend → QR → Resume → Cost Update)

| Passo | Azione UC | Metodo | Componente | Fonte | Esito |
|-------|-----------|--------|------------|-------|-------|
| 1 | User requests suspend | `sospendiCorsa(idCorsa)` | AppUtente | `Master_Spec.cgd.md:707` | **PASS** |
| 2 | IoT physical block | `bloccoMezzoFisico(idMezzo)` | Mezzo:IoT | `Master_Spec.cgd.md:835` | **PASS** |
| 3 | Set stato "sospeso" | `setStato(sospeso)` | Mezzo | `Master_Spec.cgd.md:321` | **PASS** |
| 4 | Generate QR Code | `mostraQRCode()` | AppUtente | `Master_Spec.cgd.md:696` | **PASS** |
| 5 | Scan QR to resume | `scansionaQRCode(qrCode)` | AppUtente | `Master_Spec.cgd.md:702` | **PASS** |
| 6 | IoT unlock | `sbloccoMezzoFisico(idMezzo)` | Mezzo:IoT | `Master_Spec.cgd.md:836` | **PASS** |
| 7 | Set stato "in_uso" | `setStato(in_uso)` | Mezzo | `Master_Spec.cgd.md:321` | **PASS** |
| 8 | Update cost with suspension fee | `aggiornaCosto(costoSosp)` | Corsa | `Master_Spec.cgd.md:373` | **PASS** |
| 9 | Show resume confirmation | `mostraRipresaCorsa()` | AppUtente | `Master_Spec.cgd.md:697` | **PASS** |

---

## 4. Sequence Diagram (Reconstructed)

```
Utente          AppUtente       GestioneCorsa     Mezzo        Mezzo:IoT      Corsa
  |                 |                 |               |              |            |
  |--sospendiCorsa->|                 |               |              |            |
  |                 |--sospensioneCorsa()->           |              |            |
  |                 |                 |--bloccoMezzoFisico(idMezzo)-->        |
  |                 |                 |<--bool:true----|              |            |
  |                 |                 |--setStato(sospeso)->         |            |
  |                 |<--mostraQRCode()-|               |              |            |
  |<--QR Code-------|                 |               |              |            |
  |                                                             [sospeso attivo]
  |--scansionaQRCode(qrCode)->|        |               |              |            |
  |                 |--richiediSblocco(qrCode)->        |              |            |
  |                 |                 |--sbloccoMezzoFisico(idMezzo)-->        |
  |                 |                 |<--bool:true----|              |            |
  |                 |                 |--setStato(in_uso)->          |            |
  |                 |                 |--aggiornaCosto(costoSosp)--->|----------->|
  |                 |<--mostraRipresaCorsa()|          |              |            |
  |<--conferma------|                 |               |              |            |
```

**Flusso Alternativo (FA1 — Corsa non esistente):**
```
Utente          AppUtente       GestioneCorsa
  |                 |                 |
  |--sospendiCorsa->|                 |
  |                 |--sospensioneCorsa()-> |
  |                 |<--bool:false----|
  |<--mostraErrore--|                 |
```

---

## 5. State Transition Diagram

```
         UC.UT.03 avvio                              UC.UT.06 sospendi
    disponibile ────────────> in_uso ──────────────────────────────> sospeso
                                  ^                                      │
                                  │       UC.UT.06 scansionaQRCode       │
                                  └──────────────────────────────────────┘
                                              (ripresa corsa)

                                              UC.UT.07 termina
                                    in_uso ───────────────────> disponibile
```

*Ciclo verificato: `in_uso → sospeso → in_uso` è transizione valida nello stato Mezzo secondo l'invariante di dominio `Master_Spec.cgd.md:974`.*

---

## 6. Architectural Constraints

| Vincolo | Rilevanza per UC.UT.06 | Fonte |
|---------|------------------------|-------|
| **Autenticazione obbligatoria** | L'utente deve avere una sessione attiva per sospendere/riprendere una corsa | `Master_Spec.cgd.md:956` |
| **Blocco corsa attiva** | L'utente non può avviare una nuova corsa mentre la corsa corrente è sospesa | `Master_Spec.cgd.md:959` |
| **Disaccoppiamento View-Model** | AppUtente non interagisce direttamente con Mezzo o Corsa — tutto passa da GestioneCorsa | `Master_Spec.cgd.md:965` |
| **Simulazione sistemi esterni** | Mezzo:IoT è componente simulata — progetto universitario | `chiarimenti-vari.md:16` |
| **Mezzo:IoT ha entrambi i metodi** | `bloccoMezzoFisico()` e `sbloccoMezzoFisico()` sono entrambi presenti nel sistema esterno simulato | `Master_Spec.cgd.md:835-836` |

---

## 7. Data Involved

| Entità | Attributo | Modifica in UC | Operazione |
|--------|-----------|----------------|------------|
| **Mezzo** | `stato` | `in_uso → sospeso` (suspend), `sospeso → in_uso` (resume) | `setStato()` |
| **Corsa** | `costo` | Incrementato della tariffa di sospensione | `aggiornaCosto()` |
| **AppUtente** | — | Visualizzazione QR Code, errore, conferma ripresa | `mostraQRCode()`, `mostraErrore()`, `mostraRipresaCorsa()` |

---

## 8. Glossario (UC-specifico)

| Termine | Definizione |
|---------|-------------|
| **Sospensione Corsa** | Pausa temporanea della corsa con mantenimento del possesso del mezzo. Il veicolo viene fisicamente bloccato ma rimane assegnato all'utente |
| **QR Code di sospensione** | Codice QR generato dal sistema dopo il blocco del mezzo, necessario per la successiva ripresa della corsa |
| **Tariffa di sospensione** | Costo orario differenziato applicato durante il periodo di sospensione, distinto dalla tariffa oraria standard di corsa *(estimated — formula non specificata nei requisiti)* |

---

## 9. Critical Checks — Consolidato Finale

| # | Check | Risultato |
|---|-------|-----------|
| 1 | `GestioneCorsa.sospensioneCorsa()` returns `bool` | **VERIFICATO** (`Master_Spec.cgd.md:578`) |
| 2 | `Mezzo:IoT` has `bloccoMezzoFisico()` AND `sbloccoMezzoFisico()` | **VERIFICATO** (`Master_Spec.cgd.md:835-836`) |
| 3 | Mezzo state cycle: `in_uso → sospeso → in_uso` is correct | **VERIFICATO** (`Master_Spec.cgd.md:974`) |
| 4 | Suspension cost included in `Corsa.aggiornaCosto()` | **VERIFICATO** (`Master_Spec.cgd.md:975`) |
| 5 | Full flow mapping (suspend → QR → resume → cost update) | **VERIFICATO** — 9/9 passi tracciati |

---

## 10. Sequence Diagram — XMI Event Trace

*Estratto da `UC.UT.06-clean.uml`, normalizzato da artefatti XMI (chiarimenti-vari.md punto 14):*

| # | Message (XMI) | Send Event | Receive Event | Sort | Corrispondenza Master_Spec |
|---|---------------|------------|---------------|------|---------------------------|
| 1 | `sospendiCorsa(idCorsa)` | Utente | AppUtente | synchCall | `AppUtente.sospendiCorsa(idCorsa)` |
| 2 | `scansionaQRCode(QR_Code)` | Utente | AppUtente | synchCall | `AppUtente.scansionaQRCode(qrCode)` |
| 3 | `visualizza QR Code` | AppUtente | Utente | reply | `AppUtente.mostraQRCode()` → feedback |
| 4 | `visualizza sblocco corsa` | AppUtente | Utente | reply | `AppUtente.mostraRipresaCorsa()` → feedback |
| 5 | `visualizza errore` | AppUtente | Utente | reply | `AppUtente.mostraErrore(msg)` → feedback |

*I nomi dei messaggi reply nel diagramma XMI rappresentano il contenuto visualizzato (non i nomi dei metodi). I metodi corrispondenti sono identificati dalla traceability matrix §2.*

---

## HITL Verification Record

### Round A: Derived Data Confirmation

Tutti i claim sono derivati da fonti presenti nel repository (documentazione.md, Master_Spec.cgd.md, UC.UT.06-clean.uml). Due claim richiedono conferma umana:

| # | Claim ID | Claim | Fonte | Stato |
|---|----------|-------|-------|-------|
| 1 | claim-a3f1b2c0 | Tariffa sospensione differenziata — `Corsa.aggiornaCosto()` gestisce il calcolo, formula esatta non nei requisiti | documentazione.md + Master_Spec.cgd.md | **PENDING** |
| 2 | claim-d8e2f5a1 | Flusso QR: `mostraQRCode()` genera e visualizza, `scansionaQRCode()` raccoglie e inoltra a `richiediSblocco()` | documentazione.md + Master_Spec.cgd.md + XMI | **PENDING** |

### Round B: True HITL Verification

*Nessun claim richiede Round B — tutti i claim sono derivati da fonti documentali esistenti e richiedono solo conferma di interpretazione (Round A).*

---

**Verdict:** CLEAR | PENDING — 5/5 critical checks passed, 2 claims pending Round A confirmation.

---

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | PENDING

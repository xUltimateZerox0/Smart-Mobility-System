---
clarity-gate-version: 2.1
document-type: Implementation
processed-date: 2026-06-25
processed-by: AI Cross-Reference Engine, response2.md, UC.UT.03-clean.uml v2.0
sources:
  primary: documentazione.md §2.2.2 — UC.UT.03 specifica tabellare
  master-spec: Master_Spec.cgd.md v4.0 — GestioneCorsa, Corsa, Mezzo, AppUtente, External Systems
  sequence-diagram: docs/diagrams/sequence-diagrams/UC.UT.03/UC.UT.03-clean.uml
  chiarimenti: chiarimenti-vari.md — punti 2 (orarioFine) e 3 (auth=constraint)
  uc08-spec: UC.UT.08.cgd.md — specifica Monitoraggio Costo (nuovo use case attivato)
clarity-status: CLEAR
hitl-status: REVIEWED
hitl-pending-count: 0
points-passed: 1-9
document-sha256: 5418226c426907ee172da640dcad9810724fc0b4ea9b8afb844312c9d36cd851
hitl-claims:
  - id: claim-uc03-avviacorsa-params
    text: "avviaCorsa() nel Controller non ha parametri in Master_Spec.cgd ma il SD usa avviaCorsa(idMezzo, idUtente)"
    value: "CONFERMATO: con parametri (idMezzo, idUtente). MS needs updating."
    source: "Verificare nel Master_Spec.cgd linea 573 e confrontare con il sequence diagram UC.UT.03-clean.uml"
    location: "GestioneCorsa/avviaCorsa"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-uc03-lifeline-naming
    text: "Il SD usa lifeline 'Vehicle' e 'User' (in inglese) alternati a 'Mezzo' e 'Utente' — artefatti XMI"
    value: "CONFERMATO: Italian names (Mezzo/Utente) are correct."
    source: "Confrontare le lifeline nel SD UC.UT.03-clean.uml con i nomi entità in Master_Spec.cgd §2-4"
    location: "Sequence Diagram/lifelines"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-uc03-descriptive-msgs
    text: "I messaggi 'stimaCosto', 'calculate partial cost', 'start ride timer', 'activate cost update cycle' nel SD non corrispondono a metodi nominali del Master_Spec"
    value: "CONFERMATO: stimaCosto is reply data from `aggiornaStima(idCorsa)`. Descriptive msgs are XMI artifacts."
    source: "Confrontare messaggi SD UC.UT.03-clean.uml con metodi GestioneCorsa e Corsa in Master_Spec.cgd §2-3"
    location: "Sequence Diagram/descriptive messages"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
---

# UC.UT.03 — Gestione Corsa
## Complete Use Case Specification v2.1

---

## 1. Use Case Identity

| Campo | Valore |
|-------|--------|
| **ID** | UC.UT.03 |
| **Nome** | Gestione Corsa |
| **Attore Primario** | Utente (autenticato, con sessione attiva) |
| **User Stories** | UT.03 (importo in tempo reale), UT.07 (autenticazione per sblocco — vincolo) |
| **Breve Descrizione** | L'utente scansiona il QR Code per avviare una corsa. Il sistema verifica la disponibilità del veicolo e richiede la selezione di un metodo di pagamento. Al superamento dei controlli, il mezzo viene sbloccato fisicamente, la corsa ha inizio e il sistema avvia un monitoraggio continuo del costo, che viene mostrato in tempo reale all'utente. |
| **Priorità (Sprint Backlog)** | 40 (UT.03 Sprint 1) + 40 (UT.07 Sprint 1) |

---

## 2. Relazioni Use Case *(fonte: Master_Spec.cgd §7, documentazione.md §2.2.2)*

| Relazione | UC Destinazione | Direzione |
|-----------|-----------------|-----------|
| **Include** | UC.UT.05 (Metodo Pagamento) | UC.UT.03 include UC.UT.05 — la selezione del metodo di pagamento è passo obbligatorio del flusso principale |
| **Include** | UC.UT.07 (Termina Corsa e Pagamento) | UC.UT.03 include UC.UT.07 — la terminazione della corsa completa il ciclo di vita |
| **Attiva** | UC.UT.08 (Monitoraggio Costo) | UC.UT.03 attiva UC.UT.08 — dopo l'avvio della corsa, il monitoraggio periodico del costo viene attivato e prosegue fino alla terminazione della corsa |
| **Estende** | UC.UT.02 (Prenotazione Mezzo) | UC.UT.03 estende UC.UT.02 — l'avvio della corsa parte dalla prenotazione attiva |
| **Esteso da** | UC.UT.06 (Sospensione Corsa) | UC.UT.06 estende UC.UT.03 — la sospensione è possibile solo durante corsa attiva |

> **Nota su UT.07:** Come da chiarimenti-vari.md punto 3, "utilizzare un metodo di autenticazione per sbloccare il mezzo" è un **vincolo architetturale** (non una funzionalità). Il QR code funge da token di autenticazione fisica tramite `richiediSblocco(qrCode)`.

---

## 3. Precondizioni *(fonte: documentazione.md §2.2.2 UC.UT.03)*

| # | Precondizione | Verifica | Metodo |
|---|---------------|----------|--------|
| P1 | L'utente ha effettuato l'accesso e ha una sessione attiva | Sessione valida | GestioneAutenticazione (vincolo §8.1) |
| P2 | Il mezzo selezionato è bloccato (fisicamente) | StatoMezzo ≠ in_uso | Mezzo.getStato() |
| P3 | Il mezzo selezionato risulta prenotato (da UC.UT.02) | Prenotazione attiva associata all'utente | Prenotazione.getStato() == attiva |
| P4 | L'utente non si trova in una corsa attiva | Nessuna corsa con orarioFine == null per l'utente | Corsa.ricercaCorsa() — vincolo §8.4 |

---

## 4. Flusso Principale *(fonte primaria: documentazione.md §2.2.2)*

| Step | Attore/Sistema | Azione | Metodo Tracciato | Componente |
|------|----------------|--------|------------------|------------|
| 1 | Utente | Scansiona il QR code sul mezzo | `scansionaQRCode(qrCode)` | AppUtente:708 |
| 2 | Sistema | Verifica che il mezzo sia disponibile | `controllaDisponibilita(qrCode)` [overload String] | GestioneCorsa:576 |
| 3 | Sistema | Richiede all'utente di selezionare un metodo di pagamento | `→ include UC.UT.05` | GestorePagamento |
| 4 | Utente | Seleziona metodo di pagamento (da UC.UT.05) | `acquisisciSceltaMetodo(idMetodoPagamento)` | GestioneCorsa:581 |
| 5 | Sistema | Richiede avvio corsa | `avviaCorsa()` | GestioneCorsa:573 |
| 6 | Sistema | Registra la nuova corsa nel DB | `creaCorsa(orarioInizio, coordinatePartenza, idUtente, idMezzo)` | Corsa:368 |
| 7 | Sistema | Sblocca fisicamente il mezzo | `sbloccoMezzoFisico(idMezzo)` | Mezzo:IoT:836 |
| 8 | Sistema | Imposta lo stato logico del mezzo a "in uso" | `setStato(StatoMezzo.in_uso)` | Mezzo:321 |
| 9 | Sistema | Notifica l'avvio della corsa all'utente | `mostraSuccesso()` | AppUtente:694 |
| 10 | Sistema | Attiva monitoraggio periodico del costo (UC.UT.08) | `Attivazione caso d'uso UC.UT.08` → `aggiornaStima(idCorsa)` → `mostraStima(idCorsa)` | GestioneCorsa:577, AppUtente:693 |

> **Nota su Step 10:** Il diagramma di sequenza UC.UT.03-clean.uml mostra il messaggio esplicito `Attivazione caso d'uso UC.UT.08` (synchCall) dopo la notifica di avvio corsa e la conferma `true` dello sblocco fisico. Questa attivazione avvia il ciclo periodico di monitoraggio costo documentato in UC.UT.08.cgd.md.

### 4.1 Dettaglio Cost Flow (Step 10)

| Sotto-step | Azione | Metodo | Componente |
|------------|--------|--------|------------|
| 10a | Il Controller attiva UC.UT.08 | `Attivazione caso d'uso UC.UT.08` (synchCall) | GestioneCorsa |
| 10b | Il Controller calcola il costo parziale | `aggiornaStima(idCorsa)` → `return float` | GestioneCorsa:577 |
| 10c | Il Model aggiorna il costo della corsa | `aggiornaCosto(costo)` | Corsa:373 |
| 10d | L'AppUtente mostra la stima aggiornata | `mostraStima(idCorsa)` | AppUtente:693 |
| 10e | Ripetizione periodica (loop) fino a terminazione | — | GestioneCorsa → AppUtente |

### 4.2 Verifica Sblocco (Step 7 — Dettaglio IoT)

| Sotto-step | Azione | Metodo | Componente |
|------------|--------|--------|------------|
| 7a | Controller richiede sblocco via QR | `richiediSblocco(qrCode)` → `return bool` | GestioneCorsa:579 |
| 7b | IoT esegue sblocco fisico | `sbloccoMezzoFisico(idMezzo)` → `return bool` | Mezzo:IoT:836 |
| 7c | Se fallisce: `mostraErrore("cannot unlock")` | `mostraErrore(msg)` | AppUtente:692 |

---

## 5. Flussi Alternativi *(fonte: documentazione.md §2.2.2)*

### FA-01: Mezzo non disponibile

| Step | Azione | Metodo |
|------|--------|--------|
| A1 | Al passo 2 del flusso principale, `controllaDisponibilita(qrCode)` restituisce `false` | GestioneCorsa:576 |
| A2 | Il sistema mostra un messaggio di errore | `mostraErrore("mezzo non disponibile")` — AppUtente:692 |
| A3 | L'avvio della corsa è impedito | — |

### FA-02: Metodo di pagamento non convalidato *(da UC.UT.05)*

| Step | Azione | Metodo |
|------|--------|--------|
| B1 | `GestorePagamento.elaboraDatiCarta()` fallisce o `convalidaCarta()` restituisce `false` | GestorePagamento:601, GatewayPagamento:852 |
| B2 | Il sistema mostra errore | `mostraErrore("metodo non convalidato")` |
| B3 | L'utente può reinserire i dati o selezionare metodo esistente | → rientro in UC.UT.05 |

### FA-03: Sblocco IoT fallito

| Step | Azione | Metodo |
|------|--------|--------|
| C1 | `sbloccoMezzoFisico(idMezzo)` restituisce `false` | Mezzo:IoT:836 |
| C2 | Il sistema annulla la corsa creata | Rollback `Corsa` |
| C3 | Il sistema mostra errore | `mostraErrore("cannot unlock vehicle: transaction failed or account blocked")` |

---

## 6. Postcondizioni *(fonte: documentazione.md §2.2.2)*

| # | Postcondizione | Verifica |
|---|----------------|----------|
| Q1 | Il veicolo è stato sbloccato fisicamente | `sbloccoMezzoFisico()` ha restituito `true` |
| Q2 | La corsa è attiva | `Corsa.orarioInizio` è stato impostato, `orarioFine` è null |
| Q3 | Lo stato del mezzo è "in_uso" | `Mezzo.getStato() == StatoMezzo.in_uso` |
| Q4 | Il costo è in aggiornamento periodico | `aggiornaStima()` è in esecuzione ciclica |
| Q5 | Un metodo di pagamento è stato associato alla corsa | `Corsa.idMetodoPagamento` è stato impostato |

> **Nota su orarioFine (chiarimenti-vari.md punto 2):** Al termine del flusso principale UC.UT.03, `orarioFine` è **null** — viene impostato solo al termine della corsa (UC.UT.07). La condizione "corsa attiva" è verificabile tramite `orarioFine == null`.

---

## 7. Controller Traceability — GestioneCorsa *(fonte: Master_Spec.cgd §3)*

### 7.1 Metodi Coinvolti nel Flusso

| Metodo | Ritorno | Parametri | Step UC | Note |
|--------|---------|-----------|---------|------|
| `controllaDisponibilita(qrCode)` | bool | qrCode: String | 2 | Overload String — usa QR code per verificare |
| `controllaDisponibilita()` | bool | — | — | Overload no-args — disponibilità generica |
| `acquisisciSceltaMetodo(idMetodoPagamento)` | void | idMetodoPagamento | 4 | Associa metodo alla sessione corsa |
| `avviaCorsa()` | void | — | 5 | **Nessun parametro** — recupera ID da sessione/contesto |
| `richiediSblocco(qrCode)` | bool | qrCode: String | 7a | Delega a Mezzo:IoT |
| `aggiornaStima(idCorsa)` | float | idCorsa | 10 | Calcola costo parziale |
| `terminaCorsa()` | void | — | (UC.UT.07) | Termina la corsa attiva |
| `sospensioneCorsa()` | bool | — | (UC.UT.06) | Sospende temporaneamente |
| `richiediCalcoloPercorso(coordinateUtente, destinazione)` | percorso | coordinateUtente: String, destinazione: String | (UC.UT.04) | Delega a Servizio Mappa |

### 7.2 Attributi GestioneCorsa

| Attributo | Tipo | Visibilità | Ruolo |
|-----------|------|------------|-------|
| `idGestioneCorsa` | — | private | Identificativo controller |
| `idMetodoPagamento` | — | private | FK metodo selezionato per la corsa corrente |

---

## 8. Model Traceability

### 8.1 Corsa *(fonte: Master_Spec.cgd §2 — Corsa)*

| Metodo | Step UC | Ruolo nel Flusso |
|--------|---------|------------------|
| `creaCorsa(orarioInizio, coordinatePartenza, idUtente, idMezzo)` | 6 | Crea nuova istanza corsa |
| `aggiornaCosto(costo)` | 10b | Aggiorna costo cumulativo |
| `getCosto()` | 10c | Recupera costo per la View |
| `setCosto(costo)` | — | Setter Model passivo |
| `getOrarioInizio()` / `setOrarioInizio()` | 6 | Timestamp avvio |
| `getOrarioFine()` / `setOrarioFine()` | (UC.UT.07) | Timestamp termine |
| `getCoordinatePartenza()` / `setCoordinatePartenza()` | 6 | Posizione avvio (String x,y,z) |
| `getCoordinateArrivo()` / `setCoordinateArrivo()` | (UC.UT.07) | Posizione termine (String x,y,z) |
| `getIdMetodoPagamento()` / `setIdMetodoPagamento()` | 4 | FK metodo pagamento |
| `ricercaCorsa(idCorsa)` | — | Lookup per ID |

### 8.2 Mezzo *(fonte: Master_Spec.cgd §2 — Mezzo)*

| Metodo | Step UC | Ruolo nel Flusso |
|--------|---------|------------------|
| `getStato()` | 2 | Verifica disponibilità |
| `setStato(StatoMezzo.in_uso)` | 8 | Aggiorna stato a "in_uso" |
| `getCoordinateMezzo()` | 2 | Posizione veicolo (String x,y,z) |
| `getCostoOrario()` | 10a | Base per calcolo costo |
| `getAutonomia()` | 2 | Dato accessorio per disponibilità |

### 8.3 AppUtente *(fonte: Master_Spec.cgd §4 — AppUtente)*

| Metodo | Step UC | Ruolo nel Flusso |
|--------|---------|------------------|
| `scansionaQRCode(qrCode)` | 1 | Acquisizione QR code |
| `mostraStima(idCorsa)` | 10c | Visualizzazione costo tempo reale |
| `mostraSuccesso()` | 9 | Notifica avvio corsa |
| `mostraErrore(msg)` | FA-A2, FA-C3 | Messaggi di errore |
| `apriAvvioCorsa()` | — | Interfaccia avvio corsa |
| `mostraMetodoConvalidato()` | 4 | Conferma metodo selezionato |
| `mostraSceltaMetodi()` | 3 | Interfaccia selezione metodo (UC.UT.05) |
| `mostraMetodi(metodi)` | 3 | Lista metodi salvati (UC.UT.05) |

---

## 9. External Systems Traceability

### 9.1 Mezzo:IoT *(fonte: Master_Spec.cgd §5)*

| Metodo | Step UC | Note |
|--------|---------|------|
| `sbloccoMezzoFisico(idMezzo)` → bool | 7 | Simulato (chiarimenti-vari.md punto 16) |
| `bloccoMezzoFisico(idMezzo)` → bool | (UC.UT.07) | Blocco al termine |

### 9.2 Gateway Pagamento *(fonte: Master_Spec.cgd §5)*

| Metodo | Step UC | Note |
|--------|---------|------|
| `effettuaPagamento(idMetodoPagamento, idCorsa)` → bool | (UC.UT.07) | Pagamento finale |
| `convalidaCarta(numCarta, dsCarta, cvv, intestatarioCarta)` → bool | (UC.UT.05) | Validazione carta in fase selezione |

> **Nota XMI:** Il nome `EffettuaPagamento` (E maiuscola) nel class diagram XMI è un typo di esportazione — corretto a `effettuaPagamento` (Master_Spec.cgd:851, claim-5d8e2f020).

---

## 10. Sequence Diagram Cross-Validation *(fonte: UC.UT.03-clean.uml v2.0)*

### 10.1 Lifeline Mapping

| Lifeline nel SD | Nome Canonico | Stato |
|-----------------|---------------|-------|
| `User` / `Utente` | Utente | **INCONSISTENTE:** nomi intercambiabili — artefatto XMI. Riferimento canonico: Utente. |
| `AppUtente` / `App` | AppUtente | **INCONSISTENTE:** abbreviazione `App` è artefatto XMI. Riferimento canonico: AppUtente. |
| `Vehicle` / `Mezzo` | Mezzo | **INCONSISTENTE:** `Vehicle` è artefatto XMI. Riferimento canonico: Mezzo. |
| `GestioneCorsa` / `Controller` | GestioneCorsa | **INCONSISTENTE:** `Controller` generico è artefatto XMI. Riferimento canonico: GestioneCorsa. |
| `GestorePagamento` | GestorePagamento | Corretto |
| `Corsa` | Corsa | Corretto |
| `DBMS` | DBMS | Corretto |

> **Root cause:** chiarimenti-vari.md punto 14 — "Potresti trovare metodi/classi/attributi al plurale piuttosto che al singolare... errori di esportazione XMI."

### 10.2 Flusso SD vs Flusso documentazione.md

| Step SD | Messaggio SD | Metodo Canonico (Master_Spec) | Allineamento |
|---------|-------------|-------------------------------|-------------|
| Scansione QR | `scansioneQRCode(QR_Code)` | `scansionaQRCode(qrCode)` | Corretto *(overload String)* |
| QR scan (attore) | `scans QR code on vehicle(vehicleId, userId)` | `scansionaQRCode(qrCode)` | **PARZIALE:** SD usa firma diversa (vehicleId, userId) — artefatto XMI |
| Controllo disponibilità | `controllaDisponibilità(QR_Code)` | `controllaDisponibilita(info: String)` | Corretto |
| Verifica mezzo | `getStato(idMezzo)` | `Mezzo.getStato()` | Corretto |
| Risposta stato | `stato:enum` | *(reply value)* | **DESCRITTIVO** |
| Verifica mezzo (eng) | `check vehicle availability(vehicleId)` | `Mezzo.getStato()` | **DESCRITTIVO:** non è nome metodo reale |
| Verifica pagamento | `check payment validity(userId)` | — | **DESCRITTIVO:** non mappa a metodo specifico |
| Disponibilità ok | `true` / `notifica mezzo disponibile` | — | **DESCRITTIVO:** risposte intermedie |
| Conferma visiva | `visualizza conferma` | `mostraSuccesso()` | **DESCRITTIVO** |
| Metodo convalidato | `mostraMetodoConvalidato()` | `mostraMetodoConvalidato()` | Corretto |
| Attivazione UC.UT.05 | `Attivazione caso d'uso UC.UT.05` (asynchCall) | — | **DESCRITTIVO:** attiva include UC.UT.05 |
| Avvio corsa UI | `apriAvvioCorsa()` | AppUtente (View) | Corretto |
| Avvio corsa | `avviaCorsa(idMezzo, idUtente)` | `avviaCorsa()` | **DISCREPANZA PARAMETRI:** Master_Spec ha no-args, SD ha (idMezzo, idUtente) |
| Creazione corsa | `creaCorsa(orarioInizio, coordinatePartenza, idUtente, idMezzo)` | `creaCorsa(orarioinizio, coordinatePartenza, idUtente, idMezzo)` | Corretto |
| Sblocco IoT | `sbloccoMezzoFisico(idMezzo)` | `sbloccoMezzoFisico(idMezzo)` | Corretto |
| Set stato | `setStato(in_uso)` | `setStato(StatoMezzo.in_uso)` | Corretto |
| Attivazione UC.UT.08 | `Attivazione caso d'uso UC.UT.08` (synchCall) | — | **NOVITÀ:** attiva monitoraggio costo periodico (UC.UT.08) |
| Aggiornamento costo | `aggiornaStima(idCorsa)` | `aggiornaStima(idCorsa)` | Corretto |
| Calcolo parziale | `calculate partial cost(vehicleId)` | — | **DESCRITTIVO:** logica interna di aggiornaStima |
| Stima display | `stimaCosto` | — | **DESCRITTIVO:** non è metodo — è il valore mostrato |
| Mostra stima | `mostraStima(idCorsa)` | `mostraStima(idCorsa)` | Corretto |
| Notifica avvio | `notifica avvio corsa` | `mostraSuccesso()` | **DESCRITTIVO:** naming generico |
| Conferma metodo | `mostraMetodoConvalidato()` | `mostraMetodoConvalidato()` | Corretto |
| Errore disponibilità | `mostraErrore("mezzo non disponibile")` | `mostraErrore(msg)` | Corretto |
| Errore metodo | `mostraErrore("metodo non convalidato")` | `mostraErrore(msg)` | Corretto |

### 10.3 Messaggi Descrittivi/Logici (non metodi reali)

| Messaggio SD | Spiegazione |
|-------------|-------------|
| `start ride timer` | Logica interna — non è un metodo esplicito nel Master_Spec |
| `activate cost update cycle` | Loop di `aggiornaStima()` — concettuale |
| `update interface cost(cost)` | Aggiornamento UI — non è metodo nominale |
| `visualizza errore` | Proxy per `mostraErrore(msg)` |
| `visualizza stima` | Proxy per `mostraStima(idCorsa)` |
| `visualizza conferma` | Proxy per `mostraSuccesso()` |
| `return partial cost(cost)` | Valore di ritorno di `aggiornaStima()` |
| `notifica mezzo disponibile` | Messaggio di sistema post-verifica disponibilità |
| `Attivazione caso d'uso UC.UT.05` | Attivazione dell'include UC.UT.05 (Metodo Pagamento) — messaggio di flusso, non metodo |
| `Attivazione caso d'uso UC.UT.08` | Attivazione del monitoraggio costo periodico — messaggio di flusso, non metodo |
| `scans QR code on vehicle(vehicleId, userId)` | Azione utente descrittiva — alternativa XMI a `scansionaQRCode(QR_Code)` |
| `initiate scan verification(vehicleId, userId, vehicle)` | Interazione utente concettuale — artefatto XMI della vista alternativa del diagramma |
| `unlock vehicle physically` | Proxy per `sbloccoMezzoFisico(idMezzo)` — descrizione dell'effetto |
| `check vehicle availability(vehicleId)` | Proxy per `controllaDisponibilita()` + `getStato()` |
| `check payment validity(userId)` | Proxy per verifica metodo pagamento via UC.UT.05 |
| `cannot unlock vehicle: transaction failed or account blocked` | Messaggio di errore descrittivo — proxy per `mostraErrore()` |
| `notifica avvio corsa` | Proxy per `mostraSuccesso()` |
| `stato:enum` | Valore di ritorno di `Mezzo.getStato()` |
| `true` / `false` | Risposte booleane di verifica disponibilità / pagamento |
| `void` | Risposta di conferma per metodi senza ritorno |
| `apriAvvioCorsa()` | Metodo View per interfaccia di avvio corsa |

---

## 11. Critical Checks — Risultati

### 11.1 Check 1: CalcoloPercorso() assente da Master_Spec.cgd ✅

| Verifica | Risultato |
|----------|-----------|
| `CalcoloPercorso()` in Master_Spec.cgd | **NO** — non presente |
| `richiediCalcoloPercorso()` in Master_Spec.cgd | **SÍ** — GestioneCorsa:580 |
| `CalcoloPercorso` nel SD UC.UT.03 | **NO** — non presente nel diagramma di sequenza |
| `CalcoloPercorso` nel progetto | Solo in Master_Spec.cgd:28,587,1118 come nota "NON esiste" |

> **Verdetto:** Nessuna inconsistenza. Il SD non fa riferimento a CalcoloPercorso(). Il metodo canonico è `richiediCalcoloPercorso()` (usato in UC.UT.04).

### 11.2 Check 2: controllaDisponibilita() — 2 overload ✅

| Overload | Firma | Master_Spec.cgd |
|----------|-------|-----------------|
| No-args | `controllaDisponibilita()` → bool | Linea 575 |
| String | `controllaDisponibilita(info: String)` → bool | Linea 576 |

> **Verdetto:** Due overload confermati con claim HITL (claim-5a7b9c011). Il SD usa l'overload String: `controllaDisponibilità(QR_Code)`.

### 11.3 Check 3: Relazioni Include/Estende ✅

| Relazione | documentazione.md | Master_Spec.cgd §7 | Consistente |
|-----------|-------------------|-------------------|-------------|
| Include UC.UT.05 | Riga 320 | Riga 933 | ✅ |
| Include UC.UT.07 | Riga 320 | Riga 933 | ✅ |
| Estende UC.UT.02 | Riga 321 | Riga 933 | ✅ |
| Esteso da UC.UT.06 | Riga 322 | Riga 936 | ✅ |

> **Verdetto:** Tutte le relazioni sono correttamente documentate in entrambe le fonti.

### 11.4 Check 4: Flow Step Mapping ✅

| Step | Descrizione | Metodo Tracciato | Fonte |
|------|-------------|------------------|-------|
| QR scan | Scansione QR code | `scansionaQRCode(qrCode)` | AppUtente:708, doc §2.2.2 |
| Disponibilità | Verifica mezzo | `controllaDisponibilita(qrCode)` | GestioneCorsa:576, doc §2.2.2 |
| Pagamento | Selezione metodo | `include UC.UT.05` | doc §2.2.2 |
| Crea corsa | Registrazione DB | `creaCorsa(orarioInizio, coordinatePartenza, idUtente, idMezzo)` | Corsa:368, doc §2.2.2 step 5 |
| IoT sblocco | Sblocco fisico | `sbloccoMezzoFisico(idMezzo)` | Mezzo:IoT:836, doc §2.2.2 step 6 |
| Stato in_uso | Aggiornamento logico | `setStato(StatoMezzo.in_uso)` | Mezzo:321, doc §2.2.2 step 7 |
| Notifica | Conferma utente | `mostraSuccesso()` | AppUtente:694, doc §2.2.2 step 8 |
| Monitoraggio costo | Aggiornamento periodico | `aggiornaStima(idCorsa)` → `mostraStima(idCorsa)` | GestioneCorsa:577, AppUtente:693, doc §2.2.2 step 9 |

> **Verdetto:** Flusso completamente tracciato con mapping biunivoco metodo-sorgente.

### 11.5 Check 5: Coordinate Types ✅

| Attributo | Tipo | Entità | Master_Spec.cgd |
|-----------|------|--------|-----------------|
| `coordinateMezzo` | String | Mezzo | Linea 304 |
| `coordinatePartenza` | String | Corsa | Linea 351 |
| `coordinateArrivo` | String | Corsa | Linea 352 |
| `coordinateUtente` | String | Utente | Linea 235 |

> **Verdetto:** Tutte le coordinate sono `String` (x,y,z) — consistente con claim-7f2a5b013 confermato dal team.

### 11.6 Check 6: terminaCorsa() vs fineCorsa() ✅

| Metodo | Esiste in Master_Spec.cgd | Note |
|--------|--------------------------|------|
| `terminaCorsa()` | **SÍ** — GestioneCorsa:574 | Metodo canonico |
| `fineCorsa()` | **NO** | Artefatto XMI rimosso (Master_Spec.cgd:587, claim-3c8d1e012) |

> **Verdetto:** `terminaCorsa()` esiste, `fineCorsa()` non esiste. Nessun riferimento a `fineCorsa()` nel SD UC.UT.03 (la terminazione è in UC.UT.07).

### 11.7 Check 7: Dipendenze View → Controller ✅

| View | Controller | Fonte Master_Spec.cgd |
|------|-----------|----------------------|
| AppUtente → GestioneCorsa | UC.UT.03 | Linea 916 |
| AppUtente → GestorePagamento | UC.UT.05 | Linea 916 |
| AppUtente → GestioneAutenticazione | Precondizione P1 | Linea 916 |

> **Verdetto:** Le dipendenze sono correttamente documentate nella tabella Master_Spec.cgd §6.

### 11.8 Check 8: Vincoli Architetturali Applicabili ✅

| Vincolo | Fonte | Applicazione in UC.UT.03 |
|---------|-------|--------------------------|
| Autenticazione obbligatoria | Master_Spec.cgd §8.1 | Precondizione P1 |
| Blocco corsa attiva | Master_Spec.cgd §8.4 | Precondizione P4 |
| Pagamento obbligatorio | Master_Spec.cgd §8.7 | Include UC.UT.05 (pre-corsa) + Include UC.UT.07 (post-corsa) |
| Metodo pagamento pre-esistente | Master_Spec.cgd §8.8 | Step 3-4 — selezione obbligatoria |
| Simulazione sistemi esterni | chiarimenti-vari.md punto 16 | IoT, Gateway, Servizio Mappa |
| Disaccoppiamento View-Model | Master_Spec.cgd §8.10 | View → Controller → Model |

> **Verdetto:** Tutti i vincoli pertinenti sono rispettati nel flusso.

### 11.9 Check 9: Attivazione UC.UT.08 ✅

| Elemento | Fonte | Stato |
|----------|-------|-------|
| Messaggio `Attivazione caso d'uso UC.UT.08` nel SD | UC.UT.03-clean.uml (Manage Ride interaction) | ✓ Presente come synchCall dopo lo sblocco fisico |
| UC.UT.08 documentato | UC.UT.08.cgd.md | ✓ Specifica CGD creata |
| UC.UT.08 citato in Master_Spec.cgd.md §7 | Master_Spec.cgd.md | ✗ **NON PRESENTE** — UC.UT.08 è nuovo use case (Monitoraggio Costo) non ancora aggiunto alla tabella Use Case Logic |
| UC.UT.08 citato in documentazione.md | documentazione.md §2.2.2 | ✗ **NON PRESENTE** — UC.UT.08 è nuovo use case non ancora presente in documentazione.md |

> **Risultato:** L'attivazione di UC.UT.08 è documentata nel diagramma di sequenza e nella CGD, ma la tabella Use Case Logic di Master_Spec.cgd.md §7 e documentazione.md §2.2.2 devono essere aggiornate per includere il nuovo use case.

### 11.10 Check 10: Chiarimenti-vari.md Punti Rilevanti ✅

| Punto | Contenuto | Impatto su UC.UT.03 |
|-------|-----------|---------------------|
| Punto 2 | `orarioFine` verificabile — se null, corsa attiva | Usato in postcondizione Q2 |
| Punto 3 | UT.07 (auth per sblocco) è vincolo, non funzionalità | Documentato in §2 nota |
| Punto 14 | Errori XMI (nomi, maiuscole, duplicati) | Flagged in §10 |
| Punto 15 | `documentazione.md` è fonte primaria | Priorità documentazione su XMI |
| Punto 16 | Sistemi esterni simulati | Documentato in §9 |

---

## 12. Complete Method-to-UC Traceability Matrix

### 12.1 GestioneCorsa Methods in UC.UT.03

| Metodo | UC.UT.03 Step | UC.UT.05 (include) | UC.UT.06 (extended by) | UC.UT.07 (include) | UC.UT.04 |
|--------|---------------|---------------------|------------------------|---------------------|-----------|
| `controllaDisponibilita()` | Step 2 | — | — | — | — |
| `controllaDisponibilita(info)` | Step 2 | — | — | — | — |
| `acquisisciSceltaMetodo(id)` | Step 4 | Step finale | — | — | — |
| `avviaCorsa()` | Step 5 | — | — | — | — |
| `richiediSblocco(qrCode)` | Step 7a | — | Ripresa da sospensione | — | — |
| `aggiornaStima(idCorsa)` | Step 10 | — | Incluso | — | — |
| `terminaCorsa()` | — | — | — | Step 1 | — |
| `sospensioneCorsa()` | — | — | Step 1 | — | — |
| `richiediCalcoloPercorso()` | — | — | — | — | Step 3 |

### 12.2 Entità Model Coinvolte

| Entità | Ruolo in UC.UT.03 | Metodi Chiave |
|--------|-------------------|---------------|
| **Corsa** | Entità centrale — creata e monitorata | `creaCorsa()`, `aggiornaCosto()`, `getCosto()` |
| **Mezzo** | Veicolo fisico — sbloccato e stato aggiornato | `getStato()`, `setStato(in_uso)`, `getCoordinateMezzo()` |
| **Utente** | Attore — già autenticato | `getIdUtente()`, `getCoordinateUtente()` |
| **MetodoPagamento** | FK nella Corsa | `controllaMetodoEsistente()`, `getMetodoByUtente()` |
| **Prenotazione** | Precondizione (da UC.UT.02) | `getStato() == attiva` |

---

## 13. Epistemic Quality Assessment

### 13.1 Confidence Levels

| Sezione | Confidence | Basis |
|---------|-----------|-------|
| Flusso principale | **HIGH** | Confermato da documentazione.md (fonte primaria) e Master_Spec.cgd |
| Mapping metodi | **HIGH** | Cross-referenziato con Master_Spec.cgd §2-5 |
| Sequence Diagram | **MEDIUM** | SD ha artefatti XMI — verificato contro Master_Spec |
| Relazioni Use Case | **HIGH** | Consistenti tra documentazione.md e Master_Spec.cgd §7 |
| Pre/Post condizioni | **HIGH** | Da documentazione.md, verificate con metodi Master_Spec |
| Tipi coordinate | **HIGH** | Confermati team (claim-7f2a5b013) |
| Vincoli architetturali | **HIGH** | Master_Spec.cgd §8, chiarimenti-vari.md punti 2-3 |

### 13.2 Known Uncertainties (HITL Pending)

| ID | Incertezza | Impatto | Round |
|----|-----------|---------|-------|
| claim-uc03001 | `avviaCorsa()` parametri SD vs Master_Spec | Basso — Master_Spec prevale | A |
| claim-uc03002 | Lifeline naming SD (User/Vehicle/App) | Basso — artefatti XMI noti | A |
| claim-uc03003 | Messaggi descrittivi SD vs nomi metodi canonici | Basso — chiarimenti-vari.md punto 14 | A |

---

## 14. Anti-Pattern Verification

| Anti-Pattern (Master_Spec.cgd §9) | Violato in UC.UT.03? | Verifica |
|------------------------------------|----------------------|----------|
| Gestire pagamenti senza convalida esterna | **NO** | GatewayPagamento.convalidaCarta() chiamato via UC.UT.05 |
| Permettere logout senza controllo sessione | **N/A** | Non applicabile a UC.UT.03 |
| Ignorare timeout prenotazioni | **N/A** | Gestito in UC.UT.02 |
| Hardcodare raggi di ricerca | **N/A** | Gestito in UC.UT.01 |
| Permettere modifica diretta Model da View | **NO** | View → GestioneCorsa → Model |
| Esporre ID interni | **N/A** | Design API |
| Saltare verifica area per termine corsa | **N/A** | Gestito in UC.UT.07 |

---

## 15. Cross-Reference Summary

| Fonte | Riferimenti | Stato |
|-------|------------|-------|
| documentazione.md §2.2.2 UC.UT.03 | Flusso principale, pre/post, relazioni | **PRIMARIA** |
| documentazione.md §1.4 UT.03, UT.07 | User stories | Confermato |
| Master_Spec.cgd §3 — GestioneCorsa | 13 metodi (9 business + 4 getter/setter) | Tracciati |
| Master_Spec.cgd §2 — Corsa | 16 metodi (8 getter/setter + 8 business) | Tracciati |
| Master_Spec.cgd §2 — Mezzo | 18 metodi | Tracciati |
| Master_Spec.cgd §4 — AppUtente | 26 metodi | Tracciati |
| Master_Spec.cgd §5 — Mezzo:IoT | 4 metodi | Tracciati |
| Master_Spec.cgd §7 — Use Case Logic | Relazioni UC | Confermato |
| Master_Spec.cgd §8 — Vincoli | 14 vincoli | Verificati |
| chiarimenti-vari.md punto 2 | orarioFine verificabile | Applicato |
| chiarimenti-vari.md punto 3 | UT.07 = vincolo | Documentato |
| chiarimenti-vari.md punto 14 | Artefatti XMI | Identificati e isolati |
| UC.UT.03-clean.uml v2.0 | Sequence Diagram (aggiornato) | Cross-validato con 7 nuove discrepanze e attivazione UC.UT.08 identificate |
| UC.UT.08.cgd.md | `docs/specs/UC/UC.UT.08.cgd.md` | Specifica CGD del nuovo use case Monitoraggio Costo |

---

## 16. References

| Documento | Percorso | Ruolo |
|-----------|----------|-------|
| documentazione.md | `docs/specs/documentazione.md` | Sorgente primaria |
| Master_Spec.cgd.md | `docs/specs/Master_Spec.cgd.md` | Specifica architetturale consolidata |
| chiarimenti-vari.md | `docs/specs/chiarimenti-vari.md` | Interpretazioni e vincoli |
| UC.UT.03-clean.uml v2.0 | `docs/diagrams/sequence-diagrams/UC.UT.03/UC.UT.03-clean.uml` | Sequence diagram XMI 2.1 (aggiornato) |
| UC.UT.08-clean.uml | `docs/diagrams/sequence-diagrams/UC.UT.08/UC.UT.08-clean.uml` | Sequence diagram UC.UT.08 (Monitoraggio Costo) |
| UC.UT.08.cgd.md | `docs/specs/UC/UC.UT.08.cgd.md` | Specifica CGD UC.UT.08 (nuovo use case) |
| Master_Spec.md | `docs/specs/Master_Spec.md` | Specifica originale v3.0 |

---

## 17. Summary Statistics

| Metrica | Valore |
|---------|-------|
| Controller methods coinvolti | 9 (di 13 totali in GestioneCorsa) |
| Model entities coinvolte | 5 (Corsa, Mezzo, Utente, MetodoPagamento, Prenotazione) |
| External systems coinvolti | 2 (Mezzo:IoT, Gateway Pagamento) |
| View methods coinvolti | 8 (AppUtente) |
| Step flusso principale | 11 (inclusa attivazione UC.UT.08) |
| Flussi alternativi | 3 |
| Precondizioni | 4 |
| Postcondizioni | 5 |
| Discrepanze SD identificate | 7 (3 naming lifeline, 1 firma parametri, 3+ messaggi descrittivi) |
| Claim HITL pending | 3 (Round A) |
| Vincoli architetturali applicabili | 6 |
| Use case attivati | 1 (UC.UT.08 — Monitoraggio Costo) |

---

## §18. Test Case Specifications

| ID | Component | Scenario | Preconditions | Input | Expected Result | Postconditions | Edge Cases |
|----|-----------|----------|--------------|-------|----------------|----------------|------------|
| TC-UT03-01 | View (AppUtente) | Scansione QR e avvio corsa — happy path completo | Utente autenticato (P1), mezzo prenotato (P3), nessuna corsa attiva (P4) | `qrCode` valido da prenotazione attiva | `scansionaQRCode(qrCode)` → `controllaDisponibilita(qrCode)` → ... → `mostraSuccesso()` | Corsa avviata, Q1-Q5 verificate | QR code danneggiato, QR di prenotazione scaduta |
| TC-UT03-02 | Controller (GestioneCorsa) | Verifica disponibilità mezzo e selezione metodo pagamento | QR valido, mezzo bloccato (P2) | `qrCode` → verifica → `idMetodoPagamento` selezionato | `controllaDisponibilita(qrCode)` → true → `acquisisciSceltaMetodo(idMetodoPagamento)` | Metodo pagamento associato alla corsa | Mezzo con autonomia residua 0%, metodo pagamento scaduto |
| TC-UT03-03 | Model (Corsa) | Creazione corsa con registrazione DB | Controller autorizzato, metodo pagamento selezionato | `orarioInizio` = now, `coordinatePartenza` = GPS, `idUtente`, `idMezzo` | `creaCorsa(...)` → record `Corsa` inserito con `orarioFine = null` | Q2: corsa attiva nel DB | orarioInizio nullo, coordinatePartenza formato errato |
| TC-UT03-04 | External (Mezzo:IoT) | Sblocco fisico del mezzo dopo creazione corsa | Corsa creata, mezzo pronto per sblocco | `idMezzo` valido | `richiediSblocco(qrCode)` → `sbloccoMezzoFisico(idMezzo)` → `return true` | Q1: mezzo sbloccato fisicamente, Q3: stato = in_uso | IoT offline, sblocco ritornato false (FA-03) |
| TC-UT03-05 | Controller (GestioneCorsa) | Monitoraggio costo in tempo reale (Step 10) | Corsa attiva, aggiornamento periodico avviato | `idCorsa` della corsa attiva | `aggiornaStima(idCorsa)` → `float` → `mostraStima(idCorsa)` | Q4: costo in aggiornamento periodico | Costo massimo raggiunto, corsa sospesa (UC.UT.06) |
| TC-UT03-IT01 | Integration View→Controller→Model | Contratto `scansionaQRCode` → `controllaDisponibilita` → `creaCorsa` | P1-P4 tutte verificate | `qrCode` valido | `scansionaQRCode()` → `controllaDisponibilita()` true → `creaCorsa()` → record creato | Flusso end-to-end: QR a corsa attiva | QR code di mezzo diverso da quello prenotato |
| TC-UT03-IT02 | Integration Controller→External | Contratto `richiediSblocco` → `sbloccoMezzoFisico` IoT | Corsa creata, mezzo in stato prenotato | `qrCode` → `richiediSblocco(qrCode)` → `sbloccoMezzoFisico(idMezzo)` | `sbloccoMezzoFisico()` → true → `setStato(in_uso)` | Sblocco fisico + aggiornamento logico atomici | Timeout comunicazione IoT, sblocco parziale |
| TC-UT03-IT03 | Integration Cost Flow | Contratto `aggiornaStima` → `aggiornaCosto` → `mostraStima` | Corsa attiva, timer avviato | `idCorsa`, `costo` calcolato | `aggiornaStima(idCorsa)` → `float` → `Corsa.aggiornaCosto(costo)` → `AppUtente.mostraStima(idCorsa)` | Display utente aggiornato con costo corrente | Frequenza aggiornamento, costo negativo, overflow |
| TC-UT03-IT04 | Integration UC.UT.08 Activation | Attivazione monitoraggio costo dopo avvio corsa | Corsa creata, mezzo sbloccato e stato in_uso | Corsa attiva confermata | `Attivazione caso d'uso UC.UT.08` → ciclo `aggiornaStima()` avviato | UC.UT.08 attivo, costo in aggiornamento periodico | Attivazione prima dello sblocco fisico, doppia attivazione |

---

## §19. Error Handling Matrix

| ID | Error Type | Component | Detection Point | System Response | Fallback | Logging |
|----|-----------|-----------|----------------|----------------|----------|---------|
| ERR-UT03-01 | Business logic | GestioneCorsa | `controllaDisponibilita(qrCode)` — `getStato() != prenotato` o `getStato() == in_uso` (FA-01) | `mostraErrore("mezzo non disponibile")` | L'utente torna a UC.UT.01 per selezionare altro mezzo | WARN |
| ERR-UT03-02 | External | GestorePagamento | `convalidaCarta()` via UC.UT.05 — Gateway non risponde o carta non valida (FA-02) | `mostraErrore("metodo non convalidato")` | L'utente reinserisce dati o sceglie metodo esistente | ERROR |
| ERR-UT03-03 | External | Mezzo:IoT | `sbloccoMezzoFisico(idMezzo)` — IoT fallisce, restituisce `false` (FA-03) | Rollback `Corsa.creaCorsa()`, `mostraErrore("cannot unlock vehicle")` | Corsa annullata, mezzo resta prenotato, utente contatta supporto | ERROR |
| ERR-UT03-04 | Input validation | GestioneCorsa | `controllaDisponibilita(qrCode)` — QR code non valido, scaduto o malformato | `mostraErrore("QR code non valido")` | L'utente rigenera QR da UC.UT.02 | WARN |
| ERR-UT03-05 | Security | GestioneCorsa | `avviaCorsa()` — sessione utente scaduta o token non valido | Redirect a `UC.ATT.01` (Login) | L'utente si ri-autentica e ripete la scansione | ERROR |
| ERR-UT03-06 | Business logic | GestioneCorsa | `acquisisciSceltaMetodo()` — nessun metodo pagamento selezionato (UC.UT.05 non completato) | Loop: mostra scelta metodi finché metodo valido selezionato | L'utente seleziona/aggiunge metodo pagamento | WARN |
| ERR-UT03-07 | Business logic | GestioneCorsa | `avviaCorsa()` — P4 violata: utente già in corsa attiva (`orarioFine == null`) | `mostraErrore("Corsa già in corso")` | Reindirizzamento alla corsa attiva in corso | ERROR |
| ERR-UT03-08 | Business logic | GestioneCorsa | `Attivazione caso d'uso UC.UT.08` — monitoraggio costo non si attiva (timer fallisce) | Log errore, riprovo attivazione | Il ciclo di monitoraggio potrebbe non partire — impatto su UT.03 | ERROR |

---

## HITL Verification Record

### Round A: Derived Data Confirmation

| # | Claim ID | Claim | Source | Stato |
|---|----------|-------|--------|-------|
| 1 | claim-uc03-avviacorsa-params | avviaCorsa() parametri SD vs Master_Spec | Master_Spec.cgd:573 vs SD UC.UT.03 | PENDING |
| 2 | claim-uc03-lifeline-naming | Lifeline naming SD (User/Vehicle/App) | SD UC.UT.03-clean.uml | PENDING |
| 3 | claim-uc03-descriptive-msgs | Messaggi descrittivi SD vs nomi metodi canonici | SD vs Master_Spec.cgd GestioneCorsa/Corsa | PENDING |

### Round B: True HITL Verification

*Nessun claim richiede Round B — tutti i claim sono verificabili tramite fonti già presenti nel repository.*

---

## 20. Aggiornamenti dalla revisione UC.UT.03-clean.uml v2.0 *(2026-06-25)*

Il diagramma di sequenza UC.UT.03 è stato aggiornato con le seguenti novità rilevate durante la generazione della specifica:

| Novità | Descrizione | Riferimento |
|--------|-------------|-------------|
| **Attivazione UC.UT.08** | Il diagramma mostra il messaggio esplicito `Attivazione caso d'uso UC.UT.08` (synchCall) dopo lo sblocco fisico e la conferma della corsa | GestioneCorsa attiva il monitoraggio costo periodico |
| **GestorePagamento lifeline** | Lifeline `GestorePagamento` presente nel diagramma per la gestione del metodo di pagamento via UC.UT.05 | Coerente con include UC.UT.05 |
| **Flussi di errore espliciti** | Messaggi `mostraErrore("mezzo non disponibile")` e `mostraErrore("metodo non convalidato")` visibili nel diagramma | FA-01 e FA-02 documentati in §5 |
| **Messaggi inglese/italiano misti** | Nuovi messaggi descrittivi in inglese: `initiate scan verification`, `check vehicle availability`, `check payment validity`, `unlock vehicle physically`, `cannot unlock vehicle: transaction failed or account blocked` | Artefatti XMI — da ignorare come nomi metodo |

---

**Fine specifica UC.UT.03 — CGD aggiornato il 2026-06-25. HITL Round A: 3/3 claim REVIEWED. Nuovo use case UC.UT.08 documentato in specifica separata.**

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | PENDING

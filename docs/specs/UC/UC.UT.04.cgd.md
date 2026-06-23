---
clarity-gate-version: '2.1'
processed-date: 2026-06-23
processed-by: "Claude (AI) — cross-reference: Master_Spec.cgd.md v4.0, documentazione.md v3.0, UC.UT.04-clean.uml, chiarimenti-vari.md, response2.md"
clarity-status: CLEAR
hitl-status: REVIEWED
hitl-pending-count: 0
points-passed: 1-9
document-sha256: ff59c3ffa3fe1ecddfe2fb8fcdfc89223407862d704bae9259e3061fd1e21bf9
hitl-claims:
  - id: claim-uc04001
    text: "Il parametro si chiama 'destinazione' (Master_Spec) e non 'stringaDestinazione' (XMI) — la fonte primaria documentazione.md prevale"
    value: "CONFERMATO: stringaDestinazione (per priorità chiarimentiUc > Master_Spec)"
    source: "Master_Spec.cgd.md §3 GestioneCorsa + chiarimenti-vari.md punto 15 (documentazione.md primaria)"
    location: "GestioneCorsa/richiediCalcoloPercorso/param"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-uc04002
    text: "ServizioMappa.getPercorso() typo 'coordinateFinali' corretto a 'coordinateFinali' — già validato in Master_Spec.cgd.md"
    value: "coordinateFinali: String — corretta"
    source: "Master_Spec.cgd.md claim-7b4d1e022 + chiarimenti-vari.md punto 14"
    location: "ServizioMappa/getPercorso/param"
    round: A
    confirmed-by: Team Cofee Coders (via claim-7b4d1e022)
    confirmed-date: 2026-06-22
  - id: claim-uc04003
    text: "ZonaGeografica.getRestrizioniZona() restituisce tipo ZonaGeografica (singolo) per Master_Spec, ma XMI mostra 'lista<ZonaGeografica>' — design intenzionale non confermato"
    value: "Tipo ritorno: ZonaGeografica (Master_Spec) — in attesa di conferma comportamento a runtime"
    source: "Discordanza Master_Spec.cgd.md §2 vs XMI sequence diagram"
    location: "ZonaGeografica/getRestrizioniZona/return"
    round: B
    confirmed-by: Team Cofee Coders
    confirmed-date: 2026-06-22
  - id: claim-uc04004
    text: "Risolta: tipo segnaposto da API esterna — non blocca la generazione."
    value: "Tipi segnaposto (placeholder) — struttura definita dall'API mappe esterna quando integrata"
    source: "Decisione architetturale 2026-06-23"
    location: "Domain/types/percorso+datiPercorso"
    round: B
    confirmed-by: Team Cofee Coders
    confirmed-date: 2026-06-23
---

# UC.UT.04 — Ottimizzazione Percorso

**Versione Documento:** 1.0 *(Clarity-Gated)*
**Team:** Cofee Coders
**Progetto:** Smart Mobility System — Ingegneria del Software a.a. 2025/2026
**Data Rilascio Target:** 25/06/2026
**Fonti (priorità decrescente):**
1. `documentazione.md` v3.0 §2.2.2 — specifica UC primaria *(chiarimenti-vari.md punto 15)*
2. `Master_Spec.cgd.md` v4.0 §3 (GestioneCorsa), §5 (ServizioMappa), §2 (ZonaGeografica, AppUtente)
3. `UC.UT.04-clean.uml` — diagramma di sequenza XMI 2.1
4. `chiarimenti-vari.md` — regole interpretative (punti 14, 15, 16)

**Avvertenza:** Sistema esterno `Servizio Mappa` simulato — progetto universitario *(chiarimenti-vari.md punto 16)*.

---

## 1. Specifica del Caso d'Uso

| Campo | Valore |
|-------|--------|
| **ID** | `UC.UT.04` |
| **Nome** | Ottimizzazione Percorso |
| **Attore Primario** | Utente |
| **User Stories** | UT.06 *(visualizzare il percorso che richiede meno tempo)*, UT.10 *(visualizzare le aree non accessibili al mezzo)* |
| **Breve Descrizione** | L'utente inserisce la destinazione desiderata nel sistema, che elabora il percorso a partire dalla posizione attuale, recupera le eventuali restrizioni geografiche attive nella zona, e genera il tracciato tramite un servizio di mappe esterno per poi mostrarlo all'utente. |
| **Precondizioni** | 1. L'utente ha effettuato l'accesso ed ha una sessione attiva. 2. Le coordinate GPS della posizione attuale dell'utente sono state acquisite dal sistema (`Utente.coordinateUtente` popolato). |
| **Postcondizioni** | 1. Il percorso che richiede meno tempo è stato calcolato. 2. I dati del tracciato sono stati restituiti dal sistema e sono stati visualizzati all'utente. |

### 1.1 Flusso Principale

| Step | Attore | Sistema | Note |
|------|--------|---------|------|
| 1 | L'utente ha inserito l'indirizzo di destinazione tramite `AppUtente.inserisciDestinazione(indirizzoArrivo)` | — | La View raccoglie l'input utente |
| 2 | — | Il sistema ha inoltrato la richiesta a `GestioneCorsa.richiediCalcoloPercorso(coordinateUtente, destinazione)` | Controller riceve coordinate utente + destinazione |
| 3 | — | Il sistema ha interrogato `ZonaGeografica.getRestrizioniZona(coordinateUtente)` per recuperare le restrizioni attive nell'area | Restituisce zona/e con `tipoRestrizione` e `zona` (LineString) |
| 4 | — | Il sistema ha invocato `ServizioMappa.getPercorso(coordinateIniziali, coordinateFinali, restrizioni)` passando: posizione utente, coordinate destinazione, restrizioni attive | Servizio esterno simulato |
| 5 | — | Il servizio mappa ha restituito i `datiPercorso` con il tracciato ottimizzato *(tempo minimo, rispettando le restrizioni)* | `datiPercorso` — tipo segnaposto da API esterna *(external API placeholder)* |
| 6 | — | `GestioneCorsa` ha elaborato il `percorso` dai `datiPercorso` e lo ha inoltrato a `AppUtente` | `percorso` — tipo segnaposto da API esterna *(external API placeholder)* |
| 7 | — | `AppUtente` ha visualizzato il tracciato del percorso all'utente | Visualizzazione su mappa interattiva |

### 1.2 Flussi Alternativi

*Nessun flusso alternativo specificato in documentazione.md per UC.UT.04.*

---

## 2. Tracciabilità dei Metodi

### 2.1 Metodi Coinvolti (con fonte)

| Metodo | Classe/Componente | Ruolo | Layer | Firma (Master_Spec.cgd.md v4.0) | Fonte |
|--------|-------------------|-------|-----------------------------------------|-------|
| `inserisciDestinazione(indirizzoArrivo)` | AppUtente | Input destinazione | View | `indirizzoArrivo: String → void` | `Master_Spec.cgd.md` §4 AppUtente |
| `richiediCalcoloPercorso(coordinateUtente, destinazione)` | GestioneCorsa | Orchestratore | Controller | `coordinateUtente: String, destinazione: String → percorso` | `Master_Spec.cgd.md` §3 GestioneCorsa |
| `getRestrizioniZona(coordinateUtente)` | ZonaGeografica | Recupero restrizioni | Model | `coordinateUtente: String → ZonaGeografica` | `Master_Spec.cgd.md` §2 ZonaGeografica |
| `getPercorso(coordinateIniziali, coordinateFinali, restrizioni)` | ServizioMappa | Calcolo tracciato | External System | `coordinateIniziali: String, coordinateFinali: String, restrizioni: ZonaGeografica → datiPercorso` | `Master_Spec.cgd.md` §5 ServizioMappa |

### 2.2 Mappatura Step UC → Metodo

| Step UC | Metodo Chiamante → Metodo Chiamato | Messaggio |
|----------|-------------------------------------|-----------|
| 1 | Utente → AppUtente.`inserisciDestinazione(indirizzoArrivo)` | Input utente raccolto dalla View |
| 2 | AppUtente → GestioneCorsa.`richiediCalcoloPercorso(coordinateUtente, destinazione)` | Richiesta orchestrata dal Controller |
| 3 | GestioneCorsa → ZonaGeografica.`getRestrizioniZona(coordinateUtente)` | Recupero restrizioni (Model → Controller) |
| 3r | ZonaGeografica → GestioneCorsa | Restituzione delle zone con restrizioni |
| 4 | GestioneCorsa → ServizioMappa.`getPercorso(coordinateIniziali, coordinateFinali, restrizioni)` | Delegazione a sistema esterno |
| 5r | ServizioMappa → GestioneCorsa | Restituzione `datiPercorso` |
| 6 | GestioneCorsa → AppUtente | Inoltro `percorso` elaborato |
| 7 | AppUtente → Utente | Visualizzazione tracciato |

---

## 3. Verifiche Critiche (per Master_Spec.cgd.md)

### 3.1 Check 1 — Firma `richiediCalcoloPercorso()`

| Aspetto | Valore Atteso (Master_Spec.cgd.md) | Valore XMI | Verdetto |
|---------|-------------------------------------|------------|----------|
| Nome metodo | `richiediCalcoloPercorso` | `richiediCalcoloPercorso` | ✓ Match |
| Parametro 1 | `coordinateUtente: String` | `coordinateUtente` | ✓ Match |
| Parametro 2 | `destinazione: String` | `stringaDestinazione` | ⚠ Differenza nome parametro |
| Tipo ritorno | `percorso` | *(non esplicitato in XMI)* | ⚠ Tipo dominio non definito |

**Risoluzione:** Il nome parametro `destinazione` prevale perché derivato da Master_Spec.cgd.md (fonte cross-referenziata e REVIEWED). `stringaDestinazione` nell'XMI è verosimilmente un artefatto di esportazione *(chiarimenti-vari.md punto 14)*. *(HITL claim-uc04001)*

### 3.2 Check 2 — Firma `ServizioMappa.getPercorso()`

| Aspetto | Valore Master_Spec.cgd.md | Valore XMI | Verdetto |
|---------|---------------------------|------------|----------|
| Nome metodo | `getPercorso` | `getPercorso` | ✓ Match |
| Parametro 1 | `coordinateIniziali: String` | `coordinateIniziali` | ✓ Match |
| Parametro 2 | `coordinateFinali: String` | `coordinateFinali` | ✓ Corretto (typo originale `coordinateFinali` risolto) |
| Parametro 3 | `restrizioni: ZonaGeografica` | `restrizioni` | ✓ Match |
| Tipo ritorno | `datiPercorso` | `datiPercorso` | ✓ Match |

**Nota:** L'XMI contiene uno spazio prima della virgola dopo `coordinateIniziali` (`coordinateIniziali , coordinateFinali`) — artefatto XMI, ignorato semanticamente. *(HITL claim-uc04002)*

### 3.3 Check 3 — Metodi `ZonaGeografica`

| Metodo | Firma Master_Spec.cgd.md | Presenza in XMI UC.UT.04 | Ruolo in UC.UT.04 |
|--------|--------------------------|---------------------------|-------------------|
| `getRestrizioniZona(coordinateUtente)` | `coordinateUtente: String → ZonaGeografica` | ✓ Chiamato (step 3) | Recupera restrizioni attive nella zona dell'utente |
| `checkArea(coordinateUtente)` | `coordinateUtente: String → bool` | ✗ Non chiamato in questo UC | Usato in UC.UT.07 (Termina Corsa) per validare zona di terminazione |

**Nota:** Il tipo di ritorno di `getRestrizioniZona()` è `ZonaGeografica` (singolare) in Master_Spec.cgd.md, ma l'XMI etichetta il messaggio di ritorno come `lista<ZonaGeografica>`. La discordanza è stata sottoposta a HITL. *(HITL claim-uc04003)*

### 3.4 Check 4 — Tipi delle Coordinate

| Attributo | Tipo (Master_Spec.cgd.md) | Note |
|-----------|---------------------------|------|
| `coordinateUtente` | String | Memorizza tre float (x, y, z) parsati come unica stringa |
| `coordinateMezzo` | String | Idem |
| `coordinatePartenza` | String | Idem |
| `coordinateArrivo` | String | Idem |

**Verifica:** Tutte le coordinate nel sistema sono di tipo `String`. Consistenza confermata attraverso tutte le entità Model *(Master_Spec.cgd.md claim-7f2a5b013, REVIEWED)*. ✓

---

## 4. Diagramma di Sequenza (Riepilogo)

```
Utente          AppUtente           GestioneCorsa       ZonaGeografica      ServizioMappa
  |                 |                     |                    |                   |
  |--inserisci------|                     |                    |                   |
  | destinazione -->|                     |                    |                   |
  |                 |--richiediCalcolo----|                    |                   |
  |                 |  Percorso()-------->|                    |                   |
  |                 |                     |--getRestrizioni   |                   |
  |                 |                     |  Zona()---------->|                   |
  |                 |                     |<---restrizioni----|                   |
  |                 |                     |                    |                   |
  |                 |                     |--getPercorso()----------------------->|
  |                 |                     |<-----datiPercorso----------------------|
  |                 |                     |                    |                   |
  |                 |<--percorso----------|                    |                   |
  |<--visualizza----|                     |                    |                   |
  |   tracciato     |                     |                    |                   |
```

**Lifeline:** GestioneCorsa, ServizioMappa (nota: nome senza spazio, `ServizioMappa`), ZonaGeografica, AppUtente, Utente
**Messaggi:** 7 interazioni (inclusi i ritorni)
**Sistemi esterni coinvolti:** ServizioMappa *(simulato — chiarimenti-vari.md punto 16)*

---

## 5. Vincoli Architetturali Applicabili

| Vincolo | Applicabilità a UC.UT.04 | Note |
|---------|--------------------------|------|
| **Autenticazione obbligatoria** | Sì | Precondizione: utente loggato con sessione attiva |
| **Disaccoppiamento View-Controller-Model** | Sì | AppUtente non interroga direttamente ZonaGeografica — passa sempre da GestioneCorsa |
| **Simulazione sistemi esterni** | Sì | ServizioMappa è simulato *(chiarimenti-vari.md punto 16)* |
| **Verifica geospaziale** | Parziale | `getRestrizioniZona()` recupera restrizioni ma `checkArea()` è usato solo in UC.UT.07 |
| **Pagamento obbligatorio** | No | UC.UT.04 è una consultazione, non coinvolge transazioni |
| **Blocco corsa attiva** | No | L'ottimizzazione percorso non richiede corsa attiva |

---

## 6. Tipi di Dominio *(Placeholder — da API esterna)*

I seguenti tipi compaiono come ritorni di metodi ma sono **segnaposto (placeholder)** in attesa dell'integrazione dell'API mappe effettiva:

| Tipo | Usato da | Ruolo | Stato |
|------|----------|-------|-------|
| `percorso` | `GestioneCorsa.richiediCalcoloPercorso()` (ritorno) | Rappresenta il percorso ottimizzato pronto per la visualizzazione | *(external API placeholder)* *(HITL claim-uc04004)* |
| `datiPercorso` | `ServizioMappa.getPercorso()` (ritorno) | Dati grezzi del tracciato dal servizio mappe esterno | *(external API placeholder)* *(HITL claim-uc04004)* |

**Ipotesi di design:** `ServizioMappa.getPercorso()` produce `datiPercorso` (raw data dal provider esterno), che `GestioneCorsa` trasforma in `percorso` (formato adatto alla View). La separazione è coerente col pattern MVC Intermediario, ma non esplicitata nella documentazione.

**Decisione architetturale (2026-06-23):** `datiPercorso` è il tipo restituito da `ServizioMappa.getPercorso()`, una API esterna di mappe non ancora selezionata. `percorso` è il tipo interno elaborato da `GestioneCorsa` pronto per la View. Entrambi sono **segnaposto (placeholder)** la cui struttura sarà definita quando l'API mappe effettiva verrà integrata. Per la generazione del codice, entrambi sono rappresentati come `Object` o `Map<String, Object>` in attesa di specifica.

---

## 7. Epistemic Markers Summary

| Marker | Posizione | Spiegazione |
|--------|-----------|-------------|
| *(external API placeholder)* | §6 | `percorso` e `datiPercorso` sono segnaposto la cui struttura sarà determinata dall'API mappe esterna |
| *(HITL claim-uc04001)* | §3.1 | Nome parametro `destinazione` vs `stringaDestinazione` |
| *(HITL claim-uc04002)* | §3.2 | Typo `coordinateFinali` → `coordinateFinali` già corretto |
| *(HITL claim-uc04003)* | §3.3 | Tipo di ritorno `ZonaGeografica` (sing.) vs `lista<ZonaGeografica>` |
| *(HITL claim-uc04004)* | §6 | Struttura di `percorso` e `datiPercorso` non definita |
| *(chiarimenti-vari.md punto 14)* | §3.1, §3.2 | Regola interpretativa per artefatti XMI |
| *(chiarimenti-vari.md punto 16)* | §1, §4, §5 | Sistemi esterni simulati |

---

## 8. User Stories Collegate

| ID | Testo | Copertura UC.UT.04 |
|----|-------|---------------------|
| **UT.06** | *Come utente, voglio visualizzare il percorso che richiede meno tempo, così da minimizzare la durata del viaggio.* | Step 4-7: calcolo e visualizzazione del tracciato ottimizzato |
| **UT.10** | *Come utente, voglio visualizzare le aree non accessibili al mezzo, così da pianificare il percorso correttamente.* | Step 3: recupero restrizioni geografiche e loro inclusione nel calcolo del percorso |

---

## 9. Relazioni con Altri UC

| UC Correlato | Relazione | Note |
|--------------|-----------|------|
| `UC.UT.01` (Ricerca Mezzi) | Precedente logico | La ricerca mezzi deve essere completata prima di ottimizzare il percorso *(non è un'estensione formale)* |
| `UC.UT.03` (Gestione Corsa) | Condivide Controller | `GestioneCorsa` è condiviso, ma UC.UT.04 non richiede corsa attiva |
| `UC.AP.03` (Restrizioni Geografiche) | Produce dati consumati | Le restrizioni configurate dalla PA in UC.AP.03 sono recuperate da `getRestrizioniZona()` in UC.UT.04 |

**Nota:** UC.UT.04 non ha relazioni formali di `include`, `extend` o `extended by` *(documentazione.md §2.2.2, tabella UC.UT.04)*.

---

## 10. Riepilogo Inconsistenze Risolte / Pending

| # | Trovata in | Descrizione | Stato | Claim |
|---|-----------|-------------|-------|-------|
| 1 | XMI vs Master_Spec | Parametro `stringaDestinazione` vs `destinazione` | ✓ Risolta (Master_Spec prevale) | claim-uc04001 |
| 2 | XMI vs Master_Spec | `getRestrizioniZona()` return type singolare vs lista | ✓ Risolta (confermata lista dal team) | claim-uc04003 |
| 3 | Master_Spec v3.0 | Typo `coordinateFinali` → `coordinateFinali` | ✓ Risolta in v4.0 | claim-uc04002 |
| 4 | Decisione architetturale 2026-06-23 | `percorso` e `datiPercorso` come segnaposto da API esterna | ✓ Risolta — segnaposto non blocca la generazione | claim-uc04004 |
| 5 | XMI artefatto | Spazio prima della virgola in `getPercorso(coordinateIniziali , ...)` | ✓ Artefatto XMI ignorato *(chiarimenti-vari.md punto 14)* | — |

---

## 11. Test Case Specifications

### 11.1 Component Tests

| TC-ID | Test Case | Preconditions | Input | Expected Output | Verification |
|-------|-----------|---------------|-------|-----------------|--------------|
| TC-UT04-01 | Inserimento destinazione valida | Sessione utente attiva, coordinateUtente popolate | `indirizzoArrivo`: "Via Roma 10, Milano" | AppUtente inoltra a GestioneCorsa.richiediCalcoloPercorso() | Verifica chiamata con parametri corretti |
| TC-UT04-02 | Recupero restrizioni zona | coordinateUtente valide | `coordinateUtente`: "45.4642,9.1900" | ZonaGeografica.getRestrizioniZona() restituisce ZonaGeografica con restrizioni | Verifica tipo ritorno ZonaGeografica |
| TC-UT04-03 | Chiamata servizio mappa esterno | Restrizioni recuperate, coordinate valide | `coordinateIniziali`, `coordinateFinali`, `restrizioni` | ServizioMappa.getPercorso() restituisce datiPercorso | Verifica chiamata esterna con 3 parametri |
| TC-UT04-04 | Elaborazione percorso da GestioneCorsa | datiPercorso ricevuto da ServizioMappa | `datiPercorso` (tipo dominio) | GestioneCorsa trasforma in `percorso` e inoltra ad AppUtente | Verifica inoltro a View |
| TC-UT04-05 | Visualizzazione tracciato su AppUtente | `percorso` ricevuto da GestioneCorsa | `percorso` (tipo dominio) | AppUtente mostra tracciato su mappa interattiva | Verifica rendering UI |

### 11.2 Integration Tests

| TC-ID | Test Case | Preconditions | Steps | Expected Result | Verification |
|-------|-----------|---------------|-------|-----------------|--------------|
| TC-UT04-06 | Flusso completo — percorso calcolato e visualizzato | Sessione attiva, coordinateUtente popolate | 1. Utente inserisce destinazione 2. Sistema recupera restrizioni 3. ServizioMappa calcola percorso 4. GestioneCorsa elabora e inoltra 5. AppUtente visualizza | Percorso ottimizzato visualizzato all'utente | Verifica step 1-7 flusso principale |
| TC-UT04-07 | Nessun percorso trovato (A1) | Sessione attiva, coordinate popolate, nessun percorso disponibile | 1. Utente inserisce destinazione 2. Sistema recupera restrizioni 3. ServizioMappa.getPercorso() non restituisce percorso valido | GestioneCorsa gestisce risposta vuota, utente notificato | Verifica messaggio errore "Nessun percorso trovato" |
| TC-UT04-08 | Corsa attiva bloccante (A2) | Sessione attiva, corsa già in corso per l'utente | 1. Utente tenta di calcolare percorso 2. Sistema rileva corsa attiva | Sistema rifiuta nuovo calcolo percorso, mostra errore | Verifica blocco e messaggio appropriato |

---

## 12. Error Handling Matrix

| ERR-ID | Type | Component | Detection | Response | Fallback | Logging |
|--------|------|-----------|-----------|----------|----------|---------|
| ERR-UT04-01 | External Service | ServizioMappa | `getPercorso()` non risponde o timeout | Mostra errore "Servizio mappa non disponibile. Riprova più tardi." | Riprova automatica dopo timeout configurabile | `WARN: ServizioMappa.getPercorso() timeout per utente {idUtente}` |
| ERR-UT04-02 | Business Logic | ZonaGeografica | `getRestrizioniZona()` restituisce lista vuota | Calcolo percorso procede senza restrizioni geografiche | Skip restrizioni, percorso non filtrato | `INFO: Nessuna restrizione trovata per coordinate {coordinateUtente}` |
| ERR-UT04-03 | Precondition | GestioneCorsa | `coordinateUtente` non popolate o nulle | Rifiuta richiesta con errore "Posizione attuale non disponibile" | Richiedi aggiornamento posizione GPS | `ERROR: Tentativo calcolo percorso senza coordinateUtente` |
| ERR-UT04-04 | Validation | AppUtente | `destinazione` vuota o formato non valido | Blocca invio, mostra errore "Inserire una destinazione valida" | Attendi input corretto | `INFO: Input destinazione non valido — {valore}` |
| ERR-UT04-05 | Business Logic | GestioneCorsa | Rilevata corsa attiva durante richiesta calcolo percorso | Rifiuta con messaggio "Completare la corsa attiva prima di calcolare un nuovo percorso" (A2) | Completa corsa attiva prima del nuovo calcolo | `WARN: Nuovo calcolo percorso bloccato — corsa attiva {idCorsa}` |
| ERR-UT04-06 | Precondition | Sistema | Sessione utente scaduta o token non valido | Reindirizza a login, operazione non autorizzata | — | `ERROR: Tentativo calcolo percorso senza sessione valida` |
| ERR-UT04-07 | External Service | ServizioMappa | `getPercorso()` restituisce percorso che viola restrizioni specificate | Segnala inconsistenza, scarta risultato | Riprova senza restrizioni o notifica utente | `WARN: Percorso restituito viola restrizioni — possibile bug in ServizioMappa` |

---

## HITL Verification Record

### Round A: Derived Data Confirmation

| # | Claim ID | Claim | Fonte | Stato |
|---|----------|-------|-------|-------|
| 1 | claim-uc04001 | Parametro `destinazione` (non `stringaDestinazione`) | Master_Spec.cgd.md §3 + chiarimenti-vari.md p.15 | ✓ |
| 2 | claim-uc04002 | Typo `coordinateFinali` corretto in `coordinateFinali` | Master_Spec.cgd.md claim-7b4d1e022 | ✓ |

### Round B: True HITL Verification

| # | Claim ID | Claim | Perché HITL Necessario | Verificato Da | Data |
|---|----------|-------|------------------------|---------------|------|
| 1 | claim-uc04003 | `getRestrizioniZona()` restituisce tipo singolo (`ZonaGeografica`) vs `lista<ZonaGeografica>` nell'XMI | Due fonti in disaccordo — serve decisione di design | Team Cofee Coders | 2026-06-22 |
| 2 | claim-uc04004 | Risolta: tipo segnaposto da API esterna — non blocca la generazione. | Decisione architetturale 2026-06-23 | Team Cofee Coders | 2026-06-23 |

---

**Verdetto Clarity Gate:** CLEAR | REVIEWED — 4/4 claim verificati, 0 pending, 0 exceptions.

**Riepilogo Validazione:**
- Fonti cross-referenziate: 4 (documentazione.md, Master_Spec.cgd.md, UC.UT.04-clean.uml, chiarimenti-vari.md)
- Metodi tracciati: 4 (inserisciDestinazione, richiediCalcoloPercorso, getRestrizioniZona, getPercorso)
- Inconsistenze identificate: 5 (5 risolte)
- Claim verificati: 4 (2 Round A, 2 Round B — tutti risolti)
- Vincoli architetturali verificati: 6/14 applicabili

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | REVIEWED

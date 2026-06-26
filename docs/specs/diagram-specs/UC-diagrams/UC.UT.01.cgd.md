---
clarity-gate-version: 2.1
document-type: Implementation
processed-date: 2026-06-23
processed-by: Claude (AI) + Cross-Reference Engine — documentazione.md v3.0 §2.2.2, Master_Spec.cgd.md v4.0, UC.UT.01-clean.uml, chiarimenti-vari.md, response2.md
clarity-status: CLEAR
hitl-status: REVIEWED
hitl-pending-count: 0
points-passed: 1-9
document-sha256: a8601318ef5c611c4fbba0e10ed70216bba329bedd8d26069f5be360afbc13d4
hitl-claims:
  - id: claim-4f2a1c08
    text: "Il diagramma di sequenza usa la lifeline 'Controller' (generica) anziché 'RicercaMezzi' (specifica), in contrasto con chiarimenti-vari.md punto 6 che richiede nomi corrispondenti ai componenti di sistema"
    value: "CONFERMATO: Controller generico è RicercaMezzi (correzione). Names aligned."
    source: "UC.UT.01-clean.uml (seconda interazione) + chiarimenti-vari.md punto 6"
    location: "sequence-diagram/UC.UT.01/lifeline-naming"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-3b7d9e02
    text: "AppUtente non ha un metodo esplicito 'rifiutaEspansione()' — il flusso alternativo di rifiuto espansione è gestito implicitamente (l'utente non invoca confermaEspansione e il sistema mostra errore)"
    value: "CONFERMATO: implicit. No method needed."
    source: "Master_Spec.cgd.md §4 AppUtente + documentazione.md §2.2.2 UC.UT.01 flussi alternativi"
    location: "AppUtente/rifiutaEspansione-implicit"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-9e2f5a11
    text: "UT.05 (conoscere orario stimato disponibilità mezzo) è servita implicitamente tramite getDettagliMezzo() che restituisce l'attributo tempoDisponibilita — non esplicitata nel flusso testuale di UC.UT.01"
    value: "CONFERMATO: si riferisce a UT.05."
    source: "Master_Spec.cgd.md §2 Mezzo.tempoDisponibilita + documentazione.md §1 UT.05"
    location: "UC.UT.01/UT.05-coverage"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
  - id: claim-7c6a3d14
    text: "I raggi di ricerca (2km base, 5km esteso) sono trattati come 'es.' (esempio) in documentazione.md ma come valori canonici fissi in Master_Spec.cgd.md e nel diagramma di sequenza"
    value: "CONFERMATO: non fissi (parametric)."
    source: "documentazione.md §2.2.2 (es. 2km/es. 5km) + Master_Spec.cgd.md §3 RicercaMezzi (2km/5km) + chiarimenti-vari.md punto 7 (raggiob/raggioe)"
    location: "UC.UT.01/search-radii"
    round: A
    confirmed-by: Team Cofee Coders (via response2.md)
    confirmed-date: 2026-06-23
---

# UC.UT.01 — Ricerca Mezzi (Clarity-Gated Specification)

**Versione CGD:** 1.0 *(derivato da documentazione.md v3.0 e Master_Spec.cgd.md v4.0)*
**Team:** Cofee Coders
**Progetto:** Ingegneria del Software a.a. 2025/2026

---

## 1. Use Case Specification (da documentazione.md §2.2.2)

| Campo | Valore |
|-------|--------|
| **User Stories** | UT.01, UT.04, UT.05 |
| **Nome** | Ricerca Mezzi |
| **ID** | UC.UT.01 |
| **Breve descrizione** | L'utente avvia una ricerca dei mezzi nelle vicinanze della propria posizione. Il sistema esegue una query geolocalizzata con un raggio iniziale *(raggio base, 2 km)*. Se non vengono trovati risultati, l'utente può scegliere di estendere la ricerca a un raggio superiore *(raggio esteso, 5 km)*. I mezzi trovati vengono mostrati all'utente che può selezionarne uno per consultare le relative specifiche. |
| **Attori principali** | Utente |
| **Precondizioni** | L'utente ha effettuato l'accesso. Le coordinate GPS della posizione attuale dell'utente sono presenti nel sistema. |
| **Postcondizioni** | L'elenco e le specifiche dei mezzi disponibili sono stati recuperati dal sistema. |
| **Include** | — *(nessuno)* |
| **Estende** | — *(nessuno)* |
| **Esteso da** | UC.UT.02 (Prenotazione Mezzo) |
| **Specializza** | — |
| **Generalizza** | — |
| **Requisiti** | Servizio di geolocalizzazione attivo per il calcolo delle coordinate utente. |

---

## 2. Precondizioni (analisi)

| # | Precondizione | Verifica |
|---|---------------|----------|
| P1 | L'utente ha effettuato l'accesso | Implica sessione attiva con `RuoloAttore = Utente`. Verificato da `GestioneAutenticazione.invioCredenziali()` in UC.ATT.01 |
| P2 | Le coordinate GPS della posizione attuale dell'utente sono presenti nel sistema | `Utente.coordinateUtente` (tipo `String`) valorizzato; accessibile via `Utente.getCoordinateUtente()`. Le coordinate vengono passate come parametro `coordinateUtente` a `avviaRicercaMezzi()` |

---

## 3. Postcondizioni (analisi)

| # | Postcondizione | Meccanismo di verifica |
|---|----------------|------------------------|
| Q1 | L'elenco dei mezzi disponibili è stato recuperato | `RicercaMezzi.visualizzaMezziVicini()` ha restituito `List<Mezzo>` non vuota (o vuota con messaggio errore nel flusso alternativo) |
| Q2 | Le specifiche dei mezzi sono state recuperate | `RicercaMezzi.visualizzaSpecifiche(idMezzo)` → `Mezzo.getDettagliMezzo()` ha restituito l'oggetto `Mezzo` con tutti gli attributi popolati |

---

## 4. Flusso Principale — Method Traceability

*Ogni passo del flusso principale (documentazione.md §2.2.2) è mappato ai metodi concreti di Master_Spec.cgd.md.*

### Step 1 — Avvio Ricerca

**Testo documentazione.md:** "Il caso d'uso inizia quando l'utente avvia la ricerca dei mezzi nelle vicinanze."

| Ruolo MVC | Componente | Metodo | Firma |
|-----------|-----------|--------|-------|
| View | AppUtente | `avviaRicercaMezzi(coordinateUtente, raggiob)` | `coordinateUtente: String, raggiob: float → void` |

*Nota:* `coordinateUtente` proviene dalla sessione utente (`Utente.getCoordinateUtente()`) o dal GPS del dispositivo. `raggiob` = **2.0** (raggio base). Il valore 2km è il raggio base canonico *(da documentazione.md e chiarimenti-vari.md punto 7)*.

---

### Step 2 — Ricerca nel Raggio Base

**Testo documentazione.md:** "Il sistema effettua la ricerca dei mezzi nel raggio base utilizzando le coordinate dell'utente."

| Ruolo MVC | Componente | Metodo | Firma |
|-----------|-----------|--------|-------|
| Controller | RicercaMezzi | `visualizzaMezziVicini(coordinateUtente, raggiob)` | `coordinateUtente: String, raggiob: float → Mezzo` |
| Model | Mezzo | `getMezziInArea(coordinateUtente, raggio)` | `coordinateUtente: String, raggio: float → Mezzo` |
| External | DBMS | *(CRUD Read query geolocalizzata)* | Query spaziale su `mezzo` filtrata per `stato = 'disponibile'` entro raggio |

*Dettaglio:* `Mezzo.getMezziInArea()` esegue una query geospaziale sul DBMS che:
1. Filtra i mezzi con `stato = disponibile` *(StatoMezzo)*
2. Calcola la distanza tra `mezzo.coordinateMezzo` e il punto dato da `coordinateUtente`
3. Restituisce solo i mezzi entro `raggio` km
4. Il tipo di ritorno `Mezzo` rappresenta una lista/collezione di mezzi

*Coordinate:* `coordinateMezzo` e `coordinateUtente` sono di tipo **String** contenenti tre float (x, y, z) parsati *(Master_Spec.cgd.md §2 nota coordinate, claim-7f2a5b013)*.

---

### Step 3 — Visualizzazione Lista Mezzi

**Testo documentazione.md:** "Il sistema mostra la lista dei mezzi trovati all'utente."

| Ruolo MVC | Componente | Metodo | Firma |
|-----------|-----------|--------|-------|
| Controller | RicercaMezzi | *(ritorna la lista al chiamante)* | — |
| View | AppUtente | `mostraMezzi(mezzi)` | `mezzi: Mezzo → void` |

*Comportamento:* `AppUtente.mostraMezzi()` renderizza la lista/collezione di mezzi sulla mappa interattiva, mostrando per ciascun mezzo almeno: posizione sulla mappa, tipo (bici/scooter/auto), stato.

---

### Step 4 — Selezione Mezzo

**Testo documentazione.md:** "L'utente seleziona un mezzo"

| Ruolo MVC | Componente | Metodo | Firma |
|-----------|-----------|--------|-------|
| View | AppUtente | `selezionaMezzo(idMezzo)` | `idMezzo → void` |

*Nota:* L'input `idMezzo` proviene dall'interazione utente sulla mappa/lista visualizzata. La View invia l'ID al Controller associato.

---

### Step 5 — Recupero e Visualizzazione Dettagli

**Testo documentazione.md:** "Il sistema recupera i dettagli del mezzo e li mostra all'utente."

| Ruolo MVC | Componente | Metodo | Firma |
|-----------|-----------|--------|-------|
| Controller | RicercaMezzi | `visualizzaSpecifiche(idMezzo)` | `idMezzo → Mezzo` |
| Model | Mezzo | `getDettagliMezzo()` | `→ Mezzo` |
| View | AppUtente | `renderizzaDettagliVeicolo(mezzo)` | `mezzo: Mezzo → void` |

*Dettaglio:* `Mezzo.getDettagliMezzo()` restituisce l'oggetto Mezzo completo con tutti gli attributi:

| Attributo | Tipo | Significato per l'utente |
|-----------|------|--------------------------|
| `idMezzo` | PK | Identificativo veicolo |
| `coordinateMezzo` | String | Posizione attuale (x,y,z) |
| `stato` | StatoMezzo | Stato operativo |
| `autonomia` | float | Autonomia residua (%) |
| `costoOrario` | float | Tariffa oraria |
| `velocitaMax` | float | Velocità massima |
| `condizione` | String | Condizioni fisiche |
| `tipo` | String | Tipologia (bici/scooter/auto) |
| `idFlotta` | String | Flotta di appartenenza |
| `tempoDisponibilita` | time | Tempo stimato di disponibilità *(serve UT.05)* |

`AppUtente.renderizzaDettagliVeicolo()` presenta questi dati in formato leggibile all'utente.

---

## 5. Flussi Alternativi — Method Traceability

### 5.1 Nessun Mezzo Trovato nel Raggio Base — Espansione Accettata

**Testo documentazione.md:** "Al passaggio 3, la ricerca non restituisce mezzi. Il sistema propone l'espansione del raggio. L'utente accetta l'espansione. Il sistema esegue la ricerca con il nuovo raggio e mostra i risultati."

*Trigger:* `Mezzo.getMezziInArea(coordinateUtente, 2.0)` restituisce lista vuota.

| Passo | Componente | Metodo | Descrizione |
|-------|-----------|--------|-------------|
| A1 | AppUtente | *(riceve lista vuota da Controller)* | Il sistema rileva nessun risultato |
| A2 | AppUtente | *(mostra prompt espansione)* | Proposta all'utente: "Nessun mezzo trovato. Espandere a 5km?" |
| A3 | AppUtente | `confermaEspansione()` | `→ void` — L'utente accetta |
| A4 | RicercaMezzi | `visualizzaMezziVicini(coordinateUtente, 5.0)` | `raggiob = 5.0` (raggio esteso) |
| A5 | Mezzo | `getMezziInArea(coordinateUtente, 5.0)` | Query con raggio 5km |
| A6 | *Prosegue come flusso principale Step 3-5* | `mostraMezzi()` → `selezionaMezzo()` → `visualizzaSpecifiche()` → `renderizzaDettagliVeicolo()` |

### 5.2 Nessun Mezzo Trovato nel Raggio Espanso

**Testo documentazione.md:** "Il sistema mostra un messaggio di errore indicando che nessun mezzo è disponibile."

*Trigger:* `Mezzo.getMezziInArea(coordinateUtente, 5.0)` restituisce lista vuota.

| Passo | Componente | Metodo | Descrizione |
|-------|-----------|--------|-------------|
| B1 | AppUtente | `mostraErrore(msg)` | `msg: "Nessun mezzo disponibile nell'area"` *(inferred message text)* *(inferred)* |

### 5.3 Utente Rifiuta Espansione

**Testo documentazione.md:** "Il sistema mostra un messaggio di errore indicando che nessun mezzo è disponibile."

*Trigger:* Dopo il prompt di espansione, l'utente non invoca `confermaEspansione()` *(comportamento implicito — nessun metodo di rifiuto esplicito in AppUtente)* *(inferred)*.

| Passo | Componente | Metodo | Descrizione |
|-------|-----------|--------|-------------|
| C1 | AppUtente | `mostraErrore(msg)` | `msg: "Nessun mezzo disponibile"` *(inferred message text)* *(inferred)* |

> **Nota architetturale:** `AppUtente` non espone un metodo `rifiutaEspansione()`. Il rifiuto è gestito implicitamente: l'utente chiude il prompt/torna indietro senza chiamare `confermaEspansione()`. *(Vedi HITL claim-3b7d9e02)*

---

## 6. Sequenza Completa dei Metodi (Main Flow)

```
1. AppUtente.avviaRicercaMezzi(coordinateUtente, 2.0)
2.   RicercaMezzi.visualizzaMezziVicini(coordinateUtente, 2.0)
3.     Mezzo.getMezziInArea(coordinateUtente, 2.0)
4.     DBMS: SELECT * FROM mezzo WHERE stato='disponibile' AND distanza(...) <= 2.0
5.     → List<Mezzo> (non vuota)
6.   → List<Mezzo>
7. AppUtente.mostraMezzi(List<Mezzo>)
8. AppUtente.selezionaMezzo(idMezzo)                    // input utente
9.   RicercaMezzi.visualizzaSpecifiche(idMezzo)
10.    Mezzo.getDettagliMezzo()
11.    DBMS: SELECT * FROM mezzo WHERE id_mezzo = idMezzo
12.    → Mezzo (completo)
13.  → Mezzo
14. AppUtente.renderizzaDettagliVeicolo(Mezzo)
```

### Alternative: Nessun mezzo a 2km → Espansione Accettata

```
2a.  Mezzo.getMezziInArea(coordinateUtente, 2.0) → [] (vuota)
2b.  AppUtente: prompt "Nessun mezzo. Espandere a 5km?"
2c.  AppUtente.confermaEspansione()
2d.  RicercaMezzi.visualizzaMezziVicini(coordinateUtente, 5.0)
2e.    Mezzo.getMezziInArea(coordinateUtente, 5.0)
2f.    → List<Mezzo> (non vuota)
      // prosegue da step 6 del flusso principale
```

### Alternative: Nessun mezzo a 2km → Nessun mezzo a 5km

```
2a.  Mezzo.getMezziInArea(coordinateUtente, 2.0) → []
2b.  AppUtente: prompt espansione
2c.  AppUtente.confermaEspansione()
2d.  RicercaMezzi.visualizzaMezziVicini(coordinateUtente, 5.0)
2e.    Mezzo.getMezziInArea(coordinateUtente, 5.0) → []
2f.  AppUtente.mostraErrore("Nessun mezzo disponibile")
```

### Alternative: Utente Rifiuta Espansione

```
2a.  Mezzo.getMezziInArea(coordinateUtente, 2.0) → []
2b.  AppUtente: prompt espansione
2c.  (utente chiude/annulla — nessuna chiamata a confermaEspansione)
2d.  AppUtente.mostraErrore("Nessun mezzo disponibile")
```

---

## 7. Mapping User Stories

| User Story | Testo | Copertura in UC.UT.01 | Metodo/i |
|------------|-------|------------------------|----------|
| **UT.01** | Visualizzare i mezzi disponibili in un raggio prestabilito a partire dalla posizione scelta | Main Flow Step 2-3: query geolocalizzata e visualizzazione | `visualizzaMezziVicini()` → `getMezziInArea()` → `mostraMezzi()` |
| **UT.04** | Consultare le specifiche tecniche del mezzo disponibile | Main Flow Step 5: recupero e visualizzazione dettagli | `visualizzaSpecifiche()` → `getDettagliMezzo()` → `renderizzaDettagliVeicolo()` |
| **UT.05** | Conoscere l'orario stimato per la disponibilità di un mezzo | Implicitamente coperta da Step 5: `getDettagliMezzo()` restituisce l'attributo `tempoDisponibilita` *(inferred)* *(inferred)* | `getDettagliMezzo()` → `renderizzaDettagliVeicolo()` *(include `tempoDisponibilita` nei dettagli)* |

> **Nota su UT.05:** Il flusso testuale di UC.UT.01 in documentazione.md non menziona esplicitamente la visualizzazione del `tempoDisponibilita`. Tuttavia, `Mezzo.getDettagliMezzo()` restituisce l'oggetto Mezzo completo che include l'attributo `tempoDisponibilita`. La copertura di UT.05 è quindi implicita ma strutturalmente garantita dal modello dati. *(Vedi HITL claim-9e2f5a11)*

---

## 8. Verifica Tipo Coordinate

*Verifica del vincolo architetturale: tutte le coordinate sono di tipo **String** (x,y,z float parsati).*

| Attributo | Entità | Tipo | Consistenza |
|-----------|--------|------|-------------|
| `coordinateUtente` | Utente | String | ✓ Master_Spec §2 |
| `coordinateMezzo` | Mezzo | String | ✓ Master_Spec §2 |
| `coordinatePartenza` | Corsa | String | ✓ Master_Spec §2 |
| `coordinateArrivo` | Corsa | String | ✓ Master_Spec §2 |
| Parametro `coordinateUtente` | `avviaRicercaMezzi()` | String | ✓ Master_Spec §4 |
| Parametro `coordinateUtente` | `visualizzaMezziVicini()` | String | ✓ Master_Spec §3 |
| Parametro `coordinateUtente` | `getMezziInArea()` | String | ✓ Master_Spec §2 |

**Verdetto:** Consistenza totale. Tutti gli attributi e parametri di coordinate usano `String` come da design decision del team *(claim-7f2a5b013)*.

---

## 9. Verifica Raggi di Ricerca

| Fonte | Raggio Base | Raggio Esteso | Notazione |
|-------|-------------|---------------|-----------|
| documentazione.md §2.2.2 | "es. 2 km" | "es. 5 km" | "es." = esempio, suggerisce flessibilità |
| Master_Spec.cgd.md §3 | "raggio base 2km" | "esteso 5km" | Valori canonici nel commento |
| Master_Spec.cgd.md §9 Anti-Patterns | "raggio base 2km, esteso 5km" | — | Parametrizzare, non hardcodare |
| UC.UT.01-clean.uml | `2.0` (float) | `5.0` (float) | Valori numerici nei messaggi |
| chiarimenti-vari.md punto 7 | "raggiob (raggio base)" | "raggioe (raggio esteso)" | Conferma la nomenclatura |

**Verdetto:** I valori **2.0 km** (base) e **5.0 km** (esteso) sono coerenti come valori di riferimento in tutte le fonti. La notazione "es." in documentazione.md suggerisce che possano essere parametrizzati *(l'anti-pattern §9 conferma: "Parametrizzare — non hardcodare")*. *(Vedi HITL claim-7c6a3d14)*

---

## 10. Verifica Sequence Diagram vs documentazione.md

### 10.1 Struttura del Diagramma

Il file `UC.UT.01-clean.uml` contiene **due interazioni** XMI:

| Interazione | Nome | Lifeline | Qualità |
|-------------|------|----------|---------|
| 1ª | "Ricerca Mezzi – Caso d'Uso UC.UT.01" | AppUtente, Controller, DBMS, Mezzo, Utente | Nomi messaggi informali ("richiede ricerca mezzi raggio 2km", "invia richiesta mezzi(raggio: 2.0)") |
| 2ª | "Ricerca Mezzi – UC.UT.01" | AppUtente, Controller, DBMS, Mezzo | Nomi metodi precisi (`avviaRicercaMezzi()`, `visualizzaMezziVicini()`, `getMezziInArea()`, ecc.) |

*La 2ª interazione è la versione raffinata e viene usata come riferimento primario per la method traceability.*

### 10.2 Corrispondenza Flussi

| Flusso documentazione.md | Coperto nel diagramma | Note |
|--------------------------|----------------------|------|
| Main Flow (mezzi trovati a 2km) | ✓ Sì | Passi 1-12 mappati esattamente |
| Alt: nessun mezzo a 2km → espansione accettata | ✓ Sì | `confermaEspansione()` → ricerca a 5km |
| Alt: nessun mezzo a 5km dopo espansione | ✓ Sì | `mostraErrore()` con messaggio |
| Alt: utente rifiuta espansione | ✓ Sì | `mostraErrore()` senza chiamata a `confermaEspansione()` |

### 10.3 Inconsistenze Rilevate

| # | Inconsistenza | Severità | Dettaglio |
|---|---------------|----------|-----------|
| I1 | Lifeline "Controller" anziché "RicercaMezzi" | Media | Il diagramma usa il nome generico "Controller" invece d6 richiede che i nomi delle lifeline corrispondano ai componenti di siel nome specifico del componente "RicercaMezzi". Chiarimenti-vari.md punto stema. La 1ª interazione include anche "Utente" come lifeline (modello), ma la 2ª la rimuove correttamente. *(Vedi HITL claim-4f2a1c08)* |
| I2 | Nomi messaggi informali nella 1ª interazione | Bassa | La 1ª interazione ha messaggi come "richiede ricerca mezzi raggio 2km" che non corrispondono a nomi di metodo reali. La 2ª interazione corregge usando nomi metodo precisi. *(Artefatto XMI — chiarimenti-vari.md punto 14)* |
| I3 | `selezionaMezzo()` vs "utente seleziona mezzo" | Bassa | Il diagramma mostra sia `selezionaMezzo(idMezzo)` (chiamata esplicita) che "utente seleziona mezzo" (azione attore). Sono coerenti: la prima è la chiamata al metodo, la seconda è l'azione utente che la innesca. |

*Nessuna inconsistenza bloccante rilevata.* Il diagramma di sequenza (2ª interazione) corrisponde fedelmente al flusso descritto in documentazione.md §2.2.2.

---

## 11. Dipendenze e Relazioni tra Use Case

```
UC.ATT.01 (Login)
    │
    ▼ (autenticato)
UC.UT.01 (Ricerca Mezzi) ◄── esteso da ── UC.UT.02 (Prenotazione Mezzo)
    │                                            │
    │                                            ▼ (prenotazione completata)
    │                                       UC.UT.03 (Gestione Corsa)
    │                                            │
    │                                            ├── include ── UC.UT.05 (Metodo Pagamento)
    │                                            ├── include ── UC.UT.07 (Termina Corsa e Pagamento)
    │                                            │
    │                                            ▼ (esteso da)
    │                                       UC.UT.06 (Sospensione Corsa)
    │
    ▼ (indipendente)
UC.UT.04 (Ottimizzazione Percorso)
```

*UC.UT.01 non include né estende altri use case. È esteso da UC.UT.02 (l'utente può prenotare un mezzo dopo averlo cercato).*

---

## 12. Vincoli Architetturali Applicabili

| # | Vincolo (da Master_Spec §8) | Applicabilità a UC.UT.01 |
|---|------------------------------|--------------------------|
| 1 | Autenticazione obbligatoria | ✓ Precondizione P1: login richiesto |
| 5 | RBAC: routing per ruolo | ✓ Solo attori con `RuoloAttore.Utente` accedono |
| 10 | Disaccoppiamento View-Controller-Model | ✓ AppUtente → RicercaMezzi → Mezzo (nessun accesso diretto View→Model) |
| 11 | Ruolo unico per sessione | ✓ Sessione Utente attiva |
| — | Anti-pattern: "Hardcodare raggi di ricerca" | ⚠ I valori 2km/5km appaiono hardcodati nel diagramma ma l'anti-pattern §9 prescrive parametrizzazione |

---

## 13. Copertura Epistemica

*Legenda marker:*

| Marker | Significato |
|--------|-------------|
| *(inferred)* | Dato dedotto da altre fonti, non esplicitamente dichiarato |
| *(unverified)* | Claim non ancora confermato dal team |
| *(derived)* | Derivato da analisi incrociata delle fonti |

### Claim che richiedono verifica HITL

| # | Claim ID | Descrizione | Round |
|---|----------|-------------|-------|
| 1 | claim-4f2a1c08 | Lifeline "Controller" vs "RicercaMezzi" — naming inconsistency | A |
| 2 | claim-3b7d9e02 | Assenza metodo esplicito `rifiutaEspansione()` | A |
| 3 | claim-9e2f5a11 | UT.05 coperta implicitamente via `tempoDisponibilita` | A |
| 4 | claim-7c6a3d14 | Raggi di ricerca: "es." vs valori canonici fissi | A |

---

## §16. Test Case Specifications

| ID | Component | Scenario | Preconditions | Input | Expected Result | Postconditions | Edge Cases |
|----|-----------|----------|--------------|-------|----------------|----------------|------------|
| TC-UT01-01 | View (AppUtente) | Avvio ricerca mezzi — happy path coordinate valide | Utente autenticato (P1), coordinate GPS presenti (P2) | `coordinateUtente` = "12.34,56.78,0.0", `raggiob` = 2.0 | `avviaRicercaMezzi()` invoca `visualizzaMezziVicini()` → `mostraMezzi(List<Mezzo>)` con lista non vuota | Q1: elenco mezzi recuperato | Coordinate al limite del raggio di copertura (2.0 km esatti) |
| TC-UT01-02 | View (AppUtente) | Avvio ricerca con coordinate non valide | Utente autenticato | `coordinateUtente` = "999,999,999" (fuori copertura) | `mostraErrore("Coordinate non valide")` chiamato | Nessuna query inviata | Stringa vuota, formato errato ("abc,def"), coordinate nulle |
| TC-UT01-03 | Controller (RicercaMezzi) | Ricerca base restituisce lista vuota → espansione accettata | Utente autenticato, nessun mezzo nel raggio 2km | `coordinateUtente` valide, `raggiob` = 2.0 → `getMezziInArea()` → [] | `confermaEspansione()` → `visualizzaMezziVicini(coordinateUtente, 5.0)` | Ricerca estesa a 5km eseguita | Raggio esteso = 5.0 km, utente annulla prima di confermare |
| TC-UT01-04 | Model (Mezzo) | Query spaziale DBMS restituisce 3 mezzi disponibili | DBMS connesso, 3 mezzi con `stato = disponibile` nel raggio | `coordinateUtente` = "12.34,56.78,0.0", `raggio` = 2.0 | `getMezziInArea()` restituisce `List<Mezzo>` con 3 elementi | Mezzi filtrati per stato e distanza | Raggio = 0 km (solo coordinate esatte), raggio massimo, nessun mezzo nello stato `disponibile` |
| TC-UT01-05 | View (AppUtente) | Selezione mezzo e visualizzazione dettagli | Mezzo selezionato disponibile nella lista | `idMezzo` valido | `selezionaMezzo(idMezzo)` → `visualizzaSpecifiche()` → `renderizzaDettagliVeicolo(Mezzo)` con tutti gli attributi popolati | Q2: specifiche recuperate | `idMezzo` inesistente/null, mezzo senza `tempoDisponibilita` valorizzato |
| TC-UT01-IT01 | Integration View→Controller | Contratto `avviaRicercaMezzi` → `visualizzaMezziVicini` | Precondizioni P1-P2 verificate | `coordinateUtente` valide | `avviaRicercaMezzi()` delega a `visualizzaMezziVicini()` con stessi parametri | Parametri passati correttamente tra View e Controller | `raggiob` = 0.0, coordinata con separatori diversi |
| TC-UT01-IT02 | Integration Controller→Model | Contratto `visualizzaMezziVicini` → `getMezziInArea` | Controller ha ricevuto richiesta valida | `coordinateUtente` valide, `raggio` = 2.0 | `getMezziInArea()` invocato con coordinate e raggio corretti | Model restituisce `List<Mezzo>` al Controller | Raggio esteso 5.0, conversione tipo String→coordinate |
| TC-UT01-IT03 | Integration Model→DBMS | Query spaziale JTS Point per filtro geografico | Model ha ricevuto coordinate e raggio | Coordinate parsate in `Point` JTS, `stato='disponibile'` | DBMS esegue `SELECT * FROM mezzo WHERE stato='disponibile' AND ST_Distance(...) <= raggio` | Risultati filtrati correttamente per distanza e stato | Mezzo con coordinate nulle, indice spaziale non utilizzato |

---

## §17. Error Handling Matrix

| ID | Error Type | Component | Detection Point | System Response | Fallback | Logging |
|----|-----------|-----------|----------------|----------------|----------|---------|
| ERR-UT01-01 | Input validation | AppUtente | `avviaRicercaMezzi(coordinateUtente)` — formato String non valido o coordinate fuori copertura | `mostraErrore("Coordinate non valide")` | L'utente reinserisce le coordinate | WARN |
| ERR-UT01-02 | Business logic | RicercaMezzi | `visualizzaMezziVicini(coordinateUtente, 2.0)` — `getMezziInArea()` → lista vuota | Prompt: "Nessun mezzo trovato. Espandere a 5km?" (Alt 5.1) | Raggio esteso 5km (se utente accetta) | INFO |
| ERR-UT01-03 | Business logic | RicercaMezzi | Dopo espansione, `visualizzaMezziVicini(coordinateUtente, 5.0)` → lista vuota | `mostraErrore("Nessun mezzo disponibile nell'area")` (Alt 5.2) | L'utente torna alla mappa | WARN |
| ERR-UT01-04 | External | Mezzo (DBMS) | `getMezziInArea()` — query spaziale fallisce (timeout/errore connessione) | `mostraErrore("Errore caricamento mezzi")` | L'utente riprova la ricerca | ERROR |
| ERR-UT01-05 | Business logic | AppUtente | Dopo prompt espansione — utente non invoca `confermaEspansione()` | `mostraErrore("Nessun mezzo disponibile")` (Alt 5.3) | Flusso terminato, utente torna alla home | INFO |
| ERR-UT01-06 | Security | RicercaMezzi | `visualizzaMezziVicini()` — sessione utente scaduta o token non valido | Redirect a `UC.ATT.01` (Login) | L'utente si ri-autentica | WARN |
| ERR-UT01-07 | Input validation | RicercaMezzi | `visualizzaSpecifiche(idMezzo)` — `idMezzo` inesistente o nullo | `mostraErrore("Mezzo non trovato")` | L'utente seleziona un altro mezzo | WARN |

---

## HITL Verification Record

### Round A: Derived Data Confirmation

Tutti i claim derivano da cross-reference tra documentazione.md, Master_Spec.cgd.md, UC.UT.01-clean.uml e chiarimenti-vari.md. Ciascuno richiede conferma interpretativa dal team Cofee Coders.

| # | Claim ID | Claim | Rilevanza | Stato |
|---|----------|-------|-----------|-------|
| 1 | claim-4f2a1c08 | Diagramma usa "Controller" non "RicercaMezzi" — è intenzionale o va corretto? | Naming consistente con chiarimenti-vari.md p.to 6 | REVIEWED |
| 2 | claim-3b7d9e02 | Rifiuto espansione è implicito (nessun metodo `rifiutaEspansione()`) — il design è corretto? | Completezza API View | REVIEWED |
| 3 | claim-9e2f5a11 | UT.05 servita implicitamente da `getDettagliMezzo()` — confermate che `tempoDisponibilita` è sufficiente? | Copertura user story | REVIEWED |
| 4 | claim-7c6a3d14 | I raggi 2km/5km sono canonici o parametrici? documentazione.md dice "es." | Flessibilità vs specificità | REVIEWED |

### Round B: True HITL Verification

*Nessun claim richiede Round B — tutti i claim sono derivati dalle fonti disponibili e richiedono solo conferma interpretativa (Round A).*

---

## 14. Riepilogo Inconsistenze Cross-Source

| # | Tipo | Descrizione | Fonti coinvolte | Impatto |
|---|------|-------------|-----------------|---------|
| I1 | Naming | Lifeline "Controller" generica vs componente specifico "RicercaMezzi" | UC.UT.01-clean.uml vs chiarimenti-vari.md p.to 6 | Basso — non altera la logica del flusso *(derived)* |
| I2 | Naming | Messaggi informali nella 1ª interazione XMI | UC.UT.01-clean.uml interazione 1 vs Master_Spec metodi | Basso — 2ª interazione corregge |
| I3 | Naming | "es. 2km" in documentazione.md vs valore fisso 2.0 nel diagramma e Master_Spec | documentazione.md vs Master_Spec.cgd.md vs UC.UT.01-clean.uml | Basso — l'anti-pattern §9 conferma parametrizzazione *(derived)* |
| I4 | Copertura | UT.05 non esplicitata nel testo del flusso ma coperta dal modello dati | documentazione.md UT.05 vs UC.UT.01 flusso principale | Basso — `tempoDisponibilita` è nel modello *(derived)* |

*Nessuna inconsistenza bloccante. Tutte le divergenze sono di natura formale/naming, non logica o funzionale.*

---

## 15. Componenti e Metodi Coinvolti — Riepilogo

### View: AppUtente
| Metodo | Ruolo in UC.UT.01 |
|--------|-------------------|
| `avviaRicercaMezzi(coordinateUtente, raggiob)` | Innesco use case (Step 1) |
| `mostraMezzi(mezzi)` | Visualizzazione risultati (Step 3) |
| `selezionaMezzo(idMezzo)` | Selezione utente (Step 4) |
| `renderizzaDettagliVeicolo(mezzo)` | Dettagli mezzo (Step 5) |
| `confermaEspansione()` | Accettazione raggio esteso (Alt 5.1) |
| `mostraErrore(msg)` | Messaggi errore (Alt 5.2, 5.3) |

### Controller: RicercaMezzi
| Metodo | Ruolo in UC.UT.01 |
|--------|-------------------|
| `visualizzaMezziVicini(coordinateUtente, raggiob)` | Query geolocalizzata (Step 2, Alt 5.1 A4) |
| `visualizzaSpecifiche(idMezzo)` | Dettagli singolo mezzo (Step 5) |

### Model: Mezzo
| Metodo | Ruolo in UC.UT.01 |
|--------|-------------------|
| `getMezziInArea(coordinateUtente, raggio)` | Query spaziale DBMS (Step 2, Alt 5.1 A5) |
| `getDettagliMezzo()` | Recupero attributi completi (Step 5) |

### Model: Utente
| Metodo | Ruolo in UC.UT.01 |
|--------|-------------------|
| `getCoordinateUtente()` | Fornisce coordinate per la ricerca (Precondizione P2) |

### External: DBMS
| Operazione | Ruolo in UC.UT.01 |
|------------|-------------------|
| Query spaziale CRUD Read su `mezzo` | Filtro geografico e per stato |

---

## References

| Documento | Percorso | Ruolo |
|-----------|----------|-------|
| documentazione.md v3.0 | `docs/specs/documentazione.md` | Sorgente primaria UC.UT.01 (§2.2.2) e user stories UT.01/UT.04/UT.05 (§1) |
| Master_Spec.cgd.md v4.0 | `docs/specs/Master_Spec.cgd.md` | Metodi, attributi, controller, vincoli architetturali |
| UC.UT.01-clean.uml | `docs/diagrams/sequence-diagrams/UC.UT.01/UC.UT.01-clean.uml` | Diagramma di sequenza (XMI 2.1) |
| chiarimenti-vari.md | `docs/specs/chiarimenti-vari.md` | Interpretazioni e vincoli (punti 6, 7, 14, 15) |

---

**Fine specifica UC.UT.01 — CGD generato il 2026-06-23. HITL Round A: 4/4 claim REVIEWED.**

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | REVIEWED

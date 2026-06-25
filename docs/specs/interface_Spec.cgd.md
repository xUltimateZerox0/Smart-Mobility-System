---
clarity-gate-version: 2.1
document-type: Implementation
processed-by: AI — Class_Diagram_Spec.cgd.md v1.8, Component_Diagram_Spec.cgd.md v1.0, Master_Spec.cgd.md v4.0
processed-date: 2026-06-25
clarity-status: CLEAR
hitl-status: REVIEWED
hitl-pending-count: 0
points-passed: 1-9
document-sha256: 56dd4c8bf160a77083fec9685fe110b105ba3d1f8c40ed2da4a4bc6efcd15524
hitl-claims:
  - id: claim-iface-qualificazione
    text: "Solo metodi PUBLIC possono essere esposti in interfacce. I metodi PRIVATE sono esclusi."
    value: "Regola architetturale applicata — 23 metodi privati View esclusi. Metodi Controller senza visibilità esplicita nel documento sono considerati public per convenzione MVC."
    source: "Class_Diagram_Spec.cgd.md (colonna Visibilità per View) + costrutto linguaggio Java"
    location: "global/visibility-rule"
    round: A
    confirmed-by: HITL (Team Cofee Coders)
    confirmed-date: 2026-06-25
  - id: claim-iface-gestione-corsa-concludi
    text: "concludiPrenotazione(idPrenotazione) è presente in Class_Diagram_Spec ma assente in Master_Spec v4.0 §2.7. Il metodo viene incluso in questa specifica — Class_Diagram_Spec ha priorità maggiore per i dettagli delle classi in quanto più specifico e aggiornato (HITL decisione)."
    value: "Metodo confermato in Class_Diagram_Spec.cgd.md:473 e nell'XMI originale classDiagram-v1.8-clean.uml. Incluso senza riserva."
    source: "Class_Diagram_Spec.cgd.md v1.8 (sezione GestionePrenotazione:473) + XMI classDiagram-v1.8-clean.uml"
    location: "Controller-provided/Gestione Corsa / Moderazione Utente"
    round: A
    confirmed-by: HITL (Team Cofee Coders)
    confirmed-date: 2026-06-25
---

# Interface Specification — Smart Mobility System

**Versione:** 1.0
**Team:** Cofee Coders
**Progetto:** Ingegneria del Software a.a. 2025/2026
**Architettura:** MVC Web-oriented con Controller Intermediario
**Fonti:** Class_Diagram_Spec.cgd.md v1.8, Component_Diagram_Spec.cgd.md v1.0, Master_Spec.cgd.md v4.0

## Regola di Visibilità

**Solo i metodi con visibilità PUBLIC possono essere esposti nelle interfacce.** I metodi PRIVATE sono dettagli implementativi della classe e NON compaiono in alcuna interfaccia.

- **View layer (5 classi):** La visibilità è esplicitamente documentata in Class_Diagram_Spec §3. I 23 metodi privati (prevalentemente `mostra*`, `visualizza*`, `renderizza*`) sono esclusi.
- **Controller layer (9 classi):** Nessuna colonna visibilità in Class_Diagram_Spec §2 — tutti i metodi sono considerati `public` per convenzione architetturale MVC (definiscono la API surface per le View).
- **External Systems (4 classi):** Stessa convenzione Controller — tutti i metodi sono `public`.
- **Model layer (12 classi):** Tutti i metodi getter/setter e CRUD sono `public`. Il Model espone le proprie operazioni tramite le 3 interfacce di gruppo (§3: Gestione Dati Utente, Gestione Dati Supporto, Gestione Dati Corsa), non a livello di singola classe. I metodi sono elencati per completezza in §3 raggruppati per interfaccia target (non per classe individuale).

---

## 1. View-Provided Interfaces (fornite da View → consumate da Controller)

Il Controller invoca i metodi di queste interfacce per notificare aggiornamenti alla View e ricevere input utente.

### 1.1 Aggiornamenti Corsa

| Proprietà | Valore |
|-----------|--------|
| **ID XMI** | `BGnPp3mD.AACARJJ` |
| **Classe Realizzazione** | `AppUtente` |
| **Consumatore** | Controller (per notifiche corsa ad AppUtente) |
| **Descrizione** | Interfaccia per la gestione del ciclo di vita della corsa lato View. Il Controller chiama questi metodi per richiedere input all'utente e notificare cambiamenti di stato. |

**Metodi:**

| Metodo | Ritorno | Parametri | UC di Riferimento |
|--------|---------|-----------|-------------------|
| `scansionaQRCode(qrCode)` | void | qrCode: String | UC.UT.03 |
| `inserisciDatiCarta(idUtente, numCarta, dsCarta, cvv, intestatarioCarta)` | void | idUtente, numCarta, dsCarta, cvv, intestatarioCarta | UC.UT.05 |
| `apriAvvioCorsa()` | void | — | UC.UT.03 |
| `terminazioneCorsa(idCorsa)` | void | idCorsa | UC.UT.07 |
| `sospendiCorsa(idCorsa)` | void | idCorsa | UC.UT.06 |
| `apriSezioneProfilo(idUtente)` | void | idUtente | UC.UT.05 |
| `apriInserimentoMetodoPagamento(idUtente)` | void | idUtente | UC.UT.05 |
| `selezionaMezzo(idMezzo)` | void | idMezzo | UC.UT.01, UC.UT.02 |
| `inserisciDestinazione(indirizzoArrivo)` | void | indirizzoArrivo: String | UC.UT.04 |
| `avviaRicercaMezzi(coordinateUtente, raggiob)` | void | coordinateUtente: String, raggiob: float | UC.UT.01 |
| `confermaEspansione()` | void | — | UC.UT.01 |
| `notificaAzione(idUtente, azione)` | void | idUtente, azione: String | UC.OP.02 |
| `ottieniMetodiSalvati()` | void | — | UC.UT.05 |
| `selezionaMetodo(numCarta)` | void | numCarta: String | UC.UT.05 |
| `richiestaLogout(email)` | void | email: String | UC.UT.09 |
| `getIdUtente()` | — | — | — |
| `setIdUtente(id)` | void | id | — |
| `getIdSessioneUtente()` | — | — | — |
| `setIdSessioneUtente(id)` | void | id | — |

**Metodi PRIVATE esclusi (11):** `mostraErrore`, `mostraStima`, `mostraSuccesso`, `mostraFineCorsa`, `mostraQRCode`, `mostraRipresaCorsa`, `mostraMetodi`, `mostraSceltaMetodi`, `mostraMezzi`, `mostraMetodoConvalidato`, `renderizzaDettagliVeicolo`

---

### 1.2 Stato Flotta

| Proprietà | Valore |
|-----------|--------|
| **ID XMI** | `88Pfp3mD.AACARMt` |
| **Classe Realizzazione** | `AppOperatoreTecnico` |
| **Consumatore** | Controller (per invio dati flotta a AppOperatoreTecnico) |
| **Descrizione** | Interfaccia per la visualizzazione dello stato della flotta e il controllo remoto dei veicoli lato View. |

**Metodi:**

| Metodo | Ritorno | Parametri | UC di Riferimento |
|--------|---------|-----------|-------------------|
| `richiedeStatoFlotta(idFlotta)` | void | idFlotta: String | UC.OP.01 |
| `selezionaVeicolo(idMezzo)` | void | idMezzo | UC.OP.01 |
| `richiestaLogout(email)` | void | email: String | UC.OP.04 |
| `getIdOperatoreTecnico()` | — | — | — |
| `setIdOperatoreTecnico(id)` | void | id | — |
| `getIdSessioneOperatoreTecnico()` | — | — | — |
| `setIdSessioneOperatoreTecnico(id)` | void | id | — |

**Metodi PRIVATE esclusi (3):** `mostraSuccesso`, `mostraErrore`, `visualizzaMezzi`

---

### 1.3 Diagnostica

| Proprietà | Valore |
|-----------|--------|
| **ID XMI** | `Xu9_p3mD.AACARM.` |
| **Classe Realizzazione** | `AppPA` |
| **Consumatore** | Controller (per invio report diagnostici a AppPA) |
| **Descrizione** | Interfaccia per la diagnostica della flotta e gestione restrizioni geografiche lato View. |

**Metodi:**

| Metodo | Ritorno | Parametri | UC di Riferimento |
|--------|---------|-----------|-------------------|
| `selezionaIntervallo(dataInizio, dataFine)` | void | dataInizio: date, dataFine: date | UC.AP.01 |
| `richiedeStatoFlotta(idFlotta)` | void | idFlotta: String | UC.AP.02 |
| `avviaIntervento(idFlotta)` | void | idFlotta: String | UC.AP.02 |
| `selezionaMappa()` | void | — | UC.AP.03 |
| `modificaRestrizioni(zona)` | void | zona: ZonaGeografica | UC.AP.03 |
| `confermaSovrascrittura(idArea, tipoRestrizione, noteRestrizione)` | void | idArea, tipoRestrizione, noteRestrizione | UC.AP.03 |
| `rifiutaSovrascrittura()` | void | — | UC.AP.03 |
| `richiestaLogout(email)` | void | email: String | UC.AP.04 |
| `getIdPA()` | — | — | — |
| `setIdPA(id)` | void | id | — |
| `getIdSessionePA()` | — | — | — |
| `setIdSessionePA(id)` | void | id | — |

**Metodi PRIVATE esclusi (5):** `mostraErrore`, `mostraSuccesso`, `mostraStatistiche`, `visualizzaMezzi`, `mostraMappa`

---

### 1.4 Eventi Utente

| Proprietà | Valore |
|-----------|--------|
| **ID XMI** | `8v9fp3mD.AACARMc` |
| **Classe Realizzazione** | `AppOperatoreSC` |
| **Consumatore** | Controller (per notifiche moderazione e prenotazioni a AppOperatoreSC) |
| **Descrizione** | Interfaccia per la moderazione utenti e amministrazione prenotazioni lato View. |

**Metodi:**

| Metodo | Ritorno | Parametri | UC di Riferimento |
|--------|---------|-----------|-------------------|
| `mostraReport(idUtente)` | void | idUtente | UC.OP.02 |
| `richiediListaPrenotazioni()` | void | — | UC.OP.03 |
| `selezionaPrenotazione(idPrenotazione)` | void | idPrenotazione | UC.OP.03 |
| `aggiornaReport(idUtente)` | void | idUtente | UC.OP.02 |
| `richiestaLogout(email)` | void | email: String | UC.OP.05 |
| `getIdOperatoreSC()` | — | — | — |
| `setIdOperatoreSC(id)` | void | id | — |
| `getIdSessioneOperatoreSC()` | — | — | — |
| `setIdSessioneOperatoreSC(id)` | void | id | — |

**Metodi PRIVATE esclusi (3):** `mostraPrenotazioni`, `mostraErrore`, `mostraSuccesso`

---

### 1.5 Stato Sessione

| Proprietà | Valore |
|-----------|--------|
| **ID XMI** | `xgSAZ3mD.AACARNt` |
| **Classe Realizzazione** | `Autenticazione` |
| **Consumatore** | Controller (per gestione stato sessione su Autenticazione) |
| **Descrizione** | Interfaccia per login, registrazione e gestione della sessione lato View. |

**Metodi:**

| Metodo | Ritorno | Parametri | UC di Riferimento |
|--------|---------|-----------|-------------------|
| `inserisciCredenziali(nome, cognome, email, password, datanascita)` | void | nome, cognome, email, password, datanascita | UC.ATT.01 |
| `registrazioneUtente()` | void | — | UC.UT.08 |
| `getIdAttore()` | — | — | — |
| `setIdAttore(id)` | void | id | — |
| `getIdSessioneAttore()` | — | — | — |
| `setIdSessioneAttore(id)` | void | id | — |

**Metodi PRIVATE esclusi (1):** `mostraFormRegistrazione`

---

## 2. Controller-Provided Interfaces (fornite da Controller → consumate da View)

Le View invocano i metodi di queste interfacce per richiedere operazioni di business al Controller. Ogni interfaccia raggruppa i metodi delle classi Controller necessari per lo specifico attore.

### 2.1 Gestione Corsa

| Proprietà | Valore |
|-----------|--------|
| **ID XMI** | `R.IxKnmD.AACAQ9w` |
| **Classi Realizzazione** | GestioneCorsa, GestorePagamento, RicercaMezzi, GestionePrenotazione, GestioneAutenticazione |
| **Consumatore** | AppUtente |
| **Descrizione** | Interfaccia completa per l'utente cittadino: ciclo vita corsa, pagamenti, ricerca mezzi, prenotazioni e autenticazione. |

#### Da GestioneCorsa

| Metodo | Ritorno | Parametri | UC di Riferimento |
|--------|---------|-----------|-------------------|
| `avviaCorsa()` | void | — | UC.UT.03 |
| `terminaCorsa()` | void | — | UC.UT.07 |
| `controllaDisponibilita()` | bool | — | UC.UT.02, UC.UT.03 |
| `controllaDisponibilita(info)` | bool | info: String | UC.UT.03 |
| `aggiornaStima(idCorsa)` | float | idCorsa | UC.UT.03 |
| `sospensioneCorsa()` | bool | — | UC.UT.06 |
| `richiediSblocco(qrCode)` | bool | qrCode: String | UC.UT.03 |
| `richiediCalcoloPercorso(coordinateUtente, destinazione)` | — (percorso) | coordinateUtente: String, destinazione: String | UC.UT.04 |
| `acquisisciSceltaMetodo(idMetodoPagamento)` | void | idMetodoPagamento | UC.UT.05 |
| `getIdGestioneCorsa()` | — | — | — |
| `setIdGestioneCorsa(id)` | void | id | — |
| `getIdMetodoPagamento()` | — | — | — |
| `setIdMetodoPagamento(id)` | void | id | — |

#### Da GestorePagamento

| Metodo | Ritorno | Parametri | UC di Riferimento |
|--------|---------|-----------|-------------------|
| `pagamentoCorsa(idUtente, idMetodoPagamento, costo)` | bool | idUtente, idMetodoPagamento, costo: float | UC.UT.07 |
| `elaboraDatiCarta(idUtente, numCarta, dsCarta, cvv, intestatarioCarta)` | bool | idUtente, numCarta, dsCarta, cvv, intestatarioCarta | UC.UT.05 |
| `recuperaMetodiSalvati()` | MetodoPagamento | — | UC.UT.05 |
| `getIdGestorePagamento()` | — | — | — |
| `setIdGestorePagamento(id)` | void | id | — |

#### Da RicercaMezzi

| Metodo | Ritorno | Parametri | UC di Riferimento |
|--------|---------|-----------|-------------------|
| `visualizzaMezziVicini(coordinateUtente, raggiob)` | Mezzo | coordinateUtente: String, raggiob: float | UC.UT.01 |
| `visualizzaSpecifiche(idMezzo)` | Mezzo | idMezzo | UC.UT.01 |
| `getIdRicercaMezzi()` | — | — | — |
| `setIdRicercaMezzi(id)` | void | id | — |

#### Da GestionePrenotazione

| Metodo | Ritorno | Parametri | UC di Riferimento |
|--------|---------|-----------|-------------------|
| `inviaRichiestaPrenotazione()` | void | — | UC.UT.02 |
| `richiediLista()` | Prenotazione | — | UC.OP.03 |
| `annullaPrenotazione()` | bool | — | UC.OP.03 |
| `gestisciTimeout()` | void | — | UC.UT.02 |
| `notificaScadenzaTempo(idPrenotazione)` | void | idPrenotazione | UC.UT.02 |
| `concludiPrenotazione(idPrenotazione)` | void | idPrenotazione | UC.UT.03 |
| `getIdGestionePrenotazione()` | — | — | — |
| `setIdGestionePrenotazione(id)` | void | id | — |

#### Da GestioneAutenticazione

| Metodo | Ritorno | Parametri | UC di Riferimento |
|--------|---------|-----------|-------------------|
| `verificaValidita(nome, cognome, email, password, datanascita)` | RuoloAttore | nome, cognome, email, password, datanascita | UC.UT.08 |
| `invioCredenziali(email, password)` | RuoloAttore | email: String, password: String | UC.ATT.01 |
| `inviaRichiestaLogout(email)` | void | email: String | UC.UT.09 |
| `getIdGestioneAutenticazione()` | — | — | — |
| `setIdGestioneAutenticazione(id)` | void | id | — |

> **Nota:** `concludiPrenotazione(idPrenotazione)` confermato in Class_Diagram_Spec.cgd.md:473 e XMI classDiagram-v1.8-clean.uml. Il Class_Diagram_Spec ha priorità maggiore per i dettagli delle classi (HITL decisione 2026-06-25).

---

### 2.2 Moderazione Utente

| Proprietà | Valore |
|-----------|--------|
| **ID XMI** | `FSGPv7mD.AACARbj` |
| **Classi Realizzazione** | GestioneUtenti, GestionePrenotazione, GestioneAutenticazione |
| **Consumatore** | AppOperatoreSC |
| **Descrizione** | Interfaccia per operatore servizio clienti: moderazione account, gestione prenotazioni e autenticazione. |

#### Da GestioneUtenti

| Metodo | Ritorno | Parametri | UC di Riferimento |
|--------|---------|-----------|-------------------|
| `gestioneUtente(idUtente)` | bool | idUtente | UC.OP.02 |
| `cercaReport(idUtente)` | String | idUtente | UC.OP.02 |
| `getIdGestioneUtenti()` | — | — | — |
| `setIdGestioneUtenti(id)` | void | id | — |

#### Da GestionePrenotazione

| Metodo | Ritorno | Parametri | UC di Riferimento |
|--------|---------|-----------|-------------------|
| `richiediLista()` | Prenotazione | — | UC.OP.03 |
| `annullaPrenotazione()` | bool | — | UC.OP.03 |
| `notificaScadenzaTempo(idPrenotazione)` | void | idPrenotazione | UC.OP.03 |
| `inviaRichiestaPrenotazione()` | void | — | — |
| `gestisciTimeout()` | void | — | — |
| `concludiPrenotazione(idPrenotazione)` | void | idPrenotazione | UC.UT.03, UC.OP.03 |
| `getIdGestionePrenotazione()` | — | — | — |
| `setIdGestionePrenotazione(id)` | void | id | — |

#### Da GestioneAutenticazione

| Metodo | Ritorno | Parametri | UC di Riferimento |
|--------|---------|-----------|-------------------|
| `invioCredenziali(email, password)` | RuoloAttore | email: String, password: String | UC.ATT.01 |
| `inviaRichiestaLogout(email)` | void | email: String | UC.OP.05 |
| `verificaValidita(nome, cognome, email, password, datanascita)` | RuoloAttore | nome, cognome, email, password, datanascita | — |
| `getIdGestioneAutenticazione()` | — | — | — |
| `setIdGestioneAutenticazione(id)` | void | id | — |

---

### 2.3 Amministrazione Flotta

| Proprietà | Valore |
|-----------|--------|
| **ID XMI** | `BvUAwXmD.AACAQue` |
| **Classi Realizzazione** | GestioneFlotta, GestioneAutenticazione |
| **Consumatore** | AppOperatoreTecnico |
| **Descrizione** | Interfaccia per operatore tecnico: monitoraggio flotta, controlli remoti e autenticazione. |

#### Da GestioneFlotta

| Metodo | Ritorno | Parametri | UC di Riferimento |
|--------|---------|-----------|-------------------|
| `analisiStatoFlotta(idFlotta)` | bool | idFlotta: String | UC.OP.01 |
| `bloccaMezzo(idMezzo)` | bool | idMezzo | UC.OP.01 |
| `avviaManutenzione(idFlotta)` | bool | idFlotta: String | UC.OP.01 |
| `getCondizioniMezzi(idFlotta)` | Mezzo | idFlotta: String | UC.OP.01 |
| `getIdGestioneFlotta()` | — | — | — |
| `setIdGestioneFlotta(id)` | void | id | — |

#### Da GestioneAutenticazione

| Metodo | Ritorno | Parametri | UC di Riferimento |
|--------|---------|-----------|-------------------|
| `invioCredenziali(email, password)` | RuoloAttore | email: String, password: String | UC.ATT.01 |
| `inviaRichiestaLogout(email)` | void | email: String | UC.OP.04 |
| `verificaValidita(nome, cognome, email, password, datanascita)` | RuoloAttore | nome, cognome, email, password, datanascita | — |
| `getIdGestioneAutenticazione()` | — | — | — |
| `setIdGestioneAutenticazione(id)` | void | id | — |

---

### 2.4 Statistiche e Restrizioni

| Proprietà | Valore |
|-----------|--------|
| **ID XMI** | `ie1hKnmD.AACAQ4x` |
| **Classi Realizzazione** | GestioneStatistiche, GestioneAree, GestioneFlotta, GestioneAutenticazione |
| **Consumatore** | AppPA |
| **Descrizione** | Interfaccia per PA: analisi statistiche, gestione zone geografiche, diagnostica flotta e autenticazione. |

#### Da GestioneStatistiche

| Metodo | Ritorno | Parametri | UC di Riferimento |
|--------|---------|-----------|-------------------|
| `analisiTratte(dataInizio, dataFine)` | — (statistiche) | dataInizio: date, dataFine: date | UC.AP.01 |
| `generaFileStatistiche(corse)` | void | corse: Corsa | UC.AP.01 |
| `getIdGestioneStatistiche()` | — | — | — |
| `setIdGestioneStatistiche(id)` | void | id | — |

#### Da GestioneAree

| Metodo | Ritorno | Parametri | UC di Riferimento |
|--------|---------|-----------|-------------------|
| `aggiornaRestrizione(idArea, tipoRestrizione, noteRestrizione, zona)` | void | idArea, tipoRestrizione, noteRestrizione, zona | UC.AP.03 |
| `analisiConflitti(zona)` | bool | zona: ZonaGeografica | UC.AP.03 |
| `getZoneGeografiche()` | ZonaGeografica | — | UC.AP.03 |
| `getIdGestioneAree()` | — | — | — |
| `setIdGestioneAree(id)` | void | id | — |

#### Da GestioneFlotta

| Metodo | Ritorno | Parametri | UC di Riferimento |
|--------|---------|-----------|-------------------|
| `analisiStatoFlotta(idFlotta)` | bool | idFlotta: String | UC.AP.02 |
| `getCondizioniMezzi(idFlotta)` | Mezzo | idFlotta: String | UC.AP.02 |
| `bloccaMezzo(idMezzo)` | bool | idMezzo | — |
| `avviaManutenzione(idFlotta)` | bool | idFlotta: String | UC.AP.02 |
| `getIdGestioneFlotta()` | — | — | — |
| `setIdGestioneFlotta(id)` | void | id | — |

#### Da GestioneAutenticazione

| Metodo | Ritorno | Parametri | UC di Riferimento |
|--------|---------|-----------|-------------------|
| `invioCredenziali(email, password)` | RuoloAttore | email: String, password: String | UC.ATT.01 |
| `inviaRichiestaLogout(email)` | void | email: String | UC.AP.04 |
| `verificaValidita(nome, cognome, email, password, datanascita)` | RuoloAttore | nome, cognome, email, password, datanascita | — |
| `getIdGestioneAutenticazione()` | — | — | — |
| `setIdGestioneAutenticazione(id)` | void | id | — |

---

### 2.5 Gestione Sessioni

| Proprietà | Valore |
|-----------|--------|
| **ID XMI** | `2eaHp3mD.AACAQ9m` |
| **Classe Realizzazione** | GestioneAutenticazione |
| **Consumatore** | Autenticazione (View) |
| **Descrizione** | Interfaccia per login, registrazione e logout. Unica interfaccia Controller consumata da Autenticazione View. |

#### Da GestioneAutenticazione

| Metodo | Ritorno | Parametri | UC di Riferimento |
|--------|---------|-----------|-------------------|
| `verificaValidita(nome, cognome, email, password, datanascita)` | RuoloAttore | nome, cognome, email, password, datanascita | UC.UT.08 |
| `invioCredenziali(email, password)` | RuoloAttore | email: String, password: String | UC.ATT.01 |
| `inviaRichiestaLogout(email)` | void | email: String | UC.UT.09, UC.OP.04, UC.OP.05, UC.AP.04 |
| `getIdGestioneAutenticazione()` | — | — | — |
| `setIdGestioneAutenticazione(id)` | void | id | — |

---

## 3. Model-Provided Interfaces (fornite da Model → consumate da Controller)

Il Model espone le proprie operazioni tramite interfacce aggregate. Ogni interfaccia raggruppa le operazioni CRUD e di business delle entità afferenti. Le classi Model (12 entità) implementano collettivamente queste interfacce.

> **Nota:** I metodi elencati sono ricavati dalle classi Model in Master_Spec §2. L'interfaccia `Connessione Dati` (§4.3) media l'accesso al DBMS per la persistenza.

### 3.1 Gestione Dati Utente

| Proprietà | Valore |
|-----------|--------|
| **ID XMI** | `.XEXv7mD.AACARYL` |
| **Classi Realizzazione** | Attore, Utente, Operatore, PA |
| **Consumatore** | Controller (GestioneAutenticazione, GestioneUtenti) |
| **Descrizione** | Accesso a dati anagrafici, stato e report degli attori del sistema. |

**Metodi (dalle classi Model):**

| Metodo | Classe | Ritorno | Parametri |
|--------|--------|---------|-----------|
| `getId()` | Attore | — | — |
| `setId(id)` | Attore | void | id |
| `getEmail()` | Attore | String | — |
| `setEmail(email)` | Attore | void | email: String |
| `getPassword()` | Attore | String | — |
| `setPassword(password)` | Attore | void | password: String |
| `getRuolo()` | Attore | RuoloAttore | — |
| `setRuolo(ruolo)` | Attore | void | ruolo: RuoloAttore |
| `getIdUtente()` | Utente | — | — |
| `setIdUtente(idUtente)` | Utente | void | idUtente |
| `ricercaUtente(idUtente)` | Utente | Utente | idUtente |
| `creaAccountUtente(nome, cognome, email, password, datanascita)` | Utente | void | nome, cognome, email, password, datanascita |
| `azioneCorrettiva(azione)` | Utente | void | azione: String |
| `getStatoUtente()` | Utente | StatoUtente | — |
| `setStatoUtente(statoUtente)` | Utente | void | statoUtente: StatoUtente |
| `getReportUtente()` | Utente | String | — |
| `setReportUtente(reportUtente)` | Utente | void | reportUtente: String |
| `getNomeUtente()` | Utente | String | — |
| `setNomeUtente(nomeUtente)` | Utente | void | nomeUtente: String |
| `getCognomeUtente()` | Utente | String | — |
| `setCognomeUtente(cognomeUtente)` | Utente | void | cognomeUtente: String |
| `getTelefono()` | Utente | String | — |
| `setTelefono(telefono)` | Utente | void | telefono: String |
| `getCoordinateUtente()` | Utente | String | — |
| `setCoordinateUtente(coordinateUtente)` | Utente | void | coordinateUtente: String |
| `getNumMezziPrenotati()` | Utente | int | — |
| `setNumMezziPrenotati(numMezziPrenotati)` | Utente | void | numMezziPrenotati: int |
| `getTipo()` | Operatore | TipoOperatore | — |
| `setTipo(tipo)` | Operatore | void | tipo: TipoOperatore |
| `getIdPA()` | PA | — | — |
| `setIdPA(idPA)` | PA | void | idPA |

---

### 3.2 Gestione Dati Supporto

| Proprietà | Valore |
|-----------|--------|
| **ID XMI** | `wpvLX3mD.AACAQuK` |
| **Classi Realizzazione** | Segnalazione, Prenotazione, ZonaGeografica, Transito |
| **Consumatore** | Controller (GestioneFlotta, GestionePrenotazione, GestioneAree, GestioneStatistiche) |
| **Descrizione** | Accesso a dati di supporto: segnalazioni, prenotazioni, zone geografiche e transiti. |

**Metodi (dalle classi Model):**

| Metodo | Classe | Ritorno | Parametri |
|--------|--------|---------|-----------|
| `getIdSegnalazione()` | Segnalazione | — | — |
| `setIdSegnalazione(idSegnalazione)` | Segnalazione | void | idSegnalazione |
| `getIdMezzo()` | Segnalazione | — | — |
| `setIdMezzo(idMezzo)` | Segnalazione | void | idMezzo |
| `getStato()` | Segnalazione | StatoSegnalazione | — |
| `setStato(stato)` | Segnalazione | void | stato: StatoSegnalazione |
| `getOra()` | Segnalazione | time | — |
| `setOra(ora)` | Segnalazione | void | ora: time |
| `getData()` | Segnalazione | date | — |
| `setData(data)` | Segnalazione | void | data: date |
| `creaSegnalazione(idMezzo, statoS, data, ora, note)` | Segnalazione | void | idMezzo, statoS, data: date, ora: time, note: String |
| `getIdPrenotazione()` | Prenotazione | — | — |
| `setIdPrenotazione(idPrenotazione)` | Prenotazione | void | idPrenotazione |
| `getStato()` | Prenotazione | StatoPrenotazione | — |
| `setStato(stato)` | Prenotazione | void | stato: StatoPrenotazione |
| `getOrarioInizio()` | Prenotazione | time | — |
| `setOrarioInizio(orarioInizio)` | Prenotazione | void | orarioInizio: time |
| `getIdUtente()` | Prenotazione | — | — |
| `setIdUtente(idUtente)` | Prenotazione | void | idUtente |
| `getIdMezzo()` | Prenotazione | — | — |
| `setIdMezzo(idMezzo)` | Prenotazione | void | idMezzo |
| `getData()` | Prenotazione | date | — |
| `setData(data)` | Prenotazione | void | data: date |
| `creaPrenotazione(idMezzo, idUtente, orarioInizio)` | Prenotazione | void | idMezzo, idUtente, orarioInizio: time |
| `getPrenotazioneByStato(stato)` | Prenotazione | Prenotazione | stato: StatoPrenotazione |
| `getIdArea()` | ZonaGeografica | — | — |
| `setIdArea(idArea)` | ZonaGeografica | void | idArea |
| `getTipoRestrizione()` | ZonaGeografica | TipoRestrizione | — |
| `setTipoRestrizione(tipoRestrizione)` | ZonaGeografica | void | tipoRestrizione: TipoRestrizione |
| `getNoteRestrizione()` | ZonaGeografica | String | — |
| `setNoteRestrizione(noteRestrizione)` | ZonaGeografica | void | noteRestrizione: String |
| `getZona()` | ZonaGeografica | LineString | — |
| `setZona(zona)` | ZonaGeografica | void | zona: LineString |
| `getZone()` | ZonaGeografica | ZonaGeografica | — |
| `verificaSovrapposizioni(zona)` | ZonaGeografica | bool | zona: ZonaGeografica |
| `checkArea(coordinateUtente)` | ZonaGeografica | bool | coordinateUtente: String |
| `creaZonaGeografica(idArea, tipoRestrizione, noteRestrizione, zona)` | ZonaGeografica | void | idArea, tipoRestrizione, noteRestrizione, zona |
| `getRestrizioniZona(coordinateUtente)` | ZonaGeografica | ZonaGeografica | coordinateUtente: String |
| `getIdCorsa()` | Transito | — | — |
| `setIdCorsa(idCorsa)` | Transito | void | idCorsa |
| `getIdArea()` | Transito | — | — |
| `setIdArea(idArea)` | Transito | void | idArea |
| `getTransitiByCorsa(idCorsa)` | Transito | Transito | idCorsa |

> **Nota di disambiguazione:** `getStato()` e `getIdMezzo()` compaiono in più classi Model (Segnalazione, Prenotazione) con firme identiche ma significati diversi. Nell'implementazione, il polimorfismo per enclosing class (es. interfacce distinte per tipo di entità) risolve l'ambiguità — design non ancora validato.

---

### 3.3 Gestione Dati Corsa

| Proprietà | Valore |
|-----------|--------|
| **ID XMI** | `jYqrX3mD.AACAQux` |
| **Classi Realizzazione** | Corsa, Mezzo, MetodoPagamento |
| **Consumatore** | Controller (GestioneCorsa, GestorePagamento, RicercaMezzi, GestioneFlotta, GestioneStatistiche) |
| **Descrizione** | Accesso a dati di corsa, mezzi e metodi di pagamento. |

**Metodi (dalle classi Model):**

| Metodo | Classe | Ritorno | Parametri |
|--------|--------|---------|-----------|
| `getIdCorsa()` | Corsa | — | — |
| `setIdCorsa(idCorsa)` | Corsa | void | idCorsa |
| `getCosto()` | Corsa | float | — |
| `setCosto(costo)` | Corsa | void | costo: float |
| `getOrarioInizio()` | Corsa | time | — |
| `setOrarioInizio(orarioInizio)` | Corsa | void | orarioInizio: time |
| `getOrarioFine()` | Corsa | time | — |
| `setOrarioFine(orarioFine)` | Corsa | void | orarioFine: time |
| `getCoordinatePartenza()` | Corsa | String | — |
| `setCoordinatePartenza(coordinatePartenza)` | Corsa | void | coordinatePartenza: String |
| `getCoordinateArrivo()` | Corsa | String | — |
| `setCoordinateArrivo(coordinateArrivo)` | Corsa | void | coordinateArrivo: String |
| `creaCorsa(orarioinizio, coordinatePartenza, idUtente, idMezzo)` | Corsa | void | orarioinizio, coordinatePartenza, idUtente, idMezzo |
| `ricercaCorsa(idCorsa)` | Corsa | Corsa | idCorsa |
| `getCorseByPeriodo(dataInizio, dataFine)` | Corsa | Corsa | dataInizio: date, dataFine: date |
| `aggiornaCosto(costo)` | Corsa | void | costo: float |
| `getIdMetodoPagamento()` | Corsa | — | — |
| `setIdMetodoPagamento(idMetodoPagamento)` | Corsa | void | idMetodoPagamento |
| `getIdUtente()` | Corsa | — | — |
| `setIdUtente(idUtente)` | Corsa | void | idUtente |
| `getIdMezzo()` | Mezzo | — | — |
| `setIdMezzo(idMezzo)` | Mezzo | void | idMezzo |
| `getCoordinateMezzo()` | Mezzo | String | — |
| `setCoordinateMezzo(coordinateMezzo)` | Mezzo | void | coordinateMezzo: String |
| `getStato()` | Mezzo | StatoMezzo | — |
| `setStato(stato)` | Mezzo | void | stato: StatoMezzo |
| `getAutonomia()` | Mezzo | float | — |
| `setAutonomia(autonomia)` | Mezzo | void | autonomia: float |
| `getCostoOrario()` | Mezzo | float | — |
| `setCostoOrario(costoOrario)` | Mezzo | void | costoOrario: float |
| `getVelocitaMax()` | Mezzo | float | — |
| `setVelocitaMax(velocitaMax)` | Mezzo | void | velocitaMax: float |
| `getCondizione()` | Mezzo | String | — |
| `setCondizione(condizione)` | Mezzo | void | condizione: String |
| `getTipo()` | Mezzo | String | — |
| `setTipo(tipo)` | Mezzo | void | tipo: String |
| `getIdFlotta()` | Mezzo | String | — |
| `setIdFlotta(idFlotta)` | Mezzo | void | idFlotta: String |
| `getTempoDisponibilita()` | Mezzo | time | — |
| `setTempoDisponibilita(tempoDisponibilita)` | Mezzo | void | tempoDisponibilita: time |
| `getMezzibyFlotta(idFlotta)` | Mezzo | Mezzo | idFlotta: String |
| `getMezziInArea(coordinateUtente, raggio)` | Mezzo | Mezzo | coordinateUtente: String, raggio: float |
| `getDettagliMezzo()` | Mezzo | Mezzo | — |
| `getIdMetodoPagamento()` | MetodoPagamento | — | — |
| `setIdMetodoPagamento(idMetodoPagamento)` | MetodoPagamento | void | idMetodoPagamento |
| `getNumCarta()` | MetodoPagamento | String | — |
| `setNumCarta(numCarta)` | MetodoPagamento | void | numCarta: String |
| `getIntestatarioCarta()` | MetodoPagamento | String | — |
| `setIntestatarioCarta(intestatarioCarta)` | MetodoPagamento | void | intestatarioCarta: String |
| `creaMetodoPagamento(numCarta, intestatarioCarta)` | MetodoPagamento | void | numCarta: String, intestatarioCarta: String |
| `controllaMetodoEsistente(numCarta)` | MetodoPagamento | bool | numCarta: String |
| `getMetodoByUtente(idUtente)` | MetodoPagamento | MetodoPagamento | idUtente |

---

## 4. External System Interfaces

Tutti i sistemi esterni sono simulati (progetto universitario — chiarimenti-vari.md punto 16). I metodi includono getter/setter per completezza dell'interfaccia.

### 4.1 API Mappa

| Proprietà | Valore |
|-----------|--------|
| **ID XMI** | `xP59v7mD.AACARGb` |
| **Classe Realizzazione** | ServizioMappa |
| **Consumatore** | RicercaMezzi (Controller) |
| **Descrizione** | Servizio di geolocalizzazione e routing esterno per il calcolo di percorsi tra coordinate. |

**Metodi:**

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getPercorso(coordinateIniziali, coordinateFinali, restrizioni)` | — (datiPercorso) | coordinateIniziali: String, coordinateFinali: String, restrizioni: ZonaGeografica |
| `getIdServizioMappa()` | — | — |
| `setIdServizioMappa(id)` | void | id |

> **Nota:** Il parametro `coordinateFinali` corregge il typo XMI `coorfinateFinali` (claim-7b4d1e022, confermato dal team).

---

### 4.2 API Pagamento

| Proprietà | Valore |
|-----------|--------|
| **ID XMI** | `Z.W3v7mD.AACARaG` |
| **Classe Realizzazione** | GatewayPagamento |
| **Consumatore** | GestorePagamento (Controller) |
| **Descrizione** | Processore di pagamento esterno per convalida carte e transazioni. |

**Metodi:**

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `effettuaPagamento(idMetodoPagamento, idCorsa)` | bool | idMetodoPagamento, idCorsa |
| `convalidaCarta(numCarta, dsCarta, cvv, intestatarioCarta)` | bool | numCarta, dsCarta, cvv, intestatarioCarta |
| `getIdGatewayPagamento()` | — | — |
| `setIdGatewayPagamento(id)` | void | id |

> **Nota:** Il nome `effettuaPagamento` corregge il typo XMI `EffettuaPagamento` (claim-5d8e2f020, confermato dal team).

---

### 4.3 Connessione Dati

| Proprietà | Valore |
|-----------|--------|
| **ID XMI** | `jvJnv7mD.AACARXl` |
| **Classe Realizzazione** | DBMS |
| **Consumatore** | Model (tutte le 12 entità) |
| **Descrizione** | Interfaccia CRUD standard verso database relazionale per la persistenza dei dati. |

**Metodi concettuali (design CRUD):**

| Operazione | Comportamento |
|------------|---------------|
| `Create` | Inserimento nuove entità nel database |
| `Read` | Lettura entità per chiave primaria o criteri di ricerca |
| `Update` | Aggiornamento attributi di entità esistenti |
| `Delete` | Rimozione entità dal database |

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `getIdDBMS()` | — | — |
| `setIdDBMS(id)` | void | id |

> **Design:** Interfaccia generica CRUD (claim-4c8a2f023). Le operazioni specifiche sono implementate dalle classi Model che chiamano il DBMS per la persistenza. I metodi `getIdDBMS()` / `setIdDBMS(id)` sono presenti nell'XMI ma non costituiscono l'interfaccia funzionale — sono inclusi per completezza di tracciabilità.

---

### 4.4 API Controllo

| Proprietà | Valore |
|-----------|--------|
| **ID XMI** | `3P9eKnmD.AACAQlF` |
| **Classe Realizzazione** | Mezzo : IoT |
| **Consumatore** | GestioneCorsa (Controller) |
| **Descrizione** | Interfaccia fisica col veicolo per blocco/sblocco remoto. Orphan nell'XMI del diagramma componenti (nessun realization collegato a Mezzo:IoT). |
| **Stato** | Funzionale — inclusa nonostante lo stato orphan XMI (Component_Diagram_Spec §4.4) |

**Metodi:**

| Metodo | Ritorno | Parametri |
|--------|---------|-----------|
| `bloccoMezzoFisico(idMezzo)` | bool | idMezzo |
| `sbloccoMezzoFisico(idMezzo)` | bool | idMezzo |
| `getIdMezzoIoT()` | — | — |
| `setIdMezzoIoT(id)` | void | id |

---

## 5. Riepilogo Interfacce Funzionali

| # | Interfaccia | Categoria | Classe Realizzazione | Consumatore |
|---|-------------|-----------|---------------------|-------------|
| 1 | Aggiornamenti Corsa | View-provided | AppUtente | Controller |
| 2 | Stato Flotta | View-provided | AppOperatoreTecnico | Controller |
| 3 | Diagnostica | View-provided | AppPA | Controller |
| 4 | Eventi Utente | View-provided | AppOperatoreSC | Controller |
| 5 | Stato Sessione | View-provided | Autenticazione | Controller |
| 6 | Gestione Corsa | Controller-provided | GestioneCorsa, GestorePagamento, RicercaMezzi, GestionePrenotazione, GestioneAutenticazione | AppUtente |
| 7 | Moderazione Utente | Controller-provided | GestioneUtenti, GestionePrenotazione, GestioneAutenticazione | AppOperatoreSC |
| 8 | Amministrazione Flotta | Controller-provided | GestioneFlotta, GestioneAutenticazione | AppOperatoreTecnico |
| 9 | Statistiche e Restrizioni | Controller-provided | GestioneStatistiche, GestioneAree, GestioneFlotta, GestioneAutenticazione | AppPA |
| 10 | Gestione Sessioni | Controller-provided | GestioneAutenticazione | Autenticazione |
| 11 | Gestione Dati Utente | Model-provided | Attore, Utente, Operatore, PA | Controller |
| 12 | Gestione Dati Supporto | Model-provided | Segnalazione, Prenotazione, ZonaGeografica, Transito | Controller |
| 13 | Gestione Dati Corsa | Model-provided | Corsa, Mezzo, MetodoPagamento | Controller |
| 14 | API Mappa | External | ServizioMappa | RicercaMezzi |
| 15 | API Pagamento | External | GatewayPagamento | GestorePagamento |
| 16 | Connessione Dati | External | DBMS | Model |
| 17 | API Controllo | External | Mezzo : IoT | GestioneCorsa |

---

## 6. Metadati Documento

| Metrica | Valore | Note |
|---------|--------|------|
| Interfacce funzionali documentate | 17 | Confermato da Component_Diagram_Spec §9 |
| Metodi pubblici nelle tabelle (entry count) | est. ~280 | Include duplicati tra interfacce (stessa metodo in più interfacce) |
| Metodi privati esclusi (View) | 23 | Somma diretta delle exclusion list per sezione (11+3+5+3+1) |
| Classi realizzatrici (unique) | 26 | 5 View + 9 Controller + 12 Model (esclusi duplicati XMI) |
| UC di riferimento nel cross-reference | 19 | Tutti i 19 UC documentati (Master_Spec §7) |
| XMI ID tracciati nelle tabelle proprietà | 17 | 5 View + 5 Controller + 3 Model + 4 External |
| Claim HITL risolti | 2 | Entrambi confermati in HITL Round A |

---

## HITL Verification Record

### Round A: Derived Data Confirmation

Tutti i dati strutturali sono stati derivati dalle tre fonti primarie:
- **View interfaces (§1):** Metodi public/private da Class_Diagram_Spec §3 (visibilità esplicita)
- **Controller interfaces (§2):** Raggruppamento logico basato su Master_Spec §6 (Dipendenze View → Controller) + metodi da Class_Diagram_Spec §2
- **Model interfaces (§3):** Entità raggruppate per interfaccia target da Component_Diagram_Spec §3.2 + metodi da Master_Spec §2
- **External interfaces (§4):** Metodi da Master_Spec §5 + Component_Diagram_Spec §4

### Round B: True HITL Verification

| Claim ID | Testo | Risoluzione | Confermato Da | Data |
|----------|-------|-------------|---------------|------|
| claim-iface-qualificazione | Solo metodi PUBLIC nelle interfacce. | CONFERMATA — regola architetturale valida. | Team Cofee Coders | 2026-06-25 |
| claim-iface-gestione-corsa-concludi | concludiPrenotazione() presente in Class_Diagram_Spec ma assente in Master_Spec. | CONFERMATA — Class_Diagram_Spec ha priorità per dettagli classi. Metodo incluso. | Team Cofee Coders | 2026-06-25 |

---

<!-- CLARITY_GATE_END -->
Clarity Gate: CLEAR | REVIEWED — 2/2 claim HITL confermati, 0 pending.

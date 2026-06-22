```yaml
status: SUCCESS_WITH_INFERENCE
extractor_version: "2.0-unsafe"
parsed_entities: 28
```

# UML-XMI Semantic Extraction Report (Unsafe Mode)

**Source Model:** Diagramma Casi d'uso  
**Exporter:** Visual Paradigm 7.0.2  
**XMI Version:** 2.1  
**Profile:** Diagramma_Casi_d_uso_profile (stereotipi: UseCase, CaseStory, Context)

---

## 1. System Boundaries & Actors

Il modello definisce un unico confine di sistema esplicito: il package **Smart Mobility System** (`oJU7knmD.AACAQl.`, tipo `uml:Model`). Tutti i casi d'uso risiedono all'interno di questo confine.

| Attore | xmi:id | Descrizione |
|---|---|---|
| **Utente** | `DtcDknmD.AACARAz` |   Utente finale della piattaforma di mobilità condivisa. Interagisce col sistema per cercare mezzi, prenotare, gestire corse, pagare e richiedere sospensioni. |
| **PA** | `hxcTknmD.AACARG_` |   Pubblica Amministrazione. Attore istituzionale che monitora statistiche e analisi sull'utilizzo del servizio, analizza lo stato della flotta e gestisce le restrizioni geografiche operative. |
| **Operatore Tecnico** | `FA3zknmD.AACARIt` |   Operatore specializzato nella manutenzione e logistica. Gestisce la flotta di veicoli (stato operativo, manutenzione, rilocazione). |
| **Operatore Servizio Clienti** | `tkvzknmD.AACARI2` |   Operatore di supporto customer care. Modera gli utenti e amministra le prenotazioni per conto del cliente. |

---

## 2. Core Entities & Components

Tutti gli elementi seguenti sono di tipo `uml:UseCase` contenuti nel package **Smart Mobility System**.

### 2.1 MetodoPagamento
- **xmi:id:** `d_KDknmD.AACARBB`
- **Tipo:** UseCase
- **Responsabilità:**   Gestione dei metodi di pagamento dell'utente: registrazione, modifica, eliminazione e selezione del metodo di pagamento preferito (carta di credito, wallet digitale, ecc.) per le transazioni legate alle corse e prenotazioni.

### 2.2 GestioneCorsa
- **xmi:id:** `qmijknmD.AACARCz`
- **Tipo:** UseCase
- **Responsabilità:**   Gestione del ciclo di vita di una corsa attiva: avvio, monitoraggio in tempo reale, terminazione e calcolo del costo. Include la logica di tracciamento GPS e l'aggiornamento dello stato del mezzo durante l'utilizzo.

### 2.3 PrenotazioneMezzo
- **xmi:id:** `MSajknmD.AACARDF`
- **Tipo:** UseCase
- **Responsabilità:**   Prenotazione anticipata di un mezzo di trasporto condiviso. Include la selezione del mezzo disponibile, la conferma temporale della prenotazione e la gestione di eventuali scadenze di riserva.

### 2.4 RicercaMezzi
- **xmi:id:** `FgmjknmD.AACARDg`
- **Tipo:** UseCase
- **Responsabilità:**   Ricerca e localizzazione dei mezzi disponibili in prossimità dell'utente o in un'area specificata. Restituisce una lista filtrata per tipo di veicolo, disponibilità e distanza.

### 2.5 OttimizzazionePercorso
- **xmi:id:** `sGhjknmD.AACARD2`
- **Tipo:** UseCase
- **Responsabilità:**   Calcolo e suggerimento del percorso ottimale per raggiungere la destinazione, tenendo conto di traffico, distanza, consumo energetico del mezzo e restrizioni geografiche attive.

### 2.6 MonitoraggioStatisticheAnalisi
- **xmi:id:** `TsrjknmD.AACARFl`
- **Tipo:** UseCase
- **Responsabilità:**   Dashboard di monitoraggio e reporting statistico sull'utilizzo del servizio di mobilità. Fornisce KPI, trend di utilizzo, analisi delle tratte più frequentate e metriche di performance del servizio alla Pubblica Amministrazione.

### 2.7 AnalisiStatoFlotta
- **xmi:id:** `nI3jknmD.AACARF3`
- **Tipo:** UseCase
- **Responsabilità:**   Analisi aggregata dello stato operativo della flotta veicoli: livelli di carica/carburante, stato di manutenzione, distribuzione geografica e tasso di utilizzo. Destinata alla PA per decisioni di governance.

### 2.8 RestrizioniGeografiche
- **xmi:id:** `DUATknmD.AACARGJ`
- **Tipo:** UseCase
- **Responsabilità:**   Definizione e gestione delle zone operative: aree di parcheggio consentite, zone a traffico limitato, geo-fence di confine del servizio. La PA configura le regole territoriali che il sistema applica automaticamente.

### 2.9 GestioneFlotta
- **xmi:id:** `PDqTknmD.AACARHp`
- **Tipo:** UseCase
- **Responsabilità:**   Gestione operativa della flotta di veicoli: registrazione nuovi mezzi, dismissione, assegnazione a zone operative, pianificazione manutenzioni e rilocazione dei veicoli. Responsabilità dell'Operatore Tecnico.

### 2.10 ModerazioneUtente
- **xmi:id:** `JWWTknmD.AACARH_`
- **Tipo:** UseCase
- **Responsabilità:**   Gestione disciplinare degli utenti: revisione di comportamenti scorretti, emissione di avvertimenti, sospensione o ban di account, gestione di segnalazioni e reclami. Eseguita dall'Operatore Servizio Clienti.

### 2.11 AmministrazionePrenotazioni
- **xmi:id:** `mCxTknmD.AACARIR`
- **Tipo:** UseCase
- **Responsabilità:**   Gestione back-office delle prenotazioni: visualizzazione, modifica, cancellazione e risoluzione di conflitti sulle prenotazioni per conto degli utenti. Eseguita dall'Operatore Servizio Clienti.

### 2.12 SospensioneCorsa
- **xmi:id:** `aF4vqnmD.AACAQjg`
- **Tipo:** UseCase
- **Responsabilità:**   Sospensione temporanea di una corsa in corso (es. pausa con mantenimento della riserva del mezzo). Include la logica di timeout massimo e la ripresa o terminazione automatica della corsa.

---

## 3. Use Case Logic & Flows

> **Nota:** Il modello XMI non contiene relazioni `<<include>>` o `<<extend>>` esplicite tra i casi d'uso. Tutte le relazioni presenti nel modello sono associazioni binarie tra attori e casi d'uso. Non sono stati trovati `ownedComment`, `ownedRule` o `Documentation` su nessun elemento.

### Use Case: MetodoPagamento
- **Business Logic:**   L'utente accede alla sezione pagamenti, visualizza i metodi registrati, può aggiungerne uno nuovo (tramite provider di pagamento esterno), modificare i dati o eliminare un metodo. Il sistema valida i dati della carta/wallet prima del salvataggio.
- **Includes:** Nessuno
- **Extends:** Nessuno

### Use Case: GestioneCorsa
- **Business Logic:**   L'utente avvia la corsa dopo aver sbloccato un mezzo prenotato o trovato tramite ricerca. Il sistema traccia posizione e durata in tempo reale. A fine corsa, il sistema calcola il costo in base a tariffe tempo/distanza e avvia il processo di pagamento automatico.
- **Includes:** Nessuno
- **Extends:** Nessuno

### Use Case: PrenotazioneMezzo
- **Business Logic:**   L'utente seleziona un mezzo disponibile dalla mappa e lo prenota per un intervallo temporale. Il sistema blocca il mezzo rendendolo non disponibile per altri utenti. Una prenotazione non utilizzata entro un timeout decade automaticamente.
- **Includes:** Nessuno
- **Extends:** Nessuno

### Use Case: RicercaMezzi
- **Business Logic:**   L'utente apre la mappa o effettua una ricerca testuale/filtrata. Il sistema interroga il database dei mezzi disponibili in base alla posizione GPS dell'utente, al raggio di ricerca e ai filtri (tipo mezzo, autonomia). Restituisce risultati ordinati per prossimità.
- **Includes:** Nessuno
- **Extends:** Nessuno

### Use Case: OttimizzazionePercorso
- **Business Logic:**   L'utente inserisce una destinazione. Il sistema calcola il percorso ottimale utilizzando algoritmi di routing che considerano traffico in tempo reale, distanza, restrizioni geografiche attive e autonomia residua del mezzo. Propone alternative e stima il tempo di arrivo.
- **Includes:** Nessuno
- **Extends:** Nessuno

### Use Case: MonitoraggioStatisticheAnalisi
- **Business Logic:**   La PA accede a una dashboard analitica che aggrega i dati di utilizzo del servizio: numero di corse, ricavi, distribuzione oraria, zone più attive, feedback utenti. I dati sono aggiornati periodicamente e filtrabili per intervallo temporale e area geografica.
- **Includes:** Nessuno
- **Extends:** Nessuno

### Use Case: AnalisiStatoFlotta
- **Business Logic:**   La PA visualizza un report sullo stato operativo della flotta: percentuale di mezzi attivi/inattivi/in manutenzione, distribuzione geografica, livelli medi di carica, storico dei guasti. Il sistema supporta alert automatici su soglie critiche.
- **Includes:** Nessuno
- **Extends:** Nessuno

### Use Case: RestrizioniGeografiche
- **Business Logic:**   La PA definisce zone operative tramite interfaccia cartografica: aree di parcheggio valide, zone vietate, confini del servizio (geo-fence). Il sistema applica queste restrizioni in tempo reale bloccando l'avvio o il parcheggio di mezzi in zone non autorizzate.
- **Includes:** Nessuno
- **Extends:** Nessuno

### Use Case: GestioneFlotta
- **Business Logic:**   L'Operatore Tecnico aggiunge, modifica o dismette mezzi nel sistema. Pianifica interventi di manutenzione ordinaria e straordinaria, rilocazione fisica dei mezzi per bilanciamento territoriale e aggiorna lo stato operativo di ciascun veicolo.
- **Includes:** Nessuno
- **Extends:** Nessuno

### Use Case: ModerazioneUtente
- **Business Logic:**   L'Operatore Servizio Clienti riceve segnalazioni di comportamenti scorretti (danni, abbandono, uso improprio). Esamina le evidenze, emette sanzioni graduali (avvertimento → sospensione temporanea → ban permanente) e gestisce le contro-deduzioni dell'utente.
- **Includes:** Nessuno
- **Extends:** Nessuno

### Use Case: AmministrazionePrenotazioni
- **Business Logic:**   L'Operatore Servizio Clienti accede alla lista delle prenotazioni, può visualizzare dettagli, modificare orari, cancellare prenotazioni in conflitto o risolvere dispute tra utenti. Il sistema registra tutte le azioni di back-office a fini di audit.
- **Includes:** Nessuno
- **Extends:** Nessuno

### Use Case: SospensioneCorsa
- **Business Logic:**   L'utente attiva la sospensione durante una corsa in corso. Il mezzo resta riservato e il conteggio tariffario passa a una tariffa di sosta ridotta. Esiste un timeout massimo di sospensione superato il quale la corsa viene automaticamente terminata.
- **Includes:** Nessuno
- **Extends:** Nessuno

---

## 4. Architectural Relationships

Relazioni estratte dalle associazioni `uml:Association` nel package **Smart Mobility System**:

| # | Entità A | Entità B | xmi:id Associazione | Spiegazione |
|---|---|---|---|---|
| 1 | **Utente** | **RicercaMezzi** | `tjMHknmD.AACAQrR` |   L'utente avvia la ricerca di mezzi disponibili per localizzare un veicolo da utilizzare. |
| 2 | **Utente** | **OttimizzazionePercorso** | `1DsHknmD.AACAQrZ` |   L'utente richiede il calcolo del percorso ottimale verso la destinazione desiderata. |
| 3 | **Operatore Servizio Clienti** | **ModerazioneUtente** | `ZeCXknmD.AACAQvh` |   L'operatore customer care esegue azioni di moderazione disciplinare sugli utenti segnalati. |
| 4 | **Operatore Servizio Clienti** | **AmministrazionePrenotazioni** | `3oMXknmD.AACAQvN` |   L'operatore customer care gestisce e modifica le prenotazioni per conto degli utenti. |
| 5 | **Utente** | **MetodoPagamento** | `bjGDknmD.AACARBZ` |   L'utente gestisce i propri metodi di pagamento registrati nel sistema. |
| 6 | **Utente** | **PrenotazioneMezzo** | `QADjknmD.AACAREz` |   L'utente prenota un mezzo di trasporto condiviso per un utilizzo futuro. |
| 7 | **Utente** | **GestioneCorsa** | `9Pv7knmD.AACAQqv` |   L'utente avvia, monitora e termina una corsa con il mezzo selezionato. |
| 8 | **PA** | **MonitoraggioStatisticheAnalisi** | `H22nknmD.AACAQsa` |   La Pubblica Amministrazione accede alle statistiche e analisi di utilizzo del servizio. |
| 9 | **PA** | **AnalisiStatoFlotta** | `uvenknmD.AACAQsw` |   La Pubblica Amministrazione monitora lo stato operativo aggregato della flotta veicoli. |
| 10 | **PA** | **RestrizioniGeografiche** | `voBnknmD.AACAQs4` |   La Pubblica Amministrazione definisce e gestisce le zone operative e le restrizioni territoriali. |
| 11 | **Operatore Tecnico** | **GestioneFlotta** | `TZrnknmD.AACAQt.` |   L'Operatore Tecnico esegue la gestione operativa della flotta (manutenzione, rilocazione, dismissione). |
| 12 | **Utente** | **SospensioneCorsa** | `rdOvqnmD.AACAQnl` |   L'utente sospende temporaneamente una corsa in corso, mantenendo la prenotazione del mezzo. |

---

## 5. Architectural Constraints & Invariants

> **Nota:** Il modello XMI non contiene vincoli espliciti (`ownedRule`, `ownedConstraint`). I seguenti vincoli sono dedotti dal dominio applicativo.

1. **  Vincolo di unicità della corsa attiva:** Un utente non può avere più di una corsa attiva contemporaneamente. GestioneCorsa e SospensioneCorsa operano sulla stessa istanza di corsa.

2. **  Vincolo di disponibilità del mezzo:** Un mezzo prenotato (PrenotazioneMezzo) o in corsa (GestioneCorsa) non è disponibile per RicercaMezzi di altri utenti. La disponibilità è un invariante di stato esclusivo.

3. **  Vincolo di geo-fence:** OttimizzazionePercorso deve rispettare i confini definiti da RestrizioniGeografiche. Nessun percorso calcolato può transitare o terminare in zone vietate.

4. **  Vincolo di pagamento valido:** L'avvio di una GestioneCorsa o PrenotazioneMezzo richiede almeno un MetodoPagamento valido e attivo registrato dall'utente.

5. **  Vincolo di timeout sospensione:** SospensioneCorsa ha un limite temporale massimo. Superato il timeout, il sistema termina automaticamente la corsa.

6. **  Vincolo di separazione dei ruoli:** Gli attori PA, Operatore Tecnico e Operatore Servizio Clienti operano su casi d'uso distinti e non sovrapposti, rispettando il principio di separazione delle responsabilità (SoC).

7. **  Vincolo di auditabilità:** Le azioni di AmministrazionePrenotazioni e ModerazioneUtente eseguite dall'Operatore Servizio Clienti devono essere tracciabili e registrate per finalità di audit.

---

## Appendice: Profilo UML Applicato

Il profilo **Diagramma_Casi_d_uso_profile** definisce i seguenti stereotipi estensivi:

| Stereotipo | Attributi del profilo |
|---|---|
| **«UseCase»** | Level, Complexity, UseCaseStatus, ImplementationStatus, Preconditions, Post-conditions, Author, Assumptions |
| **«CaseStory»** | (nessun attributo aggiuntivo) |
| **«Context»** | (nessun attributo aggiuntivo) |

> **Nota:** Tutti i 12 casi d'uso hanno lo stereotipo `«UseCase»` applicato, ma tutti gli attributi del profilo (Preconditions, Post-conditions, Level, Complexity, ecc.) risultano **vuoti** nel modello XMI. Non è stato possibile estrarre dati strutturati aggiuntivi dal profilo.

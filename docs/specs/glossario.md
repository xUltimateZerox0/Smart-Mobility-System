# Glossario Formale del Sistema: Smart Mobility System

Il presente documento costituisce il glossario formale di tutte le entità, classi, attributi, attori e casi d'uso estratti o inferiti dal modello UML-XMI (Use Case, Component Diagram e Class Diagram). Fornisce una definizione univoca e priva di ambiguità per ogni elemento strutturale della piattaforma.

---

## 1. Attori del Sistema

* **Attore (Concetto astratto):** Qualsiasi entità, umana o digitale, esterna al confine di sistema che interagisce direttamente con esso inviando o ricevendo informazioni.
* **Utente:** L'utente finale e fruitore della piattaforma. Corrisponde al cittadino che, previa registrazione, si interfaccia col sistema per cercare mezzi disponibili, prenotarli, effettuare corse e autorizzare i pagamenti.
* **PA (Pubblica Amministrazione):** Ente governativo, municipale o istituzionale. Gode di permessi speciali per la visualizzazione di dashboard aggregate (es. flussi di traffico) e per l'applicazione di normative stradali via piattaforma (es. istituzione di zone a traffico limitato).
* **Operatore Tecnico:** Personale manutentivo incaricato di operare sul territorio. Interagisce col sistema per recuperare veicoli guasti (scarichi o danneggiati), segnalare interventi manutentivi ed effettuare spostamenti (rilocazione veicoli).
* **Operatore Servizio Clienti (SC):** Personale di back-office incaricato di gestire il supporto agli utenti. Ha privilegi per visionare log transazionali, risolvere anomalie su prenotazioni in corso e sanzionare/moderare utenti non in regola.

---

## 2. Entità di Dominio (Model)

### 2.1 Gerarchia Attori
* **Attore (Classe Astratta):** Rappresentazione dati base della gerarchia utenti.
  * `id`: Identificativo primario e autogenerato.
  * `email`: Indirizzo di posta elettronica per comunicazioni e login.
* **Utente (Classe):** Estensione dell'attore base specifica per il cliente della piattaforma.
  * `coordinateUtente`: Posizione geospaziale (JTS Point) corrente del dispositivo del cliente.
  * `numMezziPrenotati`: Contatore temporaneo indicante quante riserve attive l'utente sta mantenendo.
  * `nomeUtente`, `cognomeUtente`, `telefono`: Dati anagrafici primari.
  * `reportUtente`: Campo documentale aggregante feedback, valutazioni o strike comportamentali dell'utente.
* **Operatore / PA (Classi Marker):** Sottoclassi di `Attore` prive di proprietà aggiuntive esplicite, utilizzate per implementare meccanismi di RBAC (Role-Based Access Control).

### 2.2 Entità Veicolari e Transazionali
* **Flotta:** Contenitore logico-amministrativo usato per partizionare i veicoli dell'azienda secondo criteri geografici o di tipo.
  * `idFlotta`: Chiave primaria della partizione.
* **Mezzo:** Astrazione logica e persistente del veicolo fisico IoT (es. monopattino o bici).
  * `idMezzo`: Codice seriale interno.
  * `coordinateMezzo`: Posizione geospaziale (JTS Point) reale inviata dal GPS di bordo.
  * `stato`: Indicatore enum dello status corrente (`DISPONIBILE`, `IN_USO`, `IN_MANUTENZIONE`, `BLOCCATO`).
  * `autonomia`: Quantitativo residuo misurabile (capacità batteria in percentuale o chilometri stimati).
  * `costoOrario`: Componente di calcolo tariffario base legato al tempo d'utilizzo.
  * `velocitàMax`: Limite meccanico/software di velocità del veicolo, che può variare per cause normative.
  * `condizione`: Testo libero o enum indicante lo stato di salute fisico del mezzo.
  * `tipo`: Categoria commerciale (Bici, Scooter, Monopattino).
* **Corsa:** Istanza di fruizione di un veicolo. Ha inizio con lo sblocco e termine con il blocco regolare.
  * `idCorsa`: Chiave primaria del record.
  * `costo`: Somma monetaria derivante da scatto alla risposta + costo orario/chilometrico.
  * `orarioInizio` / `orarioFine`: Marche temporali (timestamp o LocalTime) di avvio e chiusura.
  * `coordinatePartenza` / `coordinateArrivo`: Punti geospaziali in cui il mezzo è stato preso e poi rilasciato.
* **Prenotazione:** Intento confermato di un Utente di bloccare la fruizione di un mezzo a terzi in attesa di raggiungerlo.
  * `idPrenotazione`: Chiave primaria della riserva.
  * `stato`: Stato del ciclo di vita (`ATTIVA`, `SCADUTA`, `ANNULLATA`, `COMPLETATA`).
  * `orarioInizio` / `data`: Marche temporali in cui la prenotazione ha avuto luogo.
* **Segnalazione:** Ticket gestionale interno indicante un'anomalia emersa a carico di un veicolo (danno, effrazione, parcheggio improprio).
  * `idSegnalazione`: Codice ticket.
  * `stato`: Situazione del ticket (`APERTA`, `IN_LAVORAZIONE`, `CHIUSA`).
  * `ora` / `data`: Momento di registrazione dell'anomalia.

### 2.3 Entità Accessorie
* **MetodoPagamento:** Entità crittografata contenente i token del metodo di incasso.
  * `NumCarta`: Identificativo mascherato (es. PAN) del mezzo di pagamento.
  * `intestatarioCarta`: Denominazione formale del portatore della carta.
* **ZonaGeografica:** Poligono o perimetro geospaziale applicato su mappa vettoriale (Geo-Fence).
  * `idArea`: Identificatore del perimetro.
  * `tipoRestrizione`: Tipologia di applicazione normativa (`ZTL`, `DIVIETO_PARCHEGGIO`, `LIMITE_VELOCITA`).
  * `noteRestrizione`: Stringa descrittiva contenente eventuali ordinanze, date di validità o fasce orarie.
  * `zona`: La geometria complessa definente il recinto digitale (LineString/Polygon).

---

## 3. Classi Architetturali (Controller / Logica di Business)

* **GestioneCorsa:** Componente orchestratore. Controlla il flusso che interroga il mezzo IoT per l'avvio, valuta la fattibilità (Disponibilità) e aggiorna i costi incrementali (`aggiornaStima`). Gestisce l'interruzione provvisoria (`sospensioneCorsa`) e definitiva.
* **GestorePagamento:** Gateway middleware di instradamento al sistema bancario. Accetta richieste per elaborazioni (convalida) e autorizza lo storno a fine noleggio (`pagamentoCorsa`).
* **RicercaMezzi:** Servizio di indicizzazione geospaziale. Esegue query topologiche per ricavare tutti i mezzi all'interno di un raggio stabilito (`visualizzaMezziVicini`) filtrandoli per `StatoMezzo`.
* **GestioneUtenti:** Motore di regole e sicurezza dedicato all'interdizione o alla garanzia di integrità degli account (`gestioneUtente`, `cercaReport`).
* **GestioneFlotta:** Quadro comandi esposto esclusivamente all'Operatore Tecnico. Forza stati sui mezzi prescindendo dal normale lifecycle (es. `bloccaMezzo`, `avviaManutenzione`).
* **GestioneStatistiche:** Motore OLAP/Aggregazione. Effettua elaborazioni ETL leggere sulle `Corsa` passate offrendo KPI via `analisiTratte`.
* **GestionePrenotazione:** Worker schedulato per l'emissione, cancellazione e, in modo critico, per l'espirazione temporale delle code (`gestisciTimeout`, `notificaScadenzaTempo`).
* **GestioneAree:** Motore di validazione vettoriale. Mantiene in cache o su db spaziale le geometrie delle `ZonaGeografica` e implementa la detection in tempo reale (`analisiConflitti`).

---

## 4. Layer di Presentazione (Views)

* **AppUtente:** Applicazione (Mobile o Web) di front-end rivolta all'utente consumer per l'intero ciclo di scoperte/viaggio/fatturazione.
* **AppOperatoreSC:** Cruscotto di back-office aziendale orientato al customer management, disaccoppiato dalle interfacce hardware.
* **AppPA:** Dashboard (presumibilmente Web/Desktop) a disposizione degli assessorati alla viabilità cittadina, con tool di manipolazione cartografica.
* **AppOperatoreTecnico:** Applicaizone (spesso field-mobile) orientata alla diagnostica rapida hardware e alla segnalazione ticket in loco.

---

## 5. Sistemi e Interfacce Esterne (Integration)

* **Mezzo : IoT:** Sub-sistema elettronico integrato nei veicoli (micro-controllore con modem cellulare e GPS). Espone le `API Controllo` capaci di pilotare gli attuatori fisici di sblocco serratura o accensione motore.
* **Gateway Pagamento:** Interfaccia esterna di un Payment Service Provider (es. Stripe) deputato alla conservazione crittografica e conformità PCI-DSS dei dati di pagamento.
* **Servizio Mappa:** Provider geomatico esterno. Oltre a fornire le map tiles per le App, fornisce il servizio di routing as-a-service incrociando i layer di traffico standard alle `restrizioni` passate via API.
* **DBMS / Connessione Dati:** Layer persistenziale basato su database relazionale integrato con estensioni spaziali (es. Hibernate Spatial su MySQL) per la conservazione ACID delle entità di dominio.

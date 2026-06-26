	

**Cofee Coders**

**Smart Mobility System**

1. # **Product Backlog**

   1. ## **Introduzione** 

Il progetto consiste in un sistema intelligente di **Smart Urban Mobility** per il comune di **Zootropolis**, volto a integrare servizi di bike, car e monopattino sharing. L'obiettivo è bilanciare le esigenze di utenti, operatori e Amministrazione Pubblica, riducendo traffico e inquinamento. Il sistema punta a creare un ecosistema urbano sostenibile e sicuro, ottimizzando la gestione della flotta e contrastando frodi e inefficienze.

2. ## **Contesto di business**

Il progetto nasce dalla necessità del comune di **Zootropolis** di introdurre un sistema di mobilità sostenibile (Smart Urban Mobility) che integri diversi servizi di sharing (bicicletta, auto e monopattino). Il contesto è quello di una crescente urbanizzazione e congestione stradale, dove l'obiettivo è l'integrazione con il paradigma di **Smart City**.

3. ## **Stakeholder**

Il sistema deve funzionare in un contesto urbano con:

* **Utenti**: Cercano un accesso rapido, un'esperienza fluida, sicurezza nei pagamenti e trasparenza per evitare frodi.    
* **Operatori del Servizio**: Puntano all'ottimizzazione della flotta (redistribuzione dei mezzi), alla riduzione dei costi operativi e alla prevenzione di furti, vandalismi e frodi sugli account.    
  Gli operatori sono divisi in due categorie:  
1. **Operatori Tecnici**: dedicati alla gestione dei mezzi e al corretto funzionamento di essi e del sistema.  
2. **Operatori del servizio clienti**: dedicati alla moderazione dei clienti sul sistema.  
* **Pubblica Amministrazione (P.A.)**: Mira alla riduzione di traffico e inquinamento e necessita di dati affidabili per la pianificazione urbana e delle infrastrutture (es. piste ciclabili).

## 

## 

## 

## 

## **1.4 Item funzionali**

Contiene l’elenco e la specifica di tutti i requisiti funzionali espressi attraverso lo

schema delle user stories

**Utenti**

**1.4.1 UT.01**

*Come* utente, 

*Voglio* visualizzare i mezzi disponibili con le loro specifiche in un raggio prestabilito a partire dalla posizione scelta, 

*Così da* poter scegliere il mezzo adatto alle mie esigenze.

**1.4.2 UT.02**

*Come* utente, 

*Voglio poter* prenotare dei mezzi se disponibili,

*Così da* trovarli a disposizione quando arrivo.

**1.4.3 UT.03**

*Come* utente, 

Voglio visualizzare l’importo della corsa in tempo reale, 

*Così da* sapere quanto sto pagando.

**1.4.4 UT.04**

*Come* utente, 

*Voglio* visualizzare il percorso che richiede meno tempo sulla base del mezzo scelto (controllando aree accessibili al mezzo e non)

*Così* da minimizzare la durata del viaggio.

**1.4.5 UT.05**  
*Come* utente,   
*Voglio* poter inserire un metodo di pagamento nel mio account,   
*Così da* permettere l'addebito al termine di ogni utilizzo.

**1.4.6 UT.06**  
*Come* utente,   
*Voglio* poter effettuare delle soste senza perdere il possesso del mezzo,  
*Così* da poter sospendere la mia corsa.

**1.4.7 UT.07**  
*Come* utente,   
*Voglio* poter effettuare il pagamento per il servizio,  
*Così* da poter terminare la corsa

**Amministrazione Pubblica**

**1.4.8 AP.01**

*Come* amministrazione comunale, 

*Voglio* accedere alle statistiche di utilizzo del sistema,

*Così da* supportare decisioni strategiche e pianificare la manutenzione in specifiche aree.

**1.4.9 AP.02**

*Come* amministrazione comunale,

*Voglio* analizzare le condizioni fisiche dei mezzi,

*Così da* poter intervenire in caso di necessità.

**Operatore del Servizio**

**1.4.10 OP.01**

*Come* operatore,   
*Voglio* visualizzare la distribuzione dei mezzi e poter intervenire in caso di necessità.  
*Così da* poter bloccare i mezzi se necessario.

**1.4.11 OP.02**  
*Come* operatore,   
*Voglio* poter moderare l'account di un utente,   
*Così da* prevenire futuri utilizzi in caso di violazione dei termini di servizio

**1.4.12 OP.03**

*Come* operatore,   
*Voglio* amministrare le prenotazioni sui mezzi*,*  
*Così da* moderarne l’utilizzo

## **1.5 Item non funzionali**

Contiene l’elenco e la specifica di tutti i requisiti non funzionali espressi attraverso lo schema delle user stories

**1.5.1 UT.08**  
*Come* utente,   
*Voglio* utilizzare un metodo di autenticazione per sbloccare il mezzo,  
*Così da* evitare accessi non autorizzati e avviare la mia corsa.

**1.5.2 AP.03** 

*Come amministrazione comunale*, 

*Voglio gestire le restrizioni geografiche*,  
*Così da* evitare posizionamenti o percorrenze illecite dei mezzi.

### 

Sprint Report N. 3

**Smart Mobility System**

2. # **Sprint Report**

   1. ## **Sprint Backlog**

| Sprint | ID (Caso D’uso) | Attore | Tipo | Elemento | Priorità (1-50) |
| :---- | :---: | :---: | :---: | ----- | ----- |
| 3 | UT.03 (UC.UT.08) | Utente | Funzionale | Visualizzare l’importo della corsa in tempo reale, così da sapere quanto sto pagando. | 40 |
| 3 | UT.08 (UC.UT.03) | Utente | Non Funzionale | Dover utilizzare un metodo d’autenticazione per sbloccare il mezzo, così da evitare accessi non autorizzati e avviare la mia corsa. | 40 |
| 3 | UC.UT.10 (Funz. implicita) | Utente | Funzionale | L'utente non registrato inserisce i propri dati anagrafici e le credenziali per creare un nuovo profilo. Il sistema verifica la validità dei dati e l'assenza di duplicati, creando il nuovo account utente. | 50  |
| 3 | UC.ATT.01 (Funz. implicita) | Attore | Funzionale | L'attore inserisce le proprie credenziali per autenticarsi. Il sistema verifica se i dati forniti sono già presenti nel sistema e, in caso di esito positivo, concede l'accesso alle funzionalità riservate in base al ruolo dell'attore (Utente, Operatore, o Amministrazione Pubblica). | 50  |
| 3 | UC.OP.04 (Funz. implicita) | Operatore Tecnico | Funzionale | L'Operatore Tecnico richiede di terminare la propria sessione. Il sistema chiude la sessione e rimuove i permessi di accesso alle aree riservate. | 10  |
| 3 | UC.AP.04 (Funz. implicita) | Amm. Pubblica | Funzionale | L'Amministrazione Pubblica richiede di terminare la propria sessione. Il sistema chiude la sessione e rimuove i permessi di accesso alle aree riservate. | 10  |
| 3 | UC.UT.09 (Funz. implicita) | Utente | Funzionale | L'utente richiede di terminare la propria sessione. Il sistema chiude la sessione e rimuove i permessi di accesso alle aree riservate. | 10  |
| 3 | UC.OP.05 (Funz. implicita) | Operatore SC | Funzionale | L’operatore servizio clienti richiede di terminare la propria sessione. Il sistema chiude la sessione e rimuove i permessi di accesso alle aree riservate. | 10  |

   2. ## **Product Requirement Specification** 

      1. ### **Diagramma dei Casi d’uso**

![][image1]

2. ### **Specifiche dei Casi d’uso**

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **UT.01** |
| **Nome** | **Ricerca Mezzi** |
| **ID** | **UC.UT.01** |
| **Breve descrizione** | L'utente avvia una ricerca dei mezzi nelle vicinanze della propria posizione. Il sistema esegue una query geolocalizzata con un raggio iniziale (raggio base, es. 2 km). Se non vengono trovati risultati, l'utente può scegliere di estendere la ricerca a un raggio superiore (raggio esteso, es. 5 km). I mezzi trovati vengono mostrati all'utente che può selezionarne uno per consultare le relative specifiche. |
| **Attori principali** | Utente |
| **Precondizioni** | \- Le coordinate GPS della posizione attuale dell'utente sono presenti nel sistema. |
| **Flusso principale** |  Il caso d'uso inizia quando l'utente avvia la ricerca dei mezzi nelle vicinanze. Il sistema effettua la ricerca dei mezzi nel raggio base utilizzando le coordinate dell'utente. Il sistema mostra la lista dei mezzi trovati all'utente. L'utente seleziona un mezzo Il sistema recupera i dettagli del mezzo e li mostra all'utente.  |
| **Flussi alternativi** | *Nessun mezzo trovato nel raggio base – Espansione accettata:* Al passaggio 3, la ricerca non restituisce mezzi. Il sistema propone l'espansione del raggio. L'utente accetta l'espansione. Il sistema esegue la ricerca con il nuovo raggio e mostra i risultati. *Nessun mezzo trovato nel raggio espanso / Utente rifiuta espansione:* Il sistema mostra un messaggio di errore indicando che nessun mezzo è disponibile. |
| **Postcondizioni** |  L’elenco e le specifiche dei mezzi disponibili sono stati recuperati dal sistema |
| **Include** | \- |
| **Estende** | \- |
| **Esteso dal caso d'uso** | UC.UT.02 (Prenotazione Mezzo) |
| **Specializza il caso d'uso** | \- |
| **Generalizza il caso d'uso** | \- |
| **Requisiti** | Servizio di geolocalizzazione attivo per il calcolo delle coordinate utente.  |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **UT.02** |
| **Nome** | **Prenotazione Mezzo** |
| **ID** | **UC.UT.02** |
| **Breve descrizione** | L'utente seleziona un mezzo disponibile e ne richiede la prenotazione. Il sistema verifica la disponibilità, aggiorna lo stato del mezzo a “prenotato”. È previsto un meccanismo automatico di annullamento se l'utente non raggiunge il mezzo entro 15 minuti dell'orario prenotato. |
| **Attori principali** | Utente |
| **Precondizioni** |  La mappa con i mezzi è stata resa disponibile (e quindi visibile) dal sistema. |
| **Flusso principale** |  Il caso d'uso inizia quando l'utente seleziona un mezzo disponibile dalla mappa e ne richiede la prenotazione. Il sistema aggiorna lo stato del mezzo a “prenotato” Il sistema registra la prenotazione. Il sistema notifica all'utente l'avvenuta prenotazione e gli fornisce il QR Code. |
| **Flussi alternativi** | *Scadenza tempo prenotazione (dopo 15 minuti dall'orario prenotato):* Trascorsi 15 minuti dall'orario prenotato, il sistema rileva il superamento del timeout. Il sistema reimposta lo stato del mezzo a “disponibile". Il sistema imposta lo stato della prenotazione a “scaduta”. Il sistema invia una notifica di annullamento della prenotazione all'utente per decorrenza dei termini.  |
| **Postcondizioni** | 1\.   il mezzo risulta “prenotato”. 2\.   La prenotazione è stata registrata. |
| **Include** | \- |
| **Estende** | UC.UT.01 (Ricerca Mezzi) |
| **Esteso dal caso d'uso** | UC.UT.03 (Avvio Corsa) |
| **Specializza il caso d'uso** | \- |
| **Generalizza il caso d'uso** | \- |
| **Requisiti** | Meccanismo di timeout automatico a 15 minuti per l'annullamento delle prenotazioni scadute.  |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **UT.08** |
| **Nome** | **Avvio Corsa** |
| **ID** | **UC.UT.03** |
| **Breve descrizione** | L'utente si autentica per avviare una corsa. Il sistema verifica la disponibilità del veicolo e richiede la selezione di un metodo di pagamento. Al superamento dei controlli, il mezzo viene sbloccato fisicamente e la corsa ha inizio.  |
| **Attori principali** | Utente |
| **Precondizioni** | 1\. Il mezzo selezionato è bloccato. 2\. Il mezzo selezionato risulta prenotato. 3\. L’utente non si trova in una corsa attiva. |
| **Flusso principale** | Il caso d'uso inizia quando l'utente scansiona il QR code. Il sistema verifica che il mezzo sia disponibile. Il sistema richiede all’utente di selezionare un metodo di pagamento. L'utente richiede l'avvio della corsa. Il sistema registra la nuova corsa Il sistema sblocca fisicamente il mezzo Il sistema imposta lo stato logico del mezzo a “in uso” Il sistema notifica l'avvio della corsa all'utente |
| **Flussi alternativi** | Mezzo non disponibile:  Al passaggio 2 del flusso principale, il sistema rileva che il mezzo non è disponibile. Il sistema mostra un messaggio di errore all'utente, impedendo l'avvio della corsa. |
| **Postcondizioni** | Il veicolo risulta sbloccato fisicamente. La corsa è attiva. |
| **Include** | UC.UT.08 (Monitoraggio Costo) |
| **Estende** | UC.UT.02 (Prenotazione Mezzo) |
| **Esteso dal caso d'uso** | UC.UT.06 (Sospensione Corsa)  |
| **Specializza il caso d'uso** | \- |
| **Generalizza il caso d'uso** | \- |
| **Requisiti** | Interfaccia IoT per sblocco Mezzo remoto |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **UT.04** |
| **Nome** | **Ricerca Percorso Migliore** |
| **ID** | **UC.UT.04** |
| **Breve descrizione** | L'utente inserisce la destinazione desiderata nel sistema, che elabora il percorso a partire dalla posizione attuale, recupera le eventuali restrizioni geografiche attive nella zona, e genera il tracciato tramite un servizio di mappe esterno per poi mostrarlo all'utente. |
| **Attori principali** | Utente |
| **Precondizioni** | 1.    L'utente ha effettuato l'accesso del 2\.  Le coordinate della posizione attuale dell'utente sono presenti nel sistema. |
| **Flusso principale** | Il caso d'uso inizia quando l'utente inserisce l'indirizzo di destinazione nel sistema. Il sistema elabora le restrizioni geografiche in base alle coordinate di partenza e alla destinazione. Il sistema calcola il tracciato del percorso che richiede meno tempo rispettando le restrizioni geografiche Il sistema mostra il tracciato del percorso all'utente |
| **Flussi alternativi** | **\-** |
| **Postcondizioni** |  Il percorso che richiede meno tempo è stato calcolato e i dati del tracciato sono stati restituiti dal sistema. |
| **Include** | \- |
| **Estende** | \- |
| **Esteso dal caso d'uso** | \- |
| **Specializza il caso d'uso** | \- |
| **Generalizza il caso d'uso** | \- |
| **Requisiti** | Integrazione con un Servizio Mappa esterno per l'elaborazione dei percorsi e delle coordinate |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **UT.05** |
| **Nome** | **Seleziona Metodo Pagamento** |
| **ID** | **UC.UT.05** |
| **Breve descrizione** | L’utente seleziona il metodo di pagamento che desidera utilizzare. Il sistema verifica se ne ha già salvato almeno uno e gli fa scegliere fra quelli, altrimenti salva un nuovo metodo di pagamento facendogli inserire e convalidando i dati del metodo nel sistema. |
| **Attori principali** | Utente |
| **Precondizioni** | \- |
| **Flusso principale** | Il caso d’uso inizia quando l’utente deve scegliere un metodo di pagamento. Il sistema richiede all’utente se inserire un nuovo metodo di pagamento o selezionarne uno esistente. L'utente richiede di visualizzare i metodi salvati.  Il sistema recupera i metodi di pagamento associati all'utente e ne mostra la lista all’utente  L'utente seleziona un metodo di pagamento dalla lista. Il sistema acquisisce la scelta e la associa alla sessione corrente   Il sistema notifica all'utente il successo dell'operazione. |
| **Flussi alternativi** | *Registrazione nuovo metodo (Opzionale): 1\. Al passaggio 3 del flusso principale, l'utente richiede di inserire un nuovo metodo di pagamento. 2\. Il sistema mostra il form di inserimento. 3\. L'utente inserisce i dati della carta (numero, scadenza, CVV, intestatario) e invia. 4\. Il sistema convalida la carta 5\. Il sistema controlla se la carta inserita esiste già nel sistema. 6\. Il sistema rileva che il metodo non esiste e lo crea,salvandolo nel sistema  7\. Il sistema mostra un messaggio di convalida all'utente. 8\. Il flusso riprende permettendo all'utente di selezionare uno tra i metodi della lista, incluso quello appena inserito. Metodo già esistente:* Al passaggio 7, il sistema rileva che il metodo è già presente, salta il salvataggio e restituisce direttamente esito positivo. *Non convalidato:* Al passaggio 4, la validazione della carta fallisce e il sistema mostra l'errore all'utente.  |
| **Postcondizioni** | Un metodo di pagamento è stato associato alla sessione corrente dell’utente. |
| **Include** | \- |
| **Estende** | \- |
| **Esteso dal caso d'uso** | \- |
| **Specializza il caso d'uso** | \- |
| **Generalizza il caso d'uso** | \- |
| **Requisiti** | Integrazione con un’interfaccia di pagamento per validare in tempo reale i dati della carta. |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **UT.06** |
| **Nome** | **Sospensione Corsa** |
| **ID** | **UC.UT.06** |
| **Breve descrizione** | L'utente richiede di sospendere la propria corsa attiva. Il sistema blocca il veicolo fisicamente e ne aggiorna lo stato a “sospeso", fornendo all'utente un QR code. Successivamente, l'utente scansiona il QR code per sbloccare il mezzo, riprendere la corsa e il sistema aggiorna i costi relativi al tempo di sospensione. |
| **Attori principali** | Utente |
| **Precondizioni** | 1\.   La corsa è attiva 2\.   Il mezzo è in uso  |
| **Flusso principale** | Il caso d'uso inizia quando l'utente invia il comando per sospendere la corsa attiva. Il sistema blocca fisicamente il mezzo Il sistema imposta lo stato del mezzo su “sospeso” Il sistema fornisce il QR Code all'utente. L'utente, per riprendere la corsa, scansiona il QR code. Il sistema sblocca il mezzo Il sistema reimposta lo stato del mezzo a “in uso” Il sistema aggiorna il costo Il sistema mostra la conferma di ripresa all'utente.  |
| **Flussi alternativi** | Corsa non esistente: Al passaggio 2, il sistema rileva che la corsa indicata non è presente a sistema. Il sistema mostra un messaggio di errore all'utente, impedendo la sospensione.  |
| **Postcondizioni** | \- Il costo della corsa include anche il costo della sospensione |
| **Include** | \- |
| **Estende** | UC.UT.03 (Gestione Corsa) |
| **Esteso dal caso d'uso** | **\-** |
| **Specializza il caso d'uso** | **\-** |
| **Generalizza il caso d'uso** | **\-** |
| **Requisiti** | Integrazione sensore per lettura QR Code sul mezzo, Interfaccia IoT per blocco/sblocco Mezzo remoto, tariffa differenziata per la sospensione. |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **UT.07** |
| **Nome** | **Termina Corsa** |
| **ID** | **UC.UT.07** |
| **Breve descrizione** |  L'utente richiede la terminazione della corsa. Il sistema verifica se il veicolo si trova in un'area consentita, calcola il costo, effettua il pagamento, invia il comando di blocco al veicolo e lo rende nuovamente disponibile. |
| **Attori principali** | Utente |
| **Precondizioni** | 1.    la corsa deve essere attiva 2\.   il mezzo deve essere in uso  |
| **Flusso principale** |  Il caso d'uso inizia quando l'utente richiede la terminazione della corsa. Il sistema verifica che il veicolo si trovi in un'area consentita. Il sistema calcola il costo finale della corsa. Il sistema elabora la transazione Il sistema blocca fisicamente il mezzo Il sistema imposta lo stato del mezzo su "disponibile" Il sistema imposta lo stato della prenotazione su "completata"   Il sistema mostra il messaggio di fine corsa all'utente. |
| **Flussi alternativi** | *Corsa non trovata:* Il sistema rileva che la corsa indicata non è presente o attiva a sistema. Il sistema mostra un messaggio di errore all'utente. *Area non consentita:* Il sistema rileva che il veicolo si trova in una zona in cui non è consentito terminare la corsa. Il sistema mostra un messaggio di errore all'utente, impedendo la terminazione della corsa. *Pagamento non riuscito:* Il pagamento fallisce Il sistema mostra l'errore all'utente e richiede l'inserimento di un metodo di pagamento valido. |
| **Postcondizioni** | 1\.  la transazione di fine corsa è stato effettuata 2\.  il mezzo è disponibile e fisicamente bloccato 3\.  la prenotazione risulta completata 4\.  la corsa risulta terminata  |
| **Include** | \- |
| **Estende** | \- |
| **Esteso dal caso d'uso** | \- |
| **Specializza il caso d'uso** | \- |
| **Generalizza il caso d'uso** | \- |
| **Requisiti** | Integrazione con un'interfaccia di pagamento, Tracciamento e verifica geospaziale GPS, Interfaccia IoT per blocco mezzo remoto. |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **UT.03** |
| **Nome** | **Monitoraggio costo**  |
| **ID** | **UC.UT.08** |
| **Breve descrizione** | Il sistema avvia un monitoraggio continuo del costo, che viene mostrato in tempo reale all’utente.  |
| **Attori principali** | Utente |
| **Precondizioni** | 1.    la corsa deve essere attiva 2\.   il mezzo deve essere in uso  |
| **Flusso principale** |  Il caso d'uso inizia quando l'utente la corsa viene avviata. Il sistema  recupera il costo orario del mezzo selezionato  il sistema avvia l'aggiornamento periodico del costo e lo mostra all’utente.  |
| **Flussi alternativi** | \- |
| **Postcondizioni** | \- |
| **Include** | \- |
| **Estende** | \- |
| **Esteso dal caso d'uso** | \- |
| **Specializza il caso d'uso** | \- |
| **Generalizza il caso d'uso** | \- |
| **Requisiti** | \- |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **OP.01** |
| **Nome** | **Moderazione Flotta** |
| **ID** | **UC.OP.01** |
| **Breve descrizione** | L'Operatore Tecnico accede alla mappa della flotta per visualizzare la posizione e lo stato dei veicoli. Se un'azione è necessaria, l'operatore seleziona un veicolo e invia un comando remoto. Il sistema tenta di eseguire il comando: se il veicolo è online, viene bloccato e lo stato aggiornato; se la connessione è persa, viene creata una segnalazione e l'operatore viene notificato. |
| **Attori principali** | Operatore Tecnico |
| **Precondizioni** | L'Operatore Tecnico ha effettuato l'accesso e ha una sessione attiva. |
| **Flusso principale** | Il caso d'uso inizia quando l'Operatore Tecnico accede alla mappa della flotta. Il sistema recupera e mostra la lista dei veicoli e il loro stato. L'operatore seleziona un veicolo e invia un comando remoto di blocco. Il sistema blocca il mezzo Il sistema aggiorna lo stato del mezzo Il sistema conferma all'operatore la riuscita dell'operazione. |
| **Flussi alternativi** | *Connessione persa:* 1. Al passaggio 4, se il sistema non riesce a raggiungere il veicolo, crea una segnalazione di guasto  2\. Il sistema mostra un alert all'operatore.  |
| **Postcondizioni** | Il veicolo risulta in stato "bloccato" e quindi non è più prenotabile |
| **Include** | \- |
| **Estende** | \- |
| **Esteso dal caso d'uso** | \- |
| **Specializza il caso d'uso** | \- |
| **Generalizza il caso d'uso** | \- |
| **Requisiti** | Connettività IoT per il comando remoto dei veicoli. Sistema di segnalazione per la gestione dei veicoli non raggiungibili.  |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **OP.02**  |
| **Nome** | **Moderazione Utenti** |
| **ID** | **UC.OP.02** |
| **Breve descrizione** | L'Operatore Servizio Clienti cerca un utente tramite il suo identificativo. Il sistema recupera i dati e il report associato all'utente e li mostra all'operatore. L'operatore può quindi aggiornare il report e applicare un'azione correttiva (sospensione/disattivazione dell’account) che verrà poi notificata all’utente |
| **Attori principali** | Operatore Servizio Clienti |
| **Precondizioni** | \- L'Operatore Servizio Clienti ha effettuato l'accesso. |
| **Flusso principale** |   Il caso d'uso inizia quando l'Operatore Servizio Clienti cerca un utente tramite il suo identificativo. Il sistema recupera e mostra i dati e il report associato all'utente. L'operatore aggiorna il report e applica un'azione correttiva (sospensione/disattivazione account). Il sistema salva l'azione e aggiorna lo stato dell'utente  Il sistema invia una notifica dell’azione all’utente e lo disconnette da tutte le sue sessioni aperte. Il sistema conferma l'esito positivo all'operatore.  |
| **Flussi alternativi** | *Utente Non Trovato:* Al passaggio 2 del flusso principale, il sistema non trova alcun utente corrispondente all'identificativo fornito. Il sistema mostra un messaggio di errore all'operatore indicando che l'utente non è stato trovato, e l'operazione si interrompe. |
| **Postcondizioni** | L’azione correttiva è stata applicata. Il report dell'utente è stato aggiornato.  È stata inviata all’utente una notifica sull'azione intrapresa |
| **Include** | \- |
| **Estende** | \- |
| **Esteso dal caso d'uso** | \- |
| **Specializza il caso d'uso** | \- |
| **Generalizza il caso d'uso** | \- |
| **Requisiti** | Sistema di gestione utenti con report consultabili e modificabili. Meccanismo di collegamento con l'interfaccia dell'utente per comunicare le azioni correttive ed applicarle. |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **OP.03** |
| **Nome** | **Moderazione Prenotazioni** |
| **ID** | **UC.OP.03** |
| **Breve descrizione** | L'Operatore Servizio Clienti accede alla lista delle prenotazioni valide, ne seleziona una e richiede l’annullamento. La prenotazione viene così annullata, il mezzo torna disponibile e l'operatore riceve una notifica di conferma. |
| **Attori principali** | Operatore Servizio Clienti |
| **Precondizioni** | \-. |
| **Flusso principale** |  Il caso d'uso inizia quando l'operatore richiede la lista delle prenotazioni attive. Il sistema recupera e mostra le prenotazioni valide. L'operatore seleziona una prenotazione e ne chiede l'annullamento. Il sistema aggiorna lo stato della prenotazione ad "annullata" e rende il mezzo nuovamente "disponibile". Il sistema notifica l'operatore dell'avvenuto annullamento. |
| **Flussi alternativi** | Lista Prenotazioni Vuota: Al passaggio 2 del flusso principale, il sistema recupera una lista vuota. Il sistema notifica l’Operatore Servizio Clienti dell’assenza di prenotazioni, mostrando un errore. |
| **Postcondizioni** | 1\. La prenotazione risulta “annullata"  2\. Il mezzo risulta "disponibile". |
| **Include** | \- |
| **Estende** | \- |
| **Esteso dal caso d'uso** | \- |
| **Specializza il caso d'uso** | \- |
| **Generalizza il caso d'uso** | \- |
| **Requisiti** | \- |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **AP.01** |
| **Nome** | **Monitoraggio Statistiche e Analisi Tratte** |
| **ID** | **UC.AP.01** |
| **Breve descrizione** | La Pubblica Amministrazione seleziona un intervallo temporale di interesse. Il sistema recupera le corse effettuate nel periodo indicato e i dati sull’attraversamento delle tratte, genera un file di statistiche aggregate e lo presenta all'utente con la possibilità di scaricarlo. |
| **Attori principali** | Amministrazione Pubblica |
| **Precondizioni** | \-  |
| **Flusso principale** | Il caso d'uso inizia quando l'Amministrazione Pubblica seleziona un intervallo temporale di interesse. Il sistema recupera tutte le corse effettuate nel periodo indicato. Per ogni corsa dell’intervallo, il sistema recupera le zone attraversate dal mezzo utilizzato. Il sistema elabora i dati e genera il file di statistiche aggregate. Il sistema mostra le statistiche e offre la possibilità di scaricare il report. |
| **Flussi alternativi** | *Dati non presenti nel periodo selezionato:* Al passaggio 2, il sistema rileva che non ci sono corse effettuate per il periodo indicato. Il sistema mostra un messaggio di errore all'Amministrazione Pubblica indicando l'assenza di dati. |
| **Postcondizioni** | Le statistiche aggregate sono state generate e il file di report è reso disponibile dal sistema.  |
| **Include** | \- |
| **Estende** | \- |
| **Esteso dal caso d'uso** | \- |
| **Specializza il caso d'uso** | \- |
| **Generalizza il caso d'uso** | \- |
| **Requisiti** | Disponibilità di dati storici delle corse nel database. Capacità di generazione di file di statistiche aggregate esportabili. |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **AP.02** |
| **Nome** | **Moderazione Stato Flotta** |
| **ID** | **UC.AP.02** |
| **Breve descrizione** | La Pubblica Amministrazione richiede lo stato della flotta. Il sistema recupera i mezzi associati alla flotta, ne analizza le condizioni e presenta una dashboard riepilogativa. Se dei veicoli richiedono manutenzione, l'Amministrazione Pubblica può avviare un intervento che comporta la creazione di segnalazioni e l'aggiornamento dello stato dei mezzi interessati. |
| **Attori principali** | Amministrazione Pubblica |
| **Precondizioni** | L'Amministrazione Pubblica ha effettuato l'accesso ed ha una sessione attiva. La flotta selezionata è registrata a sistema con i relativi mezzi associati. |
| **Flusso principale** | Il caso d'uso inizia quando l'Amministrazione Pubblica richiede lo stato della flotta. Il sistema recupera la lista e le condizioni operative di tutti i mezzi associati. Il sistema mostra un riepilogo complessivo L'Amministrazione Pubblica avvia un intervento di manutenzione per i veicoli che lo necessitano. Il sistema crea una segnalazione per i mezzi interessati Per ciascuno dei mezzi interessati, il sistema aggiorna lo stato a "manutenzione". Il sistema mostra il riepilogo degli interventi all'utente. |
| **Flussi alternativi** | *I veicoli non richiedono manutenzione:* Dopo la visualizzazione del riepilogo complessivo al passaggio 3, il sistema rileva che nessun veicolo necessita di intervento. Il sistema notifica all'Amministrazione Pubblica che la flotta è completamente operativa. |
| **Postcondizioni** | 1\.   I mezzi interessati risultano in stato "manutenzione"  2\.  Le relative segnalazioni sono state create nel sistema. |
| **Include** | \- |
| **Estende** | \- |
| **Esteso dal caso d'uso** | \- |
| **Specializza il caso d'uso** | \- |
| **Generalizza il caso d'uso** | \- |
| **Requisiti** | Database aggiornato con lo stato operativo di ciascun mezzo della flotta. Sistema di segnalazione per la gestione degli interventi di manutenzione. |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **AP.03** |
| **Nome** | **Restrizioni Geografiche** |
| **ID** | **UC.AP.03** |
| **Breve descrizione** | L' Amministrazione Pubblica accede alla mappa per gestire le restrizioni geografiche. Il sistema mostra le zone esistenti e consente la modifica delle restrizioni. In caso di conflitto con restrizioni già presenti, l’Amministrazione Pubblica può scegliere di sovrascrivere le regole esistenti o annullare l'operazione. |
| **Attori principali** | Amministrazione Pubblica |
| **Precondizioni** |  |
| **Flusso principale** | Il caso d'uso inizia quando l'Amministrazione Pubblica accede alla sezione per la gestione delle aree. Il sistema recupera e mostra le zone geografiche esistenti. L'Amministrazione Pubblica richiede la modifica delle restrizioni per una zona. Il sistema verifica eventuali sovrapposizioni con le restrizioni in vigore. Il sistema salva le nuove restrizioni. Il sistema mostra la situazione aggiornata e notifica il successo dell'operazione.  |
| **Flussi alternativi** | *Conflitto con restrizioni esistenti – Sovrascrittura confermata:* Al passaggio 4 del flusso principale, il sistema rileva un conflitto con le restrizioni esistenti. Il sistema notifica l'Amministrazione Pubblica del conflitto e chiede conferma per la sovrascrittura. L'Amministrazione Pubblica conferma la sovrascrittura. Il sistema salva le restrizioni sovrascritte e conferma il successo dell'operazione. *Conflitto con restrizioni esistenti – Annullamento:* Al passaggio 2 del flusso alternativo precedente, l'Amministrazione Pubblica rifiuta la sovrascrittura. |
| **Postcondizioni** | La zona geografica è stata aggiornata con le nuove regole. |
| **Include** | **\-** |
| **Estende** |  **\-** |
| **Esteso dal caso d'uso** | **\-** |
| **Specializza il caso d'uso** | **\-** |
| **Generalizza il caso d'uso** | **\-** |
| **Requisiti** | Sistema di gestione delle zone geografiche con supporto alla verifica dei conflitti tra restrizioni sovrapposte. Visualizzazione cartografica interattiva delle zone operative. |

   3. ### **Altro**

TABELLA RIEPILOGATIVA USER STORIES E BACKLOG

| Sprint | ID (Caso D’uso) | Attore | Tipo | Elemento | Priorità (1-50) |
| :---- | :---: | :---: | :---: | ----- | :---: |
| 1 | UT.01 (UC.UT.01) | Utente | Funzionale | Visualizzare i mezzi disponibili con le loro specifiche in un raggio prestabilito a partire dalla posizione scelta,  Così da poter scegliere il mezzo adatto alle mie esigenze. | 50 |
| 2 | UT.07 (UC.UT.07) | Utente | Funzionale | Effettuare il pagamento per il servizio, Così da poter terminare la corsa | 45 |
| 1 | AP.02 (UC.AP.02) | Amm. Pubblica | Funzionale | Analizzare le condizioni fisiche dei mezzi, così da poter intervenire in caso di necessità. | 40 |
| 1 | UT.03 (UC.UT.08) | Utente | Funzionale | Visualizzare l’importo della corsa in tempo reale, così da sapere quanto sto pagando. | 40 |
| 1 | UT.08 (UC.UT.03) | Utente | Non Funzionale | Dover utilizzare un metodo d’autenticazione per sbloccare il mezzo, così da evitare accessi non autorizzati e avviare la mia corsa. | 40 |
| 1 | OP.02 (UC.OP.02) | Operatore Servizio Clienti | Funzionale | Poter moderare l'account di un utente, così da prevenire futuri utilizzi in caso di violazione dei termini di servizio | 35 |
| 1 | UT.05 (UC.UT.05) | Utente | Funzionale | Poter inserire un metodo di pagamento nel mio account, così da permettere l'addebito al termine di ogni utilizzo. | 35 |
| 2 | UT.02 (UC.UT.02) | Utente | Funzionale | Prenotare dei mezzi se disponibili, Così da trovarli a disposizione quando arrivo. | 35 |
| 2 | UT.06 (UC.UT.06) | Utente | Funzionale | Poter effettuare delle soste senza perdere il possesso del mezzo, così da poter sospendere la mia corsa | 35 |
| 2 | AP.03 (UC.AP.03) | Amm. Pubblica | Non Funzionale | Gestire le restrizioni geografiche, Così da evitare posizionamenti o percorrenze illecite dei mezzi. | 30 |
| 2 | UT.04 (UC.UT.04) | Utente | Funzionale | Visualizzare il percorso che richiede meno tempo sulla base del mezzo scelto (controllando aree accessibili al mezzo e non) Così da minimizzare la durata del viaggio. | 30 |
| 2 | OP.03 (UC.OP.03) | Operatore Servizio Clienti | Funzionale | Amministrare le prenotazioni sui mezzi, così da modernarne l'utilizzo. | 25 |
| 2 | AP.01 (UC.AP.01) | Amm. Pubblica | Funzionale | Accedere alle statistiche di utilizzo del sistema, così da supportare decisioni strategiche e pianificare la manutenzione in specifiche aree. | 20 |
| 2 | OP.01 (UC.OP.01) | Operatore Tecnico | Funzionale | Visualizzare la distribuzione dei mezzi e poter intervenire in caso di necessità. Così da poter bloccare i mezzi se necessario. | 15 |

| Campo | Descrizione |
| :---- | :---- |
| UserStories | DECLASSATA A FUNZ. IMPLICITA IN SPRINT REVIEW |
| Nome | Registrazione Utente |
| ID | UC.UT.10 |
| Breve descrizione | L'utente non registrato inserisce i propri dati anagrafici e le credenziali per creare un nuovo profilo. Il sistema verifica la validità dei dati e l'assenza di duplicati, creando il nuovo account utente. |
| Attori principali | Utente (Non registrato) |
| Precondizioni | L'utente non dispone di un account nel sistema. |
| Flusso principale | 1\. Il caso d'uso inizia quando un utente non registrato richiede di registrarsi nel sistema. 2\. Il sistema restituisce il form per l'inserimento dei dati necessari (dati anagrafici, email, password) all’utente. 3\. L'utente inserisce i dati e invia la richiesta di registrazione. 4\. Il sistema verifica che i dati siano nel formato corretto e che l'email non sia già in uso. 5\. Il sistema crea il nuovo account e salva le credenziali in modo sicuro. 6\. Il sistema conferma all'utente l'avvenuta registrazione. |
| Flussi alternativi | *Formattazione sbagliata:* 1\. Al passaggio 4, il sistema rileva che alcuni campi obbligatori sono vuoti o non rispettano i formati richiesti. 2\. Il sistema mostra un messaggio di errore e richiede la correzione dei dati. *Email già in uso:* 1\. Al passaggio 4, il sistema rileva che l'indirizzo email inserito è già associato a un account esistente. 2\. Il sistema mostra un messaggio di errore e suggerisce di effettuare il login all’account indicato. |
| Postcondizioni | Il nuovo account è stato creato ed è presente nel sistema. |
| Include | \- |
| Estende | \- |
| Esteso dal caso d'uso | \- |
| Specializza il caso d’uso | \- |
| Generalizza il caso d’uso | \- |
| Requisiti | Cifratura delle password. Validazione dei dati di input.  |

| Campo | Descrizione |
| :---- | :---- |
| UserStories | DECLASSATA A FUNZ. IMPLICITA IN SPRINT REVIEW |
| Nome | Logout Utente |
| ID | UC.UT.09 |
| Breve descrizione | L'utente richiede di terminare la propria sessione. Il sistema chiude la sessione e rimuove i permessi di accesso alle aree riservate. |
| Attori principali | Utente |
| Precondizioni | L'utente ha effettuato l'accesso e ha una sessione attiva. |
| Flusso principale | 1\. Il caso d'uso inizia quando l'utente richiede di disconnettersi dal sistema. 2\. Il sistema termina la sessione corrente dell'utente e lo disconnette. |
| Flussi alternativi | \- |
| Postcondizioni | La sessione dell'utente è terminata |
| Include | \- |
| Estende | \- |
| Esteso dal caso d'uso | \- |
| Specializza il caso d’uso | \- |
| Generalizza il caso d’uso | \- |
| Requisiti | Gestione sicura delle sessioni. |

| Campo | Descrizione |
| :---- | :---- |
| UserStories | DECLASSATA A FUNZ. IMPLICITA IN SPRINT REVIEW |
| Nome | Logout Operatore Tecnico |
| ID | UC.OP.04 |
| Breve descrizione | L'Operatore Tecnico richiede di terminare la propria sessione. Il sistema chiude la sessione e rimuove i permessi di accesso alle aree riservate. |
| Attori principali | Operatore Tecnico |
| Precondizioni | L'Operatore Tecnico ha effettuato l'accesso e ha una sessione attiva. |
| Flusso principale | 1\. Il caso d'uso inizia quando l'Operatore Tecnico richiede di disconnettersi dal sistema. 2\. Il sistema termina la sessione corrente dell'Operatore Tecnico e lo disconnette. |
| Flussi alternativi | \- |
| Postcondizioni | La sessione dell'Operatore Tecnico è terminata. |
| Include | \- |
| Estende | \- |
| Esteso dal caso d'uso | \- |
| Specializza il caso d’uso | \- |
| Generalizza il caso d’uso | \- |
| Requisiti | Gestione sicura delle sessioni. |

| Campo | Descrizione |
| :---- | :---- |
| UserStories | DECLASSATA A FUNZ. IMPLICITA IN SPRINT REVIEW |
| Nome | Logout Operatore SC |
| ID | UC.OP.05 |
| Breve descrizione | L’Operatore Servizio Clienti richiede di terminare la propria sessione. Il sistema chiude la sessione e rimuove i permessi di accesso alle aree riservate. |
| Attori principali | Operatore Servizio Clienti |
| Precondizioni | L'Operatore Servizio Clienti ha effettuato l'accesso e ha una sessione attiva. |
| Flusso principale | 1\. Il caso d'uso inizia quando l'Operatore Servizio Clienti richiede di disconnettersi dal sistema. 2\. Il sistema termina la sessione corrente dell'Operatore Servizio Clienti e lo disconnette. |
| Flussi alternativi | \- |
| Postcondizioni | La sessione dell'Operatore Servizio Clienti è terminata |
| Include | \- |
| Estende | \- |
| Esteso dal caso d'uso | \- |
| Specializza il caso d’uso | \- |
| Generalizza il caso d’uso | \- |
| Requisiti | Gestione sicura delle sessioni. |

| Campo | Descrizione |
| :---- | :---- |
| UserStories | DECLASSATA A FUNZ. IMPLICITA IN SPRINT REVIEW |
| Nome | Login |
| ID | UC.ATT.01 |
| Breve descrizione | L'attore inserisce le proprie credenziali per autenticarsi. Il sistema verifica se i dati forniti sono già presenti nel sistema e, in caso di esito positivo, concede l'accesso alle funzionalità riservate in base al ruolo dell'attore (Utente, Operatore, o Amministrazione Pubblica). |
| Attori principali | Attore |
| Precondizioni | L'attore possiede un account nel sistema. Operatore Tecnico, Operatore Servizio Clienti e  PA possiedono credenziali di accesso pre-generate e fornite dall'amministrazione.  |
| Flusso principale | 1\. Il caso d'uso inizia quando l'attore richiede di effettuare l'accesso al sistema. 2\. Il sistema richiede l'inserimento delle credenziali. 3\. L'attore inserisce i dati e li invia. 4\. Il sistema verifica la corrispondenza delle credenziali fornite in memoria e lo stato dell'account. 5\. Il sistema autentica l'attore, crea la sessione e sblocca le funzionalità previste per il suo ruolo specifico. |
| Flussi alternativi | *Email non valida:* 1\. Al passaggio 4, il sistema rileva che i dati inseriti non corrispondono a nessun account. 2\. Il sistema mostra un messaggio di errore e nega l'accesso. *Password non valida:* 1\. Al passaggio 4, il sistema rileva che la password per l’account della email inserita è errata. 2\. Il sistema mostra un messaggio di errore e nega l'accesso. |
| Postcondizioni | L'attore risulta autenticato Una nuova sessione è stata creata con i permessi e la pagina corrispondente al ruolo dell’attore. |
| Include | \- |
| Estende | \- |
| Esteso dal caso d'uso | \- |
| Specializza il caso d'uso | \- |
| Generalizza il caso d’uso | \- |
| Requisiti | Gestione sicura delle sessioni. Meccanismo di cifratura delle password. Gestione dei ruoli (RBAC). |

| Campo | Descrizione |
| :---- | :---- |
| UserStories | DECLASSATA A FUNZ. IMPLICITA IN SPRINT REVIEW |
| Nome | Logout PA |
| ID | UC.AP.04 |
| Breve descrizione | L'Amministrazione Pubblica richiede di terminare la propria sessione. Il sistema chiude la sessione e rimuove i permessi di accesso alle aree riservate. |
| Attori principali | Amministrazione Pubblica |
| Precondizioni | L'Amministrazione Pubblica ha effettuato l'accesso ed ha una sessione attiva. |
| Flusso principale | 1\. Il caso d'uso inizia quando l'Amministrazione Pubblica richiede di disconnettersi dal sistema. 2\. Il sistema termina la sessione corrente dell'Amministrazione Pubblica e la disconnette. |
| Flussi alternativi | \- |
| Postcondizioni | La sessione dell'Amministrazione Pubblica è terminata |
| Include | \- |
| Estende | \- |
| Esteso dal caso d'uso | \- |
| Specializza il caso d’uso | \- |
| Generalizza il caso d’uso | \- |
| Requisiti | Gestione sicura delle sessioni. |

3. ## **System Architecture**

   1. ### **Diagramma delle Componenti**


2. ### **Specifica delle componenti**

Per la progettazione e lo sviluppo del sistema, è stato adottato il pattern architetturale Model-View-Controller (MVC) nella sua variante Moderna (Web-oriented / con Controller Intermediario).

A differenza dell'approccio MVC, la configurazione scelta centralizza l'intero flusso di controllo e di scambio dati all'interno dei componenti Controller. Questa decisione è stata guidata dalla necessità di gestire un ecosistema eterogeneo composto da molteplici client (AppUtente, AppOperatoreSC, AppPA, AppOperatoreTecnico e Autenticazione) e flussi di dati asincroni provenienti dai dispositivi IoT (i mezzi della flotta). 

Il sistema implementa la separazione delle responsabilità secondo la seguente logica, ampiamente documentata nel Diagramma delle Componenti e nei Diagrammi di Sequenza:

## 1\. Specifica Componente View

Componente che si occupa di visualizzare i dati all'utente e gestisce l'interazione fra quest'ultimo e l'infrastruttura sottostante. Le View non interrogano mai direttamente il Model ogni comunicazione avviene tramite le interfacce dei Controller. 

- **AppUtente**: è l'interfaccia principale per il cittadino fruitore dei servizi di sharing. Consente la ricerca dei mezzi nelle vicinanze (raggio base 2km, esteso 5km), la selezione del veicolo, la visualizzazione delle specifiche tecniche e dei costi, l'avvio della corsa tramite scansione QR code, la sospensione e la riattivazione della corsa, la terminazione della corsa, l'inserimento della destinazione per l'ottimizzazione del percorso, la gestione dei metodi di pagamento (inserimento dati carta, selezione metodo salvato) e la richiesta di logout. Realizza l'interfaccia View `Aggiornamenti Corsa`  per notificare al Controller lo stato della corsa. Dipende dalle interfacce Controller `Gestione Corsa` per le operazioni su corse, mezzi e prenotazioni.

- **AppOperatoreTecnico**: è la dashboard per l'operatore tecnico. Consente la visualizzazione dello stato della flotta, la richiesta dello stato di una flotta specifica, la selezione di un veicolo per visualizzarne i dettagli, e la richiesta di logout. Realizza l'interfaccia View `Stato Flotta` per ricevere dati aggiornati su posizione e stato dei veicoli. Dipende dall'interfaccia Controller `Amministrazione Flotta` per il controllo remoto e la diagnostica della flotta.

- **AppOperatoreSC**: è l'interfaccia per l'operatore del servizio clienti. Consente la visualizzazione dei report utente, la richiesta della lista delle prenotazioni, la selezione di una prenotazione specifica, l'aggiornamento del report di un utente e la richiesta di logout. Realizza l'interfaccia View `Eventi Utente` per ricevere notifiche relative a moderazione, prenotazioni e azioni correttive. Dipende dall'interfaccia Controller `Moderazione Utente` per le operazioni di moderazione account.

- **AppPA**: è l'interfaccia per la Pubblica Amministrazione. Consente la selezione di un intervallo di date per le statistiche, la richiesta dello stato della flotta, l'avvio di un intervento su una flotta, la selezione della mappa per la gestione delle zone geografiche, la modifica delle restrizioni geografiche, la conferma o il rifiuto della sovrascrittura delle restrizioni e la richiesta di logout. Realizza l'interfaccia View `Diagnostica` per ricevere report diagnostici sullo stato dei mezzi e della flotta. Dipende dall'interfaccia Controller `Statistiche e Restrizioni` per le statistiche e la gestione delle restrizioni.

- **Autenticazione**: è l'interfaccia di pre-autenticazione per il login e la registrazione. Consente l'inserimento delle credenziali (nome, cognome, email, password, data di nascita) per la registrazione e l'invio dei dati per il login. Realizza l'interfaccia View `Stato Sessione` per gestire lo stato della sessione attuale (autenticato, ruolo, permessi). Dipende dall'interfaccia Controller `Gestione Sessioni` per l'autenticazione e la gestione delle sessioni.

## 2\. Specifica Componente Controller

Componente che riceve i comandi e i dati dell'utente attraverso la View ed esegue operazioni che possono alterare il Model e che portano ad un cambiamento di stato della View; orchestra il flusso MVC, valida le richieste e media tra View, Model e sistemi esterni. 

- **GestioneAutenticazione**: gestisce la validazione delle credenziali, la registrazione degli utenti e le sessioni di autenticazione. Fornisce i metodi `verificaValidita()` per la validazione dei dati di registrazione, `invioCredenziali()` per il login e `inviaRichiestaLogout()` per la terminazione della sessione. Realizza le interfacce Controller `Gestione Sessioni` per Autenticazione View e contribuisce alle interfacce `Gestione Corsa`, `Moderazione Utente`, `Amministrazione Flotta` e `Statistiche e Restrizioni` per le altre View. Dipende dall'interfaccia Model `Gestione Dati Utente` per l'accesso ai dati anagrafici degli attori.

- **GestioneUtenti**: gestisce la moderazione degli account utente, inclusa la sospensione e la disattivazione. Fornisce i metodi `gestioneUtente()` per la modifica dello stato dell'account e `cercaReport()` per la consultazione dei report. Realizza l'interfaccia Controller `Moderazione Utente` per AppOperatoreSC. Dipende dall'interfaccia Model `Gestione Dati Utente` per l'accesso ai dati degli utenti.

- **RicercaMezzi**: gestisce le query geolocalizzate sui mezzi disponibili, con raggi multipli (raggio base 2km, raggio esteso 5km — default da UC.UT.01). Fornisce i metodi `visualizzaMezziVicini()` per la ricerca per coordinate e `visualizzaSpecifiche()` per i dettagli di un mezzo. Contribuisce all'interfaccia Controller `Gestione Corsa` per AppUtente. Dipende dal sistema esterno Servizio Mappa tramite l'interfaccia `API Mappa` per le funzionalità di geolocalizzazione. Dipende dall'interfaccia Model `Gestione Dati Corsa` per l'accesso ai dati dei mezzi.

- **GestioneCorsa**: orchestra l'intero ciclo di vita della corsa: avvio, sospensione, riattivazione, terminazione, calcolo del percorso e stima dei costi. Fornisce i metodi `avviaCorsa()`, `terminaCorsa()`, `sospensioneCorsa()`, `controllaDisponibilita()` (con due overload: no-args e con parametro info), `aggiornaStima()`, `richiediSblocco()` tramite QR code, `richiediCalcoloPercorso()` delegando a Servizio Mappa esterno, e `acquisisciSceltaMetodo()` per il metodo di pagamento. Contribuisce all'interfaccia Controller `Gestione Corsa` per AppUtente. Dipende dal sistema esterno Servizio Mappa tramite `API Mappa` per il routing e dal sistema esterno Mezzo:IoT tramite `API Controllo` per il blocco/sblocco fisico del veicolo. Dipende dall'interfaccia Model `Gestione Dati Corsa` per l'accesso ai dati di corsa, mezzi e pagamenti. È in relazione di associazione con Mezzo (1..\* a 0..*) e Corsa (0..* a 1).

- **GestorePagamento**: elabora le transazioni economiche e valida i metodi di pagamento, delegando al sistema esterno Gateway Pagamento. Fornisce i metodi `pagamentoCorsa()` per processare il pagamento di una corsa, `elaboraDatiCarta()` per la validazione dei dati di una nuova carta e `recuperaMetodiSalvati()` per ottenere i metodi di pagamento pre-esistenti. Contribuisce all'interfaccia Controller `Gestione Corsa` per AppUtente. Dipende dal sistema esterno Gateway Pagamento tramite l'interfaccia `API Pagamento` per la convalida carte e l'elaborazione transazioni. Dipende dall'interfaccia Model `Gestione Dati Corsa` per l'accesso ai dati dei metodi di pagamento.

- **GestioneFlotta**: monitora e controlla la flotta di veicoli da remoto. Fornisce i metodi `analisiStatoFlotta()` per rilevare mezzi da manutenere (crea Segnalazione e imposta Mezzo.stato a 'manutenzione'), `bloccaMezzo()` per il blocco remoto, `avviaManutenzione()` per avviare la manutenzione su una flotta e `getCondizioniMezzi()` per la visualizzazione dashboard dello stato della flotta. Realizza l'interfaccia Controller `Amministrazione Flotta`  per AppOperatoreTecnico e contribuisce all'interfaccia `Statistiche e Restrizioni`  per AppPA. Dipende dall'interfaccia Model `Gestione Dati Corsa` per i dati dei mezzi e dall'interfaccia `Gestione Dati Supporto` per la creazione e consultazione delle segnalazioni. È in relazione di associazione con Mezzo (0..\* a 0..*) e Segnalazione (0..* a 1).

- **GestionePrenotazione**: gestisce il ciclo di vita delle prenotazioni con timeout automatico a 15 minuti (default da UC.UT.02). Fornisce i metodi `inviaRichiestaPrenotazione()` per prenotare un mezzo, `richiediLista()` per ottenere la lista delle prenotazioni, `annullaPrenotazione()` per cancellare una prenotazione, `gestisciTimeout()` per l'annullamento automatico allo scadere dei 15 minuti, `notificaScadenzaTempo()` per notificare la scadenza e `concludiPrenotazione()` per chiudere la prenotazione all'avvio della corsa. Contribuisce alle interfacce Controller `Gestione Corsa` (per AppUtente) e `Moderazione Utente` (per AppOperatoreSC). Dipende dall'interfaccia Model `Gestione Dati Supporto` per l'accesso ai dati di prenotazioni e segnalazioni. È in relazione di associazione con Mezzo (1..\* a 0..*), Prenotazione (0..* a 1\) e Segnalazione (0..\* a 1).

- **GestioneStatistiche**: aggrega i dati delle corse e genera report statistici per la Pubblica Amministrazione. Fornisce i metodi `analisiTratte()` per l'analisi delle corse in un intervallo di date e `generaFileStatistiche()` per la generazione del file statistico. Contribuisce all'interfaccia Controller `Statistiche e Restrizioni`  per AppPA. Dipende dall'interfaccia Model `Gestione Dati Corsa` per i dati delle corse e dall'interfaccia `Gestione Dati Supporto` per i dati dei transiti. È in relazione di associazione con Corsa (0..\* a 0..*) e Transito (0..* a 0..\*).

- **GestioneAree**: esegue operazioni sulle zone geografiche e verifica i conflitti tra restrizioni sovrapposte. Fornisce i metodi `aggiornaRestrizione()` per modificare una zona, `analisiConflitti()` per verificare sovrapposizioni tra zone e `getZoneGeografiche()` per ottenere tutte le zone. Contribuisce all'interfaccia Controller `Statistiche e Restrizioni` per AppPA. Dipende dall'interfaccia Model `Gestione Dati Supporto` per l'accesso ai dati delle zone geografiche. È in relazione di associazione con ZonaGeografica (0..\* a 0..\*).

---

## 3\. Specifica Componente Model

Componente che contiene le entità core del sistema e i metodi di accesso ai dati. Ricopre un ruolo passivo — espone metodi per accesso e modifica dello stato richiesti dai Controller, ma è totalmente privo di logiche di notifica verso l'esterno .Tutte le entità dipendono da DBMS per la persistenza tramite l'interfaccia `Connessione Dati`

### 3.1 DominioAttori

Racchiude le entità del sistema che modellano gli attori del dominio. Mappa all'interfaccia Model `Gestione Dati Utente` consumata dai Controller GestioneAutenticazione e GestioneUtenti.

- **Attore**: classe base astratta che modella le credenziali di accesso (email, password cifrata) e il ruolo dell'attore (RuoloAttore: Utente, Operatore, PA). Utilizza strategia JOINED inheritance per la persistenza. È in relazione di generalizzazione con Utente, Operatore e PA.

- **Utente**: modella il cittadino fruitore dei servizi di sharing. Contiene i dati anagrafici (nomeUtente, cognomeUtente, telefono), la posizione corrente (coordinateUtente), lo stato dell'account (StatoUtente: attivo, sospeso, disattivato), il report di moderazione (reportUtente) e il contatore dei mezzi prenotati (numMezziPrenotati). Eredita email, password, id e ruolo da Attore. Fornisce metodi per la ricerca (`ricercaUtente`), l'azione correttiva sulla moderazione (`azioneCorrettiva`) e la creazione dell'account (`creaAccountUtente`).

- **Operatore**: modella il personale professionale del servizio, distinto in Tecnico o Servizio Clienti tramite l'enum TipoOperatore. La distinzione è data dal valore dell'enum `tipo`, non da classi separate. Eredita email, password, id e ruolo da Attore.

- **PA**: modella la Pubblica Amministrazione (ente comunale) con privilegi di analisi, statistiche e gestione delle restrizioni geografiche. L'attributo `idPA` coincide con `Attore.id`. Eredita email, password, id e ruolo da Attore.

### 3.2 DominioCorsa

Racchiude le entità del sistema che modellano le corse, i veicoli e i pagamenti. Mappa all'interfaccia Model `Gestione Dati Corsa` consumata dai Controller GestioneCorsa, GestorePagamento, RicercaMezzi, GestioneFlotta e GestioneStatistiche.

- **Mezzo**: modella il veicolo della flotta (bicicletta, monopattino, auto). Traccia lo stato (StatoMezzo: disponibile, prenotato, in\_uso, sospeso, bloccato, manutenzione), la posizione (coordinateMezzo), l'autonomia residua, il costo orario, la velocità massima, la condizione fisica, il tipo, l'identificativo della flotta (idFlotta) e il tempo di disponibilità. Fornisce metodi di interrogazione avanzati come la ricerca per flotta (`getMezzibyFlotta`), la ricerca geospaziale (`getMezziInArea`) e l'ottenimento dei dettagli completi (`getDettagliMezzo`). È in relazione di associazione 1 a Molti con Corsa.

- **Corsa**: modella la sessione di utilizzo di un mezzo dall'avvio al termine. Include il costo totale, gli orari di inizio e fine, le coordinate di partenza e arrivo, e le foreign key verso MetodoPagamento e Utente. Fornisce metodi per la creazione (`creaCorsa`), la ricerca per ID (`ricercaCorsa`), l'interrogazione per periodo (`getCorseByPeriodo`) e l'aggiornamento del costo (`aggiornaCosto`). È in relazione di associazione con Mezzo (0..\* a 1).

- **MetodoPagamento**: modella i dati cifrati della carta di credito/debito associata a un utente. Utilizza chiave surrogata `idMetodoPagamento` come PK (pattern PCI-DSS). Contiene il numero carta (cifrato) e l'intestatario. Fornisce metodi per la creazione (`creaMetodoPagamento`), la verifica di esistenza (`controllaMetodoEsistente`) e la ricerca per utente (`getMetodoByUtente`).

### 3.3 DominioSupporto

Racchiude le entità del sistema che modellano i dati di supporto: prenotazioni, segnalazioni, zone geografiche e transiti. Mappa all'interfaccia Model `Gestione Dati Supporto` consumata dai Controller GestioneFlotta, GestionePrenotazione, GestioneAree e GestioneStatistiche.

- **Prenotazione**: modella il blocco temporaneo di un mezzo, con timeout automatico a 15 minuti (default da UC.UT.02). Traccia lo stato (StatoPrenotazione: attiva, scaduta, annullata, completata), l'orario di inizio, la data, e le foreign key verso Utente e Mezzo. Fornisce metodi per la creazione (`creaPrenotazione`) e la ricerca per stato (`getPrenotazioneByStato`).

- **Segnalazione**: modella il report di anomalia su un mezzo (guasto, manutenzione, veicolo non raggiungibile). Traccia lo stato (StatoSegnalazione: aperta, in\_lavorazione, chiusa), l'ora, la data e la foreign key verso Mezzo. Fornisce il metodo di creazione (`creaSegnalazione`).

- **ZonaGeografica**: modella un'area geografica con restrizioni di circolazione o sosta (TipoRestrizione: divieto\_parcheggio, ZTL, limite\_velocita). Contiene il tipo di restrizione, le note descrittive e la geometria della zona (LineString). Fornisce metodi per la verifica di sovrapposizioni (`verificaSovrapposizioni`), il controllo di appartenenza di una coordinata all'area (`checkArea` — utilizzato per il vincolo architetturale AP.04), la creazione (`creaZonaGeografica`), l'interrogazione delle restrizioni per coordinate (`getRestrizioniZona`) e il salvataggio (`salvaRestrizioni`).

- **Transito**: modella l'associazione M:N tra Corsa e ZonaGeografica per tracciare le zone attraversate durante una corsa. Contiene le foreign key verso Corsa (`idCorsa`) e ZonaGeografica (`idArea`). Fornisce il metodo per ottenere i transiti di una corsa (`getTransitiByCorsa`).

## 4\. Specifica Componente Sistemi Esterni

Tutti i sistemi esterni sono simulati .

### 4.1 Servizio Mappa

Componente che fornisce servizi di geolocalizzazione e routing esterno per il calcolo di percorsi tra coordinate. 

- **Descrizione**: calcola il percorso ottimale tra coordinate iniziali e coordinate finali, tenendo conto delle restrizioni geografiche delle zone attraversate. Realizza l'interfaccia `API Mappa`   
- **Metodo esposto**: `getPercorso(coordinateIniziali, coordinateFinali, restrizioni)` — restituisce i dati del percorso calcolato.   
- **Consumatori**: RicercaMezzi (per la geolocalizzazione dei mezzi vicini) e GestioneCorsa (per il calcolo del percorso ottimale — `richiediCalcoloPercorso()`).

### 4.2 Gateway Pagamento

Componente che processa i pagamenti e convalida le carte di credito/debito come processore di pagamento esterno. 

- **Descrizione**: convalida i dati della carta presso il circuito esterno e processa la transazione economica per la corsa. Realizza l'interfaccia `API Pagamento`   
- **Metodi esposti**: `effettuaPagamento(idMetodoPagamento, idCorsa)` per processare il pagamento e `convalidaCarta(numCarta, dsCarta, cvv, intestatarioCarta)` per la validazione dei dati della carta.  
- **Consumatore**: GestorePagamento (Controller).

### 4.3 Mezzo : IoT

Componente che fornisce l'interfaccia fisica col veicolo per le operazioni di blocco e sblocco remoto. 

- **Descrizione**: interfaccia di controllo del veicolo per operazioni fisiche remote. L'interfaccia `API Controllo` è definita ma senza realization collegato a Mezzo:IoT nell'XMI del diagramma componenti . Nonostante lo stato orphan, l'interfaccia è considerata funzionale .  
- **Metodi esposti**: `bloccoMezzoFisico(idMezzo)` per bloccare il veicolo e `sbloccoMezzoFisico(idMezzo)` per sbloccarlo.  
- **Consumatore**: GestioneCorsa (Controller) — utilizzato per lo sblocco tramite QR code all'avvio corsa e per il blocco in sospensione/termine corsa.

### 4.4 DBMS

Componente che ha la responsabilità di rendere persistenti i dati immagazzinandoli in un database relazionale. 

- **Descrizione**: Realizza l'interfaccia `Connessione Dati`. Le classi Model dipendono da DBMS per la persistenza: Attore, Corsa, MetodoPagamento, Mezzo, Prenotazione, Segnalazione, Transito, ZonaGeografica.  
- **Design**: interfaccia generica simulata. I metodi `getIdDBMS()` e `setIdDBMS(id)` presenti nell'XMI sono inclusi per completezza di tracciabilità. Le operazioni specifiche sono implementate dalle classi Model che chiamano il DBMS per la persistenza.  
- **Consumatore**: Model (tutte le entità).


  3. ### **Specifica delle interfacce**

     4. ###  Aggiornamenti Corsa

| Proprietà | Valore |
| :---- | :---- |
| **Classe Realizzazione** | `AppUtente` |
| **Consumatore** | Controller (per notifiche corsa ad AppUtente) |
| **Descrizione** | Interfaccia per la gestione del ciclo di vita della corsa lato View. Il Controller chiama questi metodi per richiedere input all'utente e notificare cambiamenti di stato. |

| Metodo | Ritorno |
| :---- | :---- |
| `scansionaQRCode(qrCode)` | void |
| `inserisciDatiCarta(idUtente, numCarta, dsCarta, cvv, intestatarioCarta)` | void |
| `apriAvvioCorsa()` | void |
| `terminazioneCorsa(idCorsa)` | void |
| `sospendiCorsa(idCorsa)` | void |
| `apriSezioneProfilo(idUtente)` | void |
| `apriInserimentoMetodoPagamento(idUtente)` | void |
| `selezionaMezzo(idMezzo)` | void |
| `inserisciDestinazione(indirizzoArrivo)` | void |
| `avviaRicercaMezzi(coordinateUtente, raggiob)` | void |
| `confermaEspansione()` | void |
| `notificaAzione(idUtente, azione)` | void |
| `ottieniMetodiSalvati()` | void |
| `selezionaMetodo(numCarta)` | void |
| `richiestaLogout(email)` | void |
| `getIdUtente()` | — |
| `setIdUtente(id)` | void |
| `getIdSessioneUtente()` | — |
| `setIdSessioneUtente(id)` | void |

     5.  Stato Flotta

| Proprietà | Valore |
| :---- | :---- |
| **Classe Realizzazione** | `AppOperatoreTecnico` |
| **Consumatore** | Controller (per invio dati flotta a AppOperatoreTecnico) |
| **Descrizione** | Interfaccia per la visualizzazione dello stato della flotta e il controllo remoto dei veicoli lato View. |

**Metodi:**

| Metodo | Ritorno |
| :---- | :---- |
| `richiedeStatoFlotta(idFlotta)` | void |
| `selezionaVeicolo(idMezzo)` | void |
| `richiestaLogout(email)` | void |
| `getIdOperatoreTecnico()` | — |
| `setIdOperatoreTecnico(id)` | void |
| `getIdSessioneOperatoreTecnico()` | — |
| `setIdSessioneOperatoreTecnico(id)` | void |

6. ###  Diagnostica

| Proprietà | Valore |
| :---- | :---- |
| **Classe Realizzazione** | `AppPA` |
| **Consumatore** | Controller (per invio report diagnostici a AppPA) |
| **Descrizione** | Interfaccia per la diagnostica della flotta e gestione restrizioni geografiche lato View. |

| Metodo | Ritorno |
| :---- | :---- |
| `selezionaIntervallo(dataInizio, dataFine)` | void |
| `richiedeStatoFlotta(idFlotta)` | void |
| `avviaIntervento(idFlotta)` | void |
| `selezionaMappa()` | void |
| `modificaRestrizioni(zona)` | void |
| `confermaSovrascrittura(idArea, tipoRestrizione, noteRestrizione)` | void |
| `rifiutaSovrascrittura()` | void |
| `richiestaLogout(email)` | void |
| `getIdPA()` | — |
| `setIdPA(id)` | void |
| `getIdSessionePA()` | — |
| `setIdSessionePA(id)` | void |

   7. ###  Eventi Utente

| Proprietà | Valore |
| :---- | :---- |
| **Classe Realizzazione** | `AppOperatoreSC` |
| **Consumatore** | Controller (per notifiche moderazione e prenotazioni a AppOperatoreSC) |
| **Descrizione** | Interfaccia per la moderazione utenti e amministrazione prenotazioni lato View. |

| Metodo | Ritorno |
| :---- | :---- |
| `mostraReport(idUtente)` | void |
| `richiediListaPrenotazioni()` | void |
| `selezionaPrenotazione(idPrenotazione)` | void |
| `aggiornaReport(idUtente)` | void |
| `richiestaLogout(email)` | void |
| `getIdOperatoreSC()` | — |
| `setIdOperatoreSC(id)` | void |
| `getIdSessioneOperatoreSC()` | — |
| `setIdSessioneOperatoreSC(id)` | void |

      8. ### Stato Sessione

| Proprietà | Valore |
| :---- | :---- |
| **Classe Realizzazione** | `Autenticazione` |
| **Consumatore** | Controller (per gestione stato sessione su Autenticazione) |
| **Descrizione** | Interfaccia per login, registrazione e gestione della sessione lato View. |

| Metodo | Ritorno |
| :---- | :---- |
| `inserisciCredenziali(nome, cognome, email, password, datanascita)` | void |
| `registrazioneUtente()` | void |
| `getIdAttore()` | — |
| `setIdAttore(id)` | void |
| `getIdSessioneAttore()` | — |
| `setIdSessioneAttore(id)` | void |

      9. ### Gestione Corsa

| Proprietà | Valore |
| :---- | :---- |
| **Classi Realizzazione** | GestioneCorsa, GestorePagamento, RicercaMezzi, GestionePrenotazione, GestioneAutenticazione |
| **Consumatore** | AppUtente |
| **Descrizione** | Interfaccia completa per l'utente cittadino: ciclo vita corsa, pagamenti, ricerca mezzi, prenotazioni e autenticazione. |

#### Da GestioneCorsa

| Metodo | Ritorno |
| :---- | :---- |
| `avviaCorsa()` | void |
| `terminaCorsa()` | void |
| `controllaDisponibilita()` | bool |
| `controllaDisponibilita(info)` | bool |
| `aggiornaStima(idCorsa)` | float |
| `sospensioneCorsa()` | bool |
| `richiediSblocco(qrCode)` | bool |
| `richiediCalcoloPercorso(coordinateUtente, destinazione)` | (percorso) |
| `acquisisciSceltaMetodo(idMetodoPagamento)` | void |
| `getIdGestioneCorsa()` | — |
| `setIdGestioneCorsa(id)` | void |
| `getIdMetodoPagamento()` | — |
| `setIdMetodoPagamento(id)` | void |

#### Da GestorePagamento

| Metodo | Ritorno |
| :---- | :---- |
| `pagamentoCorsa(idUtente, idMetodoPagamento, costo)` | bool |
| `elaboraDatiCarta(idUtente, numCarta, dsCarta, cvv, intestatarioCarta)` | bool |
| `recuperaMetodiSalvati()` | MetodoPagamento |
| `getIdGestorePagamento()` | — |
| `setIdGestorePagamento(id)` | void |

#### Da RicercaMezzi

| Metodo | Ritorno |
| :---- | :---- |
| `visualizzaMezziVicini(coordinateUtente, raggiob)` | Mezzo |
| `visualizzaSpecifiche(idMezzo)` | Mezzo |
| `getIdRicercaMezzi()` | — |
| `setIdRicercaMezzi(id)` | void |

#### Da GestionePrenotazione

| Metodo | Ritorno |
| :---- | :---- |
| `inviaRichiestaPrenotazione()` | void |
| `richiediLista()` | Prenotazione |
| `annullaPrenotazione()` | bool |
| `gestisciTimeout()` | void |
| `notificaScadenzaTempo(idPrenotazione)` | void |
| `concludiPrenotazione(idPrenotazione)` | void |
| `getIdGestionePrenotazione()` | — |
| `setIdGestionePrenotazione(id)` | void |

#### Da GestioneAutenticazione

| Metodo | Ritorno |
| :---- | :---- |
| `verificaValidita(nome, cognome, email, password, datanascita)` | RuoloAttore |
| `invioCredenziali(email, password)` | RuoloAttore |
| `inviaRichiestaLogout(email)` | void |
| `getIdGestioneAutenticazione()` | — |
| `setIdGestioneAutenticazione(id)` | void |

10. ###  Moderazione Utente

| Proprietà | Valore |
| :---- | :---- |
| **Classi Realizzazione** | GestioneUtenti, GestionePrenotazione, GestioneAutenticazione |
| **Consumatore** | AppOperatoreSC |
| **Descrizione** | Interfaccia per operatore servizio clienti: moderazione account, gestione prenotazioni e autenticazione. |

#### Da GestioneUtenti

| Metodo | Ritorno |
| :---- | :---- |
| `gestioneUtente(idUtente)` | bool |
| `cercaReport(idUtente)` | String |
| `getIdGestioneUtenti()` | — |
| `setIdGestioneUtenti(id)` | void |

#### Da GestionePrenotazione

| Metodo | Ritorno |
| :---- | :---- |
| `richiediLista()` | Prenotazione |
| `annullaPrenotazione()` | bool |
| `notificaScadenzaTempo(idPrenotazione)` | void |
| `inviaRichiestaPrenotazione()` | void |
| `gestisciTimeout()` | void |
| `concludiPrenotazione(idPrenotazione)` | void |
| `getIdGestionePrenotazione()` | — |
| `setIdGestionePrenotazione(id)` | void |

#### Da GestioneAutenticazione

| Metodo | Ritorno |
| :---- | :---- |
| `invioCredenziali(email, password)` | RuoloAttore |
| `inviaRichiestaLogout(email)` | void |
| `verificaValidita(nome, cognome, email, password, datanascita)` | RuoloAttore |
| `getIdGestioneAutenticazione()` | — |
| `setIdGestioneAutenticazione(id)` | void |

11. ###  Amministrazione Flotta

| Proprietà | Valore |
| :---- | :---- |
| **Classi Realizzazione** | GestioneFlotta, GestioneAutenticazione |
| **Consumatore** | AppOperatoreTecnico |
| **Descrizione** | Interfaccia per operatore tecnico: monitoraggio flotta, controlli remoti e autenticazione. |

#### Da GestioneFlotta

| Metodo | Ritorno |
| :---- | :---- |
| `analisiStatoFlotta(idFlotta)` | bool |
| `bloccaMezzo(idMezzo)` | bool |
| `avviaManutenzione(idFlotta)` | bool |
| `getCondizioniMezzi(idFlotta)` | Mezzo |
| `getIdGestioneFlotta()` | — |
| `setIdGestioneFlotta(id)` | void |

#### Da GestioneAutenticazione

| Metodo | Ritorno |
| :---- | :---- |
| `invioCredenziali(email, password)` | RuoloAttore |
| `inviaRichiestaLogout(email)` | void |
| `verificaValidita(nome, cognome, email, password, datanascita)` | RuoloAttore |
| `getIdGestioneAutenticazione()` | — |
| `setIdGestioneAutenticazione(id)` | void |

12. ###  Statistiche e Restrizioni

| Proprietà | Valore |
| :---- | :---- |
| **Classi Realizzazione** | GestioneStatistiche, GestioneAree, GestioneFlotta, GestioneAutenticazione |
| **Consumatore** | AppPA |
| **Descrizione** | Interfaccia per PA: analisi statistiche, gestione zone geografiche, diagnostica flotta e autenticazione. |

#### Da GestioneStatistiche

| Metodo | Ritorno |
| :---- | :---- |
| `analisiTratte(dataInizio, dataFine)` | — (statistiche) |
| `generaFileStatistiche(corse)` | void |
| `getIdGestioneStatistiche()` | — |
| `setIdGestioneStatistiche(id)` | void |

#### Da GestioneAree

| Metodo | Ritorno |
| :---- | :---- |
| `aggiornaRestrizione(idArea, tipoRestrizione, noteRestrizione, zona)` | void |
| `analisiConflitti(zona)` | bool |
| `getZoneGeografiche()` | ZonaGeografica |
| `getIdGestioneAree()` | — |
| `setIdGestioneAree(id)` | void |

#### Da GestioneFlotta

| Metodo | Ritorno |
| :---- | :---- |
| `analisiStatoFlotta(idFlotta)` | bool |
| `getCondizioniMezzi(idFlotta)` | Mezzo |
| `bloccaMezzo(idMezzo)` | bool |
| `avviaManutenzione(idFlotta)` | bool |
| `getIdGestioneFlotta()` | — |
| `setIdGestioneFlotta(id)` | void |

#### Da GestioneAutenticazione

| Metodo | Ritorno |
| :---- | :---- |
| `invioCredenziali(email, password)` | RuoloAttore |
| `inviaRichiestaLogout(email)` | void |
| `verificaValidita(nome, cognome, email, password, datanascita)` | RuoloAttore |
| `getIdGestioneAutenticazione()` | — |
| `setIdGestioneAutenticazione(id)` | void |

---

13. ### Gestione Sessioni

| Proprietà | Valore |
| :---- | :---- |
| **Classe Realizzazione** | GestioneAutenticazione |
| **Consumatore** | Autenticazione (View) |
| **Descrizione** | Interfaccia per login, registrazione e logout. Unica interfaccia Controller consumata da Autenticazione View. |

#### Da GestioneAutenticazione

| Metodo | Ritorno |
| :---- | :---- |
| `verificaValidita(nome, cognome, email, password, datanascita)` | RuoloAttore |
| `invioCredenziali(email, password)` | RuoloAttore |
| `inviaRichiestaLogout(email)` | void |
| `getIdGestioneAutenticazione()` | int |
| `setIdGestioneAutenticazione(id)` | void |

14. ### Gestione Dati Corsa

| Proprietà | Valore |
| :---- | :---- |
| **Classi Realizzazione** | Corsa, Mezzo, MetodoPagamento |
| **Consumatore** | Controller (GestioneCorsa, GestorePagamento, RicercaMezzi, GestioneFlotta, GestioneStatistiche) |
| **Descrizione** | Accesso a dati di corsa, mezzi e metodi di pagamento. |

| Metodo | Classe | Ritorno |
| :---- | :---- | :---- |
| `getIdCorsa()` | Corsa | — |
| `setIdCorsa(idCorsa)` | Corsa | void |
| `getCosto()` | Corsa | float |
| `setCosto(costo)` | Corsa | void |
| `getOrarioInizio()` | Corsa | time |
| `setOrarioInizio(orarioInizio)` | Corsa | void |
| `getOrarioFine()` | Corsa | time |
| `setOrarioFine(orarioFine)` | Corsa | void |
| `getCoordinatePartenza()` | Corsa | String |
| `setCoordinatePartenza( coordinatePartenza)` | Corsa | void |
| `getCoordinateArrivo()` | Corsa | String |
| `setCoordinateArrivo(coordinateArrivo)` | Corsa | void |
| `creaCorsa(orarioinizio, coordinatePartenza, idUtente, idMezzo)` | Corsa | void |
| `ricercaCorsa(idCorsa)` | Corsa | Corsa |
| `getCorseByPeriodo(dataInizio, dataFine)` | Corsa | Corsa |
| `aggiornaCosto(costo)` | Corsa | void |
| `getIdMetodoPagamento()` | Corsa | — |
| `setIdMetodoPagamento( idMetodoPagamento)` | Corsa | void |
| `getIdUtente()` | Corsa | — |
| `setIdUtente(idUtente)` | Corsa | void |
| `getIdMezzo()` | Mezzo | — |
| `setIdMezzo(idMezzo)` | Mezzo | void |
| `getCoordinateMezzo()` | Mezzo | String |
| `setCoordinateMezzo( coordinateMezzo)` | Mezzo | void |
| `getStato()` | Mezzo | StatoMezzo |
| `setStato(stato)` | Mezzo | void |
| `getAutonomia()` | Mezzo | float |
| `setAutonomia(autonomia)` | Mezzo | void |
| `getCostoOrario()` | Mezzo | float |
| `setCostoOrario(costoOrario)` | Mezzo | void |
| `getVelocitaMax()` | Mezzo | float |
| `setVelocitaMax(velocitaMax)` | Mezzo | void |
| `getCondizione()` | Mezzo | String |
| `setCondizione(condizione)` | Mezzo | void |
| `getTipo()` | Mezzo | String |
| `setTipo(tipo)` | Mezzo | void |
| `getIdFlotta()` | Mezzo | String |
| `setIdFlotta(idFlotta)` | Mezzo | void |
| `getTempoDisponibilita()` | Mezzo | time |
| `setTempoDisponibilita( tempoDisponibilita)` | Mezzo | void |
| `getMezzibyFlotta(idFlotta)` | Mezzo | Mezzo |
| `getMezziInArea( coordinateUtente, raggio)` | Mezzo | Mezzo |
| `getDettagliMezzo()` | Mezzo | Mezzo |
| `getIdMetodoPagamento()` | MetodoPagamento | — |
| `setIdMetodoPagamento( idMetodoPagamento)` | MetodoPagamento | void |
| `getNumCarta()` | MetodoPagamento | String |
| `setNumCarta(numCarta)` | MetodoPagamento | void |
| `getIntestatarioCarta()` | MetodoPagamento | String |
| `setIntestatarioCarta( intestatarioCarta)` | MetodoPagamento | void |
| `creaMetodoPagamento(numCarta, intestatarioCarta)` | MetodoPagamento | void |
| `controllaMetodoEsistente( numCarta)` | MetodoPagamento | bool |
| `getMetodoByUtente(idUtente)` | MetodoPagamento | MetodoPagamento |

    15. ###  Gestione Dati Utente

| Proprietà | Valore |
| :---- | :---- |
| **Classi Realizzazione** | Attore, Utente, Operatore, PA |
| **Consumatore** | Controller (GestioneAutenticazione, GestioneUtenti) |
| **Descrizione** | Accesso a dati anagrafici, stato e report degli attori del sistema. |

| Metodo | Classe | Ritorno |
| :---- | :---- | :---- |
| `getId()` | Attore | — |
| `setId(id)` | Attore | void |
| `getEmail()` | Attore | String |
| `setEmail(email)` | Attore | void |
| `getPassword()` | Attore | String |
| `setPassword(password)` | Attore | void |
| `getRuolo()` | Attore | RuoloAttore |
| `setRuolo(ruolo)` | Attore | void |
| `getIdUtente()` | Utente | — |
| `setIdUtente(idUtente)` | Utente | void |
| `ricercaUtente(idUtente)` | Utente | Utente |
| `creaAccountUtente(nome, cognome, email, password, datanascita)` | Utente | void |
| `azioneCorrettiva(azione)` | Utente | void |
| `getStatoUtente()` | Utente | StatoUtente |
| `setStatoUtente(statoUtente)` | Utente | void |
| `getReportUtente()` | Utente | String |
| `setReportUtente(reportUtente)` | Utente | void |
| `getNomeUtente()` | Utente | String |
| `setNomeUtente(nomeUtente)` | Utente | void |
| `getCognomeUtente()` | Utente | String |
| `setCognomeUtente(cognomeUtente)` | Utente | void |
| `getTelefono()` | Utente | String |
| `setTelefono(telefono)` | Utente | void |
| `getCoordinateUtente()` | Utente | String |
| `setCoordinateUtente(coordinateUtente)` | Utente | void |
| `getNumMezziPrenotati()` | Utente | int |
| `setNumMezziPrenotati(numMezziPrenotati)` | Utente | void |
| `getTipo()` | Operatore | TipoOperatore |
| `setTipo(tipo)` | Operatore | void |
| `getIdPA()` | PA | — |
| `setIdPA(idPA)` | PA | void |

        16. ###  Gestione Dati Supporto

| Proprietà | Valore |
| :---- | :---- |
| **Classi Realizzazione** | Segnalazione, Prenotazione, ZonaGeografica, Transito |
| **Consumatore** | Controller (GestioneFlotta, GestionePrenotazione, GestioneAree, GestioneStatistiche) |
| **Descrizione** | Accesso a dati di supporto: segnalazioni, prenotazioni, zone geografiche e transiti. |

| Metodo | Classe | Ritorno |
| :---- | :---- | :---- |
| `getIdSegnalazione()` | Segnalazione | — |
| `setIdSegnalazione(idSegnalazione)` | Segnalazione | void |
| `getIdMezzo()` | Segnalazione | — |
| `setIdMezzo(idMezzo)` | Segnalazione | void |
| `getStato()` | Segnalazione | StatoSegnalazione |
| `setStato(stato)` | Segnalazione | void |
| `getOra()` | Segnalazione | time |
| `setOra(ora)` | Segnalazione | void |
| `getData()` | Segnalazione | date |
| `setData(data)` | Segnalazione | void |
| `creaSegnalazione(idMezzo, statoS, data, ora, note)` | Segnalazione | void |
| `getIdPrenotazione()` | Prenotazione | — |
| `setIdPrenotazione(idPrenotazione)` | Prenotazione | void |
| `getStato()` | Prenotazione | StatoPrenotazione |
| `setStato(stato)` | Prenotazione | void |
| `getOrarioInizio()` | Prenotazione | time |
| `setOrarioInizio(orarioInizio)` | Prenotazione | void |
| `getIdUtente()` | Prenotazione | — |
| `setIdUtente(idUtente)` | Prenotazione | void |
| `getIdMezzo()` | Prenotazione | — |
| `setIdMezzo(idMezzo)` | Prenotazione | void |
| `getData()` | Prenotazione | date |
| `setData(data)` | Prenotazione | void |
| `creaPrenotazione(idMezzo, idUtente, orarioInizio)` | Prenotazione | void |
| `getPrenotazioneByStato(stato)` | Prenotazione | Prenotazione |
| `getIdArea()` | ZonaGeografica | — |
| `setIdArea(idArea)` | ZonaGeografica | void |
| `getTipoRestrizione()` | ZonaGeografica | TipoRestrizione |
| `setTipoRestrizione(tipoRestrizione)` | ZonaGeografica | void |
| `getNoteRestrizione()` | ZonaGeografica | String |
| `setNoteRestrizione(noteRestrizione)` | ZonaGeografica | void |
| `getZona()` | ZonaGeografica | LineString |
| `setZona(zona)` | ZonaGeografica | void |
| `getZone()` | ZonaGeografica | ZonaGeografica |
| `verificaSovrapposizioni(zona)` | ZonaGeografica | bool |
| `checkArea(coordinateUtente)` | ZonaGeografica | bool |
| `creaZonaGeografica(idArea, tipoRestrizione, noteRestrizione, zona)` | ZonaGeografica | void |
| `getRestrizioniZona(coordinateUtente)` | ZonaGeografica | ZonaGeografica |
| `getIdCorsa()` | Transito | — |
| `setIdCorsa(idCorsa)` | Transito | void |
| `getIdArea()` | Transito | — |
| `setIdArea(idArea)` | Transito | void |
| `getTransitiByCorsa(idCorsa)` | Transito | Transito |

        17. ###  API Mappa

| Proprietà | Valore |
| :---- | :---- |
| **Classe Realizzazione** | ServizioMappa |
| **Consumatore** | RicercaMezzi (Controller) |
| **Descrizione** | Servizio di geolocalizzazione e routing esterno per il calcolo di percorsi tra coordinate. |

| Metodo | Ritorno |
| :---- | :---- |
| `getPercorso(coordinateIniziali, coordinateFinali, restrizioni)` | — (datiPercorso) |
| `getIdServizioMappa()` | — |
| `setIdServizioMappa(id)` | void |

        18. ###  API Pagamento

| Proprietà | Valore |
| :---- | :---- |
| **Classe Realizzazione** | GatewayPagamento |
| **Consumatore** | GestorePagamento (Controller) |
| **Descrizione** | Processore di pagamento esterno per convalida carte e transazioni. |

| Metodo | Ritorno |
| :---- | :---- |
| `effettuaPagamento(idMetodoPagamento, idCorsa)` | bool |
| `convalidaCarta(numCarta, dsCarta, cvv, intestatarioCarta)` | bool |
| `getIdGatewayPagamento()` | — |
| `setIdGatewayPagamento(id)` | void |

        19. ###  Connessione Dati

| Proprietà | Valore |
| :---- | :---- |
| **Classe Realizzazione** | DBMS |
| **Consumatore** | Model (tutte le 12 entità) |
| **Descrizione** | Interfaccia di connessione al database relazionale per la persistenza dei dati. |

| Metodo | Ritorno |
| :---- | :---- |
| `getIdDBMS()` | — |
| `setIdDBMS(id)` | void |

        20. ###  API Controllo

| Proprietà | Valore |
| :---- | :---- |
| **Classe Realizzazione** | Mezzo : IoT |
| **Consumatore** | GestioneCorsa (Controller) |
| **Descrizione** | Interfaccia fisica col veicolo per blocco/sblocco remoto. Orphan nell'XMI del diagramma componenti (nessun realization collegato a Mezzo:IoT). |

| Metodo | Ritorno |
| :---- | :---- |
| `bloccoMezzoFisico(idMezzo)` | bool |
| `sbloccoMezzoFisico(idMezzo)` | bool |
| `getIdMezzoIoT()` | — |
| `setIdMezzoIoT(id)` | void |

    4. ## **Detailed Product Design**

       1. ### **Diagramma delle Classi**


2. ### **Specifiche delle Classi**

### **2.4.2.1 Classi View**

Sono le Classi che si occupano dell'interazione con l'utente e della visualizzazione dell'interfaccia del programma e dei risultati delle elaborazioni. Le View sono progettate per essere disaccoppiate dal Model \[pattern MVC con Controller Intermediario\]. Ogni interazione avviene tramite richieste esplicite ai Controller. La modellazione segue un approccio ibrido, prevalentemente funzionale, in cui l'oggetto UML Classe è usato come un Modulo di Funzioni.

3. **AppUtente**: ha la responsabilità di fornire l'interfaccia utente per il cittadino fruitore dei servizi di sharing. Gestisce la visualizzazione di mappe, QR code, dettagli dei mezzi, costi delle corse e stime. Fornisce metodi per la ricerca dei mezzi, la selezione del veicolo, l'avvio/sospensione/terminazione della corsa, l'inserimento della destinazione per l'ottimizzazione del percorso, la gestione dei metodi di pagamento (inserimento dati carta, selezione metodo salvato) e la scansione del QR code per lo sblocco del mezzo. Comunica con i Controller GestioneCorsa, GestorePagamento, RicercaMezzi, GestionePrenotazione e GestioneAutenticazione tramite l'interfaccia `Aggiornamenti Corsa`. Realizza l'interfaccia View `Aggiornamenti Corsa` consumata dal Controller.  
     
4. **AppOperatoreTecnico**: ha la responsabilità di fornire la dashboard per l'operatore tecnico, consentendo la visualizzazione dello stato della flotta, l'emissione di comandi remoti (blocco/sblocco) e la gestione della manutenzione dei veicoli. Comunica con i Controller GestioneFlotta e GestioneAutenticazione tramite l'interfaccia `Stato Flotta`. Realizza l'interfaccia View `Stato Flotta` consumata dal Controller.  
     
5. **AppOperatoreSC**: ha la responsabilità di fornire l'interfaccia per l'operatore del servizio clienti, consentendo la moderazione degli account utente (sospensione/disattivazione, consultazione report) e l'amministrazione delle prenotazioni (visualizzazione lista, selezione, aggiornamento report). Comunica con i Controller GestioneUtenti, GestionePrenotazione e GestioneAutenticazione tramite l'interfaccia `Eventi Utente`. Realizza l'interfaccia View `Eventi Utente` consumata dal Controller.  
     
6. **AppPA**: ha la responsabilità di fornire l'interfaccia per la Pubblica Amministrazione, consentendo l'analisi delle statistiche sulle corse, il monitoraggio dello stato della flotta, la gestione delle restrizioni geografiche (creazione, modifica, sovrascrittura) e la visualizzazione delle zone sulla mappa. Comunica con i Controller GestioneFlotta, GestioneStatistiche, GestioneAree e GestioneAutenticazione tramite l'interfaccia `Diagnostica`. Realizza l'interfaccia View `Diagnostica`  consumata dal Controller.  
     
7. **Autenticazione (View)**: ha la responsabilità di fornire l'interfaccia di pre-autenticazione per il login e la registrazione dell'utente nel sistema. Gestisce la visualizzazione del form di registrazione e l'inserimento delle credenziali (nome, cognome, email, password, data di nascita). Comunica con il Controller GestioneAutenticazione tramite l'interfaccia `Stato Sessione`. Realizza l'interfaccia View `Stato Sessione` consumata dal Controller.

**2.4.2.2 Specifica Classi Controller**  
Sono le Classi che si occupano della gestione delle richieste dell'Utente, dell'orchestrazione del flusso MVC e della elaborazione dei dati. Intercettano gli input della View, validano le richieste, interrogano o aggiornano il Model e, una volta elaborata la risposta, indirizzano e formattano i dati per la View. Ogni interazione è mediata dal Controller — le View non interrogano mai direttamente il Model. 

- **GestioneAutenticazione**: ha la responsabilità di validare le credenziali, gestire la registrazione degli utenti e amministrare le sessioni di autenticazione. Fornisce i metodi per la verifica dei dati di registrazione (`verificaValidita`), l'invio delle credenziali per il login (`invioCredenziali`) e la richiesta di logout (`inviaRichiestaLogout`). Comunica con la View Autenticazione fornendo l'interfaccia `Gestione Sessioni`   
  È consumata da AppUtente, AppOperatoreTecnico, AppOperatoreSC, AppPA e Autenticazione. Accede al Model tramite l'interfaccia `Gestione Dati Utente` per le operazioni sugli attori.  
    
- **GestioneUtenti**: ha la responsabilità di eseguire le operazioni di moderazione degli account utente, inclusa la sospensione e la disattivazione. Fornisce i metodi per la gestione dello stato dell'utente (`gestioneUtente`) e la ricerca dei report (`cercaReport`). Comunica con la View AppOperatoreSC tramite l'interfaccia `Moderazione Utente` . Accede al Model tramite l'interfaccia `Gestione Dati Utente`.  
    
- **RicercaMezzi**: ha la responsabilità di eseguire query geolocalizzate sui mezzi disponibili, con raggi multipli (raggio base 2km, raggio esteso 5km — default da UC.UT.01). Fornisce i metodi per visualizzare i mezzi vicini a una coordinata (`visualizzaMezziVicini`) e le specifiche di un singolo mezzo (`visualizzaSpecifiche`). Comunica con la View AppUtente tramite l'interfaccia `Gestione Corsa` Dipende dal sistema esterno Servizio Mappa tramite l'interfaccia `API Mappa` per le funzionalità di geolocalizzazione. Accede al Model tramite l'interfaccia `Gestione Dati Corsa`.  
    
    
    
- **GestioneCorsa**: ha la responsabilità di orchestrare l'intero ciclo di vita della corsa: avvio, sospensione, riattivazione, terminazione, calcolo del percorso e stima dei costi. Fornisce metodi per avviare la corsa (`avviaCorsa`), terminarla (`terminaCorsa`), sospenderla (`sospensioneCorsa`), controllare la disponibilità del mezzo (`controllaDisponibilita` con due overload), aggiornare la stima dei costi (`aggiornaStima`), richiedere lo sblocco tramite QR code (`richiediSblocco`), calcolare il percorso ottimale (`richiediCalcoloPercorso` delegando a Servizio Mappa) e acquisire la scelta del metodo di pagamento (`acquisisciSceltaMetodo`). Comunica con la View AppUtente tramite l'interfaccia `Gestione Corsa`. Dipende dai sistemi esterni Servizio Mappa (tramite `API Mappa`) per il routing e Mezzo:IoT (tramite `API Controllo`) per il blocco/sblocco fisico del veicolo. Accede al Model tramite l'interfaccia `Gestione Dati Corsa`. È in relazione di associazione con Mezzo (1..\* a 0..*) e Corsa (0..* a 1).  
    
- **GestorePagamento**: ha la responsabilità di elaborare le transazioni economiche e validare i metodi di pagamento, delegando al sistema esterno Gateway Pagamento. Fornisce metodi per processare il pagamento di una corsa (`pagamentoCorsa`), elaborare i dati di una nuova carta (`elaboraDatiCarta`) e recuperare i metodi salvati dall'utente (`recuperaMetodiSalvati`). Comunica con la View AppUtente tramite l'interfaccia `Gestione Corsa`. Dipende dal sistema esterno Gateway Pagamento tramite l'interfaccia `API Pagamento` Accede al Model tramite l'interfaccia `Gestione Dati Corsa`. È in relazione di associazione con MetodoPagamento (0..\* a 0..\*).  
    
- **GestioneFlotta**: ha la responsabilità di monitorare e controllare la flotta di veicoli da remoto, inclusi il blocco dei mezzi e l'avvio della manutenzione. Fornisce metodi per analizzare lo stato della flotta (`analisiStatoFlotta` — rileva mezzi da manutenere e crea Segnalazione), bloccare un mezzo (`bloccaMezzo`), avviare la manutenzione (`avviaManutenzione`) e ottenere le condizioni dei mezzi (`getCondizioniMezzi` per visualizzazione dashboard). Comunica con le View AppOperatoreTecnico (tramite l'interfaccia `Amministrazione Flotta`, e AppPA (tramite l'interfaccia `Statistiche e Restrizioni`). Accede al Model tramite l'interfaccia `Gestione Dati Corsa` per i dati dei mezzi e tramite `Gestione Dati Supporto` per le segnalazioni. È in relazione di associazione con Mezzo (0..\* a 0..*) e Segnalazione (0..* a 1).  
    
- **GestionePrenotazione**: ha la responsabilità di gestire il ciclo di vita delle prenotazioni, con timeout automatico a 15 minuti (default da UC.UT.02). Fornisce metodi per inviare una richiesta di prenotazione (`inviaRichiestaPrenotazione`), richiedere la lista delle prenotazioni (`richiediLista`), annullare una prenotazione (`annullaPrenotazione`), gestire il timeout (`gestisciTimeout`), notificare la scadenza del tempo (`notificaScadenzaTempo`) e concludere la prenotazione una volta avviata la corsa (`concludiPrenotazione`). Comunica con le View AppUtente e AppOperatoreSC tramite le rispettive interfacce Controller (`Gestione Corsa` e `Moderazione Utente`). Accede al Model tramite l'interfaccia `Gestione Dati Supporto`. È in relazione di associazione con Mezzo (1..\* a 0..*), Prenotazione (0..* a 1\) e Segnalazione (0..\* a 1).  
    
- **GestioneStatistiche**: ha la responsabilità di aggregare i dati delle corse e generare report statistici per la Pubblica Amministrazione. Fornisce metodi per analizzare le tratte in un periodo (`analisiTratte`) e generare il file statistiche (`generaFileStatistiche`). Comunica con la View AppPA tramite l'interfaccia `Statistiche e Restrizioni`. Accede al Model tramite le interfacce `Gestione Dati Corsa` (per i dati delle corse) e `Gestione Dati Supporto` (per i dati dei transiti). È in relazione di associazione con Corsa (0..\* a 0..*) e Transito (0..* a 0..\*).  
    
- **GestioneAree**: ha la responsabilità di eseguire operazioni sulle zone geografiche e verificare i conflitti tra restrizioni sovrapposte. Fornisce metodi per aggiornare una restrizione (`aggiornaRestrizione`), analizzare i conflitti tra zone (`analisiConflitti`) e ottenere tutte le zone geografiche (`getZoneGeografiche`). Comunica con la View AppPA tramite l'interfaccia `Statistiche e Restrizioni`. Accede al Model tramite l'interfaccia `Gestione Dati Supporto`. È in relazione di associazione con ZonaGeografica (0..\* a 0..\*).

## **2.4.2.3 Specifica Classi Model**

Sono le Classi che modellano il Dominio di Business dal punto di vista dei dati. Sono presenti solo getter e setter come operazioni perché le istanze non hanno altra utilità se non quella di visualizzare e modificare i valori contenuti nei suoi campi (Model passivo). Ogni entità dipende da DBMS per la persistenza tramite l'interfaccia `Connessione Dati` 

3.1 Attore (abstract)  
Classe base astratta per tutti gli attori del sistema. Utilizza strategia JOINED inheritance per la persistenza (tabella `attore` base con `utente`, `operatore`, `pa` collegate 1:1). Modella le credenziali di accesso (email, password cifrata) e il ruolo dell'attore nel sistema tramite l'enum RuoloAttore. È in relazione di generalizzazione con Utente, Operatore e PA.

3.2 Utente (extends Attore)

Modella l'entità del cittadino fruitore dei servizi di sharing (bicicletta, monopattino, auto). Contiene i dati anagrafici (nome, cognome, telefono), la posizione corrente (coordinateUtente), lo stato dell'account (attivo, sospeso, disattivato), il report di moderazione e il contatore dei mezzi attualmente prenotati. Fornisce metodi per la ricerca di un utente (`ricercaUtente`), l'azione correttiva sulla moderazione (`azioneCorrettiva`) e la creazione dell'account (`creaAccountUtente`). Eredita email, password, id e ruolo da Attore. È in relazione di associazione 1 a Molti con Corsa e Prenotazione.

3.3 Operatore (extends Attore)

Modella l'entità del personale professionale del servizio, distinto in Tecnico o Servizio Clienti tramite l'enum TipoOperatore (`tipo`). Non esistono classi separate per ciascun tipo — la distinzione è data dal valore dell'enum. Eredita email, password, id e ruolo da Attore.

3.4 PA (extends Attore)

Modella l'entità della Pubblica Amministrazione (ente comunale) con privilegi di analisi, statistiche e gestione delle restrizioni geografiche. L'attributo `idPA` coincide con `Attore.id`. Eredita email, password, id e ruolo da Attore.

3.5 Mezzo

Modella l'entità del veicolo della flotta (bicicletta, monopattino, auto). Traccia lo stato (disponibile, prenotato, in\_uso, sospeso, bloccato, manutenzione), la posizione (coordinateMezzo), l'autonomia residua, il costo orario, la velocità massima, la condizione fisica, il tipo di veicolo, l'identificativo della flotta di appartenenza e il tempo di disponibilità. Fornisce metodi di interrogazione avanzati come la ricerca per flotta (`getMezzibyFlotta`), la ricerca geospaziale (`getMezziInArea`) e l'ottenimento dei dettagli completi (`getDettagliMezzo`). È in relazione di associazione 1 a Molti con Corsa (un mezzo può essere utilizzato in più corse).

3.6 Corsa

Modella la sessione di utilizzo di un mezzo dall'avvio al termine. Include il costo totale, gli orari di inizio e fine, le coordinate di partenza e arrivo, e le foreign key verso MetodoPagamento e Utente. Fornisce metodi per la creazione della corsa (`creaCorsa`), la ricerca per ID (`ricercaCorsa`), l'interrogazione per periodo (`getCorseByPeriodo`) e l'aggiornamento del costo (`aggiornaCosto`). È in relazione di associazione con Mezzo (0..\* a 1\) — una corsa è sempre associata a un utente e un mezzo.

3.7 MetodoPagamento

Modella i dati cifrati della carta di credito/debito associata a un utente. Utilizza una chiave surrogata (`idMetodoPagamento`) come PK invece del numero carta per sicurezza secondo pattern PCI-DSS. Contiene il numero carta (cifrato) e l'intestatario. Fornisce metodi per la creazione (`creaMetodoPagamento`), la verifica di esistenza (`controllaMetodoEsistente`) e la ricerca per utente (`getMetodoByUtente`). È in relazione di associazione con GestorePagamento (0..\* a 0..\*).

3.8 Prenotazione

Modella il blocco temporaneo di un mezzo, con timeout automatico a 15 minuti (default da UC.UT.02). Traccia lo stato (attiva, scaduta, annullata, completata), l'orario di inizio, la data, e le foreign key verso Utente e Mezzo. Fornisce metodi per la creazione (`creaPrenotazione`) e la ricerca per stato (`getPrenotazioneByStato`). È in relazione di associazione con GestionePrenotazione (0..\* a 1).

3.9 Segnalazione

Modella il report di anomalia su un mezzo: guasto, necessità di manutenzione, veicolo non raggiungibile. Traccia lo stato (aperta, in\_lavorazione, chiusa), l'ora, la data e la foreign key verso Mezzo. Fornisce il metodo di creazione (`creaSegnalazione`). È in relazione di associazione con GestioneFlotta (0..\* a 1\) e GestionePrenotazione (0..\* a 1).

3.10 ZonaGeografica

Modella un'area geografica con restrizioni di circolazione o sosta per i mezzi (divieto\_parcheggio, ZTL, limite\_velocita). Contiene il tipo di restrizione, le note descrittive e la geometria della zona (LineString). Fornisce metodi per la verifica di sovrapposizioni (`verificaSovrapposizioni`), il controllo se una coordinata ricade nell'area (`checkArea`), la creazione (`creaZonaGeografica`), l'interrogazione delle restrizioni per coordinate (`getRestrizioniZona`) e il salvataggio (`salvaRestrizioni`). È in relazione di associazione con GestioneAree (0..\* a 0..\*). Il vincolo architetturale AP.04 richiede la validazione tramite `checkArea()` prima della terminazione di una corsa.

3.11 Transito

Modella l'associazione M:N tra Corsa e ZonaGeografica per tracciare le zone attraversate durante una corsa. Contiene le foreign key verso Corsa (`idCorsa`) e ZonaGeografica (`idArea`). Fornisce il metodo per ottenere i transiti di una corsa (`getTransitiByCorsa`).

## **2.4.2.4 Specifica Sistemi Esterni (Infrastruttura)**

Tutti i sistemi esterni sono simulati (progetto universitario — chiarimenti-vari.md punto 16). La modellazione segue un approccio ibrido, prevalentemente funzionale, in cui l'oggetto UML Classe è usato come un Modulo di Funzioni.

- **Mezzo : IoT**: ha la responsabilità di fornire l'interfaccia fisica col veicolo per le operazioni di blocco e sblocco remoto. Fornisce i metodi `bloccoMezzoFisico(idMezzo)` e `sbloccoMezzoFisico(idMezzo)`. L'interfaccia `API Controllo` è definita ma senza realization collegato a Mezzo:IoT nell'XMI del diagramma componenti (orphan XMI). È consumata da GestioneCorsa (Controller) per lo sblocco tramite QR code e il blocco in sospensione/termine corsa.  
    
- **Gateway Pagamento**: ha la responsabilità di processare i pagamenti e convalidare le carte di credito/debito come processore di pagamento esterno. Fornisce i metodi `effettuaPagamento(idMetodoPagamento, idCorsa)` (correzione typo XMI da EffettuaPagamento) e `convalidaCarta(numCarta, dsCarta, cvv, intestatarioCarta)`. Realizza l'interfaccia `API Pagamento` È consumata da GestorePagamento (Controller).  
    
- **Servizio Mappa**: ha la responsabilità di fornire servizi di geolocalizzazione e routing esterno per il calcolo di percorsi tra coordinate. Fornisce il metodo `getPercorso(coordinateIniziali, coordinateFinali, restrizioni)` (correzione typo XMI da coorfinateFinali a coordinateFinali). Realizza l'interfaccia `API Mappa` .  
  È consumata da RicercaMezzi e GestioneCorsa (Controller) per le funzionalità di georouting.  
    
- **DBMS**: ha la responsabilità di fornire la persistenza dei dati verso un database relazionale. Realizza l'interfaccia `Connessione Dati` . È consumata da tutte le entità del Model (Attore, Corsa, MetodoPagamento, Mezzo, Prenotazione, Segnalazione, Transito, ZonaGeografica) per le operazioni di persistenza. I metodi `getIdDBMS()` e `setIdDBMS(id)` presenti nell'XMI sono inclusi per completezza di tracciabilità ma non costituiscono l'interfaccia funzionale.


9. # **Glossario**

   1. ## **Definizioni**

## 1\. Acronimi

| Acronimo | Significato |
| :---- | :---- |
| API | Application Programming Interface |
| IoT | Internet of Things |
| ORM | Object-Relational Mapping |
| PCI-DSS | Payment Card Industry Data Security Standard |
| QR | Quick Response (codice QR) |
| ZTL | Zona a Traffico Limitato |
| PA | Pubblica amministrazione |
| SC | Servizio Clienti |

## 2\. Definizioni

2.1 Smart Urban Mobility

Sistema intelligente di mobilità urbana che integra servizi di bike, car e monopattino sharing per il comune di una città generica con copertura WiFi full-range. L'obiettivo è bilanciare le esigenze di utenti, operatori e Pubblica Amministrazione.

2.2 Attore

Generalizzazione di Utente, Operatore e Pubblica Amministrazione. Indica anche la persona non ancora autenticata (non loggata) che può interagire solo con la View Autenticazione fino al completamento del login 

2.3 Utente  
Cittadino fruitore dei servizi di sharing (bicicletta, monopattino, auto). Può cercare mezzi disponibili, prenotare, avviare/sospendere/terminare corse, gestire metodi di pagamento. 

2.4 Operatore  
Personale professionale del servizio, distinto in due categorie identificate dall'enum `TipoOperatore`: Operatore Tecnico (gestione flotta e mezzi) e Operatore Servizio Clienti (moderazione utenti e amministrazione prenotazioni). Le credenziali di accesso sono pre-generate e fornite dall'amministrazione. 

2.5 Pubblica Amministrazione (PA)

Ente comunale con privilegi di analisi, visualizzazione di statistiche, monitoraggio dello stato della flotta e gestione delle restrizioni geografiche. Le credenziali di accesso sono pre-generate e fornite dall'amministrazione. 

2.6 Mezzo

Veicolo della flotta (bicicletta, monopattino, auto) con stato, posizione, autonomia residua, costo orario, velocità massima, condizione fisica e tempo di disponibilità. 

2.7 Corsa

Sessione di utilizzo di un mezzo dall'avvio al termine. Include il costo totale, gli orari di inizio e fine, le coordinate di partenza e arrivo.Una corsa è sempre associata a un Utente, un Mezzo e un MetodoPagamento.

2.8 Flotta

Insieme di mezzi identificati da un comune identificativo `idFlotta`. La flotta è gestita dagli Operatori Tecnici per il monitoraggio e la manutenzione, e dalla PA per l'analisi delle condizioni.

2.9 MetodoPagamento

Dati della carta di credito/debito associata a un utente. I dati della carta sono memorizzati e cifrati.

2.10 Prenotazione

Blocco temporaneo di un mezzo con validità fino a 15 minuti dall'orario di inizio della prenotazione (timeout). Se l'utente non avvia la corsa entro questo intervallo, la prenotazione viene automaticamente annullata e il mezzo torna disponibile. Gli stati possibili sono: attiva, scaduta, annullata, completata.

2.11 Segnalazione  
Report di anomalia su un mezzo (guasto, necessità di manutenzione, veicolo non raggiungibile). Creata dal sistema tramite `GestioneFlotta.analisiStatoFlotta()`. Il ciclo di vita dello stato segue: `aperta → in_lavorazione → chiusa` .

2.12 ZonaGeografica  
Area geografica con restrizioni di circolazione o sosta per i mezzi. Le restrizioni sono definite tramite l'enum `TipoRestrizione` con valori: `divieto_parcheggio` , `ZTL` (Zona a Traffico Limitato) e `limite_velocita`. La geometria della zona è modellata come LineString .

2.13 Transito

Associazione molti-a-molti (M:N) tra Corsa e ZonaGeografica che traccia le zone geografiche attraversate durante una corsa.

2.14 Coordinate

Attributo di tipo String che memorizza tre coordinate spaziali float (x, y, z) parsate come unica stringa. Utilizzato per `coordinateMezzo`, `coordinateUtente`, `coordinatePartenza` e `coordinateArrivo`.

2.15 QR Code

Codice di risposta rapida generato dal sistema, utilizzato come metodo di autenticazione per lo sblocco del mezzo. Viene generato al termine della prenotazione e utilizzato per avviare la corsa. Viene rigenerato durante la sospensione della corsa per la successiva ripresa.

2.16 Sospensione Corsa  
Pausa temporanea della corsa con mantenimento del possesso del mezzo. Il veicolo viene fisicamente bloccato ma rimane assegnato all'utente. Durante la sospensione si applica una tariffa oraria differenziata. La ripresa avviene tramite scansione del QR Code di sospensione.

2.17 Stima Costi

Calcolo del costo finale di una corsa secondo la formula: `stimaCosto = costoOrario * oreUtilizzo + costoSospensione(eventuale)`, dove `costoOrario` è attributo di Mezzo, `oreUtilizzo = (orarioFine - orarioInizio)` in ore, e `costoSospensione` è il costo accumulato durante eventuali sospensioni. 

2.18 Raggio di Ricerca  
Parametro di geolocalizzazione per la ricerca dei mezzi disponibili. Definito con due valori: raggio base (2 km di default) e raggio esteso (5 km di default). Se nessun mezzo viene trovato nel raggio base, il sistema propone all'utente di espandere la ricerca al raggio esteso.

2.19  MVC con Controller Intermediario (fat controller)  
Pattern architetturale adottato dal sistema, variante moderna (Web-oriented) del pattern Model-View-Controller. Centralizza l'intero flusso di controllo e scambio dati all'interno dei componenti Controller, che mediano tra View, Model e sistemi esterni. Le View non interrogano mai direttamente il Model.

2.20 Sistemi Esterni Simulati  
I componenti Servizio Mappa, Gateway Pagamento, DBMS e Mezzo:IoT sono simulati in quanto trattasi di progetto universitario. Le interfacce sono definite ma senza implementazione reale.

2.21 Chiave Surrogata  
Identificatore artificiale utilizzato come PK al posto di un identificatore naturale. Nel sistema, `idMetodoPagamento` è una chiave surrogata che sostituisce il numero della carta per motivi di sicurezza PCI-DSS .

2.22 ReportUtente

Stringa associata all'account di un utente che contiene informazioni relative alla moderazione. Può essere consultata e aggiornata dall'Operatore Servizio Clienti.

2.23 Autonomia

Autonomia residua di un mezzo, espressa in float. Indica la distanza o il tempo di utilizzo residuo prima che il mezzo necessiti di ricarica/manutenzione.

2.24 Costo Orario  
Costo per ora di utilizzo di un mezzo, attributo float della classe Mezzo. Utilizzato nella formula di calcolo della stima costi di una corsa. 

2.25 LineString  
Tipo di dato geometrico che rappresenta una linea composta da una sequenza di punti. Utilizzato per modellare il campo zona di una ZonaGeografica.

2.26 Servizio Mappa  
Sistema esterno (simulato) di geolocalizzazione e routing che fornisce il calcolo del percorso ottimale tra coordinate, tenendo conto delle restrizioni geografiche. Espone l'interfaccia `API Mappa` con il metodo `getPercorso()` .

2.27 Gateway Pagamento  
Sistema esterno (simulato) che processa i pagamenti e convalida le carte di credito/debito. Espone l'interfaccia `API Pagamento` con i metodi `effettuaPagamento()` e `convalidaCarta()` .

2.28 Mezzo:IoT  
Sistema esterno (simulato) che fornisce l'interfaccia fisica col veicolo per le operazioni di blocco e sblocco remoto. Espone l'interfaccia `API Controllo` con i metodi `bloccoMezzoFisico()` e `sbloccoMezzoFisico()` .

2.29 DBMS

Sistema esterno (simulato) per la persistenza dei dati. Tutte le entità del Model dipendono da DBMS per la persistenza.

2.30 Chiave Primaria (PK)  
Identificatore univoco per ogni record di una tabella del database.

2.31 Chiave Esterna (FK)  
Attributo che referenzia la chiave primaria di un'altra tabella per stabilire relazioni.

2.32 RuoloAttore (enum)  
Enumerazione che definisce i tre ruoli possibili nel sistema: `Utente` (cittadino fruitore), `Operatore` (personale tecnico o servizio clienti), `PA` (Pubblica Amministrazione). Determina il routing post-login e le View accessibili.

2.33 TipoOperatore (enum)  
Enumerazione che distingue le due categorie di Operatore: `OperatoreTecnico` (gestione flotta e mezzi) e `OperatoreSC` (Servizio Clienti e moderazione). La distinzione è data dal valore dell'enum, non da classi separate.

2.34 StatoUtente (enum)

Enumerazione dello stato dell'account utente: `attivo` (funzionante), `sospeso` (temporaneamente bloccato), `disattivato` (permanentemente disabilitato).

2.35 StatoMezzo (enum)  
Enumerazione dello stato del veicolo: `disponibile` (libero e prenotabile), `prenotato` (bloccato da prenotazione attiva), `in_uso` (corsa in corso), `sospeso` (corsa in pausa temporanea), `bloccato` (blocco remoto da operatore), `manutenzione` (fuori servizio per intervento tecnico) .

2.36 StatoPrenotazione (enum)

Enumerazione dello stato della prenotazione: `attiva` (valida entro i 15 minuti), `scaduta` (timeout superato, annullata automaticamente), `annullata` (cancellata da utente o operatore SC), `completata` (prenotazione onorata, corsa avviata) .

2.37 StatoSegnalazione (enum)

Enumerazione dello stato della segnalazione: `aperta` (creata, in attesa), `in_lavorazione` (in gestione), `chiusa` (risolta e archiviata) .

2.38 TipoRestrizione (enum)

Enumerazione dei tipi di restrizione geografica: `divieto_parcheggio` (divieto di sosta), `ZTL` (Zona a Traffico Limitato), `limite_velocita` (limite di velocità). Ogni ZonaGeografica ha sempre un tipoRestrizione valido.

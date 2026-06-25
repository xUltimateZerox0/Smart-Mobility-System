
**Cofee Coders**

**Smart Mobility System**
**Type:** Strategic

Versione 3.0

Data di rilascio:

25/06/2026

Ingegneria del Software a.a. 2025-2026  
\[CdL\]

Indice

**1\.**	**Product Backlog	[5](#product-backlog)**

1.1	Introduzione	[5](#introduzione)  
1.2	Contesto di business	[5](#contesto-di-business)  
1.3	Stakeholder	[5](#stakeholder)  
1.4	Item funzionali	[5](#item-funzionali)  
*1.4.1*	*IF-1	[5](#if-c1)*  
*1.4.2*	*IF-2	[5](#if-2)*  
*1.4.3*	*IF-n	[5](#if-n)*  
1.5	Item non funzionali	[5](#item-non-funzionali)  
*1.5.1*	*Item Informativi	[6](#ut.07-come-utente,-voglio-utilizzare-un-metodo-di-autenticazione-per-sbloccare-il-mezzo,-così-da-evitare-accessi-non-autorizzati.)*  
*1.5.2*	*Item di interfaccia	[6](#item-di-interfaccia)*  
*1.5.3*	*Item Qualitativi	[6](#item-qualitativi)*  
*1.5.4*	*Altri Item	[6](#altri-item)*

**2\.**	**Sprint Report	[8](#sprint-report)**

2.1	Sprint Backlog	[8](#sprint-backlog)  
2.2	Product Requirement Specification	[9](#product-requirement-specification)  
*2.2.1*	*Diagramma dei Casi d’uso	[9](#diagramma-dei-casi-d’uso)*  
*2.2.2*	*Specifiche dei Casi d’uso	[9](#specifiche-dei-casi-d’uso)*  
*2.2.3*	*Altro	[9](#altro)*  
2.3	System Architecture	[9](#system-architecture)  
*2.3.1*	*Diagramma delle Componenti	[9](#diagramma-delle-componenti)*  
*2.3.2*	*Specifica delle componenti	[9](#specifica-delle-componenti)*  
*2.3.3*	*Specifica delle interfacce	[9](#per-la-progettazione-e-lo-sviluppo-del-sistema,-è-stato-adottato-il-pattern-architetturale-model-view-controller-\(mvc\)-nella-sua-variante-moderna-\(web-oriented-/-con-controller-intermediario\).)*  
2.4	Detailed Product Design	[9](#detailed-product-design)  
*2.4.1*	*Diagramma delle Classi	[9](#diagramma-delle-classi)*  
*2.4.2*	*Specifiche delle Classi	[9](#specifiche-delle-classi)*  
*2.4.3*	*Diagrammi di Sequenza	[9](#heading=h.mu0am2315s7w)*  
2.5	Data modeling and design	[9](#data-modeling-and-design)  
*2.5.1*	*Modello logico del Database	[10](#modello-logico-del-database)*  
*2.5.2*	*Struttura fisica del Database	[10](#struttura-fisica-del-database)*

**3\.**	**Glossario	[11](#glossario)**

3.1	Acronimi	[11](#acronimi)  
3.2	Definizioni	[11](#definizioni)

                        Product Backlog

**Smart Mobility System**

1. # **Product Backlog** {#product-backlog}

   1. ## **Introduzione**  {#introduzione}

Il progetto consiste in un sistema intelligente di **Smart Urban Mobility** per il comune di **Zootropolis**, volto a integrare servizi di bike, car e scooter sharing. L'obiettivo è bilanciare le esigenze di utenti, operatori e Amministrazione Pubblica, riducendo traffico e inquinamento. Il sistema punta a creare un ecosistema urbano sostenibile e sicuro, ottimizzando la gestione della flotta e contrastando frodi e inefficienze.

2. ## **Contesto di business** {#contesto-di-business}

Il progetto nasce dalla necessità del comune di **Zootropolis** di introdurre un sistema di mobilità sostenibile (Smart Urban Mobility) che integri diversi servizi di sharing (bike, car ed e-scooter). Il contesto è quello di una crescente urbanizzazione e congestione stradale, dove l'obiettivo è l'integrazione con il paradigma di **Smart City**.

3. ## **Stakeholder** {#stakeholder}

Il sistema deve funzionare in un contesto urbano con:

* **Utenti**: Cercano un accesso rapido, un'esperienza fluida, sicurezza nei pagamenti e trasparenza per evitare frodi.    
* **Operatori del Servizio**: Puntano all'ottimizzazione della flotta (redistribuzione dei mezzi), alla riduzione dei costi operativi e alla prevenzione di furti, vandalismi e frodi sugli account.    
  Gli operatori sono divisi in due categorie:  
1. **Operatori Tecnici**: dedicati alla gestione dei mezzi e al corretto funzionamento di essi e del sistema.  
2. **Operatori del servizio clienti**: dedicati alla moderazione dei clienti sul sistema.  
* **Pubblica Amministrazione (P.A.)**: Mira alla riduzione di traffico e inquinamento e necessita di dati affidabili per la pianificazione urbana e delle infrastrutture (es. piste ciclabili).

  4. ## **Item funzionali** {#item-funzionali}

**Utenti**

* **UT.01**

  *Come* utente, 

  *Voglio* visualizzare i mezzi disponibili in un raggio prestabilito a partire dalla posizione scelta, 

  *Così da* poter iniziare una corsa.

* **UT.02**

  *Come* utente, 

  *Voglio poter* prenotare dei mezzi se disponibili,

  *Così da* trovarli a disposizione quando arrivo.

* **UT.03**

  *Come* utente, 

  Voglio visualizzare l’importo della corsa in tempo reale, 

  *Così da* sapere quanto sto pagando.

* **UT.04**

  *Come* utente, 

  *Voglio* consultare le specifiche tecniche del mezzo disponibile, 

  *Così* da effettuare una scelta. 

* **UT.05**

  *Come* utente, 

  *Voglio* conoscere l’orario stimato per la disponibilità di un mezzo, 

  *Così* da valutare l’utilizzo del servizio.

* **UT.06**

  *Come* utente, 

  *Voglio* visualizzare il percorso che richiede meno tempo,

  *Così* da minimizzare la durata del viaggio.

* **UT.08**  
  *Come* utente,   
  *Voglio* poter inserire un metodo di pagamento nel mio account,   
  *Così da* permettere l'addebito al termine di ogni utilizzo.

* **UT.09**  
  *Come* utente,   
  *Voglio* poter effettuare delle soste senza perdere il possesso del mezzo,  
  *Così* da poter sospendere la mia corsa.

* **UT.10**

  *Come* utente,

  *Voglio* visualizzare le aree non accessibili al mezzo,

  *Così da* pianificare il percorso correttamente.

**Amministrazione Pubblica**

* **AP.01**

  *Come* amministrazione comunale, 

  *Voglio* accedere alle statistiche di utilizzo del sistema,

  *Così da* supportare decisioni strategiche.

* **AP.02**

  *Come* amministrazione comunale,

  *Voglio* analizzare le condizioni fisiche dei mezzi,

  *Così da* poter intervenire in caso di necessità.

* **AP.03**

  *Come* amministrazione comunale,  
  *Voglio* poter conoscere le tratte più utilizzate,  
  *Così da* poter pianificare la manutenzione in specifiche aree.

**Operatore del Servizio**

* **OP.01**

  *Come* operatore, 

  *Voglio* visualizzare la distribuzione dei mezzi, 

  *Così da* ottimizzare il posizionamento della flotta

* **OP.02**

  *Come* operatore,

  *Voglio* conoscere l’anagrafica dell’utente 

  *Così da* poter risalire ad eventuali furti ed incidenti

* **OP.03**  
  *Come* operatore,   
  *Voglio* poter moderare l'account di un utente,   
  *Così da* prevenire futuri utilizzi in caso di violazione dei termini di servizio

* **OP.04**  
  *Come* operatore,   
  *Voglio poter* forzare il blocco da remoto di un mezzo se fuori dalle zone consentite,  
  *Così da* prevenire violazioni dei termini di servizio

* **OP.05**  
  *Come* operatore,   
  *Voglio* amministrare le prenotazioni sui mezzi*,*  
  *Così da* moderarne l’utilizzo

  1. ### **IF-C1** {#if-c1}

     2. ### **IF-2** {#if-2}

     3. ### **IF-n** {#if-n}

  5. ## **Item non funzionali** {#item-non-funzionali}

* **UT.07**  
  *Come* utente,   
  *Voglio* utilizzare un metodo di autenticazione per sbloccare il mezzo,  
  *Così da* evitare accessi non autorizzati.

* **AP.04**

  *Come amministrazione comunale*, 

  *Voglio impedire che i mezzi vengano* lasciati in aree non designate al termine della corsa,

  *Così da* evitare posizionamenti illeciti.

  1. ### **Item Informativi**

     1. #### **IIN-1**

        2. #### **IIN-2**

        3. #### **IIN-n**

     2. ### **Item di interfaccia** {#item-di-interfaccia}

        1. #### **IUI-1**

        2. #### **IUI-2**

        3. #### **IUI-n**

     3. ### **Item Qualitativi** {#item-qualitativi}

        1. #### **IQ-1**

        2. #### **IQ-2**

        3. #### **IQ-n**

     4. ### **Altri Item** {#altri-item}

Sprint Report N. 3

**Smart Mobility System**

2. # **Sprint Report** {#sprint-report}

   1. ## **Sprint Backlog** {#sprint-backlog}

![][image1]

2. ## **Product Requirement Specification**  {#product-requirement-specification}

   1. ### **Diagramma dei Casi d’uso** {#diagramma-dei-casi-d’uso}

![][image2]

2. ### **Specifiche dei Casi d’uso** {#specifiche-dei-casi-d’uso}

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **UT.01, UT.04, UT.05** |
| **Nome** | **Ricerca Mezzi** |
| **ID** | **UC.UT.01** |
| **Breve descrizione** | **L'utente avvia una ricerca dei mezzi nelle vicinanze della propria posizione. Il sistema esegue una query geolocalizzata con un raggio iniziale (raggio base, es. 2 km). Se non vengono trovati risultati, l'utente può scegliere di estendere la ricerca a un raggio superiore (raggio esteso, es. 5 km). I mezzi trovati vengono mostrati all'utente che può selezionarne uno per consultare le relative specifiche.** |
| **Attori principali** | **Utente** |
| **Precondizioni** | **\- L'utente ha effettuato l'accesso. \- Le coordinate GPS della posizione attuale dell'utente sono presenti nel sistema.** |
| **Flusso principale** |  **Il caso d'uso inizia quando l'utente avvia la ricerca dei mezzi nelle vicinanze. Il sistema effettua la ricerca dei mezzi nel raggio base utilizzando le coordinate dell'utente. Il sistema mostra la lista dei mezzi trovati all'utente. L'utente seleziona un mezzo Il sistema recupera i dettagli del mezzo e li mostra all'utente.**  |
| **Flussi alternativi** | ***Nessun mezzo trovato nel raggio base – Espansione accettata:* Al passaggio 3, la ricerca non restituisce mezzi. Il sistema propone l'espansione del raggio. L'utente accetta l'espansione. Il sistema esegue la ricerca con il nuovo raggio e mostra i risultati. *Nessun mezzo trovato nel raggio espanso / Utente rifiuta espansione:* Il sistema mostra un messaggio di errore indicando che nessun mezzo è disponibile.** |
| **Postcondizioni** |  **L’elenco e le specifiche dei mezzi disponibili sono stati recuperati dal sistema** |
| **Include** | **\-** |
| **Estende** | **\-** |
| **Esteso dal caso d'uso** | **UC.UT.02 (Prenotazione Mezzo)** |
| **Specializza il caso d'uso** | **\-** |
| **Generalizza il caso d'uso** | **\-** |
| **Requisiti** | **Servizio di geolocalizzazione attivo per il calcolo delle coordinate utente. ** |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **UT.02** |
| **Nome** | **Prenotazione Mezzo** |
| **ID** | **UC.UT.02** |
| **Breve descrizione** | **L'utente seleziona un mezzo disponibile e ne richiede la prenotazione. Il sistema verifica la disponibilità, aggiorna lo stato del mezzo a “prenotato”. È previsto un meccanismo automatico di annullamento se l'utente non raggiunge il mezzo entro 15 minuti dell'orario prenotato.** |
| **Attori principali** | **Utente** |
| **Precondizioni** |  **L'utente ha effettuato l'accesso. La mappa con i mezzi è stata resa disponibile (e quindi visibile) dal sistema.** |
| **Flusso principale** |  **Il caso d'uso inizia quando l'utente seleziona un mezzo disponibile dalla mappa e ne richiede la prenotazione. Il sistema aggiorna lo stato del mezzo a “prenotato” Il sistema registra la prenotazione. Il sistema notifica all'utente l'avvenuta prenotazione e gli fornisce il QR Code.** |
| **Flussi alternativi** | ***Scadenza tempo prenotazione (dopo 15 minuti dall'orario prenotato):* Trascorsi 15 minuti dall'orario prenotato, il sistema rileva il superamento del timeout. Il sistema reimposta lo stato del mezzo a “disponibile". Il sistema invia una notifica di annullamento della prenotazione all'utente per decorrenza dei termini.**  |
| **Postcondizioni** | **1\.   il mezzo risulta “prenotato”. 2\.   La prenotazione è stata registrata.** |
| **Include** | **\-** |
| **Estende** | **UC.UT.01 (Ricerca Mezzi)** |
| **Esteso dal caso d'uso** | **UC.UT.03 (Gestione Corsa)** |
| **Specializza il caso d'uso** | **\-** |
| **Generalizza il caso d'uso** | **\-** |
| **Requisiti** | **Meccanismo di timeout automatico a 15 minuti per l'annullamento delle prenotazioni scadute.**  |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **UT.03, UT.07** |
| **Nome** | **Gestione Corsa** |
| **ID** | **UC.UT.03** |
| **Breve descrizione** | **L'utente scansiona il QR Code per avviare una corsa. Il sistema verifica la disponibilità del veicolo e richiede la selezione di un metodo di pagamento. Al superamento dei controlli, il mezzo viene sbloccato fisicamente, la corsa ha inizio e il sistema avvia un monitoraggio continuo del costo, che così viene mostrato in tempo reale all’utente.** |
| **Attori principali** | **Utente** |
| **Precondizioni** | **1\. Il mezzo selezionato è bloccato. 2\. Il mezzo selezionato risulta prenotato. 3\. L’utente non si trova in una corsa attiva.** |
| **Flusso principale** | **Il caso d'uso inizia quando l'utente scansiona il QR code. Il sistema verifica che il mezzo sia disponibile. Il sistema richiede all’utente di selezionare un metodo di pagamento. L'utente richiede l'avvio della corsa. Il sistema registra la nuova corsa Il sistema sblocca fisicamente il mezzo Il sistema imposta lo stato logico del mezzo a “in uso” Il sistema notifica l'avvio della corsa all'utente Il sistema avvia l'aggiornamento periodico del costo e lo mostra all’utente.** |
| **Flussi alternativi** | **Mezzo non disponibile:  Al passaggio 2 del flusso principale, il sistema rileva che il mezzo non è disponibile. Il sistema mostra un messaggio di errore all'utente, impedendo l'avvio della corsa.** |
| **Postcondizioni** | **Il veicolo risulta sbloccato fisicamente. La corsa è attiva.** |
| **Include** | **UC.UT.05 (Metodo Pagamento), UC.UT.07 (Termina Corsa e Pagamento)** |
| **Estende** | **UC.UT.02 (Prenotazione Mezzo)** |
| **Esteso dal caso d'uso** | **UC.UT.06 (Sospensione Corsa)**  |
| **Specializza il caso d'uso** | **\-** |
| **Generalizza il caso d'uso** | **\-** |
| **Requisiti** | **Interfaccia IoT per sblocco Mezzo remoto** |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **UT.06, UT.10** |
| **Nome** | **Ottimizzazione Percorso** |
| **ID** | **UC.UT.04** |
| **Breve descrizione** | **L'utente inserisce la destinazione desiderata nel sistema, che elabora il percorso a partire dalla posizione attuale, recupera le eventuali restrizioni geografiche attive nella zona, e genera il tracciato tramite un servizio di mappe esterno per poi mostrarlo all'utente.** |
| **Attori principali** | **Utente** |
| **Precondizioni** | **1.    L'utente ha effettuato l'accesso del 2\.  Le coordinate della posizione attuale dell'utente sono presenti nel sistema.** |
| **Flusso principale** | **Il caso d'uso inizia quando l'utente inserisce l'indirizzo di destinazione nel sistema. Il sistema elabora le restrizioni geografiche in base alle coordinate di partenza e alla destinazione. Il sistema calcola il tracciato del percorso che richiede meno tempo rispettando le restrizioni geografiche Il sistema mostra il tracciato del percorso all'utente** |
| **Flussi alternativi** | **\-** |
| **Postcondizioni** |  **Il percorso che richiede meno tempo è stato calcolato e i dati del tracciato sono stati restituiti dal sistema.** |
| **Include** | **\-** |
| **Estende** | **\-** |
| **Esteso dal caso d'uso** | **\-** |
| **Specializza il caso d'uso** | **\-** |
| **Generalizza il caso d'uso** | **\-** |
| **Requisiti** | **Integrazione con un Servizio Mappa esterno per l'elaborazione dei percorsi e delle coordinate** |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **UT.08** |
| **Nome** | **Metodo Pagamento** |
| **ID** | **UC.UT.05** |
| **Breve descrizione** | **L’utente seleziona il metodo di pagamento che desidera utilizzare. Il sistema verifica se ne ha già salvato almeno uno e gli fa scegliere fra quelli, altrimenti salva un nuovo metodo di pagamento facendogli inserire e convalidando i dati del metodo nel sistema.** |
| **Attori principali** | **Utente** |
| **Precondizioni** | **L'utente ha effettuato l'accesso ed ha una sessione attiva.** |
| **Flusso principale** | **Il caso d’uso inizia quando l’utente deve scegliere un metodo di pagamento. Il sistema richiede all’utente se inserire un nuovo metodo di pagamento o selezionarne uno esistente. L'utente richiede di visualizzare i metodi salvati.  Il sistema recupera i metodi di pagamento associati all'utente e ne mostra la lista all’utente  L'utente seleziona un metodo di pagamento dalla lista. Il sistema acquisisce la scelta e la associa alla sessione corrente   Il sistema notifica all'utente il successo dell'operazione.** |
| **Flussi alternativi** | ***Registrazione nuovo metodo (Opzionale): 1\. Al passaggio 3 del flusso principale, l'utente richiede di inserire un nuovo metodo di pagamento. 2\. Il sistema mostra il form di inserimento. 3\. L'utente inserisce i dati della carta (numero, scadenza, CVV, intestatario) e invia. 4\. Il sistema convalida la carta 5\. Il sistema controlla se la carta inserita esiste già nel sistema. 6\. Il sistema rileva che il metodo non esiste e lo crea,salvandolo nel sistema  7\. Il sistema mostra un messaggio di convalida all'utente. 8\. Il flusso riprende permettendo all'utente di selezionare uno tra i metodi della lista, incluso quello appena inserito. Metodo già esistente:* Al passaggio 7, il sistema rileva che il metodo è già presente, salta il salvataggio e restituisce direttamente esito positivo. *Non convalidato:* Al passaggio 4, la validazione della carta fallisce e il sistema mostra l'errore all'utente.**  |
| **Postcondizioni** | **Un metodo di pagamento è stato associato alla sessione corrente dell’utente.** |
| **Include** | **\-** |
| **Estende** | **\-** |
| **Esteso dal caso d'uso** | **\-** |
| **Specializza il caso d'uso** | **\-** |
| **Generalizza il caso d'uso** | **\-** |
| **Requisiti** | **Integrazione con un’interfaccia di pagamento per validare in tempo reale i dati della carta.** |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **UT.09** |
| **Nome** | **Sospensione Corsa** |
| **ID** | **UC.UT.06** |
| **Breve descrizione** | **L'utente richiede di sospendere la propria corsa attiva. Il sistema blocca il veicolo fisicamente e ne aggiorna lo stato a “sospeso", fornendo all'utente un QR code. Successivamente, l'utente scansiona il QR code per sbloccare il mezzo, riprendere la corsa e il sistema aggiorna i costi relativi al tempo di sospensione.** |
| **Attori principali** | **Utente** |
| **Precondizioni** | **1\.   La corsa è attiva 2\.   Il mezzo è in uso ** |
| **Flusso principale** | **Il caso d'uso inizia quando l'utente invia il comando per sospendere la corsa attiva. Il sistema blocca fisicamente il mezzo Il sistema imposta lo stato del mezzo su “sospeso” Il sistema fornisce il QR Code all'utente. L'utente, per riprendere la corsa, scansiona il QR code. Il sistema sblocca il mezzo Il sistema reimposta lo stato del mezzo a “in uso” Il sistema aggiorna il costo Il sistema mostra la conferma di ripresa all'utente.**  |
| **Flussi alternativi** | **Corsa non esistente: Al passaggio 2, il sistema rileva che la corsa indicata non è presente a sistema. Il sistema mostra un messaggio di errore all'utente, impedendo la sospensione.**  |
| **Postcondizioni** | **\- Il costo della corsa include anche il costo della sospensione** |
| **Include** | **\-** |
| **Estende** | **UC.UT.03 (Gestione Corsa)** |
| **Esteso dal caso d'uso** | **\-** |
| **Specializza il caso d'uso** | **\-** |
| **Generalizza il caso d'uso** | **\-** |
| **Requisiti** | **Integrazione sensore per lettura QR Code sul mezzo, Interfaccia IoT per blocco/sblocco Mezzo remoto, tariffa differenziata per la sospensione.** |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **\-** |
| **Nome** | **Termina Corsa e Pagamento** |
| **ID** | **UC.UT.07** |
| **Breve descrizione** |  **L'utente richiede la terminazione della corsa. Il sistema verifica se il veicolo si trova in un'area consentita, calcola il costo, effettua il pagamento, invia il comando di blocco al veicolo e lo rende nuovamente disponibile.** |
| **Attori principali** | **Utente** |
| **Precondizioni** | **1.    la corsa deve essere attiva 2\.   il mezzo deve essere in uso ** |
| **Flusso principale** |  **Il caso d'uso inizia quando l'utente richiede la terminazione della corsa. Il sistema verifica che il veicolo si trovi in un'area consentita. Il sistema calcola il costo finale della corsa. Il sistema elabora la transazione Il sistema blocca fisicamente il mezzo Il sistema imposta lo stato del mezzo su "disponibile"  Il sistema mostra il messaggio di fine corsa all'utente.** |
| **Flussi alternativi** | ***Corsa non trovata:* Il sistema rileva che la corsa indicata non è presente o attiva a sistema. Il sistema mostra un messaggio di errore all'utente. *Area non consentita:* Il sistema rileva che il veicolo si trova in una zona in cui non è consentito terminare la corsa. Il sistema mostra un messaggio di errore all'utente, impedendo la terminazione della corsa. *Pagamento non riuscito:* Il pagamento fallisce Il sistema mostra l'errore all'utente e richiede l'inserimento di un metodo di pagamento valido.** |
| **Postcondizioni** | **1\.   la transazione di fine corsa è stato effettuata 2\.   il mezzo è disponibile e fisicamente bloccato 3\.  la corsa risulta terminata**  |
| **Include** | **\-** |
| **Estende** | **\-** |
| **Esteso dal caso d'uso** | **\-** |
| **Specializza il caso d'uso** | **\-** |
| **Generalizza il caso d'uso** | **\-** |
| **Requisiti** | **Integrazione con un'interfaccia di pagamento, Tracciamento e verifica geospaziale GPS, Interfaccia IoT per blocco mezzo remoto.** |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **\-** |
| **Nome** | **Registrazione Utente** |
| **ID** | **UC.UT.10** |
| **Breve descrizione** | **L'utente non registrato inserisce i propri dati anagrafici e le credenziali per creare un nuovo profilo. Il sistema verifica la validità dei dati e l'assenza di duplicati, creando il nuovo account utente.** |
| **Attori principali** | **Utente (Non registrato)** |
| **Precondizioni** | **L'utente non dispone di un account nel sistema.** |
| **Flusso principale** | **1\. Il caso d'uso inizia quando un utente non registrato richiede di registrarsi nel sistema. 2\. Il sistema richiede l'inserimento dei dati necessari (dati anagrafici, email, password). 3\. L'attore inserisce i dati e invia la richiesta di registrazione. 4\. Il sistema verifica che i dati siano nel formato corretto e che l'email non sia già in uso. 5\. Il sistema crea il nuovo account e salva le credenziali in modo sicuro. 6\. Il sistema conferma all'utente l'avvenuta registrazione.** |
| **Flussi alternativi** | ***Formattazione sbagliata:* 1\. Al passaggio 4, il sistema rileva che alcuni campi obbligatori sono vuoti o non rispettano i formati richiesti. 2\. Il sistema mostra un messaggio di errore e richiede la correzione dei dati. *Email già in uso:* 1\. Al passaggio 4, il sistema rileva che l'indirizzo email inserito è già associato a un account esistente. 2\. Il sistema mostra un messaggio di errore e suggerisce di effettuare il login all’account indicato.** |
| **Postcondizioni** | **Il nuovo account è stato creato ed è presente nel sistema.** |
| **Include** | **\-** |
| **Estende** | **\-** |
| **Esteso dal caso d'uso** | **\-** |
| **Specializza il caso d’uso** | **\-** |
| **Generalizza il caso d’uso** | **\-** |
| **Requisiti** | **Cifratura delle password. Validazione dei dati di input.**  |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **\-** |
| **Nome** | **Logout Utente** |
| **ID** | **UC.UT.09** |
| **Breve descrizione** | **L'utente richiede di terminare la propria sessione. Il sistema chiude la sessione e rimuove i permessi di accesso alle aree riservate.** |
| **Attori principali** | **Utente** |
| **Precondizioni** | **L'utente ha effettuato l'accesso e ha una sessione attiva.** |
| **Flusso principale** | **1\. Il caso d'uso inizia quando l'utente richiede di disconnettersi dal sistema. 2\. Il sistema termina la sessione corrente dell'utente e lo disconnette.** |
| **Flussi alternativi** | **\-** |
| **Postcondizioni** | **La sessione dell'utente è terminata** |
| **Include** | **\-** |
| **Estende** | **\-** |
| **Esteso dal caso d'uso** | **\-** |
| **Specializza il caso d’uso** | **\-** |
| **Generalizza il caso d’uso** | **\-** |
| **Requisiti** | **Gestione sicura delle sessioni.** |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **OP.01, OP.04** |
| **Nome** | **Gestione Flotta** |
| **ID** | **UC.OP.01** |
| **Breve descrizione** | **L'Operatore Tecnico accede alla mappa della flotta per visualizzare la posizione e lo stato dei veicoli. Se un'azione è necessaria, l'operatore seleziona un veicolo e invia un comando remoto. Il sistema tenta di eseguire il comando: se il veicolo è online, viene bloccato e lo stato aggiornato; se la connessione è persa, viene creata una segnalazione e l'operatore viene notificato.** |
| **Attori principali** | **Operatore Tecnico** |
| **Precondizioni** | **L'Operatore Tecnico ha effettuato l'accesso e ha una sessione attiva.** |
| **Flusso principale** | **Il caso d'uso inizia quando l'Operatore Tecnico accede alla mappa della flotta. Il sistema recupera e mostra la lista dei veicoli e il loro stato. L'operatore seleziona un veicolo e invia un comando remoto di blocco. Il sistema blocca il mezzo Il sistema aggiorna lo stato del mezzo Il sistema conferma all'operatore la riuscita dell'operazione.** |
| **Flussi alternativi** | ***Connessione persa:* 1. Al passaggio 4, se il sistema non riesce a raggiungere il veicolo, crea una segnalazione di guasto  2\. Il sistema mostra un alert all'operatore. ** |
| **Postcondizioni** | **Il veicolo risulta in stato "bloccato" e quindi non è più prenotabile** |
| **Include** | **\-** |
| **Estende** | **\-** |
| **Esteso dal caso d'uso** | **\-** |
| **Specializza il caso d'uso** | **\-** |
| **Generalizza il caso d'uso** | **\-** |
| **Requisiti** | **Connettività IoT per il comando remoto dei veicoli. Sistema di segnalazione per la gestione dei veicoli non raggiungibili. ** |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **OP.02, OP.03** |
| **Nome** | **Moderazione Utenti** |
| **ID** | **UC.OP.02** |
| **Breve descrizione** | **L'Operatore Servizio Clienti cerca un utente tramite il suo identificativo. Il sistema recupera i dati e il report associato all'utente e li mostra all'operatore. L'operatore può quindi aggiornare il report e applicare un'azione correttiva (sospensione/disattivazione dell’account) che verrà poi notificata all’utente** |
| **Attori principali** | **Operatore Servizio Clienti** |
| **Precondizioni** | **\- L'Operatore Servizio Clienti ha effettuato l'accesso.** |
| **Flusso principale** | **  Il caso d'uso inizia quando l'Operatore Servizio Clienti cerca un utente tramite il suo identificativo. Il sistema recupera e mostra i dati e il report associato all'utente. L'operatore aggiorna il report e applica un'azione correttiva (sospensione/disattivazione account). Il sistema salva l'azione e aggiorna lo stato dell'utente  Il sistema invia una notifica dell’azione all’utente e lo disconnette da tutte le sue sessioni aperte. Il sistema conferma l'esito positivo all'operatore.**  |
| **Flussi alternativi** | ***Utente Non Trovato:* Al passaggio 2 del flusso principale, il sistema non trova alcun utente corrispondente all'identificativo fornito. Il sistema mostra un messaggio di errore all'operatore indicando che l'utente non è stato trovato, e l'operazione si interrompe.** |
| **Postcondizioni** | **L’azione correttiva è stata applicata. Il report dell'utente è stato aggiornato.  È stata inviata all’utente una notifica sull'azione intrapresa** |
| **Include** | **\-** |
| **Estende** | **\-** |
| **Esteso dal caso d'uso** | **\-** |
| **Specializza il caso d'uso** | **\-** |
| **Generalizza il caso d'uso** | **\-** |
| **Requisiti** | **Sistema di gestione utenti con report consultabili e modificabili. Meccanismo di collegamento con l'interfaccia dell'utente per comunicare le azioni correttive ed applicarle.** |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **OP.05** |
| **Nome** | **Amministrazione Prenotazioni** |
| **ID** | **UC.OP.03** |
| **Breve descrizione** | **L'Operatore Servizio Clienti accede alla lista delle prenotazioni valide, ne seleziona una e richiede l’annullamento. La prenotazione viene così annullata, il mezzo torna disponibile e l'operatore riceve una notifica di conferma.** |
| **Attori principali** | **Operatore Servizio Clienti** |
| **Precondizioni** | **\- L'Operatore Servizio Clienti ha effettuato l'accesso ed ha una sessione attiva.** |
| **Flusso principale** |  **Il caso d'uso inizia quando l'operatore richiede la lista delle prenotazioni attive. Il sistema recupera e mostra le prenotazioni valide. L'operatore seleziona una prenotazione e ne chiede l'annullamento. Il sistema aggiorna lo stato della prenotazione ad "annullata" e rende il mezzo nuovamente "disponibile". Il sistema notifica l'operatore dell'avvenuto annullamento.** |
| **Flussi alternativi** | **Lista Prenotazioni Vuota: Al passaggio 2 del flusso principale, il sistema recupera una lista vuota. Il sistema notifica l’Operatore Servizio Clienti dell’assenza di prenotazioni, mostrando un errore.** |
| **Postcondizioni** | **1\. La prenotazione risulta “annullata"  2\. Il mezzo risulta "disponibile".** |
| **Include** | **\-** |
| **Estende** | **\-** |
| **Esteso dal caso d'uso** | **\-** |
| **Specializza il caso d'uso** | **\-** |
| **Generalizza il caso d'uso** | **\-** |
| **Requisiti** | **\-** |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **\-** |
| **Nome** | **Logout Operatore Tecnico** |
| **ID** | **UC.OP.04** |
| **Breve descrizione** | **L'Operatore Tecnico richiede di terminare la propria sessione. Il sistema chiude la sessione e rimuove i permessi di accesso alle aree riservate.** |
| **Attori principali** | **Operatore Tecnico** |
| **Precondizioni** | **L'Operatore Tecnico ha effettuato l'accesso e ha una sessione attiva.** |
| **Flusso principale** | **1\. Il caso d'uso inizia quando l'Operatore Tecnico richiede di disconnettersi dal sistema. 2\. Il sistema termina la sessione corrente dell'Operatore Tecnico e lo disconnette.** |
| **Flussi alternativi** | **\-** |
| **Postcondizioni** | **La sessione dell'Operatore Tecnico è terminata.** |
| **Include** | **\-** |
| **Estende** | **\-** |
| **Esteso dal caso d'uso** | **\-** |
| **Specializza il caso d’uso** | **\-** |
| **Generalizza il caso d’uso** | **\-** |
| **Requisiti** | **Gestione sicura delle sessioni.** |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **\-** |
| **Nome** | **Logout Operatore SC** |
| **ID** | **UC.OP.05** |
| **Breve descrizione** | **L’Operatore Servizio Clienti richiede di terminare la propria sessione. Il sistema chiude la sessione e rimuove i permessi di accesso alle aree riservate.** |
| **Attori principali** | **Operatore Servizio Clienti** |
| **Precondizioni** | **L'Operatore Servizio Clienti ha effettuato l'accesso e ha una sessione attiva.** |
| **Flusso principale** | **1\. Il caso d'uso inizia quando l'Operatore Servizio Clienti richiede di disconnettersi dal sistema. 2\. Il sistema termina la sessione corrente dell'Operatore Servizio Clienti e lo disconnette.** |
| **Flussi alternativi** | **\-** |
| **Postcondizioni** | **La sessione dell'Operatore Servizio Clienti è terminata** |
| **Include** | **\-** |
| **Estende** | **\-** |
| **Esteso dal caso d'uso** | **\-** |
| **Specializza il caso d’uso** | **\-** |
| **Generalizza il caso d’uso** | **\-** |
| **Requisiti** | **Gestione sicura delle sessioni.** |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **AP.01, AP.03** |
| **Nome** | **Monitoraggio Statistiche e Analisi Tratte** |
| **ID** | **UC.AP.01** |
| **Breve descrizione** | **La Pubblica Amministrazione seleziona un intervallo temporale di interesse. Il sistema recupera le corse effettuate nel periodo indicato e i dati sull’attraversamento delle tratte, genera un file di statistiche aggregate e lo presenta all'utente con la possibilità di scaricarlo.** |
| **Attori principali** | **Amministrazione Pubblica** |
| **Precondizioni** | **\- L’Amministrazione Pubblica ha effettuato l'accesso ed ha una sessione attiva.** |
| **Flusso principale** | **Il caso d'uso inizia quando l'Amministrazione Pubblica seleziona un intervallo temporale di interesse. Il sistema recupera tutte le corse effettuate nel periodo indicato. Per ogni corsa dell’intervallo, il sistema recupera le zone attraversate dal mezzo utilizzato. Il sistema elabora i dati e genera il file di statistiche aggregate. Il sistema mostra le statistiche e offre la possibilità di scaricare il report.** |
| **Flussi alternativi** | ***Dati non presenti nel periodo selezionato:* Al passaggio 2, il sistema rileva che non ci sono corse effettuate per il periodo indicato. Il sistema mostra un messaggio di errore all'Amministrazione Pubblica indicando l'assenza di dati.** |
| **Postcondizioni** | **Le statistiche aggregate sono state generate e il file di report è reso disponibile dal sistema.**  |
| **Include** | **\-** |
| **Estende** | **\-** |
| **Esteso dal caso d'uso** | **\-** |
| **Specializza il caso d'uso** | **\-** |
| **Generalizza il caso d'uso** | **\-** |
| **Requisiti** | **Disponibilità di dati storici delle corse nel database. Capacità di generazione di file di statistiche aggregate esportabili.** |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **AP.02** |
| **Nome** | **Analisi Stato Flotta** |
| **ID** | **UC.AP.02** |
| **Breve descrizione** | **La Pubblica Amministrazione richiede lo stato della flotta. Il sistema recupera i mezzi associati alla flotta, ne analizza le condizioni e presenta una dashboard riepilogativa. Se dei veicoli richiedono manutenzione, l'Amministrazione Pubblica può avviare un intervento che comporta la creazione di segnalazioni e l'aggiornamento dello stato dei mezzi interessati.** |
| **Attori principali** | **Amministrazione Pubblica** |
| **Precondizioni** | **L'Amministrazione Pubblica ha effettuato l'accesso ed ha una sessione attiva. La flotta selezionata è registrata a sistema con i relativi mezzi associati.** |
| **Flusso principale** | **Il caso d'uso inizia quando l'Amministrazione Pubblica richiede lo stato della flotta. Il sistema recupera la lista e le condizioni operative di tutti i mezzi associati. Il sistema mostra un riepilogo complessivo L'Amministrazione Pubblica avvia un intervento di manutenzione per i veicoli che lo necessitano. Il sistema crea una segnalazione per i mezzi interessati Per ciascuno dei mezzi interessati, il sistema aggiorna lo stato a "manutenzione". Il sistema mostra il riepilogo degli interventi all'utente.** |
| **Flussi alternativi** | ***I veicoli non richiedono manutenzione:* Dopo la visualizzazione del riepilogo complessivo al passaggio 3, il sistema rileva che nessun veicolo necessita di intervento. Il sistema notifica all'Amministrazione Pubblica che la flotta è completamente operativa.** |
| **Postcondizioni** | **1\.   I mezzi interessati risultano in stato "manutenzione"  2\.  Le relative segnalazioni sono state create nel sistema.** |
| **Include** | **\-** |
| **Estende** | **\-** |
| **Esteso dal caso d'uso** | **\-** |
| **Specializza il caso d'uso** | **\-** |
| **Generalizza il caso d'uso** | **\-** |
| **Requisiti** | **Database aggiornato con lo stato operativo di ciascun mezzo della flotta. Sistema di segnalazione per la gestione degli interventi di manutenzione.** |

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **AP.04** |
| **Nome** | **Restrizioni Geografiche** |
| **ID** | **UC.AP.03** |
| **Breve descrizione** | **L' Amministrazione Pubblica accede alla mappa per gestire le restrizioni geografiche. Il sistema mostra le zone esistenti e consente la modifica delle restrizioni. In caso di conflitto con restrizioni già presenti, l’Amministrazione Pubblica può scegliere di sovrascrivere le regole esistenti o annullare l'operazione.** |
| **Attori principali** | **Amministrazione Pubblica** |
| **Precondizioni** | **L'Amministrazione Pubblica ha effettuato l'accesso ed ha una sessione attiva.** |
| **Flusso principale** | **Il caso d'uso inizia quando l'Amministrazione Pubblica accede alla sezione per la gestione delle aree. Il sistema recupera e mostra le zone geografiche esistenti. L'Amministrazione Pubblica richiede la modifica delle restrizioni per una zona. Il sistema verifica eventuali sovrapposizioni con le restrizioni in vigore. Il sistema salva le nuove restrizioni. Il sistema mostra la situazione aggiornata e notifica il successo dell'operazione.**  |
| **Flussi alternativi** | ***Conflitto con restrizioni esistenti – Sovrascrittura confermata:* Al passaggio 4 del flusso principale, il sistema rileva un conflitto con le restrizioni esistenti. Il sistema notifica l'Amministrazione Pubblica del conflitto e chiede conferma per la sovrascrittura. L'Amministrazione Pubblica conferma la sovrascrittura. Il sistema salva le restrizioni sovrascritte e conferma il successo dell'operazione. *Conflitto con restrizioni esistenti – Annullamento:* Al passaggio 2 del flusso alternativo precedente, l'Amministrazione Pubblica rifiuta la sovrascrittura.** |
| **Postcondizioni** | **La zona geografica è stata aggiornata con le nuove regole.** |
| **Include** | **\-** |
| **Estende** |  **\-** |
| **Esteso dal caso d'uso** | **\-** |
| **Specializza il caso d'uso** | **\-** |
| **Generalizza il caso d'uso** | **\-** |
| **Requisiti** | **Sistema di gestione delle zone geografiche con supporto alla verifica dei conflitti tra restrizioni sovrapposte. Visualizzazione cartografica interattiva delle zone operative.** |

** **

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **\-** |
| **Nome** | **Logout PA** |
| **ID** | **UC.AP.04** |
| **Breve descrizione** | **L'Amministrazione Pubblica richiede di terminare la propria sessione. Il sistema chiude la sessione e rimuove i permessi di accesso alle aree riservate.** |
| **Attori principali** | **Amministrazione Pubblica** |
| **Precondizioni** | **L'Amministrazione Pubblica ha effettuato l'accesso ed ha una sessione attiva.** |
| **Flusso principale** | **1\. Il caso d'uso inizia quando l'Amministrazione Pubblica richiede di disconnettersi dal sistema. 2\. Il sistema termina la sessione corrente dell'Amministrazione Pubblica e la disconnette.** |
| **Flussi alternativi** | **\-** |
| **Postcondizioni** | **La sessione dell'Amministrazione Pubblica è terminata** |
| **Include** | **\-** |
| **Estende** | **\-** |
| **Esteso dal caso d'uso** | **\-** |
| **Specializza il caso d’uso** | **\-** |
| **Generalizza il caso d’uso** | **\-** |
| **Requisiti** | **Gestione sicura delle sessioni.** |

** **

| Campo | Descrizione |
| :---- | :---- |
| **UserStories** | **\-** |
| **Nome** | **Login** |
| **ID** | **UC.ATT.01** |
| **Breve descrizione** | **L'attore inserisce le proprie credenziali per autenticarsi. Il sistema verifica se i dati forniti sono già presenti nel sistema e, in caso di esito positivo, concede l'accesso alle funzionalità riservate in base al ruolo dell'attore (Utente, Operatore, o Amministrazione Pubblica).** |
| **Attori principali** | **Attore** |
| **Precondizioni** | **L'attore possiede un account nel sistema. Operatore Tecnico, Operatore Servizio Clienti e  PA possiedono credenziali di accesso pre-generate e fornite dall'amministrazione.**  |
| **Flusso principale** | **1\. Il caso d'uso inizia quando l'attore richiede di effettuare l'accesso al sistema. 2\. Il sistema richiede l'inserimento delle credenziali. 3\. L'attore inserisce i dati e li invia. 4\. Il sistema verifica la corrispondenza delle credenziali fornite in memoria e lo stato dell'account. 5\. Il sistema autentica l'attore, crea la sessione e sblocca le funzionalità previste per il suo ruolo specifico.** |
| **Flussi alternativi** | ***Email non valida:* 1\. Al passaggio 4, il sistema rileva che i dati inseriti non corrispondono a nessun account. 2\. Il sistema mostra un messaggio di errore e nega l'accesso. *Password non valida:* 1\. Al passaggio 4, il sistema rileva che la password per l’account della email inserita è errata. 2\. Il sistema mostra un messaggio di errore e nega l'accesso.** |
| **Postcondizioni** | **L'attore risulta autenticato Una nuova sessione è stata creata con i permessi e la pagina corrispondente al ruolo dell’attore.** |
| **Include** | **\-** |
| **Estende** | **\-** |
| **Esteso dal caso d'uso** | **\-** |
| **Specializza il caso d'uso** | **\-** |
| **Generalizza il caso d’uso** | **\-** |
| **Requisiti** | **Gestione sicura delle sessioni. Meccanismo di cifratura delle password. Gestione dei ruoli (RBAC).** |

3. ### **Altro** {#altro}

TABELLA RIEPILOGATIVA USER STORIES E BACKLOG

| Sprint | ID (Caso D’uso) | Attore | Tipo | Elemento | Priorità (1-50) |
| :---- | :---: | :---: | :---: | ----- | :---: |
| 1 | UT.01 (UC.UT.01) | Utente | Funzionale | Visualizzare i mezzi disponibili in un raggio prestabilito a partire dalla posizione scelta, così da poter iniziare una corsa. | 50 |
| 3 | UC.UT.10 | Utente (non registrato) | Funzionale | L'utente non registrato inserisce i propri dati anagrafici e le credenziali per creare un nuovo profilo. Il sistema verifica la validità dei dati e l'assenza di duplicati, creando il nuovo account utente. | 50 |
| 3 | UC.ATT.01 | Attore | Funzionale | L'attore inserisce le proprie credenziali per autenticarsi. Il sistema verifica se i dati forniti sono già presenti nel sistema e, in caso di esito positivo, concede l'accesso alle funzionalità riservate in base al ruolo dell'attore (Utente, Operatore, o Amministrazione Pubblica). | 50 |
| 1 | OP.02 (UC.OP.02) | Operatore Servizio Clienti | Funzionale | Conoscere l’anagrafica dell’utente, così da poter risalire ad eventuali furti ed incidenti | 45 |
| 2 | UC.UT.07 | Utente | Funzionale | L'utente richiede la terminazione della corsa. Il sistema verifica se il veicolo si trova in un'area consentita, calcola il costo, effettua il pagamento, invia il comando di blocco al veicolo e lo rende nuovamente disponibile. | 45 |
| 1 | AP.02 (UC.AP.02) | Amm. Pubblica | Funzionale | Analizzare le condizioni fisiche dei mezzi, così da poter intervenire in caso di necessità. | 40 |
| 1 | UT.03 (UC.UT.03) | Utente | Funzionale | Visualizzare l’importo della corsa in tempo reale, così da sapere quanto sto pagando. | 40 |
| 1 | UT.05 (UC.UT.01) | Utente | Funzionale | Conoscere l’orario stimato per la disponibilità di un mezzo, così da valutare l’utilizzo del servizio. | 40 |
| 1 | UT.07 (UC.UT.03) | Utente | Non Funzionale | Dover utilizzare un metodo d’autenticazione per sbloccare il mezzo, così da evitare accessi non autorizzati. | 40 |
| 1 | UT.04 (UC.UT.01) | Utente | Funzionale | Consultare le specifiche tecniche del mezzo disponibile, così da effettuare una scelta. | 35 |
| 1 | OP.03 (UC.OP.02) | Operatore Servizio Clienti | Funzionale | Poter moderare l'account di un utente, così da prevenire futuri utilizzi in caso di violazione dei termini di servizio | 35 |
| 1 | UT.08 (UC.UT.05) | Utente | Funzionale | Poter inserire un metodo di pagamento nel mio account, così da permettere l'addebito al termine di ogni utilizzo. | 35 |
| 2 | UT.02 (UC.UT.02) | Utente | Funzionale | Prenotare dei mezzi se disponibili, così da trovarli a disposizione quando arrivo. | 35 |
| 2 | UT.09 (UC.UT.06) | Utente | Funzionale | Poter effettuare delle soste senza perdere il possesso del mezzo, così da poter sospendere la mia corsa | 35 |
| 2 | AP.04 (UC.AP.03) | Amm. Pubblica | Non Funzionale | Impedire che i mezzi vengano lasciati in aree non designate al termine della corsa, così da evitare posizionamenti illeciti | 30 |
| 2 | UT.06 (UC.UT.04) | Utente | Funzionale | Visualizzare il percorso che richiede meno tempo, così da minimizzare la durata del viaggio. | 30 |
| 2 | UT.10 (UC.UT.04) | Utente | Funzionale | Visualizzare le aree non accessibili al mezzo, così da pianificare il percorso correttamente. | 30 |
| 2 | OP.05 (UC.OP.03) | Operatore Servizio Clienti | Funzionale | Amministrare le prenotazioni sui mezzi, così da modernarne l'utilizzo. | 25 |
| 2 | AP.01 (UC.AP.01) | Amm. Pubblica | Funzionale | Accedere alle statistiche di utilizzo del sistema, così da supportare decisioni strategiche. | 20 |
| 2 | AP.03 (UC.AP.01) | Amm. Pubblica | Funzionale | Poter conoscere le tratte più utilizzate, così da poter pianificare la manutenzione in specifiche aree. | 15 |
| 2 | OP.01 (UC.OP.01) | Operatore Tecnico | Funzionale | Visualizzare la distribuzione dei mezzi, così da ottimizzare il posizionamento della flotta. | 15 |
| 2 | OP.04 (UC.OP.01) | Operatore Tecnico | Non Funzionale | Forzare il blocco da remoto di un mezzo se fuori dalle zone consentite, così da prevenire violazioni dei termini di servizio | 10 |
| 3 | UC.OP.04 | Operatore Tecnico | Funzionale | L'Operatore Tecnico richiede di terminare la propria sessione. Il sistema chiude la sessione e rimuove i permessi di accesso alle aree riservate. | 10 |
| 3 | UC.AP.04 | Amm. Pubblica | Funzionale | L'Amministrazione Pubblica richiede di terminare la propria sessione. Il sistema chiude la sessione e rimuove i permessi di accesso alle aree riservate. | 10 |
| 3 | UC.UT.09 | Utente | Funzionale | L'utente richiede di terminare la propria sessione. Il sistema chiude la sessione e rimuove i permessi di accesso alle aree riservate. | 10 |
| 3 | UC.OP.05 | Operatore Servizio Clienti | Funzionale | L’Operatore Servizio Clienti richiede di terminare la propria sessione. Il sistema chiude la sessione e rimuove i permessi di accesso alle aree riservate. | 10 |

3. ## **System Architecture** {#system-architecture}

   1. ### **Diagramma delle Componenti** {#diagramma-delle-componenti}

![][image3]

2. ### **Specifica delle componenti** {#specifica-delle-componenti}

Per la progettazione e lo sviluppo del sistema, è stato adottato il pattern architetturale Model-View-Controller (MVC) nella sua variante Moderna (Web-oriented / con Controller Intermediario).

A differenza dell'approccio MVC, la configurazione scelta centralizza l'intero flusso di controllo e di scambio dati all'interno dei componenti Controller. Questa decisione è stata guidata dalla necessità di gestire un ecosistema eterogeneo composto da molteplici client (AppUtente, AppOperatoreSC, AppPA, AppOperatoreTecnico e Autenticazione) e flussi di dati asincroni provenienti dai dispositivi IoT (i mezzi della flotta). 

Il sistema implementa la separazione delle responsabilità secondo la seguente logica, ampiamente documentata nel Diagramma delle Componenti e nei Diagrammi di Sequenza:

1. View: I componenti AppUtente, AppOperatoreSC, AppPA, AppOperatoreTecnico e Autenticazione rappresentano lo strato di presentazione. Esse sono completamente disaccoppiate dal dominio dei dati. Le View non interrogano mai direttamente lo stato del sistema né rimangono in ascolto di eventi generati dal Model; ogni interazione avviene inviando richieste esplicite ai rispettivi Controller e attendendo da essi i dati per l'aggiornamento.   
2. Controller: I componenti di controllo fungono da intermediari attivi del sistema. Il Controller intercetta gli input della View, valida le richieste, interroga o aggiorna il Model e, una volta elaborata la risposta, si occupa di indirizzare e formattare i dati per la View.   
3. Model: Rappresentato dalle entità core del sistema (Mezzo, Utente, Corsa, Flotta ecc..), il Model ricopre un ruolo prettamente passivo. Esso espone i metodi per l'accesso e la modifica dello stato (getter/setter) richiesti dai Controller, ma è totalmente privo di logiche di notifica verso l'esterno.

status: SUCCESS_WITH_INFERENCE
extractor_version: "3.1-SequenceToUseCase"
Use Case: Ottimizzazione Percorso

```text
UserStories: [INFERRED] Come utente voglio inserire una destinazione e visualizzare il percorso calcolato sulla mappa, in modo da poter avviare una corsa verso la mia meta.

Nome: Ottimizzazione Percorso

ID: UC.UT.04

Breve descrizione: L'utente inserisce la destinazione desiderata nell'applicazione. Il sistema elabora il percorso a partire dalla posizione attuale, recupera le eventuali restrizioni geografiche attive nella zona, e genera il tracciato tramite un servizio di mappe esterno per poi mostrarlo all'utente.

Attori principali: Utente, ServizioMappa

Precondizioni:
    - [INFERRED] L'utente ha effettuato l'accesso all'applicazione.
    - [INFERRED] Il sistema ha già acquisito le coordinate della posizione attuale dell'utente.
    
Flusso principale:
    1. [INFERRED] Il sistema renderizza la vista contenente la mappa e il campo di ricerca destinazione.
    2. L'utente inserisce l'indirizzo di arrivo e conferma (`inserisciDestinazione`).
    3. Il sistema richiede il calcolo del percorso al gestore, fornendo le coordinate dell'utente e la stringa della destinazione (`richiediCalcoloPercorso`).
    4. Il gestore richiede i dati relativi alle restrizioni della zona in base alle coordinate dell'utente (`getRestrizioniZona`).
    5. Il sistema restituisce la lista delle zone geografiche e relative regole applicabili (`lista<ZonaGeografica>`).
    6. Il gestore invia le coordinate di partenza, di arrivo e le restrizioni al servizio di mappa esterno per l'elaborazione del tracciato (`getRouteData`).
    7. Il servizio di mappa elabora e restituisce i dati completi del percorso (`datiPercorso: RoutePayload`).
    8. Il gestore conferma l'avvenuto calcolo del percorso all'applicazione (`percorso calcolato`).
    9. Il sistema renderizza a schermo il tracciato del percorso per l'utente (`Visualizza tracciato`).
    
Flussi alternativi:
    Nessuno
        
Postcondizioni:
    - [INFERRED] Il tracciato del percorso è visibile sulla mappa e il sistema è in attesa dell'azione di conferma o annullamento da parte dell'utente.
    
Include: Nessuno

Estende: Nessuno

Esteso dal caso d'uso: Nessuno

Specializza il caso d'uso: Nessuno

Generalizza il caso d'uso: Nessuno

Requisiti: [INFERRED] Necessaria integrazione affidabile con un Servizio Mappa esterno per l'elaborazione delle rotte.
```

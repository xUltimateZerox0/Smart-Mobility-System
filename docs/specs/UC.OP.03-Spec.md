status: SUCCESS_WITH_INFERENCE
extractor_version: "3.1-SequenceToUseCase"
Use Case: Annullamento Prenotazione da Operatore

```text
UserStories: [INFERRED] Come operatore del servizio clienti voglio poter visualizzare le prenotazioni attive e annullarne una selezionata, affinché il mezzo venga reso disponibile e l'utente venga notificato dell'annullamento.

Nome: Annullamento Prenotazione da Operatore

ID: UC.OP.03

Breve descrizione: L'operatore del servizio clienti accede alla lista delle prenotazioni valide, ne seleziona una e richiede l'annullamento. Il sistema tenta di inviare un comando di sblocco al mezzo tramite connessione IoT. In caso di successo, la prenotazione viene annullata, il mezzo torna disponibile e l'operatore riceve una notifica di conferma. Se la connessione IoT fallisce, il sistema crea una segnalazione e notifica l'errore.

Attori principali: Operatore Servizio Clienti

Precondizioni:
    - [INFERRED] L'operatore ha effettuato l'accesso all'applicazione AppOperatore con credenziali autorizzate.
    - [INFERRED] Esiste almeno una prenotazione in stato "valida" a sistema.

Flusso principale:
    1. L'operatore richiede la lista delle prenotazioni attive (`richiediListaPrenotazioni()`).
    2. L'applicazione inoltra la richiesta al gestore delle prenotazioni (`richiediLista()`).
    3. Il gestore interroga l'entità Prenotazione per recuperare tutte le prenotazioni valide (`Prenotazione.getPrenotazioneByStato(valida)`).
    4. L'entità restituisce la lista delle prenotazioni (`lista<Prenotazione>`).
    5. Il gestore restituisce la lista all'applicazione (`listaPrenotazioni`).
    6. L'applicazione renderizza le prenotazioni nell'interfaccia (`mostraPrenotazioni()`).
    7. Il sistema visualizza la lista delle prenotazioni all'operatore (`visualizza prenotazioni`).
    8. L'operatore seleziona una prenotazione dalla lista (`selezionaPrenotazione(idPrenotazione)`).
    9. L'applicazione invia il comando di annullamento al gestore (`annullaPrenotazione(idPrenotazione)`).
    10. Il gestore invia il comando di sblocco fisico al mezzo tramite IoT (`inviaComandoSblocco(idMezzo)`).
    11. [Condizione: Connessione IoT avvenuta] Il mezzo conferma lo sblocco (`true`).
    12. Il gestore aggiorna lo stato della prenotazione a "annullata" (`setStato(annullata)`).
    13. La prenotazione conferma l'aggiornamento (`void`).
    14. Il gestore aggiorna lo stato del mezzo a "disponibile" (`setStato(disponibile)`).
    15. Il mezzo conferma l'aggiornamento dello stato (`void`).
    16. Il gestore restituisce l'esito positivo all'applicazione (`true`).
    17. L'applicazione renderizza il messaggio di conferma (`mostraSuccesso(messaggio)`).
    18. Il sistema notifica l'operatore dell'avvenuto annullamento (`notifica annullamento`).

Flussi alternativi:
    Connessione IoT fallita:
        1. Al passaggio 10 del flusso principale, la connessione IoT verso il mezzo non va a buon fine.
        2. Il gestore restituisce un esito negativo all'applicazione (`false`).
        3. Il gestore gestisce internamente il timeout (`gestisciTimeout()`).
        4. Il gestore crea una segnalazione per il mezzo con i dettagli dell'errore (`creaSegnalazione(idMezzo, data, ora, statoS, note)`).
        5. L'applicazione renderizza il messaggio di errore (`mostraErrore(messaggio)`).
        6. Il sistema notifica l'operatore dell'errore di comunicazione (`visualizza errore`).

Postcondizioni:
    - [INFERRED] Se la connessione IoT ha successo, la prenotazione risulta in stato "annullata" e il mezzo risulta in stato "disponibile".
    - [INFERRED] Se la connessione IoT fallisce, nessuna modifica di stato è applicata alla prenotazione o al mezzo, e una segnalazione viene creata a sistema per intervento tecnico.

Include: Nessuno

Estende: Nessuno

Esteso dal caso d'uso: Nessuno

Specializza il caso d'uso: Nessuno

Generalizza il caso d'uso: Nessuno

Requisiti: [INFERRED] Connettività IoT affidabile per il comando remoto di sblocco dei mezzi. Sistema di segnalazione per la gestione dei fallimenti di comunicazione.
```

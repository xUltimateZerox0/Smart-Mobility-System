status: SUCCESS_WITH_INFERENCE
extractor_version: "3.1-SequenceToUseCase"
Use Case: Prenotazione Mezzo

```text
UserStories: [INFERRED] Come utente voglio selezionare un mezzo disponibile sulla mappa e prenotarlo, in modo da assicurarmi la disponibilità del veicolo al mio arrivo.

Nome: Prenotazione Mezzo

ID: UC.UT.02

Breve descrizione: L'utente seleziona un mezzo disponibile dall'applicazione e ne richiede la prenotazione. Il sistema verifica la disponibilità, aggiorna lo stato del mezzo a "prenotato", crea l'entità Prenotazione, sblocca il mezzo fisicamente e notifica l'avvenuta prenotazione. È previsto un meccanismo automatico di annullamento se l'utente non raggiunge il mezzo entro 15 minuti dall'orario prenotato.

Attori principali: Utente

Precondizioni:
    - [INFERRED] L'utente ha effettuato l'accesso all'applicazione.
    - [INFERRED] L'utente ha visualizzato la mappa con i mezzi disponibili.

Flusso principale:
    1. [INFERRED] Il sistema renderizza la vista mappa con i mezzi disponibili.
    2. L'utente seleziona un mezzo dalla lista o dalla mappa (`selezionaMezzo`).
    3. L'applicazione invia la richiesta di prenotazione al gestore (`inviaRichiestaPrenotazione`).
    4. Il gestore aggiorna lo stato del mezzo impostandolo a "prenotato" (`setStato("prenotato")`).
    5. Il mezzo conferma l'aggiornamento dello stato (`void`).
    6. Il gestore invia il comando di sblocco fisico del mezzo (`sbloccoMezzoFisico`).
    7. Il mezzo conferma l'avvenuto sblocco (`true`).
    8. Il gestore crea e registra l'entità Prenotazione con i dati del mezzo, dell'utente e l'orario di inizio (`creaPrenotazione`).
    9. Il gestore conferma l'esito positivo dell'operazione all'applicazione (`true`).
    10. L'applicazione renderizza il messaggio di successo (`mostraSuccesso`).
    11. L'applicazione notifica all'utente l'avvenuta prenotazione (`Notifica prenotazione avvenuta`).

Flussi alternativi:
    Scadenza tempo prenotazione (dopo 15 minuti dall'orario prenotato):
        1. [INFERRED] Trascorsi 15 minuti dall'orario prenotato, il sistema rileva il superamento del timeout.
        2. Il gestore reimposta lo stato del mezzo a "disponibile" (`setStato("disponibile")`).
        3. Il mezzo conferma il ripristino dello stato (`void`).
        4. Il gestore invia una notifica di annullamento all'applicazione (`notificaScadenzaTempo`).
        5. L'applicazione invia la notifica di annullamento della prenotazione all'utente (`notifica Annullamento Prenotazione`).
        6. L'applicazione renderizza il messaggio di avvenuto annullamento (`mostraSuccesso`).
        7. L'utente riceve la notifica che la prenotazione è stata annullata per scadenza (`Notifica prenotazione annullata`).

Postcondizioni:
    - [INFERRED] Se la prenotazione ha esito positivo, il mezzo risulta "prenotato", l'entità Prenotazione è registrata a sistema e il veicolo è sbloccato fisicamente.
    - [INFERRED] Se il tempo di prenotazione scade, il mezzo torna allo stato "disponibile", la prenotazione è annullata e l'utente viene notificato.

Include: Nessuno

Estende: Nessuno

Esteso dal caso d'uso: Nessuno

Specializza il caso d'uso: Nessuno

Generalizza il caso d'uso: Nessuno

Requisiti: [INFERRED] Meccanismo di timeout automatico a 15 minuti per l'annullamento delle prenotazioni scadute. Capacità di sblocco fisico remoto del mezzo.
```

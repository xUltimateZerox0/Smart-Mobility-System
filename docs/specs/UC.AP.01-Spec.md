status: SUCCESS_WITH_INFERENCE
extractor_version: "3.1-SequenceToUseCase"
Use Case: Monitoraggio Statistiche e Analisi Tratte

```text
UserStories: [INFERRED] Come Pubblica Amministrazione (PA) voglio consultare le statistiche delle corse effettuate in un periodo selezionato e scaricare un report, affinché possa analizzare l'utilizzo del servizio di mobilità e pianificare interventi sul territorio.

Nome: Monitoraggio Statistiche e Analisi Tratte

ID: UC.AP.01

Breve descrizione: L'attore PA (Pubblica Amministrazione) seleziona un intervallo temporale di interesse. Il sistema recupera le corse effettuate nel periodo indicato, genera un file di statistiche aggregate e lo presenta all'utente con la possibilità di scaricarlo.

Attori principali: PA (Pubblica Amministrazione)

Precondizioni:
    - [INFERRED] L'utente PA ha effettuato l'accesso all'applicazione AppPA con credenziali autorizzate.
    - [INFERRED] Esistono dati storici delle corse nel database.

Flusso principale:
    1. [INFERRED] Il sistema renderizza la vista di selezione dell'intervallo temporale per l'analisi.
    2. L'attore PA seleziona l'intervallo temporale di interesse specificando data di inizio e data di fine (`selezionaIntervallo(dataInizio, dataFine)`).
    3. L'applicazione inoltra la richiesta di analisi tratte al gestore delle statistiche (`analisiTratte(dataInizio, dataFine)`).
    4. Il gestore interroga l'entità Corsa per recuperare tutte le corse nel periodo indicato (`Corsa.getCorseByPeriodo(dataInizio, dataFine)`).
    5. L'entità Corsa restituisce la lista delle corse trovate (`lista<Corsa>`).
    6. [Condizione: Dati presenti] Il gestore elabora la lista e genera il file di statistiche aggregate (`generaFileStatistiche(lista<Corsa>)`).
    7. Il gestore restituisce le statistiche elaborate all'applicazione (`statistiche`).
    8. L'applicazione renderizza le statistiche nell'interfaccia (`mostraStatistiche(statistiche)`).
    9. Il sistema visualizza le statistiche e offre la possibilità di scaricare il file all'attore PA (`visualizza statistiche e scarica file`).

Flussi alternativi:
    Dati non presenti nel periodo selezionato:
        1. Al passaggio 5 del flusso principale, l'entità Corsa non restituisce risultati per il periodo indicato (`null`).
        2. L'applicazione renderizza un messaggio di errore (`mostraErrore(messaggio)`).
        3. Il sistema visualizza l'errore all'attore PA (`visualizza errore`).

Postcondizioni:
    - [INFERRED] Se i dati sono presenti, l'attore PA ha visualizzato le statistiche aggregate e ha la possibilità di scaricare il file di report.
    - [INFERRED] Se i dati non sono presenti, l'attore PA è stato informato dell'assenza di dati per il periodo selezionato.

Include: Nessuno

Estende: Nessuno

Esteso dal caso d'uso: Nessuno

Specializza il caso d'uso: Nessuno

Generalizza il caso d'uso: Nessuno

Requisiti: [INFERRED] Disponibilità di dati storici delle corse nel database. Capacità di generazione di file di statistiche aggregate esportabili.
```

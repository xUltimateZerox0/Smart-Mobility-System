UserStories
AP.01, AP.03
Nome 
Monitoraggio Statistiche e Analisi Tratte
ID 
UC.AP.01
Breve descrizione
L'attore PA (Pubblica Amministrazione) seleziona un intervallo temporale di interesse. Il sistema recupera le corse effettuate nel periodo indicato, genera un file di statistiche aggregate e lo presenta all'utente con la possibilità di scaricarlo.
Attori principali
Amministrazione Pubblica
Precondizioni
 L'utente PA ha effettuato l'accesso 
credenziali sono state autorizzate.
Flusso principale
1.  Il sistema renderizza la vista di selezione dell'intervallo temporale per l'analisi.
    2. L'attore PA seleziona l'intervallo temporale di interesse specificando data di inizio e data di fine 
    3. L'interfaccia inoltra la richiesta di analisi tratte al gestore delle statistiche 
    4. Il gestore interroga l'entità Corsa per recuperare tutte le corse nel periodo indicato 
    5. L'entità Corsa restituisce la lista delle corse trovate .
    6. Il gestore elabora la lista e genera il file di statistiche aggregate.
    7. Il gestore restituisce le statistiche elaborate all’interfaccia.
    8. L'interfaccia renderizza le statistiche dell'interfaccia. 
    9. Il sistema visualizza le statistiche e offre la possibilità di scaricare il file all'attore PA.
Flussi alternativi
Dati non presenti nel periodo selezionato:
        1. Al passaggio 5 del flusso principale, l'entità Corsa non restituisce risultati per il periodo indicato 
        2. L'interfaccia renderizza un messaggio di errore 
        3. Il sistema visualizza l'errore all'attore PA .
Postcondizioni
 i dati sono presenti, l'attore PA ha visualizzato le statistiche aggregate e ha la possibilità di scaricare il file di report.
  i dati non sono presenti, l'attore PA è stato informato dell'assenza di dati per il periodo selezionato.
Include
-
Estende
-
Esteso dal caso d'uso
-
Specializza il caso d'uso
-
Generalizza il caso d'uso
-
Requisiti
Disponibilità di dati storici delle corse nel database. Capacità di generazione di file di statistiche aggregate esportabili.
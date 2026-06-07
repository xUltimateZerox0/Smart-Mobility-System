UserStories
AP.02

Nome
Analisi Stato Flotta
ID
UC.AP.02
Breve descrizione
L'attore PA richiede lo stato della flotta. Il sistema recupera i mezzi associati alla flotta, ne analizza le condizioni e presenta una dashboard riepilogativa. Se dei veicoli richiedono manutenzione, l'attore può avviare un intervento che comporta la creazione di segnalazioni e l'aggiornamento dello stato dei mezzi interessati.
Attori principali
Amministrazione Pubblica
Precondizioni
L'utente PA ha effettuato l'accesso all'interfaccia AppPA con credenziali autorizzate.
   La flotta identificata da idFlotta è registrata a sistema con mezzi associati.
Flusso principale
1.  Il sistema renderizza la vista di gestione della flotta.
    2. L'attore PA richiede lo stato della flotta 
    3. L'interfaccia inoltra la richiesta al gestore della flotta per recuperare le condizioni dei mezzi 
    4. Il gestore interroga l'entità Mezzo per ottenere la lista di tutti i mezzi della flotta 
    5. L'entità Mezzo restituisce la lista dei veicoli 
    6. Il gestore restituisce la lista elaborata all'interfaccia .
    7. L'interfaccia renderizza la lista dei mezzi.
    8. Il sistema visualizza la dashboard dei mezzi all'attore PA. 
    9. L'attore PA decide di avviare un intervento di manutenzione.
    10. L'interfaccia inoltra la richiesta di manutenzione al gestore. 
    11. Il gestore esegue un'analisi interna dello stato della flotta.
    12.  Il gestore crea una segnalazione per il mezzo interessato.
    13. Il gestore aggiorna lo stato del mezzo a "manutenzione". 
    14. Il mezzo conferma l'aggiornamento dello stato.
    15. [Fine loop] Ripete per ogni mezzo da riparare.
    16. Il gestore restituisce la conferma con l'indicazione dei mezzi in manutenzione.
    17. Il gestore invia il riepilogo degli interventi all'interfaccia.
Flussi alternativi
I veicoli non richiedono manutenzione:
        1. Al passaggio 9 del flusso principale, il gestore rileva che nessun veicolo necessita di intervento.
        2. Il gestore notifica all'interfaccia che la flotta è completamente operativa .
Postcondizioni
Se è stato avviato un intervento, i mezzi interessati risultano in stato "manutenzione" e le relative segnalazioni sono state create a sistema.
 Se nessun intervento è necessario, l'attore PA ha ricevuto conferma che la flotta è operativa.
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
Database aggiornato con lo stato operativo di ciascun mezzo della flotta. Sistema di segnalazione per la gestione degli interventi di manutenzione.

UserStories
UT.02
Nome
Prenotazione Mezzo
ID
UC.UT.02
Breve descrizione
L'utente seleziona un mezzo disponibile dall'interfaccia e ne richiede la prenotazione. Il sistema verifica la disponibilità, aggiorna lo stato del mezzo a "prenotato", crea l'entità Prenotazione, sblocca il mezzo fisicamente e notifica l'avvenuta prenotazione. È previsto un meccanismo automatico di annullamento se l'utente non raggiunge il mezzo entro 15 minuti dell'orario prenotato.
Attori principali
Utente
Precondizioni
 L'utente ha effettuato l'accesso 
 L'utente ha visualizzato la mappa con i mezzi disponibili.
Flusso principale
    1. Il sistema renderizza la mappa con i mezzi disponibili.
    2. L'utente seleziona un mezzo dalla lista.
    3. L’interfaccia invia la richiesta di prenotazione al gestore. 
    4. Il gestore invia il comando di sblocco fisico del mezzo.
    5. Il mezzo IoT conferma l'avvenuto sblocco.
    6.  Il gestore aggiorna lo stato del mezzo impostandolo a "prenotato". 
    7. Il mezzo conferma l'aggiornamento dello stato.
    8. Il gestore crea e registra l'entità Prenotazione con i dati del mezzo, dell'utente e l'orario di prenotazione.
    9. Il gestore conferma l'esito positivo dell'operazione all'interfaccia. 
    10. L'interfaccia renderizza il messaggio di successo.
    11. L'interfaccia notifica all'utente l'avvenuta prenotazione. 
Flussi alternativi
Scadenza tempo prenotazione (dopo 15 minuti dall'orario prenotato):
        1.  Trascorsi 15 minuti dall'orario prenotato, il sistema rileva il superamento del timeout.
        2. Il gestore reimposta lo stato del mezzo a "disponibile". 
        3. Il mezzo conferma il ripristino dello stato. 
        4. Il gestore invia una notifica di annullamento all'interfaccia. 
        5. L'interfaccia invia la notifica di annullamento della prenotazione all'utente.
        6. L'interfaccia renderizza il messaggio di avvenuto annullamento. 
        7. L'utente riceve la notifica che la prenotazione è stata annullata per scadenza.
Postcondizioni
 la prenotazione ha esito positivo, il mezzo risulta "prenotato", l'entità Prenotazione è registrata a sistema e il veicolo è sbloccato.
  Se il tempo di prenotazione scade, il mezzo torna allo stato "disponibile", la prenotazione è annullata e l'utente viene notificato.
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
Meccanismo di timeout automatico a 15 minuti per l'annullamento delle prenotazioni scadute. Capacità di sblocco fisico remoto del mezzo.


UserStories
OP.05
Nome
Amministrazione Prenotazioni
ID
UC.OP.03
Breve descrizione
L'operatore del servizio clienti accede alla lista delle prenotazioni valide, ne seleziona una e richiede l'annullamento. Il sistema tenta di inviare un comando di sblocco al mezzo tramite connessione IoT. In caso di successo, la prenotazione viene annullata, il mezzo torna disponibile e l'operatore riceve una notifica di conferma. Se la connessione IoT fallisce, il sistema crea una segnalazione e notifica l'errore.
Attori principali
Operatore  Servizio Clienti
Precondizioni
- L'operatore ha effettuato l'accesso all'interfaccia AppOperatoreSC con credenziali autorizzate.
Flusso principale
    1. L'operatore richiede la lista delle prenotazioni attive.
    2. L'interfaccia inoltra la richiesta al gestore delle prenotazioni.
    3. Il gestore interroga l'entità Prenotazione per recuperare tutte le prenotazioni valide.
    4. L'entità restituisce la lista delle prenotazioni al gestore.
    5. Il gestore restituisce la lista all'interfaccia.
    6. L'interfaccia renderizza le prenotazioni.
    7. Il sistema visualizza la lista delle prenotazioni all'operatore .
    8. L'operatore seleziona una prenotazione dalla lista.
    9. L'interfaccia invia il comando di annullamento al gestore.
    10. [Condizione: Connessione IoT fallita] Il gestore invia il comando di sblocco fisico al mezzo tramite IoT.
    11. Il mezzo conferma lo sblocco.
    12. Il gestore aggiorna lo stato della prenotazione ad "annullata" .
    13. La prenotazione conferma l'aggiornamento.
    14. Il gestore aggiorna lo stato del mezzo a "disponibile".
    15. Il mezzo conferma l'aggiornamento dello stato.
    16. Il gestore restituisce l'esito positivo all'interfaccia.
    17. L'interfaccia renderizza il messaggio di conferma.
    18. Il sistema notifica l'operatore dell'avvenuto annullamento.
Flussi alternativi
Connessione IoT fallita:
        1. Al passaggio 10 del flusso principale, la connessione IoT verso il mezzo non va a buon fine.
        2. Il gestore restituisce un esito negativo all'interfaccia.
        3. Il gestore gestisce internamente il timeout.
        4. Il gestore crea una segnalazione per il mezzo con i dettagli dell'errore.
        5. L'interfaccia renderizza il messaggio di errore.
        6. Il sistema notifica l'operatore dell'errore di comunicazione.
Postcondizioni
- Se la connessione IoT ha successo, la prenotazione risulta in stato "annullata" e il mezzo risulta in stato "disponibile".
- Se la connessione IoT fallisce, nessuna modifica di stato è applicata alla prenotazione o al mezzo, e una segnalazione viene creata a sistema per intervento tecnico.
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
Connettività IoT affidabile per il comando remoto di sblocco dei mezzi. Sistema di segnalazione per la gestione dei fallimenti di comunicazione.
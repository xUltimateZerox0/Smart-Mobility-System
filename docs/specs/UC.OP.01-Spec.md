UserStories
OP.01, OP.04
Nome: Gestione Flotta

ID: UC.OP.01

Breve descrizione:L'operatore tecnico accede alla mappa della flotta per visualizzare la posizione e lo stato dei veicoli. Se un'azione è necessaria, l'operatore seleziona un veicolo e invia un comando remoto. Il sistema tenta di eseguire il comando: se il veicolo è online, viene bloccato e lo stato aggiornato; se la connessione è persa, viene creata una segnalazione e l'operatore viene notificato.

Attori principali: Operatore Tecnico

Precondizioni:
     L'operatore tecnico ha effettuato l'accesso all'interfaccia AppOperatoreTecnico con credenziali autorizzate.

Flusso principale:
        1. L'operatore tecnico accede alla mappa della flotta.
    2. L'interfaccia richiede i mezzi della flotta al gestore.
    3. Il gestore recupera la lista dei veicoli dall'entità Mezzo.
    4. L'entità Mezzo restituisce la lista dei veicoli.
    5. Il gestore restituisce la lista dei mezzi all'interfaccia.
    6. L'operatore seleziona un veicolo e invia un comando di blocco.
    8. L'interfaccia inoltra la richiesta di blocco al gestore.
    9. [Condizione: Veicolo online ed esegue il comando] Il gestore invia il comando di blocco al mezzo.
    10. Il mezzo conferma l'avvenuto blocco.
    11. Il gestore aggiorna lo stato del mezzo nel database.
    12. L'entità Mezzo conferma l'aggiornamento.
    13. Il gestore notifica l'interfaccia del successo dell'operazione.
    14. Il sistema conferma all'operatore che l'operazione è riuscita.


Flussi alternativi:
 Connessione con il veicolo persa:
        1. Al passaggio 9 del flusso principale, il gestore non riesce a raggiungere il veicolo.
        2. Il gestore registra internamente il fallimento della connessione.
        3. Il gestore crea una segnalazione per il mezzo non raggiungibile.
        4. Il gestore notifica l'interfaccia che il veicolo non è raggiungibile.
        5. Il sistema mostra all'operatore un alert indicante che il veicolo è offline.


Postcondizioni:
  -  Se il comando remoto ha successo, il veicolo risulta in stato "bloccato" e lo stato è aggiornato a sistema.
 - Se la connessione è persa, una segnalazione è stata creata in Segnalazione e l'operatore è stato informato della necessità di un intervento fisico.


Include: Nessuno

Estende: Nessuno

Esteso dal caso d'uso: Nessuno

Specializza il caso d'uso: Nessuno

Generalizza il caso d'uso: Nessuno

Requisiti:Connettività IoT per il comando remoto dei veicoli. Sistema di segnalazione per la gestione dei veicoli non raggiungibili. 

status: SUCCESS_WITH_INFERENCE
extractor_version: "3.1-SequenceToUseCase"
Use Case: Gestione Flotta

```text
UserStories: [INFERRED] Come operatore tecnico voglio accedere alla mappa della flotta per monitorare lo stato dei veicoli e, se necessario, inviare comandi remoti di blocco ai mezzi che richiedono un intervento.

Nome: Gestione Flotta

ID: UC.OP.01

Breve descrizione: L'operatore tecnico accede alla mappa della flotta per visualizzare la posizione e lo stato (attivo/inattivo) dei veicoli. Se un'azione è necessaria, l'operatore seleziona un veicolo e invia un comando remoto. Il sistema tenta di eseguire il comando: se il veicolo è online, viene bloccato e lo stato aggiornato; se la connessione è persa, viene creata una segnalazione e l'operatore viene notificato.

Attori principali: Operatore Tecnico

Precondizioni:
    - [INFERRED] L'operatore tecnico ha effettuato l'accesso all'applicazione AppOperatore con credenziali autorizzate.
    - [INFERRED] La flotta è registrata a sistema con almeno un veicolo associato.

Flusso principale:
    1. L'operatore tecnico accede alla mappa della flotta (`Accede alla mappa della flotta`).
    2. L'applicazione richiede lo stato della flotta al gestore (`richiede stato flotta`).
    3. Il gestore recupera lo stato dei veicoli dall'entità Mezzo (`recupera stato veicoli`).
    4. L'entità Mezzo restituisce lo stato dei veicoli (`restituisce stato veicoli`).
    5. Il gestore restituisce la mappa aggiornata all'applicazione (`mappa aggiornata`).
    6. Il sistema visualizza la posizione dei veicoli distinguendo attivi e inattivi (`mostra posizione veicoli e distingue attivi e inattivi`).
    7. [Condizione: Azione necessaria] L'operatore seleziona un veicolo e invia un comando remoto (`Seleziona veicolo e invia comando`).
    8. L'applicazione inoltra la richiesta di comando remoto al gestore (`inoltra richiesta comando remoto`).
    9. [Condizione: Veicolo online ed esegue il comando] Il gestore invia il comando di blocco al mezzo (`blocco veicolo`).
    10. Il mezzo conferma l'avvenuto blocco (`operazione riuscita`).
    11. Il gestore aggiorna lo stato del mezzo nel database (`set stato`).
    12. L'entità Mezzo conferma l'aggiornamento (`aggiornamento riuscito`).
    13. Il gestore notifica l'applicazione del successo dell'operazione (`notifica successo operazione`).
    14. Il sistema conferma all'operatore che l'operazione è riuscita (`Operazione riuscita`).

Flussi alternativi:
    Connessione con il veicolo persa:
        1. Al passaggio 9 del flusso principale, il gestore non riesce a raggiungere il veicolo.
        2. Il gestore registra internamente il fallimento della connessione (`veicolo non raggiungibile`).
        3. Il gestore crea una segnalazione per il mezzo non raggiungibile (`crea segnalazione`).
        4. Il gestore notifica l'applicazione che il veicolo non è raggiungibile (`veicolo non raggiungibile`).
        5. Il sistema mostra all'operatore un alert indicante che il veicolo è offline e che è richiesto un intervento (`mostra alert offline e intervento richiesto`).

Postcondizioni:
    - [INFERRED] Se il comando remoto ha successo, il veicolo risulta in stato "bloccato" e lo stato è aggiornato a sistema.
    - [INFERRED] Se la connessione è persa, una segnalazione è stata creata a sistema e l'operatore è stato informato della necessità di un intervento fisico.

Include: Nessuno

Estende: Nessuno

Esteso dal caso d'uso: Nessuno

Specializza il caso d'uso: Nessuno

Generalizza il caso d'uso: Nessuno

Requisiti: [INFERRED] Connettività IoT per il comando remoto dei veicoli. Sistema di segnalazione per la gestione dei veicoli non raggiungibili. Visualizzazione cartografica in tempo reale dello stato della flotta.
```

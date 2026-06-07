status: SUCCESS_WITH_INFERENCE
extractor_version: "3.1-SequenceToUseCase"
Use Case: Gestione Corsa

```text
UserStories: [INFERRED] Come utente voglio scansionare il QR code di un mezzo per sbloccarlo e iniziare una corsa, verificando il mio metodo di pagamento e monitorando in tempo reale il costo stimato.

Nome: Gestione Corsa

ID: UC.UT.03

Breve descrizione: L'utente inquadra il QR code di un mezzo tramite l'app per avviare una corsa. Il sistema verifica la disponibilità del veicolo e richiede l'inserimento e la convalida di un metodo di pagamento. Al superamento dei controlli, il mezzo viene sbloccato fisicamente, la corsa ha inizio e l'applicazione avvia un monitoraggio continuo del costo.

Attori principali: Utente, Mezzo, GestorePagamento

Precondizioni:
    - [INFERRED] L'utente ha effettuato l'accesso all'applicazione ed è in prossimità del mezzo.
    - [INFERRED] L'utente ha concesso all'app i permessi per l'utilizzo della fotocamera.

Flusso principale:
    1. L'utente scansiona il QR code del mezzo (`scansionaQRCode`).
    2. Il sistema decodifica il QR code nell'identificativo del mezzo e invia la richiesta di controllo disponibilità al gestore (`controllaDisponibilità`).
    3. Il gestore richiede al mezzo il suo stato logico attuale (`getStato`).
    4. Il mezzo restituisce lo stato attuale (`stato:enum`).
    5. [Condizione: Mezzo disponibile] Il gestore conferma all'applicazione che il mezzo è disponibile (`true`).
    6. Il sistema renderizza la schermata per l'inserimento del metodo di pagamento (`mostraInserimentoMetodoPagamento`).
    7. Il sistema visualizza la richiesta all'utente (`visualizza Inserimento MetodoPagamento`).
    8. L'utente compila il form con i dati della carta e li invia (`inserisciDatiCarta`).
    9. L'applicazione richiede al Gestore Pagamento di elaborare e validare i dati (`elaboraDatiCarta`).
    10. [Condizione: Metodo pagamento valido] Il Gestore Pagamento conferma la validità della carta (`true`).
    11. Il sistema renderizza la conferma della validazione (`mostraMetodoConvalidato`).
    12. Il sistema visualizza la notifica di successo all'utente (`visualizza conferma`).
    13. L'utente richiede l'avvio effettivo della corsa (`apriAvvioCorsa`).
    14. L'applicazione trasmette il comando di avvio al gestore (`avviaCorsa`).
    15. Il gestore invia un comando asincrono di sblocco hardware al mezzo (`sbloccoMezzoFisico`).
    16. Il gestore aggiorna lo stato logico del mezzo impostandolo a in uso (`setStato(in_uso)`).
    17. Il gestore crea e registra a sistema l'entità della nuova corsa (`creaCorsa`).
    18. Il mezzo conferma l'avvenuto sblocco (`true`) e il cambio di stato logico (`void`).
    19. Il gestore conferma l'avvio della corsa all'applicazione (`true`).
    20. [INFERRED] Il sistema inizia un loop di aggiornamento periodico impostato ogni 30 secondi.
    21. L'applicazione richiede l'aggiornamento della stima del costo in base alla durata (`aggiornaStima`).
    22. Il gestore calcola e restituisce il valore stimato (`stimaCosto`).
    23. Il sistema renderizza l'importo calcolato (`mostraStima`).
    24. Il sistema visualizza il costo aggiornato a schermo per l'utente (`visualizza stima`).

Flussi alternativi:
    Mezzo non disponibile:
        1. Al passaggio 5 del flusso principale, il gestore rileva che il mezzo non è disponibile e restituisce esito negativo (`false`).
        2. Il sistema renderizza un messaggio di errore (`mostraErrore("mezzo non disponibile")`).
        3. Il sistema mostra l'errore all'utente (`visualizza errore`), impedendo l'avvio.

    Metodo pagamento non valido:
        1. Al passaggio 10 del flusso principale, il Gestore Pagamento rileva che la carta non è valida (`false`).
        2. Il sistema renderizza un messaggio di errore (`mostraErrore("metodo non convalidato")`).
        3. Il sistema mostra l'errore all'utente (`visualizza errore`), impedendo l'avvio.

Postcondizioni:
    - [INFERRED] Se i controlli hanno esito positivo, il veicolo risulta sbloccato fisicamente, lo stato logico è aggiornato e l'entità Corsa è attiva e in monitoraggio.
    - [INFERRED] In caso di fallimento in una delle validazioni, la corsa non viene creata e il veicolo rimane bloccato.

Include: Nessuno

Estende: Nessuno

Esteso dal caso d'uso: Nessuno

Specializza il caso d'uso: Nessuno

Generalizza il caso d'uso: Nessuno

Requisiti: [INFERRED] Interazione affidabile in tempo reale per lo sblocco hardware del mezzo e integrazione sicura con un Gestore Pagamento esterno.
```

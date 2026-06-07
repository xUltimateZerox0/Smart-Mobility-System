UserStories
UT.03, UT.07
Nome
Gestione Corsa
ID
UC.UT.03
Breve descrizione
L'utente inquadra il QR code di un mezzo tramite l'app per avviare una corsa. Il sistema verifica la disponibilità del veicolo e richiede l'inserimento e la convalida di un metodo di pagamento. Al superamento dei controlli, il mezzo viene sbloccato fisicamente, la corsa ha inizio e il sistema avvia un monitoraggio continuo del costo.
Attori principali
Utente
Precondizioni
- Il mezzo selezionato è sbloccato.
Flusso principale
1. L'utente scansiona il QR code del mezzo.
    2. L’interfaccia AppUtente decodifica il QR code nell'identificativo del mezzo e invia la richiesta di controllo disponibilità al gestore. 
    3. Il gestore della corsa richiede al mezzo il suo stato logico attuale.
    4. Il mezzo restituisce lo stato attuale.
    5. Il gestore della corsa conferma che il mezzo è disponibile.
    6. L’interfaccia AppUtente  renderizza la schermata per l'inserimento del metodo di pagamento. 
    7. L’interfaccia AppUtente mostra la richiesta all'utente.
    8. L'utente compila il form con i dati della carta e li invia. 
    9. L'interfaccia AppUtente richiede al Gestore Pagamento di elaborare e validare i dati.
    10. Se il metodo di pagamento è valido, il Gestore Pagamento conferma la validità della carta. 
    11. L’interfaccia AppUtente renderizza la conferma della validazione. 
    12. L’interfaccia AppUtente mostra il messaggio di validazione all’utente.
    13. L'utente richiede l'avvio effettivo della corsa. 
    14. L'interfaccia AppUtente trasmette il comando di avvio al gestore della corsa.
    15. Il gestore crea e registra a sistema l'entità della nuova corsa.
    16. Il gestore della corsa invia un comando asincrono di sblocco hardware al mezzo.
    17. Il gestore aggiorna lo stato logico del mezzo impostandolo a in uso.
    18. Il mezzo conferma l'avvenuto sblocco e il cambio di stato logico. 
    19. Il gestore conferma l'avvio della corsa all’interfaccia AppUtente 
    20. Il gestore inizia un loop di aggiornamento periodico del costo impostato ogni 30 secondi.
    21. L’interfaccia AppUtente richiede l'aggiornamento della stima del costo in base alla durata. 
    22. Il gestore calcola e restituisce il valore stimato all’interfaccia AppUtente. 
    23. L’interfaccia AppUtente renderizza l'importo calcolato. 
    24. L’interfaccia AppUtente visualizza il costo aggiornato a schermo per l'utente 
Flussi alternativi
Mezzo non disponibile:
        1. Al passaggio 3 del flusso principale, il gestore rileva che il mezzo non è disponibile e restituisce esito negativo 
        2. L’interfaccia AppUtente renderizza un messaggio di errore 
        3. L’interfaccia AppUtente mostra l'errore all'utente, impedendo l'avvio.

    Metodo pagamento non valido:
        1. Al passaggio 10 del flusso principale, il Gestore Pagamento rileva che la carta non è valida 
        2. L’interfaccia AppUtente renderizza un messaggio di errore 
        3. L’interfaccia AppUtente mostra l'errore all'utente, impedendo l'avvio.
Postcondizioni
 - Il veicolo risulta sbloccato fisicamente.


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
Interfaccia IoT per blocco/sblocco Mezzo remoto


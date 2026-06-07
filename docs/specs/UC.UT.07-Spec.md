UserStories
-
Nome
Termina Corsa e Pagamento
ID
UC.UT.07
Breve descrizione
 L'utente richiede la terminazione della corsa. Il sistema verifica se il veicolo si trova in un'area consentita, calcola il costo, effettua il pagamento, invia il comando di blocco al veicolo e lo rende nuovamente disponibile.


Attori principali
Utente
Precondizioni
 la corsa deve essere attiva
il mezzo deve essere in uso 


Flusso principale
    1. Il sistema renderizza la schermata della corsa attiva.
    2. L'Utente seleziona il termine della corsa.
    3. L'interfaccia inoltra la richiesta di terminazione al gestore della corsa.
    4. Il gestore della corsa ricerca la corsa attiva e la recupera con successo.
    5. Il gestore della corsa invia la richiesta al sistema per verificare le coordinate.
    6. Il sistema restituisce esito positivo. 
    7. Il gestore della corsa aggiorna e stima il costo della corsa, notificando l'interfaccia.
    8. L'interfaccia inoltra la richiesta di pagamento al gestore del pagamento.
    9. Il gestore del pagamento interagisce con il Gateway Pagamento per processare la transazione.
    10. Il Gateway Pagamento restituisce esito positivo
    11. L'interfaccia richiede la finalizzazione della corsa al gestore della corsa.
    12. Il gestore della corsa invia il comando di blocco fisico al mezzo.
    13. Il mezzo esegue l'operazione e restituisce la conferma di blocco.
    14. Il gestore della corsa imposta lo stato del mezzo su "disponibile" e riceve conferma dal mezzo.
    15. L'interfaccia renderizza il feedback visivo di successo.
    16. L’interfaccia mostra il messaggio di fine corsa all’utente.
Flussi alternativi
Corsa non trovata:
        1. Il gestore della corsa verifica e non trova la corsa indicata al passaggio 4.
        2. L'interfaccia renderizza feedback visivo di errore "Corsa non trovata".
L’utente viene notificato dell’errore.
    Area non consentita:
        1.  Il sistema restituisce esito negativo alla verifica dell'area del passaggio 5. 
        2. L'interfaccia renderizza feedback visivo di errore.
        3. L’utente viene notificato dell’errore.
        
    Pagamento non riuscito:
        1. Il Gateway Pagamento restituisce esito negativo per la transazione al passaggio 9.
        2. L'interfaccia renderizza feedback visivo di errore all'utente. 
        3. L'interfaccia mostra la form/view per l'inserimento di un nuovo metodo di pagamento.


Postcondizioni
 la cosa deve essere terminata
il pagamento deve essere completato
il mezzo deve essere disponibile 
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
Integrazione Gateway Pagamento, Tracciamento e verifica geospaziale GPS, Interfaccia IoT per blocco/sblocco Mezzo remoto.


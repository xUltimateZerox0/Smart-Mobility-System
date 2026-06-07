UserStories
UT.08
Nome
Metodo Pagamento
ID
UC.UT.05
Breve descrizione
L’utente inserisce i dati della propria carta nel sistema. Il sistema convalida tali dati tramite un gateway di pagamento esterno e, se validi e non già presenti, crea e salva il nuovo metodo di pagamento.
Attori principali
Utente
Precondizioni
L'utente ha effettuato l'accesso.
Flusso principale
   1. L'utente richiede di aprire la sezione del profilo.
   2. L’interfaccia AppUtente reindirizza la schermata del profilo.
   3. L’interfaccia AppUtente mostra la schermata del profilo all’utente.
   4. L'utente richiede di aprire la schermata di inserimento di un nuovo metodo di pagamento.
   5. L’interfaccia AppUtente mostra il form di inserimento del metodo di pagamento. 
   6. L'utente inserisce i dati della carta (numero carta, data di scadenza, CVV, intestatario) e invia.
    7. Il gestore pagamento avvia l'elaborazione dei dati per il salvataggio.
    8. Il gestore pagamento richiede la convalida della carta al Gateway Pagamento esterno.
    9. Il Gateway Pagamento restituisce esito positivo alla convalida.
    10. Il sistema verifica se il metodo di pagamento esiste già controllando il numero della carta. 
    11. Il gestore rileva che il metodo non esiste nel database.
    12. Il sistema salva il nuovo metodo di pagamento.
    13. Il gestore restituisce esito positivo dell'operazione.
    14. L’interfaccia AppUtente reindirizza la schermata di successo.
    15. L’interfaccia AppUtente restituisce la conferma di convalida del metodo di pagamento all’utente. 
Flussi alternativi
Convalidato ma Metodo di pagamento  già esistente:
        1. Al passaggio 10 del flusso principale, il sistema rileva che il metodo di pagamento è già presente.
        2. Il sistema salta la creazione del nuovo metodo ma indica ugualmente il successo della convalidazione.
       3. L’interfaccia AppUtente restituisce esito positivo dell’operazione
 14. Non convalidato:
        1. Al passaggio 8 del flusso principale, il Gateway Pagamento restituisce esito negativo alla convalida della carta. 
        2. Il gestore restituisce l'esito negativo all'interfaccia AppUtente. 
        3. L’interfaccia AppUtente reindirizza la schermata di errore.
        4. L’interfaccia AppUtente mostra l'errore all'utente.
Postcondizioni
Il metodo di pagamento è registrato
Include
-
Estende
UC.UT.02 (Gestione Corsa)
Esteso dal caso d'uso
-
Specializza il caso d'uso
-
Generalizza il caso d'uso
-
Requisiti
Integrazione con il Gateway Pagamento per validare in tempo reale i dati della carta.


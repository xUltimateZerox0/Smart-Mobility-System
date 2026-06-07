status: SUCCESS_WITH_INFERENCE
extractor_version: "3.1-SequenceToUseCase"
Use Case: Termina Corsa e Pagamento

```
UserStories: [INFERRED] Come Utente voglio terminare la corsa in corso e pagare il servizio affinché il mezzo venga bloccato in sicurezza e io concluda il noleggio.

Nome: Termina Corsa e Pagamento

ID: UC.UT.07

Breve descrizione: L'utente richiede la terminazione della corsa. Il sistema verifica se il veicolo si trova in un'area consentita, calcola il costo, effettua il pagamento, invia il comando di blocco al veicolo e lo rende nuovamente disponibile.

Attori principali: Utente

Precondizioni:
    - Corsa.inCorso == True
    - AppUtente.isLoggedIn == True
    - Mezzo.Stato == inUso
    
Flusso principale:
    1. [INFERRED] Il sistema renderizza la schermata della corsa attiva;
    2. L'Utente seleziona il termine della corsa;
    3. L'AppUtente inoltra la richiesta di terminazione a GestioneCorsa (terminaCorsa).
    4. GestioneCorsa ricerca la Corsa attiva e la recupera con successo.
    5. GestioneCorsa invia la richiesta a ZonaGeografica per verificare le coordinate (checkArea).
    6. ZonaGeografica restituisce esito positivo (Area Consentita).
    7. GestioneCorsa aggiorna e stima il costo della corsa, notificando l'AppUtente.
    8. L'AppUtente inoltra la richiesta di pagamento al GestorePagamento.
    9. Il GestorePagamento interagisce con il Gateway Pagamento per processare la transazione.
    10. Il Gateway Pagamento restituisce esito positivo (Pagamento Riuscito).
    11. L'AppUtente richiede la finalizzazione della corsa a GestioneCorsa (fineCorsa).
    12. GestioneCorsa invia il comando di blocco fisico al Mezzo (bloccoMezzoFisico).
    13. Il Mezzo esegue l'operazione e restituisce conferma di blocco.
    14. GestioneCorsa imposta lo stato del Mezzo su "disponibile" e riceve conferma dal Mezzo.
    15. L'AppUtente renderizza il feedback visivo di successo all'Utente ("Race terminated successfully").
    
Flussi alternativi:
    Corsa NON trovata:
        1. GestioneCorsa verifica e non trova la corsa indicata.
        2. L'AppUtente renderizza feedback visivo di errore "Corsa non trovata" all'Utente.
        
    Area NON Consentita:
        1. ZonaGeografica restituisce esito negativo alla verifica dell'area (Area NON Consentita).
        2. L'AppUtente renderizza feedback visivo di errore ("Race not terminated: not in allowed area.").
        3.Il sistema riporta l'utente alla View della corsa in corso.
        
    Pagamento NON Riuscito:
        1. Il Gateway Pagamento restituisce esito negativo per la transazione (Pagamento NON Riuscito).
        2. L'AppUtente renderizza feedback visivo di errore all'Utente ("Payment failed. Please check payment method or try again.").
        3. L'AppUtente mostra la form/view per l'inserimento di un nuovo metodo di pagamento.
        
Postcondizioni:
    - Corsa.inCorso == False
    - Mezzo.Stato == Disponibile
    - Pagamento.Completato == True
    
Include: Nessuno

Estende: Nessuno

Esteso dal caso d'uso: Nessuno

Specializza il caso d'uso: Nessuno

Generalizza il caso d'uso: Nessuno

Requisiti: [INFERRED] Integrazione Gateway Pagamento, [INFERRED] Tracciamento e verifica geospaziale GPS, [INFERRED] Interfaccia IoT per blocco/sblocco Mezzo remoto.

```

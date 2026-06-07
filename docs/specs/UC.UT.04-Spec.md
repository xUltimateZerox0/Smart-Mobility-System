UserStories
UT.06
Nome
Ottimizzazione Percorso
ID
UC.UT.04
Breve descrizione
L'utente inserisce la destinazione desiderata nell'interfaccia. Il sistema elabora il percorso a partire dalla posizione attuale, recupera le eventuali restrizioni geografiche attive nella zona, e genera il tracciato tramite un servizio di mappe esterno per poi mostrarlo all'utente.
Attori principali
Utente
Precondizioni
 L'utente ha effettuato l'accesso 
 le coordinate della posizione attuale dell'utente sono state acquisite.
Flusso principale
1.  L'interfaccia renderizza l’interfaccia contenente la mappa e il campo di ricerca destinazione.
    2. L'utente inserisce l'indirizzo di arrivo. 
    3.  L’interfaccia richiede il calcolo del percorso al gestore, fornendo le coordinate dell'utente e la stringa della destinazione. 
    4. Il gestore richiede i dati relativi alle restrizioni della zona in base alle coordinate dell'utente e alla destinazione. 
    5. Il sistema restituisce la lista delle zone geografiche e relative restrizioni.
    6. Il gestore invia le coordinate di partenza, di arrivo e le restrizioni al servizio di mappa esterno per l'elaborazione del percorso. 
    7. Il servizio di mappa elabora e restituisce i dati completi del percorso.
    8. Il gestore conferma l'avvenuto calcolo del percorso all'interfaccia.
    9. L’interfaccia renderizza a schermo il tracciato del percorso per l'utente.
Flussi alternativi
-
Postcondizioni
 Il tracciato del percorso è visibile sulla mappa. 
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
Integrazione con un Servizio Mappa esterno per l'elaborazione dei percorsi e delle coordinate


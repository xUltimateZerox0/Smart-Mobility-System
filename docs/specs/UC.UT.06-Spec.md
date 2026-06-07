UserStories
UT.09
Nome
Sospensione Corsa
ID
UC.UT.06
Breve descrizione
L'utente richiede di sospendere la propria corsa attiva. Il sistema blocca il veicolo fisicamente e ne aggiorna lo stato a "sospeso", fornendo all'utente un QR code sull'app. Successivamente, l'utente scansiona il QR code per sbloccare il mezzo, riprendere la corsa e il sistema aggiorna i costi relativi al tempo di sospensione.
Attori principali
Utente
Precondizioni
La corsa è attiva
Il mezzo è in uso 
Flusso principale
1. Il sistema renderizza l’interfaccia AppUtente della corsa attiva includendo il comando per sospendere la corsa.
    2. L'Utente invia il comando per sospendere la corsa.
    3. L'interfaccia AppUtente inoltra la richiesta di sospensione al gestore della corsa.
    4. Il gestore della corsa ricerca la corsa attiva tramite il suo id e la recupera con successo.
    5. Il gestore della corsa invia il comando di blocco fisico al mezzo.
    6. Il Mezzo esegue il blocco e restituisce la conferma.
    7. Il gestore della corsa imposta lo stato del mezzo su "sospeso".
    8. Il gestore della corsa trasmette all'interfaccia AppUtente il QR Code per il futuro sblocco.
    9. L'interfaccia AppUtente mostra il QR Code all'Utente. 
    10. L'Utente, per riprendere la corsa, effettua la scansione del QR code tramite l’'interfaccia AppUtente.
    11. L'interfaccia AppUtente inoltra la richiesta di sblocco al gestore della corsa inviando il QR Code letto. 
    12. Il gestore della corsa invia il comando di sblocco fisico al mezzo. 
    13. Il mezzo esegue l'operazione e restituisce conferma.
    14. Il gestore della corsa imposta nuovamente lo stato del Mezzo a "In_Uso". 
    15. Il gestore della corsa invia asincronamente l'aggiornamento del costo della corsa al sistema. 
    16. Il gestore della corsa restituisce esito positivo all'interfaccia AppUtente.
    17. L'interfaccia AppUtente renderizza il feedback visivo di ripresa corsa all'Utente.
    18. L’interfaccia AppUtente mostra all’utente la schermata di ripresa della corsa. 
Flussi alternativi
Corsa non esistente:
        1. Il gestore della corsa ricerca la corsa indicata ma non la trova al passaggio 4. 
        2. Il gestore della corsa restituisce esito nullo all'interfaccia AppUtente.
        3. L'interfaccia renderizza un feedback visivo di errore all'utente 
        4. Il sistema mostra la schermata di errore all’utente.
Postcondizioni
 la corsa è attiva 
il mezzo è in uso 
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
Integrazione sensore per lettura QR Code nell'App, Interfaccia IoT per blocco/sblocco Mezzo remoto, tariffa differenziata per la sospensione.


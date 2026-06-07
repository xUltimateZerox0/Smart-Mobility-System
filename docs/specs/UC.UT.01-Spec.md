UserStories
UT.01, UT.04, UT.05
Nome
Ricerca Mezzi
ID
UC.UT.01
Breve descrizione
L'utente avvia una ricerca di mezzi disponibili nelle vicinanze della propria posizione. Il sistema esegue una query geolocalizzata con un raggio iniziale (raggio base, es. 2 km). Se non vengono trovati risultati, l'utente può scegliere di estendere la ricerca a un raggio superiore (raggio esteso, es. 5 km). I mezzi trovati vengono visualizzati sulla mappa e l'utente può selezionarne uno 
Attori principali
Utente
Precondizioni
- L'utente ha effettuato l'accesso.
- Il sistema ha acquisito le coordinate GPS della posizione attuale dell'utente.
Flusso principale
1. L'utente avvia la ricerca dei mezzi nelle vicinanze 
    2. L’interfaccia AppUtente inoltra la richiesta al controller di ricerca fornendo le coordinate e il raggio base.
    3. Il gestore effettua la ricerca dei mezzi nel raggio base.
    4. Il sistema riporta la lista dei mezzi trovati.
    5.  Il gestore restituisce la lista dei mezzi all’interfaccia AppUtente.
    6. L'interfaccia AppUtente  renderizza i mezzi trovati sulla mappa.
    7. L’interfaccia AppUtente mostra la lista dei mezzi sulla mappa all'utente. 
    8. L'utente seleziona un mezzo dalla mappa.
    9. L'interfaccia AppUtente richiede le specifiche del mezzo al gestore.
    10. Il gestore interroga il sistema per ottenere i dettagli del mezzo.
    11. Il sistema restituisce i dati del mezzo al gestore.
    12. Il controller restituisce le specifiche all'interfaccia AppUtente.
    13. L'interfaccia AppUtente renderizza i dettagli del veicolo.
    14. L’interfaccia AppUtente mostra dettagli del mezzo all'utente. 
Flussi alternativi
Nessun mezzo trovato nel raggio base – Espansione accettata:
        1. Al passaggio 4 del flusso principale, la ricerca non restituisce mezzi.
        2. L'interfaccia AppUtente notifica all'utente che nessun mezzo è stato trovato nel raggio base e propone l'espansione.
        3. L'utente accetta l'espansione della ricerca.
        4. L'interfaccia inoltra la nuova richiesta con il raggio espanso al controller 
        5. Il controller esegue la ricerca con il nuovo raggio.
        6. Il sistema restituisce la lista dei mezzi trovati nel raggio espanso .
        7.  Il flusso riprende dal passaggio 5 del flusso principale con la lista ottenuta.

    Nessun mezzo trovato nel raggio espanso:
        1. Al passaggio 6 del flusso alternativo precedente, la ricerca con raggio espanso non restituisce risultati 
        2. L'interfaccia AppUtente renderizza un messaggio di errore 
        3. L’interfaccia AppUtente notifica l'utente che non sono disponibili mezzi nell'area 
     Utente rifiuta espansione della ricerca:
        1. L'utente non accetta l'espansione della ricerca dopo il primo risultato vuoto.
        2. L'interfaccia AppUtente mostra un messaggio di errore che indica che nessun mezzo è disponibile.
Postcondizioni
la ricerca ha successo, l'utente visualizza i mezzi sulla mappa e, eventualmente, le specifiche di un mezzo selezionato (es. "ID: 1, Tipo: Bicicletta, Autonomia: 100.0%, Condizione: Ottima").
nessun mezzo è disponibile in nessun raggio, l'utente viene informato e nessuna azione ulteriore è possibile.
Include
-
Estende
-
Esteso dal caso d'uso
UC.UT.02 (Prenotazione Mezzo)
Specializza il caso d'uso
-
Generalizza il caso d'uso
-
Requisiti
Servizio di geolocalizzazione attivo per il calcolo delle coordinate utente. 


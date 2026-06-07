status: SUCCESS_WITH_INFERENCE
extractor_version: "3.1-SequenceToUseCase"
Use Case: Ricerca Mezzi

```text
UserStories: [INFERRED] Come utente voglio cercare i mezzi disponibili nelle vicinanze e visualizzarne le specifiche, in modo da scegliere il veicolo più adatto alle mie esigenze.

Nome: Ricerca Mezzi

ID: UC.UT.01

Breve descrizione: L'utente avvia una ricerca di mezzi disponibili nelle vicinanze della propria posizione. Il sistema esegue una query geolocalizzata con un raggio iniziale (raggiob, es. 2km). Se non vengono trovati risultati, l'utente può scegliere di espandere la ricerca a un raggio superiore (raggioe, es. 5km). I mezzi trovati vengono visualizzati sulla mappa e l'utente può selezionarne uno per consultarne le specifiche tecniche.

Attori principali: Utente

Precondizioni:
    - [INFERRED] L'utente ha effettuato l'accesso all'applicazione.
    - [INFERRED] Il sistema ha acquisito le coordinate GPS della posizione attuale dell'utente.

Flusso principale:
    1. L'utente richiede la ricerca dei mezzi nelle vicinanze (`avviaRicercaMezzi`).
    2. L'applicazione inoltra la richiesta al controller di ricerca fornendo le coordinate e il raggio base (`visualizzaMezziVicini(coordinateUtente, raggiob)`).
    3. Il controller esegue la query sul database per recuperare i mezzi nell'area (`Mezzo.getMezziInArea(coordinateUtente, raggiob)`).
    4. Il database restituisce la lista dei mezzi trovati (`lista<Mezzo>`).
    5. [Condizione: Mezzi trovati nel raggio base] Il controller restituisce la lista non vuota all'applicazione (`lista<Mezzo>`).
    6. L'applicazione renderizza i mezzi trovati sulla mappa (`mostraMezzi(lista<Mezzo>)`).
    7. Il sistema visualizza la lista dei mezzi sulla mappa all'utente (`mostra lista mezzi`).
    8. L'utente seleziona un mezzo dalla mappa (`selezionaMezzo(idMezzo)`).
    9. L'applicazione richiede le specifiche del mezzo al controller (`visualizzaSpecifiche(idMezzo)`).
    10. Il controller interroga l'entità Mezzo per ottenerne i dettagli (`getDettagliMezzo()`).
    11. Il mezzo restituisce i propri dati (`Mezzo`).
    12. Il controller restituisce le specifiche all'applicazione (`mostraSpecificheMezzo(Mezzo)`).
    13. L'applicazione renderizza i dettagli del veicolo nell'interfaccia utente (`renderizzaDettagliVeicolo(Mezzo)`).
    14. Il sistema visualizza i dettagli del mezzo all'utente (`mostra dettagli mezzo`).

Flussi alternativi:
    Nessun mezzo trovato nel raggio base – Espansione accettata:
        1. Al passaggio 4 del flusso principale, la query non restituisce risultati (`listaVuota`).
        2. L'applicazione notifica all'utente che nessun mezzo è stato trovato nel raggio base e propone l'espansione (`notifica scelta espansione`).
        3. L'utente accetta l'espansione della ricerca (`confermaEspansione()`).
        4. L'applicazione inoltra la nuova richiesta con il raggio espanso al controller (`visualizzaMezziVicini(coordinateUtente, raggioe)`).
        5. Il controller esegue la query con il nuovo raggio (`Mezzo.getMezziInArea(coordinateUtente, raggioe)`).
        6. Il database restituisce la lista dei mezzi trovati nel raggio espanso (`lista<Mezzo>`).
        7. [Condizione: Mezzi trovati nel raggio espanso] Il flusso riprende dal passaggio 5 del flusso principale con la lista ottenuta.

    Nessun mezzo trovato nel raggio espanso:
        1. Al passaggio 6 del flusso alternativo precedente, la query con raggio espanso non restituisce risultati (`listaVuota`).
        2. L'applicazione renderizza un messaggio di errore (`mostraErrore()`).
        3. Il sistema notifica l'utente che non sono disponibili mezzi nell'area (`notifica errore`).

    Utente rifiuta espansione della ricerca:
        1. [INFERRED] L'utente non accetta l'espansione della ricerca dopo il primo risultato vuoto.
        2. L'applicazione mostra un messaggio di errore che indica che nessun mezzo è disponibile (`mostra messaggio errore "mezzi non trovati"`).

Postcondizioni:
    - [INFERRED] Se la ricerca ha successo, l'utente visualizza i mezzi sulla mappa e, eventualmente, le specifiche di un mezzo selezionato (es. "ID: 1, Tipo: Bicicletta, Autonomia: 100.0%, Condizione: Ottima").
    - [INFERRED] Se nessun mezzo è disponibile in nessun raggio, l'utente viene informato e nessuna azione ulteriore è possibile.

Include: Nessuno

Estende: Nessuno

Esteso dal caso d'uso: Nessuno

Specializza il caso d'uso: Nessuno

Generalizza il caso d'uso: Nessuno

Requisiti: [INFERRED] Servizio di geolocalizzazione attivo per il calcolo delle coordinate utente. Database con indice geospaziale per query di prossimità efficienti sui mezzi.
```

status: SUCCESS_WITH_INFERENCE
extractor_version: "3.1-SequenceToUseCase"
Use Case: Moderazione Utenti

```text
UserStories: [INFERRED] Come operatore del servizio clienti voglio cercare un utente per visualizzarne il report e, se necessario, applicare un'azione correttiva con notifica all'utente interessato.

Nome
Moderazione Utenti
ID
UC.OP.02
Breve descrizione
L'operatore del servizio clienti cerca un utente tramite il suo identificativo. Il sistema recupera i dati e il report associato all'utente e li mostra all'operatore. L'operatore può quindi aggiornare il report e applicare un'azione correttiva (es. sospensione, ammonimento) che viene propagata all'utente tramite notifica sulla sua interfaccia AppUtente.
Attori principali
Operatore Servizio Clienti
Precondizioni
- L'operatore ha effettuato l'accesso all'interfaccia AppOperatoreSC con credenziali autorizzate.
Flusso principale
    1. Il sistema renderizza la vista di moderazione utenti con il campo di ricerca.
    2. L'operatore richiede la visualizzazione del report di un utente.
    3. L'interfaccia inoltra la richiesta di ricerca al gestore utenti.
    4. Il gestore interroga l'entità Utente per recuperare i dati.
    5. L'entità Utente restituisce i propri dati.
    6. [Condizione: Utente Trovato] Il gestore restituisce il report dell'utente all'interfaccia.
    7. L'interfaccia renderizza il report nell'interfaccia.
    8. L'operatore aggiorna il report dell'utente.
    9. L'interfaccia inoltra la richiesta di gestione al gestore utenti.
    10. Il gestore applica l'azione correttiva sull'entità Utente.
    11. L'entità Utente conferma l'interfaccia dell'azione.
    12. Il gestore invia una notifica asincrona all'interfaccia dell'utente interessato.
    13. Il gestore conferma l'esito positivo dell'operazione all'interfaccia dell'operatore.
    14. L'interfaccia restituisce la conferma all'operatore.
Flussi alternativi
Utente Non Trovato:
        1. Al passaggio 5 del flusso principale, l'entità Utente non viene trovata nel sistema.
        2. Il gestore restituisce un esito negativo all'interfaccia.
        3. L'interfaccia notifica l'operatore che l'utente non è stato trovato.
Postcondizioni
-Se l'utente è stato trovato e l'azione correttiva applicata, il report dell'utente è aggiornato e l'utente ha ricevuto una notifica sull'azione intrapresa tramite la propria AppUtente.
 -Se l'utente non è stato trovato, nessuna modifica è stata effettuata e l'operatore è stato informato.
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
Sistema di gestione utenti con report consultabili e modificabili. Meccanismo di notifica asincrona verso l'interfaccia dell'utente (AppUtente) per comunicare le azioni correttive.


```

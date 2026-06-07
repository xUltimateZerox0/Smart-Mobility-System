status: SUCCESS_WITH_INFERENCE
extractor_version: "3.1-SequenceToUseCase"
Use Case: Moderazione Utenti

```text
UserStories: [INFERRED] Come operatore del servizio clienti voglio cercare un utente per visualizzarne il report e, se necessario, applicare un'azione correttiva con notifica all'utente interessato.

Nome: Moderazione Utenti

ID: UC.OP.02

Breve descrizione: L'operatore del servizio clienti cerca un utente tramite il suo identificativo. Il sistema recupera i dati e il report associato all'utente e li mostra all'operatore. L'operatore può quindi aggiornare il report e applicare un'azione correttiva (es. sospensione, ammonimento) che viene propagata all'utente tramite notifica sulla sua applicazione.

Attori principali: Operatore Servizio Clienti

Precondizioni:
    - [INFERRED] L'operatore ha effettuato l'accesso all'applicazione AppOperatoreSC con credenziali autorizzate.

Flusso principale:
    1. [INFERRED] Il sistema renderizza la vista di moderazione utenti con il campo di ricerca.
    2. L'operatore richiede la visualizzazione del report di un utente (`mostraReport(idUtente)`).
    3. L'applicazione inoltra la richiesta di ricerca al gestore utenti (`cercaReport(idUtente)`).
    4. Il gestore interroga l'entità Utente per recuperare i dati (`Utente.ricercaUtente(idUtente)`).
    5. L'entità Utente restituisce i propri dati (`Utente`).
    6. [Condizione: Utente Trovato] Il gestore restituisce il report dell'utente all'applicazione (`Utente.report`).
    7. L'applicazione renderizza il report nell'interfaccia (`visualizza report`).
    8. L'operatore aggiorna il report dell'utente (`aggiornaReport(idUtente)`).
    9. L'applicazione inoltra la richiesta di gestione al gestore utenti (`gestioneUtente(idUtente)`).
    10. Il gestore applica l'azione correttiva sull'entità Utente (`azioneCorrettiva(azione)`).
    11. L'entità Utente conferma l'applicazione dell'azione (`void`).
    12. Il gestore invia una notifica asincrona all'applicazione dell'utente interessato (`notificaAzione(idUtente, azione)`).
    13. Il gestore conferma l'esito positivo dell'operazione all'applicazione dell'operatore (`true`).
    14. L'applicazione restituisce la conferma all'operatore (`stringaSuccesso`).

Flussi alternativi:
    Utente Non Trovato:
        1. Al passaggio 5 del flusso principale, l'entità Utente non viene trovata nel sistema (`null`).
        2. Il gestore restituisce un esito negativo all'applicazione (`false`).
        3. L'applicazione notifica l'operatore che l'utente non è stato trovato (`stringaUtenteNonTrovato`).

Postcondizioni:
    - [INFERRED] Se l'utente è stato trovato e l'azione correttiva applicata, il report dell'utente è aggiornato e l'utente ha ricevuto una notifica sull'azione intrapresa tramite la propria AppUtente.
    - [INFERRED] Se l'utente non è stato trovato, nessuna modifica è stata effettuata e l'operatore è stato informato.

Include: Nessuno

Estende: Nessuno

Esteso dal caso d'uso: Nessuno

Specializza il caso d'uso: Nessuno

Generalizza il caso d'uso: Nessuno

Requisiti: [INFERRED] Sistema di gestione utenti con report consultabili e modificabili. Meccanismo di notifica asincrona verso l'applicazione dell'utente (AppUtente) per comunicare le azioni correttive.
```

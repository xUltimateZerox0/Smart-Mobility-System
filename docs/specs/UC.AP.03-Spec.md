status: SUCCESS_WITH_INFERENCE
extractor_version: "3.1-SequenceToUseCase"
Use Case: Restrizioni Geografiche

```text
UserStories: [INFERRED] Come Amministrazione Pubblica voglio gestire le restrizioni geografiche sulle zone operative della flotta, affinché il servizio di mobilità rispetti le normative vigenti e le limitazioni territoriali.

Nome: Restrizioni Geografiche

ID: UC.AP.03

Breve descrizione: L'attore Amministrazione Pubblica accede alla vista mappa per gestire le restrizioni geografiche. Il sistema recupera le zone esistenti, le visualizza e consente la modifica delle restrizioni. In caso di conflitto con restrizioni già presenti, l'utente può scegliere di sovrascrivere le regole esistenti o annullare l'operazione.

Attori principali: Amministrazione Pubblica

Precondizioni:
    - [INFERRED] L'utente PA ha effettuato l'accesso all'applicazione AppPA con credenziali autorizzate.
    - [INFERRED] Esistono zone geografiche registrate a sistema.

Flusso principale:
    1. L'attore PA seleziona la vista mappa nell'applicazione (`selezionaMappa()`).
    2. L'applicazione richiede le zone geografiche al gestore aree (`getZoneGeografiche()`).
    3. Il gestore interroga l'entità ZonaGeografica per recuperare tutte le zone (`ZonaGeografica.getZone()`).
    4. L'entità restituisce la lista delle zone (`Lista<ZonaGeografica>`).
    5. La lista viene restituita all'applicazione (`Lista<ZonaGeografica>`).
    6. L'applicazione renderizza la mappa con le zone esistenti (`reindirizzaMappa(Lista<ZonaGeografica>)`).
    7. Il sistema visualizza la UI Mappa all'attore PA (`UI Mappa`).
    8. L'attore PA richiede la modifica delle restrizioni per una zona (`modificaRestrizioni(ZonaGeografica)`).
    9. L'applicazione inoltra la richiesta di analisi dei conflitti al gestore aree (`AnalisiConflitti(ZonaGeografica)`).
    10. Il gestore verifica le sovrapposizioni con le restrizioni esistenti (`verificaSovrapposizioni(ZonaGeografica)`).
    11. L'entità ZonaGeografica restituisce la lista delle zone in conflitto (`Lista<ZonaGeografica>`).
    12. [Condizione: Nessun conflitto] Il gestore procede al salvataggio diretto delle restrizioni (`salvaRestrizioni(idArea, tipo, note, zona)`).
    13. L'entità ZonaGeografica conferma il salvataggio (`true`).
    14. Il gestore restituisce la conferma all'applicazione (`true`).
    15. L'applicazione renderizza la mappa aggiornata (`reindirizzaMappa()`).
    16. Il sistema visualizza la UI Mappa e la notifica di successo all'attore PA (`UI Mappa`).

Flussi alternativi:
    Conflitto con restrizioni esistenti – Sovrascrittura confermata:
        1. Al passaggio 11 del flusso principale, il gestore rileva un conflitto con restrizioni esistenti (`true`).
        2. L'applicazione notifica l'attore PA del conflitto e chiede conferma per la sovrascrittura (`notifica conflitto e richiesta sovrascrittura`).
        3. L'attore PA conferma la sovrascrittura (`confermaSovrascrittura(idArea, tipo, note, zona)`).
        4. L'applicazione aggiorna la restrizione nel gestore aree (`aggiornaRestrizione(idArea, tipo, note, zona)`).
        5. Il gestore procede al salvataggio delle restrizioni sovrascritte (`salvaRestrizioni(idArea, tipo, note, zona)`).
        6. L'entità ZonaGeografica conferma il salvataggio con sovrascrittura (`true`).
        7. Il gestore conferma l'operazione all'applicazione (`true`).
        8. L'applicazione restituisce la notifica di successo all'attore PA (`UI Mappa e notifica successo operazione`).

    Conflitto con restrizioni esistenti – Annullamento:
        1. Al passaggio 2 del flusso alternativo precedente, l'attore PA decide di non procedere alla sovrascrittura.
        2. L'attore PA rifiuta la sovrascrittura (`rifiutaSovrascrittura()`).
        3. L'applicazione notifica l'attore PA che nessuna modifica è stata effettuata (`stringaNessunaModifica`).

Postcondizioni:
    - [INFERRED] Se la restrizione è stata salvata (con o senza sovrascrittura), la zona geografica risulta aggiornata a sistema con le nuove regole.
    - [INFERRED] Se l'attore PA ha annullato la sovrascrittura, nessuna modifica è stata applicata e la mappa riflette lo stato precedente.

Include: Nessuno

Estende: Nessuno

Esteso dal caso d'uso: Nessuno

Specializza il caso d'uso: Nessuno

Generalizza il caso d'uso: Nessuno

Requisiti: [INFERRED] Sistema di gestione delle zone geografiche con supporto alla verifica dei conflitti tra restrizioni sovrapposte. Visualizzazione cartografica interattiva delle zone operative.
```

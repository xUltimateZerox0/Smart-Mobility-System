status: SUCCESS_WITH_INFERENCE
extractor_version: "3.1-SequenceToUseCase"
Use Case: Restrizioni Geografiche

```text
UserStories: [INFERRED] Come Amministrazione Pubblica voglio gestire le restrizioni geografiche sulle zone operative della flotta, affinché il servizio di mobilità rispetti le normative vigenti e le limitazioni territoriali.

Nome
Restrizioni Geografiche
ID
UC.AP.03
Breve descrizione
L' Amministrazione Pubblica accede alla vista mappa per gestire le restrizioni geografiche. Il sistema recupera le zone esistenti, le visualizza e consente la modifica delle restrizioni. In caso di conflitto con restrizioni già presenti, l'utente può scegliere di sovrascrivere le regole esistenti o annullare l'operazione.
Attori principali
Amministrazione Pubblica
Precondizioni
 -L'utente PA ha effettuato l'accesso all'interfaccia AppPA con credenziali autorizzate.
 - Esistono zone geografiche registrate a sistema.
Flusso principale
1. L'attore PA seleziona la vista mappa nell'interfaccia. 
    2. L'interfaccia richiede le zone geografiche al gestore delle aree. 
    3. Il gestore interroga l'entità ZonaGeografica per recuperare tutte le zone. 
    4. L'entità restituisce la lista delle zone. 
    5. La lista viene restituita all'interfaccia.. 
    6. L'interfaccia renderizza la mappa con le zone esistenti. 
    7. Il sistema visualizza la UI Mappa dell'attore PA. 
    8. L'attore PA richiede la modifica delle restrizioni per una zona. 
    9. L'interfaccia inoltra la richiesta di analisi dei conflitti al gestore aree.
    10. Il gestore verifica le sovrapposizioni con le restrizioni esistenti.
    11. L'entità ZonaGeografica restituisce la lista delle zone in conflitto. 
    12.Il gestore procede al salvataggio diretto delle restrizioni.
    13. L'entità ZonaGeografica conferma il salvataggio.
    14. Il gestore restituisce la conferma all'interfaccia.
    15. L'interfaccia renderizza la mappa aggiornata.
    16. Il sistema visualizza la UI Mappa e la notifica di successo a PA.
Flussi alternativi
Conflitto con restrizioni esistenti – Sovrascrittura confermata:
        1. Al passaggio 11 del flusso principale, il gestore rileva un conflitto con restrizioni esistenti 
        2. L'interfaccia notifica l'attore PA del conflitto e chiede conferma per la sovrascrittura 
        3. L'attore PA conferma la sovrascrittura
        4. L'interfaccia aggiorna la restrizione del gestore aree 
        5. Il gestore procede al salvataggio delle restrizioni sovrascritte 
        6. L'entità ZonaGeografica conferma il salvataggio con sovrascrittura 
        7. Il gestore conferma l'operazione all'interfaccia .
        8. L'interfaccia restituisce la notifica di successo all'attore PA 
    Conflitto con restrizioni esistenti – Annullamento:
        1. Al passaggio 2 del flusso alternativo precedente, l'attore PA decide di non procedere alla sovrascrittura.
        2. L'attore PA rifiuta la sovrascrittura
        3. L'interfaccia notifica l'attore PA che nessuna modifica è stata effettuata 
Postcondizioni
Se la restrizione è stata salvata (con o senza sovrascrittura), la zona geografica risulta aggiornata a sistema con le nuove regole.
  Se l'attore PA ha annullato la sovrascrittura, nessuna modifica è stata applicata e la mappa riflette lo stato precedente.
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
Sistema di gestione delle zone geografiche con supporto alla verifica dei conflitti tra restrizioni sovrapposte. Visualizzazione cartografica interattiva delle zone operative.


```

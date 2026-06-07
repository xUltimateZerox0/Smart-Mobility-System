status: SUCCESS_WITH_INFERENCE
extractor_version: "3.1-SequenceToUseCase"
Use Case: Metodo Pagamento

```text
UserStories: [INFERRED] Come utente voglio inserire e convalidare una carta come metodo di pagamento, affinché io possa pagare i servizi offerti dall'applicazione.

Nome: Metodo Pagamento

ID: UC.UT.05

Breve descrizione: Il caso d'uso descrive il processo mediante il quale un utente inserisce i dati della propria carta (credito/debito) nell'applicazione. Il sistema convalida tali dati tramite un gateway di pagamento esterno e, se validi e non duplicati, crea e salva il nuovo metodo di pagamento.

Attori principali: Utente, Gateway Pagamento

Precondizioni:
    - [INFERRED] L'utente ha effettuato l'accesso all'applicazione.

Flusso principale:
    1. L'utente richiede di aprire la sezione del profilo (`apriSezioneProfilo`).
    2. Il sistema renderizza la vista del profilo (`mostraSezioneProfilo`).
    3. Il sistema visualizza il profilo all'utente (`visualizza Profilo`).
    4. L'utente richiede di aprire la schermata di inserimento di un nuovo metodo di pagamento (`apriInserimentoMetodoPagamento`).
    5. Il sistema renderizza il form di inserimento del metodo di pagamento (`mostraInserimentoMetodoPagamento`).
    6. Il sistema visualizza il form all'utente (`visualizza Inserimento MetodoPagamento`).
    7. L'utente inserisce i dati della carta (numero carta, descrizione, CVV, intestatario) e invia (`inserisciDatiCarta`).
    8. Il sistema avvia l'elaborazione dei dati per il salvataggio (`elaboraDatiCarta`).
    9. Il gestore interno richiede la convalida della carta al Gateway Pagamento esterno (`convalidaCarta`).
    10. [Condizione: Convalidato] Il Gateway Pagamento restituisce esito positivo alla convalida (`true`).
    11. Il sistema verifica se il metodo di pagamento esiste già controllando il numero della carta (`controllaMetodoEsistente`).
    12. [Condizione: Metodo non esistente] Il sistema rileva che il metodo non esiste nel database (`false`).
    13. Il sistema crea e salva il nuovo metodo di pagamento (`creaMetodoPagamento`).
    14. Il gestore restituisce esito positivo dell'operazione all'applicazione (`true`).
    15. Il sistema renderizza la conferma di convalida del metodo di pagamento (`mostraMetodoConvalidato`).
    16. Il sistema visualizza il messaggio di successo all'utente (`visualizza messaggio metodo convalidato`).

Flussi alternativi:
    Convalidato ma Metodo già esistente:
        1. Al passaggio 11 del flusso principale, il sistema rileva che il metodo di pagamento è già presente (`true`).
        2. Il sistema salta la creazione del nuovo metodo e passa direttamente al passaggio 14.
        
    Non convalidato:
        1. Al passaggio 10 del flusso principale, il Gateway Pagamento restituisce esito negativo alla convalida della carta (`false`).
        2. Il gestore restituisce l'esito negativo all'applicazione (`false`).
        3. Il sistema renderizza un messaggio di errore (`mostraErrore('Metodo non convalidato')`).
        4. Il sistema visualizza l'errore all'utente (`visualizza errore`).

Postcondizioni:
    - [INFERRED] Se il flusso termina con successo, il metodo di pagamento è salvato a sistema e pronto per l'utilizzo.
    - [INFERRED] Se la convalida fallisce, nessun metodo di pagamento viene aggiunto.

Include: Nessuno

Estende: Nessuno

Esteso dal caso d'uso: Nessuno

Specializza il caso d'uso: Nessuno

Generalizza il caso d'uso: Nessuno

Requisiti: [INFERRED] Necessaria integrazione affidabile e sicura con il Gateway Pagamento per validare in tempo reale i dati della carta.
```

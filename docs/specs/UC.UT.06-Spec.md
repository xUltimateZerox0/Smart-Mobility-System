status: SUCCESS_WITH_INFERENCE
extractor_version: "3.1-SequenceToUseCase"
Use Case: Sospensione e Ripresa Corsa

```
UserStories: [INFERRED] Come Utente voglio poter sospendere temporaneamente la corsa affinché il veicolo venga bloccato senza terminare il noleggio, e poterlo sbloccare successivamente scansionando il QR code per riprendere la marcia.

Nome: Sospensione e Ripresa Corsa

ID: UC.UT.06

Breve descrizione: L'utente richiede di sospendere la corsa in corso. Il sistema blocca il veicolo fisicamente e ne aggiorna lo stato a "sospeso", fornendo all'utente un QR code sull'app. Successivamente, l'utente scansiona il QR code per sbloccare il mezzo, riprendere la corsa e il sistema aggiorna i costi relativi al tempo di sospensione.

Attori principali: Utente

Precondizioni:
    - AppUtente.isLoggedIn == True
    - Corsa.inCorso == True
    - Mezzo.Stato == In_Uso
    
Flusso principale:
    1. [INFERRED] Il sistema renderizza la View della corsa attiva includendo il controllo per sospendere la corsa.
    2. L'Utente interagisce con il controllo per sospendere la corsa (sospendiCorsa).
    3. L'AppUtente inoltra la richiesta di sospensione a GestioneCorsa (sospensioneCorsa).
    4. GestioneCorsa ricerca la Corsa attiva tramite idCorsa e la recupera con successo.
    5. GestioneCorsa invia il comando di blocco fisico al Mezzo (bloccoMezzoFisico).
    6. Il Mezzo esegue il blocco e restituisce conferma.
    7. GestioneCorsa imposta lo stato del Mezzo su "sospeso" (setStato).
    8. GestioneCorsa trasmette all'AppUtente il QR_Code per il futuro sblocco.
    9. L'AppUtente mostra la view con il QR Code all'Utente (mostraQRCode / visualizza QR Code).
    10. L'Utente, per riprendere la marcia, effettua la scansione del QR code tramite l'AppUtente (scansionaQRCode).
    11. L'AppUtente inoltra la richiesta di sblocco a GestioneCorsa inviando il QR_Code letto (richiediSblocco).
    12. GestioneCorsa invia il comando di sblocco fisico al Mezzo (sbloccoMezzoFisico).
    13. Il Mezzo esegue l'operazione e restituisce conferma (true).
    14. GestioneCorsa imposta nuovamente lo stato del Mezzo a "In_Uso" (setStato).
    15. GestioneCorsa invia asincronamente l'aggiornamento del costo di sospensione all'entità Corsa (AggiornaCosto).
    16. GestioneCorsa restituisce esito positivo all'AppUtente.
    17. L'AppUtente renderizza il feedback visivo di ripresa corsa all'Utente (mostraRipresaCorsa / visualizza sblocco corsa).

Flussi alternativi:
    Corsa NON Esistente:
        1. GestioneCorsa ricerca la corsa indicata ma non la trova (restituisce null).
        2. GestioneCorsa restituisce esito nullo all'AppUtente.
        3. L'AppUtente renderizza un feedback visivo di errore all'Utente ("Corsa non esistente").
        4. [INFERRED] Il sistema riporta l'utente alla schermata precedente.
        
Postcondizioni:
    - Corsa.inCorso == True
    - Mezzo.Stato == In_Uso
    - Corsa.CostoSospensione == Aggiornato
    
Include: Nessuno

Estende: Nessuno

Esteso dal caso d'uso: Nessuno

Specializza il caso d'uso: Nessuno

Generalizza il caso d'uso: Nessuno

Requisiti: [INFERRED] Integrazione sensore/fotocamera per lettura QR Code nell'App, [INFERRED] Interfaccia IoT per blocco/sblocco Mezzo remoto, [INFERRED] Logica di calcolo tariffa differenziata per la sospensione.

```

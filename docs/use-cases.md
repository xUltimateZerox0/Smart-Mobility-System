Campo	Descrizione
UserStories	UT.08
Nome	Metodo Pagamento
ID	UC.UT.05
Breve descrizione	L'utente inserisce e salva i dati di pagamento e consulta le informazioni relative alle restrizioni d'uso.
Attori principali	Utente
Precondizioni	L'utente ha effettuato l'accesso all'applicazione.
Flusso principale	1. L'utente accede alla sezione 'Profilo'.
2. Seleziona 'Metodi di Pagamento'.
3. Inserisce i dati della carta o collega un account digitale.
4. Il sistema valida il metodo ed invia conferma.
Flussi alternativi	3a. Dati carta non validi:
  1. Il sistema mostra un errore.
  2. Richiede un nuovo inserimento.
Postcondizioni	Il metodo di pagamento è registrato e attivo per le corse successive.
Include	-
Estende	UC.UT.02 (Gestione Corsa)
Esteso dal caso d'uso	-
Specializza il caso d'uso	-
Generalizza il caso d'uso	-
Requisiti	Conformità agli standard di sicurezza PCI-DSS per il trattamento dei dati bancari.

-------

Campo	Descrizione
UserStories	UT.09
Nome	Sospensione Corsa
ID	UC.UT.06
Breve descrizione	L’utente vuole poter sospendere temporaneamente la corsa, così da poter effettuare delle soste senza perdere il possesso del veicolo.
Attori principali	Utente
Precondizioni	L'utente ha iniziato una corsa.
L’utente si trova in una area in cui è consentito sospendere la corsa.
Flusso principale	1. L'utente accede alla sezione ‘Corsa’.
2. Seleziona ‘Sospensione Corsa’.
3. Il mezzo viene bloccato.
4. L’utente riceve un QR Code per lo sblocco del mezzo.
5.L’utente sblocca il mezzo.
6. Viene aggiornata la tariffa con il costo della sospensione.
7. L’utente riprende la sua corsa.
Flussi alternativi	-
Postcondizioni	-
Include	-
Estende	UC.UT.02 (Gestione Corsa)
Esteso dal caso d'uso	-
Specializza il caso d'uso	-
Generalizza il caso d'uso	-
Requisiti	

-------

Campo	Descrizione
UserStories	UT.03, UT.07
Nome	Gestione Corsa
ID	UC.UT.03
Breve descrizione	L'utente sblocca il mezzo tramite autenticazione, avvia la corsa, monitora il costo in tempo reale e gestisce eventuali soste.
Attori principali	Utente
Precondizioni	Il mezzo selezionato è sbloccato ed è stata avviata la sessione tramite app.
Flusso principale	1. L'utente scansiona il QR code sul mezzo.
2. Il sistema verifica la disponibilità del mezzo e la validità del pagamento dell'utente.
3. Il mezzo si sblocca fisicamente.
4. L'app avvia il timer della corsa.
Flussi alternativi	2a. Transazione fallita o account bloccato:
  1. Il sistema nega lo sblocco.
  2. Mostra un messaggio di errore all'utente.
Postcondizioni	La corsa è terminata correttamente; l'importo viene addebitato e il mezzo viene bloccato.
Include	-
Estende	-
Esteso dal caso d'uso	-
Specializza il caso d'uso	-
Generalizza il caso d'uso	-
Requisiti	Aggiornamento dei costi ogni 30 secondi; comunicazione sicura tra App e IoT del mezzo.
	
-------

Campo	Descrizione
UserStories	UT.02
Nome	Prenotazione Mezzo
ID	UC.UT.02
Breve descrizione	L'utente blocca uno o più mezzi specifici per un tempo limitato.
Attori principali	Utente
Precondizioni	L'utente ha individuato un mezzo disponibile sulla mappa.
Flusso principale	1. L'utente seleziona un veicolo sulla mappa.
2. Clicca su 'Prenota'.
3. Il sistema riserva il mezzo.
4. Connessione al mezzo e creazione istanza di notifica.
Flussi alternativi	3a. L'utente non raggiunge il mezzo entro 15 minuti dal tempo di prenotazione:
  1. La prenotazione scade automaticamente.
  2. Il mezzo torna disponibile per gli altri utenti.
Postcondizioni	Il mezzo è riservato all'utente; nessun altro può sbloccarlo.
Include	-
Estende	-
Esteso dal caso d'uso	-
Specializza il caso d'uso	-
Generalizza il caso d'uso	-
Requisiti	Gestione della concorrenza (concurrency control) per evitare doppie prenotazioni sullo stesso mezzo.
	
-------

Campo	Descrizione
UserStories	UT.01, UT.04, UT.05
Nome	Ricerca Mezzi 
ID	UC.UT.01
Breve descrizione	L'utente visualizza sulla mappa i mezzi disponibili, consulta le specifiche (es. autonomia) e seleziona il veicolo.
Attori principali	Utente
Precondizioni	L'utente ha aperto l'app e ha concesso i permessi di geolocalizzazione.
Flusso principale	1. L'applicazione rileva la posizione GPS dell'utente.
2. Mostra sulla mappa i veicoli disponibili nel raggio circostante.
3. L'utente clicca sull'icona di un mezzo per vedere i dettagli(mostra anche il tempo stimato per disponibilità del mezzo).
Flussi alternativi	2a. Assenza di veicoli nell'area:
  1. Il sistema mostra un avviso.
  2. Suggerisce di ampliare il raggio di ricerca.
Postcondizioni	L'utente ha le informazioni necessarie per scegliere e raggiungere il mezzo.
Include	-
Estende	-
Esteso dal caso d'uso	UC.UT.02 (Prenotazione Mezzo)
Specializza il caso d'uso	-
Generalizza il caso d'uso	-
Requisiti	Latenza minima nel caricamento dei dati geografici; precisione GPS entro i 5 metri.

-------

Campo	Descrizione
UserStories	UT.06
Nome	Ottimizzazione Percorso
ID	UC.UT.04
Breve descrizione	L'utente richiede al sistema il percorso più veloce ed efficiente per raggiungere la destinazione desiderata.
Attori principali	Utente
Precondizioni	L'utente ha iniziato una corsa attiva o sta pianificando un tragitto.
Flusso principale	1. L'utente inserisce la destinazione nell'app.
2. Il sistema calcola l'itinerario ottimale considerando ciclabili e zone a traffico limitato.
3. Mostra il tempo stimato e le indicazioni passo-passo.
Flussi alternativi	
Postcondizioni	L'utente riceve aggiornamenti in tempo reale sul percorso fino al termine della corsa.
Include	-
Estende	-
Esteso dal caso d'uso	-
Specializza il caso d'uso	-
Generalizza il caso d'uso	-
Requisiti	Integrazione con i dati in tempo reale del comune di Zootropolis riguardo i lavori stradali.
	
-------

Campo	Descrizione
UserStories	AP.01, AP.03
Nome	Monitoraggio Statistiche e Analisi Tratte
ID	UC.AP.01
Breve descrizione	L'amministrazione comunale accede a report aggregati e mappe delle tratte stradali per monitorare la mobilità urbana.
Attori principali	Amministrazione Pubblica
Precondizioni	L'utente amministratore ha effettuato l'accesso al portale analytics con credenziali valide.
Flusso principale	1. L'amministratore seleziona l'intervallo temporale.
2. Il sistema genera una mappa di calore delle tratte più frequentate.
3. Viene mostrato il grafico sui tempi medi di percorrenza.
Flussi alternativi	2a. Mancanza di dati storici per il periodo selezionato:
  1. Il sistema mostra un messaggio di avviso e invita a modificare le date.
Postcondizioni	I dati analizzati vengono esportati in formato PDF/CSV per fini amministrativi.
Include	-
Estende	-
Esteso dal caso d'uso	-
Specializza il caso d'uso	-
Generalizza il caso d'uso	-
Requisiti	I dati di tracciamento delle tratte devono essere rigorosamente aggregati e anonimizzati a monte per garantire la piena conformità al GDPR.

-------

Campo	Descrizione
UserStories	AP.02
Nome	Analisi Stato Flotta
ID	UC.AP.02
Breve descrizione	L'Amministrazione Pubblica esamina lo stato complessivo di usura e le condizioni fisiche dei mezzi per pianificare interventi manutentivi.
Attori principali	Amministrazione Pubblica
Precondizioni	L'utente amministratore ha effettuato l'accesso alla dashboard di gestione flotta.
Flusso principale	1. L'amministratore accede alla sezione 'Stato Flotta'.
2. Il sistema elenca i veicoli .
3. Mostra alert per i mezzi che richiedono manutenzione.
Flussi alternativi	3a. Nessun mezzo richiede interventi:
  1. La dashboard mostra una notifica di stato ottimale ('Flotta 100% operativa').
Postcondizioni	Viene generato un ordine di lavoro automatico per la squadra di tecnici sul campo.
Include	-
Estende	-
Esteso dal caso d'uso	-
Specializza il caso d'uso	-
Generalizza il caso d'uso	-
Requisiti	Classificazione automatica dello stato del mezzo tramite soglie di allerta (es. integrità della batteria, segnalazioni strutturali).

-------

Campo	Descrizione
UserStories	AP.04
Nome	Restrizioni Geografiche
ID	UC.AP.03
Breve descrizione	L'Amministrazione Pubblica definisce e aggiorna sulla mappa i confini delle zone in cui è vietato rilasciare i mezzi o vi sono limiti di velocità.
Attori principali	Amministrazione Pubblica
Precondizioni	L'amministratore è autenticato nel pannello di controllo della piattaforma.
Flusso principale	1. L'amministratore seleziona lo strumento di disegno sulla mappa.
2. Traccia il perimetro di una nuova area di sosta vietata.
3. Imposta le regole (es. divieto assoluto o tariffa extra).
4. Salva la configurazione.
Flussi alternativi	4a. Conflitto con aree esistenti:
  1. Il sistema segnala la sovrapposizione.
  2. Chiede la conferma di sovrascrittura o modifica.
Postcondizioni	Le nuove regole geografiche sono applicate istantaneamente a tutti i veicoli in circolazione.
Include	-
Estende	-
Esteso dal caso d'uso	-
Specializza il caso d'uso	-
Generalizza il caso d'uso	-
Requisiti	La modifica dei vincoli deve riflettersi immediatamente sui motori di calcolo delle app mobili: se un utente prova a terminare la corsa fuori dalle aree permesse, il sistema blocca l'azione.

-------

Campo	Descrizione
UserStories	OP.01, OP.04
Nome	Gestione Flotta
ID	UC.OP.01
Breve descrizione	L'operatore visualizza la distribuzione della flotta sulla mappa per ottimizzarne il posizionamento e può forzare comandi remoti.
Attori principali	Operatore Tecnico
Precondizioni	L'operatore ha effettuato l'accesso al sistema di gestione flotta centralizzato.
Flusso principale	1. Il sistema mostra in tempo reale i veicoli attivi e inattivi.
2. L'operatore rileva un veicolo fuori area o in posizione pericolosa.
3. Invia un comando remoto di blocco.
Flussi alternativi	3a. Mancata risposta del mezzo (offline):
  1. Crea una segnalazione di manutenzione per la squadra sul campo.
Postcondizioni	Il veicolo è messo in sicurezza o riposizionato correttamente; lo stato è aggiornato a sistema.
Include	-
Estende	-
Esteso dal caso d'uso	-
Specializza il caso d'uso	-
Generalizza il caso d'uso	-
Requisiti	La comunicazione tra server e hardware del mezzo per il comando di blocco remoto deve avere una latenza massima tollerabile di 5 secondi.

-------

Campo	Descrizione
UserStories	OP.02, OP.03
Nome	Moderazione Utenti
ID	UC.OP.02
Breve descrizione	L'operatore accede ai dati anagrafici degli utenti per investigare su segnalazioni di furti o incidenti e applicare eventuali sanzioni o blocchi account.
Attori principali	Operatore Servizio Clienti
Precondizioni	L'operatore è autenticato nel pannello di amministrazione utenti.
Flusso principale	1. L'operatore cerca l'utente tramite ID o email.
2. Visualizza la cronologia delle segnalazioni associate.
3. Seleziona l'azione correttiva (es. ammonizione, sospensione).
4. Il sistema notifica l'utente.
Flussi alternativi	1a. Utente non trovato:
  1. Il sistema mostra un messaggio di errore.
  2. Permette una nuova ricerca.
Postcondizioni	L'account dell'utente subisce le restrizioni impostate e l'azione viene loggata nel sistema.
Include	-
Estende	-
Esteso dal caso d'uso	-
Specializza il caso d'uso	-
Generalizza il caso d'uso	-
Requisiti	Accesso all'anagrafica regolato da policy GDPR rigorose; ogni azione di blocco deve generare un log di audit inalterabile.

-------

Campo	Descrizione
UserStories	OP.05
Nome	Amministrazione Prenotazioni
ID	UC.OP.03
Breve descrizione	L'operatore monitora l'elenco delle prenotazioni attive sui mezzi e le gestisce, potendole annullare o mandare in caso di necessità operative un tecnico sul campo.
Attori principali	Operatore  Servizio Clienti
Precondizioni	L'operatore ha effettuato l'accesso al sistema di gestione delle prenotazioni.
Flusso principale	1. L'operatore accede alla lista delle prenotazioni attive.
2. Seleziona una prenotazione da cancellare.
3. Clicca su 'Annulla prenotazione'.
4. Il veicolo torna disponibile sulla mappa.
Flussi alternativi	3a. Errore di comunicazione con il mezzo:
  1. Crea una segnalazione per il tecnico sul campo.
  2. Il sistema notifica l'operatore.
Postcondizioni	La prenotazione viene interrotta; lo stato del mezzo viene aggiornato in tempo reale.
Include	-
Estende	-
Esteso dal caso d'uso	-
Specializza il caso d'uso	-
Generalizza il caso d'uso	-
Requisiti	Sincronizzazione in tempo reale tra l'annullamento della prenotazione sul back-office e la ricomparsa del mezzo sull'app.

-------

Campo	Descrizione
UserStories	-
Nome	Termina Corsa e Pagamento
ID	UC.UT.07
Breve descrizione	L’utente termina la corsa e prosegue al pagamento di essa.
Attori principali	Utente
Precondizioni	L’utente ha avviato una corsa.
L’utente si trova in una area in cui è consentito terminare la corsa.
Flusso principale	1. L’utente termina la corsa via app. 
2.Il sistema verifica se il mezzo si trova in un area consentita per terminare la corsa. 
3. Si effettua il pagamento
4. La corsa viene terminata.
Flussi alternativi	2a. L’utente non si trova in un area consentita per terminare la corsa. 
1.Avviso via app dell’errore
2. L’utente deve spostarsi in un area consentita per riprovare. 
3a. Il pagamento non riesce ad andare a buon fine
1.L’API di Pagamento da un messaggio di errore e viene trasmesso via app all’utente.
Postcondizioni	Lo stato del mezzo viene aggiornato in disponibile per prenotazione o avvio nuova corsa.
La transazione è avvenuta con successo. 
Il mezzo fisico viene bloccato.
Include	-
Estende	-
Esteso dal caso d'uso	-
Specializza il caso d'uso	-
Generalizza il caso d'uso	-
Requisiti	-

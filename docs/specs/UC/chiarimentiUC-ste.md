## Inconsistencies response

### UC.AP.01

tutti i claim sono confermati (intuizione corretta)

### UC.AP.02

tutti i claim sono confermati (intuizione corretta)

### UC.AP.03

tutti i claim sono confermati (intuizione corretta)

### UC.AP.04

claim 1: confermo che inviaRichiestaLogout(email) accetta email:String e ritorna void

claim 2: confermo che richiestaLogout(email) esiste in AppPA

claim 3: UC.UT.09, UC.OP.04, UC.OP.05, UC.AP.04 sono identici per scelta architetturale

claim 4 to 10: tutti confermati

claim 11: mostraSuccesso() è stato aggiunto ad AppPA

### UC.OP.01

tutti i claim sono confermati (intuizione corretta)

### UC.OP.02










### UC.UT.01 

clam 1: Il controller usato nel caso d'uso non è un Controller generico, ma piuttosto RicercaMezzi, quindi va corretto.

clam 2: rifiutaEspansione() è implicito, non esiste realmente come metodo. Infatti l'utente quando riceve "notifica scelta espansione" può entrare nel blocco opzionale di confermaEspansione(), ma in alternativa se la ricerca non restituisce mezzi il flusso termina e basta.

claim 3: si tempoDisponibilità si riferisce alla UT.05 ed è sufficiente a coprirla.

claim 4: i due raggi impostati sono parametrici (2 e 5 km), non sono fissi. Utilizza costanti per permettere modifiche facilmente manutenibili ai raggi.

### UC.UT.02

claim 1: inviaRichiestaPrenotazione() ha effettivamente due diversi parametri, ovvero idMezzo e idUtente

claim 2: notificaScadenzaTempo(idPrenotazione) va effettivamente da AppUtente (View) a GestionePrenotazione (Controller) ed è un metodo di quest'ultimo necessario ad informare il controller che è stato superato il tempo di 15 minuti dall'orario della prenotazione. La documentazione dice "il sistema invia una notifica" perchè si riferisce a "Notifica prenotazione annullata" che viene inviata da AppUtente all'Utente dopo l'esecuzione di "MostraSuccesso" da parte di AppUtente (self message)

claim 3: si è corretto, MostraSuccesso(messaggio) è identico tra le varie view in cui è presente.

claim 4: ho aggiornato il diagramma XMI per includere setStato(scaduta) a prenotazione che è effettivamente necessaria all'interno del flusso di esecuzione (prima di notificaAnnullamentoPrenotazione() da controller gestioneprenotazione a apputente view e dopo il return void di setstato a mezzo model dal controller gestioneprenotazione)

### UC.UT.03

claim 1: avviaCorsa(idMezzo, idUtente) è la versione corretta, quella appunto riportata nel diagramma di sequenza che va da AppUtente a GestioneCorsa

claim 2: i nomi corretti sono sempre quelli in italiano, ovvero in questo specifico caso Utente e Mezzo.

claim 3: stimaCosto è il dato restituito da aggiornaStima(idCorsa) tra AppUtente (View) e Gestione(Corsa). I metodi inclusi in questo diagramma sono rispettivamente per AppUtente:
scansionaQRCode(QR_Code); apriAvvioCorsa(); mostraStima(idCorsa); mostraErrore("mezzo non disponibile")
per GestioneCorsa:
controllaDisponibilità(QR_Code); avviaCorsa(idMezzo, idUtente), aggiornaStima(idCorsa)
per Mezzo - Model:
getStato(idMezzo); setStato(in_uso)
per Corsa:
creaCorsa(orarioInizio, coordinatePartenza, idUtente, idMezzo)-->constructor
per Mezzo - IoT:
sbloccoMezzoFisico(idMezzo)

### UC.UT.04

claim 1: i parametri ufficiali di richiediCalcoloPercorso sono coordinateUtente e stringaDestinazione, come specificato nel diagramma di sequenza.

claim 2: coordinateFinali è giusto (parametro di getPercorso)

claim 3: getRestrizioniZona(coordinateUtente) restituisce lista<ZonaGeografica>

claim 4: datiPercorso e percorso calcolato si riferiscono ai dati sul percorso ottimale restituiti dalla chiamata all'API del servizio mappa. Trattandosi di una black box, i dettagli relativi all'implementazione dei metodi e i dati relativi sono stati tralasciati in quanto non pervenuti e differenti in base alla scelta del servizioMappa.

### UC.UT.05


  
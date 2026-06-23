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

claim 1: confermo che la firma di cercaReport è string

claim 2: confermato

claim 3: !attenzione! come indicato nel classDiagram-v1.8, il parametro 'azione' è di tipo enum convalori possibili: 'sospensione', 'disattivazione'

### UC.OP.03

claim 1: confermata

claim 2: annullaPrenotazione() ha come parametro idPrenotazione  (diagramma delle classi corretto)

claim 3: il nome canonico è GestionePrenotazione

claim 4: richiediLista() è un metodo del controller

claim 5: confermato

claim 6: confermiamo, il filtro 'attiva' è implicito

### UC.OP.04

tutti i claim sono confermati (intuizione corretta)

### UC.OP.05

tutti i claim sono confermati (intuizione corretta)

### UC.UT.05

tutti i claim sono confermati (intuizione corretta)

### UC.UT.06

tutti i claim sono confermati (intuizione corretta)

### UC.UT.07

`fineCorsa(idCorsa)` esiste sia in UC.UT.07-clean.uml che nel classDiagram

### UC.UT.08

claim 1: confermato, il progetto è strutturato sul pattern MVC con fat controller; ogni comunicazione dalla view al model o viceversa, deve necessariamente passare dal controller.

claim 2 (7b2d5e09): !attenzione! il metodo verificaValidita() serve a validare sintatticamente i dati di login (ad esempio la corretta sintassi della email)

### UC.UT.09

tutti i claim sono confermati (intuizione corretta)

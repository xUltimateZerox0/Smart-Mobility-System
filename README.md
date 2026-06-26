# Smart Mobility System

Piattaforma di **smart urban mobility** per il sharing di **bici, auto e
monopattini** — progetto accademico di ingegneria del software (a.a. 2025/2026)
del **Team Cofee Coders**.

Il sistema serve quattro ruoli attore — cittadini, operatori tecnici, operatori
servizio clienti e pubblica amministrazione — attraverso un'interfaccia web
unificata supportata da un'API REST.

---

## Architettura

**MVC Web-oriented con Controller Intermediario.** Le View non interrogano mai
il Model direttamente — ogni interazione passa attraverso i Controller che
orchestrano validazione, logica di business e mediazione con i sistemi esterni.

```
View (Vue 3)  →  REST API  →  Controller (Spring)  →  Service  →  Repository  →  DB
                    ↕                              ↕
               proxy (Vite)              Sistemi Esterni (SIMULATI)
                                           • Servizio Mappa (routing)
                                           • Gateway Pagamento (pagamenti)
                                           • Mezzo:IoT (blocco/sblocco veicolo)
                                           • DBMS (persistenza)
```

> **Attenzione:** Tutti i componenti esterni — incluso il **DBMS** (database),
> il **Servizio Mappa**, il **Gateway Pagamento** e **Mezzo:IoT** — sono
> **simulati** con implementazioni stub. In sviluppo viene usato H2
> (database in-memory), in produzione è richiesto MySQL. Si tratta di un
> **progetto accademico**: le integrazioni reali non sono state implementate.

---

## Stack Tecnologico

### Backend
| Componente | Tecnologia |
|------------|------------|
| Linguaggio | Java 21 |
| Framework  | Spring Boot 3.2.4 |
| Web        | Spring Web (API REST) |
| ORM        | Spring Data JPA + Hibernate Spatial |
| DB (dev)   | H2 in-memory (zero configurazione) — **simulato** |
| DB (prod)  | MySQL 8.0+ |
| Auth       | Token-based custom (BCrypt + SessionRegistry) |
| API docs   | SpringDoc OpenAPI 2.5 (Swagger UI) |

### Frontend
| Componente | Tecnologia |
|------------|------------|
| Linguaggio | TypeScript 6 |
| Framework  | Vue 3.5 (Composition API, `<script setup>`) |
| Build      | Vite 8 |
| Stato      | Pinia 3 |
| Router     | Vue Router 4 |
| HTTP       | Axios 1 |
| Mappe      | Leaflet 1.9 |

---

## Funzionalità per Ruolo

### Utente (Cittadino)
- Ricerca mezzi vicini (raggio 2 km / 5 km)
- Prenotazione veicolo (timeout 15 min)
- Avvio corsa tramite scansione QR code
- Sospensione / ripresa corsa
- Termine corsa con stima costi
- Gestione metodi di pagamento

### Operatore Tecnico
- Dashboard flotta — visualizzazione stato di tutti i veicoli
- Blocco/sblocco remoto veicoli
- Segnalazione veicoli per manutenzione

### Operatore Servizio Clienti
- Visualizzazione report utenti e storico prenotazioni
- Moderazione account utente (sospensione, azioni correttive)

### PA (Pubblica Amministrazione)
- Monitoraggio condizioni flotta
- Statistiche corse ed export dati
- Gestione zone geografiche con rilevamento conflitti restrizioni

---

## Avvio Rapido

```bash
# Il launcher verifica le dipendenze (le installa se mancanti),
# compila il backend, avvia backend (porta 8080) e frontend (porta 5173)
node start.js
```

Il launcher controlla Java 21+, Maven, Node.js 20+ e npm — e installa
gli strumenti mancanti tramite **winget** (Windows), **brew** (macOS),
o **apt/dnf/yum/pacman/apk** (Linux).

**Istruzioni dettagliate per ogni piattaforma:** [`INSTALL.md`](INSTALL.md)

### Avvio Manuale

```bash
# Terminale 1 — Backend
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Terminale 2 — Frontend
cd frontend && npm install && npm run dev
```

### Accesso

| Servizio     | URL                                          |
|-------------|----------------------------------------------|
| Frontend    | http://localhost:5173                        |
| API Backend | http://localhost:8080/api/v1                 |
| Swagger UI  | http://localhost:8080/api/v1/swagger-ui.html |

### Credenziali di Test (profilo dev, generate automaticamente)

| Ruolo                  | Email                                      | Password |
|------------------------|--------------------------------------------|----------|
| Utente                 | test@smartmobility.com                     | password |
| Operatore Tecnico      | operatore.tecnico@smartmobility.com        | password |
| Operatore SC           | operatore.sc@smartmobility.com             | password |
| PA                     | pa@smartmobility.com                       | password |

---

## Panoramica API

Tutti gli endpoint sono prefissati con `/api/v1`.

| Controller      | Path Base        | Endpoint Principali                                 |
|-----------------|------------------|------------------------------------------------------|
| Auth            | `/auth`          | `POST login`, `POST register`, `POST logout`         |
| Vehicles        | `/vehicles`      | `POST nearby`, `GET /{id}`, `GET /{id}/availability` |
| Bookings        | `/bookings`      | `POST create`, `DELETE /{id}`, `GET /user/me`        |
| Rides           | `/rides`         | `POST start`, `POST /{id}/end`, `POST /{id}/pause`, `POST unlock` |
| Payments        | `/payments`      | `POST process`, `POST methods`, `GET methods`        |
| Fleet           | `/fleet`         | `POST lock/unlock/maintenance`, `POST /{flottaId}/analyze` |
| Zones           | `/zones`         | `GET list`, `PUT /{id}`, `POST conflict-check`       |
| Statistics      | `/statistics`    | `POST analyze`, `POST export`                        |
| Admin/Users     | `/admin/users`   | `GET list`, `PUT /{id}/moderate`, `POST block`       |

La specifica OpenAPI completa è disponibile all'indirizzo
`/api/v1/swagger-ui.html` quando il backend è in esecuzione.

---

## Modello del Dominio

```
Attore (abstract, ereditarietà JOINED)
├── Utente          — cittadino con stato account, report, contatore prenotazioni
├── Operatore       — tecnico o servizio clienti (enum TipoOperatore)
└── PA              — pubblica amministrazione

Mezzo               — veicolo (bici/scooter/auto) con posizione, autonomia, costo, stato
Corsa               — sessione di guida con orari inizio/fine, costo, coordinate
Prenotazione        — blocco temporaneo (timeout 15 min)
MetodoPagamento     — dati carta cifrati (PK surrogata per PCI-DSS)
Segnalazione        — report anomalia (aperta → in_lavorazione → chiusa)
Transito            — M:N tra Corsa e ZonaGeografica
ZonaGeografica      — zona geografica con tipo restrizione (ZTL, divieto_parcheggio, limite_velocita)
```

### Enumerazioni

| Enum              | Valori                                                     |
|-------------------|------------------------------------------------------------|
| RuoloAttore       | `Utente`, `Operatore`, `PA`                                |
| TipoOperatore     | `OperatoreTecnico`, `OperatoreSC`                          |
| StatoUtente       | `attivo`, `sospeso`, `disattivato`                         |
| StatoMezzo        | `disponibile`, `prenotato`, `in_uso`, `sospeso`, `bloccato`, `manutenzione` |
| StatoPrenotazione | `attiva`, `scaduta`, `annullata`, `completata`             |
| StatoSegnalazione | `aperta`, `in_lavorazione`, `chiusa`                       |
| TipoRestrizione   | `divieto_parcheggio`, `ZTL`, `limite_velocita`             |

---

## Struttura del Progetto

```
Smart-Mobility-System/
├── pom.xml                         # Maven root (backend)
├── src/main/java/com/smartmobility/
│   ├── SmartMobilityApplication.java
│   ├── config/                     # CORS, DataInitializer (seed dev)
│   ├── controller/                 # 9 controller REST
│   ├── dto/                        # DTO request/response
│   ├── integration/                # stub sistemi esterni (SIMULATI)
│   ├── model/                      # entità JPA + enum
│   ├── repository/                 # repository Spring Data JPA
│   ├── security/                   # autenticazione token (SessionRegistry)
│   ├── service/                    # interfacce logica di business + implementazioni
│   └── view/                       # view CLI (legacy)
├── src/main/resources/             # application.yml, application-dev.yml
├── src/test/                       # 30+ classi di test (JUnit 5)
├── frontend/
│   ├── package.json                # Vue 3 + Vite + TypeScript
│   ├── vite.config.ts              # proxy di sviluppo → localhost:8080
│   └── src/
│       ├── main.ts                 # entry point Vue
│       ├── App.vue                 # componente radice
│       ├── api/                    # client Axios (auth, vehicles, bookings, rides, ...)
│       ├── router/                 # Vue Router (routing basato sui ruoli)
│       ├── stores/                 # Pinia (auth, ride)
│       ├── types/                  # interfacce TypeScript
│       └── views/                  # view per ruolo (auth, utente, operatore-t, operatore-sc, pa)
├── docs/
│   ├── diagrams/                   # UML (classi, componenti, ER, sequenza, use case)
│   └── specs/                      # specifiche CGD (Master_Spec, Component_Spec, Interface_Spec, Glossario)
├── scripts/                        # utility Python
├── start.js                        # launcher unificato cross-platform (auto-install dipendenze)
├── INSTALL.md                      # guida all'installazione per piattaforma
├── run-backend.sh
├── run-frontend.sh
└── run_pipeline.sh                 # compila → test → impacchetta → verifica
```

---

## Documentazione

Il progetto utilizza **Clarity-Gated Documents (CGD)** — una metodologia di
documentazione in cui ogni specifica viene verificata per qualità epistemica
prima di entrare nella knowledge base.

| Documento | Descrizione |
|-----------|-------------|
| `Master_Spec.cgd.md` | Specifica core del sistema — entità, flussi, vincoli |
| `Component_Spec.cgd.md` | Architettura a componenti — View, Controller, Model |
| `Interface_Spec.cgd.md` | Contratti di interfaccia tra i layer |
| `Class_Spec.cgd.md` | Specifica dettagliata delle classi |
| `Glossary.cgd.md` | Terminologia di dominio con matrice di cross-riferimento |
| `final-verdict.md` | Risultati verifica Spec Gate (13/13) e Clarity Gate (9/9) |

Le specifiche dei casi d'uso e i diagrammi UML si trovano nella directory
[`docs/`](docs/).

---

## Esecuzione Test

```bash
mvn test                                          # tutti i test
mvn test -Dtest=SmartMobilityApplicationTests     # classe singola
```

## Pipeline CI

```bash
./run_pipeline.sh            # compila → test → impacchetta → verifica
```

---

## Nota sui Sistemi Esterni

Trattandosi di un **progetto accademico**, tutti i sistemi esterni sono
**simulati**:

| Sistema       | Ruolo                            | Implementazione                          |
|---------------|----------------------------------|------------------------------------------|
| **DBMS**      | Persistenza dei dati             | H2 in-memory (dev), MySQL (prod) — tutto simulato via repository JPA |
| **Servizio Mappa** | Geolocalizzazione e routing | Stub che restituisce percorsi fissi       |
| **Gateway Pagamento** | Elaborazione pagamenti  | Stub che accetta sempre qualsiasi carta   |
| **Mezzo:IoT** | Blocco/sblocco fisico veicoli    | Stub che conferma sempre l'operazione     |

Nessuno di questi sistemi è connesso a un servizio reale. Le interfacce sono
definite e pronte per l'integrazione, ma le implementazioni attuali sono
sostituibili con servizi reali in una fase successiva.

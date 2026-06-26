# Smart Mobility System - Guida all'Installazione

## Prerequisiti

| Strumento    | Versione | Scopo                              |
|-------------|----------|------------------------------------|
| Java JDK    | 21+      | Runtime backend e compilazione     |
| Apache Maven| 3.9+     | Build backend e gestione dipendenze |
| Node.js     | 20+      | Runtime frontend e build           |
| npm         | 10+      | Gestione dipendenze frontend       |
| MySQL       | 8.0+     | Database di produzione (opzionale) |

---

## Installazione delle Dipendenze

### Windows

#### Opzione A: Automatica (tramite `start.js`)

Il launcher unificato (`node start.js`, presente nel repository) rileva gli strumenti mancanti e li installa
tramite **winget** (integrato in Windows 10 1809+/Windows 11) o **Chocolatey**.

#### Opzione B: Manuale

**Java 21**
```powershell
# winget
winget install --id EclipseAdoptium.Temurin.21.JDK

# oppure Chocolatey
choco install temurin21

# oppure manuale: scarica da https://adoptium.net/temurin/releases/?version=21
```
Dopo l'installazione, imposta `JAVA_HOME` e aggiungi `java` al `PATH`:
```powershell
# Verifica dove è stato installato Java
dir "C:\Program Files\Eclipse Adoptium\"  # oppure "C:\Program Files\Java\"

# Imposta le variabili d'ambiente (regola il percorso in base alla tua installazione)
[Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Eclipse Adoptium\jdk-21.0.xxxx-hotspot", "User")
[Environment]::SetEnvironmentVariable("Path", [Environment]::GetEnvironmentVariable("Path", "User") + ";%JAVA_HOME%\bin", "User")
```
Riavvia il terminale, poi verifica: `java -version`

**Maven**
```powershell
# winget
winget install --id Apache.Maven

# oppure Chocolatey
choco install maven

# oppure manuale: scarica da https://maven.apache.org/download.cgi
#   estrai in C:\tools\apache-maven-3.9.x
#   aggiungi C:\tools\apache-maven-3.9.x\bin al PATH
```

**Node.js 20 + npm**
```powershell
# winget
winget install --id OpenJS.NodeJS.20

# oppure Chocolatey
choco install nodejs-lts

# oppure manuale: scarica da https://nodejs.org/ (versione LTS 20.x)
```

**Verifica tutto:**
```powershell
java -version   # Deve mostrare 21+
mvn -version    # Deve mostrare 3.9+
node -v         # Deve mostrare v20+
npm -v          # Deve mostrare 10+
```

---

### macOS

#### Opzione A: Automatica (tramite `start.js`)

Il launcher unificato (presente nel repository) rileva gli strumenti mancanti e li installa tramite **Homebrew**.

#### Opzione B: Manuale (Homebrew)

```bash
# Java 21
brew install openjdk@21
sudo ln -sfn /usr/local/opt/openjdk@21/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-21.jdk

# Maven
brew install maven

# Node.js 20 + npm
brew install node
```

**Verifica tutto:**
```bash
java -version   # Deve mostrare 21+
mvn -version    # Deve mostrare 3.9+
node -v         # Deve mostrare v20+
npm -v          # Deve mostrare 10+
```

#### Opzione C: Manuale (senza Homebrew)

**Java 21:** Scarica da https://adoptium.net/temurin/releases/?version=21 (`.pkg` o `.tar.gz`)

**Maven:**
```bash
curl -LO https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz
tar -xzf apache-maven-3.9.9-bin.tar.gz -C /usr/local
echo 'export PATH=/usr/local/apache-maven-3.9.9/bin:$PATH' >> ~/.zshrc
```

**Node.js 20:** Scarica da https://nodejs.org/ (LTS 20.x `.pkg`)

---

### Linux

#### Opzione A: Automatica (tramite `start.js`)

Il launcher unificato (presente nel repository) rileva gli strumenti mancanti e li installa
tramite **apt** / **dnf** / **yum** / **apk** con `sudo`.

#### Opzione B: Manuale

**Debian / Ubuntu (apt)**
```bash
# Java 21
sudo apt-get update
sudo apt-get install -y openjdk-21-jdk

# Maven
sudo apt-get install -y maven

# Node.js 20 + npm (NodeSource - consigliato per l'ultima 20.x)
sudo apt-get update
sudo apt-get install -y ca-certificates curl gnupg
curl -fsSL https://deb.nodesource.com/setup_20.x | sudo bash -
sudo apt-get install -y nodejs
```

**Arch Linux (pacman)**
```bash
# Java 21
sudo pacman -S --noconfirm jdk21-openjdk

# Maven
sudo pacman -S --noconfirm maven

# Node.js 20 + npm
sudo pacman -S --noconfirm nodejs-lts-iron npm
```

**Fedora / RHEL (dnf)**
```bash
# Java 21
sudo dnf install -y java-21-openjdk-devel

# Maven
sudo dnf install -y maven

# Node.js 20 + npm
sudo dnf install -y nodejs
```

**CentOS / RHEL 7 (yum)**
```bash
# Java 21
sudo yum install -y java-21-openjdk-devel

# Maven
sudo yum install -y maven

# Node.js 20 + npm (richiede EPEL)
sudo yum install -y epel-release
sudo yum install -y nodejs
```

**Alpine (apk)**
```bash
# Java 21
apk add openjdk21

# Maven
apk add maven

# Node.js 20 + npm
apk add nodejs npm
```

**Verifica tutto:**
```bash
java -version   # Deve mostrare 21+
mvn -version    # Deve mostrare 3.9+
node -v         # Deve mostrare v20+
npm -v          # Deve mostrare 10+
```

---

## Avvio Rapido (Sviluppo - H2 In-Memory)

Per lo sviluppo locale, il backend utilizza H2 (database in-memory senza configurazione).
Non è necessario configurare MySQL.

### Utilizzo del Launcher Unificato (consigliato)

Il launcher (script unificato presente nel repository) verifica le dipendenze, installa
eventuali strumenti mancanti, compila il backend, avvia entrambi i server e gestisce
la pulizia alla pressione di Ctrl+C.

```bash
node start.js
```

### Avvio Manuale

**Terminale 1 - Backend:**
```bash
cd Smart-Mobility-System
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

**Terminale 2 - Frontend:**
```bash
cd Smart-Mobility-System/frontend
npm install
npm run dev
```

### Accesso all'Applicazione

| Servizio     | URL                                          |
|--------------|----------------------------------------------|
| Frontend     | http://localhost:5173                        |
| Backend API  | http://localhost:8080/api/v1                 |
| Swagger UI   | http://localhost:8080/api/v1/swagger-ui.html |
| Specifica OpenAPI | http://localhost:8080/api/v1/api-docs   |
| Console H2   | http://localhost:8080/api/v1/h2-console      |

### Credenziali di Test (precaricate automaticamente nel profilo dev)

| Ruolo                     | Email                                      | Password |
|---------------------------|--------------------------------------------|----------|
| Utente                    | test@smartmobility.com                     | password |
| Operatore Tecnico         | operatore.tecnico@smartmobility.com        | password |
| Operatore Sociale         | operatore.sc@smartmobility.com             | password |
| Pubblica Amministrazione (PA) | pa@smartmobility.com                   | password |

---

## Configurazione di Produzione (MySQL)

1. Crea il database:

```sql
CREATE DATABASE smart_mobility CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. Imposta le variabili d'ambiente:

```bash
export DB_URL=jdbc:mysql://localhost:3306/smart_mobility
export DB_USER=root
export DB_PASS=your_password
```

3. Compila ed esegui:

```bash
mvn package -DskipTests
java -jar target/smart-mobility-system-1.0.0.jar
```

> **Nota:** In modalità produzione, JPA `ddl-auto` è impostato su `validate`. Gestisci
> le migrazioni dello schema manualmente (es. tramite Flyway, Liquibase o script SQL).

---

## Esecuzione dei Test

```bash
# Tutti i test
mvn test

# Singola classe di test
mvn test -Dtest=SmartMobilityApplicationTests

# Con output dettagliato
mvn test -Dtest=GestioneAutenticazioneControllerTest -X
```

## Pipeline CI Completa

```bash
./run_pipeline.sh
```

Esegue: `compile` -> `test` -> `package` -> `verify`

---

## Struttura del Progetto

```
Smart-Mobility-System/
  pom.xml                        # Maven root (build backend)
  src/main/java/                 # Sorgenti Java backend
  src/main/resources/            # Configurazione backend (application.yml)
  src/test/java/                 # Test unitari/integrazione backend
  frontend/
    package.json                 # Dipendenze frontend
    vite.config.ts               # Dev server Vite + configurazione proxy
    src/                         # Sorgenti Vue 3 + TypeScript
    index.html                   # Punto di ingresso HTML
  docs/                          # Diagrammi UML e specifiche
  scripts/                       # Script di utilità
  start.js                       # Launcher unificato multipiattaforma (presente nel repository)
  run-backend.sh                 # Launcher backend (Linux/macOS)
  run-frontend.sh                # Launcher frontend (Linux/macOS)
  run_pipeline.sh                # Pipeline CI
```

---

## Risoluzione dei Problemi

| Problema                           | Soluzione                                                    |
|------------------------------------|--------------------------------------------------------------|
| `java: release 21 not supported`   | Installa JDK 21 e imposta `JAVA_HOME` puntando ad esso       |
| `JAVA_HOME` non impostato (Windows)| `[Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Path\To\jdk-21", "User")` |
| `mvn: command not found`           | Installa Maven e aggiungi la directory `bin/` al `PATH`      |
| `mvn` non trovato dopo winget      | Riavvia il terminale — winget potrebbe non aggiornare il `PATH` per la sessione corrente |
| Porta 8080 già in uso              | Termina il processo: `lsof -ti:8080 \| xargs kill` (macOS/Linux) oppure `netstat -ano \| findstr :8080` poi `taskkill /PID <id>` (Windows) |
| Porta 5173 già in uso              | Vite seleziona automaticamente la porta disponibile successiva |
| Il frontend non raggiunge il backend | Verifica che il backend sia in esecuzione sulla porta 8080; controlla il proxy Vite in `vite.config.ts` |
| `npm install` fallisce             | Verifica Node.js 20+ (`node -v`); elimina `node_modules` e `package-lock.json`, riprova |
| Console H2 non accessibile         | Usa `http://localhost:8080/api/v1/h2-console` con URL JDBC `jdbc:h2:mem:smart_mobility` (solo profilo dev) |
| `start.js` bloccato su Windows     | Esegui `powershell -File <script>` o apri in terminale VS Code |
| Avviso di sicurezza `curl \| bash` | Controlla lo script NodeSource su https://deb.nodesource.com/setup_20.x prima di eseguire il pipe in bash |

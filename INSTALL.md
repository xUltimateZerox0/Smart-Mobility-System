# Smart Mobility System - Installation Guide

## Prerequisites

| Tool        | Version   | Purpose                          |
|-------------|-----------|----------------------------------|
| Java JDK    | 21+       | Backend runtime & compilation    |
| Apache Maven| 3.9+      | Backend build & dependency mgmt  |
| Node.js     | 20+       | Frontend runtime & build         |
| npm         | 10+       | Frontend dependency management   |
| MySQL       | 8.0+      | Production database (optional)   |

---

## Installing Dependencies

### Windows

#### Option A: Automatic (via `start.js`)

The unified launcher (`node start.js`) detects missing tools and installs them
via **winget** (built into Windows 10 1809+/Windows 11) or **Chocolatey**.

#### Option B: Manual

**Java 21**
```powershell
# winget
winget install --id EclipseAdoptium.Temurin.21.JDK

# or Chocolatey
choco install temurin21

# or manual: download from https://adoptium.net/temurin/releases/?version=21
```
After install, set `JAVA_HOME` and add `java` to `PATH`:
```powershell
# Check where Java was installed
dir "C:\Program Files\Eclipse Adoptium\"  # or "C:\Program Files\Java\"

# Set environment variables (adjust path to match your install)
[Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Eclipse Adoptium\jdk-21.0.xxxx-hotspot", "User")
[Environment]::SetEnvironmentVariable("Path", [Environment]::GetEnvironmentVariable("Path", "User") + ";%JAVA_HOME%\bin", "User")
```
Restart your terminal, then verify: `java -version`

**Maven**
```powershell
# winget
winget install --id Apache.Maven

# or Chocolatey
choco install maven

# or manual: download from https://maven.apache.org/download.cgi
#   extract to C:\tools\apache-maven-3.9.x
#   add C:\tools\apache-maven-3.9.x\bin to PATH
```

**Node.js 20 + npm**
```powershell
# winget
winget install --id OpenJS.NodeJS.20

# or Chocolatey
choco install nodejs-lts

# or manual: download from https://nodejs.org/ (LTS version 20.x)
```

**Verify all:**
```powershell
java -version   # Must show 21+
mvn -version    # Must show 3.9+
node -v         # Must show v20+
npm -v          # Must show 10+
```

---

### macOS

#### Option A: Automatic (via `start.js`)

The unified launcher detects missing tools and installs them via **Homebrew**.

#### Option B: Manual (Homebrew)

```bash
# Java 21
brew install openjdk@21
sudo ln -sfn /usr/local/opt/openjdk@21/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-21.jdk

# Maven
brew install maven

# Node.js 20 + npm
brew install node
```

**Verify all:**
```bash
java -version   # Must show 21+
mvn -version    # Must show 3.9+
node -v         # Must show v20+
npm -v          # Must show 10+
```

#### Option C: Manual (without Homebrew)

**Java 21:** Download from https://adoptium.net/temurin/releases/?version=21 (`.pkg` or `.tar.gz`)

**Maven:**
```bash
curl -LO https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz
tar -xzf apache-maven-3.9.9-bin.tar.gz -C /usr/local
echo 'export PATH=/usr/local/apache-maven-3.9.9/bin:$PATH' >> ~/.zshrc
```

**Node.js 20:** Download from https://nodejs.org/ (LTS 20.x `.pkg`)

---

### Linux

#### Option A: Automatic (via `start.js`)

The unified launcher detects missing tools and installs them via **apt** /
**dnf** / **yum** / **apk** with `sudo`.

#### Option B: Manual

**Debian / Ubuntu (apt)**
```bash
# Java 21
sudo apt-get update
sudo apt-get install -y openjdk-21-jdk

# Maven
sudo apt-get install -y maven

# Node.js 20 + npm (NodeSource - recommended for latest 20.x)
sudo apt-get update
sudo apt-get install -y ca-certificates curl gnupg
curl -fsSL https://deb.nodesource.com/setup_20.x | sudo bash -
sudo apt-get install -y nodejs
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

# Node.js 20 + npm (requires EPEL)
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

**Verify all:**
```bash
java -version   # Must show 21+
mvn -version    # Must show 3.9+
node -v         # Must show v20+
npm -v          # Must show 10+
```

---

## Quick Start (Development - H2 In-Memory)

For local development, the backend uses H2 (zero-configuration in-memory
database). No MySQL setup is needed.

### Using the Unified Launcher (recommended)

The launcher checks dependencies, installs any missing tools, compiles the
backend, starts both servers, and handles cleanup on Ctrl+C.

```bash
node start.js
```

### Manual Start

**Terminal 1 - Backend:**
```bash
cd Smart-Mobility-System
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

**Terminal 2 - Frontend:**
```bash
cd Smart-Mobility-System/frontend
npm install
npm run dev
```

### Access the Application

| Service       | URL                                          |
|---------------|----------------------------------------------|
| Frontend      | http://localhost:5173                        |
| Backend API   | http://localhost:8080/api/v1                 |
| Swagger UI    | http://localhost:8080/api/v1/swagger-ui.html |
| OpenAPI spec  | http://localhost:8080/api/v1/api-docs        |
| H2 Console    | http://localhost:8080/api/v1/h2-console      |

### Test Credentials (auto-seeded in dev profile)

| Role                   | Email                                      | Password |
|------------------------|--------------------------------------------|----------|
| User (Utente)          | test@smartmobility.com                     | password |
| Tech Operator          | operatore.tecnico@smartmobility.com        | password |
| Social Operator        | operatore.sc@smartmobility.com             | password |
| Public Admin (PA)      | pa@smartmobility.com                       | password |

---

## Production Setup (MySQL)

1. Create the database:

```sql
CREATE DATABASE smart_mobility CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. Set environment variables:

```bash
export DB_URL=jdbc:mysql://localhost:3306/smart_mobility
export DB_USER=root
export DB_PASS=your_password
```

3. Build and run:

```bash
mvn package -DskipTests
java -jar target/smart-mobility-system-1.0.0.jar
```

> **Note:** In production mode, JPA `ddl-auto` is set to `validate`. Manage
> schema migrations manually (e.g., via Flyway, Liquibase, or SQL scripts).

---

## Running Tests

```bash
# All tests
mvn test

# Single test class
mvn test -Dtest=SmartMobilityApplicationTests

# With verbose output
mvn test -Dtest=GestioneAutenticazioneControllerTest -X
```

## Full CI Pipeline

```bash
./run_pipeline.sh
```

Executes: `compile` -> `test` -> `package` -> `verify`

---

## Project Structure

```
Smart-Mobility-System/
  pom.xml                        # Maven root (backend build)
  src/main/java/                 # Backend Java sources
  src/main/resources/            # Backend config (application.yml)
  src/test/java/                 # Backend unit/integration tests
  frontend/
    package.json                 # Frontend dependencies
    vite.config.ts               # Vite dev server + proxy config
    src/                         # Vue 3 + TypeScript sources
    index.html                   # HTML entry point
  docs/                          # UML diagrams and specs
  scripts/                       # Utility scripts
  start.js                       # Cross-platform unified launcher
  run-backend.sh                 # Backend launcher (Linux/macOS)
  run-frontend.sh                # Frontend launcher (Linux/macOS)
  run_pipeline.sh                # CI pipeline
```

---

## Troubleshooting

| Problem                           | Solution                                                     |
|-----------------------------------|--------------------------------------------------------------|
| `java: release 21 not supported`  | Install JDK 21 and set `JAVA_HOME` to point to it            |
| `JAVA_HOME` not set (Windows)     | `[Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Path\To\jdk-21", "User")` |
| `mvn: command not found`          | Install Maven and add its `bin/` directory to `PATH`         |
| `mvn` not found after winget      | Restart terminal -- winget may not update `PATH` for the current session |
| Port 8080 already in use          | Kill the process: `lsof -ti:8080 \| xargs kill` (macOS/Linux) or `netstat -ano \| findstr :8080` then `taskkill /PID <id>` (Windows) |
| Port 5173 already in use          | Vite auto-selects the next available port                    |
| Frontend can't reach backend      | Ensure backend runs on port 8080; check Vite proxy in `vite.config.ts` |
| `npm install` fails               | Verify Node.js 20+ (`node -v`); delete `node_modules` and `package-lock.json`, retry |
| H2 Console not accessible         | Use `http://localhost:8080/api/v1/h2-console` with JDBC URL `jdbc:h2:mem:smart_mobility` (dev profile only) |
| `start.js` on Windows blocked     | Run `powershell -File <script>` or open in VS Code terminal  |
| `curl \| bash` security warning   | Review the NodeSource script at https://deb.nodesource.com/setup_20.x before piping to bash |

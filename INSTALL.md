# Smart Mobility System - Installation Guide

## Prerequisites

| Tool        | Version   | Purpose                          |
|-------------|-----------|----------------------------------|
| Java JDK    | 21+       | Backend runtime & compilation    |
| Apache Maven| 3.9+      | Backend build & dependency mgmt  |
| Node.js     | 20+       | Frontend runtime & build         |
| npm         | 10+       | Frontend dependency management   |
| MySQL       | 8.0+      | Production database (optional)   |

Verify installations:

```bash
java -version        # Must show Java 21+
mvn -version         # Must show Maven 3.9+ with Java 21
node -v              # Must show v20+
npm -v               # Must show 10+
```

## Quick Start (Development - H2 In-Memory Database)

For local development, the backend uses H2 (zero-configuration in-memory database). No MySQL setup is needed.

### Using the Unified Launcher (recommended)

```bash
# Install frontend dependencies, start backend (port 8080) and frontend (port 5173)
node start.js
```

### Manual Start

**Terminal 1 - Backend:**
```bash
cd /path/to/Smart-Mobility-System
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

**Terminal 2 - Frontend:**
```bash
cd /path/to/Smart-Mobility-System/frontend
npm install
npm run dev
```

### Access the Application

| Service   | URL                          |
|-----------|------------------------------|
| Frontend  | http://localhost:5173        |
| Backend   | http://localhost:8080/api/v1 |
| Swagger   | http://localhost:8080/api/v1/swagger-ui.html |
| Swagger   | http://localhost:8080/api/v1/api-docs |
| H2 Console| http://localhost:8080/api/v1/h2-console |

### Test Credentials (auto-seeded in dev profile)

| Role                   | Email                        | Password    |
|------------------------|------------------------------|-------------|
| User (Utente)          | test@smartmobility.com       | password    |
| Tech Operator          | operatore.tecnico@smartmobility.com | password |
| Social Operator        | operatore.sc@smartmobility.com | password  |
| Public Admin (PA)      | pa@smartmobility.com         | password    |

## Production Setup (MySQL)

1. Create the database:

```sql
CREATE DATABASE smart_mobility CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. Set environment variables (or edit `src/main/resources/application.yml`):

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

> **Note:** In production mode (`application.yml`), JPA `ddl-auto` is set to `validate`. You must manage schema migrations manually (e.g., via Flyway, Liquibase, or SQL scripts).

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

Executes: `compile` → `test` → `package` → `verify`

## Project Structure

```
Smart-Mobility-System/
├── pom.xml                        # Maven root (backend build)
├── src/main/java/                 # Backend Java sources
├── src/main/resources/            # Backend config (application.yml)
├── src/test/java/                 # Backend unit/integration tests
├── frontend/
│   ├── package.json               # Frontend dependencies
│   ├── vite.config.ts             # Vite dev server + proxy config
│   ├── src/                       # Vue 3 + TypeScript sources
│   └── index.html                 # HTML entry point
├── docs/                          # UML diagrams and specs
├── scripts/                       # Utility scripts
├── start.js                       # Cross-platform unified launcher
├── run-backend.sh                 # Backend launcher (Linux/macOS)
├── run-frontend.sh                # Frontend launcher (Linux/macOS)
└── run_pipeline.sh                # CI pipeline
```

## Troubleshooting

| Problem                        | Solution                                    |
|--------------------------------|---------------------------------------------|
| `java: release 21 not supported`| Install JDK 21, set `JAVA_HOME`             |
| Port 8080 already in use       | Kill existing process or change port in `application.yml` |
| Port 5173 already in use       | Vite auto-selects next available port       |
| Frontend can't reach backend   | Ensure backend is running on port 8080, check Vite proxy in `vite.config.ts` |
| `mvn: command not found`       | Install Maven and add to `PATH`             |
| `npm install` fails            | Check Node.js version (20+ required)        |

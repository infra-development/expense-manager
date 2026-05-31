# Development Environment Verification

Use this guide to confirm your local machine is ready for Expense Manager backend development.

For tool versions and recommendations, see [development-environment.md](development-environment.md).  
For initial setup (`.env`, profiles, workflow), see [local-development-setup.md](local-development-setup.md).

---

## Verification Checklist

Complete each section in order. Mark items as you go.

### A. Host tools

| # | Check | Command / action | Expected |
|---|--------|------------------|----------|
| A1 | Java installed | `java -version` | Version matches `java.version` in `pom.xml` (currently **17**) |
| A2 | Maven or Wrapper | `mvn -version` or `.\mvnw.cmd -version` (Windows) / `./mvnw -version` | Maven **3.9+** |
| A3 | Docker installed | `docker --version` | Client runs without error |
| A4 | Docker Compose | `docker compose version` | Compose v2+ |
| A5 | Git installed | `git --version` | Any recent version |
| A6 | Project root | `cd` to repo; `dir docker-compose.yml` (Windows) / `ls docker-compose.yml` | File exists |

### B. Local configuration

| # | Check | Command / action | Expected |
|---|--------|------------------|----------|
| B1 | `.env` exists | `copy .env.example .env` if missing | File at project root |
| B2 | Passwords aligned | Open `.env` | `POSTGRES_PASSWORD` = `SPRING_DATASOURCE_PASSWORD` |
| B3 | JDBC URL port | Open `.env` | `SPRING_DATASOURCE_URL` uses same host/port as `POSTGRES_PORT` (default `5432`) |
| B4 | Optional local YAML | `application-local.yml` or env vars | Datasource password available at runtime (not empty) |

### C. PostgreSQL (Docker)

| # | Check | Command / action | Expected |
|---|--------|------------------|----------|
| C1 | Container running | `docker compose up -d` then `docker compose ps` | `expense-manager-postgres` **Up** |
| C2 | Health | `docker compose ps` | Status **healthy** |
| C3 | Port listening | `docker compose port postgres 5432` | Maps to `localhost:5432` (or your `POSTGRES_PORT`) |
| C4 | DB connectivity | See [Verify PostgreSQL connectivity](#verify-postgresql-connectivity) | Connection succeeds |
| C5 | Flyway table (after app run) | Query `flyway_schema_history` | At least one successful migration |

### D. Maven build

| # | Check | Command / action | Expected |
|---|--------|------------------|----------|
| D1 | Clean compile | See [Verify Maven build](#verify-maven-build) | `BUILD SUCCESS` |
| D2 | Tests (optional) | `mvn test` or `.\mvnw.cmd test` | Tests pass (when present) |
| D3 | Package (optional) | `mvn package -DskipTests` | JAR under `target/` |

### E. Spring Boot application

| # | Check | Command / action | Expected |
|---|--------|------------------|----------|
| E1 | Env loaded | Load `.env` into shell or IDE | `SPRING_DATASOURCE_*` set |
| E2 | Application starts | See [Verify Spring Boot startup](#verify-spring-boot-startup) | No datasource/Flyway failures |
| E3 | Flyway in logs | Startup log | `Successfully applied` or schema up to date |
| E4 | Swagger UI | Browser: http://localhost:8080/swagger-ui.html | Page loads |
| E5 | OpenAPI | http://localhost:8080/api-docs | JSON document returned |

### F. Ready for development

| # | Check | Expected |
|---|--------|----------|
| F1 | Daily workflow understood | PostgreSQL → load env → `spring-boot:run` |
| F2 | No secrets committed | `.env` and local overrides not in `git status` |

---

## Start PostgreSQL with Docker Compose

Run all commands from the **project root** (where `docker-compose.yml` lives).

### Step 1 — Create `.env` (first time only)

**Windows (Command Prompt):**

```cmd
copy .env.example .env
```

**Windows (PowerShell) / Linux / macOS:**

```powershell
Copy-Item .env.example .env
```

```bash
cp .env.example .env
```

Edit `.env` and set `POSTGRES_PASSWORD` and `SPRING_DATASOURCE_PASSWORD` to the **same** value.

### Step 2 — Start the database

```bash
docker compose up -d
```

Docker Compose reads `.env` from the project root for variable substitution (`POSTGRES_*`).

### Step 3 — Confirm the container is healthy

```bash
docker compose ps
```

Expected:

```text
NAME                       STATUS
expense-manager-postgres   Up (healthy)
```

### Step 4 — Inspect logs (if not healthy)

```bash
docker compose logs postgres
```

### Step 5 — Stop PostgreSQL (when finished)

```bash
docker compose down
```

Reset data (fixes password mismatch after first init):

```bash
docker compose down -v
docker compose up -d
```

---

## Verify PostgreSQL connectivity

Replace `expense_manager` / port / password with values from your `.env`.

### 1. Docker health check (no extra tools)

```bash
docker compose exec postgres pg_isready -U expense_manager -d expense_manager
```

Expected: `accepting connections`

### 2. Run SQL inside the container

```bash
docker compose exec postgres psql -U expense_manager -d expense_manager -c "SELECT 1 AS ok;"
```

Expected:

```text
 ok
----
  1
```

### 3. Check baseline migration objects (after Spring Boot has started once)

```bash
docker compose exec postgres psql -U expense_manager -d expense_manager -c "\dt"
```

Expected: includes `flyway_schema_history`.

```bash
docker compose exec postgres psql -U expense_manager -d expense_manager -c "SELECT version, description, success FROM flyway_schema_history;"
```

Expected: row for `V1` / baseline with `success = t`.

### 4. Connect from the host (optional)

If `psql` is installed locally:

**Linux / macOS:**

```bash
psql -h localhost -p 5432 -U expense_manager -d expense_manager -c "SELECT current_database(), current_user;"
```

**Windows (PowerShell)** — set password first:

```powershell
$env:PGPASSWORD = "<your-password-from-.env>"
psql -h localhost -p 5432 -U expense_manager -d expense_manager -c "SELECT current_database(), current_user;"
```

### 5. Test TCP port from the host

**Windows (PowerShell):**

```powershell
Test-NetConnection -ComputerName localhost -Port 5432
```

Expected: `TcpTestSucceeded : True`

**Linux / macOS:**

```bash
nc -zv localhost 5432
```

### 6. JDBC URL sanity check

Your `.env` should contain:

```text
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/expense_manager
```

Host must be `localhost` when the app runs on the host (not `postgres` — that hostname is only for containers on the Docker network).

---

## Verify Maven build

Use **Maven Wrapper** (`mvnw` / `mvnw.cmd`) if Maven is not on your `PATH`.

### 1. Show versions

**Windows:**

```cmd
.\mvnw.cmd -version
java -version
```

**Linux / macOS:**

```bash
./mvnw -version
java -version
```

Expected: Maven 3.9+, Java version matches `pom.xml` (`java.version`).

### 2. Download dependencies and compile

**Windows:**

```cmd
.\mvnw.cmd clean compile
```

**Linux / macOS:**

```bash
./mvnw clean compile
```

Expected last lines:

```text
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

### 3. Run tests (recommended)

```bash
.\mvnw.cmd test
```

```bash
./mvnw test
```

### 4. Package without running (optional)

```bash
.\mvnw.cmd package -DskipTests
```

Expected: `target/expense-manager-0.1.0-SNAPSHOT.jar` (version from `pom.xml`).

### 5. Common build failure signals

| Log / message | Likely cause |
|---------------|--------------|
| `release version 17 not supported` | JDK too old; install JDK 17+ |
| `mvn` is not recognized | Use `mvnw.cmd` / `./mvnw` or install Maven |
| MapStruct / Lombok errors | Enable annotation processing in IDE |

---

## Verify Spring Boot startup

### Step 1 — Start PostgreSQL

```bash
docker compose up -d
```

### Step 2 — Load environment variables

**Windows (PowerShell)** — from project root:

```powershell
Get-Content .env | ForEach-Object {
  if ($_ -match '^\s*([^#][^=]+)=(.*)$') {
    [Environment]::SetEnvironmentVariable($matches[1].Trim(), $matches[2].Trim(), 'Process')
  }
}
```

**Linux / macOS:**

```bash
set -a
source .env
set +a
```

Alternatively, configure the same variables in your IDE run configuration.

### Step 3 — Start the application

**Windows:**

```cmd
.\mvnw.cmd spring-boot:run
```

**Linux / macOS:**

```bash
./mvnw spring-boot:run
```

### Step 4 — Confirm success in logs

Look for lines similar to:

```text
HikariPool-1 - Start completed.
Successfully validated 1 migration
Successfully applied 1 migration
Started ExpenseManagerApplication in X seconds
Tomcat started on port 8080
```

Phase 1 has no business REST endpoints; an empty API document in Swagger is expected.

### Step 5 — HTTP checks

**Browser:**

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI: http://localhost:8080/api-docs

**Windows (PowerShell):**

```powershell
Invoke-WebRequest -Uri http://localhost:8080/api-docs -UseBasicParsing | Select-Object StatusCode
```

Expected: `200`

**Linux / macOS:**

```bash
curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8080/api-docs
```

Expected: `200`

### Step 6 — Stop the application

Press `Ctrl+C` in the terminal running Spring Boot.

---

## Common troubleshooting (local setup)

### Docker / PostgreSQL

| Problem | Cause | Fix |
|---------|--------|-----|
| `port is already allocated` | Another service uses 5432 | Stop other PostgreSQL or set `POSTGRES_PORT=5433` in `.env` and update `SPRING_DATASOURCE_URL` |
| Container exits immediately | Invalid env or image pull issue | `docker compose logs postgres` |
| Status not `healthy` | DB still starting or misconfigured user/db | Wait 30s; check `POSTGRES_USER` / `POSTGRES_DB` in `.env` |
| `password authentication failed` | `.env` password ≠ volume init password | `docker compose down -v` then `up -d` after fixing `.env` |
| App cannot connect; DB works in container | Wrong JDBC host | Use `localhost` in URL when app runs on host |

### Environment variables

| Problem | Cause | Fix |
|---------|--------|-----|
| `Failed to configure a DataSource` | `SPRING_DATASOURCE_PASSWORD` unset | Load `.env` or copy `application-local.yml.example` → `application-local.yml` |
| Variables ignored | Spring Boot does not load `.env` by default | Use IDE env injection or shell `source` / PowerShell snippet above |
| Password works in Docker but not app | Mismatched passwords | Align `POSTGRES_PASSWORD` and `SPRING_DATASOURCE_PASSWORD` |

### Flyway / JPA

| Problem | Cause | Fix |
|---------|--------|-----|
| Flyway migration failed | SQL error in `db/migration/` | Read stack trace; fix migration file |
| `Schema-validation: missing table` | Migrations not applied | Ensure Flyway ran; check `flyway_schema_history` |
| Hibernate `ddl-auto` errors | Schema drift | Keep `ddl-auto: validate` in `application.yml`; change schema only via Flyway |

### Maven

| Problem | Cause | Fix |
|---------|--------|-----|
| `mvn` not found | Maven not on PATH | Use `.\mvnw.cmd` or `./mvnw` |
| Wrong Java version | `JAVA_HOME` points to old JDK | Point to JDK matching `pom.xml` |
| Slow first build | Dependency download | Normal; retry with stable network |

### Spring Boot

| Problem | Cause | Fix |
|---------|--------|-----|
| Port 8080 in use | Another process on 8080 | Stop other app or set `server.port` in local config |
| Swagger 404 | Wrong path | Use `/swagger-ui.html` (see `application.yml` springdoc settings) |
| Startup slow then fails | DB not ready | Wait for `healthy` before `spring-boot:run` |

### Git / secrets

| Problem | Cause | Fix |
|---------|--------|-----|
| `.env` appears in `git status` | Should be gitignored | Do not commit; confirm `.gitignore` contains `.env` |
| Accidentally committed secrets | Credential in history | Rotate passwords; remove from history per team policy |

---

## Quick reference

| Task | Command |
|------|---------|
| Start PostgreSQL | `docker compose up -d` |
| PostgreSQL status | `docker compose ps` |
| PostgreSQL logs | `docker compose logs postgres` |
| DB shell | `docker compose exec postgres psql -U expense_manager -d expense_manager` |
| Compile | `.\mvnw.cmd clean compile` (Windows) / `./mvnw clean compile` |
| Run app | Load `.env`, then `.\mvnw.cmd spring-boot:run` |
| Swagger | http://localhost:8080/swagger-ui.html |

---

## Related documentation

* [development-environment.md](development-environment.md)
* [local-development-setup.md](local-development-setup.md)
* [technology-stack.md](technology-stack.md)

# Local Development Setup

This guide explains how to run the Expense Manager backend on your machine using Docker for PostgreSQL and Spring Boot on the host.

For required tool versions and recommendations, see [development-environment.md](development-environment.md).

For a verification checklist, connectivity commands, and expanded troubleshooting, see [development-environment-verification.md](development-environment-verification.md).

---

## Prerequisites

Install and verify the following before you begin:

| Tool | Version | Verify |
|------|---------|--------|
| Java | 21 | `java -version` |
| Maven | 3.9+ | `mvn -version` |
| Docker | Latest stable | `docker --version` |
| Docker Compose | v2+ | `docker compose version` |
| Git | Any recent | `git --version` |

Recommended (optional):

* **IDE:** IntelliJ IDEA Community Edition or Cursor (with Lombok and Spring support)
* **Database client:** DBeaver or pgAdmin
* **API testing:** Postman, Bruno, or Insomnia

---

## 1. Clone the Repository

```bash
git clone <repository-url>
cd expense-manager
```

---

## 2. Configure Environment Variables

Sensitive values must not be committed. Use the example files as templates.

### Create `.env`

```bash
# Linux / macOS
cp .env.example .env

# Windows (Command Prompt)
copy .env.example .env
```

Edit `.env` and set at least:

* `POSTGRES_PASSWORD` — password for the Docker PostgreSQL container
* `SPRING_DATASOURCE_PASSWORD` — must match `POSTGRES_PASSWORD` when connecting from the host

Keep `SPRING_DATASOURCE_URL` pointed at `localhost` and the same port as `POSTGRES_PORT` (default `5432`).

### Optional: Create `application-local.yml`

Spring Boot activates the `local` profile by default (see `application.yml`). You can override settings in a gitignored file:

```bash
# From src/main/resources/
cp application-local.yml.example application-local.yml
```

If you use `application-local.yml`, ensure datasource passwords are not committed. Prefer `${SPRING_DATASOURCE_PASSWORD}` without a default in that file, and supply the value via `.env` or your IDE run configuration.

---

## 3. Start PostgreSQL (Docker Compose)

From the project root:

```bash
docker compose up -d
```

Check status:

```bash
docker compose ps
docker compose logs postgres
```

Wait until the health check reports healthy:

```bash
docker compose ps
# STATUS should show "healthy"
```

### Connection details (defaults)

| Setting | Value |
|---------|--------|
| Host | `localhost` |
| Port | `5432` (or `POSTGRES_PORT` from `.env`) |
| Database | `expense_manager` |
| User | `expense_manager` |
| Password | Value from your `.env` |

### Stop PostgreSQL

```bash
docker compose down
```

To remove the data volume as well:

```bash
docker compose down -v
```

---

## 4. Load Environment Variables for Spring Boot

Spring Boot does not read `.env` automatically. Choose one approach:

### Option A — IDE run configuration (recommended)

**IntelliJ IDEA:** Run → Edit Configurations → Environment variables → load from `.env` (EnvFile plugin) or paste variables manually.

**Cursor / VS Code:** Add variables to `launch.json` or use a `.env` extension that injects env vars into the Java debugger.

### Option B — Shell (Linux / macOS)

```bash
set -a
source .env
set +a
mvn spring-boot:run
```

### Option C — Windows PowerShell

```powershell
Get-Content .env | ForEach-Object {
  if ($_ -match '^\s*([^#][^=]+)=(.*)$') {
    [Environment]::SetEnvironmentVariable($matches[1].Trim(), $matches[2].Trim(), 'Process')
  }
}
mvn spring-boot:run
```

### Option D — `application-local.yml`

Copy from `application-local.yml.example` and set datasource properties there. Do not commit this file.

---

## 5. Run the Application

With PostgreSQL running and environment variables set:

```bash
mvn spring-boot:run
```

On startup:

1. Spring Boot connects to PostgreSQL.
2. Flyway runs migrations from `src/main/resources/db/migration/`.
3. The API listens on port `8080` (configurable in `application.yml`).

---

## 6. Verify the Setup

| Check | URL / command |
|-------|----------------|
| Application health | App starts without datasource or Flyway errors in logs |
| Swagger UI | http://localhost:8080/swagger-ui.html |
| OpenAPI JSON | http://localhost:8080/api-docs |
| Database | Connect with DBeaver using credentials from `.env` |
| Flyway | Table `flyway_schema_history` exists in `expense_manager` |

---

## Development Workflow

Follow this order each day:

1. `docker compose up -d` — start PostgreSQL
2. Load `.env` (IDE or shell)
3. `mvn spring-boot:run` — start the application
4. Open Swagger UI and test endpoints (APIs expand in later phases)
5. `docker compose down` — stop PostgreSQL when finished (optional)

---

## Troubleshooting

See [development-environment-verification.md](development-environment-verification.md#common-troubleshooting-local-setup) for a full troubleshooting table.

---

## File Reference

| File | Purpose | Committed |
|------|---------|-----------|
| `docker-compose.yml` | Local PostgreSQL 16 | Yes |
| `.env.example` | Template for local secrets | Yes |
| `.env` | Your local secrets | **No** (gitignored) |
| `application-local.yml.example` | Template for local Spring overrides | Yes |
| `application-local.yml` | Your local Spring overrides | **No** (gitignored) |
| `application.yml` | Shared defaults, `local` profile active | Yes |

---

## Related Documentation

* [development-environment.md](development-environment.md) — tool versions and recommendations
* [development-environment-verification.md](development-environment-verification.md) — checklist and verification commands
* [technology-stack.md](technology-stack.md) — approved stack
* [project-roadmap.md](project-roadmap.md) — implementation phases

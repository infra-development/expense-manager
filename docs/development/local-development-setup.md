# Local Development Setup

## Purpose

This guide explains how to run the Expense Manager backend locally.

It assumes the required software is already installed. See `development-environment.md` for tool requirements.

---

# Prerequisites

Before continuing, ensure:

- Java is installed
- Maven is installed
- Docker is installed
- Docker Compose is installed

---

# Clone Repository

```bash
git clone <repository-url>
cd expense-manager
```

---

# Configure Environment

Create the environment file.

```bash
cp .env.example .env
```

or

```cmd
copy .env.example .env
```

Configure at least:

- POSTGRES_PASSWORD
- SPRING_DATASOURCE_PASSWORD

Passwords must match.

---

# Optional Local Configuration

If required:

```
application-local.yml
```

should be created from

```
application-local.yml.example
```

Do not commit this file.

---

# Start PostgreSQL

```bash
docker compose up -d
```

Verify:

```bash
docker compose ps
```

Container should report:

```
healthy
```

---

# Load Environment Variables

Choose one:

- IntelliJ Environment Variables
- Cursor launch configuration
- Shell

---

# Start Application

```bash
mvn spring-boot:run
```

---

# Verify Startup

Confirm:

- Application starts successfully
- Flyway migrations execute
- Swagger loads
- Database connection succeeds

---

# Shutdown

```bash
docker compose down
```

---

# Daily Development Workflow

1. Pull latest code.
2. Start PostgreSQL.
3. Load environment variables.
4. Start Spring Boot.
5. Develop.
6. Stop PostgreSQL when finished.

---

# Related Documents

- development-environment.md
- development-environment-verification.md
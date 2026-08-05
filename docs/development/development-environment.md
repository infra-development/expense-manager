# Development Environment

## Purpose

This document defines the standard development environment for the Expense Manager backend.

Following this document ensures every developer uses a consistent toolchain and development workflow.

---

# Required Software

| Tool | Version |
|------|---------|
| Java | 21 |
| Maven | 3.9+ |
| PostgreSQL | 16 (Docker) |
| Docker | Latest Stable |
| Docker Compose | v2+ |
| Git | Latest Stable |

---

# Recommended Tools

## IDE

Recommended:

- IntelliJ IDEA Community Edition
- Cursor

Recommended Plugins

- Lombok
- Spring Boot
- MapStruct (optional)

---

## Database Client

Recommended:

- DBeaver
- pgAdmin

---

## API Client

Recommended:

- Bruno
- Postman
- Insomnia

---

# Runtime

Application

- Spring Boot

Database

- PostgreSQL

Profiles

- local
- dev
- prod

---

# Build

Build Tool

- Maven

Wrapper

Preferred when available.

```
./mvnw
```

---

# Database

Development database runs using Docker Compose.

Developers should not install PostgreSQL locally unless required.

---

# Environment Variables

Sensitive configuration should be provided through:

- .env
- IDE Environment Variables

Never commit secrets.

---

# Database Migration

Schema changes are managed using Flyway.

Every database change must be delivered through a new migration.

Existing migrations must never be modified after they have been committed.

---

# API Documentation

Swagger / OpenAPI is available after application startup.

```
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON

```
http://localhost:8080/api-docs
```

---

# Development Principles

Every developer should be able to:

- Clone repository
- Start PostgreSQL
- Load environment variables
- Run Spring Boot
- Access Swagger
- Develop without additional manual configuration

---

# Daily Workflow

1. Pull latest changes.
2. Start PostgreSQL.
3. Load environment variables.
4. Start Spring Boot.
5. Verify Flyway.
6. Develop.
7. Stop PostgreSQL when finished.

---

# Related Documents

- local-development-setup.md
- development-environment-verification.md
- coding-standards.md
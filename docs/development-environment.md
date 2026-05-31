# Development Environment

## Overview

This document defines the local development environment required for Expense Manager backend development.

All developers should use compatible tool versions.

---

# Operating Systems

Supported:

- Windows 11
- Linux
- macOS

---

# Java

Version:

- Java 17

Verification:

```bash
java -version
```

---

# Maven

Version:

- Maven 3.9+

Verification:

```bash
mvn -version
```

---

# Docker

Purpose:

- Local infrastructure
- PostgreSQL
- Future containerized execution

Verification:

```bash
docker --version
docker compose version
```

---

# PostgreSQL

Purpose:

- Primary application database

Version:

- PostgreSQL 16+

Local Development:

- Run using Docker Compose

Direct local installation is not required.

---

# Database Client

Recommended:

- DBeaver

Alternative:

- pgAdmin

Purpose:

- Query execution
- Database inspection

---

# API Testing

Recommended:

- Postman

Alternative:

- Bruno
- Insomnia

Purpose:

- API testing
- Collection management

---

# IDE

Recommended:

- IntelliJ IDEA Community Edition

Alternative:

- Cursor

Required Plugins:

- Lombok
- Spring Support
- MapStruct Support

---

# Source Control

Tool:

- Git

Verification:

```bash
git --version
```

---

# Documentation

Recommended:

- Markdown

Tools:

- Cursor
- IntelliJ
- VS Code

---

# Local Infrastructure

Services:

- PostgreSQL

Managed By:

- Docker Compose

---

# Environment Variables

Local configuration should be externalized through:

- application-local.yml
- Environment Variables

Sensitive information must not be committed to source control.

Examples:

- Database Passwords
- JWT Secrets

---

# Development Workflow

1. Start Docker Compose.
2. Start PostgreSQL.
3. Run Flyway migrations.
4. Start Spring Boot application.
5. Verify Swagger UI.
6. Test APIs using Postman.

For step-by-step local setup instructions, see [local-development-setup.md](local-development-setup.md).
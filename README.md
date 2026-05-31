# Expense Manager Backend

## Overview

Expense Manager is a backend application for managing personal finances.

The system allows users to:

* Track income
* Track expenses
* Manage expense categories
* Create financial goals
* Track goal contributions
* Generate financial reports
* Plan future financial objectives

This project focuses exclusively on backend development.

Frontend applications will be developed separately and will consume the APIs exposed by this service.

---

## Architecture

Architecture Style:

* Modular Monolith

Modules:

* Identity
* Income
* Expense
* Planning
* Reporting

---

## Technology Stack

* Java 21
* Spring Boot 3.x
* Maven
* PostgreSQL
* Spring Data JPA
* Hibernate
* Flyway
* JWT Authentication
* MapStruct
* Lombok
* OpenAPI
* Swagger UI
* Docker

For complete technology decisions see:

```text
docs/technology-stack.md
```

---

## Documentation

Project documentation is located under:

```text
docs/
```

Important documents:

* vision.md
* requirements.md
* user-stories.md
* domain-model.md
* architecture.md
* database-design.md
* api-design.md
* security-design.md
* technology-stack.md
* project-roadmap.md
* decisions.md
* development-environments.md
* local-development-setup.md

---

## Local Development

Quick start:

1. Copy `.env.example` to `.env` and set your database password.
2. Start PostgreSQL: `docker compose up -d`
3. Load environment variables and run: `mvn spring-boot:run`
4. Open Swagger UI: http://localhost:8080/swagger-ui.html

See [docs/local-development-setup.md](docs/local-development-setup.md) for full instructions.

---

## Cursor Rules

Cursor-specific generation rules are located under:

```text
.cursor/rules/
```

Important rule files:

* backend-rules.md
* naming-conventions.md

All generated code must follow these rules.

---

## Development Approach

Implementation should follow the roadmap defined in:

```text
docs/project-roadmap.md
```

Modules should be implemented phase by phase.

Avoid generating the entire application at once.

---

## Current Status

Project Phase:

* Design Complete
* Implementation Not Started

Next Step:

* Phase 1 - Project Foundation

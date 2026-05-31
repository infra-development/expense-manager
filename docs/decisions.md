# Architecture and Design Decisions

This document records important project decisions and the reasoning behind them.

---

# DECISION-001

Title:

* Backend Only Project

Decision:

* This project focuses exclusively on backend development.

Reasoning:

* Frontend development will be handled by a separate team.
* Backend concerns should remain independent of presentation concerns.

Status:

* Accepted

---

# DECISION-002

Title:

* Modular Monolith Architecture

Decision:

* Use a Modular Monolith architecture.

Reasoning:

* Simpler development.
* Simpler deployment.
* Lower operational overhead.
* Easier testing.
* Future migration path to microservices.

Status:

* Accepted

---

# DECISION-003

Title:

* Java 17

Decision:

* Use Java 17.

Reasoning:

* Long-Term Support release.
* Compatible with Spring Boot 3.
* Access to modern Java language features.
* Suitable for long-term maintenance.

Status:

* Accepted

---

# DECISION-004

Title:

* Spring Boot 3

Decision:

* Use Spring Boot 3.x.

Reasoning:

* Modern Spring ecosystem.
* Strong community support.
* Good integration with Java 17.

Status:

* Accepted

---

# DECISION-005

Title:

* Maven Build System

Decision:

* Use Maven.

Reasoning:

* Widely adopted.
* Strong Spring Boot support.
* Predictable project structure.

Status:

* Accepted

---

# DECISION-006

Title:

* PostgreSQL

Decision:

* Use PostgreSQL as the primary database.

Reasoning:

* Mature relational database.
* Excellent Spring support.
* Strong performance and reliability.

Status:

* Accepted

---

# DECISION-007

Title:

* UUID Primary Keys

Decision:

* Use UUID identifiers for all entities.

Reasoning:

* Avoid predictable identifiers.
* Better support for future distributed systems.
* Consistent identifier strategy.

Status:

* Accepted

---

# DECISION-008

Title:

* Spring Data JPA with Hibernate

Decision:

* Use Spring Data JPA and Hibernate.

Reasoning:

* Fast development.
* Excellent Spring integration.
* Well suited for CRUD-heavy applications.

Status:

* Accepted

---

# DECISION-009

Title:

* Flyway Database Migrations

Decision:

* Use Flyway for schema migrations.

Reasoning:

* Simple migration management.
* Version-controlled database changes.

Status:

* Accepted

---

# DECISION-010

Title:

* JWT Authentication

Decision:

* Use JWT Access Tokens and Refresh Tokens.

Reasoning:

* Stateless API authentication.
* Industry-standard approach.
* Supports frontend and mobile clients.

Status:

* Accepted

---

# DECISION-011

Title:

* Multiple Active Sessions

Decision:

* Support multiple active user sessions.

Reasoning:

* Users commonly access applications from multiple devices.
* Revoking one session should not affect others.

Status:

* Accepted

---

# DECISION-012

Title:

* Refresh Token Persistence

Decision:

* Store refresh tokens in the database.

Reasoning:

* Supports token revocation.
* Supports logout functionality.
* Enables session management.

Status:

* Accepted

---

# DECISION-013

Title:

* Goal Contributions

Decision:

* Financial goal progress shall be calculated using Goal Contributions.

Reasoning:

* Explicit user intent.
* Accurate progress tracking.
* Easier auditing and reporting.

Status:

* Accepted

---

# DECISION-014

Title:

* Soft Deletes

Decision:

* Use soft deletes for business entities.

Reasoning:

* Prevent accidental data loss.
* Support future recovery features.
* Improve auditability.

Status:

* Accepted

---

# DECISION-015

Title:

* UTC Time Handling

Decision:

* Store timestamps in UTC using Instant.

Reasoning:

* Avoid timezone issues.
* Consistent backend behavior.
* Easier support for global clients.

Status:

* Accepted

---

# DECISION-016

Title:

* System and User Categories in Same Table

Decision:

* Store system-defined and user-defined categories in the same table.

Reasoning:

* Simpler implementation.
* Reduced complexity.
* Sufficient for MVP requirements.

Status:

* Accepted

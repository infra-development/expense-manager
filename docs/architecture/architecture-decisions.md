# Architecture Decision Records (ADR)

This document records significant architectural decisions made during the development of Expense Manager.

Each decision documents:

- The problem
- The chosen solution
- The reasoning
- The current status

---

# ADR-001

## Title

Backend Only Project

## Decision

Expense Manager focuses exclusively on backend development.

## Reasoning

- Separation of concerns
- Independent backend evolution
- REST APIs consumable by multiple clients

## Status

Accepted

---

# ADR-002

## Title

Modular Monolith Architecture

## Decision

The backend is implemented as a Modular Monolith.

## Reasoning

- Simpler deployment
- Lower operational overhead
- Easier debugging
- Clear business boundaries
- Future migration path to microservices

## Status

Accepted

---

# ADR-003

## Title

Java 17

## Decision

Use Java 17 as the runtime version.

## Reasoning

- Long Term Support
- Spring Boot compatibility
- Modern language features

## Status

Accepted

---

# ADR-004

## Title

Spring Boot

## Decision

Use Spring Boot 3.x.

## Reasoning

- Mature ecosystem
- Excellent Spring Security support
- Strong PostgreSQL integration

## Status

Accepted

---

# ADR-005

## Title

Maven

## Decision

Use Maven as the build system.

## Reasoning

- Industry standard
- Excellent Spring Boot integration

## Status

Accepted

---

# ADR-006

## Title

PostgreSQL

## Decision

Use PostgreSQL as the primary relational database.

## Reasoning

- Mature
- Reliable
- Excellent support for relational modelling

## Status

Accepted

---

# ADR-007

## Title

UUID Primary Keys

## Decision

All persistent entities use UUID identifiers.

## Reasoning

- Globally unique
- Harder to enumerate
- Future distributed architecture support

## Status

Accepted

---

# ADR-008

## Title

Spring Data JPA

## Decision

Use Spring Data JPA with Hibernate.

## Reasoning

- Fast development
- Excellent Spring integration
- Suitable for business applications

## Status

Accepted

---

# ADR-009

## Title

Flyway Database Migrations

## Decision

Database schema changes are managed using Flyway.

## Reasoning

- Version controlled schema
- Repeatable deployments
- Reliable upgrades

## Status

Accepted

---

# ADR-010

## Title

JWT Authentication

## Decision

Authentication uses JWT Access Tokens and Refresh Tokens.

## Reasoning

- Stateless authentication
- Suitable for REST APIs
- Supports web and mobile clients

## Status

Accepted

---

# ADR-011

## Title

Multiple Active Sessions

## Decision

A user may have multiple active sessions simultaneously.

## Reasoning

- Multiple devices
- Independent session management
- Better user experience

## Status

Accepted

---

# ADR-012

## Title

Persistent Refresh Tokens

## Decision

Refresh tokens are persisted in the database.

## Reasoning

- Session revocation
- Logout support
- Device management

## Status

Accepted

---

# ADR-013

## Title

Hashed Refresh Tokens

## Decision

Only SHA-256 hashes of refresh tokens are stored.

## Reasoning

If the database is compromised, refresh tokens cannot be directly reused.

This follows the same principle as password hashing by ensuring sensitive authentication artifacts are never stored in plaintext.

## Status

Accepted

---

# ADR-014

## Title

Refresh Token Rotation

## Decision

Every successful refresh request invalidates the previous refresh token and issues a new one.

## Reasoning

- Limits replay attacks
- Improves session security
- Aligns with modern authentication practices

## Status

Accepted

---

# ADR-015

## Title

Soft Deletes

## Decision

Business entities use soft deletion.

## Reasoning

- Prevent accidental data loss
- Support future recovery
- Preserve historical records

## Status

Accepted

---

# ADR-016

## Title

Shared Base Entities

## Decision

All entities inherit common behavior from shared base classes.

## BaseEntity

Provides:

- UUID identifier
- Auditing fields

## SoftDeletableEntity

Provides:

- Soft delete support
- Deleted timestamp

## Reasoning

- Eliminate duplication
- Consistent persistence model
- Centralized auditing

## Status

Accepted

---

# ADR-017

## Title

DTO Boundary

## Decision

REST controllers never expose JPA entities.

## Reasoning

- API stability
- Security
- Separation of persistence and API contracts

## Status

Accepted

---

# ADR-018

## Title

MapStruct Mapping

## Decision

Entity-to-DTO conversion is implemented using MapStruct.

## Reasoning

- Compile-time mapping
- Better performance than reflection
- Cleaner service layer

## Status

Accepted

---

# ADR-019

## Title

Java Records for DTOs

## Decision

Immutable Java Records are used for request and response DTOs whenever appropriate.

## Reasoning

- Less boilerplate
- Immutable API contracts
- Better readability

## Status

Accepted

---

# ADR-020

## Title

Global Exception Handling

## Decision

Application exceptions are translated into consistent REST responses through a centralized exception handler.

## Reasoning

- Consistent API behavior
- Cleaner controllers
- Easier maintenance

## Status

Accepted

---

# ADR-021

## Title

Layered Package Structure

## Decision

Each business module owns its own:

- controller
- service
- repository
- entity
- dto
- mapper
- exception

## Reasoning

- High cohesion
- Clear ownership
- Easier module extraction in the future

## Status

Accepted

---

# ADR-022

## Title

Family-Centric Financial Model

## Decision

Business data is organized around families instead of individual users.

## Reasoning

Families become the primary ownership boundary for:

- Accounts
- Transactions
- Budgets

Users authenticate individually but operate within one or more family contexts.

This model supports shared household finance while preserving individual authentication.

## Status

Accepted (Architecture)
Implementation in progress

---

# ADR-023

## Title

Unified Transaction Ledger

## Decision

Income, Expense, and Transfer are represented by a single Transaction entity differentiated by transaction type.

## Reasoning

- Simpler reporting
- Easier balance calculation
- More extensible ledger model
- Reduced duplication

## Status

Accepted (Architecture)
Implementation in progress
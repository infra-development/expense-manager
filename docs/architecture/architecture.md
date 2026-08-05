# Architecture

## Overview

Expense Manager is implemented as a **Modular Monolith**.

The application is deployed as a single Spring Boot service while maintaining strict boundaries between business domains. Each module owns its business logic, persistence, APIs, and domain model.

This architecture provides:

- Simpler development
- Simpler deployment
- Easier testing
- Lower operational overhead
- Clear ownership of business domains
- Future migration path to microservices if required

---

# Architecture Style

**Style**

- Modular Monolith

**Deployment Unit**

- expense-manager-service

**Database**

- Single PostgreSQL database

**Module Communication**

- Internal method calls only
- Modules communicate through service interfaces
- Modules own their data and business rules

**External Communication**

- REST APIs

---

# High-Level Architecture

The backend is organized into three layers of responsibility.

## Shared Infrastructure

Provides reusable functionality used across all modules.

Responsibilities:

- Base entities
- Auditing
- Soft delete support
- Global exception handling
- Shared utilities
- Common configuration

Current Components:

- BaseEntity
- SoftDeletableEntity
- GlobalExceptionHandler
- ErrorResponse

---

## Configuration

Provides application-wide configuration.

Responsibilities:

- Spring Security
- JWT configuration
- OpenAPI / Swagger
- JPA auditing
- Startup validation

Current Components:

- SecurityConfig
- JwtProperties
- OpenApiConfig
- JpaConfig
- JwtConfigurationVerifier

---

## Business Modules

Each business module owns its own domain model, services, repositories, controllers, DTOs, mappers, and exceptions.

---

# Modules

## Identity Module

### Responsibilities

- User registration
- Authentication
- Authorization
- Password management
- User profile management
- Refresh token lifecycle
- Session management

### Owned Domain Objects

- User
- RefreshToken

### Current Status

**Implemented**

---

## Family Module

### Responsibilities

- Family management
- Family membership
- Family roles

### Owned Domain Objects

- Family
- FamilyMember

### Current Status

**Planned**

---

## Ledger Module

### Responsibilities

- Account management
- Transaction management
- Transfer handling
- Balance calculations

### Owned Domain Objects

- Account
- Transaction

### Current Status

**Planned**

---

## Category Module

### Responsibilities

- System categories
- Family categories
- User categories

### Owned Domain Objects

- Category

### Current Status

**Database completed**
**Business implementation planned**

---

## Budget Module

### Responsibilities

- Family budgets
- Category budgets
- Budget monitoring

### Owned Domain Objects

- Budget

### Current Status

**Database completed**
**Business implementation planned**

---

## Planning Module

### Responsibilities

- Financial goals
- Goal contributions
- Goal progress tracking

### Owned Domain Objects

- FinancialGoal
- GoalContribution

### Current Status

**Future phase**

---

## Reporting Module

### Responsibilities

- Dashboard summary
- Monthly reports
- Yearly reports
- Category reports
- Budget reports

Reports are generated from existing business data and do not own persistent entities.

### Current Status

**Future phase**

---

# Module Dependency Rules

## Shared Infrastructure

May be used by every module.

Must never depend on business modules.

---

## Configuration

May depend on Shared Infrastructure.

May configure all business modules.

Must not contain business logic.

---

## Identity Module

No dependency on business modules.

---

## Family Module

Depends on Identity.

---

## Ledger Module

Depends on:

- Identity
- Family

---

## Category Module

Depends on:

- Identity
- Family

---

## Budget Module

Depends on:

- Family
- Category
- Ledger

---

## Planning Module

Depends on:

- Identity

---

## Reporting Module

May read data from:

- Ledger
- Category
- Budget
- Planning

Reporting must never modify data owned by other modules.

---

# API Style

Architecture:

- REST API

Base Path:

- /api/v1

Response Format:

- JSON

API Versioning:

- URI versioning

Examples:

- /api/v1/auth
- /api/v1/users
- /api/v1/accounts
- /api/v1/transactions
- /api/v1/categories
- /api/v1/budgets

---

# Security

Authentication

- JWT Access Token
- Refresh Token

Authorization

- User authentication
- Family membership authorization
- Resource ownership validation

Rules

- Users may access only authorized resources.
- Family resources require valid family membership.
- Business modules enforce ownership rules.

---

# Cross-Cutting Concerns

The following concerns are shared across the application:

- Authentication
- Authorization
- Auditing
- Soft Deletes
- Validation
- Exception Handling
- Logging
- OpenAPI Documentation

---

# Non-Functional Requirements

## Maintainability

Business modules must have clear ownership and well-defined boundaries.

---

## Scalability

Architecture should support future extraction into microservices if required.

---

## Testability

Business logic should be independently testable.

---

## Observability

Application logging and centralized exception handling shall be implemented.

---

# Current Implementation Status

Completed

- Project Foundation
- Common Infrastructure
- Configuration
- Identity Module
- JWT Authentication
- Refresh Token Management
- Database Foundation
- Flyway Migration Infrastructure

In Progress

- Category Module

Planned

- Family Module
- Ledger Module
- Budget Module
- Planning Module
- Reporting Module
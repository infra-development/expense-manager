# Architecture

## Overview

Expense Manager will be implemented as a Modular Monolith.

The application will be deployed as a single backend service while maintaining clear boundaries between business domains.

This approach provides:

* Simpler development
* Simpler deployment
* Easier testing
* Lower operational overhead
* Future migration path to microservices

---

# Architecture Style

Style: Modular Monolith

Deployment Unit:

expense-manager-service

Database:

Single PostgreSQL Database

Communication:

Internal method calls between modules

External Communication:

REST APIs

---

# High Level Modules

## Identity Module

Responsibilities:

* Registration
* Authentication
* Authorization
* Password Management
* User Profile Management

Owned Domain Objects:

* User
* RefreshToken

---

## Income Module

Responsibilities:

* Create Income
* Update Income
* Delete Income
* Retrieve Income History

Owned Domain Objects:

* Income

---

## Expense Module

Responsibilities:

* Create Expense
* Update Expense
* Delete Expense
* Retrieve Expense History

Owned Domain Objects:

* Expense
* Category

---

## Planning Module

Responsibilities:

* Create Financial Goals
* Update Financial Goals
* Track Goal Progress
* Manage Goal Contributions

Owned Domain Objects:

* Financial Goal
* Goal Contribution

---

## Reporting Module

Responsibilities:

* Monthly Reports
* Yearly Reports
* Category Reports
* Dashboard Summary

Owned Domain Objects:

None

Notes:

Reports are generated from existing domain data.

---

# Module Dependency Rules

Identity Module

No dependency on other modules.

Income Module

Depends on Identity Module.

Expense Module

Depends on Identity Module.

Planning Module

Depends on Identity Module.

Reporting Module

May read data from:

* Income Module
* Expense Module
* Planning Module

Reporting Module must not modify data owned by other modules.

---

# API Style

Architecture:

REST API

Response Format:

JSON

API Versioning:

/api/v1

Examples:

/api/v1/auth
/api/v1/incomes
/api/v1/expenses
/api/v1/goals
/api/v1/reports

---

# Security

Authentication:

JWT Access Token

Authorization:

User-based access control

Rules:

Users may access only their own data.

---

# Documentation

The backend shall expose:

* OpenAPI Specification
* Swagger UI

---

# Non Functional Requirements

Scalability:

Support future migration to microservices.

Maintainability:

Modules must have clear ownership.

Testability:

Business logic must be independently testable.

Observability:

Application logging shall be implemented.

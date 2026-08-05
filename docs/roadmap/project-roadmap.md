# Project Roadmap

This document defines the implementation roadmap for the Expense Manager backend.

The project follows an incremental, domain-driven implementation strategy where each phase delivers a complete business capability while maintaining a deployable application.

---

# Phase 1 — Foundation

## Goals

- Initialize Spring Boot project
- Configure Maven
- Configure PostgreSQL
- Configure Flyway
- Configure Docker
- Configure OpenAPI
- Configure Logging
- Configure Environment Profiles
- Configure Global Exception Handling
- Configure Base Infrastructure

## Deliverables

- Application starts successfully
- PostgreSQL integration
- Flyway migrations
- Docker development environment
- Swagger/OpenAPI
- Shared infrastructure
- Auditing support
- Soft delete infrastructure

## Status

✅ Completed

---

# Phase 2 — Identity Module

## Goals

- User registration
- User authentication
- JWT authentication
- Access token support
- Refresh token support
- Refresh token rotation
- Multiple active sessions
- Logout
- Logout from all devices
- User profile foundation

## Deliverables

- Authentication APIs
- Spring Security configuration
- JWT authentication filter
- Refresh token persistence
- Secure password storage
- Session management

## Status

✅ Completed

---

# Phase 3 — Family Module

## Goals

- Family creation
- Family membership
- Member invitations
- Family roles
- Family ownership

## Deliverables

- Family CRUD APIs
- Family membership management
- Role-based authorization

## Status

📋 Planned

---

# Phase 4 — Ledger Module

## Goals

- Account management
- Opening balances
- Transaction management
- Income transactions
- Expense transactions
- Transfer transactions
- Balance calculation

## Deliverables

- Account APIs
- Transaction APIs
- Transfer support
- Balance calculation services

## Status

📋 Planned

---

# Phase 5 — Category Module

## Goals

- System categories
- Family categories
- User categories
- Category validation
- Category management

## Deliverables

- Category CRUD APIs
- Scoped category support

## Status

📋 Planned

---

# Phase 6 — Budget Module

## Goals

- Family budgets
- Category budgets
- Monthly budgets
- Yearly budgets
- Budget utilization

## Deliverables

- Budget CRUD APIs
- Budget calculation services
- Budget reporting

## Status

📋 Planned

---

# Phase 7 — Planning Module

## Goals

- Financial goals
- Goal contributions
- Goal completion
- Goal progress calculation

## Deliverables

- Goal management APIs
- Goal contribution APIs
- Goal progress services

## Status

📋 Planned

---

# Phase 8 — Reporting Module

## Goals

- Dashboard summary
- Monthly reports
- Yearly reports
- Category reports
- Budget reports
- Balance reports

## Deliverables

- Reporting APIs
- Dashboard APIs
- Aggregation services

## Status

📋 Planned

---

# Phase 9 — Testing & Production Readiness

## Goals

- Unit testing
- Integration testing
- Security testing
- Performance testing
- API documentation review
- Documentation review
- Production configuration
- Monitoring improvements

## Deliverables

- High test coverage
- Production-ready backend
- Complete documentation
- Stable API

## Status

📋 Planned

---

# Current Project Progress

| Phase | Status |
|--------|--------|
| Foundation | ✅ Completed |
| Identity | ✅ Completed |
| Family | 📋 Planned |
| Ledger | 📋 Planned |
| Category | 📋 Planned |
| Budget | 📋 Planned |
| Planning | 📋 Planned |
| Reporting | 📋 Planned |
| Production Readiness | 📋 Planned |

---

# Roadmap Principles

Each implementation phase should:

- Deliver a complete business capability.
- Preserve module boundaries.
- Maintain backward compatibility where practical.
- Include database migrations.
- Include API documentation.
- Include validation.
- Include exception handling.
- Be independently testable.

Every phase should leave the application in a deployable state.
# Coding Standards

## Purpose

This document defines the coding conventions used throughout the Expense Manager backend.

The goals are:

- Consistent code style
- Readability
- Maintainability
- Predictable project structure
- Easier code reviews

These standards apply to every module in the project.

---

# General Principles

Code should be:

- Simple
- Readable
- Consistent
- Testable
- Self-documenting

Avoid clever solutions when a simpler solution exists.

---

# Package Structure

Every business module follows the same package layout.

```
identity
├── controller
├── dto
├── entity
├── exception
├── mapper
├── repository
├── scheduler
└── service
```

New modules should follow the same structure.

---

# Package Responsibilities

## controller

- REST endpoints
- Request validation
- HTTP response creation

Controllers must not contain business logic.

---

## service

Contains business logic.

Responsibilities include:

- Validation beyond Bean Validation
- Transactions
- Coordination between repositories
- Domain rules

---

## repository

Responsible only for persistence.

Repositories must not contain business logic.

---

## entity

Contains JPA entities only.

Entities should model the business domain.

---

## dto

Defines API contracts.

DTOs are used only for communication with API clients.

Entities must never be exposed directly.

---

## mapper

Responsible for converting between Entities and DTOs.

MapStruct is the preferred mapping framework.

---

## exception

Contains module-specific exceptions.

Business exceptions should be meaningful and specific.

---

# Entity Guidelines

All persistent entities shall inherit from:

```
BaseEntity
```

Entities supporting logical deletion shall inherit from:

```
SoftDeletableEntity
```

Do not duplicate auditing fields in entities.

---

# DTO Guidelines

Use immutable Java Records whenever possible.

Example:

```java
public record RegisterRequest(
        String email,
        String password
) {}
```

DTOs should contain validation annotations.

Example:

- @NotBlank
- @Email
- @Size

---

# Controller Guidelines

Controllers should:

- Be thin
- Delegate to services
- Validate input
- Return DTOs

Controllers should never:

- Access repositories directly
- Contain business rules
- Build entities manually

---

# Service Guidelines

Services should:

- Contain business logic
- Coordinate repositories
- Enforce business rules

Services should not:

- Know HTTP details
- Return JPA entities to controllers

---

# Repository Guidelines

Repositories should:

- Extend Spring Data repositories
- Expose only required query methods

Avoid unnecessary custom queries.

---

# Exception Handling

Use domain-specific exceptions.

Examples:

- UserNotFoundException
- EmailAlreadyExistsException
- InvalidCredentialsException

Do not throw generic RuntimeException.

---

# Validation

Use Jakarta Bean Validation for request validation.

Business validation belongs in the service layer.

---

# Security

Never:

- Log passwords
- Log tokens
- Log password hashes
- Log refresh token hashes

Always retrieve the authenticated user from Spring Security.

---

# Mapping

Entity ↔ DTO mapping shall use MapStruct.

Avoid manual mapping unless there is a strong reason.

---

# Naming Conventions

Entities

```
User
RefreshToken
Account
Transaction
```

Repositories

```
UserRepository
AccountRepository
```

Services

```
AuthService
RefreshTokenService
```

Controllers

```
AuthController
UserController
```

DTOs

```
LoginRequest
LoginResponse
UserResponse
```

Exceptions

```
UserNotFoundException
InvalidRefreshTokenException
```

---

# Transactions

Database transactions belong in the service layer.

Repositories must not manage transactions directly.

---

# Logging

Log:

- Important business events
- Authentication failures
- Unexpected exceptions

Do not log sensitive information.

---

# Documentation

Every public REST endpoint should be documented using OpenAPI annotations where appropriate.

Complex business rules should be documented in code.

---

# Testing

When tests are introduced:

- Unit test business logic
- Integration test repositories
- Integration test REST APIs
- Mock external dependencies

---

# Pull Request Checklist

Before merging:

- Code compiles
- Tests pass
- Flyway migration added (if schema changed)
- Documentation updated (if behavior changed)
- OpenAPI updated
- No secrets committed
- Coding standards followed
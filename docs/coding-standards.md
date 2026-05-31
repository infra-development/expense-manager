# Coding Standards

## Java Version

* Java 17

---

## Framework

* Spring Boot 3.x

---

## Architecture Style

* Modular Monolith

Modules:

* Identity
* Income
* Expense
* Planning
* Reporting

---

## Package Structure

Each module should contain:

* controller
* service
* repository
* domain
* dto
* mapper
* exception

Example:

expense/
├── controller
├── service
├── repository
├── domain
├── dto
├── mapper
└── exception

---

## Dependency Injection

Requirements:

* Constructor Injection Only

Do Not Use:

* Field Injection

---

## DTO Usage

Requirements:

* Controllers must never expose entities directly.
* Request DTOs must be used.
* Response DTOs must be used.

---

## Validation

Requirements:

* Jakarta Validation
* Validation at API boundaries

Examples:

* @NotNull
* @NotBlank
* @Positive

---

## Exception Handling

Requirements:

* Global Exception Handler
* Consistent Error Responses

---

## Logging

Requirements:

* SLF4J Logging

Do Not Log:

* Passwords
* Tokens
* Sensitive Financial Data

---

## Database Access

Requirements:

* Spring Data JPA

---

## Testing

Requirements:

* Unit Tests for Business Logic
* Integration Tests for REST APIs

---

## API Documentation

Requirements:

* OpenAPI
* Swagger UI

---

## Naming Conventions

Classes:

* PascalCase

Methods:

* camelCase

Constants:

* UPPER_SNAKE_CASE

Packages:

* lowercase

---

## Code Quality

Requirements:

* Single Responsibility Principle
* Small Methods
* Clear Naming
* Avoid Duplication
* Prefer Immutability Where Practical

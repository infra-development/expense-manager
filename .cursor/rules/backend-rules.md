# Backend Generation Rules

## General Rules

* Follow all documents in the docs directory.
* Do not invent requirements.
* Do not introduce technologies not listed in technology-stack.md.
* Prefer simplicity over unnecessary abstractions.

---

## Technology Rules

* Use Java 21.
* Use Spring Boot 3.x.
* Use Maven.
* Use PostgreSQL.
* Use Spring Data JPA.
* Use Hibernate.
* Use Flyway.
* Use MapStruct.
* Use Lombok where appropriate.

---

## Architecture Rules

* Follow the Modular Monolith architecture.
* Respect module boundaries.
* Keep business logic inside services.
* Controllers must remain thin.

Modules:

* Identity
* Income
* Expense
* Planning
* Reporting

---

## Controller Rules

* Controllers expose REST APIs only.
* Controllers must not contain business logic.
* Controllers must use DTOs.
* Controllers must never expose JPA entities.

---

## Service Rules

* Business logic belongs in services.
* Services must be focused on a single responsibility.
* Services should be easy to unit test.

---

## Repository Rules

* Use Spring Data JPA repositories.
* Repositories should contain persistence logic only.
* Business logic must not be placed in repositories.

---

## Entity Rules

* Use UUID primary keys.
* Use auditing fields:

  * created_at
  * updated_at
* Use soft delete where defined.
* Entities should represent domain concepts only.

---

## DTO Rules

* Separate request DTOs and response DTOs.
* Validate request DTOs using Jakarta Validation.
* Never expose internal entity structure directly.

---

## Mapping Rules

* Use MapStruct for DTO mapping.
* Avoid manual mapping when practical.

---

## Security Rules

* All protected APIs require authentication.
* Enforce resource ownership checks.
* Never expose passwords.
* Never expose password hashes.
* Never expose JWT tokens except through authentication APIs.

---

## Exception Handling Rules

* Use global exception handling.
* Follow error-handling.md.
* Return consistent error responses.

---

## Database Rules

* Use Flyway migrations.
* Never modify schema manually.
* Follow database-design.md.

---

## Logging Rules

* Use SLF4J.
* Do not log passwords.
* Do not log tokens.
* Do not log sensitive financial information.

---

## Testing Rules

* Generate unit tests for business logic.
* Generate integration tests for REST APIs.
* Prefer meaningful tests over excessive tests.

---

## Code Quality Rules

* Constructor injection only.
* Avoid field injection.
* Use clear method names.
* Keep methods small.
* Follow Single Responsibility Principle.
* Prefer readability over cleverness.

---

## Documentation Rules

* Generate OpenAPI annotations where appropriate.
* Keep code self-explanatory.
* Add comments only when they provide real value.

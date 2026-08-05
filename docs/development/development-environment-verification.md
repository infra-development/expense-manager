# Development Environment Verification

## Purpose

This guide verifies that the local development environment has been configured correctly.

Run these checks after initial setup or whenever development issues occur.

---

# Host Verification

Verify:

- Java
- Maven
- Docker
- Docker Compose
- Git

Expected versions should match `development-environment.md`.

---

# Environment Verification

Confirm:

- .env exists
- Passwords match
- JDBC URL is correct
- application-local.yml is configured if used

---

# PostgreSQL Verification

Verify:

- Docker container is running
- Container is healthy
- Port mapping is correct
- Database accepts connections
- Flyway schema history exists

---

# Maven Verification

Verify:

```bash
mvn clean compile
```

Expected:

```
BUILD SUCCESS
```

Optional:

```bash
mvn test
```

---

# Spring Boot Verification

Verify:

- Application starts
- Flyway completes
- No datasource errors
- No security initialization errors

---

# API Verification

Verify:

Swagger

```
http://localhost:8080/swagger-ui.html
```

OpenAPI

```
http://localhost:8080/api-docs
```

---

# Database Verification

Verify:

- Tables exist
- Flyway history exists
- Database user can connect

---

# Troubleshooting

If verification fails:

1. Check Docker.
2. Check PostgreSQL logs.
3. Check environment variables.
4. Check Flyway.
5. Check application logs.

---

# Verification Complete

Development environment is considered ready when:

- All tools installed
- PostgreSQL healthy
- Flyway successful
- Spring Boot running
- Swagger accessible
- OpenAPI accessible

---

# Related Documents

- development-environment.md
- local-development-setup.md
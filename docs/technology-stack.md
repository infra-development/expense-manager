# Technology Stack

## Overview

This document defines the approved technology stack for the Expense Manager backend.

All implementation decisions should follow this document unless explicitly updated.

---

# Runtime

Language:

* Java 17

Framework:

* Spring Boot 3.x

---

# Build Tool

Build System:

* Maven

---

# Database

Database:

* PostgreSQL

Primary Key Strategy:

* UUID

Time Zone Strategy:

* UTC

Time Representation:

* Instant

Rules:

* All timestamps shall be stored in UTC.
* Instant shall be used for timestamp fields.
* Timezone conversions shall be handled by clients when required.

---

# Persistence Layer

ORM Framework:

* Hibernate

Data Access Framework:

* Spring Data JPA

Migration Tool:

* Flyway

Rules:

* All database access shall use Spring Data JPA repositories.
* Hibernate shall be the JPA implementation.
* Database schema changes shall be managed through Flyway migrations.

---

# Security

Authentication:

* JWT Access Token
* JWT Refresh Token

Password Hashing:

* BCrypt

Authorization Model:

* User-Owned Resources

---

# Object Mapping

Mapping Framework:

* MapStruct

Rules:

* Entities shall not be exposed directly through APIs.
* DTOs shall be mapped using MapStruct.

---

# Boilerplate Reduction

Library:

* Lombok

Usage:

* Constructors
* Getters
* Setters
* Builders

---

# API Documentation

Documentation Standard:

* OpenAPI

Tools:

* springdoc-openapi
* Swagger UI

---

# Testing

Unit Testing:

* JUnit 5

Mocking:

* Mockito

Integration Testing:

* Spring Boot Test

---

# Logging

Logging API:

* SLF4J

Logging Implementation:

* Logback

Rules:

* Sensitive data must not be logged.
* Passwords must never be logged.
* Tokens must never be logged.

---

# Containerization

Container Technology:

* Docker

---

# Architecture

Architecture Style:

* Modular Monolith

Modules:

* Identity
* Income
* Expense
* Planning
* Reporting

Communication:

* Internal module communication through application services.

---

# Excluded Technologies

The following technologies are not part of MVP:

* Kafka
* RabbitMQ
* Redis
* Elasticsearch
* GraphQL
* gRPC
* Microservices

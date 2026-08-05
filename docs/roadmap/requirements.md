# Expense Manager Requirements

Version: 2.0

Status: Active

This document defines the functional and non-functional requirements for the Expense Manager backend.

The project focuses exclusively on backend development and provides REST APIs for managing personal and family finances.

---

# Project Goals

The Expense Manager backend shall provide:

- Secure user authentication
- Family-based financial management
- Multi-account ledger management
- Transaction recording
- Budget management
- Financial reporting
- Extensible modular architecture

---

# Functional Requirements

## Identity Management

The system shall allow users to:

- Register using email and password
- Log in
- Refresh authentication sessions
- Log out from the current device
- Log out from all devices
- View profile
- Update profile
- Change password

The system shall:

- Encrypt passwords
- Support multiple active sessions
- Store refresh tokens securely
- Rotate refresh tokens
- Support token revocation

---

## Family Management

The system shall allow users to:

- Create families
- Join families
- Leave families
- Invite members
- Manage family members

Each family shall contain one or more users.

A user may belong to multiple families.

Each membership has an associated role.

Supported roles:

- Family Owner
- Family Member

---

## Account Management

The system shall allow family members to manage financial accounts.

Supported account types include:

- Cash
- Bank
- Credit Card
- Loan
- Investment

Each account shall contain:

- Name
- Account Type
- Currency
- Opening Balance

---

## Transaction Management

The system shall record all financial activity using a unified transaction model.

Supported transaction types:

- Income
- Expense
- Transfer

Each transaction shall contain:

- Amount
- Transaction Date
- Account
- Category (when applicable)
- Description
- Notes

Transfer transactions shall support:

- Source account
- Destination account

---

## Category Management

The system shall support three category scopes.

### System Categories

Available to every family.

Cannot be modified.

Cannot be deleted.

---

### Family Categories

Visible only within a family.

Managed by authorized family members.

---

### User Categories

Visible only to their owner.

Users may create, modify and delete them.

---

## Budget Management

Families shall be able to create budgets.

Supported budget periods:

- Monthly
- Yearly

Budgets may be:

- Overall family budgets
- Category-specific budgets

The system shall calculate budget utilization automatically.

---

## Reporting

The system shall provide:

- Dashboard summary
- Monthly reports
- Yearly reports
- Category reports
- Budget reports

Reports shall be generated from transaction data.

Reports shall not be permanently stored.

---

## Financial Planning

Financial goals are planned for a future release.

The planning module will support:

- Financial goals
- Goal contributions
- Goal progress

---

# Security Requirements

The system shall provide:

- JWT authentication
- Refresh token support
- Password encryption
- Secure session management
- Resource ownership validation
- Family membership authorization
- Soft delete protection

Sensitive information shall never be exposed through APIs.

---

# Non-Functional Requirements

The backend shall provide:

- REST APIs
- OpenAPI documentation
- Swagger UI
- Database migrations using Flyway
- UTC timestamp handling
- Structured logging
- Consistent error responses

---

# Out of Scope

The following features are not part of the current project scope:

- Investment portfolio tracking
- Tax calculations
- Bank integrations
- UPI integrations
- AI recommendations
- Notifications
- Mobile application
- Web frontend

---

# Backend Responsibilities

The backend team is responsible for:

- Business logic
- Authentication
- Authorization
- Database design
- Domain model
- REST APIs
- Data validation
- Reporting logic
- Budget calculations
- API documentation

---

# Frontend Responsibilities

Frontend implementation is outside the scope of this project.

Frontend teams are responsible for:

- User interfaces
- Mobile applications
- Web applications
- User experience
- API integration

---

# Success Criteria

The backend is considered functionally complete when it provides:

- Authentication APIs
- Family management APIs
- Account management APIs
- Transaction management APIs
- Category management APIs
- Budget management APIs
- Reporting APIs

with complete documentation, automated database migrations, and comprehensive testing.
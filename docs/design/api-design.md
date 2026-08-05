# API Design

## Overview

Expense Manager exposes a versioned REST API.

### Architecture Style

- REST API

### Base Path

```
/api/v1
```

### Data Format

- JSON

### Authentication

- JWT Access Token
- Refresh Token

### Authorization

- Resource ownership validation
- Family membership validation

---

# Authentication APIs

## Register

```
POST /api/v1/auth/register
```

Purpose

- Register a new user

---

## Login

```
POST /api/v1/auth/login
```

Purpose

- Authenticate user
- Return Access Token and Refresh Token

---

## Refresh Token

```
POST /api/v1/auth/refresh
```

Purpose

- Rotate refresh token
- Generate new access token

---

## Logout

```
POST /api/v1/auth/logout
```

Purpose

- Revoke current refresh token

---

## Logout All Devices

```
POST /api/v1/auth/logout-all
```

Purpose

- Revoke every active refresh token for the user

---

# User APIs

## Get Current User

```
GET /api/v1/users/me
```

Purpose

- Retrieve authenticated user's profile

---

## Update Profile

```
PUT /api/v1/users/me
```

Purpose

- Update profile information

---

## Change Password

```
PUT /api/v1/users/me/password
```

Purpose

- Change account password

---

# Family APIs

## Create Family

```
POST /api/v1/families
```

Purpose

- Create a new family

---

## Get Families

```
GET /api/v1/families
```

Purpose

- Retrieve all families the user belongs to

---

## Get Family

```
GET /api/v1/families/{familyId}
```

Purpose

- Retrieve family information

---

## Update Family

```
PUT /api/v1/families/{familyId}
```

Purpose

- Update family details

---

## Delete Family

```
DELETE /api/v1/families/{familyId}
```

Purpose

- Archive a family

---

# Family Member APIs

## Invite Member

```
POST /api/v1/families/{familyId}/members
```

Purpose

- Invite a user to join the family

---

## List Members

```
GET /api/v1/families/{familyId}/members
```

Purpose

- Retrieve family members

---

## Update Member Role

```
PUT /api/v1/families/{familyId}/members/{memberId}
```

Purpose

- Change member role

---

## Remove Member

```
DELETE /api/v1/families/{familyId}/members/{memberId}
```

Purpose

- Remove member from family

---

# Account APIs

## Create Account

```
POST /api/v1/accounts
```

Purpose

- Create financial account

---

## Get Account

```
GET /api/v1/accounts/{accountId}
```

Purpose

- Retrieve account

---

## List Accounts

```
GET /api/v1/accounts
```

Purpose

- Retrieve family accounts

---

## Update Account

```
PUT /api/v1/accounts/{accountId}
```

Purpose

- Update account information

---

## Delete Account

```
DELETE /api/v1/accounts/{accountId}
```

Purpose

- Soft delete account

---

# Transaction APIs

## Create Transaction

```
POST /api/v1/transactions
```

Purpose

- Record income
- Record expense
- Record transfer

---

## Get Transaction

```
GET /api/v1/transactions/{transactionId}
```

Purpose

- Retrieve transaction details

---

## List Transactions

```
GET /api/v1/transactions
```

Supported Filters

- Date range
- Account
- Category
- Transaction type

---

## Update Transaction

```
PUT /api/v1/transactions/{transactionId}
```

Purpose

- Update transaction

---

## Delete Transaction

```
DELETE /api/v1/transactions/{transactionId}
```

Purpose

- Soft delete transaction

---

# Category APIs

## Create Category

```
POST /api/v1/categories
```

Purpose

- Create user or family category

---

## List Categories

```
GET /api/v1/categories
```

Purpose

- Retrieve available categories

Returns

- System categories
- Family categories
- User categories

---

## Update Category

```
PUT /api/v1/categories/{categoryId}
```

Purpose

- Update category

---

## Delete Category

```
DELETE /api/v1/categories/{categoryId}
```

Purpose

- Soft delete category

Business Rules

- System categories cannot be modified.
- System categories cannot be deleted.

---

# Budget APIs

## Create Budget

```
POST /api/v1/budgets
```

Purpose

- Create budget

---

## Get Budget

```
GET /api/v1/budgets/{budgetId}
```

Purpose

- Retrieve budget

---

## List Budgets

```
GET /api/v1/budgets
```

Purpose

- Retrieve family budgets

---

## Update Budget

```
PUT /api/v1/budgets/{budgetId}
```

Purpose

- Update budget

---

## Delete Budget

```
DELETE /api/v1/budgets/{budgetId}
```

Purpose

- Soft delete budget

---

# Reporting APIs

## Dashboard

```
GET /api/v1/dashboard
```

Purpose

- Retrieve dashboard summary

---

## Monthly Report

```
GET /api/v1/reports/monthly
```

Purpose

- Monthly financial report

---

## Yearly Report

```
GET /api/v1/reports/yearly
```

Purpose

- Yearly financial report

---

## Category Report

```
GET /api/v1/reports/categories
```

Purpose

- Category spending analysis

---

## Budget Report

```
GET /api/v1/reports/budgets
```

Purpose

- Budget utilization report

---

# Common API Conventions

## Request Format

- JSON

## Response Format

- JSON

## Validation

- Jakarta Bean Validation

## Authentication

Protected endpoints require

```
Authorization: Bearer <access-token>
```

## Error Responses

All errors follow the standard error response format defined in
`error-handling.md`.

---

# Versioning

API versioning uses URI versioning.

Example

```
/api/v1/...
```

Future breaking changes shall use

```
/api/v2/...
```
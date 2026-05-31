# Error Handling

## Overview

All API errors shall follow a consistent response format.

The purpose is to provide:

* Predictable API behavior
* Easier frontend integration
* Better debugging
* Better observability

---

# Standard Error Response

```json
{
  "timestamp": "2026-06-01T10:30:00Z",
  "status": 400,
  "errorCode": "VALIDATION_ERROR",
  "message": "Validation failed",
  "path": "/api/v1/expenses"
}
```

---

# Error Fields

## timestamp

Time when the error occurred.

## status

HTTP status code.

Examples:

* 400
* 401
* 403
* 404
* 409
* 500

## errorCode

Machine-readable error identifier.

## message

Human-readable error description.

## path

API endpoint where the error occurred.

---

# Validation Errors

HTTP Status:

* 400 Bad Request

Example:

```json
{
  "timestamp": "2026-06-01T10:30:00Z",
  "status": 400,
  "errorCode": "VALIDATION_ERROR",
  "message": "Amount must be greater than zero",
  "path": "/api/v1/expenses"
}
```

---

# Authentication Errors

HTTP Status:

* 401 Unauthorized

Error Codes:

* INVALID_CREDENTIALS
* TOKEN_EXPIRED
* TOKEN_INVALID

---

# Authorization Errors

HTTP Status:

* 403 Forbidden

Error Codes:

* ACCESS_DENIED

Examples:

* User attempts to access another user's data.

---

# Resource Not Found

HTTP Status:

* 404 Not Found

Error Codes:

* USER_NOT_FOUND
* EXPENSE_NOT_FOUND
* INCOME_NOT_FOUND
* CATEGORY_NOT_FOUND
* GOAL_NOT_FOUND

---

# Conflict Errors

HTTP Status:

* 409 Conflict

Error Codes:

* EMAIL_ALREADY_EXISTS
* CATEGORY_ALREADY_EXISTS

---

# Internal Server Errors

HTTP Status:

* 500 Internal Server Error

Error Codes:

* INTERNAL_SERVER_ERROR

Notes:

* Internal exception details must not be exposed to clients.

---

# Logging Requirements

The system shall log:

* Request failures
* Authentication failures
* Unexpected exceptions

Sensitive information must never be logged.

Examples:

* Passwords
* Tokens
* Personal financial data

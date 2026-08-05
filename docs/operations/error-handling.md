# Error Handling

## Overview

All REST APIs shall return errors using a consistent response structure.

The objectives are:

- Predictable API behavior
- Easier frontend integration
- Consistent client-side error handling
- Better observability
- Easier troubleshooting

---

# Standard Error Response

Every error response shall contain:

```json
{
  "timestamp": "2026-06-01T10:30:00Z",
  "status": 400,
  "errorCode": "VALIDATION_ERROR",
  "message": "Validation failed",
  "path": "/api/v1/categories"
}
```

---

# Response Fields

| Field | Description |
|--------|-------------|
| timestamp | UTC time when the error occurred |
| status | HTTP status code |
| errorCode | Machine-readable application error |
| message | Human-readable description |
| path | Request URI |

---

# Validation Errors

HTTP Status

```
400 Bad Request
```

Examples

- Required field missing
- Invalid email
- Invalid UUID
- Amount less than zero
- Constraint violations

Error Code

```
VALIDATION_ERROR
```

---

# Authentication Errors

HTTP Status

```
401 Unauthorized
```

Possible Error Codes

```
INVALID_CREDENTIALS
TOKEN_INVALID
TOKEN_EXPIRED
REFRESH_TOKEN_INVALID
REFRESH_TOKEN_REVOKED
```

---

# Authorization Errors

HTTP Status

```
403 Forbidden
```

Possible Error Codes

```
ACCESS_DENIED
FAMILY_ACCESS_DENIED
RESOURCE_ACCESS_DENIED
```

These errors occur when:

- User is not permitted to access the resource.
- User is not a member of the requested family.
- User attempts to modify another user's data.

---

# Resource Not Found

HTTP Status

```
404 Not Found
```

Examples

```
USER_NOT_FOUND
FAMILY_NOT_FOUND
ACCOUNT_NOT_FOUND
TRANSACTION_NOT_FOUND
CATEGORY_NOT_FOUND
BUDGET_NOT_FOUND
```

---

# Conflict Errors

HTTP Status

```
409 Conflict
```

Examples

```
EMAIL_ALREADY_EXISTS
CATEGORY_ALREADY_EXISTS
FAMILY_ALREADY_EXISTS
```

---

# Business Rule Violations

HTTP Status

```
422 Unprocessable Entity
```

Examples

```
INSUFFICIENT_BALANCE
INVALID_TRANSFER
INVALID_CATEGORY_SCOPE
INVALID_FAMILY_ROLE
BUDGET_ALREADY_EXISTS
```

Use this status when:

- Request syntax is valid.
- Resource exists.
- Business rules prevent execution.

---

# Internal Server Errors

HTTP Status

```
500 Internal Server Error
```

Error Code

```
INTERNAL_SERVER_ERROR
```

Clients must never receive internal exception details.

---

# Logging

The application shall log:

- Validation failures
- Authentication failures
- Authorization failures
- Unexpected exceptions

The application shall never log:

- Passwords
- Password hashes
- Access Tokens
- Refresh Tokens
- Refresh Token hashes
- Sensitive financial information

---

# Exception Handling

Application exceptions are translated into REST responses by a centralized exception handler.

Controllers should never construct error responses manually.

Business modules should throw domain-specific exceptions.

The Global Exception Handler is responsible for converting exceptions into standard API responses.

---

# Error Response Principles

Every error response shall:

- Use an appropriate HTTP status code.
- Contain a machine-readable error code.
- Contain a user-friendly message.
- Exclude implementation details.
- Exclude stack traces.
- Exclude sensitive information.

The response format shall remain consistent across all modules.
# Security Design

## Overview

This document defines the security architecture, authentication model, authorization model, and security principles of the Expense Manager backend.

Its objectives are to:

- Protect user accounts
- Protect financial data
- Prevent unauthorized access
- Provide secure session management
- Protect sensitive information
- Support secure REST API communication

This document serves as the authoritative security specification for the backend.

---

# Security Objectives

The backend is designed to satisfy the following security objectives.

## Authentication

- Authenticate every user before allowing access to protected resources.
- Support secure stateless authentication.
- Support multiple concurrent user sessions.

---

## Authorization

- Ensure users access only resources they are authorized to access.
- Enforce ownership rules.
- Enforce family membership rules.
- Prevent privilege escalation.

---

## Data Protection

The system shall protect:

- User credentials
- Authentication tokens
- Personal information
- Financial information

Sensitive information must never be exposed through APIs or application logs.

---

## Session Security

The backend shall:

- Support multiple active sessions.
- Allow session revocation.
- Rotate Refresh Tokens.
- Automatically remove expired sessions.

---

## API Security

All protected APIs shall require valid authentication.

Public APIs shall be explicitly configured.

The default security posture is:

> Deny by default.

---

# Security Architecture

Expense Manager uses stateless authentication based on JSON Web Tokens (JWT).

Authentication consists of the following components:

- Access Token
- Refresh Token
- Spring Security
- JWT Authentication Filter
- Security Context

```
                +--------------------+
                |      Client        |
                +--------------------+
                          |
                    Login Request
                          |
                          v
                 +------------------+
                 | Auth Controller  |
                 +------------------+
                          |
                          v
                   Authentication
                          |
                          v
               Generate JWT Tokens
                          |
        +-----------------+----------------+
        |                                  |
        v                                  v
 Access Token                    Refresh Token
 (returned)                      (returned)
                                    |
                                    v
                        SHA-256 Hash Stored
                              in Database
```

The backend never creates HTTP sessions.

Every request is independently authenticated.

---

# Authentication Lifecycle

The complete authentication lifecycle is shown below.

```
Register
      │
      ▼
Create User
      │
      ▼
Login
      │
      ▼
Validate Credentials
      │
      ▼
Generate Access Token
      │
      ▼
Generate Refresh Token
      │
      ▼
Store SHA-256 Hash
      │
      ▼
Return Tokens
      │
      ▼
Protected Requests
      │
      ▼
JWT Authentication Filter
      │
      ▼
SecurityContext
      │
      ▼
Access Token Expires
      │
      ▼
Refresh Request
      │
      ▼
Validate Refresh Token
      │
      ▼
Rotate Refresh Token
      │
      ▼
Generate New Tokens
      │
      ▼
Continue Session
      │
      ▼
Logout
      │
      ▼
Revoke Refresh Token
```

The authentication lifecycle is completely stateless except for Refresh Token persistence.

---

# Authentication Components

The authentication system consists of the following logical components.

| Component | Responsibility |
|------------|----------------|
| Auth Controller | Authentication APIs |
| Auth Service | Authentication business logic |
| JWT Service | Token generation and validation |
| Refresh Token Service | Session management |
| JWT Authentication Filter | Request authentication |
| Spring Security | Security configuration |
| Security Context | Authenticated user context |

Each component has a single responsibility.

Business modules never authenticate requests directly.

---

# Access Token

## Purpose

Access Tokens authenticate requests made to protected REST APIs.

---

## Characteristics

- JWT
- Stateless
- Short-lived
- Signed
- Not stored in the database

---

## Transport

Every protected request shall include:

```http
Authorization: Bearer <access-token>
```

---

## Contents

An Access Token contains:

- User Identifier (UUID)

No business information is stored inside the token.

---

## Lifetime

Access Tokens should remain short-lived.

Current default:

```
15 Minutes
```

Expiration is configurable through application configuration.

---

## Validation

Every Access Token must satisfy all of the following:

- Valid signature
- Not expired
- Correct token type
- Valid subject
- User exists

Invalid tokens are rejected before reaching business logic.

---

# Refresh Token

## Purpose

Refresh Tokens allow users to obtain new Access Tokens without re-authenticating.

---

## Characteristics

- JWT
- Long-lived
- Persisted securely
- Independently revocable
- Device specific

---

## Lifetime

Refresh Tokens are configurable.

Current implementation:

```
The expiration period is configurable through application configuration.
```

The expiration period is intentionally longer than Access Tokens.

---

## Device Sessions

Each device receives its own Refresh Token.

Examples:

- Mobile Phone
- Laptop
- Desktop
- Tablet

Revoking one session must not revoke others.

---

## Storage

Refresh Tokens are **never stored in plaintext**.

Only the SHA-256 hash of the Refresh Token is stored in the database.

This protects user sessions if database contents are compromised.

# Refresh Token Rotation

## Overview

Expense Manager uses **Refresh Token Rotation** to improve session security.

Every successful refresh request invalidates the existing Refresh Token and issues a new Access Token and Refresh Token pair.

The previous Refresh Token becomes permanently unusable.

---

## Rotation Process

The refresh process consists of the following steps.

1. Receive Refresh Token.
2. Validate JWT signature.
3. Verify expiration.
4. Calculate SHA-256 hash.
5. Locate matching Refresh Token record.
6. Verify token has not been revoked.
7. Revoke existing Refresh Token.
8. Generate new Refresh Token.
9. Persist SHA-256 hash of the new Refresh Token.
10. Generate new Access Token.
11. Return both new tokens.

```
Client
   │
   ▼
Refresh Token
   │
   ▼
Validate JWT
   │
   ▼
Find Token Hash
   │
   ▼
Token Active?
   │
   ├── No ──► Reject Request
   │
   ▼
Revoke Old Token
   │
   ▼
Generate New Refresh Token
   │
   ▼
Store New Hash
   │
   ▼
Generate New Access Token
   │
   ▼
Return New Tokens
```

---

## Benefits

Refresh Token Rotation provides:

- Replay attack protection
- Session continuity
- Immediate invalidation of old Refresh Tokens
- Better overall session security

---

# Session Management

Expense Manager supports multiple concurrent user sessions.

Each session represents one authenticated device.

Examples:

- Mobile Phone
- Laptop
- Desktop
- Tablet

Each session owns:

- One Refresh Token
- One expiration time
- One revocation state

---

## Logout

Logout affects only the current authenticated session.

The backend:

1. Identifies the current Refresh Token.
2. Revokes it.
3. Leaves every other active session unchanged.

---

## Logout From All Devices

Users may terminate every active session.

The backend:

- Finds every active Refresh Token for the user.
- Marks each token as revoked.

Subsequent refresh attempts using any revoked token are rejected.

---

# Password Security

Passwords are stored using BCrypt.

Passwords are never:

- Stored in plaintext
- Returned by APIs
- Logged
- Included inside JWTs

---

## Password Requirements

Passwords should satisfy the application's validation rules.

Examples include:

- Minimum length
- Maximum length
- Required complexity (if enabled)

Validation rules belong to the authentication module.

---

# Authorization

Authentication identifies the user.

Authorization determines what the user is permitted to do.

Expense Manager performs authorization after successful authentication.

---

## Current Authorization Model

Current implementation is based on resource ownership.

Users may:

- View their own resources.
- Update their own resources.
- Delete their own resources.

Users may not access resources owned by another user.

---

## Future Authorization Model

Future modules introduce family-based authorization.

Every request operating on family resources shall verify:

- Family membership
- Membership status
- Assigned role

Authorization decisions shall be enforced in the service layer.

---

# Category Authorization

Categories support multiple visibility scopes.

## System Categories

Properties:

- Read-only
- Visible to every authenticated user

Users may:

- View

Users may not:

- Modify
- Delete

---

## Family Categories

Properties:

- Shared within one family

Users may:

- View
- Create
- Modify (subject to family role)
- Delete (subject to family role)

---

## User Categories

Properties:

- Private

Users may:

- Create
- View
- Update
- Delete

Ownership validation is always required.

---

# Token Validation

## Access Token Validation

Every Access Token shall satisfy:

- Valid signature
- Supported algorithm
- Correct issuer (if configured)
- Correct token type
- Not expired
- Valid user identifier

Failure of any validation results in authentication failure.

---

## Refresh Token Validation

Refresh Tokens require additional validation.

The backend verifies:

- Valid JWT
- Token hash exists
- Token not revoked
- Token not expired
- Associated user exists

Only then may new tokens be generated.

---

# JWT Authentication Filter

Every protected HTTP request passes through the JWT Authentication Filter.

Responsibilities:

1. Read Authorization header.
2. Extract Access Token.
3. Validate token.
4. Load authenticated user.
5. Create Authentication object.
6. Populate Spring Security Context.
7. Continue request processing.

If authentication fails:

- Security Context remains empty.
- Protected endpoints return Unauthorized.

Controllers never authenticate requests manually.

---

# Security Maintenance

Expired Refresh Tokens should not remain indefinitely in the database.

The backend performs cleanup:

- During application startup.
- On a scheduled interval.

Cleanup removes expired Refresh Token records that are no longer usable.

---

# Transport Security

Production deployments shall use HTTPS.

Authentication tokens must never be transmitted over unencrypted HTTP connections.

TLS termination should occur before requests reach the application.

---

# Sensitive Data

Sensitive information includes:

- Passwords
- Password hashes
- Access Tokens
- Refresh Tokens
- Refresh Token hashes
- Personal financial information

Sensitive information must never:

- Appear in API responses
- Be written to logs
- Be exposed through exceptions
- Be included in debugging output

---

# Logging Requirements

The application shall log:

- Authentication failures
- Authorization failures
- Refresh Token revocations
- Unexpected security exceptions

The application shall not log:

- Passwords
- JWTs
- Refresh Tokens
- Refresh Token hashes
- Financial data

Log messages should provide sufficient operational information without exposing sensitive data.

---

# Security Principles

Expense Manager follows these security principles.

## Least Privilege

Users receive only the permissions required to perform authorized actions.

---

## Secure by Default

Protected endpoints require authentication unless explicitly configured as public.

---

## Defense in Depth

Security is enforced at multiple layers:

- HTTP Security
- JWT Validation
- Authentication Filter
- Authorization
- Business Rules
- Database Constraints

---

## Stateless Authentication

Authentication information is carried by Access Tokens.

The server does not maintain HTTP sessions.

---

## Resource Ownership

Users may only manipulate resources they own or are authorized to access.

---

## Family Authorization

Family resources require valid family membership and appropriate role.

---

## Secure Secret Management

Application secrets shall be provided through secure configuration mechanisms.

Secrets must never be hardcoded or committed to source control.

---

# Summary

Expense Manager implements a layered security architecture based on:

- Stateless JWT authentication
- Refresh Token rotation
- Secure Refresh Token hashing
- BCrypt password hashing
- Centralized authentication
- Resource ownership authorization
- Family-based authorization (future modules)
- Secure session management

This architecture provides a secure foundation for all current and future business modules.
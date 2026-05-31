# Security Design

## Overview

This document defines the security requirements for the Expense Manager backend.

Goals:

* Protect user accounts
* Protect financial data
* Prevent unauthorized access
* Support secure API communication

---

# Authentication

Authentication Type:

* JWT Authentication

Components:

* Access Token
* Refresh Token

---

# Access Token

Purpose:

* Authenticate API requests

Characteristics:

* Short-lived
* Sent with every protected request

Authorization Header:

Bearer <token>

Recommended Expiration:

* 15 Minutes

---

# Refresh Token

Purpose:

* Obtain new access tokens

Characteristics:

* Long-lived
* Stored securely

Recommended Expiration:

* 30 Days

Session Model:

* Multiple active sessions are supported.

Examples:

* Phone
* Laptop
* Tablet

Each device receives its own refresh token.

Revoking one refresh token must not affect other active sessions.


---

# Registration Security

Requirements:

* Email must be unique
* Password must never be stored in plain text

---

# Password Storage

Algorithm:

* BCrypt

Requirements:

* Store only password hash
* Never store plain-text passwords
* Never log passwords

---

# Authorization

Authorization Model:

* User-Owned Resources

Rules:

* Users may access only their own data.
* Users may update only their own data.
* Users may delete only their own data.

Protected Resources:

* Income
* Expense
* Category
* Financial Goal
* Goal Contribution

---

# Category Authorization

Rules:

* Users may create categories.
* Users may update their own categories.
* Users may delete their own categories.
* Users may view system categories.
* Users may not modify system categories.
* Users may not delete system categories.

---

# Token Security

Requirements:

* Expired tokens must be rejected.
* Invalid tokens must be rejected.
* Tampered tokens must be rejected.

---

# Transport Security

Requirements:

* HTTPS required in production.

---

# Sensitive Data

Sensitive Data Includes:

* Passwords
* Password Hashes
* JWT Tokens
* Refresh Tokens

Requirements:

* Must never be returned in API responses.
* Must never be written to application logs.

---

# Account Ownership Rule

Every request involving user-owned resources must verify ownership before returning or modifying data.

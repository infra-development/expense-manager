# V4 - Create Users

## Migration Contents

```sql
CREATE TABLE users (
    id              UUID            PRIMARY KEY DEFAULT gen_random_uuid(),
    email           VARCHAR(255)    NOT NULL,
    password_hash   VARCHAR(255)    NOT NULL,
    first_name      VARCHAR(100)    NOT NULL,
    last_name       VARCHAR(100)    NOT NULL,
    is_deleted      BOOLEAN         NOT NULL DEFAULT false,
    deleted_at      TIMESTAMPTZ,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT now(),
    updated_at      TIMESTAMPTZ     NOT NULL DEFAULT now(),

    CONSTRAINT uk_users_email UNIQUE (email)
);
```

---

# Business Problem

The system needs to answer:

```text
Who is using the application?
```

This introduces:

```text
User Identity
```

which is different from:

```text
Family Membership
```

and different from:

```text
Authentication
```

---

# Core Domain Concepts

The schema intentionally separates:

```text
User
```

from

```text
Family Membership
```

A user is a person in the system.

A family membership is that person's participation in a specific family.

---

# Identity vs Authentication

One of the most important lessons from V4.

Identity answers:

```text
Who are you?
```

Authentication answers:

```text
How do you prove it?
```

Examples:

Identity:

```text
users.id
```

Authentication:

```text
email
password
google login
microsoft login
mobile number
passkey
```

Authentication methods may change.

Identity should remain stable.

---

# Why UUID Primary Key Instead Of Email

Bad design:

```sql
email PRIMARY KEY
```

Problems:

* Email can change
* Login methods can change
* Identity becomes coupled to authentication

---

Better design:

```sql
id UUID PRIMARY KEY
email UNIQUE
```

Benefits:

* Stable identity
* Flexible authentication
* Easier future evolution

---

# Identity Lifecycle

A user may change:

```text
Email
Password
Phone Number
Authentication Provider
```

many times.

The user remains the same person.

Therefore:

```text
users.id
```

is the true identity.

---

# User vs Family Membership

Many beginners design:

```sql
users.family_id
```

and stop there.

Our schema intentionally uses:

```sql
users
family_members
families
```

instead.

Reason:

A user can belong to multiple families.

A family can contain multiple users.

---

# Cardinality

Relationship types:

```text
One-to-One
One-to-Many
Many-to-Many
```

User and Family is:

```text
Many-to-Many
```

because:

```text
One User -> Many Families
One Family -> Many Users
```

---

# Junction Table

Many-to-many relationships require a junction table.

Example:

```sql
family_members
```

This table represents:

```text
User ↔ Family
```

---

# Relationship Attributes

Many important facts belong to the relationship rather than either entity.

Examples:

```text
role
joined_at
invited_by
permissions
accepted_at
left_at
```

These belong to:

```sql
family_members
```

not:

```sql
users
```

or

```sql
families
```

---

# Relationship Lifecycle

The relationship itself has a lifecycle.

Example:

```text
Invited
Accepted
Active
Removed
```

This is another reason the relationship deserves its own table.

---

# Email Column

```sql
email VARCHAR(255) NOT NULL
```

Business meaning:

```text
Authentication Identifier
```

not:

```text
User Identity
```

---

# Email Uniqueness

```sql
UNIQUE(email)
```

represents the business rule:

```text
One email address can own only one account.
```

This is not the same thing as making email the primary key.

---

# Canonicalization

Users may enter:

```text
Haresh@gmail.com
haresh@gmail.com
HARESH@gmail.com
```

Many systems treat these as the same email.

Common solution:

```text
Convert to lowercase before storing.
```

This process is called:

```text
Canonicalization
```

Purpose:

```text
Different representations
        ↓
Same canonical value
```

---

# Password Storage

Never store:

```sql
password
```

Store:

```sql
password_hash
```

instead.

The database should store proof of a password, not the password itself.

---

# Password Hashing

Process:

```text
Password
    ↓
Hash Function
    ↓
Stored Hash
```

Common algorithms:

```text
BCrypt
Argon2
PBKDF2
```

---

# Why Hash Passwords?

If the database is compromised:

```text
Attackers should not see actual passwords.
```

Only password hashes should be visible.

---

# Salted Hashes

Modern password hashing uses:

```text
Salt
```

Conceptually:

```text
Password
    +
Salt
    ↓
Hash
```

Benefits:

* Same password produces different hashes.
* Harder to attack.
* Prevents password comparison attacks.

---

# Example

Two users choose:

```text
Password123
```

Stored hashes:

```text
User A -> Hash A
User B -> Hash B
```

because different salts are used.

---

# Why Password Hash Should NOT Be Unique

Bad idea:

```sql
UNIQUE(password_hash)
```

Reason:

Business does not require:

```text
Every user must have a different password.
```

Also:

Salted hashes are intentionally different.

---

# Constraints Represent Business Rules

Every constraint should correspond to a business invariant.

Good example:

```sql
UNIQUE(email)
```

Business rule:

```text
One email = One account
```

---

Bad example:

```sql
UNIQUE(password_hash)
```

No valid business rule exists.

---

# Soft Delete On Users

Columns:

```sql
is_deleted
deleted_at
```

allow logical deletion.

---

# Why Soft Delete Users?

Users own:

```text
Transactions
Accounts
Budgets
Audit History
```

Removing them completely may break historical references.

Soft delete preserves history.

---

# Email Reuse Discussion

Current schema:

```sql
UNIQUE(email)
```

means:

A soft-deleted user still reserves that email address.

Example:

```text
haresh@gmail.com
```

cannot be reused after deletion.

---

Alternative design:

Partial unique index.

```sql
CREATE UNIQUE INDEX ...
WHERE is_deleted = false;
```

This would allow reuse of emails from deleted accounts.

Whether this is correct depends on business requirements.

---

# Separation Of Concerns

V4 introduces three distinct concepts:

```text
Who are you?
```

↓

```text
User Identity
```

---

```text
How do you log in?
```

↓

```text
Authentication
```

---

```text
What are you allowed to do?
```

↓

```text
Authorization
```

Authorization will later be handled through:

```text
Family Membership
Role
Permissions
```

not through the users table itself.

---

# Security Lessons

1. Never store plaintext passwords.
2. Use modern password hashing algorithms.
3. Use salted hashes.
4. Identity should not depend on login credentials.
5. Authentication methods may evolve over time.
6. Constraints should enforce business invariants.

---

# Database Design Lessons

1. User and Family Membership are different concepts.
2. Many-to-many relationships require junction tables.
3. Relationship attributes belong to the relationship.
4. Stable identity should be separated from mutable business data.
5. Authentication and identity should not be coupled.

---

# Interview Questions

1. Why use UUID instead of email as primary key?
2. What is the difference between identity and authentication?
3. Why should passwords be hashed?
4. What is a salt?
5. Why are salted hashes important?
6. Why should password_hash not be unique?
7. What is canonicalization?
8. Why is User ↔ Family a many-to-many relationship?
9. Why does family_members exist?
10. What are relationship attributes?

---

# Most Important Lesson From V4

The most important insight is:

```text
Identity
Authentication
Authorization
```

are three different concerns.

Good systems model them separately.

Poor systems combine them and become difficult to evolve as requirements grow.

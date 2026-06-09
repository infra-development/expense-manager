# V3 - Create Families

## Migration Contents

```sql
CREATE TABLE families (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    name VARCHAR(100) NOT NULL,

    default_currency_code CHAR(3) NOT NULL DEFAULT 'INR',

    is_deleted BOOLEAN NOT NULL DEFAULT false,
    deleted_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),

    CONSTRAINT chk_families_currency_code
        CHECK (default_currency_code ~ '^[A-Z]{3}$')
);
```

---

# Business Problem

Before storing:

* Accounts
* Transactions
* Categories
* Budgets

we must decide:

```text
Who owns financial data?
```

Possible answers:

```text
User
Family
Organization
Household
```

Our domain chose:

```text
Family
```

This is one of the most important modeling decisions in the system.

---

# Why Family Exists

The application supports:

```text
Shared finances
Shared budgets
Shared categories
Multiple family members
```

A single-user ownership model would make sharing significantly more complex.

Instead:

```text
Family
    ↓
Accounts
Categories
Budgets
Transactions
```

becomes the ownership hierarchy.

---

# Aggregate Root

Domain Driven Design concept.

```text
Family
 ├─ Members
 ├─ Accounts
 ├─ Categories
 ├─ Budgets
 └─ Transactions
```

Family becomes the root of a business aggregate.

Most financial information belongs to a family boundary.

---

# Tenant Boundary

Later many tables will contain:

```sql
family_id
```

This creates a logical boundary:

```text
Family A Data
```

must never leak into:

```text
Family B Data
```

This is a simplified form of multi-tenancy.

---

# UUID Primary Key

```sql
id UUID PRIMARY KEY DEFAULT gen_random_uuid()
```

Purpose:

```text
Stable identity
Globally unique
Future-proof for distributed systems
Offline-friendly
```

Detailed discussion captured in V1 notes.

---

# Family Name

```sql
name VARCHAR(100) NOT NULL
```

Important observation:

```sql
UNIQUE(name)
```

was intentionally NOT added.

Reason:

Family names are not identities.

Examples:

```text
Patel Family
Patel Family
Patel Family
```

can all legitimately exist.

---

# Candidate Key Principle

Before adding:

```sql
UNIQUE(...)
```

ask:

```text
What business rule requires uniqueness?
```

Bad reason:

```text
Looks unique
```

Good reason:

```text
Business requires uniqueness
```

Family names are not candidate keys.

---

# Currency Code

```sql
default_currency_code CHAR(3)
```

Examples:

```text
INR
USD
EUR
GBP
```

---

# Datatype As Validation

The datatype itself can encode business rules.

Example:

```sql
CHAR(3)
```

communicates:

```text
Exactly 3 characters
```

which matches ISO currency codes.

---

# Semantic Modeling Principle

Choose datatypes that model reality.

Examples:

| Business Meaning | Preferred Type |
| ---------------- | -------------- |
| Currency Code    | CHAR(3)        |
| UUID             | UUID           |
| Money            | NUMERIC        |
| Date of Birth    | DATE           |
| Timestamp        | TIMESTAMPTZ    |
| Flag             | BOOLEAN        |

Datatypes are not only storage choices.

They are part of domain modeling.

---

# Business Defaults

```sql
DEFAULT 'INR'
```

encodes a business assumption:

```text
Primary market is India
```

Defaults are business decisions, not technical decisions.

---

# Currency Validation

```sql
CHECK (
  default_currency_code ~ '^[A-Z]{3}$'
)
```

Allowed:

```text
INR
USD
EUR
GBP
```

Rejected:

```text
inr
Rs
123
USDD
```

---

# Defense In Depth

Validation should exist in multiple layers.

```text
UI Validation
      +
Backend Validation
      +
Database Constraints
```

Never trust a single layer.

---

# Soft Delete

Columns:

```sql
is_deleted BOOLEAN
deleted_at TIMESTAMPTZ
```

Instead of:

```sql
DELETE FROM families
```

we do:

```sql
UPDATE families
SET is_deleted = true
```

---

# Why Soft Delete?

Provides:

```text
Recovery
Auditability
Historical Analysis
Accidental Deletion Protection
```

Deleted records remain available for investigation and restoration.

---

# Logical Delete vs Physical Delete

Physical Delete:

```sql
DELETE
```

Record disappears permanently.

---

Logical Delete:

```sql
is_deleted = true
```

Record remains in database but is hidden from normal operations.

---

# Auditability

Soft delete allows answering:

```text
When was it deleted?
Can it be restored?
What existed historically?
```

Physical deletion destroys this information.

---

# Derived Data Discussion

Current design:

```sql
is_deleted
deleted_at
```

Observation:

```text
is_deleted
```

can be derived from:

```text
deleted_at IS NOT NULL
```

Meaning:

```text
is_deleted
```

is redundant data.

---

Potential inconsistency:

```text
is_deleted = false
deleted_at = 2026-01-01
```

Invalid state.

Some teams therefore prefer:

```sql
deleted_at
```

only.

---

# Soft Delete Query Problem

Every query must remember:

```sql
WHERE is_deleted = false
```

Otherwise deleted records appear unexpectedly.

This is one of the biggest drawbacks of soft delete.

---

# Soft Delete Leak

Database reality:

```text
1000 rows exist
```

Business reality:

```text
850 rows exist
```

because 150 are deleted.

Developers must constantly remember this distinction.

---

# Partial Unique Indexes

Soft deletes introduce uniqueness problems.

Example:

```text
Patel Family
```

is soft deleted.

Can another:

```text
Patel Family
```

be created?

Business usually says:

```text
Yes
```

---

Solution:

```sql
CREATE UNIQUE INDEX ...
WHERE is_deleted = false;
```

This enforces uniqueness only for active rows.

---

# Benefits Of Partial Unique Indexes

* Accurate business rule enforcement
* Smaller indexes
* Better performance
* Allows reuse of deleted values

---

# Business Rules Can Be Conditional

Not all rules are absolute.

Example:

Wrong rule:

```text
All family names must be unique forever.
```

Correct rule:

```text
Active family names must be unique.
```

Partial indexes allow databases to enforce conditional business rules.

---

# Soft Delete vs Status

Soft Delete answers:

```text
Should this record be visible?
```

Status answers:

```text
What business state is this entity in?
```

---

# Status Example

```text
ACTIVE
FROZEN
SUSPENDED
CLOSED
```

These represent a lifecycle.

---

# Lifecycle Modeling

Financial entities usually evolve through states.

Examples:

```text
Bank Account
Loan
Insurance Policy
Subscription
Order
```

These are better represented by:

```sql
status
```

than by soft delete.

---

# When To Use Soft Delete

Good candidates:

```text
Category
Tag
Draft
Configuration
Reference Data
```

Goal:

```text
Hide and possibly restore
```

---

# When To Use Status

Good candidates:

```text
Bank Account
Loan
Subscription
Order
Employee
```

Goal:

```text
Model business lifecycle
```

---

# Senior Engineer Takeaways

1. Database design starts with ownership modeling.
2. Family is the aggregate root of the finance domain.
3. Datatypes are part of domain modeling.
4. UNIQUE constraints represent business rules.
5. Soft delete introduces hidden complexity.
6. Business rules are often conditional.
7. Partial indexes are powerful PostgreSQL features.
8. Status models lifecycle; soft delete models visibility.
9. Multi-tenancy starts with ownership boundaries.
10. The schema should describe reality, not just store data.

---

# Interview Questions

1. Why is Family the aggregate root?
2. What is a tenant boundary?
3. Why use CHAR(3) instead of VARCHAR(3)?
4. Why should family name not be unique?
5. What is a candidate key?
6. What are the advantages and disadvantages of soft delete?
7. What is derived data?
8. What is a soft delete leak?
9. What is a partial unique index?
10. When should status be preferred over soft delete?

---

# Most Important Lesson From V3

The most important decision in this migration is not a column or a constraint.

It is deciding:

```text
Who owns the financial data?
```

Everything else in the schema will eventually revolve around that answer:

```text
Family owns financial data.
```

This single domain decision shapes accounts, categories, budgets, transactions, permissions, reporting, and future scalability.

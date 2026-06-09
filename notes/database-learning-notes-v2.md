# V2 - Create Enums

## Migration Contents

```sql
CREATE TYPE family_role AS ENUM (...)
CREATE TYPE account_type AS ENUM (...)
CREATE TYPE category_scope AS ENUM (...)
CREATE TYPE transaction_type AS ENUM (...)
CREATE TYPE budget_period_type AS ENUM (...)
```

---

# Business Problem

Many business concepts have a finite set of valid values.

Examples:

```text
Transaction Type:
- INCOME
- EXPENSE
- TRANSFER

Family Role:
- FAMILY_OWNER
- FAMILY_MEMBER
```

The database should enforce these rules instead of relying entirely on application code.

---

# What Is An Enum?

An enum is a database type that restricts values to a predefined set.

Example:

```sql
CREATE TYPE transaction_type AS ENUM (
    'INCOME',
    'EXPENSE',
    'TRANSFER'
);
```

Only these values can be stored.

---

# Why Not VARCHAR?

Without enums:

```text
EXPENSE
expense
Expense
Expence
MoneySpent
```

all become possible.

This leads to:

* Inconsistent data
* Broken reports
* More validation code
* Data cleanup efforts

Enums eliminate these problems.

---

# Hidden Concept: Business Vocabulary

One of the most important ideas in V2.

The database is no longer just storing data.

It is starting to understand the business language.

Examples:

```text
family_role
account_type
transaction_type
budget_period_type
```

These are business concepts, not technical concepts.

The schema is becoming a model of the business domain.

---

# Domain Modeling

Database design is not about tables.

Database design is about modeling reality.

Process:

```text
Business Concept
        ↓
Domain Vocabulary
        ↓
Database Type
        ↓
Table Column
```

Example:

```text
Expense can only be:
- INCOME
- EXPENSE
- TRANSFER
```

becomes:

```sql
transaction_type ENUM
```

---

# Invalid States Should Be Impossible

A senior engineering principle.

Bad systems:

```text
Allow anything
Validate later
```

Better systems:

```text
Prevent invalid data from existing
```

Example:

Without enum:

```text
EXPENSE
expense
expens
xyz
```

With enum:

```text
INCOME
EXPENSE
TRANSFER
```

only.

The invalid states cannot exist.

---

# Enum vs Lookup Table

Alternative design:

```sql
transaction_types
-----------------
id
code
name
```

Example:

| id | code     |
| -- | -------- |
| 1  | INCOME   |
| 2  | EXPENSE  |
| 3  | TRANSFER |

Advantages:

* Easier to add new values
* Business users can manage values
* No schema migration required

Disadvantages:

* Extra joins
* More complexity
* Weaker enforcement

---

# Enum vs VARCHAR

VARCHAR:

```sql
transaction_type VARCHAR(50)
```

Advantages:

* Flexible
* No migrations needed

Disadvantages:

* Weak validation
* Data inconsistency
* Reporting problems

Enum:

Advantages:

* Strong validation
* Better data quality
* Better domain modeling

Disadvantages:

* Requires schema migration to add values

---

# Why Some Architects Avoid Enums

Example:

Business later introduces:

```text
REFUND
```

Now schema migration is required:

```sql
ALTER TYPE transaction_type
ADD VALUE 'REFUND';
```

Some organizations prefer lookup tables to avoid schema changes.

---

# Why Some Architects Prefer Enums

Many business vocabularies rarely change.

Examples:

```text
INCOME
EXPENSE
TRANSFER
```

may remain unchanged for years.

In such cases:

```text
Data Integrity Benefits
        >
Migration Cost
```

Enums become a good choice.

---

# Performance Considerations

PostgreSQL enums are not stored as text.

Benefits:

* Less storage
* Faster comparisons
* Faster sorting
* Smaller indexes

Enum columns are generally more efficient than VARCHAR columns for fixed vocabularies.

---

# Advanced Concept: Attributes Of Relationships

One of the most important lessons discovered during discussion.

Many developers assume every column belongs to an entity.

Example:

```text
User
Family
```

However some facts belong to the relationship itself.

Example:

```text
User joins Family
```

Relationship attributes:

```text
role
joined_at
invited_by
permissions
```

These do not belong to:

```text
users
```

or

```text
families
```

They belong to:

```text
family_members
```

---

# Relationship Modeling

Wrong thinking:

```text
User has role
```

Correct thinking:

```text
User has a role within a family
```

Role depends on the relationship.

Therefore:

```sql
family_members.role
```

is correct.

While:

```sql
users.role
```

would be an incorrect business model.

---

# Associative Entity (Junction Table)

When a relationship has its own attributes, the relationship becomes a first-class entity.

Example:

```text
Users
  ↔
Family Members
  ↔
Families
```

The junction table stores relationship-specific information.

This is one of the most important concepts in relational modeling.

---

# Table.Column Notation

During architecture discussions, engineers commonly use:

```text
table.column
```

Examples:

```text
users.email
family_members.role
transactions.amount
```

Meaning:

```text
Column inside a specific table
```

This notation is used extensively in database design discussions.

---

# Senior Engineer Takeaways

When introducing an enum, ask:

```text
Is this a stable business vocabulary?
```

Good enum candidates:

```text
Transaction Type
Family Role
Budget Period
Account Type
```

Poor enum candidates:

```text
Country
City
Department
Product Category
```

because they change frequently.

---

# Interview Questions

1. Enum vs VARCHAR?
2. Enum vs Lookup Table?
3. When should enums be avoided?
4. Why do enums improve data integrity?
5. Why can enums make schema evolution harder?
6. What does "invalid states become impossible" mean?
7. What are relationship attributes?
8. Why does role belong in family_members rather than users?
9. What is an associative entity?
10. When should a junction table become its own entity?

---

# Most Important Lesson From V2

The database is no longer just storing information.

It is becoming a model of the business domain.

Enums are not primarily a storage optimization.

They are a way of encoding business vocabulary and business rules directly into the database.

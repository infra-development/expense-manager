# Database Design

## Overview

The Expense Manager system uses a relational database.

Primary key strategy:

* UUID for all entities

Database:

* PostgreSQL

---

# User

Purpose:

Stores registered users.

Fields:

* id (UUID, Primary Key)
* email
* password_hash
* first_name
* last_name
* created_at
* updated_at

Constraints:

* email must be unique
* email cannot be null
* password_hash cannot be null

Indexes:

* email

---
# Refresh Token

Purpose:

Stores authenticated user sessions.

Fields:

* id (UUID, Primary Key)
* user_id (UUID)
* token
* device_id
* expires_at
* revoked
* created_at
* updated_at

Relationships:

* Many Refresh Tokens belong to one User

Indexes:

* user_id
* token

Constraints:

* token must be unique
* token cannot be null

# Income

Purpose:

Stores user income records.

Fields:

* id (UUID, Primary Key)
* user_id (UUID)
* amount
* income_date
* source
* notes
* created_at
* updated_at

Relationships:

* Many Income records belong to one User

Constraints:

* amount > 0
* income_date required

Indexes:

* user_id
* income_date
* user_id + income_date

---

# Category

Purpose:

Stores expense categories.

Fields:

* id (UUID, Primary Key)
* user_id (UUID)
* name
* description
* is_system_category
* created_at
* updated_at

Relationships:

* Many Categories belong to one User

Constraints:

* System categories have:
  * user_id = NULL
  * is_system_category = true

* User categories have:
  * user_id = User ID
  * is_system_category = false

Indexes:

* user_id
* name

---

# Expense

Purpose:

Stores expense transactions.

Fields:

* id (UUID, Primary Key)
* user_id (UUID)
* category_id (UUID)
* amount
* expense_date
* description
* notes
* created_at
* updated_at

Relationships:

* Many Expenses belong to one User
* Many Expenses belong to one Category

Constraints:

* amount > 0
* category required
* expense_date required

Indexes:

* user_id
* category_id
* expense_date
* user_id + expense_date

---

# Financial Goal

Purpose:

Stores long-term financial objectives.

Fields:

* id (UUID, Primary Key)
* user_id (UUID)
* name
* target_amount
* duration_years
* description
* created_at
* updated_at

Relationships:

* Many Financial Goals belong to one User

Constraints:

* target_amount > 0
* duration_years allowed values:

  * 1
  * 3
  * 5

Indexes:

* user_id

---

# Goal Contribution

Purpose:

Stores contributions toward financial goals.

Fields:

* id (UUID, Primary Key)
* user_id (UUID)
* financial_goal_id (UUID)
* amount
* contribution_date
* notes
* created_at
* updated_at

Relationships:

* Many Goal Contributions belong to one Financial Goal
* Many Goal Contributions belong to one User

Constraints:

* amount > 0

Indexes:

* user_id
* financial_goal_id
* contribution_date

---

# Derived Data

The following information is calculated and not stored:

* Current Balance
* Goal Progress Percentage
* Remaining Goal Amount
* Dashboard Summary
* Reports

---

# Time Handling

Time Zone:

* UTC

Java Representation:

* Instant

Rules:

* All timestamps shall be stored in UTC.
* All auditing fields shall use Instant.
* Timezone conversion shall be handled by API consumers.

---

# Soft Delete Strategy

The following entities shall support soft deletion:

* Income
* Expense
* Category
* Financial Goal
* Goal Contribution

Additional Fields:

* is_deleted
* deleted_at

Rules:

* Delete operations shall perform soft deletes.
* Soft deleted records shall not be returned by default queries.
* Soft deleted records may be recovered in the future.

---

# Auditing

Every entity shall contain:

* created_at
* updated_at

All timestamps shall be stored in UTC.

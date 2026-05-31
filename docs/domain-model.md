# Domain Model

## Overview

This document defines the core business concepts of the Expense Manager system.

The domain model is independent of:

* Database technology
* API design
* Framework implementation
* User interface

Its purpose is to identify the business entities and relationships that make up the system.

---

# User

Represents a registered account in the system.

Responsibilities:

* Authenticate
* Manage personal financial data
* Create financial goals
* Generate reports

Relationships:

* Owns many Income Records
* Owns many Expense Records
* Owns many Categories
* Owns many Financial Goals

---

# Income

Represents money received by a user.

Examples:

* Salary
* Bonus
* Interest
* Freelance Payment

Attributes:

* Amount
* Date
* Source
* Notes

Relationships:

* Belongs to one User

---

# Expense

Represents money spent by a user.

Examples:

* Food
* Rent
* Shopping
* Fuel

Attributes:

* Amount
* Date
* Category
* Description
* Notes

Relationships:

* Belongs to one User
* Belongs to one Category

---

# Category

Represents a classification of expenses.

Examples:

* Food
* Transportation
* Healthcare
* Entertainment

Attributes:

* Name
* Description

Relationships:

* Belongs to one User
* Contains many Expenses

Notes:

* Categories may be system-defined
* Categories may be user-defined

Business Rules:

- Categories can be system-defined or user-defined.
- System-defined categories are available to all users.
- User-defined categories are visible only to their owner.
- Users cannot modify system-defined categories.

---

# Financial Goal

Represents a future financial objective.

Examples:

* Buy a House
* Buy a Car
* Education Fund
* Emergency Fund

Attributes:

* Name
* Target Amount
* Duration
* Description
* Status

Allowed Status Values:

* ACTIVE
* COMPLETED
* CANCELLED

Relationships:

* Belongs to one User

Business Rules:

* New goals are created with ACTIVE status.
* A goal may have zero or more contributions.
* Users may cancel ACTIVE goals.
* A goal becomes COMPLETED when total contributions reach or exceed the target amount.
* Target amount must be greater than zero.
* Duration must be one of:

  * 1 Year
  * 3 Years
  * 5 Years

---

# Goal Contribution

Represents money allocated toward a Financial Goal.

Examples:

* Added ₹5,000 toward Emergency Fund
* Added ₹10,000 toward Car Fund

Attributes:

* Amount
* Date
* Notes

Relationships:

* Belongs to one Financial Goal
* Belongs to one User

Purpose:

* Track actual progress toward a goal
* Calculate completion percentage
* Calculate remaining amount

Business Rules

* A Goal Contribution belongs to exactly one Financial Goal.
* A Goal Contribution belongs to exactly one User.
* Contribution amount must be greater than zero.
* Goal progress is calculated from all contributions associated with the goal.
---

# Dashboard Summary

Represents aggregated financial information.

Examples:

* Total Income
* Total Expenses
* Current Balance
* Recent Transactions

Notes:

* Not stored permanently
* Calculated from domain data

---

# Report

Represents aggregated financial analysis.

Examples:

* Monthly Report
* Yearly Report
* Category Spending Report

Notes:

* Generated on demand
* Not stored permanently

---

# Domain Relationships

User
├── Income Records
├── Expense Records
├── Categories
├── Financial Goals
└── Goal Contributions

Category
└── Expenses

Financial Goal
└── Goal Contributions

Dashboard Summary
└── Generated from Income, Expense and Goal data

Reports
└── Generated from Income, Expense and Goal data


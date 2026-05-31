# API Design

## Overview

Architecture Style:

* REST API

Base Path:

* /api/v1

Data Format:

* JSON

Authentication:

* JWT

---

# Authentication APIs

## Register User

POST /api/v1/auth/register

Purpose:

* Register a new user

---

## Login User

POST /api/v1/auth/login

Purpose:

* Authenticate a user

---

## Refresh Token

POST /api/v1/auth/refresh

Purpose:

* Generate a new access token

---

## Logout User

POST /api/v1/auth/logout

Purpose:

* Invalidate user session

---

# User APIs

## Get Profile

GET /api/v1/users/me

Purpose:

* Retrieve current user profile

---

## Update Profile

PUT /api/v1/users/me

Purpose:

* Update profile information

---

## Change Password

PUT /api/v1/users/me/password

Purpose:

* Change account password

---

# Income APIs

## Create Income

POST /api/v1/incomes

Purpose:

* Create income record

---

## Get Income By Id

GET /api/v1/incomes/{incomeId}

Purpose:

* Retrieve income details

---

## Get Income List

GET /api/v1/incomes

Purpose:

* Retrieve income history

Supported Filters:

* Date Range

---

## Update Income

PUT /api/v1/incomes/{incomeId}

Purpose:

* Update income record

---

## Delete Income

DELETE /api/v1/incomes/{incomeId}

Purpose:

* Delete income record

---

# Category APIs

## Create Category

POST /api/v1/categories

Purpose:

* Create user-defined category

---

## Get Categories

GET /api/v1/categories

Purpose:

* Retrieve system and user categories

---

## Update Category

PUT /api/v1/categories/{categoryId}

Purpose:

* Update user-defined category

---

## Delete Category

DELETE /api/v1/categories/{categoryId}

Purpose:

* Delete user-defined category

Business Rules:

* System categories cannot be modified
* System categories cannot be deleted

---

# Expense APIs

## Create Expense

POST /api/v1/expenses

Purpose:

* Create expense record

---

## Get Expense By Id

GET /api/v1/expenses/{expenseId}

Purpose:

* Retrieve expense details

---

## Get Expense List

GET /api/v1/expenses

Purpose:

* Retrieve expense history

Supported Filters:

* Date Range
* Category

---

## Update Expense

PUT /api/v1/expenses/{expenseId}

Purpose:

* Update expense record

---

## Delete Expense

DELETE /api/v1/expenses/{expenseId}

Purpose:

* Delete expense record

---

# Financial Goal APIs

## Create Goal

POST /api/v1/goals

Purpose:

* Create financial goal

---

## Get Goal By Id

GET /api/v1/goals/{goalId}

Purpose:

* Retrieve goal details

---

## Get Goals

GET /api/v1/goals

Purpose:

* Retrieve all goals

---

## Update Goal

PUT /api/v1/goals/{goalId}

Purpose:

* Update goal information

---

## Cancel Goal

POST /api/v1/goals/{goalId}/cancel

Purpose:

* Cancel an active goal

---

# Goal Contribution APIs

## Create Contribution

POST /api/v1/goals/{goalId}/contributions

Purpose:

* Add contribution to goal

---

## Get Contributions

GET /api/v1/goals/{goalId}/contributions

Purpose:

* Retrieve goal contributions

---

# Reporting APIs

## Dashboard Summary

GET /api/v1/dashboard

Purpose:

* Retrieve dashboard summary

---

## Monthly Report

GET /api/v1/reports/monthly

Purpose:

* Generate monthly report

---

## Yearly Report

GET /api/v1/reports/yearly

Purpose:

* Generate yearly report

---

## Category Spending Report

GET /api/v1/reports/categories

Purpose:

* Generate category spending report

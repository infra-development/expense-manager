# Naming Conventions

## General Principles

* Use business-oriented names.
* Prefer clarity over brevity.
* Use consistent terminology throughout the project.
* Follow names defined in domain-model.md.

---

## Entity Naming

Use domain names directly.

Examples:

* User
* Income
* Expense
* Category
* FinancialGoal
* GoalContribution

Do Not Use:

* UserEntity
* ExpenseEntity
* IncomeEntity

---

## Repository Naming

Pattern:

<Entity>NameRepository

Examples:

* UserRepository
* ExpenseRepository
* IncomeRepository
* CategoryRepository
* FinancialGoalRepository
* GoalContributionRepository

---

## Service Naming

Pattern:

<Entity>NameService

Examples:

* UserService
* ExpenseService
* IncomeService
* CategoryService
* FinancialGoalService

---

## Controller Naming

Pattern:

<Entity>NameController

Examples:

* AuthController
* UserController
* ExpenseController
* IncomeController
* CategoryController
* FinancialGoalController

---

## DTO Naming

Request DTOs:

* CreateExpenseRequest
* UpdateExpenseRequest
* CreateIncomeRequest

Response DTOs:

* ExpenseResponse
* IncomeResponse
* CategoryResponse

Do Not Use:

* ExpenseDto
* ExpenseData

---

## Mapper Naming

Pattern:

<Entity>NameMapper

Examples:

* ExpenseMapper
* IncomeMapper
* CategoryMapper

---

## Exception Naming

Pattern:

<Entity>NameException

Examples:

* ExpenseNotFoundException
* IncomeNotFoundException
* GoalNotFoundException

---

## Enum Naming

Pattern:

<Entity>NameStatus

Examples:

* GoalStatus

Values:

* ACTIVE
* COMPLETED
* CANCELLED

---

## API Path Naming

Use plural resource names.

Examples:

* /api/v1/users
* /api/v1/incomes
* /api/v1/expenses
* /api/v1/categories
* /api/v1/goals

---

## Database Table Naming

Use snake_case.

Examples:

* users
* incomes
* expenses
* categories
* financial_goals
* goal_contributions

---

## Database Column Naming

Use snake_case.

Examples:

* created_at
* updated_at
* user_id
* target_amount
* contribution_date

---

## Method Naming

Use verb-first naming.

Examples:

* createExpense
* updateExpense
* deleteExpense
* findExpenseById
* calculateGoalProgress

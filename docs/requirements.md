# Expense Manager Requirements

## Version

MVP 1.0

## User Management

### Registration

The system shall allow a user to:

* Register using email and password
* Log in using email and password
* Log out
* Reset password

### User Profile

The system shall allow a user to:

* View profile information
* Update profile information
* Change password

## Income Management

The system shall allow a user to:

* Add income
* Edit income
* Delete income
* View income history

Income shall include:

* Amount
* Date
* Source
* Notes

## Expense Management

The system shall allow a user to:

* Add expense
* Edit expense
* Delete expense
* View expense history

Expense shall include:

* Amount
* Date
* Category
* Description
* Notes

## Categories

The system shall provide:

* Default categories
* User-defined categories

Examples:

* Food
* Transportation
* Shopping
* Entertainment
* Education
* Healthcare

## Dashboard

The dashboard shall display:

* Total income
* Total expenses
* Current balance
* Recent transactions
* Spending by category

## Reports

The system shall provide:

* Monthly reports
* Yearly reports
* Category-wise reports

## Financial Planning

The system shall allow users to create financial plans.

A plan shall include:

* Name
* Duration
* Target amount
* Description

Supported durations:

* 1 Year
* 3 Years
* 5 Years

Examples:

* Buy a car
* Buy a house
* Emergency fund
* Education fund

The system shall track:

* Target amount
* Current progress
* Remaining amount
* Estimated completion percentage

## Security

The system shall ensure:

* User authentication
* User authorization
* Password encryption
* Data isolation between users

## Out of Scope for MVP

The following features are excluded from MVP:

* Investment tracking
* Tax calculations
* Bank integrations
* UPI integrations
* AI recommendations
* Family/shared accounts

## Project Scope

### Backend Responsibility

This project focuses exclusively on backend development.

The backend team is responsible for:

* Business logic
* REST APIs
* Authentication and authorization
* Database design
* Data validation
* Reporting logic
* Financial planning logic
* Security
* API documentation

### Frontend Responsibility

Frontend development is outside the scope of this project.

A separate team or developer will be responsible for:

* User interface design
* Web application implementation
* Mobile application implementation
* User experience design
* API integration

### Backend Deliverables

The backend shall provide:

* REST APIs
* OpenAPI / Swagger documentation
* Database schema
* Authentication APIs
* Reporting APIs
* Financial planning APIs

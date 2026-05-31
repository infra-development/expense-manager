-- ============================================================================
-- Phase 2
-- PostgreSQL enum definitions
--
-- Tables depending on these enums:
--   families
--   family_members
--   accounts
--   categories
--   transactions
--   budgets
-- ============================================================================

CREATE TYPE family_role AS ENUM (
    'FAMILY_OWNER',
    'FAMILY_MEMBER'
);

CREATE TYPE account_type AS ENUM (
    'CASH',
    'SAVINGS',
    'CURRENT',
    'CREDIT_CARD',
    'LOAN',
    'INVESTMENT'
);

CREATE TYPE category_scope AS ENUM (
    'SYSTEM',
    'FAMILY',
    'USER'
);

CREATE TYPE transaction_type AS ENUM (
    'INCOME',
    'EXPENSE',
    'TRANSFER'
);

CREATE TYPE budget_period_type AS ENUM (
    'MONTHLY',
    'YEARLY'
);

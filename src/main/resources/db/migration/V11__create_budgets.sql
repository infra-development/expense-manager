-- Phase 2: budgets table
--
-- Supports:
-- 1. Family-wide budgets (category_id IS NULL)
-- 2. Category-specific budgets (category_id IS NOT NULL)
--
-- Examples:
--
-- Family Budget:
--   category_id = NULL
--   MONTHLY
--   ₹50,000
--
-- Food Budget:
--   category_id = Food
--   MONTHLY
--   ₹10,000

CREATE TABLE budgets (
    id              UUID                PRIMARY KEY DEFAULT gen_random_uuid(),

    family_id       UUID                NOT NULL,
    category_id     UUID,

    period_type     budget_period_type  NOT NULL,

    period_start    DATE                NOT NULL,

    amount_limit    NUMERIC(19,4)       NOT NULL,

    currency_code   CHAR(3)             NOT NULL DEFAULT 'INR',

    is_deleted      BOOLEAN             NOT NULL DEFAULT false,
    deleted_at      TIMESTAMPTZ,

    created_at      TIMESTAMPTZ         NOT NULL DEFAULT now(),
    updated_at      TIMESTAMPTZ         NOT NULL DEFAULT now(),

    CONSTRAINT fk_budgets_family
        FOREIGN KEY (family_id)
        REFERENCES families (id)
        ON DELETE RESTRICT,

    CONSTRAINT fk_budgets_category
        FOREIGN KEY (category_id)
        REFERENCES categories (id)
        ON DELETE RESTRICT,

    CONSTRAINT chk_budgets_amount_limit_positive
        CHECK (amount_limit > 0),

    CONSTRAINT chk_budgets_currency_code
        CHECK (currency_code ~ '^[A-Z]{3}$'),

    CONSTRAINT chk_budgets_soft_delete_consistency
        CHECK (
            deleted_at IS NULL
            OR is_deleted = true
        ),

    CONSTRAINT chk_budgets_monthly_period_start
        CHECK (
            period_type <> 'MONTHLY'
            OR period_start = date_trunc('month', period_start::timestamp)::date
        ),

    CONSTRAINT chk_budgets_yearly_period_start
        CHECK (
            period_type <> 'YEARLY'
            OR period_start = date_trunc('year', period_start::timestamp)::date
        )
);

-- One active category budget per family/category/period

CREATE UNIQUE INDEX uk_budgets_family_category_period_active
    ON budgets (
        family_id,
        category_id,
        period_type,
        period_start
    )
    WHERE is_deleted = false
      AND category_id IS NOT NULL;

-- One active family-wide budget per family/period

CREATE UNIQUE INDEX uk_budgets_family_period_active
    ON budgets (
        family_id,
        period_type,
        period_start
    )
    WHERE is_deleted = false
      AND category_id IS NULL;

CREATE INDEX idx_budgets_category_id
    ON budgets (category_id)
    WHERE category_id IS NOT NULL;

CREATE INDEX idx_budgets_family_period_active
    ON budgets (
        family_id,
        period_type,
        period_start
    )
    WHERE is_deleted = false;
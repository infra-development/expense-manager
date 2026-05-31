-- Phase 2: transactions table
--
-- Transaction model:
--
-- INCOME
--   account_id      NOT NULL
--   category_id     NOT NULL
--   from_account_id NULL
--   to_account_id   NULL
--
-- EXPENSE
--   account_id      NOT NULL
--   category_id     NOT NULL
--   from_account_id NULL
--   to_account_id   NULL
--
-- TRANSFER
--   account_id      NULL
--   category_id     NULL
--   from_account_id NOT NULL
--   to_account_id   NOT NULL

CREATE TABLE transactions (
    id                  UUID                PRIMARY KEY DEFAULT gen_random_uuid(),

    family_id           UUID                NOT NULL,
    created_by_user_id  UUID                NOT NULL,

    transaction_type    transaction_type    NOT NULL,

    category_id         UUID,
    account_id          UUID,

    from_account_id     UUID,
    to_account_id       UUID,

    amount              NUMERIC(19,4)       NOT NULL,

    transaction_date    TIMESTAMPTZ         NOT NULL,

    description         VARCHAR(255),
    notes               TEXT,

    is_deleted          BOOLEAN             NOT NULL DEFAULT false,
    deleted_at          TIMESTAMPTZ,

    created_at          TIMESTAMPTZ         NOT NULL DEFAULT now(),
    updated_at          TIMESTAMPTZ         NOT NULL DEFAULT now(),

    CONSTRAINT fk_transactions_family
        FOREIGN KEY (family_id)
        REFERENCES families (id)
        ON DELETE RESTRICT,

    CONSTRAINT fk_transactions_created_by_user
        FOREIGN KEY (created_by_user_id)
        REFERENCES users (id)
        ON DELETE RESTRICT,

    CONSTRAINT fk_transactions_category
        FOREIGN KEY (category_id)
        REFERENCES categories (id)
        ON DELETE RESTRICT,

    CONSTRAINT fk_transactions_account
        FOREIGN KEY (account_id)
        REFERENCES accounts (id)
        ON DELETE RESTRICT,

    CONSTRAINT fk_transactions_from_account
        FOREIGN KEY (from_account_id)
        REFERENCES accounts (id)
        ON DELETE RESTRICT,

    CONSTRAINT fk_transactions_to_account
        FOREIGN KEY (to_account_id)
        REFERENCES accounts (id)
        ON DELETE RESTRICT,

    CONSTRAINT chk_transactions_amount_positive
        CHECK (amount > 0),

    CONSTRAINT chk_transactions_soft_delete_consistency
        CHECK (
            deleted_at IS NULL
            OR is_deleted = true
        ),

    CONSTRAINT chk_transactions_consistency
        CHECK (
            (
                transaction_type IN ('INCOME', 'EXPENSE')
                AND account_id IS NOT NULL
                AND category_id IS NOT NULL
                AND from_account_id IS NULL
                AND to_account_id IS NULL
            )
            OR
            (
                transaction_type = 'TRANSFER'
                AND account_id IS NULL
                AND category_id IS NULL
                AND from_account_id IS NOT NULL
                AND to_account_id IS NOT NULL
                AND from_account_id <> to_account_id
            )
        )
);

CREATE INDEX idx_transactions_family_id
    ON transactions (family_id);

CREATE INDEX idx_transactions_created_by_user_id
    ON transactions (created_by_user_id);

CREATE INDEX idx_transactions_account_id
    ON transactions (account_id);

CREATE INDEX idx_transactions_from_account_id
    ON transactions (from_account_id);

CREATE INDEX idx_transactions_to_account_id
    ON transactions (to_account_id);

CREATE INDEX idx_transactions_category_id
    ON transactions (category_id)
    WHERE category_id IS NOT NULL;

CREATE INDEX idx_transactions_family_date_active
    ON transactions (family_id, transaction_date DESC)
    WHERE is_deleted = false;

CREATE INDEX idx_transactions_family_type_date_active
    ON transactions (family_id, transaction_type, transaction_date)
    WHERE is_deleted = false;

CREATE INDEX idx_transactions_account_date_active
    ON transactions (account_id, transaction_date DESC)
    WHERE is_deleted = false;

CREATE INDEX idx_transactions_family_category_date_active
    ON transactions (family_id, category_id, transaction_date)
    WHERE category_id IS NOT NULL
      AND is_deleted = false;
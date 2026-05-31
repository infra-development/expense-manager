-- Phase 2: accounts table

CREATE TABLE accounts (
    id              UUID            PRIMARY KEY DEFAULT gen_random_uuid(),
    family_id       UUID            NOT NULL,
    name            VARCHAR(100)    NOT NULL,
    account_type    account_type    NOT NULL,
    currency_code   CHAR(3)         NOT NULL DEFAULT 'INR',
    opening_balance NUMERIC(19, 4)  NOT NULL DEFAULT 0,
    is_deleted      BOOLEAN         NOT NULL DEFAULT false,
    deleted_at      TIMESTAMPTZ,
    created_at      TIMESTAMPTZ     NOT NULL DEFAULT now(),
    updated_at      TIMESTAMPTZ     NOT NULL DEFAULT now(),
    CONSTRAINT fk_accounts_family
        FOREIGN KEY (family_id) REFERENCES families (id) ON DELETE RESTRICT,
    CONSTRAINT chk_accounts_currency_code
        CHECK (currency_code ~ '^[A-Z]{3}$')
);

CREATE UNIQUE INDEX uk_accounts_family_name_active
    ON accounts (family_id, name)
    WHERE is_deleted = false;

CREATE INDEX idx_accounts_family_id ON accounts (family_id);

CREATE INDEX idx_accounts_family_type_active
    ON accounts (family_id, account_type)
    WHERE is_deleted = false;

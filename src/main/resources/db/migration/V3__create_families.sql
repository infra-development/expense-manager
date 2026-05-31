-- Phase 2: families table

CREATE TABLE families (
    id                      UUID            PRIMARY KEY DEFAULT gen_random_uuid(),
    name                    VARCHAR(100)    NOT NULL,
    default_currency_code   CHAR(3)         NOT NULL DEFAULT 'INR',
    is_deleted              BOOLEAN         NOT NULL DEFAULT false,
    deleted_at              TIMESTAMPTZ,
    created_at              TIMESTAMPTZ     NOT NULL DEFAULT now(),
    updated_at              TIMESTAMPTZ     NOT NULL DEFAULT now(),

    CONSTRAINT chk_families_currency_code
        CHECK (default_currency_code ~ '^[A-Z]{3}$')
);


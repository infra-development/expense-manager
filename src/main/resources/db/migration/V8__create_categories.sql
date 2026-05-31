-- Phase 2: categories table

CREATE TABLE categories (
    id          UUID            PRIMARY KEY DEFAULT gen_random_uuid(),
    scope       category_scope  NOT NULL,
    family_id   UUID,
    user_id     UUID,
    name        VARCHAR(100)    NOT NULL,
    description TEXT,
    is_deleted  BOOLEAN         NOT NULL DEFAULT false,
    deleted_at  TIMESTAMPTZ,
    created_at  TIMESTAMPTZ     NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ     NOT NULL DEFAULT now(),
    CONSTRAINT fk_categories_family
        FOREIGN KEY (family_id) REFERENCES families (id) ON DELETE RESTRICT,
    CONSTRAINT fk_categories_user
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE RESTRICT,
    CONSTRAINT chk_categories_scope_consistency CHECK (
        (scope = 'SYSTEM' AND family_id IS NULL AND user_id IS NULL)
        OR (scope = 'FAMILY' AND family_id IS NOT NULL AND user_id IS NULL)
        OR (scope = 'USER' AND family_id IS NULL AND user_id IS NOT NULL)
    )
);

CREATE UNIQUE INDEX uk_categories_system_name_active
    ON categories (name)
    WHERE scope = 'SYSTEM' AND is_deleted = false;

CREATE UNIQUE INDEX uk_categories_family_name_active
    ON categories (family_id, name)
    WHERE scope = 'FAMILY' AND is_deleted = false;

CREATE UNIQUE INDEX uk_categories_user_name_active
    ON categories (user_id, name)
    WHERE scope = 'USER' AND is_deleted = false;

CREATE INDEX idx_categories_family_scope_active
    ON categories (family_id)
    WHERE scope = 'FAMILY' AND is_deleted = false;

CREATE INDEX idx_categories_user_scope_active
    ON categories (user_id)
    WHERE scope = 'USER' AND is_deleted = false;


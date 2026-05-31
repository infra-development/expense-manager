-- Phase 2: family_members table

CREATE TABLE family_members (
    id          UUID            PRIMARY KEY DEFAULT gen_random_uuid(),
    family_id   UUID            NOT NULL,
    user_id     UUID            NOT NULL,
    role        family_role     NOT NULL,
    is_deleted  BOOLEAN         NOT NULL DEFAULT false,
    deleted_at  TIMESTAMPTZ,
    created_at  TIMESTAMPTZ     NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ     NOT NULL DEFAULT now(),
    CONSTRAINT fk_family_members_family
        FOREIGN KEY (family_id) REFERENCES families (id) ON DELETE RESTRICT,
    CONSTRAINT fk_family_members_user
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE RESTRICT
);

CREATE UNIQUE INDEX uk_family_members_family_user_active
    ON family_members (family_id, user_id)
    WHERE is_deleted = false;

CREATE INDEX idx_family_members_user_id ON family_members (user_id);


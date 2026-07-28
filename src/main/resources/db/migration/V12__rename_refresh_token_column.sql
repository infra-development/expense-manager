ALTER TABLE refresh_tokens
    RENAME COLUMN token TO token_hash;

ALTER TABLE refresh_tokens
    RENAME CONSTRAINT uk_refresh_tokens_token
        TO uk_refresh_tokens_token_hash;
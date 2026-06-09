package com.expensemanager.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Base class for entities that support logical deletion.
 *
 * Matches Flyway columns:
 * is_deleted, deleted_at
 */
@Getter
@Setter
@MappedSuperclass
public abstract class SoftDeletableEntity extends BaseEntity {

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    public void softDelete() {
        this.isDeleted = true;
        this.deletedAt = Instant.now();
    }

    public void restore() {
        this.isDeleted = false;
        this.deletedAt = null;
    }
}
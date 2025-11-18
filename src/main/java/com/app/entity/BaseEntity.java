package com.app.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
@MappedSuperclass
public class BaseEntity {
    @CreatedDate
    @Column(name="created_At", nullable=false, updatable=false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name="updated_At", nullable=false, updatable=false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

    }
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();

    }


}

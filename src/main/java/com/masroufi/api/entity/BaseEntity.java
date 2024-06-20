package com.masroufi.api.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @Column
    private String uuid;

    @Column
    private Long createdBy;

    @Column
    private Date createdAt;

    @Column
    private Boolean isDeleted;

    @PrePersist
    public void prePersist() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
            this.createdAt = new Date();
            this.isDeleted = false;
        }
    }
}

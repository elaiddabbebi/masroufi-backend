package com.masroufi.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
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
        }
    }
}

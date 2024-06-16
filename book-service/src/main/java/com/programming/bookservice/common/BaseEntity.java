package com.programming.bookservice.common;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@SuperBuilder
public abstract class BaseEntity {

    @Version
    private Integer version;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date lastModifiedDate;

    @LastModifiedBy
    private String lastModifiedBy;

    public BaseEntity() {
    }

    public BaseEntity(Integer version, Date createdDate, String createdBy, Date lastModifiedDate, String lastModifiedBy) {
        this.version = version;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastModifiedDate = lastModifiedDate;
        this.lastModifiedBy = lastModifiedBy;
    }
}

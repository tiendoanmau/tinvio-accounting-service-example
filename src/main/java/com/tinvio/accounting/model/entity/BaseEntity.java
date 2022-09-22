package com.tinvio.accounting.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import java.sql.Timestamp;

@Data
@SuperBuilder
@NoArgsConstructor
public class BaseEntity {
    @Column(name = "created_at")
    public Timestamp createdAt;

    @Column(name = "created_by")
    public String createdBy;

    @Column(name = "updated_at")
    public Timestamp updatedAt;

    @Column(name = "updated_by")
    public String updatedBy;
}

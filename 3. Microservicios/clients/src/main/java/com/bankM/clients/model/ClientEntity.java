package com.bankM.clients.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@PrimaryKeyJoinColumn(name = "id")
@Table(name = "t_client", schema = "bank")
public class ClientEntity extends PersonEntity {
    private String password;@Column(name = "status")
    protected String status;
    @Column(name = "last_modified_date")
    protected Date lastModifiedDate;

    @PrePersist
    public void setAuditToNew() {
        this.lastModifiedDate = new Date();
        this.status = "1";
    }

    @PreUpdate
    public void setAuditToUpdate() {
        this.lastModifiedDate = new Date();
    }
}

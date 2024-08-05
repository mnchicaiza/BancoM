package com.bankM.transactions.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "t_account", schema = "bank")
public class AccountEntity extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
    @SequenceGenerator(name = "account_seq", schema = "bank", sequenceName = "bank.s_account", allocationSize = 1)
    @Column(name = "account_id")
    private Integer accountId;
    @Column(name = "client_id")
    private Integer clientId;
    @Column(name = "type_account")
    private String typeAccount;
    @Column(name = "balance")
    private BigDecimal balance;
}

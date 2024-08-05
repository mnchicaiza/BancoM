package com.bankM.transactions.model;

import com.bankM.transactions.model.enums.TransactionTypes;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "t_transactions", schema = "bank")
public class TransactionEntity extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transactions_seq")
    @SequenceGenerator(name = "transactions_seq", schema = "bank", sequenceName = "bank.s_transactions", allocationSize = 1)
    @Column(name = "transaction_id")
    private Integer transactionId;
    @Column(name = "account_id",insertable = false, updatable = false)
    private Integer accountId;
    private Date date;
    private BigDecimal amount;
    @Column(name = "balance_previous")
    private BigDecimal balancePrevious;
    @Column(name = "balance_final")
    private BigDecimal balanceFinal;
    @Enumerated(EnumType.STRING)
    private TransactionTypes type;

    @JoinColumn(name = "account_id", nullable = false)
    @ManyToOne
    private AccountEntity accountEntity;

}

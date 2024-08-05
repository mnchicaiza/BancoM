package com.bankM.transactions.vo;

import com.bankM.transactions.model.enums.TransactionTypes;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {
    private Integer transactionId;
    private Integer accountId;
    private String typeAccount;
    private BigDecimal balanceFinal;
    private BigDecimal amount;
    private TransactionTypes type;
}

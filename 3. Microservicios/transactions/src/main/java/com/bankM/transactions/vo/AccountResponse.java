package com.bankM.transactions.vo;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {
    private Integer accountId;
    private String nameClient;
    private String typeAccount;
    private BigDecimal balance;
    private String status;
}

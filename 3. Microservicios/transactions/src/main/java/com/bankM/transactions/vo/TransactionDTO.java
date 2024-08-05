package com.bankM.transactions.vo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {
    @NotNull
    private Integer accountId;
    @NotNull
    private BigDecimal amount;
}

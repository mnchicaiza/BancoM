package com.bankM.transactions.vo;

import com.bankM.transactions.model.enums.TransactionTypes;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response {
    private String date;
    private String nameClient;
    private Integer accountId;
    private String typeAccount;
    private BigDecimal balancePrevious;
    private BigDecimal amount;
    private BigDecimal balanceFinal;
    private TransactionTypes type;
}

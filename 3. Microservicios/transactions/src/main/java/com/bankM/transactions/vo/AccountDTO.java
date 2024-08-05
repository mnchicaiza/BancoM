package com.bankM.transactions.vo;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO {

    @NotBlank(message = "Ci de cliente no puede ser vacío")
    @Size(min=10, max=10, message = "Ci no valida, ingrese nuevamente")
    private String ci;
    @NotBlank(message = "Tipo de cuenta de cliente no puede ser vacío")
    private String typeAccount;
    @NotBlank(message = "Se requiere estado")
    private String status;

}

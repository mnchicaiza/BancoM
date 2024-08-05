package com.bankM.transactions.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientToTransactionResponse {
    private Integer id;
    private String name;
    private String ci;
    private String gender;
    private Integer age;
    private String address;
    private String phone;
}

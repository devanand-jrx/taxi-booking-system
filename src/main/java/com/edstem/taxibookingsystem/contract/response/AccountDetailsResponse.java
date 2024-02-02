package com.edstem.taxibookingsystem.contract.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailsResponse {

    private Long userId;
    private String name;
    private String email;
    private Long accountBalance;
}

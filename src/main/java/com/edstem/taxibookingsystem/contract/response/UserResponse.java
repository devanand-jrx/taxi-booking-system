package com.edstem.taxibookingsystem.contract.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserResponse {
    private Long userId;
    private String name;
    private String email;
    private String password;
}

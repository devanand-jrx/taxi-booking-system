package com.edstem.taxibookingsystem.contract.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaxiResponse {

    private String driverName;
    private Long licenseNumber;
    private String currentLocation;
}

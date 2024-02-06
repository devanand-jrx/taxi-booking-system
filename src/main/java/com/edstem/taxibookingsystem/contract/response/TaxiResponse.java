package com.edstem.taxibookingsystem.contract.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaxiResponse {

    private String taxiId;
    private String taxiName;
    private String driverName;
    private String licenseNumber;
    private String currentLocation;
}

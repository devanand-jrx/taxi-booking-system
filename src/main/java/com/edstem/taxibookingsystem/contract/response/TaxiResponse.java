package com.edstem.taxibookingsystem.contract.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaxiResponse {

    private Long taxiId;
    private String taxiName;
    private String driverName;
    private Long licenseNumber;
    private String currentLocation;
}

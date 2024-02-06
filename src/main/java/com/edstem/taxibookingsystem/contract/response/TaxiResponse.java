package com.edstem.taxibookingsystem.contract.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaxiResponse {

    private String taxiId;
    private String taxiName;
    private String driverName;
    private String licenseNumber;
    private String currentLocation;
}

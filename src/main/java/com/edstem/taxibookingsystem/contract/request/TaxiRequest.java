package com.edstem.taxibookingsystem.contract.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaxiRequest {

    private String taxiName;

    private String driverName;
    private Long licenseNumber;
    private String currentLocation;
}

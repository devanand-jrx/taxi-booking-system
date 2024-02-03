package com.edstem.taxibookingsystem.contract.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaxiRequest {

    private String taxiName;

    private String driverName;
    private Long licenseNumber;
    private String currentLocation;
}

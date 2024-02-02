package com.edstem.taxibookingsystem.contract.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NearestTaxiResponse {

    private String taxiName;
    private String currentLocation;

}

package com.edstem.taxibookingsystem.contract.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {

    private String pickupLocation;
    private String dropOffLocation;


}

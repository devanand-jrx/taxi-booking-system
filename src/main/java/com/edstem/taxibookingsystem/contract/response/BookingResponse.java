package com.edstem.taxibookingsystem.contract.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private String taxiId;
    private String userId;
    private String bookingId;
    private String pickupLocation;
    private String dropOffLocation;
    private String bookingTime;
    private String status;
}

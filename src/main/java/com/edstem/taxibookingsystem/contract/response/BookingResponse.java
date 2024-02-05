package com.edstem.taxibookingsystem.contract.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BookingResponse {
    private String taxiId;
    private String bookingId;
    private String pickupLocation;
    private String dropOffLocation;
    private String bookingTime;
    private String status;
}

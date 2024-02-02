package com.edstem.taxibookingsystem.contract.response;

import com.edstem.taxibookingsystem.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private String id;
    private String pickupLocation;
    private String dropOffLocation;
    private String bookingTime;
    private Status status;
}


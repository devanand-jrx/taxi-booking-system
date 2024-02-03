package com.edstem.taxibookingsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.edstem.taxibookingsystem.contract.request.DistanceRequest;
import com.edstem.taxibookingsystem.contract.response.BillingResponse;
import com.edstem.taxibookingsystem.model.Booking;
import com.edstem.taxibookingsystem.repository.BookingRepository;
import com.edstem.taxibookingsystem.repository.TaxiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

public class BillingServiceTest {

    @InjectMocks BillingService billingService;

    @Mock BookingRepository bookingRepository;
    @Mock TaxiRepository taxiRepository;

    @Mock ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void fareCalculateTest() {
        DistanceRequest distanceRequest = new DistanceRequest(14D);
        double minimumCharge = 22.00;
        Booking billing =
                Booking.builder()
                        .fare(distanceRequest.getDistance() * minimumCharge)
                        .build();

        BillingResponse expectedBookingResponse = modelMapper.map(billing, BillingResponse.class);
        when(bookingRepository.save(any(Booking.class))).thenReturn(billing);

        BillingResponse actualBookingResponse = billingService.fareCalculate(distanceRequest);
        assertEquals(expectedBookingResponse, actualBookingResponse);
    }
}

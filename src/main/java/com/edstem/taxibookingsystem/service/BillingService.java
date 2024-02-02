package com.edstem.taxibookingsystem.service;

import com.edstem.taxibookingsystem.contract.request.BillingRequest;
import com.edstem.taxibookingsystem.contract.request.DistanceRequest;
import com.edstem.taxibookingsystem.contract.response.BillingResponse;
import com.edstem.taxibookingsystem.model.Booking;
import com.edstem.taxibookingsystem.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;

    public BillingResponse fareCalculate(DistanceRequest distanceRequest){
        Booking billing = Booking.builder()
                .fare(distanceRequest.getDistance()*22)
                .build();
        billing = bookingRepository.save(billing);
        return modelMapper.map(billing, BillingResponse.class);

    }

}

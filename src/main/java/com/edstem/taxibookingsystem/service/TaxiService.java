package com.edstem.taxibookingsystem.service;

import com.edstem.taxibookingsystem.contract.request.TaxiRequest;
import com.edstem.taxibookingsystem.contract.response.TaxiResponse;
import com.edstem.taxibookingsystem.model.Taxi;
import com.edstem.taxibookingsystem.repository.TaxiRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaxiService {
    private final TaxiRepository taxiRepository;
    private final ModelMapper modelMapper;

    public TaxiResponse addTaxi(TaxiRequest taxiRequest){

        Taxi taxi = Taxi.builder()
                .taxiName(taxiRequest.getTaxiName())
                .driverName(taxiRequest.getDriverName())
                .licenseNumber(taxiRequest.getLicenseNumber())
                .currentLocation(taxiRequest.getCurrentLocation())
                .build();
        taxi =   taxiRepository.save(taxi);
        return modelMapper.map(taxi, TaxiResponse.class);
    }



}

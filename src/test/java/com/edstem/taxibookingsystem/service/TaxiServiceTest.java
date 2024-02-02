package com.edstem.taxibookingsystem.service;

import com.edstem.taxibookingsystem.contract.request.TaxiRequest;
import com.edstem.taxibookingsystem.contract.response.TaxiResponse;
import com.edstem.taxibookingsystem.model.Booking;
import com.edstem.taxibookingsystem.model.Taxi;
import com.edstem.taxibookingsystem.repository.BookingRepository;
import com.edstem.taxibookingsystem.repository.TaxiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TaxiServiceTest {

    @InjectMocks
    TaxiService taxiService;


    @Mock
    TaxiRepository taxiRepository;

    @Mock
    ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddTaxi(){
        TaxiRequest taxiRequest = new TaxiRequest("Polo","Tommy",43333L,"Kakkand");
        Taxi taxi = modelMapper.map(taxiRequest, Taxi.class);
        TaxiResponse expectedTaxiResponse = modelMapper.map(taxi, TaxiResponse.class);
        when(taxiRepository.save(any(Taxi.class))).thenReturn(taxi);

        TaxiResponse actualTaxiResponse = taxiService.addTaxi(taxiRequest);
        assertEquals(expectedTaxiResponse, actualTaxiResponse);

    }
}

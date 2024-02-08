package com.edstem.taxibookingsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.edstem.taxibookingsystem.contract.request.TaxiRequest;
import com.edstem.taxibookingsystem.contract.response.TaxiResponse;
import com.edstem.taxibookingsystem.exception.UserNotFoundException;
import com.edstem.taxibookingsystem.model.Taxi;
import com.edstem.taxibookingsystem.model.User;
import com.edstem.taxibookingsystem.repository.TaxiRepository;
import com.edstem.taxibookingsystem.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

public class TaxiServiceTest {

    @InjectMocks TaxiService taxiService;

    @Mock TaxiRepository taxiRepository;
    @Mock UserRepository userRepository;

    @Mock ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddTaxi() {
        Long userId = 1L;
        TaxiRequest taxiRequest = new TaxiRequest("Polo", "Tommy", 43333L, "Kakkand");

        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Taxi taxi = modelMapper.map(taxiRequest, Taxi.class);
        TaxiResponse expectedTaxiResponse = modelMapper.map(taxi, TaxiResponse.class);

        when(taxiRepository.save(any(Taxi.class))).thenReturn(taxi);

        TaxiResponse actualTaxiResponse = taxiService.addTaxi(userId, taxiRequest);
        assertEquals(expectedTaxiResponse, actualTaxiResponse);
    }

    @Test
    public void testAddTaxi_UserNotFound() {
        Long userId = 1L;
        TaxiRequest taxiRequest = new TaxiRequest("Polo", "Tommy", 43333L, "Kakkand");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> {
                    taxiService.addTaxi(userId, taxiRequest);
                });
    }
}

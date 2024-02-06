package com.edstem.taxibookingsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.edstem.taxibookingsystem.contract.request.DistanceRequest;
import com.edstem.taxibookingsystem.contract.response.AccountDetailsResponse;
import com.edstem.taxibookingsystem.contract.response.BillingResponse;
import com.edstem.taxibookingsystem.exception.InsufficientBalanceException;
import com.edstem.taxibookingsystem.exception.UserNotFoundException;
import com.edstem.taxibookingsystem.model.Booking;
import com.edstem.taxibookingsystem.model.User;
import com.edstem.taxibookingsystem.repository.BookingRepository;
import com.edstem.taxibookingsystem.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

public class BillingServiceTest {

    @InjectMocks BillingService billingService;

    @Mock BookingRepository bookingRepository;

    @Mock UserRepository userRepository;

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
                Booking.builder().fare(distanceRequest.getDistance() * minimumCharge).build();

        BillingResponse expectedBookingResponse = modelMapper.map(billing, BillingResponse.class);
        when(bookingRepository.save(any(Booking.class))).thenReturn(billing);

        BillingResponse actualBookingResponse = billingService.fareCalculate(distanceRequest);
        assertEquals(expectedBookingResponse, actualBookingResponse);
    }

    @Test
    void testBalanceAmount() {
        Long userId = 1L;
        Double accountBalance = 1000.0;
        Double fare = 200.0;

        User user = User.builder().userId(userId).accountBalance(accountBalance).build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User updatedUser =
                User.builder().userId(userId).accountBalance(accountBalance - fare).build();

        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        AccountDetailsResponse response =
                AccountDetailsResponse.builder()
                        .userId(userId.toString())
                        .accountBalance(accountBalance - fare)
                        .build();

        when(modelMapper.map(updatedUser, AccountDetailsResponse.class)).thenReturn(response);

        AccountDetailsResponse actualBalanceAmount =
                billingService.balanceAmount(userId, accountBalance, fare);
        assertEquals(response, actualBalanceAmount);
    }

    @Test
    public void whenInsufficientBalance_thenThrowException() {
        Long userId = 1L;
        Double accountBalance = 100.0;
        Double fare = 200.0;

        User user = User.builder().userId(userId).accountBalance(accountBalance - fare).build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Exception exception =
                assertThrows(
                        InsufficientBalanceException.class,
                        () -> {
                            billingService.balanceAmount(userId, accountBalance, fare);
                        });

        String expectedMessage = "Insufficient balance in the account";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testBalanceAmount_UserNotFound() {
        Long userId = 1L;
        Double accountBalance = 100.0;
        Double fare = 100.0;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> {
                    billingService.balanceAmount(userId, accountBalance, fare);
                });
    }
}

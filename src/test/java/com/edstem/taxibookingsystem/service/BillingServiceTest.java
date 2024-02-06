package com.edstem.taxibookingsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.edstem.taxibookingsystem.constant.Status;
import com.edstem.taxibookingsystem.contract.response.AccountDetailsResponse;
import com.edstem.taxibookingsystem.contract.response.BillingResponse;
import com.edstem.taxibookingsystem.exception.BookingNotFoundException;
import com.edstem.taxibookingsystem.exception.InsufficientBalanceException;
import com.edstem.taxibookingsystem.exception.UserNotFoundException;
import com.edstem.taxibookingsystem.model.Booking;
import com.edstem.taxibookingsystem.model.User;
import com.edstem.taxibookingsystem.repository.BookingRepository;
import com.edstem.taxibookingsystem.repository.UserRepository;
import java.time.LocalTime;
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
    public void testFareCalculate() {
        Long bookingId = 1L;
        Double distance = 10.0;
        double minimumCharge = 22.00;

        Booking booking =
                Booking.builder()
                        .bookingId(bookingId)
                        .dropOffLocation("Location1")
                        .pickupLocation("Location2")
                        .bookingTime(LocalTime.now())
                        .status(Status.CANCEL)
                        .build();

        BillingResponse billingResponse =
                BillingResponse.builder().fare(distance * minimumCharge).build();

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        when(modelMapper.map(any(Booking.class), eq(BillingResponse.class)))
                .thenReturn(billingResponse);

        BillingResponse result = billingService.fareCalculate(bookingId, distance);

        assertEquals(distance * minimumCharge, result.getFare());
    }

    @Test
    public void testFareCalculate_BookingNotFoundException() {
        Long bookingId = 1L;
        Double distance = 10.0;

        when(bookingRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(
                BookingNotFoundException.class,
                () -> {
                    billingService.fareCalculate(bookingId, distance);
                });
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

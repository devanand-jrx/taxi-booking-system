package com.edstem.taxibookingsystem.service;

import com.edstem.taxibookingsystem.constant.Status;
import com.edstem.taxibookingsystem.contract.request.BookingRequest;
import com.edstem.taxibookingsystem.contract.request.DistanceRequest;
import com.edstem.taxibookingsystem.contract.response.BillingResponse;
import com.edstem.taxibookingsystem.contract.response.BookingResponse;
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

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BookingServiceTest {

    @InjectMocks
    BookingService bookingService;

    @InjectMocks
    BillingService billingService;

    @Mock
    BookingRepository bookingRepository;
    @Mock
    TaxiRepository taxiRepository;

    @Mock
    ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddBooking() {
        // Arrange
        Long taxiId = 1L;
        BookingRequest bookingRequest = new BookingRequest("pickup test", "dropoff test");
        Taxi nearestTaxi = Taxi.builder().build();
        Booking booking = Booking.builder()
                .pickupLocation(bookingRequest.getPickupLocation())
                .dropOffLocation(bookingRequest.getDropOffLocation())
                .bookingTime(LocalTime.parse(LocalTime.now().toString()))
                .status(Status.BOOKED)
                .taxi(nearestTaxi)
                .build();
        BookingResponse expectedResponse = modelMapper.map(booking, BookingResponse.class);
        when(taxiRepository.findById(any(Long.class))).thenReturn(Optional.of(nearestTaxi));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        // Act
        BookingResponse actualResponse = bookingService.addBooking(taxiId, bookingRequest);

        // Assert
        assertEquals(expectedResponse, actualResponse);
    }


    @Test
    void testViewBooking() {
        Booking booking = new Booking();
        BookingResponse expectedResponse = modelMapper.map(booking, BookingResponse.class);
        when(bookingRepository.findById(any(Long.class))).thenReturn(Optional.of(booking));
        BookingResponse actualResponse = bookingService.viewBooking(1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testUpdateBooking() {
        Booking booking = new Booking();
        Booking.builder()
                .status(Status.CANCEL)
                .build();        BookingResponse expectedResponse = modelMapper.map(booking, BookingResponse.class);
        when(bookingRepository.findById(any(Long.class))).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        BookingResponse actualResponse = bookingService.updateBooking(1L);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testCancelBooking(){
        Long bookingId = 1L;
        Booking booking = new Booking(bookingId, "ernakulam test", "kakanad", 55.00, LocalTime.now(), Status.BOOKED, null,null);
        when(bookingRepository.existsById(bookingId)).thenReturn(true);
        bookingService.cancelBooking(bookingId);
    }





}

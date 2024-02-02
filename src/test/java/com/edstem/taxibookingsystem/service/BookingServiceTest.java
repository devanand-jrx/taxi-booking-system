package com.edstem.taxibookingsystem.service;

import com.edstem.taxibookingsystem.constant.Status;
import com.edstem.taxibookingsystem.contract.request.BookingRequest;
import com.edstem.taxibookingsystem.contract.response.BookingResponse;
import com.edstem.taxibookingsystem.model.Booking;
import com.edstem.taxibookingsystem.repository.BookingRepository;
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

    @Mock
    BookingRepository bookingRepository;

    @Mock
    ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddBooking(){
        BookingRequest bookingRequest = new BookingRequest("ernakulam test", "kakanad");
        Booking booking = modelMapper.map(bookingRequest, Booking.class);
        BookingResponse expectedResponse = modelMapper.map(booking, BookingResponse.class);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        BookingResponse actualResponse = bookingService.addBooking(bookingRequest);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testViewBooking(){
        Long bookingId = 1L;
        Booking booking = new Booking(bookingId, "ernakulam test", "kakanad", 55.00, LocalTime.now(), Status.BOOKED, null,null);
        BookingResponse expectedResponse = modelMapper.map(booking, BookingResponse.class);
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        BookingResponse actualResponse = bookingService.viewBooking(bookingId);
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

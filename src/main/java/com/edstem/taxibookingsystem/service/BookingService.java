package com.edstem.taxibookingsystem.service;

import com.edstem.taxibookingsystem.constant.Status;
import com.edstem.taxibookingsystem.contract.request.BookingRequest;
import com.edstem.taxibookingsystem.contract.response.BookingResponse;
import com.edstem.taxibookingsystem.exception.BookingNotFoundException;
import com.edstem.taxibookingsystem.exception.TaxiNotFoundException;
import com.edstem.taxibookingsystem.exception.UserNotFoundException;
import com.edstem.taxibookingsystem.model.Booking;
import com.edstem.taxibookingsystem.model.Taxi;
import com.edstem.taxibookingsystem.model.User;
import com.edstem.taxibookingsystem.repository.BookingRepository;
import com.edstem.taxibookingsystem.repository.TaxiRepository;
import com.edstem.taxibookingsystem.repository.UserRepository;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    private final TaxiRepository taxiRepository;

    private final ModelMapper modelMapper;

    public BookingResponse addBooking(Long userId, Long taxiId, BookingRequest bookingRequest) {

        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new UserNotFoundException("User not found"));

        Taxi nearestTaxi =
                taxiRepository
                        .findById(taxiId)
                        .orElseThrow(() -> new TaxiNotFoundException("Taxi not found"));

        Booking booking =
                Booking.builder()
                        .user(user)
                        .pickupLocation(bookingRequest.getPickupLocation())
                        .dropOffLocation(bookingRequest.getDropOffLocation())
                        .bookingTime(LocalTime.parse(LocalTime.now().toString()))
                        .status(Status.BOOKED)
                        .taxi(nearestTaxi)
                        .build();
        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingResponse.class);
    }

    public BookingResponse viewBooking(Long bookingId) {
        Booking booking =
                bookingRepository
                        .findById(bookingId)
                        .orElseThrow(() -> new BookingNotFoundException("Booking Not Found"));
        return modelMapper.map(booking, BookingResponse.class);
    }

    public BookingResponse updateBooking(Long bookingId) {
        Booking booking =
                bookingRepository
                        .findById(bookingId)
                        .orElseThrow(() -> new BookingNotFoundException("Booking Not Found"));
        booking =
                Booking.builder()
                        .user(booking.getUser())
                        .taxi(booking.getTaxi())
                        .bookingId(booking.getBookingId())
                        .dropOffLocation(booking.getDropOffLocation())
                        .pickupLocation(booking.getPickupLocation())
                        .bookingTime(booking.getBookingTime())
                        .status(Status.CANCEL)
                        .build();
        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingResponse.class);
    }
}

package com.edstem.taxibookingsystem.service;

import com.edstem.taxibookingsystem.constant.Status;
import com.edstem.taxibookingsystem.contract.request.BookingRequest;
import com.edstem.taxibookingsystem.contract.response.BookingResponse;
import com.edstem.taxibookingsystem.contract.response.NearestTaxiResponse;
import com.edstem.taxibookingsystem.exception.BookingNotFoundException;
import com.edstem.taxibookingsystem.exception.TaxiNotFoundException;
import com.edstem.taxibookingsystem.model.Booking;
import com.edstem.taxibookingsystem.model.Taxi;
import com.edstem.taxibookingsystem.repository.BookingRepository;
import com.edstem.taxibookingsystem.repository.TaxiRepository;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TaxiRepository taxiRepository;
    private final ModelMapper modelMapper;

    public BookingResponse addBooking(Long taxiId, BookingRequest bookingRequest) {

        Taxi nearestTaxi =
                taxiRepository
                        .findById(taxiId)
                        .orElseThrow(() -> new TaxiNotFoundException("Taxi not found"));

        Booking booking =
                Booking.builder()
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
                        .bookingId(booking.getBookingId())
                        .dropOffLocation(booking.getDropOffLocation())
                        .pickupLocation(booking.getPickupLocation())
                        .bookingTime(booking.getBookingTime())
                        .status(Status.CANCEL)
                        .build();
        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingResponse.class);
    }

    public void cancelBooking(Long bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new BookingNotFoundException("Booking Not Found");
        }
        bookingRepository.deleteById(bookingId);
    }

    public NearestTaxiResponse nearestTaxi(Long bookingId, Long taxiId) {
        Booking booking =
                bookingRepository
                        .findById(bookingId)
                        .orElseThrow(() -> new BookingNotFoundException("Booking Not Found"));
        booking = Booking.builder().pickupLocation(booking.getPickupLocation()).build();

        Taxi taxi =
                taxiRepository
                        .findById(taxiId)
                        .orElseThrow(() -> new TaxiNotFoundException("Taxi Not Found"));
        taxi = Taxi.builder().currentLocation(taxi.getCurrentLocation()).build();

        return modelMapper.map(booking, NearestTaxiResponse.class);
    }
}

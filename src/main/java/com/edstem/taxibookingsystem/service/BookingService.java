package com.edstem.taxibookingsystem.service;

import com.edstem.taxibookingsystem.constant.Status;
import com.edstem.taxibookingsystem.contract.request.BookingRequest;
import com.edstem.taxibookingsystem.contract.response.BookingResponse;
import com.edstem.taxibookingsystem.exception.BookingNotFoundException;
import com.edstem.taxibookingsystem.model.Booking;
import com.edstem.taxibookingsystem.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;

    public BookingResponse addBooking(BookingRequest bookingRequest){
        Booking booking  =Booking.builder()
                .pickupLocation(bookingRequest.getPickupLocation())
                .dropOffLocation(bookingRequest.getDropOffLocation())
                .bookingTime(LocalTime.parse(LocalTime.now().toString()))
                .status(Status.BOOKED)
                .build();
        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingResponse.class);
    }


    public BookingResponse viewBooking(Long bookingId){
        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking Not Found"));
        return modelMapper.map(booking, BookingResponse.class);
    }

    public void cancelBooking(Long bookingId){
        if(!bookingRepository.existsById(bookingId)){
            throw new BookingNotFoundException("Booking Not Found");
        }
        bookingRepository.deleteById(bookingId);
    }


}

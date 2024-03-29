package com.edstem.taxibookingsystem.controller;

import com.edstem.taxibookingsystem.contract.request.BookingRequest;
import com.edstem.taxibookingsystem.contract.response.BookingResponse;
import com.edstem.taxibookingsystem.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    @Autowired private BookingService bookingService;

    @PostMapping("/{userId}/{taxiId}")
    public BookingResponse addBooking(
            @PathVariable Long userId,
            @PathVariable Long taxiId,
            @RequestBody BookingRequest bookingRequest) {
        return bookingService.addBooking(userId, taxiId, bookingRequest);
    }

    @GetMapping("/{bookingId}")
    public BookingResponse viewBooking(@PathVariable Long bookingId) {
        return bookingService.viewBooking(bookingId);
    }

    @PutMapping("/{bookingId}")
    public BookingResponse updateBooking(@PathVariable Long bookingId) {
        return bookingService.updateBooking(bookingId);
    }
}

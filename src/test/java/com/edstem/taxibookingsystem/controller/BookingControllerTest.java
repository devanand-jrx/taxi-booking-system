package com.edstem.taxibookingsystem.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.edstem.taxibookingsystem.constant.Status;
import com.edstem.taxibookingsystem.contract.response.BookingResponse;
import com.edstem.taxibookingsystem.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest {
    @Autowired private MockMvc mockMvc;

    @MockBean private BookingService bookingService;

    @Autowired ObjectMapper mapper = new ObjectMapper();

    //    @Test
    //    void testAddBooking() throws Exception {
    //        BookingResponse expectedResponse = BookingResponse.builder()
    //                .taxiId("1L")
    //                .bookingId("2L")
    //                .pickupLocation("ernakulam")
    //                .dropOffLocation("kakkanad")
    //                .bookingTime(LocalTime.now().toString())
    //                .status(Status.BOOKED)
    //                .build();
    //
    // when(bookingService.addBooking(any(Long.class))),any(BookingRequest.class).thenReturn(expectedResponse);

    //        BookingRequest bookingRequest = new BookingRequest("ernakulam test", "kakanad");

    //        BookingResponse expectedResponse = new BookingResponse("1L", "2L", "ernakulam test",
    // "kakanad", LocalTime.now().toString(), Status.BOOKED);
    //
    //
    //        mockMvc.perform(
    //                        post("/bookings")
    //                            .contentType(MediaType.APPLICATION_JSON)
    //                            .content(new ObjectMapper().writeValueAsString(bookingRequest)))
    //                .andDo(print())
    //                .andExpect(status().isOk())
    //                .andExpect(content().json(new
    // ObjectMapper().writeValueAsString(expectedResponse)));
    //
    //    }

    @Test
    void testViewBooking() throws Exception {
        Long bookingId = 1L;

        BookingResponse expectedResponse =
                new BookingResponse(
                        "2L",
                        "1L",
                        "ernakulam test",
                        "kakanad",
                        LocalTime.now().toString(),
                        Status.BOOKED);
        when(bookingService.viewBooking(bookingId)).thenReturn(expectedResponse);

        mockMvc.perform(get("/bookings/" + bookingId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }

    @Test
    void testCancelBooking() throws Exception {
        Long bookingId = 1L;

        doNothing().when(bookingService).cancelBooking(bookingId);

        mockMvc.perform(delete("/bookings/" + bookingId).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}

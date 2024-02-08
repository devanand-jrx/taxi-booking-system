package com.edstem.taxibookingsystem.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.edstem.taxibookingsystem.contract.response.AccountDetailsResponse;
import com.edstem.taxibookingsystem.contract.response.BillingResponse;
import com.edstem.taxibookingsystem.service.BillingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class BillingControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private BillingService billingService;

    @Autowired ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testFareCalculate() throws Exception {
        Long bookingId = 1L;
        Double distance = 10.0;
        Double expectedFare = 100.0;

        when(billingService.fareCalculate(bookingId, distance))
                .thenReturn(new BillingResponse(expectedFare));

        mockMvc.perform(
                        put("/billings")
                                .param("bookingId", bookingId.toString())
                                .param("distance", distance.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fare", is(expectedFare)));
    }

    @Test
    public void testBalanceAmount() throws Exception {
        Long userId = 1L;
        Double accountBalance = 100.0;
        Double fare = 50.0;
        AccountDetailsResponse expectedResponse = new AccountDetailsResponse();

        when(billingService.balanceAmount(eq(userId), eq(accountBalance), eq(fare)))
                .thenReturn(expectedResponse);

        mockMvc.perform(
                        get("/billings/" + userId)
                                .param("accountBalance", accountBalance.toString())
                                .param("fare", fare.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }
}

package com.edstem.taxibookingsystem.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.edstem.taxibookingsystem.contract.request.DistanceRequest;
import com.edstem.taxibookingsystem.contract.response.AccountDetailsResponse;
import com.edstem.taxibookingsystem.contract.response.BillingResponse;
import com.edstem.taxibookingsystem.service.BillingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class BillingControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private BillingService billingService;

    @Autowired ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testFareCalculate() throws Exception {
        DistanceRequest distanceRequest = new DistanceRequest();
        BillingResponse expectedResponse = new BillingResponse();

        when(billingService.fareCalculate(any(DistanceRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(
                        post("/billings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(distanceRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
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

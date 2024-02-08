package com.edstem.taxibookingsystem.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.edstem.taxibookingsystem.contract.request.TaxiRequest;
import com.edstem.taxibookingsystem.contract.response.TaxiResponse;
import com.edstem.taxibookingsystem.service.TaxiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TaxiControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private TaxiService taxiService;

    @Autowired ObjectMapper mapper = new ObjectMapper();

    @Test
    void testAddTaxi() throws Exception {
        Long userId = 1L;
        TaxiRequest taxiRequest = new TaxiRequest("Polo", "Tommy", 43333L, "Kakkand");
        ;
        TaxiResponse expectedResponse =
                new TaxiResponse("1L", "1L", "Polo", "Tommy", "43333L", "Kakkand");
        when(taxiService.addTaxi(any(Long.class), any(TaxiRequest.class)))
                .thenReturn(expectedResponse);

        mockMvc.perform(
                        post("/taxi/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(taxiRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }
}

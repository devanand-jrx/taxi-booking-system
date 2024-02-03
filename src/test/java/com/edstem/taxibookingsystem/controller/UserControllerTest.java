package com.edstem.taxibookingsystem.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.edstem.taxibookingsystem.contract.request.SignupRequest;
import com.edstem.taxibookingsystem.contract.response.UserResponse;
import com.edstem.taxibookingsystem.model.User;
import com.edstem.taxibookingsystem.service.UserService;
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
public class UserControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private UserService userService;

    @Test
    void testSignUp() throws Exception {

        SignupRequest signupRequest = new SignupRequest("vignesh", "vig@Gmail.com", "vig@123");
        User user =
                User.builder()
                        .name(signupRequest.getName())
                        .email(signupRequest.getEmail())
                        .password(signupRequest.getPassword())
                        .build();

        UserResponse expectedResponse =
                UserResponse.builder().email(user.getEmail()).name(user.getName()).build();
        when(userService.signUp(any(SignupRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(
                        post("/user/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"email\":\"vignesh@example.com\",\"name\":\"vignesh\",\"password\":\"vig@123\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }
}

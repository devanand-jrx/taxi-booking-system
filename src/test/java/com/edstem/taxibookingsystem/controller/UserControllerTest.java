package com.edstem.taxibookingsystem.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.edstem.taxibookingsystem.contract.request.LoginRequest;
import com.edstem.taxibookingsystem.contract.request.SignupRequest;
import com.edstem.taxibookingsystem.contract.response.AccountDetailsResponse;
import com.edstem.taxibookingsystem.contract.response.AuthResponse;
import com.edstem.taxibookingsystem.contract.response.UserResponse;
import com.edstem.taxibookingsystem.model.User;
import com.edstem.taxibookingsystem.repository.UserRepository;
import com.edstem.taxibookingsystem.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private UserService userService;

    @Mock private UserRepository userRepository;

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
                        post("/v1/user/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"email\":\"vignesh@example.com\",\"name\":\"vignesh\",\"password\":\"vig@123\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }

    @Test
    void login() throws Exception {
        LoginRequest request = new LoginRequest("devanand@gmail.com", "Deva@123");

        AuthResponse expectedResponse = new AuthResponse("devanand@gmail.com", "Deva@123");

        when(userService.login(any(LoginRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(
                        post("/v1/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"email\":\"test@example.com\",\"password\":\"testPassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(expectedResponse.getName())))
                .andExpect(jsonPath("$.token", is(expectedResponse.getToken())));
    }

    @Test
    public void testAddBalance() throws Exception {
        Long userId = 1L;
        Double accountBalance = 10000.0;

        AccountDetailsResponse expectedResponse =
                AccountDetailsResponse.builder()
                        .userId(userId.toString())
                        .name("devanand")
                        .email("devanand@example.com")
                        .accountBalance(accountBalance)
                        .build();

        when(userService.addBalance(any(Long.class), anyDouble())).thenReturn(expectedResponse);

        mockMvc.perform(
                        put("/v1/" + userId + "/balance")
                                .param("accountBalance", accountBalance.toString())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }

    @Test
    public void testGetAccountDetails() throws Exception {
        Long userId = 1L;

        AccountDetailsResponse expectedResponse =
                new AccountDetailsResponse("1L", "devanand", "devanand@gmail.com", 556D);
        when(userService.getAccountDetails(any(Long.class))).thenReturn(expectedResponse);

        mockMvc.perform(get("/v1/" + userId + "/details"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }
}

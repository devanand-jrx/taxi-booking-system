package com.edstem.taxibookingsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.edstem.taxibookingsystem.contract.request.SignupRequest;
import com.edstem.taxibookingsystem.contract.response.AccountDetailsResponse;
import com.edstem.taxibookingsystem.contract.response.UserResponse;
import com.edstem.taxibookingsystem.model.User;
import com.edstem.taxibookingsystem.repository.UserRepository;
import com.edstem.taxibookingsystem.security.JwtService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {

    @InjectMocks UserService userService;

    @InjectMocks JwtService jwtService;

    @Mock UserRepository userRepository;
    @Mock PasswordEncoder passwordEncoder;

    @Mock ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSignUp() {

        SignupRequest signupRequest = new SignupRequest("vignesh", "vig@Gmail.com", "vig@123");
        User user =
                User.builder()
                        .name(signupRequest.getName())
                        .email(signupRequest.getEmail())
                        .password(passwordEncoder.encode(signupRequest.getPassword()))
                        .build();

        UserResponse expectedResponse =
                UserResponse.builder().email(user.getEmail()).name(user.getName()).build();

        when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(user, UserResponse.class)).thenReturn(expectedResponse);

        UserResponse actualResponse = userService.signUp(signupRequest);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testAddBalance() {
        Long userId = 1L;
        Double accountBalance = 100.00;
        User existingUser = new User();
        existingUser =
                User.builder()
                        .userId(existingUser.getUserId())
                        .name(existingUser.getName())
                        .email(existingUser.getEmail())
                        .accountBalance(accountBalance)
                        .build();
        AccountDetailsResponse expectedResponse =
                modelMapper.map(existingUser, AccountDetailsResponse.class);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        AccountDetailsResponse actualResponse = userService.addBalance(userId, accountBalance);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetAccountDetails() {
        Long userId = 1L;

        User user = new User();
        user =
                User.builder()
                        .userId(1L)
                        .name("devanand")
                        .email("devanand@gmail.com")
                        .accountBalance(123D)
                        .build();

        AccountDetailsResponse expectedResponse = new AccountDetailsResponse();
        expectedResponse =
                AccountDetailsResponse.builder()
                        .userId(user.getUserId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .accountBalance(user.getAccountBalance().longValue())
                        .build();
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(modelMapper.map(user, AccountDetailsResponse.class)).thenReturn(expectedResponse);

        AccountDetailsResponse actualResponse = userService.getAccountDetails(userId);

        assertEquals(expectedResponse, actualResponse);
    }
}

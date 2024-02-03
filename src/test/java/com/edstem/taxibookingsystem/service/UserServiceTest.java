package com.edstem.taxibookingsystem.service;

import com.edstem.taxibookingsystem.contract.request.LoginRequest;
import com.edstem.taxibookingsystem.contract.request.SignupRequest;
import com.edstem.taxibookingsystem.contract.response.AuthResponse;
import com.edstem.taxibookingsystem.contract.response.UserResponse;
import com.edstem.taxibookingsystem.model.User;
import com.edstem.taxibookingsystem.repository.TaxiRepository;
import com.edstem.taxibookingsystem.repository.UserRepository;
import com.edstem.taxibookingsystem.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @InjectMocks
    JwtService jwtService;

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSignUp(){

        SignupRequest signupRequest = new SignupRequest("vignesh","vig@Gmail.com","vig@123");
        User user = User.builder()
                .name(signupRequest.getName())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .build();

        UserResponse expectedResponse = UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();

        when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(user, UserResponse.class)).thenReturn(expectedResponse);

        UserResponse actualResponse = userService.signUp(signupRequest);

        assertEquals(expectedResponse, actualResponse);
    }

//    @Test
//    void testLogin() {
//        User user = new User(1L, "vig", "vig@gmail.com", "vig", 0.0);
//        LoginRequest request = new LoginRequest("sharok@gmail.com", "Helloworld");
//        UserResponse expectedResponse = new ModelMapper().map(request, UserResponse.class);
//
//
//        when(userRepository.findByEmail(request.getEmail())).thenReturn(user);
//        when(!passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);
//
//        UserResponse actualResponse = userService.login(request);
//
//        assertEquals(expectedResponse, actualResponse);
//    }

//    @Test
//    void testLogin() throws Exception {
//
//        LoginRequest loginRequest = new LoginRequest("vig@Gmail.com", "vig@123");
//        User user = User.builder()
//                .email(loginRequest.getEmail())
//                .password(passwordEncoder.encode(loginRequest.getPassword()))
//                .build();
//
//        String mockToken = "mockToken";
//
//
//        AuthResponse expectedResponse = new AuthResponse(mockToken);
//
//        when(userRepository.existsByEmail(loginRequest.getEmail())).thenReturn(true);
//        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(user);
//        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
//        when(jwtService.generateToken(any(User.class))).thenReturn(expectedResponse);
//
//        AuthResponse actualResponse = userService.login(loginRequest);
//
//        assertEquals(expectedResponse, actualResponse);
//    }



}

package com.edstem.taxibookingsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.edstem.taxibookingsystem.contract.response.AuthResponse;
import com.edstem.taxibookingsystem.model.User;
import com.edstem.taxibookingsystem.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class JwtServiceTest {

    @Mock private JwtService jwtService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateTokenTest() {
        User user = new User(1L, "deva", "Deva@gmail.com", "Deva@123", null);

        String token = "token1233559";

        AuthResponse expectedToken = AuthResponse.builder().token(token).build();
        when(jwtService.generateToken(user)).thenReturn(expectedToken);

        AuthResponse actualToken = jwtService.generateToken(user);

        assertEquals(expectedToken, actualToken);
    }
}

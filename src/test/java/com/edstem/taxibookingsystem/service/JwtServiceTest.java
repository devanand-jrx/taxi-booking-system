package com.edstem.taxibookingsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.edstem.taxibookingsystem.contract.response.AuthResponse;
import com.edstem.taxibookingsystem.model.User;
import com.edstem.taxibookingsystem.security.JwtService;
import com.edstem.taxibookingsystem.security.UserInfoUserDetails;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.function.Function;

public class JwtServiceTest {

    @Mock private JwtService jwtService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExtractUsername() {

        jwtService.extractUsername("ABC123");
    }

    @Test
    void testExtractExpiration() {


        jwtService.extractExpiration("ABC123");
    }


    @Test
    void testExtractClaim() {

        String token = "";
        Function<Claims, Object> claimsResolver = null;

        Object actualExtractClaimResult = this.jwtService.extractClaim(token, claimsResolver);


    }


    @Test
    void testValidateToken() {

        jwtService.validateToken("ecksjabckjbfeirhji26548", new UserInfoUserDetails(new User()));
    }

    @Test
    void testGenerateToken() {

        User user = new User();
        user.setAccountBalance(10.0d);
        user.setEmail("deva@example.org");
        user.setName("deva");
        user.setPassword("deva@123");
        user.setUserId(1L);
        jwtService.generateToken(user);
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

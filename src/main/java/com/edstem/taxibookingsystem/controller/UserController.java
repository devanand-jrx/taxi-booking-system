package com.edstem.taxibookingsystem.controller;

import com.edstem.taxibookingsystem.contract.request.LoginRequest;
import com.edstem.taxibookingsystem.contract.request.SignupRequest;
import com.edstem.taxibookingsystem.contract.response.AccountDetailsResponse;
import com.edstem.taxibookingsystem.contract.response.AuthResponse;
import com.edstem.taxibookingsystem.contract.response.UserResponse;
import com.edstem.taxibookingsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user/signup")
    public UserResponse SignUp(@Valid @RequestBody SignupRequest request) {
        return userService.signUp(request);
    }

    @PostMapping("/user/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) throws Exception {
        return userService.login(request);
    }

    @PutMapping("/{userId}/balance")
    public AccountDetailsResponse addBalance(
            @PathVariable Long userId, @RequestParam Double accountBalance) {
        return userService.addBalance(userId, accountBalance);
    }

    @GetMapping("/{userId}/details")
    public AccountDetailsResponse getAccountDetails(@PathVariable Long userId) {
        return userService.getAccountDetails(userId);
    }
}

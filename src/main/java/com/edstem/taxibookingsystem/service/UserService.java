package com.edstem.taxibookingsystem.service;

import com.edstem.taxibookingsystem.contract.request.LoginRequest;
import com.edstem.taxibookingsystem.contract.request.SignupRequest;
import com.edstem.taxibookingsystem.contract.response.AccountDetailsResponse;
import com.edstem.taxibookingsystem.contract.response.AuthResponse;
import com.edstem.taxibookingsystem.contract.response.UserResponse;
import com.edstem.taxibookingsystem.exception.InvalidLoginException;
import com.edstem.taxibookingsystem.exception.UserAlreadyExistsException;
import com.edstem.taxibookingsystem.exception.UserNotFoundException;
import com.edstem.taxibookingsystem.model.User;
import com.edstem.taxibookingsystem.repository.UserRepository;
import com.edstem.taxibookingsystem.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public UserResponse signUp(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        User user =
                User.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .build();
        user = userRepository.save(user);
        return modelMapper.map(user, UserResponse.class);
    }

    public AuthResponse login(LoginRequest request) throws Exception {
        String email = request.getEmail();
        String password = request.getPassword();
        if (!userRepository.existsByEmail(email)) {
            throw new UserNotFoundException("Check your email and password entered correctly");
        }
        User user = userRepository.findByEmail(request.getEmail());
        if (passwordEncoder.matches(password, user.getPassword())) {
            return jwtService.generateToken(user);
        }
        throw new InvalidLoginException();
    }

    public AccountDetailsResponse addBalance(Long userId, Double accountBalance) {

        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new UserNotFoundException("User not found"));
        Double currentBalance = user.getAccountBalance();
        if (currentBalance == null) {
            currentBalance = 0.0;
        }
        user =
                User.builder()
                        .userId(user.getUserId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .accountBalance(currentBalance + accountBalance)
                        .password(user.getPassword())
                        .build();
        user = userRepository.save(user);
        return modelMapper.map(user, AccountDetailsResponse.class);
    }

    public AccountDetailsResponse getAccountDetails(Long userId) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new UserNotFoundException("User not found"));
        return modelMapper.map(user, AccountDetailsResponse.class);
    }
}

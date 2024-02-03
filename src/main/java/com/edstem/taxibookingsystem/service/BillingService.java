package com.edstem.taxibookingsystem.service;

import com.edstem.taxibookingsystem.contract.request.BillingRequest;
import com.edstem.taxibookingsystem.contract.request.DistanceRequest;
import com.edstem.taxibookingsystem.contract.response.AccountDetailsResponse;
import com.edstem.taxibookingsystem.contract.response.BillingResponse;
import com.edstem.taxibookingsystem.exception.InsufficientBalanceException;
import com.edstem.taxibookingsystem.exception.UserNotFoundException;
import com.edstem.taxibookingsystem.model.Booking;
import com.edstem.taxibookingsystem.model.User;
import com.edstem.taxibookingsystem.repository.BookingRepository;
import com.edstem.taxibookingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;




    public BillingResponse fareCalculate(DistanceRequest distanceRequest){
        double minimumCharge = 22.00;
        Booking billing = Booking.builder()
                .fare(distanceRequest.getDistance()*minimumCharge)
                .build();
        billing = bookingRepository.save(billing);
        return modelMapper.map(billing, BillingResponse.class);

    }

    public AccountDetailsResponse balanceAmount(Long userId, Double accountBalance, Double fare) {

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            Double updatedBalance = user.getAccountBalance() + accountBalance - fare;
            if (updatedBalance < 0) {
                throw new InsufficientBalanceException("Insufficient balance in the account");
            }


            user = User.builder()
                    .userId(user.getUserId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .accountBalance(updatedBalance)
                    .build();
            user = userRepository.save(user);
            return modelMapper.map(user, AccountDetailsResponse.class);
        }



}



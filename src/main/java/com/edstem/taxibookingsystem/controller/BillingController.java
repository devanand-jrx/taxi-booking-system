package com.edstem.taxibookingsystem.controller;

import com.edstem.taxibookingsystem.contract.request.DistanceRequest;
import com.edstem.taxibookingsystem.contract.response.AccountDetailsResponse;
import com.edstem.taxibookingsystem.contract.response.BillingResponse;
import com.edstem.taxibookingsystem.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/billings")
@RequiredArgsConstructor
public class BillingController {

    @Autowired private BillingService billingService;

    @PostMapping
    public @ResponseBody BillingResponse fareCalculate(
            @RequestBody DistanceRequest distanceRequest) {
        return billingService.fareCalculate(distanceRequest);
    }

    @GetMapping("/{userId}")
    public @ResponseBody AccountDetailsResponse balanceAmount(
            @PathVariable Long userId,
            @RequestParam Double accountBalance,
            @RequestParam Double fare) {
        return billingService.balanceAmount(userId, accountBalance, fare);
    }
}

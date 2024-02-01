package com.edstem.taxibookingsystem.controller;

import com.edstem.taxibookingsystem.contract.request.BillingRequest;
import com.edstem.taxibookingsystem.contract.response.BillingResponse;
import com.edstem.taxibookingsystem.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/billings")
@RequiredArgsConstructor
public class BillingController {

    @Autowired
    private BillingService billingService;

    @PostMapping
    public @ResponseBody BillingResponse fareCalculate(@RequestBody BillingRequest billingRequest){
        return billingService.fareCalculate(billingRequest);
    }

}

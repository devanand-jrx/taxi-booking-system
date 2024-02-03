package com.edstem.taxibookingsystem.controller;

import com.edstem.taxibookingsystem.contract.request.TaxiRequest;
import com.edstem.taxibookingsystem.contract.response.TaxiResponse;
import com.edstem.taxibookingsystem.service.TaxiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/taxi")
@RequiredArgsConstructor
public class TaxiController {

    @Autowired private TaxiService taxiService;

    @PostMapping
    public @ResponseBody TaxiResponse addTaxi(@RequestBody TaxiRequest taxiRequest) {
        return taxiService.addTaxi(taxiRequest);
    }
}

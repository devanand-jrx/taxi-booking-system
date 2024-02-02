package com.edstem.taxibookingsystem.exception;

public class TaxiNotFoundException extends RuntimeException {
    public TaxiNotFoundException(String message) {
        super(message);
    }
}

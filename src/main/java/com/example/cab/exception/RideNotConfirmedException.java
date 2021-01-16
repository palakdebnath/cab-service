package com.example.cab.exception;

import lombok.Getter;

@Getter
public class RideNotConfirmedException extends RuntimeException {
    public RideNotConfirmedException() {
        super("Ride is not Confirmed yet!");
    }
}

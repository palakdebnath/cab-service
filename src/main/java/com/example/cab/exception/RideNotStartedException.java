package com.example.cab.exception;

import lombok.Getter;

@Getter
public class RideNotStartedException extends RuntimeException {
    public RideNotStartedException() {
        super("Ride is not Started yet!");
    }

    public RideNotStartedException(String errorMsg) {
        super(errorMsg);
    }
}

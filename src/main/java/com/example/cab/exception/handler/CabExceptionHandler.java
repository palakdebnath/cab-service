package com.example.cab.exception.handler;

import com.example.cab.exception.RideNotConfirmedException;
import com.example.cab.exception.RideNotStartedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CabExceptionHandler {

    @ExceptionHandler(RideNotConfirmedException.class)
    public ResponseEntity<String> handleRideNotConfirmedException(RideNotConfirmedException ex) {
        return new ResponseEntity<>(ex.getMessage(), null, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(RideNotStartedException.class)
    public ResponseEntity<String> handleRideNotStartedException(RideNotStartedException ex) {
        return new ResponseEntity<>(ex.getMessage(), null, HttpStatus.PRECONDITION_FAILED);
    }

}

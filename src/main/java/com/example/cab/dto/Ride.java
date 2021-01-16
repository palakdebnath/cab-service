package com.example.cab.dto;

import com.example.cab.enums.RideStatus;
import lombok.Data;

@Data
public class Ride {
    private long id;

    private double currentLatitude;
    private double currentLongitude;

    private double originLatitude;
    private double originLongitude;
    private double destinationLatitude;
    private double destinationLongitude;

    private RideStatus status;
    private double distanceInKm;
    private double totalFare;

    private double baseFare;
    private double bookingFee;
    private double cgst;
    private double sgst;
}

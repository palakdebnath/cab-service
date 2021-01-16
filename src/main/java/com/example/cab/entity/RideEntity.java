package com.example.cab.entity;

import com.example.cab.enums.RideStatus;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ride")
@Data
public class RideEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double currentLatitude;
    private double currentLongitude;

    private double originLatitude;
    private double originLongitude;
    private double destinationLatitude;
    private double destinationLongitude;

    @Enumerated(EnumType.STRING)
    private RideStatus status;

    private double distanceInKm;
    private double totalFare;

    private double baseFare;
    private double bookingFee;
    private double cgst;
    private double sgst;
}

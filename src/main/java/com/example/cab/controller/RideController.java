package com.example.cab.controller;

import com.example.cab.enums.RideStatus;
import com.example.cab.dto.Ride;
import com.example.cab.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/rides")
public class RideController {

    @Autowired
    private RideService rideService;

    @PostMapping
    public Ride bookRide(@RequestBody Ride ride) {
        return rideService.bookRide(ride);
    }

    //For Driver
    @PutMapping(path="/confirm/{ride-id}")
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    public String confirmRide(@PathVariable(name="ride-id") long rideId) {
        return rideService.updateRideBookingStatus(rideId, RideStatus.CONFIRMED);
    }

    //For Customer/User
    @PutMapping(path="/cancel/{ride-id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String cancelRide(@PathVariable(name="ride-id") long rideId) {
        return rideService.updateRideBookingStatus(rideId, RideStatus.CANCELLED);
    }

    //For Driver/Customer
    @GetMapping(path="/{ride-id}")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_DRIVER')")
    public Ride getRideDetails(@PathVariable(name="ride-id") long rideId) {
        return rideService.findRideById(rideId);
    }

    //For Driver/Customer
    @GetMapping(path="/confirmed/{ride-id}")
    @PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_DRIVER')")
    public Ride getConfirmedRideDetails(@PathVariable(name="ride-id") long rideId) {
        return rideService.findConfirmedRideById(rideId);
    }

    @PutMapping
    public Ride updateCurrentOrDestinationLocationInRide
            (@RequestBody Ride updatedRide,
             @RequestParam(required = false, defaultValue = "true") boolean currentLocation,
             @RequestParam(required = false, defaultValue = "false") boolean destinationLocation) {
        return rideService.updateCurrentLocationInRide(updatedRide, currentLocation, destinationLocation);
    }

    @GetMapping(path="/best")
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    public Ride findSuitableRideFromDriverLocation(
            @RequestParam(required = true) double driverLat,
            @RequestParam(required = true) double driverLong) {
        return rideService.findSuitableRideFromDriverLocation(driverLat, driverLong);
    }

}

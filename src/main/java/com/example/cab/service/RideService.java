package com.example.cab.service;

import com.example.cab.constant.RideConstants;
import com.example.cab.dao.RideDao;
import com.example.cab.dto.Ride;
import com.example.cab.entity.RideEntity;
import com.example.cab.enums.RideStatus;
import com.example.cab.exception.RideNotConfirmedException;
import com.example.cab.exception.RideNotStartedException;
import com.example.cab.mapper.RideMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class RideService {

    @Autowired
    private RideDao rideDao;

    @Autowired
    private RideMapper mapper;

    @Transactional
    public Ride bookRide(final Ride ride) {
        ride.setStatus(RideStatus.BOOKED);
        calculateFare(ride);
        RideEntity rideEntity = rideDao.createRide(mapper.toRideEntity(ride));
        return mapper.toRide(rideEntity);
    }

    @Transactional
    public String updateRideBookingStatus(long rideId, RideStatus status) {
        RideEntity rideEntity = rideDao.findRideById(rideId);
        rideEntity.setStatus(status);
        rideDao.updateRide(rideEntity);
        return "Your ride is " + status + " now!";
    }

    @Transactional(readOnly = true)
    public Ride findRideById(Long id) {
        RideEntity rideEntity = rideDao.findRideById(id);
        return mapper.toRide(rideEntity);
    }

    @Transactional(readOnly = true)
    public Ride findConfirmedRideById(long id) {
        RideEntity rideEntity = rideDao.findRideById(id);
        if(Objects.nonNull(rideEntity) && rideEntity.getStatus() != RideStatus.CONFIRMED) {
            throw new RideNotConfirmedException();
        }
        return mapper.toRide(rideEntity);
    }

    @Transactional(readOnly = true)
    public Ride updateCurrentLocationInRide(Ride updatedRide, boolean updateCurrentLocation, boolean updateDestinationLocation) {
        RideEntity updatedRideEntity = rideDao.findRideById(updatedRide.getId());
        if(Objects.nonNull(updatedRideEntity) && updatedRideEntity.getStatus() != RideStatus.STARTED) {
            throw new RideNotStartedException("Can not update current/destination location if Ride status is not STARTED!");
        }

        // Update ride with Current Location/destination location
        if(updateCurrentLocation) {
            updatedRideEntity.setCurrentLatitude(updatedRide.getCurrentLatitude());
            updatedRideEntity.setCurrentLongitude(updatedRide.getCurrentLongitude());
        }
        if(updateDestinationLocation) {
            updatedRideEntity.setDestinationLatitude(updatedRide.getDestinationLatitude());
            updatedRideEntity.setDestinationLongitude(updatedRide.getDestinationLongitude());
        }

        return mapper.toRide(rideDao.updateRide(updatedRideEntity));
    }

    @Transactional(readOnly = true)
    public Ride findSuitableRideFromDriverLocation(double driverLat, double driverLong) {

        List<RideEntity> allBookedRides = rideDao.findRideWithRideStatusBooked();
        RideEntity rideCloseToDriver = null;
        double distBtwnDriverAndPassengerInKM = 99999;

        for(RideEntity rideEntity : allBookedRides) {
            double tempDistBtwnDriverAndPassengerInKM =
                    calculateDistanceInMeters(driverLat, driverLong,
                            rideEntity.getOriginLatitude(), rideEntity.getOriginLongitude()) / 1000;

            if (tempDistBtwnDriverAndPassengerInKM < distBtwnDriverAndPassengerInKM) {
                distBtwnDriverAndPassengerInKM = tempDistBtwnDriverAndPassengerInKM;
                rideCloseToDriver = rideEntity;
            }
        }

        return mapper.toRide(rideCloseToDriver);
    }

    private void calculateFare(Ride ride) {
        double distanceInKM =
                calculateDistanceInMeters(ride.getOriginLatitude(), ride.getOriginLongitude(),
                        ride.getDestinationLatitude(), ride.getDestinationLongitude()) / 1000;

        ride.setDistanceInKm(distanceInKM);
        ride.setBaseFare(5 * distanceInKM);
        ride.setBookingFee(RideConstants.BOOKING_FEE);
        ride.setCgst(ride.getBaseFare() * RideConstants.CGST_RATE);
        ride.setSgst(ride.getBaseFare() * RideConstants.SGST_RATE);

        ride.setTotalFare(
                ride.getBaseFare() +
                ride.getBookingFee() +
                ride.getCgst() +
                ride.getSgst());
    }

    public double calculateDistanceInMeters(double lat1, double long1, double lat2, double long2) {
        double dist = org.apache.lucene.util.SloppyMath.haversinMeters(lat1, long1, lat2, long2);
        return dist;
    }
}

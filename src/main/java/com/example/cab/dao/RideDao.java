package com.example.cab.dao;

import com.example.cab.entity.RideEntity;
import com.example.cab.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class RideDao {

    @Autowired
    private RideRepository rideRepository;

    public RideEntity createRide(final RideEntity rideEntity) {
        return rideRepository.save(rideEntity);
    }

    public RideEntity findRideById(long id) {
        return rideRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ride Record not found"));
    }

    public RideEntity updateRide(final RideEntity rideEntity) {
        return rideRepository.save(rideEntity);
    }

    public List<RideEntity> findRideWithRideStatusBooked() {
        return rideRepository.findRideWithRideStatusBooked();
    }
}

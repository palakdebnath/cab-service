package com.example.cab.repository;

import com.example.cab.enums.RideStatus;
import com.example.cab.entity.RideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<RideEntity, Long> {

    // @Query("select r from RideEntity r where r.id=:rideid and r.status='CONFIRMED'")
    // RideEntity findConfirmedRideById(long rideId);

    RideEntity findByIdAndStatus(long rideId, RideStatus status);

    @Query("select r from RideEntity r where r.status='BOOKED'")
    List<RideEntity> findRideWithRideStatusBooked();
}

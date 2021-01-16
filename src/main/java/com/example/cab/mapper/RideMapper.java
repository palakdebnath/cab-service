package com.example.cab.mapper;

import com.example.cab.dto.Ride;
import com.example.cab.entity.RideEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface RideMapper {

    RideEntity toRideEntity(Ride ride);

    Ride toRide(RideEntity rideEntity);

}

package com.example.cab.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RideStatus {
    REQUESTING("REQUESTING"),
    BOOKED("BOOKED"),
    CONFIRMED("CONFIRMED"),
    STARTED("STARTED"),
    CANCELLED("CANCELLED"),
    COMPLETED("COMPLETED");

    private String value;

    RideStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}

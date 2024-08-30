package com.uber.strategires;

import com.uber.entities.RideRequest;

public interface RideFareCalculationStrategy {

	double RIDE_FARE_MULTIPLIER = 10;

	double calculateFare(RideRequest rideRequest);
}

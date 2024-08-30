package com.uber.strategires.impl;

import com.uber.entities.RideRequest;
import com.uber.services.DistanceService;
import com.uber.strategires.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy {

	private final DistanceService distanceService;
	private static final double SURGE_FACTOR =  2;

	@Override
	public double calculateFare(RideRequest rideRequest) {
		double distance = distanceService.calculateDistance(rideRequest.getPickUpLocation(), rideRequest.getDropOffLocation());
		return distance * RIDE_FARE_MULTIPLIER * SURGE_FACTOR;
	}
}

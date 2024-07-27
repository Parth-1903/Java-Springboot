package com.uber.strategires;

import com.uber.dto.RideRequestDto;

public interface RideFareCalculationStrategy {

	double calculateFare(RideRequestDto rideRequestDto);
}

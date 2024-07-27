package com.uber.strategires.impl;

import com.uber.dto.RideRequestDto;
import com.uber.strategires.RideFareCalculationStrategy;
import org.springframework.stereotype.Service;

@Service
public class RiderFareDefaultFareCalculationStrategy implements RideFareCalculationStrategy {
	@Override
	public double calculateFare(RideRequestDto rideRequestDto) {
		return 0;
	}
}

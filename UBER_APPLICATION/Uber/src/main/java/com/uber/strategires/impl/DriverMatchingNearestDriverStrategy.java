package com.uber.strategires.impl;

import com.uber.entities.Driver;
import com.uber.entities.RideRequest;
import com.uber.repositories.DriverRepository;
import com.uber.strategires.DriverMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverMatchingNearestDriverStrategy implements DriverMatchingStrategy {

	private final DriverRepository driverRepository;
	@Override
	public List<Driver>  findMatchingDriver(RideRequest rideRequest) {
		return driverRepository.findTenNearestDrivers(rideRequest.getPickUpLocation());
	}
}

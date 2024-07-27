package com.uber.strategires.impl;

import com.uber.dto.DriverDto;
import com.uber.dto.RideRequestDto;
import com.uber.strategires.DriverMatchingStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverMatchingNearestDriverStrategy implements DriverMatchingStrategy {
	@Override
	public List<DriverDto> findMatchesDriver(RideRequestDto rideRequestDto) {
		return List.of();
	}
}

package com.uber.strategires;

import com.uber.dto.DriverDto;
import com.uber.dto.RideRequestDto;

import java.util.List;

public interface DriverMatchingStrategy {

	List<DriverDto> findMatchesDriver(RideRequestDto rideRequestDto);


}

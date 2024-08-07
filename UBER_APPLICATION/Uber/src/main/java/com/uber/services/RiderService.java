package com.uber.services;

import com.uber.dto.DriverDto;
import com.uber.dto.RideDto;
import com.uber.dto.RideRequestDto;
import com.uber.dto.RiderDto;
import com.uber.entities.Rider;
import com.uber.entities.User;

import java.util.List;

public interface RiderService {

	RideRequestDto requestRide(RideRequestDto rideRequestDto);

	RideDto cancelRide(Long rideId);

	RiderDto rateDriver(Long rideId, Integer rating);

	RiderDto  getMyProfile();

	List<RideDto> getAllMyRides();

	Rider createNewRider(User user);

	Rider getCurrentRider();
}

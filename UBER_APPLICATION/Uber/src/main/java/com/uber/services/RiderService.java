package com.uber.services;

import com.uber.dto.DriverDto;
import com.uber.dto.RideDto;
import com.uber.dto.RideRequestDto;
import com.uber.dto.RiderDto;
import com.uber.entities.Rider;
import com.uber.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface RiderService {

	RideRequestDto requestRide(RideRequestDto rideRequestDto);

	RideDto cancelRide(Long rideId);

	DriverDto rateDriver(Long rideId, Integer rating);

	RiderDto  getMyProfile();

	Page<RideDto> getAllMyRides(PageRequest pageRequest);

	Rider createNewRider(User user);

	Rider getCurrentRider();
}

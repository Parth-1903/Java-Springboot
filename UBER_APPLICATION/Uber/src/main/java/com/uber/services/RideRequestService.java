package com.uber.services;

import com.uber.entities.RideRequest;

public interface RideRequestService {

	RideRequest findRideRequestById(Long rideRequestId);

	void update(RideRequest rideRequest);

}

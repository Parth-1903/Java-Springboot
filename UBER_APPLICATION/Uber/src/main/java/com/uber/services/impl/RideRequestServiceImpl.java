package com.uber.services.impl;

import com.uber.entities.RideRequest;
import com.uber.exceptions.ResourceNotFoundException;
import com.uber.repositories.RideRequestRepository;
import com.uber.services.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {

	private final RideRequestRepository rideRequestRepository;

	@Override
	public RideRequest findRideRequestById(Long rideRequestId) {
		return rideRequestRepository.findById(rideRequestId)
				.orElseThrow(() -> new ResourceNotFoundException("RideRequest not found with id: "+rideRequestId));
	}

	@Override
	public void update(RideRequest rideRequest) {
		RideRequest toSave = rideRequestRepository.findById(rideRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("RideRequest not found with id: "+rideRequest.getId()));
		rideRequestRepository.save(toSave);
	}
}

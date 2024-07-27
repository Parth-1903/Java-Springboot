package com.uber.services.impl;

import com.uber.dto.RideDto;
import com.uber.dto.RideRequestDto;
import com.uber.dto.RiderDto;
import com.uber.entities.RideRequest;
import com.uber.services.RiderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {

	private final ModelMapper modelMapper;
	@Override
	public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
		RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
		log.info(rideRequest.toString());
		return null;
	}

	@Override
	public RideDto cancelRide(Long rideId) {
		return null;
	}

	@Override
	public RiderDto rateDriver(Long rideId, Integer rating) {
		return null;
	}

	@Override
	public RiderDto getMyProfile() {
		return null;
	}

	@Override
	public List<RideDto> getAllMyRides() {
		return null;
	}
}

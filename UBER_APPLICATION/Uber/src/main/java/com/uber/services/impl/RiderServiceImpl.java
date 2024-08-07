package com.uber.services.impl;

import com.uber.dto.RideDto;
import com.uber.dto.RideRequestDto;
import com.uber.dto.RiderDto;
import com.uber.entities.RideRequest;
import com.uber.entities.Rider;
import com.uber.entities.User;
import com.uber.entities.enums.RideRequestStatus;
import com.uber.exceptions.ResourceNotFoundException;
import com.uber.repositories.RideRequestRepository;
import com.uber.repositories.RiderRepository;
import com.uber.services.RiderService;
import com.uber.strategires.DriverMatchingStrategy;
import com.uber.strategires.RideFareCalculationStrategy;
import com.uber.strategires.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {

	private final ModelMapper modelMapper;
	private final RideStrategyManager rideStrategyManager;
	private final RideRequestRepository rideRequestRepository;
	private final RiderRepository riderRepository;
	@Override
	@Transactional
	public RideRequestDto requestRide(RideRequestDto rideRequestDto) {

		Rider rider = this.getCurrentRider();
		log.info("Received RideRequestDto: {}", rideRequestDto);

		RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
		rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
		rideRequest.setRider(rider);

		Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
		rideRequest.setFare(fare);

		log.info("Mapped RideRequest entity: {}", rideRequest);

		RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);

		log.info("Successfully saved RideRequest: {}", savedRideRequest);

		rideStrategyManager.driverMatchingStrategy(rider.getRating()).findMatchingDriver(rideRequest);

		return modelMapper.map(savedRideRequest, RideRequestDto.class);
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

	@Override
	public Rider createNewRider(User user) {
		Rider rider = Rider
				.builder()
				.user(user)
				.rating(0.0)
				.build();
		return riderRepository.save(rider);
	}

	@Override
	public Rider getCurrentRider() {
		// TODO : implement Spring Security
		return riderRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException(
				"Rider not found with id: "+1));
	}


}

package com.uber.services.impl;

import com.uber.dto.DriverDto;
import com.uber.dto.RideDto;
import com.uber.dto.RideRequestDto;
import com.uber.dto.RiderDto;
import com.uber.entities.*;
import com.uber.entities.enums.RideRequestStatus;
import com.uber.entities.enums.RideStatus;
import com.uber.exceptions.ResourceNotFoundException;
import com.uber.repositories.RideRequestRepository;
import com.uber.repositories.RiderRepository;
import com.uber.services.DriverService;
import com.uber.services.RideService;
import com.uber.services.RiderService;
import com.uber.strategires.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
	private final RideService rideService;
	private final DriverService driverService;
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

		List<Driver> drivers = rideStrategyManager.driverMatchingStrategy(rider.getRating()).findMatchingDriver(rideRequest);

		//TODO : Send notification to all the drivers about this ride request

		return modelMapper.map(savedRideRequest, RideRequestDto.class);
	}

	@Override
	public RideDto cancelRide(Long rideId) {
		Rider rider = this.getCurrentRider();
		Ride ride = rideService.getRideById(rideId);

		if(!rider.equals(ride.getRider())){
			throw new RuntimeException("Rider does not own this ride with id: "+rideId);
		}

		if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
			throw new RuntimeException("Ride cannot be cancelled, invalid status: "+ride.getRideStatus());
		}

		Ride saveRide = rideService.updateRideStatus(ride, RideStatus.CANCELLED);

		driverService.updateDriverAvailability(ride.getDriver(), true);



		return modelMapper.map(ride, RideDto.class);
	}

	@Override
	public DriverDto rateDriver(Long rideId, Integer rating) {
		return null;
	}

	@Override
	public RiderDto getMyProfile() {
		Rider currentRider = this.getCurrentRider();
		return modelMapper.map(currentRider, RiderDto.class);
	}

	@Override
	public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
		Rider currentRider = this.getCurrentRider();

		return rideService.getAllRidesOfRider(currentRider, pageRequest).map(
				ride -> modelMapper.map(ride, RideDto.class)
		);
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

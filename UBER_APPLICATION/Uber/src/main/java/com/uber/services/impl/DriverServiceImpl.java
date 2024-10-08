package com.uber.services.impl;

import com.uber.dto.DriverDto;
import com.uber.dto.RideDto;
import com.uber.dto.RiderDto;
import com.uber.entities.Driver;
import com.uber.entities.Ride;
import com.uber.entities.RideRequest;
import com.uber.entities.enums.PaymentMethod;
import com.uber.entities.enums.RideRequestStatus;
import com.uber.entities.enums.RideStatus;
import com.uber.exceptions.ResourceNotFoundException;
import com.uber.repositories.DriverRepository;
import com.uber.services.DriverService;
import com.uber.services.PaymentService;
import com.uber.services.RideRequestService;
import com.uber.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

	private final RideRequestService rideRequestService;

	private final DriverRepository driverRepository;
	private final RideService rideService;
	private final ModelMapper modelMapper;
	private final PaymentService paymentService;

	@Override
	@Transactional
	public RideDto acceptRide(Long rideRequestId) {

		RideRequest rideRequest = rideRequestService.findRideRequestById(rideRequestId);

		if(!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)){
			throw new RuntimeException("RideRequest cannot be accepted, status is "+rideRequest.getRideRequestStatus());
		}

		Driver currentDriver = this.getCurrentDriver();

		if(!currentDriver.getAvailable()){
			throw new RuntimeException("Driver cannot accept ride due to unavailability");
		}

		Driver savedDriver = this.updateDriverAvailability(currentDriver, false);

		Ride ride = rideService.createNewRide(rideRequest,savedDriver);

		return modelMapper.map(ride, RideDto.class);
	}

	@Override
	public RideDto cancelRide(Long rideId) {
		Ride ride = rideService.getRideById(rideId);
		Driver driver = getCurrentDriver();

		if(!driver.equals(ride.getDriver())){
			throw new RuntimeException("Driver cannot start a ride as he has not accepted it earlier");
		}

		if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
			throw new RuntimeException("Ride cannot be cancelled, invalid status: "+ride.getRideStatus());
		}

		rideService.updateRideStatus(ride, RideStatus.CANCELLED);

		this.updateDriverAvailability(driver, true);

		return modelMapper.map(ride, RideDto.class);
	}

	@Override
	public RideDto startRide(Long rideId, String otp) {
		Ride ride = rideService.getRideById(rideId);
		Driver driver = this.getCurrentDriver();

		if(!driver.equals(ride.getDriver())){
			throw new RuntimeException("Driver cannot start a ride as he has not accepted it earlier");
		}

		if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
			throw new RuntimeException("Ride statis is not CONFIRMED hence cannot be started, status: "+ride.getRideStatus());
		}

		if(!otp.equals(ride.getOtp())){
			throw new RuntimeException("Otp is not valid, otp: "+otp);
		}

		ride.setStartedAt(LocalDateTime.now());
		Ride savedRide = rideService.updateRideStatus(ride,RideStatus.ONGOING);

		paymentService.createNewPayment(savedRide);

		return modelMapper.map(savedRide, RideDto.class);
	}

	@Override
	@Transactional
	public RideDto endRide(Long rideId) {
		Ride ride = rideService.getRideById(rideId);
		Driver driver = this.getCurrentDriver();

		if(!driver.equals(ride.getDriver())){
			throw new RuntimeException("Driver cannot start a ride as he has not accepted it earlier");
		}

		if(!ride.getRideStatus().equals(RideStatus.ONGOING)){
			throw new RuntimeException("Ride statis is not ONGOING hence cannot be started, status: "+ride.getRideStatus());
		}

		ride.setEndedAt(LocalDateTime.now());
		Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ENDED);
		this.updateDriverAvailability(driver, true);
		paymentService.processPayment(ride);

		return modelMapper.map(savedRide,RideDto.class);
	}

	@Override
	public RiderDto rateRider(Long rideId, Integer rating) {
		return null;
	}

	@Override
	public DriverDto getMyProfile() {
		Driver driver = this.getCurrentDriver();
		return modelMapper.map(driver, DriverDto.class);
	}

	@Override
	public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
		Driver currentDriver = this.getCurrentDriver();

		return rideService.getAllRidesOfDriver(currentDriver, pageRequest).map(
				ride -> modelMapper.map(ride, RideDto.class)
		);
	}

	@Override
	public Driver getCurrentDriver() {
		return driverRepository.findById(2L).orElseThrow(() -> new ResourceNotFoundException("Driver not found!"));
	}

	@Override
	public Driver updateDriverAvailability(Driver driver, boolean available) {
		driver.setAvailable(available);
		return driverRepository.save(driver);
	}
}

package com.uber.controllers;

import com.uber.advices.ApiResponse;
import com.uber.dto.RideDto;
import com.uber.dto.RideStartDto;
import com.uber.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/drivers")
public class DriverController {

	private final DriverService driverService;

	@PostMapping("/acceptRide/{rideRequestId}")
	public ApiResponse<RideDto> acceptRide(@PathVariable("rideRequestId") Long rideRequestId){
		return new ApiResponse<>(HttpStatus.OK.value(),"Ride accepted successfully",driverService.acceptRide(rideRequestId));
	}

	@PostMapping("/startRide/{rideRequestId}")
	public ApiResponse<RideDto> startRide(@PathVariable("rideRequestId") Long rideRequestId, @RequestBody RideStartDto rideStartDto){
		return new ApiResponse<>(HttpStatus.OK.value(),"Ride accepted successfully",driverService.startRide(rideRequestId, rideStartDto.getOtp()));
	}

	@PostMapping("/endRide/{rideId}")
	public ApiResponse<RideDto> endRide(@PathVariable("rideId") Long rideId){
		return new ApiResponse<>(HttpStatus.OK.value(),"Ride accepted successfully",driverService.endRide(rideId));
	}



}

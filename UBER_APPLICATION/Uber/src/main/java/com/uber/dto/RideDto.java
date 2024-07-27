package com.uber.dto;

import com.uber.entities.Driver;
import com.uber.entities.Rider;
import com.uber.entities.enums.PaymentMethod;
import com.uber.entities.enums.RideStatus;

import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

public class RideDto {

	private Long id;

	private Point pickUpLocation;

	private Point dropOffLocation;

	private LocalDateTime createdTime;

	private RiderDto riderDto;

	private DriverDto driverDto;

	private PaymentMethod paymentMethod;

	private RideStatus rideRequestStatus;

	private String otp;

	private Double fare;

	private LocalDateTime startedAt;

	private LocalDateTime endedAt;
}

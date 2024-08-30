package com.uber.dto;

import com.uber.entities.Rider;
import com.uber.entities.enums.PaymentMethod;
import com.uber.entities.enums.RideRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDto {
	private Long id;

	private PointDto pickUpLocation;

	private PointDto dropOffLocation;

	private LocalDateTime requestedTime;

	private RiderDto rider;

	private PaymentMethod paymentMethod;

	private RideRequestStatus rideRequestStatus;

	private Double fare;
}

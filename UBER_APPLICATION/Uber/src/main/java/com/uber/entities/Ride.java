package com.uber.entities;

import com.uber.entities.enums.PaymentMethod;
import com.uber.entities.enums.RideStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Ride {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "Geometry(Point, 4326)")
	private Point pickUpLocation;

	@Column(columnDefinition = "Geometry(Point, 4326)")
	private Point dropOffLocation;

	@CreationTimestamp
	private LocalDateTime createdTime;

	@ManyToOne(fetch = FetchType.LAZY)
	private Rider rider;

	@ManyToOne(fetch = FetchType.LAZY)
	private Driver driver;

	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;

	@Enumerated(EnumType.STRING)
	private RideStatus rideStatus;

	private String otp;

	private Double fare;

	private LocalDateTime startedAt;

	private LocalDateTime endedAt;
}


package com.uber.entities;

import com.uber.entities.enums.PaymentMethod;
import com.uber.entities.enums.RideRequestStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RideRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "Geometry(Point, 4326)")
	private Point pickUpLocation;

	@Column(columnDefinition = "Geometry(Point, 4326)")
	private Point dropOffLocation;

	@CreationTimestamp
	private LocalDateTime requestedTime;

	@ManyToOne(fetch = FetchType.LAZY)
	private Rider rider;

	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;

	@Enumerated(EnumType.STRING)
	private RideRequestStatus rideRequestStatus;

	private Double fare;
}

package com.uber.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Driver {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name="user_id")
	private User user;

	private Double rating;

	private Boolean available;

	private String vehicleId;

	@Column(columnDefinition = "Geometry(Point, 4326)")
	private Point currentLocation;
}

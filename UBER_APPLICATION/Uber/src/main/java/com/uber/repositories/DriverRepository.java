package com.uber.repositories;

import com.uber.entities.Driver;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//ST_Distance(point1, point2);
//ST_DWithin(point1,10000); //Near to that location withing 10km

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

	//This query checks if the current location of the drivers is within 10,000 meters(10KM)
	// of the provided pickUpLocation.
	@Query(value = "SELECT d.*,ST_Distance(d.current_location, :pickUpLocation) AS distance " +
			"FROM driver d " +
			"WHERE d.available=true AND ST_DWithin(d.current_location, :pickUpLocation, 10000) " +
			"ORDER BY distance " +
			"LIMIT 10", nativeQuery = true)
	List<Driver> findTenNearestDrivers(@Param("pickUpLocation") Point pickUpLocation);

	@Query(value = "SELECT d.* " +
			"FROM driver d " +
			"WHERE d.available=true AND ST_DWithin(d.current_location, :pickupLocation, 15000) " +
			"ORDER BY d.rating DESC " +
			"LIMIT 10", nativeQuery = true)
	List<Driver> findTenNearbyTopRatedDrivers(@Param("pickupLocation") Point pickupLocation);
}

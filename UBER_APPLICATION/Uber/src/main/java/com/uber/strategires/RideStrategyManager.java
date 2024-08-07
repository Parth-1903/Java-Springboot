package com.uber.strategires;

import com.uber.strategires.impl.DriverMatchingHighestRatedDriverStrategy;
import com.uber.strategires.impl.DriverMatchingNearestDriverStrategy;
import com.uber.strategires.impl.RideFareSurgePricingFareCalculationStrategy;
import com.uber.strategires.impl.RiderFareDefaultFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RideStrategyManager {

	private final DriverMatchingHighestRatedDriverStrategy highestRatedDriverStrategy;
	private final DriverMatchingNearestDriverStrategy nearestDriverStrategy;
	private final RideFareSurgePricingFareCalculationStrategy surgePricingFareCalculationStrategy;
	private final RiderFareDefaultFareCalculationStrategy defaultFareCalculationStrategy;

	public DriverMatchingStrategy driverMatchingStrategy(double rideRating){
//		if(rideRating >= 4.5){
//			return highestRatedDriverStrategy;
//		}else{
//			return nearestDriverStrategy;
//		}

		return (rideRating >= 4.5) ? highestRatedDriverStrategy : nearestDriverStrategy;
	}

	public RideFareCalculationStrategy rideFareCalculationStrategy(){
		//peek time 6PM to 9PM
		LocalTime surgeStartTime = LocalTime.of(18,0);
		LocalTime surgeEndTime = LocalTime.of(21,0);
		LocalTime currentTime = LocalTime.now();

		boolean isSurgeTime = currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);
		return isSurgeTime ? surgePricingFareCalculationStrategy : defaultFareCalculationStrategy;
	}

}

package com.week_1_HW.Week_1_HW_CakeBakery.Services;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "cake.name",havingValue = "chocolate")
public class ChocolateFrosting implements Frosting {
	@Override
	public void getFrostingType() {
		System.out.print("Getting Chocolate Frosting ");
	}
}

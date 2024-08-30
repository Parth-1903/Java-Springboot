package com.week_1_HW.Week_1_HW_CakeBakery.Services;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "cake.name",havingValue = "strawberry")
public class StrawberryFrosting implements Frosting {

	@Override
	public void getFrostingType() {
		System.out.print("Getting Strawberry flavour Frosting ");
	}
}

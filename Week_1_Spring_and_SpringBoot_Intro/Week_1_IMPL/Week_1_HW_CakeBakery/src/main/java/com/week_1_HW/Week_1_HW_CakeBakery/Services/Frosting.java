package com.week_1_HW.Week_1_HW_CakeBakery.Services;

import org.springframework.stereotype.Component;

@Component
public class Frosting implements FrostingInteface{
	@Override
	public void getFrostingType() {
		System.out.println("Getting Frosting");
	}
}

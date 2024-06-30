package com.week_1_HW.Week_1_HW_CakeBakery.Services;

import org.springframework.stereotype.Component;

@Component
public class CakeBaker {

	private Syrup syrup;

	private Frosting frosting;

	CakeBaker(Syrup syrup, Frosting frosting){
		this.syrup = syrup;
		this.frosting = frosting;
	}

	public void bakeCake(){
		System.out.println("Baking cake for Chocolate and Strawberry flavors");
	}
}

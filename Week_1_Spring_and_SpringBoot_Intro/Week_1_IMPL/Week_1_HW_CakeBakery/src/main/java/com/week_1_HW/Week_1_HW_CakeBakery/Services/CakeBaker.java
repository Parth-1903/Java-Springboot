package com.week_1_HW.Week_1_HW_CakeBakery.Services;

import org.springframework.stereotype.Component;

@Component
public class CakeBaker {

	private final Syrup syrup;

	private final Frosting frosting;

	CakeBaker(Syrup syrup, Frosting frosting){
		this.syrup = syrup;
		this.frosting = frosting;
	}

	public void bakeCake(){
		System.out.println("For baking cake we are ");
		syrup.getSyrupType();
		System.out.print(" and ");
		frosting.getFrostingType();
	}
}

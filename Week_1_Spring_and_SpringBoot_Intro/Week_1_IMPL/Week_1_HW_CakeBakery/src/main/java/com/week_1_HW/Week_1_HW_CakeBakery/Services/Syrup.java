package com.week_1_HW.Week_1_HW_CakeBakery.Services;

import org.springframework.stereotype.Component;

@Component
public class Syrup implements SyrupInterface{
	@Override
	public void getSyrupType() {
		System.out.println("Getting Syrup");
	}
}

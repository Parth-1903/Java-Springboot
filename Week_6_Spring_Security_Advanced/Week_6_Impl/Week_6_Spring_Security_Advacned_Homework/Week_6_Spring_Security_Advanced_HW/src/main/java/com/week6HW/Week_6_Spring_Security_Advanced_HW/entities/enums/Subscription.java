package com.week6HW.Week_6_Spring_Security_Advanced_HW.entities.enums;

public enum Subscription {

	FREE("FREE",1),
	BASIC("BASIC",2),
	PREMIUM("PREMIUM",3);

	private final String value;
	private final Integer count;

	Subscription(String value, Integer count) {
		this.value = value;
		this.count = count;
	}

	public String getValue() {
		return value;
	}

	public Integer getCount() {
		return count;
	}
}

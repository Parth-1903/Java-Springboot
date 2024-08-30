package com.week6HW.Week_6_Spring_Security_Advanced_HW.exception;

public class SessionLimitException extends RuntimeException{

	public SessionLimitException(String message) {
		super(message);
	}
}

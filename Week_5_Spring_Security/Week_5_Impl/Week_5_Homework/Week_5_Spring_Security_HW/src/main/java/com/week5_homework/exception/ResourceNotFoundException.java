package com.week5_homework.exception;

public class ResourceNotFoundException extends RuntimeException{

	ResourceNotFoundException(String message){
		super(message);
	}

}

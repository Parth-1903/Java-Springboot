package com.w2HomeWork.Exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ResourceNotFoundException extends RuntimeException{
	public ResourceNotFoundException(String message){
		super(message);
	}
}

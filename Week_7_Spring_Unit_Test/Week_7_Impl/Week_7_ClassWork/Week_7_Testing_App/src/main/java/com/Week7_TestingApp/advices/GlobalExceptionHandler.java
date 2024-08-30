package com.Week7_TestingApp.advices;

import com.Week7_TestingApp.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handlerResourceNotFoundException(ResourceNotFoundException e){
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handlerRunTimeException(RuntimeException e){
		return ResponseEntity.internalServerError().build();
	}

}

package com.w2HomeWork.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler{

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFound(ResourceNotFoundException ex){
		ApiResponse<Object> apiResponse = ApiResponse.builder()
				.status(HttpStatus.NOT_FOUND)
				.message(ex.getMessage())
				.build();
		return new ResponseEntity<>(apiResponse,apiResponse.getStatus());
	}

	//We can create many more but it is just for learning purpose.

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse> methodNotValidException(MethodArgumentNotValidException ex){

		List<String> errorList = ex.getBindingResult().getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());

		ApiResponse<Object> apiResponse = ApiResponse.builder()
				.status(HttpStatus.BAD_REQUEST)
				.message(errorList.toString())
				.build();

		return new ResponseEntity<>(apiResponse,apiResponse.getStatus());
	}
}

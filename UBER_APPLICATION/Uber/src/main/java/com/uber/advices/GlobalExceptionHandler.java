package com.uber.advices;

import com.uber.exceptions.ResourceNotFoundException;
import com.uber.exceptions.RuntimeConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiError> handlerResourceNotFoundException(ResourceNotFoundException exception){
		ApiError apiError = new ApiError(exception.getLocalizedMessage(), HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(RuntimeConflictException.class)
	public ResponseEntity<ApiError> handleRunTimeConflictExcpetion(RuntimeConflictException exception){
		ApiError apiError = new ApiError(exception.getMessage(), HttpStatus.CONFLICT);
		return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
	}


	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleException(Exception exception){
		ApiError apiError = new ApiError(exception.getLocalizedMessage(), HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
	}


	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> methodNotValidException(MethodArgumentNotValidException ex){

		List<String> errorList = ex.getBindingResult().getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());

		ApiError apiError = ApiError.builder()
				.status(HttpStatus.BAD_REQUEST)
				.message(errorList.toString())
				.build();

		return new ResponseEntity<>(apiError,apiError.getStatus());
	}

}

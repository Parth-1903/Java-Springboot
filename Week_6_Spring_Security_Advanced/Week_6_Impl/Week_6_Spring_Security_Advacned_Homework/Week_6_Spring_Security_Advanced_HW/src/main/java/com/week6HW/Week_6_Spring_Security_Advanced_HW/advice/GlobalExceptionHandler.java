package com.week6HW.Week_6_Spring_Security_Advanced_HW.advice;

import com.week6HW.Week_6_Spring_Security_Advanced_HW.exception.ResourceNotFoundException;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.exception.SessionLimitException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiError> handlerResourceNotFoundException(ResourceNotFoundException exception){
		ApiError apiError = new ApiError(exception.getLocalizedMessage(), HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ApiError> handlerAuthenticationException(AuthenticationException exception){
		ApiError apiError = new ApiError(exception.getLocalizedMessage(),HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(JwtException.class)
	public ResponseEntity<ApiError> handlerJwtException(JwtException exception){
		ApiError apiError = new ApiError(exception.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<>(apiError,HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex){
		ApiError apiError = new ApiError(ex.getLocalizedMessage(), HttpStatus.FORBIDDEN);
		return new ResponseEntity<>(apiError,HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(SessionLimitException.class)
	public ResponseEntity<ApiError> handleSessionLimitException(SessionLimitException ex){
		ApiError apiError = new ApiError(ex.getLocalizedMessage(), HttpStatus.PRECONDITION_FAILED);
		return new ResponseEntity<>(apiError,HttpStatus.PRECONDITION_FAILED);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handlerResourceNotFoundException(Exception exception){
		ApiError apiError = new ApiError(exception.getLocalizedMessage(), HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
	}
}
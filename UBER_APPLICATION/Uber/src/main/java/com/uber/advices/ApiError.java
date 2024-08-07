package com.uber.advices;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class ApiError {

	private HttpStatus status;
	private String message;
	private List<String> subErrors;

	public ApiError() {
	}

	public ApiError(String localizedMessage, HttpStatus httpStatus) {
		this.status = httpStatus;
		this.message = localizedMessage;
	}

	public ApiError(HttpStatus status, String message, List<String> subErrors) {
		this.status = status;
		this.message = message;
		this.subErrors = subErrors;
	}
}

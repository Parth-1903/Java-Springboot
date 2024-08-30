package com.week5_homework.advices;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

	private HttpStatus status;
	private String message;
	private T body;

	public ApiResponse(){}

	public ApiResponse(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public ApiResponse(HttpStatus status, String message, T body) {
		this.status = status;
		this.message = message;
		this.body = body;
	}
}

package com.w2HomeWork.Exception;

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
	private T data;

	public ApiResponse(HttpStatus status,String message){
		this.status = status;
		this.message = message;
	}
	public ApiResponse(HttpStatus status, String message, T data){
		this.status = status;
		this.message = message;
		this.data = data;
	}
}

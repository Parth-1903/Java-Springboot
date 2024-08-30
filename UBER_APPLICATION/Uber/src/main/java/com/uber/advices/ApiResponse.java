package com.uber.advices;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T>{
	private int code;
	private String message;
	private T data;


	public ApiResponse(T data) {
		this.data = data;
	}

	public ApiResponse(int status, String message){
		this.code = status;
		this.message = message;
	}
	public ApiResponse(int status, String message, T data){
		this.code = status;
		this.message = message;
		this.data = data;
	}
}

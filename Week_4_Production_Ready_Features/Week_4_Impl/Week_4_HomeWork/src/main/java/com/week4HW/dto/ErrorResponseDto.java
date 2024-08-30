package com.week4HW.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {

	@JsonProperty("message")
	private String message;

	@JsonProperty("errors")
	private Map<String,ErrorDetailDto> errors;

	@JsonProperty("info")
	private String info;

}

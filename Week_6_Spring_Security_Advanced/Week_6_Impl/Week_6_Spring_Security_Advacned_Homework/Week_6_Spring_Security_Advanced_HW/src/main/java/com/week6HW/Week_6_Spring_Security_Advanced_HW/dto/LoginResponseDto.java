package com.week6HW.Week_6_Spring_Security_Advanced_HW.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

	private Long id;
	private String accessToken;
	private String refreshToken;

}

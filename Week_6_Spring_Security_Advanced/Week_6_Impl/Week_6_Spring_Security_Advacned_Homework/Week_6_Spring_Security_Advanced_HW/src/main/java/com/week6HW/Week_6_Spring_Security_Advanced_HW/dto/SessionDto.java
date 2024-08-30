package com.week6HW.Week_6_Spring_Security_Advanced_HW.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDto {

	private Long id;

	private String refreshToken;

	private UserDto user;

	private LocalDateTime expiredDate;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}

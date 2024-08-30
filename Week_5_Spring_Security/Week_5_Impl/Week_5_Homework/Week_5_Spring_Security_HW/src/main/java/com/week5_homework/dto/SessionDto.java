package com.week5_homework.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SessionDto {

	private Long id;

	private String token;

	private LocalDateTime createdAt;

}

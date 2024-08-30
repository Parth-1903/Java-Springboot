package com.w2HomeWork.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DepartmentDTO {

	private Long id;
	@NotBlank(message = "Title must required!")
	private String title;

	private Boolean isActive;

	private LocalDateTime createdAt;
}

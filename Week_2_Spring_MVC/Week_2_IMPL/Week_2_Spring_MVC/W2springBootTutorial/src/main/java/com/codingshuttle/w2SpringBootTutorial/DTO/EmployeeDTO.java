package com.codingshuttle.w2SpringBootTutorial.DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeDTO {

	private Long id;

	@NotBlank(message = "Name of the Employee cannot be blank")
	private String name;

	private String email;

	private Integer age;

	private String role;

	private LocalDate localDate;

	private Boolean isActive;
}

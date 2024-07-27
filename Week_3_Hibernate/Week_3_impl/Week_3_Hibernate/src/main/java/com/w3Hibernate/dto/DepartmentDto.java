package com.w3Hibernate.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.w3Hibernate.entities.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {

	private Long id;

	private String title;

	private EmployeeDto manager;

	private Set<EmployeeDto> workers;
}

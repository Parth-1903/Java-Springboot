package com.Week7_TestingApp.services;

import com.Week7_TestingApp.Dto.EmployeeDto;

public interface EmployeeService {

	EmployeeDto getEmployeeById(Long id);
	EmployeeDto createNewEmployee(EmployeeDto employeeDto);
	EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto);
	void deleteEmployee(Long id);
}

package com.w3Hibernate.cotroller;

import com.w3Hibernate.dto.EmployeeDto;
import com.w3Hibernate.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/employee")
public class EmployeeController {

	private final EmployeeService employeeService;

	@GetMapping("/{id}")
	public EmployeeDto employeeById(@PathVariable("id")Long id){
		return employeeService.getEmployeeById(id);
	}

	@GetMapping("/all")
	public List<EmployeeDto> getAllEmployee(){
		return employeeService.getAllEmployees();
	}

	@PostMapping
	public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto){
		return employeeService.createEmployee(employeeDto);
	}

	@PutMapping("{empId}/project/{projectId}")
	public EmployeeDto assignedProjectToEmployee(@PathVariable("empId")Long empId, @PathVariable("projectId") Long projectId){
		return employeeService.assignedProjectToEmployee(empId,projectId);
	}
}

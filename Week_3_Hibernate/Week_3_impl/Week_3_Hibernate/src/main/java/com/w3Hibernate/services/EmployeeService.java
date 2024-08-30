package com.w3Hibernate.services;

//import com.w3Hibernate.Mapper.DepartmentMapper;
//import com.w3Hibernate.Mapper.EmployeeMapper;
import com.w3Hibernate.Mapper.EmployeeMapper;
import com.w3Hibernate.dto.EmployeeDto;
import com.w3Hibernate.entities.Employee;
import com.w3Hibernate.entities.Project;
import com.w3Hibernate.repositories.EmployeeRepository;
import com.w3Hibernate.repositories.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final EmployeeMapper employeeMapper;

	private final ProjectRepository projectRepository; // we can use service class as well


	public EmployeeDto getEmployeeById(Long id){
		Optional<Employee> optionalDepartment = employeeRepository.findById(id);

		if(optionalDepartment.isEmpty()){
			return null;
		}

		return optionalDepartment.map(employeeMapper::toEmployeeDto).orElse(null);
	}

	@Transactional
	public List<EmployeeDto> getAllEmployees(){
		List<Employee> allEmployee = employeeRepository.findAll();

		return employeeMapper.toEmployeeList(allEmployee);
	}
	public EmployeeDto createEmployee(EmployeeDto employeeDto){
		Employee employee = employeeMapper.toEntity(employeeDto);

		return employeeMapper.toEmployeeDto(employeeRepository.save(employee));
	}

	public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto){
		Optional<Employee> optionalEmployee = employeeRepository.findById(id);
		if(optionalEmployee.isEmpty()){
			return null;
		}
//		Employee employee = optionalEmployee.get();
		Employee employee = employeeMapper.mergeEmployee(employeeDto, optionalEmployee.get());

		return employeeMapper.toEmployeeDto(employeeRepository.save(employee));
	}


	public boolean deleteEmployee(Long id){
		boolean flag = employeeRepository.existsById(id);

		if(!flag){
			return false;
		}
		employeeRepository.deleteById(id);
		return true;
	}

	public EmployeeDto assignedProjectToEmployee(Long empId, Long projectId){
		Optional<Employee> optionalEmployee = employeeRepository.findById(empId);
		Optional<Project> optionalProject = projectRepository.findById(projectId);

		if(optionalEmployee.isPresent() && optionalProject.isPresent()){
			Employee employee = optionalEmployee.get();
			Project project = optionalProject.get();

			employee.getProjects().add(project);

			return employeeMapper.toEmployeeDto(employeeRepository.save(employee));
		}
		return null;
	}
}

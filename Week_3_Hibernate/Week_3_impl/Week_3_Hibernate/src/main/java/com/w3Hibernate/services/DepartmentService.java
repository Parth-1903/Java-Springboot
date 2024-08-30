package com.w3Hibernate.services;

import com.w3Hibernate.Mapper.DepartmentMapper;
import com.w3Hibernate.dto.DepartmentDto;
import com.w3Hibernate.entities.Department;
import com.w3Hibernate.entities.Employee;
import com.w3Hibernate.repositories.DepartmentRepository;
import com.w3Hibernate.repositories.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

	private final DepartmentRepository departmentRepository;
	private final EmployeeRepository employeeRepository;
	private final DepartmentMapper departmentMapper;


	public DepartmentDto getDepartmentById(Long id){
		Optional<Department> optionalDepartment = departmentRepository.findById(id);

		return optionalDepartment.map(departmentMapper::toDepartmentDto).orElse(null);
	}

	public List<DepartmentDto> getAllDepartment(){
		List<Department> allDepartment = departmentRepository.findAll();

		return departmentMapper.toDepartmentList(allDepartment);
	}
	public DepartmentDto createDepartment(DepartmentDto departmentDto){
		Department department = departmentMapper.toEntity(departmentDto);

		return departmentMapper.toDepartmentDto(departmentRepository.save(department));
	}

	public DepartmentDto updateDepartment(Long deptId, Long empId){
		Optional<Department> optionalDepartment = departmentRepository.findById(deptId);
		Optional<Employee> optionalEmployee = employeeRepository.findById(empId);

		if(optionalDepartment.isPresent() && optionalEmployee.isPresent()){
			Department department = optionalDepartment.get();
			Employee employee = optionalEmployee.get();

//			employeeRepository.save(employee);    // Save employee to persist changes
			department.setManager(employee);
			employee.setDepartment(department);  // Ensure bidirectional update

			employeeRepository.save(employee);
			return departmentMapper.toDepartmentDto(departmentRepository.save(department));
		}
		return null;
	}

	@Transactional
	public DepartmentDto setWorkersInDepartment(Long deptId, Long empId){
		Optional<Department> optionalDepartment = departmentRepository.findById(deptId);
		Optional<Employee> optionalEmployee = employeeRepository.findById(empId);

		if(optionalDepartment.isPresent() && optionalEmployee.isPresent()){
			Department department = optionalDepartment.get();
			Employee employee = optionalEmployee.get();

			employee.setWorkerDepartment(department);
			department.getWorkers().add(employee);

			employeeRepository.save(employee);
			return departmentMapper.toDepartmentDto(departmentRepository.save(department));
		}
		return null;
	}


	public boolean deleteDepartment(Long id){
		boolean flag = departmentRepository.existsById(id);

		if(!flag){
			return false;
		}
		departmentRepository.deleteById(id);
		return true;
	}
}

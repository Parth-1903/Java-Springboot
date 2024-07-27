package com.w3Hibernate.Mapper;

import com.w3Hibernate.dto.EmployeeDto;
import com.w3Hibernate.entities.Employee;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
		nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {

	Employee toEntity(EmployeeDto employeeDto);
	EmployeeDto toEmployeeDto(Employee employee);

	Employee mergeEmployee(EmployeeDto employeeDto, @MappingTarget Employee employee);
	List<EmployeeDto> toEmployeeList(List<Employee> employeeList);

//	@AfterMapping
//	default void linkEmployee(@MappingTarget Employee employee){
//		if(employee.getDepartment() != null){
//			employee.getDepartment().setManager(employee);
//		}
//	}
}
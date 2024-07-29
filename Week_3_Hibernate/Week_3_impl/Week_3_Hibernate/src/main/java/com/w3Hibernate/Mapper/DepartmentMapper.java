package com.w3Hibernate.Mapper;

import com.w3Hibernate.dto.DepartmentDto;
import com.w3Hibernate.entities.Department;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
		nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DepartmentMapper {

	Department toEntity(DepartmentDto departmentDto);

	DepartmentDto toDepartmentDto(Department department);

	Department mergeDepartment(DepartmentDto departmentDto, @MappingTarget Department department);

	List<DepartmentDto> toDepartmentList(List<Department> departmentList);
}

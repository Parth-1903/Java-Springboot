package com.week4.Clients;

import com.week4.dto.DepartmentDto;

import java.util.List;

public interface DepartmentClient {

	List<DepartmentDto> getAllDepartments();

	DepartmentDto getDepartmentById(Long deptId);
}

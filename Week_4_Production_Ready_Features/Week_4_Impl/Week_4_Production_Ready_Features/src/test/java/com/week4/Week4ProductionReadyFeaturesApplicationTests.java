package com.week4;

import com.week4.Clients.DepartmentClient;
import com.week4.dto.DepartmentDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Week4ProductionReadyFeaturesApplicationTests {

	@Autowired
	private DepartmentClient departmentClient;

	@Test
	@Order(2)
	void getAllDepartment() {
		List<DepartmentDto> departmentDtoList = departmentClient.getAllDepartments();
		System.out.println(departmentDtoList);
	}
	@Test
	@Order(1)
	void getDepartmentById() {
		DepartmentDto departmentDto = departmentClient.getDepartmentById(1L);
		System.out.println(departmentDto);
	}
}

package com.Week7_TestingApp.controller;

import com.Week7_TestingApp.Dto.EmployeeDto;
import com.Week7_TestingApp.TestContainerConfiguration;
import com.Week7_TestingApp.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@Import(TestContainerConfiguration.class)
@AutoConfigureWebTestClient(timeout = "100000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractIntegrationTest {

	@Autowired
	WebTestClient webTestClient;

	Employee testEmployee = Employee.builder()
			.id(1L)
			.email("parth@gmail.com")
			.name("Parth")
			.salary(200L)
			.build();

	EmployeeDto testemployeeDto = EmployeeDto.builder()
			.id(1L)
			.email("parth@gmail.com")
			.name("Parth")
			.salary(200L)
			.build();
}

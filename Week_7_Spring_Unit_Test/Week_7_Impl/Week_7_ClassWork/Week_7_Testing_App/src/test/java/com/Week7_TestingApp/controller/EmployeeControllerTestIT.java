package com.Week7_TestingApp.controller;

import com.Week7_TestingApp.Dto.EmployeeDto;
import com.Week7_TestingApp.entities.Employee;
import com.Week7_TestingApp.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


class EmployeeControllerTestIT extends AbstractIntegrationTest{


	@Autowired
	private EmployeeRepository employeeRepository;

	@Spy
	private ModelMapper modelMapper;

	@BeforeEach
	void setUp(){
		employeeRepository.deleteAll();
	}

	@Test
	void testGetEmployeeById_Success(){
		Employee savedEmployee = employeeRepository.save(testEmployee);

		webTestClient.get()
				.uri("/employees/{id}",savedEmployee.getId())
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.id").isEqualTo(savedEmployee.getId())
				.jsonPath("$.email").isEqualTo(savedEmployee.getEmail());


//				.value(employeeDto -> {
//					assertThat(employeeDto.getEmail()).isEqualTo(savedEmployee.getEmail());
//					assertThat(employeeDto.getId()).isEqualTo(savedEmployee.getId());
//				});
	}

	@Test
	void testGetEmployeeById_Failure(){
		webTestClient.get()
				.uri("/employees/1")
				.exchange()
				.expectStatus().isNotFound();
	}

	@Test
	void testCreateNewEmployee_whenEmployeeAlreadyExists_thenThrowException(){
		Employee savedEmployee = employeeRepository.save(testEmployee);

		webTestClient.post()
				.uri("/employees")
				.bodyValue(testemployeeDto)
				.exchange()
				.expectStatus().is5xxServerError();
	}

	@Test
	void testCreateNewEmployee_whenEmployeeDoesNotExists_thenCreateEmployee(){
		webTestClient.post()
				.uri("/employees")
				.bodyValue(testemployeeDto)
				.exchange()
				.expectStatus().isCreated()
				.expectBody()
				.jsonPath("$.email").isEqualTo(testemployeeDto.getEmail())
				.jsonPath("$.name").isEqualTo(testemployeeDto.getName());
	}

	@Test
	void testUpdateEmployee_whenEmployeeDoesNotExists_thenThrowException(){
		webTestClient.put()
				.uri("/employees/999")
				.bodyValue(testemployeeDto)
				.exchange()
				.expectStatus().isNotFound();
	}

	@Test
	void testUpdateEmployee_whenAttempingToUpdateTheEmail_thenThrowException(){
		Employee savedEmployee = employeeRepository.save(testEmployee);
		testemployeeDto.setName("Random name");
		testemployeeDto.setEmail("random@gmail.com");

		webTestClient.put()
				.uri("/employees/{id}",savedEmployee.getId())
				.bodyValue(testemployeeDto)
				.exchange()
				.expectStatus().is5xxServerError();
	}

	@Test
	void testUpdateEmployee_whenEmployeeIsValid_thenUpdateEmployee(){
		Employee savedEmployee = employeeRepository.save(testEmployee);
		testemployeeDto.setName("random name");
		testemployeeDto.setSalary(250L);

		webTestClient.put()
				.uri("/employees/{id}", savedEmployee.getId())
				.bodyValue(testemployeeDto)
				.exchange()
				.expectStatus().isOk()
				.expectBody(EmployeeDto.class)
				.isEqualTo(testemployeeDto);
	}

	@Test
	void testDeleteEmployee_whenEmployeeDoesNotExists_thenThrowException(){
		webTestClient.delete()
				.uri("/employees/1")
				.exchange()
				.expectStatus().isNotFound();
	}

	@Test
	void testDeleteEmployee_whenEmployeeExists_thenDeleteEmployee(){
		Employee savedEmployee = employeeRepository.save(testEmployee);

		webTestClient.delete()
				.uri("/employees/{id}",savedEmployee.getId())
				.exchange()
				.expectStatus().isNoContent()
				.expectBody(Void.class);

		webTestClient.delete()
				.uri("/employees/1")
				.exchange()
				.expectStatus().isNotFound();
	}

}
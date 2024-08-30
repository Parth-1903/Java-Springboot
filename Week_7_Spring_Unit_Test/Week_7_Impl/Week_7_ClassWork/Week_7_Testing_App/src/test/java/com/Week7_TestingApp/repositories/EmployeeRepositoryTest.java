package com.Week7_TestingApp.repositories;

import com.Week7_TestingApp.TestContainerConfiguration;
import com.Week7_TestingApp.entities.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@SpringBootTest
@Import(TestContainerConfiguration.class)
@DataJpaTest
//above annotation by default configured embedded database and use that
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest {

	@Autowired
	private EmployeeRepository employeeRepository;

	private Employee employee;

	@BeforeEach
	void setUp(){
		employee = Employee.builder()
				.id(1L)
				.name("Parth")
				.email("parth@gmail.com")
				.salary(1000L)
				.build();
	}

	@Test
	void testFineByEmail_whenEmailIsValid_thenReturnEmployee() {
		// Arrange, Given (Arrange the test cases)
		employeeRepository.save(employee);

		// Act, When
		List<Employee> employeeList = employeeRepository.findByEmail(employee.getEmail());

		// Assert, Then
		Assertions.assertThat(employeeList).isNotNull();
		Assertions.assertThat(employeeList).isNotEmpty();
		assertThat(employeeList.get(0).getEmail()).isEqualTo(employee.getEmail());
	}

	@Test
	void testFindByEmail_whenEmailIsNotFound_thenReturnEmployeeEmptyEmployeeList(){
		// Arrange
		String email = "not@gmail.com";

		// Act
		List<Employee> employeeList = employeeRepository.findByEmail(email);

		// Assert
		Assertions.assertThat(employeeList).isNotNull();
		Assertions.assertThat(employeeList).isEmpty();
//		Assertions.assertThat(employeeList).isNotEmpty(); //Throws an error
	}
}
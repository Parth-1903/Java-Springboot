package com.Week7_TestingApp.services.impl;

import com.Week7_TestingApp.Dto.EmployeeDto;
import com.Week7_TestingApp.TestContainerConfiguration;
import com.Week7_TestingApp.entities.Employee;
import com.Week7_TestingApp.exceptions.ResourceNotFoundException;
import com.Week7_TestingApp.repositories.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@Import(TestContainerConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

	@Mock
	private EmployeeRepository employeeRepository;

//	@Mock
	@Spy
	private ModelMapper modelMapper;

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	private Employee mockEmployee;

	private EmployeeDto mockEmployeeDto;

	@BeforeEach
	void setUp(){
		mockEmployee = Employee.builder()
				.id(1L)
				.email("parth@gmail.com")
				.name("Parth")
				.salary(200L)
				.build();

		mockEmployeeDto = modelMapper.map(mockEmployee, EmployeeDto.class);
	}

	@Test
	void testGetEmployeeById_WhenEmployeeidIsPresent_ThenReturnEmployeeDto(){

		// Assign

		Long id = mockEmployee.getId();
		when(employeeRepository.findById(id)).thenReturn(Optional.of(mockEmployee)); //stubbing

		// act
		EmployeeDto employeeDto = employeeService.getEmployeeById(id);

		//assert
		Assertions.assertThat(employeeDto).isNotNull();
		Assertions.assertThat(employeeDto.getId()).isEqualTo(id);
		Assertions.assertThat(employeeDto.getEmail()).isEqualTo(mockEmployee.getEmail());

//		verify(employeeRepository).findById(2L);
		verify(employeeRepository, atLeast(1)).findById(id); //times also there
	}

	@Test
	void testGetEmployeeById_whenEmployeeIsNotPresent_thenThrowException(){
		// arrange
		when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

		// act and assert
		Assertions.assertThatThrownBy(() -> employeeService.getEmployeeById(1L))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Employee not found with id: 1");

		verify(employeeRepository).findById(1L);
	}

	@Test
	void testCreateNewEmployee_WhenValidEmployee_ThenCreateNewEmployee(){
		// assign
		when(employeeRepository.findByEmail(anyString())).thenReturn(List.of());
		when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);

		// act
		EmployeeDto employeeDto = employeeService.createNewEmployee(mockEmployeeDto);

		// assert

		Assertions.assertThat(employeeDto).isNotNull();
		Assertions.assertThat(employeeDto.getEmail()).isEqualTo(mockEmployeeDto.getEmail());

		ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);
		verify(employeeRepository).save(employeeArgumentCaptor.capture());

		Employee captureEmployee = employeeArgumentCaptor.getValue();
		Assertions.assertThat(captureEmployee.getEmail()).isEqualTo(mockEmployee.getEmail());
	}

	@Test
	void testCreatenewEmployee_whenAttemptingToCreateEmployeeWithExistingEmail_thenThrowException(){
		// arrange
		when(employeeRepository.findByEmail(mockEmployeeDto.getEmail())).thenReturn(List.of(mockEmployee));

		// act and assert
		Assertions.assertThatThrownBy(() -> employeeService.createNewEmployee(mockEmployeeDto))
				.isInstanceOf(RuntimeException.class)
				.hasMessage("Employee already exists with email: " + mockEmployee.getEmail());

		verify(employeeRepository).findByEmail(mockEmployeeDto.getEmail());
		verify(employeeRepository,never()).save(any());
	}

	@Test
	void testUpdateEmployee_whenEmployeeDoesNotExists_thenThrowException(){
		// arrange
		when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

		// act and assert
		Assertions.assertThatThrownBy(() -> employeeService.updateEmployee(1L,mockEmployeeDto))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Employee not found with id: 1");

		verify(employeeRepository).findById(mockEmployeeDto.getId());
		verify(employeeRepository,never()).save(any());
	}

	@Test
	void testUpdateEmployee_whenAttemptingToUpdateEmail_thenThrowException(){
		// arrange
		when(employeeRepository.findById(1L)).thenReturn(Optional.of(mockEmployee));
		mockEmployeeDto.setName("random");
		mockEmployeeDto.setEmail("random@gmail.com");

		// act and assert
		Assertions.assertThatThrownBy(() -> employeeService.updateEmployee(mockEmployeeDto.getId(),mockEmployeeDto))
				.isInstanceOf(RuntimeException.class)
				.hasMessage("The email of the employee cannot be updated");

		verify(employeeRepository).findById(mockEmployeeDto.getId());
		verify(employeeRepository,never()).save(any());
	}

	@Test
	void testUpdateEmployee_whenValidEmployee_thenUpdateEmployee(){
		// arrange
		when(employeeRepository.findById(mockEmployeeDto.getId())).thenReturn(Optional.of(mockEmployee));
		mockEmployeeDto.setName("Random name");
		mockEmployeeDto.setSalary(199L);

		Employee newEmployee = modelMapper.map(mockEmployeeDto, Employee.class);
		when(employeeRepository.save(any(Employee.class))).thenReturn(newEmployee);

		// act and assert
		EmployeeDto updatedEmployeeDto = employeeService.updateEmployee(mockEmployeeDto.getId(), mockEmployeeDto);


		Assertions.assertThat(updatedEmployeeDto).isEqualTo(mockEmployeeDto);

		verify(employeeRepository).findById(1L);
		verify(employeeRepository).save(any(Employee.class));
	}

	@Test
	void testDeleteEmployee_whenEmployeeDoesNotExists_thenThrowException(){
		// arrange
		when(employeeRepository.existsById(1L)).thenReturn(false);

		// act and assert
		Assertions.assertThatThrownBy(() -> employeeService.deleteEmployee(1L))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Employee not found with id: " + 1L);

		verify(employeeRepository,never()).deleteById(anyLong());
	}

	@Test
	void testDeleteEmployee_whenEmployeeIsvalid_thenDeleteEmployee(){
		// arrange
		when(employeeRepository.existsById(1L)).thenReturn(true);

		// act and assert
		Assertions.assertThatCode(() -> employeeService.deleteEmployee(1L))
						.doesNotThrowAnyException();

		verify(employeeRepository).deleteById(1L);
	}

}
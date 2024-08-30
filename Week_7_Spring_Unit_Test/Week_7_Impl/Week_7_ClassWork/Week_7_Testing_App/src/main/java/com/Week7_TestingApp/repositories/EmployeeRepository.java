package com.Week7_TestingApp.repositories;

import com.Week7_TestingApp.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	List<Employee> findByEmail(String email);
}

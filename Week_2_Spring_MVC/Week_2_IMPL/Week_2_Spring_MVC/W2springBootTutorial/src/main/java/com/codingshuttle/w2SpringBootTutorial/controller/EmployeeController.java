package com.codingshuttle.w2SpringBootTutorial.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingshuttle.w2SpringBootTutorial.Repository.EmployeeRepository;

@RestController
public class EmployeeController {



	@GetMapping
	public String getString() {
		return "Getting String";
	}
}

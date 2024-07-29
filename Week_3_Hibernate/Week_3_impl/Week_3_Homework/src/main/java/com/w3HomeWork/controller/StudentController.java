package com.w3HomeWork.controller;

import com.w3HomeWork.dto.StudentDto;
import com.w3HomeWork.repositories.StudentRepository;
import com.w3HomeWork.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/student")
@RequiredArgsConstructor
public class StudentController {

	private final StudentService studentService;

	@GetMapping("/{studentId}")
	public StudentDto getStudentById(@PathVariable("studentId")Long studentId){
		return studentService.getStudentById(studentId);
	}

	@GetMapping("/all")
	public List<StudentDto> getAllStudent(){
		return studentService.getAllStudents();
	}

	@PostMapping
	public StudentDto createStudent(@RequestBody StudentDto studentDto){
		return studentService.createStudent(studentDto);
	}

	@PutMapping("{studentId}/subject/{subjectId}")
	public StudentDto assginedSubjectToStudent(@PathVariable("studentId")Long studentId, @PathVariable("subjectId")Long subjectId){
		return studentService.assignSubjectToStudent(studentId,subjectId);
	}

}

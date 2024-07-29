package com.w3HomeWork.controller;

import com.w3HomeWork.dto.SubjectDto;
import com.w3HomeWork.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/subject")
@RequiredArgsConstructor
public class SubjectController {

	private final SubjectService subjectService;

	@GetMapping("/{subjectId}")
	public SubjectDto getSubjectById(@PathVariable("subjectId")Long subjectId){
		return subjectService.getSubjectById(subjectId);
	}

	@GetMapping("/all")
	public List<SubjectDto> getAllSubject(){
		return subjectService.getAllSubject();
	}

	@PostMapping
	public SubjectDto createSubject(@RequestBody SubjectDto subjectDto){
		return subjectService.createSubject(subjectDto);
	}



}

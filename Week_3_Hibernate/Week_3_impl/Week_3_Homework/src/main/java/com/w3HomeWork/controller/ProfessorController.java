package com.w3HomeWork.controller;

import com.w3HomeWork.dto.ProfessorDto;
import com.w3HomeWork.services.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/professor")
@RequiredArgsConstructor
public class ProfessorController {

	private final ProfessorService professorService;


	@GetMapping("/{professorId}")
	public ProfessorDto getProfessorById(@PathVariable("professorId") Long id){
		return professorService.getProfessorById(id);
	}

	@GetMapping("/all")
	public List<ProfessorDto> getAllProfessor(){
		return professorService.getAllProfessor();
	}

	@PostMapping
	public ProfessorDto createProfessor(@RequestBody ProfessorDto professorDto){
		return professorService.createProfessor(professorDto);
	}

	@PutMapping("{professorId}/subject/{subjectId}")
	public ProfessorDto assignedSubjectToProfessor(@PathVariable("professorId")Long professorId, @PathVariable("subjectId")Long subjectId){
		return professorService.assignedSubjectToProfessor(professorId, subjectId);
	}

	@PutMapping("{professorId}/student/{studentId}")
	public ProfessorDto assignedStudentToProfessor(@PathVariable("professorId") Long professorId, @PathVariable("studentId") Long studentId){
		return professorService.assignedStudentToProfessor(professorId,studentId);
	}

}

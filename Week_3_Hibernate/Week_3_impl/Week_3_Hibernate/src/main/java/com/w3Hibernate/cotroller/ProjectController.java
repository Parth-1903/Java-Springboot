package com.w3Hibernate.cotroller;

import com.w3Hibernate.dto.ProjectDto;
import com.w3Hibernate.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/project")
public class ProjectController {

	private final ProjectService projectService;

	@GetMapping("{id}")
	public ProjectDto getProjectById(@PathVariable("id") Long id){
		return projectService.getProjectById(id);
	}

	@GetMapping("/all")
	public List<ProjectDto> getAllProjects(){
		return projectService.getAllProjects();
	}

	@PostMapping
	public ProjectDto createProject(@RequestBody ProjectDto projectDto){
		return projectService.createProject(projectDto);
	}
}

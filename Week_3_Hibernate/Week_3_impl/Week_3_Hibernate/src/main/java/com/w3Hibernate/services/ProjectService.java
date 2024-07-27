package com.w3Hibernate.services;

import com.w3Hibernate.Mapper.ProjectMapper;
import com.w3Hibernate.dto.ProjectDto;
import com.w3Hibernate.entities.Project;
import com.w3Hibernate.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {

	private final ProjectRepository projectRepository;

	private final ProjectMapper projectMapper;

	public List<ProjectDto> getAllProjects(){
		return projectMapper.toProjectList(projectRepository.findAll());
	}

	public ProjectDto getProjectById(Long id){
		Optional<Project> optionalProject = projectRepository.findById(id);
		return optionalProject.map(projectMapper::toProjectDto).orElse(null);
	}

	public ProjectDto createProject(ProjectDto projectDto){
		Project project = projectMapper.toEntity(projectDto);

		return projectMapper.toProjectDto(projectRepository.save(project));
	}

}

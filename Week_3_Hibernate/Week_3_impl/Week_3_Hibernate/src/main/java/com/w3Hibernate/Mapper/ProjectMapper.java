package com.w3Hibernate.Mapper;

import com.w3Hibernate.dto.ProjectDto;
import com.w3Hibernate.entities.Project;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
		nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProjectMapper {

	Project toEntity(ProjectDto projectDto);

	ProjectDto toProjectDto(Project project);

	Project mergeProject(ProjectDto projectDto, @MappingTarget Project project);

	List<ProjectDto> toProjectList(List<Project> projectList);
}

package com.w3HomeWork.Mapper;

import com.w3HomeWork.dto.ProfessorDto;
import com.w3HomeWork.entities.Professor;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
		nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProfessorMapper {

	Professor toEntity(ProfessorDto professorDto);

	@Mapping(target = "subjects", ignore = true)
	ProfessorDto toProfessorDto(Professor professor);

	Professor mergeProfessor(ProfessorDto professorDto, @MappingTarget Professor professor);

	List<ProfessorDto> toProfessorList(List<Professor> professorDtoList);
}

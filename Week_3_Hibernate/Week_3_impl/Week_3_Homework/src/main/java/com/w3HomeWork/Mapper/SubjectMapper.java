package com.w3HomeWork.Mapper;

import com.w3HomeWork.dto.SubjectDto;
import com.w3HomeWork.entities.Subject;
import org.mapstruct.*;

import java.util.List;

@Mapper(
		componentModel = "spring",
		nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SubjectMapper {

	@Mapping(target = "professor", ignore = true) // Ignore professor field
	Subject toEntity(SubjectDto subjectDto);

	@Mapping(target = "professor", ignore = true) // Ignore professor field
	SubjectDto toSubjectDto(Subject subject);

	Subject mergeSubject(SubjectDto subjectDto, @MappingTarget Subject subject);

	List<SubjectDto> toSubjectList(List<Subject> subjectDtoList);
}

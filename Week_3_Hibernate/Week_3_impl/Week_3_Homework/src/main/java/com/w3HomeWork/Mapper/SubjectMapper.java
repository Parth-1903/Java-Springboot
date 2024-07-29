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


	Subject toEntity(SubjectDto subjectDto);
	@Mapping(target = "professor.subjects", ignore = true)
	SubjectDto toSubjectDto(Subject subject);

	Subject mergeSubject(SubjectDto subjectDto, @MappingTarget Subject subject);

	List<SubjectDto> toSubjectList(List<Subject> subjectDtoList);
}

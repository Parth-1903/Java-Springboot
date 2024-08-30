package com.w3HomeWork.Mapper;

import com.w3HomeWork.dto.StudentDto;
import com.w3HomeWork.entities.Student;
import org.mapstruct.*;

import java.util.List;

@Mapper(
		componentModel = "spring",
		uses = {SubjectMapper.class}, // Use SubjectMapper here
		nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface StudentMapper {

	@Mapping(target = "subjects", source = "subjects") // Ensure to map subjects
	Student toEntity(StudentDto studentDto);

	@Mapping(target = "subjects", source = "subjects") // Ensure to map subjects
	StudentDto toStudentDto(Student student);

	@Mapping(target = "subjects", ignore = true) // Ignore subjects in merge
	Student mergeStudent(StudentDto studentDto, @MappingTarget Student student);

	List<StudentDto> toStudentList(List<Student> studentList);
}

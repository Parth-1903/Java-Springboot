package com.w3HomeWork.Mapper;

import com.w3HomeWork.dto.StudentDto;
import com.w3HomeWork.entities.Student;
import org.mapstruct.*;

import java.util.List;

@Mapper(
		componentModel = "spring",
		nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
		nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface StudentMapper {

	Student toEntity(StudentDto studentDto);

	@Mapping(target = "subjects.professor",ignore = true)
	StudentDto toStudentDto(Student student);

	Student mergeStudent(StudentDto studentDto, @MappingTarget Student student);

	List<StudentDto> toStudentList(List<Student> studentList);
}

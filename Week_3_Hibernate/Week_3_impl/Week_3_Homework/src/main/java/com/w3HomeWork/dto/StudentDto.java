package com.w3HomeWork.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDto {

	private Long id;

	@JsonProperty("student_name")
	private String studentName;

	@JsonProperty("class_name")
	private Integer className;

	@JsonManagedReference
	private Set<SubjectDto> subjects;
}

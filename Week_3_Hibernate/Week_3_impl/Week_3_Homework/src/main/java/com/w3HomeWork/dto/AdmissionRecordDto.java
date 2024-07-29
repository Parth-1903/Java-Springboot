package com.w3HomeWork.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdmissionRecordDto {

	private Long id;

	private Integer fees;

	private Integer className;

	private Set<StudentDto> students;
}

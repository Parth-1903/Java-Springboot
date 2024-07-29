package com.w3HomeWork.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String studentName;

	private Integer className;

	@ManyToOne
	private AdmissionRecord admissionRecord;

	@ManyToMany(mappedBy = "students")
	private Set<Professor> professors;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "student_subject_mapping",
			joinColumns = @JoinColumn(name = "student_id"),
			inverseJoinColumns = @JoinColumn(name = "subject_id")
	)
	private Set<Subject> subjects;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Student student = (Student) o;
		return Objects.equals(id, student.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}

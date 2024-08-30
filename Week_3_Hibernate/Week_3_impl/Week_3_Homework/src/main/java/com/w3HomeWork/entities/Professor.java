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
@Table(name = "professor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Professor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@OneToMany(mappedBy = "professor",cascade = CascadeType.ALL)
	private Set<Subject> subjects;

	@ManyToMany
	@JoinTable(
			name = "professor_student_mapping",
			joinColumns = @JoinColumn(name = "professor_id"),
			inverseJoinColumns = @JoinColumn(name = "student_id")
	)
	private Set<Student> students;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Professor professor = (Professor) o;
		return Objects.equals(id, professor.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}

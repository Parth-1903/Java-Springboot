package com.w3HomeWork.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "admission")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer fees;

	private Integer className;

	private Integer count;

	@OneToMany(mappedBy = "admissionRecord",cascade = CascadeType.ALL)
	private Set<Student> student;



	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AdmissionRecord admissionRecord = (AdmissionRecord) o;
		return Objects.equals(id, admissionRecord.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}

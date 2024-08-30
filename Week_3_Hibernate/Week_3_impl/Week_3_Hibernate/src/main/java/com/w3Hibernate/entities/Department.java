package com.w3Hibernate.entities;

import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@OneToOne
	private Employee manager;

	@OneToMany(mappedBy = "workerDepartment",fetch = FetchType.LAZY,orphanRemoval = true)
	private Set<Employee> workers;

}

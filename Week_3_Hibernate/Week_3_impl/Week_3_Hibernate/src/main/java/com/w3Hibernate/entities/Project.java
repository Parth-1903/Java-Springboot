package com.w3Hibernate.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String projectTitle;

	@ManyToMany(mappedBy = "projects")
	private List<Employee> members;
}

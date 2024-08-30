package com.week6HW.Week_6_Spring_Security_Advanced_HW.entities;

import com.week6HW.Week_6_Spring_Security_Advanced_HW.entities.enums.Subscription;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "shows")
@NoArgsConstructor
@AllArgsConstructor
public class Shows{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String movieName;

	private Double ratings;

	private Subscription subscription;

}

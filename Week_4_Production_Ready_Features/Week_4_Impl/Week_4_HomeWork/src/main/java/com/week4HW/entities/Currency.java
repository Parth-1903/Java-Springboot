package com.week4HW.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

@Data
@Entity
@Table(name = "currency")
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class Currency extends Auditable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private double currencyValue;

	private String currencyType;

	private double convertValue;

	private String convertType;
}

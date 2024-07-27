package com.uber.entities;

import com.uber.entities.enums.PaymentMethod;
import com.uber.entities.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;

//	@OneToOne(fetch=FetchType.LAZY)
//	private Ride ride;

	private Double amount;

	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;

	@CreationTimestamp
	private LocalDateTime paymentTime;
}

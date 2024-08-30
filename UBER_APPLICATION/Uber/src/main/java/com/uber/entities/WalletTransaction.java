package com.uber.entities;

import com.uber.entities.enums.TransactionMethod;
import com.uber.entities.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Double amount;

	private TransactionType transactionType;

	private TransactionMethod transactionMethod;

	@ManyToOne
	private Ride ride;

	private String transactionId;

	@ManyToOne
	private Wallet wallet;

	@CreationTimestamp
	private LocalDateTime timeStamp;
}

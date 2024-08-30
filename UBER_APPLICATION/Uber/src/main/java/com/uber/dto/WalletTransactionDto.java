package com.uber.dto;

import com.uber.entities.enums.TransactionMethod;
import com.uber.entities.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletTransactionDto {

	private Long id;

	private Double amount;

	private TransactionType transactionType;

	private TransactionMethod transactionMethod;

	private RideDto ride;

	private String transactionId;

	private WalletDto wallet;

	private LocalDateTime timeStamp;

}

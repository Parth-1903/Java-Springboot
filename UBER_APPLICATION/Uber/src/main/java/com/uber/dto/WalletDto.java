package com.uber.dto;

import com.uber.entities.User;
import com.uber.entities.WalletTransaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto {

	private Long id;

	private UserDto user;

	private Double balance;

	private List<WalletTransactionDto> transaction;
}

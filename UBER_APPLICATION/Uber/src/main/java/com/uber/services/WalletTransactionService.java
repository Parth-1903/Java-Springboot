package com.uber.services;

import com.uber.dto.WalletTransactionDto;
import com.uber.entities.WalletTransaction;

public interface WalletTransactionService {

	void createNewWalletTransaction(WalletTransaction walletTransaction);

}

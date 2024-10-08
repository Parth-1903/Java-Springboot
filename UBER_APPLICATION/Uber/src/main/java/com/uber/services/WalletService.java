package com.uber.services;


import com.uber.entities.Ride;
import com.uber.entities.User;
import com.uber.entities.Wallet;
import com.uber.entities.enums.TransactionMethod;

public interface WalletService {

	Wallet addMoneyToWallet(User user, Double amount,
	                        String transactionId,
	                        Ride ride,
	                        TransactionMethod transactionMethod);

	Wallet deductMoneyFromWallet(User user, Double amount,
	                             String transactionId,
	                             Ride ride,
	                             TransactionMethod transactionMethod);

	void withdrawAllMyMoneyFromWallet();

	Wallet findWalletById(Long walletId);

	Wallet createNewWallet(User user);

	Wallet findByUser(User user);

}

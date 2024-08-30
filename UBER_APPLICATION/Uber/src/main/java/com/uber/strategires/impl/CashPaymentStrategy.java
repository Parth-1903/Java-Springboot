package com.uber.strategires.impl;

import com.uber.entities.Driver;
import com.uber.entities.Payment;
import com.uber.entities.enums.PaymentStatus;
import com.uber.entities.enums.TransactionMethod;
import com.uber.repositories.PaymentRepository;
import com.uber.services.PaymentService;
import com.uber.services.WalletService;
import com.uber.strategires.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


//Rider -> 100
//Driver -> 70 Deduct 30Rs from Driver's wallet

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

	private final WalletService walletService;
	private final PaymentRepository paymentRepository;

	@Override
	public void processPayment(Payment payment) {
		Driver driver = payment.getRide().getDriver();

		double platformCommission = payment.getAmount() * PLATFORM_COMMISION;

		walletService.deductMoneyFromWallet(driver.getUser(),platformCommission,null,
				payment.getRide(),
				TransactionMethod.RIDE);
		payment.setPaymentStatus(PaymentStatus.CONFIRMED);
		paymentRepository.save(payment);
	}
}

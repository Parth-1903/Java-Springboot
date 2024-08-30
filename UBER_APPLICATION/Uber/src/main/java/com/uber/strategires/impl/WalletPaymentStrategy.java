package com.uber.strategires.impl;

import com.uber.entities.Driver;
import com.uber.entities.Payment;
import com.uber.entities.Rider;
import com.uber.entities.enums.PaymentStatus;
import com.uber.entities.enums.TransactionMethod;
import com.uber.repositories.PaymentRepository;
import com.uber.services.PaymentService;
import com.uber.services.WalletService;
import com.uber.strategires.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Rider has 232 Rs., Driver had 500
// Ride cost is 100, comission = 30
//Rider -> 232-100 = 132
//Driver -> 500 + (100 - 30) = 570

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {

	private final WalletService walletService;
	private final PaymentRepository paymentRepository;

	@Override
	@Transactional
	public void processPayment(Payment payment) {
		Driver driver = payment.getRide().getDriver();
		Rider rider = payment.getRide().getRider();

		walletService.deductMoneyFromWallet(rider.getUser(), payment.getAmount(), null, payment.getRide()
				, TransactionMethod.RIDE);
		double driverCut = payment.getAmount() * (1 - PLATFORM_COMMISION);
		walletService.addMoneyToWallet(driver.getUser(), driverCut, null, payment.getRide(), TransactionMethod.RIDE);
		payment.setPaymentStatus(PaymentStatus.CONFIRMED);
		paymentRepository.save(payment);
	}
}

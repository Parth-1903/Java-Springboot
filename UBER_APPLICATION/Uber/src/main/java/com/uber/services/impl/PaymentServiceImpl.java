package com.uber.services.impl;

import com.uber.entities.Payment;
import com.uber.entities.Ride;
import com.uber.entities.enums.PaymentMethod;
import com.uber.entities.enums.PaymentStatus;
import com.uber.exceptions.ResourceNotFoundException;
import com.uber.repositories.PaymentRepository;
import com.uber.services.PaymentService;
import com.uber.strategires.PaymentStrategy;
import com.uber.strategires.PaymentStrategyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;
	private final PaymentStrategyManager paymentStrategyManager;

	@Override
	public void processPayment(Ride ride) {
		Payment payment = paymentRepository.findByRide(ride).orElseThrow(() -> new ResourceNotFoundException("Payment not found for this ride: "+ ride.getId()));
		paymentStrategyManager.paymentStrategy(payment.getPaymentMethod()).processPayment(payment);
	}

	@Override
	public Payment createNewPayment(Ride ride) {
		Payment payment = Payment.builder()
				.ride(ride)
				.paymentMethod(ride.getPaymentMethod())
				.amount(ride.getFare())
				.paymentStatus(PaymentStatus.PENDING)
				.build();
		return paymentRepository.save(payment);
	}

	@Override
	public void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus) {
		payment.setPaymentStatus(paymentStatus);
		paymentRepository.save(payment);
	}
}

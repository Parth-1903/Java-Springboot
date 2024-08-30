package com.uber.strategires;

import com.uber.entities.Payment;

public interface PaymentStrategy {

	Double PLATFORM_COMMISION = 0.3;

	void processPayment(Payment payment);

}

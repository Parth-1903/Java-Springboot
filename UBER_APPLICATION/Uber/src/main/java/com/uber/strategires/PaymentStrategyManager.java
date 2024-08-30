package com.uber.strategires;

import com.uber.entities.enums.PaymentMethod;
import com.uber.strategires.impl.CashPaymentStrategy;
import com.uber.strategires.impl.WalletPaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStrategyManager {

	private final WalletPaymentStrategy walletPaymentStrategy;
	private final CashPaymentStrategy cashPaymentStrategy;

	public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod){
		return switch (paymentMethod){
			case WALLET -> walletPaymentStrategy;
			case CASH -> cashPaymentStrategy;
		};
	}
}

package com.week4HW.service;

import com.week4HW.dto.CurrencyDto;

import java.util.List;

public interface CurrencyService {

	List<CurrencyDto> getAllCurrency();
	CurrencyDto insertCurrency(String baseCurrency,String targetCurrency,CurrencyDto currencyDto);
}

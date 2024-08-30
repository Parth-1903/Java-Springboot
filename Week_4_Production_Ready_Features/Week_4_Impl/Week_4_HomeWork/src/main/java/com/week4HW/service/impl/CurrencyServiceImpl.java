package com.week4HW.service.impl;

import com.week4HW.Clients.CurrencyClient;
import com.week4HW.Repository.CurrencyRepository;
import com.week4HW.dto.CurrencyDto;
import com.week4HW.dto.DataDto;
import com.week4HW.entities.Currency;
import com.week4HW.exception.ResourceNotFoundException;
import com.week4HW.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

	private final ModelMapper modelMapper;

	private final CurrencyClient currencyClient;

	private final CurrencyRepository currencyRepository;


	public List<CurrencyDto> getAllCurrency(){
		List<Currency> allCurrencies = currencyRepository.findAll();

		return allCurrencies.stream().map(currency -> modelMapper.map(currency,CurrencyDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public CurrencyDto insertCurrency(String baseCurrency,String targetCurrency,CurrencyDto currencyDto) {
		currencyDto.setCurrencyType(baseCurrency);
		currencyDto.setConvertType(targetCurrency);
		DataDto dataDto = currencyClient.getCurrency(currencyDto);

		Double conversionRate = dataDto.getData().get(targetCurrency);
		if (conversionRate == null) {
			throw new ResourceNotFoundException("Conversion rate for currency " + targetCurrency + " not found.");
		}

		currencyDto.setConvertValue(conversionRate * currencyDto.getCurrencyValue());

		Currency currency = modelMapper.map(currencyDto,Currency.class);
		return modelMapper.map(currencyRepository.save(currency),CurrencyDto.class);
	}
}

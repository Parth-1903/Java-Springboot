package com.week4HW.Clients;

import com.week4HW.dto.CurrencyDto;
import com.week4HW.dto.DataDto;

public interface CurrencyClient {

	DataDto getCurrency(CurrencyDto currencyDto);
}

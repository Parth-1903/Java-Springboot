package com.week4HW.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDto {
	private Long id;

	@JsonProperty("currency_value")
	private double currencyValue;

	private String currencyType;

	private double convertValue;

	private String convertType;
}

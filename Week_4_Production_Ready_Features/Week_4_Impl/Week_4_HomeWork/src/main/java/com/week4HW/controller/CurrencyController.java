package com.week4HW.controller;

import com.week4HW.advice.ApiResponse;
import com.week4HW.dto.CurrencyDto;
import com.week4HW.service.CurrencyService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/currency")
@RequiredArgsConstructor
public class CurrencyController {

	private final CurrencyService currencyService;

	@GetMapping("/all")
	public ApiResponse<List<CurrencyDto>> getAllRecords(){
		List<CurrencyDto> allRecords = currencyService.getAllCurrency();
		return new ApiResponse<>(HttpStatus.OK,"Records Successfully Retrieved",allRecords);
	}

	@PostMapping
	public ApiResponse<CurrencyDto> insertCurrency(@RequestParam("baseCurrency")String baseCurrency,
	                                               @RequestParam("targetCurrency") String targetCurrency,
	                                               @RequestBody CurrencyDto currencyDto){
		CurrencyDto currencyDto1 = currencyService.insertCurrency(baseCurrency,targetCurrency,currencyDto);
		return new ApiResponse<>(HttpStatus.OK,"Currency Successfully Calculated",currencyDto1);
	}

}

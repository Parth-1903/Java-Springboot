package com.week4HW.Clients.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.week4HW.Clients.CurrencyClient;
import com.week4HW.dto.CurrencyDto;
import com.week4HW.dto.DataDto;
import com.week4HW.dto.ErrorResponseDto;
import com.week4HW.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyClientImpl implements CurrencyClient {

	private final RestClient restClient;

	private final ObjectMapper objectMapper;

	@Value("${currencyConverter.apiKey}")
	private String API_KEY;
	@Override
	public DataDto getCurrency(CurrencyDto currencyDto) {
		try {
//			String url = String.format("?apikey="+API_KEY+"&currencies=%s&base_currency=%s",
//					 currencyDto.getConvertType(), currencyDto.getCurrencyType());

			Object object = restClient.get()
					.uri(uriBuilder ->
						uriBuilder
//								.path("v1/latest")
								.queryParam("currencies",currencyDto.getConvertType())
								.queryParam("base_currency",currencyDto.getCurrencyType())
								.build())
					.header("apikey",API_KEY)
					.retrieve()
					.body(new ParameterizedTypeReference<Map<String, Object>>() {});

			String jsonResponse = objectMapper.writeValueAsString(object);

			DataDto dataDto = objectMapper.readValue(jsonResponse, DataDto.class);

			return dataDto;
		} catch (JsonMappingException e) {
			e.printStackTrace();
			throw new ResourceNotFoundException("Error Fetching currency data: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResourceNotFoundException("Error Fetching currency data: " + e.getMessage());
		}
	}


	//This will only work with the commented line of the RestClientConfig


//	@Value("${currencyConverter.apiKey}")
//	private String API_KEY;
//	@Override
//	public DataDto getCurrency(CurrencyDto currencyDto) {
//		try {
//			String url = String.format("?currencies=%s&base_currency=%s",
//					currencyDto.getConvertType(), currencyDto.getCurrencyType());
//
//			Object object = restClient.get()
//					.uri(url) // Relative path to the base URL
//					.retrieve()
//					.body(new ParameterizedTypeReference<Map<String, Object>>() {});
//
//			String jsonResponse = objectMapper.writeValueAsString(object);
//
//			DataDto dataDto = objectMapper.readValue(jsonResponse, DataDto.class);
//
//			return dataDto;
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//			throw new ResourceNotFoundException("Error Fetching currency data: " + e.getMessage());
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new ResourceNotFoundException("Error Fetching currency data: " + e.getMessage());
//		}
//	}
}

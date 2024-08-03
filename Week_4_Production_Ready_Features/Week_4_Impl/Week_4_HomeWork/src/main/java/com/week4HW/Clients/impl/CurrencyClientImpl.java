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

//	@Override
//	public DataDto getCurrency(CurrencyDto currencyDto) {
//		try{
//			Object object = restClient.get()
//					.uri("&currencies="+currencyDto.getConvertType()+"&base_currency="+currencyDto.getCurrencyType())
//					.retrieve()
//					.body(new ParameterizedTypeReference<Object>() {
//					});
//
//			 try{
//				 String jsonResponse = objectMapper.writeValueAsString(object);
//				 return objectMapper.readValue(jsonResponse, DataDto.class);
//			 }catch(JsonMappingException jsonMappingException){
//				 try{
//					 String jsonResponse = objectMapper.writeValueAsString(object);
//					 ErrorResponseDto errorResponseDto = objectMapper.readValue(jsonResponse, ErrorResponseDto.class);
//					 throw new ResourceNotFoundException("Error Fetching currency data: "+errorResponseDto.getErrors());
//				 }catch (Exception e){
//					 e.printStackTrace();
//				 }
//			 }
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//		return null;
//	}

//	//You can do this as well


	@Value("${currencyConverter.apiKey}")
	private String API_KEY;
	@Override
	public DataDto getCurrency(CurrencyDto currencyDto) {
		try {
			String url = String.format("?apikey=%s&currencies=%s&base_currency=%s",
					API_KEY, currencyDto.getConvertType(), currencyDto.getCurrencyType());

			Object object = restClient.get()
					.uri(url) // Relative path to the base URL
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
}
